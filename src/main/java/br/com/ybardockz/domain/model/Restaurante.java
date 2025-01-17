package br.com.ybardockz.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

//@ValorZeroIncluiDescricao(descricaoField = "nome", 
//descricaoObrigatoria = "Frete Grátis", valorField = "taxaFrete")
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurante {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	//@NotBlank
	private String nome;
	
	//@NotNull
	//@PositiveOrZero
	@Column(nullable = false)
	private BigDecimal taxaFrete;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "restaurante_pagamento",
	joinColumns = @JoinColumn(name = "restaurante_id"),
	inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private List<FormaPagamento> formasDePagamento = new ArrayList<>();
	
	//@Valid
	//@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
	//@NotNull
	@ManyToOne
	@JoinColumn(nullable = false)
	private Cozinha cozinha;
	
	@Embedded
	private Endereco endereco;
	
	@CreationTimestamp
	@Column(nullable = false)
	private Instant dataCadastro;
	
	@UpdateTimestamp
	@Column(nullable = false)
	private Instant dataAtualizacao;
	
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();
	
}
