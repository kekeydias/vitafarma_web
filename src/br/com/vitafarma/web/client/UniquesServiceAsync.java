package br.com.vitafarma.web.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UniquesServiceAsync {
	void domainKeyIsUnique(String value, String uniqueDomain,
			AsyncCallback<Boolean> callback);

	void domainKeyIsUnique(String value, String uniqueDomain,
			List<Long> idsDomain, AsyncCallback<Boolean> callback);
}
