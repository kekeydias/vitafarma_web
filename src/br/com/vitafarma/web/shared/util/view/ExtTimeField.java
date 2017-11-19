package br.com.vitafarma.web.shared.util.view;

import java.util.Date;

import com.extjs.gxt.ui.client.util.DateWrapper;
import com.extjs.gxt.ui.client.widget.form.Time;
import com.extjs.gxt.ui.client.widget.form.TimeField;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;

public class ExtTimeField extends TimeField {
	private Time time;
	private String label;
	private boolean allowEmpty;
	private String emptyText;

	public ExtTimeField(Time time, String label, boolean allowEmpty) {
		this(time, label, allowEmpty, "Selecione o horario");
	}

	public ExtTimeField(Time time, String label, boolean allowEmpty,
			String emptyText) {
		super();

		this.time = time;
		this.label = label;
		this.allowEmpty = allowEmpty;
		this.emptyText = emptyText;

		this.init();
	}

	@Override
	public void setValue(Time value) {
		this.updateProperties(value);
	}

	private void init() {
		this.setValue(this.time);
		this.setFieldLabel(this.label);

		this.setAllowBlank(this.allowEmpty);
		if (this.getAllowBlank() == false) {
			this.setEmptyText(this.emptyText);
		} else {
			this.setEmptyText(null);
		}

		DateWrapper wrap = new DateWrapper(1970, 1, 1);
		wrap = wrap.clearTime();
		wrap = wrap.addHours(4);

		this.setMinLength(5);
		this.setMaxLength(5);
		this.setTriggerAction(TriggerAction.ALL);
		this.setEmptyText("Preencha o horario");

		this.setMinValue(wrap.asDate());
		this.setDateValue(new Date());
		this.setIncrement(1);

		DateTimeFormat format = DateTimeFormat
				.getFormat(PredefinedFormat.TIME_SHORT);
		this.setFormat(format);
	}

	private void updateProperties(Time time) {
		if (time == null) {
			super.setValue(null);
			return;
		}

		Date date = time.getDate();
		Time match = this.findModel(date);
		super.setValue(match);
	}

	@Override
	public void setIncrement(int increment) {
		super.setIncrement(increment <= 0 ? 1 : increment);
	}
}
