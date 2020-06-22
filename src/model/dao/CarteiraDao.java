package model.dao;

import java.util.List;

import model.entities.Posicao;

public interface CarteiraDao {

	List<Posicao> obterPosicoesCarteira(Integer id);
}

