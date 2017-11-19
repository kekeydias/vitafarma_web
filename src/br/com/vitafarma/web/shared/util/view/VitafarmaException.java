package br.com.vitafarma.web.shared.util.view;

import java.io.Serializable;

public class VitafarmaException extends Exception implements Serializable {
	private static final long serialVersionUID = 7252239658737885318L;
	private String completeMessage;

	public VitafarmaException() {
		super();
		this.completeMessage = "";
	}

	public VitafarmaException(String message) {
		super(message);
		this.completeMessage = message;
	}

	public VitafarmaException(Exception e) {
		super(e.getCause());
		this.completeMessage = extractMessage(e);
	}

	public String getCompleteMessage() {
		return completeMessage;
	}

	private String extractMessage(Throwable caught) {
		String caughtMessage = "";
		if (caught != null) {
			caughtMessage = "Message: " + caught.getMessage();
			Throwable throwable = caught.getCause();
			while (throwable != null) {
				caughtMessage += "\nCause: " + throwable.getMessage();
				throwable = throwable.getCause();
			}
		}
		return caughtMessage;
	}
}