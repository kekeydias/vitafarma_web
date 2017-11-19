package br.com.vitafarma.web.server.excel.imp;

import java.util.Date;
import java.util.List;

import br.com.vitafarma.web.server.util.ParseDateTime;
import br.com.vitafarma.web.shared.util.VitafarmaUtil;

public abstract class AbstractImportExcelBean {
	private int row;

	public AbstractImportExcelBean(int row) {
		this.row = row;
	}

	public int getRow() {
		return this.row;
	}

	protected boolean isEmptyField(String value) {
		return (value == null || value.equals(""));
	}

	protected void checkMandatoryField(String value,
			ImportExcelError errorType, List<ImportExcelError> errorsList) {
		if (this.isEmptyField(value)) {
			errorsList.add(errorType);
		}
	}

	protected void checkMandatoryFields(String[] values,
			ImportExcelError[] errorsTypes, List<ImportExcelError> errorsList) {
		boolean[] empty = new boolean[values.length];

		for (int i = 0; i < empty.length; i++) {
			empty[i] = false;
		}

		boolean allEmpty = true;
		for (int i = 0; i < empty.length; i++) {
			empty[i] = isEmptyField(values[i]);
			allEmpty &= empty[i];
		}

		if (!allEmpty) {
			for (int i = 0; i < empty.length; i++) {
				if (empty[i]) {
					errorsList.add(errorsTypes[i]);
				}
			}
		}
	}

	protected Double checkNonNegativeDoubleField(String value,
			ImportExcelError doubleErrorType,
			ImportExcelError nonNegativeErrorType,
			List<ImportExcelError> errorsList) {
		Double doubleValue = null;

		if (!this.isEmptyField(value)) {
			try {
				value = VitafarmaUtil.financialFormatToDoubleFormat(value);
				doubleValue = Double.valueOf(value);

				if (doubleValue < 0.0) {
					errorsList.add(nonNegativeErrorType);
				}
			} catch (NumberFormatException e) {
				errorsList.add(doubleErrorType);
			}
		}

		return doubleValue;
	}

	protected Date checkDateTimeField(String value,
			ImportExcelError dateTimeErrorType,
			List<ImportExcelError> errorsList) {
		Date date = null;

		if (!this.isEmptyField(value)) {
			try {
				ParseDateTime parser = new ParseDateTime(value);
				date = parser.getDatetime();

			} catch (NumberFormatException e) {
				errorsList.add(dateTimeErrorType);
			}
		}

		return date;
	}

	protected Integer checkNonNegativeIntegerField(String value,
			ImportExcelError integerErrorType,
			ImportExcelError nonNegativeErrorType,
			List<ImportExcelError> errorsList) {
		Integer integerValue = null;

		if (!isEmptyField(value)) {
			try {
				value = VitafarmaUtil.financialFormatToDoubleFormat(value);
				integerValue = Double.valueOf(value).intValue();

				if (integerValue < 0) {
					errorsList.add(nonNegativeErrorType);
				}
			} catch (NumberFormatException e) {
				errorsList.add(integerErrorType);
			}
		}

		return integerValue;
	}

	protected Boolean checkBooleanField(String value,
			ImportExcelError errorType, List<ImportExcelError> errorsList) {
		Boolean booleanValue = null;

		if (!isEmptyField(value)) {
			if ("Sim".equalsIgnoreCase(value) || "True".equalsIgnoreCase(value)) {
				booleanValue = true;
			} else {
				booleanValue = false;
			}
		}

		if (booleanValue == null) {
			errorsList.add(errorType);
		}

		return booleanValue;
	}

	protected <EnumType> EnumType checkEnumField(String value,
			Class<EnumType> enumClass, ImportExcelError enumErrorTupe,
			List<ImportExcelError> errorsList) {
		EnumType enumValue = null;

		if (enumClass.isEnum()) {
			for (EnumType enumConstant : enumClass.getEnumConstants()) {
				if (enumConstant.toString().equalsIgnoreCase(value)) {
					enumValue = enumConstant;
					break;
				}
			}

			if (enumValue == null) {
				errorsList.add(enumErrorTupe);
			}
		}

		return enumValue;
	}
}
