package br.com.vitafarma.web.server.excel.exp;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import br.com.vitafarma.domain.ItemVenda;
import br.com.vitafarma.domain.Venda;
import br.com.vitafarma.web.shared.excel.ExcelInformationType;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nConstants;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nMessages;
import br.com.vitafarma.web.shared.util.VitafarmaDate;
import br.com.vitafarma.web.shared.util.VitafarmaUtil;

public class VendasExportExcel extends AbstractExportExcel {
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

	public VendasExportExcel(ExportExcelParameters parametros,
			VitafarmaI18nConstants i18nConstants,
			VitafarmaI18nMessages i18nMessages) {
		this(true, parametros, i18nConstants, i18nMessages);
	}

	public VendasExportExcel(boolean removeUnusedSheets,
			ExportExcelParameters parametros,
			VitafarmaI18nConstants i18nConstants,
			VitafarmaI18nMessages i18nMessages) {
		super(true, ExcelInformationType.VENDAS.getSheetName(), parametros,
				i18nConstants, i18nMessages);

		this.cellStyles = new HSSFCellStyle[ExcelCellStyleReference.values().length];
		this.removeUnusedSheets = removeUnusedSheets;
		this.initialRow = 6;
	}

	@Override
	public String getFileName() {
		return getI18nConstants().vendas();
	}

	@Override
	protected String getReportName() {
		return getI18nConstants().vendas();
	}

	@Override
	protected boolean fillInExcel(HSSFWorkbook workbook) {
		List<Venda> vendas = Venda.findAll();

		if (!vendas.isEmpty()) {
			if (this.removeUnusedSheets) {
				removeUnusedSheets(this.getSheetName(), workbook);
			}

			HSSFSheet sheet = workbook.getSheet(this.getSheetName());
			fillInCellStyles(sheet);

			int nextRow = this.initialRow;
			for (Venda venda : vendas) {
				nextRow = writeData(venda, nextRow, sheet);
			}

			return true;
		}

		return false;
	}

	private int writeData(Venda venda, int row, HSSFSheet sheet) {
		// Código da venda
		this.setCell(row, 2, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				venda.getId());

		// Nome do cliente
		this.setCell(row, 3, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()], venda
						.getCliente().getNome());

		// Cpf do cliente
		this.setCell(row, 4, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()], venda
						.getCliente().getCpf());

		// Data da venda
		VitafarmaDate dataVenda = new VitafarmaDate(venda.getDataVenda()
				.getTime());
		this.setCell(row, 5, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				dataVenda.toString());

		List<ItemVenda> itensVenda = ItemVenda.findByVenda(venda);

		Double totalVenda = 0.0;
		Double quantidadeItens = 0.0;
		for (ItemVenda itemVenda : itensVenda) {
			totalVenda += itemVenda.getPrecoFinal();
			quantidadeItens += itemVenda.getQuantidade();
		}

		// Total de itens da Venda
		this.setCell(row, 6, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				quantidadeItens);

		// Valor total dos produtos vendidos
		this.setCell(row, 7, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				VitafarmaUtil.formatCurrencyValueString(totalVenda));

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
