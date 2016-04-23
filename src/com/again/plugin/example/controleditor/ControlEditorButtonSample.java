package com.again.plugin.example.controleditor;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ControlEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class ControlEditorButtonSample {
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

		final Canvas canvas = new Canvas(shell, SWT.BORDER);
		canvas.setBounds(10, 10, 300, 300);
		canvas.setBackground(color);
		ControlEditor editor = new ControlEditor(canvas);
		// The editor will be a button in the bottom right corner of the canvas.
		// When selected, it will launch a Color dialog that will change the
		// background
		// of the canvas.
		Button button = new Button(canvas, SWT.PUSH);
		button.setText("Select Color...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ColorDialog dialog = new ColorDialog(shell);
				dialog.open();
				RGB rgb = dialog.getRGB();
				if (rgb != null) {
					if (color != null)
						color.dispose();
					color = new Color(null, rgb);
					canvas.setBackground(color);
				}

			}
		});

		editor.horizontalAlignment = SWT.RIGHT;
		editor.verticalAlignment = SWT.BOTTOM;
		editor.grabHorizontal = false;
		editor.grabVertical = false;
		Point size = button.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		editor.minimumWidth = size.x;
		editor.minimumHeight = size.y;
		editor.setEditor(button);

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
