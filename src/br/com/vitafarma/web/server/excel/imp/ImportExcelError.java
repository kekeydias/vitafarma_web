package br.com.vitafarma.web.server.excel.imp;

import br.com.vitafarma.web.shared.i18n.VitafarmaI18nMessages;

public enum ImportExcelError {
	TUDO_VAZIO, ABCFARMA_MED_ABC_VAZIO, ABCFARMA_MED_ABC_FORMATO_INVALIDO, ABCFARMA_MED_CTR_VAZIO, ABCFARMA_MED_CTR_FORMATO_INVALIDO, ABCFARMA_MED_LAB_VAZIO, ABCFARMA_MED_LAB_FORMATO_INVALIDO, ABCFARMA_LAB_NOM_VAZIO, ABCFARMA_LAB_NOM_FORMATO_INVALIDO, ABCFARMA_MED_DES_VAZIO, ABCFARMA_MED_DES_FORMATO_INVALIDO, ABCFARMA_MED_APR_VAZIO, ABCFARMA_MED_APR_FORMATO_INVALIDO, ABCFARMA_MED_PLA1_VAZIO, ABCFARMA_MED_PLA1_FORMATO_INVALIDO, ABCFARMA_MED_PCO1_VAZIO, ABCFARMA_MED_PCO1_FORMATO_INVALIDO, ABCFARMA_MED_FRA1_VAZIO, ABCFARMA_MED_FRA1_FORMATO_INVALIDO, ABCFARMA_MED_UNI_VAZIO, ABCFARMA_MED_UNI_FORMATO_INVALIDO, ABCFARMA_MED_IPI_VAZIO, ABCFARMA_MED_IPI_FORMATO_INVALIDO, ABCFARMA_DT_VIG_VAZIO, ABCFARMA_DT_VIG_FORMATO_INVALIDO, ABCFARMA_EXP_13_VAZIO, ABCFARMA_EXP_13_FORMATO_INVALIDO, ABCFARMA_MED_BARRA_VAZIO, ABCFARMA_MED_BARRA_FORMATO_INVALIDO, ABCFARMA_MED_GENE_VAZIO, ABCFARMA_MED_GENE_FORMATO_INVALIDO, ABCFARMA_MED_NEG_POS_VAZIO, ABCFARMA_MED_NEG_POS_FORMATO_INVALIDO, ABCFARMA_MED_PRINCI_VAZIO, ABCFARMA_MED_PRINCI_FORMATO_INVALIDO, ABCFARMA_MED_PLA0_VAZIO, ABCFARMA_MED_PLA0_FORMATO_INVALIDO, ABCFARMA_MED_PCO0_VAZIO, ABCFARMA_MED_PCO0_FORMATO_INVALIDO, ABCFARMA_MED_FRA0_VAZIO, ABCFARMA_MED_FRA0_FORMATO_INVALIDO, ABCFARMA_MED_REGIMS_VAZIO, ABCFARMA_MED_REGIMS_FORMATO_INVALIDO, ABCFARMA_MED_VAR_PRE_VAZIO, ABCFARMA_MED_VAR_PRE_FORMATO_INVALIDO, PRODUTO_CODIGO_VAZIO, PRODUTO_CODIGO_FORMATO_INVALIDO, PRODUTO_NOME_VAZIO, PRODUTO_DESCRICAO_VAZIO, PRODUTO_PRECO_VENDA_VAZIO, PRODUTO_PRECO_VENDA_FORMATO_INVALIDO, PRODUTO_ESTOQUE_VAZIO, PRODUTO_ESTOQUE_FORMATO_INVALIDO, PRODUTO_CODIGO_ABCFARMA_VAZIO, PRODUTO_CODIGO_ABCFARMA_INVALIDO, PRODUTO_UNIDADE_ENTRADA_VAZIO, PRODUTO_UNIDADE_VENDA_VAZIO;

	public String getMessage(String param1, VitafarmaI18nMessages i18nMessages) {
		switch (this) {
		// EXCEL VAZIO
		case TUDO_VAZIO:
			return i18nMessages.excelErroDocumentoVazio();

			// PRODUTOS
		case PRODUTO_CODIGO_VAZIO:
			return i18nMessages.excelErroSintaticoColunaVazia(param1,
					ProdutosImportExcel.CODIGO_COLUMN_NAME);
		case PRODUTO_CODIGO_FORMATO_INVALIDO:
			return i18nMessages.excelErroSintaticoFormatoInvalido(param1,
					ProdutosImportExcel.CODIGO_COLUMN_NAME);
		case PRODUTO_UNIDADE_ENTRADA_VAZIO:
			return i18nMessages.excelErroSintaticoFormatoInvalido(param1,
					ProdutosImportExcel.UNIDADE_ENTRADA_COLUMN_NAME);
		case PRODUTO_UNIDADE_VENDA_VAZIO:
			return i18nMessages.excelErroSintaticoFormatoInvalido(param1,
					ProdutosImportExcel.UNIDADE_VENDA_COLUMN_NAME);
		case PRODUTO_NOME_VAZIO:
			return i18nMessages.excelErroSintaticoColunaVazia(param1,
					ProdutosImportExcel.NOME_COLUMN_NAME);
		case PRODUTO_CODIGO_ABCFARMA_VAZIO:
			return i18nMessages.excelErroSintaticoColunaVazia(param1,
					ProdutosImportExcel.CODIGO_ABCFARMA_COLUMN_NAME);
		case PRODUTO_CODIGO_ABCFARMA_INVALIDO:
			return i18nMessages.excelErroSintaticoFormatoInvalido(param1,
					ProdutosImportExcel.CODIGO_ABCFARMA_COLUMN_NAME);
		case PRODUTO_DESCRICAO_VAZIO:
			return i18nMessages.excelErroSintaticoColunaVazia(param1,
					ProdutosImportExcel.DESCRICAO_COLUMN_NAME);
		case PRODUTO_PRECO_VENDA_VAZIO:
			return i18nMessages.excelErroSintaticoColunaVazia(param1,
					ProdutosImportExcel.PRECO_COLUMN_NAME);
		case PRODUTO_PRECO_VENDA_FORMATO_INVALIDO:
			return i18nMessages.excelErroSintaticoFormatoInvalido(param1,
					ProdutosImportExcel.PRECO_COLUMN_NAME);
		case PRODUTO_ESTOQUE_VAZIO:
			return i18nMessages.excelErroSintaticoColunaVazia(param1,
					ProdutosImportExcel.ESTOQUE_COLUMN_NAME);
		case PRODUTO_ESTOQUE_FORMATO_INVALIDO:
			return i18nMessages.excelErroSintaticoFormatoInvalido(param1,
					ProdutosImportExcel.ESTOQUE_COLUMN_NAME);

			// TABELA ABCFARMA
		case ABCFARMA_MED_ABC_VAZIO:
			return i18nMessages.excelErroSintaticoColunaVazia(param1,
					ProdutosAbcfarmaImportExcel.MED_ABC_COLUMN_NAME);
		case ABCFARMA_MED_ABC_FORMATO_INVALIDO:
			return i18nMessages.excelErroSintaticoFormatoInvalido(param1,
					ProdutosAbcfarmaImportExcel.MED_ABC_COLUMN_NAME);
		case ABCFARMA_MED_CTR_VAZIO:
			return i18nMessages.excelErroSintaticoColunaVazia(param1,
					ProdutosAbcfarmaImportExcel.MED_CTR_COLUMN_NAME);
		case ABCFARMA_MED_CTR_FORMATO_INVALIDO:
			return i18nMessages.excelErroSintaticoFormatoInvalido(param1,
					ProdutosAbcfarmaImportExcel.MED_CTR_COLUMN_NAME);
		case ABCFARMA_MED_LAB_VAZIO:
			return i18nMessages.excelErroSintaticoColunaVazia(param1,
					ProdutosAbcfarmaImportExcel.MED_LAB_COLUMN_NAME);
		case ABCFARMA_MED_LAB_FORMATO_INVALIDO:
			return i18nMessages.excelErroSintaticoFormatoInvalido(param1,
					ProdutosAbcfarmaImportExcel.MED_LAB_COLUMN_NAME);
		case ABCFARMA_LAB_NOM_VAZIO:
			return i18nMessages.excelErroSintaticoColunaVazia(param1,
					ProdutosAbcfarmaImportExcel.LAB_NOM_COLUMN_NAME);
		case ABCFARMA_LAB_NOM_FORMATO_INVALIDO:
			return i18nMessages.excelErroSintaticoFormatoInvalido(param1,
					ProdutosAbcfarmaImportExcel.MED_LAB_COLUMN_NAME);
		case ABCFARMA_MED_DES_VAZIO:
			return i18nMessages.excelErroSintaticoColunaVazia(param1,
					ProdutosAbcfarmaImportExcel.MED_DES_COLUMN_NAME);
		case ABCFARMA_MED_DES_FORMATO_INVALIDO:
			return i18nMessages.excelErroSintaticoFormatoInvalido(param1,
					ProdutosAbcfarmaImportExcel.MED_DES_COLUMN_NAME);
		case ABCFARMA_MED_APR_VAZIO:
			return i18nMessages.excelErroSintaticoColunaVazia(param1,
					ProdutosAbcfarmaImportExcel.MED_APR_COLUMN_NAME);
		case ABCFARMA_MED_APR_FORMATO_INVALIDO:
			return i18nMessages.excelErroSintaticoFormatoInvalido(param1,
					ProdutosAbcfarmaImportExcel.MED_APR_COLUMN_NAME);
		case ABCFARMA_MED_PLA1_VAZIO:
			return i18nMessages.excelErroSintaticoColunaVazia(param1,
					ProdutosAbcfarmaImportExcel.MED_PLA1_COLUMN_NAME);
		case ABCFARMA_MED_PLA1_FORMATO_INVALIDO:
			return i18nMessages.excelErroSintaticoFormatoInvalido(param1,
					ProdutosAbcfarmaImportExcel.MED_PLA1_COLUMN_NAME);
		case ABCFARMA_MED_PCO1_VAZIO:
			return i18nMessages.excelErroSintaticoColunaVazia(param1,
					ProdutosAbcfarmaImportExcel.MED_PCO1_COLUMN_NAME);
		case ABCFARMA_MED_PCO1_FORMATO_INVALIDO:
			return i18nMessages.excelErroSintaticoFormatoInvalido(param1,
					ProdutosAbcfarmaImportExcel.MED_PCO1_COLUMN_NAME);
		case ABCFARMA_MED_FRA1_VAZIO:
			return i18nMessages.excelErroSintaticoColunaVazia(param1,
					ProdutosAbcfarmaImportExcel.MED_FRA1_COLUMN_NAME);
		case ABCFARMA_MED_FRA1_FORMATO_INVALIDO:
			return i18nMessages.excelErroSintaticoFormatoInvalido(param1,
					ProdutosAbcfarmaImportExcel.MED_FRA1_COLUMN_NAME);
		case ABCFARMA_MED_UNI_VAZIO:
			return i18nMessages.excelErroSintaticoColunaVazia(param1,
					ProdutosAbcfarmaImportExcel.MED_UNI_COLUMN_NAME);
		case ABCFARMA_MED_UNI_FORMATO_INVALIDO:
			return i18nMessages.excelErroSintaticoFormatoInvalido(param1,
					ProdutosAbcfarmaImportExcel.MED_UNI_COLUMN_NAME);
		case ABCFARMA_MED_IPI_VAZIO:
			return i18nMessages.excelErroSintaticoColunaVazia(param1,
					ProdutosAbcfarmaImportExcel.MED_IPI_COLUMN_NAME);
		case ABCFARMA_MED_IPI_FORMATO_INVALIDO:
			return i18nMessages.excelErroSintaticoFormatoInvalido(param1,
					ProdutosAbcfarmaImportExcel.MED_IPI_COLUMN_NAME);
		case ABCFARMA_DT_VIG_VAZIO:
			return i18nMessages.excelErroSintaticoColunaVazia(param1,
					ProdutosAbcfarmaImportExcel.MED_DT_VIG_COLUMN_NAME);
		case ABCFARMA_DT_VIG_FORMATO_INVALIDO:
			return i18nMessages.excelErroSintaticoFormatoInvalido(param1,
					ProdutosAbcfarmaImportExcel.MED_DT_VIG_COLUMN_NAME);
		case ABCFARMA_EXP_13_VAZIO:
			return i18nMessages.excelErroSintaticoColunaVazia(param1,
					ProdutosAbcfarmaImportExcel.EXP_13_COLUMN_NAME);
		case ABCFARMA_EXP_13_FORMATO_INVALIDO:
			return i18nMessages.excelErroSintaticoFormatoInvalido(param1,
					ProdutosAbcfarmaImportExcel.EXP_13_COLUMN_NAME);
		case ABCFARMA_MED_BARRA_VAZIO:
			return i18nMessages.excelErroSintaticoColunaVazia(param1,
					ProdutosAbcfarmaImportExcel.MED_BARRA_COLUMN_NAME);
		case ABCFARMA_MED_BARRA_FORMATO_INVALIDO:
			return i18nMessages.excelErroSintaticoFormatoInvalido(param1,
					ProdutosAbcfarmaImportExcel.MED_BARRA_COLUMN_NAME);
		case ABCFARMA_MED_GENE_VAZIO:
			return i18nMessages.excelErroSintaticoColunaVazia(param1,
					ProdutosAbcfarmaImportExcel.MED_GENE_COLUMN_NAME);
		case ABCFARMA_MED_GENE_FORMATO_INVALIDO:
			return i18nMessages.excelErroSintaticoFormatoInvalido(param1,
					ProdutosAbcfarmaImportExcel.MED_GENE_COLUMN_NAME);
		case ABCFARMA_MED_NEG_POS_VAZIO:
			return i18nMessages.excelErroSintaticoColunaVazia(param1,
					ProdutosAbcfarmaImportExcel.MED_NEG_POS_COLUMN_NAME);
		case ABCFARMA_MED_NEG_POS_FORMATO_INVALIDO:
			return i18nMessages.excelErroSintaticoFormatoInvalido(param1,
					ProdutosAbcfarmaImportExcel.MED_NEG_POS_COLUMN_NAME);
		case ABCFARMA_MED_PRINCI_VAZIO:
			return i18nMessages.excelErroSintaticoColunaVazia(param1,
					ProdutosAbcfarmaImportExcel.MED_PRINCI_COLUMN_NAME);
		case ABCFARMA_MED_PRINCI_FORMATO_INVALIDO:
			return i18nMessages.excelErroSintaticoFormatoInvalido(param1,
					ProdutosAbcfarmaImportExcel.MED_PRINCI_COLUMN_NAME);
		case ABCFARMA_MED_PLA0_VAZIO:
			return i18nMessages.excelErroSintaticoColunaVazia(param1,
					ProdutosAbcfarmaImportExcel.MED_PLA0_COLUMN_NAME);
		case ABCFARMA_MED_PLA0_FORMATO_INVALIDO:
			return i18nMessages.excelErroSintaticoFormatoInvalido(param1,
					ProdutosAbcfarmaImportExcel.MED_PLA0_COLUMN_NAME);
		case ABCFARMA_MED_PCO0_VAZIO:
			return i18nMessages.excelErroSintaticoColunaVazia(param1,
					ProdutosAbcfarmaImportExcel.MED_PCO0_COLUMN_NAME);
		case ABCFARMA_MED_PCO0_FORMATO_INVALIDO:
			return i18nMessages.excelErroSintaticoFormatoInvalido(param1,
					ProdutosAbcfarmaImportExcel.MED_PCO0_COLUMN_NAME);
		case ABCFARMA_MED_FRA0_VAZIO:
			return i18nMessages.excelErroSintaticoColunaVazia(param1,
					ProdutosAbcfarmaImportExcel.MED_FRA0_COLUMN_NAME);
		case ABCFARMA_MED_FRA0_FORMATO_INVALIDO:
			return i18nMessages.excelErroSintaticoFormatoInvalido(param1,
					ProdutosAbcfarmaImportExcel.MED_FRA0_COLUMN_NAME);
		case ABCFARMA_MED_REGIMS_VAZIO:
			return i18nMessages.excelErroSintaticoColunaVazia(param1,
					ProdutosAbcfarmaImportExcel.MED_REGIMS_COLUMN_NAME);
		case ABCFARMA_MED_REGIMS_FORMATO_INVALIDO:
			return i18nMessages.excelErroSintaticoFormatoInvalido(param1,
					ProdutosAbcfarmaImportExcel.MED_REGIMS_COLUMN_NAME);
		case ABCFARMA_MED_VAR_PRE_VAZIO:
			return i18nMessages.excelErroSintaticoColunaVazia(param1,
					ProdutosAbcfarmaImportExcel.MED_VAR_PRE_COLUMN_NAME);
		case ABCFARMA_MED_VAR_PRE_FORMATO_INVALIDO:
			return i18nMessages.excelErroSintaticoFormatoInvalido(param1,
					ProdutosAbcfarmaImportExcel.MED_VAR_PRE_COLUMN_NAME);
		default:
			break;
		}

		return "";
	}
}
