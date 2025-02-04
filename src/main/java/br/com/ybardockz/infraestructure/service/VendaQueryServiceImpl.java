package br.com.ybardockz.infraestructure.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.ybardockz.domain.filter.VendaDiariaFilter;
import br.com.ybardockz.domain.model.Pedido;
import br.com.ybardockz.domain.model.dto.VendaDiaria;
import br.com.ybardockz.domain.model.enums.StatusPedido;
import br.com.ybardockz.domain.service.VendaQueryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<VendaDiaria> listarVendasDiarias(VendaDiariaFilter filter,
			String timeOffSet) {
		var builder = em.getCriteriaBuilder();
		var query = builder.createQuery(VendaDiaria.class);
		var root = query.from(Pedido.class);
		
		var predicates = new ArrayList<Predicate>();
		
		var convertTimeZoneFunction = builder.function
				("convert_tz", LocalDate.class,
						root.get("dataCriacao"),
						builder.literal("+00:00"),
						builder.literal(timeOffSet));
		
		var dateFunctionDataCriacao = builder.function
				("date", LocalDate.class, convertTimeZoneFunction);
		
		var selection = builder.construct(VendaDiaria.class,
				dateFunctionDataCriacao, 
				builder.count(root.get("id")), 
				builder.sum(root.get("valorTotal")));
		
		predicates.add(root.get("status")
				.in(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));
		
		if (filter.getRestauranteId() != null) {
			predicates.add(builder.equal(root.get("restaurante").get("id"), 
					filter.getRestauranteId()));
		}
		
		if (filter.getDataInicio() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"),
					filter.getDataInicio()));
		}
		
		if (filter.getDataFim() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), 
					filter.getDataFim()));
		}
		
		query.select(selection);
		query.where(builder.and(predicates.toArray(new Predicate[0])));
		query.groupBy(dateFunctionDataCriacao);
		
		return em.createQuery(query).getResultList();
	}

}
