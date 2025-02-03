package br.com.ybardockz.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.ybardockz.domain.filter.VendaDiariaFilter;
import br.com.ybardockz.domain.model.dto.VendaDiaria;

@Service
public interface VendaQueryService {
	
	List<VendaDiaria> listarVendasDiarias(VendaDiariaFilter filter);

}
