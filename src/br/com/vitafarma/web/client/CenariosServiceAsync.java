package br.com.vitafarma.web.client;

import java.util.List;

import br.com.vitafarma.web.shared.dtos.CenarioDTO;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CenariosServiceAsync {
	void getCenario(Long id, AsyncCallback<CenarioDTO> callback);

	void getList(PagingLoadConfig config,
			AsyncCallback<PagingLoadResult<CenarioDTO>> callback);

	void getBuscaList(Integer ano, Integer semestre, PagingLoadConfig config,
			AsyncCallback<PagingLoadResult<CenarioDTO>> callback);

	void editar(CenarioDTO cenarioDTO, AsyncCallback<Void> callback);

	void criar(CenarioDTO cenarioDTO, AsyncCallback<Void> callback);

	void clonar(CenarioDTO cenarioDTO, AsyncCallback<Void> callback);

	void remove(List<CenarioDTO> cenarioDTOList, AsyncCallback<Void> callback);

	void getMasterData(AsyncCallback<CenarioDTO> callback);
}
