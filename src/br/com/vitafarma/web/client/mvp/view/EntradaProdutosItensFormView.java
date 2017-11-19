package br.com.vitafarma.web.client.mvp.view;

import br.com.vitafarma.web.client.mvp.presenter.EntradaProdutosItensFormPresenter;
import br.com.vitafarma.web.shared.dtos.EntradaProdutoDTO;
import br.com.vitafarma.web.shared.dtos.EntradaProdutoGroupDTO;
import br.com.vitafarma.web.shared.dtos.ProdutoDTO;
import br.com.vitafarma.web.shared.mvp.view.MyComposite;
import br.com.vitafarma.web.shared.util.resources.Resources;
import br.com.vitafarma.web.shared.util.view.ProdutosComboBox;
import br.com.vitafarma.web.shared.util.view.SimpleModal;

import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

public class EntradaProdutosItensFormView extends MyComposite implements
		EntradaProdutosItensFormPresenter.Display {
	private SimpleModal simpleModal;
	private FormPanel formPanel;
	private NumberField quantidadeTF;
	private NumberField precoUnitarioTF;
	private ProdutosComboBox produtosComboBox;
	private EntradaProdutoGroupDTO entradaProdutoGroupDTO;
	private EntradaProdutoDTO entradaProdutoDTO;
	private ProdutoDTO produtoDTO;

	public EntradaProdutosItensFormView(
			EntradaProdutoGroupDTO entradaProdutoGroupDTO,
			EntradaProdutoDTO entradaProdutoDTO) {
		this(entradaProdutoGroupDTO, entradaProdutoDTO, null);
	}

	public EntradaProdutosItensFormView(
			EntradaProdutoGroupDTO entradaProdutoGroupDTO,
			EntradaProdutoDTO entradaProdutoDTO, ProdutoDTO produtoDTO) {
		this.entradaProdutoDTO = entradaProdutoDTO;
		this.entradaProdutoGroupDTO = entradaProdutoGroupDTO;
		this.produtoDTO = produtoDTO;
		this.initUI();
	}

	private void initUI() {
		String title = ((this.entradaProdutoDTO.getId() == null) ? "Novo Item"
				: "Edicao de Item");

		this.simpleModal = new SimpleModal(title,
				Resources.DEFAULTS.produtos16());
		this.simpleModal.setHeight(230);
		this.simpleModal.setWidth(500);
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
		this.produtosComboBox
				.setName(EntradaProdutoDTO.PROPERTY_ENTRADA_PRODUTO_PRODUTO_STRING);
		this.produtosComboBox.setFieldLabel(this.getI18nConstants().produto());
		this.produtosComboBox.setAllowBlank(false);
		this.produtosComboBox.setValue(this.produtoDTO);
		this.produtosComboBox.setEmptyText("Selecione o produto");
		geralFS.add(this.produtosComboBox, formData);

		// Preço Unitário
		this.precoUnitarioTF = new NumberField();
		this.precoUnitarioTF
				.setName(EntradaProdutoDTO.PROPERTY_ENTRADA_PRODUTO_PRECO_UNITARIO_VALUE);
		this.precoUnitarioTF.setValue(this.entradaProdutoDTO
				.getPrecoUnitarioValue());
		this.precoUnitarioTF.setFieldLabel(this.getI18nConstants()
				.precoUnitario());
		this.precoUnitarioTF.setAllowBlank(false);
		this.precoUnitarioTF.setMinLength(1);
		this.precoUnitarioTF.setMaxLength(50);
		this.precoUnitarioTF.setEmptyText("Preencha o preco unitario");
		geralFS.add(this.precoUnitarioTF, formData);

		// Quantidade
		this.quantidadeTF = new NumberField();
		this.quantidadeTF
				.setName(EntradaProdutoDTO.PROPERTY_ENTRADA_PRODUTO_QUANTIDADE_VALUE);
		this.quantidadeTF.setValue(this.entradaProdutoDTO.getQuantidadeValue());
		this.quantidadeTF.setFieldLabel("Quantidade");
		this.quantidadeTF.setAllowBlank(false);
		this.quantidadeTF.setMinLength(1);
		this.quantidadeTF.setMaxLength(50);
		this.quantidadeTF.setEmptyText("Preencha a quantidade");
		geralFS.add(this.quantidadeTF, formData);

		this.formPanel.add(geralFS, formData);

		FormButtonBinding binding = new FormButtonBinding(this.formPanel);
		binding.addButton(this.simpleModal.getSalvarBt());

		this.simpleModal.setFocusWidget(this.produtosComboBox);
	}

	@Override
	public boolean isValid() {
		return this.formPanel.isValid();
	}

	@Override
	public Button getSalvarButton() {
		return this.simpleModal.getSalvarBt();
	}

	@Override
	public NumberField getQuantidadeTextField() {
		return this.quantidadeTF;
	}

	@Override
	public SimpleModal getSimpleModal() {
		return this.simpleModal;
	}

	@Override
	public NumberField getPrecoUnitarioTextField() {
		return this.precoUnitarioTF;
	}

	@Override
	public EntradaProdutoDTO getEntradaProdutoDTO() {
		return this.entradaProdutoDTO;
	}

	@Override
	public ProdutosComboBox getProdutosComboBox() {
		return this.produtosComboBox;
	}

	@Override
	public EntradaProdutoGroupDTO getEntradaProdutoGroupDTO() {
		return this.entradaProdutoGroupDTO;
	}
}
