package br.com.vitafarma.web.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import br.com.vitafarma.domain.Estado;
import br.com.vitafarma.web.client.EstadosService;
import br.com.vitafarma.web.server.util.ConvertBeans;
import br.com.vitafarma.web.shared.dtos.EstadoDTO;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.BaseListLoadResult;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;

public class EstadosServiceImpl extends VitafarmaRemoteService implements EstadosService {
	private static final long serialVersionUID = 8347811891158838654L;

	@Override
	public Boolean saveEstado(EstadoDTO estadoDTO) {

		Logger logger = Logger.getLogger("EstadosServiceImpl");

		try {
			Estado estado = new Estado(estadoDTO.getId(), estadoDTO.getNome(), estadoDTO.getSigla());

			estado.save();
		} catch (Exception ex) {
			logger.warning("Erro ao salvar um objeto estado: " + ex.getCause());

			return false;
		}

		logger.info("Estado salvo com sucesso.");

		return true;
	}

	@Override
	public PagingLoadResult<EstadoDTO> getBuscaList(String nome, String sigla, PagingLoadConfig config) {

		List<EstadoDTO> list = new ArrayList<EstadoDTO>();
		String orderBy = config.getSortField();

		if (orderBy != null) {
			if (config.getSortDir() != null && config.getSortDir().equals(SortDir.DESC)) {
				orderBy = (orderBy + " asc");
			} else {
				orderBy = (orderBy + " desc");
			}
		}

		List<Estado> estados = Estado.findBy(nome, sigla);
		Collections.sort(estados);

		list.addAll(ConvertBeans.toListEstadoDTO(estados));

		BasePagingLoadResult<EstadoDTO> result = new BasePagingLoadResult<EstadoDTO>(list);
		result.setOffset(config.getOffset());
		result.setTotalLength(list.size());

		return result;
	}

	@Override
	public void remove(List<EstadoDTO> estadosDTOList) {
		for (EstadoDTO estadoDTO : estadosDTOList) {
			Estado estado = ConvertBeans.toEstado(estadoDTO);

			if (estado != null) {
				estado.remove();
			}
		}
	}

	@Override
	public ListLoadResult<EstadoDTO> getList(BasePagingLoadConfig loadConfig) {
		List<EstadoDTO> listDTO = new ArrayList<EstadoDTO>();
		String nomeEstado = loadConfig.get("query").toString();

		List<Estado> list = Estado.findBy(nomeEstado, null);
		Collections.sort(list);

		listDTO.addAll(ConvertBeans.toListEstadoDTO(list));

		return new BaseListLoadResult<EstadoDTO>(listDTO);
	}

	@Override
	public EstadoDTO getEstadoDTO(Long estadoId) {
		if (estadoId == null) {
			return null;
		}

		EstadoDTO estadoDTO = null;

		Estado estado = Estado.find(estadoId);

		if (estado != null) {
			estadoDTO = ConvertBeans.toEstadoDTO(estado);
		}

		return estadoDTO;
	}
}
