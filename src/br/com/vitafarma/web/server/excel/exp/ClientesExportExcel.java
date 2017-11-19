package br.com.vitafarma.web.server.excel.exp;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import br.com.vitafarma.domain.Cliente;
import br.com.vitafarma.web.shared.excel.ExcelInformationType;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nConstants;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nMessages;
import br.com.vitafarma.web.shared.util.VitafarmaUtil;

public class ClientesExportExcel extends AbstractExportExcel {
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

	public ClientesExportExcel(ExportExcelParameters parametros,
			VitafarmaI18nConstants i18nConstants,
			VitafarmaI18nMessages i18nMessages) {
		this(true, parametros, i18nConstants, i18nMessages);
	}

	public ClientesExportExcel(boolean removeUnusedSheets,
			ExportExcelParameters parametros,
			VitafarmaI18nConstants i18nConstants,
			VitafarmaI18nMessages i18nMessages) {
		super(true, ExcelInformationType.CLIENTES.getSheetName(), parametros,
				i18nConstants, i18nMessages);

		this.cellStyles = new HSSFCellStyle[ExcelCellStyleReference.values().length];
		this.removeUnusedSheets = removeUnusedSheets;
		this.initialRow = 6;
	}

	@Override
	public String getFileName() {
		return getI18nConstants().clientes();
	}

	@Override
	protected String getReportName() {
		return getI18nConstants().clientes();
	}

	@Override
	protected boolean fillInExcel(HSSFWorkbook workbook) {
		List<Cliente> clientes = Cliente.findAll();

		if (!clientes.isEmpty()) {
			HSSFSheet sheet = workbook.getSheet(this.getSheetName());
			this.fillInCellStyles(sheet);

			int nextRow = this.initialRow;
			for (Cliente cliente : clientes) {
				nextRow = writeData(cliente, nextRow, sheet);
			}

			if (this.removeUnusedSheets) {
				this.removeUnusedSheets(this.getSheetName(), workbook);
			}

			return true;
		}

		return false;
	}

	private int writeData(Cliente cliente, int row, HSSFSheet sheet) {
		// Nome
		this.setCell(row, 2, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				cliente.getNome());
		// Cpf
		setCell(row, 3, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				VitafarmaUtil.parseCpfToString(cliente.getCpf()));

		// Valor total dos produtos vendidos ao cliente
		this.setCell(row, 4, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				VitafarmaUtil.formatCurrencyValueString(cliente
						.getValorTotalProdutos()));

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
