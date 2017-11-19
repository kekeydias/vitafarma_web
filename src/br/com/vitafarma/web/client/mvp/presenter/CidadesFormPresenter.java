package br.com.vitafarma.web.client.mvp.presenter;

import br.com.vitafarma.web.client.CidadesServiceAsync;
import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.shared.dtos.CidadeDTO;
import br.com.vitafarma.web.shared.i18n.IVitafarmaI18nGateway;
import br.com.vitafarma.web.shared.mvp.presenter.Presenter;
import br.com.vitafarma.web.shared.util.VitafarmaUtil;
import br.com.vitafarma.web.shared.util.view.AbstractAsyncCallbackWithDefaultOnFailure;
import br.com.vitafarma.web.shared.util.view.EstadosComboBox;
import br.com.vitafarma.web.shared.util.view.SimpleGrid;
import br.com.vitafarma.web.shared.util.view.SimpleModal;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.user.client.ui.Widget;

public class CidadesFormPresenter implements Presenter {
	public interface Display extends IVitafarmaI18nGateway {
		Button getSalvarButton();

		CidadeDTO getCidadeDTO();

		EstadosComboBox getEstadosComboBox();

		TextField<String> getNomeTextField();

		boolean isValid();

		SimpleModal getSimpleModal();
	}

	private SimpleGrid<CidadeDTO> gridPanel = null;
	private Display display = null;

	public CidadesFormPresenter(Display display, SimpleGrid<CidadeDTO> gridPanel) {
		this.gridPanel = gridPanel;
		this.display = display;

		this.setListeners();
	}

	public CidadesFormPresenter(Display display) {
		this(display, null);
	}

	private void setListeners() {
		this.display.getSalvarButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (isValid()) {
					final CidadesServiceAsync service = Services.cidades();

					service.saveCidade(getDTO(), new AbstractAsyncCallbackWithDefaultOnFailure<Boolean>(display) {
						@Override
						public void onSuccess(Boolean result) {
							display.getSimpleModal().hide();

							if (gridPanel != null) {
								gridPanel.updateList();
							}

							if (result) {
								Info.display("Salvo", "Cidade salva com sucesso!");
							} else {
								Info.display("Erro", "Erro ao salvar a cidade!");
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

	private CidadeDTO getDTO() {
		CidadeDTO cidadeDTO = this.display.getCidadeDTO();

		cidadeDTO.setNome(this.display.getNomeTextField().getValue());
		cidadeDTO.setEstadoId(this.display.getEstadosComboBox().getValue() == null ? null
				: this.display.getEstadosComboBox().getValue().getId());

		return cidadeDTO;
	}

	@Override
	public void go(Widget widget) {
		VitafarmaUtil.log("###CIDADES GO###");
		this.display.getSimpleModal().show();
	}
}
