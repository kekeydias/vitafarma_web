package br.com.vitafarma.web.shared.dtos;

import java.util.Date;

public class CenarioDTO extends AbstractDTO<String> implements
		Comparable<CenarioDTO> {

	private static final long serialVersionUID = 4046119822523079085L;

	// Propriedades
	public static final String PROPERTY_ID = "cenarioId";
	public static final String PROPERTY_NOME = "cenarioNome";
	public static final String PROPERTY_ENDERECO1 = "cenarioEndereco1";
	public static final String PROPERTY_ENDERECO2 = "cenarioEndereco2";
	public static final String PROPERTY_CRIADO_USUARIO_STRING = "cenarioCriadoUsuarioString";
	public static final String PROPERTY_CRIADO_USUARIO_DATE = "cenarioCriadoUsuarioDate";
	public static final String PROPERTY_ATUALIZADO_USUARIO_DATE = "cenarioAtualizadoUsuarioDate";
	public static final String PROPERTY_ATUALIZADO_USUARIO_STRING = "cenarioAtualizadoUsuarioString";
	public static final String PROPERTY_VERSAO_SISTEMA = "cenarioVersaoSistema";
	public static final String PROPERTY_CNPJ_VALUE = "cenarioCnpjValue";
	public static final String PROPERTY_CNPJ_STRING = "cenarioCnpjString";
	public static final String PROPERTY_INSCRICAO_ESTADUAL = "cenarioInscricaoEstadual";
	public static final String PROPERTY_INSCRICAO_MUNICIPAL = "cenarioNomeInscricaoMunicipal";
	public static final String PROPERTY_COO = "cenarioNomeCoo";

	public CenarioDTO() {
		super();
	}

	public void setId(Long value) {
		this.set(PROPERTY_ID, value);
	}

	public Long getId() {
		return this.get(PROPERTY_ID);
	}

	public void setNome(String value) {
		this.set(PROPERTY_NOME, value);
	}

	public String getNome() {
		return this.get(PROPERTY_NOME);
	}

	public void setEndereco1(String value) {
		this.set(PROPERTY_ENDERECO1, value);
	}

	public String getEndereco1() {
		return this.get(PROPERTY_ENDERECO1);
	}

	public void setEndereco2(String value) {
		this.set(PROPERTY_ENDERECO2, value);
	}

	public String getEndereco2() {
		return this.get(PROPERTY_ENDERECO2);
	}

	public void setCriadoUsuarioString(String value) {
		this.set(PROPERTY_CRIADO_USUARIO_STRING, value);
	}

	public String getCriadoUsuarioString() {
		return this.get(PROPERTY_CRIADO_USUARIO_STRING);
	}

	public void setAtualizadoUsuarioString(String value) {
		this.set(PROPERTY_ATUALIZADO_USUARIO_STRING, value);
	}

	public String getAtualizadoUsuarioString() {
		return this.get(PROPERTY_ATUALIZADO_USUARIO_STRING);
	}

	public void setVersaoSistema(String value) {
		this.set(PROPERTY_VERSAO_SISTEMA, value);
	}

	public String getVersaoSistema() {
		return this.get(PROPERTY_VERSAO_SISTEMA);
	}

	public void setCriadoUsuarioDate(Date value) {
		this.set(PROPERTY_CRIADO_USUARIO_DATE, value);
	}

	public Date getCriadoUsuarioDate() {
		return this.get(PROPERTY_CRIADO_USUARIO_DATE);
	}

	public void setAtualizadoUsuarioDate(Date value) {
		this.set(PROPERTY_ATUALIZADO_USUARIO_DATE, value);
	}

	public Date getAtualizadoUsuarioDate() {
		return this.get(PROPERTY_ATUALIZADO_USUARIO_DATE);
	}

	public void setCnpjValue(Long value) {
		this.set(PROPERTY_CNPJ_VALUE, value);
	}

	public Long getCnpjValue() {
		return this.get(PROPERTY_CNPJ_VALUE);
	}

	public void setCnpjString(String value) {
		this.set(PROPERTY_CNPJ_STRING, value);
	}

	public String getCnpjString() {
		return this.get(PROPERTY_CNPJ_STRING);
	}

	public void setInscricaoEstadual(String value) {
		this.set(PROPERTY_INSCRICAO_ESTADUAL, value);
	}

	public String getInscricaoEstadual() {
		return this.get(PROPERTY_INSCRICAO_ESTADUAL);
	}

	public void setInscricaoMunicipal(String value) {
		this.set(PROPERTY_INSCRICAO_MUNICIPAL, value);
	}

	public String getInscricaoMunicipal() {
		return this.get(PROPERTY_INSCRICAO_MUNICIPAL);
	}

	public void setCoo(String value) {
		this.set(PROPERTY_COO, value);
	}

	public String getCoo() {
		return this.get(PROPERTY_COO);
	}

	@Override
	public String getNaturalKey() {
		return (this.getId() == null ? null : this.getId().toString());
	}

	@Override
	public int compareTo(CenarioDTO o) {
		return this.getNome().compareTo(o.getNome());
	}
}