package br.com.vitafarma.web.client;

import java.util.List;

import br.com.vitafarma.web.shared.dtos.FornecedorDTO;

import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FornecedoresServiceAsync {
	void saveFornecedor(FornecedorDTO fornecedorDTO,
			AsyncCallback<Boolean> callback);

	void getBuscaList(String nome, String nomeFantasia, Long cnpj,
			Long telefone, String endereco, String email,
			PagingLoadConfig config,
			AsyncCallback<PagingLoadResult<FornecedorDTO>> callback);

	void remove(List<FornecedorDTO> fornecedoresDTOList,
			AsyncCallback<Void> callback);

	void getList(BasePagingLoadConfig loadConfig,
			AsyncCallback<ListLoadResult<FornecedorDTO>> callback);
}
