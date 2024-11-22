package br.com.ybardockz.infraestructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import br.com.ybardockz.domain.repository.CustomJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID>
	implements CustomJpaRepository<T, ID>{
	
	private EntityManager em;

	public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		
		this.em = entityManager;
	}

	@Override
	public Optional<T> findFirst() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		
		CriteriaQuery<T> criteriaQuery = builder.createQuery(getDomainClass());
		
		Root<T> root = criteriaQuery.from(getDomainClass());
		
		criteriaQuery.select(root);
		
		TypedQuery<T> query = em.createQuery(criteriaQuery);
		
		query.setMaxResults(1);
		
		T entity = query.getSingleResult();
		
		return Optional.ofNullable(entity);
	}

}
