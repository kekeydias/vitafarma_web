package br.com.vitafarma.web.shared.util.view;

import br.com.vitafarma.web.shared.dtos.AbstractDTO;
import br.com.vitafarma.web.shared.excel.ExcelInformationType;
import br.com.vitafarma.web.shared.mvp.presenter.Presenter;
import br.com.vitafarma.web.shared.mvp.view.MyComposite;
import br.com.vitafarma.web.shared.util.VitafarmaUtil;
import br.com.vitafarma.web.shared.util.resources.Resources;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FileUploadField;
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;
import com.extjs.gxt.ui.client.widget.form.FormPanel.Encoding;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.FormPanel.Method;
import com.extjs.gxt.ui.client.widget.form.HiddenField;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

public class ImportExcelFormView extends MyComposite {
	private SimpleModal simpleModal;
	private ImportExcelFormPanel formPanel;
	private HiddenField<String> hiddenFieldInfoToBeExported;
	private HiddenField<Boolean> hiddenFieldCreateNewEntities;
	private CheckBox createNewEntitiesCB;
	private FileUploadField fileUploadField;

	private ImportExcelFormView(ExcelParametros parametros, SimpleGrid<? extends AbstractDTO<?>> gridToBeUpdated,
			Presenter presenter, GTab gTab) {
		this.initUI(parametros.getInfo(), gridToBeUpdated);
		this.addPresentActions(gTab, presenter);
		this.initActions();
	}

	public ImportExcelFormView(ExcelParametros parametros, SimpleGrid<? extends AbstractDTO<?>> gridToBeUpdated) {
		this(parametros, gridToBeUpdated, null, null);
	}

	public ImportExcelFormView(ExcelParametros parametros) {
		this(parametros, null, null, null);
	}

	public ImportExcelFormView(ExcelParametros parametros, Presenter presenter, GTab gTab) {
		this(parametros, null, presenter, gTab);
	}

	private void initUI(ExcelInformationType infoToBeImported, SimpleGrid<? extends AbstractDTO<?>> gridToBeUpdated) {
		this.simpleModal = new SimpleModal(this.getI18nMessages().importExcelHeading(),
				Resources.DEFAULTS.importar16());

		this.createForm(infoToBeImported, gridToBeUpdated);
		this.simpleModal.setWidth(320);
		this.simpleModal.setHeight(200);
		this.simpleModal.setContent(this.formPanel);
	}

	private void createForm(ExcelInformationType infoToBeImported,
			SimpleGrid<? extends AbstractDTO<?>> gridToBeUpdated) {
		FormData formData = new FormData("-20");

		FieldSet geralFS = new FieldSet();
		FormLayout formLayout = new FormLayout(LabelAlign.RIGHT);
		formLayout.setLabelWidth(75);
		geralFS.setLayout(formLayout);
		geralFS.setHeading(this.getI18nConstants().informacoesGerais());

		this.hiddenFieldInfoToBeExported = new HiddenField<String>();
		this.hiddenFieldInfoToBeExported.setName(ExcelInformationType.getInformationParameterName());
		this.hiddenFieldInfoToBeExported.setValue(infoToBeImported.toString());
		geralFS.add(this.hiddenFieldInfoToBeExported, formData);

		this.fileUploadField = new FileUploadField();
		this.fileUploadField.setAllowBlank(false);
		this.fileUploadField.setName(ExcelInformationType.getFileParameterName());
		this.fileUploadField.setFieldLabel(this.getI18nMessages().arquivo());
		geralFS.add(this.fileUploadField, formData);

		this.formPanel = new ImportExcelFormPanel(this.simpleModal, this.simpleModal.getSalvarBt());
		this.formPanel.setLayout(new FlowLayout());
		this.formPanel.setLabelWidth(50);
		this.formPanel.setFrame(true);
		this.formPanel.setHeaderVisible(false);
		this.formPanel.setAction(GWT.getModuleBaseURL() + "importExcelServlet");
		this.formPanel.setEncoding(Encoding.MULTIPART);
		this.formPanel.setMethod(Method.POST);
		this.formPanel.setButtonAlign(HorizontalAlignment.CENTER);

		this.createNewEntitiesCB = VitafarmaUtil.createCheckBox(this.getI18nConstants().criarNovosLaboratorios(),
				false);
		this.createNewEntitiesCB.setName(ExcelInformationType.getCreateNewEntities());
		geralFS.add(this.createNewEntitiesCB, formData);

		this.formPanel.add(geralFS, formData);
		this.formPanel.addListener(Events.Submit, new ExcelFormListener(this.simpleModal, gridToBeUpdated,
				this.getI18nConstants(), this.getI18nMessages(), ExcelOperation.IMPORT));

		this.simpleModal.setFocusWidget(this.fileUploadField);

		FormButtonBinding binding = new FormButtonBinding(this.formPanel);
		binding.addButton(this.simpleModal.getSalvarBt());
	}

	private void initActions() {
		this.simpleModal.getSalvarBt().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (!formPanel.isValid()) {
					return;
				}

				try {
					desabilitaBotao();

					hiddenFieldCreateNewEntities = new HiddenField<Boolean>();
					hiddenFieldCreateNewEntities.setName(ExcelInformationType.getCreateNewEntities());
					hiddenFieldCreateNewEntities
							.setValue(createNewEntitiesCB.getValue() == null ? false : createNewEntitiesCB.getValue());
					formPanel.add(hiddenFieldCreateNewEntities);

					formPanel.submit();
				} catch (Exception ex) {
					System.out.println("ImportExcelFormView: submit error.");
					System.out.println(ex.getCause() + "\n\n" + ex.getMessage());
				}
			}
		});
	}

	public void addPresentActions(final GTab gTab, final Presenter presenter) {
		if (presenter != null && gTab != null) {
			this.simpleModal.addListener(Events.Hide, new Listener<BaseEvent>() {
				@Override
				public void handleEvent(BaseEvent be) {
					habilitarBotao();
					presenter.go(gTab);
				}
			});
		}
	}

	private void desabilitaBotao() {
		this.simpleModal.getSalvarBt().setIcon(AbstractImagePrototype.create(Resources.DEFAULTS.ajax16()));

		this.simpleModal.getSalvarBt().disable();
		this.simpleModal.setClosable(false);
	}

	private void habilitarBotao() {
		this.simpleModal.getSalvarBt().enable();
		this.simpleModal.setClosable(true);

		this.simpleModal.getSalvarBt().setIcon(AbstractImagePrototype.create(Resources.DEFAULTS.save16()));
	}

	public void show() {
		this.simpleModal.show();
	}
}
