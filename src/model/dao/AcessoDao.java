package model.dao;

import model.entities.Operador;

public interface AcessoDao {

	void cadastrar(Operador op);
	boolean entrar(Operador op);
	
	/*
	 * void insert(Department obj); void update(Department obj); void
	 * deleteById(Integer id); Department findById(Integer id);
	 * List<Department>findAll();
	 */	
}

