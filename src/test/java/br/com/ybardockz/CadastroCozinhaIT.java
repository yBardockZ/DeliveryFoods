package br.com.ybardockz;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import br.com.ybardockz.domain.model.Cozinha;
import br.com.ybardockz.domain.repository.CozinhaRepository;
import br.com.ybardockz.util.DatabaseCleaner;
import br.com.ybardockz.util.JsonReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CadastroCozinhaIT {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	private int numeroDeCozinhas;
	
	private static final Long cozinhaIdInexistente = 100L;
	
	private Cozinha cozinhaAmericana;
	
	private String cozinhaJsonCorreto;

    @BeforeEach
    void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinha";
		
		cozinhaJsonCorreto = JsonReader.getContentFromResource("json/correto/CozinhaData.json");
		
		databaseCleaner.clearTables();
		
		prepararDados();
	}

    @Test
    void deveRetornarCorpoEStatusCorretos_QuandoConsultarCozinhaExistente() {
		given()
		.accept(ContentType.JSON)
		.pathParam("cozinhaId", cozinhaAmericana.getId())
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(200)
			.body("nome", equalTo(cozinhaAmericana.getNome()));
	}


    @Test
    void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
		given()
		.accept(ContentType.JSON)
		.pathParam("cozinhaId", cozinhaIdInexistente)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());

	}

    @Test
    void deveRetornar200_QuandoConsultarCozinhas() {
		given()
		.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
		
		
	}

    @Test
    void deveConterQuantidadeCorretaDeCozinhas_QuandoListarCozinhas() {
		given()
		.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", hasSize(numeroDeCozinhas));
		
		
	}

    @Test
    void deveRetornarStatus201_QuandoCadastrarCozinha() {
		given()
		.body(cozinhaJsonCorreto)
		.contentType(ContentType.JSON)
		.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
				
	}
	
	private void prepararDados() {
		List<Cozinha> cozinhas = new ArrayList<>();	
		
		Cozinha cozinhaTailandesa = new Cozinha(null, "Tailandesa");
		cozinhaAmericana = new Cozinha(null, "Americana");
		
		cozinhas.add(cozinhaTailandesa);
		cozinhas.add(cozinhaAmericana);
		
		cozinhaRepository.saveAll(cozinhas);
		
		numeroDeCozinhas = cozinhas.size();
	}

}
