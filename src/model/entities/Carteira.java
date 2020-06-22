package model.entities;

import java.util.Map;

import model.dao.DaoFactory;
import model.dao.impl.CarteiraDaoJDBC;

public class Carteira {

	private Map<String, Posicao> posicoes;
	private Integer idOperador;

	public Carteira(Integer id) {
		idOperador = id;
		carregarCarteira();
	}	
	
	public Map<String, Posicao> getPosicoes() {
		return posicoes;
	}

	@Override
	public String toString() {
		//TODO: CONCATENAR UM STRING QUE IMPRIME TODAS AS POSICOES		
		return "Carteira [posicoes=" + posicoes + "]";
	}

	private void carregarCarteira() {
		CarteiraDaoJDBC obj = (CarteiraDaoJDBC) DaoFactory.criarCarteiraDao();
		posicoes = obj.obterPosicoesDaCarteira(idOperador);
//		for (Posicao p : posicoes.values()) {
//			System.out.println(p);
//		}
	}
	
	public void atualizarCarteira(Operacao o) {
		String key = o.getAtivo().getCodigo();
		Posicao p;		
		if (posicoes.containsKey(key)) {
			p = operacaoToPosicao(o,posicoes.get(o.getAtivo().getCodigo()));			
		}else {
			p = operacaoToPosicao(o);			
		}
		Integer id = persistirCarteira(p);
		p.setId(id);
		posicoes.put(key, p);		
	}
	
	private Posicao operacaoToPosicao(Operacao o) {
		return new Posicao(o.getAtivo(), o.getCotas(), o.getValor());
	}
	
	private Posicao operacaoToPosicao(Operacao o, Posicao p) {
		int cotaO = o.getCotas();
		int cotaP = p.getCotas();
		int cotaTotal = 0;
		double valorO = o.getValor();
		double valorP = p.getValorMedio();
		double valorMedio = p.getValorMedio();
		
		switch (o.getTipo()) {
		case COMPRA:
			cotaTotal = cotaO + cotaP;
			valorMedio = (valorP*cotaP+valorO*cotaO)/cotaTotal;
			break;
		case VENDA:			
			cotaTotal = cotaP - cotaO;
			break;
		}
		
		p.setCotas(cotaTotal);
		p.setValorMedio(valorMedio);
		
		return p;
	}
	
	private Integer persistirCarteira(Posicao p) {		
		CarteiraDaoJDBC obj = (CarteiraDaoJDBC) DaoFactory.criarCarteiraDao();
		Integer id;
		if (p.getId() == null) {
			id = obj.inserirPosicaoNaCarteira(p, idOperador);
		}
		else {
			id = obj.atualizarPosicaoNaCarteira(p);
		}		
		return id;
	}
}
