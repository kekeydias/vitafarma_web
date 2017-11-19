package br.com.vitafarma.web.client;

import java.util.Date;
import java.util.List;

import br.com.vitafarma.web.shared.dtos.ItemVendaDTO;
import br.com.vitafarma.web.shared.dtos.VendaDTO;

import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface VendasServiceAsync {
	void saveVenda(VendaDTO vendaDTO, AsyncCallback<Boolean> callback);

	void getBuscaList(Long codigoCliente, String nomeCliente, Long cpfCliente,
			Date dataVenda, PagingLoadConfig config,
			AsyncCallback<PagingLoadResult<VendaDTO>> callback);

	void remove(List<VendaDTO> vendasDTOList, AsyncCallback<Void> callback);

	void getItensVendaList(VendaDTO vendaDTO, PagingLoadConfig config,
			AsyncCallback<PagingLoadResult<ItemVendaDTO>> callback);

	void incluirItensVenda(VendaDTO vendaDTO, List<ItemVendaDTO> itensVendaDTO,
			AsyncCallback<Boolean> callback);

	void removeItemVenda(List<ItemVendaDTO> list, AsyncCallback<Void> callback);

	void getList(AsyncCallback<ListLoadResult<VendaDTO>> callback);

	void emitiuCupomVenda(VendaDTO vendaDTO, AsyncCallback<Boolean> callback);
}
