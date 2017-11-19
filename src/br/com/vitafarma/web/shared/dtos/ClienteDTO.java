package br.com.vitafarma.web.shared.dtos;

public class ClienteDTO extends AbstractDTO<String> implements
		Comparable<ClienteDTO> {
	private static final long serialVersionUID = -5134820110949139907L;

	// Propriedades
	public static final String PROPERTY_CLIENTE_ID = "clienteId";
	public static final String PROPERTY_CLIENTE_NOME = "clienteNome";
	public static final String PROPERTY_CLIENTE_TELEFONE = "clienteTelefone";
	public static final String PROPERTY_CLIENTE_ENDERECO = "clienteEndereco";
	public static final String PROPERTY_CLIENTE_EMAIL = "clienteEmail";
	public static final String PROPERTY_CLIENTE_CPF_VALUE = "clienteCpfValue";
	public static final String PROPERTY_CLIENTE_CPF_STRING = "clienteCpfString";
	public static final String PROPERTY_CLIENTE_CIDADE_ID = "clienteCidadeId";
	public static final String PROPERTY_CLIENTE_CIDADE_NOME = "clienteCidadeNome";
	public static final String PROPERTY_CLIENTE_ESTADO_ID = "clienteEstadoId";
	public static final String PROPERTY_CLIENTE_ESTADO_NOME = "clienteEstadoNome";
	public static final String PROPERTY_CLIENTE_ESTADO_SIGLA = "clienteEstadoSigla";

	public ClienteDTO() {
		super();
	}

	public void setId(Long value) {
		this.set(PROPERTY_CLIENTE_ID, value);
	}

	public Long getId() {
		return this.get(PROPERTY_CLIENTE_ID);
	}

	public void setCpfValue(Long value) {
		this.set(PROPERTY_CLIENTE_CPF_VALUE, value);
	}

	public Long getCpfValue() {
		return this.get(PROPERTY_CLIENTE_CPF_VALUE);
	}

	public void setCpfString(String value) {
		this.set(PROPERTY_CLIENTE_CPF_STRING, value);
	}

	public String getCpfString() {
		return this.get(PROPERTY_CLIENTE_CPF_STRING);
	}

	public void setNome(String value) {
		this.set(PROPERTY_CLIENTE_NOME, value);
	}

	public String getNome() {
		return this.get(PROPERTY_CLIENTE_NOME);
	}

	public void setTelefone(Long value) {
		this.set(PROPERTY_CLIENTE_TELEFONE, value);
	}

	public Long getTelefone() {
		return this.get(PROPERTY_CLIENTE_TELEFONE);
	}

	public void setEndereco(String value) {
		this.set(PROPERTY_CLIENTE_ENDERECO, value);
	}

	public String getEndereco() {
		return this.get(PROPERTY_CLIENTE_ENDERECO);
	}

	public void setEmail(String value) {
		this.set(PROPERTY_CLIENTE_EMAIL, value);
	}

	public String getEmail() {
		return this.get(PROPERTY_CLIENTE_EMAIL);
	}

	public void setCidadeId(Long value) {
		this.set(PROPERTY_CLIENTE_CIDADE_ID, value);
	}

	public Long getCidadeId() {
		return this.get(PROPERTY_CLIENTE_CIDADE_ID);
	}

	public void setCidadeNome(String value) {
		this.set(PROPERTY_CLIENTE_CIDADE_NOME, value);
	}

	public String getCidadeNome() {
		return this.get(PROPERTY_CLIENTE_CIDADE_NOME);
	}

	public void setEstadoId(Long value) {
		this.set(PROPERTY_CLIENTE_ESTADO_ID, value);
	}

	public Long getEstadoId() {
		return this.get(PROPERTY_CLIENTE_ESTADO_ID);
	}

	public void setEstadoNome(String value) {
		this.set(PROPERTY_CLIENTE_ESTADO_NOME, value);
	}

	public String getEstadoNome() {
		return this.get(PROPERTY_CLIENTE_ESTADO_NOME);
	}

	public void setEstadoSigla(String value) {
		this.set(PROPERTY_CLIENTE_ESTADO_SIGLA, value);
	}

	public String getEstadoSigla() {
		return this.get(PROPERTY_CLIENTE_ESTADO_SIGLA);
	}

	@Override
	public String getNaturalKey() {
		return (this.getCpfValue() == null ? "" : this.getCpfValue().toString());
	}

	@Override
	public int compareTo(ClienteDTO o) {
		int result = this.getNaturalKey().compareTo(o.getNaturalKey());
		return result;
	}
}
