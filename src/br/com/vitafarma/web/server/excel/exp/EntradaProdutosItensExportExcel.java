package br.com.vitafarma.web.server.excel.exp;

import java.util.Collections;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import br.com.vitafarma.domain.EntradaProduto;
import br.com.vitafarma.web.shared.excel.ExcelInformationType;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nConstants;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nMessages;
import br.com.vitafarma.web.shared.util.VitafarmaCurrency;
import br.com.vitafarma.web.shared.util.VitafarmaUtil;

public class EntradaProdutosItensExportExcel extends AbstractExportExcel {
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

	public EntradaProdutosItensExportExcel(ExportExcelParameters parametros,
			VitafarmaI18nConstants i18nConstants,
			VitafarmaI18nMessages i18nMessages) {
		this(true, parametros, i18nConstants, i18nMessages);
	}

	public EntradaProdutosItensExportExcel(boolean removeUnusedSheets,
			ExportExcelParameters parametros,
			VitafarmaI18nConstants i18nConstants,
			VitafarmaI18nMessages i18nMessages) {
		super(true, ExcelInformationType.ENTRADA_PRODUTOS_ITENS.getSheetName(),
				parametros, i18nConstants, i18nMessages);

		this.cellStyles = new HSSFCellStyle[ExcelCellStyleReference.values().length];
		this.removeUnusedSheets = removeUnusedSheets;
		this.initialRow = 6;
	}

	@Override
	public String getFileName() {
		return this.getI18nConstants().entradaProdutoItensExcel();
	}

	@Override
	protected String getReportName() {
		return this.getI18nConstants().entradaProdutoItensExcel();
	}

	@Override
	protected boolean fillInExcel(HSSFWorkbook workbook) {
		List<EntradaProduto> entradaProdutos = null;

		if (this.getParametros().getGroupEntrada() == null) {
			entradaProdutos = Collections.emptyList();
		} else {
			entradaProdutos = EntradaProduto.findByGroup(this.getParametros()
					.getGroupEntrada());
		}

		if (!entradaProdutos.isEmpty()) {
			if (this.removeUnusedSheets) {
				removeUnusedSheets(this.getSheetName(), workbook);
			}

			HSSFSheet sheet = workbook.getSheet(this.getSheetName());
			this.fillInCellStyles(sheet);

			int nextRow = this.initialRow;
			for (EntradaProduto entradaProduto : entradaProdutos) {
				nextRow = writeData(entradaProduto, nextRow, sheet);
			}

			return true;
		}

		return false;
	}

	private int writeData(EntradaProduto entradaProduto, int row,
			HSSFSheet sheet) {
		// Código do grupo de entrada
		this.setCell(row, 2, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				entradaProduto.getEntradaProdutoGroup().getId());

		// Código da Nota Fiscal
		String notaFiscalStr = (entradaProduto.getNotaFiscal() == null ? "-----"
				: entradaProduto.getNotaFiscal().getCodigo());
		this.setCell(row, 3, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				notaFiscalStr);

		// Código do Fornecedor
		this.setCell(row, 4, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				entradaProduto.getFornecedor().getId());

		// Nome do Fornecedor
		this.setCell(row, 5, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				entradaProduto.getFornecedor().getNome());

		// Código do Produto
		this.setCell(row, 6, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				entradaProduto.getProduto().getId());

		// Nome do Produto
		this.setCell(row, 7, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				entradaProduto.getProduto().getNome());

		// Quantidade
		VitafarmaCurrency quantidade = new VitafarmaCurrency(
				entradaProduto.getQuantidade());
		this.setCell(row, 8, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				quantidade.toString());

		// Preço Unitário
		VitafarmaCurrency precoUnitario = new VitafarmaCurrency(
				entradaProduto.getPrecoUnitario());
		this.setCell(row, 9, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				precoUnitario.toString());

		// Data de Entrada
		this.setCell(row, 10, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				VitafarmaUtil.buildDateString(entradaProduto.getDataEntrada()));

		// Valor Total
		VitafarmaCurrency valorTotal = new VitafarmaCurrency(
				entradaProduto.getQuantidade()
						* entradaProduto.getPrecoUnitario());
		this.setCell(row, 11, sheet,
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
