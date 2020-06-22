package model.dao;

import db.DB;
import model.dao.impl.AcessoDaoJDBC;
import model.dao.impl.CarteiraDaoJDBC;

public class DaoFactory {
	
	public static AcessoDao criarAcessoDao() {
		return new AcessoDaoJDBC(DB.getConnection());
	}
	
	public static CarteiraDao criarCarteiraDao() {
		return new CarteiraDaoJDBC(DB.getConnection());
	}

}
