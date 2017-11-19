package br.com.vitafarma.web.client;

import java.util.List;

import br.com.vitafarma.web.shared.dtos.CidadeDTO;

import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("cidades")
public interface CidadesService extends RemoteService {
	Boolean saveCidade(CidadeDTO cidadeDTO);

	PagingLoadResult<CidadeDTO> getBuscaList(String nomeCidade, String nomeEstado, PagingLoadConfig config);

	void remove(List<CidadeDTO> cidadesDTOList);

	ListLoadResult<CidadeDTO> getList(BasePagingLoadConfig loadConfig);

	CidadeDTO getCidadeDTO(Long cidadeId);
}
