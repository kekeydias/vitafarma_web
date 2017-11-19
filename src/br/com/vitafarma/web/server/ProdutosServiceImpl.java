package br.com.vitafarma.web.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import br.com.vitafarma.domain.Cliente;
import br.com.vitafarma.domain.EntradaProduto;
import br.com.vitafarma.domain.Fornecedor;
import br.com.vitafarma.domain.ItemVenda;
import br.com.vitafarma.domain.Laboratorio;
import br.com.vitafarma.domain.Produto;
import br.com.vitafarma.domain.Venda;
import br.com.vitafarma.web.client.ProdutosService;
import br.com.vitafarma.web.server.util.ConvertBeans;
import br.com.vitafarma.web.shared.dtos.ClienteDTO;
import br.com.vitafarma.web.shared.dtos.FornecedorDTO;
import br.com.vitafarma.web.shared.dtos.ProdutoDTO;
import br.com.vitafarma.web.shared.dtos.RelatorioBalancoDTO;
import br.com.vitafarma.web.shared.util.VitafarmaUtil;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.BaseListLoadResult;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;

public class ProdutosServiceImpl extends VitafarmaRemoteService implements ProdutosService {
	private static final long serialVersionUID = 8347811891158838654L;

	@Override
	public Boolean saveProduto(ProdutoDTO produtoDTO) {
		Logger logger = Logger.getLogger("ProdutosServiceImpl");

		try {
			Laboratorio laboratorio = Laboratorio.find(produtoDTO.getLaboratorioId());

			Produto produto = new Produto(produtoDTO.getId(), produtoDTO.getMedAbcValue(), produtoDTO.getNome(),
					produtoDTO.getDescricao(), produtoDTO.getPrecoValue(), produtoDTO.getEstoque(),
					produtoDTO.getUnidadeEntrada(), produtoDTO.getUnidadeVenda(), laboratorio);

			produto.save();
		} catch (Exception ex) {
			logger.warning("Erro ao salvar um objeto produto: " + ex.getCause() + " - " + ex.getMessage());

			return false;
		}

		logger.info("Produto salvo com sucesso.");

		return true;
	}

	@Override
	public PagingLoadResult<ProdutoDTO> getBuscaList(Long codigo, Long medAbc, String nome, Double preco,
			String nomeLaboratorio, PagingLoadConfig config) {
		List<ProdutoDTO> list = new ArrayList<ProdutoDTO>();

		String orderBy = config.getSortField();

		if (orderBy != null) {
			if (config.getSortDir() != null && config.getSortDir().equals(SortDir.DESC)) {
				orderBy = (orderBy + " asc");
			} else {
				orderBy = (orderBy + " desc");
			}
		}

		List<Produto> produtos = Produto.findBy(codigo, medAbc, nome, preco, nomeLaboratorio);

		Collections.sort(produtos);

		list.addAll(ConvertBeans.toListProdutoDTO(produtos));

		BasePagingLoadResult<ProdutoDTO> result = new BasePagingLoadResult<ProdutoDTO>(list);

		result.setOffset(config.getOffset());
		result.setTotalLength(list.size());

		return result;
	}

	@Override
	public void remove(List<ProdutoDTO> produtosDTOList) {
		for (ProdutoDTO produtoDTO : produtosDTOList) {
			Produto produto = ConvertBeans.toProduto(produtoDTO);

			if (produto != null) {
				produto.remove();
			}
		}
	}

	@Override
	public ListLoadResult<ProdutoDTO> getList(BasePagingLoadConfig loadConfig) {
		List<ProdutoDTO> listDTO = new ArrayList<ProdutoDTO>();

		String nomeProduto = loadConfig.get("query").toString();

		// Método utilizado pela classe 'ProdutosComboBox',
		// evitando que se carregue todos os produtos na query
		List<Produto> list = null;

		if (!VitafarmaUtil.isBlank(nomeProduto)) {
			list = Produto.findBy(null, null, nomeProduto, null, null);
		} else {
			list = Produto.findMaxResultsLimited(20);
		}

		Collections.sort(list);

		for (Produto produto : list) {
			listDTO.add(ConvertBeans.toProdutoDTO(produto));
		}

		return new BaseListLoadResult<ProdutoDTO>(listDTO);
	}

	@Override
	public Boolean updateEstoque(ProdutoDTO produtoDTO) {
		Logger logger = Logger.getLogger("ProdutosServiceImpl");

		try {
			Produto produto = Produto.find(produtoDTO.getId());

			List<Produto> produtos = new ArrayList<Produto>();

			if (produto != null) {
				produtos.add(produto);
			} else {
				produtos.addAll(Produto.findAll());
			}

			// Atualizando o estoque
			Produto.updateEstoque(produtos);
		} catch (Exception ex) {
			logger.warning("Erro ao atualizar o estoque do(s) produto(s): " + ex.getCause() + " - " + ex.getMessage());

			return false;
		}

		logger.info("Estoque atualizado com sucesso.");

		return true;
	}

	@Override
	public List<RelatorioBalancoDTO> getRelatorioBalancoPeriodo(ProdutoDTO produtoDTO, ClienteDTO clienteDTO,
			FornecedorDTO fornecedorDTO, Date dataInicio, Date dataFim) {
		Produto produto = null;

		if (produtoDTO != null) {
			produto = Produto.find(produtoDTO.getId());
		}

		Cliente cliente = null;

		if (clienteDTO != null) {
			cliente = Cliente.find(clienteDTO.getId());
		}

		Fornecedor fornecedor = null;

		if (fornecedorDTO != null) {
			fornecedor = Fornecedor.find(fornecedorDTO.getId());
		}

		return this.getRelatorioBalancoPeriodo(produto, cliente, fornecedor, dataInicio, dataFim);
	}

	public List<RelatorioBalancoDTO> getRelatorioBalancoPeriodo(Produto produto, Cliente cliente, Fornecedor fornecedor,
			Date dataInicio, Date dataFim) {
		List<RelatorioBalancoDTO> relatoriosBalancoDTO = new ArrayList<RelatorioBalancoDTO>();

		// Relatório de Vendas
		Set<Venda> setVendas = new HashSet<Venda>();

		List<ItemVenda> itensVenda = ItemVenda.findBy(produto, cliente, dataInicio, dataFim);

		for (ItemVenda itemVenda : itensVenda) {
			setVendas.add(itemVenda.getVenda());
		}

		List<Venda> listVendas = new ArrayList<Venda>(setVendas);

		Collections.sort(listVendas);

		for (Venda venda : listVendas) {
			relatoriosBalancoDTO.add(ConvertBeans.getRelatorioBalancoDTO(venda,
					VitafarmaUtil.getVitafarmaDate(venda.getDataVenda()), venda.getValorTotalVenda()));
		}

		// Relatório de Entradas de Produtos
		List<EntradaProduto> entradas = EntradaProduto.findBy(produto, fornecedor, dataInicio, dataFim);

		Collections.sort(entradas);

		for (EntradaProduto entradaProduto : entradas) {
			relatoriosBalancoDTO.add(ConvertBeans.getRelatorioBalancoDTO(entradaProduto,
					VitafarmaUtil.getVitafarmaDate(entradaProduto.getDataEntrada()),
					entradaProduto.getValorTotalItem()));
		}

		return relatoriosBalancoDTO;
	}

	@Override
	public ProdutoDTO getProdutoDTO(Long produtoId) {
		if (produtoId == null) {
			return null;
		}

		ProdutoDTO produtoDTO = null;

		Produto produto = Produto.find(produtoId);

		if (produto != null) {
			produtoDTO = ConvertBeans.toProdutoDTO(produto);
		}

		return produtoDTO;
	}
}
