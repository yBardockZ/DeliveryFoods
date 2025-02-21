package br.com.ybardockz.api.controller;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import br.com.ybardockz.api.model.assembler.FormaPagamentoInputDisassembler;
import br.com.ybardockz.api.model.assembler.FormaPagamentoModelAssembler;
import br.com.ybardockz.api.model.domain.FormaPagamentoModel;
import br.com.ybardockz.api.model.input.FormaPagamentoInput;
import br.com.ybardockz.api.openapi.controller.FormaPagamentoControllerOpenApi;
import br.com.ybardockz.domain.model.FormaPagamento;
import br.com.ybardockz.domain.repository.FormaPagamentoRepository;
import br.com.ybardockz.domain.service.CadastroFormaPagamentoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "forma-pagamento", 
produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {
	
	@Autowired
	private FormaPagamentoRepository repository;
	
	@Autowired
	private CadastroFormaPagamentoService service;
	
	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
	
	@Autowired
	private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;
	
	@GetMapping
	public ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		String etag = "0";
		
		Instant dataUltimaAtualizacao = repository.consultarDataAtualizada();
		
		if (dataUltimaAtualizacao != null) {
			etag = String.valueOf(dataUltimaAtualizacao.toEpochMilli());
		} 
		
		if (request.checkNotModified(etag)) {
			return null;
		}
		
		List<FormaPagamento> formasPagamento = repository.findAll();
		
		CollectionModel<FormaPagamentoModel> formasPagamentoModel = 
				formaPagamentoModelAssembler.toCollectionModel(formasPagamento);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10L, TimeUnit.SECONDS).cachePublic())
				.eTag(etag)
				.body(formasPagamentoModel);
	}
	
	@GetMapping("/{formaPagamentoId}")
	public ResponseEntity<FormaPagamentoModel> buscarPorId(@PathVariable Long formaPagamentoId,
			ServletWebRequest request) {
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		
		String etag = "0";
		
		Instant ultimaDataAtualizacao = repository.consultarDataAtualizada();
		
		if (ultimaDataAtualizacao != null) {
			etag = String.valueOf(ultimaDataAtualizacao.getEpochSecond());
		}
		
		if (request.checkNotModified(etag)) {
			return null;
		}
		
		FormaPagamento formaPagamento = service.buscarOuFalhar(formaPagamentoId);
		
		FormaPagamentoModel formaPagamentoModel = formaPagamentoModelAssembler
				.toModel(formaPagamento);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10L, TimeUnit.SECONDS))
				.body(formaPagamentoModel);
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoModel adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
		FormaPagamento formaPagamentoDomain = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);
		
		return formaPagamentoModelAssembler.toModel(service.salvar(formaPagamentoDomain));
	}
	
	@PutMapping("/{formaPagamentoId}")
	public FormaPagamentoModel atualizar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput,
			@PathVariable Long formaPagamentoId) {
		
		FormaPagamento formaPagamentoDomain = service.buscarOuFalhar(formaPagamentoId);
		
		formaPagamentoInputDisassembler.copyToDomain(formaPagamentoInput, formaPagamentoDomain);
		
		return formaPagamentoModelAssembler.toModel(service.salvar(formaPagamentoDomain));
		
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	public void remover(@PathVariable Long formaPagamentoId) {
		service.remover(formaPagamentoId);
	}
	

}
