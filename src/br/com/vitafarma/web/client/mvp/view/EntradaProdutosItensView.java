package br.com.vitafarma.web.client.mvp.view;

import java.util.ArrayList;
import java.util.List;

import br.com.vitafarma.web.client.mvp.presenter.EntradaProdutosItensPresenter;
import br.com.vitafarma.web.shared.dtos.EntradaProdutoDTO;
import br.com.vitafarma.web.shared.dtos.EntradaProdutoGroupDTO;
import br.com.vitafarma.web.shared.mvp.view.MyComposite;
import br.com.vitafarma.web.shared.util.resources.Resources;
import br.com.vitafarma.web.shared.util.view.GTabItem;
import br.com.vitafarma.web.shared.util.view.SimpleGrid;
import br.com.vitafarma.web.shared.util.view.SimpleToolBar;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;

public class EntradaProdutosItensView extends MyComposite implements
		EntradaProdutosItensPresenter.Display {
	private SimpleToolBar toolBar;
	private SimpleGrid<EntradaProdutoDTO> gridPanel;
	private EntradaProdutoGroupDTO entradaProdutoGroupDTO;
	private ContentPanel panel;
	private GTabItem tabItem;

	public EntradaProdutosItensView(
			EntradaProdutoGroupDTO entradaProdutoGroupDTO) {
		this.entradaProdutoGroupDTO = entradaProdutoGroupDTO;
		this.initUI();
	}

	private void initUI() {
		this.panel = new ContentPanel(new BorderLayout());

		if (this.entradaProdutoGroupDTO.getId() == null) {
			this.panel.setHeading(this.getI18nConstants()
					.itensEntradaHeadingPanel());
		} else {
			this.panel.setHeading(this.getI18nConstants()
					.itensEntradaHeadingPanel()
					+ " ("
					+ this.getI18nConstants().notaFiscal()
					+ " "
					+ this.entradaProdutoGroupDTO.getNotaFiscalCodigo() + ")");
		}

		this.createToolBar();
		this.createGrid();
		this.createTabItem();
		this.initComponent(this.tabItem);
	}

	private void createTabItem() {
		this.tabItem = new GTabItem(this.getI18nConstants()
				.itensEntradaHeadingPanel(),
				Resources.DEFAULTS.incluirItemEntrada16());

		this.tabItem.setContent(this.panel);
	}

	private void createToolBar() {
		this.toolBar = new SimpleToolBar(true, true, true, false, true, this);
		this.panel.setTopComponent(this.toolBar);
	}

	private void createGrid() {
		BorderLayoutData bld = new BorderLayoutData(LayoutRegion.CENTER);
		bld.setMargins(new Margins(5, 5, 5, 5));

		this.gridPanel = new SimpleGrid<EntradaProdutoDTO>(
				this.getColumnList(), this);
		this.panel.add(this.gridPanel, bld);
	}

	public List<ColumnConfig> getColumnList() {
		List<ColumnConfig> list = new ArrayList<ColumnConfig>();

		list.add(new ColumnConfig(
				EntradaProdutoDTO.PROPERTY_ENTRADA_PRODUTO_ID, this
						.getI18nConstants().item(), 80));
		list.add(new ColumnConfig(
				EntradaProdutoDTO.PROPERTY_ENTRADA_PRODUTO_PRODUTO_ID, this
						.getI18nConstants().codigoProduto(), 120));
		list.add(new ColumnConfig(
				EntradaProdutoDTO.PROPERTY_ENTRADA_PRODUTO_PRODUTO_STRING, this
						.getI18nConstants().produto(), 300));
		list.add(new ColumnConfig(
				EntradaProdutoDTO.PROPERTY_ENTRADA_PRODUTO_FORNECEDOR_STRING,
				this.getI18nConstants().fornecedor(), 120));
		list.add(new ColumnConfig(
				EntradaProdutoDTO.PROPERTY_ENTRADA_PRODUTO_PRECO_UNITARIO_STRING,
				this.getI18nConstants().precoUnitario(), 120));
		list.add(new ColumnConfig(
				EntradaProdutoDTO.PROPERTY_ENTRADA_PRODUTO_QUANTIDADE_STRING,
				this.getI18nConstants().quantidade(), 80));
		list.add(new ColumnConfig(
				EntradaProdutoDTO.PROPERTY_ENTRADA_PRODUTO_VALOR_TOTAL_STRING,
				this.getI18nConstants().totalEntradaItem(), 80));

		return list;
	}

	@Override
	public Button getNewButton() {
		return this.toolBar.getNewButton();
	}

	@Override
	public Button getRemoveButton() {
		return this.toolBar.getRemoveButton();
	}

	@Override
	public SimpleGrid<EntradaProdutoDTO> getGrid() {
		return this.gridPanel;
	}

	@Override
	public EntradaProdutoGroupDTO getEntradaProdutoGroupDTO() {
		return this.entradaProdutoGroupDTO;
	}

	@Override
	public Button getExportExcelButton() {
		return this.toolBar.getExportExcelButton();
	}

	@Override
	public void setProxy(RpcProxy<PagingLoadResult<EntradaProdutoDTO>> proxy) {
		this.gridPanel.setProxy(proxy);
	}

	@Override
	public Button getEditButton() {
		return this.toolBar.getEditButton();
	}
}
