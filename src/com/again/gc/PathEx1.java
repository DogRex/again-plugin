package com.again.gc;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class PathEx1 {
	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("二次方曲线路径");
		final Image image = new Image(display, 400, 300);
		final Rectangle rect = image.getBounds();
		shell.addListener(SWT.Paint, new Listener() {
			public void handleEvent(Event event) {
				GC gc = event.gc;
				gc.drawImage(image, 0, 0, rect.width, rect.height, 0, 0, rect.width / 2, rect.height / 2);
				Path path = new Path(display);// 绘制二次方曲线路径path.quadTo(2,60,60,140);
				path.quadTo(2, 60, 60, 140);
				gc.setBackground(display.getSystemColor(SWT.COLOR_GREEN));
				gc.setForeground(display.getSystemColor(SWT.COLOR_BLUE));
				gc.fillPath(path);
				gc.drawPath(path);// 记住释放 Path 资源path.dispose();
				path.dispose();
			}
		});
		shell.setSize(shell.computeSize(rect.width / 2, rect.height / 2));
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		image.dispose();
		display.dispose();
	}
}
