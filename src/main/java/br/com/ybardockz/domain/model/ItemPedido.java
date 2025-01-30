package br.com.ybardockz.domain.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemPedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	private Integer quantidade;
	private BigDecimal precoUnitario;
	private BigDecimal precoTotal;
	private String observacao;
	
	@ManyToOne
	private Produto produto;
	
	@ManyToOne
	private Pedido pedido;
	
	public void calcularPrecoTotal() {
		if (quantidade == null) {
	        quantidade = 0;
	    }
		
		if (precoUnitario == null) {
			precoUnitario = BigDecimal.ZERO;
		}
	    
	   this.setPrecoTotal(precoUnitario.multiply(BigDecimal.valueOf(quantidade)));
	}

}