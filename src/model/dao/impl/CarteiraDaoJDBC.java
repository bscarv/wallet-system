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
import model.dao.ComumDao;
import model.entities.Ativo;
import model.entities.Posicao;
import model.entities.enums.TipoAtivo;

public class CarteiraDaoJDBC extends ComumDao implements CarteiraDao {

	public CarteiraDaoJDBC(Connection conn) {
		super(conn);
	}

	@Override
	public Map<String, Posicao> obterPosicoesDaCarteira(Integer idOperador) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
				"SELECT tbl_posicoes.*,tbl_ativos.Codigo,tbl_ativos.Tipo "
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
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			//Método da superclasse ComumDao que verifica se já existe ativo no DB
			//e não existindo cria. Em ambos os casos, retorna o id do ativo.
			idAtivo = persistirAtivo(p.getAtivo().getCodigo(), p.getAtivo().getTipo());
			
			st = conn.prepareStatement("INSERT INTO tbl_posicoes "
										+ "(Cotas, ValorMedio, IdAtivo, IdOperador) "
										+ "VALUES "
										+ "(?, ?, ?, ?)",
										Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, p.getCotas());
			st.setDouble(2, p.getValorMedio());
			st.setInt(3, idAtivo);
			st.setInt(4, idOperador);
			
			rowsAffected = st.executeUpdate();
						
			if(rowsAffected > 0) {
				rs = st.getGeneratedKeys();
				if(rs.next()) {
					idPosicao  = rs.getInt(1);						
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
			DB.closeResultSet(rs);
			DB.closeStatement(st);
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
		Ativo ativo = new Ativo(rs.getInt("IdAtivo"), rs.getString("Codigo"), TipoAtivo.valueOf(rs.getString("Tipo")));		
		return ativo;
	}
	
	private Posicao instanciarPosicao(ResultSet rs, Ativo ativo) throws SQLException {
		Posicao posicao = new Posicao(rs.getInt("IdPosicao"), ativo, rs.getInt("Cotas"), rs.getDouble("ValorMedio"));
		return posicao;
	}
}
