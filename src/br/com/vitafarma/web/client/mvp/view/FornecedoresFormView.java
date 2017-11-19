package br.com.vitafarma.web.client.mvp.view;

import br.com.vitafarma.web.client.mvp.presenter.FornecedoresFormPresenter;
import br.com.vitafarma.web.shared.dtos.CidadeDTO;
import br.com.vitafarma.web.shared.dtos.FornecedorDTO;
import br.com.vitafarma.web.shared.mvp.view.MyComposite;
import br.com.vitafarma.web.shared.util.resources.Resources;
import br.com.vitafarma.web.shared.util.view.CidadesComboBox;
import br.com.vitafarma.web.shared.util.view.SimpleModal;
import br.com.vitafarma.web.shared.util.view.SizedNumberField;
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

public class FornecedoresFormView extends MyComposite implements
		FornecedoresFormPresenter.Display {
	private SimpleModal simpleModal;
	private FormPanel formPanel;
	private TextField<String> nomeTF;
	private TextField<String> nomeFantasiaTF;
	private SizedNumberField cnpjTF;
	private NumberField telefoneTF;
	private TextField<String> enderecoTF;
	private TextField<String> emailTF;
	private CidadesComboBox cidadesCB;
	private FornecedorDTO fornecedorDTO;
	private CidadeDTO cidadeDTO;

	public FornecedoresFormView(FornecedorDTO fornecedorDTO) {
		this(fornecedorDTO, null);
	}

	public FornecedoresFormView(FornecedorDTO fornecedorDTO, CidadeDTO cidadeDTO) {
		this.fornecedorDTO = fornecedorDTO;
		this.cidadeDTO = cidadeDTO;
		this.initUI();
	}

	private void initUI() {
		String title = ((this.fornecedorDTO.getId() == null) ? "Insercao de Fornecedor"
				: "Edicao de Fornecedor");

		this.simpleModal = new SimpleModal(title,
				Resources.DEFAULTS.fornecedores16());
		this.simpleModal.setHeight(350);
		this.simpleModal.setWidth(550);
		createForm();
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

		// Nome
		this.nomeTF = new TextField<String>();
		this.nomeTF.setName(FornecedorDTO.PROPERTY_FORNECEDOR_NOME);
		this.nomeTF.setValue(this.fornecedorDTO.getNome());
		this.nomeTF.setFieldLabel("Nome");
		this.nomeTF.setAllowBlank(false);
		this.nomeTF.setMinLength(1);
		this.nomeTF.setMaxLength(50);
		this.nomeTF.setEmptyText("Preencha o nome");
		geralFS.add(this.nomeTF, formData);

		// Nome Fantasia
		this.nomeFantasiaTF = new TextField<String>();
		this.nomeFantasiaTF
				.setName(FornecedorDTO.PROPERTY_FORNECEDOR_NOME_FANTASIA);
		this.nomeFantasiaTF.setValue(this.fornecedorDTO.getNomeFantasia());
		this.nomeFantasiaTF.setFieldLabel("Nome Fantasia");
		this.nomeFantasiaTF.setAllowBlank(false);
		this.nomeFantasiaTF.setMinLength(1);
		this.nomeFantasiaTF.setMaxLength(50);
		this.nomeFantasiaTF.setEmptyText("Preencha o nome fantasia");
		geralFS.add(this.nomeFantasiaTF, formData);

		// Cnpj
		this.cnpjTF = new UniqueSizedNumberField(this, 14, 14,
				UniqueDomain.FORNECEDOR, this.getI18nMessages()
						.errorDuplicatedCnpjFornecedor());
		this.cnpjTF.setName(FornecedorDTO.PROPERTY_FORNECEDOR_CNPJ_VALUE);
		this.cnpjTF.setValue(this.fornecedorDTO.getCnpjValue());
		this.cnpjTF.setFieldLabel("Cnpj");
		this.cnpjTF.setAllowBlank(false);
		this.cnpjTF.setMinLength(14);
		this.cnpjTF.setMaxLength(14);
		this.cnpjTF.setEmptyText("Preencha o cnpj");
		geralFS.add(this.cnpjTF, formData);

		// Telefone
		this.telefoneTF = new NumberField();
		this.telefoneTF
				.setName(FornecedorDTO.PROPERTY_FORNECEDOR_TELEFONE_VALUE);
		this.telefoneTF.setValue(this.fornecedorDTO.getTelefoneValue());
		this.telefoneTF.setFieldLabel("Telefone");
		this.telefoneTF.setAllowBlank(true);
		this.telefoneTF.setEmptyText("Preencha o telefone");
		geralFS.add(this.telefoneTF, formData);

		// Endereço
		this.enderecoTF = new TextField<String>();
		this.enderecoTF.setName(FornecedorDTO.PROPERTY_FORNECEDOR_ENDERECO);
		this.enderecoTF.setValue(this.fornecedorDTO.getEndereco());
		this.enderecoTF.setFieldLabel("Endereco");
		this.enderecoTF.setAllowBlank(true);
		this.enderecoTF.setEmptyText("Preencha o endereco");
		geralFS.add(this.enderecoTF, formData);

		// Cidade
		this.cidadesCB = new CidadesComboBox();
		this.cidadesCB.setName(FornecedorDTO.PROPERTY_FORNECEDOR_CIDADE_NOME);
		this.cidadesCB.setFieldLabel(this.getI18nConstants().cidade());
		this.cidadesCB.setAllowBlank(false);
		this.cidadesCB.setValue(this.cidadeDTO);
		this.cidadesCB.setEmptyText("Selecione a cidade");
		geralFS.add(this.cidadesCB, formData);

		// Email
		this.emailTF = new TextField<String>();
		this.emailTF.setName(FornecedorDTO.PROPERTY_FORNECEDOR_EMAIL);
		this.emailTF.setValue(this.fornecedorDTO.getEmail());
		this.emailTF.setFieldLabel("Email");
		this.emailTF.setAllowBlank(true);
		this.emailTF.setEmptyText("Preencha o email");
		geralFS.add(this.emailTF, formData);

		this.formPanel.add(geralFS, formData);

		FormButtonBinding binding = new FormButtonBinding(this.formPanel);
		binding.addButton(this.simpleModal.getSalvarBt());

		this.simpleModal.setFocusWidget(this.nomeTF);
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
	public SimpleModal getSimpleModal() {
		return this.simpleModal;
	}

	@Override
	public NumberField getCnpjTextField() {
		return this.cnpjTF;
	}

	@Override
	public FornecedorDTO getFornecedorDTO() {
		return this.fornecedorDTO;
	}

	@Override
	public TextField<String> getNomeFantasiaTextField() {
		return this.nomeFantasiaTF;
	}

	@Override
	public NumberField getTelefoneTextField() {
		return this.telefoneTF;
	}

	@Override
	public TextField<String> getEnderecoTextField() {
		return this.enderecoTF;
	}

	@Override
	public TextField<String> getEmailTextField() {
		return this.emailTF;
	}

	@Override
	public CidadesComboBox getCidadesCB() {
		return this.cidadesCB;
	}
}
