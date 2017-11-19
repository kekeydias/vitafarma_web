package br.com.vitafarma.web.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import br.com.vitafarma.domain.Cidade;
import br.com.vitafarma.domain.Estado;
import br.com.vitafarma.web.client.CidadesService;
import br.com.vitafarma.web.server.util.ConvertBeans;
import br.com.vitafarma.web.shared.dtos.CidadeDTO;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.BaseListLoadResult;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;

public class CidadesServiceImpl extends VitafarmaRemoteService implements CidadesService {
	private static final long serialVersionUID = 8347811891158838654L;

	@Override
	public Boolean saveCidade(CidadeDTO cidadeDTO) {
		Logger logger = Logger.getLogger("CidadesServiceImpl");

		try {
			Estado estado = Estado.find(cidadeDTO.getEstadoId());

			Cidade cidade = new Cidade(cidadeDTO.getId(), cidadeDTO.getNome(), estado);

			cidade.save();
		} catch (Exception ex) {
			logger.warning("Erro ao salvar um objeto cidade: " + ex.getCause());

			return false;
		}

		logger.info("Cidade salvo com sucesso.");

		return true;
	}

	@Override
	public PagingLoadResult<CidadeDTO> getBuscaList(String nomeCidade, String nomeEstado, PagingLoadConfig config) {
		List<CidadeDTO> list = new ArrayList<CidadeDTO>();

		String orderBy = config.getSortField();

		if (orderBy != null) {
			if (config.getSortDir() != null && config.getSortDir().equals(SortDir.DESC)) {
				orderBy = (orderBy + " asc");
			} else {
				orderBy = (orderBy + " desc");
			}
		}

		List<Cidade> cidades = Cidade.findBy(nomeCidade, nomeEstado);
		Collections.sort(cidades);
		list.addAll(ConvertBeans.toListCidadeDTO(cidades));

		BasePagingLoadResult<CidadeDTO> result = new BasePagingLoadResult<CidadeDTO>(list);
		result.setOffset(config.getOffset());
		result.setTotalLength(list.size());

		return result;
	}

	@Override
	public void remove(List<CidadeDTO> cidadesDTOList) {
		for (CidadeDTO cidadeDTO : cidadesDTOList) {
			Cidade cidade = ConvertBeans.toCidade(cidadeDTO);

			if (cidade != null) {
				cidade.remove();
			}
		}
	}

	@Override
	public ListLoadResult<CidadeDTO> getList(BasePagingLoadConfig loadConfig) {
		List<CidadeDTO> listDTO = new ArrayList<CidadeDTO>();
		String nomeCidade = loadConfig.get("query").toString();

		List<Cidade> list = Cidade.findBy(nomeCidade, null);
		Collections.sort(list);
		listDTO.addAll(ConvertBeans.toListCidadeDTO(list));

		return new BaseListLoadResult<CidadeDTO>(listDTO);
	}

	@Override
	public CidadeDTO getCidadeDTO(Long cidadeId) {
		if (cidadeId == null) {
			return null;
		}

		CidadeDTO cidadeDTO = null;

		Cidade cidade = Cidade.find(cidadeId);

		if (cidade != null) {
			cidadeDTO = ConvertBeans.toCidadeDTO(cidade);
		}

		return cidadeDTO;
	}
}
