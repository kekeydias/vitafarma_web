package br.com.vitafarma.web.shared.util;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class VitafarmaDate extends Date implements IsSerializable {
	private static final long serialVersionUID = 3295647842705641679L;

	public VitafarmaDate() {
		super();
	}

	public VitafarmaDate(long time) {
		super(time);
	}

	public VitafarmaDate(Date date) {
		super(date.getTime());
	}

	@Override
	public String toString() {
		return VitafarmaUtil.buildDateString(this);
	}

	public String toDateTimeString() {
		return VitafarmaUtil.buildDateTimeString(this);
	}
}
