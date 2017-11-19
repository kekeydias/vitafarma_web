package br.com.vitafarma.web.server.excel.exp;

import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import br.com.vitafarma.domain.EntradaProdutoGroup;
import br.com.vitafarma.web.shared.excel.ExcelInformationType;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nConstants;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nMessages;
import br.com.vitafarma.web.shared.util.VitafarmaCurrency;
import br.com.vitafarma.web.shared.util.VitafarmaUtil;

public class EntradaProdutosGroupExportExcel extends AbstractExportExcel {
	enum ExcelCellStyleReference {
		TEXT(6, 2), CURRENCY(6, 7);

		private int row;
		private int col;

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

	private HSSFCellStyle[] cellStyles;
	private boolean removeUnusedSheets;
	private int initialRow;

	public EntradaProdutosGroupExportExcel(ExportExcelParameters parametros,
			VitafarmaI18nConstants i18nConstants,
			VitafarmaI18nMessages i18nMessages) {
		this(true, parametros, i18nConstants, i18nMessages);
	}

	public EntradaProdutosGroupExportExcel(boolean removeUnusedSheets,
			ExportExcelParameters parametros,
			VitafarmaI18nConstants i18nConstants,
			VitafarmaI18nMessages i18nMessages) {
		super(true, ExcelInformationType.ENTRADA_PRODUTOS_GROUP.getSheetName(),
				parametros, i18nConstants, i18nMessages);

		this.cellStyles = new HSSFCellStyle[ExcelCellStyleReference.values().length];
		this.removeUnusedSheets = removeUnusedSheets;
		this.initialRow = 6;
	}

	@Override
	public String getFileName() {
		return this.getI18nConstants().entradaProdutoGroupExcel();
	}

	@Override
	protected String getReportName() {
		return this.getI18nConstants().entradaProdutoGroupExcel();
	}

	@Override
	protected boolean fillInExcel(HSSFWorkbook workbook) {
		// Verifica se pelo menos um dos parâmetros
		// foi informado como filtro de busca
		Long codigoFornecedor = this.getParametros().getCodigoFornecedor();
		String nomeFornecedor = this.getParametros().getNomeFornecedor();
		Date dataEntrada = this.getParametros().getDataEntrada();
		String codigoNotaFiscal = this.getParametros().getCodigoNotaFiscal();

		boolean hasParameters = VitafarmaUtil.isBlank(codigoFornecedor,
				nomeFornecedor, dataEntrada, codigoNotaFiscal);

		List<EntradaProdutoGroup> entradaProdutosGroup = null;
		if (hasParameters) {
			entradaProdutosGroup = EntradaProdutoGroup.findBy(codigoFornecedor,
					nomeFornecedor, dataEntrada, codigoNotaFiscal);
		} else {
			entradaProdutosGroup = EntradaProdutoGroup.findAll();
		}

		if (!entradaProdutosGroup.isEmpty()) {
			if (this.removeUnusedSheets) {
				removeUnusedSheets(this.getSheetName(), workbook);
			}

			HSSFSheet sheet = workbook.getSheet(this.getSheetName());
			this.fillInCellStyles(sheet);

			int nextRow = this.initialRow;
			for (EntradaProdutoGroup item : entradaProdutosGroup) {
				nextRow = this.writeData(item, nextRow, sheet);
			}

			return true;
		}

		return false;
	}

	private int writeData(EntradaProdutoGroup entradaProdutoGroup, int row,
			HSSFSheet sheet) {
		// Código da Nota Fiscal
		String notaFiscalStr = (entradaProdutoGroup.getNotaFiscal() == null ? "-----"
				: entradaProdutoGroup.getNotaFiscal().getCodigo());
		this.setCell(row, 2, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				notaFiscalStr);

		// Código do Fornecedor
		this.setCell(row, 3, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				entradaProdutoGroup.getFornecedor().getId());

		// Nome do Fornecedor
		this.setCell(row, 4, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				entradaProdutoGroup.getFornecedor().getNome());

		// Data de Entrada
		this.setCell(row, 5, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				VitafarmaUtil.buildDateString(entradaProdutoGroup
						.getDataEntrada()));

		// Valor Total
		VitafarmaCurrency valorTotal = new VitafarmaCurrency(
				entradaProdutoGroup.getValorTotalEntrada());
		this.setCell(row, 6, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				valorTotal.toString());

		row++;
		return row;
	}

	private void fillInCellStyles(HSSFSheet sheet) {
		for (ExcelCellStyleReference cellStyleReference : ExcelCellStyleReference
				.values()) {
			this.cellStyles[cellStyleReference.ordinal()] = getCell(
					cellStyleReference.getRow(), cellStyleReference.getCol(),
					sheet).getCellStyle();
		}
	}
}
