package br.com.vitafarma.web.server;

import br.com.vitafarma.web.client.GreetingService;
import br.com.vitafarma.web.shared.FieldVerifier;

public class GreetingServiceImpl extends VitafarmaRemoteService implements
		GreetingService {
	private static final long serialVersionUID = -3549803965078941107L;

	@Override
	public String greetServer(String name, String cpf, String matricula)
			throws IllegalArgumentException {
		// Verify that the input is valid.
		if (!FieldVerifier.isValidName(name)) {
			// If the input is not valid, throw an IllegalArgumentException back
			// to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script
		// vulnerabilities.
		name = escapeHtml(name);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + name + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html
	 *            the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
}
