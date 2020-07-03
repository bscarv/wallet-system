package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;
import model.dao.AcessoDao;
import model.dao.ComumDao;
import model.entities.Operador;

public class AcessoDaoJDBC extends ComumDao implements AcessoDao {

	public AcessoDaoJDBC(Connection conn) {
		super(conn);
	}

	@Override
	public boolean cadastrar(Operador op) {
		boolean ret = false;
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO tbl_operadores " 
					+ "(Nome, Sobrenome, CPF, senha) VALUES (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, op.getNome());
			st.setString(2, op.getSobrenome());
			st.setString(3, op.get_CPF());
			st.setString(4, op.getSenha());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
//				ResultSet rs = st.getGeneratedKeys();
//				if (rs.next()) {
//					int id = rs.getInt(1);
//					op.setId(id);
//				}
//				DB.closeResultSet(rs);
				ret = true;
			} else {
				throw new DbException("Erro inesperado! Nenhuma linha foi incluida na base de dados.");				
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
		return ret;
	}

	@Override
	public boolean entrar(Operador op) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT tbl_operadores.* " 
					+ "FROM tbl_operadores WHERE tbl_operadores.CPF = ? "
					+ "AND tbl_operadores.Senha = ?");
			st.setString(1, op.get_CPF());
			st.setString(2, op.getSenha());
			rs = st.executeQuery();
			if (rs.next()) {
				op = instanciarOperador(rs);
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}
	
	@Override
	public boolean verificarOperadorDisponivel(Operador op) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT tbl_operadores.* " 
					+ "FROM tbl_operadores WHERE tbl_operadores.CPF = ?");
			st.setString(1, op.get_CPF());
			rs = st.executeQuery();
			if (rs.next()) {				
				return false;
			}
			return true;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	private Operador instanciarOperador(ResultSet rs) throws SQLException {
		Operador op = Operador.obterSingletonOperador();
		op.setId(rs.getInt("IdOperador"));
		op.setNome(rs.getString("Nome"));
		op.setSobrenome(rs.getString("Sobrenome"));
		return op;
	}
}
