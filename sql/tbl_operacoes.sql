SELECT * FROM `wallet-system-db`.tbl_operacoes;

INSERT INTO tbl_operacoes (Tipo, Cotas, Valor, DataOp, IdAtivo, IdOperador) 
VALUES ('COMPRA', 10, 130.0, '2020/06/23', 6, 1);

ALTER TABLE tbl_operacoes MODIFY COLUMN Tipo ENUM('COMPRA', 'VENDA') NOT NULL;
ALTER TABLE tbl_operacoes AUTO_INCREMENT=1;