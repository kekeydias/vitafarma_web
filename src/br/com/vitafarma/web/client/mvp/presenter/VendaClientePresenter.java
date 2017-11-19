package br.com.vitafarma.web.client.mvp.presenter;

import java.util.List;

import br.com.vitafarma.web.client.ProdutosServiceAsync;
import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.client.VendasServiceAsync;
import br.com.vitafarma.web.client.mvp.view.ItemVendaFormView;
import br.com.vitafarma.web.shared.dtos.ItemVendaDTO;
import br.com.vitafarma.web.shared.dtos.ProdutoDTO;
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

public class VendaClientePresenter implements Presenter {
	public interface Display extends IVitafarmaI18nGateway {
		Button getNewButton();

		Button getEditButton();

		Button getRemoveButton();

		Button getExportExcelButton();

		VendaDTO getVendaDTO();

		SimpleGrid<ItemVendaDTO> getGrid();

		Component getComponent();

		void setProxy(RpcProxy<PagingLoadResult<ItemVendaDTO>> proxy);
	}

	private Display display;
	private GTab gTab;

	public VendaClientePresenter(Display display) {
		this.display = display;
		this.configureProxy();
		this.setListeners();
	}

	private void configureProxy() {
		final VendasServiceAsync service = Services.vendas();

		RpcProxy<PagingLoadResult<ItemVendaDTO>> proxy = new RpcProxy<PagingLoadResult<ItemVendaDTO>>() {
			@Override
			public void load(Object loadConfig, AsyncCallback<PagingLoadResult<ItemVendaDTO>> callback) {
				VendaDTO vendaDTO = display.getVendaDTO();
				service.getItensVendaList(vendaDTO, (PagingLoadConfig) loadConfig, callback);
			}
		};

		this.display.setProxy(proxy);
	}

	private void setListeners() {
		this.display.getNewButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Presenter presenter = new ItemVendaFormPresenter(
						new ItemVendaFormView(new ItemVendaDTO(), display.getVendaDTO()), display.getGrid());

				presenter.go(null);
			}
		});

		this.display.getEditButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				ItemVendaDTO itemVendaDTO = display.getGrid().getGrid().getSelectionModel().getSelectedItem();

				if (itemVendaDTO != null) {
					showEditPresenter(itemVendaDTO);
				}
			}
		});

		this.display.getRemoveButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				final VendasServiceAsync service = Services.vendas();

				List<ItemVendaDTO> list = display.getGrid().getSelectionModel().getSelectedItems();

				if (list != null && list.size() != 0) {
					service.removeItemVenda(list, new AbstractAsyncCallbackWithDefaultOnFailure<Void>(display) {
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
				ExcelParametros parametros = new ExcelParametros(ExcelInformationType.ITENS_VENDA,
						ExcelOperation.EXPORT);

				ExportExcelFormSubmit e = new ExportExcelFormSubmit(parametros, display.getI18nConstants(),
						display.getI18nMessages());

				// Informando a venda que devo exportar os itens
				if (display.getVendaDTO().getVendaId() != null) {
					e.addParameter("idVenda", display.getVendaDTO().getVendaId().toString());
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

	private void showEditPresenter(final ItemVendaDTO itemVendaDTO) {
		if (itemVendaDTO == null) {
			return;
		}

		ProdutosServiceAsync service = Services.produtos();

		service.getProdutoDTO(itemVendaDTO.getProdutoId(), new AsyncCallback<ProdutoDTO>() {

			@Override
			public void onSuccess(ProdutoDTO result) {

				// Edição do item contendo o produto
				Presenter presenter = new ItemVendaFormPresenter(
						new ItemVendaFormView(itemVendaDTO, result, display.getVendaDTO()), display.getGrid());

				presenter.go(null);
			}

			@Override
			public void onFailure(Throwable caught) {

				// Edição do item não contendo o produto
				Presenter presenter = new ItemVendaFormPresenter(
						new ItemVendaFormView(itemVendaDTO, display.getVendaDTO()), display.getGrid());

				presenter.go(null);
			}
		});
	}
}
