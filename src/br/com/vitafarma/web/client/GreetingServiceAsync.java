package br.com.vitafarma.web.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String name, String cpf, String matricula,
			AsyncCallback<String> callback) throws IllegalArgumentException;
}
