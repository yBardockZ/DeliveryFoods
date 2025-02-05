CREATE TABLE foto_produto (
	produto_id bigint not null,
    nome_arquivo varchar(150) not null,
    descricao varchar(30),
    content_type varchar(30) not null,
    tamanho int not null,

	primary key(produto_id)
) engine=InnoDB default charset=utf8mb4;

ALTER TABLE foto_produto
ADD CONSTRAINT FK_FOTO_PRODUTO foreign key(produto_id) references produto (id);