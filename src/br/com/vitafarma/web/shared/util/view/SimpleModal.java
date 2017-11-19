package br.com.vitafarma.web.shared.util.view;

import br.com.vitafarma.web.shared.util.resources.Resources;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Widget;

public class SimpleModal extends Window {
	private Button salvarBt;
	private Button cancelarBt;
	private String textSalvar = "Salvar";
	private String textCancelar = "Cancelar";

	public SimpleModal(String textSalvar, String textCancelar, String heading,
			ImageResource icon) {
		this.textSalvar = textSalvar;
		this.textCancelar = textCancelar;

		this.setHeading(heading);
		this.setIcon(AbstractImagePrototype.create(icon));
		this.configuration();
	}

	public SimpleModal(String heading, ImageResource icon) {
		this.setHeading(heading);
		this.setIcon(AbstractImagePrototype.create(icon));
		this.configuration();
	}

	private void configuration() {
		this.setModal(true);
		this.setLayout(new FitLayout());
		this.setBodyBorder(false);
		this.addButtons();
		this.setResizable(false);
	}

	public void setContent(Widget widget) {
		this.add(widget);
	}

	private void addButtons() {
		if (this.textSalvar != null) {
			this.salvarBt = new Button(this.textSalvar,
					AbstractImagePrototype.create(Resources.DEFAULTS.save16()));

			this.addButton(this.salvarBt);
		}

		if (this.textCancelar != null) {
			this.cancelarBt = new Button(
					this.textCancelar,
					AbstractImagePrototype.create(Resources.DEFAULTS.cancel16()));

			this.cancelarBt
					.addSelectionListener(new SelectionListener<ButtonEvent>() {
						@Override
						public void componentSelected(ButtonEvent ce) {
							hide();
						}
					});

			this.addButton(this.cancelarBt);
		}
	}

	public Button getSalvarBt() {
		return this.salvarBt;
	}

	public Button getCancelarBt() {
		return this.cancelarBt;
	}

	public ToolButton getCloseBt() {
		return this.getCloseBt();
	}
}
