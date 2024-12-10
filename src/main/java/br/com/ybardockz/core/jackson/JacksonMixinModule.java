package br.com.ybardockz.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;

import br.com.ybardockz.api.model.mixin.CidadeMixin;
import br.com.ybardockz.api.model.mixin.CozinhaMixin;
import br.com.ybardockz.api.model.mixin.RestauranteMixin;
import br.com.ybardockz.domain.model.Cidade;
import br.com.ybardockz.domain.model.Cozinha;
import br.com.ybardockz.domain.model.Restaurante;

@Component
public class JacksonMixinModule extends SimpleModule {
	
	private static final long serialVersionUID = 1L;
	
	public JacksonMixinModule() {
		setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
		setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
		setMixInAnnotation(Cidade.class, CidadeMixin.class);
	}

}

