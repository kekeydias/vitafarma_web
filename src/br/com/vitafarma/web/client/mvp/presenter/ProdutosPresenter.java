package br.com.vitafarma.web.client.mvp.presenter;

import java.util.List;

import br.com.vitafarma.web.client.ProdutosServiceAsync;
import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.client.mvp.view.AtualizaEstoqueFormView;
import br.com.vitafarma.web.client.mvp.view.ProdutosFormView;
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
import br.com.vitafarma.web.shared.util.view.ImportExcelFormView;
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

public class ProdutosPresenter implements Presenter {
	public interface Display extends IVitafarmaI18nGateway {
		Button getNewButton();

		Button getEditButton();

		Button getRemoveButton();

		NumberField getMedAbcBuscaTextField();

		NumberField getCodigoBuscaTextField();

		TextField<String> getNomeBuscaTextField();

		NumberField getPrecoBuscaTextField();

		TextField<String> getNomeLaboratorioBuscaTextField();

		Button getSubmitBuscaButton();

		Button getResetBuscaButton();

		Button getImportExcelButton();

		Button getExportExcelButton();

		Button getAtualizaEstoqueButton();

		SimpleGrid<ProdutoDTO> getGrid();

		Component getComponent();

		void setProxy(RpcProxy<PagingLoadResult<ProdutoDTO>> proxy);
	}

	private Display display = null;
	private GTab gTab = null;

	public ProdutosPresenter(Display display) {
		this.display = display;

		this.configureProxy();
		this.setListeners();
	}

	private void configureProxy() {
		final ProdutosServiceAsync service = Services.produtos();

		RpcProxy<PagingLoadResult<ProdutoDTO>> proxy = new RpcProxy<PagingLoadResult<ProdutoDTO>>() {
			@Override
			public void load(Object loadConfig, AsyncCallback<PagingLoadResult<ProdutoDTO>> callback) {

				Long medAbc = display.getMedAbcBuscaTextField().getValue() == null ? null
						: display.getMedAbcBuscaTextField().getValue().longValue();
				Long codigo = display.getCodigoBuscaTextField().getValue() == null ? null
						: display.getCodigoBuscaTextField().getValue().longValue();
				String nome = display.getNomeBuscaTextField().getValue();
				Double preco = display.getPrecoBuscaTextField().getValue() == null ? null
						: display.getPrecoBuscaTextField().getValue().doubleValue();
				String nomeLaboratorio = display.getNomeLaboratorioBuscaTextField().getValue();

				service.getBuscaList(codigo, medAbc, nome, preco, nomeLaboratorio, (PagingLoadConfig) loadConfig,
						callback);
			}
		};

		this.display.setProxy(proxy);
	}

	private void setListeners() {
		this.display.getNewButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Presenter presenter = new ProdutosFormPresenter(new ProdutosFormView(new ProdutoDTO()),
						display.getGrid());

				presenter.go(null);
			}
		});

		this.display.getEditButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				ProdutoDTO produtoDTO = display.getGrid().getGrid().getSelectionModel().getSelectedItem();

				Presenter presenter = new ProdutosFormPresenter(new ProdutosFormView(produtoDTO), display.getGrid());

				presenter.go(null);
			}
		});

		this.display.getRemoveButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				final ProdutosServiceAsync service = Services.produtos();

				List<ProdutoDTO> list = display.getGrid().getGrid().getSelectionModel().getSelectedItems();

				service.remove(list, new AbstractAsyncCallbackWithDefaultOnFailure<Void>(display) {
					@Override
					public void onFailure(Throwable caught) {
						MessageBox.alert("ERRO!", "Nao foi possivel remover o(s) produto(s)", null);
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
				display.getCodigoBuscaTextField().setValue(null);
				display.getMedAbcBuscaTextField().setValue(null);
				display.getNomeBuscaTextField().setValue(null);
				display.getPrecoBuscaTextField().setValue(null);
				display.getNomeLaboratorioBuscaTextField().setValue(null);

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
				ExcelParametros parametros = new ExcelParametros(ExcelInformationType.PRODUTOS, ExcelOperation.EXPORT);

				ExportExcelFormSubmit e = new ExportExcelFormSubmit(parametros, display.getI18nConstants(),
						display.getI18nMessages());

				Long codigoProduto = (display.getCodigoBuscaTextField().getValue() == null ? null
						: display.getCodigoBuscaTextField().getValue().longValue());

				Long medAbc = (display.getMedAbcBuscaTextField().getValue() == null ? null
						: display.getMedAbcBuscaTextField().getValue().longValue());

				String nomeProduto = display.getNomeBuscaTextField().getValue();

				Double precoProduto = (display.getPrecoBuscaTextField().getValue() == null ? null
						: display.getPrecoBuscaTextField().getValue().doubleValue());

				String nomeLaboratorio = display.getNomeLaboratorioBuscaTextField().getValue();

				if (codigoProduto != null) {
					e.addParameter("codigoProduto", codigoProduto.toString());
				}

				if (medAbc != null) {
					e.addParameter("medAbc", medAbc.toString());
				}

				e.addParameter("nomeProduto", nomeProduto);

				if (precoProduto != null) {
					e.addParameter("precoProduto", precoProduto.toString());
				}

				e.addParameter("nomeLaboratorio", nomeLaboratorio);

				e.submit();
			}
		});

		this.display.getAtualizaEstoqueButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				ProdutoDTO produtoDTO = display.getGrid().getGrid().getSelectionModel().getSelectedItem();

				Presenter presenter = new AtualizaEstoqueFormPresenter(new AtualizaEstoqueFormView(produtoDTO));

				presenter.go(null);
			}
		});

		this.display.getImportExcelButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				ExcelParametros parametros = new ExcelParametros(ExcelInformationType.PRODUTOS, ExcelOperation.IMPORT);

				ImportExcelFormView importExcelFormView = new ImportExcelFormView(parametros, display.getGrid());

				importExcelFormView.show();
			}
		});
	}

	@Override
	public void go(Widget widget) {
		this.gTab = (GTab) widget;

		this.gTab.add((GTabItem) this.display.getComponent());
	}
}
