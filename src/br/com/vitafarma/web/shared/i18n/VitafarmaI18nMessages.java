package br.com.vitafarma.web.shared.i18n;

public class VitafarmaI18nMessages {
	private static ErrorMessages errorMessages;

	public VitafarmaI18nMessages() {
		super();
	}

	private static ErrorMessages getErrorMessages() {
		if (VitafarmaI18nMessages.errorMessages == null) {
			VitafarmaI18nMessages.errorMessages = new ErrorMessages();
		}

		return VitafarmaI18nMessages.errorMessages;
	}

	public String falhaOperacao() {
		return "Erro";
	}

	public String confirmacaoRemocaoButton() {
		return "Deseja realmente remover o item?";
	}

	public String loading() {
		return "Carregando";
	}

	public String excelErroObterExcelTemplate(String pathExcelTemplate, String reportName, String msg) {
		return "Erro ao exportar o documento " + reportName + " : " + msg;
	}

	public String sucessoImportacaoExcel() {
		return "Importacao realizada com sucesso!";
	}

	public String sucessoExportacaoExcel() {
		return "Exportacao realizada com sucesso!";
	}

	public String sucessoOperacaoExcel() {
		return "Operacao realizada com sucesso!";
	}

	public String erroSalvarVendaProduto() {
		return "Erro ao salvar um item na venda.";
	}

	public String erroSalvarVenda() {
		return "Erro ao salvar a venda.";
	}

	public String mensagemErro() {
		return "Mensagem de Erro";
	}

	public String confirmacao() {
		return "Confirmar?";
	}

	public String excelErroBD(String nomeArquivo, String motivo) {
		return ("Erro ao processar o arquivo " + nomeArquivo + " : " + motivo);
	}

	public String excelErroSintaticoLinhasInvalidas(String linhasComErro, String nomeArquivo) {
		return ("Erro ao processar o arquivo " + nomeArquivo + " nas linhas " + linhasComErro);
	}

	public String excelErroSintaticoCabecalhoAusente(String cabecalho, String nomeArquivo) {
		return ("Erro no cabecalho do arquivo " + nomeArquivo + " : " + cabecalho);
	}

	public String excelErroArquivoInvalido(String nomeArquivo, String motivo) {
		return ("Arquivo " + nomeArquivo + " invalido : " + motivo);
	}

	public String excelErroLogicoUnicidadeViolada(String valorRepetido, String linhasComErro) {
		return ("Existem itens repetidos, " + valorRepetido + ", nas linhas " + linhasComErro);
	}

	public String excelErroSintaticoColunaVazia(String linhasComErro, String nomeColuna) {
		return ("A coluna " + nomeColuna + " esta vazia nas linhas " + linhasComErro);
	}

	public String excelErroDocumentoVazio() {
		return ("Documento vazio.");
	}

	public String excelErroSintaticoFormatoInvalido(String linhasComErro, String nomeColuna) {
		return ("A coluna " + nomeColuna + " possui valores invalidos nas linhas " + linhasComErro);
	}

	public String excelErroImportadorNulo(String infoASerImportada) {
		return ("Erro ao importar os daods de " + infoASerImportada + " - importador nulo.");
	}

	public String importExcelHeading() {
		return "Importacao de Excel";
	}

	public String arquivo() {
		return "Arquivo";
	}

	public String fixedSizeTextFieldMessage(int size) {
		return "Esse campo deve conter exatamente " + size + " digitos";
	}

	public String erroCamposObrigatorios(String... errors) {
		if (errors.length == 0) {
			return "";
		}

		String response = "Os seguintes campos sao obrigatorios: ";
		response += errors[0];
		for (int i = 1; i < errors.length; i++) {
			response += (", " + errors[i]);
		}
		return response;
	}

	public String errorDuplicatedNotaFiscal() {
		return VitafarmaI18nMessages.getErrorMessages().getNotaFiscalAlreadyExistis();
	}

	public String errorDuplicatedCpfCliente() {
		return VitafarmaI18nMessages.getErrorMessages().getCpfClienteAlreadyExistis();
	}

	public String errorDuplicatedCpfFuncionario() {
		return VitafarmaI18nMessages.getErrorMessages().getCpfFuncionarioAlreadyExistis();
	}

	public String errorDuplicatedCpfVendedor() {
		return VitafarmaI18nMessages.getErrorMessages().getCpfVendedorAlreadyExistis();
	}

	public String errorDuplicatedCnpjFornecedor() {
		return VitafarmaI18nMessages.getErrorMessages().getCnpjFornecedorAlreadyExistis();
	}

	public String errorDuplicatedMedAbcfarmaProduto() {
		return VitafarmaI18nMessages.getErrorMessages().getMedAbcfarmaProdutoAlreadyExistis();
	}

	public String errorDuplicatedMedLabLaboratorio() {
		return VitafarmaI18nMessages.getErrorMessages().getMedLabLaboratorioAlreadyExistis();
	}

	public String errorDuplicatedCnpjLaboratorio() {
		return VitafarmaI18nMessages.getErrorMessages().getCnpjLaboratorioAlreadyExistis();
	}

	public String errorDuplicatedCidade() {
		return VitafarmaI18nMessages.getErrorMessages().getCidadeAlreadyExistis();
	}

	public String errorDuplicatedEstadoNome() {
		return VitafarmaI18nMessages.getErrorMessages().getEstadoNomeAlreadyExistis();
	}

	public String errorDuplicatedEstadoSigla() {
		return VitafarmaI18nMessages.getErrorMessages().getEstadoSiglaAlreadyExistis();
	}

	public String excelErroGenericoExportacao(String mensagemErro) {
		return ("Erro na geracao do relatorio: " + mensagemErro);
	}
}

final class ErrorMessages {
	private static final String NOTA_FISCAL_ALREADY_EXISTIS = "Nota fiscal ja existe";
	private static final String CPF_CLIENTE_ALREADY_EXISTIS = "Existe um cliente com este cpf";
	private static final String CPF_FUNCIONARIO_ALREADY_EXISTIS = "Existe um funcionario com este cpf";
	private static final String CPF_VENDEDOR_ALREADY_EXISTIS = "Existe um vendedor com este cpf";
	private static final String CNPJ_FORNECEDOR_ALREADY_EXISTIS = "Existe um fornecedor com este cnpj";
	private static final String MED_ABCFARMA_PRODUTO_ALREADY_EXISTIS = "Existe um produto com este codigo AbcFarma";
	private static final String CNPJ_LABORATORIO_ALREADY_EXISTIS = "Existe um laboratorio com este cnpj";
	private static final String MED_LAB_LABORATORIO_ALREADY_EXISTIS = "Existe um laboratorio com este codigo AbcFarma";
	private static final String CIDADE_ESTADO_ALREADY_EXISTIS = "Existe uma cidade com este nome no mesmo estado";
	private static final String ESTADO_NOME_ALREADY_EXISTIS = "Existe uma estado com este nome";
	private static final String ESTADO_SIGLA_ALREADY_EXISTIS = "Existe uma estado com esta sigla";

	public String getNotaFiscalAlreadyExistis() {
		return ErrorMessages.NOTA_FISCAL_ALREADY_EXISTIS;
	}

	public String getCpfClienteAlreadyExistis() {
		return ErrorMessages.CPF_CLIENTE_ALREADY_EXISTIS;
	}

	public String getCpfFuncionarioAlreadyExistis() {
		return ErrorMessages.CPF_FUNCIONARIO_ALREADY_EXISTIS;
	}

	public String getCpfVendedorAlreadyExistis() {
		return ErrorMessages.CPF_VENDEDOR_ALREADY_EXISTIS;
	}

	public String getCnpjFornecedorAlreadyExistis() {
		return ErrorMessages.CNPJ_FORNECEDOR_ALREADY_EXISTIS;
	}

	public String getMedAbcfarmaProdutoAlreadyExistis() {
		return ErrorMessages.MED_ABCFARMA_PRODUTO_ALREADY_EXISTIS;
	}

	public String getMedLabLaboratorioAlreadyExistis() {
		return ErrorMessages.MED_LAB_LABORATORIO_ALREADY_EXISTIS;
	}

	public String getCnpjLaboratorioAlreadyExistis() {
		return ErrorMessages.CNPJ_LABORATORIO_ALREADY_EXISTIS;
	}

	public String getCidadeAlreadyExistis() {
		return ErrorMessages.CIDADE_ESTADO_ALREADY_EXISTIS;
	}

	public String getEstadoNomeAlreadyExistis() {
		return ErrorMessages.ESTADO_NOME_ALREADY_EXISTIS;
	}

	public String getEstadoSiglaAlreadyExistis() {
		return ErrorMessages.ESTADO_SIGLA_ALREADY_EXISTIS;
	}
}
