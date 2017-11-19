package br.com.vitafarma.web.client.mvp.presenter;

import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.client.VendasServiceAsync;
import br.com.vitafarma.web.shared.dtos.VendaDTO;
import br.com.vitafarma.web.shared.i18n.IVitafarmaI18nGateway;
import br.com.vitafarma.web.shared.mvp.presenter.Presenter;
import br.com.vitafarma.web.shared.util.view.AbstractAsyncCallbackWithDefaultOnFailure;
import br.com.vitafarma.web.shared.util.view.ClientesComboBox;
import br.com.vitafarma.web.shared.util.view.SimpleGrid;
import br.com.vitafarma.web.shared.util.view.SimpleModal;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.Time;
import com.extjs.gxt.ui.client.widget.form.TimeField;
import com.google.gwt.user.client.ui.Widget;

public class VendasFormPresenter implements Presenter {
	public interface Display extends IVitafarmaI18nGateway {
		Button getSalvarButton();

		ClientesComboBox getClientesComboBox();

		DateField getDataVendaTextField();

		TimeField getHorarioVendaTextField();

		NumberField getDescontoTextField();

		VendaDTO getVendaDTO();

		boolean isValid();

		SimpleModal getSimpleModal();
	}

	private SimpleGrid<VendaDTO> gridPanel;
	private Display display;

	public VendasFormPresenter(Display display) {
		this(display, null);
	}

	public VendasFormPresenter(Display display, SimpleGrid<VendaDTO> gridPanel) {
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
							final VendasServiceAsync service = Services
									.vendas();

							service.saveVenda(
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
													"Venda salva com sucesso!");
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

	private VendaDTO getDTO() {
		VendaDTO vendaDTO = this.display.getVendaDTO();

		// Cliente da venda
		vendaDTO.setClienteId(this.display.getClientesComboBox().getValue()
				.getId());

		// Desconto da venda
		vendaDTO.setDescontoValue(this.display.getDescontoTextField()
				.getValue().doubleValue());

		// Data da venda
		vendaDTO.setVendaDataValue(this.display.getDataVendaTextField().getValue());

		// Horário da venda
		Time time = this.display.getHorarioVendaTextField().getValue();
		vendaDTO.setVendaHora(time == null ? null : time.getHour());
		vendaDTO.setVendaMinuto(time == null ? null : time.getMinutes());

		return vendaDTO;
	}

	@Override
	public void go(Widget widget) {
		this.display.getSimpleModal().show();
	}
}
