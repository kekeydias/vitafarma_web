package br.com.vitafarma.web.shared.util.view;

import br.com.vitafarma.web.shared.util.resources.Resources;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

public class ImportExcelFormPanel extends FormPanel {
	private Button buttonChange;
	private SimpleModal modalChange;

	public ImportExcelFormPanel(SimpleModal modalChange, Button buttonChange) {
		super();
		this.buttonChange = buttonChange;
		this.modalChange = modalChange;
		this.initAction();
	}

	private void initAction() {
		this.addListener(Events.Submit, new Listener<BaseEvent>() {
			@Override
			public void handleEvent(BaseEvent be) {
				desabilitaBotao();
			}
		});
	}

	private void desabilitaBotao() {
		if (this.buttonChange != null) {
			this.buttonChange.setIcon(AbstractImagePrototype
					.create(Resources.DEFAULTS.ajax16()));
		}

		if (this.buttonChange != null) {
			this.buttonChange.disable();
			this.modalChange.setClosable(false);
		}
	}

	@Override
	public void submit() {
		super.submit();
	}
}
