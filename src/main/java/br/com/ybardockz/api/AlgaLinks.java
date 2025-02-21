package br.com.ybardockz.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.controller.PedidoController;

@Component
public class AlgaLinks {

	public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
			new TemplateVariable("page", VariableType.REQUEST_PARAM),
			new TemplateVariable("size", VariableType.REQUEST_PARAM),
			new TemplateVariable("sort", VariableType.REQUEST_PARAM));
	
	public Link linkToPedidos() {
		String pedidosUrl = linkTo(PedidoController.class).toUri().toString();
		

		TemplateVariables filtrosVariables = new TemplateVariables(
				new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoFinal", VariableType.REQUEST_PARAM),
				new TemplateVariable("clienteId", VariableType.REQUEST_PARAM));

			return Link.of(UriTemplate.of(pedidosUrl, PAGINACAO_VARIABLES.concat(filtrosVariables))
					, "pedidos");
	}

}
