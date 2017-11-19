package br.com.vitafarma.web.client.mvp.presenter;

import br.com.vitafarma.web.client.FuncionariosServiceAsync;
import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.shared.dtos.FuncionarioDTO;
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

public class FuncionariosFormPresenter implements Presenter {
	public interface Display extends IVitafarmaI18nGateway {
		Button getSalvarButton();

		FuncionarioDTO getFuncionarioDTO();

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

	private SimpleGrid<FuncionarioDTO> gridPanel;
	private Display display;

	public FuncionariosFormPresenter(Display display,
			SimpleGrid<FuncionarioDTO> gridPanel) {
		this.gridPanel = gridPanel;
		this.display = display;
		this.setListeners();
	}

	public FuncionariosFormPresenter(Display display) {
		this(display, null);
	}

	private void setListeners() {
		this.display.getSalvarButton().addSelectionListener(
				new SelectionListener<ButtonEvent>() {
					@Override
					public void componentSelected(ButtonEvent ce) {
						if (isValid()) {
							final FuncionariosServiceAsync service = Services
									.funcionarios();

							service.saveFuncionario(
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
													"Funcionario salvo com sucesso!");
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

	private FuncionarioDTO getDTO() {
		FuncionarioDTO funcionarioDTO = this.display.getFuncionarioDTO();

		funcionarioDTO.setNome(this.display.getNomeTextField().getValue());
		funcionarioDTO
				.setCpfValue(this.display.getCpfTextField().getValue() == null ? null
						: this.display.getCpfTextField().getValue().longValue());
		funcionarioDTO.setSalarioValue(this.display.getSalarioNumberField()
				.getValue() == null ? null : this.display
				.getSalarioNumberField().getValue().doubleValue());
		funcionarioDTO.setDataAdimissao(this.display
				.getDataAdimissaoDateField().getValue());
		funcionarioDTO.setDataDemissao(this.display.getDataDemissaoDateField()
				.getValue());
		funcionarioDTO.setTelefone(this.display.getTelefoneTextField()
				.getValue() == null ? null : this.display
				.getTelefoneTextField().getValue().longValue());
		funcionarioDTO.setEndereco(this.display.getEnderecoTextField()
				.getValue());
		funcionarioDTO.setEmail(this.display.getEmailTextField().getValue());
		funcionarioDTO
				.setCidadeId(this.display.getCidadesCB().getValue() == null ? null
						: this.display.getCidadesCB().getValue().getId());

		return funcionarioDTO;
	}

	@Override
	public void go(Widget widget) {
		this.display.getSimpleModal().show();
	}
}
