package br.com.vitafarma.web.server.excel.exp;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public interface IExportExcel {
	public String getFileName();

	public HSSFWorkbook export() throws NoSuchAlgorithmException;

	public boolean export(HSSFWorkbook workbook) throws NoSuchAlgorithmException;

	public List<String> getErrors();

	public List<String> getWarnings();
}
