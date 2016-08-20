package com.again.gc;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class DrawArcSWT {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Canvas Example");
		shell.setLayout(new FillLayout());

		Canvas canvas = new Canvas(shell, SWT.NONE);

		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				e.gc.setBackground(e.display.getSystemColor(SWT.COLOR_GREEN));
				e.gc.fillArc(0, 0, 400, 400, 0, 360);
				e.gc.setBackground(e.display.getSystemColor(SWT.COLOR_BLUE));
				e.gc.fillArc(100, 100, 200, 200, 0, 360);
			}
		});

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}
