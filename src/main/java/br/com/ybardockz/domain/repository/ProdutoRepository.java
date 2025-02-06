package br.com.ybardockz.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ybardockz.domain.model.FotoProduto;
import br.com.ybardockz.domain.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>,
	ProdutoRepositoryQueries{
	
	Optional<Produto> findByRestauranteIdAndId(Long restauranteId, Long id);
	
	List<Produto> findProdutosByRestauranteId(Long restauranteId);
	
	@Query("SELECT p FROM Produto p WHERE p.restaurante.id = :restauranteId AND p.ativo = true")
	List<Produto> findProdutosAtivosByRestauranteId(@Param("restauranteId") Long restauranteId);
	
	@Query("select f from FotoProduto f join f.produto p "
			+ "where p.restaurante.id = :restauranteId and f.produto.id = :produtoId")
	Optional<FotoProduto> findFotoById(Long restauranteId, Long produtoId);
	
}
