package br.com.vitafarma.web.client.mvp.view;

import java.util.Date;

import br.com.vitafarma.web.client.mvp.presenter.EntradaProdutosGroupFormPresenter;
import br.com.vitafarma.web.shared.dtos.EntradaProdutoGroupDTO;
import br.com.vitafarma.web.shared.dtos.FornecedorDTO;
import br.com.vitafarma.web.shared.dtos.NotaFiscalDTO;
import br.com.vitafarma.web.shared.mvp.view.MyComposite;
import br.com.vitafarma.web.shared.util.VitafarmaUtil;
import br.com.vitafarma.web.shared.util.resources.Resources;
import br.com.vitafarma.web.shared.util.view.ExtTimeField;
import br.com.vitafarma.web.shared.util.view.FornecedoresComboBox;
import br.com.vitafarma.web.shared.util.view.SimpleModal;
import br.com.vitafarma.web.shared.util.view.UniqueDomain;
import br.com.vitafarma.web.shared.util.view.UniqueTextField;

import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Time;
import com.extjs.gxt.ui.client.widget.form.TimeField;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

public class EntradaProdutosGroupFormView extends MyComposite implements
		EntradaProdutosGroupFormPresenter.Display {
	private SimpleModal simpleModal;
	private FormPanel formPanel;
	private FornecedoresComboBox fornecedoresCB;
	private DateField dataEntradaTF;
	private ExtTimeField horarioEntrada;
	private EntradaProdutoGroupDTO entradaProdutoGroupDTO;
	private NotaFiscalDTO notaFiscalDTO;
	private FornecedorDTO fornecedorDTO;
	private UniqueTextField notaFiscalTF;

	public EntradaProdutosGroupFormView(
			EntradaProdutoGroupDTO entradaProdutoGroupDTO) {
		this(entradaProdutoGroupDTO, null, null);
	}

	public EntradaProdutosGroupFormView(
			EntradaProdutoGroupDTO entradaProdutoGroupDTO,
			FornecedorDTO fornecedorDTO, NotaFiscalDTO notaFiscalDTO) {
		this.entradaProdutoGroupDTO = entradaProdutoGroupDTO;
		this.fornecedorDTO = fornecedorDTO;
		this.notaFiscalDTO = notaFiscalDTO;
		this.initUI();
	}

	private void initUI() {
		String title = ((this.entradaProdutoGroupDTO.getId() == null) ? "Nova Entrada de Produtos"
				: "Editar Entrada de Produtos");

		this.simpleModal = new SimpleModal(title,
				Resources.DEFAULTS.entradaProdutos16());
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

		// Fornecedor
		this.fornecedoresCB = new FornecedoresComboBox();
		this.fornecedoresCB
				.setName(EntradaProdutoGroupDTO.PROPERTY_ENTRADA_PRODUTO_GROUP_FORNECEDOR_STRING);
		this.fornecedoresCB.setFieldLabel(this.getI18nConstants().fornecedor());
		this.fornecedoresCB.setAllowBlank(false);
		this.fornecedoresCB.setValue(this.fornecedorDTO);
		this.fornecedoresCB.setEmptyText("Selecione o fornecedor");
		geralFS.add(this.fornecedoresCB, formData);

		// Nota Fiscal
		this.notaFiscalTF = new UniqueTextField(UniqueDomain.NOTA_FISCAL, this
				.getI18nMessages().errorDuplicatedNotaFiscal());
		this.notaFiscalTF
				.setName(EntradaProdutoGroupDTO.PROPERTY_ENTRADA_PRODUTO_GROUP_NOTA_FISCAL_CODIGO);
		this.notaFiscalTF.setValue(this.notaFiscalDTO == null ? null
				: this.notaFiscalDTO.getCodigo());
		this.notaFiscalTF.setFieldLabel("Nota Fiscal");
		this.notaFiscalTF.setAllowBlank(false);
		this.notaFiscalTF.setMinLength(1);
		this.notaFiscalTF.setMaxLength(50);
		this.notaFiscalTF.setEmptyText("Preencha a nota fiscal");
		geralFS.add(this.notaFiscalTF, formData);

		// Data da venda
		this.dataEntradaTF = new DateField();
		this.dataEntradaTF.setFieldLabel(this.getI18nConstants().dataVenda());
		this.dataEntradaTF.setAllowBlank(false);
		this.dataEntradaTF.setValue(this.entradaProdutoGroupDTO
				.getDataEntradaValue());
		this.dataEntradaTF.setEmptyText("Preencha a data da entrada");
		geralFS.add(this.dataEntradaTF, formData);

		// Horário da entrada
		Date dataEntrada = (this.entradaProdutoGroupDTO.getDataEntradaValue() == null ? new Date()
				: this.entradaProdutoGroupDTO.getDataEntradaValue());

		Time time = new Time(dataEntrada);
		time.setText(VitafarmaUtil.shortTimeString(dataEntrada));

		this.horarioEntrada = new ExtTimeField(time, this.getI18nConstants()
				.horarioEntrada(), false, "Preencha o horario da entrada");
		geralFS.add(this.horarioEntrada, formData);

		this.formPanel.add(geralFS, formData);

		FormButtonBinding binding = new FormButtonBinding(this.formPanel);
		binding.addButton(this.simpleModal.getSalvarBt());

		this.simpleModal.setFocusWidget(this.fornecedoresCB);
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
	public EntradaProdutoGroupDTO getEntradaProdutoGroupDTO() {
		return this.entradaProdutoGroupDTO;
	}

	@Override
	public FornecedoresComboBox getFornecedoresComboBox() {
		return this.fornecedoresCB;
	}

	@Override
	public DateField getDataEntradaTextField() {
		return this.dataEntradaTF;
	}

	@Override
	public TimeField getHorarioEntradaTextField() {
		return this.horarioEntrada;
	}

	@Override
	public TextField<String> getNotaFiscalTextField() {
		return this.notaFiscalTF;
	}
}
