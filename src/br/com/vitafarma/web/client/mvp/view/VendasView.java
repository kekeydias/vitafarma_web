package br.com.vitafarma.web.client.mvp.view;

import java.util.ArrayList;
import java.util.List;

import br.com.vitafarma.web.client.mvp.presenter.VendasPresenter;
import br.com.vitafarma.web.shared.dtos.VendaDTO;
import br.com.vitafarma.web.shared.mvp.view.MyComposite;
import br.com.vitafarma.web.shared.util.resources.Resources;
import br.com.vitafarma.web.shared.util.view.SizedNumberField;
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

public class VendasView extends MyComposite implements VendasPresenter.Display {
	private SimpleToolBar toolBar;
	private SimpleGrid<VendaDTO> gridPanel;
	private SimpleFilter filter;
	private TextField<String> nomeClienteTextField;
	private NumberField codigoClienteTextField;
	private SizedNumberField cpfClienteTextField;
	private DateField dataVendaTextField;
	private ContentPanel panel;
	private GTabItem tabItem;
	private Button incluirItensVendaBT;
	private Button emitirCupomVendaBT;

	public VendasView() {
		this.initUI();
	}

	private void initUI() {
		this.panel = new ContentPanel(new BorderLayout());
		this.panel.setHeading(this.getI18nConstants().vendasHeadingPanel());

		this.createToolBar();
		this.createGrid();
		this.createFilter();
		this.createTabItem();
		this.initComponent(this.tabItem);
	}

	private void createTabItem() {
		this.tabItem = new GTabItem(this.getI18nConstants().vendas(),
				Resources.DEFAULTS.vendas16());

		this.tabItem.setContent(this.panel);
	}

	private void createToolBar() {
		this.toolBar = new SimpleToolBar(true, true, true, true, true, this);

		this.toolBar.add(new SeparatorToolItem());

		// Botão de inclusão de itens na venda
		this.incluirItensVendaBT = this.toolBar.createButton(this
				.getI18nConstants().incluirItensVenda(), Resources.DEFAULTS
				.incluirItemVenda16());
		this.toolBar.add(this.incluirItensVendaBT);

		// Botão de emissão do cupom fiscal da venda
		this.emitirCupomVendaBT = this.toolBar.createButton(this
				.getI18nConstants().emitirCupomFiscalVenda(),
				Resources.DEFAULTS.exportar16());
		this.toolBar.add(this.emitirCupomVendaBT);

		this.panel.setTopComponent(this.toolBar);
	}

	private void createGrid() {
		BorderLayoutData bld = new BorderLayoutData(LayoutRegion.CENTER);
		bld.setMargins(new Margins(5, 5, 5, 5));

		this.gridPanel = new SimpleGrid<VendaDTO>(this.getColumnList(), this);
		this.panel.add(this.gridPanel, bld);
	}

	public List<ColumnConfig> getColumnList() {
		List<ColumnConfig> list = new ArrayList<ColumnConfig>();

		list.add(new ColumnConfig(VendaDTO.PROPERTY_VENDA_ID, this
				.getI18nConstants().codigoVenda(), 120));
		list.add(new ColumnConfig(VendaDTO.PROPERTY_VENDA_CLIENTE_ID, this
				.getI18nConstants().codigoCliente(), 120));
		list.add(new ColumnConfig(VendaDTO.PROPERTY_VENDA_CLIENTE_NOME, this
				.getI18nConstants().nomeCliente(), 300));
		list.add(new ColumnConfig(VendaDTO.PROPERTY_VENDA_CLIENTE_CPF_STRING,
				this.getI18nConstants().cpfCliente(), 150));
		list.add(new ColumnConfig(VendaDTO.PROPERTY_VENDA_DATA_STRING, this
				.getI18nConstants().dataVenda(), 130));
		list.add(new ColumnConfig(VendaDTO.PROPERTY_VENDA_SUBTOTAL_STRING, this
				.getI18nConstants().subtotal(), 100));
		list.add(new ColumnConfig(VendaDTO.PROPERTY_VENDA_DESCONTO_STRING, this
				.getI18nConstants().desconto(), 100));
		list.add(new ColumnConfig(VendaDTO.PROPERTY_VENDA_VALOR_FINAL_STRING,
				this.getI18nConstants().valorFinal(), 100));
		list.add(new ColumnConfig(VendaDTO.PROPERTY_VENDA_CUPOM_VENDA_STRING,
				this.getI18nConstants().cupomFiscal(), 100));

		return list;
	}

	private void createFilter() {
		BorderLayoutData bld = new BorderLayoutData(LayoutRegion.EAST);
		bld.setMargins(new Margins(5, 5, 5, 0));
		bld.setCollapsible(true);

		this.filter = new SimpleFilter();
		this.filter.setWidth(400);

		this.nomeClienteTextField = new TextField<String>();
		this.nomeClienteTextField.setFieldLabel(this.getI18nConstants()
				.nomeCliente());

		this.codigoClienteTextField = new NumberField();
		this.codigoClienteTextField.setFieldLabel(this.getI18nConstants()
				.codigoCliente());

		this.cpfClienteTextField = new SizedNumberField(this, 11, 11);
		this.cpfClienteTextField.setFieldLabel(this.getI18nConstants()
				.cpfCliente());

		this.dataVendaTextField = new DateField();
		this.dataVendaTextField.setFieldLabel(this.getI18nConstants()
				.dataVenda());

		this.filter.addField(this.codigoClienteTextField);
		this.filter.addField(this.nomeClienteTextField);
		this.filter.addField(this.cpfClienteTextField);
		this.filter.addField(this.dataVendaTextField);

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
	public void setProxy(RpcProxy<PagingLoadResult<VendaDTO>> proxy) {
		this.gridPanel.setProxy(proxy);
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
	public Button getImportExcelButton() {
		return this.toolBar.getImportExcelButton();
	}

	@Override
	public Button getIncluirProdutosVendaButton() {
		return this.incluirItensVendaBT;
	}

	@Override
	public TextField<String> getNomeClienteBuscaTF() {
		return this.nomeClienteTextField;
	}

	@Override
	public SimpleGrid<VendaDTO> getGrid() {
		return this.gridPanel;
	}

	@Override
	public Button getExportExcelButton() {
		return this.toolBar.getExportExcelButton();
	}

	@Override
	public NumberField getCpfClienteBuscaTF() {
		return this.cpfClienteTextField;
	}

	@Override
	public DateField getDataVendaBuscaTF() {
		return this.dataVendaTextField;
	}

	@Override
	public Button getEmitirCupomVendaButton() {
		return this.emitirCupomVendaBT;
	}

	@Override
	public NumberField getCodigoClienteBuscaTF() {
		return this.codigoClienteTextField;
	}
}
