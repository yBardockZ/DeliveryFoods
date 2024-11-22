package br.com.ybardockz.domain.model.jpa;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.ybardockz.AlgafoodApiApplication;
import br.com.ybardockz.domain.model.Restaurante;
import br.com.ybardockz.domain.repository.RestauranteRepository;

public class InclusaoRestauranteMain {
	
	public static void main(String args[]) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		RestauranteRepository service = applicationContext.getBean(RestauranteRepository.class);
		
		Restaurante restaurante = new Restaurante(null, "Tenore", new BigDecimal("2.0"));
		
		service.save(restaurante);
		
		List<Restaurante> restaurantes = service.findAll();
		
		restaurantes.forEach(System.out::println);
		
		
	}

}
