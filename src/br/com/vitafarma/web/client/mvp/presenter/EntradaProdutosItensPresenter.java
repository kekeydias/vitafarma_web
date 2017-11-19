package br.com.vitafarma.web.client.mvp.presenter;

import java.util.List;

import br.com.vitafarma.web.client.EntradaProdutosServiceAsync;
import br.com.vitafarma.web.client.ProdutosServiceAsync;
import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.client.mvp.view.EntradaProdutosItensFormView;
import br.com.vitafarma.web.shared.dtos.EntradaProdutoDTO;
import br.com.vitafarma.web.shared.dtos.EntradaProdutoGroupDTO;
import br.com.vitafarma.web.shared.dtos.ProdutoDTO;
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
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class EntradaProdutosItensPresenter implements Presenter {
	public interface Display extends IVitafarmaI18nGateway {
		Button getNewButton();

		Button getEditButton();

		Button getRemoveButton();

		Button getExportExcelButton();

		EntradaProdutoGroupDTO getEntradaProdutoGroupDTO();

		SimpleGrid<EntradaProdutoDTO> getGrid();

		Component getComponent();

		void setProxy(RpcProxy<PagingLoadResult<EntradaProdutoDTO>> proxy);
	}

	private Display display;
	private GTab gTab;

	public EntradaProdutosItensPresenter(Display display) {
		this.display = display;
		this.configureProxy();
		this.setListeners();
	}

	private void configureProxy() {
		final EntradaProdutosServiceAsync service = Services.entradaProdutos();

		RpcProxy<PagingLoadResult<EntradaProdutoDTO>> proxy = new RpcProxy<PagingLoadResult<EntradaProdutoDTO>>() {
			@Override
			public void load(Object loadConfig, AsyncCallback<PagingLoadResult<EntradaProdutoDTO>> callback) {
				EntradaProdutoGroupDTO entradaProdutoGroupDTO = display.getEntradaProdutoGroupDTO();
				service.getItensEntradaProdutoList(entradaProdutoGroupDTO, (PagingLoadConfig) loadConfig, callback);
			}
		};

		this.display.setProxy(proxy);
	}

	private void setListeners() {
		this.display.getNewButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Presenter presenter = new EntradaProdutosItensFormPresenter(
						new EntradaProdutosItensFormView(display.getEntradaProdutoGroupDTO(), new EntradaProdutoDTO()),
						display.getGrid());

				presenter.go(null);
			}
		});

		this.display.getEditButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				EntradaProdutoDTO entradaProdutoDTO = display.getGrid().getGrid().getSelectionModel().getSelectedItem();

				if (entradaProdutoDTO != null) {
					showEditPresenter(entradaProdutoDTO);
				}
			}
		});

		this.display.getRemoveButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				final EntradaProdutosServiceAsync service = Services.entradaProdutos();

				List<EntradaProdutoDTO> list = display.getGrid().getSelectionModel().getSelectedItems();

				if (list != null && list.size() != 0) {
					service.removeItens(list, new AbstractAsyncCallbackWithDefaultOnFailure<Void>(display) {
						@Override
						public void onSuccess(Void result) {
							if (display.getGrid() != null) {
								display.getGrid().updateList();
							}

							Info.display("Removido", "Item(ns) removido(s) com sucesso!");
						}
					});
				}
			}
		});

		this.display.getExportExcelButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				ExcelParametros parametros = new ExcelParametros(ExcelInformationType.ENTRADA_PRODUTOS_ITENS,
						ExcelOperation.EXPORT);

				ExportExcelFormSubmit e = new ExportExcelFormSubmit(parametros, display.getI18nConstants(),
						display.getI18nMessages());

				// Informando a entrada que devo exportar os itens
				if (display.getEntradaProdutoGroupDTO().getId() != null) {
					e.addParameter("idEntradaGroup", display.getEntradaProdutoGroupDTO().getId().toString());
				}

				e.submit();
			}
		});
	}

	@Override
	public void go(Widget widget) {
		this.gTab = (GTab) widget;
		this.gTab.add((GTabItem) this.display.getComponent());
	}

	private void showEditPresenter(final EntradaProdutoDTO entradaProdutoDTO) {
		if (entradaProdutoDTO == null) {
			return;
		}

		ProdutosServiceAsync service = Services.produtos();

		service.getProdutoDTO(entradaProdutoDTO.getProdutoId(), new AsyncCallback<ProdutoDTO>() {

			@Override
			public void onSuccess(ProdutoDTO result) {
				// Edição do item contendo o produto
				Presenter presenter = new EntradaProdutosItensFormPresenter(new EntradaProdutosItensFormView(
						display.getEntradaProdutoGroupDTO(), entradaProdutoDTO, result), display.getGrid());

				presenter.go(null);
			}

			@Override
			public void onFailure(Throwable caught) {
				// Edição do item não contendo o produto
				Presenter presenter = new EntradaProdutosItensFormPresenter(
						new EntradaProdutosItensFormView(display.getEntradaProdutoGroupDTO(), entradaProdutoDTO),
						display.getGrid());

				presenter.go(null);
			}
		});
	}
}
