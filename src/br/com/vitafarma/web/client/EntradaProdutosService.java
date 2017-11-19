package br.com.vitafarma.web.client;

import java.util.Date;
import java.util.List;

import br.com.vitafarma.web.shared.dtos.EntradaProdutoDTO;
import br.com.vitafarma.web.shared.dtos.EntradaProdutoGroupDTO;
import br.com.vitafarma.web.shared.dtos.FornecedorDTO;
import br.com.vitafarma.web.shared.dtos.NotaFiscalDTO;
import br.com.vitafarma.web.shared.dtos.ParDTO;

import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("entradaProdutos")
public interface EntradaProdutosService extends RemoteService {
	Boolean saveEntradaProdutoItem(EntradaProdutoGroupDTO entradaProdutoGroupDTO,
			EntradaProdutoDTO entradaProdutoDTO);

	PagingLoadResult<EntradaProdutoDTO> getBuscaListItens(Long codigoProduto,
			String nomeProduto, Long codigoFornecedor, String nomeFornecedor,
			Date dataEntrada, String codigoNotaFiscal, PagingLoadConfig config);

	void removeItens(List<EntradaProdutoDTO> entradaProdutosDTOList);

	ListLoadResult<EntradaProdutoDTO> getListItens(BasePagingLoadConfig loadConfig);

	NotaFiscalDTO finaNotaFiscalDTO(Long idNotaFiscal);

	PagingLoadResult<EntradaProdutoDTO> findEntradaProdutosPorNotaFiscal(
			NotaFiscalDTO notaFiscalDTO, Long codigoProduto,
			String nomeProduto, Long codigoFornecedor, String nomeFornecedor,
			Date dataEntrada, PagingLoadConfig config);

	ParDTO<NotaFiscalDTO, FornecedorDTO> getNotaFiscalAndFornecedorDTO(
			Long notaFiscalId, Long fornecedorId);

	PagingLoadResult<EntradaProdutoDTO> getItensEntradaProdutoList(
			EntradaProdutoGroupDTO entradaProdutoGroupDTO,
			PagingLoadConfig config);

	EntradaProdutoGroupDTO getEntradaProdutoGroupDTO(Long groupId);

	PagingLoadResult<EntradaProdutoGroupDTO> getBuscaListGroups(
			Long codigoFornecedor, String nomeFornecedor, Date dataEntrada,
			String codigoNotaFiscal, PagingLoadConfig loadConfig);

	boolean saveEntradaProdutoGroup(
			EntradaProdutoGroupDTO entradaProdutoGroupDTO);

	void removeGroup(List<EntradaProdutoGroupDTO> entradaProdutosGroupDTOList);
}
