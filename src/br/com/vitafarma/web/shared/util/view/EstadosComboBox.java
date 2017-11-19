package br.com.vitafarma.web.shared.util.view;

import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.shared.dtos.AbstractDTO;
import br.com.vitafarma.web.shared.dtos.EstadoDTO;

import com.extjs.gxt.ui.client.data.BaseListLoadResult;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class EstadosComboBox extends ComboBox<EstadoDTO> {

	public EstadosComboBox() {
		RpcProxy<ListLoadResult<EstadoDTO>> proxy = new RpcProxy<ListLoadResult<EstadoDTO>>() {
			@Override
			public void load(Object loadConfig,
					AsyncCallback<ListLoadResult<EstadoDTO>> callback) {
				Services.estados().getList((BasePagingLoadConfig) loadConfig,
						callback);
			}
		};

		this.setStore(new ListStore<EstadoDTO>(
				new BaseListLoader<BaseListLoadResult<EstadoDTO>>(proxy)));

		this.setFieldLabel("Estado");
		this.setEmptyText("Selecione um Estado");
		this.setDisplayField(AbstractDTO.PROPERTY_DISPLAY_TEXT);
		this.setEditable(true);
		this.setTriggerAction(TriggerAction.ALL);
	}
}
