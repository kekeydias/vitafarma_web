package br.com.vitafarma.web.server;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import br.com.vitafarma.domain.Cenario;
import br.com.vitafarma.domain.Cliente;
import br.com.vitafarma.domain.CupomVenda;
import br.com.vitafarma.domain.ItemVenda;
import br.com.vitafarma.domain.Venda;
import br.com.vitafarma.web.client.VendasService;
import br.com.vitafarma.web.server.util.ConvertBeans;
import br.com.vitafarma.web.shared.dtos.ItemVendaDTO;
import br.com.vitafarma.web.shared.dtos.VendaDTO;
import br.com.vitafarma.web.shared.util.VitafarmaUtil;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.BaseListLoadResult;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;

public class VendasServiceImpl extends VitafarmaRemoteService implements VendasService {
	private static final long serialVersionUID = 8347811891158838654L;

	@Override
	public Boolean saveVenda(VendaDTO vendaDTO) {
		Logger logger = Logger.getLogger("VendasServiceImpl");

		try {
			Cliente cliente = Cliente.find(vendaDTO.getClienteId());
			CupomVenda cupomVenda = CupomVenda.find(vendaDTO.getCupomVendaId());

			Cenario cenario = null;
			if (vendaDTO.getVendaId() == null) {
				cenario = Cenario.getCurrentCenario();
			} else {
				cenario = Cenario.find(vendaDTO.getCenarioId());
			}

			// Configuring date and time
			Date dataVenda = this.getDateTime(vendaDTO.getVendaDataValue(), vendaDTO.getVendaHora(),
					vendaDTO.getVendaMinuto(), 0);

			Venda venda = new Venda(vendaDTO.getVendaId(), dataVenda, vendaDTO.getDescontoValue(), cliente, cenario,
					cupomVenda);

			// Verifica se os campos informados para essa venda são válidos
			if (!this.validadeVenda(venda)) {
				return false;
			}

			venda.save();
		} catch (Exception ex) {
			logger.warning("Erro ao salvar um objeto venda: " + ex.getCause() + " - " + ex.getMessage());

			return false;
		}

		logger.info("Venda salvo com sucesso.");

		return true;
	}

	@Override
	public PagingLoadResult<VendaDTO> getBuscaList(Long codigoCliente, String nomeCliente, Long cpfCliente,
			Date dataVenda, PagingLoadConfig config) {
		List<Venda> vendas = Venda.findBy(codigoCliente, nomeCliente, cpfCliente, dataVenda);

		Collections.sort(vendas);

		List<VendaDTO> vendasDTO = ConvertBeans.toListVendaDTO(vendas);

		BasePagingLoadResult<VendaDTO> result = new BasePagingLoadResult<VendaDTO>(vendasDTO);

		result.setOffset(config.getOffset());
		result.setTotalLength(vendas.size());

		return result;
	}

	@Override
	public PagingLoadResult<ItemVendaDTO> getItensVendaList(VendaDTO vendaDTO, PagingLoadConfig config) {
		Venda venda = Venda.find(vendaDTO.getVendaId());
		List<ItemVenda> itens = ItemVenda.findByVenda(venda);
		Collections.sort(itens);

		List<ItemVendaDTO> itensDTO = ConvertBeans.toListItemVendaDTO(itens);

		String orderBy = config.getSortField();
		if (orderBy != null) {
			if (config.getSortDir() != null && config.getSortDir().equals(SortDir.DESC)) {
				orderBy = (orderBy + " asc");
			} else {
				orderBy = (orderBy + " desc");
			}
		}

		BasePagingLoadResult<ItemVendaDTO> result = new BasePagingLoadResult<ItemVendaDTO>(itensDTO);
		result.setTotalLength(itens.size());

		return result;
	}

	@Override
	public Boolean incluirItensVenda(VendaDTO vendaDTO, List<ItemVendaDTO> itensVendaDTO) {
		Logger logger = Logger.getLogger("VendasServiceImpl");

		try {
			List<ItemVenda> itensVenda = ConvertBeans.toListItemVenda(itensVendaDTO);

			// Verifica se os campos informados
			// para esse da venda são válidos
			if (!this.validadeItemVenda(itensVenda)) {
				return false;
			}

			for (ItemVenda itemVenda : itensVenda) {
				itemVenda.save();
			}
		} catch (Exception ex) {
			logger.warning("Erro ao salvar um item da venda: " + ex.getCause());
			return false;
		}

		logger.info("Item(ns) salvo(s) com sucesso.");
		return true;
	}

	@Override
	public void removeItemVenda(List<ItemVendaDTO> listDTO) {
		List<ItemVenda> itens = ConvertBeans.toListItemVenda(listDTO);
		for (ItemVenda item : itens) {
			item.remove();
		}
	}

	@Override
	public ListLoadResult<VendaDTO> getList() {
		List<Venda> vendas = Venda.findBy(null, null, null, null);
		Collections.sort(vendas);

		List<VendaDTO> listVendasDTO = ConvertBeans.toListVendaDTO(vendas);

		BaseListLoadResult<VendaDTO> result = new BaseListLoadResult<VendaDTO>(listVendasDTO);
		return result;
	}

	@Override
	public void remove(List<VendaDTO> vendasDTOList) {
		List<Venda> listVendas = ConvertBeans.toListVenda(vendasDTOList);
		for (Venda venda : listVendas) {
			venda.remove();
		}
	}

	private boolean validadeVenda(Venda venda) {
		if (venda == null) {
			return false;
		}

		if (venda.getCliente() == null || venda.getDataVenda() == null) {
			return false;
		}

		if (venda.getDesconto() != null) {
			if (!VitafarmaUtil.validadeValorDesconto(venda.getDesconto())) {
				return false;
			}
		}

		return true;
	}

	private boolean validadeItemVenda(Collection<ItemVenda> itensVenda) {
		for (ItemVenda itemVenda : itensVenda) {
			if (!this.validadeItemVenda(itemVenda)) {
				return false;
			}
		}

		return true;
	}

	private boolean validadeItemVenda(ItemVenda itemVenda) {
		if (itemVenda == null) {
			return false;
		}

		if (itemVenda.getDesconto() != null) {
			if (!VitafarmaUtil.validadeValorDesconto(itemVenda.getDesconto())) {
				return false;
			}
		}

		return true;
	}

	@Override
	public Boolean emitiuCupomVenda(VendaDTO vendaDTO) {
		Venda venda = Venda.find(vendaDTO.getVendaId());
		CupomVenda cupomVenda = CupomVenda.findByVenda(venda);

		if (cupomVenda != null) {
			return true;
		}

		return false;
	}
}
