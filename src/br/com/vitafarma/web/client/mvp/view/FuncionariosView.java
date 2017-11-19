package br.com.vitafarma.web.client.mvp.view;

import java.util.ArrayList;
import java.util.List;

import br.com.vitafarma.web.client.mvp.presenter.FuncionariosPresenter;
import br.com.vitafarma.web.shared.dtos.FuncionarioDTO;
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

public class FuncionariosView extends MyComposite implements
		FuncionariosPresenter.Display {
	private SimpleToolBar toolBar;
	private SimpleGrid<FuncionarioDTO> gridPanel;
	private SimpleFilter filter;
	private TextField<String> nomeBuscaTextField;
	private NumberField cpfBuscaTextField;
	private NumberField telefoneBuscaTextField;
	private TextField<String> enderecoBuscaTextField;
	private TextField<String> emailBuscaTextField;
	private ContentPanel panel;
	private GTabItem tabItem;

	public FuncionariosView() {
		this.initUI();
	}

	private void initUI() {
		this.panel = new ContentPanel(new BorderLayout());
		this.panel.setHeading(getI18nConstants().funcionariosHeadingPanel());

		this.createToolBar();
		this.createGrid();
		this.createFilter();
		this.createTabItem();
		this.initComponent(this.tabItem);
	}

	private void createTabItem() {
		this.tabItem = new GTabItem(getI18nConstants().funcionarios(),
				Resources.DEFAULTS.funcionarios16());

		this.tabItem.setContent(this.panel);
	}

	private void createToolBar() {
		this.toolBar = new SimpleToolBar(true, true, true, false, true, this);

		this.panel.setTopComponent(this.toolBar);
	}

	private void createGrid() {
		BorderLayoutData bld = new BorderLayoutData(LayoutRegion.CENTER);
		bld.setMargins(new Margins(5, 5, 5, 5));

		this.gridPanel = new SimpleGrid<FuncionarioDTO>(getColumnList(), this);
		this.panel.add(this.gridPanel, bld);
	}

	public List<ColumnConfig> getColumnList() {
		List<ColumnConfig> list = new ArrayList<ColumnConfig>();

		list.add(new ColumnConfig(FuncionarioDTO.PROPERTY_FUNCIONARIO_ID,
				getI18nConstants().codigoFuncionario(), 120));
		list.add(new ColumnConfig(FuncionarioDTO.PROPERTY_FUNCIONARIO_NOME,
				getI18nConstants().nome(), 200));
		list.add(new ColumnConfig(
				FuncionarioDTO.PROPERTY_FUNCIONARIO_CPF_STRING,
				getI18nConstants().cpf(), 120));
		list.add(new ColumnConfig(
				FuncionarioDTO.PROPERTY_FUNCIONARIO_SALARIO_STRING,
				getI18nConstants().salario(), 100));
		list.add(new ColumnConfig(
				FuncionarioDTO.PROPERTY_FUNCIONARIO_DATA_ADIMISSAO_STRING,
				getI18nConstants().dataAdimissao(), 130));
		list.add(new ColumnConfig(
				FuncionarioDTO.PROPERTY_FUNCIONARIO_DATA_DEMISSAO_STRING,
				getI18nConstants().dataDemissao(), 130));
		list.add(new ColumnConfig(FuncionarioDTO.PROPERTY_FUNCIONARIO_TELEFONE,
				getI18nConstants().telefone(), 150));
		list.add(new ColumnConfig(FuncionarioDTO.PROPERTY_FUNCIONARIO_ENDERECO,
				getI18nConstants().endereco(), 300));
		list.add(new ColumnConfig(FuncionarioDTO.PROPERTY_FUNCIONARIO_EMAIL,
				getI18nConstants().email(), 150));

		return list;
	}

	private void createFilter() {
		BorderLayoutData bld = new BorderLayoutData(LayoutRegion.EAST);
		bld.setMargins(new Margins(5, 5, 5, 0));
		bld.setCollapsible(true);

		this.filter = new SimpleFilter();

		this.nomeBuscaTextField = new TextField<String>();
		this.nomeBuscaTextField.setFieldLabel(getI18nConstants().nome());

		this.cpfBuscaTextField = new NumberField();
		this.cpfBuscaTextField.setFieldLabel(getI18nConstants().cpf());

		this.telefoneBuscaTextField = new NumberField();
		this.telefoneBuscaTextField
				.setFieldLabel(getI18nConstants().telefone());

		this.enderecoBuscaTextField = new TextField<String>();
		this.enderecoBuscaTextField
				.setFieldLabel(getI18nConstants().endereco());

		this.emailBuscaTextField = new TextField<String>();
		this.emailBuscaTextField.setFieldLabel(getI18nConstants().email());

		this.filter.addField(this.nomeBuscaTextField);
		this.filter.addField(this.cpfBuscaTextField);
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
	public SimpleGrid<FuncionarioDTO> getGrid() {
		return this.gridPanel;
	}

	@Override
	public void setProxy(RpcProxy<PagingLoadResult<FuncionarioDTO>> proxy) {
		this.gridPanel.setProxy(proxy);
	}

	@Override
	public TextField<String> getNomeBuscaTextField() {
		return this.nomeBuscaTextField;
	}

	@Override
	public NumberField getCpfBuscaTextField() {
		return this.cpfBuscaTextField;
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
