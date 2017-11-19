package br.com.vitafarma.web.client;

import java.util.List;

import br.com.vitafarma.web.shared.dtos.EstadoDTO;

import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("estados")
public interface EstadosService extends RemoteService {
	public Boolean saveEstado(EstadoDTO estadoDTO);

	public PagingLoadResult<EstadoDTO> getBuscaList(String nome, String sigla, PagingLoadConfig config);

	public void remove(List<EstadoDTO> estadosDTOList);

	public ListLoadResult<EstadoDTO> getList(BasePagingLoadConfig loadConfig);

	public EstadoDTO getEstadoDTO(Long estadoId);
}
