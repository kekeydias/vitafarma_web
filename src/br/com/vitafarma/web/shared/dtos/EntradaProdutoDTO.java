package br.com.vitafarma.web.shared.dtos;

import java.util.Date;

import br.com.vitafarma.web.shared.util.VitafarmaDate;
import br.com.vitafarma.web.shared.util.VitafarmaUtil;

public class EntradaProdutoDTO extends AbstractDTO<String> implements
		Comparable<EntradaProdutoDTO> {
	private static final long serialVersionUID = -5134820110949139907L;

	// Propriedades
	public static final String PROPERTY_ENTRADA_PRODUTO_ID = "entradaProdutoId";
	public static final String PROPERTY_ENTRADA_PRODUTO_GROUP_ID = "entradaProdutoGroupId";
	public static final String PROPERTY_ENTRADA_PRODUTO_GROUP_STRING = "entradaProdutoGroupString";
	public static final String PROPERTY_ENTRADA_PRODUTO_DATA_ENTRADA_VALUE = "entradaProdutoDataEntrada";
	public static final String PROPERTY_ENTRADA_PRODUTO_DATA_ENTRADA_STRING = "entradaProdutoDataEntradaString";
	public static final String PROPERTY_ENTRADA_PRODUTO_HORA_MINUTO_STRING = "entradaProdutoHoraMinutoString";
	public static final String PROPERTY_ENTRADA_PRODUTO_HORA = "entradaProdutoHora";
	public static final String PROPERTY_ENTRADA_PRODUTO_MINUTO = "entradaProdutoMinuto";
	public static final String PROPERTY_ENTRADA_PRODUTO_PRECO_UNITARIO_VALUE = "entradaProdutoPrecoUnitarioValue";
	public static final String PROPERTY_ENTRADA_PRODUTO_PRECO_UNITARIO_STRING = "entradaProdutoPrecoUnitarioString";
	public static final String PROPERTY_ENTRADA_PRODUTO_QUANTIDADE_VALUE = "entradaProdutoQuantidadeValue";
	public static final String PROPERTY_ENTRADA_PRODUTO_QUANTIDADE_STRING = "entradaProdutoQuantidadeString";
	public static final String PROPERTY_ENTRADA_PRODUTO_FORNECEDOR_ID = "entradaProdutoFornecedorId";
	public static final String PROPERTY_ENTRADA_PRODUTO_FORNECEDOR_STRING = "entradaProdutoFornecedorString";
	public static final String PROPERTY_ENTRADA_PRODUTO_PRODUTO_ID = "entradaProdutoProdutoId";
	public static final String PROPERTY_ENTRADA_PRODUTO_PRODUTO_STRING = "entradaProdutoProdutoString";
	public static final String PROPERTY_ENTRADA_PRODUTO_NOTA_FISCAL_ID = "entradaProdutoNotaFiscalId";
	public static final String PROPERTY_ENTRADA_PRODUTO_NOTA_FISCAL_CODIGO = "entradaProdutoNotaFiscalCodigo";
	public static final String PROPERTY_ENTRADA_PRODUTO_NOTA_FISCAL_DATA = "entradaProdutoNotaFiscalData";
	public static final String PROPERTY_ENTRADA_PRODUTO_VALOR_TOTAL_VALUE = "entradaProdutoValorTotalValue";
	public static final String PROPERTY_ENTRADA_PRODUTO_VALOR_TOTAL_STRING = "entradaProdutoValorTotalString";

	public EntradaProdutoDTO() {
		super();
	}

	public void setId(Long value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_ID, value);
	}

	public Long getId() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_ID);
	}

	public void setGroupId(Long value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_GROUP_ID, value);
	}

	public Long getGroupId() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_GROUP_ID);
	}

	public void setGroupString(String value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_GROUP_STRING, value);
	}

	public String getGroupString() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_GROUP_STRING);
	}

	public void setDataEntradaValue(VitafarmaDate value) {
		Date date = (value == null ? null : VitafarmaUtil.getDate(value));
		this.set(PROPERTY_ENTRADA_PRODUTO_DATA_ENTRADA_VALUE, date);
	}

	public VitafarmaDate getDataEntradaValue() {
		Date date = this.get(PROPERTY_ENTRADA_PRODUTO_DATA_ENTRADA_VALUE);
		return VitafarmaUtil.getVitafarmaDate(date);
	}

	public void setDataEntradaString(String value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_DATA_ENTRADA_STRING, value);
	}

	public String getDataEntradaString() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_DATA_ENTRADA_STRING);
	}

	public void setPrecoUnitarioValue(Double value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_PRECO_UNITARIO_VALUE, value);
	}

	public Double getPrecoUnitarioValue() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_PRECO_UNITARIO_VALUE);
	}

	public void setPrecoUnitarioString(String value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_PRECO_UNITARIO_STRING, value);
	}

	public String getPrecoUnitarioString() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_PRECO_UNITARIO_STRING);
	}

	public void setQuantidadeValue(Double value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_QUANTIDADE_VALUE, value);
	}

	public Double getQuantidadeValue() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_QUANTIDADE_VALUE);
	}

	public void setQuantidadeString(String value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_QUANTIDADE_STRING, value);
	}

	public String getQuantidadeString() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_QUANTIDADE_STRING);
	}

	public void setFornecedorId(Long value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_FORNECEDOR_ID, value);
	}

	public Long getFornecedorId() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_FORNECEDOR_ID);
	}

	public void setFornecedorString(String value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_FORNECEDOR_STRING, value);
	}

	public String getFornecedorString() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_FORNECEDOR_STRING);
	}

	public void setProdutoId(Long value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_PRODUTO_ID, value);
	}

	public Long getProdutoId() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_PRODUTO_ID);
	}

	public void setProdutoString(String value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_PRODUTO_STRING, value);
	}

	public String getProdutoString() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_PRODUTO_STRING);
	}

	public void setNotaFiscalCodigo(String value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_NOTA_FISCAL_CODIGO, value);
	}

	public String getNotaFiscalCodigo() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_NOTA_FISCAL_CODIGO);
	}

	public void setNotaFiscalId(Long value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_NOTA_FISCAL_ID, value);
	}

	public Long getNotaFiscalId() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_NOTA_FISCAL_ID);
	}

	public void setNotaFiscalData(VitafarmaDate value) {
		Date date = (value == null ? null : VitafarmaUtil.getDate(value));
		this.set(PROPERTY_ENTRADA_PRODUTO_NOTA_FISCAL_DATA, date);
	}

	public VitafarmaDate getNotaFiscalData() {
		Date date = this.get(PROPERTY_ENTRADA_PRODUTO_NOTA_FISCAL_DATA);
		return VitafarmaUtil.getVitafarmaDate(date);
	}

	public void setEntradaProdutoHora(Integer value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_HORA, value);
	}

	public Integer getEntradaProdutoHora() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_HORA);
	}

	public void setEntradaProdutoMinuto(Integer value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_MINUTO, value);
	}

	public Integer getEntradaProdutoMinuto() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_MINUTO);
	}

	public void setEntradaProdutoHoraMinutoString(String value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_HORA_MINUTO_STRING, value);
	}

	public String getEntradaProdutoHoraMinutoString() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_HORA_MINUTO_STRING);
	}

	public void setValorTotalValue(Double value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_VALOR_TOTAL_VALUE, value);
	}

	public Double getValorTotalValue() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_VALOR_TOTAL_VALUE);
	}

	public void setValorTotalString(String value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_VALOR_TOTAL_STRING, value);
	}

	public String getValorTotalString() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_VALOR_TOTAL_STRING);
	}

	@Override
	public String getNaturalKey() {
		return (this.getId() + "-" + this.getFornecedorId() + "-" + this
				.getProdutoId());
	}

	@Override
	public int compareTo(EntradaProdutoDTO o) {
		int result = this.getId().compareTo(o.getId());
		return result;
	}
}
