package br.com.vitafarma.web.shared.dtos;

import java.util.Date;

import br.com.vitafarma.web.shared.util.VitafarmaDate;
import br.com.vitafarma.web.shared.util.VitafarmaUtil;

public class EntradaProdutoGroupDTO extends AbstractDTO<String> implements
		Comparable<EntradaProdutoGroupDTO> {
	private static final long serialVersionUID = -5134820110949139907L;

	// Propriedades
	public static final String PROPERTY_ENTRADA_PRODUTO_GROUP_ID = "entradaProdutoGroupId";
	public static final String PROPERTY_ENTRADA_PRODUTO_GROUP_DATA_ENTRADA_VALUE = "entradaProdutoGroupDataEntrada";
	public static final String PROPERTY_ENTRADA_PRODUTO_GROUP_DATA_ENTRADA_STRING = "entradaProdutoGroupDataEntradaString";
	public static final String PROPERTY_ENTRADA_PRODUTO_GROUP_HORA_MINUTO_STRING = "entradaProdutoGroupHoraMinutoString";
	public static final String PROPERTY_ENTRADA_PRODUTO_GROUP_HORA = "entradaProdutoGroupHora";
	public static final String PROPERTY_ENTRADA_PRODUTO_GROUP_MINUTO = "entradaProdutoGroupMinuto";
	public static final String PROPERTY_ENTRADA_PRODUTO_GROUP_FORNECEDOR_ID = "entradaProdutoGroupFornecedorId";
	public static final String PROPERTY_ENTRADA_PRODUTO_GROUP_FORNECEDOR_STRING = "entradaProdutoGroupFornecedorString";
	public static final String PROPERTY_ENTRADA_PRODUTO_GROUP_NOTA_FISCAL_ID = "entradaProdutoGroupNotaFiscalId";
	public static final String PROPERTY_ENTRADA_PRODUTO_GROUP_NOTA_FISCAL_CODIGO = "entradaProdutoGroupNotaFiscalCodigo";
	public static final String PROPERTY_ENTRADA_PRODUTO_GROUP_NOTA_FISCAL_DATA = "entradaProdutoGroupNotaFiscalData";
	public static final String PROPERTY_ENTRADA_PRODUTO_GROUP_VALOR_TOTAL_VALUE = "entradaProdutoGroupValorTotalValue";
	public static final String PROPERTY_ENTRADA_PRODUTO_GROUP_VALOR_TOTAL_STRING = "entradaProdutoGroupValorTotalString";

	public EntradaProdutoGroupDTO() {
		super();
	}

	public void setId(Long value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_GROUP_ID, value);
	}

	public Long getId() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_GROUP_ID);
	}

	public void setDataEntradaValue(VitafarmaDate value) {
		Date date = (value == null ? null : VitafarmaUtil.getDate(value));
		this.set(PROPERTY_ENTRADA_PRODUTO_GROUP_DATA_ENTRADA_VALUE, date);
	}

	public VitafarmaDate getDataEntradaValue() {
		Date date = this.get(PROPERTY_ENTRADA_PRODUTO_GROUP_DATA_ENTRADA_VALUE);
		return VitafarmaUtil.getVitafarmaDate(date);
	}

	public void setDataEntradaString(String value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_GROUP_DATA_ENTRADA_STRING, value);
	}

	public String getDataEntradaString() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_GROUP_DATA_ENTRADA_STRING);
	}

	public void setFornecedorId(Long value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_GROUP_FORNECEDOR_ID, value);
	}

	public Long getFornecedorId() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_GROUP_FORNECEDOR_ID);
	}

	public void setFornecedorString(String value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_GROUP_FORNECEDOR_STRING, value);
	}

	public String getFornecedorString() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_GROUP_FORNECEDOR_STRING);
	}

	public void setNotaFiscalCodigo(String value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_GROUP_NOTA_FISCAL_CODIGO, value);
	}

	public String getNotaFiscalCodigo() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_GROUP_NOTA_FISCAL_CODIGO);
	}

	public void setNotaFiscalId(Long value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_GROUP_NOTA_FISCAL_ID, value);
	}

	public Long getNotaFiscalId() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_GROUP_NOTA_FISCAL_ID);
	}

	public void setNotaFiscalData(VitafarmaDate value) {
		Date date = (value == null ? null : VitafarmaUtil.getDate(value));
		this.set(PROPERTY_ENTRADA_PRODUTO_GROUP_NOTA_FISCAL_DATA, date);
	}

	public VitafarmaDate getNotaFiscalData() {
		Date date = this.get(PROPERTY_ENTRADA_PRODUTO_GROUP_NOTA_FISCAL_DATA);
		return VitafarmaUtil.getVitafarmaDate(date);
	}

	public void setEntradaProdutoHora(Integer value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_GROUP_HORA, value);
	}

	public Integer getEntradaProdutoHora() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_GROUP_HORA);
	}

	public void setEntradaProdutoMinuto(Integer value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_GROUP_MINUTO, value);
	}

	public Integer getEntradaProdutoMinuto() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_GROUP_MINUTO);
	}

	public void setEntradaProdutoHoraMinutoString(String value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_GROUP_HORA_MINUTO_STRING, value);
	}

	public String getEntradaProdutoHoraMinutoString() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_GROUP_HORA_MINUTO_STRING);
	}

	public void setValorTotalValue(Double value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_GROUP_VALOR_TOTAL_VALUE, value);
	}

	public Double getValorTotalValue() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_GROUP_VALOR_TOTAL_VALUE);
	}

	public void setValorTotalString(String value) {
		this.set(PROPERTY_ENTRADA_PRODUTO_GROUP_VALOR_TOTAL_STRING, value);
	}

	public String getValorTotalString() {
		return this.get(PROPERTY_ENTRADA_PRODUTO_GROUP_VALOR_TOTAL_STRING);
	}

	@Override
	public String getNaturalKey() {
		return (this.getId() + "-" + this.getFornecedorId());
	}

	@Override
	public int compareTo(EntradaProdutoGroupDTO o) {
		int result = this.getId().compareTo(o.getId());
		return result;
	}
}
