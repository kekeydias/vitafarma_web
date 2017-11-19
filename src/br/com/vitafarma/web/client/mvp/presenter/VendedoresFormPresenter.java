package br.com.vitafarma.web.client.mvp.presenter;

import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.client.VendedoresServiceAsync;
import br.com.vitafarma.web.shared.dtos.VendedorDTO;
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
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.user.client.ui.Widget;

public class VendedoresFormPresenter implements Presenter {
	public interface Display extends IVitafarmaI18nGateway {
		Button getSalvarButton();

		VendedorDTO getVendedorDTO();

		TextField<String> getNomeTextField();

		NumberField getCpfTextField();

		NumberField getTelefoneTextField();

		TextField<String> getEnderecoTextField();

		TextField<String> getEmailTextField();

		NumberField getSalarioNumberField();

		DateField getDataAdimissaoDateField();

		DateField getDataDemissaoDateField();

		CidadesComboBox getCidadesCB();

		boolean isValid();

		SimpleModal getSimpleModal();
	}

	private SimpleGrid<VendedorDTO> gridPanel;
	private Display display;

	public VendedoresFormPresenter(Display display,
			SimpleGrid<VendedorDTO> gridPanel) {
		this.gridPanel = gridPanel;
		this.display = display;
		this.setListeners();
	}

	public VendedoresFormPresenter(Display display) {
		this(display, null);
	}

	private void setListeners() {
		this.display.getSalvarButton().addSelectionListener(
				new SelectionListener<ButtonEvent>() {
					@Override
					public void componentSelected(ButtonEvent ce) {
						if (isValid()) {
							final VendedoresServiceAsync service = Services
									.vendedores();

							service.saveVendedor(
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
													"Vendedor salvo com sucesso!");
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

	private VendedorDTO getDTO() {
		VendedorDTO vendedorDTO = this.display.getVendedorDTO();

		vendedorDTO.setNome(this.display.getNomeTextField().getValue());
		vendedorDTO
				.setCpfValue(this.display.getCpfTextField().getValue() == null ? null
						: this.display.getCpfTextField().getValue().longValue());
		vendedorDTO.setSalarioValue(this.display.getSalarioNumberField()
				.getValue() == null ? null : this.display
				.getSalarioNumberField().getValue().doubleValue());
		vendedorDTO.setDataAdimissao(this.display.getDataAdimissaoDateField()
				.getValue());
		vendedorDTO.setDataDemissao(this.display.getDataDemissaoDateField()
				.getValue());
		vendedorDTO
				.setTelefone(this.display.getTelefoneTextField().getValue() == null ? null
						: this.display.getTelefoneTextField().getValue()
								.longValue());
		vendedorDTO.setEndereco(this.display.getEnderecoTextField().getValue());
		vendedorDTO.setEmail(this.display.getEmailTextField().getValue());
		vendedorDTO
				.setCidadeId(this.display.getCidadesCB().getValue() == null ? null
						: this.display.getCidadesCB().getValue().getId());

		return vendedorDTO;
	}

	@Override
	public void go(Widget widget) {
		this.display.getSimpleModal().show();
	}
}
