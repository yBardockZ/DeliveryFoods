package br.com.ybardockz.domain.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.ybardockz.domain.model.Permissao;

@Component
public interface PermissaoRepository {
	
	List<Permissao> listar();
	Permissao buscarPorId(Long id);
	Permissao salvar(Permissao permissao);
	void remover(Permissao permissao);

}
