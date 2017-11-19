package br.com.vitafarma.web.shared.i18n;

public class VitafarmaI18nConstants {
	private static ImportExcelAbcFarma abcFarma;

	public VitafarmaI18nConstants() {
		super();
	}

	private static ImportExcelAbcFarma getAbcFarma() {
		if (VitafarmaI18nConstants.abcFarma == null) {
			VitafarmaI18nConstants.abcFarma = new ImportExcelAbcFarma();
		}

		return VitafarmaI18nConstants.abcFarma;
	}

	public String cpf() {
		return "Cpf";
	}

	public String cnpj() {
		return "Cnpj";
	}

	public String cpfCliente() {
		return "Cpf do Cliente";
	}

	public String matricula() {
		return "Matricula";
	}

	public String nome() {
		return "Nome";
	}

	public String nomeProdutoExcel() {
		return "Nome";
	}

	public String nomeFantasia() {
		return "Nome Fantasia";
	}

	public String medLab() {
		return "Codigo do Laboratorio";
	}

	public String nomeCliente() {
		return "Nome do Cliente";
	}

	public String nomeFornecedor() {
		return "Nome do Fornecedor";
	}

	public String vitafarmaVersion() {
		return "Versao";
	}

	public String vitafarma() {
		return "Vitafarma";
	}

	public String vitafarmaDetailMessageHeadingText() {
		return "Detalhes";
	}

	public String adicionar() {
		return "Adicionar";
	}

	public String editar() {
		return "Editar";
	}

	public String importarExcel() {
		return "Importar";
	}

	public String exportarExcel() {
		return "Exportar";
	}

	public String remover() {
		return "Remover";
	}

	public String fornecedores() {
		return "Fornecedores";
	}

	public String fornecedor() {
		return "Fornecedor";
	}

	public String fornecedoresHeadingPanel() {
		return "Fornecedores";
	}

	public String laboratoriosHeadingPanel() {
		return "Laboratorios";
	}

	public String clientes() {
		return "Clientes";
	}

	public String cliente() {
		return "Cliente";
	}

	public String clientesHeadingPanel() {
		return "Clientes";
	}

	public String vendasHeadingPanel() {
		return "Vendas";
	}

	public String funcionariosHeadingPanel() {
		return "Funcionarios";
	}

	public String vendedoresHeadingPanel() {
		return "Vendedores";
	}

	public String salario() {
		return "Salario";
	}

	public String sigla() {
		return "Sigla";
	}

	public String dataAdimissao() {
		return "Data de Adimissao";
	}

	public String dataEntrada() {
		return "Data de Entrada";
	}

	public String horarioEntrada() {
		return "Horario da Entrada";
	}

	public String notaFiscal() {
		return "Nota Fiscal";
	}

	public String dataDemissao() {
		return "Data de Demissao";
	}

	public String funcionarios() {
		return "Funcionarios";
	}

	public String vendedores() {
		return "Vendedores";
	}

	public String vendedor() {
		return "Vendedor";
	}

	public String produtos() {
		return "Produtos";
	}

	public String estados() {
		return "Estados";
	}

	public String estado() {
		return "Estado";
	}

	public String cidades() {
		return "Cidades";
	}

	public String cidade() {
		return "Cidade";
	}

	public String laboratorio() {
		return "Laboratorio";
	}

	public String laboratorios() {
		return "Laboratorios";
	}

	public String produto() {
		return "Produto";
	}

	public String entradaProdutos() {
		return "Entrada de Produtos";
	}

	public String produtosHeadingPanel() {
		return "Produtos";
	}

	public String estadosHeadingPanel() {
		return "Estados";
	}

	public String cidadesHeadingPanel() {
		return "Cidades";
	}

	public String entradaProdutosGroupHeadingPanel() {
		return "Entrada de Produtos";
	}

	public String entradaProdutosItensHeadingPanel() {
		return "Itens da Entrada";
	}

	public String preco() {
		return "Preco";
	}

	public String precoVenda() {
		return "Preco de Venda (R$)";
	}

	public String precoVendaProdutoExcel() {
		return "Preco de Venda";
	}

	public String precoUnitario() {
		return "Preco Unitario";
	}

	public String precoFinal() {
		return "Preco Final";
	}

	public String valorFinal() {
		return "Valor Final (R$)";
	}

	public String totalEntradaItem() {
		return "Total (R$)";
	}

	public String totalEntradaGroup() {
		return "Total (R$)";
	}

	public String preenchaPreco() {
		return "Preencha o Preco";
	}

	public String informacao() {
		return "Informacao";
	}

	public String vendas() {
		return "Vendas";
	}

	public String venda() {
		return "Venda";
	}

	public String itensVenda() {
		return "ItensVenda";
	}

	public String itensVendaHeadingPanel() {
		return "Itens da Venda";
	}

	public String itensEntradaHeadingPanel() {
		return "Itens da Entrada";
	}

	public String dataVenda() {
		return "Data da Venda";
	}

	public String horarioVenda() {
		return "Horario da Venda";
	}

	public String dataInicioPeriodo() {
		return "Inicio do Periodo";
	}

	public String dataFimPeriodo() {
		return "Fim do Periodo";
	}

	public String subtotal() {
		return "Subtotal";
	}

	public String incluirItensVenda() {
		return "Incluir Itens";
	}

	public String incluirItensEntrada() {
		return "Incluir Itens";
	}

	public String informacoesGerais() {
		return "Informacoes Gerais";
	}

	public String descricaoItem() {
		return "Descricao do Item";
	}

	public String itens() {
		return "Itens";
	}

	public String item() {
		return "Item";
	}

	public String descricao() {
		return "Descricao";
	}

	public String descricaoProdutoExcel() {
		return "Descricao";
	}

	public String desconto() {
		return "Desconto (%)";
	}

	public String descontoItem() {
		return "Desconto no Item (%)";
	}

	public String codigoProduto() {
		return "Codigo do Produto";
	}

	public String codigoProdutoAbcFarma() {
		return "Codigo AbcFarma";
	}

	public String codigoProdutoExcel() {
		return "Codigo do Produto";
	}

	public String codigoAbcFarmaExcel() {
		return "Codigo AbcFarma";
	}

	public String codigoCidade() {
		return "Codigo da Cidade";
	}

	public String codigoEstado() {
		return "Codigo do Estado";
	}

	public String codigoCliente() {
		return "Codigo do Cliente";
	}

	public String codigoVenda() {
		return "Codigo da Venda";
	}

	public String operacao() {
		return "Operacao";
	}

	public String data() {
		return "Data";
	}

	public String codigoEntradaProduto() {
		return "Codigo da Entrada";
	}

	public String codigoFornecedor() {
		return "Codigo do Fornecedor";
	}

	public String codigoLaboratorio() {
		return "Codigo do Laboratorio";
	}

	public String codigoFuncionario() {
		return "Codigo do Funcionario";
	}

	public String codigoVendedor() {
		return "Codigo do Vendedor";
	}

	public String estoqueProduto() {
		return "Estoque";
	}

	public String estoqueProdutoExcel() {
		return "Estoque";
	}

	public String entradaProdutoGroupExcel() {
		return "EntradaProdutos";
	}

	public String entradaProdutoItensExcel() {
		return "ItensEntradaProdutos";
	}

	public String quantidade() {
		return "Quanditade";
	}

	public String telefone() {
		return "Telefone";
	}

	public String endereco() {
		return "Endereco";
	}

	public String email() {
		return "Email";
	}

	public String unidadeEntrada() {
		return "UN (Entrada)";
	}

	public String unidadeVenda() {
		return "UN (Venda)";
	}

	public String emitirCupomFiscalVenda() {
		return "Emitir Cupom Fiscal";
	}

	public String atualizarEstoque() {
		return "Atualizar Estoque";
	}

	public String importExcelAbcfarma() {
		return "Tabela ABCFARMA";
	}

	public String atualizarTodoEstoque() {
		return "Atualizar todo o estoque";
	}

	public String criarNovosLaboratorios() {
		return "Criar Novos Laboratorios ?";
	}

	public String adicionarItensNotaFiscal() {
		return "Emitir Cupom Fiscal";
	}

	public String cupomFiscal() {
		return "Cupom Fiscal";
	}

	public String relatorioBalancoPeriodoExcel() {
		return "RelatorioBalancoPeriodo";
	}

	public String medAbcExcel() {
		return VitafarmaI18nConstants.getAbcFarma().getMED_ABC_COLUMN_NAME();
	}

	public String medCtrExcel() {
		return VitafarmaI18nConstants.getAbcFarma().getMED_CTR_COLUMN_NAME();
	}

	public String medLabExcel() {
		return VitafarmaI18nConstants.getAbcFarma().getMED_LAB_COLUMN_NAME();
	}

	public String labNomProdutoExcel() {
		return VitafarmaI18nConstants.getAbcFarma().getLAB_NOM_COLUMN_NAME();
	}

	public String medDesExcel() {
		return VitafarmaI18nConstants.getAbcFarma().getMED_DES_COLUMN_NAME();
	}

	public String medAprExcel() {
		return VitafarmaI18nConstants.getAbcFarma().getMED_APR_COLUMN_NAME();
	}

	public String medPla1Excel() {
		return VitafarmaI18nConstants.getAbcFarma().getMED_PLA1_COLUMN_NAME();
	}

	public String medFra1ProdutoExcel() {
		return VitafarmaI18nConstants.getAbcFarma().getMED_FRA1_COLUMN_NAME();
	}

	public String medPco1Excel() {
		return VitafarmaI18nConstants.getAbcFarma().getMED_PCO1_COLUMN_NAME();
	}

	public String medUniExcel() {
		return VitafarmaI18nConstants.getAbcFarma().getMED_UNI_COLUMN_NAME();
	}

	public String medIpiExcel() {
		return VitafarmaI18nConstants.getAbcFarma().getMED_IPI_COLUMN_NAME();
	}

	public String medDtVigExcel() {
		return VitafarmaI18nConstants.getAbcFarma().getMED_DT_VIG_COLUMN_NAME();
	}

	public String exp13Excel() {
		return VitafarmaI18nConstants.getAbcFarma().getEXP_13_COLUMN_NAME();
	}

	public String medBarraExcel() {
		return VitafarmaI18nConstants.getAbcFarma().getMED_BARRA_COLUMN_NAME();
	}

	public String medGeneExcel() {
		return VitafarmaI18nConstants.getAbcFarma().getMED_GENE_COLUMN_NAME();
	}

	public String medNegPosExcel() {
		return VitafarmaI18nConstants.getAbcFarma()
				.getMED_NEG_POS_COLUMN_NAME();
	}

	public String medRegimsExcel() {
		return VitafarmaI18nConstants.getAbcFarma().getMED_REGIMS_COLUMN_NAME();
	}

	public String medFra0Excel() {
		return VitafarmaI18nConstants.getAbcFarma().getMED_FRA0_COLUMN_NAME();
	}

	public String medPco0Excel() {
		return VitafarmaI18nConstants.getAbcFarma().getMED_PCO0_COLUMN_NAME();
	}

	public String medPrinciExcel() {
		return VitafarmaI18nConstants.getAbcFarma().getMED_PRINCI_COLUMN_NAME();
	}

	public String medVarPreExcel() {
		return VitafarmaI18nConstants.getAbcFarma()
				.getMED_VAR_PRE_COLUMN_NAME();
	}

	public String medPla0Excel() {
		return VitafarmaI18nConstants.getAbcFarma().getMED_PLA0_COLUMN_NAME();
	}
}

final class ImportExcelAbcFarma {
	private static final String MED_ABC_COLUMN_NAME = "MED_ABC";
	private static final String MED_CTR_COLUMN_NAME = "MED_CTR";
	private static final String MED_LAB_COLUMN_NAME = "MED_LAB";
	private static final String LAB_NOM_COLUMN_NAME = "LAB_NOM";
	private static final String MED_DES_COLUMN_NAME = "MED_DES";
	private static final String MED_APR_COLUMN_NAME = "MED_APR";
	private static final String MED_PLA1_COLUMN_NAME = "MED_PLA1";
	private static final String MED_PCO1_COLUMN_NAME = "MED_PCO1";
	private static final String MED_FRA1_COLUMN_NAME = "MED_FRA1";
	private static final String MED_UNI_COLUMN_NAME = "MED_UNI";
	private static final String MED_IPI_COLUMN_NAME = "MED_IPI";
	private static final String MED_DT_VIG_COLUMN_NAME = "MED_DTVIG";
	private static final String EXP_13_COLUMN_NAME = "EXP_13";
	private static final String MED_BARRA_COLUMN_NAME = "MED_BARRA";
	private static final String MED_GENE_COLUMN_NAME = "MED_GENE";
	private static final String MED_NEG_POS_COLUMN_NAME = "MED_NEGPOS";
	private static final String MED_PRINCI_COLUMN_NAME = "MED_PRINCI";
	private static final String MED_PLA0_COLUMN_NAME = "MED_PLA0";
	private static final String MED_PCO0_COLUMN_NAME = "MED_PCO0";
	private static final String MED_FRA0_COLUMN_NAME = "MED_FRA0";
	private static final String MED_REGIMS_COLUMN_NAME = "MED_REGIMS";
	private static final String MED_VAR_PRE_COLUMN_NAME = "MED_VARPRE";

	public String getMED_ABC_COLUMN_NAME() {
		return ImportExcelAbcFarma.MED_ABC_COLUMN_NAME;
	}

	public String getMED_CTR_COLUMN_NAME() {
		return ImportExcelAbcFarma.MED_CTR_COLUMN_NAME;
	}

	public String getMED_LAB_COLUMN_NAME() {
		return ImportExcelAbcFarma.MED_LAB_COLUMN_NAME;
	}

	public String getLAB_NOM_COLUMN_NAME() {
		return ImportExcelAbcFarma.LAB_NOM_COLUMN_NAME;
	}

	public String getMED_DES_COLUMN_NAME() {
		return ImportExcelAbcFarma.MED_DES_COLUMN_NAME;
	}

	public String getMED_APR_COLUMN_NAME() {
		return ImportExcelAbcFarma.MED_APR_COLUMN_NAME;
	}

	public String getMED_PLA1_COLUMN_NAME() {
		return ImportExcelAbcFarma.MED_PLA1_COLUMN_NAME;
	}

	public String getMED_PCO1_COLUMN_NAME() {
		return ImportExcelAbcFarma.MED_PCO1_COLUMN_NAME;
	}

	public String getMED_FRA1_COLUMN_NAME() {
		return ImportExcelAbcFarma.MED_FRA1_COLUMN_NAME;
	}

	public String getMED_UNI_COLUMN_NAME() {
		return ImportExcelAbcFarma.MED_UNI_COLUMN_NAME;
	}

	public String getMED_IPI_COLUMN_NAME() {
		return ImportExcelAbcFarma.MED_IPI_COLUMN_NAME;
	}

	public String getMED_DT_VIG_COLUMN_NAME() {
		return ImportExcelAbcFarma.MED_DT_VIG_COLUMN_NAME;
	}

	public String getEXP_13_COLUMN_NAME() {
		return ImportExcelAbcFarma.EXP_13_COLUMN_NAME;
	}

	public String getMED_BARRA_COLUMN_NAME() {
		return ImportExcelAbcFarma.MED_BARRA_COLUMN_NAME;
	}

	public String getMED_GENE_COLUMN_NAME() {
		return ImportExcelAbcFarma.MED_GENE_COLUMN_NAME;
	}

	public String getMED_NEG_POS_COLUMN_NAME() {
		return ImportExcelAbcFarma.MED_NEG_POS_COLUMN_NAME;
	}

	public String getMED_PRINCI_COLUMN_NAME() {
		return ImportExcelAbcFarma.MED_PRINCI_COLUMN_NAME;
	}

	public String getMED_PLA0_COLUMN_NAME() {
		return ImportExcelAbcFarma.MED_PLA0_COLUMN_NAME;
	}

	public String getMED_PCO0_COLUMN_NAME() {
		return ImportExcelAbcFarma.MED_PCO0_COLUMN_NAME;
	}

	public String getMED_FRA0_COLUMN_NAME() {
		return ImportExcelAbcFarma.MED_FRA0_COLUMN_NAME;
	}

	public String getMED_REGIMS_COLUMN_NAME() {
		return ImportExcelAbcFarma.MED_REGIMS_COLUMN_NAME;
	}

	public String getMED_VAR_PRE_COLUMN_NAME() {
		return ImportExcelAbcFarma.MED_VAR_PRE_COLUMN_NAME;
	}
}