package br.com.vitafarma.web.server.excel.exp;

import br.com.vitafarma.web.shared.excel.ExcelInformationType;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nConstants;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nMessages;

public class ExportExcelFactory {
	static public IExportExcel createExporter(String infoToBeExported, ExportExcelParameters parametros,
			VitafarmaI18nConstants i18nConstants, VitafarmaI18nMessages i18nMessages) {
		IExportExcel exporter = null;

		ExcelInformationType informationToBeExported = ExcelInformationType.valueOf(infoToBeExported);

		switch (informationToBeExported) {
		case TUDO: {
			exporter = new VitafarmaExportExcel(parametros, i18nConstants, i18nMessages);

			break;
		}
		case CLIENTES: {
			exporter = new ClientesExportExcel(true, parametros, i18nConstants, i18nMessages);

			break;
		}
		case FORNECEDORES: {
			exporter = new FornecedoresExportExcel(true, parametros, i18nConstants, i18nMessages);

			break;
		}
		case FUNCIONARIOS: {
			exporter = new FuncionariosExportExcel(true, parametros, i18nConstants, i18nMessages);

			break;
		}
		case PRODUTOS: {
			exporter = new ProdutosExportExcel(true, parametros, i18nConstants, i18nMessages);

			break;
		}
		case VENDAS: {
			exporter = new VendasExportExcel(true, parametros, i18nConstants, i18nMessages);

			break;
		}
		case ITENS_VENDA: {
			exporter = new ItensVendaExportExcel(true, parametros, i18nConstants, i18nMessages);

			break;
		}
		case ENTRADA_PRODUTOS_GROUP: {
			exporter = new EntradaProdutosGroupExportExcel(true, parametros, i18nConstants, i18nMessages);

			break;
		}
		case ENTRADA_PRODUTOS_ITENS: {
			exporter = new EntradaProdutosItensExportExcel(true, parametros, i18nConstants, i18nMessages);

			break;
		}
		case CUPOM_FISCAL: {
			exporter = new CupomFiscalExportExcel(true, parametros, i18nConstants, i18nMessages);

			break;
		}
		case VENDEDORES: {
			exporter = new VendedoresExportExcel(true, parametros, i18nConstants, i18nMessages);

			break;
		}
		case RELATORIO_BALANCO_PERIODO: {
			exporter = new RelatorioBalancoPeriodoExportExcel(true, parametros, i18nConstants, i18nMessages);

			break;
		}
		case CENARIOS: {
			break;
		}
		case CIDADES: {
			break;
		}
		case ESTADOS: {
			break;
		}
		case PESSOAS: {
			break;
		}
		case USUARIOS: {
			break;
		}
		default: {
			break;
		}
		}

		return exporter;
	}
}
