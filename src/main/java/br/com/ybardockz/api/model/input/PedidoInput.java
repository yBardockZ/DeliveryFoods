package br.com.ybardockz.api.model.input;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoInput {
	
	@NotNull
	@Valid
	private RestauranteIdInput restaurante;
	
	@NotNull
	@Valid
	private FormaPagamentoIdInput formaPagamento;
	
	@NotNull
	@Valid
	private EnderecoInput enderecoEntrega;
	
	@NotNull
	@Size(min = 1)
	@Valid
	private List<ItemPedidoInput> itens;

}
