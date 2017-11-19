package br.com.vitafarma.web.client;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import br.com.vitafarma.web.shared.dtos.UsuarioDTO;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("usuarios")
public interface UsuariosService extends RemoteService {
	UsuarioDTO getUsuario(String username);

	void save(UsuarioDTO usuarioDTO) throws NoSuchAlgorithmException;

	void remove(List<UsuarioDTO> usuarioDTOList);

	Boolean avoidSessionExpire();

	PagingLoadResult<UsuarioDTO> getBuscaList(String nome, String username,
			String email, PagingLoadConfig config) throws NoSuchAlgorithmException;

	UsuarioDTO getCurrentUser() throws NoSuchAlgorithmException;
}
