package br.com.ybardockz.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.ybardockz.api.AlgaLinks;
import br.com.ybardockz.api.model.assembler.FormaPagamentoModelAssembler;
import br.com.ybardockz.api.model.domain.FormaPagamentoModel;
import br.com.ybardockz.api.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import br.com.ybardockz.domain.model.Restaurante;
import br.com.ybardockz.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(path = "/restaurante/{restauranteId}/forma-pagamento",
		produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {
	
	@Autowired
	private CadastroRestauranteService restauranteService;
	
	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoAssembler;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@GetMapping
	public CollectionModel<FormaPagamentoModel> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
		
		CollectionModel<FormaPagamentoModel> formasPagamentoModel = 
				formaPagamentoAssembler
				.toCollectionModel(restaurante.getFormasDePagamento())
				.removeLinks()
				.add(algaLinks.linkToRestauranteFormaPagamento(restauranteId));
		
		formasPagamentoModel.getContent().forEach(formaPagamentoModel -> {
			formaPagamentoModel.add(algaLinks
					.linkToRestauranteFormaPagamentoDesassociacao(restauranteId,  
							formaPagamentoModel.getId(), "desassociar"));
		});
		
		return formasPagamentoModel;
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	public ResponseEntity<Void> dissasociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		restauranteService.dessasociarFormaPagamento(restauranteId, formaPagamentoId);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		restauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);

	}
	

}
