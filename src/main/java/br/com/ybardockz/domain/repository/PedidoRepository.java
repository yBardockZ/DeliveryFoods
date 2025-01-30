package br.com.ybardockz.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ybardockz.domain.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	@Query("SELECT DISTINCT p FROM Pedido p"
			+ " join fetch p.cliente join fetch p.restaurante r"
			+ " join fetch r.cozinha"
			+ " join fetch p.enderecoEntrega e"
			+ " join fetch e.cidade c"
			+ " join fetch c.estado")
	List<Pedido> findAll();
	
}
