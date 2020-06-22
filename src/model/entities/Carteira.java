package model.entities;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.impl.CarteiraDaoJDBC;

public class Carteira {

	private List<Posicao> posicoes;

	public Carteira(Integer id) {
		carregarCarteira(id);
	}	
	
	public List<Posicao> getPosicoes() {
		return posicoes;
	}

	private void carregarCarteira(Integer id) {
		CarteiraDaoJDBC obj = (CarteiraDaoJDBC) DaoFactory.criarCarteiraDao();
		posicoes = obj.obterPosicoesCarteira(id);
		for (Posicao p : posicoes) {
			System.out.println(p);
		}
	}	
}
