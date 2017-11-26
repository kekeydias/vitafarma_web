package br.com.vitafarma.web.server.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import br.com.vitafarma.domain.Authority;
import br.com.vitafarma.domain.Cenario;
import br.com.vitafarma.domain.Cidade;
import br.com.vitafarma.domain.Cliente;
import br.com.vitafarma.domain.CupomVenda;
import br.com.vitafarma.domain.EntradaProduto;
import br.com.vitafarma.domain.EntradaProdutoGroup;
import br.com.vitafarma.domain.Estado;
import br.com.vitafarma.domain.Fornecedor;
import br.com.vitafarma.domain.Funcionario;
import br.com.vitafarma.domain.ItemVenda;
import br.com.vitafarma.domain.Laboratorio;
import br.com.vitafarma.domain.NotaFiscal;
import br.com.vitafarma.domain.Produto;
import br.com.vitafarma.domain.Usuario;
import br.com.vitafarma.domain.Venda;
import br.com.vitafarma.domain.Vendedor;
import br.com.vitafarma.util.InputOutputUtils;
import br.com.vitafarma.web.shared.dtos.CenarioDTO;
import br.com.vitafarma.web.shared.dtos.CidadeDTO;
import br.com.vitafarma.web.shared.dtos.ClienteDTO;
import br.com.vitafarma.web.shared.dtos.EntradaProdutoDTO;
import br.com.vitafarma.web.shared.dtos.EntradaProdutoGroupDTO;
import br.com.vitafarma.web.shared.dtos.EstadoDTO;
import br.com.vitafarma.web.shared.dtos.FornecedorDTO;
import br.com.vitafarma.web.shared.dtos.FuncionarioDTO;
import br.com.vitafarma.web.shared.dtos.ItemVendaDTO;
import br.com.vitafarma.web.shared.dtos.LaboratorioDTO;
import br.com.vitafarma.web.shared.dtos.NotaFiscalDTO;
import br.com.vitafarma.web.shared.dtos.ProdutoDTO;
import br.com.vitafarma.web.shared.dtos.RelatorioBalancoDTO;
import br.com.vitafarma.web.shared.dtos.UsuarioDTO;
import br.com.vitafarma.web.shared.dtos.VendaDTO;
import br.com.vitafarma.web.shared.dtos.VendedorDTO;
import br.com.vitafarma.web.shared.util.VitafarmaCurrency;
import br.com.vitafarma.web.shared.util.VitafarmaDate;
import br.com.vitafarma.web.shared.util.VitafarmaUtil;
import br.com.vitafarma.web.shared.util.view.RelatorioBalancoGrid.Operacao;

public class ConvertBeans {
	public static Usuario toUsuario(UsuarioDTO dto) {
		Authority authority = Authority.find(dto.getAuthorityUsername());

		Usuario domain = new Usuario(dto.getNome(), dto.getEmail(), dto.getUsername(), dto.getPassword(),
				dto.getEnabled(), authority);

		return domain;
	}

	public static UsuarioDTO toUsuarioDTO(Usuario domain) {
		UsuarioDTO dto = new UsuarioDTO();

		Authority authority = domain.getAuthority();

		dto.setNome(domain.getNome());
		dto.setEmail(domain.getEmail());
		dto.setUsername(domain.getUsername());
		dto.setPassword(domain.getPassword());
		dto.setEnabled(domain.getEnabled());
		dto.setAuthorityUsername(authority == null ? null : authority.getUsername());
		dto.setDisplayText(domain.getNome());

		return dto;
	}

	public static Cenario toCenario(CenarioDTO dto) {
		Usuario criadoPor = Usuario.find(dto.getCriadoUsuarioString());
		Usuario atualizadoPor = Usuario.find(dto.getAtualizadoUsuarioString());

		Cenario domain = new Cenario(dto.getId(), dto.getNome(), dto.getCriadoUsuarioDate(),
				dto.getAtualizadoUsuarioDate(), dto.getVersaoSistema(), dto.getCnpjValue(), dto.getInscricaoEstadual(),
				dto.getInscricaoMunicipal(), dto.getCoo(), criadoPor, atualizadoPor, dto.getEndereco1(),
				dto.getEndereco2());

		return domain;
	}

	public static CenarioDTO toCenarioDTO(Cenario domain) {
		CenarioDTO dto = new CenarioDTO();

		dto.setId(domain.getId());
		dto.setNome(domain.getNome());
		dto.setEndereco1(domain.getEndereco1());
		dto.setEndereco2(domain.getEndereco2());
		dto.setVersaoSistema(domain.getVersaoSistema());
		dto.setCriadoUsuarioString(domain.getCriadoPor().getNome());
		dto.setCriadoUsuarioDate(domain.getDataCriacao());
		dto.setAtualizadoUsuarioString(domain.getAtualizadoPor().getNome());
		dto.setAtualizadoUsuarioDate(domain.getDataAtualizacao());
		dto.setCnpjValue(domain.getCnpj());
		dto.setCnpjString(VitafarmaUtil.parseCnpjToString(domain.getCnpj()));
		dto.setInscricaoEstadual(domain.getInscricaoEstadual());
		dto.setInscricaoMunicipal(domain.getInscricaoMunicipal());
		dto.setCoo(domain.getCoo());

		dto.setDisplayText(domain.getNome());

		return dto;
	}

	public static Fornecedor toFornecedor(FornecedorDTO dto) {
		Cidade cidade = Cidade.find(dto.getCidadeId());

		Fornecedor domain = new Fornecedor(dto.getId(), dto.getNome(), dto.getTelefoneValue(), dto.getEndereco(),
				dto.getEmail(), dto.getNomeFantasia(), dto.getCnpjValue(), cidade);

		return domain;
	}

	public static FornecedorDTO toFornecedorDTO(Fornecedor domain) {
		FornecedorDTO dto = new FornecedorDTO();

		dto.setId(domain.getId());
		dto.setNome(domain.getNome());
		dto.setTelefoneValue(domain.getTelefone());
		dto.setTelefoneString(VitafarmaUtil.parsePhoneNumberToString(domain.getTelefone()));
		dto.setEndereco(domain.getEndereco());
		dto.setEmail(domain.getEmail());
		dto.setNomeFantasia(domain.getNomeFantasia());
		dto.setCnpjValue(domain.getCnpj());
		dto.setCnpjString(VitafarmaUtil.parseCnpjToString(domain.getCnpj()));

		dto.setCidadeId(domain.getCidade() == null ? null : domain.getCidade().getId());
		dto.setCidadeNome(domain.getCidade() == null ? null : domain.getCidade().getNome());

		dto.setEstadoId(domain.getCidade() == null || domain.getCidade().getEstado() == null ? null
				: domain.getCidade().getEstado().getId());
		dto.setEstadoNome(domain.getCidade() == null || domain.getCidade().getEstado() == null ? null
				: domain.getCidade().getEstado().getNome());

		dto.setDisplayText(domain.getNome());

		return dto;
	}

	public static List<Fornecedor> toListFornecedor(List<FornecedorDTO> listDTO) {
		if (listDTO == null) {
			return Collections.<Fornecedor> emptyList();
		}

		List<Fornecedor> listDomains = new ArrayList<Fornecedor>();

		for (FornecedorDTO dto : listDTO) {
			listDomains.add(ConvertBeans.toFornecedor(dto));
		}

		return listDomains;
	}

	public static List<FornecedorDTO> toListFornecedorDTO(List<Fornecedor> listDomains) {
		if (listDomains == null) {
			return Collections.<FornecedorDTO> emptyList();
		}

		List<FornecedorDTO> listDTOs = new ArrayList<FornecedorDTO>();

		for (Fornecedor domain : listDomains) {
			listDTOs.add(ConvertBeans.toFornecedorDTO(domain));
		}

		return listDTOs;
	}

	public static Cliente toCliente(ClienteDTO dto) {
		Cidade cidade = Cidade.find(dto.getCidadeId());

		Cliente domain = new Cliente(dto.getId(), dto.getNome(), dto.getCpfValue(), dto.getTelefone(),
				dto.getEndereco(), dto.getEmail(), cidade);

		return domain;
	}

	public static ClienteDTO toClienteDTO(Cliente domain) {
		ClienteDTO dto = new ClienteDTO();

		dto.setId(domain.getId());
		dto.setNome(domain.getNome());
		dto.setCpfValue(domain.getCpf());
		dto.setCpfString(VitafarmaUtil.parseCpfToString(domain.getCpf()));
		dto.setTelefone(domain.getTelefone());
		dto.setEndereco(domain.getEndereco());
		dto.setEmail(domain.getEmail());

		Cidade cidade = domain.getCidade();
		if (cidade != null) {
			dto.setCidadeId(cidade.getId());
			dto.setCidadeNome(cidade.getNome());
		}

		Estado estado = (domain.getCidade() == null ? null : domain.getCidade().getEstado());
		if (estado != null) {
			dto.setEstadoId(estado.getId());
			dto.setEstadoNome(estado.getNome());
			dto.setEstadoSigla(estado.getSigla());
		}

		dto.setDisplayText(domain.getNome());

		return dto;
	}

	public static List<Cliente> toListCliente(List<ClienteDTO> listDTO) {
		if (listDTO == null) {
			return Collections.<Cliente> emptyList();
		}

		List<Cliente> listDomains = new ArrayList<Cliente>();

		for (ClienteDTO dto : listDTO) {
			listDomains.add(ConvertBeans.toCliente(dto));
		}

		return listDomains;
	}

	public static List<ClienteDTO> toListClienteDTO(List<Cliente> listDomains) {
		if (listDomains == null) {
			return Collections.<ClienteDTO> emptyList();
		}

		List<ClienteDTO> listDTOs = new ArrayList<ClienteDTO>();

		for (Cliente domain : listDomains) {
			listDTOs.add(ConvertBeans.toClienteDTO(domain));
		}

		return listDTOs;
	}

	public static Funcionario toFuncionario(FuncionarioDTO dto) {
		Cidade cidade = Cidade.find(dto.getCidadeId());

		Funcionario domain = new Funcionario(dto.getId(), dto.getNome(), dto.getCpfValue(), dto.getTelefone(),
				dto.getEndereco(), dto.getEmail(), dto.getDataAdimissao(), dto.getDataDemissao(), dto.getSalarioValue(),
				cidade);

		return domain;
	}

	public static FuncionarioDTO toFuncionarioDTO(Funcionario domain) {
		FuncionarioDTO dto = new FuncionarioDTO();

		dto.setId(domain.getId());
		dto.setNome(domain.getNome());
		dto.setCpfValue(domain.getCpf());
		dto.setCpfString(VitafarmaUtil.parseCpfToString(domain.getCpf()));

		VitafarmaCurrency salario = new VitafarmaCurrency(domain.getSalario());
		dto.setSalarioValue(salario.getDoubleValue());
		dto.setSalarioString(salario.toString());

		dto.setTelefone(domain.getTelefone());
		dto.setEndereco(domain.getEndereco());
		dto.setEmail(domain.getEmail());

		dto.setDataAdimissao(domain.getDataAdimissao());
		dto.setDataAdimissaoString(VitafarmaUtil.buildDateString(domain.getDataAdimissao()));

		dto.setDataDemissao(domain.getDataDemissao());
		dto.setDataDemissaoString(VitafarmaUtil.buildDateString(domain.getDataDemissao()));

		dto.setCidadeId(domain.getCidade() == null ? null : domain.getCidade().getId());
		dto.setCidadeNome(domain.getCidade() == null ? null : domain.getCidade().getNome());

		dto.setEstadoId(domain.getCidade() == null || domain.getCidade().getEstado() == null ? null
				: domain.getCidade().getEstado().getId());
		dto.setEstadoNome(domain.getCidade() == null || domain.getCidade().getEstado() == null ? null
				: domain.getCidade().getEstado().getNome());

		dto.setDisplayText(domain.getNome());

		return dto;
	}

	public static List<Funcionario> toListFuncionario(List<FuncionarioDTO> listDTO) {
		if (listDTO == null) {
			return Collections.<Funcionario> emptyList();
		}

		List<Funcionario> listDomains = new ArrayList<Funcionario>();

		for (FuncionarioDTO dto : listDTO) {
			listDomains.add(ConvertBeans.toFuncionario(dto));
		}

		return listDomains;
	}

	public static List<FuncionarioDTO> toListFuncionarioDTO(List<Funcionario> listDomains) {
		if (listDomains == null) {
			return Collections.<FuncionarioDTO> emptyList();
		}

		List<FuncionarioDTO> listDTOs = new ArrayList<FuncionarioDTO>();

		for (Funcionario domain : listDomains) {
			listDTOs.add(ConvertBeans.toFuncionarioDTO(domain));
		}

		return listDTOs;
	}

	public static Produto toProduto(ProdutoDTO dto) {
		Laboratorio laboratorio = Laboratorio.find(dto.getLaboratorioId());

		Produto domain = new Produto(dto.getId(), dto.getMedAbcValue(), dto.getNome(), dto.getDescricao(),
				dto.getPrecoValue(), dto.getEstoque(), dto.getUnidadeEntrada(), dto.getUnidadeVenda(), laboratorio);

		return domain;
	}

	public static ProdutoDTO toProdutoDTO(Produto domain) {
		ProdutoDTO dto = new ProdutoDTO();

		Laboratorio laboratorio = domain.getLaboratorio();

		if (laboratorio != null) {
			dto.setLaboratorioId(laboratorio.getId());
			dto.setLaboratorioString(laboratorio.getNome());
		}

		dto.setId(domain.getId());

		if (domain.getMedAbc() != null) {
			dto.setMedAbcValue(domain.getMedAbc());
			dto.setMedAbcString(domain.getMedAbc().toString());
		}

		dto.setNome(domain.getNome());
		dto.setDescricao(domain.getDescricao());

		VitafarmaCurrency preco = new VitafarmaCurrency(domain.getPreco());
		dto.setPrecoValue(preco.getDoubleValue());
		dto.setPrecoString(preco.toString());

		dto.setEstoque(domain.getEstoque());
		dto.setUnidadeEntrada(domain.getUnidadeEntrada());
		dto.setUnidadeVenda(domain.getUnidadeVenda());
		dto.setDisplayText(domain.getNome());

		return dto;
	}

	public static List<Produto> toListProduto(List<ProdutoDTO> listDTO) {
		if (listDTO == null) {
			return Collections.<Produto> emptyList();
		}

		List<Produto> listDomains = new ArrayList<Produto>();

		for (ProdutoDTO dto : listDTO) {
			listDomains.add(ConvertBeans.toProduto(dto));
		}

		return listDomains;
	}

	public static List<ProdutoDTO> toListProdutoDTO(List<Produto> listDomains) {
		if (listDomains == null) {
			return Collections.<ProdutoDTO> emptyList();
		}

		List<ProdutoDTO> listDTOs = new ArrayList<ProdutoDTO>();

		for (Produto domain : listDomains) {
			listDTOs.add(ConvertBeans.toProdutoDTO(domain));
		}

		return listDTOs;
	}

	public static Venda toVenda(VendaDTO dto) {
		Cenario cenario = Cenario.find(dto.getCenarioId());
		Cliente cliente = Cliente.find(dto.getClienteId());
		CupomVenda cupomVenda = CupomVenda.find(dto.getCupomVendaId());

		Venda domain = new Venda(dto.getVendaId(), dto.getVendaDataValue(), dto.getDescontoValue(), cliente, cenario,
				cupomVenda);

		return domain;
	}

	public static VendaDTO toVendaDTO(Venda domain) {
		VendaDTO dto = new VendaDTO();
		List<ItemVenda> itensVenda = ItemVenda.findByVenda(domain);

		dto.setVendaId(domain.getId());
		dto.setClienteId(domain.getCliente().getId());
		dto.setCenarioId(domain.getCenario().getId());
		dto.setClienteNome(domain.getCliente().getNome());
		dto.setClienteCpfValue(domain.getCliente().getCpf());
		dto.setClienteCpfString(VitafarmaUtil.parseCpfToString(domain.getCliente().getCpf()));

		// Desconto da venda
		VitafarmaCurrency desconto = new VitafarmaCurrency(domain.getDesconto());
		dto.setDescontoValue(desconto.getDoubleValue());
		dto.setDescontoString(desconto.toString());

		// Subtotal da venda
		VitafarmaCurrency subtotal = new VitafarmaCurrency(ConvertBeans.getSubtotalItens(itensVenda));
		dto.setSubtotalValue(subtotal.getDoubleValue());
		dto.setSubtotalString(subtotal.toString());

		// Calcula o preço final da venda, aplicando o desconto
		// da venda no subtotal (preco final dos itens)
		VitafarmaCurrency precoFinalVenda = new VitafarmaCurrency(ConvertBeans.getValorTotalItens(itensVenda));
		dto.setValorFinalValue(precoFinalVenda.getDoubleValue());
		dto.setValorFinalString(precoFinalVenda.toString());

		// Data da venda
		Date dataVenda = domain.getDataVenda();
		dto.setVendaDataValue(dataVenda);
		dto.setVendaDataString(VitafarmaUtil.buildDateTimeString(dataVenda));

		if (dataVenda != null) {
			dto.setVendaHora(InputOutputUtils.getDateHour(dataVenda));
			dto.setVendaMinuto(InputOutputUtils.getDateMinute(dataVenda));
			dto.setVendaHoraMinutoString(VitafarmaUtil.shortTimeString(dataVenda));
		}

		// Cupom fiscal da venda
		CupomVenda cupomVenda = CupomVenda.findByVenda(domain);
		if (cupomVenda != null) {
			dto.setCupomVendaId(cupomVenda.getId());
			dto.setCupomVendaString(cupomVenda.getId().toString());
		}

		dto.setDisplayText(domain.getId().toString());

		return dto;
	}

	public static List<Venda> toListVenda(List<VendaDTO> listDTO) {
		if (listDTO == null) {
			return Collections.<Venda> emptyList();
		}

		List<Venda> listDomains = new ArrayList<Venda>();

		for (VendaDTO dto : listDTO) {
			listDomains.add(ConvertBeans.toVenda(dto));
		}

		return listDomains;
	}

	public static List<VendaDTO> toListVendaDTO(List<Venda> listDomains) {
		if (listDomains == null) {
			return Collections.<VendaDTO> emptyList();
		}

		List<VendaDTO> listDTOs = new ArrayList<VendaDTO>();

		for (Venda domain : listDomains) {
			listDTOs.add(ConvertBeans.toVendaDTO(domain));
		}

		return listDTOs;
	}

	public static ItemVenda toItemVenda(ItemVendaDTO dto) {
		Venda venda = Venda.find(dto.getVendaId());
		Produto produto = Produto.find(dto.getProdutoId());
		CupomVenda cupomVenda = CupomVenda.find(dto.getCupomVendaId());

		ItemVenda domain = new ItemVenda(dto.getId(), produto, venda, dto.getPrecoUnitarioValue(),
				dto.getQuantidadeValue(), dto.getDescontoValue(), cupomVenda);

		return domain;
	}

	public static ItemVendaDTO toItemVendaDTO(ItemVenda domain) {
		ItemVendaDTO dto = new ItemVendaDTO();

		dto.setId(domain.getId());
		dto.setVendaId(domain.getId());

		VitafarmaCurrency quantidade = new VitafarmaCurrency(domain.getQuantidade());
		dto.setQuantidadeValue(quantidade.getDoubleValue());
		dto.setQuantidadeString(quantidade.toString());

		dto.setClienteId(domain.getVenda().getCliente().getId());
		dto.setClienteString(domain.getVenda().getCliente().getNome());
		dto.setClienteCpfValue(domain.getVenda().getCliente().getCpf());
		dto.setClienteCpfString(VitafarmaUtil.parseCpfToString(domain.getVenda().getCliente().getCpf()));
		dto.setProdutoId(domain.getProduto().getId());
		dto.setProdutoString(domain.getProduto().getNome());

		VitafarmaCurrency valorUnitario = new VitafarmaCurrency(domain.getPrecoUnitario());
		dto.setPrecoUnitarioValue(valorUnitario.getDoubleValue());
		dto.setPrecoUnitarioString(valorUnitario.toString());

		// Desconto do item
		VitafarmaCurrency desconto = new VitafarmaCurrency(domain.getDesconto());
		dto.setDescontoValue(desconto.getDoubleValue());
		dto.setDescontoString(desconto.toString());

		// Preço final do item, já considerado o desconto
		VitafarmaCurrency precoFinal = new VitafarmaCurrency(domain.getPrecoFinal());
		dto.setPrecoFinalValue(precoFinal.getDoubleValue());
		dto.setPrecoFinalString(precoFinal.toString());

		// Cupom fiscal da venda relacionada ao item
		CupomVenda cupomVenda = domain.getCupomVenda();
		dto.setCupomVendaId(cupomVenda == null ? null : cupomVenda.getId());
		dto.setCupomVendaString(cupomVenda == null ? null : cupomVenda.getId().toString());

		// Subtotal do item
		VitafarmaCurrency subtotal = new VitafarmaCurrency(domain.getPrecoUnitario() * domain.getQuantidade());
		dto.setSubtotalValue(subtotal.getDoubleValue());
		dto.setSubtotalString(subtotal.toString());

		dto.setDisplayText(domain.getId().toString());

		return dto;
	}

	public static List<ItemVenda> toListItemVenda(List<ItemVendaDTO> listDTO) {
		if (listDTO == null) {
			return Collections.<ItemVenda> emptyList();
		}

		List<ItemVenda> listDomains = new ArrayList<ItemVenda>();

		for (ItemVendaDTO dto : listDTO) {
			listDomains.add(ConvertBeans.toItemVenda(dto));
		}

		return listDomains;
	}

	public static List<ItemVendaDTO> toListItemVendaDTO(List<ItemVenda> listDomains) {
		if (listDomains == null) {
			return Collections.<ItemVendaDTO> emptyList();
		}

		List<ItemVendaDTO> listDTOs = new ArrayList<ItemVendaDTO>();

		for (ItemVenda domain : listDomains) {
			listDTOs.add(ConvertBeans.toItemVendaDTO(domain));
		}

		return listDTOs;
	}

	public static EntradaProduto toEntradaProduto(EntradaProdutoDTO dto) {
		EntradaProdutoGroup group = EntradaProdutoGroup.find(dto.getGroupId());
		Produto produto = Produto.find(dto.getProdutoId());
		Fornecedor fornecedor = Fornecedor.find(dto.getFornecedorId());
		NotaFiscal notaFiscal = NotaFiscal.find(dto.getNotaFiscalId());

		EntradaProduto domain = new EntradaProduto(dto.getId(), dto.getDataEntradaValue(), dto.getQuantidadeValue(),
				dto.getPrecoUnitarioValue(), fornecedor, produto, notaFiscal, group);

		return domain;
	}

	public static EntradaProdutoDTO toEntradaProdutoDTO(EntradaProduto domain) {
		EntradaProdutoDTO dto = new EntradaProdutoDTO();

		dto.setId(domain.getId());

		EntradaProdutoGroup group = domain.getEntradaProdutoGroup();

		if (group != null) {
			dto.setGroupId(group.getId());
			dto.setGroupString(group.getId() == null ? "" : group.getId().toString());
		}

		// Data da Entrada
		Date dataEntrada = domain.getDataEntrada();
		dto.setDataEntradaValue(VitafarmaUtil.getVitafarmaDate(dataEntrada));
		dto.setDataEntradaString(VitafarmaUtil.buildDateTimeString(dataEntrada));

		if (dataEntrada != null) {
			dto.setEntradaProdutoHora(InputOutputUtils.getDateHour(dataEntrada));
			dto.setEntradaProdutoMinuto(InputOutputUtils.getDateMinute(dataEntrada));
			dto.setEntradaProdutoHoraMinutoString(VitafarmaUtil.shortTimeString(dataEntrada));
		}

		// Quantidade
		VitafarmaCurrency quantidade = new VitafarmaCurrency(domain.getQuantidade());
		dto.setQuantidadeValue(quantidade.getDoubleValue());
		dto.setQuantidadeString(quantidade.toString());

		// Preço Unitário
		VitafarmaCurrency precoUnitario = new VitafarmaCurrency(domain.getPrecoUnitario());
		dto.setPrecoUnitarioValue(precoUnitario.getDoubleValue());
		dto.setPrecoUnitarioString(precoUnitario.toString());

		// Valor total do item de entrada
		VitafarmaCurrency valorTotal = new VitafarmaCurrency(domain.getValorTotalItem());
		dto.setValorTotalValue(valorTotal.getDoubleValue());
		dto.setValorTotalString(valorTotal.toString());

		// Nota Fiscal
		NotaFiscal notaFiscal = domain.getNotaFiscal();
		if (notaFiscal != null) {
			dto.setNotaFiscalId(notaFiscal.getId());
			dto.setNotaFiscalCodigo(notaFiscal.getCodigo());
			dto.setNotaFiscalData(VitafarmaUtil.getVitafarmaDate(notaFiscal.getData()));
		}

		// Produto
		Produto produto = domain.getProduto();
		dto.setProdutoId(produto == null ? null : domain.getProduto().getId());
		dto.setProdutoString(produto == null ? "" : domain.getProduto().getNome());

		// Fornecedor
		Fornecedor fornecedor = domain.getFornecedor();
		dto.setFornecedorId(fornecedor == null ? null : domain.getFornecedor().getId());
		dto.setFornecedorString(fornecedor == null ? "" : domain.getFornecedor().getNomeFantasia());

		dto.setDisplayText(domain.getId() == null ? "" : domain.getId().toString());

		return dto;
	}

	public static List<EntradaProduto> toListEntradaProduto(List<EntradaProdutoDTO> listDTO) {
		if (listDTO == null) {
			return Collections.<EntradaProduto> emptyList();
		}

		List<EntradaProduto> listDomains = new ArrayList<EntradaProduto>();

		for (EntradaProdutoDTO dto : listDTO) {
			listDomains.add(ConvertBeans.toEntradaProduto(dto));
		}

		return listDomains;
	}

	public static List<EntradaProdutoDTO> toListEntradaProdutoDTO(List<EntradaProduto> listDomains) {
		if (listDomains == null) {
			return Collections.<EntradaProdutoDTO> emptyList();
		}

		List<EntradaProdutoDTO> listDTOs = new ArrayList<EntradaProdutoDTO>();

		for (EntradaProduto domain : listDomains) {
			listDTOs.add(ConvertBeans.toEntradaProdutoDTO(domain));
		}

		return listDTOs;
	}

	public static Estado toEstado(EstadoDTO dto) {
		Estado domain = new Estado(dto.getId(), dto.getNome(), dto.getSigla());

		return domain;
	}

	public static EstadoDTO toEstadoDTO(Estado domain) {
		EstadoDTO dto = new EstadoDTO();

		dto.setId(domain.getId());
		dto.setNome(domain.getNome());
		dto.setSigla(domain.getSigla());
		dto.setDisplayText(domain.getNome() + " / " + domain.getSigla());

		return dto;
	}

	public static List<Estado> toListEstado(List<EstadoDTO> listDTO) {
		if (listDTO == null) {
			return Collections.<Estado> emptyList();
		}

		List<Estado> listDomains = new ArrayList<Estado>();

		for (EstadoDTO dto : listDTO) {
			listDomains.add(ConvertBeans.toEstado(dto));
		}

		return listDomains;
	}

	public static List<EstadoDTO> toListEstadoDTO(List<Estado> listDomains) {
		if (listDomains == null) {
			return Collections.<EstadoDTO> emptyList();
		}

		List<EstadoDTO> listDTOs = new ArrayList<EstadoDTO>();

		for (Estado domain : listDomains) {
			listDTOs.add(ConvertBeans.toEstadoDTO(domain));
		}

		return listDTOs;
	}

	public static Cidade toCidade(CidadeDTO dto) {
		Estado estado = Estado.find(dto.getEstadoId());

		Cidade domain = new Cidade(dto.getId(), dto.getNome(), estado);

		return domain;
	}

	public static CidadeDTO toCidadeDTO(Cidade domain) {
		CidadeDTO dto = new CidadeDTO();

		dto.setId(domain.getId());
		dto.setNome(domain.getNome());

		Estado estado = domain.getEstado();

		if (estado != null) {
			dto.setEstadoId(estado.getId());
			dto.setEstadoNome(estado.getNome());
			dto.setEstadoSigla(estado.getSigla());
			dto.setDisplayText(domain.getNome() + " / " + estado.getSigla());
		} else {
			dto.setDisplayText(domain.getNome() + " / --");
		}

		return dto;
	}

	public static List<Cidade> toListCidade(List<CidadeDTO> listDTO) {
		if (listDTO == null) {
			return Collections.<Cidade> emptyList();
		}

		List<Cidade> listDomains = new ArrayList<Cidade>();

		for (CidadeDTO dto : listDTO) {
			listDomains.add(ConvertBeans.toCidade(dto));
		}

		return listDomains;
	}

	public static List<CidadeDTO> toListCidadeDTO(List<Cidade> listDomains) {
		if (listDomains == null) {
			return Collections.<CidadeDTO> emptyList();
		}

		List<CidadeDTO> listDTOs = new ArrayList<CidadeDTO>();

		for (Cidade domain : listDomains) {
			listDTOs.add(ConvertBeans.toCidadeDTO(domain));
		}

		return listDTOs;
	}

	public static NotaFiscal toNotaFiscal(NotaFiscalDTO dto) {
		Fornecedor fornecedor = Fornecedor.find(dto.getFornecedorId());

		NotaFiscal domain = new NotaFiscal(dto.getId(), dto.getCodigo(), dto.getData(), fornecedor);

		return domain;
	}

	public static NotaFiscalDTO toNotaFiscalDTO(NotaFiscal domain) {
		NotaFiscalDTO dto = new NotaFiscalDTO();

		dto.setId(domain.getId());
		dto.setCodigo(domain.getCodigo());
		dto.setData(domain.getData());

		Fornecedor fornecedor = domain.getFornecedor();

		if (fornecedor != null) {
			dto.setFornecedorId(fornecedor.getId());
			dto.setFornecedorString(fornecedor.getNome());
		}

		dto.setDisplayText(domain.getCodigo());

		return dto;
	}

	public static List<NotaFiscal> toListNotaFiscal(List<NotaFiscalDTO> listDTO) {
		if (listDTO == null) {
			return Collections.<NotaFiscal> emptyList();
		}

		List<NotaFiscal> listDomains = new ArrayList<NotaFiscal>();

		for (NotaFiscalDTO dto : listDTO) {
			listDomains.add(ConvertBeans.toNotaFiscal(dto));
		}

		return listDomains;
	}

	public static List<NotaFiscalDTO> toListNotaFiscalDTO(List<NotaFiscal> listDomains) {
		if (listDomains == null) {
			return Collections.<NotaFiscalDTO> emptyList();
		}

		List<NotaFiscalDTO> listDTOs = new ArrayList<NotaFiscalDTO>();

		for (NotaFiscal domain : listDomains) {
			listDTOs.add(ConvertBeans.toNotaFiscalDTO(domain));
		}

		return listDTOs;
	}

	public static RelatorioBalancoDTO getRelatorioBalancoDTO(Venda venda, VitafarmaDate data, Double valorFinal) {
		RelatorioBalancoDTO relatorioBalancoDTO = new RelatorioBalancoDTO();

		relatorioBalancoDTO.setOperacao(Operacao.VENDA.toString());

		relatorioBalancoDTO.setVendaId(venda.getId());
		relatorioBalancoDTO.setEntradaProdutoId(null);

		Cliente cliente = venda.getCliente();
		relatorioBalancoDTO.setClienteId(cliente.getId());
		relatorioBalancoDTO.setClienteString(cliente.getNome());
		relatorioBalancoDTO.setClienteCpfValue(cliente.getCpf());
		relatorioBalancoDTO.setClienteCpfString(VitafarmaUtil.parseCpfToString(cliente.getCpf()));

		return ConvertBeans.setDefaultProperties(relatorioBalancoDTO, data, valorFinal);
	}

	public static RelatorioBalancoDTO getRelatorioBalancoDTO(EntradaProduto entradaProduto, VitafarmaDate data,
			Double valorFinal) {
		RelatorioBalancoDTO relatorioBalancoDTO = new RelatorioBalancoDTO();

		relatorioBalancoDTO.setOperacao(Operacao.ENTRADA.toString());
		relatorioBalancoDTO.setVendaId(null);
		relatorioBalancoDTO.setEntradaProdutoId(entradaProduto.getId());

		Fornecedor fornecedor = entradaProduto.getFornecedor();

		if (fornecedor != null) {
			relatorioBalancoDTO.setFornecedorId(fornecedor.getId());
			relatorioBalancoDTO.setFornecedorString(fornecedor.getNome());
			relatorioBalancoDTO.setFornecedorCnpjValue(fornecedor.getCnpj());
			relatorioBalancoDTO.setFornecedorCnpjString(VitafarmaUtil.parseCnpjToString(fornecedor.getCnpj()));
		}

		Produto produto = entradaProduto.getProduto();

		if (produto != null) {
			relatorioBalancoDTO.setProdutoId(produto.getId());
			relatorioBalancoDTO.setProdutoString(produto.getNome());
		}

		return ConvertBeans.setDefaultProperties(relatorioBalancoDTO, data, valorFinal);
	}

	private static RelatorioBalancoDTO setDefaultProperties(RelatorioBalancoDTO relatorioBalancoDTO, VitafarmaDate data,
			Double valorFinal) {
		relatorioBalancoDTO.setDataValue(data);
		relatorioBalancoDTO.setDataString(data.toDateTimeString());

		VitafarmaCurrency value = new VitafarmaCurrency(valorFinal);
		relatorioBalancoDTO.setValorFinalValue(value.getDoubleValue());
		relatorioBalancoDTO.setValorFinalString(value.toString());

		return relatorioBalancoDTO;
	}

	public static Vendedor toVendedor(VendedorDTO dto) {
		Cidade cidade = Cidade.find(dto.getCidadeId());

		Vendedor domain = new Vendedor(dto.getId(), dto.getNome(), dto.getCpfValue(), dto.getTelefone(),
				dto.getEndereco(), dto.getEmail(), dto.getDataAdimissao(), dto.getDataDemissao(), dto.getSalarioValue(),
				cidade);

		return domain;
	}

	public static VendedorDTO toVendedorDTO(Vendedor domain) {
		VendedorDTO dto = new VendedorDTO();

		dto.setId(domain.getId());
		dto.setNome(domain.getNome());
		dto.setCpfValue(domain.getCpf());
		dto.setCpfString(VitafarmaUtil.parseCpfToString(domain.getCpf()));

		VitafarmaCurrency salario = new VitafarmaCurrency(domain.getSalario());
		dto.setSalarioValue(salario.getDoubleValue());
		dto.setSalarioString(salario.toString());

		dto.setTelefone(domain.getTelefone());
		dto.setEndereco(domain.getEndereco());
		dto.setEmail(domain.getEmail());

		dto.setDataAdimissao(domain.getDataAdimissao());
		dto.setDataAdimissaoString(VitafarmaUtil.buildDateString(domain.getDataAdimissao()));

		dto.setDataDemissao(domain.getDataDemissao());
		dto.setDataDemissaoString(VitafarmaUtil.buildDateString(domain.getDataDemissao()));

		dto.setCidadeId(domain.getCidade() == null ? null : domain.getCidade().getId());
		dto.setCidadeNome(domain.getCidade() == null ? null : domain.getCidade().getNome());

		dto.setEstadoId(domain.getCidade() == null || domain.getCidade().getEstado() == null ? null
				: domain.getCidade().getEstado().getId());
		dto.setEstadoNome(domain.getCidade() == null || domain.getCidade().getEstado() == null ? null
				: domain.getCidade().getEstado().getNome());

		dto.setDisplayText(domain.getNome());

		return dto;
	}

	public static List<Vendedor> toListVendedor(List<VendedorDTO> listDTO) {
		if (listDTO == null) {
			return Collections.<Vendedor> emptyList();
		}

		List<Vendedor> listDomains = new ArrayList<Vendedor>();

		for (VendedorDTO dto : listDTO) {
			listDomains.add(ConvertBeans.toVendedor(dto));
		}

		return listDomains;
	}

	public static List<VendedorDTO> toListVendedorDTO(List<Vendedor> listDomains) {
		if (listDomains == null) {
			return Collections.<VendedorDTO> emptyList();
		}

		List<VendedorDTO> listDTOs = new ArrayList<VendedorDTO>();

		for (Vendedor domain : listDomains) {
			listDTOs.add(ConvertBeans.toVendedorDTO(domain));
		}

		return listDTOs;
	}

	public static Laboratorio toLaboratorio(LaboratorioDTO dto) {
		Cidade cidade = Cidade.find(dto.getCidadeId());

		Laboratorio domain = new Laboratorio(dto.getId(), dto.getMedLabValue(), dto.getNome(), dto.getNomeFantasia(),
				dto.getCnpjValue(), dto.getTelefoneValue(), dto.getEndereco(), dto.getEmail(), cidade);

		return domain;
	}

	public static LaboratorioDTO toLaboratorioDTO(Laboratorio domain) {
		LaboratorioDTO dto = new LaboratorioDTO();

		dto.setId(domain.getId());
		dto.setNome(domain.getNome());
		dto.setTelefoneValue(domain.getTelefone());
		dto.setTelefoneString(VitafarmaUtil.parsePhoneNumberToString(domain.getTelefone()));
		dto.setEndereco(domain.getEndereco());
		dto.setEmail(domain.getEmail());
		dto.setNomeFantasia(domain.getNomeFantasia());
		dto.setCnpjValue(domain.getCnpj());
		dto.setCnpjString(VitafarmaUtil.parseCnpjToString(domain.getCnpj()));

		if (domain.getMedLab() != null) {
			dto.setMedLabValue(domain.getMedLab());
			dto.setMedLabString(domain.getMedLab().toString());
		}

		dto.setCidadeId(domain.getCidade() == null ? null : domain.getCidade().getId());
		dto.setCidadeNome(domain.getCidade() == null ? null : domain.getCidade().getNome());

		dto.setEstadoId(domain.getCidade() == null || domain.getCidade().getEstado() == null ? null
				: domain.getCidade().getEstado().getId());
		dto.setEstadoNome(domain.getCidade() == null || domain.getCidade().getEstado() == null ? null
				: domain.getCidade().getEstado().getNome());

		dto.setDisplayText(domain.getNome());

		return dto;
	}

	public static List<Laboratorio> toListLaboratorio(List<LaboratorioDTO> listDTO) {
		if (listDTO == null) {
			return Collections.<Laboratorio> emptyList();
		}

		List<Laboratorio> listDomains = new ArrayList<Laboratorio>();

		for (LaboratorioDTO dto : listDTO) {
			listDomains.add(ConvertBeans.toLaboratorio(dto));
		}

		return listDomains;
	}

	public static List<LaboratorioDTO> toListLaboratorioDTO(List<Laboratorio> listDomains) {
		if (listDomains == null) {
			return Collections.<LaboratorioDTO> emptyList();
		}

		List<LaboratorioDTO> listDTOs = new ArrayList<LaboratorioDTO>();

		for (Laboratorio domain : listDomains) {
			listDTOs.add(ConvertBeans.toLaboratorioDTO(domain));
		}

		return listDTOs;
	}

	public static EntradaProdutoGroup toEntradaProdutoGroup(EntradaProdutoGroupDTO dto) {
		Fornecedor fornecedor = Fornecedor.find(dto.getFornecedorId());
		NotaFiscal notaFiscal = NotaFiscal.find(dto.getNotaFiscalId());

		EntradaProdutoGroup domain = new EntradaProdutoGroup(dto.getId(), dto.getDataEntradaValue(), fornecedor,
				notaFiscal);

		return domain;
	}

	public static EntradaProdutoGroupDTO toEntradaProdutoGroupDTO(EntradaProdutoGroup domain) {
		EntradaProdutoGroupDTO dto = new EntradaProdutoGroupDTO();

		dto.setId(domain.getId());

		// Data da Entrada
		Date dataEntrada = domain.getDataEntrada();
		dto.setDataEntradaValue(VitafarmaUtil.getVitafarmaDate(dataEntrada));
		dto.setDataEntradaString(VitafarmaUtil.buildDateTimeString(dataEntrada));

		if (dataEntrada != null) {
			dto.setEntradaProdutoHora(InputOutputUtils.getDateHour(dataEntrada));
			dto.setEntradaProdutoMinuto(InputOutputUtils.getDateMinute(dataEntrada));
			dto.setEntradaProdutoHoraMinutoString(VitafarmaUtil.shortTimeString(dataEntrada));
		}

		// Nota Fiscal
		NotaFiscal notaFiscal = domain.getNotaFiscal();
		if (notaFiscal != null) {
			dto.setNotaFiscalId(notaFiscal.getId());
			dto.setNotaFiscalCodigo(notaFiscal.getCodigo());
			dto.setNotaFiscalData(VitafarmaUtil.getVitafarmaDate(notaFiscal.getData()));
		}

		// Fornecedor
		Fornecedor fornecedor = domain.getFornecedor();
		dto.setFornecedorId(fornecedor == null ? null : domain.getFornecedor().getId());
		dto.setFornecedorString(fornecedor == null ? "" : domain.getFornecedor().getNomeFantasia());

		// Valor total da entrada
		VitafarmaCurrency valorTotal = new VitafarmaCurrency(domain.getValorTotalEntrada());
		dto.setValorTotalValue(valorTotal.getDoubleValue());
		dto.setValorTotalString(valorTotal.toString());

		dto.setDisplayText(domain.getId() == null ? "" : domain.getId().toString());

		return dto;
	}

	public static List<EntradaProdutoGroup> toListEntradaProdutoGroup(List<EntradaProdutoGroupDTO> listDTO) {
		if (listDTO == null) {
			return Collections.<EntradaProdutoGroup> emptyList();
		}

		List<EntradaProdutoGroup> listDomains = new ArrayList<EntradaProdutoGroup>();

		for (EntradaProdutoGroupDTO dto : listDTO) {
			listDomains.add(ConvertBeans.toEntradaProdutoGroup(dto));
		}

		return listDomains;
	}

	public static List<EntradaProdutoGroupDTO> toListEntradaProdutoGroupDTO(List<EntradaProdutoGroup> listDomains) {
		if (listDomains == null) {
			return Collections.<EntradaProdutoGroupDTO> emptyList();
		}

		List<EntradaProdutoGroupDTO> listDTOs = new ArrayList<EntradaProdutoGroupDTO>();

		for (EntradaProdutoGroup domain : listDomains) {
			listDTOs.add(ConvertBeans.toEntradaProdutoGroupDTO(domain));
		}

		return listDTOs;
	}

	public static Double getValorTotalItens(List<ItemVenda> itensVenda) {
		if (itensVenda == null) {
			return 0.0;
		}

		Double total = 0.0;

		for (ItemVenda itemVenda : itensVenda) {
			total += itemVenda.getPrecoFinal();
		}

		return total;
	}

	public static Double getSubtotalItens(List<ItemVenda> itensVenda) {
		if (itensVenda == null) {
			return 0.0;
		}

		Double total = 0.0;

		for (ItemVenda itemVenda : itensVenda) {
			total += (itemVenda.getPrecoUnitario() * itemVenda.getQuantidade());
		}

		return total;
	}
}
