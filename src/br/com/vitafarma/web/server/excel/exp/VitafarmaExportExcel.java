package br.com.vitafarma.web.server.excel.exp;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import br.com.vitafarma.web.shared.excel.ExcelInformationType;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nConstants;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nMessages;

public class VitafarmaExportExcel extends AbstractExportExcel {
	public VitafarmaExportExcel(ExportExcelParameters parametros, VitafarmaI18nConstants i18nConstants,
			VitafarmaI18nMessages i18nMessages) {
		super(false, ExcelInformationType.TUDO.getSheetName(), parametros, i18nConstants, i18nMessages);
	}

	@Override
	public String getFileName() {
		return this.getI18nConstants().vitafarma();
	}

	@Override
	protected String getReportName() {
		return this.getI18nConstants().vitafarma();
	}

	@Override
	protected boolean fillInExcel(HSSFWorkbook workbook) {
		List<IExportExcel> exporters = new ArrayList<IExportExcel>();

		exporters.add(
				new ClientesExportExcel(false, this.getParametros(), this.getI18nConstants(), this.getI18nMessages()));
		exporters.add(new FornecedoresExportExcel(false, this.getParametros(), this.getI18nConstants(),
				this.getI18nMessages()));
		exporters.add(new FuncionariosExportExcel(false, this.getParametros(), this.getI18nConstants(),
				this.getI18nMessages()));
		exporters.add(
				new ProdutosExportExcel(false, this.getParametros(), this.getI18nConstants(), this.getI18nMessages()));
		exporters.add(
				new VendasExportExcel(false, this.getParametros(), this.getI18nConstants(), this.getI18nMessages()));
		exporters.add(new EntradaProdutosGroupExportExcel(false, this.getParametros(), this.getI18nConstants(),
				this.getI18nMessages()));
		exporters.add(new VendedoresExportExcel(false, this.getParametros(), this.getI18nConstants(),
				this.getI18nMessages()));
		exporters.add(new RelatorioBalancoPeriodoExportExcel(false, this.getParametros(), this.getI18nConstants(),
				this.getI18nMessages()));

		Logger logger = Logger.getLogger("VitafarmaExportExcel");

		List<String> exportersNames = new ArrayList<String>();

		Exception exception = null;

		try {
			for (IExportExcel exporter : exporters) {
				// Armazena a aba que está sendo inserida na planilha
				exportersNames.add(exporter.getFileName());
				exporter.export(workbook);
			}
		} catch (Exception ex) {
			logger.warning("Erro na exportacao do cenario: " + ex.getCause() + " - " + ex.getMessage());

			ex.printStackTrace();

			exception = ex;
		}

		if (exception != null) {
			this.errors.add(this.getI18nMessages().excelErroGenericoExportacao(exception.toString()));

			return false;
		} else {
			logger.info("Exportacao de cenario realizada com sucesso.");

			// Procura pelas abas da planilha que não serão exibidas
			// no relatório (exemplo: PALETA_CORES)
			List<String> removeSheetsNames = new ArrayList<String>();

			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				HSSFSheet sheet = workbook.getSheetAt(i);
				if (!exportersNames.contains(sheet.getSheetName())) {
					removeSheetsNames.add(sheet.getSheetName());
				}
			}

			// Remove as abas desnecessárias
			while (!removeSheetsNames.isEmpty()) {
				String name = removeSheetsNames.get(0);
				int index = workbook.getSheetIndex(name);
				workbook.removeSheetAt(index);
				removeSheetsNames.remove(0);
			}

			return true;
		}
	}
}
