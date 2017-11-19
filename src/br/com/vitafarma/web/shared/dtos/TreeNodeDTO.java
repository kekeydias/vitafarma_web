package br.com.vitafarma.web.shared.dtos;

import com.extjs.gxt.ui.client.data.BaseModel;

public class TreeNodeDTO extends BaseModel implements Comparable<TreeNodeDTO> {

	private static final long serialVersionUID = -8890847882508721586L;

	// Propriedades
	public static final String PROPERTY_TEXT = "treeNodeText";
	public static final String PROPERTY_PATH = "treeNodePath";
	public static final String PROPERTY_LEAF = "treeNodeLeaf";
	public static final String PROPERTY_EMPTY = "treeNodeEmpty";

	private AbstractDTO<?> content;
	private TreeNodeDTO parent;

	public TreeNodeDTO() {
		super();
	}

	public TreeNodeDTO(AbstractDTO<?> content, String text, String path,
			Boolean leaf, Boolean empty, TreeNodeDTO parent) {
		this.content = content;
		this.setText(text);
		this.setPath(path);
		this.setLeaf(leaf);
		this.setEmpty(empty);
		this.setParent(parent);
	}

	public TreeNodeDTO(AbstractDTO<?> content) {
		this(content, content.getDisplayText(),
				(content.getDisplayText() + "/"), false, true, null);
	}

	public TreeNodeDTO(AbstractDTO<?> content, TreeNodeDTO parent) {
		this(content, content.getDisplayText(), "", false, true, parent);
		if (parent != null) {
			this.setPath(parent.getPath() + content.getDisplayText() + "/");
			this.parent.setEmpty(false);
			parent.setLeaf(false);
		}
	}

	public TreeNodeDTO(String text) {
		this(null, text, "", false, true, null);
	}

	public AbstractDTO<?> getContent() {
		return this.content;
	}

	public void setContent(AbstractDTO<?> content) {
		this.content = content;
	}

	public void setText(String text) {
		this.set(PROPERTY_TEXT, text);
	}

	public String getText() {
		return this.get(PROPERTY_TEXT);
	}

	public void setPath(String path) {
		this.set(PROPERTY_PATH, path);
	}

	public String getPath() {
		return this.get(PROPERTY_PATH);
	}

	public void setLeaf(Boolean leaf) {
		this.set(PROPERTY_LEAF, leaf);
	}

	public Boolean getLeaf() {
		return this.get(PROPERTY_LEAF);
	}

	public void setEmpty(Boolean empty) {
		this.set(PROPERTY_EMPTY, empty);
	}

	public Boolean getEmpty() {
		return this.get(PROPERTY_EMPTY);
	}

	public TreeNodeDTO getParent() {
		return this.parent;
	}

	public void setParent(TreeNodeDTO parent) {
		this.parent = parent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.getText() == null) ? 0 : this.getText().hashCode());
		result = prime * result
				+ ((this.getPath() == null) ? 0 : this.getPath().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (this.getClass() != obj.getClass()) {
			return false;
		}

		TreeNodeDTO other = (TreeNodeDTO) obj;
		if (this.getText() == null) {
			if (other.getText() != null) {
				return false;
			}
		} else if (!this.getText().equals(other.getText())) {
			return false;
		}

		if (this.getPath() == null) {
			if (other.getPath() != null) {
				return false;
			}
		} else if (!this.getPath().equals(other.getPath())) {
			return false;
		}

		return true;
	}

	@Override
	public int compareTo(TreeNodeDTO o) {
		return this.getText().compareTo(o.getText());
	}
}