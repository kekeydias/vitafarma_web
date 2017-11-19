package br.com.vitafarma.web.server.excel.exp;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import br.com.vitafarma.domain.Cliente;
import br.com.vitafarma.domain.Fornecedor;
import br.com.vitafarma.domain.Produto;
import br.com.vitafarma.web.server.ProdutosServiceImpl;
import br.com.vitafarma.web.shared.dtos.RelatorioBalancoDTO;
import br.com.vitafarma.web.shared.excel.ExcelInformationType;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nConstants;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nMessages;
import br.com.vitafarma.web.shared.util.VitafarmaDate;
import br.com.vitafarma.web.shared.util.VitafarmaUtil;

public class RelatorioBalancoPeriodoExportExcel extends AbstractExportExcel {
	enum ExcelCellStyleReference {
		HEADER_LEFT_TEXT(5, 3), //
		HEADER_CENTER_VALUE(5, 4), //
		HEADER_CENTER_TEXT(7, 2), //
		TEXT(8, 2); //

		private int row = 0;
		private int col = 0;

		private ExcelCellStyleReference(int row, int col) {
			this.row = row;

			this.col = col;
		}

		public int getRow() {
			return this.row;
		}

		public int getCol() {
			return this.col;
		}
	}

	private HSSFCellStyle[] cellStyles = null;
	private boolean removeUnusedSheets = false;
	private int initialRow = 0;

	public RelatorioBalancoPeriodoExportExcel(ExportExcelParameters parametros, VitafarmaI18nConstants i18nConstants,
			VitafarmaI18nMessages i18nMessages) {
		this(true, parametros, i18nConstants, i18nMessages);
	}

	public RelatorioBalancoPeriodoExportExcel(boolean removeUnusedSheets, ExportExcelParameters parametros,
			VitafarmaI18nConstants i18nConstants, VitafarmaI18nMessages i18nMessages) {
		super(true, ExcelInformationType.RELATORIO_BALANCO_PERIODO.getSheetName(), parametros, i18nConstants,
				i18nMessages);

		this.cellStyles = new HSSFCellStyle[ExcelCellStyleReference.values().length];
		this.removeUnusedSheets = removeUnusedSheets;
		this.initialRow = 6;
	}

	@Override
	public String getFileName() {
		return this.getI18nConstants().relatorioBalancoPeriodoExcel();
	}

	@Override
	protected String getReportName() {
		return this.getI18nConstants().relatorioBalancoPeriodoExcel();
	}

	@Override
	protected boolean fillInExcel(HSSFWorkbook workbook) {
		boolean result = false;

		Date dataInicio = this.getParametros().getDataInicio();
		Date dataFim = this.getParametros().getDataFim();

		// Quando esse relatório é gerada a partir da função 'Exportar Cenário',
		// pela classe VitafarmaExportExcel, deve-se exportar todos os registros
		// existentes na base de dados, desconsiderando o período
		// --> Quando o atributo 'removeUnusedSheets' vale 'true', temos que
		// --> o relatório está sendo gerado partir da exportação de cenário
		if (this.removeUnusedSheets && (dataInicio == null || dataFim == null)) {
			return false;
		}

		Produto produto = Produto.find(this.getParametros().getCodigoProduto());
		Cliente cliente = Cliente.find(this.getParametros().getCodigoCliente());
		Fornecedor fornecedor = Fornecedor.find(this.getParametros().getCodigoFornecedor());

		ProdutosServiceImpl service = new ProdutosServiceImpl();

		List<RelatorioBalancoDTO> relatorios = service.getRelatorioBalancoPeriodo(produto, cliente, fornecedor,
				dataInicio, dataFim);

		Collections.sort(relatorios);

		HSSFSheet sheet = workbook.getSheet(this.getSheetName());
		this.fillInCellStyles(sheet);

		this.writeHeader(sheet, dataInicio, dataFim);

		int nextRow = this.initialRow;
		for (RelatorioBalancoDTO relatorio : relatorios) {
			nextRow = this.writeData(relatorio, nextRow, sheet);
		}

		if (this.removeUnusedSheets) {
			this.removeUnusedSheets(this.getSheetName(), workbook);
		}

		return result;
	}

	private void fillInCellStyles(HSSFSheet sheet) {
		for (ExcelCellStyleReference cellStyleReference : ExcelCellStyleReference.values()) {
			this.cellStyles[cellStyleReference.ordinal()] = this
					.getCell(cellStyleReference.getRow(), cellStyleReference.getCol(), sheet).getCellStyle();
		}
	}

	private void writeHeader(HSSFSheet sheet, Date dataInicio, Date dataFim) {
		VitafarmaDate ini = VitafarmaUtil.getVitafarmaDate(dataInicio);
		VitafarmaDate fim = VitafarmaUtil.getVitafarmaDate(dataFim);

		String periodoStr = ("");

		if (ini != null && fim != null) {
			periodoStr = ("Inicio: " + ini.toString() + " - Fim: " + fim.toString());
		} else {
			periodoStr = ("Todas as atividades do cenario");
		}

		// Período de referência do relatório
		this.setCell(3, 3, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()], periodoStr);
		this.setCellFontBoldCentered(3, 3, sheet);
	}

	private int writeData(RelatorioBalancoDTO relatorio, int row, HSSFSheet sheet) {
		int col = 2;

		// Operação
		this.setCell(row, col++, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				relatorio.getOperacao());

		// Data
		this.setCell(row, col++, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				relatorio.getDataValue().toDateTimeString());

		// Produto
		this.setCell(row, col++, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				relatorio.getProdutoString());

		// Cliente
		this.setCell(row, col++, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				relatorio.getClienteString());

		// Fornecedor
		this.setCell(row, col++, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				relatorio.getFornecedorString());

		// Valor Total
		this.setCell(row, col++, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				relatorio.getValorFinalString());

		return (row + 1);
	}
}
