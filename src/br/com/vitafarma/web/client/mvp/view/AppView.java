package br.com.vitafarma.web.client.mvp.view;

import br.com.vitafarma.web.client.mvp.presenter.AppPresenter;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nConstants;
import br.com.vitafarma.web.shared.mvp.view.MyComposite;
import br.com.vitafarma.web.shared.util.resources.Resources;
import br.com.vitafarma.web.shared.util.view.CenarioPanel;
import br.com.vitafarma.web.shared.util.view.GTab;

import com.extjs.gxt.ui.client.Style.ButtonArrowAlign;
import com.extjs.gxt.ui.client.Style.ButtonScale;
import com.extjs.gxt.ui.client.Style.IconAlign;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Widget;

public class AppView extends MyComposite implements AppPresenter.Display {
	private Viewport viewport = null;
	private ContentPanel panel = null;

	private GTab tab = null;
	private CenarioPanel cenarioPanel = null;

	public AppView() {
		this.initUI();
	}

	private void initUI() {
		VitafarmaI18nConstants i18nConstants = GWT.create(VitafarmaI18nConstants.class);

		this.viewport = new Viewport();
		this.viewport.setLayout(new FitLayout());

		this.panel = new ContentPanel(new BorderLayout());
		this.panel.setIcon(AbstractImagePrototype.create(Resources.DEFAULTS.logo()));
		this.panel.getHeader().addTool(this.getLogoutButton());
		this.panel.getHeader().setTitle(i18nConstants.vitafarmaVersion());

		this.viewport.add(this.panel);

		this.createWest();
		this.createCenter();

		this.initComponent(this.viewport);
	}

	private void createWest() {
		// FIXME -- Aba de cenário
		// BorderLayoutData bld = new BorderLayoutData(LayoutRegion.WEST);
		// bld.setMargins(new Margins(5, 0, 5, 5));
		// bld.setCollapsible(true);
		// bld.setFloatable(true);

		// this.cenarioPanel = new CenarioPanel();
		// this.panel.add(this.cenarioPanel, bld);
	}

	private void createCenter() {
		BorderLayoutData bld = new BorderLayoutData(LayoutRegion.CENTER);
		bld.setMargins(new Margins(5, 5, 5, 5));

		this.tab = new GTab();
		this.panel.add(this.tab, bld);
	}

	public Button getLogoutButton() {
		Button b = this.createButton("Sair", "Sair", Resources.DEFAULTS.logout16());

		b.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				// FIXME -- Logout da sessão
				// Window.open("../resources/j_spring_security_logout"
				// + VitafarmaUtil.paramsDebug(), "_self", "");
				return;
			}
		});

		return b;
	}

	private Button createButton(String text, String toolTip, ImageResource icon) {
		Button bt = new Button(text, AbstractImagePrototype.create(icon));

		bt.setToolTip(toolTip);
		bt.setScale(ButtonScale.MEDIUM);
		bt.setIconAlign(IconAlign.RIGHT);
		bt.setArrowAlign(ButtonArrowAlign.BOTTOM);
		bt.setHeight(25);

		return bt;
	}

	@Override
	public ContentPanel getPanel() {
		return this.panel;
	}

	@Override
	public GTab getGTab() {
		return this.tab;
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public CenarioPanel getCenarioPanel() {
		return this.cenarioPanel;
	}
}
