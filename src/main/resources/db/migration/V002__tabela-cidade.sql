create table cidade (
	id bigint auto_increment,
    nome_cidade varchar(60) not null,
    nome_estado varchar(60) not null,
    
    primary key(id)
) engine=InnoDB default charset=utf8mb4