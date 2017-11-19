package br.com.vitafarma.web.server;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import br.com.vitafarma.domain.Cenario;
import br.com.vitafarma.web.client.CenariosService;
import br.com.vitafarma.web.server.util.CenarioUtil;
import br.com.vitafarma.web.server.util.ConvertBeans;
import br.com.vitafarma.web.shared.dtos.CenarioDTO;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;

public class CenariosServiceImpl extends VitafarmaRemoteService implements CenariosService {
	private static final long serialVersionUID = -5951529933566541220L;

	@Override
	public CenarioDTO getCenario(Long id) {
		Cenario cenario = Cenario.find(id);
		return (cenario == null ? null : ConvertBeans.toCenarioDTO(cenario));
	}

	@Override
	public PagingLoadResult<CenarioDTO> getList(PagingLoadConfig config) throws NoSuchAlgorithmException {
		List<CenarioDTO> list = new ArrayList<CenarioDTO>();
		String orderBy = config.getSortField();

		if (orderBy != null) {
			if (config.getSortDir() != null && config.getSortDir().equals(SortDir.DESC)) {
				orderBy = (orderBy + " asc");
			} else {
				orderBy = (orderBy + " desc");
			}
		}

		List<Cenario> listDomains = Cenario.findAll();

		for (Cenario cenario : listDomains) {
			list.add(ConvertBeans.toCenarioDTO(cenario));
		}

		BasePagingLoadResult<CenarioDTO> result = new BasePagingLoadResult<CenarioDTO>(list);

		result.setOffset(config.getOffset());
		result.setTotalLength(Cenario.findAll().size());

		return result;
	}

	@Override
	public PagingLoadResult<CenarioDTO> getBuscaList(Integer ano, Integer semestre, PagingLoadConfig config) throws NoSuchAlgorithmException {
		List<CenarioDTO> list = new ArrayList<CenarioDTO>();
		String orderBy = config.getSortField();

		if (orderBy != null) {
			if (config.getSortDir() != null && config.getSortDir().equals(SortDir.DESC)) {
				orderBy = (orderBy + " asc");
			} else {
				orderBy = (orderBy + " desc");
			}
		}

		List<Cenario> listCenarios = Cenario.findAll();

		for (Cenario cenario : listCenarios) {
			list.add(ConvertBeans.toCenarioDTO(cenario));
		}

		BasePagingLoadResult<CenarioDTO> result = new BasePagingLoadResult<CenarioDTO>(list);

		result.setOffset(config.getOffset());
		result.setTotalLength(list.size());

		return result;
	}

	@Override
	public void editar(CenarioDTO cenarioDTO) {
		Cenario cenario = ConvertBeans.toCenario(cenarioDTO);

		cenario.save();
	}

	@Override
	public void criar(CenarioDTO cenarioDTO) {
		Cenario cenario = ConvertBeans.toCenario(cenarioDTO);

		CenarioUtil cenarioUtil = new CenarioUtil();
		cenarioUtil.criarCenario(cenario);
	}

	@Override
	public void clonar(CenarioDTO cenarioDTO) {
		Cenario cenario = ConvertBeans.toCenario(cenarioDTO);

		CenarioUtil cenarioUtil = new CenarioUtil();
		cenarioUtil.clonarCenario(cenario);
	}

	@Override
	public void remove(List<CenarioDTO> cenarioDTOList) {
		for (CenarioDTO cenarioDTO : cenarioDTOList) {
			ConvertBeans.toCenario(cenarioDTO).remove();
		}
	}

	@Override
	public CenarioDTO getMasterData() throws NoSuchAlgorithmException {
		CenarioDTO cenarioDTO = new CenarioDTO();
		Cenario cenario = Cenario.getCurrentCenario();
		cenarioDTO = ConvertBeans.toCenarioDTO(cenario);
		return cenarioDTO;
	}
}
