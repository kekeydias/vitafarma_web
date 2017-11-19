package br.com.vitafarma.web.client;

import java.util.List;

import br.com.vitafarma.web.shared.dtos.FuncionarioDTO;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FuncionariosServiceAsync {
	void saveFuncionario(FuncionarioDTO funcionarioDTO,
			AsyncCallback<Boolean> callback);

	void getBuscaList(String nome, Long cpf, Long telefone, String endereco,
			String email, PagingLoadConfig config,
			AsyncCallback<PagingLoadResult<FuncionarioDTO>> callback);

	void remove(List<FuncionarioDTO> funcionariosDTOList,
			AsyncCallback<Void> callback);
}
