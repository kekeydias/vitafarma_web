package br.com.vitafarma.web.server.excel.exp;

import java.util.Date;

import br.com.vitafarma.domain.Cenario;
import br.com.vitafarma.domain.EntradaProdutoGroup;
import br.com.vitafarma.domain.Venda;

public class ExportExcelParameters {
	private Cenario cenario;
	private Venda venda;
	private String codigoNotaFiscal;
	private String nomeFornecedor;
	private Date dataEntrada;
	private Boolean gerarNovoCupomVenda;

	// Filtros de produtos
	private Long codigoProduto;
	private Long medAbc;
	private String nomeProduto;
	private String nomeLaboratorio;
	private Double precoProduto;

	// Relatório de balanço do período
	private Long codigoCliente = null;
	private Long codigoFornecedor = null;
	private Date dataInicio = null;
	private Date dataFim = null;

	// Entrada de Produtos
	private EntradaProdutoGroup groupEntrada = null;

	public ExportExcelParameters() {
		super();
	}

	public Cenario getCenario() {
		return this.cenario;
	}

	public void setCenario(Cenario cenario) {
		this.cenario = cenario;
	}

	public Venda getVenda() {
		return this.venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public String getNomeFornecedor() {
		return this.nomeFornecedor;
	}

	public void setNomeFornecedor(String nomeFornecedor) {
		this.nomeFornecedor = nomeFornecedor;
	}

	public Date getDataEntrada() {
		return this.dataEntrada;
	}

	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public String getNomeProduto() {
		return this.nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public Boolean getGerarNovoCupomVenda() {
		return this.gerarNovoCupomVenda;
	}

	public void setGerarNovoCupomVenda(Boolean gerarNovoCupomVenda) {
		this.gerarNovoCupomVenda = gerarNovoCupomVenda;
	}

	public String getCodigoNotaFiscal() {
		return this.codigoNotaFiscal;
	}

	public void setCodigoNotaFiscal(String codigoNotaFiscal) {
		this.codigoNotaFiscal = codigoNotaFiscal;
	}

	public Long getCodigoProduto() {
		return this.codigoProduto;
	}

	public void setCodigoProduto(Long codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public Double getPrecoProduto() {
		return this.precoProduto;
	}

	public void setPrecoProduto(Double precoProduto) {
		this.precoProduto = precoProduto;
	}

	public Long getCodigoCliente() {
		return this.codigoCliente;
	}

	public void setCodigoCliente(Long codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public Long getCodigoFornecedor() {
		return this.codigoFornecedor;
	}

	public void setCodigoFornecedor(Long codigoFornecedor) {
		this.codigoFornecedor = codigoFornecedor;
	}

	public Date getDataInicio() {
		return this.dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return this.dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getNomeLaboratorio() {
		return this.nomeLaboratorio;
	}

	public void setNomeLaboratorio(String nomeLaboratorio) {
		this.nomeLaboratorio = nomeLaboratorio;
	}

	public Long getMedAbc() {
		return this.medAbc;
	}

	public void setMedAbc(Long medAbc) {
		this.medAbc = medAbc;
	}

	public EntradaProdutoGroup getGroupEntrada() {
		return this.groupEntrada;
	}

	public void setGroupEntrada(EntradaProdutoGroup groupEntrada) {
		this.groupEntrada = groupEntrada;
	}
}
