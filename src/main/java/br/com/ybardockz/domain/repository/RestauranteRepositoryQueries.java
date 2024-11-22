  package br.com.ybardockz.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.ybardockz.domain.model.Restaurante;

@Repository
public interface RestauranteRepositoryQueries {
	
	List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal TaxaFreteFinal);
	
	List<Restaurante> restaurantesComFreteGratis(String nome);

}
