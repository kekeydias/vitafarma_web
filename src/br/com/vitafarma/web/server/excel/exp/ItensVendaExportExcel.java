package br.com.vitafarma.web.server.excel.exp;

import java.util.Collections;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import br.com.vitafarma.domain.ItemVenda;
import br.com.vitafarma.web.shared.excel.ExcelInformationType;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nConstants;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nMessages;
import br.com.vitafarma.web.shared.util.VitafarmaUtil;

public class ItensVendaExportExcel extends AbstractExportExcel {
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

	public ItensVendaExportExcel(ExportExcelParameters parametros,
			VitafarmaI18nConstants i18nConstants,
			VitafarmaI18nMessages i18nMessages) {
		this(true, parametros, i18nConstants, i18nMessages);
	}

	public ItensVendaExportExcel(boolean removeUnusedSheets,
			ExportExcelParameters parametros,
			VitafarmaI18nConstants i18nConstants,
			VitafarmaI18nMessages i18nMessages) {
		super(true, ExcelInformationType.ITENS_VENDA.getSheetName(),
				parametros, i18nConstants, i18nMessages);

		this.cellStyles = new HSSFCellStyle[ExcelCellStyleReference.values().length];
		this.removeUnusedSheets = removeUnusedSheets;
		this.initialRow = 6;
	}

	@Override
	public String getFileName() {
		return this.getI18nConstants().itensVenda();
	}

	@Override
	protected String getReportName() {
		return this.getI18nConstants().itensVenda();
	}

	@Override
	protected boolean fillInExcel(HSSFWorkbook workbook) {
		List<ItemVenda> itens = null;
		if (this.getParametros().getVenda() == null) {
			itens = Collections.emptyList();
		} else {
			itens = ItemVenda.findByVenda(this.getParametros().getVenda());
		}

		if (!itens.isEmpty()) {
			if (this.removeUnusedSheets) {
				this.removeUnusedSheets(this.getSheetName(), workbook);
			}

			HSSFSheet sheet = workbook.getSheet(this.getSheetName());
			this.fillInCellStyles(sheet);

			int nextRow = this.initialRow;
			for (ItemVenda item : itens) {
				nextRow = this.writeData(item, nextRow, sheet);
			}

			return true;
		}

		return false;
	}

	private int writeData(ItemVenda item, int row, HSSFSheet sheet) {
		// Código da venda
		this.setCell(row, 2, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()], item
						.getVenda().getId());

		// Código do item
		this.setCell(row, 3, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				item.getId());

		// Código do produto
		this.setCell(row, 4, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()], item
						.getProduto().getId());

		// Nome do produto
		this.setCell(row, 5, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()], item
						.getProduto().getNome());

		// Quantidade
		this.setCell(row, 6, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				item.getQuantidade());

		// Desconto
		this.setCell(row, 7, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				item.getDesconto());

		// Preço Final
		this.setCell(row, 8, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				VitafarmaUtil.parseVitafarmaCurrency(item.getPrecoFinal())
						.toString());

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
