package model.dao;

import db.DB;
import model.dao.impl.AcessoDaoJDBC;
import model.dao.impl.CarteiraDaoJDBC;
import model.dao.impl.OperacaoDaoJDBC;

public class FactoryDao {
	
	public static AcessoDao criarAcessoDao() {
		return new AcessoDaoJDBC(DB.getConnection());
	}
	
	public static CarteiraDao criarCarteiraDao() {
		return new CarteiraDaoJDBC(DB.getConnection());
	}
	
	public static OperacaoDao criarOperacaoDao() {
		return new OperacaoDaoJDBC(DB.getConnection());
	}

}
