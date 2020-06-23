package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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
	public Map<String, Posicao> obterPosicoesDaCarteira(Integer idOperador) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
				"SELECT tbl_posicoes.*,tbl_ativos.Codigo "
				+ "FROM tbl_posicoes INNER JOIN tbl_ativos "
				+ "ON tbl_posicoes.IdAtivo = tbl_ativos.IdAtivo "
				+ "WHERE tbl_posicoes.IdOperador = ? "
				+ "ORDER BY tbl_ativos.Codigo");	
			st.setInt(1, idOperador);
			
			rs = st.executeQuery();
			
			Map<String, Posicao> posicoes = new TreeMap<>();
			Map<Integer, Ativo> map = new HashMap<>();
			
			while(rs.next()) {
				
				Ativo ativo = map.get(rs.getInt("IdAtivo"));
				if (ativo == null) {
					ativo = instanciarAtivo(rs);
					map.put(rs.getInt("IdAtivo"), ativo);
				}
				//System.out.println(ativo.getCodigo());
				Posicao posicao = instanciarPosicao(rs, ativo);
				posicoes.put(ativo.getCodigo(), posicao);				
			}	
			return posicoes;	
		}
		catch (SQLException e){
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}
	
	@Override
	public Integer inserirPosicaoNaCarteira(Posicao p, Integer idOperador) {
		Integer idAtivo = 0;	
		Integer idPosicao = 0;
		int rowsAffected = 0;
		PreparedStatement st1 = null;
		ResultSet rs1 = null;
		PreparedStatement st2 = null;
		ResultSet rs2 = null;
		PreparedStatement st3 = null;
		ResultSet rs3 = null;
		try {
			st1 = conn.prepareStatement("SELECT tbl_ativos.* "
										+"FROM tbl_ativos "
										+"WHERE tbl_ativos.Codigo = ?");			
			st1.setString(1, p.getAtivo().getCodigo());
			rs1 = st1.executeQuery();
						
			if(rs1.next()) {
				idAtivo = rs1.getInt("IdAtivo");				
			}			
			else
			{
				st2 = conn.prepareStatement("INSERT INTO tbl_ativos (Codigo) VALUES (?)",
											Statement.RETURN_GENERATED_KEYS);
				st2.setString(1, p.getAtivo().getCodigo());
				rowsAffected = st2.executeUpdate();
				
				if(rowsAffected > 0) {
					rs2 = st2.getGeneratedKeys();
					if(rs2.next()) {
						idAtivo  = rs2.getInt(1);							
					}
				}
				else {
					throw new DbException("Erro inesperado! Nenhuma linha foi incluida em tbl_ativos.");
				}				
			}
			st3 = conn.prepareStatement("INSERT INTO tbl_posicoes "
										+ "(Cotas, ValorMedio, IdAtivo, IdOperador) "
										+ "VALUES "
										+ "(?, ?, ?, ?)",
										Statement.RETURN_GENERATED_KEYS);
			st3.setInt(1, p.getCotas());
			st3.setDouble(2, p.getValorMedio());
			st3.setInt(3, idAtivo);
			st3.setInt(4, idOperador);
			
			rowsAffected = st3.executeUpdate();
			
			if(rowsAffected > 0) {
				rs3 = st3.getGeneratedKeys();
				if(rs3.next()) {
					idPosicao  = rs3.getInt(1);						
				}
				else {
					throw new DbException("Erro inesperado! Nenhuma linha foi incluida em tbl_posicoes.");
				}
			}				
		}
		catch (SQLException e){
			throw new DbException(e.getMessage());
		}
		finally {			
			DB.closeResultSet(rs1);
			DB.closeStatement(st1);
			DB.closeResultSet(rs2);
			DB.closeStatement(st2);
			DB.closeResultSet(rs3);
			DB.closeStatement(st3);
		}		
		return idPosicao;
	}

	@Override
	public Integer atualizarPosicaoNaCarteira(Posicao p) {
		PreparedStatement st = null;
		try {
			if(p.getCotas() <= 0) {
				st = conn.prepareStatement("DELETE FROM tbl_posicoes "
											+ "WHERE IdPosicao = ?",
											Statement.RETURN_GENERATED_KEYS);
				st.setInt(1, p.getId());
				
				st.executeUpdate();
			}
			else {
				st = conn.prepareStatement("UPDATE tbl_posicoes SET Cotas = ?, ValorMedio = ? "
											+ "WHERE IdPosicao = ?",
											Statement.RETURN_GENERATED_KEYS);
				//System.out.println(p);
				st.setInt(1, p.getCotas());
				st.setDouble(2, p.getValorMedio());
				st.setInt(3, p.getId());
				
				st.executeUpdate();
			}				
			
		} catch (SQLException e) {
			throw new DbException("Erro inesperado!. Não foi possível atualizar a tabela tbl_posicoes");
		} finally {
			DB.closeStatement(st);
		}
		
		return p.getId();
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
