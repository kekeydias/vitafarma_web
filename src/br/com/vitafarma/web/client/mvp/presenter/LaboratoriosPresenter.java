package br.com.vitafarma.web.client.mvp.presenter;

import java.util.List;

import br.com.vitafarma.web.client.CidadesServiceAsync;
import br.com.vitafarma.web.client.LaboratoriosServiceAsync;
import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.client.mvp.view.LaboratoriosFormView;
import br.com.vitafarma.web.shared.dtos.CidadeDTO;
import br.com.vitafarma.web.shared.dtos.LaboratorioDTO;
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

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class LaboratoriosPresenter implements Presenter {
	public interface Display extends IVitafarmaI18nGateway {
		Button getNewButton();

		Button getEditButton();

		Button getRemoveButton();

		TextField<String> getNomeBuscaTextField();

		NumberField getCnpjBuscaTextField();

		NumberField getTelefoneBuscaTextField();

		TextField<String> getNomeFantasiaBuscaTextField();

		TextField<String> getEnderecoBuscaTextField();

		TextField<String> getEmailBuscaTextField();

		Button getSubmitBuscaButton();

		Button getResetBuscaButton();

		Button getExportExcelButton();

		SimpleGrid<LaboratorioDTO> getGrid();

		Component getComponent();

		void setProxy(RpcProxy<PagingLoadResult<LaboratorioDTO>> proxy);
	}

	private Display display;
	private GTab gTab;

	public LaboratoriosPresenter(Display display) {
		this.display = display;
		this.configureProxy();
		this.setListeners();
	}

	private void configureProxy() {
		final LaboratoriosServiceAsync service = Services.laboratorios();

		RpcProxy<PagingLoadResult<LaboratorioDTO>> proxy = new RpcProxy<PagingLoadResult<LaboratorioDTO>>() {
			@Override
			public void load(Object loadConfig, AsyncCallback<PagingLoadResult<LaboratorioDTO>> callback) {

				String nome = display.getNomeBuscaTextField().getValue();
				String nomeFantasia = display.getNomeFantasiaBuscaTextField().getValue();
				Long cnpj = (display.getCnpjBuscaTextField().getValue() == null ? null
						: display.getCnpjBuscaTextField().getValue().longValue());

				Long telefone = (display.getTelefoneBuscaTextField().getValue() == null ? null
						: display.getTelefoneBuscaTextField().getValue().longValue());

				String endereco = display.getEnderecoBuscaTextField().getValue();

				String email = display.getEmailBuscaTextField().getValue();

				service.getBuscaList(nome, nomeFantasia, cnpj, telefone, endereco, email, (PagingLoadConfig) loadConfig,
						callback);
			}
		};

		this.display.setProxy(proxy);
	}

	private void setListeners() {
		this.display.getNewButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Presenter presenter = new LaboratoriosFormPresenter(new LaboratoriosFormView(new LaboratorioDTO()),
						display.getGrid());

				presenter.go(null);
			}
		});

		this.display.getEditButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				LaboratorioDTO laboratorioDTO = display.getGrid().getGrid().getSelectionModel().getSelectedItem();

				showEditPresenter(laboratorioDTO);
			}
		});

		this.display.getRemoveButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				final LaboratoriosServiceAsync service = Services.laboratorios();

				List<LaboratorioDTO> list = display.getGrid().getGrid().getSelectionModel().getSelectedItems();

				service.remove(list, new AbstractAsyncCallbackWithDefaultOnFailure<Void>(display) {
					@Override
					public void onFailure(Throwable caught) {
						MessageBox.alert("ERRO!", "Nao foi possivel remover o(s) laboratorios(s)", null);
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

				display.getNomeBuscaTextField().setValue(null);
				display.getNomeFantasiaBuscaTextField().setValue(null);
				display.getCnpjBuscaTextField().setValue(null);

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
				ExcelParametros parametros = new ExcelParametros(ExcelInformationType.LABORATORIOS,
						ExcelOperation.EXPORT);

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

	private void showEditPresenter(final LaboratorioDTO laboratorioDTO) {
		if (laboratorioDTO == null) {
			return;
		}

		CidadesServiceAsync service = Services.cidades();

		service.getCidadeDTO(laboratorioDTO.getCidadeId(), new AsyncCallback<CidadeDTO>() {

			@Override
			public void onSuccess(CidadeDTO result) {

				// Edição do laboratório contendo a cidade
				Presenter presenter = new LaboratoriosFormPresenter(new LaboratoriosFormView(laboratorioDTO, result),
						display.getGrid());

				presenter.go(null);
			}

			@Override
			public void onFailure(Throwable caught) {

				// Edição do laboratório não contendo a cidade
				Presenter presenter = new LaboratoriosFormPresenter(new LaboratoriosFormView(laboratorioDTO),
						display.getGrid());

				presenter.go(null);
			}
		});
	}
}
