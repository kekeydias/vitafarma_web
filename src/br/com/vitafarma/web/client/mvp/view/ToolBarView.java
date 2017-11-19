package br.com.vitafarma.web.client.mvp.view;

import br.com.vitafarma.web.client.mvp.presenter.ToolBarPresenter;
import br.com.vitafarma.web.shared.mvp.view.MyComposite;
import br.com.vitafarma.web.shared.util.resources.Resources;

import com.extjs.gxt.ui.client.Style.ButtonArrowAlign;
import com.extjs.gxt.ui.client.Style.ButtonScale;
import com.extjs.gxt.ui.client.Style.IconAlign;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

public class ToolBarView extends MyComposite implements ToolBarPresenter.Display {
	private LayoutContainer container = null;

	private ToolBar clientesToolBar = null;
	private ToolBar fornecedoresToolBar = null;
	private ToolBar funcionariosToolBar = null;
	private ToolBar laboratoriosToolBar = null;
	private ToolBar produtosToolBar = null;
	private ToolBar vendedoresToolBar = null;
	private ToolBar vendasToolBar = null;
	private ToolBar estadosToolBar = null;
	private ToolBar cidadesToolBar = null;
	private ToolBar relatoriosToolBar = null;

	// Cidades
	private Button cidadeNovoCidadeBt = null;
	private Button cidadesListCidadesBt = null;

	// Clientes
	private Button clientesNovoClienteBt = null;
	private Button clientesListClientesBt = null;

	// Estados
	private Button estadoNovoEstadoBt = null;
	private Button estadosListEstadosBt = null;

	// Fornecedores
	private Button fornecedoresNovoFornecedorBt = null;
	private Button fornecedoresListFornecedoresBt = null;

	// Funcionarios
	private Button funcionariosNovoFuncionarioBt = null;
	private Button funcionariosListFuncionariosBt = null;

	// Laboratorios
	private Button laboratoriosNovoLaboratorioBt = null;
	private Button laboratoriosListLaboratoriosBt = null;

	// Produtos
	private Button produtosNovoProdutoBt = null;
	private Button produtosListProdutosBt = null;
	private Button importProdutosAbcfarmaBT = null;

	// Funcionarios
	private Button vendedoresNovoVendedorBt = null;
	private Button vendedoresListVendedoresBt = null;

	// Vendas
	private Button vendasNovoVendasBt = null;
	private Button vendasListVendasBt = null;

	// Entrada de Produtos
	private Button entradaProdutosNovoProdutoBt = null;
	private Button entradaProdutosListProdutosBt = null;

	// Relatórios
	private Button relatorioBalancoPeriodoBt = null;
	private Button exportExcelCenarioBt = null;

	public ToolBarView() {
		this.initUI();
	}

	private void initUI() {
		this.container = new LayoutContainer(new HBoxLayout());

		ContentPanel masterDataPanel = new ContentPanel();

		masterDataPanel.setHeaderVisible(false);
		masterDataPanel.setBodyBorder(false);

		HBoxLayoutData flex = new HBoxLayoutData(new Margins(0));
		flex.setFlex(1);
		this.container.add(masterDataPanel, flex);

		TabItem cidadesTabItem = new TabItem("Cidades");
		this.cidadesToolBar = new ToolBar();
		cidadesTabItem.add(this.cidadesToolBar);

		TabItem clientesTabItem = new TabItem("Clientes");
		this.clientesToolBar = new ToolBar();
		clientesTabItem.add(this.clientesToolBar);

		TabItem estadosTabItem = new TabItem("Estados");
		this.estadosToolBar = new ToolBar();
		estadosTabItem.add(this.estadosToolBar);

		TabItem fornecedoresTabItem = new TabItem("Fornecedores");
		this.fornecedoresToolBar = new ToolBar();
		fornecedoresTabItem.add(this.fornecedoresToolBar);

		TabItem funcionariosTabItem = new TabItem("Funcionarios");
		this.funcionariosToolBar = new ToolBar();
		funcionariosTabItem.add(this.funcionariosToolBar);

		TabItem laboratoriosTabItem = new TabItem("Laboratorios");
		this.laboratoriosToolBar = new ToolBar();
		laboratoriosTabItem.add(this.laboratoriosToolBar);

		TabItem produtosTabItem = new TabItem("Produtos");
		this.produtosToolBar = new ToolBar();
		produtosTabItem.add(this.produtosToolBar);

		TabItem vendedoresTabItem = new TabItem("Vendedores");
		this.vendedoresToolBar = new ToolBar();
		vendedoresTabItem.add(this.vendedoresToolBar);

		TabItem vendasTabItem = new TabItem("Vendas");
		this.vendasToolBar = new ToolBar();
		vendasTabItem.add(this.vendasToolBar);

		TabItem relatoriosTabItem = new TabItem("Relatorios");
		this.relatoriosToolBar = new ToolBar();
		relatoriosTabItem.add(this.relatoriosToolBar);

		this.createGroups();

		int height = 92;

		TabPanel masterDataTab = new TabPanel();
		masterDataTab.addStyleName("tabPanelMasterData");
		masterDataTab.setHeight(height);

		TabItem masterDataItem = new TabItem("Vitafarma");

		masterDataItem.disable();

		masterDataTab.add(masterDataItem);
		masterDataTab.add(cidadesTabItem);
		masterDataTab.add(estadosTabItem);
		masterDataTab.add(clientesTabItem);
		masterDataTab.add(fornecedoresTabItem);
		masterDataTab.add(funcionariosTabItem);
		masterDataTab.add(laboratoriosTabItem);
		masterDataTab.add(produtosTabItem);
		masterDataTab.add(vendedoresTabItem);
		masterDataTab.add(vendasTabItem);
		masterDataTab.add(relatoriosTabItem);

		// define que item eh ativado quando a pagina eh carregada
		masterDataTab.setSelection(cidadesTabItem);
		masterDataPanel.setTopComponent(masterDataTab);

		this.container.setHeight(height);
		this.initComponent(this.container);
	}

	private void createGroups() {
		this.createCidades();
		this.createClientes();
		this.createEstados();
		this.createFornecedores();
		this.createFuncionarios();
		this.createLaboratorios();
		this.createProdutos();
		this.createVendedores();
		this.createVendas();
		this.createRelatorios();
	}

	private void createFornecedores() {
		this.fornecedoresNovoFornecedorBt = this.createButton("Novo", "Adicionar Novo Fornecedor",
				Resources.DEFAULTS.fornecedoresNovo24());
		this.fornecedoresToolBar.add(this.fornecedoresNovoFornecedorBt);

		this.fornecedoresListFornecedoresBt = this.createButton("Listar", "Listar Fornecedores",
				Resources.DEFAULTS.fornecedoresListar24());
		this.fornecedoresToolBar.add(this.fornecedoresListFornecedoresBt);
	}

	private void createCidades() {
		this.cidadeNovoCidadeBt = this.createButton("Novo", "Adicionar Nova Cidade",
				Resources.DEFAULTS.cidadesNovo24());
		this.cidadesToolBar.add(this.cidadeNovoCidadeBt);

		this.cidadesListCidadesBt = this.createButton("Listar", "Listar Cidades", Resources.DEFAULTS.cidadesListar24());
		this.cidadesToolBar.add(this.cidadesListCidadesBt);
	}

	private void createClientes() {
		this.clientesNovoClienteBt = this.createButton("Novo", "Adicionar Novo Cliente",
				Resources.DEFAULTS.clientesNovo24());
		this.clientesToolBar.add(this.clientesNovoClienteBt);

		this.clientesListClientesBt = this.createButton("Listar", "Listar Clientes",
				Resources.DEFAULTS.clientesListar24());
		this.clientesToolBar.add(this.clientesListClientesBt);
	}

	private void createEstados() {
		this.estadoNovoEstadoBt = this.createButton("Novo", "Adicionar Novo Estado",
				Resources.DEFAULTS.estadosNovo24());
		this.estadosToolBar.add(this.estadoNovoEstadoBt);

		this.estadosListEstadosBt = this.createButton("Listar", "Listar Estados", Resources.DEFAULTS.estadosListar24());
		this.estadosToolBar.add(this.estadosListEstadosBt);
	}

	private void createFuncionarios() {
		this.funcionariosNovoFuncionarioBt = this.createButton("Novo", "Adicionar Novo Funcionario",
				Resources.DEFAULTS.funcionariosNovo24());
		this.funcionariosToolBar.add(this.funcionariosNovoFuncionarioBt);

		this.funcionariosListFuncionariosBt = this.createButton("Listar", "Listar Funcionarios",
				Resources.DEFAULTS.funcionariosListar24());
		this.funcionariosToolBar.add(this.funcionariosListFuncionariosBt);
	}

	private void createLaboratorios() {
		this.laboratoriosNovoLaboratorioBt = this.createButton("Novo", "Adicionar Novo Laboratorio",
				Resources.DEFAULTS.laboratoriosNovo24());
		this.laboratoriosToolBar.add(this.laboratoriosNovoLaboratorioBt);

		this.laboratoriosListLaboratoriosBt = this.createButton("Listar", "Listar Laboratorios",
				Resources.DEFAULTS.laboratoriosListar24());
		this.laboratoriosToolBar.add(this.laboratoriosListLaboratoriosBt);
	}

	private void createProdutos() {
		this.produtosNovoProdutoBt = this.createButton("Novo", "Adicionar Novo Produto",
				Resources.DEFAULTS.produtosNovo24());
		this.produtosToolBar.add(this.produtosNovoProdutoBt);

		this.produtosListProdutosBt = this.createButton("Listar", "Listar Produtos",
				Resources.DEFAULTS.produtosListar24());
		this.produtosToolBar.add(this.produtosListProdutosBt);

		this.produtosToolBar.add(new SeparatorToolItem());

		// Tabela ABCFARMA
		this.importProdutosAbcfarmaBT = this.createButton(this.getI18nConstants().importExcelAbcfarma(),
				this.getI18nConstants().importExcelAbcfarma(), Resources.DEFAULTS.importExcelAbcfarma24());
		this.produtosToolBar.add(this.importProdutosAbcfarmaBT);

		this.produtosToolBar.add(new SeparatorToolItem());

		// Entrada de Produtos
		this.entradaProdutosNovoProdutoBt = this.createButton("Nova Entrada", "Adicionar Nova Entrada de Produto",
				Resources.DEFAULTS.produtosNovo24());
		this.produtosToolBar.add(this.entradaProdutosNovoProdutoBt);

		this.entradaProdutosListProdutosBt = this.createButton("Listar Entradas", "Listar Entradas de Produtos",
				Resources.DEFAULTS.produtosListar24());
		this.produtosToolBar.add(this.entradaProdutosListProdutosBt);
	}

	private void createVendedores() {
		this.vendedoresNovoVendedorBt = this.createButton("Novo", "Adicionar Novo Vendedor",
				Resources.DEFAULTS.vendedoresNovo24());
		this.vendedoresToolBar.add(this.vendedoresNovoVendedorBt);

		this.vendedoresListVendedoresBt = this.createButton("Listar", "Listar Vendedores",
				Resources.DEFAULTS.vendedoresListar24());
		this.vendedoresToolBar.add(this.vendedoresListVendedoresBt);
	}

	private void createVendas() {
		this.vendasNovoVendasBt = this.createButton("Novo", "Adicionar Nova Venda", Resources.DEFAULTS.vendasNovo24());
		this.vendasToolBar.add(this.vendasNovoVendasBt);

		this.vendasListVendasBt = this.createButton("Listar", "Listar Vendas", Resources.DEFAULTS.vendasListar24());
		this.vendasToolBar.add(this.vendasListVendasBt);
	}

	private void createRelatorios() {
		this.relatorioBalancoPeriodoBt = this.createButton("Balanco do Periodo", "Balanco do Periodo",
				Resources.DEFAULTS.relatorioBalancoPeriodo24());
		this.relatoriosToolBar.add(this.relatorioBalancoPeriodoBt);

		this.exportExcelCenarioBt = this.createButton("Exportar Cenario", "Exportar Cenario",
				Resources.DEFAULTS.exportExcelCenario24());
		this.relatoriosToolBar.add(this.exportExcelCenarioBt);
	}

	private Button createButton(String text, String toolTip, ImageResource icon) {
		Button bt = new Button(text, AbstractImagePrototype.create(icon));

		bt.setToolTip(toolTip);
		bt.setScale(ButtonScale.MEDIUM);
		bt.setIconAlign(IconAlign.TOP);
		bt.setArrowAlign(ButtonArrowAlign.BOTTOM);
		bt.setHeight(60);

		return bt;
	}

	@Override
	public Button getFornecedorNovoFornecedoresButton() {
		return this.fornecedoresNovoFornecedorBt;
	}

	@Override
	public Button getFornecedoresListFornecedoresButton() {
		return this.fornecedoresListFornecedoresBt;
	}

	@Override
	public Button getClienteNovoClientesButton() {
		return this.clientesNovoClienteBt;
	}

	@Override
	public Button getClientesListClientesButton() {
		return this.clientesListClientesBt;
	}

	@Override
	public Button getFuncionarioNovoFuncionariosButton() {
		return this.funcionariosNovoFuncionarioBt;
	}

	@Override
	public Button getFuncionariosListFuncionariosButton() {
		return this.funcionariosListFuncionariosBt;
	}

	@Override
	public Button getProdutoNovoProdutosButton() {
		return this.produtosNovoProdutoBt;
	}

	@Override
	public Button getProdutosListProdutosButton() {
		return this.produtosListProdutosBt;
	}

	@Override
	public Button getVendaNovoVendasButton() {
		return this.vendasNovoVendasBt;
	}

	@Override
	public Button getVendasListVendasButton() {
		return this.vendasListVendasBt;
	}

	@Override
	public Button getEntradaProdutosNovoEntradaProdutosButton() {
		return this.entradaProdutosNovoProdutoBt;
	}

	@Override
	public Button getEntradaProdutosListEntradaProdutosButton() {
		return this.entradaProdutosListProdutosBt;
	}

	@Override
	public Button getEstadoNovoEstadosButton() {
		return this.estadoNovoEstadoBt;
	}

	@Override
	public Button getEstadosListEstadosButton() {
		return this.estadosListEstadosBt;
	}

	@Override
	public Button getCidadeNovaCidadeButton() {
		return this.cidadeNovoCidadeBt;
	}

	@Override
	public Button getCidadesListCidadesButton() {
		return this.cidadesListCidadesBt;
	}

	@Override
	public Button getRelatorioBalancoPeriodoButton() {
		return this.relatorioBalancoPeriodoBt;
	}

	@Override
	public Button getVendedorNovoVendedoresButton() {
		return this.vendedoresNovoVendedorBt;
	}

	@Override
	public Button getVendedoresListVendedoresButton() {
		return this.vendedoresListVendedoresBt;
	}

	@Override
	public Button getLaboratorioNovoLaboratoriosButton() {
		return this.laboratoriosNovoLaboratorioBt;
	}

	@Override
	public Button getLaboratoriosListLaboratoriosButton() {
		return this.laboratoriosListLaboratoriosBt;
	}

	@Override
	public Button getProdutosImportProdutosAbcfarmaButton() {
		return this.importProdutosAbcfarmaBT;
	}

	@Override
	public Button getExportExcelCenarioButton() {
		return this.exportExcelCenarioBt;
	}
}
