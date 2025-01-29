package br.com.ybardockz.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ybardockz.domain.model.Permissao;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

    @Query("SELECT p FROM Permissao p WHERE p IN (SELECT perm FROM Grupo g JOIN g.permissoes perm WHERE g.id = :grupoId)")
    List<Permissao> findPermissoesByGrupoId(@Param("grupoId") Long grupoId);

}
