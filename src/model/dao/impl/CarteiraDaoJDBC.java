package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.CarteiraDao;
import model.entities.Ativo;
import model.entities.Posicao;

public class CarteiraDaoJDBC implements CarteiraDao {

	private Connection conn;

	public CarteiraDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Posicao> obterPosicoesCarteira(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
				"SELECT tbl_posicoes.*,tbl_ativos.Codigo "
				+ "FROM tbl_posicoes INNER JOIN tbl_ativos "
				+ "ON tbl_posicoes.IdAtivo = tbl_ativos.IdAtivo "
				+ "WHERE tbl_posicoes.IdOperador = ?");	
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			List<Posicao> list = new ArrayList<>();
			Map<Integer, Ativo> map = new HashMap<>();
			
			while(rs.next()) {
				
				Ativo ativo = map.get(rs.getInt("IdAtivo"));
				if (ativo == null) {
					ativo = instanciarAtivo(rs);
					map.put(rs.getInt("IdAtivo"), ativo);
				}
				
				Posicao posicao = instanciarPosicao(rs, ativo);				
				list.add(posicao);
			}
			return list;		
		}
		catch (SQLException e){
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}
	
	private Ativo instanciarAtivo(ResultSet rs) throws SQLException {
		Ativo ativo = new Ativo(rs.getInt("IdAtivo"), rs.getString("Codigo"));		
		return ativo;
	}
	
	private Posicao instanciarPosicao(ResultSet rs, Ativo ativo) throws SQLException {
		Posicao posicao = new Posicao(rs.getInt("IdPosicao"), ativo, rs.getInt("Cotas"), rs.getDouble("ValorMedio"));
		return posicao;
	}
}
