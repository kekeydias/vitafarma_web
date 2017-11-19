package br.com.vitafarma.web.client.mvp.view;

import br.com.vitafarma.web.client.mvp.presenter.ClientesFormPresenter;
import br.com.vitafarma.web.shared.dtos.CidadeDTO;
import br.com.vitafarma.web.shared.dtos.ClienteDTO;
import br.com.vitafarma.web.shared.mvp.view.MyComposite;
import br.com.vitafarma.web.shared.util.resources.Resources;
import br.com.vitafarma.web.shared.util.view.CidadesComboBox;
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

public class ClientesFormView extends MyComposite implements
		ClientesFormPresenter.Display {
	private SimpleModal simpleModal;
	private FormPanel formPanel;
	private TextField<String> nomeTF;
	private UniqueSizedNumberField cpfTF;
	private NumberField telefoneTF;
	private TextField<String> enderecoTF;
	private TextField<String> emailTF;
	private CidadesComboBox cidadesCB;
	private ClienteDTO clienteDTO;
	private CidadeDTO cidadeDTO;

	public ClientesFormView(ClienteDTO clienteDTO) {
		this(clienteDTO, null);
	}

	public ClientesFormView(ClienteDTO clienteDTO, CidadeDTO cidadeDTO) {
		this.clienteDTO = clienteDTO;
		this.cidadeDTO = cidadeDTO;
		this.initUI();
	}

	private void initUI() {
		String title = ((this.clienteDTO.getId() == null) ? "Insercao de Cliente"
				: "Edicao de Cliente");

		this.simpleModal = new SimpleModal(title,
				Resources.DEFAULTS.clientes16());
		this.simpleModal.setHeight(350);
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

		// Nome
		this.nomeTF = new TextField<String>();
		this.nomeTF.setName(ClienteDTO.PROPERTY_CLIENTE_NOME);
		this.nomeTF.setValue(this.clienteDTO.getNome());
		this.nomeTF.setFieldLabel("Nome");
		this.nomeTF.setAllowBlank(false);
		this.nomeTF.setMinLength(1);
		this.nomeTF.setMaxLength(50);
		this.nomeTF.setEmptyText("Preencha o nome");
		geralFS.add(this.nomeTF, formData);

		// Cpf
		this.cpfTF = new UniqueSizedNumberField(this, 11, 11,
				UniqueDomain.CLIENTE, this.getI18nMessages()
						.errorDuplicatedCpfCliente());
		this.cpfTF.setName(ClienteDTO.PROPERTY_CLIENTE_CPF_VALUE);
		this.cpfTF.setValue(this.clienteDTO.getCpfValue());
		this.cpfTF.setFieldLabel("Cpf");
		this.cpfTF.setAllowBlank(false);
		this.cpfTF.setEmptyText("Preencha o cpf");
		geralFS.add(this.cpfTF, formData);

		// Telefone
		this.telefoneTF = new NumberField();
		this.telefoneTF.setName(ClienteDTO.PROPERTY_CLIENTE_TELEFONE);
		this.telefoneTF.setValue(this.clienteDTO.getTelefone());
		this.telefoneTF.setFieldLabel("Telefone");
		this.telefoneTF.setAllowBlank(true);
		this.telefoneTF.setEmptyText("Preencha o telefone");
		geralFS.add(this.telefoneTF, formData);

		// Endereço
		this.enderecoTF = new TextField<String>();
		this.enderecoTF.setName(ClienteDTO.PROPERTY_CLIENTE_ENDERECO);
		this.enderecoTF.setValue(this.clienteDTO.getEndereco());
		this.enderecoTF.setFieldLabel("Endereco");
		this.enderecoTF.setAllowBlank(true);
		this.enderecoTF.setEmptyText("Preencha o endereco");
		geralFS.add(this.enderecoTF, formData);

		// Cidade
		this.cidadesCB = new CidadesComboBox();
		this.cidadesCB.setName(ClienteDTO.PROPERTY_CLIENTE_CIDADE_NOME);
		this.cidadesCB.setFieldLabel(this.getI18nConstants().cidade());
		this.cidadesCB.setAllowBlank(false);
		this.cidadesCB.setValue(this.cidadeDTO);
		this.cidadesCB.setEmptyText("Selecione a cidade");
		geralFS.add(this.cidadesCB, formData);

		// Email
		this.emailTF = new TextField<String>();
		this.emailTF.setName(ClienteDTO.PROPERTY_CLIENTE_EMAIL);
		this.emailTF.setValue(this.clienteDTO.getEmail());
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
	public ClienteDTO getClienteDTO() {
		return this.clienteDTO;
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
