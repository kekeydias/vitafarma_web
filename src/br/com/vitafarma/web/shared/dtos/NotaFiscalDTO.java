package br.com.vitafarma.web.shared.dtos;

import java.util.Date;

public class NotaFiscalDTO extends AbstractDTO<String> implements
		Comparable<NotaFiscalDTO> {
	private static final long serialVersionUID = -5134820110949139907L;

	// Propriedades
	public static final String PROPERTY_NOTA_FISCAL_ID = "notaFiscalId";
	public static final String PROPERTY_NOTA_FISCAL_CODIGO = "notaFiscalCodigo";
	public static final String PROPERTY_NOTA_FISCAL_DATA = "notaFiscalData";
	public static final String PROPERTY_NOTA_FISCAL_FORNECEDOR_ID = "notaFiscalFornecedorId";
	public static final String PROPERTY_NOTA_FISCAL_FORNECEDOR_STRING = "notaFiscalFornecedorString";

	public NotaFiscalDTO() {
		super();
	}

	public void setId(Long value) {
		this.set(PROPERTY_NOTA_FISCAL_ID, value);
	}

	public Long getId() {
		return this.get(PROPERTY_NOTA_FISCAL_ID);
	}

	public void setCodigo(String value) {
		this.set(PROPERTY_NOTA_FISCAL_CODIGO, value);
	}

	public String getCodigo() {
		return this.get(PROPERTY_NOTA_FISCAL_CODIGO);
	}

	public void setData(Date value) {
		this.set(PROPERTY_NOTA_FISCAL_DATA, value);
	}

	public Date getData() {
		return this.get(PROPERTY_NOTA_FISCAL_DATA);
	}

	public void setFornecedorId(Long value) {
		this.set(PROPERTY_NOTA_FISCAL_FORNECEDOR_ID, value);
	}

	public Long getFornecedorId() {
		return this.get(PROPERTY_NOTA_FISCAL_FORNECEDOR_ID);
	}

	public void setFornecedorString(String value) {
		this.set(PROPERTY_NOTA_FISCAL_FORNECEDOR_STRING, value);
	}

	public String getFornecedorString() {
		return this.get(PROPERTY_NOTA_FISCAL_FORNECEDOR_STRING);
	}

	@Override
	public String getNaturalKey() {
		return (this.getId() + "-" + this.getCodigo() + "-" + this.getData());
	}

	@Override
	public int compareTo(NotaFiscalDTO o) {
		int result = this.getCodigo().compareTo(o.getCodigo());

		if (result == 0) {
			result = this.getData().compareTo(o.getData());
		}

		return result;
	}
}
