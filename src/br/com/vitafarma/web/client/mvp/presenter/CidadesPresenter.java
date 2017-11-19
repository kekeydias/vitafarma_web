package br.com.vitafarma.web.client.mvp.presenter;

import java.util.List;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

import br.com.vitafarma.web.client.CidadesServiceAsync;
import br.com.vitafarma.web.client.EstadosServiceAsync;
import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.client.mvp.view.CidadesFormView;
import br.com.vitafarma.web.shared.dtos.CidadeDTO;
import br.com.vitafarma.web.shared.dtos.EstadoDTO;
import br.com.vitafarma.web.shared.excel.ExcelInformationType;
import br.com.vitafarma.web.shared.i18n.IVitafarmaI18nGateway;
import br.com.vitafarma.web.shared.mvp.presenter.Presenter;
import br.com.vitafarma.web.shared.util.view.AbstractAsyncCallbackWithDefaultOnFailure;
import br.com.vitafarma.web.shared.util.view.ExcelOperation;
import br.com.vitafarma.web.shared.util.view.ExcelParametros;
import br.com.vitafarma.web.shared.util.view.ExportExcelFormSubmit;
import br.com.vitafarma.web.shared.util.view.GTab;
import br.com.vitafarma.web.shared.util.view.GTabItem;
import br.com.vitafarma.web.shared.util.view.SimpleGrid;

public class CidadesPresenter implements Presenter {
	public interface Display extends IVitafarmaI18nGateway {
		Button getNewButton();

		Button getEditButton();

		Button getRemoveButton();

		TextField<String> getNomeCidadeBuscaTextField();

		TextField<String> getNomeEstadoBuscaTextField();

		Button getSubmitBuscaButton();

		Button getResetBuscaButton();

		Button getExportExcelButton();

		SimpleGrid<CidadeDTO> getGrid();

		Component getComponent();

		void setProxy(RpcProxy<PagingLoadResult<CidadeDTO>> proxy);
	}

	private Display display = null;
	private GTab gTab = null;

	public CidadesPresenter(Display display) {
		this.display = display;

		this.configureProxy();
		this.setListeners();
	}

	private void configureProxy() {
		final CidadesServiceAsync service = Services.cidades();

		RpcProxy<PagingLoadResult<CidadeDTO>> proxy = new RpcProxy<PagingLoadResult<CidadeDTO>>() {
			@Override
			public void load(Object loadConfig, AsyncCallback<PagingLoadResult<CidadeDTO>> callback) {
				String nomeCidade = display.getNomeCidadeBuscaTextField().getValue();
				String nomeEstado = display.getNomeEstadoBuscaTextField().getValue();

				service.getBuscaList(nomeCidade, nomeEstado, (PagingLoadConfig) loadConfig, callback);
			}
		};

		this.display.setProxy(proxy);
	}

	private void setListeners() {
		this.display.getNewButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Presenter presenter = new CidadesFormPresenter(new CidadesFormView(new CidadeDTO()), display.getGrid());

				presenter.go(null);
			}
		});

		this.display.getEditButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				CidadeDTO cidadeDTO = display.getGrid().getGrid().getSelectionModel().getSelectedItem();

				showEditPresenter(cidadeDTO);
			}
		});

		this.display.getRemoveButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				final CidadesServiceAsync service = Services.cidades();

				List<CidadeDTO> list = display.getGrid().getGrid().getSelectionModel().getSelectedItems();

				service.remove(list, new AbstractAsyncCallbackWithDefaultOnFailure<Void>(display) {
					@Override
					public void onFailure(Throwable caught) {
						MessageBox.alert("ERRO!", "Nao foi possivel remover o(s) cidade(s)", null);
					}

					@Override
					public void onSuccess(Void result) {
						display.getGrid().updateList();

						Info.display("Removido", "Item(ns) removido(s) com sucesso!");
					}
				});
			}
		});

		this.display.getResetBuscaButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				display.getNomeCidadeBuscaTextField().setValue(null);
				display.getNomeEstadoBuscaTextField().setValue(null);
				display.getGrid().updateList();
			}
		});

		this.display.getSubmitBuscaButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				display.getGrid().updateList();
			}
		});

		this.display.getExportExcelButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				ExcelParametros parametros = new ExcelParametros(ExcelInformationType.CIDADES, ExcelOperation.EXPORT);

				ExportExcelFormSubmit e = new ExportExcelFormSubmit(parametros, display.getI18nConstants(),
						display.getI18nMessages());

				e.submit();
			}
		});
	}

	@Override
	public void go(Widget widget) {
		this.gTab = (GTab) widget;

		this.gTab.add((GTabItem) this.display.getComponent());
	}

	private void showEditPresenter(final CidadeDTO cidadeDTO) {
		if (cidadeDTO == null) {
			return;
		}

		EstadosServiceAsync service = Services.estados();

		service.getEstadoDTO(cidadeDTO.getEstadoId(), new AsyncCallback<EstadoDTO>() {
			@Override
			public void onSuccess(EstadoDTO result) {
				// Edição da cidade contendo o estado
				Presenter presenter = new CidadesFormPresenter(new CidadesFormView(cidadeDTO, result),
						display.getGrid());

				presenter.go(null);
			}

			@Override
			public void onFailure(Throwable caught) {
				// Edição da cidade NÃO contendo o estado
				Presenter presenter = new CidadesFormPresenter(new CidadesFormView(cidadeDTO), display.getGrid());

				presenter.go(null);
			}
		});
	}
}
