package br.com.vitafarma.web.client.mvp.presenter;

import br.com.vitafarma.web.client.FornecedoresServiceAsync;
import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.shared.dtos.FornecedorDTO;
import br.com.vitafarma.web.shared.i18n.IVitafarmaI18nGateway;
import br.com.vitafarma.web.shared.mvp.presenter.Presenter;
import br.com.vitafarma.web.shared.util.view.AbstractAsyncCallbackWithDefaultOnFailure;
import br.com.vitafarma.web.shared.util.view.CidadesComboBox;
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

public class FornecedoresFormPresenter implements Presenter {
	public interface Display extends IVitafarmaI18nGateway {
		Button getSalvarButton();

		FornecedorDTO getFornecedorDTO();

		TextField<String> getNomeTextField();

		NumberField getCnpjTextField();

		TextField<String> getNomeFantasiaTextField();

		NumberField getTelefoneTextField();

		TextField<String> getEnderecoTextField();

		TextField<String> getEmailTextField();

		CidadesComboBox getCidadesCB();

		boolean isValid();

		SimpleModal getSimpleModal();
	}

	private SimpleGrid<FornecedorDTO> gridPanel;
	private Display display;

	public FornecedoresFormPresenter(Display display,
			SimpleGrid<FornecedorDTO> gridPanel) {
		this.gridPanel = gridPanel;
		this.display = display;
		this.setListeners();
	}

	public FornecedoresFormPresenter(Display display) {
		this(display, null);
	}

	private void setListeners() {
		this.display.getSalvarButton().addSelectionListener(
				new SelectionListener<ButtonEvent>() {
					@Override
					public void componentSelected(ButtonEvent ce) {
						if (isValid()) {
							final FornecedoresServiceAsync service = Services
									.fornecedores();

							service.saveFornecedor(
									getDTO(),
									new AbstractAsyncCallbackWithDefaultOnFailure<Boolean>(
											display) {
										@Override
										public void onSuccess(Boolean result) {
											display.getSimpleModal().hide();

											if (gridPanel != null) {
												gridPanel.updateList();
											}

											Info.display("Salvo",
													"Fornecedor salvo com sucesso!");
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

	private FornecedorDTO getDTO() {
		FornecedorDTO fornecedorDTO = this.display.getFornecedorDTO();

		fornecedorDTO.setNome(this.display.getNomeTextField().getValue());
		fornecedorDTO.setNomeFantasia(this.display.getNomeFantasiaTextField()
				.getValue());
		fornecedorDTO
				.setCnpjValue(this.display.getCnpjTextField().getValue() == null ? null
						: this.display.getCnpjTextField().getValue()
								.longValue());
		fornecedorDTO.setTelefoneValue(this.display.getTelefoneTextField()
				.getValue() == null ? null : this.display
				.getTelefoneTextField().getValue().longValue());
		fornecedorDTO.setEndereco(this.display.getEnderecoTextField()
				.getValue());
		fornecedorDTO.setEmail(this.display.getEmailTextField().getValue());
		fornecedorDTO
				.setCidadeId(this.display.getCidadesCB().getValue() == null ? null
						: this.display.getCidadesCB().getValue().getId());

		return fornecedorDTO;
	}

	@Override
	public void go(Widget widget) {
		this.display.getSimpleModal().show();
	}
}
