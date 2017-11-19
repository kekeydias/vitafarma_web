package br.com.vitafarma.web.shared.util.view;

import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.shared.dtos.AbstractDTO;
import br.com.vitafarma.web.shared.dtos.ProdutoDTO;

import com.extjs.gxt.ui.client.data.BaseListLoadResult;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ProdutosComboBox extends ComboBox<ProdutoDTO> {

	public ProdutosComboBox() {
		RpcProxy<ListLoadResult<ProdutoDTO>> proxy = new RpcProxy<ListLoadResult<ProdutoDTO>>() {
			@Override
			public void load(Object loadConfig,
					AsyncCallback<ListLoadResult<ProdutoDTO>> callback) {
				Services.produtos().getList((BasePagingLoadConfig) loadConfig,
						callback);
			}
		};

		this.setStore(new ListStore<ProdutoDTO>(
				new BaseListLoader<BaseListLoadResult<ProdutoDTO>>(proxy)));

		this.setFieldLabel("Produto");
		this.setEmptyText("Selecione um Produto");
		this.setDisplayField(AbstractDTO.PROPERTY_DISPLAY_TEXT);
		this.setEditable(true);
		this.setTriggerAction(TriggerAction.ALL);
	}
}
