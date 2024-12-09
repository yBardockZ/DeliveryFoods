package br.com.ybardockz;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import br.com.ybardockz.domain.model.Cozinha;
import br.com.ybardockz.domain.repository.CozinhaRepository;
import br.com.ybardockz.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinha";
		
		databaseCleaner.clearTables();
		
		prepararDados();
	}
	
	@Test
	public void deveRetornar200_QuandoConsultarCozinhas() {
		given()
		.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(200);
		
		
	}
	
	@Test
	public void deveConterQuatroCozinhas_QuandoConsultarCozinhas() {
		given()
		.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", hasSize(2))
			.body("nome", hasItems("Tailandesa", "Indiana"));
		
		
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {
		given()
		.body("{ \"nome\": \"Chinesa\" }")
		.contentType(ContentType.JSON)
		.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(201);
				
	}
	
	private void prepararDados() {
		Cozinha cozinha1 = new Cozinha(null, "Tailandesa");
		cozinhaRepository.save(cozinha1);
		Cozinha cozinha2 = new Cozinha(null, "Americana");
		cozinhaRepository.save(cozinha2);
	}

}
