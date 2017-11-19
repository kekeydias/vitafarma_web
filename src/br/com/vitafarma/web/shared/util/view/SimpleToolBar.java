package br.com.vitafarma.web.shared.util.view;

import br.com.vitafarma.web.shared.i18n.IVitafarmaI18nGateway;
import br.com.vitafarma.web.shared.util.resources.Resources;

import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

public class SimpleToolBar extends ToolBar {
	private Button newButton;
	private boolean showNewButton = true;
	private Button editButton;
	private boolean showEditButton = true;
	private Button removeButton;
	private boolean showRemoveButton = true;
	private Button importExcelButton;
	private boolean showImportExcelButton = true;
	private Button exportExcelButton;
	private boolean showExportExcelButton = true;

	private IVitafarmaI18nGateway i18nGateway;

	public SimpleToolBar(IVitafarmaI18nGateway i18nGateway) {
		super();
		this.i18nGateway = i18nGateway;
		this.initUI();
	}

	public SimpleToolBar(boolean showNewButton, boolean showEditButton,
			boolean showRemoveButton, boolean showImportExcelButton,
			boolean showExportExcelButton, IVitafarmaI18nGateway i18nGateway) {
		super();

		this.showNewButton = showNewButton;
		this.showEditButton = showEditButton;
		this.showRemoveButton = showRemoveButton;
		this.showImportExcelButton = showImportExcelButton;
		this.showExportExcelButton = showExportExcelButton;
		this.i18nGateway = i18nGateway;

		this.initUI();
	}

	private void initUI() {
		if (this.showNewButton) {
			this.newButton = this
					.createButton(this.i18nGateway.getI18nConstants()
							.adicionar(), Resources.DEFAULTS.add16());

			this.add(this.newButton);
		}

		if (this.showEditButton) {
			this.editButton = this.createButton(this.i18nGateway
					.getI18nConstants().editar(), Resources.DEFAULTS.edit16());

			this.add(this.editButton);
		}

		if (this.showRemoveButton) {
			this.removeButton = new ConfirmationButton(this.i18nGateway);
			this.removeButton.setIcon(AbstractImagePrototype
					.create(Resources.DEFAULTS.del16()));
			this.removeButton.setToolTip(this.i18nGateway.getI18nConstants()
					.remover());

			this.add(this.removeButton);
		}

		if (this.showImportExcelButton || this.showExportExcelButton) {
			this.add(new SeparatorToolItem());
		}

		if (this.showImportExcelButton) {
			this.importExcelButton = this.createButton(this.i18nGateway
					.getI18nConstants().importarExcel(), Resources.DEFAULTS
					.importar16());

			this.add(this.importExcelButton);
		}

		if (this.showExportExcelButton) {
			this.exportExcelButton = this.createButton(this.i18nGateway
					.getI18nConstants().exportarExcel(), Resources.DEFAULTS
					.exportar16());

			this.add(this.exportExcelButton);
		}
	}

	public void activateEmptyState() {
		this.editButton.setEnabled(false);
		this.removeButton.setEnabled(false);
	}

	public void enableMultiState() {
		this.editButton.setEnabled(false);
		this.removeButton.setEnabled(true);
	}

	public void enableSimpleState() {
		this.editButton.setEnabled(true);
		this.removeButton.setEnabled(true);
	}

	public Button getNewButton() {
		return this.newButton;
	}

	public Button getEditButton() {
		return this.editButton;
	}

	public Button getRemoveButton() {
		return this.removeButton;
	}

	public Button getImportExcelButton() {
		return this.importExcelButton;
	}

	public Button getExportExcelButton() {
		return this.exportExcelButton;
	}

	public Button createButton(String toolTip, ImageResource img) {
		Button bt = new Button();

		bt.setIcon(AbstractImagePrototype.create(img));
		bt.setToolTip(toolTip);

		return bt;
	}
}
