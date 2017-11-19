package br.com.vitafarma.web.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import br.com.vitafarma.domain.Cidade;
import br.com.vitafarma.domain.Fornecedor;
import br.com.vitafarma.web.client.FornecedoresService;
import br.com.vitafarma.web.server.util.ConvertBeans;
import br.com.vitafarma.web.shared.dtos.FornecedorDTO;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.BaseListLoadResult;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;

public class FornecedoresServiceImpl extends VitafarmaRemoteService implements FornecedoresService {
	private static final long serialVersionUID = 8347811891158838654L;

	@Override
	public Boolean saveFornecedor(FornecedorDTO fornecedorDTO) {

		Logger logger = Logger.getLogger("FornecedoresServiceImpl");

		try {
			Cidade cidade = Cidade.find(fornecedorDTO.getCidadeId());

			Fornecedor fornecedor = new Fornecedor(fornecedorDTO.getId(), fornecedorDTO.getNome(),
					fornecedorDTO.getTelefoneValue(), fornecedorDTO.getEndereco(), fornecedorDTO.getEmail(),
					fornecedorDTO.getNomeFantasia(), fornecedorDTO.getCnpjValue(), cidade);

			fornecedor.save();
		} catch (Exception ex) {
			logger.warning("Erro ao salvar um objeto fornecedor: " + ex.getCause());

			return false;
		}

		logger.info("Fornecedor salvo com sucesso.");
		return true;
	}

	@Override
	public PagingLoadResult<FornecedorDTO> getBuscaList(String nome, String nomeFantasia, Long cnpj, Long telefone,
			String endereco, String email, PagingLoadConfig config) {

		List<FornecedorDTO> list = new ArrayList<FornecedorDTO>();
		String orderBy = config.getSortField();

		if (orderBy != null) {
			if (config.getSortDir() != null && config.getSortDir().equals(SortDir.DESC)) {
				orderBy = (orderBy + " asc");
			} else {
				orderBy = (orderBy + " desc");
			}
		}

		List<Fornecedor> fornecedores = Fornecedor.findBy(nome, nomeFantasia, cnpj, telefone, endereco, email);
		Collections.sort(fornecedores);

		list.addAll(ConvertBeans.toListFornecedorDTO(fornecedores));

		BasePagingLoadResult<FornecedorDTO> result = new BasePagingLoadResult<FornecedorDTO>(list);
		result.setOffset(config.getOffset());
		result.setTotalLength(list.size());

		return result;
	}

	@Override
	public void remove(List<FornecedorDTO> fornecedoresDTOList) {
		for (FornecedorDTO fornecedorDTO : fornecedoresDTOList) {
			Fornecedor fornecedor = ConvertBeans.toFornecedor(fornecedorDTO);

			if (fornecedor != null) {
				fornecedor.remove();
			}
		}
	}

	@Override
	public ListLoadResult<FornecedorDTO> getList(BasePagingLoadConfig loadConfig) {
		List<FornecedorDTO> listDTO = new ArrayList<FornecedorDTO>();
		String nomeFornecedor = loadConfig.get("query").toString();

		List<Fornecedor> list = Fornecedor.findBy(nomeFornecedor, null, null, null, null, null);
		Collections.sort(list);

		for (Fornecedor fornecedor : list) {
			listDTO.add(ConvertBeans.toFornecedorDTO(fornecedor));
		}

		return new BaseListLoadResult<FornecedorDTO>(listDTO);
	}
}
