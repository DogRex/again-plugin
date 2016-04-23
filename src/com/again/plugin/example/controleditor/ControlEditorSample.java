package com.again.plugin.example.controleditor;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ControlEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ControlEditorSample {
	private static final Map COLORS = new HashMap();
	static {
		COLORS.put("red", new RGB(255, 0, 0));
		COLORS.put("green", new RGB(0, 255, 0));
		COLORS.put("blue", new RGB(0, 0, 255));
		COLORS.put("yellow", new RGB(255, 255, 0));
		COLORS.put("black", new RGB(0, 0, 0));
		COLORS.put("white", new RGB(255, 255, 255));
	}

	static Display display = new Display();

	static Color color = new Color(display, 255, 0, 0);

	public static void main(String[] args) {

		final Shell shell = new Shell(display);
		shell.setText("Control Editor");

		final Composite composite = new Composite(shell, SWT.NONE);
		composite.setBackground(color);
		composite.setBounds(0, 0, 300, 100);

		ControlEditor editor = new ControlEditor(composite);
		final Text text = new Text(composite, SWT.BORDER);
		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				RGB rgb = (RGB) COLORS.get(text.getText());
				if (rgb != null) {
					if (color != null)
						color.dispose();
					color = new Color(shell.getDisplay(), rgb);
					composite.setBackground(color);
				}
			}
		});

		// Place the editor in the top middle of the parent composite
		editor.horizontalAlignment = SWT.CENTER;
		editor.verticalAlignment = SWT.TOP;
		Point size = text.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		editor.minimumWidth = size.x;
		editor.minimumHeight = size.y;
		editor.setEditor(text);

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		if (color != null)
			color.dispose();
		display.dispose();

	}
}
