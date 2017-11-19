package br.com.vitafarma.web.shared.util.view;

import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.shared.dtos.AbstractDTO;
import br.com.vitafarma.web.shared.dtos.CidadeDTO;

import com.extjs.gxt.ui.client.data.BaseListLoadResult;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class CidadesComboBox extends ComboBox<CidadeDTO> {

	public CidadesComboBox() {
		RpcProxy<ListLoadResult<CidadeDTO>> proxy = new RpcProxy<ListLoadResult<CidadeDTO>>() {
			@Override
			public void load(Object loadConfig,
					AsyncCallback<ListLoadResult<CidadeDTO>> callback) {
				Services.cidades().getList((BasePagingLoadConfig) loadConfig,
						callback);
			}
		};

		this.setStore(new ListStore<CidadeDTO>(
				new BaseListLoader<BaseListLoadResult<CidadeDTO>>(proxy)));

		this.setFieldLabel("Cidade");
		this.setEmptyText("Selecione uma Cidade");
		this.setDisplayField(AbstractDTO.PROPERTY_DISPLAY_TEXT);
		this.setEditable(true);
		this.setTriggerAction(TriggerAction.ALL);
	}
}
