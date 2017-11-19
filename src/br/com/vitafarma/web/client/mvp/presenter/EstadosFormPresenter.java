package br.com.vitafarma.web.client.mvp.presenter;

import br.com.vitafarma.web.client.EstadosServiceAsync;
import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.shared.dtos.EstadoDTO;
import br.com.vitafarma.web.shared.i18n.IVitafarmaI18nGateway;
import br.com.vitafarma.web.shared.mvp.presenter.Presenter;
import br.com.vitafarma.web.shared.util.VitafarmaUtil;
import br.com.vitafarma.web.shared.util.view.AbstractAsyncCallbackWithDefaultOnFailure;
import br.com.vitafarma.web.shared.util.view.SimpleGrid;
import br.com.vitafarma.web.shared.util.view.SimpleModal;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.user.client.ui.Widget;

public class EstadosFormPresenter implements Presenter {
	public interface Display extends IVitafarmaI18nGateway {
		Button getSalvarButton();

		EstadoDTO getEstadoDTO();

		TextField<String> getNomeTextField();

		TextField<String> getSiglaTextField();

		boolean isValid();

		SimpleModal getSimpleModal();
	}

	private SimpleGrid<EstadoDTO> gridPanel = null;
	private Display display = null;

	public EstadosFormPresenter(Display display, SimpleGrid<EstadoDTO> gridPanel) {
		this.gridPanel = gridPanel;
		this.display = display;

		this.setListeners();
	}

	public EstadosFormPresenter(Display display) {
		this(display, null);
	}

	private void setListeners() {
		this.display.getSalvarButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (isValid()) {
					final EstadosServiceAsync service = Services.estados();

					service.saveEstado(getDTO(), new AbstractAsyncCallbackWithDefaultOnFailure<Boolean>(display) {
						@Override
						public void onSuccess(Boolean result) {
							display.getSimpleModal().hide();

							if (gridPanel != null) {
								gridPanel.updateList();
							}

							if (result) {
								Info.display("Salvo", "Estado salvo com sucesso!");
							} else {
								Info.display("Erro", "Erro ao salvar o estado!");
							}
						}
					});
				} else {
					MessageBox.alert("ERRO!", "Verifique os campos digitados", null);
				}
			}
		});
	}

	private boolean isValid() {
		return this.display.isValid();
	}

	private EstadoDTO getDTO() {
		EstadoDTO estadoDTO = this.display.getEstadoDTO();

		estadoDTO.setNome(this.display.getNomeTextField().getValue());
		estadoDTO.setSigla(this.display.getSiglaTextField().getValue());

		return estadoDTO;
	}

	@Override
	public void go(Widget widget) {
		VitafarmaUtil.log("###ESTADOS GO###");

		this.display.getSimpleModal().show();
	}
}
