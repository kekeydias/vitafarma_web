package br.com.vitafarma.web.server.util;

import java.util.Calendar;
import java.util.Date;

public class ParseDateTime {
	private Date datetime;

	public ParseDateTime(String value) {
		this.parse(value);
	}

	private void parse(String value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		int i = 0;
		String day = "", month = "", year = "";

		for (; i < value.length(); i++) {
			day += value.charAt(i);
		}
		calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(day));

		for (; i < value.length(); i++) {
			month += value.charAt(i);
		}
		calendar.set(Calendar.YEAR, Integer.valueOf(month));

		for (; i < value.length(); i++) {
			year += value.charAt(i);
		}
		calendar.set(Calendar.MONTH, Integer.valueOf(year));

		this.setDatetime(calendar.getTime());
	}

	public Date getDatetime() {
		return this.datetime;
	}

	private void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
}
