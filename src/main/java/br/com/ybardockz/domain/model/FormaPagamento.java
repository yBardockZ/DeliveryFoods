package br.com.ybardockz.domain.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class FormaPagamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	private String descricao;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "formasDePagamento", fetch = FetchType.EAGER)
	private List<Restaurante> restaurantes = new ArrayList<>();
	
	public FormaPagamento() {
		
	}

	public FormaPagamento(Long id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "FormaPagamento [id=" + id + ", descricao=" + descricao + "]";
	}
	
}
