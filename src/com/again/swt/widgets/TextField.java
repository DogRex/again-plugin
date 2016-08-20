package com.again.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

public class TextField extends AbstractFormField {

	public TextField(Composite parent, String label) {
		super(parent, SWT.NONE, SWT.BORDER);
		this.label.setText(label);
	}

	@Override
	protected Control createFieldControl(Composite parent, int fieldControlStyle) {
		return new Text(parent, fieldControlStyle);
	}

	public Text getText() {
		return (Text) fieldControl;
	}

	public String getValue() {
		return getText().getText();
	}

	public void setValue(String value) {
		getText().setText(value);
	}

}
