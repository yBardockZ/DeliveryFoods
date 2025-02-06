package br.com.ybardockz.infraestructure.repository;

import org.springframework.stereotype.Repository;

import br.com.ybardockz.domain.model.FotoProduto;
import br.com.ybardockz.domain.repository.ProdutoRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {
	
	@PersistenceContext
	private EntityManager em;

	@Transactional
	@Override
	public FotoProduto salvar(FotoProduto foto) {
		return em.merge(foto);
	}

	@Transactional
	@Override
	public void deleteFoto(FotoProduto foto) {
		em.remove(foto);
		
	}

}
