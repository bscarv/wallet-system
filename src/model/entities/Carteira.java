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
			//Atualiza uma posi��o j� existente no objeto em mem�ria
			p = operacaoToPosicao(o,posicoes.get(o.getAtivo().getCodigo()));			
		}else {
			//Insere uma nova posi��o no objeto em mem�ria
			p = operacaoToPosicao(o);			
		}
		//Persiste no BD a posi��o nova/atualizada
		Integer id = persistirCarteira(p);
		//Coloca o id da posi��o do BD no objeto em mem�ria
		p.setId(id);
		//Coloca a posi��o com id na carteira em mem�ria
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
			//Se o objeto posi��o n�o possui id, significa que ele � novo
			//e precisa ser inserido no BD
			id = obj.inserirPosicaoNaCarteira(p, idOperador);
		}
		else {
			//Se o objeto posi��o j� possui id, significa que ele j� existe
			//no BD e precisa ser atualizado.
			id = obj.atualizarPosicaoNaCarteira(p);
		}		
		return id;
	}
}
