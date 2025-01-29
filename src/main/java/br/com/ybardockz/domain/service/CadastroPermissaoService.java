package br.com.ybardockz.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ybardockz.domain.exception.PermissaoNaoEncontradaException;
import br.com.ybardockz.domain.model.Permissao;
import br.com.ybardockz.domain.repository.GrupoRepository;
import br.com.ybardockz.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {
	
	@Autowired
	private PermissaoRepository permissaoRepository;
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CadastroGrupoService grupoService;
	
	public Permissao buscarOuFalhar(Long permissaoId) {
		return permissaoRepository.findById(permissaoId)
				.orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
	}
	
	public Permissao buscarOuFalharPorGrupo(Long grupoId, Long permissaoId) {
        grupoService.buscarOuFalhar(grupoId);

        return grupoRepository.findByGrupoIdAndPermissaoId(grupoId, permissaoId)
                .orElseThrow(() -> new PermissaoNaoEncontradaException(grupoId, permissaoId));
    }

}
