package br.com.vitafarma.web.server.excel.imp;

import br.com.vitafarma.domain.Cenario;
import br.com.vitafarma.web.shared.excel.ExcelInformationType;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nConstants;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nMessages;

public class ImportExcelFactory {
	static public IImportExcel createImporter(ImportExcelParameters parameters, Cenario cenario,
			VitafarmaI18nConstants i18nConstants, VitafarmaI18nMessages i18nMessages) {
		IImportExcel importer = null;
		ExcelInformationType informationToBeImported = ExcelInformationType.valueOf(parameters.getInfoToBeImported());

		switch (informationToBeImported) {
		case PRODUTOS: {
			importer = new ProdutosImportExcel(cenario, i18nConstants, i18nMessages);
			break;
		}
		case PRODUTOS_ABCFARMA: {
			importer = new ProdutosAbcfarmaImportExcel(cenario, i18nConstants, i18nMessages,
					parameters.getCreateNewEntities());
			break;
		}
		case VENDAS: {
			// FIXME -- importar vendas
			// importer = new ProdutosImportExcel(cenario, i18nConstants,
			// i18nMessages);
			// break;
		}
			// FIXME -- importar varias planilhas
			// case TUDO: {importer = new VitafarmaImportExcel( cenario,
			// i18nConstants, i18nMessages ); break;}
		default: {
			break;
		}
		}

		return importer;
	}
}
