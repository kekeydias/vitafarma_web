package br.com.vitafarma.web.client;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import br.com.vitafarma.web.shared.dtos.CenarioDTO;

import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("cenarios")
public interface CenariosService extends RemoteService {
	CenarioDTO getCenario(Long id);

	PagingLoadResult<CenarioDTO> getList(PagingLoadConfig config) throws NoSuchAlgorithmException;

	PagingLoadResult<CenarioDTO> getBuscaList(Integer ano, Integer semestre,
			PagingLoadConfig config) throws NoSuchAlgorithmException;

	void editar(CenarioDTO cenarioDTO);

	void criar(CenarioDTO cenarioDTO);

	void clonar(CenarioDTO cenarioDTO);

	void remove(List<CenarioDTO> cenarioDTOList);

	CenarioDTO getMasterData() throws NoSuchAlgorithmException;
}
