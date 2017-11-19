package br.com.vitafarma.web.client.mvp.presenter;

import br.com.vitafarma.web.client.ProdutosServiceAsync;
import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.shared.dtos.ProdutoDTO;
import br.com.vitafarma.web.shared.i18n.IVitafarmaI18nGateway;
import br.com.vitafarma.web.shared.mvp.presenter.Presenter;
import br.com.vitafarma.web.shared.util.view.AbstractAsyncCallbackWithDefaultOnFailure;
import br.com.vitafarma.web.shared.util.view.ProdutosComboBox;
import br.com.vitafarma.web.shared.util.view.SimpleModal;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.google.gwt.user.client.ui.Widget;

public class AtualizaEstoqueFormPresenter implements Presenter {
	public interface Display extends IVitafarmaI18nGateway {
		Button getSalvarButton();

		ProdutosComboBox getProdutosComboBox();

		CheckBox getAtualizarTodosCheckBox();

		boolean isValid();

		SimpleModal getSimpleModal();
	}

	private Display display;

	public AtualizaEstoqueFormPresenter(Display display) {
		this.display = display;
		this.setListeners();
	}

	private void setListeners() {
		this.display.getSalvarButton().addSelectionListener(
				new SelectionListener<ButtonEvent>() {
					@Override
					public void componentSelected(ButtonEvent ce) {
						if (isValid()) {
							final ProdutosServiceAsync service = Services
									.produtos();

							service.updateEstoque(
									getDTO(),
									new AbstractAsyncCallbackWithDefaultOnFailure<Boolean>(
											display) {
										@Override
										public void onSuccess(Boolean result) {
											display.getSimpleModal().hide();

											if (result) {
												Info.display("Sucesso",
														"Estoque atualizado com sucesso!");
											} else {
												Info.display("Erro",
														"Erro ao atualizar o estoque do(s) produto(s)!");
											}
										}
									});
						} else {
							MessageBox
									.alert("ERRO!",
											"Informe um produto ou marque: 'Atualizar Todo o Estoque'",
											null);
						}
					}
				});
	}

	private boolean isValid() {
		return this.display.isValid();
	}

	private ProdutoDTO getDTO() {
		ProdutoDTO produtoDTO = new ProdutoDTO();

		if (!this.display.getAtualizarTodosCheckBox().getValue()) {
			produtoDTO
					.setId(this.display.getProdutosComboBox().getValue() == null ? null
							: this.display.getProdutosComboBox().getValue()
									.getId());
		}

		return produtoDTO;
	}

	@Override
	public void go(Widget widget) {
		this.display.getSimpleModal().show();
	}
}
