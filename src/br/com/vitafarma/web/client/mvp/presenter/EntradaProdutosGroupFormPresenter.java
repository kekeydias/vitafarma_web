package br.com.vitafarma.web.client.mvp.presenter;

import br.com.vitafarma.web.client.EntradaProdutosServiceAsync;
import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.shared.dtos.EntradaProdutoGroupDTO;
import br.com.vitafarma.web.shared.i18n.IVitafarmaI18nGateway;
import br.com.vitafarma.web.shared.mvp.presenter.Presenter;
import br.com.vitafarma.web.shared.util.VitafarmaDate;
import br.com.vitafarma.web.shared.util.view.AbstractAsyncCallbackWithDefaultOnFailure;
import br.com.vitafarma.web.shared.util.view.FornecedoresComboBox;
import br.com.vitafarma.web.shared.util.view.SimpleGrid;
import br.com.vitafarma.web.shared.util.view.SimpleModal;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Time;
import com.extjs.gxt.ui.client.widget.form.TimeField;
import com.google.gwt.user.client.ui.Widget;

public class EntradaProdutosGroupFormPresenter implements Presenter {
	public interface Display extends IVitafarmaI18nGateway {
		Button getSalvarButton();

		FornecedoresComboBox getFornecedoresComboBox();

		TextField<String> getNotaFiscalTextField();

		DateField getDataEntradaTextField();

		TimeField getHorarioEntradaTextField();

		EntradaProdutoGroupDTO getEntradaProdutoGroupDTO();

		boolean isValid();

		SimpleModal getSimpleModal();
	}

	private SimpleGrid<EntradaProdutoGroupDTO> gridPanel;
	private Display display;

	public EntradaProdutosGroupFormPresenter(Display display) {
		this(display, null);
	}

	public EntradaProdutosGroupFormPresenter(Display display,
			SimpleGrid<EntradaProdutoGroupDTO> gridPanel) {
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

							service.saveEntradaProdutoGroup(
									getDTO(),
									new AbstractAsyncCallbackWithDefaultOnFailure<Boolean>(
											display.getI18nMessages()
													.erroSalvarVenda(), display) {

										@Override
										public void onSuccess(Boolean result) {

											display.getSimpleModal().hide();

											if (gridPanel != null) {
												gridPanel.updateList();
											}

											Info.display("Salvo",
													"Entrada de produtos salva com sucesso!");
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

	private EntradaProdutoGroupDTO getDTO() {
		EntradaProdutoGroupDTO entradaProdutoGroupDTO = this.display
				.getEntradaProdutoGroupDTO();

		// Fornecedor da entrada de produtos
		entradaProdutoGroupDTO.setFornecedorId(this.display
				.getFornecedoresComboBox().getValue().getId());

		// Nota Fiscal da entrada de produtos
		entradaProdutoGroupDTO.setNotaFiscalCodigo(this.display
				.getNotaFiscalTextField().getValue());

		// Data da entrada de produtos
		VitafarmaDate dataEntrada = new VitafarmaDate(this.display
				.getDataEntradaTextField().getValue());
		entradaProdutoGroupDTO.setDataEntradaValue(dataEntrada);

		// Horário da entrada de produtos
		Time time = this.display.getHorarioEntradaTextField().getValue();
		entradaProdutoGroupDTO.setEntradaProdutoHora(time == null ? null : time
				.getHour());
		entradaProdutoGroupDTO.setEntradaProdutoMinuto(time == null ? null
				: time.getMinutes());

		return entradaProdutoGroupDTO;
	}

	@Override
	public void go(Widget widget) {
		this.display.getSimpleModal().show();
	}
}
