package com.again.gc;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class DrawArcSWT1 {
	private static final int DEFAULT_WIDTH = 400;
	
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Canvas Example");
		shell.setLayout(new FillLayout());

		Canvas canvas = new Canvas(shell, SWT.NONE);

		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				
				GC gc = e.gc;
                gc.setLineWidth(1);
                gc.setLineStyle(SWT.LINE_SOLID);
                gc.setAntialias(SWT.ON);
                int standardRadius = DEFAULT_WIDTH / 2;
                float brightness = 1.0f;
                for (int index = standardRadius; index > 1; index = index
                        - 1) {
                    drawCycle(gc, index, brightness);
                }
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
	
	private static void drawCycle(GC graphics, int radius, float brightness) {

        int standardRadius = DEFAULT_WIDTH / 2;
        int delta = standardRadius - radius;
        float saturation = 1.0f - delta * 1.0f / standardRadius;
        Color color = null;
        for (int index = 0; index < 360; index++) {
            color = new Color(null, new RGB(index, saturation, brightness));
            graphics.setBackground(color);
            graphics.fillArc(delta, delta, radius * 2, radius * 2, index,
                    1);
            color.dispose();
        }
    }
}
