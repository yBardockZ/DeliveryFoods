package br.com.ybardockz.api.model.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.model.domain.FormaPagamentoModel;
import br.com.ybardockz.domain.model.FormaPagamento;

@Component
public class FormaPagamentoModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public FormaPagamentoModel toModel(FormaPagamento formaPagamento) {
		return modelMapper.map(formaPagamento, FormaPagamentoModel.class);
	}
	
	public List<FormaPagamentoModel> toCollectionModel(Collection<FormaPagamento> formasDePagamento) {
		List<FormaPagamentoModel> formasDePagamentoModel = formasDePagamento.stream()
				.map((formaDePagamento) -> toModel(formaDePagamento))
				.collect(Collectors.toList());
		
		return formasDePagamentoModel;
	}

}
