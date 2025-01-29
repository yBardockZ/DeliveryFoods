package br.com.ybardockz.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ybardockz.domain.model.Grupo;
import br.com.ybardockz.domain.model.Permissao;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {
	
	@Query("SELECT p "
			+ "FROM Grupo g "
			+ "JOIN g.permissoes p "
			+ "WHERE g.id = :grupoId "
			+ "AND p.id = :permissaoId")
	Optional<Permissao> findByGrupoIdAndPermissaoId(@Param("grupoId") Long grupoId, 
			@Param("permissaoId") Long permissaoId);
	
}
