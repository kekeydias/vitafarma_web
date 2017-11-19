package br.com.vitafarma.web.server.excel.imp;

import java.util.ArrayList;
import java.util.List;

public class ProdutosImportExcelBean extends AbstractImportExcelBean implements Comparable<ProdutosImportExcelBean> {
	private String codigoProdutoStr = null;
	private String codigoAbcFarmaStr = null;
	private String nomeProdutoStr = null;
	private String descricaoProdutoStr = null;
	private String precoVendaProdutoStr = null;
	private String estoqueProdutoStr = null;
	private String unidadeEntradaProdutoStr = null;
	private String unidadeVendaProdutoStr = null;

	private Long codigoProduto = null;
	private Long codigoAbcFarma = null;
	private Double precoVendaProduto = null;
	private Double estoqueProduto = null;

	public ProdutosImportExcelBean(int row) {
		super(row);
	}

	public List<ImportExcelError> checkSyntacticErrors() {
		List<ImportExcelError> erros = new ArrayList<ImportExcelError>();

		if (!this.tudoVazio()) {
			this.codigoProduto = this.checkNonNegativeDoubleField(this.codigoProdutoStr,
					ImportExcelError.PRODUTO_CODIGO_VAZIO, ImportExcelError.PRODUTO_CODIGO_FORMATO_INVALIDO, erros)
					.longValue();

			this.checkMandatoryField(this.nomeProdutoStr, ImportExcelError.PRODUTO_NOME_VAZIO, erros);

			this.checkMandatoryField(this.unidadeEntradaProdutoStr, ImportExcelError.PRODUTO_UNIDADE_ENTRADA_VAZIO,
					erros);

			this.checkMandatoryField(this.unidadeVendaProdutoStr, ImportExcelError.PRODUTO_UNIDADE_VENDA_VAZIO, erros);

			this.checkMandatoryField(this.codigoAbcFarmaStr, ImportExcelError.PRODUTO_CODIGO_ABCFARMA_VAZIO, erros);

			this.codigoProduto = this
					.checkNonNegativeDoubleField(this.codigoProdutoStr, ImportExcelError.PRODUTO_CODIGO_ABCFARMA_VAZIO,
							ImportExcelError.PRODUTO_CODIGO_ABCFARMA_INVALIDO, erros)
					.longValue();

			this.checkMandatoryField(this.descricaoProdutoStr, ImportExcelError.PRODUTO_DESCRICAO_VAZIO, erros);

			this.precoVendaProduto = this.checkNonNegativeDoubleField(this.precoVendaProdutoStr,
					ImportExcelError.PRODUTO_PRECO_VENDA_VAZIO, ImportExcelError.PRODUTO_PRECO_VENDA_FORMATO_INVALIDO,
					erros);

			this.estoqueProduto = this.checkNonNegativeDoubleField(this.estoqueProdutoStr,
					ImportExcelError.PRODUTO_ESTOQUE_VAZIO, ImportExcelError.PRODUTO_ESTOQUE_FORMATO_INVALIDO, erros);
		} else {
			erros.add(ImportExcelError.TUDO_VAZIO);
		}

		return erros;
	}

	protected boolean tudoVazio() {
		return (this.isEmptyField(this.codigoProdutoStr) && this.isEmptyField(this.codigoAbcFarmaStr)
				&& this.isEmptyField(this.nomeProdutoStr) && this.isEmptyField(this.descricaoProdutoStr)
				&& this.isEmptyField(this.precoVendaProdutoStr) && this.isEmptyField(this.estoqueProdutoStr));
	}

	public String getCodigoProdutoStr() {
		return this.codigoProdutoStr;
	}

	public void setCodigoProdutoStr(String codigoProdutoStr) {
		this.codigoProdutoStr = codigoProdutoStr;
	}

	public String getNomeProdutoStr() {
		return this.nomeProdutoStr;
	}

	public void setNomeProdutoStr(String nomeProdutoStr) {
		this.nomeProdutoStr = nomeProdutoStr;
	}

	public String getDescricaoProdutoStr() {
		return this.descricaoProdutoStr;
	}

	public void setDescricaoProdutoStr(String descricaoProdutoStr) {
		this.descricaoProdutoStr = descricaoProdutoStr;
	}

	public String getPrecoVendaProdutoStr() {
		return this.precoVendaProdutoStr;
	}

	public void setPrecoVendaProdutoStr(String precoVendaProdutoStr) {
		this.precoVendaProdutoStr = precoVendaProdutoStr;
	}

	public String getEstoqueProdutoStr() {
		return this.estoqueProdutoStr;
	}

	public void setEstoqueProdutoStr(String estoqueProdutoStr) {
		this.estoqueProdutoStr = estoqueProdutoStr;
	}

	public Long getCodigoProduto() {
		return this.codigoProduto;
	}

	public void setCodigoProduto(Long codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public Double getPrecoVendaProduto() {
		return this.precoVendaProduto;
	}

	public void setPrecoVendaProduto(Double precoVendaProduto) {
		this.precoVendaProduto = precoVendaProduto;
	}

	public Double getEstoqueProduto() {
		return this.estoqueProduto;
	}

	public void setEstoqueProduto(Double estoqueProduto) {
		this.estoqueProduto = estoqueProduto;
	}

	public String getCodigoAbcFarmaStr() {
		return this.codigoAbcFarmaStr;
	}

	public void setCodigoAbcFarmaStr(String codigoAbcFarmaStr) {
		this.codigoAbcFarmaStr = codigoAbcFarmaStr;
	}

	public Long getCodigoAbcFarma() {
		return this.codigoAbcFarma;
	}

	public void setCodigoAbcFarma(Long codigoAbcFarma) {
		this.codigoAbcFarma = codigoAbcFarma;
	}

	public String getUnidadeEntradaProdutoStr() {
		return this.unidadeEntradaProdutoStr;
	}

	public void setUnidadeEntradaProdutoStr(String unidadeEntradaProdutoStr) {
		this.unidadeEntradaProdutoStr = unidadeEntradaProdutoStr;
	}

	public String getUnidadeVendaProdutoStr() {
		return this.unidadeVendaProdutoStr;
	}

	public void setUnidadeVendaProdutoStr(String unidadeVendaProdutoStr) {
		this.unidadeVendaProdutoStr = unidadeVendaProdutoStr;
	}

	@Override
	public int compareTo(ProdutosImportExcelBean o) {
		return this.getCodigoProdutoStr().compareTo(o.getCodigoProdutoStr());
	}
}
