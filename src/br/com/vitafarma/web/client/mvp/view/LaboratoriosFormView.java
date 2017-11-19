package br.com.vitafarma.web.client.mvp.view;

import br.com.vitafarma.web.client.mvp.presenter.LaboratoriosFormPresenter;
import br.com.vitafarma.web.shared.dtos.CidadeDTO;
import br.com.vitafarma.web.shared.dtos.LaboratorioDTO;
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

public class LaboratoriosFormView extends MyComposite implements
		LaboratoriosFormPresenter.Display {
	private SimpleModal simpleModal;
	private FormPanel formPanel;
	private NumberField medLabTF;
	private TextField<String> nomeTF;
	private TextField<String> nomeFantasiaTF;
	private SizedNumberField cnpjTF;
	private NumberField telefoneTF;
	private TextField<String> enderecoTF;
	private TextField<String> emailTF;
	private CidadesComboBox cidadesCB;
	private LaboratorioDTO laboratorioDTO;
	private CidadeDTO cidadeDTO;

	public LaboratoriosFormView(LaboratorioDTO laboratorioDTO) {
		this(laboratorioDTO, null);
	}

	public LaboratoriosFormView(LaboratorioDTO laboratorioDTO,
			CidadeDTO cidadeDTO) {
		this.laboratorioDTO = laboratorioDTO;
		this.cidadeDTO = cidadeDTO;
		this.initUI();
	}

	private void initUI() {
		String title = ((this.laboratorioDTO.getId() == null) ? "Insercao de Laboratorio"
				: "Edicao de Laboratorio");

		this.simpleModal = new SimpleModal(title,
				Resources.DEFAULTS.laboratorios16());
		this.simpleModal.setHeight(350);
		this.simpleModal.setWidth(600);
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

		// MedLab
		this.medLabTF = new UniqueSizedNumberField(this,
				UniqueDomain.LABORATORIO_MED_LAB, this.getI18nMessages()
						.errorDuplicatedMedLabLaboratorio());
		this.medLabTF
				.setName(LaboratorioDTO.PROPERTY_LABORATORIO_MED_LAB_VALUE);
		this.medLabTF.setValue(this.laboratorioDTO.getMedLabValue());
		this.medLabTF.setFieldLabel("Codigo do Laboratorio");
		this.medLabTF.setAllowBlank(false);
		this.medLabTF.setEmptyText("Preencha o codigo do laboratorio");
		geralFS.add(this.medLabTF, formData);

		// Nome
		this.nomeTF = new TextField<String>();
		this.nomeTF.setName(LaboratorioDTO.PROPERTY_LABORATORIO_NOME);
		this.nomeTF.setValue(this.laboratorioDTO.getNome());
		this.nomeTF.setFieldLabel("Nome");
		this.nomeTF.setAllowBlank(false);
		this.nomeTF.setMinLength(1);
		this.nomeTF.setMaxLength(50);
		this.nomeTF.setEmptyText("Preencha o nome");
		geralFS.add(this.nomeTF, formData);

		// Nome Fantasia
		this.nomeFantasiaTF = new TextField<String>();
		this.nomeFantasiaTF
				.setName(LaboratorioDTO.PROPERTY_LABORATORIO_NOME_FANTASIA);
		this.nomeFantasiaTF.setValue(this.laboratorioDTO.getNomeFantasia());
		this.nomeFantasiaTF.setFieldLabel("Nome Fantasia");
		this.nomeFantasiaTF.setAllowBlank(false);
		this.nomeFantasiaTF.setMinLength(1);
		this.nomeFantasiaTF.setMaxLength(50);
		this.nomeFantasiaTF.setEmptyText("Preencha o nome fantasia");
		geralFS.add(this.nomeFantasiaTF, formData);

		// Cnpj
		this.cnpjTF = new UniqueSizedNumberField(this, 14, 14,
				UniqueDomain.LABORATORIO_CNPJ, this.getI18nMessages()
						.errorDuplicatedCnpjLaboratorio());
		this.cnpjTF.setName(LaboratorioDTO.PROPERTY_LABORATORIO_CNPJ_VALUE);
		this.cnpjTF.setValue(this.laboratorioDTO.getCnpjValue());
		this.cnpjTF.setFieldLabel("Cnpj");
		this.cnpjTF.setAllowBlank(false);
		this.cnpjTF.setMinLength(14);
		this.cnpjTF.setMaxLength(14);
		this.cnpjTF.setEmptyText("Preencha o cnpj");
		geralFS.add(this.cnpjTF, formData);

		// Telefone
		this.telefoneTF = new NumberField();
		this.telefoneTF
				.setName(LaboratorioDTO.PROPERTY_LABORATORIO_TELEFONE_VALUE);
		this.telefoneTF.setValue(this.laboratorioDTO.getTelefoneValue());
		this.telefoneTF.setFieldLabel("Telefone");
		this.telefoneTF.setAllowBlank(true);
		this.telefoneTF.setEmptyText("Preencha o telefone");
		geralFS.add(this.telefoneTF, formData);

		// Endereço
		this.enderecoTF = new TextField<String>();
		this.enderecoTF.setName(LaboratorioDTO.PROPERTY_LABORATORIO_ENDERECO);
		this.enderecoTF.setValue(this.laboratorioDTO.getEndereco());
		this.enderecoTF.setFieldLabel("Endereco");
		this.enderecoTF.setAllowBlank(true);
		this.enderecoTF.setEmptyText("Preencha o endereco");
		geralFS.add(this.enderecoTF, formData);

		// Cidade
		this.cidadesCB = new CidadesComboBox();
		this.cidadesCB.setName(LaboratorioDTO.PROPERTY_LABORATORIO_ESTADO_NOME);
		this.cidadesCB.setFieldLabel(this.getI18nConstants().cidade());
		this.cidadesCB.setAllowBlank(false);
		this.cidadesCB.setValue(this.cidadeDTO);
		this.cidadesCB.setEmptyText("Selecione a cidade");
		geralFS.add(this.cidadesCB, formData);

		// Email
		this.emailTF = new TextField<String>();
		this.emailTF.setName(LaboratorioDTO.PROPERTY_LABORATORIO_EMAIL);
		this.emailTF.setValue(this.laboratorioDTO.getEmail());
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
	public LaboratorioDTO getLaboratorioDTO() {
		return this.laboratorioDTO;
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

	@Override
	public NumberField getMedLabTextField() {
		return this.medLabTF;
	}
}
