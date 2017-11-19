package br.com.vitafarma.web.client.mvp.view;

import br.com.vitafarma.web.client.mvp.presenter.ProdutosFormPresenter;
import br.com.vitafarma.web.shared.dtos.LaboratorioDTO;
import br.com.vitafarma.web.shared.dtos.ProdutoDTO;
import br.com.vitafarma.web.shared.mvp.view.MyComposite;
import br.com.vitafarma.web.shared.util.resources.Resources;
import br.com.vitafarma.web.shared.util.view.LaboratoriosComboBox;
import br.com.vitafarma.web.shared.util.view.SimpleModal;
import br.com.vitafarma.web.shared.util.view.UniqueDomain;
import br.com.vitafarma.web.shared.util.view.UniqueSizedNumberField;

import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

public class ProdutosFormView extends MyComposite implements
		ProdutosFormPresenter.Display {
	private SimpleModal simpleModal;
	private FormPanel formPanel;
	private NumberField medAbcTF;
	private TextField<String> nomeTF;
	private TextField<String> descricaoTF;
	private NumberField precoTF;
	private TextField<String> unidadeEntradaTF;
	private TextField<String> unidadeVendaTF;
	private ProdutoDTO produtoDTO;
	private LaboratoriosComboBox laboratoriosCB;
	private LaboratorioDTO laboratorioDTO;

	public ProdutosFormView(ProdutoDTO produtoDTO) {
		this.produtoDTO = produtoDTO;
		this.initUI();
	}

	public ProdutosFormView(ProdutoDTO produtoDTO, LaboratorioDTO laboratorioDTO) {
		this.produtoDTO = produtoDTO;
		this.laboratorioDTO = laboratorioDTO;
		this.initUI();
	}

	private void initUI() {
		String title = ((this.produtoDTO.getId() == null) ? "Insercao de Produto"
				: "Edicao de Produto");

		this.simpleModal = new SimpleModal(title,
				Resources.DEFAULTS.produtos16());
		this.simpleModal.setHeight(350);
		this.simpleModal.setWidth(430);
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

		this.medAbcTF = new UniqueSizedNumberField(this, UniqueDomain.PRODUTO,
				this.getI18nMessages().errorDuplicatedMedAbcfarmaProduto());
		this.medAbcTF.setName(ProdutoDTO.PROPERTY_PRODUTO_MED_ABC_VALUE);
		this.medAbcTF.setValue(this.produtoDTO.getMedAbcValue());
		this.medAbcTF.setFieldLabel(this.getI18nConstants()
				.codigoProdutoAbcFarma());
		this.medAbcTF.setAllowBlank(false);
		this.medAbcTF.setMinLength(1);
		this.medAbcTF.setMaxLength(20);
		this.medAbcTF.setEmptyText("Preencha o codigo AbcFarma");
		geralFS.add(this.medAbcTF, formData);

		this.nomeTF = new TextField<String>();
		this.nomeTF.setName(ProdutoDTO.PROPERTY_PRODUTO_NOME);
		this.nomeTF.setValue(this.produtoDTO.getNome());
		this.nomeTF.setFieldLabel(this.getI18nConstants().nome());
		this.nomeTF.setAllowBlank(false);
		this.nomeTF.setMinLength(1);
		this.nomeTF.setMaxLength(50);
		this.nomeTF.setEmptyText("Preencha o nome");
		geralFS.add(this.nomeTF, formData);

		this.descricaoTF = new TextField<String>();
		this.descricaoTF.setName(ProdutoDTO.PROPERTY_PRODUTO_DESCRICAO);
		this.descricaoTF.setValue(this.produtoDTO.getDescricao());
		this.descricaoTF.setFieldLabel(this.getI18nConstants().descricao());
		this.descricaoTF.setAllowBlank(false);
		this.descricaoTF.setMinLength(1);
		this.descricaoTF.setMaxLength(50);
		this.descricaoTF.setEmptyText("Preencha a descricao");
		geralFS.add(this.descricaoTF, formData);

		// Laboratório
		this.laboratoriosCB = new LaboratoriosComboBox();
		this.laboratoriosCB.setName(LaboratorioDTO.PROPERTY_LABORATORIO_NOME);
		this.laboratoriosCB
				.setFieldLabel(this.getI18nConstants().laboratorio());
		this.laboratoriosCB.setAllowBlank(true);
		this.laboratoriosCB.setValue(this.laboratorioDTO);
		this.laboratoriosCB.setEmptyText("Selecione o laboratorio");
		geralFS.add(this.laboratoriosCB, formData);

		this.precoTF = new NumberField();
		this.precoTF.setName(ProdutoDTO.PROPERTY_PRODUTO_PRECO_VALUE);
		this.precoTF.setValue(this.produtoDTO.getPrecoValue());
		this.precoTF.setFieldLabel(this.getI18nConstants().precoVenda());
		this.precoTF.setAllowBlank(false);
		this.precoTF.setMinLength(1);
		this.precoTF.setMaxLength(20);
		this.precoTF.setEmptyText("Preencha o preco");
		geralFS.add(this.precoTF, formData);

		this.unidadeEntradaTF = new TextField<String>();
		this.unidadeEntradaTF
				.setName(ProdutoDTO.PROPERTY_PRODUTO_UNIDADE_ENTRADA);
		this.unidadeEntradaTF.setValue(this.produtoDTO.getUnidadeEntrada());
		this.unidadeEntradaTF.setFieldLabel(this.getI18nConstants()
				.unidadeEntrada());
		this.unidadeEntradaTF.setAllowBlank(true);
		this.unidadeEntradaTF.setMaxLength(20);
		this.unidadeEntradaTF.setEmptyText("Preencha a unidade de entrada");
		geralFS.add(this.unidadeEntradaTF, formData);

		this.unidadeVendaTF = new TextField<String>();
		this.unidadeVendaTF.setName(ProdutoDTO.PROPERTY_PRODUTO_UNIDADE_VENDA);
		this.unidadeVendaTF.setValue(this.produtoDTO.getUnidadeVenda());
		this.unidadeVendaTF.setFieldLabel(this.getI18nConstants()
				.unidadeVenda());
		this.unidadeVendaTF.setAllowBlank(true);
		this.unidadeVendaTF.setMaxLength(20);
		this.unidadeVendaTF.setEmptyText("Preencha a unidade de venda");
		geralFS.add(this.unidadeVendaTF, formData);

		this.formPanel.add(geralFS, formData);

		FormButtonBinding binding = new FormButtonBinding(this.formPanel);
		binding.addButton(this.simpleModal.getSalvarBt());

		this.simpleModal.setFocusWidget(this.medAbcTF);
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
	public TextField<String> getNomeTextField() {
		return this.nomeTF;
	}

	@Override
	public TextField<String> getDescricaoTextField() {
		return this.descricaoTF;
	}

	@Override
	public SimpleModal getSimpleModal() {
		return this.simpleModal;
	}

	@Override
	public NumberField getPrecoTextField() {
		return this.precoTF;
	}

	@Override
	public ProdutoDTO getProdutoDTO() {
		return this.produtoDTO;
	}

	@Override
	public TextField<String> getUnidadeEntradaTextField() {
		return this.unidadeEntradaTF;
	}

	@Override
	public TextField<String> getUnidadeVendaTextField() {
		return this.unidadeVendaTF;
	}

	@Override
	public LaboratoriosComboBox getLaboratoriosComboBox() {
		return this.laboratoriosCB;
	}

	@Override
	public NumberField getMedAbcTextField() {
		return this.medAbcTF;
	}
}
