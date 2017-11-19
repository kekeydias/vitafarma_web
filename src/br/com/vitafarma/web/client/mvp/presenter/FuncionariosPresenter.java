package br.com.vitafarma.web.client.mvp.presenter;

import java.util.List;

import br.com.vitafarma.web.client.CidadesServiceAsync;
import br.com.vitafarma.web.client.FuncionariosServiceAsync;
import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.client.mvp.view.FuncionariosFormView;
import br.com.vitafarma.web.shared.dtos.CidadeDTO;
import br.com.vitafarma.web.shared.dtos.FuncionarioDTO;
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

public class FuncionariosPresenter implements Presenter {
	public interface Display extends IVitafarmaI18nGateway {
		Button getNewButton();

		Button getEditButton();

		Button getRemoveButton();

		TextField<String> getNomeBuscaTextField();

		NumberField getCpfBuscaTextField();

		NumberField getTelefoneBuscaTextField();

		TextField<String> getEnderecoBuscaTextField();

		TextField<String> getEmailBuscaTextField();

		Button getSubmitBuscaButton();

		Button getResetBuscaButton();

		Button getExportExcelButton();

		SimpleGrid<FuncionarioDTO> getGrid();

		Component getComponent();

		void setProxy(RpcProxy<PagingLoadResult<FuncionarioDTO>> proxy);
	}

	private Display display;
	private GTab gTab;

	public FuncionariosPresenter(Display display) {
		this.display = display;
		this.configureProxy();
		this.setListeners();
	}

	private void configureProxy() {
		final FuncionariosServiceAsync service = Services.funcionarios();

		RpcProxy<PagingLoadResult<FuncionarioDTO>> proxy = new RpcProxy<PagingLoadResult<FuncionarioDTO>>() {
			@Override
			public void load(Object loadConfig, AsyncCallback<PagingLoadResult<FuncionarioDTO>> callback) {
				String nome = display.getNomeBuscaTextField().getValue();
				Long cpf = (display.getCpfBuscaTextField().getValue() == null ? null
						: display.getCpfBuscaTextField().getValue().longValue());
				Long telefone = (display.getTelefoneBuscaTextField().getValue() == null ? null
						: display.getTelefoneBuscaTextField().getValue().longValue());
				String endereco = display.getEnderecoBuscaTextField().getValue();
				String email = display.getEmailBuscaTextField().getValue();

				service.getBuscaList(nome, cpf, telefone, endereco, email, (PagingLoadConfig) loadConfig, callback);
			}
		};

		this.display.setProxy(proxy);
	}

	private void setListeners() {
		this.display.getNewButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Presenter presenter = new FuncionariosFormPresenter(new FuncionariosFormView(new FuncionarioDTO()),
						display.getGrid());

				presenter.go(null);
			}
		});

		this.display.getEditButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				FuncionarioDTO funcionarioDTO = display.getGrid().getGrid().getSelectionModel().getSelectedItem();

				showEditPresenter(funcionarioDTO);
			}
		});

		this.display.getRemoveButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				final FuncionariosServiceAsync service = Services.funcionarios();

				List<FuncionarioDTO> list = display.getGrid().getGrid().getSelectionModel().getSelectedItems();

				service.remove(list, new AbstractAsyncCallbackWithDefaultOnFailure<Void>(display) {
					@Override
					public void onFailure(Throwable caught) {
						MessageBox.alert("ERRO!", "Nao foi possivel remover o(s) funcionario(s)", null);
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
				display.getCpfBuscaTextField().setValue(null);

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
				ExcelParametros parametros = new ExcelParametros(ExcelInformationType.FUNCIONARIOS,
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

	private void showEditPresenter(final FuncionarioDTO funcionarioDTO) {
		if (funcionarioDTO == null) {
			return;
		}

		CidadesServiceAsync service = Services.cidades();

		service.getCidadeDTO(funcionarioDTO.getCidadeId(), new AsyncCallback<CidadeDTO>() {

			@Override
			public void onSuccess(CidadeDTO result) {
				// Edição do funcionário contendo a cidade
				Presenter presenter = new FuncionariosFormPresenter(new FuncionariosFormView(funcionarioDTO, result),
						display.getGrid());

				presenter.go(null);
			}

			@Override
			public void onFailure(Throwable caught) {
				// Edição do funcionário não contendo a cidade
				Presenter presenter = new FuncionariosFormPresenter(new FuncionariosFormView(funcionarioDTO),
						display.getGrid());

				presenter.go(null);
			}
		});
	}
}
