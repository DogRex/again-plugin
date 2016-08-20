package com.again.swt.widgets;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class GeneralForm1 extends Composite {
	private final int columnNum;
	private int labelHHint = 80;
	private int position = 0;

	public GeneralForm1(Composite parent, int columnNum) {
		super(parent, SWT.NONE);
		this.columnNum = columnNum * 2;
		createChildren();
	}

	private void createChildren() {
		GridLayoutFactory.fillDefaults().equalWidth(false).numColumns(columnNum).applyTo(this);
	}

	public int getLabelHHint() {
		return labelHHint;
	}

	public void setLabelHHint(int labelHHint) {
		this.labelHHint = labelHHint;
	}

	public TextField createTextField(String label) {
		int indent = position % columnNum == 0 ? 0 : 80;
		TextField textField = new TextField(this, label);
		GridDataFactory.fillDefaults().grab(false, false).indent(indent, 0).hint(labelHHint, SWT.DEFAULT)
				.applyTo(textField.getLabel());
		GridDataFactory.fillDefaults().grab(true, false).applyTo(textField.getText());
		position += 2;
		return textField;
	}
}
