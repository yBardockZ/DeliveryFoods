package br.com.ybardockz.infraestructure.repository.spec;

import java.util.ArrayList;

import org.springframework.data.jpa.domain.Specification;

import br.com.ybardockz.domain.model.Pedido;
import br.com.ybardockz.domain.repository.filter.PedidoFilter;
import jakarta.persistence.criteria.Predicate;

public class PedidoSpecs {

	public static Specification<Pedido> usandoFiltro(PedidoFilter filtro) {
		return (root, query, builder) -> {
			var predicates = new ArrayList<Predicate>();
			root.fetch("restaurante").fetch("cozinha");
			root.fetch("cliente");
			root.fetch("enderecoEntrega").fetch("cidade").fetch("estado");
			
			if (filtro.getClienteId() != null) {
				predicates.add(builder.equal(root.get("cliente").get("id"), filtro.getClienteId()));
			}
			
			if (filtro.getRestauranteId() != null) {
				predicates.add(builder.equal(root.get("restaurante").get("id"), filtro.getRestauranteId()));
			}
			
			if (filtro.getDataCriacaoInicio() != null) {
				predicates.add(builder.greaterThan(root.get("dataCriacao"), filtro.getDataCriacaoInicio()));
			}
			
			if (filtro.getDataCriacaoFinal() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"), filtro.getDataCriacaoFinal()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}
	
}
