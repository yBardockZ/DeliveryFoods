package br.com.ybardockz.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.controller.CidadeController;
import br.com.ybardockz.api.controller.CozinhaController;
import br.com.ybardockz.api.controller.EstadoController;
import br.com.ybardockz.api.controller.FluxoPedidoController;
import br.com.ybardockz.api.controller.FormaPagamentoController;
import br.com.ybardockz.api.controller.PedidoController;
import br.com.ybardockz.api.controller.RestauranteController;
import br.com.ybardockz.api.controller.RestauranteFormaPagamentoController;
import br.com.ybardockz.api.controller.RestauranteProdutoController;
import br.com.ybardockz.api.controller.RestauranteUsuarioController;
import br.com.ybardockz.api.controller.UsuarioController;
import br.com.ybardockz.api.controller.UsuarioGrupoController;

@Component
public class AlgaLinks {

	public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
			new TemplateVariable("page", VariableType.REQUEST_PARAM),
			new TemplateVariable("size", VariableType.REQUEST_PARAM),
			new TemplateVariable("sort", VariableType.REQUEST_PARAM));
	
	public Link linkToPedidos(String rel) {
		String pedidosUrl = linkTo(PedidoController.class).toUri().toString();
		

		TemplateVariables filtrosVariables = new TemplateVariables(
				new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoFinal", VariableType.REQUEST_PARAM),
				new TemplateVariable("clienteId", VariableType.REQUEST_PARAM));

			return Link.of(UriTemplate.of(pedidosUrl, PAGINACAO_VARIABLES.concat(filtrosVariables))
					, rel);
	}
	
	public Link linkToPedidos() {
		return linkToPedidos(IanaLinkRelations.SELF_VALUE);
		
	}
	
	public Link linkToConfirmacaoPedido(String codigoPedido, String rel) {
		return linkTo(methodOn(FluxoPedidoController.class).confirmar(codigoPedido))
				.withRel(rel);
	}
	
	public Link linkToEntregaPedido(String codigoPedido, String rel) {
		return linkTo(methodOn(FluxoPedidoController.class).confirmarEntrega(codigoPedido))
				.withRel(rel);
	}
	
	public Link linkToCancelamentoPedido(String codigoPedido, String rel) {
		return linkTo(methodOn(FluxoPedidoController.class).cancelar(codigoPedido))
				.withRel(rel);
	}
	
	public Link linkToRestaurantes(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteController.class)
				.buscarPorId(restauranteId)).withRel(rel);
	}
	
	public Link linkToRestaurantes(Long restauranteId) {
		return linkToRestaurantes(restauranteId, IanaLinkRelations.SELF_VALUE);
	}
	
	public Link linkToRestaurantes(String rel) {
		String restauranteUrl = linkTo(RestauranteController.class)
				.toUri().toString();
		
		TemplateVariables projecaoVariables = new TemplateVariables(
					new TemplateVariable("projecao", VariableType.REQUEST_PARAM)
				);
		
		return Link.of(UriTemplate.of(restauranteUrl, projecaoVariables), rel);
	}
	
	public Link linkToRestaurantes() {
		return linkToRestaurantes(IanaLinkRelations.SELF_VALUE);
	}
	
	public Link linkToAtivacaoRestaurante(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteController.class).ativar(restauranteId))
				.withRel(rel);
	}
	
	public Link linkToDesativacaoRestaurante(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteController.class).inativar(restauranteId))
				.withRel(rel);
	}
	
	public Link linkToAberturaRestaurante(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteController.class).abrir(restauranteId))
				.withRel(rel);
	}
	
	public Link linkToFechamentoRestaurante(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteController.class).fechar(restauranteId))
				.withRel(rel);
	}
	
	public Link linkToResponsaveis(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteUsuarioController.class).listar(restauranteId))
				.withRel(rel);
	}
	
	public Link linkToResponsaveis(Long restauranteId) {
		return linkToResponsaveis(restauranteId, IanaLinkRelations.SELF_VALUE);
	}
	
	public Link linkToUsuarios(Long usuarioId, String rel) {
		return linkTo(methodOn(UsuarioController.class)
				.buscarPorId(usuarioId)).withRel(rel);
	}
	
	public Link linkToUsuarios(Long usuarioId) {
		return linkToUsuarios(usuarioId, IanaLinkRelations.SELF_VALUE);
	}
	
	public Link linkToUsuarios(String rel) {
		return linkTo(UsuarioController.class).withRel(rel);
	}
	
	public Link linkToUsuarios() {
		return linkToUsuarios(IanaLinkRelations.SELF_VALUE);
	}
	
	public Link linkToGrupos(Long usuarioId, String rel) {
		return linkTo(methodOn(UsuarioGrupoController.class).listar(usuarioId))
				.withRel(rel);
	}
	
	public Link linkToGrupos(Long usuarioId) {
		return linkToGrupos(usuarioId, IanaLinkRelations.SELF_VALUE);
	}
	
	public Link linkToFormasPagamento(Long formaPagamentoId, String rel) {
		return linkTo(methodOn(FormaPagamentoController.class)
				.buscarPorId(formaPagamentoId, null)).withRel(rel);
	}
	
	public Link linkToFormasPagamento(Long formaPagamentoId) {
		return linkToFormasPagamento(formaPagamentoId, IanaLinkRelations.SELF_VALUE);
	}
	
	public Link linkToRestauranteFormaPagamento(Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteFormaPagamentoController.class)
				.listar(restauranteId))
				.withRel(rel);
	}
	
	public Link linkToCidades(Long cidadeId, String rel) {
		return linkTo(methodOn(CidadeController.class)
				.buscarPorId(cidadeId)).withRel(rel);
	}
	
	public Link linkToCidades(Long cidadeId) {
		return linkToCidades(cidadeId, IanaLinkRelations.SELF_VALUE);
	}
	
	public Link linkToCidades(String rel) {
		return linkTo(CidadeController.class).withRel(rel);
	}
	
	public Link linkToProdutos(Long produtoId, Long restauranteId, String rel) {
		return linkTo(methodOn(RestauranteProdutoController.class)
				.buscarPorId(produtoId, restauranteId))
				.withRel(rel);
	}
	
	public Link linkToProdutos(Long produtoId, Long restauranteId) {
		return linkToProdutos(produtoId, restauranteId, IanaLinkRelations.SELF_VALUE);
	}
	
	public Link linkToEstados(String rel) {
		return linkTo(EstadoController.class).withRel(rel);
	}
	
	public Link linkToEstados() {
		return linkToEstados(IanaLinkRelations.SELF_VALUE);
	}
	
	public Link linkToEstados(Long estadoId, String rel) {
		return linkTo(EstadoController.class)
				.slash(estadoId).withRel(rel);
	}
	
	public Link linkToEstados(Long estadoId) {
		return linkToEstados(estadoId, IanaLinkRelations.SELF_VALUE);
	}
	
	public Link linkToCozinhas(String rel) {
		String cozinhaUrl = linkTo(CozinhaController.class).toUri().toString();
		
		return Link.of(UriTemplate.of(cozinhaUrl, PAGINACAO_VARIABLES), rel);
	}
	
	public Link linkToCozinhas() {
		return linkToCozinhas(IanaLinkRelations.SELF_VALUE);
	}
	
	public Link linkToCozinhas(Long cozinhaId, String rel) {
		return linkTo(methodOn(CozinhaController.class).buscar(cozinhaId))
				.withRel(rel);
	}
	
	public Link linkToCozinhas(Long cozinhaId) {
		return linkToCozinhas(cozinhaId, IanaLinkRelations.SELF_VALUE);
	}
	
}
