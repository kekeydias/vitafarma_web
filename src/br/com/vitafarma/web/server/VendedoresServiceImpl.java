package br.com.vitafarma.web.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import br.com.vitafarma.domain.Cidade;
import br.com.vitafarma.domain.Vendedor;
import br.com.vitafarma.web.client.VendedoresService;
import br.com.vitafarma.web.server.util.ConvertBeans;
import br.com.vitafarma.web.shared.dtos.VendedorDTO;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;

public class VendedoresServiceImpl extends VitafarmaRemoteService implements VendedoresService {
	private static final long serialVersionUID = 8347811891158838654L;

	@Override
	public Boolean saveVendedor(VendedorDTO vendedorDTO) {

		Logger logger = Logger.getLogger("FuncionariosServiceImpl");

		try {
			Cidade cidade = Cidade.find(vendedorDTO.getCidadeId());

			Vendedor vendedor = new Vendedor(vendedorDTO.getId(), vendedorDTO.getNome(), vendedorDTO.getCpfValue(),
					vendedorDTO.getTelefone(), vendedorDTO.getEndereco(), vendedorDTO.getEmail(),
					vendedorDTO.getDataAdimissao(), vendedorDTO.getDataDemissao(), vendedorDTO.getSalarioValue(),
					cidade);

			vendedor.save();
		} catch (Exception ex) {
			logger.warning("Erro ao salvar um objeto vendedor: " + ex.getCause() + " - " + ex.getMessage());

			return false;
		}

		logger.info("Vendedor salvo com sucesso.");

		return true;
	}

	@Override
	public PagingLoadResult<VendedorDTO> getBuscaList(String nome, Long cpf, Long telefone, String endereco,
			String email, PagingLoadConfig config) {

		List<VendedorDTO> list = new ArrayList<VendedorDTO>();
		String orderBy = config.getSortField();

		if (orderBy != null) {
			if (config.getSortDir() != null && config.getSortDir().equals(SortDir.DESC)) {
				orderBy = (orderBy + " asc");
			} else {
				orderBy = (orderBy + " desc");
			}
		}

		List<Vendedor> vendedores = Vendedor.findVendedoresBy(nome, cpf, telefone, endereco, email);
		Collections.sort(vendedores);
		list.addAll(ConvertBeans.toListVendedorDTO(vendedores));

		BasePagingLoadResult<VendedorDTO> result = new BasePagingLoadResult<VendedorDTO>(list);
		result.setOffset(config.getOffset());
		result.setTotalLength(list.size());

		return result;
	}

	@Override
	public void remove(List<VendedorDTO> vendedoresDTOList) {
		for (VendedorDTO vendedorDTO : vendedoresDTOList) {
			Vendedor vendedor = ConvertBeans.toVendedor(vendedorDTO);

			if (vendedor != null) {
				vendedor.remove();
			}
		}
	}
}
