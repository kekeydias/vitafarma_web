package br.com.vitafarma.web.server.excel.exp;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import br.com.vitafarma.domain.CupomVenda;
import br.com.vitafarma.domain.ItemVenda;
import br.com.vitafarma.domain.Venda;
import br.com.vitafarma.web.server.util.CupomFiscalData;
import br.com.vitafarma.web.shared.excel.ExcelInformationType;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nConstants;
import br.com.vitafarma.web.shared.i18n.VitafarmaI18nMessages;

public class CupomFiscalExportExcel extends AbstractExportExcel {
	enum ExcelCellStyleReference {
		TITLE(6, 2), TEXT(6, 2), CURRENCY(6, 7);

		private int row;
		private int col;

		private ExcelCellStyleReference(int row, int col) {
			this.row = row;
			this.col = col;
		}

		public int getRow() {
			return this.row;
		}

		public int getCol() {
			return this.col;
		}
	}

	private HSSFCellStyle[] cellStyles;
	private boolean removeUnusedSheets;

	public CupomFiscalExportExcel(ExportExcelParameters parametros, VitafarmaI18nConstants i18nConstants,
			VitafarmaI18nMessages i18nMessages) {
		this(true, parametros, i18nConstants, i18nMessages);
	}

	public CupomFiscalExportExcel(boolean removeUnusedSheets, ExportExcelParameters parametros,
			VitafarmaI18nConstants i18nConstants, VitafarmaI18nMessages i18nMessages) {
		super(true, ExcelInformationType.CUPOM_FISCAL.getSheetName(), parametros, i18nConstants, i18nMessages);

		this.cellStyles = new HSSFCellStyle[ExcelCellStyleReference.values().length];
		this.removeUnusedSheets = removeUnusedSheets;
	}

	@Override
	public String getFileName() {
		return this.getI18nConstants().cupomFiscal();
	}

	@Override
	protected String getReportName() {
		return this.getI18nConstants().cupomFiscal();
	}

	@Override
	protected boolean fillInExcel(HSSFWorkbook workbook) throws NoSuchAlgorithmException {
		Logger logger = Logger.getLogger("CupomFiscalExportExcel");

		CupomVenda cupomVenda = null;
		Venda venda = this.getParametros().getVenda();
		Boolean novoCupom = this.getParametros().getGerarNovoCupomVenda();

		try {
			if (novoCupom) {
				cupomVenda = new CupomVenda(null, venda.getDesconto(), venda.getValorTotalVenda(),
						venda.getCenario().getVersaoSistema(), venda);
			} else {
				cupomVenda = CupomVenda.findByVenda(venda);
			}
		} catch (Exception ex) {
			logger.warning("Erro ao carregar um objeto cupomVenda: " + ex.getCause() + " - " + ex.getMessage());

			return false;
		}

		if (cupomVenda != null) {
			if (this.removeUnusedSheets) {
				this.removeUnusedSheets(this.getSheetName(), workbook);
			}

			try {
				if (novoCupom) {
					cupomVenda.setId(null);
				}

				cupomVenda.save();

				cupomVenda.updateItensVenda();
			} catch (Exception ex) {
				logger.warning("Erro ao salvar um objeto cupomVenda: " + ex.getCause() + " - " + ex.getMessage());

				return false;
			}

			HSSFSheet sheet = workbook.getSheet(this.getSheetName());
			this.fillInCellStyles(sheet);
			this.writeData(cupomVenda, sheet);

			return true;
		}

		return false;
	}

	private void writeData(CupomVenda cupomVenda, HSSFSheet sheet) throws NoSuchAlgorithmException {
		Venda venda = cupomVenda.getVenda();
		List<ItemVenda> itensVenda = ItemVenda.findByVenda(cupomVenda.getVenda());

		String nomeEmpresa = CupomFiscalData.montaLinhaTituloCupom();
		String endereco1 = CupomFiscalData.montaPrimeiraLinhaEndereco();
		String endereco2 = CupomFiscalData.montaSegundaLinhaEndereco();
		String cnpjEmpresa = CupomFiscalData.montaCnpjEmpresa();
		String inscEstadual = CupomFiscalData.montaInscricaoEstadual();
		String inscMunicipal = CupomFiscalData.montaInscricaoMunicipal();
		String dataHoraCodigoCupom = CupomFiscalData.montaDataHoraCodigoCupom(cupomVenda);
		String cpfCnpjCliente = CupomFiscalData.montaCpfCnpjCliente(venda.getCliente());
		String nomeCliente = CupomFiscalData.montaNomeCliente(venda);
		String enderecoCliente = CupomFiscalData.montaEnderecoCliente(venda);
		String subtotalVenda = CupomFiscalData.montaSubtotalVenda(itensVenda);
		String descontoVenda = CupomFiscalData.montaDescontoVenda(venda, itensVenda);
		String totalVenda = CupomFiscalData.montaTotalVenda(venda, itensVenda);
		String versaoCaixaLojaOperador = CupomFiscalData.montaVersaoCaixaLojaOperador();

		int row = 1;
		final int column = 1;

		// Nome da Emrpesa
		this.setCell(row, column, sheet, nomeEmpresa);
		this.setCellFontBoldCentered(row, column, sheet);

		// Endereço da Emrpesa - Primeira Linha
		row++;
		this.setCell(row, column, sheet, endereco1);
		this.setCellAlignment(row, column, sheet, HSSFCellStyle.ALIGN_CENTER);

		// Endereço da Emrpesa - Segunda Linha
		row++;
		this.setCell(row, column, sheet, endereco2);
		this.setCellAlignment(row, column, sheet, HSSFCellStyle.ALIGN_CENTER);

		// CNPJ da Empresa
		row++;
		this.setCell(row, column, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()], cnpjEmpresa);

		// Inscrição Estadual
		row++;
		this.setCell(row, column, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()], inscEstadual);

		// Inscrição Municipal
		row++;
		this.setCell(row, column, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()], inscMunicipal);

		// Data/Hora, Código do Cupom, COO
		row += 2;
		this.setCell(row, column, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()], dataHoraCodigoCupom);

		// Cpf/Cnpj do Cliente
		row++;
		this.setCell(row, column, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()], cpfCnpjCliente);

		// Nome do Cliente
		row++;
		this.setCell(row, column, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()], nomeCliente);

		// Endereço do Cliente
		row++;
		this.setCell(row, column, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()], enderecoCliente);

		// Enviando os itens da venda para o cupom fiscal
		row = 17;
		for (int i = 0; i < itensVenda.size(); i++) {
			ItemVenda itemVenda = itensVenda.get(i);

			String linhaItemCodigoDescricao = CupomFiscalData.montaItemCodigoDescricao(i + 1, itemVenda);
			String linhaQtdUnVlunitVlitem = CupomFiscalData.montaQtdUnValorUnitarioValorItem(i + 1, itemVenda);

			// ITEM - CÓDIGO - DESCRIÇÃO
			this.setCell(row, column, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
					linhaItemCodigoDescricao);

			row++;

			// QTD - UN. - VL. UNIT. (R$) - VL. ITEM (R$)
			this.setCell(row, column, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
					linhaQtdUnVlunitVlitem);

			row++;
		}

		// Linha em branco (entre o último item da venda e a linha do subtotal)
		this.setCell(row, column, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()], "");

		// Subtotal da Venda
		row++;
		this.setCell(row, column, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()], subtotalVenda);
		this.setCellFontBold(row, column, sheet);

		// Desconto da Venda
		row++;
		this.setCell(row, column, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()], descontoVenda);

		String separator = "------------------------------------------------------------------------------------------";

		// Adiciona separador de seção
		row++;
		this.setCell(row, column, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()], separator);

		// Total da Venda
		row++;
		this.setCell(row, column, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()], totalVenda);
		this.setCellFontBold(row, column, sheet);

		// Adiciona separador de seção
		row++;
		this.setCell(row, column, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()], separator);

		// Versão do Sistema, Caixa, Loja e Operador
		row++;
		this.setCell(row, column, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()],
				versaoCaixaLojaOperador);

		// Adiciona separador de seção
		row++;
		this.setCell(row, column, sheet, this.cellStyles[ExcelCellStyleReference.TEXT.ordinal()], separator);
	}

	private void fillInCellStyles(HSSFSheet sheet) {
		for (ExcelCellStyleReference cellStyleReference : ExcelCellStyleReference.values()) {
			this.cellStyles[cellStyleReference.ordinal()] = getCell(cellStyleReference.getRow(),
					cellStyleReference.getCol(), sheet).getCellStyle();
		}
	}
}
