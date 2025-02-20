package br.com.ybardockz.api.model.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import br.com.ybardockz.api.controller.EstadoController;
import br.com.ybardockz.api.model.domain.EstadoModel;
import br.com.ybardockz.domain.model.Estado;

@Component
public class EstadoModelAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoModel> {

	@Autowired
	private ModelMapper modelMapper;
	
	public EstadoModelAssembler() {
		super(EstadoController.class, EstadoModel.class);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public EstadoModel toModel(Estado estado) {
		EstadoModel estadoModel = createModelWithId(estado.getId(), estado);
		
		modelMapper.map(estado, estadoModel);
		
		estadoModel.add(linkTo(EstadoController.class).withRel("estados"));
		
		return estadoModel;
	}
	
	@Override
	public CollectionModel<EstadoModel> toCollectionModel(Iterable<? extends Estado> entities) {
		System.out.println("Adicionando o link...");
		return super.toCollectionModel(entities).add(linkTo(EstadoController.class).withSelfRel());
	}
	
	/*public List<EstadoModel> toCollectionModel(List<Estado> estados) {
		List<EstadoModel> estadosModel = estados.stream()
				.map(estado -> toModel(estado))
				.collect(Collectors.toList());
		
		return estadosModel;
	}
	*/
	
	
}
