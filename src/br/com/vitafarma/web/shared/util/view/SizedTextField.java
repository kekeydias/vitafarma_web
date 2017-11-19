package br.com.vitafarma.web.shared.util.view;

import br.com.vitafarma.web.shared.i18n.IVitafarmaI18nGateway;

import com.extjs.gxt.ui.client.widget.form.TextField;

public class SizedTextField extends TextField<String> implements SizedField {
	private int min;
	private int max;
	private IVitafarmaI18nGateway display;

	// Avoid instantiate without size
	private SizedTextField() {
		super();
	}

	public SizedTextField(IVitafarmaI18nGateway display, int min, int max) {
		this();
		this.min = min;
		this.max = max;
		this.display = display;
		this.initMessages();
		this.initSize();
	}

	@Override
	public void initSize() {
		this.setMinLength(this.min);
		this.setMaxLength(this.max);
	}

	@Override
	public void initMessages() {
		this.getMessages().setMinLengthText(
				this.display.getI18nMessages().fixedSizeTextFieldMessage(
						this.min));
		this.getMessages().setMaxLengthText(
				this.display.getI18nMessages().fixedSizeTextFieldMessage(
						this.max));
	}

	@Override
	public IVitafarmaI18nGateway getDisplay() {
		return this.display;
	}
}
