package br.com.vitafarma.web.client.mvp.view;

import java.util.ArrayList;
import java.util.List;

import br.com.vitafarma.web.client.mvp.presenter.CidadesPresenter;
import br.com.vitafarma.web.shared.dtos.CidadeDTO;
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
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;

public class CidadesView extends MyComposite implements CidadesPresenter.Display {
	private SimpleToolBar toolBar = null;
	private SimpleGrid<CidadeDTO> gridPanel = null;
	private SimpleFilter filter = null;
	private TextField<String> nomeCidadeBuscaTextField = null;
	private TextField<String> nomeEstadoBuscaTextField = null;
	private ContentPanel panel = null;
	private GTabItem tabItem = null;

	public CidadesView() {
		this.initUI();
	}

	private void initUI() {
		this.panel = new ContentPanel(new BorderLayout());
		this.panel.setHeading(this.getI18nConstants().cidadesHeadingPanel());

		this.createToolBar();
		this.createGrid();
		this.createFilter();
		this.createTabItem();
		this.initComponent(this.tabItem);
	}

	private void createTabItem() {
		this.tabItem = new GTabItem(this.getI18nConstants().cidades(), Resources.DEFAULTS.cidades16());

		this.tabItem.setContent(this.panel);
	}

	private void createToolBar() {
		this.toolBar = new SimpleToolBar(true, true, true, false, true, this);

		this.panel.setTopComponent(this.toolBar);
	}

	private void createGrid() {
		BorderLayoutData bld = new BorderLayoutData(LayoutRegion.CENTER);
		bld.setMargins(new Margins(5, 5, 5, 5));

		this.gridPanel = new SimpleGrid<CidadeDTO>(this.getColumnList(), this);
		this.panel.add(this.gridPanel, bld);
	}

	public List<ColumnConfig> getColumnList() {
		List<ColumnConfig> list = new ArrayList<ColumnConfig>();

		list.add(new ColumnConfig(CidadeDTO.PROPERTY_CIDADE_ID, this.getI18nConstants().codigoCidade(), 120));
		list.add(new ColumnConfig(CidadeDTO.PROPERTY_CIDADE_NOME, this.getI18nConstants().nome(), 250));
		list.add(new ColumnConfig(CidadeDTO.PROPERTY_CIDADE_ESTADO_SIGLA, this.getI18nConstants().estado(), 50));

		return list;
	}

	private void createFilter() {
		BorderLayoutData bld = new BorderLayoutData(LayoutRegion.EAST);
		bld.setMargins(new Margins(5, 5, 5, 0));
		bld.setCollapsible(true);

		this.filter = new SimpleFilter();

		this.nomeCidadeBuscaTextField = new TextField<String>();
		this.nomeCidadeBuscaTextField.setFieldLabel(this.getI18nConstants().cidade());

		this.nomeEstadoBuscaTextField = new TextField<String>();
		this.nomeEstadoBuscaTextField.setFieldLabel(this.getI18nConstants().estado());

		this.filter.addField(this.nomeCidadeBuscaTextField);
		this.filter.addField(this.nomeEstadoBuscaTextField);

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
	public SimpleGrid<CidadeDTO> getGrid() {
		return this.gridPanel;
	}

	@Override
	public void setProxy(RpcProxy<PagingLoadResult<CidadeDTO>> proxy) {
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
	public Button getExportExcelButton() {
		return this.toolBar.getExportExcelButton();
	}

	@Override
	public TextField<String> getNomeCidadeBuscaTextField() {
		return this.nomeCidadeBuscaTextField;
	}

	@Override
	public TextField<String> getNomeEstadoBuscaTextField() {
		return this.nomeEstadoBuscaTextField;
	}
}
