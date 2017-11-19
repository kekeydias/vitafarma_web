package br.com.vitafarma.web.shared.mvp.presenter;

import java.util.List;

import br.com.vitafarma.web.shared.util.view.SimpleModal;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.google.gwt.user.client.ui.Widget;

public class ImportExcelMessagesPresenter implements Presenter {
	public interface Display {
		ContentPanel getMessagesWarningPanel();

		ContentPanel getMessagesErrorPanel();

		SimpleModal getSimpleModal();
	}

	private List<String> warnings;
	private List<String> errors;
	private Display display;

	public ImportExcelMessagesPresenter(List<String> warnings,
			List<String> errors, Display display) {
		this.display = display;
		this.warnings = warnings;
		this.errors = errors;
		this.populationMessages();
	}

	private void populationMessages() {
		for (String msg : this.warnings) {
			this.display.getMessagesWarningPanel().addText("• " + msg);
		}

		for (String msg : this.errors) {
			this.display.getMessagesErrorPanel().addText("• " + msg);
		}

		this.display.getMessagesWarningPanel().setVisible(
				!this.warnings.isEmpty());
		this.display.getMessagesErrorPanel().setVisible(!this.errors.isEmpty());
	}

	@Override
	public void go(Widget widget) {
		this.display.getSimpleModal().show();
	}
}
