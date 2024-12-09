package br.com.ybardockz;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadastroCozinhaIT {
	
	@LocalServerPort
	private int port;
	
	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinha";
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
			.body("", hasSize(4))
			.body("nome", hasItems("Tailandesa", "Indiana"));
		
		
	}



}
