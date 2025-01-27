package br.com.ybardockz.domain.model;

import java.time.Instant;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String senha;
	
	@CreationTimestamp
	private Instant dataCadastro;
	
	@ManyToMany
	@JoinTable(name = "usuario_grupo",
	joinColumns = @JoinColumn(name = "usuario_id"),
	inverseJoinColumns = @JoinColumn(name = "grupo_id"))
	private List<Grupo> grupos;
	
	public void atualizarSenha(String senhaNova) {
		this.senha = senhaNova;
	}
	
	public boolean senhaCoincideCom(String senhaAtual) {
		return this.senha.equals(senhaAtual);
	}

}
