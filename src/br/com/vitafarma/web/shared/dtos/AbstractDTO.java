package br.com.vitafarma.web.shared.dtos;

import com.extjs.gxt.ui.client.data.BaseModel;

public abstract class AbstractDTO<NKType> extends BaseModel {
	private static final long serialVersionUID = 2957871960531183286L;

	// Propriedades
	public static final String PROPERTY_DISPLAY_TEXT = "abstractDtoDisplayText";
	public static final String PROPERTY_EMPRESA_ID = "abstractDtoEmpresaId";
	public static final String PROPERTY_EMPRESA_STRING = "abstractDtoEmpresaString";

	public abstract NKType getNaturalKey();

	public void setDisplayText(String value) {
		this.set(PROPERTY_DISPLAY_TEXT, value);
	}

	public String getDisplayText() {
		return this.get(PROPERTY_DISPLAY_TEXT);
	}

	final public void setEmpresaId(Long value) {
		this.set(PROPERTY_EMPRESA_ID, value);
	}

	final public Long getEmpresaId() {
		return this.get(PROPERTY_EMPRESA_ID);
	}

	final public void setEmpresaString(String value) {
		this.set(PROPERTY_EMPRESA_STRING, value);
	}

	final public String getEmpresaString() {
		return this.get(PROPERTY_EMPRESA_STRING);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = (prime * result + ((this.getNaturalKey() == null) ? 0
				: this.getNaturalKey().hashCode()));

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (this.getClass() != obj.getClass()) {
			return false;
		}

		@SuppressWarnings("unchecked")
		AbstractDTO<NKType> other = (AbstractDTO<NKType>) obj;

		if (this.getNaturalKey() == null) {
			if (other.getNaturalKey() != null) {
				return false;
			}
		} else if (!this.getNaturalKey().equals(other.getNaturalKey())) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return this.getDisplayText();
	}
}