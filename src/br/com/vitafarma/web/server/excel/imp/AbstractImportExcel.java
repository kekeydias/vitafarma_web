package br.com.vitafarma.web.server.excel.imp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import br.com.vitafarma.domain.Cenario;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nConstants;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nMessages;

public abstract class AbstractImportExcel<ExcelBeanType> implements
		IImportExcel {
	protected List<String> errors;
	protected List<String> warnings;

	protected Cenario cenario;
	private VitafarmaI18nConstants i18nConstants;
	private VitafarmaI18nMessages i18nMessages;

	protected AbstractImportExcel(Cenario cenario,
			VitafarmaI18nConstants i18nConstants,
			VitafarmaI18nMessages i18nMessages) {
		this.cenario = cenario;
		this.i18nConstants = i18nConstants;
		this.i18nMessages = i18nMessages;

		this.errors = new ArrayList<String>();
		this.warnings = new ArrayList<String>();
	}

	protected abstract boolean sheetMustBeProcessed(int sheetIndex,
			HSSFSheet sheet, HSSFWorkbook workbook);

	protected abstract List<String> getHeaderColumnsNames(int sheetIndex,
			HSSFSheet sheet, HSSFWorkbook workbook);

	protected abstract ExcelBeanType createExcelBean(HSSFRow header,
			HSSFRow row, int sheetIndex, HSSFSheet sheet, HSSFWorkbook workbook);

	protected abstract String getHeaderToString();

	protected abstract void processSheetContent(String sheetName,
			List<ExcelBeanType> sheetContent);

	@Override
	public boolean load(String fileName, HSSFWorkbook workbook) {
		this.errors.clear();
		this.warnings.clear();

		Map<String, List<ExcelBeanType>> excelBeansMap = readInputStream(
				fileName, null, workbook);

		if (this.errors.isEmpty()) {
			try {
				for (Entry<String, List<ExcelBeanType>> entry : excelBeansMap
						.entrySet()) {
					processSheetContent(entry.getKey(), entry.getValue());
				}
			} catch (Exception e) {
				e.printStackTrace();

				this.errors.add(this.getI18nMessages().excelErroBD(fileName,
						this.extractMessage(e)));
			}
		}

		return this.errors.isEmpty();
	}

	@Override
	public boolean load(String fileName, InputStream inputStream) {
		this.errors.clear();
		this.warnings.clear();

		Map<String, List<ExcelBeanType>> excelBeansMap = this.readInputStream(
				fileName, inputStream, null);

		if (this.errors.isEmpty()) {
			try {
				for (Entry<String, List<ExcelBeanType>> entry : excelBeansMap
						.entrySet()) {
					this.processSheetContent(entry.getKey(), entry.getValue());
				}
			} catch (Exception e) {
				e.printStackTrace();

				this.errors.add(this.getI18nMessages().excelErroBD(fileName,
						this.extractMessage(e)));
			}
		}

		return this.errors.isEmpty();
	}

	@Override
	public List<String> getErrors() {
		return this.errors;
	}

	@Override
	public List<String> getWarnings() {
		return this.warnings;
	}

	private Map<String, List<ExcelBeanType>> readInputStream(String fileName,
			InputStream inputStream, HSSFWorkbook workbook) {
		// [ SheetName, List< ExcelBeanType > ]
		Map<String, List<ExcelBeanType>> excelBeansMap = new HashMap<String, List<ExcelBeanType>>();

		try {
			if (workbook == null) {
				POIFSFileSystem poifs = new POIFSFileSystem(inputStream);
				workbook = new HSSFWorkbook(poifs);
			}

			for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
				HSSFSheet sheet = workbook.getSheetAt(sheetIndex);

				// Verifica se a aba deve ou n�o ser processada
				if (this.sheetMustBeProcessed(sheetIndex, sheet, workbook)) {

					List<ExcelBeanType> excelBeansList = new ArrayList<ExcelBeanType>();
					excelBeansMap.put(workbook.getSheetName(sheetIndex),
							excelBeansList);

					// Procura cabe�alho
					List<String> headerColumnsNames = this
							.getHeaderColumnsNames(sheetIndex, sheet, workbook);

					int rowIndex = (sheet.getFirstRowNum());
					HSSFRow header = sheet.getRow(rowIndex);

					boolean validHeader = this.isHeaderValid(header,
							sheetIndex, sheet, workbook, headerColumnsNames);

					while ((rowIndex < sheet.getLastRowNum()) && !validHeader) {
						header = sheet.getRow(rowIndex++);

						validHeader = this.isHeaderValid(header, sheetIndex,
								sheet, workbook, headerColumnsNames);
					}

					if (validHeader) {
						List<Integer> nullRows = new ArrayList<Integer>();

						// Efetua a leitura dos dados do arquivo
						for (rowIndex++; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
							HSSFRow row = sheet.getRow(rowIndex);

							if (row != null) {
								excelBeansList.add(this.createExcelBean(header,
										row, sheetIndex, sheet, workbook));
							} else {
								nullRows.add(rowIndex);
							}
						}

						// Verifica se existem linhas nulas
						if (!nullRows.isEmpty()) {
							this.errors.add(this.getI18nMessages()
									.excelErroSintaticoLinhasInvalidas(
											nullRows.toString(), fileName));
						}
					} else {
						this.errors.add(this.getI18nMessages()
								.excelErroSintaticoCabecalhoAusente(
										this.getHeaderToString(), fileName));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

			this.errors.add(this.getI18nMessages().excelErroArquivoInvalido(
					fileName, this.extractMessage(e)));
		}

		return excelBeansMap;
	}

	private boolean isHeaderValid(HSSFRow candidateHeader, int sheetIndex,
			HSSFSheet sheet, HSSFWorkbook workbook,
			List<String> headerColumnsNames) {

		if (candidateHeader != null) {
			boolean[] columnStatus = new boolean[headerColumnsNames.size()];

			// Para cada coluna da linha a ser verificada
			for (int cellIndex = candidateHeader.getFirstCellNum(); cellIndex <= candidateHeader
					.getLastCellNum(); cellIndex++) {
				HSSFCell cell = candidateHeader.getCell(cellIndex);

				if (cell != null
						&& cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
					String columnName = cell.getRichStringCellValue()
							.getString();

					// Para cada coluna no header
					for (int headerColumnIndex = 0; headerColumnIndex < headerColumnsNames
							.size(); headerColumnIndex++) {
						String str1 = (headerColumnsNames
								.get(headerColumnIndex) == null ? ""
								: headerColumnsNames.get(headerColumnIndex)
										.trim());

						String str2 = (columnName == null ? "" : columnName
								.trim());

						if (str1.equals(str2)) {
							columnStatus[headerColumnIndex] = true;
						}
					}
				}
			}

			// Verifica se todas as colunas
			// necess�rias foram encontradas no header
			boolean test = true;

			for (int i = 0; i < columnStatus.length; i++) {
				test = (test && columnStatus[i]);
			}

			return test;
		}

		return false;
	}

	private String extractMessage(Exception e) {
		StringBuffer msg = new StringBuffer();

		msg.append(e.getMessage());

		if (e.getCause() != null) {
			msg.append(" " + e.getCause().getMessage());
		}

		return msg.toString();
	}

	protected String getCellValue(HSSFCell cell) {
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING: {
			return this.getCellStringValue(cell);
		}
		case HSSFCell.CELL_TYPE_NUMERIC: {
			return this.getCellNumericValue(cell);
		}
		}

		return null;
	}

	protected String getCellStringValue(HSSFCell cell) {
		return cell.getRichStringCellValue().getString().trim();
	}

	protected String getCellNumericValue(HSSFCell cell) {
		return Double.toString(cell.getNumericCellValue());
	}

	protected Cenario getCenario() {
		return this.cenario;
	}

	protected VitafarmaI18nConstants getI18nConstants() {
		return this.i18nConstants;
	}

	protected VitafarmaI18nMessages getI18nMessages() {
		return this.i18nMessages;
	}
}
