package br.com.vitafarma.web.client;

import java.util.List;

import br.com.vitafarma.web.shared.dtos.EstadoDTO;

import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EstadosServiceAsync {
	public void saveEstado(EstadoDTO estadoDTO, AsyncCallback<Boolean> callback);

	public void getBuscaList(String nome, String sigla, PagingLoadConfig config,
			AsyncCallback<PagingLoadResult<EstadoDTO>> callback);

	public void remove(List<EstadoDTO> estadosDTOList, AsyncCallback<Void> callback);

	public void getList(BasePagingLoadConfig loadConfig, AsyncCallback<ListLoadResult<EstadoDTO>> callback);

	public void getEstadoDTO(Long estadoId, AsyncCallback<EstadoDTO> asyncCallback);
}
