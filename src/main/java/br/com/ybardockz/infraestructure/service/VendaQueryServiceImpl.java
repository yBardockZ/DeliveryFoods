package br.com.ybardockz.infraestructure.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.ybardockz.domain.filter.VendaDiariaFilter;
import br.com.ybardockz.domain.model.Pedido;
import br.com.ybardockz.domain.model.dto.VendaDiaria;
import br.com.ybardockz.domain.service.VendaQueryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Component
public class VendaQueryServiceImpl implements VendaQueryService {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<VendaDiaria> listarVendasDiarias(VendaDiariaFilter filter) {
		var builder = em.getCriteriaBuilder();
		var query = builder.createQuery(VendaDiaria.class);
		var root = query.from(Pedido.class);
		
		var dateFunctionDataCriacao = builder.function
				("date", LocalDate.class, root.get("dataCriacao"));
		
		var selection = builder.construct(VendaDiaria.class,
				dateFunctionDataCriacao, 
				builder.count(root.get("id")), 
				builder.sum(root.get("valorTotal")));
		
		query.select(selection);
		query.groupBy(dateFunctionDataCriacao);
		
		return em.createQuery(query).getResultList();
	}

}
