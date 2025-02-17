package br.com.ybardockz.api.model.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoModel {
	
	@Schema(example = "52315-212")
	private String cep;
	
	@Schema(example = "Rua pinheiros trovão")
	private String logradouro;
	
	@Schema(example = "23")
	private String numero;
	
	@Schema(example = "ap 2302, quadra: 1")
	private String complemento;
	
	@Schema(example = "Pinheiros")
	private String bairro;
	
	@Schema(description = "Informações da cidade")
	private CidadeResumoModel cidade;
	
}
