package br.com.vitafarma.web.client;

import java.util.Date;
import java.util.List;

import br.com.vitafarma.web.shared.dtos.ClienteDTO;
import br.com.vitafarma.web.shared.dtos.FornecedorDTO;
import br.com.vitafarma.web.shared.dtos.ProdutoDTO;
import br.com.vitafarma.web.shared.dtos.RelatorioBalancoDTO;

import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProdutosServiceAsync {
	void saveProduto(ProdutoDTO produtoDTO, AsyncCallback<Boolean> callback);

	void getBuscaList(Long codigo, Long medAbc, String nome, Double preco,
			String nomeLaboratorio, PagingLoadConfig config,
			AsyncCallback<PagingLoadResult<ProdutoDTO>> callback);

	void remove(List<ProdutoDTO> produtosDTOList, AsyncCallback<Void> callback);

	void getList(BasePagingLoadConfig loadConfig,
			AsyncCallback<ListLoadResult<ProdutoDTO>> callback);

	void updateEstoque(ProdutoDTO produtoDTO, AsyncCallback<Boolean> callback);

	void getRelatorioBalancoPeriodo(ProdutoDTO produtoDTO,
			ClienteDTO clienteDTO, FornecedorDTO fornecedorDTO,
			Date dataInicio, Date dataFim,
			AsyncCallback<List<RelatorioBalancoDTO>> callback);

	void getProdutoDTO(Long produtoId, AsyncCallback<ProdutoDTO> asyncCallback);
}
