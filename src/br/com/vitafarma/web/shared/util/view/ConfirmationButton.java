package br.com.vitafarma.web.shared.util.view;

import java.util.ArrayList;

import br.com.vitafarma.web.shared.i18n.IVitafarmaI18nGateway;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;

public class ConfirmationButton extends Button {

	private ArrayList<SelectionListener<ButtonEvent>> listeners = new ArrayList<SelectionListener<ButtonEvent>>();
	private IVitafarmaI18nGateway i18nGateway;

	public ConfirmationButton(IVitafarmaI18nGateway i18nGateway) {
		this.addConfirmationListener();
		this.i18nGateway = i18nGateway;
	}

	public void addConfirmationListener() {
		super.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(final ButtonEvent ce) {
				MessageBox.confirm(i18nGateway.getI18nMessages().confirmacao(),
						i18nGateway.getI18nMessages()
								.confirmacaoRemocaoButton(),
						new Listener<MessageBoxEvent>() {
							@Override
							public void handleEvent(MessageBoxEvent be) {
								if (be.getButtonClicked().getText()
										.equalsIgnoreCase("yes")) {
									for (SelectionListener<ButtonEvent> listener : listeners) {
										listener.componentSelected(ce);
									}
								}
							}
						});
			}
		});
	}

	@Override
	public void addSelectionListener(SelectionListener<ButtonEvent> listener) {
		this.listeners.add(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener<ButtonEvent> listener) {
		this.listeners.remove(listener);
	}

}
