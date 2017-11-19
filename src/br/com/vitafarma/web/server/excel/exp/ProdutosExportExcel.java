package br.com.vitafarma.web.server.excel.exp;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import br.com.vitafarma.domain.Produto;
import br.com.vitafarma.web.shared.excel.ExcelInformationType;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nConstants;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nMessages;
import br.com.vitafarma.web.shared.util.VitafarmaUtil;

public class ProdutosExportExcel extends AbstractExportExcel {
	enum ExcelCellStyleReference {
		TEXT(6, 2), //
		CURRENCY(6, 7); //

		private int row = 0;
		private int col = 0;

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

	private HSSFCellStyle[] cellStyles = null;
	private boolean removeUnusedSheets = false;
	private int initialRow = 0;

	public ProdutosExportExcel(ExportExcelParameters parametros, VitafarmaI18nConstants i18nConstants,
			VitafarmaI18nMessages i18nMessages) {
		this(true, parametros, i18nConstants, i18nMessages);
	}

	public ProdutosExportExcel(boolean removeUnusedSheets, ExportExcelParameters parametros,
			VitafarmaI18nConstants i18nConstants, VitafarmaI18nMessages i18nMessages) {
		super(true, ExcelInformationType.PRODUTOS.getSheetName(), parametros, i18nConstants, i18nMessages);

		this.cellStyles = new HSSFCellStyle[ExcelCellStyleReference.values().length];
		this.removeUnusedSheets = removeUnusedSheets;
		this.initialRow = 6;
	}

	@Override
	public String getFileName() {
		return this.getI18nConstants().produtos();
	}

	@Override
	protected String getReportName() {
		return this.getI18nConstants().produtos();
	}

	@Override
	protected boolean fillInExcel(HSSFWorkbook workbook) {
		List<Produto> produtos = new ArrayList<Produto>();

		if (!VitafarmaUtil.isBlank(this.getParametros().getCodigoProduto(), this.getParametros().getMedAbc(),
				this.getParametros().getNomeProduto(), this.getParametros().getPrecoProduto(),
				this.getParametros().getNomeLaboratorio())) {
			produtos.addAll(Produto.findBy(this.getParametros().getCodigoProduto(), this.getParametros().getMedAbc(),
					this.getParametros().getNomeProduto(), this.getParametros().getPrecoProduto(),
					this.getParametros().getNomeLaboratorio()));
		} else {
			produtos.addAll(Produto.findAll());
		}

		if (!produtos.isEmpty()) {
			if (this.removeUnusedSheets) {
				this.removeUnusedSheets(this.getSheetName(), workbook);
			}

			HSSFSheet sheet = workbook.getSheet(this.getSheetName());

			this.fillInCellStyles(sheet);

			int nextRow = this.initialRow;

			for (Produto produto : produtos) {
				nextRow = this.writeData(produto, nextRow, sheet);
			}

			return true;
		}

		return false;
	}

	private int writeData(Produto produto, int row, HSSFSheet sheet) {
		// Código ABCFARMA
		this.setCell(row, 2, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()], produto.getMedAbc());

		// Código do Produto
		this.setCell(row, 3, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()], produto.getId());

		// Nome do Produto
		this.setCell(row, 4, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()], produto.getNome());

		// Descrição do Produto
		this.setCell(row, 5, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()], produto.getDescricao());

		// Preço de Venda do Produto
		this.setCell(row, 6, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				VitafarmaUtil.formatCurrencyValueString(produto.getPreco()));

		// Estoque do Produto
		this.setCell(row, 7, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()], produto.getEstoque());

		// Unidade de Compra
		this.setCell(row, 8, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				produto.getUnidadeEntrada());

		// Estoque do Venda
		this.setCell(row, 9, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()], produto.getUnidadeVenda());

		row++;
		return row;
	}

	private void fillInCellStyles(HSSFSheet sheet) {
		for (ExcelCellStyleReference cellStyleReference : ExcelCellStyleReference.values()) {
			this.cellStyles[cellStyleReference.ordinal()] = this
					.getCell(cellStyleReference.getRow(), cellStyleReference.getCol(), sheet).getCellStyle();
		}
	}
}
