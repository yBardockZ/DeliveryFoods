package br.com.ybardockz.domain.service;

import br.com.ybardockz.domain.filter.VendaDiariaFilter;

public interface VendaReportService {
	
	byte[] emitirVendasDiarias(VendaDiariaFilter filter, String timeOffSet);

}
