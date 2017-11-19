package br.com.vitafarma.web.shared.util.view;

import br.com.vitafarma.web.shared.i18n.IVitafarmaI18nGateway;

import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class AbstractAsyncCallbackWithDefaultOnFailure<T> implements
		AsyncCallback<T> {
	private IVitafarmaI18nGateway gateway;
	private String errorMessage;

	public AbstractAsyncCallbackWithDefaultOnFailure(
			IVitafarmaI18nGateway gateway) {
		this(gateway.getI18nMessages().falhaOperacao(), gateway);
	}

	public AbstractAsyncCallbackWithDefaultOnFailure(String errorMessage,
			IVitafarmaI18nGateway gateway) {
		this.gateway = gateway;
		this.errorMessage = errorMessage;
	}

	@Override
	public void onFailure(Throwable caught) {
		if (caught != null) {
			VitafarmaDetailMessageBox.alert(this.gateway.getI18nMessages()
					.mensagemErro(), this.errorMessage, caught);
		} else {
			MessageBox.alert(this.gateway.getI18nMessages().mensagemErro(),
					this.errorMessage, null);
		}
	}
}
