package br.com.ybardockz.domain.repository;

import org.springframework.stereotype.Repository;

import br.com.ybardockz.domain.model.Estado;

@Repository
public interface EstadoRepository extends CustomJpaRepository<Estado, Long> {

}
