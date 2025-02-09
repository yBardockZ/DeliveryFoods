package br.com.ybardockz.core.email;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Validated
@Getter
@Setter
@Component
@ConfigurationProperties(value = "algafood.email")
public class EmailProperties {
	
	@NotNull
	private String remetente;
	
	private String sandboxDestinatario;
	
	private Impl impl = Impl.FAKE;
	
	public enum Impl {
		SMTP,
		FAKE,
		SANDBOX
		
	}

}
