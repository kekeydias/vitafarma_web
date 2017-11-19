package br.com.vitafarma.web.shared.util;

import com.google.gwt.user.client.rpc.IsSerializable;

/*
 * Observacao : Ao usar essa classe em objetos do tipo 'DTO',
 * nao se pode setar diretamente o objeto com o metodo 'set'
 * da classe 'BaseModel', mas sim setar o string correspondente
 * ao objeto 'VitafarmaCurrency' ( utilizando o metodo toString() )
 * */
public class VitafarmaCurrency implements IsSerializable {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = -4919776123579824061L;
	private Double value = null;

	public VitafarmaCurrency() {
		this.setValue(0.0);
	}

	public VitafarmaCurrency(Double value) {
		this.setValue(value);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof VitafarmaCurrency)) {
			return false;
		}

		VitafarmaCurrency tc = (VitafarmaCurrency) obj;
		return this.value.equals(tc.value);
	}

	@Override
	public String toString() {
		Integer longValue = ((Double) (this.value * 100)).intValue();

		// Casas decimais
		Integer resto = (longValue % 100);
		String str1 = (resto == 0 ? "00" : ((resto >= 10) ? resto.toString() : "0" + resto.toString()));

		// Parte inteira do número
		longValue = this.value.intValue();
		String str2 = longValue.toString();

		String result = (str2 + "." + str1);
		return result;
	}

	public Double getDoubleValue() {
		return VitafarmaUtil.roundTwoDecimals(this.value);
	}

	public void setDoubleValue(Double v) {
		this.setValue(v);
	}

	private void setValue(Double v) {
		this.value = (v == null ? 0.0 : v);
	}
}
