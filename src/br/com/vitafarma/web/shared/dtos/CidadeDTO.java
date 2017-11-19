package br.com.vitafarma.web.shared.dtos;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CidadeDTO extends AbstractDTO<String> implements
		Comparable<CidadeDTO>, IsSerializable {
	private static final long serialVersionUID = -5134820110949139907L;

	// Propriedades
	public static final String PROPERTY_CIDADE_ID = "cidadeId";
	public static final String PROPERTY_CIDADE_NOME = "cidadeNome";
	public static final String PROPERTY_CIDADE_ESTADO_ID = "cidadeEstadoId";
	public static final String PROPERTY_CIDADE_ESTADO_NOME = "cidadeEstadoNome";
	public static final String PROPERTY_CIDADE_ESTADO_SIGLA = "cidadeEstadoSigla";

	public CidadeDTO() {
		super();
	}

	public void setId(Long value) {
		this.set(PROPERTY_CIDADE_ID, value);
	}

	public Long getId() {
		return this.get(PROPERTY_CIDADE_ID);
	}

	public void setNome(String value) {
		this.set(PROPERTY_CIDADE_NOME, value);
	}

	public String getNome() {
		return this.get(PROPERTY_CIDADE_NOME);
	}

	public void setEstadoId(Long value) {
		this.set(PROPERTY_CIDADE_ESTADO_ID, value);
	}

	public Long getEstadoId() {
		return this.get(PROPERTY_CIDADE_ESTADO_ID);
	}

	public void setEstadoNome(String value) {
		this.set(PROPERTY_CIDADE_ESTADO_NOME, value);
	}

	public String getEstadoNome() {
		return this.get(PROPERTY_CIDADE_ESTADO_NOME);
	}

	public void setEstadoSigla(String value) {
		this.set(PROPERTY_CIDADE_ESTADO_SIGLA, value);
	}

	public String getEstadoSigla() {
		return this.get(PROPERTY_CIDADE_ESTADO_SIGLA);
	}

	@Override
	public String getNaturalKey() {
		return (this.getId() + this.getNome());
	}

	@Override
	public int compareTo(CidadeDTO o) {
		int result = this.getEstadoId().compareTo(o.getEstadoId());

		if (result == 0) {
			result = this.getNome().compareTo(o.getNome());
		}

		return result;
	}
}
