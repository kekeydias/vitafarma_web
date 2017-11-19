package br.com.vitafarma.web.shared.util.view;

import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.shared.dtos.AbstractDTO;
import br.com.vitafarma.web.shared.dtos.ClienteDTO;

import com.extjs.gxt.ui.client.data.BaseListLoadResult;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ClientesComboBox extends ComboBox<ClienteDTO> {

	public ClientesComboBox() {
		RpcProxy<ListLoadResult<ClienteDTO>> proxy = new RpcProxy<ListLoadResult<ClienteDTO>>() {
			@Override
			public void load(Object loadConfig,
					AsyncCallback<ListLoadResult<ClienteDTO>> callback) {
				Services.clientes().getList(callback);
			}
		};

		setStore(new ListStore<ClienteDTO>(
				new BaseListLoader<BaseListLoadResult<ClienteDTO>>(proxy)));

		setFieldLabel("Cliente");
		setEmptyText("Selecione um Cliente");
		setDisplayField(AbstractDTO.PROPERTY_DISPLAY_TEXT);
		setEditable(false);
		setTriggerAction(TriggerAction.ALL);
	}
}
