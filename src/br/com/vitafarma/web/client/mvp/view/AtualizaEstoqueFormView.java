package br.com.vitafarma.web.client.mvp.view;

import br.com.vitafarma.web.client.mvp.presenter.AtualizaEstoqueFormPresenter;
import br.com.vitafarma.web.shared.dtos.ProdutoDTO;
import br.com.vitafarma.web.shared.mvp.view.MyComposite;
import br.com.vitafarma.web.shared.util.VitafarmaUtil;
import br.com.vitafarma.web.shared.util.resources.Resources;
import br.com.vitafarma.web.shared.util.view.ProdutosComboBox;
import br.com.vitafarma.web.shared.util.view.SimpleModal;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

public class AtualizaEstoqueFormView extends MyComposite implements
		AtualizaEstoqueFormPresenter.Display {
	private SimpleModal simpleModal;
	private FormPanel formPanel;
	private CheckBox atualizarTodosCheckBox;
	private ProdutosComboBox produtosComboBox;
	private ProdutoDTO produtoDTO;

	public AtualizaEstoqueFormView(ProdutoDTO produtoDTO) {
		this.produtoDTO = produtoDTO;
		this.initUI();
	}

	private void initUI() {
		String title = (this.getI18nConstants().informacoesGerais());

		this.simpleModal = new SimpleModal(title,
				Resources.DEFAULTS.atualizaEstoque16());
		this.simpleModal.setHeight(180);
		this.simpleModal.setWidth(600);
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
		geralFS.setHeading(this.getI18nConstants().atualizarEstoque());

		// Produto
		this.produtosComboBox = new ProdutosComboBox();
		this.produtosComboBox.setName(ProdutoDTO.PROPERTY_PRODUTO_NOME);
		this.produtosComboBox.setValue(this.produtoDTO);
		this.produtosComboBox.setFieldLabel(this.getI18nConstants().produto());
		this.produtosComboBox.setAllowBlank(true);
		this.produtosComboBox.setEmptyText("Selecione o Produto");
		geralFS.add(this.produtosComboBox, formData);

		this.atualizarTodosCheckBox = VitafarmaUtil.createCheckBox(this
				.getI18nConstants().atualizarTodoEstoque(), false);

		geralFS.add(this.atualizarTodosCheckBox, formData);

		this.formPanel.add(geralFS, formData);

		FormButtonBinding binding = new FormButtonBinding(this.formPanel);
		binding.addButton(this.simpleModal.getSalvarBt());

		this.createChangeListener();
		this.simpleModal.setFocusWidget(this.produtosComboBox);
	}

	private void createChangeListener() {
		// Quando marcamos a opção 'atualizar todos', a opção
		// de filtro por produto deve ser desabilitada
		Listener<FieldEvent> listener = new Listener<FieldEvent>() {
			public void handleEvent(FieldEvent be) {
				produtosComboBox.setEnabled(!atualizarTodosCheckBox.getValue());
				produtosComboBox
						.setEditable(!atualizarTodosCheckBox.getValue());
			}
		};

		this.atualizarTodosCheckBox.addListener(Events.OnChange, listener);
	}

	@Override
	public boolean isValid() {
		if (!this.formPanel.isValid()) {
			return false;
		}

		return (this.produtosComboBox.getValue() != null || this.atualizarTodosCheckBox
				.getValue());
	}

	@Override
	public Button getSalvarButton() {
		return this.simpleModal.getSalvarBt();
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
	public CheckBox getAtualizarTodosCheckBox() {
		return this.atualizarTodosCheckBox;
	}
}
