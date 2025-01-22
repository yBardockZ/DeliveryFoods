package br.com.ybardockz.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;

import br.com.ybardockz.api.model.mixin.CidadeMixin;
import br.com.ybardockz.domain.model.Cidade;

@Component
public class JacksonMixinModule extends SimpleModule {
	
	private static final long serialVersionUID = 1L;
	
	public JacksonMixinModule() {
		setMixInAnnotation(Cidade.class, CidadeMixin.class);
	}

}

