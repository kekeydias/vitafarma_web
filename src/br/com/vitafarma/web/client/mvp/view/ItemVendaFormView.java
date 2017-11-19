package br.com.vitafarma.web.client.mvp.view;

import br.com.vitafarma.web.client.mvp.presenter.ItemVendaFormPresenter;
import br.com.vitafarma.web.shared.dtos.ItemVendaDTO;
import br.com.vitafarma.web.shared.dtos.ProdutoDTO;
import br.com.vitafarma.web.shared.dtos.VendaDTO;
import br.com.vitafarma.web.shared.mvp.view.MyComposite;
import br.com.vitafarma.web.shared.util.VitafarmaUtil;
import br.com.vitafarma.web.shared.util.resources.Resources;
import br.com.vitafarma.web.shared.util.view.ProdutosComboBox;
import br.com.vitafarma.web.shared.util.view.SimpleModal;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

public class ItemVendaFormView extends MyComposite implements
		ItemVendaFormPresenter.Display {
	private ProdutosComboBox produtosComboBox;
	private SimpleModal simpleModal;
	private FormPanel formPanel;
	private ItemVendaDTO itemVendaDTO;
	private VendaDTO vendaDTO;
	private NumberField precoUnitarioTF;
	private NumberField descontoTF;
	private NumberField precoFinalTF;
	private NumberField quantidadeTF;
	private ProdutoDTO produtoDTO;

	public ItemVendaFormView(ItemVendaDTO itemVendaDTO, VendaDTO vendaDTO) {
		this(itemVendaDTO, null, vendaDTO);
	}

	public ItemVendaFormView(ItemVendaDTO itemVendaDTO, ProdutoDTO produtoDTO,
			VendaDTO vendaDTO) {
		this.itemVendaDTO = itemVendaDTO;
		this.vendaDTO = vendaDTO;
		this.produtoDTO = produtoDTO;
		this.initUI();
	}

	private void initUI() {
		String title = "Incluir item";
		this.simpleModal = new SimpleModal(title,
				Resources.DEFAULTS.incluirItemVenda16());

		this.simpleModal.setHeight(280);
		this.simpleModal.setWidth(450);
		this.createForm();
		this.simpleModal.setContent(this.formPanel);
	}

	private void createForm() {
		FormData formData = new FormData("-20");

		this.formPanel = new FormPanel();
		this.formPanel.setHeaderVisible(false);
		this.formPanel.setLayout(new FlowLayout());

		FieldSet geralFS = new FieldSet();
		FormLayout formLayout = new FormLayout(LabelAlign.RIGHT);
		formLayout.setLabelWidth(75);
		geralFS.setLayout(formLayout);
		geralFS.setHeading(this.getI18nConstants().informacoesGerais());

		// Produto
		this.produtosComboBox = new ProdutosComboBox();
		this.produtosComboBox.setName(this.getI18nConstants().produto());
		this.produtosComboBox.setFieldLabel(this.getI18nConstants().produto());
		this.produtosComboBox.setAllowBlank(false);
		this.produtosComboBox.setValue(this.produtoDTO);
		this.produtosComboBox.setEmptyText("Selecione o produto");
		geralFS.add(this.produtosComboBox, formData);

		// Preço Unitario
		this.precoUnitarioTF = new NumberField();
		this.precoUnitarioTF
				.setName(ItemVendaDTO.PROPERTY_ITEM_VENDA_PRECO_UNITARIO_VALUE);
		this.precoUnitarioTF
				.setValue(this.itemVendaDTO.getPrecoUnitarioValue());
		this.precoUnitarioTF.setFieldLabel(this.getI18nConstants()
				.precoUnitario());
		this.precoUnitarioTF.setReadOnly(false);
		geralFS.add(this.precoUnitarioTF, formData);

		// Desconto
		this.descontoTF = new NumberField();
		this.descontoTF
				.setName(ItemVendaDTO.PROPERTY_ITEM_VENDA_DESCONTO_ITEM_VALUE);
		this.descontoTF.setValue(this.itemVendaDTO.getDescontoValue());
		this.descontoTF.setFieldLabel(this.getI18nConstants().descontoItem());
		this.descontoTF.setAllowBlank(true);
		this.descontoTF.setMinValue(0);
		this.descontoTF.setMaxValue(100);
		geralFS.add(this.descontoTF, formData);

		// Quantidade
		this.quantidadeTF = new NumberField();
		this.quantidadeTF
				.setName(ItemVendaDTO.PROPERTY_ITEM_VENDA_QUANTIDADE_VALUE);
		this.quantidadeTF.setValue(this.itemVendaDTO.getQuantidadeValue());
		this.quantidadeTF.setFieldLabel(this.getI18nConstants().quantidade());
		this.quantidadeTF.setAllowBlank(false);
		this.quantidadeTF.setEmptyText("Preencha a quantidade");
		geralFS.add(this.quantidadeTF, formData);

		// Preço Final
		this.precoFinalTF = new NumberField();
		this.precoFinalTF
				.setName(ItemVendaDTO.PROPERTY_ITEM_VENDA_PRECO_FINAL_VALUE);
		this.precoFinalTF.setValue(this.itemVendaDTO.getPrecoFinalValue());
		this.precoFinalTF.setFieldLabel(this.getI18nConstants().precoFinal());
		this.precoFinalTF.setReadOnly(false);
		geralFS.add(this.precoFinalTF, formData);

		this.formPanel.add(geralFS, formData);

		FormButtonBinding binding = new FormButtonBinding(this.formPanel);
		binding.addButton(this.simpleModal.getSalvarBt());

		this.createChangeListeners();

		this.simpleModal.setFocusWidget(this.produtosComboBox);
	}

	private void createChangeListeners() {
		// Quando for alterado o produto, o preco de custo deverá ser atualizado
		// corretamente
		this.produtosComboBox
				.addSelectionChangedListener(new SelectionChangedListener<ProdutoDTO>() {
					@Override
					public void selectionChanged(
							SelectionChangedEvent<ProdutoDTO> se) {
						ProdutoDTO dto = se.getSelection().get(0);
						Double precoItem = dto.getPrecoValue();
						precoUnitarioTF.setValue(precoItem);
					}
				});

		// Quando a quantidade do item ou seu desconto forem
		// alterados, devemos atualizar o preço final corretamente
		Listener<FieldEvent> listener = new Listener<FieldEvent>() {
			public void handleEvent(FieldEvent be) {
				Double precoUnitario = precoUnitarioTF.getValue() == null ? 0.0
						: precoUnitarioTF.getValue().doubleValue();
				Double quantidade = quantidadeTF.getValue() == null ? 0.0
						: quantidadeTF.getValue().doubleValue();
				Double desconto = descontoTF.getValue() == null ? 0.0
						: descontoTF.getValue().doubleValue();

				precoFinalTF.setValue(VitafarmaUtil.getPrecoFinal(
						precoUnitario, quantidade, desconto));
			}
		};

		this.quantidadeTF.addListener(Events.OnBlur, listener);
		this.descontoTF.addListener(Events.OnLoseCapture, listener);
	}

	@Override
	public boolean isValid() {
		return this.formPanel.isValid();
	}

	@Override
	public VendaDTO getVendaDTO() {
		return this.vendaDTO;
	}

	@Override
	public Button getSalvarButton() {
		return this.simpleModal.getSalvarBt();
	}

	@Override
	public ItemVendaDTO getItemVendaDTO() {
		return this.itemVendaDTO;
	}

	@Override
	public SimpleModal getSimpleModal() {
		return this.simpleModal;
	}

	@Override
	public ProdutosComboBox getProdutosComboBox() {
		return this.produtosComboBox;
	}

	@Override
	public NumberField getQuantidadeItemTF() {
		return this.quantidadeTF;
	}

	@Override
	public NumberField getPrecoUnitarioItemTF() {
		return this.precoUnitarioTF;
	}

	@Override
	public NumberField getDescontoItemTF() {
		return this.descontoTF;
	}

	@Override
	public NumberField getPrecoFinalItemTF() {
		return this.precoFinalTF;
	}
}
