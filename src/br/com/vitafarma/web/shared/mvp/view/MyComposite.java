package br.com.vitafarma.web.shared.mvp.view;

import java.util.Iterator;

import br.com.vitafarma.web.shared.i18n.IVitafarmaI18nGateway;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nConstants;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nMessages;

import com.extjs.gxt.ui.client.widget.Composite;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class MyComposite extends Composite implements HasWidgets,
		IVitafarmaI18nGateway {
	private VitafarmaI18nConstants i18nConstants;
	private VitafarmaI18nMessages i18nMessages;

	public MyComposite() {
		i18nConstants = GWT.create(VitafarmaI18nConstants.class);
		i18nMessages = GWT.create(VitafarmaI18nMessages.class);
	}

	@Override
	public VitafarmaI18nConstants getI18nConstants() {
		return this.i18nConstants;
	}

	@Override
	public VitafarmaI18nMessages getI18nMessages() {
		return this.i18nMessages;
	}

	@Override
	public void add(Widget w) {

	}

	@Override
	public void clear() {

	}

	@Override
	public Iterator<Widget> iterator() {
		return null;
	}

	@Override
	public boolean remove(Widget w) {
		return false;
	}
}
