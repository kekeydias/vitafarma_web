package br.com.vitafarma.web.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import br.com.vitafarma.domain.EntradaProduto;
import br.com.vitafarma.domain.EntradaProdutoGroup;
import br.com.vitafarma.domain.Fornecedor;
import br.com.vitafarma.domain.NotaFiscal;
import br.com.vitafarma.domain.Produto;
import br.com.vitafarma.web.client.EntradaProdutosService;
import br.com.vitafarma.web.server.util.ConvertBeans;
import br.com.vitafarma.web.shared.dtos.EntradaProdutoDTO;
import br.com.vitafarma.web.shared.dtos.EntradaProdutoGroupDTO;
import br.com.vitafarma.web.shared.dtos.FornecedorDTO;
import br.com.vitafarma.web.shared.dtos.NotaFiscalDTO;
import br.com.vitafarma.web.shared.dtos.ParDTO;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.BaseListLoadResult;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;

public class EntradaProdutosServiceImpl extends VitafarmaRemoteService implements EntradaProdutosService {
	private static final long serialVersionUID = 8347811891158838654L;

	@Override
	public Boolean saveEntradaProdutoItem(EntradaProdutoGroupDTO entradaProdutoGroupDTO,
			EntradaProdutoDTO entradaProdutoDTO) {
		Logger logger = Logger.getLogger("EntradaProdutosServiceImpl");

		try {
			// Grupo de entrada de produtos
			EntradaProdutoGroup group = EntradaProdutoGroup.find(entradaProdutoGroupDTO.getId());

			Double quantidadeAntes = 0.0, quantidadeDepois = 0.0;

			// Quantidade antes da inclusão (Item)
			quantidadeAntes = this.searchQuantidadeItemEntradaProduto(entradaProdutoDTO.getId());

			// Produto (Item)
			Produto produto = Produto.find(entradaProdutoDTO.getProdutoId());

			// Fornecedor (Group)
			Fornecedor fornecedor = Fornecedor.find(entradaProdutoGroupDTO.getFornecedorId());

			// Nota Fiscal
			NotaFiscal notaFiscal = group.getNotaFiscal();

			// Configuring date and time
			Date dataEntrada = this.getDateTime(entradaProdutoGroupDTO.getDataEntradaValue(),
					entradaProdutoGroupDTO.getEntradaProdutoHora(), entradaProdutoGroupDTO.getEntradaProdutoMinuto(),
					0);

			// Monta o objeto a ser criado/alterado
			EntradaProduto entradaProduto = new EntradaProduto(entradaProdutoDTO.getId(), dataEntrada,
					entradaProdutoDTO.getQuantidadeValue(), entradaProdutoDTO.getPrecoUnitarioValue(), fornecedor,
					produto, notaFiscal, group);

			entradaProduto.save();

			// Quantidade após da inclusão (Item)
			quantidadeDepois = entradaProduto.getQuantidade();

			// Atualiza o estoque do produto
			this.updateEstoqueProduto(entradaProduto.getProduto(), quantidadeAntes, quantidadeDepois);
		} catch (Exception ex) {
			logger.warning("Erro ao salvar um objeto entrada de produto: " + ex.getCause() + " - " + ex.getMessage());

			return false;
		}

		logger.info("Entrada de produto salva com sucesso.");
		return true;
	}

	private void updateEstoqueProduto(Produto produto, Double quantidadeAntes, Double quantidadeDepois) {
		if (produto == null || quantidadeAntes == null || quantidadeDepois == null) {
			return;
		}

		Produto p = Produto.find(produto.getId());
		if (p == null) {
			return;
		}

		Logger logger = Logger.getLogger("EntradaProdutosServiceImpl");
		try {
			p.setEstoque(p.getEstoque() + quantidadeDepois - quantidadeAntes);

			p.save();
		} catch (Exception ex) {
			logger.warning("Erro ao atualizar o estoque de um produto: " + ex.getCause());

			return;
		}
	}

	@Override
	public PagingLoadResult<EntradaProdutoDTO> getBuscaListItens(Long codigoProduto, String nomeProduto,
			Long codigoFornecedor, String nomeFornecedor, Date dataEntrada, String codigoNotaFiscal,
			PagingLoadConfig config) {

		List<EntradaProdutoDTO> list = new ArrayList<EntradaProdutoDTO>();
		String orderBy = config.getSortField();

		if (orderBy != null) {
			if (config.getSortDir() != null && config.getSortDir().equals(SortDir.DESC)) {
				orderBy = (orderBy + " asc");
			} else {
				orderBy = (orderBy + " desc");
			}
		}

		List<EntradaProduto> entradaProdutos = EntradaProduto.findBy(codigoProduto, nomeProduto, codigoFornecedor,
				nomeFornecedor, dataEntrada, codigoNotaFiscal);
		Collections.sort(entradaProdutos);

		list.addAll(ConvertBeans.toListEntradaProdutoDTO(entradaProdutos));

		BasePagingLoadResult<EntradaProdutoDTO> result = new BasePagingLoadResult<EntradaProdutoDTO>(list);
		result.setOffset(config.getOffset());
		result.setTotalLength(list.size());

		return result;
	}

	@Override
	public void removeItens(List<EntradaProdutoDTO> entradaProdutosDTOList) {
		for (EntradaProdutoDTO entradaProdutoDTO : entradaProdutosDTOList) {
			EntradaProduto entradaProduto = ConvertBeans.toEntradaProduto(entradaProdutoDTO);
			this.removeItemAndUpdateEstoque(entradaProduto);
		}
	}

	private void removeItemAndUpdateEstoque(EntradaProduto entradaProduto) {
		if (entradaProduto != null) {
			// Remover o item de entrada
			entradaProduto.remove();

			// Atualizar estoque do produto correspondente à entrada
			this.updateEstoqueProduto(entradaProduto.getProduto(), entradaProduto.getQuantidade(), 0.0);
		}
	}

	@Override
	public ListLoadResult<EntradaProdutoDTO> getListItens(BasePagingLoadConfig loadConfig) {
		String nomeProduto = loadConfig.get("query").toString();

		List<EntradaProdutoDTO> listDTO = new ArrayList<EntradaProdutoDTO>();
		List<EntradaProduto> list = EntradaProduto.findByNomeProduto(nomeProduto);
		Collections.sort(list);

		for (EntradaProduto entradaProduto : list) {
			listDTO.add(ConvertBeans.toEntradaProdutoDTO(entradaProduto));
		}

		return new BaseListLoadResult<EntradaProdutoDTO>(listDTO);
	}

	private Double searchQuantidadeItemEntradaProduto(Long idEntradaProduto) {
		Double quantidade = 0.0;

		EntradaProduto ep = EntradaProduto.find(idEntradaProduto);
		if (ep != null) {
			quantidade = (ep.getQuantidade() == null ? 0.0 : ep.getQuantidade());
		}

		return quantidade;
	}

	@Override
	public NotaFiscalDTO finaNotaFiscalDTO(Long idNotaFiscal) {
		NotaFiscalDTO notaFiscalDTO = null;
		NotaFiscal notaFiscal = NotaFiscal.find(idNotaFiscal);
		if (notaFiscal != null) {
			notaFiscalDTO = ConvertBeans.toNotaFiscalDTO(notaFiscal);
		}
		return notaFiscalDTO;
	}

	@Override
	public PagingLoadResult<EntradaProdutoDTO> findEntradaProdutosPorNotaFiscal(NotaFiscalDTO notaFiscalDTO,
			Long codigoProduto, String nomeProduto, Long codigoFornecedor, String nomeFornecedor, Date dataEntrada,
			PagingLoadConfig config) {
		String orderBy = config.getSortField();

		if (orderBy != null) {
			if (config.getSortDir() != null && config.getSortDir().equals(SortDir.DESC)) {
				orderBy = (orderBy + " asc");
			} else {
				orderBy = (orderBy + " desc");
			}
		}

		List<EntradaProdutoDTO> listDTO = new ArrayList<EntradaProdutoDTO>();
		List<EntradaProduto> list = EntradaProduto.findBy(codigoProduto, nomeProduto, codigoFornecedor, nomeFornecedor,
				dataEntrada, notaFiscalDTO.getCodigo());
		Collections.sort(list);

		for (EntradaProduto entradaProduto : list) {
			listDTO.add(ConvertBeans.toEntradaProdutoDTO(entradaProduto));
		}

		BasePagingLoadResult<EntradaProdutoDTO> result = new BasePagingLoadResult<EntradaProdutoDTO>(listDTO);
		result.setOffset(config.getOffset());
		result.setTotalLength(list.size());

		return result;
	}

	@Override
	public ParDTO<NotaFiscalDTO, FornecedorDTO> getNotaFiscalAndFornecedorDTO(Long notaFiscalId, Long fornecedorId) {
		// Nota Fiscal da entrada
		NotaFiscalDTO notaFiscalDTO = null;
		NotaFiscal notaFiscal = NotaFiscal.find(notaFiscalId);
		if (notaFiscal != null) {
			notaFiscalDTO = ConvertBeans.toNotaFiscalDTO(notaFiscal);
		}

		// Fornecedor da entrada
		FornecedorDTO fornecedorDTO = null;
		Fornecedor fornecedor = Fornecedor.find(fornecedorId);
		if (fornecedor != null) {
			fornecedorDTO = ConvertBeans.toFornecedorDTO(fornecedor);
		}

		// Monta o objeto de retorno
		ParDTO<NotaFiscalDTO, FornecedorDTO> result = ParDTO.create(notaFiscalDTO, fornecedorDTO);

		return result;
	}

	@Override
	public PagingLoadResult<EntradaProdutoDTO> getItensEntradaProdutoList(EntradaProdutoGroupDTO entradaProdutoGroupDTO,
			PagingLoadConfig config) {

		EntradaProdutoGroup group = EntradaProdutoGroup.find(entradaProdutoGroupDTO.getId());

		List<EntradaProduto> listDomains = EntradaProduto.findByGroup(group);
		Collections.sort(listDomains);

		List<EntradaProdutoDTO> listDTOs = ConvertBeans.toListEntradaProdutoDTO(listDomains);
		String orderBy = config.getSortField();

		if (orderBy != null) {
			if (config.getSortDir() != null && config.getSortDir().equals(SortDir.DESC)) {
				orderBy = (orderBy + " asc");
			} else {
				orderBy = (orderBy + " desc");
			}
		}

		BasePagingLoadResult<EntradaProdutoDTO> result = new BasePagingLoadResult<EntradaProdutoDTO>(listDTOs);
		result.setOffset(config.getOffset());
		result.setTotalLength(listDTOs.size());

		return result;
	}

	@Override
	public EntradaProdutoGroupDTO getEntradaProdutoGroupDTO(Long groupId) {
		if (groupId == null) {
			return null;
		}

		EntradaProdutoGroupDTO result = null;
		EntradaProdutoGroup group = EntradaProdutoGroup.find(groupId);

		if (group != null) {
			result = ConvertBeans.toEntradaProdutoGroupDTO(group);
		}

		return result;
	}

	@Override
	public PagingLoadResult<EntradaProdutoGroupDTO> getBuscaListGroups(Long codigoFornecedor, String nomeFornecedor,
			Date dataEntrada, String codigoNotaFiscal, PagingLoadConfig config) {
		List<EntradaProdutoGroupDTO> list = new ArrayList<EntradaProdutoGroupDTO>();
		String orderBy = config.getSortField();

		if (orderBy != null) {
			if (config.getSortDir() != null && config.getSortDir().equals(SortDir.DESC)) {
				orderBy = (orderBy + " asc");
			} else {
				orderBy = (orderBy + " desc");
			}
		}

		List<EntradaProdutoGroup> entradaProdutosGroup = EntradaProdutoGroup.findBy(codigoFornecedor, nomeFornecedor,
				dataEntrada, codigoNotaFiscal);
		Collections.sort(entradaProdutosGroup);

		list.addAll(ConvertBeans.toListEntradaProdutoGroupDTO(entradaProdutosGroup));

		BasePagingLoadResult<EntradaProdutoGroupDTO> result = new BasePagingLoadResult<EntradaProdutoGroupDTO>(list);
		result.setOffset(config.getOffset());
		result.setTotalLength(list.size());

		return result;
	}

	@Override
	public boolean saveEntradaProdutoGroup(EntradaProdutoGroupDTO entradaProdutoGroupDTO) {
		Logger logger = Logger.getLogger("EntradaProdutosServiceImpl");
		try {
			Fornecedor fornecedor = Fornecedor.find(entradaProdutoGroupDTO.getFornecedorId());

			// Verifica-se se já existe uma nota fiscal com o código
			// informado. Caso não exista, cria-se uma nova nota fiscal
			NotaFiscal notaFiscal = NotaFiscal
					.findByCodigoNotaFiscalExactly(entradaProdutoGroupDTO.getNotaFiscalCodigo());

			if (notaFiscal == null) {
				notaFiscal = new NotaFiscal(null, entradaProdutoGroupDTO.getNotaFiscalCodigo(),
						entradaProdutoGroupDTO.getDataEntradaValue(), fornecedor);

				notaFiscal.save();
			} else {
				notaFiscal.refresh();
			}

			// Configuring date and time
			Date dataEntrada = this.getDateTime(entradaProdutoGroupDTO.getDataEntradaValue(),
					entradaProdutoGroupDTO.getEntradaProdutoHora(), entradaProdutoGroupDTO.getEntradaProdutoMinuto(),
					0);

			EntradaProdutoGroup entradaProdutoGroup = new EntradaProdutoGroup(entradaProdutoGroupDTO.getId(),
					dataEntrada, fornecedor, notaFiscal);

			entradaProdutoGroup.save();
		} catch (Exception ex) {
			logger.warning("Erro ao salvar um objeto de grupo de entrada de produtos: " + ex.getCause() + " - "
					+ ex.getMessage());
			return false;
		}

		logger.info("Grupo de entrada de produtos salva com sucesso.");

		return true;
	}

	@Override
	public void removeGroup(List<EntradaProdutoGroupDTO> entradaProdutosGroupDTOList) {
		for (EntradaProdutoGroupDTO entradaProdutoGroupDTO : entradaProdutosGroupDTOList) {
			EntradaProdutoGroup entradaProdutoGroup = ConvertBeans.toEntradaProdutoGroup(entradaProdutoGroupDTO);

			if (entradaProdutoGroup != null) {
				List<EntradaProduto> itensToRemove = EntradaProduto.findByGroup(entradaProdutoGroup);

				for (EntradaProduto entradaProduto : itensToRemove) {
					this.removeItemAndUpdateEstoque(entradaProduto);
				}

				entradaProdutoGroup.remove();
			}
		}
	}
}
