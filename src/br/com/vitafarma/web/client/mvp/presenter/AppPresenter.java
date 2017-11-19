package br.com.vitafarma.web.client.mvp.presenter;

import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.client.UsuariosServiceAsync;
import br.com.vitafarma.web.client.mvp.view.ToolBarView;
import br.com.vitafarma.web.shared.dtos.CenarioDTO;
import br.com.vitafarma.web.shared.dtos.UsuarioDTO;
import br.com.vitafarma.web.shared.i18n.IVitafarmaI18nGateway;
import br.com.vitafarma.web.shared.mvp.presenter.Presenter;
import br.com.vitafarma.web.shared.util.view.CenarioPanel;
import br.com.vitafarma.web.shared.util.view.GTab;

import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class AppPresenter implements Presenter {
	public interface Display extends IVitafarmaI18nGateway {
		ContentPanel getPanel();

		GTab getGTab();

		Widget asWidget();

		Component getComponent();

		CenarioPanel getCenarioPanel();
	}

	private Display viewport = null;

	public AppPresenter(Display viewport) {
		this.viewport = viewport;
	}

	@Override
	public void go(final Widget widget) {
		RootPanel rp = (RootPanel) widget;

		Presenter presenter = new ToolBarPresenter(new CenarioDTO(), new UsuarioDTO(), this.viewport.getCenarioPanel(),
				new ToolBarView());

		UsuariosServiceAsync service = Services.usuarios();

		service.getCurrentUser(new AsyncCallback<UsuarioDTO>() {
			@Override
			public void onSuccess(UsuarioDTO result) {
				return;
			}

			@Override
			public void onFailure(Throwable caught) {
				Info.display("Erro", "Nao foi possivel conectar-se ao servidor.");
			}
		});

		presenter.go(this.viewport.asWidget());
		rp.add(this.viewport.asWidget());

		RootPanel.get("loading").setVisible(false);
	}
}
