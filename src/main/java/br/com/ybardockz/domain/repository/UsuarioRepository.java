package br.com.ybardockz.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ybardockz.domain.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
