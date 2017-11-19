package br.com.vitafarma.web.client.mvp.view;

import java.util.ArrayList;
import java.util.List;

import br.com.vitafarma.web.client.mvp.presenter.CidadesFormPresenter;
import br.com.vitafarma.web.shared.dtos.CidadeDTO;
import br.com.vitafarma.web.shared.dtos.EstadoDTO;
import br.com.vitafarma.web.shared.mvp.view.MyComposite;
import br.com.vitafarma.web.shared.util.resources.Resources;
import br.com.vitafarma.web.shared.util.view.EstadosComboBox;
import br.com.vitafarma.web.shared.util.view.SimpleModal;
import br.com.vitafarma.web.shared.util.view.UniqueDomain;
import br.com.vitafarma.web.shared.util.view.UniqueTextField;

import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;

public class CidadesFormView extends MyComposite implements CidadesFormPresenter.Display {
	private SimpleModal simpleModal = null;
	private FormPanel formPanel = null;
	private UniqueTextField nomeTF = null;
	private CidadeDTO cidadeDTO = null;
	private EstadosComboBox estadoCB = null;
	private EstadoDTO estadoDTO = null;
	private List<Long> idsEstados = new ArrayList<Long>();

	public CidadesFormView(CidadeDTO cidadeDTO) {
		this(cidadeDTO, null);
	}

	public CidadesFormView(CidadeDTO cidadeDTO, EstadoDTO estadoDTO) {
		this.cidadeDTO = cidadeDTO;
		this.estadoDTO = estadoDTO;

		if (estadoDTO != null && estadoDTO.getId() != null) {
			this.idsEstados.add(estadoDTO.getId());
		}

		this.initUI();
	}

	private void initUI() {
		String title = ((this.cidadeDTO.getId() == null) ? "Insercao de Cidade" : "Edicao de Cidade");

		this.simpleModal = new SimpleModal(title, Resources.DEFAULTS.cidades16());
		this.simpleModal.setHeight(350);
		this.simpleModal.setWidth(320);

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

		// Nome da cidade
		this.nomeTF = new UniqueTextField(UniqueDomain.CIDADE, this.getI18nMessages().errorDuplicatedCidade(),
				this.idsEstados);
		this.nomeTF.setName(CidadeDTO.PROPERTY_CIDADE_NOME);
		this.nomeTF.setValue(this.cidadeDTO.getNome());
		this.nomeTF.setFieldLabel(this.getI18nConstants().nome());
		this.nomeTF.setAllowBlank(false);
		this.nomeTF.setMinLength(1);
		this.nomeTF.setMaxLength(50);
		this.nomeTF.setEmptyText("Preencha o nome");
		geralFS.add(this.nomeTF, formData);

		// Estado
		this.estadoCB = new EstadosComboBox();
		this.estadoCB.setName(this.getI18nConstants().estado());
		this.estadoCB.setFieldLabel(this.getI18nConstants().estado());
		this.estadoCB.setAllowBlank(false);
		this.estadoCB.setValue(this.estadoDTO);
		this.estadoCB.setEmptyText("Selecione o estado");
		geralFS.add(this.estadoCB, formData);

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
	public CidadeDTO getCidadeDTO() {
		return this.cidadeDTO;
	}

	@Override
	public EstadosComboBox getEstadosComboBox() {
		return this.estadoCB;
	}
}
