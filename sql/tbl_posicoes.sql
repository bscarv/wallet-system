SELECT * FROM `wallet-system-db`.tbl_posicoes;

SELECT tbl_posicoes.* FROM tbl_posicoes WHERE tbl_posicoes.Cotas = 100.00;

UPDATE tbl_posicoes SET Cotas = 200, ValorMedio = 15.00
WHERE IdPosicao = 1;

ALTER TABLE tbl_posicoes AUTO_INCREMENT=1;