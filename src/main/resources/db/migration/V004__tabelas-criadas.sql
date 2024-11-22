
create table forma_pagamento (id bigint not null auto_increment, 
descricao varchar(60), 
primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table grupo (id bigint not null auto_increment, 
nome varchar(60) not null, 
primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table permissao (
id bigint not null auto_increment, 
descricao varchar(60) not null,
nome varchar(60) not null, 

primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table grupo_permissao (
grupo_id bigint not null, 
permissao_id bigint not null
) engine=InnoDB default charset=utf8mb4;

create table produto (
ativo bit not null, 
preco decimal(38,2) not null, 
id bigint not null auto_increment,
restaurante_id bigint, 
descricao varchar(255), 
nome varchar(255) not null,
 
primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table restaurante (
taxa_frete decimal(38,2), 
cozinha_id bigint not null, 
data_atualizacao datetime(1) not null, 
data_cadastro datetime(1) not null, 
endereco_cidade_id bigint, 
id bigint not null auto_increment, 
endereco_bairro varchar(60), 
endereco_cep varchar(60), 
endereco_complemento varchar(60), 
endereco_logradouro varchar(60), 
endereco_numero varchar(60), 
nome varchar(60), 

primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table restaurante_pagamento (
forma_pagamento_id bigint not null, 
restaurante_id bigint not null
) engine=InnoDB default charset=utf8mb4;

create table usuario (
data_cadastro datetime(1), 
id bigint not null auto_increment, 
email varchar(60) not null, 
nome varchar(60) not null, 
senha varchar(60) not null, 

primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table usuario_grupo (
grupo_id bigint not null, 
usuario_id bigint not null
) engine=InnoDB default charset=utf8mb4;

alter table grupo_permissao add constraint FK_grupo_permissao foreign key (permissao_id) references permissao (id);
alter table grupo_permissao add constraint FK_permissao_grupo foreign key (grupo_id) references grupo (id);
alter table produto add constraint FK_produto_restaurante foreign key (restaurante_id) references restaurante (id);
alter table restaurante add constraint FK_restaurante_cozinha foreign key (cozinha_id) references cozinha (id);
alter table restaurante add constraint FK_restaurante_cidade foreign key (endereco_cidade_id) references cidade (id);
alter table restaurante_pagamento add constraint FK_restaurante_pagamento foreign key (forma_pagamento_id) references forma_pagamento (id);
alter table restaurante_pagamento add constraint FK_pagamento_restaurante foreign key (restaurante_id) references restaurante (id);
alter table usuario_grupo add constraint FK_usuario_grupo foreign key (grupo_id) references grupo (id);
alter table usuario_grupo add constraint FK_grupo_usuario foreign key (usuario_id) references usuario (id);

