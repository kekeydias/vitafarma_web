package br.com.vitafarma.web.shared.dtos;

public class FornecedorDTO extends AbstractDTO<String> implements
		Comparable<FornecedorDTO> {
	private static final long serialVersionUID = -5134820110949139907L;

	// Propriedades
	public static final String PROPERTY_FORNECEDOR_ID = "fornecedorId";
	public static final String PROPERTY_FORNECEDOR_NOME = "fornecedorNome";
	public static final String PROPERTY_FORNECEDOR_TELEFONE_VALUE = "fornecedorTelefoneValue";
	public static final String PROPERTY_FORNECEDOR_TELEFONE_STRING = "fornecedorTelefoneString";
	public static final String PROPERTY_FORNECEDOR_ENDERECO = "fornecedorEndereco";
	public static final String PROPERTY_FORNECEDOR_EMAIL = "fornecedorEmail";
	public static final String PROPERTY_FORNECEDOR_NOME_FANTASIA = "fornecedorNomeFantasia";
	public static final String PROPERTY_FORNECEDOR_CNPJ_VALUE = "fornecedorCnpjValue";
	public static final String PROPERTY_FORNECEDOR_CNPJ_STRING = "fornecedorCnpjString";
	public static final String PROPERTY_FORNECEDOR_CIDADE_ID = "fornecedorCidadeId";
	public static final String PROPERTY_FORNECEDOR_CIDADE_NOME = "fornecedorCidadeNome";
	public static final String PROPERTY_FORNECEDOR_ESTADO_ID = "fornecedorEstadoId";
	public static final String PROPERTY_FORNECEDOR_ESTADO_NOME = "fornecedorEstadoNome";

	public FornecedorDTO() {
		super();
	}

	public void setId(Long value) {
		this.set(PROPERTY_FORNECEDOR_ID, value);
	}

	public Long getId() {
		return this.get(PROPERTY_FORNECEDOR_ID);
	}

	public void setCnpjValue(Long value) {
		this.set(PROPERTY_FORNECEDOR_CNPJ_VALUE, value);
	}

	public Long getCnpjValue() {
		return this.get(PROPERTY_FORNECEDOR_CNPJ_VALUE);
	}

	public void setCnpjString(String value) {
		this.set(PROPERTY_FORNECEDOR_CNPJ_STRING, value);
	}

	public String getCnpjString() {
		return this.get(PROPERTY_FORNECEDOR_CNPJ_STRING);
	}

	public void setNome(String value) {
		this.set(PROPERTY_FORNECEDOR_NOME, value);
	}

	public String getNome() {
		return this.get(PROPERTY_FORNECEDOR_NOME);
	}

	public void setTelefoneValue(Long value) {
		this.set(PROPERTY_FORNECEDOR_TELEFONE_VALUE, value);
	}

	public Long getTelefoneValue() {
		return this.get(PROPERTY_FORNECEDOR_TELEFONE_VALUE);
	}

	public void setTelefoneString(String value) {
		this.set(PROPERTY_FORNECEDOR_TELEFONE_STRING, value);
	}

	public String getTelefoneString() {
		return this.get(PROPERTY_FORNECEDOR_TELEFONE_STRING);
	}

	public void setEndereco(String value) {
		this.set(PROPERTY_FORNECEDOR_ENDERECO, value);
	}

	public String getEndereco() {
		return this.get(PROPERTY_FORNECEDOR_ENDERECO);
	}

	public void setEmail(String value) {
		this.set(PROPERTY_FORNECEDOR_EMAIL, value);
	}

	public String getEmail() {
		return this.get(PROPERTY_FORNECEDOR_EMAIL);
	}

	public void setNomeFantasia(String value) {
		this.set(PROPERTY_FORNECEDOR_NOME_FANTASIA, value);
	}

	public String getNomeFantasia() {
		return this.get(PROPERTY_FORNECEDOR_NOME_FANTASIA);
	}

	public void setCidadeId(Long value) {
		this.set(PROPERTY_FORNECEDOR_CIDADE_ID, value);
	}

	public Long getCidadeId() {
		return this.get(PROPERTY_FORNECEDOR_CIDADE_ID);
	}

	public void setCidadeNome(String value) {
		this.set(PROPERTY_FORNECEDOR_CIDADE_NOME, value);
	}

	public String getCidadeNome() {
		return this.get(PROPERTY_FORNECEDOR_CIDADE_NOME);
	}

	public void setEstadoId(Long value) {
		this.set(PROPERTY_FORNECEDOR_ESTADO_ID, value);
	}

	public Long getEstadoId() {
		return this.get(PROPERTY_FORNECEDOR_ESTADO_ID);
	}

	public void setEstadoNome(String value) {
		this.set(PROPERTY_FORNECEDOR_ESTADO_NOME, value);
	}

	public String getEstadoNome() {
		return this.get(PROPERTY_FORNECEDOR_ESTADO_NOME);
	}

	@Override
	public String getNaturalKey() {
		return (this.getCnpjValue() == null ? "" : this.getCnpjValue()
				.toString());
	}

	@Override
	public int compareTo(FornecedorDTO o) {
		int result = this.getCnpjValue().compareTo(o.getCnpjValue());
		return result;
	}
}
