package br.com.ybardockz.domain.model.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.com.ybardockz.AlgafoodApiApplication;
import br.com.ybardockz.domain.model.Restaurante;
import br.com.ybardockz.domain.repository.RestauranteRepository;

public class AlteracaoRestaurante {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		RestauranteRepository service = applicationContext.getBean(RestauranteRepository.class);
		
		Restaurante restaurante = service.findById(1L).get();
		
		System.out.println(restaurante);
		
		restaurante.setNome("Vietnam burguer");
		
		service.save(restaurante);
		
		System.out.println(restaurante);

	}

}
