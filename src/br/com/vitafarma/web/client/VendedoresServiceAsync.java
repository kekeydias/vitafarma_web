package br.com.vitafarma.web.client;

import java.util.List;

import br.com.vitafarma.web.shared.dtos.VendedorDTO;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface VendedoresServiceAsync {
	void saveVendedor(VendedorDTO vendedorDTO,
			AsyncCallback<Boolean> callback);

	void getBuscaList(String nome, Long cpf, Long telefone, String endereco,
			String email, PagingLoadConfig config,
			AsyncCallback<PagingLoadResult<VendedorDTO>> callback);

	void remove(List<VendedorDTO> vendedoresDTOList,
			AsyncCallback<Void> callback);
}
