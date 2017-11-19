package br.com.vitafarma.web.client.mvp.view;

import java.util.Date;

import br.com.vitafarma.web.client.mvp.presenter.VendasFormPresenter;
import br.com.vitafarma.web.shared.dtos.ClienteDTO;
import br.com.vitafarma.web.shared.dtos.VendaDTO;
import br.com.vitafarma.web.shared.mvp.view.MyComposite;
import br.com.vitafarma.web.shared.util.VitafarmaUtil;
import br.com.vitafarma.web.shared.util.resources.Resources;
import br.com.vitafarma.web.shared.util.view.ClientesComboBox;
import br.com.vitafarma.web.shared.util.view.ExtTimeField;
import br.com.vitafarma.web.shared.util.view.SimpleModal;

import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.Time;
import com.extjs.gxt.ui.client.widget.form.TimeField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

public class VendasFormView extends MyComposite implements
		VendasFormPresenter.Display {
	private SimpleModal simpleModal;
	private FormPanel formPanel;
	private ClientesComboBox clientesCB;
	private DateField dataVendaTF;
	private ExtTimeField horarioVenda;
	private NumberField descontoTF;
	private VendaDTO vendaDTO;
	private ClienteDTO clienteDTO;

	public VendasFormView(VendaDTO vendaDTO) {
		this(vendaDTO, null);
	}

	public VendasFormView(VendaDTO vendaDTO, ClienteDTO clienteDTO) {
		this.vendaDTO = vendaDTO;
		this.clienteDTO = clienteDTO;
		this.initUI();
	}

	private void initUI() {
		String title = ((this.vendaDTO.getVendaId() == null) ? "Nova Venda"
				: "Editar Venda");

		this.simpleModal = new SimpleModal(title, Resources.DEFAULTS.vendas16());

		this.simpleModal.setHeight(280);
		this.simpleModal.setWidth(500);
		this.createForm();
		this.simpleModal.setContent(this.formPanel);
	}

	private void createForm() {
		FormData formData = new FormData("-20");
		this.formPanel = new FormPanel();
		this.formPanel.setHeaderVisible(false);

		FieldSet geralFS = new FieldSet();
		FormLayout formLayout = new FormLayout(LabelAlign.RIGHT);
		formLayout.setLabelWidth(75);
		geralFS.setLayout(formLayout);
		geralFS.setHeading(this.getI18nConstants().informacoesGerais());

		// Cliente da venda
		this.clientesCB = new ClientesComboBox();
		this.clientesCB.setName(VendaDTO.PROPERTY_VENDA_CLIENTE_NOME);
		this.clientesCB.setFieldLabel(this.getI18nConstants().cliente());
		this.clientesCB.setAllowBlank(false);
		this.clientesCB.setValue(this.clienteDTO);
		this.clientesCB.setEmptyText("Selecione o cliente");
		geralFS.add(this.clientesCB, formData);

		// Desconto da venda
		this.descontoTF = new NumberField();
		this.descontoTF.setFieldLabel(this.getI18nConstants().desconto());
		this.descontoTF.setAllowBlank(true);
		this.descontoTF.setMinValue(0);
		this.descontoTF.setMaxValue(100);
		this.descontoTF.setValue(this.vendaDTO.getDescontoValue());
		geralFS.add(this.descontoTF, formData);

		// Data da venda
		this.dataVendaTF = new DateField();
		this.dataVendaTF.setFieldLabel(this.getI18nConstants().dataVenda());
		this.dataVendaTF.setAllowBlank(false);
		this.dataVendaTF.setValue(this.vendaDTO.getVendaDataValue());
		this.dataVendaTF.setEmptyText("Selecione a data da venda");
		geralFS.add(this.dataVendaTF, formData);

		// Horário da venda
		Date dataVenda = (this.vendaDTO.getVendaDataValue() == null ? new Date()
				: this.vendaDTO.getVendaDataValue());

		Time time = new Time(dataVenda);
		time.setText(VitafarmaUtil.shortTimeString(dataVenda));

		this.horarioVenda = new ExtTimeField(time, this.getI18nConstants()
				.horarioVenda(), false, "Selecione o horario da venda");
		geralFS.add(this.horarioVenda, formData);

		this.formPanel.add(geralFS, formData);

		FormButtonBinding binding = new FormButtonBinding(this.formPanel);
		binding.addButton(this.simpleModal.getSalvarBt());

		this.simpleModal.setFocusWidget(this.clientesCB);
	}

	@Override
	public boolean isValid() {
		return this.formPanel.isValid();
	}

	@Override
	public Button getSalvarButton() {
		return simpleModal.getSalvarBt();
	}

	@Override
	public SimpleModal getSimpleModal() {
		return this.simpleModal;
	}

	@Override
	public VendaDTO getVendaDTO() {
		return this.vendaDTO;
	}

	@Override
	public ClientesComboBox getClientesComboBox() {
		return this.clientesCB;
	}

	@Override
	public DateField getDataVendaTextField() {
		return this.dataVendaTF;
	}

	@Override
	public NumberField getDescontoTextField() {
		return this.descontoTF;
	}

	@Override
	public TimeField getHorarioVendaTextField() {
		return this.horarioVenda;
	}
}
