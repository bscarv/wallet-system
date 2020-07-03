package model.entities;

import model.entities.enums.TipoAtivo;

public class Ativo {
	
	private Integer id;
	private String codigo;
	private TipoAtivo tipo;	

	public Ativo(String codigo) {
		this.codigo = codigo;
	}
	
	public Ativo(Integer id, String codigo, TipoAtivo tipo) {
		this.id = id;
		this.codigo = codigo;
		this.tipo = tipo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public TipoAtivo getTipo() {
		return tipo;
	}

	public void setTipo(TipoAtivo tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return String.format("[Id: %3d, Código: %6s, Tipo: %7s]", id, codigo, tipo);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		Ativo other = (Ativo) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}	
}