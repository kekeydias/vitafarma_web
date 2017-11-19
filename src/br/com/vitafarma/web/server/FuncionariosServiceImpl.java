package br.com.vitafarma.web.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import br.com.vitafarma.domain.Cidade;
import br.com.vitafarma.domain.Funcionario;
import br.com.vitafarma.web.client.FuncionariosService;
import br.com.vitafarma.web.server.util.ConvertBeans;
import br.com.vitafarma.web.shared.dtos.FuncionarioDTO;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;

public class FuncionariosServiceImpl extends VitafarmaRemoteService implements FuncionariosService {
	private static final long serialVersionUID = 8347811891158838654L;

	@Override
	public Boolean saveFuncionario(FuncionarioDTO funcionarioDTO) {

		Logger logger = Logger.getLogger("FuncionariosServiceImpl");

		try {
			Cidade cidade = Cidade.find(funcionarioDTO.getCidadeId());

			Funcionario funcionario = new Funcionario(funcionarioDTO.getId(), funcionarioDTO.getNome(),
					funcionarioDTO.getCpfValue(), funcionarioDTO.getTelefone(), funcionarioDTO.getEndereco(),
					funcionarioDTO.getEmail(), funcionarioDTO.getDataAdimissao(), funcionarioDTO.getDataDemissao(),
					funcionarioDTO.getSalarioValue(), cidade);

			funcionario.save();
		} catch (Exception ex) {
			logger.warning("Erro ao salvar um objeto funcionario: " + ex.getCause());

			return false;
		}

		logger.info("Funcionario salvo com sucesso.");

		return true;
	}

	@Override
	public PagingLoadResult<FuncionarioDTO> getBuscaList(String nome, Long cpf, Long telefone, String endereco,
			String email, PagingLoadConfig config) {
		List<FuncionarioDTO> list = new ArrayList<FuncionarioDTO>();

		String orderBy = config.getSortField();

		if (orderBy != null) {
			if (config.getSortDir() != null && config.getSortDir().equals(SortDir.DESC)) {
				orderBy = (orderBy + " asc");
			} else {
				orderBy = (orderBy + " desc");
			}
		}

		List<Funcionario> funcionarios = Funcionario.findBy(nome, cpf, telefone, endereco, email);

		Collections.sort(funcionarios);

		list.addAll(ConvertBeans.toListFuncionarioDTO(funcionarios));

		BasePagingLoadResult<FuncionarioDTO> result = new BasePagingLoadResult<FuncionarioDTO>(list);
		result.setOffset(config.getOffset());
		result.setTotalLength(list.size());

		return result;
	}

	@Override
	public void remove(List<FuncionarioDTO> funcionariosDTOList) {
		for (FuncionarioDTO funcionarioDTO : funcionariosDTOList) {
			Funcionario funcionario = ConvertBeans.toFuncionario(funcionarioDTO);

			if (funcionario != null) {
				funcionario.remove();
			}
		}
	}
}
