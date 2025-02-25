package br.com.ybardockz.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.ybardockz.api.v1.model.domain.EnderecoModel;
import br.com.ybardockz.api.v1.model.input.ItemPedidoInput;
import br.com.ybardockz.domain.model.Endereco;
import br.com.ybardockz.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
    	var modelMapper = new ModelMapper();
    	
    	var enderecoToEnderecoModelTypeMap = modelMapper.createTypeMap(Endereco.class,
    			EnderecoModel.class);
    	
    	enderecoToEnderecoModelTypeMap.addMapping(src -> src.getCidade().getEstado().getNome(),
    			(dest, value) -> dest.getCidade().setEstado((String) value));
  
    	modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
		.addMappings(mapper -> mapper.skip(ItemPedido::setId));
    	
		return modelMapper;
	}

}
