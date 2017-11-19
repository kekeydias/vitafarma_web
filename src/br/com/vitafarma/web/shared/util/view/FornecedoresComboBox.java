package br.com.vitafarma.web.shared.util.view;

import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.shared.dtos.AbstractDTO;
import br.com.vitafarma.web.shared.dtos.FornecedorDTO;

import com.extjs.gxt.ui.client.data.BaseListLoadResult;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FornecedoresComboBox extends ComboBox<FornecedorDTO> {

	public FornecedoresComboBox() {
		RpcProxy<ListLoadResult<FornecedorDTO>> proxy = new RpcProxy<ListLoadResult<FornecedorDTO>>() {
			@Override
			public void load(Object loadConfig,
					AsyncCallback<ListLoadResult<FornecedorDTO>> callback) {
				Services.fornecedores().getList((BasePagingLoadConfig) loadConfig,
						callback);
			}
		};

		this.setStore(new ListStore<FornecedorDTO>(
				new BaseListLoader<BaseListLoadResult<FornecedorDTO>>(proxy)));

		this.setFieldLabel("Fornecedor");
		this.setEmptyText("Selecione um Fornecedor");
		this.setDisplayField(AbstractDTO.PROPERTY_DISPLAY_TEXT);
		this.setEditable(true);
		this.setTriggerAction(TriggerAction.ALL);
	}
}
