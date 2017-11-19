package br.com.vitafarma.web.client.mvp.presenter;

import java.util.Date;

import br.com.vitafarma.web.shared.dtos.ClienteDTO;
import br.com.vitafarma.web.shared.dtos.FornecedorDTO;
import br.com.vitafarma.web.shared.dtos.ProdutoDTO;
import br.com.vitafarma.web.shared.excel.ExcelInformationType;
import br.com.vitafarma.web.shared.i18n.IVitafarmaI18nGateway;
import br.com.vitafarma.web.shared.mvp.presenter.Presenter;
import br.com.vitafarma.web.shared.util.view.ClientesComboBox;
import br.com.vitafarma.web.shared.util.view.ExcelOperation;
import br.com.vitafarma.web.shared.util.view.ExcelParametros;
import br.com.vitafarma.web.shared.util.view.ExportExcelFormSubmit;
import br.com.vitafarma.web.shared.util.view.FornecedoresComboBox;
import br.com.vitafarma.web.shared.util.view.GTab;
import br.com.vitafarma.web.shared.util.view.GTabItem;
import br.com.vitafarma.web.shared.util.view.ProdutosComboBox;
import br.com.vitafarma.web.shared.util.view.RelatorioBalancoGrid;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.google.gwt.user.client.ui.Widget;

public class RelatorioBalancoPresenter implements Presenter {
	public interface Display extends IVitafarmaI18nGateway {
		Button getSubmitBuscaButton();

		ProdutosComboBox getProdutosComboBox();

		ClientesComboBox getClientesComboBox();

		FornecedoresComboBox getFornecedoresComboBox();

		DateField getDataInicioDF();

		DateField getDataFimDF();

		Component getComponent();

		RelatorioBalancoGrid getGrid();;

		Button getExportExcelButton();

		boolean isValid();
	}

	private Display display = null;

	public RelatorioBalancoPresenter(Display display) {
		this.display = display;

		this.setListeners();
	}

	private void setListeners() {
		this.display.getSubmitBuscaButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (display.isValid()) {
					display.getGrid().setProdutoDTO(display.getProdutosComboBox().getValue());
					display.getGrid().setClienteDTO(display.getClientesComboBox().getValue());
					display.getGrid().setFornecedorDTO(display.getFornecedoresComboBox().getValue());
					display.getGrid().setDataInicio(display.getDataInicioDF().getValue());
					display.getGrid().setDataFim(display.getDataFimDF().getValue());

					display.getGrid().requestRelatorios();
				} else {
					MessageBox.alert("Ajuda",
							display.getI18nMessages().erroCamposObrigatorios("Inicio do Periodo", "Fim do Periodo"),
							null);
				}
			}
		});

		this.display.getExportExcelButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				if (!display.isValid()) {
					MessageBox.alert("Ajuda",
							display.getI18nMessages().erroCamposObrigatorios("Inicio do Periodo", "Fim do Periodo"),
							null);

					return;
				}

				ExcelParametros parametros = new ExcelParametros(ExcelInformationType.RELATORIO_BALANCO_PERIODO,
						ExcelOperation.EXPORT);

				ExportExcelFormSubmit e = new ExportExcelFormSubmit(parametros, display.getI18nConstants(),
						display.getI18nMessages());

				ProdutoDTO produtoDTO = display.getProdutosComboBox().getValue();
				ClienteDTO clienteDTO = display.getClientesComboBox().getValue();
				FornecedorDTO fornecedorDTO = display.getFornecedoresComboBox().getValue();
				Date dataInicio = display.getDataInicioDF().getValue();
				Date dataFim = display.getDataFimDF().getValue();

				e.addParameter("codigoProduto", (produtoDTO == null ? null : produtoDTO.getId().toString()));
				e.addParameter("codigoCliente", (clienteDTO == null ? null : clienteDTO.getId().toString()));
				e.addParameter("codigoFornecedor", (fornecedorDTO == null ? null : fornecedorDTO.getId().toString()));
				e.addParameter("dataInicio", (dataInicio == null ? null : Long.toString(dataInicio.getTime())));
				e.addParameter("dataFim", (dataFim == null ? null : Long.toString(dataFim.getTime())));

				e.submit();
			}
		});
	}

	@Override
	public void go(Widget widget) {
		GTab tab = (GTab) widget;

		tab.add((GTabItem) this.display.getComponent());
	}
}
