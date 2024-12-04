package br.com.ybardockz;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadastroCozinhaIT {
	
	@LocalServerPort
	private int port;
	
	@Test
	public void deveRetornar200_QuandoConsultarCozinhas() {
		given()
		.port(port)
		.basePath("/cozinha")
		.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(200);
		
		
	}



}
