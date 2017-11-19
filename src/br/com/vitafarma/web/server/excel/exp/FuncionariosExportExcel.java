package br.com.vitafarma.web.server.excel.exp;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import br.com.vitafarma.domain.Funcionario;
import br.com.vitafarma.web.shared.excel.ExcelInformationType;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nConstants;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nMessages;
import br.com.vitafarma.web.shared.util.VitafarmaDate;
import br.com.vitafarma.web.shared.util.VitafarmaUtil;

public class FuncionariosExportExcel extends AbstractExportExcel {
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

	public FuncionariosExportExcel(ExportExcelParameters parametros,
			VitafarmaI18nConstants i18nConstants,
			VitafarmaI18nMessages i18nMessages) {
		this(true, parametros, i18nConstants, i18nMessages);
	}

	public FuncionariosExportExcel(boolean removeUnusedSheets,
			ExportExcelParameters parametros,
			VitafarmaI18nConstants i18nConstants,
			VitafarmaI18nMessages i18nMessages) {
		super(true, ExcelInformationType.FUNCIONARIOS.getSheetName(),
				parametros, i18nConstants, i18nMessages);

		this.cellStyles = new HSSFCellStyle[ExcelCellStyleReference.values().length];
		this.removeUnusedSheets = removeUnusedSheets;
		this.initialRow = 6;
	}

	@Override
	public String getFileName() {
		return this.getI18nConstants().funcionarios();
	}

	@Override
	protected String getReportName() {
		return this.getI18nConstants().funcionarios();
	}

	@Override
	protected boolean fillInExcel(HSSFWorkbook workbook) {
		List<Funcionario> funcionarios = Funcionario.findAll();

		if (!funcionarios.isEmpty()) {
			if (this.removeUnusedSheets) {
				this.removeUnusedSheets(this.getSheetName(), workbook);
			}

			HSSFSheet sheet = workbook.getSheet(this.getSheetName());
			fillInCellStyles(sheet);

			int nextRow = this.initialRow;
			for (Funcionario funcionario : funcionarios) {
				nextRow = this.writeData(funcionario, nextRow, sheet);
			}

			return true;
		}

		return false;
	}

	private int writeData(Funcionario funcionario, int row, HSSFSheet sheet) {
		// Nome
		this.setCell(row, 2, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				funcionario.getNome());

		// Cpf
		this.setCell(row, 3, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				VitafarmaUtil.parseCpfToString(funcionario.getCpf()));

		// Data de Adimissão
		VitafarmaDate dataAdimissao = new VitafarmaDate(funcionario
				.getDataAdimissao().getTime());
		this.setCell(row, 4, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				dataAdimissao.toString());

		// Data de Demissão
		String dataDemissaoStr = "";
		if (funcionario.getDataDemissao() != null) {
			VitafarmaDate dataDemissao = new VitafarmaDate(funcionario
					.getDataDemissao().getTime());

			dataDemissaoStr = dataDemissao.toString();
		}
		this.setCell(row, 5, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				dataDemissaoStr);

		// Salário
		this.setCell(row, 6, sheet,
				this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				VitafarmaUtil.formatCurrencyValueString(funcionario
						.getSalario()));

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
