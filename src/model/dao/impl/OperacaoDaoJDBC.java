package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;
import model.dao.ComumDao;
import model.dao.OperacaoDao;
import model.entities.Operacao;

public class OperacaoDaoJDBC extends ComumDao implements OperacaoDao{

	public OperacaoDaoJDBC(Connection conn) {
		super(conn);
	}

	@Override
	public boolean inserirOperacao(Operacao o, Integer idOperador) {		
		boolean ret = false;
		Integer idAtivo = 0;
		int rowsAffected = 0;
		PreparedStatement st1 = null;
		ResultSet rs1 = null;
		PreparedStatement st2 = null;
				
		//System.out.println(o.toString());
		
		//Método da superclasse ComumDao que verifica se já existe ativo no DB
		//e não existindo cria. Em ambos os casos, retorna o id do ativo.
		idAtivo = persistirAtivo(o.getAtivo().getCodigo(), o.getAtivo().getTipo());	

		try {
			st1 = conn.prepareStatement("SELECT tbl_operacoes.* " 
										+ "FROM tbl_operacoes " 
										+ "WHERE tbl_operacoes.Tipo = ? "
										+ "AND tbl_operacoes.Cotas = ? "
										+ "AND tbl_operacoes.Valor = ? "
										+ "AND tbl_operacoes.DataOp = ? "
										+ "AND tbl_operacoes.IdAtivo = ? "
										+ "AND tbl_operacoes.IdOperador = ?");
			st1.setString(1, o.getTipo().toString());
			st1.setInt(2, o.getCotas());
			st1.setDouble(3, o.getValor());
			st1.setDate(4, new java.sql.Date(o.getData().getTime()));			
			st1.setInt(5, idAtivo);
			st1.setInt(6, idOperador);
			
			rs1 = st1.executeQuery();
			
			if (rs1.next() == false) {	
				st2 = conn.prepareStatement("INSERT INTO tbl_operacoes "
											+ "(Tipo, Cotas, Valor, DataOp, IdAtivo, IdOperador) "
											+ "VALUES (?, ?, ?, ?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS);
				st2.setString(1, o.getTipo().toString());
				st2.setInt(2, o.getCotas());
				st2.setDouble(3, o.getValor());
				st2.setDate(4, new java.sql.Date(o.getData().getTime()));			
				st2.setInt(5, idAtivo);
				st2.setInt(6, idOperador);
				
				rowsAffected = st2.executeUpdate();
				
				if (rowsAffected > 0) {
					ret = true;
				} else {
					throw new DbException("Erro inesperado! Nenhuma linha foi incluida em tbl_operacoes.");
				}
				
			} else {
				ret = false;
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {			
			DB.closeResultSet(rs1);
			DB.closeStatement(st1);
			DB.closeStatement(st2);			
		}
		
		return ret;
	}	
}
