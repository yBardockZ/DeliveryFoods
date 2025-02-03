package br.com.ybardockz.core.data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableTranslator {
	
	public static Pageable translatePage(Pageable pageable, Map<String, String> mappedFields) {
		List<Sort.Order> orders = pageable.getSort().stream()
				.filter(order -> mappedFields.containsKey(order.getProperty()))
				.map(order -> new Sort.Order(order.getDirection(), mappedFields.get(order.getProperty())))
				.collect(Collectors.toList());
		
		return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
				 Sort.by(orders));
	}

}
