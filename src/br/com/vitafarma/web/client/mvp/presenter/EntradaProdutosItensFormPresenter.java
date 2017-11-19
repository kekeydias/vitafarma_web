package br.com.vitafarma.web.client.mvp.presenter;

import br.com.vitafarma.web.client.EntradaProdutosServiceAsync;
import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.shared.dtos.EntradaProdutoDTO;
import br.com.vitafarma.web.shared.dtos.EntradaProdutoGroupDTO;
import br.com.vitafarma.web.shared.i18n.IVitafarmaI18nGateway;
import br.com.vitafarma.web.shared.mvp.presenter.Presenter;
import br.com.vitafarma.web.shared.util.view.AbstractAsyncCallbackWithDefaultOnFailure;
import br.com.vitafarma.web.shared.util.view.ProdutosComboBox;
import br.com.vitafarma.web.shared.util.view.SimpleGrid;
import br.com.vitafarma.web.shared.util.view.SimpleModal;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.google.gwt.user.client.ui.Widget;

public class EntradaProdutosItensFormPresenter implements Presenter {
	public interface Display extends IVitafarmaI18nGateway {
		Button getSalvarButton();

		EntradaProdutoDTO getEntradaProdutoDTO();

		EntradaProdutoGroupDTO getEntradaProdutoGroupDTO();

		NumberField getQuantidadeTextField();

		NumberField getPrecoUnitarioTextField();

		ProdutosComboBox getProdutosComboBox();

		boolean isValid();

		SimpleModal getSimpleModal();
	}

	private SimpleGrid<EntradaProdutoDTO> gridPanel;
	private Display display;

	public EntradaProdutosItensFormPresenter(Display display) {
		this(display, null);
	}

	public EntradaProdutosItensFormPresenter(Display display,
			SimpleGrid<EntradaProdutoDTO> gridPanel) {
		this.gridPanel = gridPanel;
		this.display = display;

		this.setListeners();
	}

	private void setListeners() {
		this.display.getSalvarButton().addSelectionListener(
				new SelectionListener<ButtonEvent>() {
					@Override
					public void componentSelected(ButtonEvent ce) {
						if (isValid()) {
							final EntradaProdutosServiceAsync service = Services
									.entradaProdutos();

							service.saveEntradaProdutoItem(
									display.getEntradaProdutoGroupDTO(),
									getDTO(),
									new AbstractAsyncCallbackWithDefaultOnFailure<Boolean>(
											display) {
										@Override
										public void onSuccess(Boolean result) {
											display.getSimpleModal().hide();

											if (gridPanel != null) {
												gridPanel.updateList();
											}

											if (result) {
												Info.display("Salvo",
														"Item salvo com sucesso!");
											} else {
												Info.display("Erro",
														"Erro ao salvar o item!");
											}
										}
									});
						} else {
							MessageBox.alert("ERRO!",
									"Verifique os campos digitados", null);
						}
					}
				});
	}

	private boolean isValid() {
		return this.display.isValid();
	}

	private EntradaProdutoDTO getDTO() {
		EntradaProdutoDTO entradaProdutoDTO = this.display
				.getEntradaProdutoDTO();

		// Preço Unitário
		entradaProdutoDTO.setPrecoUnitarioValue(this.display
				.getPrecoUnitarioTextField().getValue() == null ? null
				: this.display.getPrecoUnitarioTextField().getValue()
						.doubleValue());

		// Quantidade
		entradaProdutoDTO.setQuantidadeValue(this.display
				.getQuantidadeTextField().getValue() == null ? null
				: this.display.getQuantidadeTextField().getValue()
						.doubleValue());

		// Produto
		entradaProdutoDTO.setProdutoId(this.display.getProdutosComboBox()
				.getValue().getId());

		return entradaProdutoDTO;
	}

	@Override
	public void go(Widget widget) {
		this.display.getSimpleModal().show();
	}
}
