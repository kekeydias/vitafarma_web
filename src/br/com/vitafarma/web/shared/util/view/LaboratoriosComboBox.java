package br.com.vitafarma.web.shared.util.view;

import br.com.vitafarma.web.client.Services;
import br.com.vitafarma.web.shared.dtos.AbstractDTO;
import br.com.vitafarma.web.shared.dtos.LaboratorioDTO;

import com.extjs.gxt.ui.client.data.BaseListLoadResult;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class LaboratoriosComboBox extends ComboBox<LaboratorioDTO> {

	public LaboratoriosComboBox() {
		RpcProxy<ListLoadResult<LaboratorioDTO>> proxy = new RpcProxy<ListLoadResult<LaboratorioDTO>>() {
			@Override
			public void load(Object loadConfig,
					AsyncCallback<ListLoadResult<LaboratorioDTO>> callback) {
				Services.laboratorios().getList(
						(BasePagingLoadConfig) loadConfig, callback);
			}
		};

		this.setStore(new ListStore<LaboratorioDTO>(
				new BaseListLoader<BaseListLoadResult<LaboratorioDTO>>(proxy)));

		this.setFieldLabel("Laboratorio");
		this.setEmptyText("Selecione um Laboratorio");
		this.setDisplayField(AbstractDTO.PROPERTY_DISPLAY_TEXT);
		this.setEditable(true);
		this.setTriggerAction(TriggerAction.ALL);
	}
}
