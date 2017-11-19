package br.com.vitafarma.web.shared.util.view;

import java.util.ArrayList;
import java.util.List;

import br.com.vitafarma.web.shared.i18n.IVitafarmaI18nGateway;

import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.LoadEvent;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.LoadListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ComponentPlugin;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridSelectionModel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.google.gwt.user.client.Element;

public class SimpleGrid<M extends BaseModel> extends ContentPanel {
	private Grid<M> grid;
	private RpcProxy<PagingLoadResult<M>> proxy;
	private PagingLoader<PagingLoadResult<ModelData>> loader;
	private List<ColumnConfig> columnList;
	private List<ComponentPlugin> plugins = new ArrayList<ComponentPlugin>();
	private IVitafarmaI18nGateway i18nGateway;

	public SimpleGrid(List<ColumnConfig> columnList, IVitafarmaI18nGateway i18nGateway) {
		super(new FitLayout());

		this.columnList = columnList;
		this.i18nGateway = i18nGateway;
		this.setHeaderVisible(false);
	}

	@Override
	protected void beforeRender() {
		super.beforeRender();

		this.loader = new BasePagingLoader<PagingLoadResult<ModelData>>(this.proxy);
		this.loader.setRemoteSort(true);

		ListStore<M> store = new ListStore<M>(this.loader);

		this.grid = new Grid<M>(store, new ColumnModel(this.columnList));
		this.grid.setStripeRows(true);
		this.grid.setBorders(true);
		this.grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		for (ComponentPlugin plugin : this.plugins) {
			this.grid.addPlugin(plugin);
		}

		this.grid.setBorders(false);
		this.pagingPanel();
		this.addLoadingListener();
		this.add(this.grid);
	}

	private void addLoadingListener() {
		this.loader.addLoadListener(new LoadListener() {
			@Override
			public void loaderBeforeLoad(LoadEvent le) {
				grid.mask(i18nGateway.getI18nMessages().loading(), "loading");
			}

			@Override
			public void loaderLoad(LoadEvent le) {
				grid.unmask();
			}
		});
	}

	@Override
	protected void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		this.loader.load();
	}

	public Grid<M> getGrid() {
		return this.grid;
	}

	public void updateList() {
		this.loader.load();
	}

	@Override
	public void addPlugin(ComponentPlugin plugin) {
		this.plugins.add(plugin);
	}

	private void pagingPanel() {
		PagingToolBar paggingToolBar = new PagingToolBar(25);
		paggingToolBar.bind(this.loader);
		this.setBottomComponent(paggingToolBar);
	}

	public GridSelectionModel<M> getSelectionModel() {
		return this.grid.getSelectionModel();
	}

	public void setProxy(RpcProxy<PagingLoadResult<M>> proxy) {
		this.proxy = proxy;
	}
}
