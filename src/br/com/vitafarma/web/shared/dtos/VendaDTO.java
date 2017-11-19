package br.com.vitafarma.web.shared.dtos;

import java.util.Date;

public class VendaDTO extends AbstractDTO<String> implements
		Comparable<VendaDTO> {
	private static final long serialVersionUID = -5134820110949139907L;

	// Propriedades
	public static final String PROPERTY_VENDA_ID = "vendaId";
	public static final String PROPERTY_VENDA_DATA_VALUE = "vendaDataValue";
	public static final String PROPERTY_VENDA_DATA_STRING = "vendaDataString";
	public static final String PROPERTY_VENDA_HORA_MINUTO_STRING = "vendaHoraMinutoString";
	public static final String PROPERTY_VENDA_HORA = "vendaHora";
	public static final String PROPERTY_VENDA_MINUTO = "vendaMinuto";
	public static final String PROPERTY_VENDA_DESCONTO_VALUE = "vendaDesconto";
	public static final String PROPERTY_VENDA_DESCONTO_STRING = "vendaDescontoString";
	public static final String PROPERTY_VENDA_SUBTOTAL_VALUE = "vendaSubtotalValue";
	public static final String PROPERTY_VENDA_SUBTOTAL_STRING = "vendaSubtotalString";
	public static final String PROPERTY_VENDA_VALOR_FINAL_VALUE = "vendaValorFinalValue";
	public static final String PROPERTY_VENDA_VALOR_FINAL_STRING = "vendaValorFinalString";
	public static final String PROPERTY_VENDA_CLIENTE_ID = "vendaClienteId";
	public static final String PROPERTY_VENDA_CLIENTE_NOME = "vendaClienteNome";
	public static final String PROPERTY_VENDA_CLIENTE_CPF_VALUE = "vendaClienteCpfValue";
	public static final String PROPERTY_VENDA_CLIENTE_CPF_STRING = "vendaClienteCpfString";
	public static final String PROPERTY_VENDA_CUPOM_VENDA_ID = "vendaCupomVendaId";
	public static final String PROPERTY_VENDA_CUPOM_VENDA_STRING = "vendaCupomVendaString";
	public static final String PROPERTY_VENDA_CENARIO_ID = "vendaCenarioId";

	public VendaDTO() {
		super();
		this.setDescontoValue(0.0);
		this.setDescontoString("0.00");
		this.setSubtotalValue(0.0);
		this.setSubtotalString("0.00");
		this.setValorFinalValue(0.0);
		this.setValorFinalString("0.00");
	}

	public void setVendaId(Long value) {
		this.set(PROPERTY_VENDA_ID, value);
	}

	public Long getVendaId() {
		return this.get(PROPERTY_VENDA_ID);
	}

	public void setVendaDataValue(Date value) {
		this.set(PROPERTY_VENDA_DATA_VALUE, value);
	}

	public Date getVendaDataValue() {
		return this.get(PROPERTY_VENDA_DATA_VALUE);
	}

	public void setVendaDataString(String value) {
		this.set(PROPERTY_VENDA_DATA_STRING, value);
	}

	public String getVendaDataString() {
		return this.get(PROPERTY_VENDA_DATA_STRING);
	}

	public void setDescontoValue(Double value) {
		this.set(PROPERTY_VENDA_DESCONTO_VALUE, value);
	}

	public Double getDescontoValue() {
		return this.get(PROPERTY_VENDA_DESCONTO_VALUE);
	}

	public void setValorFinalValue(Double value) {
		this.set(PROPERTY_VENDA_VALOR_FINAL_VALUE, value);
	}

	public Double getValorFinalValue() {
		return this.get(PROPERTY_VENDA_VALOR_FINAL_VALUE);
	}

	public void setValorFinalString(String value) {
		this.set(PROPERTY_VENDA_VALOR_FINAL_STRING, value);
	}

	public String getValorFinalString() {
		return this.get(PROPERTY_VENDA_VALOR_FINAL_STRING);
	}

	public void setSubtotalValue(Double value) {
		this.set(PROPERTY_VENDA_SUBTOTAL_VALUE, value);
	}

	public Double getSubtotalValue() {
		return this.get(PROPERTY_VENDA_SUBTOTAL_VALUE);
	}

	public void setSubtotalString(String value) {
		this.set(PROPERTY_VENDA_SUBTOTAL_STRING, value);
	}

	public String getSubtotalString() {
		return this.get(PROPERTY_VENDA_SUBTOTAL_STRING);
	}

	public void setDescontoString(String value) {
		this.set(PROPERTY_VENDA_DESCONTO_STRING, value);
	}

	public String getDescontoString() {
		return this.get(PROPERTY_VENDA_DESCONTO_STRING);
	}

	public void setClienteId(Long value) {
		this.set(PROPERTY_VENDA_CLIENTE_ID, value);
	}

	public Long getClienteId() {
		return this.get(PROPERTY_VENDA_CLIENTE_ID);
	}

	public void setClienteCpfValue(Long value) {
		this.set(PROPERTY_VENDA_CLIENTE_CPF_VALUE, value);
	}

	public Long getClienteCpfValue() {
		return this.get(PROPERTY_VENDA_CLIENTE_CPF_VALUE);
	}

	public void setClienteCpfString(String value) {
		this.set(PROPERTY_VENDA_CLIENTE_CPF_STRING, value);
	}

	public String getClienteCpfString() {
		return this.get(PROPERTY_VENDA_CLIENTE_CPF_STRING);
	}

	public void setClienteNome(String value) {
		this.set(PROPERTY_VENDA_CLIENTE_NOME, value);
	}

	public String getClienteNome() {
		return this.get(PROPERTY_VENDA_CLIENTE_NOME);
	}

	public void setCupomVendaId(Long value) {
		this.set(PROPERTY_VENDA_CUPOM_VENDA_ID, value);
	}

	public Long getCupomVendaId() {
		return this.get(PROPERTY_VENDA_CUPOM_VENDA_ID);
	}

	public void setCupomVendaString(String value) {
		this.set(PROPERTY_VENDA_CUPOM_VENDA_STRING, value);
	}

	public String getCupomVendaString() {
		return this.get(PROPERTY_VENDA_CUPOM_VENDA_STRING);
	}

	public void setCenarioId(Long value) {
		this.set(PROPERTY_VENDA_CENARIO_ID, value);
	}

	public Long getCenarioId() {
		return this.get(PROPERTY_VENDA_CENARIO_ID);
	}

	public void setVendaHora(Integer value) {
		this.set(PROPERTY_VENDA_HORA, value);
	}

	public Integer getVendaHora() {
		return this.get(PROPERTY_VENDA_HORA);
	}

	public void setVendaMinuto(Integer value) {
		this.set(PROPERTY_VENDA_MINUTO, value);
	}

	public Integer getVendaMinuto() {
		return this.get(PROPERTY_VENDA_MINUTO);
	}

	public void setVendaHoraMinutoString(String value) {
		this.set(PROPERTY_VENDA_HORA_MINUTO_STRING, value);
	}

	public String getVendaHoraMinutoString() {
		return this.get(PROPERTY_VENDA_HORA_MINUTO_STRING);
	}

	@Override
	public String getNaturalKey() {
		return (this.getVendaId() == null ? "" : this.getVendaId().toString());
	}

	@Override
	public int compareTo(VendaDTO o) {
		int result = this.getNaturalKey().compareTo(o.getNaturalKey());
		return result;
	}
}
