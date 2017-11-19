package br.com.vitafarma.web.shared.dtos;

import java.util.Date;

public class VendedorDTO extends AbstractDTO<String> implements
		Comparable<VendedorDTO> {
	private static final long serialVersionUID = -5134820110949139907L;

	// Propriedades
	public static final String PROPERTY_VENDEDOR_ID = "vendedorId";
	public static final String PROPERTY_VENDEDOR_NOME = "vendedorNome";
	public static final String PROPERTY_VENDEDOR_TELEFONE = "vendedorTelefone";
	public static final String PROPERTY_VENDEDOR_ENDERECO = "vendedorEndereco";
	public static final String PROPERTY_VENDEDOR_EMAIL = "vendedorEmail";
	public static final String PROPERTY_VENDEDOR_CPF_VALUE = "vendedorCpfValue";
	public static final String PROPERTY_VENDEDOR_CPF_STRING = "vendedorCpfString";
	public static final String PROPERTY_VENDEDOR_SALARIO_VALUE = "vendedorSalarioValue";
	public static final String PROPERTY_VENDEDOR_SALARIO_STRING = "vendedorSalarioString";
	public static final String PROPERTY_VENDEDOR_DATA_ADIMISSAO = "vendedorDataAdimissao";
	public static final String PROPERTY_VENDEDOR_DATA_ADIMISSAO_STRING = "vendedorDataAdimissaoString";
	public static final String PROPERTY_VENDEDOR_DATA_DEMISSAO = "vendedorDataDemissao";
	public static final String PROPERTY_VENDEDOR_DATA_DEMISSAO_STRING = "vendedorDataDemissaoString";
	public static final String PROPERTY_VENDEDOR_CIDADE_ID = "vendedorCidadeId";
	public static final String PROPERTY_VENDEDOR_CIDADE_NOME = "vendedorCidadeNome";
	public static final String PROPERTY_VENDEDOR_ESTADO_ID = "vendedorEstadoId";
	public static final String PROPERTY_VENDEDOR_ESTADO_NOME = "vendedorEstadoNome";

	public VendedorDTO() {
		super();
	}

	public void setId(Long value) {
		this.set(PROPERTY_VENDEDOR_ID, value);
	}

	public Long getId() {
		return this.get(PROPERTY_VENDEDOR_ID);
	}

	public void setCpfValue(Long value) {
		this.set(PROPERTY_VENDEDOR_CPF_VALUE, value);
	}

	public Long getCpfValue() {
		return this.get(PROPERTY_VENDEDOR_CPF_VALUE);
	}

	public void setCpfString(String value) {
		this.set(PROPERTY_VENDEDOR_CPF_STRING, value);
	}

	public String getCpfString() {
		return this.get(PROPERTY_VENDEDOR_CPF_STRING);
	}

	public void setNome(String value) {
		this.set(PROPERTY_VENDEDOR_NOME, value);
	}

	public String getNome() {
		return this.get(PROPERTY_VENDEDOR_NOME);
	}

	public void setTelefone(Long value) {
		this.set(PROPERTY_VENDEDOR_TELEFONE, value);
	}

	public Long getTelefone() {
		return this.get(PROPERTY_VENDEDOR_TELEFONE);
	}

	public void setEndereco(String value) {
		this.set(PROPERTY_VENDEDOR_ENDERECO, value);
	}

	public String getEndereco() {
		return this.get(PROPERTY_VENDEDOR_ENDERECO);
	}

	public void setEmail(String value) {
		this.set(PROPERTY_VENDEDOR_EMAIL, value);
	}

	public String getEmail() {
		return this.get(PROPERTY_VENDEDOR_EMAIL);
	}

	public void setSalarioValue(Double value) {
		this.set(PROPERTY_VENDEDOR_SALARIO_VALUE, value);
	}

	public Double getSalarioValue() {
		return this.get(PROPERTY_VENDEDOR_SALARIO_VALUE);
	}

	public void setSalarioString(String value) {
		this.set(PROPERTY_VENDEDOR_SALARIO_STRING, value);
	}

	public String getSalarioString() {
		return this.get(PROPERTY_VENDEDOR_SALARIO_STRING);
	}

	public void setDataAdimissao(Date value) {
		this.set(PROPERTY_VENDEDOR_DATA_ADIMISSAO, value);
	}

	public Date getDataAdimissao() {
		return this.get(PROPERTY_VENDEDOR_DATA_ADIMISSAO);
	}

	public void setDataAdimissaoString(String value) {
		this.set(PROPERTY_VENDEDOR_DATA_ADIMISSAO_STRING, value);
	}

	public String getDataAdimissaoString() {
		return this.get(PROPERTY_VENDEDOR_DATA_ADIMISSAO_STRING);
	}

	public void setDataDemissao(Date value) {
		this.set(PROPERTY_VENDEDOR_DATA_DEMISSAO, value);
	}

	public Date getDataDemissao() {
		return this.get(PROPERTY_VENDEDOR_DATA_DEMISSAO);
	}

	public void setDataDemissaoString(String value) {
		this.set(PROPERTY_VENDEDOR_DATA_DEMISSAO_STRING, value);
	}

	public String getDataDemissaoString() {
		return this.get(PROPERTY_VENDEDOR_DATA_DEMISSAO_STRING);
	}

	public void setCidadeId(Long value) {
		this.set(PROPERTY_VENDEDOR_CIDADE_ID, value);
	}

	public Long getCidadeId() {
		return this.get(PROPERTY_VENDEDOR_CIDADE_ID);
	}

	public void setCidadeNome(String value) {
		this.set(PROPERTY_VENDEDOR_CIDADE_NOME, value);
	}

	public String getCidadeNome() {
		return this.get(PROPERTY_VENDEDOR_CIDADE_NOME);
	}

	public void setEstadoId(Long value) {
		this.set(PROPERTY_VENDEDOR_ESTADO_ID, value);
	}

	public Long getEstadoId() {
		return this.get(PROPERTY_VENDEDOR_ESTADO_ID);
	}

	public void setEstadoNome(String value) {
		this.set(PROPERTY_VENDEDOR_ESTADO_NOME, value);
	}

	public String getEstadoNome() {
		return this.get(PROPERTY_VENDEDOR_ESTADO_NOME);
	}

	@Override
	public String getNaturalKey() {
		return (this.getCpfValue() == null ? "" : this.getCpfValue().toString());
	}

	@Override
	public int compareTo(VendedorDTO o) {
		int result = this.getNaturalKey().compareTo(o.getNaturalKey());
		return result;
	}
}
