package br.com.vitafarma.web.shared.dtos;

public class LaboratorioDTO extends AbstractDTO<String> implements
		Comparable<LaboratorioDTO> {
	private static final long serialVersionUID = -5134820110949139907L;

	// Propriedades
	public static final String PROPERTY_LABORATORIO_ID = "laboratorioId";
	public static final String PROPERTY_LABORATORIO_NOME = "laboratorioNome";
	public static final String PROPERTY_LABORATORIO_MED_LAB_VALUE = "laboratorioMedLabValue";
	public static final String PROPERTY_LABORATORIO_MED_LAB_STRING = "laboratorioMedLabString";
	public static final String PROPERTY_LABORATORIO_TELEFONE_VALUE = "laboratorioTelefoneValue";
	public static final String PROPERTY_LABORATORIO_TELEFONE_STRING = "laboratorioTelefoneString";
	public static final String PROPERTY_LABORATORIO_ENDERECO = "laboratorioEndereco";
	public static final String PROPERTY_LABORATORIO_EMAIL = "laboratorioEmail";
	public static final String PROPERTY_LABORATORIO_NOME_FANTASIA = "laboratorioNomeFantasia";
	public static final String PROPERTY_LABORATORIO_CNPJ_VALUE = "laboratorioCnpjValue";
	public static final String PROPERTY_LABORATORIO_CNPJ_STRING = "laboratorioCnpjString";
	public static final String PROPERTY_LABORATORIO_CIDADE_ID = "laboratorioCidadeId";
	public static final String PROPERTY_LABORATORIO_CIDADE_NOME = "laboratorioCidadeNome";
	public static final String PROPERTY_LABORATORIO_ESTADO_ID = "laboratorioEstadoId";
	public static final String PROPERTY_LABORATORIO_ESTADO_NOME = "laboratorioEstadoNome";

	public LaboratorioDTO() {
		super();
	}

	public void setId(Long value) {
		this.set(PROPERTY_LABORATORIO_ID, value);
	}

	public Long getId() {
		return this.get(PROPERTY_LABORATORIO_ID);
	}

	public void setCnpjValue(Long value) {
		this.set(PROPERTY_LABORATORIO_CNPJ_VALUE, value);
	}

	public Long getCnpjValue() {
		return this.get(PROPERTY_LABORATORIO_CNPJ_VALUE);
	}

	public void setCnpjString(String value) {
		this.set(PROPERTY_LABORATORIO_CNPJ_STRING, value);
	}

	public String getCnpjString() {
		return this.get(PROPERTY_LABORATORIO_CNPJ_STRING);
	}

	public void setNome(String value) {
		this.set(PROPERTY_LABORATORIO_NOME, value);
	}

	public String getNome() {
		return this.get(PROPERTY_LABORATORIO_NOME);
	}

	public void setTelefoneValue(Long value) {
		this.set(PROPERTY_LABORATORIO_TELEFONE_VALUE, value);
	}

	public Long getTelefoneValue() {
		return this.get(PROPERTY_LABORATORIO_TELEFONE_VALUE);
	}

	public void setTelefoneString(String value) {
		this.set(PROPERTY_LABORATORIO_TELEFONE_STRING, value);
	}

	public String getTelefoneString() {
		return this.get(PROPERTY_LABORATORIO_TELEFONE_STRING);
	}

	public void setEndereco(String value) {
		this.set(PROPERTY_LABORATORIO_ENDERECO, value);
	}

	public String getEndereco() {
		return this.get(PROPERTY_LABORATORIO_ENDERECO);
	}

	public void setEmail(String value) {
		this.set(PROPERTY_LABORATORIO_EMAIL, value);
	}

	public String getEmail() {
		return this.get(PROPERTY_LABORATORIO_EMAIL);
	}

	public void setNomeFantasia(String value) {
		this.set(PROPERTY_LABORATORIO_NOME_FANTASIA, value);
	}

	public String getNomeFantasia() {
		return this.get(PROPERTY_LABORATORIO_NOME_FANTASIA);
	}

	public void setCidadeId(Long value) {
		this.set(PROPERTY_LABORATORIO_CIDADE_ID, value);
	}

	public Long getCidadeId() {
		return this.get(PROPERTY_LABORATORIO_CIDADE_ID);
	}

	public void setCidadeNome(String value) {
		this.set(PROPERTY_LABORATORIO_CIDADE_NOME, value);
	}

	public String getCidadeNome() {
		return this.get(PROPERTY_LABORATORIO_CIDADE_NOME);
	}

	public void setEstadoId(Long value) {
		this.set(PROPERTY_LABORATORIO_ESTADO_ID, value);
	}

	public Long getEstadoId() {
		return this.get(PROPERTY_LABORATORIO_ESTADO_ID);
	}

	public void setEstadoNome(String value) {
		this.set(PROPERTY_LABORATORIO_ESTADO_NOME, value);
	}

	public String getEstadoNome() {
		return this.get(PROPERTY_LABORATORIO_ESTADO_NOME);
	}

	public void setMedLabValue(Long value) {
		this.set(PROPERTY_LABORATORIO_MED_LAB_VALUE, value);
	}

	public Long getMedLabValue() {
		return this.get(PROPERTY_LABORATORIO_MED_LAB_VALUE);
	}

	public void setMedLabString(String value) {
		this.set(PROPERTY_LABORATORIO_MED_LAB_STRING, value);
	}

	public String getMedLabString() {
		return this.get(PROPERTY_LABORATORIO_MED_LAB_STRING);
	}

	@Override
	public String getNaturalKey() {
		return (this.getCnpjValue() == null ? "" : this.getCnpjValue()
				.toString());
	}

	@Override
	public int compareTo(LaboratorioDTO o) {
		int result = this.getCnpjValue().compareTo(o.getCnpjValue());
		return result;
	}
}
