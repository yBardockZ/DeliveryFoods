package br.com.ybardockz.domain.model.jpa;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.ybardockz.AlgafoodApiApplication;
import br.com.ybardockz.domain.model.Restaurante;
import br.com.ybardockz.domain.repository.RestauranteRepositoryQueries;

public class ConsultaRestauranteMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		RestauranteRepositoryQueries service = applicationContext.getBean(RestauranteRepositoryQueries.class);
		
		List<Restaurante> restaurantes = service.find("a", BigDecimal.ONE, BigDecimal.valueOf(2D));
		restaurantes.forEach(System.out::println);
		

		
		
	}

}
