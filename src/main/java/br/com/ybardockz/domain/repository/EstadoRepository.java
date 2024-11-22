package br.com.ybardockz.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import br.com.ybardockz.domain.model.Estado;

@Component
public interface EstadoRepository extends JpaRepository<Estado, Long> {

}
