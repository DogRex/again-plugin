package com.again.swt.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

public abstract class AbstractFormField {
	protected final Label label;

	protected final Control fieldControl;

	public AbstractFormField(Composite parent, int labelStyle, int fieldControlStyle) {
		this.label = new Label(parent, labelStyle);
		this.fieldControl = createFieldControl(parent, fieldControlStyle);
	}

	protected abstract Control createFieldControl(Composite parent, int fieldControlStyle);

	public Label getLabel() {
		return label;
	}

	public Control getFieldControl() {
		return fieldControl;
	}
}
