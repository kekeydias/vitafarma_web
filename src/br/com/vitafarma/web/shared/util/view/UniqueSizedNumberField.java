package br.com.vitafarma.web.shared.util.view;

import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.shared.i18n.IVitafarmaI18nGateway;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class UniqueSizedNumberField extends SizedNumberField {
	private UniqueDomain uniqueDomain;
	private boolean checking = false;
	private boolean error = false;
	private final String uniqueInvalidMessage;

	public UniqueSizedNumberField(IVitafarmaI18nGateway display,
			UniqueDomain uniqueDomain, String errorMessage) {
		this(display, 0, Integer.MAX_VALUE, uniqueDomain, errorMessage);
	}

	public UniqueSizedNumberField(IVitafarmaI18nGateway display, int min,
			int max, UniqueDomain uniqueDomain, String errorMessage) {
		super(display, min, max);
		this.uniqueDomain = uniqueDomain;
		this.uniqueInvalidMessage = errorMessage;
		this.addListeners();
	}

	private void addListeners() {
		this.setValidator(new Validator() {
			@Override
			public String validate(Field<?> field, String value) {
				return (error ? uniqueInvalidMessage : null);
			}
		});

		this.addListener(Events.Blur, new Listener<FieldEvent>() {
			@Override
			public void handleEvent(FieldEvent be) {
				check();
			}
		});
	}

	private void check() {
		if (this.getValue() == null) {
			return;
		}

		if (this.getOriginalValue() != null && this.getValue() != null
				&& this.getOriginalValue().equals(this.getValue())) {
			return;
		}

		this.checking = true;
		this.setReadOnly(true);

		Services.uniques().domainKeyIsUnique(this.getValue().toString(),
				this.uniqueDomain.toString(), new AsyncCallback<Boolean>() {
					@Override
					public void onSuccess(Boolean isUnique) {
						error = !isUnique;
						if (error) {
							markInvalid(uniqueInvalidMessage);
						} else {
							clearInvalid();
						}

						isValid(!error);
						checking = false;
						setReadOnly(false);
					}

					@Override
					public void onFailure(Throwable caught) {
						error = true;
						isValid(false);
						checking = false;
						setReadOnly(false);
					}
				});
	}

	@Override
	public boolean isValid() {
		return !this.error;
	}

	public boolean isChecking() {
		return this.checking;
	}
}
