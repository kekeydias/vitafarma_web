package br.com.vitafarma.web.client;

import java.util.List;

import br.com.vitafarma.web.shared.dtos.CidadeDTO;

import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CidadesServiceAsync {
	void saveCidade(CidadeDTO cidadeDTO, AsyncCallback<Boolean> callback);

	void getBuscaList(String nomeCidade, String nomeEstado, PagingLoadConfig config,
			AsyncCallback<PagingLoadResult<CidadeDTO>> callback);

	void remove(List<CidadeDTO> cidadesDTOList, AsyncCallback<Void> callback);

	void getList(BasePagingLoadConfig loadConfig,
			AsyncCallback<ListLoadResult<CidadeDTO>> callback);

	void getCidadeDTO(Long cidadeId,
			AsyncCallback<CidadeDTO> asyncCallback);
}
