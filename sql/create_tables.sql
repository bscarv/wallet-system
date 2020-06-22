# Criar tabela de operadores
CREATE TABLE IF NOT EXISTS tbl_Operadores (
IdOperador TINYINT AUTO_INCREMENT,
Nome VARCHAR(25) NOT NULL,
Sobrenome VARCHAR(75) NOT NULL,
CPF VARCHAR(11) NOT NULL,
Senha VARCHAR(12) NOT NULL,
CONSTRAINT pk_id_operador PRIMARY KEY (IdOperador)
);

# Criar tabela de ativos
CREATE TABLE IF NOT EXISTS tbl_Ativos (
IdAtivo SMALLINT AUTO_INCREMENT,
Nome VARCHAR(25) NOT NULL,
CONSTRAINT pk_id_ativo PRIMARY KEY (IdAtivo)
);

# Criar tabela de operações
CREATE TABLE IF NOT EXISTS tbl_Operacoes (
IdOperacao SMALLINT NOT NULL AUTO_INCREMENT,
Tipo ENUM('COMPRA', 'VENDA') NOT NULL,
Cotas MEDIUMINT NOT NULL,
Valor DECIMAL(10,2) NOT NULL,
DataOp DATE NOT NULL,
IdAtivo SMALLINT NOT NULL,
IdOperador TINYINT NOT NULL,
CONSTRAINT pk_id_operacao PRIMARY KEY (IdOperacao),
CONSTRAINT fk_id_ativo_op FOREIGN KEY (IdAtivo) REFERENCES tbl_Ativos
(IdAtivo) ON DELETE CASCADE,
CONSTRAINT fk_id_operador_op FOREIGN KEY (IdOperador) REFERENCES tbl_Operadores
(IdOperador) ON DELETE CASCADE
);

#Adiciona chave estrangeira em tbl_Operacoes
#ALTER TABLE tbl_Operacoes
#ADD CONSTRAINT fk_id_ativo FOREIGN KEY (IdAtivo) 
#REFERENCES tbl_Ativos (IdAtivo) ON DELETE CASCADE;
#ALTER TABLE tbl_Operacoes
#ADD CONSTRAINT fk_id_operador FOREIGN KEY (IdOperador) 
#REFERENCES tbl_Operadores (IdOperador) ON DELETE CASCADE;

# Criar tabela de posições
CREATE TABLE IF NOT EXISTS tbl_Posicoes (
IdPosicao TINYINT NOT NULL AUTO_INCREMENT,
Cotas MEDIUMINT NOT NULL,
ValorMedio DECIMAL(10,2) NOT NULL,
DataUltOp DATE NOT NULL,
IdAtivo SMALLINT NOT NULL,
IdOperador TINYINT NOT NULL,
CONSTRAINT pk_id_posicao PRIMARY KEY (IdPosicao),
CONSTRAINT fk_id_ativo_pos FOREIGN KEY (IdAtivo) REFERENCES tbl_Ativos
(IdAtivo) ON DELETE CASCADE,
CONSTRAINT fk_id_operador_pos FOREIGN KEY (IdOperador) REFERENCES tbl_Operadores
(IdOperador) ON DELETE CASCADE
);


