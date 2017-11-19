package br.com.vitafarma.web.server;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

import br.com.vitafarma.domain.Usuario;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class VitafarmaRemoteService extends RemoteServiceServlet {
	private static final long serialVersionUID = 5939530994258762775L;

	final protected Usuario getUsuario() throws NoSuchAlgorithmException {
		// SecurityContext context = SecurityContextHolder.getContext();
		// String username = context.getAuthentication().getName();
		// return Usuario.find( username );

		// FIXME -- Informar usuário atual
		return Usuario.findAll().get(0);
	}

	protected Date getDateTime(Date date, Integer hour, Integer minute, Integer second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		if (hour != null) {
			calendar.set(Calendar.HOUR_OF_DAY, hour);
		}

		if (minute != null) {
			calendar.set(Calendar.MINUTE, minute);
		}

		if (second != null) {
			calendar.set(Calendar.SECOND, second);
		}

		return calendar.getTime();
	}
}
