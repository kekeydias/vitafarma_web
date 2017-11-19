package br.com.vitafarma.web.shared.util.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.vitafarma.web.client.ProdutosServiceAsync;
import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.shared.dtos.ClienteDTO;
import br.com.vitafarma.web.shared.dtos.FornecedorDTO;
import br.com.vitafarma.web.shared.dtos.ProdutoDTO;
import br.com.vitafarma.web.shared.dtos.RelatorioBalancoDTO;
import br.com.vitafarma.web.shared.i18n.IVitafarmaI18nGateway;
import br.com.vitafarma.web.shared.util.VitafarmaDate;
import br.com.vitafarma.web.shared.util.VitafarmaUtil;

import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.dnd.GridDropTarget;
import com.extjs.gxt.ui.client.event.DNDEvent;
import com.extjs.gxt.ui.client.event.DNDListener;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.tips.QuickTip;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RelatorioBalancoGrid extends ContentPanel {
	private Grid<BaseModelImpl> grid;
	private ListStore<BaseModelImpl> store;
	private List<RelatorioBalancoDTO> listRrelatoriosDTO;
	private ProdutoDTO produtoDTO;
	private FornecedorDTO fornecedorDTO;
	private ClienteDTO clienteDTO;
	private Date dataInicio;
	private Date dataFim;
	private QuickTip quickTip;
	private IVitafarmaI18nGateway display;
	private String emptyTextBeforeSearch = ("Preencha o filtro acima");
	private String emptyTextAfterSearch = ("Nenhum dado foi encontrado para este filtro.");

	public RelatorioBalancoGrid(IVitafarmaI18nGateway display) {
		super(new FitLayout());

		this.display = display;

		this.setHeaderVisible(false);
	}

	@Override
	protected void beforeRender() {
		super.beforeRender();
		this.listRrelatoriosDTO = new ArrayList<RelatorioBalancoDTO>();

		this.grid = new Grid<BaseModelImpl>(this.getListStore(), new ColumnModel(this.getColumnList()));

		this.grid.setTrackMouseOver(false);
		this.grid.setStyleName("BalancoDoPeriodo");
		this.grid.addListener(Events.BeforeSelect, new Listener<GridEvent<BaseModelImpl>>() {
			@Override
			public void handleEvent(GridEvent<BaseModelImpl> be) {
				be.setCancelled(true);
			}
		});

		this.grid.getView().setEmptyText(this.emptyTextBeforeSearch);
		this.quickTip = new QuickTip(this.grid);
		this.quickTip.getToolTipConfig().setDismissDelay(0);
		this.add(this.grid);

		GridDropTarget target = new GridDropTarget(this.grid) {
			@Override
			protected void onDragDrop(DNDEvent event) {
				return;
			}
		};

		target.addDNDListener(new DNDListener() {
			@Override
			public void dragMove(DNDEvent e) {
				int linha = grid.getView().findRowIndex(e.getDragEvent().getTarget());

				int coluna = grid.getView().findCellIndex(e.getDragEvent().getTarget(), null);

				if (linha < 0 || coluna < 1) {
					e.setCancelled(true);

					e.getStatus().setStatus(false);

					return;
				}

				e.setCancelled(false);
				e.getStatus().setStatus(true);

				return;
			}
		});

		this.requestRelatorios();
	}

	public void requestRelatorios() {
		if (this.getDataInicio() == null || this.getDataFim() == null) {
			return;
		}

		this.grid.mask("Carregando os dados, aguarde alguns instantes", "loading");

		ProdutosServiceAsync produtosService = Services.produtos();

		produtosService.getRelatorioBalancoPeriodo(this.getProdutoDTO(), this.getClienteDTO(), this.getFornecedorDTO(),
				this.getDataInicio(), this.getDataFim(), new AsyncCallback<List<RelatorioBalancoDTO>>() {
					@Override
					public void onFailure(Throwable caught) {
						MessageBox.alert("ERRO!", "Nao foi possivel carregar os dados" + " do periodo informado.",
								null);
					}

					@Override
					public void onSuccess(List<RelatorioBalancoDTO> result) {
						listRrelatoriosDTO = result;
						grid.reconfigure(getListStore(), new ColumnModel(getColumnList()));

						grid.getView().setEmptyText(emptyTextAfterSearch);
						grid.unmask();
					}
				});
	}

	public ListStore<BaseModelImpl> getListStore() {
		if (this.store == null) {
			this.store = new ListStore<BaseModelImpl>();
		} else {
			this.store.removeAll();
		}

		Set<BaseModelImpl> setRelatorios = new HashSet<BaseModelImpl>();

		for (RelatorioBalancoDTO itemDTO : this.listRrelatoriosDTO) {
			String operacaoStr = ("");

			if (Operacao.ENTRADA.toString().equals(itemDTO.getOperacao().toString())) {
				operacaoStr = ("Entrada");
			} else {
				operacaoStr = ("Venda");
			}

			setRelatorios.add(new BaseModelImpl(itemDTO.getDisplayText(), operacaoStr, itemDTO.getDataValue(),
					itemDTO.getClienteString(), itemDTO.getFornecedorString(), itemDTO.getValorFinalString()));
		}

		List<BaseModelImpl> listRelatorios = new ArrayList<BaseModelImpl>();

		listRelatorios.addAll(setRelatorios);

		Collections.sort(listRelatorios);

		for (BaseModelImpl lc : listRelatorios) {
			this.store.add(lc);
		}

		return this.store;
	}

	public List<ColumnConfig> getColumnList() {
		List<ColumnConfig> list = new ArrayList<ColumnConfig>();

		list.add(new ColumnConfig(RelatorioBalancoDTO.PROPERTY_RELATORIO_BALANCO_OPERACAO,
				this.display.getI18nConstants().operacao(), 100));
		list.add(new ColumnConfig(RelatorioBalancoDTO.PROPERTY_RELATORIO_BALANCO_DATA_STRING,
				this.display.getI18nConstants().data(), 150));
		list.add(new ColumnConfig(RelatorioBalancoDTO.PROPERTY_RELATORIO_BALANCO_CLIENTE_STRING,
				this.display.getI18nConstants().nomeCliente(), 250));
		list.add(new ColumnConfig(RelatorioBalancoDTO.PROPERTY_RELATORIO_BALANCO_FORNECEDOR_STRING,
				this.display.getI18nConstants().nomeFornecedor(), 250));
		list.add(new ColumnConfig(RelatorioBalancoDTO.PROPERTY_RELATORIO_BALANCO_VALOR_FINAL_STRING,
				this.display.getI18nConstants().valorFinal(), 100));

		return list;
	}

	public Grid<BaseModelImpl> getGrid() {
		return this.grid;
	}

	public void setGrid(Grid<BaseModelImpl> grid) {
		this.grid = grid;
	}

	public ListStore<BaseModelImpl> getStore() {
		return this.store;
	}

	public void setStore(ListStore<BaseModelImpl> store) {
		this.store = store;
	}

	public ProdutoDTO getProdutoDTO() {
		return this.produtoDTO;
	}

	public void setProdutoDTO(ProdutoDTO produtoDTO) {
		this.produtoDTO = produtoDTO;
	}

	public FornecedorDTO getFornecedorDTO() {
		return this.fornecedorDTO;
	}

	public void setFornecedorDTO(FornecedorDTO fornecedorDTO) {
		this.fornecedorDTO = fornecedorDTO;
	}

	public ClienteDTO getClienteDTO() {
		return this.clienteDTO;
	}

	public void setClienteDTO(ClienteDTO clienteDTO) {
		this.clienteDTO = clienteDTO;
	}

	public enum Operacao {
		ENTRADA, VENDA
	};

	public Date getDataInicio() {
		return this.dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return this.dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public class BaseModelImpl extends BaseModel implements Comparable<BaseModelImpl> {
		private static final long serialVersionUID = 3996652461744817138L;

		public BaseModelImpl(String display) {
			this(display, null, null, null, null, null);
		}

		public BaseModelImpl(String display, String operacaoStr, VitafarmaDate data, String clienteStr,
				String fornecedorStr, String valorFinalStr) {
			this.setDisplay(display);
			this.setDataValue(data);
			this.setDataString(data.toDateTimeString());
			this.setOperacaoStr(operacaoStr);
			this.setClienteStr(clienteStr);
			this.setFornecedorStr(fornecedorStr);
			this.setValorFinalString(valorFinalStr);
		}

		public void setDataValue(VitafarmaDate value) {
			Date date = (value == null ? null : VitafarmaUtil.getDate(value));

			this.set(RelatorioBalancoDTO.PROPERTY_RELATORIO_BALANCO_DATA_VALUE, date);
		}

		public VitafarmaDate getDataValue() {
			Date date = this.get(RelatorioBalancoDTO.PROPERTY_RELATORIO_BALANCO_DATA_VALUE);

			return VitafarmaUtil.getVitafarmaDate(date);
		}

		public void setOperacaoStr(String operacaoStr) {
			this.set(RelatorioBalancoDTO.PROPERTY_RELATORIO_BALANCO_OPERACAO, operacaoStr);
		}

		public String getOperacaoStr() {
			return this.get(RelatorioBalancoDTO.PROPERTY_RELATORIO_BALANCO_OPERACAO);
		}

		public String getClienteStr() {
			return this.get(RelatorioBalancoDTO.PROPERTY_RELATORIO_BALANCO_CLIENTE_STRING);
		}

		public void setClienteStr(String clienteStr) {
			this.set(RelatorioBalancoDTO.PROPERTY_RELATORIO_BALANCO_CLIENTE_STRING, clienteStr);
		}

		public String getDataString() {
			return this.get(RelatorioBalancoDTO.PROPERTY_RELATORIO_BALANCO_DATA_STRING);
		}

		public void setDataString(String value) {
			this.set(RelatorioBalancoDTO.PROPERTY_RELATORIO_BALANCO_DATA_STRING, value);
		}

		public String getFornecedorStr() {
			return this.get(RelatorioBalancoDTO.PROPERTY_RELATORIO_BALANCO_FORNECEDOR_STRING);
		}

		public void setFornecedorStr(String fornecedorStr) {
			this.set(RelatorioBalancoDTO.PROPERTY_RELATORIO_BALANCO_FORNECEDOR_STRING, fornecedorStr);
		}

		public String getValorFinalString() {
			return this.get(RelatorioBalancoDTO.PROPERTY_RELATORIO_BALANCO_VALOR_FINAL_STRING);
		}

		public void setValorFinalString(String valorFinal) {
			this.set(RelatorioBalancoDTO.PROPERTY_RELATORIO_BALANCO_VALOR_FINAL_STRING, valorFinal);
		}

		public String getDisplay() {
			return this.get("display");
		}

		public void setDisplay(String value) {
			this.set("display", value);
		}

		@Override
		public int compareTo(BaseModelImpl o) {
			if (o == null) {
				return 1;
			}

			int result = this.getOperacaoStr().compareTo(o.getOperacaoStr());

			if (result == 0) {
				result = this.getDataValue().compareTo(o.getDataValue());
			}

			return result;
		}
	}
}
