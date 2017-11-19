package br.com.vitafarma.web.client;

import br.com.vitafarma.web.client.mvp.presenter.AppPresenter;
import br.com.vitafarma.web.client.mvp.view.AppView;
import br.com.vitafarma.web.shared.mvp.presenter.Presenter;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class Vitafarma implements EntryPoint {
	@Override
	public void onModuleLoad() {
		Presenter appPresenter = new AppPresenter(new AppView());

		appPresenter.go(RootPanel.get());
	}
}
