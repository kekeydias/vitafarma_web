package br.com.vitafarma.web.client.mvp.view;

import java.util.ArrayList;
import java.util.List;

import br.com.vitafarma.web.client.mvp.presenter.EntradaProdutosGroupPresenter;
import br.com.vitafarma.web.shared.dtos.EntradaProdutoGroupDTO;
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
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;

public class EntradaProdutosGroupView extends MyComposite implements
		EntradaProdutosGroupPresenter.Display {
	private SimpleToolBar toolBar;
	private SimpleGrid<EntradaProdutoGroupDTO> gridPanel;
	private SimpleFilter filter;
	private DateField dataEntradaBuscaTextField;
	private NumberField codigoFornecedorBuscaTextField;
	private TextField<String> nomeFornecedorBuscaTextField;
	private TextField<String> notaFiscalBuscaTextField;
	private ContentPanel panel;
	private GTabItem tabItem;
	private Button incluirItensEntradaBT;

	public EntradaProdutosGroupView() {
		this.initUI();
	}

	private void initUI() {
		this.panel = new ContentPanel(new BorderLayout());
		this.panel.setHeading(this.getI18nConstants()
				.entradaProdutosGroupHeadingPanel());

		this.createToolBar();
		this.createGrid();
		this.createFilter();
		this.createTabItem();
		this.initComponent(this.tabItem);
	}

	private void createTabItem() {
		this.tabItem = new GTabItem(this.getI18nConstants().entradaProdutos(),
				Resources.DEFAULTS.entradaProdutos16());

		this.tabItem.setContent(this.panel);
	}

	private void createToolBar() {
		this.toolBar = new SimpleToolBar(true, true, true, false, true, this);

		this.toolBar.add(new SeparatorToolItem());

		// Botão de inclusão de itens na venda
		this.incluirItensEntradaBT = this.toolBar.createButton(this
				.getI18nConstants().incluirItensEntrada(), Resources.DEFAULTS
				.incluirItemEntrada16());
		this.toolBar.add(this.incluirItensEntradaBT);

		this.panel.setTopComponent(this.toolBar);
	}

	private void createGrid() {
		BorderLayoutData bld = new BorderLayoutData(LayoutRegion.CENTER);
		bld.setMargins(new Margins(5, 5, 5, 5));

		this.gridPanel = new SimpleGrid<EntradaProdutoGroupDTO>(
				this.getColumnList(), this);
		this.panel.add(this.gridPanel, bld);
	}

	public List<ColumnConfig> getColumnList() {
		List<ColumnConfig> list = new ArrayList<ColumnConfig>();

		list.add(new ColumnConfig(
				EntradaProdutoGroupDTO.PROPERTY_ENTRADA_PRODUTO_GROUP_ID, this
						.getI18nConstants().codigoEntradaProduto(), 120));
		list.add(new ColumnConfig(
				EntradaProdutoGroupDTO.PROPERTY_ENTRADA_PRODUTO_GROUP_NOTA_FISCAL_CODIGO,
				this.getI18nConstants().notaFiscal(), 120));
		list.add(new ColumnConfig(
				EntradaProdutoGroupDTO.PROPERTY_ENTRADA_PRODUTO_GROUP_FORNECEDOR_ID,
				this.getI18nConstants().codigoFornecedor(), 120));
		list.add(new ColumnConfig(
				EntradaProdutoGroupDTO.PROPERTY_ENTRADA_PRODUTO_GROUP_FORNECEDOR_STRING,
				this.getI18nConstants().fornecedor(), 250));
		list.add(new ColumnConfig(
				EntradaProdutoGroupDTO.PROPERTY_ENTRADA_PRODUTO_GROUP_DATA_ENTRADA_STRING,
				this.getI18nConstants().dataEntrada(), 130));
		list.add(new ColumnConfig(
				EntradaProdutoGroupDTO.PROPERTY_ENTRADA_PRODUTO_GROUP_VALOR_TOTAL_STRING,
				this.getI18nConstants().totalEntradaGroup(), 120));

		return list;
	}

	private void createFilter() {
		BorderLayoutData bld = new BorderLayoutData(LayoutRegion.EAST);
		bld.setMargins(new Margins(5, 5, 5, 0));
		bld.setCollapsible(true);

		this.filter = new SimpleFilter();
		this.filter.setWidth(400);

		this.codigoFornecedorBuscaTextField = new NumberField();
		this.codigoFornecedorBuscaTextField.setFieldLabel(this
				.getI18nConstants().codigoFornecedor());

		this.nomeFornecedorBuscaTextField = new TextField<String>();
		this.nomeFornecedorBuscaTextField.setFieldLabel(this.getI18nConstants()
				.fornecedores());

		this.dataEntradaBuscaTextField = new DateField();
		this.dataEntradaBuscaTextField.setFieldLabel(this.getI18nConstants()
				.dataEntrada());

		this.notaFiscalBuscaTextField = new TextField<String>();
		this.notaFiscalBuscaTextField.setFieldLabel(this.getI18nConstants()
				.notaFiscal());

		this.filter.addField(this.codigoFornecedorBuscaTextField);
		this.filter.addField(this.nomeFornecedorBuscaTextField);
		this.filter.addField(this.dataEntradaBuscaTextField);
		this.filter.addField(this.notaFiscalBuscaTextField);

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
	public SimpleGrid<EntradaProdutoGroupDTO> getGrid() {
		return this.gridPanel;
	}

	@Override
	public void setProxy(
			RpcProxy<PagingLoadResult<EntradaProdutoGroupDTO>> proxy) {
		this.gridPanel.setProxy(proxy);
	}

	@Override
	public DateField getDataEntradaBuscaTextField() {
		return this.dataEntradaBuscaTextField;
	}

	@Override
	public TextField<String> getNomeFornecedorBuscaTextField() {
		return this.nomeFornecedorBuscaTextField;
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
	public TextField<String> getNotaFiscalBuscaTextField() {
		return this.notaFiscalBuscaTextField;
	}

	@Override
	public NumberField getCodigoFornecedorBuscaTextField() {
		return this.codigoFornecedorBuscaTextField;
	}

	@Override
	public Button getIncluirItensEntradaButton() {
		return this.incluirItensEntradaBT;
	}
}
