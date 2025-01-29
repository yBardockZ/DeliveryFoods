package br.com.ybardockz.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import br.com.ybardockz.domain.model.enums.StatusPedido;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	private Instant dataCriacao; 
	
	private Instant dataConfirmacao;
	
	private Instant dataCancelamento;
	
	private Instant dataEntrega;
	
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
	
	@Column(name = "status_pedido")
	@Enumerated(EnumType.STRING)
	private StatusPedido status;
	
}
