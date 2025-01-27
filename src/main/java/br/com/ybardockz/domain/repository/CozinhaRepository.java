package br.com.ybardockz.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.ybardockz.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {
	
	List<Cozinha> findByNome(String nome);
	
}