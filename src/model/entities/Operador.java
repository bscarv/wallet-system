package model.entities;

import java.util.Collection;
import java.util.List;

public class Operador {

	private static Operador instanciaUnica;

	private Integer id;
	private String nome;
	private String sobrenome;
	private String _CPF;
	private String senha;

	private Carteira carteira;

	public Operador() {
	}

	public Operador(String _CPF, String senha) {
		this._CPF = _CPF;
		this.senha = senha;
	}

	private Operador(String nome, String sobrenome, String _CPF, String senha) {
		this.nome = nome;
		this.sobrenome = sobrenome;
		this._CPF = _CPF;
		this.senha = senha;
	}

	public static Operador obterSingletonOperador() {
		if (instanciaUnica == null) {
			instanciaUnica = new Operador();
		}
		return instanciaUnica;
	}

	public static Operador obterSingletonOperador(String _CPF, String senha) {
		if (instanciaUnica == null) {
			instanciaUnica = new Operador(_CPF, senha);
		}
		return instanciaUnica;
	}
	
	public static Operador obterSingletonOperador(String nome, String sobrenome, String _CPF, String senha) {
		if (instanciaUnica == null) {
			instanciaUnica = new Operador(nome, sobrenome, _CPF, senha);
		}
		return instanciaUnica;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
		carteira = new Carteira(id);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String get_CPF() {
		return _CPF;
	}

	public void set_CPF(String _CPF) {
		this._CPF = _CPF;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Carteira getCarteira() {
		return carteira;
	}

	@Override
	public String toString() {
		return "Operador [Id: " + id + ", Nome: " + nome + " " + sobrenome + ", CPF: " + _CPF + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_CPF == null) ? 0 : _CPF.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operador other = (Operador) obj;
		if (_CPF == null) {
			if (other._CPF != null)
				return false;
		} else if (!_CPF.equals(other._CPF))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		return true;
	}

	public void realizarOperacoes(List<Operacao> operacoes) {		
		for (Operacao o : operacoes) {
			//System.out.println(o);
			//Registra as operações no BD
			if (o.persistirOperacao(id)) {
				//Transforma as operações em posições na carteira em memória e no BD
				carteira.atualizarCarteira(o);	
			}					
		}
	}
	
	public void imprimirCarteira() {
		Collection<Posicao> list = (Collection<Posicao>)carteira.getPosicoes().values();
		for(Posicao p : list) {
			System.out.println(p.toString());
		}
	}

}
