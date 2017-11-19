package br.com.vitafarma.web.shared.util;

import java.util.HashMap;
import java.util.Map;

import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.event.dom.client.KeyCodes;

import com.google.gwt.user.client.Element;

public class TextFieldMask extends TextField<String> {
	private final String mask;
	private int len;
	private Settings settings;
	private int partialPosition;
	private String[] buffer;
	private boolean ignore;
	private String focusText;
	private String[] tests;
	private Integer firstNonMaskPos;
	private int cursorBegin = -1;
	private int cursorEnd = -1;
	private static final Map<String, String> defs = new HashMap<String, String>();

	// Inner class
	public class Settings {
		private String placeHolder;

		public Settings() {
			super();
		}

		public Settings(String value) {
			this.placeHolder = value;
		}

		public String getPlaceHolder() {
			return this.placeHolder;
		}

		public void setPlaceHolder(String value) {
			this.placeHolder = value;
		}
	}

	static {
		TextFieldMask.defs.put("9", "[0-9]");
		TextFieldMask.defs.put("a", "[A-Za-z]");
		TextFieldMask.defs.put("*", "[A-Za-z0-9]");
	}

	private static boolean cotainDef(String key) {
		if (TextFieldMask.defs.get(key) != null) {
			return true;
		} else {
			return false;
		}
	}

	private static String getDef(String key) {
		return TextFieldMask.defs.get(key);
	}

	public TextFieldMask(String mask) {
		this.mask = mask;
	}

	private void buffer() {
		String[] aux = split(this.mask);
		this.buffer = new String[aux.length];

		for (int i = 0; i < aux.length; i++) {
			if (cotainDef(aux[i])) {
				this.buffer[i] = this.settings.getPlaceHolder();
			} else {
				this.buffer[i] = aux[i];
			}
		}
	}

	private int checkVal(boolean allow) {
		String test = "";

		if (getValue() != null) {
			test = getValue();
		}

		int lastMatch = -1;
		int a = 0;

		for (int i = 0, pos = 0; i < this.len; i++) {
			if (this.tests[i] != null) {
				this.buffer[i] = this.settings.getPlaceHolder();
				while (pos++ < test.length()) {
					String c = String.valueOf(test.charAt(pos - 1));
					if (c.matches(this.tests[i])) {
						this.buffer[i] = String.valueOf(c);
						lastMatch = i;
						break;
					}
				}
				if (pos > test.length()) {
					break;
				}
			} else if (i != this.partialPosition) {
				try {
					char d = test.charAt(pos);
					if (this.buffer[i].equals(String.valueOf(d))) {
						pos++;
						lastMatch = i;
					}
				} catch (Exception e) {
					continue;
				}
			}
			a = i;
		}

		if (!allow && lastMatch + 1 < this.partialPosition) {
			setValue("");
			clearBuffer(0, len);
		} else if (allow || lastMatch + 1 >= this.partialPosition) {
			writeBuffer();
			if (!allow) {
				if (getValue() != null) {
					setValue(getValue().substring(0, lastMatch + 1));
				}
			}
		}
		return a;
	}

	private void clearBuffer(int start, int end) {
		for (int i = start; i < end && i < this.len; i++) {
			if (this.tests[i] != null) {
				this.buffer[i] = this.settings.getPlaceHolder();
			}
		}
	}

	private void each() {
		for (int i = 0; i < this.tests.length; i++) {
			String c = this.tests[i];
			if (c == "?") {
				this.len--;
				this.partialPosition = i;

			} else if (cotainDef(c)) {
				this.tests[i] = getDef(c);
				if (this.firstNonMaskPos == null) {
					this.firstNonMaskPos = this.tests.length - 1;
				}
			} else {
				this.tests[i] = null;
			}
		}
	}

	private void maskField() {
		this.settings = new Settings("_");

		this.tests = new String[] {};
		this.partialPosition = this.mask.length();
		this.firstNonMaskPos = null;
		this.len = this.mask.length();
		this.tests = this.split(this.mask);

		this.each();
		this.buffer();

		this.ignore = false;
		this.focusText = "";

		if (this.getValue() != null) {
			this.focusText = this.getValue();
		}

		if (!this.isReadOnly()) {
			this.checkVal(false);
		}
	}

	@Override
	protected void onBlur(ComponentEvent be) {
		super.onBlur(be);
		this.checkVal(false);
	}

	@Override
	protected void onFocus(ComponentEvent be) {
		super.onFocus(be);
		this.focusText = "";
		if (this.getValue() != null) {
			focusText = this.getValue();
		}

		int pos = this.checkVal(false);
		this.writeBuffer();

		if (pos == this.mask.length()) {
			this.cursorBegin = 0;
			this.cursorEnd = pos;
			this.select(0, pos);
		} else {
			this.cursorBegin = pos;
			this.cursorEnd = pos;
			this.setCursorPos(pos);
		}
	}

	@Override
	protected void onKeyDown(FieldEvent fe) {
		super.onKeyDown(fe);
		int k = fe.getKeyCode();
		this.ignore = (k < 16 || k > 16 && k < 32 || k > 32 && k < 41);

		// delete selection before proceeding
		if (this.cursorBegin - this.cursorEnd != 0
				&& (!this.ignore || k == KeyCodes.KEY_BACKSPACE || k == KeyCodes.KEY_DELETE)) {
			clearBuffer(this.cursorBegin, this.cursorEnd);
		}

		// backspace, delete, and escape get special treatment
		if (k == KeyCodes.KEY_BACKSPACE || k == KeyCodes.KEY_DELETE) {

			this.shiftL(this.getCursorPos() + (k == KeyCodes.KEY_DELETE ? 0 : -1));
			fe.stopEvent();
		} else if (k == KeyCodes.KEY_ESCAPE) {
			// escape
			this.setValue(this.focusText);
			fe.stopEvent();
		}
	}

	@Override
	protected void onKeyPress(FieldEvent fe) {
		super.onKeyPress(fe);
		int k = fe.getKeyCode();
		if (this.ignore) {
			// Fixes Mac FF bug on backspace
			if (k == KeyCodes.KEY_BACKSPACE)
				fe.stopEvent();
			return;
		}

		if (fe.isControlKey() || fe.isAltKey()) // Ignore
		{
			fe.stopEvent();
		} else if (k >= 32 && k <= 125 || k > 186) {
			// typeable characters
			int p = this.seekNext(getCursorPos() - 1);

			if (p < this.len) {
				String c = String.valueOf((char) fe.getKeyCode());
				if (c.matches(this.tests[p])) {
					this.shiftR(p);
					this.buffer[p] = c;
					this.writeBuffer();
					int next = this.seekNext(p);

					this.setCursorPos(next);

					this.cursorBegin = next;
					this.cursorEnd = next;
				}
			}
		}

		fe.stopEvent();
	}

	@Override
	protected void onRender(Element target, int index) {
		super.onRender(target, index);
		this.maskField();
	}

	private int seekNext(int index) {
		while (++index <= this.len) {
			try {
				if (this.tests[index] != null)
					break;
			} catch (Exception e) {
				break;
			}
		}
		return index;
	}

	private void shiftL(int index) {
		for (int i = index; i >= 0; i--) {
			if (this.tests[i] != null) {
				index = i;
				break;
			}
		}

		for (int i = index; i < this.len; i++) {
			if (this.tests[i] != null) {
				this.buffer[i] = this.settings.getPlaceHolder();
				int j = this.seekNext(i);

				if (j < this.len && this.buffer[j].matches(this.tests[i])) {
					this.buffer[i] = this.buffer[j];
				} else {
					break;
				}
			}
		}

		this.writeBuffer();
		this.setCursorPos(index);
	}

	private void shiftR(int index) {
		String c = this.settings.getPlaceHolder();

		for (int i = index; i < this.len; i++) {
			if (this.tests[i] != null) {
				int j = this.seekNext(i);
				String t = this.buffer[i];
				this.buffer[i] = c;
				if (j < this.len && t.matches(this.tests[j])) {
					c = t;
				} else {
					break;
				}
			}
		}
	}

	private String[] split(String text) {
		int length = text.length();
		String[] array = new String[length];

		for (int i = 0; i < length; i++) {
			array[i] = String.valueOf(text.charAt(i));
		}
		return array;
	}

	private void writeBuffer() {
		String valueAux = "";
		for (String element2 : this.buffer) {
			valueAux += element2;
		}

		this.setValue(valueAux);
	}

	// Define valor ao campo, utilizando a mascara ja definida Exemplo:
	// <pre>
	// TextFieldMask tfm = new TextFieldMask("99999-999");
	// tfm.setMaskedValue("30455770");
	// </pre>
	// @param value
	public void setMaskedValue(String value) {
		if (value == null) {
			return;
		}

		int pos = 0;
		StringBuilder sb = new StringBuilder();
		char[] ch = this.mask.toCharArray();

		for (int i = 0; i < ch.length; i++) {
			if ((Character.isLetter(ch[i]) || Character.isDigit(ch[i])) && (pos < value.length())) {
				sb.append(value.charAt(pos));
				pos++;
			} else {
				sb.append(ch[i]);
			}
		}

		super.setValue(sb.toString());
	}
}
