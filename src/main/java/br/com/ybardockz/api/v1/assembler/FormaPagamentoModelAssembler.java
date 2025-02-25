package br.com.ybardockz.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.v1.AlgaLinks;
import br.com.ybardockz.api.v1.controller.FormaPagamentoController;
import br.com.ybardockz.api.v1.model.domain.FormaPagamentoModel;
import br.com.ybardockz.domain.model.FormaPagamento;

@Component
public class FormaPagamentoModelAssembler extends 
	RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public FormaPagamentoModelAssembler() {
		super(FormaPagamentoController.class, FormaPagamentoModel.class);
	}
	
	public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
		FormaPagamentoModel formaPagamentoModel = 
				createModelWithId(formaPagamento.getId(), formaPagamento);
		
		modelMapper.map(formaPagamento, formaPagamentoModel);
		
		formaPagamentoModel.add(algaLinks.linkToFormasPagamento("formasPagamento"));
		
		return formaPagamentoModel;
	}
	
	@Override
	public CollectionModel<FormaPagamentoModel> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
		return super.toCollectionModel(entities)
				.add(algaLinks.linkToFormasPagamento());
	}
	
	/*
	public List<FormaPagamentoModel> toCollectionModel(Collection<FormaPagamento> formasDePagamento) {
		List<FormaPagamentoModel> formasDePagamentoModel = formasDePagamento.stream()
				.map((formaDePagamento) -> toModel(formaDePagamento))
				.collect(Collectors.toList());
		
		return formasDePagamentoModel;
	}
	*/

}
