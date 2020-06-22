package model.dao;

import java.util.Map;

import model.entities.Posicao;

public interface CarteiraDao {

	Map<String, Posicao> obterPosicoesDaCarteira(Integer idOperador);
	Integer inserirPosicaoNaCarteira(Posicao p, Integer idOperador);
	Integer atualizarPosicaoNaCarteira(Posicao p);
}

