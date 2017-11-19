package br.com.vitafarma.web.client.mvp.view;

import java.util.ArrayList;
import java.util.List;

import br.com.vitafarma.web.client.mvp.presenter.ProdutosPresenter;
import br.com.vitafarma.web.shared.dtos.ProdutoDTO;
import br.com.vitafarma.web.shared.mvp.view.MyComposite;
import br.com.vitafarma.web.shared.util.resources.Resources;
import br.com.vitafarma.web.shared.util.view.GTabItem;
import br.com.vitafarma.web.shared.util.view.SimpleFilter;
import br.com.vitafarma.web.shared.util.view.SimpleGrid;
import br.com.vitafarma.web.shared.util.view.SimpleToolBar;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;

public class ProdutosView extends MyComposite implements
		ProdutosPresenter.Display {
	private SimpleToolBar toolBar;
	private SimpleGrid<ProdutoDTO> gridPanel;
	private SimpleFilter filter;
	private NumberField codigoBuscaTextField;
	private NumberField medAbcBuscaTextField;
	private TextField<String> nomeBuscaTextField;
	private TextField<String> nomeLaboratorioBuscaTextField;
	private NumberField precoBuscaTextField;
	private ContentPanel panel;
	private GTabItem tabItem;
	private Button atualizarEstoqueBT;

	public ProdutosView() {
		this.initUI();
	}

	private void initUI() {
		this.panel = new ContentPanel(new BorderLayout());
		this.panel.setHeading(this.getI18nConstants().produtosHeadingPanel());

		this.createToolBar();
		this.createGrid();
		this.createFilter();
		this.createTabItem();
		this.initComponent(this.tabItem);
	}

	private void createTabItem() {
		this.tabItem = new GTabItem(this.getI18nConstants().produtos(),
				Resources.DEFAULTS.produtos16());

		this.tabItem.setContent(this.panel);
	}

	private void createToolBar() {
		this.toolBar = new SimpleToolBar(true, true, true, true, true, this);
		this.panel.setTopComponent(this.toolBar);
		this.toolBar.add(new SeparatorToolItem());

		// Atualizar stoque de produtos
		this.atualizarEstoqueBT = this.toolBar.createButton(this
				.getI18nConstants().atualizarEstoque(), Resources.DEFAULTS
				.atualizaEstoque16());

		this.toolBar.add(this.atualizarEstoqueBT);
	}

	private void createGrid() {
		BorderLayoutData bld = new BorderLayoutData(LayoutRegion.CENTER);
		bld.setMargins(new Margins(5, 5, 5, 5));

		this.gridPanel = new SimpleGrid<ProdutoDTO>(this.getColumnList(), this);
		this.panel.add(this.gridPanel, bld);
	}

	public List<ColumnConfig> getColumnList() {
		List<ColumnConfig> list = new ArrayList<ColumnConfig>();

		list.add(new ColumnConfig(ProdutoDTO.PROPERTY_PRODUTO_ID, this
				.getI18nConstants().codigoProduto(), 120));
		list.add(new ColumnConfig(ProdutoDTO.PROPERTY_PRODUTO_MED_ABC_STRING,
				this.getI18nConstants().codigoProdutoAbcFarma(), 120));
		list.add(new ColumnConfig(ProdutoDTO.PROPERTY_PRODUTO_NOME, this
				.getI18nConstants().nome(), 300));
		list.add(new ColumnConfig(ProdutoDTO.PROPERTY_PRODUTO_DESCRICAO, this
				.getI18nConstants().descricao(), 300));
		list.add(new ColumnConfig(
				ProdutoDTO.PROPERTY_PRODUTO_LABORATORIO_STRING, this
						.getI18nConstants().laboratorio(), 200));
		list.add(new ColumnConfig(ProdutoDTO.PROPERTY_PRODUTO_PRECO_STRING,
				this.getI18nConstants().preco(), 100));
		list.add(new ColumnConfig(ProdutoDTO.PROPERTY_PRODUTO_ESTOQUE, this
				.getI18nConstants().estoqueProduto(), 100));
		list.add(new ColumnConfig(ProdutoDTO.PROPERTY_PRODUTO_UNIDADE_ENTRADA,
				this.getI18nConstants().unidadeEntrada(), 100));
		list.add(new ColumnConfig(ProdutoDTO.PROPERTY_PRODUTO_UNIDADE_VENDA,
				this.getI18nConstants().unidadeVenda(), 100));

		return list;
	}

	private void createFilter() {
		BorderLayoutData bld = new BorderLayoutData(LayoutRegion.EAST);
		bld.setMargins(new Margins(5, 5, 5, 0));
		bld.setCollapsible(true);

		this.filter = new SimpleFilter();

		this.codigoBuscaTextField = new NumberField();
		this.codigoBuscaTextField.setFieldLabel(this.getI18nConstants()
				.codigoProduto());

		this.medAbcBuscaTextField = new NumberField();
		this.medAbcBuscaTextField.setFieldLabel(this.getI18nConstants()
				.codigoProdutoAbcFarma());

		this.nomeBuscaTextField = new TextField<String>();
		this.nomeBuscaTextField.setFieldLabel(this.getI18nConstants().nome());

		this.nomeLaboratorioBuscaTextField = new TextField<String>();
		this.nomeLaboratorioBuscaTextField.setFieldLabel(this
				.getI18nConstants().laboratorio());

		this.precoBuscaTextField = new NumberField();
		this.precoBuscaTextField.setFieldLabel(this.getI18nConstants().preco());

		this.filter.addField(this.codigoBuscaTextField);
		this.filter.addField(this.medAbcBuscaTextField);
		this.filter.addField(this.nomeBuscaTextField);
		this.filter.addField(this.nomeLaboratorioBuscaTextField);
		this.filter.addField(this.precoBuscaTextField);

		this.panel.add(this.filter, bld);
	}

	@Override
	public Button getNewButton() {
		return this.toolBar.getNewButton();
	}

	@Override
	public Button getEditButton() {
		return this.toolBar.getEditButton();
	}

	@Override
	public Button getRemoveButton() {
		return this.toolBar.getRemoveButton();
	}

	@Override
	public SimpleGrid<ProdutoDTO> getGrid() {
		return this.gridPanel;
	}

	@Override
	public void setProxy(RpcProxy<PagingLoadResult<ProdutoDTO>> proxy) {
		this.gridPanel.setProxy(proxy);
	}

	@Override
	public TextField<String> getNomeBuscaTextField() {
		return this.nomeBuscaTextField;
	}

	@Override
	public NumberField getPrecoBuscaTextField() {
		return this.precoBuscaTextField;
	}

	@Override
	public Button getSubmitBuscaButton() {
		return this.filter.getSubmitButton();
	}

	@Override
	public Button getResetBuscaButton() {
		return this.filter.getResetButton();
	}

	@Override
	public Button getExportExcelButton() {
		return this.toolBar.getExportExcelButton();
	}

	@Override
	public Button getImportExcelButton() {
		return this.toolBar.getImportExcelButton();
	}

	@Override
	public NumberField getCodigoBuscaTextField() {
		return this.codigoBuscaTextField;
	}

	@Override
	public Button getAtualizaEstoqueButton() {
		return this.atualizarEstoqueBT;
	}

	@Override
	public TextField<String> getNomeLaboratorioBuscaTextField() {
		return this.nomeLaboratorioBuscaTextField;
	}

	@Override
	public NumberField getMedAbcBuscaTextField() {
		return this.medAbcBuscaTextField;
	}
}
