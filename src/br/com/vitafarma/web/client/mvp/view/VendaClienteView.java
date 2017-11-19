package br.com.vitafarma.web.client.mvp.view;

import java.util.ArrayList;
import java.util.List;

import br.com.vitafarma.web.client.mvp.presenter.VendaClientePresenter;
import br.com.vitafarma.web.shared.dtos.ItemVendaDTO;
import br.com.vitafarma.web.shared.dtos.VendaDTO;
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

public class VendaClienteView extends MyComposite implements
		VendaClientePresenter.Display {
	private SimpleToolBar toolBar;
	private SimpleGrid<ItemVendaDTO> gridPanel;
	private VendaDTO vendaDTO;
	private ContentPanel panel;
	private GTabItem tabItem;

	public VendaClienteView(VendaDTO vendaDTO) {
		this.vendaDTO = vendaDTO;
		this.initUI();
	}

	private void initUI() {
		this.panel = new ContentPanel(new BorderLayout());

		if (this.vendaDTO.getVendaId() == null) {
			this.panel.setHeading(this.getI18nConstants()
					.itensVendaHeadingPanel());
		} else {
			this.panel.setHeading(this.getI18nConstants()
					.itensVendaHeadingPanel()
					+ " ("
					+ this.vendaDTO.getVendaId() + ")");
		}

		this.createToolBar();
		this.createGrid();
		this.createTabItem();
		this.initComponent(this.tabItem);
	}

	private void createTabItem() {
		this.tabItem = new GTabItem(this.getI18nConstants()
				.itensVendaHeadingPanel(),
				Resources.DEFAULTS.incluirItemVenda16());

		this.tabItem.setContent(this.panel);
	}

	private void createToolBar() {
		this.toolBar = new SimpleToolBar(true, true, true, false, true, this);
		this.panel.setTopComponent(this.toolBar);
	}

	private void createGrid() {
		BorderLayoutData bld = new BorderLayoutData(LayoutRegion.CENTER);
		bld.setMargins(new Margins(5, 5, 5, 5));

		this.gridPanel = new SimpleGrid<ItemVendaDTO>(this.getColumnList(),
				this);
		this.panel.add(this.gridPanel, bld);
	}

	public List<ColumnConfig> getColumnList() {
		List<ColumnConfig> list = new ArrayList<ColumnConfig>();

		list.add(new ColumnConfig(ItemVendaDTO.PROPERTY_ITEM_VENDA_ID, this
				.getI18nConstants().item(), 80));
		list.add(new ColumnConfig(ItemVendaDTO.PROPERTY_ITEM_VENDA_PRODUTO_ID,
				this.getI18nConstants().codigoProduto(), 120));
		list.add(new ColumnConfig(
				ItemVendaDTO.PROPERTY_ITEM_VENDA_PRODUTO_STRING, this
						.getI18nConstants().produto(), 300));
		list.add(new ColumnConfig(ItemVendaDTO.PROPERTY_ITEM_VENDA_CLIENTE_ID,
				this.getI18nConstants().codigoCliente(), 120));
		list.add(new ColumnConfig(
				ItemVendaDTO.PROPERTY_ITEM_VENDA_CLIENTE_STRING, this
						.getI18nConstants().cliente(), 200));
		list.add(new ColumnConfig(
				ItemVendaDTO.PROPERTY_ITEM_VENDA_PRECO_UNITARIO_STRING, this
						.getI18nConstants().precoUnitario(), 120));
		list.add(new ColumnConfig(
				ItemVendaDTO.PROPERTY_ITEM_VENDA_QUANTIDADE_STRING, this
						.getI18nConstants().quantidade(), 80));
		list.add(new ColumnConfig(
				ItemVendaDTO.PROPERTY_ITEM_VENDA_SUBTOTAL_STRING, this
						.getI18nConstants().subtotal(), 100));
		list.add(new ColumnConfig(
				ItemVendaDTO.PROPERTY_ITEM_VENDA_DESCONTO_ITEM_STRING, this
						.getI18nConstants().descontoItem(), 150));
		list.add(new ColumnConfig(
				ItemVendaDTO.PROPERTY_ITEM_VENDA_PRECO_FINAL_STRING, this
						.getI18nConstants().precoFinal(), 80));
		list.add(new ColumnConfig(
				ItemVendaDTO.PROPERTY_ITEM_VENDA_CUPOM_VENDA_STRING, this
						.getI18nConstants().cupomFiscal(), 150));

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
	public SimpleGrid<ItemVendaDTO> getGrid() {
		return this.gridPanel;
	}

	@Override
	public VendaDTO getVendaDTO() {
		return this.vendaDTO;
	}

	@Override
	public Button getExportExcelButton() {
		return this.toolBar.getExportExcelButton();
	}

	@Override
	public void setProxy(RpcProxy<PagingLoadResult<ItemVendaDTO>> proxy) {
		this.gridPanel.setProxy(proxy);
	}

	@Override
	public Button getEditButton() {
		return this.toolBar.getEditButton();
	}
}
