package br.com.ybardockz.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ybardockz.domain.filter.VendaDiariaFilter;
import br.com.ybardockz.domain.model.dto.VendaDiaria;
import br.com.ybardockz.domain.service.VendaQueryService;

@RestController
@RequestMapping("/estatistica")
public class EstatisticasController {
	
	@Autowired
	private VendaQueryService vendaQueryService;
	
	@GetMapping("/vendas-diarias")
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {
		return vendaQueryService.listarVendasDiarias(filter, timeOffSet);
	}

}
