ALTER TABLE restaurante
ADD aberto tinyint default 0;

UPDATE restaurante
SET aberto = false;

ALTER TABLE restaurante
MODIFY COLUMN aberto tinyint not null default 0;