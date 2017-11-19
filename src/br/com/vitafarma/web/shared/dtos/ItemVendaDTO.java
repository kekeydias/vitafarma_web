package br.com.vitafarma.web.shared.dtos;

public class ItemVendaDTO extends AbstractDTO<String> implements
		Comparable<ItemVendaDTO> {
	private static final long serialVersionUID = -5134820110949139907L;

	// Propriedades
	public static final String PROPERTY_ITEM_VENDA_ID = "itemVendaId";
	public static final String PROPERTY_ITEM_VENDA_VENDA_ID = "itemVendaVendaId";
	public static final String PROPERTY_ITEM_VENDA_CLIENTE_ID = "itemVendaClienteId";
	public static final String PROPERTY_ITEM_VENDA_CLIENTE_STRING = "itemVendaClienteString";
	public static final String PROPERTY_ITEM_VENDA_CLIENTE_CPF_VALUE = "itemVendaClienteCpfValue";
	public static final String PROPERTY_ITEM_VENDA_CLIENTE_CPF_STRING = "itemVendaClienteCpfString";
	public static final String PROPERTY_ITEM_VENDA_PRECO_UNITARIO_VALUE = "itemVendaPrecoUnitarioValue";
	public static final String PROPERTY_ITEM_VENDA_PRECO_UNITARIO_STRING = "itemVendaPrecoUnitarioValueString";
	public static final String PROPERTY_ITEM_VENDA_PRECO_FINAL_VALUE = "itemVendaPrecoFinalValue";
	public static final String PROPERTY_ITEM_VENDA_PRECO_FINAL_STRING = "itemVendaPrecoFinalString";
	public static final String PROPERTY_ITEM_VENDA_SUBTOTAL_VALUE = "itemVendaSubtotalValue";
	public static final String PROPERTY_ITEM_VENDA_SUBTOTAL_STRING = "itemVendaSubtotalString";
	public static final String PROPERTY_ITEM_VENDA_DESCONTO_ITEM_VALUE = "itemVendaDescontoItem";
	public static final String PROPERTY_ITEM_VENDA_DESCONTO_ITEM_STRING = "itemVendaDescontoItemString";
	public static final String PROPERTY_ITEM_VENDA_QUANTIDADE_VALUE = "itemVendaQuantidadeValue";
	public static final String PROPERTY_ITEM_VENDA_QUANTIDADE_STRING = "itemVendaQuantidadeString";
	public static final String PROPERTY_ITEM_VENDA_PRODUTO_ID = "itemVendaProdutoId";
	public static final String PROPERTY_ITEM_VENDA_PRODUTO_STRING = "itemVendaProdutoString";
	public static final String PROPERTY_ITEM_VENDA_CUPOM_VENDA_ID = "itemVendaCupomVendaId";
	public static final String PROPERTY_ITEM_VENDA_CUPOM_VENDA_STRING = "itemVendaCupomVendaString";

	public ItemVendaDTO() {
		super();
		this.setDescontoValue(0.0);
		this.setDescontoString("0.00");
		this.setPrecoUnitarioValue(0.0);
		this.setPrecoUnitarioString("0.00");
		this.setQuantidadeValue(0.0);
		this.setQuantidadeString("0.00");
		this.setSubtotalValue(0.0);
		this.setSubtotalString("0.00");
		this.setPrecoFinalValue(0.0);
		this.setPrecoFinalString("0.00");
	}

	public void setId(Long value) {
		this.set(PROPERTY_ITEM_VENDA_ID, value);
	}

	public Long getId() {
		return this.get(PROPERTY_ITEM_VENDA_ID);
	}

	public void setVendaId(Long value) {
		this.set(PROPERTY_ITEM_VENDA_VENDA_ID, value);
	}

	public Long getVendaId() {
		return this.get(PROPERTY_ITEM_VENDA_VENDA_ID);
	}

	public void setClienteId(Long value) {
		this.set(PROPERTY_ITEM_VENDA_CLIENTE_ID, value);
	}

	public Long getClienteId() {
		return this.get(PROPERTY_ITEM_VENDA_CLIENTE_ID);
	}

	public String getClienteString() {
		return this.get(PROPERTY_ITEM_VENDA_CLIENTE_STRING);
	}

	public void setClienteString(String value) {
		this.set(PROPERTY_ITEM_VENDA_CLIENTE_STRING, value);
	}

	public Long getClienteCpfValue() {
		return this.get(PROPERTY_ITEM_VENDA_CLIENTE_CPF_VALUE);
	}

	public void setClienteCpfValue(Long value) {
		this.set(PROPERTY_ITEM_VENDA_CLIENTE_CPF_VALUE, value);
	}

	public String getClienteCpfString() {
		return this.get(PROPERTY_ITEM_VENDA_CLIENTE_CPF_STRING);
	}

	public void setClienteCpfString(String value) {
		this.set(PROPERTY_ITEM_VENDA_CLIENTE_CPF_STRING, value);
	}

	public void setPrecoUnitarioValue(Double value) {
		this.set(PROPERTY_ITEM_VENDA_PRECO_UNITARIO_VALUE, value);
	}

	public Double getPrecoUnitarioValue() {
		return this.get(PROPERTY_ITEM_VENDA_PRECO_UNITARIO_VALUE);
	}

	public void setPrecoUnitarioString(String value) {
		this.set(PROPERTY_ITEM_VENDA_PRECO_UNITARIO_STRING, value);
	}

	public String getPrecoUnitarioString() {
		return this.get(PROPERTY_ITEM_VENDA_PRECO_UNITARIO_STRING);
	}

	public void setPrecoFinalValue(Double value) {
		this.set(PROPERTY_ITEM_VENDA_PRECO_FINAL_VALUE, value);
	}

	public Double getPrecoFinalValue() {
		return this.get(PROPERTY_ITEM_VENDA_PRECO_FINAL_VALUE);
	}

	public void setPrecoFinalString(String value) {
		this.set(PROPERTY_ITEM_VENDA_PRECO_FINAL_STRING, value);
	}

	public String getPrecoFinalString() {
		return this.get(PROPERTY_ITEM_VENDA_PRECO_FINAL_STRING);
	}

	public void setDescontoValue(Double value) {
		this.set(PROPERTY_ITEM_VENDA_DESCONTO_ITEM_VALUE, value);
	}

	public Double getDescontoValue() {
		return this.get(PROPERTY_ITEM_VENDA_DESCONTO_ITEM_VALUE);
	}

	public void setDescontoString(String value) {
		this.set(PROPERTY_ITEM_VENDA_DESCONTO_ITEM_STRING, value);
	}

	public String getDescontoString() {
		return this.get(PROPERTY_ITEM_VENDA_DESCONTO_ITEM_STRING);
	}

	public Double getQuantidadeValue() {
		return this.get(PROPERTY_ITEM_VENDA_QUANTIDADE_VALUE);
	}

	public void setQuantidadeValue(Double value) {
		this.set(PROPERTY_ITEM_VENDA_QUANTIDADE_VALUE, value);
	}

	public String getQuantidadeString() {
		return this.get(PROPERTY_ITEM_VENDA_QUANTIDADE_STRING);
	}

	public void setQuantidadeString(String value) {
		this.set(PROPERTY_ITEM_VENDA_QUANTIDADE_STRING, value);
	}

	public void setProdutoId(Long value) {
		this.set(PROPERTY_ITEM_VENDA_PRODUTO_ID, value);
	}

	public Long getProdutoId() {
		return this.get(PROPERTY_ITEM_VENDA_PRODUTO_ID);
	}

	public String getProdutoString() {
		return this.get(PROPERTY_ITEM_VENDA_PRODUTO_STRING);
	}

	public void setProdutoString(String value) {
		this.set(PROPERTY_ITEM_VENDA_PRODUTO_STRING, value);
	}

	public void setCupomVendaId(Long value) {
		this.set(PROPERTY_ITEM_VENDA_CUPOM_VENDA_ID, value);
	}

	public Long getCupomVendaId() {
		return this.get(PROPERTY_ITEM_VENDA_CUPOM_VENDA_ID);
	}

	public void setCupomVendaString(String value) {
		this.set(PROPERTY_ITEM_VENDA_CUPOM_VENDA_STRING, value);
	}

	public String getCupomVendaString() {
		return this.get(PROPERTY_ITEM_VENDA_CUPOM_VENDA_STRING);
	}

	public void setSubtotalValue(Double value) {
		this.set(PROPERTY_ITEM_VENDA_SUBTOTAL_VALUE, value);
	}

	public Double getSubtotalValue() {
		return this.get(PROPERTY_ITEM_VENDA_SUBTOTAL_VALUE);
	}

	public void setSubtotalString(String value) {
		this.set(PROPERTY_ITEM_VENDA_SUBTOTAL_STRING, value);
	}

	public String getSubtotalString() {
		return this.get(PROPERTY_ITEM_VENDA_SUBTOTAL_STRING);
	}

	@Override
	public String getNaturalKey() {
		return this.getId() + "-" + this.getVendaId();
	}

	@Override
	public int compareTo(ItemVendaDTO o) {
		int result = this.getId().compareTo(o.getId());
		return result;
	}
}
