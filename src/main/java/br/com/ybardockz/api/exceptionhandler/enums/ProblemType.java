package br.com.ybardockz.api.exceptionhandler.enums;

public enum ProblemType {
	
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	ERRO_INTERNO("/erro-interno", "Erro Interno"),
	ACESSO_NEGADO("/acesso-negado", "Acesso negado"),
	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos");
	
	private String title;
	private String uri;
	
	private ProblemType(String path, String title) {
		this.title = title;
		this.uri = "https://localhost:8080" + path;
	}


	public String getTitle() {
		return title;
	}


	public String getUri() {
		return uri;
	}
	
}
