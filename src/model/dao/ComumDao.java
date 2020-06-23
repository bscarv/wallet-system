package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;

public class ComumDao {

	protected Connection conn;

	protected ComumDao(Connection conn) {
		this.conn = conn;
	}

	protected Integer persistirAtivo(String codAtivo) {
		Integer idAtivo = 0;
		int rowsAffected = 0;
		PreparedStatement st1 = null;
		ResultSet rs1 = null;
		PreparedStatement st2 = null;
		ResultSet rs2 = null;

		try {
			st1 = conn.prepareStatement("SELECT tbl_ativos.* " 
										+ "FROM tbl_ativos " 
										+ "WHERE tbl_ativos.Codigo = ?");

			st1.setString(1, codAtivo);
			rs1 = st1.executeQuery();

			if (rs1.next()) {
				idAtivo = rs1.getInt("IdAtivo");
			} else {
				st2 = conn.prepareStatement("INSERT INTO tbl_ativos (Codigo) VALUES (?)",
						Statement.RETURN_GENERATED_KEYS);
				st2.setString(1, codAtivo);
				rowsAffected = st2.executeUpdate();

				if (rowsAffected > 0) {
					rs2 = st2.getGeneratedKeys();
					if (rs2.next()) {
						idAtivo = rs2.getInt(1);
					}
				} else {
					throw new DbException("Erro inesperado! Nenhuma linha foi incluida em tbl_ativos.");
				}
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {			
			DB.closeResultSet(rs1);
			DB.closeStatement(st1);
			DB.closeResultSet(rs2);
			DB.closeStatement(st2);			
		}		
		return idAtivo;
	}
}
