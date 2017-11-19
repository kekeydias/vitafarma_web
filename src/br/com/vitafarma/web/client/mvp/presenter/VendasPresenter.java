package br.com.vitafarma.web.client.mvp.presenter;

import java.util.Date;
import java.util.List;

import br.com.vitafarma.web.client.ClientesServiceAsync;
import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.client.VendasServiceAsync;
import br.com.vitafarma.web.client.mvp.view.VendaClienteView;
import br.com.vitafarma.web.client.mvp.view.VendasFormView;
import br.com.vitafarma.web.shared.dtos.ClienteDTO;
import br.com.vitafarma.web.shared.dtos.VendaDTO;
import br.com.vitafarma.web.shared.excel.ExcelInformationType;
import br.com.vitafarma.web.shared.i18n.IVitafarmaI18nGateway;
import br.com.vitafarma.web.shared.mvp.presenter.Presenter;
import br.com.vitafarma.web.shared.util.view.AbstractAsyncCallbackWithDefaultOnFailure;
import br.com.vitafarma.web.shared.util.view.ExcelOperation;
import br.com.vitafarma.web.shared.util.view.ExcelParametros;
import br.com.vitafarma.web.shared.util.view.ExportExcelFormSubmit;
import br.com.vitafarma.web.shared.util.view.GTab;
import br.com.vitafarma.web.shared.util.view.GTabItem;
import br.com.vitafarma.web.shared.util.view.ImportExcelFormView;
import br.com.vitafarma.web.shared.util.view.SimpleGrid;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class VendasPresenter implements Presenter {
	public interface Display extends IVitafarmaI18nGateway {
		Button getNewButton();

		Button getEditButton();

		Button getRemoveButton();

		Button getImportExcelButton();

		Button getExportExcelButton();

		Button getIncluirProdutosVendaButton();

		Button getEmitirCupomVendaButton();

		NumberField getCodigoClienteBuscaTF();

		TextField<String> getNomeClienteBuscaTF();

		NumberField getCpfClienteBuscaTF();

		DateField getDataVendaBuscaTF();

		Button getSubmitBuscaButton();

		Button getResetBuscaButton();

		SimpleGrid<VendaDTO> getGrid();

		Component getComponent();

		void setProxy(RpcProxy<PagingLoadResult<VendaDTO>> proxy);
	}

	private Display display;
	private GTab gTab;

	public VendasPresenter(Display display) {
		this.display = display;
		this.configureProxy();
		this.setListeners();
	}

	private void configureProxy() {
		final VendasServiceAsync service = Services.vendas();

		RpcProxy<PagingLoadResult<VendaDTO>> proxy = new RpcProxy<PagingLoadResult<VendaDTO>>() {
			@Override
			public void load(Object loadConfig, AsyncCallback<PagingLoadResult<VendaDTO>> callback) {
				Long codigoCliente = (display.getCodigoClienteBuscaTF().getValue() == null ? null
						: display.getCodigoClienteBuscaTF().getValue().longValue());
				String nomeCliente = display.getNomeClienteBuscaTF().getValue();
				Long cpfCliente = (display.getCpfClienteBuscaTF().getValue() == null ? null
						: display.getCpfClienteBuscaTF().getValue().longValue());
				Date dataVenda = display.getDataVendaBuscaTF().getValue();

				service.getBuscaList(codigoCliente, nomeCliente, cpfCliente, dataVenda, (PagingLoadConfig) loadConfig,
						callback);
			}
		};

		this.display.setProxy(proxy);
	}

	private void setListeners() {
		this.display.getNewButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Presenter presenter = new VendasFormPresenter(new VendasFormView(new VendaDTO()), display.getGrid());

				presenter.go(null);
			}
		});

		this.display.getEditButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				final VendaDTO vendaDTO = display.getGrid().getGrid().getSelectionModel().getSelectedItem();

				showEditPresenter(vendaDTO);
			}
		});

		this.display.getRemoveButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {

				List<VendaDTO> list = display.getGrid().getGrid().getSelectionModel().getSelectedItems();
				final VendasServiceAsync service = Services.vendas();

				service.remove(list, new AbstractAsyncCallbackWithDefaultOnFailure<Void>(display) {
					@Override
					public void onSuccess(Void result) {
						display.getGrid().updateList();
						Info.display("Removido", "Item(ns) removido(s) com sucesso!");
					}
				});
			}
		});

		this.display.getExportExcelButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				ExcelParametros parametros = new ExcelParametros(ExcelInformationType.VENDAS, ExcelOperation.EXPORT);

				ExportExcelFormSubmit e = new ExportExcelFormSubmit(parametros, display.getI18nConstants(),
						display.getI18nMessages());

				e.submit();
			}
		});

		this.display.getImportExcelButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				ExcelParametros parametros = new ExcelParametros(ExcelInformationType.VENDAS, ExcelOperation.IMPORT);

				ImportExcelFormView importExcelFormView = new ImportExcelFormView(parametros, display.getGrid());

				importExcelFormView.show();
			}
		});

		this.display.getResetBuscaButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				display.getCodigoClienteBuscaTF().setValue(null);
				display.getNomeClienteBuscaTF().setValue(null);
				display.getCpfClienteBuscaTF().setValue(null);
				display.getDataVendaBuscaTF().setValue(null);
				display.getGrid().updateList();
			}
		});

		this.display.getSubmitBuscaButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				display.getGrid().updateList();
			}
		});

		this.display.getIncluirProdutosVendaButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				final VendaDTO vendaDTO = display.getGrid().getGrid().getSelectionModel().getSelectedItem();

				Presenter presenter = new VendaClientePresenter(new VendaClienteView(vendaDTO));
				presenter.go(gTab);
			}
		});

		this.display.getEmitirCupomVendaButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				final VendaDTO vendaDTO = display.getGrid().getGrid().getSelectionModel().getSelectedItem();

				if (vendaDTO == null) {
					Info.display("Nenhum item selecionado.", "Selecione uma venda");
					return;
				}

				// Verifica se já existe cupom Fiscal
				final VendasServiceAsync service = Services.vendas();
				service.emitiuCupomVenda(vendaDTO, new AsyncCallback<Boolean>() {
					@Override
					public void onSuccess(Boolean result) {
						if (result) {
							// Caso já exista um cupom fiscal
							// dessa
							// venda, pergunta-se
							// ao usuário se deseja gerar um
							// cupom
							// novo, excluindo o cupom antigo
							MessageBox.confirm("Emitir Novo Cupom",
									"Ja existe um cupom para essa venda. Deseja gerar um novo cupom?",
									new Listener<MessageBoxEvent>() {
								@Override
								public void handleEvent(MessageBoxEvent be) {
									if (be.getButtonClicked().getText().equalsIgnoreCase("yes")) {
										gerarCupomVenda(vendaDTO, true);
									} else {
										gerarCupomVenda(vendaDTO, false);
									}
								}
							});
						} else {
							gerarCupomVenda(vendaDTO, true);
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						Info.display("Erro", "Nao foi possivel conectar-se ao servidor.");
					}
				});
			}
		});
	}

	private void gerarCupomVenda(VendaDTO vendaDTO, Boolean gerarNovoCupomVenda) {
		// Geração do cupom fiscal
		ExcelParametros parametros = new ExcelParametros(ExcelInformationType.CUPOM_FISCAL, ExcelOperation.EXPORT);

		ExportExcelFormSubmit e = new ExportExcelFormSubmit(parametros, display.getI18nConstants(),
				display.getI18nMessages());

		if (vendaDTO != null && vendaDTO.getVendaId() != null) {
			String idVenda = vendaDTO.getVendaId().toString();
			e.addParameter("idVenda", idVenda);
			e.addParameter("gerarNovoCupomVenda", gerarNovoCupomVenda.toString());
		}

		e.submit();
	}

	@Override
	public void go(Widget widget) {
		this.gTab = (GTab) widget;
		this.gTab.add((GTabItem) this.display.getComponent());
	}

	private void showEditPresenter(final VendaDTO vendaDTO) {
		if (vendaDTO == null) {
			return;
		}

		ClientesServiceAsync service = Services.clientes();

		service.getClienteDTO(vendaDTO.getClienteId(), new AsyncCallback<ClienteDTO>() {

			@Override
			public void onSuccess(ClienteDTO result) {

				// Edição do cliente contendo a cidade
				Presenter presenter = new VendasFormPresenter(new VendasFormView(vendaDTO, result), display.getGrid());

				presenter.go(null);
			}

			@Override
			public void onFailure(Throwable caught) {

				// Edição de venda não contendo o cliente
				Presenter presenter = new VendasFormPresenter(new VendasFormView(vendaDTO), display.getGrid());

				presenter.go(null);
			}
		});
	}
}
