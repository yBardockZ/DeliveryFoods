package br.com.ybardockz.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import br.com.ybardockz.domain.model.enums.StatusPedido;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	private BigDecimal subTotal;
	
	private BigDecimal taxaFrete;
	
	private BigDecimal  valorTotal;
	
	@CreationTimestamp
	private LocalDateTime dataCriacao; 
	
	private LocalDateTime dataConfirmacao;
	
	private LocalDateTime dataCancelamento;
	
	private LocalDateTime dataEntrega;
	
	@ManyToOne
	private Restaurante restaurante;
	
	@ManyToOne
	private Usuario cliente;
	
	@Embedded
	private Endereco enderecoEntrega;
	
	@ManyToOne
	private FormaPagamento formaPagamento;
	
	@OneToMany(mappedBy = "pedido")
	private List<ItemPedido> itens;
	
	private StatusPedido status;
	
}
