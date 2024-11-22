
create table pedido (
	id bigint not null auto_increment,
    sub_total decimal(38,2) not null,
    taxa_frete decimal(38,2) not null,
    valor_total decimal(38,2) not null,
	data_criacao datetime(2) not null,
    data_confirmacao datetime(2),
    data_cancelamento datetime(2),
    data_entrega datetime(2),
    restaurante_id bigint not null,
    cliente_id bigint not null,
    endereco_cep varchar(20) not null,
    endereco_logradouro varchar(60) not null,
    endereco_numero varchar(20) not null,
    endereco_complemento varchar(60),
    endereco_bairro varchar(60) not null,
    endereco_cidade_id bigint not null,
    forma_pagamento bigint not null,
    status_pedido varchar(60) not null,
    
    primary key(id)
) engine=InnoDB default charset=utf8mb4;

create table item_pedido (

	id bigint not null auto_increment,
    quantidade bigint not null,
    preco_unitario decimal(38,2) not null,
    preco_total decimal(38,2) not null,
    observacao varchar(60),
    produto_id bigint not null,
    pedido_id bigint not null,
    
	primary key(id)
) engine=InnoDB default charset=utf8mb4;

ALTER TABLE pedido ADD CONSTRAINT FK_pedido_restaurante
FOREIGN KEY (restaurante_id) REFERENCES restaurante (id);

ALTER TABLE pedido ADD CONSTRAINT FK_pedido_usuario
FOREIGN KEY (cliente_id) REFERENCES usuario (id);

ALTER TABLE pedido ADD CONSTRAINT FK_pedido_cidade
FOREIGN KEY (endereco_cidade_id) REFERENCES cidade (id);

ALTER TABLE pedido ADD CONSTRAINT FK_pedido_forma_pagamento
FOREIGN KEY (forma_pagamento) REFERENCES forma_pagamento (id);

ALTER TABLE item_pedido ADD CONSTRAINT FK_item_pedido_produto
FOREIGN KEY (produto_id) REFERENCES produto (id);

ALTER TABLE item_pedido ADD CONSTRAINT FK_item_pedido
FOREIGN KEY (pedido_id) REFERENCES pedido (id);
