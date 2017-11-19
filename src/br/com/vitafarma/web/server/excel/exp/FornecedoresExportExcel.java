package br.com.vitafarma.web.server.excel.exp;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import br.com.vitafarma.domain.Fornecedor;
import br.com.vitafarma.web.shared.excel.ExcelInformationType;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nConstants;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nMessages;
import br.com.vitafarma.web.shared.util.VitafarmaUtil;

public class FornecedoresExportExcel extends AbstractExportExcel {
	enum ExcelCellStyleReference {
		TEXT(6, 2), CURRENCY(6, 7);

		private int row;
		private int col;

		private ExcelCellStyleReference(int row, int col) {
			this.row = row;
			this.col = col;
		}

		public int getRow() {
			return row;
		}

		public int getCol() {
			return col;
		}
	}

	private HSSFCellStyle[] cellStyles;
	private boolean removeUnusedSheets;
	private int initialRow;

	public FornecedoresExportExcel(ExportExcelParameters parametros,
			VitafarmaI18nConstants i18nConstants,
			VitafarmaI18nMessages i18nMessages) {
		this(true, parametros, i18nConstants, i18nMessages);
	}

	public FornecedoresExportExcel(boolean removeUnusedSheets,
			ExportExcelParameters parametros,
			VitafarmaI18nConstants i18nConstants,
			VitafarmaI18nMessages i18nMessages) {
		super(true, ExcelInformationType.FORNECEDORES.getSheetName(),
				parametros, i18nConstants, i18nMessages);

		this.cellStyles = new HSSFCellStyle[ExcelCellStyleReference.values().length];
		this.removeUnusedSheets = removeUnusedSheets;
		this.initialRow = 6;
	}

	@Override
	public String getFileName() {
		return getI18nConstants().fornecedores();
	}

	@Override
	protected String getReportName() {
		return getI18nConstants().fornecedores();
	}

	@Override
	protected boolean fillInExcel(HSSFWorkbook workbook) {
		List<Fornecedor> fornecedores = Fornecedor.findAll();

		if (!fornecedores.isEmpty()) {
			if (this.removeUnusedSheets) {
				removeUnusedSheets(this.getSheetName(), workbook);
			}

			HSSFSheet sheet = workbook.getSheet(this.getSheetName());
			fillInCellStyles(sheet);

			int nextRow = this.initialRow;
			for (Fornecedor fornecedor : fornecedores) {
				nextRow = this.writeData(fornecedor, nextRow, sheet);
			}

			return true;
		}

		return false;
	}

	private int writeData(Fornecedor fornecedor, int row, HSSFSheet sheet) {
		// Nome
		setCell(row, 2, sheet,
				cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				fornecedor.getNome());
		// Nome Fantasia
		setCell(row, 3, sheet,
				cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				fornecedor.getNomeFantasia());

		// Cnpj
		setCell(row, 4, sheet,
				cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				VitafarmaUtil.parseCnpjToString(fornecedor.getCnpj()));

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
