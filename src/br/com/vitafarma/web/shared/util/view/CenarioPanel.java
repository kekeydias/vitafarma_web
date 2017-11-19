package br.com.vitafarma.web.shared.util.view;

import br.com.vitafarma.web.shared.dtos.CenarioDTO;
import br.com.vitafarma.web.shared.util.resources.Resources;

import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

public class CenarioPanel extends ContentPanel {

	private TreePanel<ModelData> treePanel;
	private TreeStore<ModelData> store = new TreeStore<ModelData>();

	public CenarioPanel() {
		super(new FitLayout());

		this.store = new TreeStore<ModelData>();

		this.treePanel = new TreePanel<ModelData>(this.store);
		this.treePanel.setDisplayProperty("name");
		this.treePanel.getStyle().setLeafIcon(
				AbstractImagePrototype.create(Resources.DEFAULTS.document16()));
		this.treePanel.mask();
		this.add(this.treePanel);
		setHeading("Navegacao");
	}

	public void addCenario(CenarioDTO cenarioDTO) {
		CenarioTreeModel model = new CenarioTreeModel(cenarioDTO);
		if (this.store.getAllItems().contains(model)) {
			MessageBox.alert("Cenario ja aberto!",
					"Cenario selecionado ja se encontra na arvore de cenario.",
					null);
		} else {
			this.treePanel.unmask();
			this.store.add(model, true);
		}
	}

	private class CenarioTreeModel extends BaseTreeModel {
		private static final long serialVersionUID = -975085243596912169L;

		private CenarioDTO cenarioDTO;

		public CenarioTreeModel(CenarioDTO cenarioDTO) {
			this.cenarioDTO = cenarioDTO;
			set("name", cenarioDTO.getNome());
			criarEstrutura();
		}

		public CenarioDTO getCenarioDTO() {
			return cenarioDTO;
		}

		public void criarEstrutura() {
			ItemTreeModel campi = new ItemTreeModel("Campi");
			ItemTreeModel unidades = new ItemTreeModel("Unidades");
			ItemTreeModel salas = new ItemTreeModel("Salas");
			ItemTreeModel[] entrada = { campi, unidades, salas };
			add(new FolderTreeModel("Entrada", entrada));

			ItemTreeModel relatorio = new ItemTreeModel("Relatorio");
			ItemTreeModel[] saida = { relatorio };
			add(new FolderTreeModel("Saida", saida));
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof CenarioTreeModel))
				return false;
			return this.cenarioDTO.getId().equals(
					((CenarioTreeModel) obj).getCenarioDTO().getId());
		}

	}

	private class FolderTreeModel extends BaseTreeModel {

		private static final long serialVersionUID = -380407385897290464L;

		public FolderTreeModel(String name, BaseTreeModel[] children) {
			set("name", name);
			for (BaseTreeModel bmt : children) {
				add(bmt);
			}
		}

	}

	private class ItemTreeModel extends BaseTreeModel {

		private static final long serialVersionUID = -2172312572936370452L;

		public ItemTreeModel(String name) {
			set("name", name);
		}

	}
}
