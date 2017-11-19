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
import br.com.vitafarma.domain.Cidade;
import br.com.vitafarma.domain.Laboratorio;
import br.com.vitafarma.domain.Produto;
import br.com.vitafarma.web.shared.excel.ExcelInformationType;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nConstants;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nMessages;

public class ProdutosAbcfarmaImportExcel extends AbstractImportExcel<ProdutosAbcfarmaImportExcelBean> {
	public static String MED_ABC_COLUMN_NAME;
	public static String MED_CTR_COLUMN_NAME;
	public static String MED_LAB_COLUMN_NAME;
	public static String LAB_NOM_COLUMN_NAME;
	public static String MED_DES_COLUMN_NAME;
	public static String MED_APR_COLUMN_NAME;
	public static String MED_PLA1_COLUMN_NAME;
	public static String MED_PCO1_COLUMN_NAME;
	public static String MED_FRA1_COLUMN_NAME;
	public static String MED_UNI_COLUMN_NAME;
	public static String MED_IPI_COLUMN_NAME;
	public static String MED_DT_VIG_COLUMN_NAME;
	public static String EXP_13_COLUMN_NAME;
	public static String MED_BARRA_COLUMN_NAME;
	public static String MED_GENE_COLUMN_NAME;
	public static String MED_NEG_POS_COLUMN_NAME;
	public static String MED_PRINCI_COLUMN_NAME;
	public static String MED_PLA0_COLUMN_NAME;
	public static String MED_PCO0_COLUMN_NAME;
	public static String MED_FRA0_COLUMN_NAME;
	public static String MED_REGIMS_COLUMN_NAME;
	public static String MED_VAR_PRE_COLUMN_NAME;

	private boolean createNewEntities = false;

	private List<String> headerColumnsNames;

	public ProdutosAbcfarmaImportExcel(Cenario cenario, VitafarmaI18nConstants i18nConstants,
			VitafarmaI18nMessages i18nMessages, boolean createNewEntities) {
		super(cenario, i18nConstants, i18nMessages);
		this.resolveHeaderColumnNames();

		this.createNewEntities = createNewEntities;

		this.headerColumnsNames = new ArrayList<String>();
		this.headerColumnsNames.add(MED_ABC_COLUMN_NAME);
		this.headerColumnsNames.add(MED_CTR_COLUMN_NAME);
		this.headerColumnsNames.add(MED_LAB_COLUMN_NAME);
		this.headerColumnsNames.add(LAB_NOM_COLUMN_NAME);
		this.headerColumnsNames.add(MED_DES_COLUMN_NAME);
		this.headerColumnsNames.add(MED_APR_COLUMN_NAME);
		this.headerColumnsNames.add(MED_PLA1_COLUMN_NAME);
		this.headerColumnsNames.add(MED_PCO1_COLUMN_NAME);
		this.headerColumnsNames.add(MED_FRA1_COLUMN_NAME);
		this.headerColumnsNames.add(MED_UNI_COLUMN_NAME);
		this.headerColumnsNames.add(MED_IPI_COLUMN_NAME);
		this.headerColumnsNames.add(MED_DT_VIG_COLUMN_NAME);
		this.headerColumnsNames.add(EXP_13_COLUMN_NAME);
		this.headerColumnsNames.add(MED_BARRA_COLUMN_NAME);
		this.headerColumnsNames.add(MED_GENE_COLUMN_NAME);
		this.headerColumnsNames.add(MED_NEG_POS_COLUMN_NAME);
		this.headerColumnsNames.add(MED_PRINCI_COLUMN_NAME);
		this.headerColumnsNames.add(MED_PLA0_COLUMN_NAME);
		this.headerColumnsNames.add(MED_PCO0_COLUMN_NAME);
		this.headerColumnsNames.add(MED_FRA0_COLUMN_NAME);
		this.headerColumnsNames.add(MED_REGIMS_COLUMN_NAME);
		this.headerColumnsNames.add(MED_VAR_PRE_COLUMN_NAME);
	}

	@Override
	protected boolean sheetMustBeProcessed(int sheetIndex, HSSFSheet sheet, HSSFWorkbook workbook) {
		String sheetName = workbook.getSheetName(sheetIndex);
		return ExcelInformationType.PRODUTOS_ABCFARMA.getSheetName().equals(sheetName);
	}

	@Override
	protected List<String> getHeaderColumnsNames(int sheetIndex, HSSFSheet sheet, HSSFWorkbook workbook) {
		return this.headerColumnsNames;
	}

	@Override
	protected ProdutosAbcfarmaImportExcelBean createExcelBean(HSSFRow header, HSSFRow row, int sheetIndex,
			HSSFSheet sheet, HSSFWorkbook workbook) {
		ProdutosAbcfarmaImportExcelBean bean = new ProdutosAbcfarmaImportExcelBean(row.getRowNum() + 1);

		for (int cellIndex = row.getFirstCellNum(); cellIndex <= row.getLastCellNum(); cellIndex++) {
			HSSFCell cell = row.getCell(cellIndex);

			if (cell != null) {
				HSSFCell headerCell = header.getCell(cell.getColumnIndex());

				if (headerCell != null) {
					String columnName = headerCell.getRichStringCellValue().getString();
					String cellValue = this.getCellValue(cell);

					if (MED_ABC_COLUMN_NAME.equals(columnName)) {
						bean.setMedAbcStr(cellValue);
					} else if (MED_CTR_COLUMN_NAME.endsWith(columnName)) {
						bean.setMedCtrStr(cellValue);
					} else if (MED_LAB_COLUMN_NAME.equals(columnName)) {
						bean.setMedLabStr(cellValue);
					} else if (LAB_NOM_COLUMN_NAME.equals(columnName)) {
						bean.setLabNomStr(cellValue);
					} else if (MED_DES_COLUMN_NAME.equals(columnName)) {
						bean.setMedDesStr(cellValue);
					} else if (MED_APR_COLUMN_NAME.equals(columnName)) {
						bean.setMedAprStr(cellValue);
					} else if (MED_PLA1_COLUMN_NAME.endsWith(columnName)) {
						bean.setMedPla1Str(cellValue);
					} else if (MED_PCO1_COLUMN_NAME.equals(columnName)) {
						bean.setMedPco1Str(cellValue);
					} else if (MED_FRA1_COLUMN_NAME.equals(columnName)) {
						bean.setMedFra1Str(cellValue);
					} else if (MED_UNI_COLUMN_NAME.equals(columnName)) {
						bean.setMedUniStr(cellValue);
					} else if (MED_IPI_COLUMN_NAME.equals(columnName)) {
						bean.setMedIpiStr(cellValue);
					} else if (MED_DT_VIG_COLUMN_NAME.endsWith(columnName)) {
						bean.setMedDtVigStr(cellValue);
					} else if (EXP_13_COLUMN_NAME.equals(columnName)) {
						bean.setExp13Str(cellValue);
					} else if (MED_BARRA_COLUMN_NAME.equals(columnName)) {
						bean.setMedBarraStr(cellValue);
					} else if (MED_GENE_COLUMN_NAME.equals(columnName)) {
						bean.setMedGeneStr(cellValue);
					} else if (MED_NEG_POS_COLUMN_NAME.endsWith(columnName)) {
						bean.setMedNegPosStr(cellValue);
					} else if (MED_PRINCI_COLUMN_NAME.equals(columnName)) {
						bean.setMedPrinciStr(cellValue);
					} else if (MED_PLA0_COLUMN_NAME.equals(columnName)) {
						bean.setMedPla0Str(cellValue);
					} else if (MED_PCO0_COLUMN_NAME.equals(columnName)) {
						bean.setMedPco0Str(cellValue);
					} else if (MED_FRA0_COLUMN_NAME.equals(columnName)) {
						bean.setMedFra0Str(cellValue);
					} else if (MED_VAR_PRE_COLUMN_NAME.equals(columnName)) {
						bean.setMedVarPreStr(cellValue);
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
		return ExcelInformationType.PRODUTOS_ABCFARMA.getSheetName();
	}

	@Override
	protected void processSheetContent(String sheetName, List<ProdutosAbcfarmaImportExcelBean> sheetContent) {
		if (this.doSyntacticValidation(sheetName, sheetContent) && this.doLogicValidation(sheetName, sheetContent)) {
			this.updateDataBase(sheetName, sheetContent);
		}
	}

	private boolean doSyntacticValidation(String sheetName, List<ProdutosAbcfarmaImportExcelBean> sheetContent) {
		// Map utilizado para associar um erro
		// às linhas do arquivo onde o mesmo ocorre

		// [ ImportExcelError -> Lista de linhas onde o erro ocorre ]
		Map<ImportExcelError, List<Integer>> syntacticErrorsMap = new HashMap<ImportExcelError, List<Integer>>();

		for (ProdutosAbcfarmaImportExcelBean bean : sheetContent) {
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

			getErrors().add(error.getMessage(linhasComErro.toString(), getI18nMessages()));
		}

		return syntacticErrorsMap.isEmpty();
	}

	private boolean doLogicValidation(String sheetName, List<ProdutosAbcfarmaImportExcelBean> sheetContent) {
		// Verifica se algum produto apareceu
		// mais de uma vez no arquivo de entrada
		this.checkUniqueness(sheetContent);
		return this.getErrors().isEmpty();
	}

	private void checkUniqueness(List<ProdutosAbcfarmaImportExcelBean> sheetContent) {
		// Map com os códigos dos produtos e as linhas
		// em que o mesmo aparece no arquivo de entrada

		// [ MedAbc -> Lista de Linhas do Arquivo de Entrada ]
		Map<Long, List<Integer>> produtosCodigoToRowsMap = new HashMap<Long, List<Integer>>();

		for (ProdutosAbcfarmaImportExcelBean bean : sheetContent) {
			List<Integer> rows = produtosCodigoToRowsMap.get(bean.getMedAbc());

			if (rows == null) {
				rows = new ArrayList<Integer>();
				produtosCodigoToRowsMap.put(bean.getMedAbc(), rows);
			}

			rows.add(bean.getRow());
		}

		// Verifica se algum produto apareceu
		// mais de uma vez no arquivo de entrada
		for (Entry<Long, List<Integer>> entry : produtosCodigoToRowsMap.entrySet()) {
			if (entry.getValue().size() > 1) {
				this.getErrors().add(this.getI18nMessages().excelErroLogicoUnicidadeViolada(entry.getKey().toString(),
						entry.getValue().toString()));
			}
		}
	}

	private void updateDataBase(String sheetName, List<ProdutosAbcfarmaImportExcelBean> sheetContent) {
		// Carrega os produtos que já existem na base de dados
		Map<Long, Produto> produtosBDMap = Produto.buildMedAbcToProdutoAbcFarmaMap(Produto.findAll());

		// Carrega os laboratórios que já existem na base de dados
		Map<Long, Laboratorio> laboratoriosBDMap = Laboratorio.buildNomeToLaboratorioMap(Laboratorio.findAll());

		// Caso seja necessário criar um novo laboratório,
		// deve-se informar uma cidade default, ou null caso
		// não exista nenhuma cidade cadastrada na base de dados
		Cidade cidade = null;
		if (Cidade.count() != 0) {
			List<Cidade> cidades = Cidade.findAll();
			cidade = cidades.get(0);
		}

		for (ProdutosAbcfarmaImportExcelBean produtoExcel : sheetContent) {
			Produto produtoBD = produtosBDMap.get(produtoExcel.getMedAbc());

			Laboratorio laboratorio = laboratoriosBDMap.get(produtoExcel.getMedLab());

			// Salvando os dados do novo laboratório
			if (laboratorio == null && this.createNewEntities) {

				laboratorio = new Laboratorio(null, produtoExcel.getMedLab(), produtoExcel.getLabNomStr(),
						produtoExcel.getLabNomStr(), 99999999999999L, 9999999999L/* Telefone */, "endereco", "email",
						cidade);

				laboratorio.save();

				laboratorio.refresh();

				laboratoriosBDMap.put(produtoExcel.getMedLab(), laboratorio);
			}

			// Verificando se o produto já existe na base de dados
			if (produtoBD != null) {
				// Update
				produtoBD.setId(produtoExcel.getMedAbc());
				produtoBD.setNome(produtoExcel.getMedDesStr());
				produtoBD.setDescricao(produtoExcel.getMedAprStr());
				produtoBD.setPreco(produtoExcel.getMedPco1());
				produtoBD.setLaboratorio(laboratorio);

				produtoBD.save();

				produtoBD.refresh();
			} else {
				// Insert
				Produto newProduto = new Produto(null, produtoExcel.getMedAbc(), produtoExcel.getMedDesStr(),
						produtoExcel.getMedAprStr(), produtoExcel.getMedPco1(), 0.0, "UN", "UN", laboratorio);

				newProduto.save();

				newProduto.refresh();

				produtosBDMap.put(produtoExcel.getMedAbc(), newProduto);
			}
		}
	}

	private void resolveHeaderColumnNames() {
		if (MED_ABC_COLUMN_NAME == null) {
			MED_ABC_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().medAbcExcel());
			MED_CTR_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().medCtrExcel());
			MED_LAB_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().medLabExcel());
			LAB_NOM_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().labNomProdutoExcel());
			MED_DES_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().medDesExcel());
			MED_APR_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().medAprExcel());
			MED_PLA1_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().medPla1Excel());
			MED_PCO1_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().medPco1Excel());
			MED_FRA1_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().medFra1ProdutoExcel());
			MED_UNI_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().medUniExcel());
			MED_IPI_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().medIpiExcel());
			MED_DT_VIG_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().medDtVigExcel());
			EXP_13_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().exp13Excel());
			MED_BARRA_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().medBarraExcel());
			MED_GENE_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().medGeneExcel());
			MED_NEG_POS_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().medNegPosExcel());
			MED_PRINCI_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().medPrinciExcel());
			MED_PLA0_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().medPla0Excel());
			MED_PCO0_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().medPco0Excel());
			MED_FRA0_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().medFra0Excel());
			MED_REGIMS_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().medRegimsExcel());
			MED_VAR_PRE_COLUMN_NAME = HtmlUtils.htmlUnescape(this.getI18nConstants().medVarPreExcel());
		}
	}
}
