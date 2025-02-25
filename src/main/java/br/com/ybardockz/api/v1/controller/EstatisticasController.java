package br.com.ybardockz.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ybardockz.api.v1.AlgaLinks;
import br.com.ybardockz.api.v1.openapi.controller.EstatisticasControllerOpenApi;
import br.com.ybardockz.domain.filter.VendaDiariaFilter;
import br.com.ybardockz.domain.model.dto.VendaDiaria;
import br.com.ybardockz.domain.service.VendaQueryService;
import br.com.ybardockz.domain.service.VendaReportService;

@RestController
@RequestMapping("/estatistica")
public class EstatisticasController implements EstatisticasControllerOpenApi {
	
	@Autowired
	private VendaQueryService vendaQueryService;
	
	@Autowired
	private VendaReportService vendaReportService;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	@GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE) 
	public EstatisticaModel estatisticas() {
		EstatisticaModel estatisticaModel = new EstatisticaModel();
		
		estatisticaModel.add(algaLinks.linkToEstatisticasVendasDiarias("vendas-diarias"));
		
		return estatisticaModel;
		
	}
	
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {
		return vendaQueryService.listarVendasDiarias(filter, timeOffSet);
	}
	
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filter,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {
		byte[] bytes = vendaReportService.emitirVendasDiarias(filter, timeOffSet);
		
		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(bytes);
				
				
	}
	
	public static class EstatisticaModel extends RepresentationModel<EstatisticaModel> {
		
	}

}
