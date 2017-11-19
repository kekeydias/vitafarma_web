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
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EntradaProdutosServiceAsync {
	void saveEntradaProdutoGroup(EntradaProdutoGroupDTO entradaProdutoGroupDTO,
			AsyncCallback<Boolean> callback);

	void saveEntradaProdutoItem(EntradaProdutoGroupDTO entradaProdutoGroupDTO,
			EntradaProdutoDTO entradaProdutoDTO, AsyncCallback<Boolean> callback);

	void getBuscaListItens(Long codigoProduto, String nomeProduto,
			Long codigoFornecedor, String nomeFornecedor, Date dataEntrada,
			String codigoNotaFiscal, PagingLoadConfig config,
			AsyncCallback<PagingLoadResult<EntradaProdutoDTO>> callback);

	void removeItens(List<EntradaProdutoDTO> entradaProdutosDTOList,
			AsyncCallback<Void> callback);

	void removeGroup(List<EntradaProdutoGroupDTO> entradaProdutosGroupDTOList,
			AsyncCallback<Void> callback);

	void getListItens(BasePagingLoadConfig loadConfig,
			AsyncCallback<ListLoadResult<EntradaProdutoDTO>> callback);

	void finaNotaFiscalDTO(Long idNotaFiscal,
			AsyncCallback<NotaFiscalDTO> callback);

	void findEntradaProdutosPorNotaFiscal(NotaFiscalDTO notaFiscalDTO,
			Long codigoProduto, String nomeProduto, Long codigoFornecedor,
			String nomeFornecedor, Date dataEntrada, PagingLoadConfig config,
			AsyncCallback<PagingLoadResult<EntradaProdutoDTO>> callback);

	void getNotaFiscalAndFornecedorDTO(Long notaFiscalId, Long fornecedorId,
			AsyncCallback<ParDTO<NotaFiscalDTO, FornecedorDTO>> asyncCallback);

	void getItensEntradaProdutoList(
			EntradaProdutoGroupDTO entradaProdutoGroupDTO,
			PagingLoadConfig config,
			AsyncCallback<PagingLoadResult<EntradaProdutoDTO>> callback);

	void getEntradaProdutoGroupDTO(Long groupId,
			AsyncCallback<EntradaProdutoGroupDTO> asyncCallback);

	void getBuscaListGroups(Long codigoFornecedor, String nomeFornecedor,
			Date dataEntrada, String codigoNotaFiscal,
			PagingLoadConfig loadConfig,
			AsyncCallback<PagingLoadResult<EntradaProdutoGroupDTO>> callback);
}
