package com.again.plugin.example.tablist;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.again.swt.widgets.GeneralForm1;
import com.again.swt.widgets.TextField;

public class TabListExample {
	public static void main(String[] args) {

		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setSize(280, 300);
		shell.setText("Tab List Example");
		shell.setLayout(new FillLayout());
		GeneralForm1 generalForm = new GeneralForm1(shell, 2);
		generalForm.setLabelHHint(50);
		TextField f1 = generalForm.createTextField("Field1:");
		TextField f2 = generalForm.createTextField("Field2:");
		TextField f3 = generalForm.createTextField("Field3:");
		TextField f4 = generalForm.createTextField("Field4:");
		TextField f5 = generalForm.createTextField("Field5:");
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();

	}
}
