package br.com.vitafarma.web.shared.util.view;

import java.util.ArrayList;
import java.util.List;

import br.com.vitafarma.web.client.Services;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class UniqueTextField extends TextField<String> {
	private UniqueDomain uniqueDomain;
	private boolean checking = false;
	private boolean error = false;
	private final String uniqueInvalidMessage;
	private List<Long> idsDomain;

	public UniqueTextField(UniqueDomain uniqueDomain, String errorMessage,
			List<Long> idsDomain) {
		this(uniqueDomain, errorMessage);
		this.idsDomain = new ArrayList<Long>();
		this.idsDomain.addAll(idsDomain);
	}

	public UniqueTextField(UniqueDomain uniqueDomain, String errorMessage) {
		super();
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
				if (idsDomain == null) {
					check();
				} else {
					checkWithDomains();
				}
			}
		});
	}

	private void checkWithDomains() {
		if (this.getOriginalValue() != null && this.getValue() != null
				&& this.getOriginalValue().equals(this.getValue())) {
			return;
		}

		this.checking = true;
		this.setReadOnly(true);

		Services.uniques().domainKeyIsUnique(this.getValue(),
				this.uniqueDomain.toString(), this.idsDomain,
				new AsyncCallback<Boolean>() {
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

	private void check() {
		if (this.getOriginalValue() != null && this.getValue() != null
				&& this.getOriginalValue().equals(this.getValue())) {
			return;
		}

		this.checking = true;
		this.setReadOnly(true);

		Services.uniques().domainKeyIsUnique(this.getValue(),
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
