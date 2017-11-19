package br.com.vitafarma.web.shared.util.view;

import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.shared.dtos.AbstractDTO;
import br.com.vitafarma.web.shared.dtos.VendaDTO;

import com.extjs.gxt.ui.client.data.BaseListLoadResult;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class VendasComboBox extends ComboBox<VendaDTO> {

	public VendasComboBox() {
		RpcProxy<ListLoadResult<VendaDTO>> proxy = new RpcProxy<ListLoadResult<VendaDTO>>() {
			@Override
			public void load(Object loadConfig,
					AsyncCallback<ListLoadResult<VendaDTO>> callback) {
				Services.vendas().getList(callback);
			}
		};

		setStore(new ListStore<VendaDTO>(
				new BaseListLoader<BaseListLoadResult<VendaDTO>>(proxy)));

		setFieldLabel("Venda");
		setEmptyText("Selecione uma Venda");
		setDisplayField(AbstractDTO.PROPERTY_DISPLAY_TEXT);
		setEditable(false);
		setTriggerAction(TriggerAction.ALL);
	}
}
