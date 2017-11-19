package br.com.vitafarma.web.shared.dtos;

public class EstadoDTO extends AbstractDTO<String> implements
		Comparable<EstadoDTO> {
	private static final long serialVersionUID = -5134820110949139907L;

	// Propriedades
	public static final String PROPERTY_ESTADO_ID = "estadoId";
	public static final String PROPERTY_ESTADO_NOME = "estadoNome";
	public static final String PROPERTY_ESTADO_SIGLA = "estadoSigla";

	public EstadoDTO() {
		super();
	}

	public void setId(Long value) {
		this.set(PROPERTY_ESTADO_ID, value);
	}

	public Long getId() {
		return this.get(PROPERTY_ESTADO_ID);
	}

	public void setNome(String value) {
		this.set(PROPERTY_ESTADO_NOME, value);
	}

	public String getNome() {
		return this.get(PROPERTY_ESTADO_NOME);
	}

	public void setSigla(String value) {
		this.set(PROPERTY_ESTADO_SIGLA, value);
	}

	public String getSigla() {
		return this.get(PROPERTY_ESTADO_SIGLA);
	}

	@Override
	public String getNaturalKey() {
		return (this.getId() + this.getNome());
	}

	@Override
	public int compareTo(EstadoDTO o) {
		int result = this.getNome().compareTo(o.getNome());
		return result;
	}
}
