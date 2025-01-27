package br.com.ybardockz.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.com.ybardockz.domain.model.Usuario;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByEmail(String email);

}
