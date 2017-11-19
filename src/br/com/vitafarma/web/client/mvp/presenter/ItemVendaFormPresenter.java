package br.com.vitafarma.web.client.mvp.presenter;

import java.util.ArrayList;
import java.util.List;

import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.client.VendasServiceAsync;
import br.com.vitafarma.web.shared.dtos.ItemVendaDTO;
import br.com.vitafarma.web.shared.dtos.VendaDTO;
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

public class ItemVendaFormPresenter implements Presenter {
	public interface Display extends IVitafarmaI18nGateway {
		Button getSalvarButton();

		ProdutosComboBox getProdutosComboBox();

		VendaDTO getVendaDTO();

		ItemVendaDTO getItemVendaDTO();

		NumberField getPrecoUnitarioItemTF();

		NumberField getDescontoItemTF();

		NumberField getQuantidadeItemTF();

		NumberField getPrecoFinalItemTF();

		boolean isValid();

		SimpleModal getSimpleModal();
	}

	private SimpleGrid<ItemVendaDTO> grid;
	private Display display;

	public ItemVendaFormPresenter(Display display, SimpleGrid<ItemVendaDTO> grid) {
		this.grid = grid;
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

							List<ItemVendaDTO> itens = new ArrayList<ItemVendaDTO>();
							itens.add(getDTO());

							service.incluirItensVenda(
									display.getVendaDTO(),
									itens,
									new AbstractAsyncCallbackWithDefaultOnFailure<Boolean>(
											display.getI18nMessages()
													.erroSalvarVendaProduto(),
											display) {

										@Override
										public void onSuccess(Boolean result) {
											if (result) {
												display.getSimpleModal().hide();

												if (grid != null) {
													grid.updateList();
												}

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

	private ItemVendaDTO getDTO() {
		ItemVendaDTO itemVendaDTO = this.display.getItemVendaDTO();

		itemVendaDTO.setVendaId(this.display.getVendaDTO().getVendaId());
		itemVendaDTO.setClienteId(this.display.getVendaDTO().getClienteId());
		itemVendaDTO.setClienteString(this.display.getVendaDTO()
				.getClienteNome());
		itemVendaDTO.setClienteCpfValue(this.display.getVendaDTO()
				.getClienteCpfValue());
		itemVendaDTO.setClienteCpfString(this.display.getVendaDTO()
				.getClienteCpfString());
		itemVendaDTO.setProdutoId(this.display.getProdutosComboBox().getValue()
				.getId());
		itemVendaDTO.setProdutoString(this.display.getProdutosComboBox()
				.getValue().getNome());
		itemVendaDTO.setPrecoUnitarioValue(this.display.getPrecoUnitarioItemTF()
				.getValue().doubleValue());
		itemVendaDTO.setPrecoFinalValue(this.display.getPrecoFinalItemTF()
				.getValue().doubleValue());
		itemVendaDTO.setDescontoValue(this.display.getDescontoItemTF().getValue()
				.doubleValue());
		itemVendaDTO.setQuantidadeValue(this.display.getQuantidadeItemTF()
				.getValue().doubleValue());

		return itemVendaDTO;
	}

	@Override
	public void go(Widget widget) {
		this.display.getSimpleModal().show();
	}
}
