package br.com.ybardockz.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import br.com.ybardockz.domain.event.PedidoConfirmadoEvent;
import br.com.ybardockz.domain.exception.NegocioException;
import br.com.ybardockz.domain.model.enums.StatusPedido;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Pedido extends AbstractAggregateRoot<Pedido> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	private String codigo;
	
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	private FormaPagamento formaPagamento;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itens;
	
	@Column(name = "status_pedido")
	@Enumerated(EnumType.STRING)
	private StatusPedido status = StatusPedido.CRIADO;
	
	public void calculoValorTotal() {
		this.getItens().forEach((item) -> item.calcularPrecoTotal());
		
		BigDecimal subTotal = BigDecimal.ZERO;
		
		for (ItemPedido item : itens) {
			subTotal = subTotal.add(item.getPrecoTotal());
		}
		
		this.setSubTotal(subTotal);
		this.setValorTotal(this.subTotal.add(this.taxaFrete));
		
	}
	
	public void confirmar() {
		setStatus(StatusPedido.CONFIRMADO); 
		this.dataConfirmacao = Instant.now();
		
		registerEvent(new PedidoConfirmadoEvent(this));
	}
	
	public void confirmarEntrega() {
		setStatus(StatusPedido.ENTREGUE);
		this.dataEntrega = Instant.now();
	}
	
	public void cancelar() {
		setStatus(StatusPedido.CANCELADO);
		this.dataCancelamento = Instant.now();
	}
	
	private void setStatus(StatusPedido status) {
		if (this.status.podeAlterarPara(status)) {
			this.status = status;
		} else {
			throw new NegocioException("O status do pedido de código: " + this.codigo + 
					" Não pode ser alterado de '" + this.status.getDescricao() + "'" +
					" para '" + status.getDescricao() + "'.");
		}
		
	}
	
	@PrePersist
	private void gerarCodigo() {
		this.setCodigo(UUID.randomUUID().toString());
	}
	
}
