package br.com.vitafarma.web.client;

import java.util.List;

import br.com.vitafarma.web.shared.dtos.ClienteDTO;

import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ClientesServiceAsync {
	void saveCliente(ClienteDTO clienteDTO, AsyncCallback<Boolean> callback);

	void getBuscaList(String nome, Long cpf, Long telefone,
			String endereco, String email, PagingLoadConfig config,
			AsyncCallback<PagingLoadResult<ClienteDTO>> callback);

	void remove(List<ClienteDTO> clientesDTOList, AsyncCallback<Void> callback);

	void getList(AsyncCallback<ListLoadResult<ClienteDTO>> callback);

	void getClienteDTO(Long clienteId, AsyncCallback<ClienteDTO> asyncCallback);
}
