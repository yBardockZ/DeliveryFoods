CREATE TABLE restaurante_usuario_responsavel (  
	restaurante_id bigint not null,     
	usuario_id bigint not null 
);

ALTER TABLE restaurante_usuario_responsavel
ADD CONSTRAINT FK_restaurante_usuario
FOREIGN KEY (restaurante_id) REFERENCES restaurante (id);

ALTER TABLE restaurante_usuario_responsavel
ADD CONSTRAINT FK_usuario_restaurante
FOREIGN KEY (usuario_id) REFERENCES usuario (id);