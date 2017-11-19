package br.com.vitafarma.web.server;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.vitafarma.domain.Authority;
import br.com.vitafarma.domain.Usuario;
import br.com.vitafarma.util.Encryption;
import br.com.vitafarma.web.client.UsuariosService;
import br.com.vitafarma.web.server.util.ConvertBeans;
import br.com.vitafarma.web.shared.dtos.UsuarioDTO;
import br.com.vitafarma.web.shared.util.VitafarmaUtil;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;

public class UsuariosServiceImpl extends VitafarmaRemoteService implements UsuariosService {
	private static final long serialVersionUID = 5672570072070386404L;

	@Override
	public UsuarioDTO getUsuario(String username) {
		return ConvertBeans.toUsuarioDTO(Usuario.find(username));
	}

	@Override
	public UsuarioDTO getCurrentUser() throws NoSuchAlgorithmException {
		Usuario usuario = this.getUsuario();

		return ((usuario == null) ? null : ConvertBeans.toUsuarioDTO(usuario));
	}

	@Override
	public PagingLoadResult<UsuarioDTO> getBuscaList(String nome, String username, String email,
			PagingLoadConfig config) throws NoSuchAlgorithmException {
		List<UsuarioDTO> list = new ArrayList<UsuarioDTO>();
		String orderBy = config.getSortField();

		if (orderBy != null) {
			if (config.getSortDir() != null && config.getSortDir().equals(SortDir.DESC)) {
				orderBy = (orderBy + " asc");
			} else {
				orderBy = (orderBy + " desc");
			}
		}

		List<Usuario> usuarios = Usuario.findAll();
		Collections.sort(usuarios);

		for (Usuario usuario : usuarios) {
			list.add(ConvertBeans.toUsuarioDTO(usuario));
		}

		BasePagingLoadResult<UsuarioDTO> result = new BasePagingLoadResult<UsuarioDTO>(list);

		result.setOffset(config.getOffset());
		result.setTotalLength(list.size());

		return result;
	}

	@Override
	public void save(UsuarioDTO usuarioDTO) throws NoSuchAlgorithmException {
		Usuario usuario = Usuario.find(usuarioDTO.getUsername());

		if (usuario != null) {
			Authority authority = Authority.find(usuarioDTO.getAuthorityUsername());

			usuario.setNome(usuarioDTO.getNome());
			usuario.setEmail(usuarioDTO.getEmail());
			usuario.setAuthority(authority);

			if (!VitafarmaUtil.isBlank(usuarioDTO.getPassword())) {
				usuario.setPassword(Encryption.toMD5(usuarioDTO.getPassword()));
			}

			usuario.save();
		} else {
			usuario = ConvertBeans.toUsuario(usuarioDTO);
			usuario.setPassword(Encryption.toMD5(usuario.getPassword()));
			usuario.setEnabled(true);

			Authority authority = new Authority();
			authority.setAuthority("ROLE_USER");
			authority.setUsername(usuario.getUsername());
			authority.save();

			usuario.setAuthority(authority);
			usuario.save();
		}
	}

	@Override
	public void remove(List<UsuarioDTO> usuarioDTOList) {
		for (UsuarioDTO usuarioDTO : usuarioDTOList) {
			Usuario usuario = Usuario.find(usuarioDTO.getUsername());

			if (usuario != null) {
				usuario.remove();
			}
		}
	}

	@Override
	public Boolean avoidSessionExpire() {
		return true;
	}
}
