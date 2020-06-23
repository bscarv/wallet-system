package model.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

import model.dao.FactoryDao;
import model.dao.impl.OperacaoDaoJDBC;
import model.entities.enums.TipoOperacao;

public class Operacao {

	private Integer id;
	private Ativo ativo;
	private Integer cotas;
	private Double valor;
	private Date data;
	private TipoOperacao tipo;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public Operacao(Date data, Ativo ativo, Integer cotas, Double valor, TipoOperacao tipo) {
		this.ativo = ativo;
		this.cotas = cotas;
		this.valor = valor;
		this.data = data;
		this.tipo = tipo;
	}

	public Ativo getAtivo() {
		return ativo;
	}

	public void setAtivo(Ativo ativo) {
		this.ativo = ativo;
	}

	public Integer getCotas() {
		return cotas;
	}

	public void setCotas(Integer cotas) {
		this.cotas = cotas;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public TipoOperacao getTipo() {
		return tipo;
	}

	public void setTipo(TipoOperacao tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {		
		return "Operacao [Id: " + id + ", Data: " + sdf.format(data) + ", Ativo: " + ativo + ", Cotas: " + cotas + ", Valor: " + String.format("R$%.2f",valor) 
				+ ", Tipo: " + tipo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ativo == null) ? 0 : ativo.hashCode());
		result = prime * result + ((cotas == null) ? 0 : cotas.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		Operacao other = (Operacao) obj;
		if (ativo == null) {
			if (other.ativo != null)
				return false;
		} else if (!ativo.equals(other.ativo))
			return false;
		if (cotas == null) {
			if (other.cotas != null)
				return false;
		} else if (!cotas.equals(other.cotas))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (tipo != other.tipo)
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

	public boolean persistirOperacao(Integer idOperador) {
		OperacaoDaoJDBC obj = (OperacaoDaoJDBC) FactoryDao.criarOperacaoDao();
		return obj.inserirOperacao(this, idOperador);
	}	
}
