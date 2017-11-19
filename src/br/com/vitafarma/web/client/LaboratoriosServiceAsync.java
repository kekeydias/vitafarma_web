package br.com.vitafarma.web.client;

import java.util.List;

import br.com.vitafarma.web.shared.dtos.LaboratorioDTO;

import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LaboratoriosServiceAsync {
	void saveLaboratorio(LaboratorioDTO laboratorioDTO,
			AsyncCallback<Boolean> callback);

	void getBuscaList(String nome, String nomeFantasia, Long cnpj,
			Long telefone, String endereco, String email,
			PagingLoadConfig config,
			AsyncCallback<PagingLoadResult<LaboratorioDTO>> callback);

	void remove(List<LaboratorioDTO> laboratoriosDTOList,
			AsyncCallback<Void> callback);

	void getList(BasePagingLoadConfig loadConfig,
			AsyncCallback<ListLoadResult<LaboratorioDTO>> callback);
}
