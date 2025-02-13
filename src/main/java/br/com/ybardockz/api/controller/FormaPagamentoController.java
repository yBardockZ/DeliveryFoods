package br.com.ybardockz.api.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
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

import br.com.ybardockz.api.model.assembler.FormaPagamentoInputDisassembler;
import br.com.ybardockz.api.model.assembler.FormaPagamentoModelAssembler;
import br.com.ybardockz.api.model.domain.FormaPagamentoModel;
import br.com.ybardockz.api.model.input.FormaPagamentoInput;
import br.com.ybardockz.domain.model.FormaPagamento;
import br.com.ybardockz.domain.repository.FormaPagamentoRepository;
import br.com.ybardockz.domain.service.CadastroFormaPagamentoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/forma-pagamento")
public class FormaPagamentoController {
	
	@Autowired
	private FormaPagamentoRepository repository;
	
	@Autowired
	private CadastroFormaPagamentoService service;
	
	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
	
	@Autowired
	private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;
	
	@GetMapping
	public ResponseEntity<List<FormaPagamentoModel>> listar() {
		List<FormaPagamentoModel> formasDePagamentoModel = 
				formaPagamentoModelAssembler.toCollectionModel(repository.findAll());
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10L, TimeUnit.SECONDS))
				.body(formasDePagamentoModel);
	}
	
	@GetMapping("/{formaPagamentoId}")
	public FormaPagamentoModel buscarPorId(@PathVariable Long formaPagamentoId) {
		FormaPagamento formaPagamento = service.buscarOuFalhar(formaPagamentoId);
		
		return formaPagamentoModelAssembler.toModel(formaPagamento);
		
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
