package br.com.vitafarma.web.server.excel.exp;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.util.CellRangeAddress;

import br.com.vitafarma.domain.Cenario;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nConstants;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nMessages;

public abstract class AbstractExportExcel implements IExportExcel {
	protected List<String> errors = null;
	protected List<String> warnings = null;
	private boolean autoSizeColumns = false;
	private ExportExcelParameters parametros = null;
	private VitafarmaI18nConstants i18nConstants = null;
	private VitafarmaI18nMessages i18nMessages = null;
	private String sheetName = null;
	private CreationHelper factory = null;
	private Drawing drawing = null;

	protected AbstractExportExcel(boolean autoSizeColumns, String sheetName, ExportExcelParameters parametros,
			VitafarmaI18nConstants i18nConstants, VitafarmaI18nMessages i18nMessages) {
		this.autoSizeColumns = autoSizeColumns;
		this.parametros = parametros;
		this.i18nConstants = i18nConstants;
		this.i18nMessages = i18nMessages;
		this.sheetName = sheetName;

		this.errors = new ArrayList<String>();
		this.warnings = new ArrayList<String>();
	}

	public ExportExcelParameters getParametros() {
		return this.parametros;
	}

	public void setParametros(ExportExcelParameters parametros) {
		this.parametros = parametros;
	}

	protected final String getPathExcelTemplate() {
		return ("/templateExport.xls");
	}

	protected abstract String getReportName();

	protected abstract boolean fillInExcel(HSSFWorkbook workbook) throws NoSuchAlgorithmException;

	@Override
	public HSSFWorkbook export() throws NoSuchAlgorithmException {
		this.errors.clear();
		this.warnings.clear();

		HSSFWorkbook workbook = null;

		try {
			workbook = this.getExcelTemplate(this.getPathExcelTemplate());
		} catch (Exception e) {
			e.printStackTrace();

			String msg = (e.getMessage() + (e.getCause() != null ? e.getCause().getMessage() : ""));

			this.errors.add(this.getI18nMessages().excelErroObterExcelTemplate(this.getPathExcelTemplate(),
					this.getReportName(), msg));
		}

		this.export(workbook);

		return workbook;
	}

	@Override
	public boolean export(HSSFWorkbook workbook) throws NoSuchAlgorithmException {
		if (workbook != null) {
			this.factory = workbook.getCreationHelper();
			this.drawing = null;
			this.errors.clear();
			this.warnings.clear();

			if (this.fillInExcel(workbook)) {
				if (this.autoSizeColumns) {
					this.autoSizeColumns(workbook);
				}

				return true;
			}
		}

		return false;
	}

	private void autoSizeColumns(HSSFWorkbook workbook) {
		if (workbook != null) {
			HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
		}

		// -- Nao funciona com a atualizacao do POI
		// -- utilizamos agora o metodo:
		// -- HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);

		// HSSFSheet sheet = workbook.getSheet(this.getSheetName());
		// if (sheet != null) {
		// this.autoSizeColumns((short) 0, (short) 100, sheet);
		// }
	}

	@Override
	public List<String> getErrors() {
		return this.errors;
	}

	@Override
	public List<String> getWarnings() {
		return this.warnings;
	}

	protected String getSheetName() {
		return this.sheetName;
	}

	protected Cenario getCenario() {
		return (this.parametros == null ? null : this.parametros.getCenario());
	}

	protected VitafarmaI18nConstants getI18nConstants() {
		return this.i18nConstants;
	}

	protected VitafarmaI18nMessages getI18nMessages() {
		return this.i18nMessages;
	}

	protected void autoSizeColumns(short firstColumn, short lastColumn, HSSFSheet sheet) {
		// -- Nao funciona com a atualizacao do POI
		// -- utilizamos agora o metodo:
		// -- HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);

		// for (short col = firstColumn; col <= lastColumn; col++) {
		// sheet.autoSizeColumn(col);
		// }
	}

	protected void removeUnusedSheets(String usedSheetName, HSSFWorkbook workbook) {
		while (workbook.getNumberOfSheets() > 1) {
			int sheetIx = 0;

			while (workbook.getSheetName(sheetIx).equals(usedSheetName)) {
				sheetIx++;
			}

			workbook.removeSheetAt(sheetIx);
		}
	}

	protected void removeUnusedSheets(List<String> usedSheetsName, HSSFWorkbook workbook) {
		Set<HSSFSheet> usedSheets = new HashSet<HSSFSheet>();

		for (String usedSheetName : usedSheetsName) {
			HSSFSheet sheet = workbook.getSheet(usedSheetName);

			if (sheet != null) {
				usedSheets.add(sheet);
			}
		}

		int sheetIx = 0;

		while (workbook.getNumberOfSheets() > usedSheets.size()) {
			HSSFSheet sheet = workbook.getSheetAt(sheetIx++);

			if (!usedSheets.contains(sheet)) {
				workbook.removeSheetAt(sheetIx);

				sheetIx = 0;
			}
		}
	}

	protected void mergeCells(int rowI, int rowF, int colI, int colF, HSSFSheet sheet) {
		sheet.addMergedRegion(new CellRangeAddress(rowI - 1, rowF - 1, colI - 1, colF - 1));
	}

	protected void mergeCells(int rowI, int rowF, int colI, int colF, HSSFSheet sheet, HSSFCellStyle style) {
		for (int row = rowI; row <= rowF; row++) {
			for (int col = colI; col <= colF; col++) {
				this.getCell(row, col, sheet).setCellStyle(style);
			}
		}

		sheet.addMergedRegion(new CellRangeAddress(rowI - 1, rowF - 1, colI - 1, colF - 1));
	}

	protected void setCell(int row, int col, HSSFSheet sheet, Calendar date) {
		final HSSFCell cell = this.getCell(row, col, sheet);

		cell.setCellValue(date);
	}

	protected void setCell(int row, int col, HSSFSheet sheet, HSSFCellStyle style, int mes, int ano) {
		final HSSFCell cell = this.getCell(row, col, sheet);

		Calendar date = Calendar.getInstance();

		// Utilizado para limpar todas as
		// informações e nÃ£o escrever no excel hh:mm:ss
		date.clear();

		date.set(ano, mes - 1, 1);
		cell.setCellValue(date);
		cell.setCellStyle(style);
	}

	protected void setCell(int row, int col, HSSFSheet sheet, HSSFCellStyle style, Calendar date) {
		final HSSFCell cell = this.getCell(row, col, sheet);

		cell.setCellValue(date);
		cell.setCellStyle(style);
	}

	protected void setCell(int row, int col, HSSFSheet sheet, HSSFCellStyle style, Date date) {
		final HSSFCell cell = this.getCell(row, col, sheet);

		cell.setCellValue(date);
		cell.setCellStyle(style);
	}

	protected void setCell(int row, int col, HSSFSheet sheet, String value) {
		HSSFCell cell = this.getCell(row, col, sheet);

		cell.setCellValue(new HSSFRichTextString(value));
	}

	protected void setCell(int row, int col, HSSFSheet sheet, HSSFCellStyle style, String value) {
		HSSFCell cell = this.getCell(row, col, sheet);

		cell.setCellValue(new HSSFRichTextString(value));
		cell.setCellStyle(style);
	}

	protected void setCell(int row, int col, HSSFSheet sheet, HSSFCellStyle style, String value, String comment) {
		HSSFCell cell = this.getCell(row, col, sheet);

		cell.setCellValue(new HSSFRichTextString(value));

		Comment cellComment = cell.getCellComment();

		if (cellComment == null) {
			ClientAnchor anchor = this.factory.createClientAnchor();

			anchor.setCol1(cell.getColumnIndex());
			anchor.setCol2(cell.getColumnIndex() + 4);

			anchor.setRow1(row);
			anchor.setRow2(row + 12);

			if (this.drawing == null) {
				this.drawing = sheet.createDrawingPatriarch();
			}

			cellComment = this.drawing.createCellComment(anchor);
			cell.setCellComment(cellComment);
		}

		cellComment.setString(this.factory.createRichTextString(comment));

		cell.setCellStyle(style);
	}

	protected void setCell(int row, int col, HSSFSheet sheet, HSSFCellStyle style,
			Iterator<HSSFComment> itExcelCommentsPool, String value, String comment) {
		HSSFCell cell = this.getCell(row, col, sheet);

		cell.setCellValue(new HSSFRichTextString(value));

		HSSFComment cellComment = cell.getCellComment();

		if (cellComment == null) {
			if (itExcelCommentsPool.hasNext()) {
				cellComment = itExcelCommentsPool.next();
				cell.setCellComment(cellComment);
				cellComment.setString(new HSSFRichTextString(comment));
			}
		} else {
			cellComment.setString(new HSSFRichTextString(comment));
		}

		cell.setCellStyle(style);
	}

	protected void setCell(int row, int col, HSSFSheet sheet, double value) {
		HSSFCell cell = this.getCell(row, col, sheet);

		cell.setCellValue(value);
	}

	protected void setCell(int row, int col, HSSFSheet sheet, HSSFCellStyle style, double value) {
		HSSFCell cell = this.getCell(row, col, sheet);

		cell.setCellValue(value);
		cell.setCellStyle(style);
	}

	protected HSSFCell getCell(int row, int col, HSSFSheet sheet) {
		final HSSFRow hssfRow = this.getRow(row - 1, sheet);
		final HSSFCell cell = this.getCell(hssfRow, (col - 1));

		return cell;
	}

	private HSSFRow getRow(int index, HSSFSheet sheet) {
		HSSFRow row = sheet.getRow(index);

		if (row == null) {
			row = sheet.createRow(index);
		}

		return row;
	}

	private HSSFCell getCell(HSSFRow row, int col) {
		HSSFCell cell = row.getCell(col);

		if (cell == null) {
			cell = row.createCell(col);
		}

		return cell;
	}

	private HSSFWorkbook getExcelTemplate(String pathExcelTemplate) throws IOException {
		final InputStream inTemplate = ExportExcelServlet.class.getResourceAsStream(pathExcelTemplate);

		HSSFWorkbook workBook = null;

		try {
			workBook = new HSSFWorkbook(inTemplate);
		} catch (IOException e) {
			throw e;
		} finally {
			if (inTemplate != null) {
				inTemplate.close();
			}
		}

		return workBook;
	}

	protected void setCellFontBoldCentered(int row, int col, HSSFSheet sheet) {
		HSSFCell cell = this.getCell(row, col, sheet);
		HSSFFont font = sheet.getWorkbook().createFont();
		HSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();

		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cell.setCellStyle(cellStyle);
	}

	protected void setCellFontBold(int row, int col, HSSFSheet sheet) {
		HSSFCell cell = this.getCell(row, col, sheet);
		HSSFFont font = sheet.getWorkbook().createFont();
		HSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();

		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		cell.setCellStyle(cellStyle);
	}

	protected void setCellAlignment(int row, int col, HSSFSheet sheet, short align) {
		HSSFCell cell = this.getCell(row, col, sheet);
		HSSFCellStyle cellStyle = sheet.getWorkbook().createCellStyle();

		cellStyle.setAlignment(align);
		cell.setCellStyle(cellStyle);
	}
}
