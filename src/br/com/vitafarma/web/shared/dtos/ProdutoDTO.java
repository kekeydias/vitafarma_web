package br.com.vitafarma.web.shared.dtos;

public class ProdutoDTO extends AbstractDTO<String> implements
		Comparable<ProdutoDTO> {
	private static final long serialVersionUID = -5134820110949139907L;

	// Propriedades
	public static final String PROPERTY_PRODUTO_ID = "produtoId";
	public static final String PROPERTY_PRODUTO_MED_ABC_VALUE = "produtoMedAbcValue";
	public static final String PROPERTY_PRODUTO_MED_ABC_STRING = "produtoMedAbcString";
	public static final String PROPERTY_PRODUTO_NOME = "produtoNome";
	public static final String PROPERTY_PRODUTO_DESCRICAO = "produtoDescricao";
	public static final String PROPERTY_PRODUTO_PRECO_VALUE = "produtoPrecoValue";
	public static final String PROPERTY_PRODUTO_PRECO_STRING = "produtoPrecoString";
	public static final String PROPERTY_PRODUTO_ESTOQUE = "produtoEstoque";
	public static final String PROPERTY_PRODUTO_UNIDADE_ENTRADA = "produtoUnidadeEntrada";
	public static final String PROPERTY_PRODUTO_UNIDADE_VENDA = "produtoUnidadeVenda";
	public static final String PROPERTY_PRODUTO_LABORATORIO_ID = "produtoLabotatorioId";
	public static final String PROPERTY_PRODUTO_LABORATORIO_STRING = "produtoLabotatorioString";

	public ProdutoDTO() {
		super();
	}

	public void setId(Long value) {
		this.set(PROPERTY_PRODUTO_ID, value);
	}

	public Long getId() {
		return this.get(PROPERTY_PRODUTO_ID);
	}

	public void setPrecoValue(Double value) {
		this.set(PROPERTY_PRODUTO_PRECO_VALUE, value);
	}

	public Double getPrecoValue() {
		return this.get(PROPERTY_PRODUTO_PRECO_VALUE);
	}

	public void setPrecoString(String value) {
		this.set(PROPERTY_PRODUTO_PRECO_STRING, value);
	}

	public String getPrecoString() {
		return this.get(PROPERTY_PRODUTO_PRECO_STRING);
	}

	public void setNome(String value) {
		this.set(PROPERTY_PRODUTO_NOME, value);
	}

	public String getNome() {
		return this.get(PROPERTY_PRODUTO_NOME);
	}

	public void setDescricao(String value) {
		this.set(PROPERTY_PRODUTO_DESCRICAO, value);
	}

	public String getDescricao() {
		return this.get(PROPERTY_PRODUTO_DESCRICAO);
	}

	public void setEstoque(Double value) {
		this.set(PROPERTY_PRODUTO_ESTOQUE, value);
	}

	public Double getEstoque() {
		return this.get(PROPERTY_PRODUTO_ESTOQUE);
	}

	public void setUnidadeEntrada(String value) {
		this.set(PROPERTY_PRODUTO_UNIDADE_ENTRADA, value);
	}

	public String getUnidadeEntrada() {
		return this.get(PROPERTY_PRODUTO_UNIDADE_ENTRADA);
	}

	public void setUnidadeVenda(String value) {
		this.set(PROPERTY_PRODUTO_UNIDADE_VENDA, value);
	}

	public String getUnidadeVenda() {
		return this.get(PROPERTY_PRODUTO_UNIDADE_VENDA);
	}

	public void setLaboratorioId(Long value) {
		this.set(PROPERTY_PRODUTO_LABORATORIO_ID, value);
	}

	public Long getLaboratorioId() {
		return this.get(PROPERTY_PRODUTO_LABORATORIO_ID);
	}

	public void setLaboratorioString(String value) {
		this.set(PROPERTY_PRODUTO_LABORATORIO_STRING, value);
	}

	public String getLaboratorioString() {
		return this.get(PROPERTY_PRODUTO_LABORATORIO_STRING);
	}

	public void setMedAbcValue(Long value) {
		this.set(PROPERTY_PRODUTO_MED_ABC_VALUE, value);
	}

	public Long getMedAbcValue() {
		return this.get(PROPERTY_PRODUTO_MED_ABC_VALUE);
	}

	public void setMedAbcString(String value) {
		this.set(PROPERTY_PRODUTO_MED_ABC_STRING, value);
	}

	public String getMedAbcString() {
		return this.get(PROPERTY_PRODUTO_MED_ABC_STRING);
	}

	@Override
	public String getNaturalKey() {
		return (this.getId() + this.getNome());
	}

	@Override
	public int compareTo(ProdutoDTO o) {
		int result = this.getNome().compareTo(o.getNome());
		return result;
	}
}
