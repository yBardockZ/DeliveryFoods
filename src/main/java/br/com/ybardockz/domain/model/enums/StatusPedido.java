package br.com.ybardockz.domain.model.enums;

public enum StatusPedido {
	
	CRIADO("criado"),
	CONFIRMADO("confirmado"),
	ENTREGUE("entregue"),
	CANCELADO("cancelado");

	private String status;
	
	StatusPedido(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	
}
