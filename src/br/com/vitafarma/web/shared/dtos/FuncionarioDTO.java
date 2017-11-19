package br.com.vitafarma.web.shared.dtos;

import java.util.Date;

public class FuncionarioDTO extends AbstractDTO<String> implements
		Comparable<FuncionarioDTO> {
	private static final long serialVersionUID = -5134820110949139907L;

	// Propriedades
	public static final String PROPERTY_FUNCIONARIO_ID = "funcionarioId";
	public static final String PROPERTY_FUNCIONARIO_NOME = "funcionarioNome";
	public static final String PROPERTY_FUNCIONARIO_TELEFONE = "funcionarioTelefone";
	public static final String PROPERTY_FUNCIONARIO_ENDERECO = "funcionarioEndereco";
	public static final String PROPERTY_FUNCIONARIO_EMAIL = "funcionarioEmail";
	public static final String PROPERTY_FUNCIONARIO_CPF_VALUE = "funcionarioCpfValue";
	public static final String PROPERTY_FUNCIONARIO_CPF_STRING = "funcionarioCpfString";
	public static final String PROPERTY_FUNCIONARIO_SALARIO_VALUE = "funcionarioSalarioValue";
	public static final String PROPERTY_FUNCIONARIO_SALARIO_STRING = "funcionarioSalarioString";
	public static final String PROPERTY_FUNCIONARIO_DATA_ADIMISSAO = "funcionarioDataAdimissao";
	public static final String PROPERTY_FUNCIONARIO_DATA_ADIMISSAO_STRING = "funcionarioDataAdimissaoString";
	public static final String PROPERTY_FUNCIONARIO_DATA_DEMISSAO = "funcionarioDataDemissao";
	public static final String PROPERTY_FUNCIONARIO_DATA_DEMISSAO_STRING = "funcionarioDataDemissaoString";
	public static final String PROPERTY_FUNCIONARIO_CIDADE_ID = "funcionarioCidadeId";
	public static final String PROPERTY_FUNCIONARIO_CIDADE_NOME = "funcionarioCidadeNome";
	public static final String PROPERTY_FUNCIONARIO_ESTADO_ID = "funcionarioEstadoId";
	public static final String PROPERTY_FUNCIONARIO_ESTADO_NOME = "funcionarioEstadoNome";

	public FuncionarioDTO() {
		super();
	}

	public void setId(Long value) {
		this.set(PROPERTY_FUNCIONARIO_ID, value);
	}

	public Long getId() {
		return this.get(PROPERTY_FUNCIONARIO_ID);
	}

	public void setCpfValue(Long value) {
		this.set(PROPERTY_FUNCIONARIO_CPF_VALUE, value);
	}

	public Long getCpfValue() {
		return this.get(PROPERTY_FUNCIONARIO_CPF_VALUE);
	}

	public void setCpfString(String value) {
		this.set(PROPERTY_FUNCIONARIO_CPF_STRING, value);
	}

	public String getCpfString() {
		return this.get(PROPERTY_FUNCIONARIO_CPF_STRING);
	}

	public void setNome(String value) {
		this.set(PROPERTY_FUNCIONARIO_NOME, value);
	}

	public String getNome() {
		return this.get(PROPERTY_FUNCIONARIO_NOME);
	}

	public void setTelefone(Long value) {
		this.set(PROPERTY_FUNCIONARIO_TELEFONE, value);
	}

	public Long getTelefone() {
		return this.get(PROPERTY_FUNCIONARIO_TELEFONE);
	}

	public void setEndereco(String value) {
		this.set(PROPERTY_FUNCIONARIO_ENDERECO, value);
	}

	public String getEndereco() {
		return this.get(PROPERTY_FUNCIONARIO_ENDERECO);
	}

	public void setEmail(String value) {
		this.set(PROPERTY_FUNCIONARIO_EMAIL, value);
	}

	public String getEmail() {
		return this.get(PROPERTY_FUNCIONARIO_EMAIL);
	}

	public void setSalarioValue(Double value) {
		this.set(PROPERTY_FUNCIONARIO_SALARIO_VALUE, value);
	}

	public Double getSalarioValue() {
		return this.get(PROPERTY_FUNCIONARIO_SALARIO_VALUE);
	}

	public void setSalarioString(String value) {
		this.set(PROPERTY_FUNCIONARIO_SALARIO_STRING, value);
	}

	public String getSalarioString() {
		return this.get(PROPERTY_FUNCIONARIO_SALARIO_STRING);
	}

	public void setDataAdimissao(Date value) {
		this.set(PROPERTY_FUNCIONARIO_DATA_ADIMISSAO, value);
	}

	public Date getDataAdimissao() {
		return this.get(PROPERTY_FUNCIONARIO_DATA_ADIMISSAO);
	}

	public void setDataAdimissaoString(String value) {
		this.set(PROPERTY_FUNCIONARIO_DATA_ADIMISSAO_STRING, value);
	}

	public String getDataAdimissaoString() {
		return this.get(PROPERTY_FUNCIONARIO_DATA_ADIMISSAO_STRING);
	}

	public void setDataDemissao(Date value) {
		this.set(PROPERTY_FUNCIONARIO_DATA_DEMISSAO, value);
	}

	public Date getDataDemissao() {
		return this.get(PROPERTY_FUNCIONARIO_DATA_DEMISSAO);
	}

	public void setDataDemissaoString(String value) {
		this.set(PROPERTY_FUNCIONARIO_DATA_DEMISSAO_STRING, value);
	}

	public String getDataDemissaoString() {
		return this.get(PROPERTY_FUNCIONARIO_DATA_DEMISSAO_STRING);
	}

	public void setCidadeId(Long value) {
		this.set(PROPERTY_FUNCIONARIO_CIDADE_ID, value);
	}

	public Long getCidadeId() {
		return this.get(PROPERTY_FUNCIONARIO_CIDADE_ID);
	}

	public void setCidadeNome(String value) {
		this.set(PROPERTY_FUNCIONARIO_CIDADE_NOME, value);
	}

	public String getCidadeNome() {
		return this.get(PROPERTY_FUNCIONARIO_CIDADE_NOME);
	}

	public void setEstadoId(Long value) {
		this.set(PROPERTY_FUNCIONARIO_ESTADO_ID, value);
	}

	public Long getEstadoId() {
		return this.get(PROPERTY_FUNCIONARIO_ESTADO_ID);
	}

	public void setEstadoNome(String value) {
		this.set(PROPERTY_FUNCIONARIO_ESTADO_NOME, value);
	}

	public String getEstadoNome() {
		return this.get(PROPERTY_FUNCIONARIO_ESTADO_NOME);
	}

	@Override
	public String getNaturalKey() {
		return (this.getCpfValue() == null ? "" : this.getCpfValue().toString());
	}

	@Override
	public int compareTo(FuncionarioDTO o) {
		int result = this.getNaturalKey().compareTo(o.getNaturalKey());
		return result;
	}
}
