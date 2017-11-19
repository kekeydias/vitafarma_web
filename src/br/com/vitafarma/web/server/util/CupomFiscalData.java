package br.com.vitafarma.web.server.util;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import br.com.vitafarma.domain.Cenario;
import br.com.vitafarma.domain.CupomVenda;
import br.com.vitafarma.domain.ItemVenda;
import br.com.vitafarma.domain.Pessoa;
import br.com.vitafarma.domain.PessoaFisica;
import br.com.vitafarma.domain.PessoaJuridica;
import br.com.vitafarma.domain.Venda;
import br.com.vitafarma.web.shared.util.VitafarmaDate;
import br.com.vitafarma.web.shared.util.VitafarmaUtil;

public class CupomFiscalData {
	private static Cenario cenario = null;
	private static final int TAMANHO_LINHA = 40;

	private static Cenario getCenario() throws NoSuchAlgorithmException {
		if (CupomFiscalData.cenario == null) {
			CupomFiscalData.cenario = Cenario.getCurrentCenario();
		}

		return CupomFiscalData.cenario;
	}

	public final static String montaLinhaTituloCupom() throws NoSuchAlgorithmException {
		String nomeEmpresa = CupomFiscalData.getCenario().getNome();
		if (nomeEmpresa == null) {
			return "Company Name";
		}

		if (nomeEmpresa.length() > CupomFiscalData.TAMANHO_LINHA + 10) {
			return nomeEmpresa.substring(0, CupomFiscalData.TAMANHO_LINHA + 10);
		}

		return nomeEmpresa;
	}

	public final static String montaPrimeiraLinhaEndereco() throws NoSuchAlgorithmException {
		String endereco1 = CupomFiscalData.getCenario().getEndereco1();
		if (endereco1 == null) {
			return "---";
		}

		if (endereco1.length() > CupomFiscalData.TAMANHO_LINHA + 20) {
			return endereco1.substring(0, CupomFiscalData.TAMANHO_LINHA + 20);
		}

		return endereco1;
	}

	public final static String montaSegundaLinhaEndereco() throws NoSuchAlgorithmException {
		String endereco2 = CupomFiscalData.getCenario().getEndereco2();
		if (endereco2 == null) {
			return "---";
		}

		if (endereco2.length() > CupomFiscalData.TAMANHO_LINHA + 20) {
			return endereco2.substring(0, CupomFiscalData.TAMANHO_LINHA + 20);
		}

		return endereco2;
	}

	public final static String montaCnpjEmpresa() throws NoSuchAlgorithmException {
		if (CupomFiscalData.getCenario().getCnpj() == null) {
			return "CNPJ: ---";
		}

		String cnpjStr = VitafarmaUtil.parseCnpjToString(CupomFiscalData.getCenario().getCnpj());
		return ("CNPJ: " + cnpjStr);
	}

	public final static String montaInscricaoEstadual() throws NoSuchAlgorithmException {
		if (CupomFiscalData.getCenario().getInscricaoEstadual() == null) {
			return "";
		}

		String ie = CupomFiscalData.getCenario().getInscricaoEstadual();
		return ("IE: " + ie);
	}

	public final static String montaInscricaoMunicipal() throws NoSuchAlgorithmException {
		if (CupomFiscalData.getCenario().getInscricaoMunicipal() == null) {
			return "IM: ---";
		}

		String im = CupomFiscalData.getCenario().getInscricaoMunicipal();
		return ("IM: " + im);
	}

	public final static String montaDataHoraCodigoCupom(CupomVenda cupomVenda) throws NoSuchAlgorithmException {
		if (cupomVenda == null) {
			return ("Data: ---       - CCF: ---       - COO: ---");
		}

		String codigoCupom = (cupomVenda.getId().toString() == null ? "" : cupomVenda.getId().toString());
		String codigoCOO = (CupomFiscalData.getCenario().getCoo() == null ? "" : CupomFiscalData.getCenario().getCoo());

		VitafarmaDate dadaVenda = VitafarmaUtil.getVitafarmaDate(cupomVenda.getVenda().getDataVenda());

		return ("Data: " + dadaVenda.toDateTimeString() + "       - CCF: " + codigoCupom + "       - COO: "
				+ codigoCOO);
	}

	public final static String montaCpfCnpjCliente(Pessoa pessoa) {
		if (pessoa == null) {
			return "CNPJ/CPF (consumidor): ---";
		}

		String cnpjCpf = null;

		if (pessoa instanceof PessoaFisica) {
			PessoaFisica pf = (PessoaFisica) pessoa;
			cnpjCpf = VitafarmaUtil.parseCpfToString(pf.getCpf());
		} else if (pessoa instanceof PessoaJuridica) {
			PessoaJuridica pj = (PessoaJuridica) pessoa;
			cnpjCpf = VitafarmaUtil.parseCnpjToString(pj.getCnpj());
		}

		if (VitafarmaUtil.isBlank(cnpjCpf)) {
			return "CNPJ/CPF consumidor: ---";
		}

		return ("CNPJ/CPF consumidor: " + cnpjCpf);
	}

	public final static String montaNomeCliente(Venda venda) {
		if (venda == null) {
			return "NOME: ---";
		}

		String nome = "";
		if (venda.getCliente() == null || venda.getCliente().getNome() == null) {
			nome = ("NOME: ---");
		} else {
			nome = ("NOME: " + venda.getCliente().getNome());
		}

		if (nome.length() > CupomFiscalData.TAMANHO_LINHA) {
			return nome.substring(0, CupomFiscalData.TAMANHO_LINHA);
		}

		return nome;
	}

	public final static String montaEnderecoCliente(Venda venda) {
		if (venda == null) {
			return "END: ---";
		}

		String enderecoCliente = "";
		if (venda.getCliente() == null || venda.getCliente().getEndereco() == null) {
			enderecoCliente = ("END: ---");
		} else {
			enderecoCliente = ("END: " + venda.getCliente().getEndereco());
		}

		if (enderecoCliente.length() > CupomFiscalData.TAMANHO_LINHA) {
			return enderecoCliente.substring(0, CupomFiscalData.TAMANHO_LINHA);
		}

		return enderecoCliente;
	}

	public final static String montaSubtotalVenda(List<ItemVenda> itensVenda) {
		if (itensVenda == null) {
			return "Subtotal: ---";
		}

		Double subTotal = VitafarmaUtil.getSubtotalItens(itensVenda);

		String result = " (R$) " + VitafarmaUtil.formatCurrencyValueString(subTotal);

		while (result.length() < 57) {
			result = (" " + result);
		}

		result = ("Subtotal: " + result);
		return result;
	}

	public final static String montaDescontoVenda(Venda venda, List<ItemVenda> itensVenda) {
		if (venda == null || itensVenda == null) {
			return "Desconto: ---";
		}

		Double valorBruto = VitafarmaUtil.getSubtotalItens(itensVenda);
		Double valorFinal = VitafarmaUtil.getValorTotalItens(itensVenda);
		Double valorDesconto = (valorBruto - valorFinal);

		String result = "(R$) " + VitafarmaUtil.formatCurrencyValueString(valorDesconto);
		while (result.length() < 64) {
			result = (" " + result);
		}

		result = ("Desconto: " + result);
		return result;
	}

	public final static String montaTotalVenda(Venda venda, List<ItemVenda> itensVenda) {
		if (venda == null || itensVenda == null) {
			return "TOTAL: ---";
		}

		Double valorFinal = VitafarmaUtil.getValorTotalItens(itensVenda);

		String result = "(R$) " + VitafarmaUtil.formatCurrencyValueString(valorFinal);
		while (result.length() < 60) {
			result = (" " + result);
		}

		result = ("TOTAL: " + result);
		return result;
	}

	public final static String montaVersaoCaixaLojaOperador() throws NoSuchAlgorithmException {
		String versaoEcf = "001";
		String codigoLoja = "0001";
		String operadorCaixa = "Vitafarma";
		String versaoSistema = CupomFiscalData.getCenario().getVersaoSistema();

		if (versaoSistema == null) {
			versaoSistema = "---";
		}

		return ("VERSÃO: " + versaoSistema + " ECF: " + versaoEcf + " LJ: " + codigoLoja + " OPR: " + operadorCaixa);
	}

	public final static String montaItemCodigoDescricao(Integer codigoItem, ItemVenda itemVenda) {
		if (codigoItem == null || itemVenda == null) {
			return "---";
		}

		String codigoItemStr = codigoItem.toString();
		while (codigoItemStr.length() < 4) {
			codigoItemStr = "0" + codigoItemStr;
		}

		String codigoProdutoStr = "";
		if (itemVenda.getProduto() == null || itemVenda.getProduto().getId() == null) {
			codigoProdutoStr = "----";
		} else {
			codigoProdutoStr = itemVenda.getProduto().getId().toString();
			while (codigoProdutoStr.length() < 9) {
				codigoProdutoStr = "0" + codigoProdutoStr;
			}
		}

		String descricaoProduto = "";
		if (itemVenda.getProduto() == null || itemVenda.getProduto().getNome() == null) {
			descricaoProduto = "-----";
		} else {
			descricaoProduto = itemVenda.getProduto().getNome();
			if (descricaoProduto.length() > 28) {
				descricaoProduto = descricaoProduto.substring(0, 29);
			}

			while (descricaoProduto.length() < 31) {
				descricaoProduto = (" " + descricaoProduto);
			}
		}

		return (codigoItemStr + "   " + codigoProdutoStr + descricaoProduto);
	}

	public final static String montaQtdUnValorUnitarioValorItem(int codigoItem, ItemVenda itemVenda) {
		if (itemVenda == null) {
			return "---";
		}

		// Quantidade
		String quantidadeStr = "";
		if (itemVenda.getQuantidade() == null) {
			quantidadeStr = VitafarmaUtil.formatCurrencyValueString(0.0);
		} else {
			quantidadeStr = VitafarmaUtil.formatCurrencyValueString(itemVenda.getQuantidade());
		}

		// Preco unitário (sem desconto)
		String precoUnitarioStr = "";
		if (itemVenda.getPrecoUnitario() == null) {
			precoUnitarioStr = VitafarmaUtil.formatCurrencyValueString(0.0);
		} else {
			precoUnitarioStr = VitafarmaUtil.formatCurrencyValueString(itemVenda.getPrecoUnitario());
		}

		// Desconto do item
		String descontoItemStr = "";
		if (itemVenda.getDesconto() == null) {
			descontoItemStr = " (-" + VitafarmaUtil.formatCurrencyValueString(0.0) + "%)";
		} else {
			descontoItemStr = " (-" + VitafarmaUtil.formatCurrencyValueString(itemVenda.getDesconto()) + "%)";
		}

		// Preço final do item (com o desconto)
		String precoFinalItem = "";
		if (itemVenda.getPrecoFinal() == null) {
			precoFinalItem = VitafarmaUtil.formatCurrencyValueString(0.0);
		} else {
			precoFinalItem = VitafarmaUtil.formatCurrencyValueString(itemVenda.getPrecoFinal());
		}

		while (precoFinalItem.length() < 34) {
			precoFinalItem = (" " + precoFinalItem);
		}

		String result = (quantidadeStr + itemVenda.getProduto().getUnidadeVenda() + "   x   " + precoUnitarioStr
				+ descontoItemStr + precoFinalItem);

		return result;
	}
}
