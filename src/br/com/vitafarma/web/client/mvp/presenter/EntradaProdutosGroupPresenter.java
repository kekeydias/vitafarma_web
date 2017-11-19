package br.com.vitafarma.web.client.mvp.presenter;

import java.util.Date;
import java.util.List;

import br.com.vitafarma.web.client.EntradaProdutosServiceAsync;
import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.client.mvp.view.EntradaProdutosGroupFormView;
import br.com.vitafarma.web.client.mvp.view.EntradaProdutosItensView;
import br.com.vitafarma.web.shared.dtos.EntradaProdutoGroupDTO;
import br.com.vitafarma.web.shared.dtos.FornecedorDTO;
import br.com.vitafarma.web.shared.dtos.NotaFiscalDTO;
import br.com.vitafarma.web.shared.dtos.ParDTO;
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
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class EntradaProdutosGroupPresenter implements Presenter {
	public interface Display extends IVitafarmaI18nGateway {
		Button getNewButton();

		Button getEditButton();

		Button getRemoveButton();

		DateField getDataEntradaBuscaTextField();

		NumberField getCodigoFornecedorBuscaTextField();

		TextField<String> getNomeFornecedorBuscaTextField();

		TextField<String> getNotaFiscalBuscaTextField();

		Button getSubmitBuscaButton();

		Button getResetBuscaButton();

		Button getExportExcelButton();

		Button getIncluirItensEntradaButton();

		SimpleGrid<EntradaProdutoGroupDTO> getGrid();

		Component getComponent();

		void setProxy(RpcProxy<PagingLoadResult<EntradaProdutoGroupDTO>> proxy);
	}

	private Display display;
	private GTab gTab;

	public EntradaProdutosGroupPresenter(Display display) {
		this.display = display;
		this.configureProxy();
		this.setListeners();
	}

	private void configureProxy() {
		final EntradaProdutosServiceAsync service = Services.entradaProdutos();

		RpcProxy<PagingLoadResult<EntradaProdutoGroupDTO>> proxy = new RpcProxy<PagingLoadResult<EntradaProdutoGroupDTO>>() {
			@Override
			public void load(Object loadConfig, AsyncCallback<PagingLoadResult<EntradaProdutoGroupDTO>> callback) {
				Long codigoFornecedor = (display.getCodigoFornecedorBuscaTextField().getValue() == null ? null
						: display.getCodigoFornecedorBuscaTextField().getValue().longValue());
				String nomeFornecedor = display.getNomeFornecedorBuscaTextField().getValue();
				Date dataEntrada = display.getDataEntradaBuscaTextField().getValue();
				String codigoNotaFiscal = display.getNotaFiscalBuscaTextField().getValue();

				service.getBuscaListGroups(codigoFornecedor, nomeFornecedor, dataEntrada, codigoNotaFiscal,
						(PagingLoadConfig) loadConfig, callback);
			}
		};

		this.display.setProxy(proxy);
	}

	private void setListeners() {
		this.display.getNewButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Presenter presenter = new EntradaProdutosGroupFormPresenter(
						new EntradaProdutosGroupFormView(new EntradaProdutoGroupDTO()), display.getGrid());

				presenter.go(null);
			}
		});

		this.display.getEditButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				EntradaProdutoGroupDTO entradaProdutoGroupDTO = display.getGrid().getGrid().getSelectionModel()
						.getSelectedItem();

				if (entradaProdutoGroupDTO != null) {
					showEditPresenter(entradaProdutoGroupDTO);
				}
			}
		});

		this.display.getRemoveButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				final EntradaProdutosServiceAsync service = Services.entradaProdutos();

				List<EntradaProdutoGroupDTO> list = display.getGrid().getGrid().getSelectionModel().getSelectedItems();

				if (list != null && list.size() != 0) {
					service.removeGroup(list, new AbstractAsyncCallbackWithDefaultOnFailure<Void>(display) {
						@Override
						public void onFailure(Throwable caught) {
							MessageBox.alert("ERRO!", "Nao foi possivel remover a(s) entrada(s) de produto(s)", null);
						}

						@Override
						public void onSuccess(Void result) {
							display.getGrid().updateList();

							Info.display("Removido", "Item(ns) removido(s) com sucesso!");
						}
					});
				}
			}
		});

		this.display.getResetBuscaButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				display.getNotaFiscalBuscaTextField().setValue(null);
				display.getCodigoFornecedorBuscaTextField().setValue(null);
				display.getNomeFornecedorBuscaTextField().setValue(null);
				display.getDataEntradaBuscaTextField().setValue(null);
				display.getGrid().updateList();
			}
		});

		this.display.getSubmitBuscaButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				display.getGrid().updateList();
			}
		});

		this.display.getIncluirItensEntradaButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				final EntradaProdutoGroupDTO entradaProdutoGroupDTO = display.getGrid().getGrid().getSelectionModel()
						.getSelectedItem();

				if (entradaProdutoGroupDTO != null) {

					Presenter presenter = new EntradaProdutosItensPresenter(
							new EntradaProdutosItensView(entradaProdutoGroupDTO));
					presenter.go(gTab);
				}
			}
		});

		this.display.getExportExcelButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				ExcelParametros parametros = new ExcelParametros(ExcelInformationType.ENTRADA_PRODUTOS_GROUP,
						ExcelOperation.EXPORT);

				ExportExcelFormSubmit e = new ExportExcelFormSubmit(parametros, display.getI18nConstants(),
						display.getI18nMessages());

				// Informando os filtros para os itens
				Long codigoFornecedor = (display.getCodigoFornecedorBuscaTextField().getValue() == null ? null
						: display.getCodigoFornecedorBuscaTextField().getValue().longValue());
				String nomeFornecedor = display.getNomeFornecedorBuscaTextField().getValue();
				Date dataEntrada = display.getDataEntradaBuscaTextField().getValue();
				String codigoNotaFiscal = display.getNotaFiscalBuscaTextField().getValue();

				if (codigoFornecedor != null) {
					e.addParameter("codigoFornecedor", codigoFornecedor.toString());
				}
				e.addParameter("nomeFornecedor", nomeFornecedor);

				if (dataEntrada != null) {
					e.addParameter("dataEntrada", Long.toString(dataEntrada.getTime()));
				}
				e.addParameter("codigoNotaFiscal", codigoNotaFiscal);

				e.submit();
			}
		});
	}

	@Override
	public void go(Widget widget) {
		this.gTab = (GTab) widget;
		this.gTab.add((GTabItem) this.display.getComponent());
	}

	private void showEditPresenter(final EntradaProdutoGroupDTO entradaProdutoGroupDTO) {
		if (entradaProdutoGroupDTO == null) {
			return;
		}

		EntradaProdutosServiceAsync service = Services.entradaProdutos();

		service.getNotaFiscalAndFornecedorDTO(entradaProdutoGroupDTO.getNotaFiscalId(),
				entradaProdutoGroupDTO.getFornecedorId(), new AsyncCallback<ParDTO<NotaFiscalDTO, FornecedorDTO>>() {

					@Override
					public void onSuccess(ParDTO<NotaFiscalDTO, FornecedorDTO> result) {

						NotaFiscalDTO notaFiscalDTO = result.getPrimeiro();
						FornecedorDTO fornecedorDTO = result.getSegundo();

						// Edição de entrada de produto contendo produto e
						// fornecedor
						Presenter presenter = new EntradaProdutosGroupFormPresenter(
								new EntradaProdutosGroupFormView(entradaProdutoGroupDTO, fornecedorDTO, notaFiscalDTO),
								display.getGrid());

						presenter.go(null);
					}

					@Override
					public void onFailure(Throwable caught) {

						// Edição de entrada de produto não contendo produto e
						// fornecedor
						Presenter presenter = new EntradaProdutosGroupFormPresenter(
								new EntradaProdutosGroupFormView(entradaProdutoGroupDTO), display.getGrid());

						presenter.go(null);
					}
				});
	}
}
