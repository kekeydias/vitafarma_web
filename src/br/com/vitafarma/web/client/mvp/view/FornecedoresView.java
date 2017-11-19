package br.com.vitafarma.web.client.mvp.view;

import java.util.ArrayList;
import java.util.List;

import br.com.vitafarma.web.client.mvp.presenter.FornecedoresPresenter;
import br.com.vitafarma.web.shared.dtos.FornecedorDTO;
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
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;

public class FornecedoresView extends MyComposite implements
		FornecedoresPresenter.Display {
	private SimpleToolBar toolBar;
	private SimpleGrid<FornecedorDTO> gridPanel;
	private SimpleFilter filter;
	private TextField<String> nomeBuscaTextField;
	private TextField<String> nomeFantasiaBuscaTextField;
	private SizedNumberField cnpjBuscaTextField;
	private NumberField telefoneBuscaTextField;
	private TextField<String> enderecoBuscaTextField;
	private TextField<String> emailBuscaTextField;
	private ContentPanel panel;
	private GTabItem tabItem;

	public FornecedoresView() {
		this.initUI();
	}

	private void initUI() {
		this.panel = new ContentPanel(new BorderLayout());
		this.panel.setHeading(getI18nConstants().fornecedoresHeadingPanel());

		this.createToolBar();
		this.createGrid();
		this.createFilter();
		this.createTabItem();
		this.initComponent(this.tabItem);
	}

	private void createTabItem() {
		this.tabItem = new GTabItem(getI18nConstants().fornecedores(),
				Resources.DEFAULTS.fornecedores16());
		this.tabItem.setContent(this.panel);
	}

	private void createToolBar() {
		this.toolBar = new SimpleToolBar(true, true, true, false, true, this);
		this.panel.setTopComponent(this.toolBar);
	}

	private void createGrid() {
		BorderLayoutData bld = new BorderLayoutData(LayoutRegion.CENTER);
		bld.setMargins(new Margins(5, 5, 5, 5));

		this.gridPanel = new SimpleGrid<FornecedorDTO>(getColumnList(), this);
		this.panel.add(this.gridPanel, bld);
	}

	public List<ColumnConfig> getColumnList() {
		List<ColumnConfig> list = new ArrayList<ColumnConfig>();

		list.add(new ColumnConfig(FornecedorDTO.PROPERTY_FORNECEDOR_ID,
				getI18nConstants().codigoFornecedor(), 200));
		list.add(new ColumnConfig(FornecedorDTO.PROPERTY_FORNECEDOR_NOME,
				getI18nConstants().nome(), 200));
		list.add(new ColumnConfig(
				FornecedorDTO.PROPERTY_FORNECEDOR_NOME_FANTASIA,
				getI18nConstants().nomeFantasia(), 200));
		list.add(new ColumnConfig(FornecedorDTO.PROPERTY_FORNECEDOR_CNPJ_STRING,
				getI18nConstants().cnpj(), 150));
		list.add(new ColumnConfig(FornecedorDTO.PROPERTY_FORNECEDOR_TELEFONE_STRING,
				getI18nConstants().telefone(), 100));
		list.add(new ColumnConfig(FornecedorDTO.PROPERTY_FORNECEDOR_ENDERECO,
				getI18nConstants().endereco(), 300));
		list.add(new ColumnConfig(FornecedorDTO.PROPERTY_FORNECEDOR_EMAIL,
				getI18nConstants().email(), 200));

		return list;
	}

	private void createFilter() {
		BorderLayoutData bld = new BorderLayoutData(LayoutRegion.EAST);
		bld.setMargins(new Margins(5, 5, 5, 0));
		bld.setCollapsible(true);

		this.filter = new SimpleFilter();

		this.nomeBuscaTextField = new TextField<String>();
		this.nomeBuscaTextField.setFieldLabel(getI18nConstants().nome());

		this.nomeFantasiaBuscaTextField = new TextField<String>();
		this.nomeFantasiaBuscaTextField.setFieldLabel(getI18nConstants()
				.nomeFantasia());

		this.cnpjBuscaTextField = new SizedNumberField(this, 11, 11);
		this.cnpjBuscaTextField.setFieldLabel(getI18nConstants().cnpj());

		this.telefoneBuscaTextField = new NumberField();
		this.telefoneBuscaTextField
				.setFieldLabel(getI18nConstants().telefone());

		this.enderecoBuscaTextField = new TextField<String>();
		this.enderecoBuscaTextField
				.setFieldLabel(getI18nConstants().endereco());

		this.emailBuscaTextField = new TextField<String>();
		this.emailBuscaTextField.setFieldLabel(getI18nConstants().email());

		this.filter.addField(this.nomeBuscaTextField);
		this.filter.addField(this.nomeFantasiaBuscaTextField);
		this.filter.addField(this.cnpjBuscaTextField);
		this.filter.addField(this.telefoneBuscaTextField);
		this.filter.addField(this.enderecoBuscaTextField);
		this.filter.addField(this.emailBuscaTextField);

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
	public SimpleGrid<FornecedorDTO> getGrid() {
		return this.gridPanel;
	}

	@Override
	public void setProxy(RpcProxy<PagingLoadResult<FornecedorDTO>> proxy) {
		this.gridPanel.setProxy(proxy);
	}

	@Override
	public TextField<String> getNomeBuscaTextField() {
		return this.nomeBuscaTextField;
	}

	@Override
	public NumberField getCnpjBuscaTextField() {
		return this.cnpjBuscaTextField;
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
	public TextField<String> getNomeFantasiaBuscaTextField() {
		return this.nomeFantasiaBuscaTextField;
	}

	@Override
	public Button getExportExcelButton() {
		return this.toolBar.getExportExcelButton();
	}

	@Override
	public NumberField getTelefoneBuscaTextField() {
		return this.telefoneBuscaTextField;
	}

	@Override
	public TextField<String> getEnderecoBuscaTextField() {
		return this.enderecoBuscaTextField;
	}

	@Override
	public TextField<String> getEmailBuscaTextField() {
		return this.emailBuscaTextField;
	}
}
