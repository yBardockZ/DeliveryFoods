package br.com.ybardockz.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Schema(hidden = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class Problema {
	
	// RFC 7807
	
	@Schema(example = "Dados inválidos")
	private String title;
	
	@Schema(example = "400")
	private Integer status;
	
	@Schema(example = "https://localhost:8080/dados-invalidos")
	private String type;
	
	@Schema(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
	private String detail;
	
	@Schema(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
	private String userMessage;
	
	@Schema(example = "2025-02-14T20:38:41.3284329")
	private LocalDateTime timestamp;
	
	@Schema(description = "Lista de objetos ou campos que geraram o erro (opcional)")
	private List<Object> objects;
	
	@Schema(name = "ObjetoProblema")
	@Getter
	@Builder
	public static class Object {
		
		@Schema(example = "email")
		private String name;
		
		@Schema(example = "O e-mail do usuário é obrigatório")
		private String userMessage;
		
	}
	
}