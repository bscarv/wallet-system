package model.dao;

import model.entities.Operacao;

public interface OperacaoDao {

	boolean inserirOperacao(Operacao o, Integer idOperador);
}
