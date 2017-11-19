package br.com.vitafarma.web.shared.util.view;

import br.com.vitafarma.web.shared.excel.ExcelInformationType;

public class ExcelParametros {
	private ExcelInformationType info = null;
	private ExcelOperation operation = null;

	public ExcelParametros(ExcelInformationType info, ExcelOperation op) {
		this.setInfo(info);

		this.setOperation(op);
	}

	public ExcelOperation getOperation() {
		return this.operation;
	}

	public void setOperation(ExcelOperation operation) {
		this.operation = operation;
	}

	public ExcelInformationType getInfo() {
		return this.info;
	}

	public void setInfo(ExcelInformationType info) {
		this.info = info;
	}
}
