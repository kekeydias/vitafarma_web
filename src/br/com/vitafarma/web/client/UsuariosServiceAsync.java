package br.com.vitafarma.web.client;

import java.util.List;

import br.com.vitafarma.web.shared.dtos.UsuarioDTO;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UsuariosServiceAsync {
	void getUsuario(String username, AsyncCallback<UsuarioDTO> callback);

	void save(UsuarioDTO usuarioDTO, AsyncCallback<Void> callback);

	void remove(List<UsuarioDTO> usuarioDTOList, AsyncCallback<Void> callback);

	void avoidSessionExpire(AsyncCallback<Boolean> callback);

	void getBuscaList(String nome, String username, String email, PagingLoadConfig config,
			AsyncCallback<PagingLoadResult<UsuarioDTO>> callback);

	void getCurrentUser(AsyncCallback<UsuarioDTO> callback);
}
