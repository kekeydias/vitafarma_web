package br.com.vitafarma.web.server.excel.exp;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import br.com.vitafarma.domain.Laboratorio;
import br.com.vitafarma.web.shared.excel.ExcelInformationType;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nConstants;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nMessages;
import br.com.vitafarma.web.shared.util.VitafarmaUtil;

public class LaboratoriosExportExcel extends AbstractExportExcel {
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

	public LaboratoriosExportExcel(ExportExcelParameters parametros,
			VitafarmaI18nConstants i18nConstants,
			VitafarmaI18nMessages i18nMessages) {
		this(true, parametros, i18nConstants, i18nMessages);
	}

	public LaboratoriosExportExcel(boolean removeUnusedSheets,
			ExportExcelParameters parametros,
			VitafarmaI18nConstants i18nConstants,
			VitafarmaI18nMessages i18nMessages) {
		super(true, ExcelInformationType.LABORATORIOS.getSheetName(),
				parametros, i18nConstants, i18nMessages);

		this.cellStyles = new HSSFCellStyle[ExcelCellStyleReference.values().length];
		this.removeUnusedSheets = removeUnusedSheets;
		this.initialRow = 6;
	}

	@Override
	public String getFileName() {
		return this.getI18nConstants().laboratorios();
	}

	@Override
	protected String getReportName() {
		return this.getI18nConstants().laboratorios();
	}

	@Override
	protected boolean fillInExcel(HSSFWorkbook workbook) {
		List<Laboratorio> laboratorios = Laboratorio.findAll();

		if (!laboratorios.isEmpty()) {
			if (this.removeUnusedSheets) {
				this.removeUnusedSheets(this.getSheetName(), workbook);
			}

			HSSFSheet sheet = workbook.getSheet(this.getSheetName());
			fillInCellStyles(sheet);

			int nextRow = this.initialRow;
			for (Laboratorio laboratorio : laboratorios) {
				nextRow = this.writeData(laboratorio, nextRow, sheet);
			}

			return true;
		}

		return false;
	}

	private int writeData(Laboratorio laboratorio, int row, HSSFSheet sheet) {
		// Código ABCFARMA
		this.setCell(row, 2, sheet,
				cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				laboratorio.getMedLab());
		// Nome
		this.setCell(row, 3, sheet,
				cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				laboratorio.getNome());
		// Nome Fantasia
		this.setCell(row, 4, sheet,
				cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				laboratorio.getNomeFantasia());

		// Cnpj
		this.setCell(row, 5, sheet,
				cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				VitafarmaUtil.parseCnpjToString(laboratorio.getCnpj()));

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
