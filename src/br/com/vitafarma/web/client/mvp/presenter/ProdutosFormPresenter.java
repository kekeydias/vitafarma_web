package br.com.vitafarma.web.client.mvp.presenter;

import br.com.vitafarma.web.client.ProdutosServiceAsync;
import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.shared.dtos.ProdutoDTO;
import br.com.vitafarma.web.shared.i18n.IVitafarmaI18nGateway;
import br.com.vitafarma.web.shared.mvp.presenter.Presenter;
import br.com.vitafarma.web.shared.util.view.AbstractAsyncCallbackWithDefaultOnFailure;
import br.com.vitafarma.web.shared.util.view.LaboratoriosComboBox;
import br.com.vitafarma.web.shared.util.view.SimpleGrid;
import br.com.vitafarma.web.shared.util.view.SimpleModal;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.user.client.ui.Widget;

public class ProdutosFormPresenter implements Presenter {
	public interface Display extends IVitafarmaI18nGateway {
		Button getSalvarButton();

		ProdutoDTO getProdutoDTO();

		NumberField getMedAbcTextField();

		TextField<String> getNomeTextField();

		TextField<String> getDescricaoTextField();

		NumberField getPrecoTextField();

		TextField<String> getUnidadeEntradaTextField();

		TextField<String> getUnidadeVendaTextField();

		LaboratoriosComboBox getLaboratoriosComboBox();

		boolean isValid();

		SimpleModal getSimpleModal();
	}

	private SimpleGrid<ProdutoDTO> gridPanel;
	private Display display;

	public ProdutosFormPresenter(Display display,
			SimpleGrid<ProdutoDTO> gridPanel) {
		this.gridPanel = gridPanel;
		this.display = display;
		this.setListeners();
	}

	public ProdutosFormPresenter(Display display) {
		this(display, null);
	}

	private void setListeners() {
		this.display.getSalvarButton().addSelectionListener(
				new SelectionListener<ButtonEvent>() {
					@Override
					public void componentSelected(ButtonEvent ce) {
						if (isValid()) {
							final ProdutosServiceAsync service = Services
									.produtos();

							service.saveProduto(
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
														"Produto salvo com sucesso!");
											} else {
												Info.display("Erro",
														"Erro ao salvar o produto!");
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

	private ProdutoDTO getDTO() {
		ProdutoDTO produtoDTO = this.display.getProdutoDTO();

		produtoDTO
				.setMedAbcValue(this.display.getMedAbcTextField().getValue() == null ? null
						: this.display.getMedAbcTextField().getValue()
								.longValue());

		produtoDTO.setNome(this.display.getNomeTextField().getValue());
		produtoDTO
				.setDescricao(this.display.getDescricaoTextField().getValue());
		produtoDTO
				.setPrecoValue(this.display.getPrecoTextField().getValue() == null ? null
						: this.display.getPrecoTextField().getValue()
								.doubleValue());
		produtoDTO.setUnidadeEntrada(this.display.getUnidadeEntradaTextField()
				.getValue());
		produtoDTO.setUnidadeVenda(this.display.getUnidadeVendaTextField()
				.getValue());

		produtoDTO.setLaboratorioId(display.getLaboratoriosComboBox()
				.getValue() == null ? null : display.getLaboratoriosComboBox()
				.getValue().getId());

		return produtoDTO;
	}

	@Override
	public void go(Widget widget) {
		this.display.getSimpleModal().show();
	}
}
