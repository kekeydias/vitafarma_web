package br.com.vitafarma.web.client;

import java.util.List;

import br.com.vitafarma.web.shared.dtos.FornecedorDTO;

import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("fornecedores")
public interface FornecedoresService extends RemoteService {
	Boolean saveFornecedor(FornecedorDTO fornecedoresDTO);

	PagingLoadResult<FornecedorDTO> getBuscaList(String nome,
			String nomeFantasia, Long cnpj, Long telefone, String endereco,
			String email, PagingLoadConfig config);

	void remove(List<FornecedorDTO> fornecedoresDTOList);

	ListLoadResult<FornecedorDTO> getList(BasePagingLoadConfig loadConfig);
}
