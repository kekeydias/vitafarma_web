package br.com.vitafarma.web.shared.util.view;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.event.FormEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.MessageBox;

import br.com.vitafarma.web.shared.dtos.AbstractDTO;
import br.com.vitafarma.web.shared.excel.ExcelInformationType;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nConstants;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nMessages;
import br.com.vitafarma.web.shared.mvp.presenter.ImportExcelMessagesPresenter;
import br.com.vitafarma.web.shared.mvp.presenter.Presenter;
import br.com.vitafarma.web.shared.mvp.view.ImportExcelMessagesView;
import br.com.vitafarma.web.shared.util.VitafarmaUtil;

public class ExcelFormListener implements Listener<FormEvent> {
	private SimpleModal modalToBeHidden = null;
	private SimpleGrid<? extends AbstractDTO<?>> gridToBeUpdated = null;
	private VitafarmaI18nConstants i18nConstants = null;
	private VitafarmaI18nMessages i18nMessages = null;
	private ExcelOperation operation = null;

	public ExcelFormListener(SimpleModal modalToBeHidden, SimpleGrid<? extends AbstractDTO<?>> gridToBeUpdated,
			VitafarmaI18nConstants i18nConstants, VitafarmaI18nMessages i18nMessages, ExcelOperation op) {
		this.modalToBeHidden = modalToBeHidden;
		this.gridToBeUpdated = gridToBeUpdated;
		this.i18nConstants = i18nConstants;
		this.i18nMessages = i18nMessages;
		this.operation = op;
	}

	public ExcelFormListener(VitafarmaI18nConstants i18nConstants, VitafarmaI18nMessages i18nMessages,
			ExcelOperation op) {
		this(null, null, i18nConstants, i18nMessages, op);
	}

	@Override
	public void handleEvent(FormEvent be) {
		if (this.modalToBeHidden != null) {
			this.modalToBeHidden.hide();
		}

		String[] resSplit = be.getResultHtml().split("[<>\n]");

		List<String> warnings = new ArrayList<String>();
		List<String> errors = new ArrayList<String>();

		if (resSplit != null) {
			for (String str : resSplit) {
				String newStr = str.trim();

				if (!VitafarmaUtil.vazio(newStr)) {
					if (newStr.startsWith(ExcelInformationType.prefixWarning())) {
						warnings.add(newStr.substring(ExcelInformationType.prefixWarning().length()));
					} else if (newStr.startsWith(ExcelInformationType.prefixError())) {
						errors.add(newStr.substring(ExcelInformationType.prefixError().length()));
					}
				}
			}
		}

		Boolean checkMessages = this.containsErrorsWarnings(warnings, errors);

		if (checkMessages) {
			Presenter presenter = new ImportExcelMessagesPresenter(warnings, errors, new ImportExcelMessagesView());

			presenter.go(null);
		} else {
			if (this.gridToBeUpdated != null) {
				this.gridToBeUpdated.updateList();
			}

			MessageBox.info(this.i18nConstants.informacao(), this.getMessageSuccess(), null);
		}
	}

	private Boolean containsErrorsWarnings(List<String> warnings, List<String> errors) {
		if (warnings.isEmpty() && errors.isEmpty()) {
			return false;
		}

		for (String error : errors) {
			if (!error.equals("")) {
				return true;
			}
		}

		for (String warning : warnings) {
			if (!warning.equals("")) {
				return true;
			}
		}

		return false;
	}

	private String getMessageSuccess() {
		switch (this.operation) {
		case IMPORT: {
			return this.i18nMessages.sucessoImportacaoExcel();
		}
		case EXPORT: {
			return this.i18nMessages.sucessoExportacaoExcel();
		}
		}

		return this.i18nMessages.sucessoOperacaoExcel();
	}
}
