package model.entities;

public class Posicao {
	
	private Integer id;
	private Ativo ativo;
	private Integer cotas;
	private Double valorMedio;
	
	public Posicao(Ativo ativo, Integer cotas, Double valorMedio) {
		this.ativo = ativo;
		this.cotas = cotas;
		this.valorMedio = valorMedio;
	}
	
	public Posicao(Integer id, Ativo ativo, Integer cotas, Double valorMedio) {
		this.id = id;
		this.ativo = ativo;
		this.cotas = cotas;
		this.valorMedio = valorMedio;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Double getValorMedio() {
		return valorMedio;
	}
	
	public void setValorMedio(Double valorMedio) {
		this.valorMedio = valorMedio;
	}

	@Override
	public String toString() {
		return "Posicao [Id: " + id + ", Ativo: " + ativo + ", Cotas: " + cotas + ", Valor médio: " + String.format("R$%.2f", valorMedio) + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ativo == null) ? 0 : ativo.hashCode());
		result = prime * result + ((cotas == null) ? 0 : cotas.hashCode());
		result = prime * result + ((valorMedio == null) ? 0 : valorMedio.hashCode());
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
		Posicao other = (Posicao) obj;
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
		if (valorMedio == null) {
			if (other.valorMedio != null)
				return false;
		} else if (!valorMedio.equals(other.valorMedio))
			return false;
		return true;
	}	
	
	
}
