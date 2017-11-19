package br.com.vitafarma.web.shared.util.view;

import br.com.vitafarma.web.shared.i18n.IVitafarmaI18nGateway;

public interface SizedField {
	void initMessages();

	void initSize();

	IVitafarmaI18nGateway getDisplay();
}
