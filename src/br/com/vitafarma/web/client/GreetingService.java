package br.com.vitafarma.web.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name, String cpf, String matricula)
			throws IllegalArgumentException;
}
