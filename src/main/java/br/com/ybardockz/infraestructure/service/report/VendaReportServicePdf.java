package br.com.ybardockz.infraestructure.service.report;

import org.springframework.stereotype.Service;

import br.com.ybardockz.domain.filter.VendaDiariaFilter;
import br.com.ybardockz.domain.service.VendaReportService;

@Service
public class VendaReportServicePdf implements VendaReportService {

	@Override
	public byte[] emitirVendasDiarias(VendaDiariaFilter filter, String timeOffSet) {
		// TODO Auto-generated method stub
		return null;
	}


}
