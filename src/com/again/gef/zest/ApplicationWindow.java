package com.again.gef.zest;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class ApplicationWindow {
	private final String title;

	public ApplicationWindow(String name) {
		super();
		this.title = name;
	}

	protected abstract void createPartControl(Composite parent);

	protected void configuraionShell(Shell shell) {
		shell.setLayout(new FillLayout());
		shell.setText(title);
		shell.setSize(600, 800);
	}

	public void open(Shell shell) {
		configuraionShell(shell);

		createPartControl(shell);

		shell.open();
		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	public void open() {
		open(new Shell(Display.getDefault()));
	}

}
