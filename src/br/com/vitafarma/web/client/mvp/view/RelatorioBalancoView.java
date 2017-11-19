package br.com.vitafarma.web.client.mvp.view;

import br.com.vitafarma.web.client.mvp.presenter.RelatorioBalancoPresenter;
import br.com.vitafarma.web.shared.dtos.ClienteDTO;
import br.com.vitafarma.web.shared.dtos.FornecedorDTO;
import br.com.vitafarma.web.shared.dtos.ProdutoDTO;
import br.com.vitafarma.web.shared.mvp.view.MyComposite;
import br.com.vitafarma.web.shared.util.resources.Resources;
import br.com.vitafarma.web.shared.util.view.ClientesComboBox;
import br.com.vitafarma.web.shared.util.view.FornecedoresComboBox;
import br.com.vitafarma.web.shared.util.view.GTabItem;
import br.com.vitafarma.web.shared.util.view.ProdutosComboBox;
import br.com.vitafarma.web.shared.util.view.RelatorioBalancoGrid;
import br.com.vitafarma.web.shared.util.view.SimpleToolBar;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.ColumnData;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

public class RelatorioBalancoView extends MyComposite implements RelatorioBalancoPresenter.Display {
	private SimpleToolBar toolBar = null;
	private Button submitBt = null;
	private RelatorioBalancoGrid grid = null;
	private ContentPanel panel = null;
	private GTabItem tabItem = null;
	private DateField dataInicioDF = null;
	private DateField dataFimDF = null;
	private ProdutosComboBox produtosComboBox = null;
	private ClientesComboBox clientesComboBox = null;
	private FornecedoresComboBox fornecedoresComboBox = null;

	public RelatorioBalancoView() {
		this.initUI();
	}

	private void initUI() {
		this.panel = new ContentPanel(new BorderLayout());
		this.panel.setHeading("Relatorio - Balanco do Periodo");

		this.createToolBar();
		this.createGrid();
		this.createFilter();
		this.createTabItem();
		this.initComponent(this.tabItem);
	}

	private void createToolBar() {
		// Exibe apenas o botão 'exportExcel'
		this.toolBar = new SimpleToolBar(false, false, false, false, true, this);

		this.panel.setTopComponent(this.toolBar);
	}

	private void createTabItem() {
		this.tabItem = new GTabItem("Balanco do Periodo", Resources.DEFAULTS.relatorioBalanco16());

		this.tabItem.setContent(this.panel);
	}

	private void createGrid() {
		BorderLayoutData bld = new BorderLayoutData(LayoutRegion.CENTER);
		bld.setMargins(new Margins(5, 5, 5, 5));

		this.grid = new RelatorioBalancoGrid(this);
		this.panel.add(this.grid, bld);
	}

	private void createFilter() {
		FormData formData = new FormData("100%");
		FormPanel panel = new FormPanel();

		panel.setHeaderVisible(true);
		panel.setHeading("Filtro");
		panel.setButtonAlign(HorizontalAlignment.RIGHT);

		LayoutContainer main = new LayoutContainer();
		main.setLayout(new ColumnLayout());

		LayoutContainer left = new LayoutContainer();
		left.setStyleAttribute("paddingRight", "10px");
		FormLayout layout = new FormLayout();
		left.setLayout(layout);

		// Produto
		this.produtosComboBox = new ProdutosComboBox();
		this.produtosComboBox.setName(ProdutoDTO.PROPERTY_PRODUTO_NOME);
		this.produtosComboBox.setValue(null);
		this.produtosComboBox.setFieldLabel(this.getI18nConstants().produto());
		this.produtosComboBox.setAllowBlank(true);
		this.produtosComboBox.setEmptyText("Selecione o Produto");
		left.add(this.produtosComboBox, formData);

		// Cliente
		this.clientesComboBox = new ClientesComboBox();
		this.clientesComboBox.setName(ClienteDTO.PROPERTY_CLIENTE_NOME);
		this.clientesComboBox.setValue(null);
		this.clientesComboBox.setFieldLabel(this.getI18nConstants().cliente());
		this.clientesComboBox.setAllowBlank(true);
		this.clientesComboBox.setEmptyText("Selecione o Cliente");
		left.add(this.clientesComboBox, formData);

		// Fornecedor
		this.fornecedoresComboBox = new FornecedoresComboBox();
		this.fornecedoresComboBox.setName(FornecedorDTO.PROPERTY_FORNECEDOR_NOME);
		this.fornecedoresComboBox.setValue(null);
		this.fornecedoresComboBox.setFieldLabel(this.getI18nConstants().fornecedor());
		this.fornecedoresComboBox.setAllowBlank(true);
		this.fornecedoresComboBox.setEmptyText("Selecione o Fornecedor");
		left.add(this.fornecedoresComboBox, formData);

		LayoutContainer right = new LayoutContainer();
		right.setStyleAttribute("paddingLeft", "10px");
		layout = new FormLayout();
		right.setLayout(layout);

		this.dataInicioDF = new DateField();
		this.dataInicioDF.setFieldLabel(this.getI18nConstants().dataInicioPeriodo());
		this.dataInicioDF.setAllowBlank(false);
		this.dataInicioDF.setValue(null);
		right.add(this.dataInicioDF, formData);

		this.dataFimDF = new DateField();
		this.dataFimDF.setFieldLabel(this.getI18nConstants().dataFimPeriodo());
		this.dataFimDF.setAllowBlank(false);
		this.dataFimDF.setValue(null);
		right.add(this.dataFimDF, formData);

		main.add(left, new ColumnData(0.5));
		main.add(right, new ColumnData(0.5));

		this.submitBt = new Button("Filtrar", AbstractImagePrototype.create(Resources.DEFAULTS.filter16()));
		panel.addButton(this.submitBt);

		panel.add(main, new FormData("100%"));

		BorderLayoutData bld = new BorderLayoutData(LayoutRegion.NORTH);
		bld.setMargins(new Margins(5, 5, 0, 5));
		bld.setCollapsible(true);
		bld.setSize(155);

		this.panel.add(panel, bld);
	}

	@Override
	public Button getSubmitBuscaButton() {
		return this.submitBt;
	}

	@Override
	public Button getExportExcelButton() {
		return this.toolBar.getExportExcelButton();
	}

	@Override
	public ProdutosComboBox getProdutosComboBox() {
		return this.produtosComboBox;
	}

	@Override
	public ClientesComboBox getClientesComboBox() {
		return this.clientesComboBox;
	}

	@Override
	public DateField getDataInicioDF() {
		return this.dataInicioDF;
	}

	@Override
	public DateField getDataFimDF() {
		return this.dataFimDF;
	}

	@Override
	public RelatorioBalancoGrid getGrid() {
		return this.grid;
	}

	@Override
	public FornecedoresComboBox getFornecedoresComboBox() {
		return this.fornecedoresComboBox;
	}

	@Override
	public boolean isValid() {
		if (this.getDataInicioDF() == null || this.getDataFimDF() == null) {
			return false;
		}

		return (this.getDataInicioDF().getValue() != null && this.getDataFimDF().getValue() != null);
	}
}
