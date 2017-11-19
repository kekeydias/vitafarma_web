package br.com.vitafarma.web.client;

import java.util.Date;
import java.util.List;

import br.com.vitafarma.web.shared.dtos.ItemVendaDTO;
import br.com.vitafarma.web.shared.dtos.VendaDTO;

import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("vendas")
public interface VendasService extends RemoteService {
	Boolean saveVenda(VendaDTO vendaDTO);

	PagingLoadResult<VendaDTO> getBuscaList(Long codigoCliente,
			String nomeCliente, Long cpfCliente, Date dataVenda,
			PagingLoadConfig config);

	void remove(List<VendaDTO> vendasDTOList);

	PagingLoadResult<ItemVendaDTO> getItensVendaList(VendaDTO vendaDTO,
			PagingLoadConfig config);

	Boolean incluirItensVenda(VendaDTO vendaDTO,
			List<ItemVendaDTO> itensVendaDTO);

	void removeItemVenda(List<ItemVendaDTO> list);

	ListLoadResult<VendaDTO> getList();

	Boolean emitiuCupomVenda(VendaDTO vendaDTO);
}
