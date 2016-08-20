package com.again.swt.widgets;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class GeneralForm extends Composite {

	private final int columnNum;

	private Composite[] children;

	private int labelHHint = 80;

	public GeneralForm(Composite parent, int columnNum) {
		super(parent, SWT.NONE);
		this.columnNum = columnNum;
		createChildren();
	}

	private void createChildren() {
		GridLayoutFactory.fillDefaults().equalWidth(true).numColumns(columnNum).spacing(80, SWT.DEFAULT).applyTo(this);
		children = new Composite[columnNum];
		for (int i = 0; i < columnNum; i++) {
			Composite child = new Composite(this, SWT.NONE);
			GridLayoutFactory.fillDefaults().numColumns(getChildColumnNum()).spacing(10, 10).applyTo(child);
			GridDataFactory.fillDefaults().grab(true, false).applyTo(child);
			children[i] = child;
		}
	}

	protected int getChildColumnNum() {
		return 2;
	}

	public int getLabelHHint() {
		return labelHHint;
	}

	public void setLabelHHint(int labelHHint) {
		this.labelHHint = labelHHint;
	}

	public TextField createTextField(int columnIndex, String label) {
		Assert.isLegal(columnIndex >= 0 && columnIndex < children.length);
		Composite child = children[columnIndex];
		TextField textField = new TextField(child, label);
		GridDataFactory.fillDefaults().grab(false, false).hint(labelHHint, SWT.DEFAULT).applyTo(textField.getLabel());
		GridDataFactory.fillDefaults().grab(true, false).applyTo(textField.getText());

		return textField;
	}
}
