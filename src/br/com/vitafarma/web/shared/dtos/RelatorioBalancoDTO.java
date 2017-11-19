package br.com.vitafarma.web.shared.dtos;

import java.util.Date;

import br.com.vitafarma.web.shared.util.VitafarmaDate;
import br.com.vitafarma.web.shared.util.VitafarmaUtil;

public class RelatorioBalancoDTO extends AbstractDTO<String>implements Comparable<RelatorioBalancoDTO> {
	private static final long serialVersionUID = -8163335523956904852L;

	// Propriedades
	public static final String PROPERTY_RELATORIO_BALANCO_VENDA_ID = "relatorioBalancoVendaId";
	public static final String PROPERTY_RELATORIO_BALANCO_ENTRADA_PRODUTO_ID = "relatorioBalancoEntradaProdutoId";
	public static final String PROPERTY_RELATORIO_BALANCO_DATA_VALUE = "relatorioBalancoDataValue";
	public static final String PROPERTY_RELATORIO_BALANCO_DATA_STRING = "relatorioBalancoDataString";
	public static final String PROPERTY_RELATORIO_BALANCO_OPERACAO = "relatorioBalancoOperacao";
	public static final String PROPERTY_RELATORIO_BALANCO_PRODUTO_ID = "relatorioBalancoProdutoId";
	public static final String PROPERTY_RELATORIO_BALANCO_PRODUTO_STRING = "relatorioBalancoProdutoString";
	public static final String PROPERTY_RELATORIO_BALANCO_CLIENTE_ID = "relatorioBalancoClienteId";
	public static final String PROPERTY_RELATORIO_BALANCO_CLIENTE_STRING = "relatorioBalancoClienteString";
	public static final String PROPERTY_RELATORIO_BALANCO_CLIENTE_CPF_VALUE = "relatorioBalancoClienteCpfValue";
	public static final String PROPERTY_RELATORIO_BALANCO_CLIENTE_CPF_STRING = "relatorioBalancoClienteCpfString";
	public static final String PROPERTY_RELATORIO_BALANCO_VALOR_FINAL_VALUE = "relatorioBalancoValorFinalValue";
	public static final String PROPERTY_RELATORIO_BALANCO_VALOR_FINAL_STRING = "relatorioBalancoValorFinalString";
	public static final String PROPERTY_RELATORIO_BALANCO_FORNECEDOR_ID = "relatorioBalancoFornecedorId";
	public static final String PROPERTY_RELATORIO_BALANCO_FORNECEDOR_STRING = "relatorioBalancoFornecedorString";
	public static final String PROPERTY_RELATORIO_BALANCO_FORNECEDOR_CNPJ_VALUE = "relatorioBalancoFornecedorCnpjValue";
	public static final String PROPERTY_RELATORIO_BALANCO_FORNECEDOR_CNPJ_STRING = "relatorioBalancoFornecedorCnpjString";

	public RelatorioBalancoDTO() {
		super();
	}

	public void setVendaId(Long value) {
		this.set(PROPERTY_RELATORIO_BALANCO_VENDA_ID, value);
	}

	public Long getVendaId() {
		return this.get(PROPERTY_RELATORIO_BALANCO_VENDA_ID);
	}

	public void setDataValue(VitafarmaDate value) {
		Date date = (value == null ? null : VitafarmaUtil.getDate(value));
		this.set(PROPERTY_RELATORIO_BALANCO_DATA_VALUE, date);
	}

	public VitafarmaDate getDataValue() {
		Date date = this.get(PROPERTY_RELATORIO_BALANCO_DATA_VALUE);
		return VitafarmaUtil.getVitafarmaDate(date);
	}

	public void setDataString(String value) {
		this.set(PROPERTY_RELATORIO_BALANCO_DATA_STRING, value);
	}

	public String getDataString() {
		return this.get(PROPERTY_RELATORIO_BALANCO_DATA_STRING);
	}

	public void setOperacao(String value) {
		this.set(PROPERTY_RELATORIO_BALANCO_OPERACAO, value);
	}

	public String getOperacao() {
		return this.get(PROPERTY_RELATORIO_BALANCO_OPERACAO);
	}

	public void setProdutoId(Long value) {
		this.set(PROPERTY_RELATORIO_BALANCO_PRODUTO_ID, value);
	}

	public Long getProdutoId() {
		return this.get(PROPERTY_RELATORIO_BALANCO_PRODUTO_ID);
	}

	public void setProdutoString(String value) {
		this.set(PROPERTY_RELATORIO_BALANCO_PRODUTO_STRING, value);
	}

	public String getProdutoString() {
		return this.get(PROPERTY_RELATORIO_BALANCO_PRODUTO_STRING);
	}

	public void setClienteId(Long value) {
		this.set(PROPERTY_RELATORIO_BALANCO_CLIENTE_ID, value);
	}

	public Long getClienteId() {
		return this.get(PROPERTY_RELATORIO_BALANCO_CLIENTE_ID);
	}

	public void setClienteCpfValue(Long value) {
		this.set(PROPERTY_RELATORIO_BALANCO_CLIENTE_CPF_VALUE, value);
	}

	public Long getClienteCpfValue() {
		return this.get(PROPERTY_RELATORIO_BALANCO_CLIENTE_CPF_VALUE);
	}

	public void setClienteCpfString(String value) {
		this.set(PROPERTY_RELATORIO_BALANCO_CLIENTE_CPF_STRING, value);
	}

	public String getClienteCpfString() {
		return this.get(PROPERTY_RELATORIO_BALANCO_CLIENTE_CPF_STRING);
	}

	public void setClienteString(String value) {
		this.set(PROPERTY_RELATORIO_BALANCO_CLIENTE_STRING, value);
	}

	public String getClienteString() {
		return this.get(PROPERTY_RELATORIO_BALANCO_CLIENTE_STRING);
	}

	public void setEntradaProdutoId(Long value) {
		this.set(PROPERTY_RELATORIO_BALANCO_ENTRADA_PRODUTO_ID, value);
	}

	public Long getEntradaProdutoId() {
		return this.get(PROPERTY_RELATORIO_BALANCO_ENTRADA_PRODUTO_ID);
	}

	public void setValorFinalValue(Double value) {
		this.set(PROPERTY_RELATORIO_BALANCO_VALOR_FINAL_VALUE, value);
	}

	public Double getValorFinalValue() {
		return this.get(PROPERTY_RELATORIO_BALANCO_VALOR_FINAL_VALUE);
	}

	public void setValorFinalString(String value) {
		this.set(PROPERTY_RELATORIO_BALANCO_VALOR_FINAL_STRING, value);
	}

	public String getValorFinalString() {
		return this.get(PROPERTY_RELATORIO_BALANCO_VALOR_FINAL_STRING);
	}

	public void setFornecedorId(Long value) {
		this.set(PROPERTY_RELATORIO_BALANCO_FORNECEDOR_ID, value);
	}

	public Long getFornecedorId() {
		return this.get(PROPERTY_RELATORIO_BALANCO_FORNECEDOR_ID);
	}

	public void setFornecedorCnpjValue(Long value) {
		this.set(PROPERTY_RELATORIO_BALANCO_FORNECEDOR_CNPJ_VALUE, value);
	}

	public Long getFornecedorCnpjValue() {
		return this.get(PROPERTY_RELATORIO_BALANCO_FORNECEDOR_CNPJ_VALUE);
	}

	public void setFornecedorCnpjString(String value) {
		this.set(PROPERTY_RELATORIO_BALANCO_FORNECEDOR_CNPJ_STRING, value);
	}

	public String getFornecedorCnpjString() {
		return this.get(PROPERTY_RELATORIO_BALANCO_FORNECEDOR_CNPJ_STRING);
	}

	public void setFornecedorString(String value) {
		this.set(PROPERTY_RELATORIO_BALANCO_FORNECEDOR_STRING, value);
	}

	public String getFornecedorString() {
		return this.get(PROPERTY_RELATORIO_BALANCO_FORNECEDOR_STRING);
	}

	@Override
	public String getNaturalKey() {
		String operacao = (this.getOperacao() == null ? "" : this.getOperacao().toString());
		String data = (this.getDataValue() == null ? "" : this.getDataValue().toString());
		String cliente = (this.getClienteId() == null ? "" : this.getClienteId().toString());
		String fornecedor = (this.getFornecedorId() == null ? "" : this.getFornecedorId().toString());

		return (operacao + " - " + data + " - " + cliente + " - " + fornecedor);
	}

	@Override
	public int compareTo(RelatorioBalancoDTO o) {
		int result = this.getOperacao().compareTo(o.getOperacao());

		if (result == 0) {
			result = this.getDataValue().compareTo(o.getDataValue());
		}

		return result;
	}
}
