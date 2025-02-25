package br.com.ybardockz.api.v1.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ybardockz.domain.model.Cozinha;
import br.com.ybardockz.domain.model.Restaurante;
import br.com.ybardockz.domain.repository.CozinhaRepository;
import br.com.ybardockz.domain.repository.RestauranteRepository;

@RestController
@RequestMapping("/teste")
public class TesteController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	
	@GetMapping(path = "/cozinhas/por-nome")
	public List<Cozinha> buscarPorNome(@RequestParam("nome") String nome) {
		return cozinhaRepository.findByNome(nome);
		
	}
	
	@GetMapping(path = "/restaurantes/por-nome")
	public List<Restaurante> buscarPorNomeAndId(@RequestParam("nome") String nome, @RequestParam("id") Long id) {
		return restauranteRepository.consultarPorNome(nome, id);
	}
	
	@GetMapping(path = "/restaurantes/por-nome-taxa-frete")
	public List<Restaurante> find(@RequestParam(name = "nome", required = false) String nome,
	@RequestParam("taxaFreteInicial") BigDecimal taxaFreteInicial, 
	@RequestParam("taxaFreteFinal")BigDecimal taxaFreteFinal) {
		return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
		
		
	}
	
	@GetMapping(path = "/restaurantes/com-frete-gratis")
	public List<Restaurante> restaurantesComFreteGratis(@RequestParam("nome") String nome) {
		return restauranteRepository.restaurantesComFreteGratis(nome);
		
	}
	
	@GetMapping(path = "/restaurantes/buscar-primeiro")
	public Optional<Restaurante> buscarPrimeiroRestaurante() {
		return restauranteRepository.findFirst();
	}

}
