package br.com.ybardockz.infraestructure.service.report;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ybardockz.domain.filter.VendaDiariaFilter;
import br.com.ybardockz.domain.model.dto.VendaDiaria;
import br.com.ybardockz.domain.service.VendaReportService;
import br.com.ybardockz.infraestructure.service.query.VendaQueryServiceImpl;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class VendaReportServicePdf implements VendaReportService {
	
	@Autowired
	private VendaQueryServiceImpl queryService;
	
	@Override
	public byte[] emitirVendasDiarias(VendaDiariaFilter filter, String timeOffSet) {
		
		try {
		
			InputStream inputStream = this.getClass().getResourceAsStream
					("/relatorios/vendas-diarias.jasper");
		
			HashMap<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("REPORT_LOCALE", Locale.of("pt", "BR"));
			
			List<VendaDiaria> collectionSource = queryService.listarVendasDiarias(filter, timeOffSet);
			
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(collectionSource);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, dataSource);
			
			return JasperExportManager.exportReportToPdf(jasperPrint);
			
		} catch (Exception e) {
			throw new ReportException(e.getMessage(), 
					e.getCause());
		}
		
	}


}
