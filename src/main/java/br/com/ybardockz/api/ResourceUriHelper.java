package br.com.ybardockz.api;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ResourceUriHelper {
	
	public static void addUriInResponseHeader(Object resourceId) {
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(resourceId).toUri();
		
		HttpServletResponse response = ((ServletRequestAttributes)
				RequestContextHolder.currentRequestAttributes()).getResponse();
		
		response.setHeader(HttpHeaders.LOCATION, uri.toString());
		
	}

}
