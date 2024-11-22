create table cidade (estado_id bigint, id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB;
create table cozinha (id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB;
create table estado (id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB;
create table forma_pagamento (id bigint not null auto_increment, descricao varchar(255), primary key (id)) engine=InnoDB;
create table grupo (id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB;
create table grupo_permissao (grupo_id bigint not null, permissao_id bigint not null) engine=InnoDB;
create table permissao (id bigint not null auto_increment, descricao varchar(255), nome varchar(255) not null, primary key (id)) engine=InnoDB;
create table produto (ativo bit not null, preco decimal(38,2) not null, id bigint not null auto_increment, restaurante_id bigint, descricao varchar(255), nome varchar(255) not null, primary key (id)) engine=InnoDB;
create table restaurante (taxa_frete decimal(38,2), cozinha_id bigint not null, data_atualizacao datetime(6) not null, data_cadastro datetime(6) not null, endereco_cidade_id bigint, id bigint not null auto_increment, endereco_bairro varchar(255), endereco_cep varchar(255), endereco_complemento varchar(255), endereco_logradouro varchar(255), endereco_numero varchar(255), nome varchar(255), primary key (id)) engine=InnoDB;
create table restaurante_pagamento (forma_pagamento_id bigint not null, restaurante_id bigint not null) engine=InnoDB;
create table usuario (data_cadastro datetime(6), id bigint not null auto_increment, email varchar(255) not null, nome varchar(255) not null, senha varchar(255) not null, primary key (id)) engine=InnoDB;
create table usuario_grupo (grupo_id bigint not null, usuario_id bigint not null) engine=InnoDB;
alter table cidade add constraint FKkworrwk40xj58kevvh3evi500 foreign key (estado_id) references estado (id);
alter table grupo_permissao add constraint FKh21kiw0y0hxg6birmdf2ef6vy foreign key (permissao_id) references permissao (id);
alter table grupo_permissao add constraint FKta4si8vh3f4jo3bsslvkscc2m foreign key (grupo_id) references grupo (id);
alter table produto add constraint FKb9jhjyghjcn25guim7q4pt8qx foreign key (restaurante_id) references restaurante (id);
alter table restaurante add constraint FK76grk4roudh659skcgbnanthi foreign key (cozinha_id) references cozinha (id);
alter table restaurante add constraint FKbc0tm7hnvc96d8e7e2ulb05yw foreign key (endereco_cidade_id) references cidade (id);
alter table restaurante_pagamento add constraint FKr8nrw4b5eabwpo0f8xo1qmjsn foreign key (forma_pagamento_id) references forma_pagamento (id);
alter table restaurante_pagamento add constraint FKaeaj9nvv4tmporurm9fwdt9b5 foreign key (restaurante_id) references restaurante (id);
alter table usuario_grupo add constraint FKk30suuy31cq5u36m9am4om9ju foreign key (grupo_id) references grupo (id);
alter table usuario_grupo add constraint FKdofo9es0esuiahyw2q467crxw foreign key (usuario_id) references usuario (id);
INSERT INTO cozinha (id, nome) values (1, 'Tailandesa');
INSERT INTO cozinha (id, nome) VALUES (2, 'Indiana');
INSERT INTO estado (nome) VALUES ("Rio de Janeiro");
INSERT INTO estado (nome) VALUES ("São Paulo");
INSERT INTO estado (nome) VALUES ("Alagoas");
INSERT INTO cidade (nome, estado_id) VALUES ("Niterói", 1);
INSERT INTO cidade (nome, estado_id) VALUES ("Rio claro", 2);
INSERT INTO cidade (nome, estado_id) VALUES ("Palmeira dos índios", 3);
INSERT INTO restaurante (nome, taxa_frete, cozinha_id, data_atualizacao, data_cadastro, endereco_cidade_id, endereco_bairro, endereco_cep, endereco_complemento, endereco_logradouro, endereco_numero) VALUES ('IndianBurguer', 3.0, 2, NOW(), NOW(), 1, "Fonseca", "24120-240", "Rua Dr.Carlos Imbassahy N° 48 ap 1105 bl 1", "Condomínio Parque Bela Vista", "48");
INSERT INTO restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) VALUES ('TaiwanFood', 2.0, 1, NOW(), NOW());
INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) VALUES ("Chesse Burguer", "O clássico Chesse Burguer", 5500.00, true, 1);
INSERT INTO forma_pagamento(id, descricao) VALUES (1, "Cartão de crédito");
INSERT INTO forma_pagamento(id, descricao) VALUES (2, "Cartão débito");
INSERT INTO restaurante_pagamento (restaurante_id, forma_pagamento_id) VALUES (1, 2);
INSERT INTO restaurante_pagamento (restaurante_id, forma_pagamento_id) VALUES (1, 1);
INSERT INTO permissao (nome, descricao) VALUES ("Admininstrador", "Tem autorização sobre todas as operações");
INSERT INTO restaurante_pagamento (restaurante_id, forma_pagamento_id) VALUES (2, 1);
INSERT INTO permissao (nome, descricao) VALUES ("Cliente", "Autorizações básicas do sistema");
create table cidade (estado_id bigint, id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB;
create table cozinha (id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB;
create table estado (id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB;
create table forma_pagamento (id bigint not null auto_increment, descricao varchar(255), primary key (id)) engine=InnoDB;
create table grupo (id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB;
create table grupo_permissao (grupo_id bigint not null, permissao_id bigint not null) engine=InnoDB;
create table permissao (id bigint not null auto_increment, descricao varchar(255), nome varchar(255) not null, primary key (id)) engine=InnoDB;
create table produto (ativo bit not null, preco decimal(38,2) not null, id bigint not null auto_increment, restaurante_id bigint, descricao varchar(255), nome varchar(255) not null, primary key (id)) engine=InnoDB;
create table restaurante (taxa_frete decimal(38,2), cozinha_id bigint not null, data_atualizacao datetime(6) not null, data_cadastro datetime(6) not null, endereco_cidade_id bigint, id bigint not null auto_increment, endereco_bairro varchar(255), endereco_cep varchar(255), endereco_complemento varchar(255), endereco_logradouro varchar(255), endereco_numero varchar(255), nome varchar(255), primary key (id)) engine=InnoDB;
create table restaurante_pagamento (forma_pagamento_id bigint not null, restaurante_id bigint not null) engine=InnoDB;
create table usuario (data_cadastro datetime(6), id bigint not null auto_increment, email varchar(255) not null, nome varchar(255) not null, senha varchar(255) not null, primary key (id)) engine=InnoDB;
create table usuario_grupo (grupo_id bigint not null, usuario_id bigint not null) engine=InnoDB;
alter table cidade add constraint FKkworrwk40xj58kevvh3evi500 foreign key (estado_id) references estado (id);
alter table grupo_permissao add constraint FKh21kiw0y0hxg6birmdf2ef6vy foreign key (permissao_id) references permissao (id);
alter table grupo_permissao add constraint FKta4si8vh3f4jo3bsslvkscc2m foreign key (grupo_id) references grupo (id);
alter table produto add constraint FKb9jhjyghjcn25guim7q4pt8qx foreign key (restaurante_id) references restaurante (id);
alter table restaurante add constraint FK76grk4roudh659skcgbnanthi foreign key (cozinha_id) references cozinha (id);
alter table restaurante add constraint FKbc0tm7hnvc96d8e7e2ulb05yw foreign key (endereco_cidade_id) references cidade (id);
alter table restaurante_pagamento add constraint FKr8nrw4b5eabwpo0f8xo1qmjsn foreign key (forma_pagamento_id) references forma_pagamento (id);
alter table restaurante_pagamento add constraint FKaeaj9nvv4tmporurm9fwdt9b5 foreign key (restaurante_id) references restaurante (id);
alter table usuario_grupo add constraint FKk30suuy31cq5u36m9am4om9ju foreign key (grupo_id) references grupo (id);
alter table usuario_grupo add constraint FKdofo9es0esuiahyw2q467crxw foreign key (usuario_id) references usuario (id);
INSERT INTO cozinha (id, nome) values (1, 'Tailandesa');
INSERT INTO cozinha (id, nome) VALUES (2, 'Indiana');
INSERT INTO estado (nome) VALUES ("Rio de Janeiro");
INSERT INTO estado (nome) VALUES ("São Paulo");
INSERT INTO estado (nome) VALUES ("Alagoas");
INSERT INTO cidade (nome, estado_id) VALUES ("Niterói", 1);
INSERT INTO cidade (nome, estado_id) VALUES ("Rio claro", 2);
INSERT INTO cidade (nome, estado_id) VALUES ("Palmeira dos índios", 3);
INSERT INTO restaurante (nome, taxa_frete, cozinha_id, data_atualizacao, data_cadastro, endereco_cidade_id, endereco_bairro, endereco_cep, endereco_complemento, endereco_logradouro, endereco_numero) VALUES ('IndianBurguer', 3.0, 2, NOW(), NOW(), 1, "Fonseca", "24120-240", "Rua Dr.Carlos Imbassahy N° 48 ap 1105 bl 1", "Condomínio Parque Bela Vista", "48");
INSERT INTO restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) VALUES ('TaiwanFood', 2.0, 1, NOW(), NOW());
INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) VALUES ("Chesse Burguer", "O clássico Chesse Burguer", 5500.00, true, 1);
INSERT INTO forma_pagamento(id, descricao) VALUES (1, "Cartão de crédito");
INSERT INTO forma_pagamento(id, descricao) VALUES (2, "Cartão débito");
INSERT INTO restaurante_pagamento (restaurante_id, forma_pagamento_id) VALUES (1, 2);
INSERT INTO restaurante_pagamento (restaurante_id, forma_pagamento_id) VALUES (1, 1);
INSERT INTO permissao (nome, descricao) VALUES ("Admininstrador", "Tem autorização sobre todas as operações");
INSERT INTO restaurante_pagamento (restaurante_id, forma_pagamento_id) VALUES (2, 1);
INSERT INTO permissao (nome, descricao) VALUES ("Cliente", "Autorizações básicas do sistema");
create table cidade (estado_id bigint, id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB;
create table cozinha (id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB;
create table estado (id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB;
create table forma_pagamento (id bigint not null auto_increment, descricao varchar(255), primary key (id)) engine=InnoDB;
create table grupo (id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB;
create table grupo_permissao (grupo_id bigint not null, permissao_id bigint not null) engine=InnoDB;
create table permissao (id bigint not null auto_increment, descricao varchar(255), nome varchar(255) not null, primary key (id)) engine=InnoDB;
create table produto (ativo bit not null, preco decimal(38,2) not null, id bigint not null auto_increment, restaurante_id bigint, descricao varchar(255), nome varchar(255) not null, primary key (id)) engine=InnoDB;
create table restaurante (taxa_frete decimal(38,2), cozinha_id bigint not null, data_atualizacao datetime(6) not null, data_cadastro datetime(6) not null, endereco_cidade_id bigint, id bigint not null auto_increment, endereco_bairro varchar(255), endereco_cep varchar(255), endereco_complemento varchar(255), endereco_logradouro varchar(255), endereco_numero varchar(255), nome varchar(255), primary key (id)) engine=InnoDB;
create table restaurante_pagamento (forma_pagamento_id bigint not null, restaurante_id bigint not null) engine=InnoDB;
create table usuario (data_cadastro datetime(6), id bigint not null auto_increment, email varchar(255) not null, nome varchar(255) not null, senha varchar(255) not null, primary key (id)) engine=InnoDB;
create table usuario_grupo (grupo_id bigint not null, usuario_id bigint not null) engine=InnoDB;
alter table cidade add constraint FKkworrwk40xj58kevvh3evi500 foreign key (estado_id) references estado (id);
alter table grupo_permissao add constraint FKh21kiw0y0hxg6birmdf2ef6vy foreign key (permissao_id) references permissao (id);
alter table grupo_permissao add constraint FKta4si8vh3f4jo3bsslvkscc2m foreign key (grupo_id) references grupo (id);
alter table produto add constraint FKb9jhjyghjcn25guim7q4pt8qx foreign key (restaurante_id) references restaurante (id);
alter table restaurante add constraint FK76grk4roudh659skcgbnanthi foreign key (cozinha_id) references cozinha (id);
alter table restaurante add constraint FKbc0tm7hnvc96d8e7e2ulb05yw foreign key (endereco_cidade_id) references cidade (id);
alter table restaurante_pagamento add constraint FKr8nrw4b5eabwpo0f8xo1qmjsn foreign key (forma_pagamento_id) references forma_pagamento (id);
alter table restaurante_pagamento add constraint FKaeaj9nvv4tmporurm9fwdt9b5 foreign key (restaurante_id) references restaurante (id);
alter table usuario_grupo add constraint FKk30suuy31cq5u36m9am4om9ju foreign key (grupo_id) references grupo (id);
alter table usuario_grupo add constraint FKdofo9es0esuiahyw2q467crxw foreign key (usuario_id) references usuario (id);
INSERT INTO cozinha (id, nome) values (1, 'Tailandesa');
INSERT INTO cozinha (id, nome) VALUES (2, 'Indiana');
INSERT INTO estado (nome) VALUES ("Rio de Janeiro");
INSERT INTO estado (nome) VALUES ("São Paulo");
INSERT INTO estado (nome) VALUES ("Alagoas");
INSERT INTO cidade (nome, estado_id) VALUES ("Niterói", 1);
INSERT INTO cidade (nome, estado_id) VALUES ("Rio claro", 2);
INSERT INTO cidade (nome, estado_id) VALUES ("Palmeira dos índios", 3);
INSERT INTO restaurante (nome, taxa_frete, cozinha_id, data_atualizacao, data_cadastro, endereco_cidade_id, endereco_bairro, endereco_cep, endereco_complemento, endereco_logradouro, endereco_numero) VALUES ('IndianBurguer', 3.0, 2, NOW(), NOW(), 1, "Fonseca", "24120-240", "Rua Dr.Carlos Imbassahy N° 48 ap 1105 bl 1", "Condomínio Parque Bela Vista", "48");
INSERT INTO restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) VALUES ('TaiwanFood', 2.0, 1, NOW(), NOW());
INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) VALUES ("Chesse Burguer", "O clássico Chesse Burguer", 5500.00, true, 1);
INSERT INTO forma_pagamento(id, descricao) VALUES (1, "Cartão de crédito");
INSERT INTO forma_pagamento(id, descricao) VALUES (2, "Cartão débito");
INSERT INTO restaurante_pagamento (restaurante_id, forma_pagamento_id) VALUES (1, 2);
INSERT INTO restaurante_pagamento (restaurante_id, forma_pagamento_id) VALUES (1, 1);
INSERT INTO permissao (nome, descricao) VALUES ("Admininstrador", "Tem autorização sobre todas as operações");
INSERT INTO restaurante_pagamento (restaurante_id, forma_pagamento_id) VALUES (2, 1);
INSERT INTO permissao (nome, descricao) VALUES ("Cliente", "Autorizações básicas do sistema");
create table cidade (estado_id bigint, id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB;
create table cozinha (id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB;
create table estado (id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB;
create table forma_pagamento (id bigint not null auto_increment, descricao varchar(255), primary key (id)) engine=InnoDB;
create table grupo (id bigint not null auto_increment, nome varchar(255) not null, primary key (id)) engine=InnoDB;
create table grupo_permissao (grupo_id bigint not null, permissao_id bigint not null) engine=InnoDB;
create table permissao (id bigint not null auto_increment, descricao varchar(255), nome varchar(255) not null, primary key (id)) engine=InnoDB;
create table produto (ativo bit not null, preco decimal(38,2) not null, id bigint not null auto_increment, restaurante_id bigint, descricao varchar(255), nome varchar(255) not null, primary key (id)) engine=InnoDB;
create table restaurante (taxa_frete decimal(38,2), cozinha_id bigint not null, data_atualizacao datetime(6) not null, data_cadastro datetime(6) not null, endereco_cidade_id bigint, id bigint not null auto_increment, endereco_bairro varchar(255), endereco_cep varchar(255), endereco_complemento varchar(255), endereco_logradouro varchar(255), endereco_numero varchar(255), nome varchar(255), primary key (id)) engine=InnoDB;
create table restaurante_pagamento (forma_pagamento_id bigint not null, restaurante_id bigint not null) engine=InnoDB;
create table usuario (data_cadastro datetime(6), id bigint not null auto_increment, email varchar(255) not null, nome varchar(255) not null, senha varchar(255) not null, primary key (id)) engine=InnoDB;
create table usuario_grupo (grupo_id bigint not null, usuario_id bigint not null) engine=InnoDB;
alter table cidade add constraint FKkworrwk40xj58kevvh3evi500 foreign key (estado_id) references estado (id);
alter table grupo_permissao add constraint FKh21kiw0y0hxg6birmdf2ef6vy foreign key (permissao_id) references permissao (id);
alter table grupo_permissao add constraint FKta4si8vh3f4jo3bsslvkscc2m foreign key (grupo_id) references grupo (id);
alter table produto add constraint FKb9jhjyghjcn25guim7q4pt8qx foreign key (restaurante_id) references restaurante (id);
alter table restaurante add constraint FK76grk4roudh659skcgbnanthi foreign key (cozinha_id) references cozinha (id);
alter table restaurante add constraint FKbc0tm7hnvc96d8e7e2ulb05yw foreign key (endereco_cidade_id) references cidade (id);
alter table restaurante_pagamento add constraint FKr8nrw4b5eabwpo0f8xo1qmjsn foreign key (forma_pagamento_id) references forma_pagamento (id);
alter table restaurante_pagamento add constraint FKaeaj9nvv4tmporurm9fwdt9b5 foreign key (restaurante_id) references restaurante (id);
alter table usuario_grupo add constraint FKk30suuy31cq5u36m9am4om9ju foreign key (grupo_id) references grupo (id);
alter table usuario_grupo add constraint FKdofo9es0esuiahyw2q467crxw foreign key (usuario_id) references usuario (id);
INSERT INTO cozinha (id, nome) values (1, 'Tailandesa');
INSERT INTO cozinha (id, nome) VALUES (2, 'Indiana');
INSERT INTO estado (nome) VALUES ("Rio de Janeiro");
INSERT INTO estado (nome) VALUES ("São Paulo");
INSERT INTO estado (nome) VALUES ("Alagoas");
INSERT INTO cidade (nome, estado_id) VALUES ("Niterói", 1);
INSERT INTO cidade (nome, estado_id) VALUES ("Rio claro", 2);
INSERT INTO cidade (nome, estado_id) VALUES ("Palmeira dos índios", 3);
INSERT INTO restaurante (nome, taxa_frete, cozinha_id, data_atualizacao, data_cadastro, endereco_cidade_id, endereco_bairro, endereco_cep, endereco_complemento, endereco_logradouro, endereco_numero) VALUES ('IndianBurguer', 3.0, 2, NOW(), NOW(), 1, "Fonseca", "24120-240", "Rua Dr.Carlos Imbassahy N° 48 ap 1105 bl 1", "Condomínio Parque Bela Vista", "48");
INSERT INTO restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) VALUES ('TaiwanFood', 2.0, 1, NOW(), NOW());
INSERT INTO produto (nome, descricao, preco, ativo, restaurante_id) VALUES ("Chesse Burguer", "O clássico Chesse Burguer", 5500.00, true, 1);
INSERT INTO forma_pagamento(id, descricao) VALUES (1, "Cartão de crédito");
INSERT INTO forma_pagamento(id, descricao) VALUES (2, "Cartão débito");
INSERT INTO restaurante_pagamento (restaurante_id, forma_pagamento_id) VALUES (1, 2);
INSERT INTO restaurante_pagamento (restaurante_id, forma_pagamento_id) VALUES (1, 1);
INSERT INTO permissao (nome, descricao) VALUES ("Admininstrador", "Tem autorização sobre todas as operações");
INSERT INTO restaurante_pagamento (restaurante_id, forma_pagamento_id) VALUES (2, 1);
INSERT INTO permissao (nome, descricao) VALUES ("Cliente", "Autorizações básicas do sistema");