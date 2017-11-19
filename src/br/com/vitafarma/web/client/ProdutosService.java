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
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("produtos")
public interface ProdutosService extends RemoteService {
	Boolean saveProduto(ProdutoDTO produtoDTO);

	PagingLoadResult<ProdutoDTO> getBuscaList(Long codigo, Long medAbc,
			String nome, Double preco, String nomeLaboratorio,
			PagingLoadConfig config);

	void remove(List<ProdutoDTO> produtosDTOList);

	ListLoadResult<ProdutoDTO> getList(BasePagingLoadConfig loadConfig);

	Boolean updateEstoque(ProdutoDTO produtoDTO);

	List<RelatorioBalancoDTO> getRelatorioBalancoPeriodo(ProdutoDTO produtoDTO,
			ClienteDTO clienteDTO, FornecedorDTO fornecedorDTO,
			Date dataInicio, Date dataFim);

	ProdutoDTO getProdutoDTO(Long produtoId);
}
