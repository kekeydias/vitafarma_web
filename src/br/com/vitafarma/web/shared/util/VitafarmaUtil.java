package br.com.vitafarma.web.shared.util;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.google.gwt.user.client.Window.Location;

import br.com.vitafarma.domain.ItemVenda;

public class VitafarmaUtil implements java.io.Serializable {
	private static final long serialVersionUID = 582528883163691473L;

	protected static Logger logger = null;

	static {
		try {
			VitafarmaUtil.logger = Logger.getLogger(VitafarmaUtil.class.getName());
		} catch (Throwable ex) {
			System.err.println("ERRO AO INICIAR OBJETO LOGGER. PASSAMOS A UTILIZAR SAIDA PADRAO DE LOG.");

			System.err.println("Cause: " + ex.getCause());
			System.err.println("Message: " + ex.getMessage());
			System.err.println("Localized Message: " + ex.getLocalizedMessage());

			ex.printStackTrace();

			VitafarmaUtil.logger = null;
		}
	}

	public static void log(Object obj) {
		String msg = null;

		if (obj == null) {
			msg = ("<null>");
		} else {
			msg = obj.toString();
		}

		if (VitafarmaUtil.logger == null) {
			System.out.println(msg);
		} else {
			LogRecord lr = new LogRecord(Level.INFO, msg);

			VitafarmaUtil.logger.log(lr);
		}
	}

	public static double round(double value, int precision) {
		if (precision != 0) {
			double precisionFactor = (precision * 10.0);

			value = Math.round(value * precisionFactor);

			return (value / precisionFactor);
		}

		return value;
	}

	public static String truncate(String text, int max) {
		return VitafarmaUtil.truncate(text, max, true);
	}

	public static String truncate(String text, int max, boolean etc) {
		if (text == null) {
			return ("");
		}

		if (max <= 0) {
			return text;
		}

		String ret = text;

		if (ret.length() > max) {
			ret = ret.substring(0, max);

			if (etc) {
				ret += ("...");
			}
		}

		return ret;
	}

	public static String financialFormatToDoubleFormat(String value) {
		if (value == null) {
			return "";
		}

		boolean contemVirgula = value.contains(",");
		boolean contemPonto = value.contains(".");

		if (contemVirgula && contemPonto) {
			value = value.replace(".", "");
		}

		if (contemVirgula) {
			value = value.replace(",", ".");
		}

		return value;
	}

	public static boolean isBlank(String value) {
		return ((value == null) || value == "");
	}

	public static boolean isBlank(Object... itens) {
		for (Object item : itens) {
			if (item != null) {
				return false;
			}
		}

		return true;
	}

	public static boolean isBlank(Long value) {
		return (value == null || value == 0);
	}

	public static boolean isBlank(Double value) {
		return (value == null || value == 0.0);
	}

	public static String paramsDebug() {
		String codesvr = Location.getParameter("gwt.codesvr");
		if (isBlank(codesvr)) {
			return "";
		}

		return ("?gwt.codesvr=" + codesvr);
	}

	public static String arrayJoin(List<String> l, String s) {
		if (s == null || s.equals("")) {
			return "";
		}

		String ret = "";
		if (l.size() == 0) {
			return ret;
		}

		ret = l.get(0);
		for (int i = 1; i < l.size(); i++) {
			ret = (ret + s + l.get(i));
		}

		return ret;
	}

	public static Double roundTwoDecimals(double value) {
		long longValue = (long) (value * 100);
		return ((double) longValue / 100.0);
	}

	@SuppressWarnings("deprecation")
	public static String shortTimeString(Date date) {
		if (date == null) {
			return "";
		}

		Integer h = date.getHours();
		String hour = h.toString();
		if (hour.length() < 2) {
			hour = "0" + hour;
		}

		Integer m = date.getMinutes();
		String minute = m.toString();
		if (minute.length() < 2) {
			minute = "0" + minute;
		}

		return (hour + ":" + minute);
	}

	public static String parsePhoneNumberToString(Long value) {
		if (value == null) {
			return "";
		}

		return value.toString();
	}

	public static VitafarmaCurrency parseVitafarmaCurrency(Double d) {
		Double doubleValue = (d == null ? 0.0 : d);
		return new VitafarmaCurrency(doubleValue);
	}

	public static VitafarmaCurrency parseVitafarmaCurrency(Object o) {
		String s = (o == null ? "" : o.toString());
		return VitafarmaUtil.parseVitafarmaCurrency(s);
	}

	public static VitafarmaCurrency parseVitafarmaCurrency(String s) {
		Double d = 0.0;
		try {
			d = Double.parseDouble(s);
		} catch (Exception e) {
			d = 0.0;
		}

		VitafarmaCurrency tc = new VitafarmaCurrency(d);
		return tc;
	}

	public static String formatCurrencyValueString(Double value) {
		VitafarmaCurrency currency = new VitafarmaCurrency(value);
		return currency.toString();
	}

	public static Double getPrecoFinal(Double precoUnitario, Double quantidade, Double desconto) {
		Double result = (precoUnitario == null ? 0.0 : precoUnitario);
		desconto = (desconto == null ? 0.0 : desconto / 100.0);
		quantidade = (quantidade == null ? 0.0 : quantidade);
		result *= quantidade;
		result *= (1 - desconto);

		result = VitafarmaUtil.truncate(result);
		return result;
	}

	public static double truncate(double value) {
		return (Math.round(value * 100) / 100d);
	}

	public static VitafarmaDate getVitafarmaDate(Date date) {
		if (date == null) {
			return null;
		}

		if (date instanceof VitafarmaDate) {
			return (VitafarmaDate) date;
		}

		VitafarmaDate vd = new VitafarmaDate(date.getTime());
		return vd;
	}

	public static Date getDate(VitafarmaDate vDate) {
		if (vDate == null) {
			return null;
		}

		Date date = new Date(vDate.getTime());
		return date;
	}

	@SuppressWarnings("deprecation")
	public static String buildDateString(Date date) {
		if (date == null) {
			return "";
		}

		Integer dia = date.getDate();
		Integer mes = date.getMonth() + 1;
		Integer ano = date.getYear() + 1900;

		String diaStr = (dia.toString().length() == 2 ? dia.toString() : "0" + dia.toString());
		String mesStr = (mes.toString().length() == 2 ? mes.toString() : "0" + mes.toString());
		String anoStr = ano.toString();

		return (diaStr + "/" + mesStr + "/" + anoStr);
	}

	@SuppressWarnings("deprecation")
	public static String buildDateTimeString(Date date) {
		if (date == null) {
			return "";
		}

		String dateStr = VitafarmaUtil.buildDateString(date);

		Integer hour = date.getHours();
		Integer minute = date.getMinutes();
		Integer second = date.getSeconds();

		String hourStr = (hour.toString().length() == 2 ? hour.toString() : "0" + hour.toString());
		String minuteStr = (minute.toString().length() == 2 ? minute.toString() : "0" + minute.toString());
		String secondStr = (second.toString().length() == 2 ? second.toString() : "0" + second.toString());

		String timeStr = (hourStr + ":" + minuteStr + ":" + secondStr);

		return (dateStr + " " + timeStr);
	}

	public static Double getValorTotalItens(List<ItemVenda> itensVenda) {
		if (itensVenda == null) {
			return 0.0;
		}

		Double total = 0.0;

		for (ItemVenda itemVenda : itensVenda) {
			total += itemVenda.getPrecoFinal();
		}

		return total;
	}

	public static Double getSubtotalItens(List<ItemVenda> itensVenda) {
		if (itensVenda == null) {
			return 0.0;
		}

		Double total = 0.0;

		for (ItemVenda itemVenda : itensVenda) {
			total += (itemVenda.getPrecoUnitario() * itemVenda.getQuantidade());
		}

		return total;
	}

	public static boolean validadeValorDesconto(Double value) {
		if (value == null) {
			return false;
		}

		return (value >= 0.0 && value <= 100.0);
	}

	public static String parseCpfToString(Long value) {
		if (value == null) {
			return null;
		}

		String cpf = value.toString();
		while (cpf.length() < 11) {
			cpf = ("0" + cpf);
		}

		char[] chars = cpf.toCharArray();

		// 999.999.999-99
		StringBuilder sb = new StringBuilder();

		sb.append(chars[0]);
		sb.append(chars[1]);
		sb.append(chars[2]);
		sb.append(".");
		sb.append(chars[3]);
		sb.append(chars[4]);
		sb.append(chars[5]);
		sb.append(".");
		sb.append(chars[6]);
		sb.append(chars[7]);
		sb.append(chars[8]);
		sb.append("-");
		sb.append(chars[9]);
		sb.append(chars[10]);

		return sb.toString();
	}

	public static String parseCnpjToString(Long value) {
		if (value == null) {
			return null;
		}

		String cnpj = value.toString();
		while (cnpj.length() < 14) {
			cnpj = ("0" + cnpj);
		}

		char[] chars = cnpj.toCharArray();

		// 99.999.999/9999-99
		StringBuilder sb = new StringBuilder();

		sb.append(chars[0]);
		sb.append(chars[1]);
		sb.append(".");
		sb.append(chars[2]);
		sb.append(chars[3]);
		sb.append(chars[4]);
		sb.append(".");
		sb.append(chars[5]);
		sb.append(chars[6]);
		sb.append(chars[7]);
		sb.append("/");
		sb.append(chars[8]);
		sb.append(chars[9]);
		sb.append(chars[10]);
		sb.append(chars[11]);
		sb.append("-");
		sb.append(chars[12]);
		sb.append(chars[13]);

		return sb.toString();
	}

	public static CheckBox createCheckBox(String label, boolean value) {
		CheckBox cb = new CheckBox();

		cb.setHideLabel(true);
		cb.setBoxLabel(label);
		cb.setValue(value);

		return cb;
	}

	public static boolean vazio(Object obj) {
		if (VitafarmaUtil.nulo(obj)) {
			return true;
		}

		return VitafarmaUtil.vazio(obj.toString());
	}

	public static boolean vazio(String str) {
		if (VitafarmaUtil.nulo(str)) {
			return true;
		}

		return (str.length() == 0);
	}

	@SuppressWarnings("rawtypes")
	public static boolean vazio(Collection c) {
		if (VitafarmaUtil.nulo(c)) {
			return true;
		}

		return (c.size() == 0);
	}

	public static boolean nulo(Object obj) {
		return (obj == null);
	}
}
