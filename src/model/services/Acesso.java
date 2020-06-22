package model.services;

import model.dao.DaoFactory;
import model.dao.impl.AcessoDaoJDBC;
import model.entities.Operador;

public class Acesso {

	public static void cadastrar(Operador op) {
		//TODO: VALIDAR FORMATO DE SENHA E CPF
		boolean senha = true;
		boolean _CPF = true;
		if (senha && _CPF) {
			AcessoDaoJDBC obj = (AcessoDaoJDBC) DaoFactory.criarAcessoDao();
			obj.cadastrar(op);
		}
		else {
			System.out.println("Senha ou CPF inválidos!");
		}		
	}

	public static boolean entrar(Operador op) {
		AcessoDaoJDBC obj = (AcessoDaoJDBC) DaoFactory.criarAcessoDao();	
		
		if (obj.entrar(op)) {			
			return true;
		}
		return false;
	}

}
