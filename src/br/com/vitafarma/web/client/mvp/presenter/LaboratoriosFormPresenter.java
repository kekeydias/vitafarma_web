package br.com.vitafarma.web.client.mvp.presenter;

import br.com.vitafarma.web.client.LaboratoriosServiceAsync;
import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.shared.dtos.LaboratorioDTO;
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

public class LaboratoriosFormPresenter implements Presenter {
	public interface Display extends IVitafarmaI18nGateway {
		Button getSalvarButton();

		LaboratorioDTO getLaboratorioDTO();

		NumberField getMedLabTextField();

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

	private SimpleGrid<LaboratorioDTO> gridPanel;
	private Display display;

	public LaboratoriosFormPresenter(Display display,
			SimpleGrid<LaboratorioDTO> gridPanel) {
		this.gridPanel = gridPanel;
		this.display = display;
		this.setListeners();
	}

	public LaboratoriosFormPresenter(Display display) {
		this(display, null);
	}

	private void setListeners() {
		this.display.getSalvarButton().addSelectionListener(
				new SelectionListener<ButtonEvent>() {
					@Override
					public void componentSelected(ButtonEvent ce) {
						if (isValid()) {
							final LaboratoriosServiceAsync service = Services
									.laboratorios();

							service.saveLaboratorio(
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
													"Laboratorio salvo com sucesso!");
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

	private LaboratorioDTO getDTO() {
		LaboratorioDTO laboratorioDTO = this.display.getLaboratorioDTO();

		laboratorioDTO.setMedLabValue(this.display.getMedLabTextField()
				.getValue() == null ? null : this.display.getMedLabTextField()
				.getValue().longValue());
		laboratorioDTO.setNome(this.display.getNomeTextField().getValue());
		laboratorioDTO.setNomeFantasia(this.display.getNomeFantasiaTextField()
				.getValue());
		laboratorioDTO
				.setCnpjValue(this.display.getCnpjTextField().getValue() == null ? null
						: this.display.getCnpjTextField().getValue()
								.longValue());
		laboratorioDTO.setTelefoneValue(this.display.getTelefoneTextField()
				.getValue() == null ? null : this.display
				.getTelefoneTextField().getValue().longValue());
		laboratorioDTO.setEndereco(this.display.getEnderecoTextField()
				.getValue());
		laboratorioDTO.setEmail(this.display.getEmailTextField().getValue());
		laboratorioDTO
				.setCidadeId(this.display.getCidadesCB().getValue() == null ? null
						: this.display.getCidadesCB().getValue().getId());

		return laboratorioDTO;
	}

	@Override
	public void go(Widget widget) {
		this.display.getSimpleModal().show();
	}
}
