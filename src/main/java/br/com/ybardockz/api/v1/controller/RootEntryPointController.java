package br.com.ybardockz.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ybardockz.api.v1.AlgaLinks;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(hidden = true)
@RestController
@RequestMapping("/v1")
public class RootEntryPointController {
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@GetMapping
	public RootEntryPointModel root() {
		RootEntryPointModel rootEntryPoint = new RootEntryPointModel();
		
		rootEntryPoint.add(algaLinks.linkToCozinhas("cozinhas"));
		rootEntryPoint.add(algaLinks.linkToRestaurantes("restaurantes"));
		rootEntryPoint.add(algaLinks.linkToCidades("cidades"));
		rootEntryPoint.add(algaLinks.linkToEstados("estados"));
		rootEntryPoint.add(algaLinks.linkToFormasPagamento("formas-pagamento"));
		rootEntryPoint.add(algaLinks.linkToGrupos("grupos"));
		rootEntryPoint.add(algaLinks.linkToUsuarios("usuarios"));
		rootEntryPoint.add(algaLinks.linkToPedidos("pedidos"));
		rootEntryPoint.add(algaLinks.linkToPermissoes("permissoes"));
		rootEntryPoint.add(algaLinks.linkToEstatisticas("estatisticas"));
		
		
		return rootEntryPoint;
	}
	
	private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
		
	}

}
