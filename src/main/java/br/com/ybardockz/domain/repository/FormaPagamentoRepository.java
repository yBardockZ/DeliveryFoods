package br.com.ybardockz.domain.repository;

import java.time.Instant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ybardockz.domain.model.FormaPagamento;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long>{

	@Query("select max(dataAtualizacao) from FormaPagamento")
	Instant consultarDataAtualizada();
	
}
