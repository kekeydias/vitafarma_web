package br.com.vitafarma.web.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import br.com.vitafarma.domain.Cidade;
import br.com.vitafarma.domain.Cliente;
import br.com.vitafarma.web.client.ClientesService;
import br.com.vitafarma.web.server.util.ConvertBeans;
import br.com.vitafarma.web.shared.dtos.ClienteDTO;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.BaseListLoadResult;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;

public class ClientesServiceImpl extends VitafarmaRemoteService implements ClientesService {
	private static final long serialVersionUID = 8347811891158838654L;

	@Override
	public Boolean saveCliente(ClienteDTO clienteDTO) {
		Logger logger = Logger.getLogger("ClientesServiceImpl");

		try {
			Cidade cidade = Cidade.find(clienteDTO.getCidadeId());

			Cliente cliente = new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getCpfValue(),
					clienteDTO.getTelefone(), clienteDTO.getEndereco(), clienteDTO.getEmail(), cidade);

			cliente.save();
		} catch (Exception ex) {
			logger.warning("Erro ao salvar um objeto cliente: " + ex.getCause());

			return false;
		}

		logger.info("Cliente salvo com sucesso.");

		return true;
	}

	@Override
	public PagingLoadResult<ClienteDTO> getBuscaList(String nome, Long cpf, Long telefone, String endereco,
			String email, PagingLoadConfig config) {

		List<ClienteDTO> list = new ArrayList<ClienteDTO>();
		String orderBy = config.getSortField();

		if (orderBy != null) {
			if (config.getSortDir() != null && config.getSortDir().equals(SortDir.DESC)) {
				orderBy = (orderBy + " asc");
			} else {
				orderBy = (orderBy + " desc");
			}
		}

		List<Cliente> clientes = Cliente.findBy(nome, cpf, telefone, endereco, email);
		Collections.sort(clientes);

		list.addAll(ConvertBeans.toListClienteDTO(clientes));

		BasePagingLoadResult<ClienteDTO> result = new BasePagingLoadResult<ClienteDTO>(list);
		result.setOffset(config.getOffset());
		result.setTotalLength(list.size());

		return result;
	}

	@Override
	public void remove(List<ClienteDTO> clientesDTOList) {
		for (ClienteDTO clienteDTO : clientesDTOList) {
			Cliente cliente = ConvertBeans.toCliente(clienteDTO);

			if (cliente != null) {
				cliente.remove();
			}
		}
	}

	@Override
	public ListLoadResult<ClienteDTO> getList() {
		List<Cliente> clientes = Cliente.findBy(null, null, null, null, null);
		Collections.sort(clientes);

		List<ClienteDTO> clientesDTO = ConvertBeans.toListClienteDTO(clientes);

		BaseListLoadResult<ClienteDTO> result = new BaseListLoadResult<ClienteDTO>(clientesDTO);
		return result;
	}

	@Override
	public ClienteDTO getClienteDTO(Long clienteId) {
		if (clienteId == null) {
			return null;
		}

		ClienteDTO clienteDTO = null;

		Cliente cliente = Cliente.find(clienteId);
		if (cliente != null) {
			clienteDTO = ConvertBeans.toClienteDTO(cliente);
		}

		return clienteDTO;
	}
}
