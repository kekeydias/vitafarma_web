package br.com.vitafarma.web.shared.excel;

public enum ExcelInformationType {
	TUDO(""), //
	FORNECEDORES("Fornecedores"), //
	PRODUTOS("Produtos"), //
	ENTRADA_PRODUTOS_GROUP("EntradaProdutos"), //
	ENTRADA_PRODUTOS_ITENS("ItensEntradaProdutos"), //
	FUNCIONARIOS("Funcionarios"), //
	VENDEDORES("Vendedores"), //
	CLIENTES("Clientes"), //
	USUARIOS("Usuarios"), //
	CENARIOS("Cenarios"), //
	PESSOAS("Pessoas"), //
	VENDAS("Vendas"), //
	ITENS_VENDA("ItensVenda"), //
	CUPOM_FISCAL("CupomFiscal"), //
	ESTADOS("Estados"), //
	CIDADES("Cidades"), //
	RELATORIO_BALANCO_PERIODO("RelatorioBalancoPeriodo"), //
	PRODUTOS_ABCFARMA("ProdutosAbsFarma"), //
	LABORATORIOS("Laboratorios"), //
	PALETA_CORES("PaletaCores"); //

	private String sheetName = null;

	ExcelInformationType(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getSheetName() {
		return this.sheetName;
	}

	public static String getInformationParameterName() {
		return ("excelInformationType");
	}

	public static String getCreateNewEntities() {
		return ("createNewEntities");
	}

	public static String getFileParameterName() {
		return ("uploadedFile");
	}

	public static String prefixError() {
		return ("@e@");
	}

	public static String prefixWarning() {
		return ("@w@");
	}
}
