package br.com.vitafarma.web.shared.util.view;

import br.com.vitafarma.web.shared.excel.ExcelInformationType;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nConstants;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nMessages;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.Method;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

public class ExportExcelFormSubmit {
	private FormPanel formPanel = null;

	public ExportExcelFormSubmit(ExcelParametros parametros, VitafarmaI18nConstants i18nConstants,
			VitafarmaI18nMessages i18nMessages) {
		this.formPanel = new FormPanel();
		this.formPanel.setMethod(Method.GET);
		this.formPanel.setAction(GWT.getModuleBaseURL() + "exportExcelServlet");
		this.formPanel.addListener(Events.Submit,
				new ExcelFormListener(i18nConstants, i18nMessages, parametros.getOperation()));

		this.addParameter(ExcelInformationType.getInformationParameterName(), parametros.getInfo().toString());
	}

	public void addParameter(String name, String value) {
		HiddenField<String> hiddenField = new HiddenField<String>();

		hiddenField.setName(name);
		hiddenField.setValue(value);

		this.formPanel.add(hiddenField);
	}

	public void submit() {
		RootPanel.get().add(this.formPanel);

		this.formPanel.submit();
	}
}
