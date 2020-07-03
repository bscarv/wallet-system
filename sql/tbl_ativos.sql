SELECT * FROM `wallet-system-db`.tbl_ativos;

SELECT tbl_ativos.* WHERE tbl_ativos.Codigo = 'PETR4';

ALTER TABLE tbl_ativos AUTO_INCREMENT=1;
ALTER TABLE tbl_ativos ADD COLUMN Tipo 
ENUM('FII', 'ACAO', 'STOCKS', 'CRYPTO', 'CDB', 'TESOURO') NOT NULL;