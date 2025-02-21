package br.com.ybardockz.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurante {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	private String nome;
	
	@Column(nullable = false)
	private BigDecimal taxaFrete;

	@ManyToMany
	@JoinTable(name = "restaurante_pagamento",
	joinColumns = @JoinColumn(name = "restaurante_id"),
	inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private Set<FormaPagamento> formasDePagamento = new HashSet<>();

	@ManyToOne
	@JoinColumn(nullable = false)
	private Cozinha cozinha;
	
	@Embedded
	private Endereco endereco;
	
	private Boolean ativo = Boolean.TRUE;
	
	private Boolean aberto = Boolean.FALSE;
	
	@CreationTimestamp
	@Column(nullable = false)
	private Instant dataCadastro;
	
	@UpdateTimestamp
	@Column(nullable = false)
	private Instant dataAtualizacao;
	
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();
	
	@ManyToMany
	@JoinTable(name = "restaurante_usuario_responsavel", 
	joinColumns = @JoinColumn(name = "restaurante_id"),
	inverseJoinColumns = @JoinColumn(name = "usuario_id"))
	private Set<Usuario> usuariosResponsaveis = new HashSet<>();
	
	public boolean podeAtivar() {
		return this.ativo == false;
	}
	
	public boolean podeAbrir() {
		return this.aberto == false;
	}
	
	public void ativar() {
		ativo = true;
	}
	
	public void inativar() {
		ativo = false;
	}
	
	public void abrir() {
		this.aberto = Boolean.TRUE;
	}
	
	public void fechar() {
		this.aberto = Boolean.FALSE;
	}
	
	public void removerFormaPagamento(FormaPagamento formaPagamento) {
		this.formasDePagamento.remove(formaPagamento);
	}
	
	public boolean adicionarFormaPagamento(FormaPagamento formaPagamento) {
		return this.formasDePagamento.add(formaPagamento);
	}
	
	public boolean aceitaFormaDePagamento(FormaPagamento formaPagamento) {
		return this.formasDePagamento.contains(formaPagamento);
	}
	
	public void adicionarUsuario(Usuario usuario) {
		this.usuariosResponsaveis.add(usuario);
	}
	
	public void removerUsuario(Usuario usuario) {
		this.usuariosResponsaveis.remove(usuario);
	}
	
}
