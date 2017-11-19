package br.com.vitafarma.web.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import br.com.vitafarma.domain.Cidade;
import br.com.vitafarma.domain.Laboratorio;
import br.com.vitafarma.web.client.LaboratoriosService;
import br.com.vitafarma.web.server.util.ConvertBeans;
import br.com.vitafarma.web.shared.dtos.LaboratorioDTO;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.BaseListLoadResult;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;

public class LaboratoriosServiceImpl extends VitafarmaRemoteService implements LaboratoriosService {
	private static final long serialVersionUID = 8347811891158838654L;

	@Override
	public Boolean saveLaboratorio(LaboratorioDTO laboratorioDTO) {

		Logger logger = Logger.getLogger("LaboratoriosServiceImpl");

		try {
			Cidade cidade = Cidade.find(laboratorioDTO.getCidadeId());

			Laboratorio laboratorio = new Laboratorio(laboratorioDTO.getId(), laboratorioDTO.getMedLabValue(),
					laboratorioDTO.getNome(), laboratorioDTO.getNomeFantasia(), laboratorioDTO.getCnpjValue(),
					laboratorioDTO.getTelefoneValue(), laboratorioDTO.getEndereco(), laboratorioDTO.getEmail(), cidade);

			laboratorio.save();
		} catch (Exception ex) {
			logger.warning("Erro ao salvar um objeto laboratorio: " + ex.getCause());

			return false;
		}

		logger.info("Laboratorio salvo com sucesso.");

		return true;
	}

	@Override
	public PagingLoadResult<LaboratorioDTO> getBuscaList(String nome, String nomeFantasia, Long cnpj, Long telefone,
			String endereco, String email, PagingLoadConfig config) {

		List<LaboratorioDTO> list = new ArrayList<LaboratorioDTO>();
		String orderBy = config.getSortField();

		if (orderBy != null) {
			if (config.getSortDir() != null && config.getSortDir().equals(SortDir.DESC)) {
				orderBy = (orderBy + " asc");
			} else {
				orderBy = (orderBy + " desc");
			}
		}

		List<Laboratorio> laboratorios = Laboratorio.findBy(nome, nomeFantasia, cnpj, telefone, endereco, email);
		Collections.sort(laboratorios);

		list.addAll(ConvertBeans.toListLaboratorioDTO(laboratorios));

		BasePagingLoadResult<LaboratorioDTO> result = new BasePagingLoadResult<LaboratorioDTO>(list);
		result.setOffset(config.getOffset());
		result.setTotalLength(list.size());

		return result;
	}

	@Override
	public void remove(List<LaboratorioDTO> laboratoriosDTOList) {
		for (LaboratorioDTO laboratorioDTO : laboratoriosDTOList) {
			Laboratorio laboratorio = ConvertBeans.toLaboratorio(laboratorioDTO);

			if (laboratorio != null) {
				laboratorio.remove();
			}
		}
	}

	@Override
	public ListLoadResult<LaboratorioDTO> getList(BasePagingLoadConfig loadConfig) {
		List<LaboratorioDTO> listDTO = new ArrayList<LaboratorioDTO>();
		String nomeLaboratorio = loadConfig.get("query").toString();

		List<Laboratorio> list = Laboratorio.findBy(nomeLaboratorio, null, null, null, null, null);
		Collections.sort(list);

		for (Laboratorio laboratorio : list) {
			listDTO.add(ConvertBeans.toLaboratorioDTO(laboratorio));
		}

		return new BaseListLoadResult<LaboratorioDTO>(listDTO);
	}
}
