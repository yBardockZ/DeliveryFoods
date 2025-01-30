package br.com.ybardockz;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import br.com.ybardockz.domain.model.Cozinha;
import br.com.ybardockz.domain.model.Restaurante;
import br.com.ybardockz.domain.repository.CozinhaRepository;
import br.com.ybardockz.domain.repository.RestauranteRepository;
import br.com.ybardockz.util.DatabaseCleaner;
import br.com.ybardockz.util.JsonReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CadastroRestauranteIT {
	
	private static final Long RESTAURANTE_ID_INEXISTENTE = 100L;
	
	private static final String DADOS_INVALIDOS_PROBLEM_TITLE = "Dados inválidos";
	
	private static final String VIOLACAO_DE_REGRA_NEGOCIO_PROBLEM_TITLE = "Violação de regra de negócio";
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	private String jsonRestauranteCorreto;
	
	private String jsonRestauranteComCozinhaInexistente;
	
	private String jsonRestauranteSemTaxaFrete;
	
	private String jsonRestauranteSemCozinha;
	
	private int numeroDeRestaurantes;
	
	private Restaurante restauranteTeste;

    @BeforeEach
    void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.basePath = "/restaurante";
		RestAssured.port = port;
		
		databaseCleaner.clearTables();
		prepararDados();
		
		jsonRestauranteCorreto = JsonReader.getContentFromResource("json/correto/"
				+ "RestauranteData.json");
		
		jsonRestauranteComCozinhaInexistente = JsonReader.getContentFromResource("json/incorreto/"
				+ "RestauranteComCozinhaInexistente.json");
		
		jsonRestauranteSemTaxaFrete = JsonReader.getContentFromResource("json/incorreto/"
				+ "RestauranteSemTaxaFrete.json");
		
		jsonRestauranteSemCozinha = JsonReader.getContentFromResource("json/incorreto/"
				+ "RestauranteSemCozinha.json");
		
	}

    @Test
    void deveRetornarCorpoEStatusCorretos_QuandoConsultarRestauranteExistente() {
		Response response = given()
		.accept(ContentType.JSON)
		.pathParam("restauranteId", restauranteTeste.getId())
		.when()
			.get("/{restauranteId}");
		
		response.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(restauranteTeste.getNome()))
			.body("cozinha.nome", equalTo(restauranteTeste.getCozinha().getNome()));
		
			BigDecimal taxaFreteRetornada = new BigDecimal(response.jsonPath().getString("taxaFrete"));
			assertEquals(restauranteTeste.getTaxaFrete(), taxaFreteRetornada);
		
		
	}

    @Test
    void deveRetornarStatus201_QuandoCadastrarRestauranteCorreto() {
		given()
		.body(jsonRestauranteCorreto)
		.contentType(ContentType.JSON)
		.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
		
	}

    @Test
    void deveRetornarStatus404_QuandoConsultarRestauranteInexistente() {
		given()
		.accept(ContentType.JSON)
		.pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
		.when()
			.get("/{restauranteId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
			
	}


    @Test
    void deveRetornarNumeroCorretoDeRestaurantes_QuandoListarRestaurantes() {
		given()
		.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", hasSize(numeroDeRestaurantes));
		
	}

    @Test
    void deveRetornarStatus400_QuandoCadastrarRestauranteComCozinhaInexistente() {
		given()
		.accept(ContentType.JSON)
		.contentType(ContentType.JSON)
		.body(jsonRestauranteComCozinhaInexistente)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(VIOLACAO_DE_REGRA_NEGOCIO_PROBLEM_TITLE));
			
			
	}

    @Test
    void deveRetornaStatus400_QuandoCadastrarRestauranteSemTaxaFrete() {
		given()
		.accept(ContentType.JSON)
		.contentType(ContentType.JSON)
		.body(jsonRestauranteSemTaxaFrete)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
	}

    @Test
    void deveRetornarStatus400_QuandoCadastrarRestauranteSemCozinha() {
		given()
		.accept(ContentType.JSON)
		.contentType(ContentType.JSON)
		.body(jsonRestauranteSemCozinha)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("title", equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
	}
	
	private void prepararDados() {
		Cozinha cozinhaTeste = new Cozinha(null, "cozinhaTeste");
		cozinhaRepository.save(cozinhaTeste);
		
		restauranteTeste = new Restaurante();
		restauranteTeste.setNome("Brasileiro");
		restauranteTeste.setTaxaFrete(BigDecimal.valueOf(2.0));
		restauranteTeste.setCozinha(cozinhaTeste);
		
		Restaurante restaurante2 = new Restaurante();
		restaurante2.setNome("Argentino");
		restaurante2.setTaxaFrete(BigDecimal.valueOf(3.0));
		restaurante2.setCozinha(cozinhaTeste);
		
		restauranteTeste = restauranteRepository.save(restauranteTeste);
		restauranteRepository.save(restaurante2);
		
		numeroDeRestaurantes = (int) restauranteRepository.count();
		
		
	}

}
