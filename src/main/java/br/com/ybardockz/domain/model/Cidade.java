package br.com.ybardockz.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cidade {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	//@NotBlank
	@Column(nullable = false)
	private String nome;
	
	//@Valid
	//@ConvertGroup(from = Default.class, to = Groups.EstadoId.class)
	//@NotNull
	@ManyToOne
	@JoinColumn(name = "estado_id")
	private Estado estado;

	
}
