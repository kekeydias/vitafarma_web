package br.com.vitafarma.web.client;

import java.util.List;

import br.com.vitafarma.web.shared.dtos.VendedorDTO;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("vendedores")
public interface VendedoresService extends RemoteService {
	Boolean saveVendedor(VendedorDTO vendedorDTO);

	PagingLoadResult<VendedorDTO> getBuscaList(String nome, Long cpf,
			Long telefone, String endereco, String email,
			PagingLoadConfig config);

	void remove(List<VendedorDTO> vendedoresDTOList);
}
