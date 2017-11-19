package br.com.vitafarma.web.server.excel.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.util.HtmlUtils;

import br.com.vitafarma.domain.Cenario;
import br.com.vitafarma.domain.Produto;
import br.com.vitafarma.web.shared.excel.ExcelInformationType;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nConstants;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nMessages;

public class ProdutosImportExcel extends AbstractImportExcel<ProdutosImportExcelBean> {
	public static String CODIGO_COLUMN_NAME = null;
	public static String UNIDADE_ENTRADA_COLUMN_NAME = null;
	public static String UNIDADE_VENDA_COLUMN_NAME = null;
	public static String CODIGO_ABCFARMA_COLUMN_NAME = null;
	public static String NOME_COLUMN_NAME = null;
	public static String DESCRICAO_COLUMN_NAME = null;
	public static String PRECO_COLUMN_NAME = null;
	public static String ESTOQUE_COLUMN_NAME = null;

	private List<String> headerColumnsNames = null;

	public ProdutosImportExcel(Cenario cenario, VitafarmaI18nConstants i18nConstants,
			VitafarmaI18nMessages i18nMessages) {
		super(cenario, i18nConstants, i18nMessages);

		this.resolveHeaderColumnNames();

		this.headerColumnsNames = new ArrayList<String>();

		this.headerColumnsNames.add(CODIGO_COLUMN_NAME);
		this.headerColumnsNames.add(CODIGO_ABCFARMA_COLUMN_NAME);
		this.headerColumnsNames.add(NOME_COLUMN_NAME);
		this.headerColumnsNames.add(DESCRICAO_COLUMN_NAME);
		this.headerColumnsNames.add(PRECO_COLUMN_NAME);
		this.headerColumnsNames.add(ESTOQUE_COLUMN_NAME);
		this.headerColumnsNames.add(UNIDADE_ENTRADA_COLUMN_NAME);
		this.headerColumnsNames.add(UNIDADE_VENDA_COLUMN_NAME);
	}

	@Override
	protected boolean sheetMustBeProcessed(int sheetIndex, HSSFSheet sheet, HSSFWorkbook workbook) {
		String sheetName = workbook.getSheetName(sheetIndex);
		return ExcelInformationType.PRODUTOS.getSheetName().equals(sheetName);
	}

	@Override
	protected List<String> getHeaderColumnsNames(int sheetIndex, HSSFSheet sheet, HSSFWorkbook workbook) {
		return this.headerColumnsNames;
	}

	@Override
	protected ProdutosImportExcelBean createExcelBean(HSSFRow header, HSSFRow row, int sheetIndex, HSSFSheet sheet,
			HSSFWorkbook workbook) {
		ProdutosImportExcelBean bean = new ProdutosImportExcelBean(row.getRowNum() + 1);

		for (int cellIndex = row.getFirstCellNum(); cellIndex <= row.getLastCellNum(); cellIndex++) {
			HSSFCell cell = row.getCell(cellIndex);

			if (cell != null) {
				HSSFCell headerCell = header.getCell(cell.getColumnIndex());

				if (headerCell != null) {
					String columnName = headerCell.getRichStringCellValue().getString();

					String cellValue = this.getCellValue(cell);

					if (CODIGO_COLUMN_NAME.equals(columnName)) {
						bean.setCodigoProdutoStr(cellValue);
					} else if (NOME_COLUMN_NAME.endsWith(columnName)) {
						bean.setNomeProdutoStr(cellValue);
					} else if (CODIGO_ABCFARMA_COLUMN_NAME.endsWith(columnName)) {
						bean.setCodigoAbcFarmaStr(cellValue);
					} else if (DESCRICAO_COLUMN_NAME.equals(columnName)) {
						bean.setDescricaoProdutoStr(cellValue);
					} else if (PRECO_COLUMN_NAME.equals(columnName)) {
						bean.setPrecoVendaProdutoStr(cellValue);
					} else if (ESTOQUE_COLUMN_NAME.equals(columnName)) {
						bean.setEstoqueProdutoStr(cellValue);
					} else if (UNIDADE_ENTRADA_COLUMN_NAME.equals(columnName)) {
						bean.setUnidadeEntradaProdutoStr(cellValue);
					} else if (UNIDADE_VENDA_COLUMN_NAME.equals(columnName)) {
						bean.setUnidadeVendaProdutoStr(cellValue);
					}
				}
			}
		}

		return bean;
	}

	@Override
	protected String getHeaderToString() {
		return this.headerColumnsNames.toString();
	}

	@Override
	public String getSheetName() {
		return ExcelInformationType.PRODUTOS.getSheetName();
	}

	@Override
	protected void processSheetContent(String sheetName, List<ProdutosImportExcelBean> sheetContent) {
		if (this.doSyntacticValidation(sheetName, sheetContent) && this.doLogicValidation(sheetName, sheetContent)) {
			this.updateDataBase(sheetName, sheetContent);
		}
	}

	private boolean doSyntacticValidation(String sheetName, List<ProdutosImportExcelBean> sheetContent) {
		// Map utilizado para associar um erro
		// às linhas do arquivo onde o mesmo ocorre

		// [ ImportExcelError -> Lista de linhas onde o erro ocorre ]
		Map<ImportExcelError, List<Integer>> syntacticErrorsMap = new HashMap<ImportExcelError, List<Integer>>();

		for (ProdutosImportExcelBean bean : sheetContent) {
			List<ImportExcelError> errorsBean = bean.checkSyntacticErrors();

			for (ImportExcelError error : errorsBean) {
				List<Integer> rowsWithErrors = syntacticErrorsMap.get(error);

				if (rowsWithErrors == null) {
					rowsWithErrors = new ArrayList<Integer>();

					syntacticErrorsMap.put(error, rowsWithErrors);
				}

				rowsWithErrors.add(bean.getRow());
			}
		}

		// Coleta os erros e adiciona os mesmos na lista de mensagens
		for (ImportExcelError error : syntacticErrorsMap.keySet()) {
			List<Integer> linhasComErro = syntacticErrorsMap.get(error);

			this.getErrors().add(error.getMessage(linhasComErro.toString(), this.getI18nMessages()));
		}

		return syntacticErrorsMap.isEmpty();
	}

	private boolean doLogicValidation(String sheetName, List<ProdutosImportExcelBean> sheetContent) {
		// Verifica se algum produto apareceu
		// mais de uma vez no arquivo de entrada
		this.checkUniqueness(sheetContent);

		return this.getErrors().isEmpty();
	}

	private void checkUniqueness(List<ProdutosImportExcelBean> sheetContent) {
		// Map com os códigos dos produtos e as linhas
		// em que o mesmo aparece no arquivo de entrada

		// [ CódigoProduto -> Lista de Linhas do Arquivo de Entrada ]
		Map<String, List<Integer>> produtosCodigoToRowsMap = new HashMap<String, List<Integer>>();

		for (ProdutosImportExcelBean bean : sheetContent) {
			List<Integer> rows = produtosCodigoToRowsMap.get(bean.getCodigoProdutoStr());

			if (rows == null) {
				rows = new ArrayList<Integer>();

				produtosCodigoToRowsMap.put(bean.getCodigoProdutoStr(), rows);
			}

			rows.add(bean.getRow());
		}

		// Verifica se algum produto apareceu
		// mais de uma vez no arquivo de entrada
		for (Entry<String, List<Integer>> entry : produtosCodigoToRowsMap.entrySet()) {
			if (entry.getValue().size() > 1) {
				this.getErrors().add(this.getI18nMessages().excelErroLogicoUnicidadeViolada(entry.getKey(),
						entry.getValue().toString()));
			}
		}
	}

	private void updateDataBase(String sheetName, List<ProdutosImportExcelBean> sheetContent) {
		Map<Long, Produto> produtosBDMap = Produto.buildCodigoToProdutoMap(Produto.findAll());

		for (ProdutosImportExcelBean produtoExcel : sheetContent) {
			Produto produtoBD = produtosBDMap.get(produtoExcel.getCodigoProduto());

			if (produtoBD != null) {
				// Update
				produtoBD.setId(produtoExcel.getCodigoProduto());
				produtoBD.setMedAbc(produtoExcel.getCodigoAbcFarma());
				produtoBD.setNome(produtoExcel.getNomeProdutoStr());
				produtoBD.setDescricao(produtoExcel.getDescricaoProdutoStr());
				produtoBD.setPreco(produtoExcel.getPrecoVendaProduto());
				produtoBD.setEstoque(produtoExcel.getEstoqueProduto());
				produtoBD.setUnidadeEntrada(produtoExcel.getUnidadeEntradaProdutoStr());
				produtoBD.setUnidadeVenda(produtoExcel.getUnidadeVendaProdutoStr());

				produtoBD.save();

				produtoBD.refresh();
			} else {
				// Insert
				Produto newProduto = new Produto(null, null, produtoExcel.getNomeProdutoStr(),
						produtoExcel.getDescricaoProdutoStr(), produtoExcel.getPrecoVendaProduto(),
						produtoExcel.getEstoqueProduto(), produtoExcel.getUnidadeEntradaProdutoStr(),
						produtoExcel.getUnidadeVendaProdutoStr(), null);

				newProduto.save();

				newProduto.refresh();

				produtosBDMap.put(produtoExcel.getCodigoProduto(), newProduto);
			}
		}
	}

	private void resolveHeaderColumnNames() {
		if (CODIGO_COLUMN_NAME == null) {
			CODIGO_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().codigoProdutoExcel());
			CODIGO_ABCFARMA_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().codigoAbcFarmaExcel());
			NOME_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().nomeProdutoExcel());
			DESCRICAO_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().descricaoProdutoExcel());
			PRECO_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().precoVendaProdutoExcel());
			ESTOQUE_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().estoqueProdutoExcel());
			UNIDADE_ENTRADA_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().unidadeEntrada());
			UNIDADE_VENDA_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().unidadeVenda());
		}
	}
}
