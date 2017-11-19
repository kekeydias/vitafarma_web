package br.com.vitafarma.web.client.mvp.presenter;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.google.gwt.user.client.ui.Widget;

import br.com.vitafarma.web.client.mvp.view.CidadesFormView;
import br.com.vitafarma.web.client.mvp.view.CidadesView;
import br.com.vitafarma.web.client.mvp.view.ClientesFormView;
import br.com.vitafarma.web.client.mvp.view.ClientesView;
import br.com.vitafarma.web.client.mvp.view.EntradaProdutosGroupFormView;
import br.com.vitafarma.web.client.mvp.view.EntradaProdutosGroupView;
import br.com.vitafarma.web.client.mvp.view.EstadosFormView;
import br.com.vitafarma.web.client.mvp.view.EstadosView;
import br.com.vitafarma.web.client.mvp.view.FornecedoresFormView;
import br.com.vitafarma.web.client.mvp.view.FornecedoresView;
import br.com.vitafarma.web.client.mvp.view.FuncionariosFormView;
import br.com.vitafarma.web.client.mvp.view.FuncionariosView;
import br.com.vitafarma.web.client.mvp.view.LaboratoriosFormView;
import br.com.vitafarma.web.client.mvp.view.LaboratoriosView;
import br.com.vitafarma.web.client.mvp.view.ProdutosFormView;
import br.com.vitafarma.web.client.mvp.view.ProdutosView;
import br.com.vitafarma.web.client.mvp.view.RelatorioBalancoView;
import br.com.vitafarma.web.client.mvp.view.VendasFormView;
import br.com.vitafarma.web.client.mvp.view.VendasView;
import br.com.vitafarma.web.client.mvp.view.VendedoresFormView;
import br.com.vitafarma.web.client.mvp.view.VendedoresView;
import br.com.vitafarma.web.shared.dtos.CenarioDTO;
import br.com.vitafarma.web.shared.dtos.CidadeDTO;
import br.com.vitafarma.web.shared.dtos.ClienteDTO;
import br.com.vitafarma.web.shared.dtos.EntradaProdutoGroupDTO;
import br.com.vitafarma.web.shared.dtos.EstadoDTO;
import br.com.vitafarma.web.shared.dtos.FornecedorDTO;
import br.com.vitafarma.web.shared.dtos.FuncionarioDTO;
import br.com.vitafarma.web.shared.dtos.LaboratorioDTO;
import br.com.vitafarma.web.shared.dtos.ProdutoDTO;
import br.com.vitafarma.web.shared.dtos.UsuarioDTO;
import br.com.vitafarma.web.shared.dtos.VendaDTO;
import br.com.vitafarma.web.shared.dtos.VendedorDTO;
import br.com.vitafarma.web.shared.excel.ExcelInformationType;
import br.com.vitafarma.web.shared.i18n.IVitafarmaI18nGateway;
import br.com.vitafarma.web.shared.mvp.presenter.Presenter;
import br.com.vitafarma.web.shared.util.view.CenarioPanel;
import br.com.vitafarma.web.shared.util.view.ExcelOperation;
import br.com.vitafarma.web.shared.util.view.ExcelParametros;
import br.com.vitafarma.web.shared.util.view.ExportExcelFormSubmit;
import br.com.vitafarma.web.shared.util.view.GTab;
import br.com.vitafarma.web.shared.util.view.ImportExcelFormView;

public class ToolBarPresenter implements Presenter {
	public interface Display extends IVitafarmaI18nGateway {
		Component getComponent();

		Button getCidadeNovaCidadeButton();

		Button getCidadesListCidadesButton();

		Button getEstadoNovoEstadosButton();

		Button getEstadosListEstadosButton();

		Button getFornecedorNovoFornecedoresButton();

		Button getFornecedoresListFornecedoresButton();

		Button getLaboratorioNovoLaboratoriosButton();

		Button getLaboratoriosListLaboratoriosButton();

		Button getVendedorNovoVendedoresButton();

		Button getVendedoresListVendedoresButton();

		Button getClienteNovoClientesButton();

		Button getClientesListClientesButton();

		Button getFuncionarioNovoFuncionariosButton();

		Button getFuncionariosListFuncionariosButton();

		Button getProdutoNovoProdutosButton();

		Button getProdutosListProdutosButton();

		Button getProdutosImportProdutosAbcfarmaButton();

		Button getVendaNovoVendasButton();

		Button getVendasListVendasButton();

		Button getEntradaProdutosNovoEntradaProdutosButton();

		Button getEntradaProdutosListEntradaProdutosButton();

		Button getRelatorioBalancoPeriodoButton();

		Button getExportExcelCenarioButton();
	}

	@SuppressWarnings("unused")
	private CenarioDTO cenarioDTO = null;

	@SuppressWarnings("unused")
	private UsuarioDTO usuarioDTO = null;

	private Display toolBar = null;
	private GTab gTab = null;

	public ToolBarPresenter(CenarioDTO masterData, UsuarioDTO usuarioDTO, CenarioPanel cenarioPanel, Display toolBar) {
		this.cenarioDTO = masterData;
		this.usuarioDTO = usuarioDTO;
		this.toolBar = toolBar;

		this.addListeners();
	}

	private void addListeners() {
		// CLIENTES
		this.toolBar.getClienteNovoClientesButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Presenter presenter = new ClientesFormPresenter(new ClientesFormView(new ClienteDTO()));

				presenter.go(null);
			}
		});

		this.toolBar.getClientesListClientesButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Presenter presenter = new ClientesPresenter(new ClientesView());

				presenter.go(gTab);
			}
		});

		// FORNECEDORES
		this.toolBar.getFornecedorNovoFornecedoresButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Presenter presenter = new FornecedoresFormPresenter(new FornecedoresFormView(new FornecedorDTO()));

				presenter.go(null);
			}
		});

		this.toolBar.getFornecedoresListFornecedoresButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Presenter presenter = new FornecedoresPresenter(new FornecedoresView());

				presenter.go(gTab);
			}
		});

		// LABORATÓRIOS
		this.toolBar.getLaboratorioNovoLaboratoriosButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Presenter presenter = new LaboratoriosFormPresenter(new LaboratoriosFormView(new LaboratorioDTO()));

				presenter.go(null);
			}
		});

		this.toolBar.getLaboratoriosListLaboratoriosButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Presenter presenter = new LaboratoriosPresenter(new LaboratoriosView());

				presenter.go(gTab);
			}
		});

		// VENDEDORES
		this.toolBar.getVendedorNovoVendedoresButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Presenter presenter = new VendedoresFormPresenter(new VendedoresFormView(new VendedorDTO()));

				presenter.go(null);
			}
		});

		this.toolBar.getVendedoresListVendedoresButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Presenter presenter = new VendedoresPresenter(new VendedoresView());

				presenter.go(gTab);
			}
		});

		// FUNCIONARIOS
		this.toolBar.getFuncionarioNovoFuncionariosButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Presenter presenter = new FuncionariosFormPresenter(new FuncionariosFormView(new FuncionarioDTO()));

				presenter.go(null);
			}
		});

		this.toolBar.getFuncionariosListFuncionariosButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Presenter presenter = new FuncionariosPresenter(new FuncionariosView());

				presenter.go(gTab);
			}
		});

		// PRODUTOS
		this.toolBar.getProdutoNovoProdutosButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Presenter presenter = new ProdutosFormPresenter(new ProdutosFormView(new ProdutoDTO()));

				presenter.go(null);
			}
		});

		this.toolBar.getProdutosListProdutosButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Presenter presenter = new ProdutosPresenter(new ProdutosView());

				presenter.go(gTab);
			}
		});

		this.toolBar.getProdutosImportProdutosAbcfarmaButton()
				.addSelectionListener(new SelectionListener<ButtonEvent>() {
					@Override
					public void componentSelected(ButtonEvent ce) {
						ExcelParametros parametros = new ExcelParametros(ExcelInformationType.PRODUTOS_ABCFARMA,
								ExcelOperation.IMPORT);

						Presenter presenter = new ProdutosPresenter(new ProdutosView());

						ImportExcelFormView importExcelFormView = new ImportExcelFormView(parametros, presenter, gTab);

						importExcelFormView.show();
					}
				});

		// VENDAS
		this.toolBar.getVendaNovoVendasButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Presenter presenter = new VendasFormPresenter(new VendasFormView(new VendaDTO()));

				presenter.go(null);
			}
		});

		this.toolBar.getVendasListVendasButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Presenter presenter = new VendasPresenter(new VendasView());

				presenter.go(gTab);
			}
		});

		// ENTRADA DE PRODUTOS
		this.toolBar.getEntradaProdutosNovoEntradaProdutosButton()
				.addSelectionListener(new SelectionListener<ButtonEvent>() {
					@Override
					public void componentSelected(ButtonEvent ce) {
						Presenter presenter = new EntradaProdutosGroupFormPresenter(
								new EntradaProdutosGroupFormView(new EntradaProdutoGroupDTO()));

						presenter.go(null);
					}
				});

		this.toolBar.getEntradaProdutosListEntradaProdutosButton()
				.addSelectionListener(new SelectionListener<ButtonEvent>() {
					@Override
					public void componentSelected(ButtonEvent ce) {
						Presenter presenter = new EntradaProdutosGroupPresenter(new EntradaProdutosGroupView());

						presenter.go(gTab);
					}
				});

		// ESTADOS
		this.toolBar.getEstadoNovoEstadosButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Presenter presenter = new EstadosFormPresenter(new EstadosFormView(new EstadoDTO()));

				presenter.go(null);
			}
		});

		this.toolBar.getEstadosListEstadosButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Presenter presenter = new EstadosPresenter(new EstadosView());

				presenter.go(gTab);
			}
		});

		// CIDADES
		this.toolBar.getCidadeNovaCidadeButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Presenter presenter = new CidadesFormPresenter(new CidadesFormView(new CidadeDTO()));

				presenter.go(null);
			}
		});

		this.toolBar.getCidadesListCidadesButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Presenter presenter = new CidadesPresenter(new CidadesView());

				presenter.go(gTab);
			}
		});

		// RELATORIOS
		this.toolBar.getRelatorioBalancoPeriodoButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				Presenter presenter = new RelatorioBalancoPresenter(new RelatorioBalancoView());

				presenter.go(gTab);
			}
		});

		this.toolBar.getExportExcelCenarioButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				ExcelParametros parametros = new ExcelParametros(ExcelInformationType.TUDO, ExcelOperation.EXPORT);

				ExportExcelFormSubmit e = new ExportExcelFormSubmit(parametros, toolBar.getI18nConstants(),
						toolBar.getI18nMessages());

				e.submit();
			}
		});
	}

	@Override
	public void go(Widget widget) {
		AppPresenter.Display container = (AppPresenter.Display) widget;

		this.gTab = container.getGTab();

		container.getPanel().setTopComponent(this.toolBar.getComponent());
	}
}
