package br.com.vitafarma.web.client.mvp.presenter;

import br.com.vitafarma.web.client.ClientesServiceAsync;
import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.shared.dtos.ClienteDTO;
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

public class ClientesFormPresenter implements Presenter {
	public interface Display extends IVitafarmaI18nGateway {
		Button getSalvarButton();

		ClienteDTO getClienteDTO();

		TextField<String> getNomeTextField();

		NumberField getCpfTextField();

		NumberField getTelefoneTextField();

		TextField<String> getEnderecoTextField();

		TextField<String> getEmailTextField();

		CidadesComboBox getCidadesCB();

		boolean isValid();

		SimpleModal getSimpleModal();
	}

	private SimpleGrid<ClienteDTO> gridPanel;
	private Display display;

	public ClientesFormPresenter(Display display,
			SimpleGrid<ClienteDTO> gridPanel) {
		this.display = display;
		this.gridPanel = gridPanel;
		this.setListeners();
	}

	public ClientesFormPresenter(Display display) {
		this(display, null);
	}

	private void setListeners() {
		this.display.getSalvarButton().addSelectionListener(
				new SelectionListener<ButtonEvent>() {
					@Override
					public void componentSelected(ButtonEvent ce) {
						if (isValid()) {
							final ClientesServiceAsync service = Services
									.clientes();

							service.saveCliente(
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
													"Cliente salvo com sucesso!");
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

	private ClienteDTO getDTO() {
		ClienteDTO clienteDTO = this.display.getClienteDTO();

		clienteDTO.setNome(this.display.getNomeTextField().getValue());
		clienteDTO
				.setCpfValue(this.display.getCpfTextField().getValue() == null ? null
						: this.display.getCpfTextField().getValue().longValue());
		clienteDTO
				.setTelefone(this.display.getTelefoneTextField().getValue() == null ? null
						: this.display.getTelefoneTextField().getValue()
								.longValue());
		clienteDTO.setEndereco(this.display.getEnderecoTextField().getValue());
		clienteDTO.setEmail(this.display.getEmailTextField().getValue());
		clienteDTO
				.setCidadeId(this.display.getCidadesCB().getValue() == null ? null
						: this.display.getCidadesCB().getValue().getId());

		return clienteDTO;
	}

	@Override
	public void go(Widget widget) {
		this.display.getSimpleModal().show();
	}
}
