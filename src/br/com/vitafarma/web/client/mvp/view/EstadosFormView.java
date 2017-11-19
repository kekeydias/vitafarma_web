package br.com.vitafarma.web.client.mvp.view;

import br.com.vitafarma.web.client.mvp.presenter.EstadosFormPresenter;
import br.com.vitafarma.web.shared.dtos.EstadoDTO;
import br.com.vitafarma.web.shared.mvp.view.MyComposite;
import br.com.vitafarma.web.shared.util.resources.Resources;
import br.com.vitafarma.web.shared.util.view.SimpleModal;
import br.com.vitafarma.web.shared.util.view.UniqueDomain;
import br.com.vitafarma.web.shared.util.view.UniqueSizedTextField;
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

public class EstadosFormView extends MyComposite implements EstadosFormPresenter.Display {
	private SimpleModal simpleModal = null;
	private FormPanel formPanel = null;
	private UniqueTextField nomeTF = null;
	private UniqueSizedTextField siglaTF = null;
	private EstadoDTO estadoDTO = null;

	public EstadosFormView(EstadoDTO estadoDTO) {
		this.estadoDTO = estadoDTO;

		this.initUI();
	}

	private void initUI() {
		String title = ((this.estadoDTO.getId() == null) ? "Insercao de Estado" : "Edicao de Estado");

		this.simpleModal = new SimpleModal(title, Resources.DEFAULTS.estados16());
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

		// Nome do estado
		this.nomeTF = new UniqueTextField(UniqueDomain.ESTADO_NOME, this.getI18nMessages().errorDuplicatedEstadoNome());
		this.nomeTF.setName(EstadoDTO.PROPERTY_ESTADO_NOME);
		this.nomeTF.setValue(this.estadoDTO.getNome());
		this.nomeTF.setFieldLabel(this.getI18nConstants().nome());
		this.nomeTF.setAllowBlank(false);
		this.nomeTF.setMinLength(1);
		this.nomeTF.setMaxLength(50);
		this.nomeTF.setEmptyText("Preencha o nome");
		geralFS.add(this.nomeTF, formData);

		// Sigla do estado
		this.siglaTF = new UniqueSizedTextField(this, 2, 2, UniqueDomain.ESTADO_SIGLA,
				this.getI18nMessages().errorDuplicatedEstadoSigla());
		this.siglaTF.setName(EstadoDTO.PROPERTY_ESTADO_SIGLA);
		this.siglaTF.setValue(this.estadoDTO.getSigla());
		this.siglaTF.setFieldLabel(this.getI18nConstants().sigla());
		this.siglaTF.setAllowBlank(false);
		this.siglaTF.setEmptyText("Preencha a sigla");
		geralFS.add(this.siglaTF, formData);

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
	public EstadoDTO getEstadoDTO() {
		return this.estadoDTO;
	}

	@Override
	public TextField<String> getSiglaTextField() {
		return this.siglaTF;
	}
}
