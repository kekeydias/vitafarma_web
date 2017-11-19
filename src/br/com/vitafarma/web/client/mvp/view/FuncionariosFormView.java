package br.com.vitafarma.web.client.mvp.view;

import br.com.vitafarma.web.client.mvp.presenter.FuncionariosFormPresenter;
import br.com.vitafarma.web.shared.dtos.CidadeDTO;
import br.com.vitafarma.web.shared.dtos.FuncionarioDTO;
import br.com.vitafarma.web.shared.mvp.view.MyComposite;
import br.com.vitafarma.web.shared.util.resources.Resources;
import br.com.vitafarma.web.shared.util.view.CidadesComboBox;
import br.com.vitafarma.web.shared.util.view.SizedNumberField;
import br.com.vitafarma.web.shared.util.view.SimpleModal;
import br.com.vitafarma.web.shared.util.view.UniqueDomain;
import br.com.vitafarma.web.shared.util.view.UniqueSizedNumberField;

import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

public class FuncionariosFormView extends MyComposite implements
		FuncionariosFormPresenter.Display {
	private SimpleModal simpleModal;
	private FormPanel formPanel;
	private TextField<String> nomeTF;
	private SizedNumberField cpfTF;
	private NumberField telefoneTF;
	private TextField<String> enderecoTF;
	private TextField<String> emailTF;
	private NumberField salarioNF;
	private DateField dataAdimissaoTF;
	private DateField dataDemissaoTF;
	private CidadesComboBox cidadesCB;
	private FuncionarioDTO funcionarioDTO;
	private CidadeDTO cidadeDTO;

	public FuncionariosFormView(FuncionarioDTO funcionarioDTO) {
		this(funcionarioDTO, null);
	}

	public FuncionariosFormView(FuncionarioDTO funcionarioDTO,
			CidadeDTO cidadeDTO) {
		this.cidadeDTO = cidadeDTO;
		this.funcionarioDTO = funcionarioDTO;
		this.initUI();
	}

	private void initUI() {
		String title = ((this.funcionarioDTO.getId() == null) ? "Insercao de Funcionario"
				: "Edicao de Funcionario");

		this.simpleModal = new SimpleModal(title,
				Resources.DEFAULTS.funcionarios16());
		this.simpleModal.setHeight(400);
		this.simpleModal.setWidth(550);
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

		// Nome do funcionário
		this.nomeTF = new TextField<String>();
		this.nomeTF.setName(FuncionarioDTO.PROPERTY_FUNCIONARIO_NOME);
		this.nomeTF.setValue(this.funcionarioDTO.getNome());
		this.nomeTF.setFieldLabel("Nome");
		this.nomeTF.setAllowBlank(false);
		this.nomeTF.setMinLength(1);
		this.nomeTF.setMaxLength(50);
		this.nomeTF.setEmptyText("Preencha o nome");
		geralFS.add(this.nomeTF, formData);

		// Cpf do funcionário
		this.cpfTF = new UniqueSizedNumberField(this, 11, 11,
				UniqueDomain.FUNCIONARIO, this.getI18nMessages()
						.errorDuplicatedCpfFuncionario());
		this.cpfTF.setName(FuncionarioDTO.PROPERTY_FUNCIONARIO_CPF_VALUE);
		this.cpfTF.setValue(this.funcionarioDTO.getCpfValue());
		this.cpfTF.setFieldLabel("Cpf");
		this.cpfTF.setAllowBlank(false);
		this.cpfTF.setMinLength(11);
		this.cpfTF.setMaxLength(11);
		this.cpfTF.setEmptyText("Preencha o cpf");
		geralFS.add(this.cpfTF, formData);

		// Salário do funcionário
		this.salarioNF = new NumberField();
		this.salarioNF
				.setName(FuncionarioDTO.PROPERTY_FUNCIONARIO_SALARIO_VALUE);
		this.salarioNF.setValue(this.funcionarioDTO.getSalarioValue());
		this.salarioNF.setFieldLabel("Salario");
		this.salarioNF.setAllowBlank(false);
		this.salarioNF.setMinLength(1);
		this.salarioNF.setMaxLength(20);
		this.salarioNF.setEmptyText("Preencha o salario");
		geralFS.add(this.salarioNF, formData);

		// Data de admissão do funcionário
		this.dataAdimissaoTF = new DateField();
		this.dataAdimissaoTF
				.setName(FuncionarioDTO.PROPERTY_FUNCIONARIO_DATA_ADIMISSAO);
		this.dataAdimissaoTF.setValue(this.funcionarioDTO.getDataAdimissao());
		this.dataAdimissaoTF.setFieldLabel("Data de adimissao");
		this.dataAdimissaoTF.setAllowBlank(false);
		this.dataAdimissaoTF.setMinLength(1);
		this.dataAdimissaoTF.setMaxLength(20);
		this.dataAdimissaoTF.setEmptyText("Preencha a data de adimissao");
		geralFS.add(this.dataAdimissaoTF, formData);

		// Data de demissão do funcionário
		this.dataDemissaoTF = new DateField();
		this.dataDemissaoTF
				.setName(FuncionarioDTO.PROPERTY_FUNCIONARIO_DATA_DEMISSAO);
		this.dataDemissaoTF.setValue(this.funcionarioDTO.getDataDemissao());
		this.dataDemissaoTF.setFieldLabel("Data de demissao");
		this.dataDemissaoTF.setAllowBlank(true);
		this.dataDemissaoTF.setMinLength(1);
		this.dataDemissaoTF.setMaxLength(20);
		this.dataDemissaoTF.setEmptyText("Preencha a data de demissao");
		geralFS.add(this.dataDemissaoTF, formData);

		// Telefone do funcionário
		this.telefoneTF = new NumberField();
		this.telefoneTF.setName(FuncionarioDTO.PROPERTY_FUNCIONARIO_TELEFONE);
		this.telefoneTF.setValue(this.funcionarioDTO.getTelefone());
		this.telefoneTF.setFieldLabel("Telefone");
		this.telefoneTF.setAllowBlank(true);
		this.telefoneTF.setEmptyText("Preencha o telefone");
		geralFS.add(this.telefoneTF, formData);

		// Endereço do funcionário
		this.enderecoTF = new TextField<String>();
		this.enderecoTF.setName(FuncionarioDTO.PROPERTY_FUNCIONARIO_ENDERECO);
		this.enderecoTF.setValue(this.funcionarioDTO.getEndereco());
		this.enderecoTF.setFieldLabel("Endereco");
		this.enderecoTF.setAllowBlank(true);
		this.enderecoTF.setEmptyText("Preencha o endereco");
		geralFS.add(this.enderecoTF, formData);

		// Cidade
		this.cidadesCB = new CidadesComboBox();
		this.cidadesCB.setName(FuncionarioDTO.PROPERTY_FUNCIONARIO_CIDADE_NOME);
		this.cidadesCB.setFieldLabel(this.getI18nConstants().cidade());
		this.cidadesCB.setAllowBlank(false);
		this.cidadesCB.setValue(this.cidadeDTO);
		this.cidadesCB.setEmptyText("Selecione a cidade");
		geralFS.add(this.cidadesCB, formData);

		// Email do funcionário
		this.emailTF = new TextField<String>();
		this.emailTF.setName(FuncionarioDTO.PROPERTY_FUNCIONARIO_EMAIL);
		this.emailTF.setValue(this.funcionarioDTO.getEmail());
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
	public NumberField getCpfTextField() {
		return this.cpfTF;
	}

	@Override
	public FuncionarioDTO getFuncionarioDTO() {
		return this.funcionarioDTO;
	}

	@Override
	public NumberField getSalarioNumberField() {
		return this.salarioNF;
	}

	@Override
	public DateField getDataAdimissaoDateField() {
		return this.dataAdimissaoTF;
	}

	@Override
	public DateField getDataDemissaoDateField() {
		return this.dataDemissaoTF;
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
