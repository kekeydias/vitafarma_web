package br.com.vitafarma.web.server.excel.imp;

public class ImportExcelParameters {
	private String infoToBeImported;
	private Boolean createNewEntities;

	public ImportExcelParameters() {
		super();
		this.infoToBeImported = "";
		this.createNewEntities = false;
	}

	public String getInfoToBeImported() {
		return this.infoToBeImported;
	}

	public void setInfoToBeImported(String infoToBeImported) {
		this.infoToBeImported = infoToBeImported;
	}

	public Boolean getCreateNewEntities() {
		return this.createNewEntities;
	}

	public void setCreateNewEntities(Boolean createNewEntities) {
		this.createNewEntities = createNewEntities;
	}
}
