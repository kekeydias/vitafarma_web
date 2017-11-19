package br.com.vitafarma.web.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("uniques")
public interface UniquesService extends RemoteService {
	Boolean domainKeyIsUnique(String value, String uniqueDomain);

	Boolean domainKeyIsUnique(String value, String uniqueDomain, List<Long> idsDomain);
}
