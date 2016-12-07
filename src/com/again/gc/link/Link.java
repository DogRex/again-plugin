package com.again.gc.link;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Link {

	private final int WIDTH = Board.WIDTH;
	private final int HEIGHT = Board.HEIGHT;

	public Link(Display display) {
		initUI(display);
	}

	@SuppressWarnings("unused")
	private void initUI(Display display) {

		Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);

		FillLayout layout = new FillLayout();
		shell.setLayout(layout);

		Board board = new Board(shell);

		shell.setText("SWT Link");
		int borW = shell.getSize().x - shell.getClientArea().width;
		int borH = shell.getSize().y - shell.getClientArea().height;
		shell.setSize(WIDTH + borW, HEIGHT + borH);

		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Display display = new Display();
		Link ex = new Link(display);
		display.dispose();
	}

}
