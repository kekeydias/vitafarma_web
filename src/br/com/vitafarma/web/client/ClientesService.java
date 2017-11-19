package br.com.vitafarma.web.client;

import java.util.List;

import br.com.vitafarma.web.shared.dtos.ClienteDTO;

import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("clientes")
public interface ClientesService extends RemoteService {
	Boolean saveCliente(ClienteDTO clienteDTO);

	PagingLoadResult<ClienteDTO> getBuscaList(String nome, Long cpf,
			Long telefone, String endereco, String email,
			PagingLoadConfig config);

	void remove(List<ClienteDTO> clientesDTOList);

	ListLoadResult<ClienteDTO> getList();

	ClienteDTO getClienteDTO(Long clienteId);
}
