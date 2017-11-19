package br.com.vitafarma.web.server;

import java.util.List;

import br.com.vitafarma.domain.Cidade;
import br.com.vitafarma.domain.Cliente;
import br.com.vitafarma.domain.Estado;
import br.com.vitafarma.domain.Fornecedor;
import br.com.vitafarma.domain.Funcionario;
import br.com.vitafarma.domain.Laboratorio;
import br.com.vitafarma.domain.NotaFiscal;
import br.com.vitafarma.domain.Produto;
import br.com.vitafarma.domain.Vendedor;
import br.com.vitafarma.web.client.UniquesService;
import br.com.vitafarma.web.shared.util.view.UniqueDomain;

public class UniquesServiceImpl extends VitafarmaRemoteService implements
		UniquesService {
	private static final long serialVersionUID = 5250776996542788849L;

	@Override
	public Boolean domainKeyIsUnique(String value, String uniqueDomain) {
		Boolean unique = true;
		UniqueDomain uniqueDomainEnum = UniqueDomain.valueOf(uniqueDomain);

		switch (uniqueDomainEnum) {
		case NOTA_FISCAL:
			unique = this.checkNotaFiscal(value);
			break;
		case CLIENTE:
			unique = this.checkCliente(value);
			break;
		case FUNCIONARIO:
			unique = this.checkFuncionario(value);
			break;
		case VENDEDOR:
			unique = this.checkVendedor(value);
			break;
		case FORNECEDOR:
			unique = this.checkFornecedor(value);
			break;
		case PRODUTO:
			unique = this.checkProduto(value);
			break;
		case ESTADO_NOME:
			unique = this.checkEstadoNome(value);
			break;
		case ESTADO_SIGLA:
			unique = this.checkEstadoSigla(value);
			break;
		case CIDADE:
			break;
		case LABORATORIO_MED_LAB:
			unique = this.checkLaboratorioMedLab(value);
			break;
		case LABORATORIO_CNPJ:
			unique = this.checkLaboratorioCnpj(value);
			break;
		default:
			break;
		}

		return unique;
	}

	@Override
	public Boolean domainKeyIsUnique(String value, String uniqueDomain,
			List<Long> idsDomain) {
		Boolean unique = true;
		UniqueDomain uniqueDomainEnum = UniqueDomain.valueOf(uniqueDomain);

		switch (uniqueDomainEnum) {
		case CIDADE:
			unique = this.checkCidade(value, idsDomain);
			break;
		default:
			break;
		}

		return unique;
	}

	private Boolean checkNotaFiscal(String value) {
		return NotaFiscal.checkCodigoUnique(value);
	}

	private Boolean checkCliente(String value) {
		return Cliente.checkCodigoUnique(value);
	}

	private Boolean checkFuncionario(String value) {
		return Funcionario.checkCodigoUnique(value);
	}

	private Boolean checkVendedor(String value) {
		return Vendedor.checkCodigoUnique(value);
	}

	private Boolean checkFornecedor(String value) {
		return Fornecedor.checkCodigoUnique(value);
	}

	private Boolean checkProduto(String value) {
		return Produto.checkCodigoUnique(value);
	}

	private Boolean checkEstadoNome(String value) {
		return Estado.checkNomeUnique(value);
	}

	private Boolean checkEstadoSigla(String value) {
		return Estado.checkSiglaUnique(value);
	}

	private Boolean checkCidade(String value, List<Long> idsDomains) {
		return Cidade.checkCidadeEstadoUnique(value, idsDomains);
	}

	private Boolean checkLaboratorioMedLab(String value) {
		return Laboratorio.checkMedLabUnique(value);
	}

	private Boolean checkLaboratorioCnpj(String value) {
		return Laboratorio.checkCnpjUnique(value);
	}
}
