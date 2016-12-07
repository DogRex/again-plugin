package com.again.gc.nibble;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;

public class Board extends Canvas {

	private final int WIDTH = 600;
	private final int HEIGHT = 600;
	private final int DOT_SIZE = 20;
	private final int ALL_DOTS = 1800;
	private final int RAND_POS = 29;
	private final int DELAY = 140;

	private int x[] = new int[ALL_DOTS];
	private int y[] = new int[ALL_DOTS];

	private int dots;
	private int apple_x;
	private int apple_y;

	private boolean left = false;
	private boolean right = true;
	private boolean up = false;
	private boolean down = false;
	private boolean inGame = true;

	// private Image ball;
	// private Image apple;
	// private Image head;

	private Color ball;
	private Color apple;
	private Color head;

	private Display display;
	private Shell shell;
	private Runnable runnable;

	public Board(Shell shell) {
		super(shell, SWT.NULL);

		this.shell = shell;

		initBoard();
	}

	private void initBoard() {

		display = shell.getDisplay();

		addListener(SWT.Paint, event -> doPainting(event));
		addListener(SWT.KeyDown, event -> onKeyDown(event));

		addListener(SWT.Dispose, event -> {

			ball.dispose();
			apple.dispose();
			head.dispose();
		});

		Color col = new Color(shell.getDisplay(), 0, 0, 0);

		setBackground(col);
		col.dispose();

		loadImages();

		initGame();
	}

	private void loadImages() {

		ball = display.getSystemColor(SWT.COLOR_GRAY);
		apple = display.getSystemColor(SWT.COLOR_RED);
		head = display.getSystemColor(SWT.COLOR_GREEN);

		// ImageData iib = new ImageData("dot.png");
		// ball = new Image(display, iib);
		//
		// ImageData iia = new ImageData("apple.png");
		// apple = new Image(display, iia);
		//
		// ImageData iih = new ImageData("head.png");
		// head = new Image(display, iih);
	}

	private void initGame() {

		dots = 3;

		for (int z = 0; z < dots; z++) {
			x[z] = 100 - z * DOT_SIZE;
			y[z] = 100;
		}

		locateApple();

		runnable = new Runnable() {
			@Override
			public void run() {

				if (inGame) {
					checkApple();
					checkCollision();
					move();

				}

				display.timerExec(DELAY, this);
				redraw();
			}
		};

		display.timerExec(DELAY, runnable);
	};

	private void doPainting(Event e) {

		GC gc = e.gc;

		Color col = new Color(shell.getDisplay(), 0, 0, 0);
		gc.setBackground(col);
		col.dispose();

		gc.setAntialias(SWT.ON);

		if (inGame) {
			drawObjects(e);
		} else {
			gameOver(e);
		}
	}

	private void drawObjects(Event e) {

		GC gc = e.gc;

		gc.setBackground(apple);
		gc.fillOval(apple_x, apple_y, DOT_SIZE, DOT_SIZE);

		for (int z = 0; z < dots; z++) {
			if (z == 0) {
				gc.setBackground(head);
				gc.fillOval(x[z], y[z], DOT_SIZE, DOT_SIZE);
			} else {
				gc.setBackground(ball);
				gc.fillOval(x[z], y[z], DOT_SIZE, DOT_SIZE);
			}
		}
	}

	private void gameOver(Event e) {

		GC gc = e.gc;

		String msg = "Game Over";

		Font font = new Font(e.display, "Helvetica", 12, SWT.NORMAL);
		Color whiteCol = new Color(e.display, 255, 255, 255);

		gc.setForeground(whiteCol);
		gc.setFont(font);

		Point size = gc.textExtent(msg);

		gc.drawText(msg, (WIDTH - size.x) / 2, (HEIGHT - size.y) / 2);

		font.dispose();
		whiteCol.dispose();

		display.timerExec(-1, runnable);
	}

	private void checkApple() {

		if ((x[0] == apple_x) && (y[0] == apple_y)) {
			dots++;
			locateApple();
		}
	}

	private void move() {

		for (int z = dots; z > 0; z--) {
			x[z] = x[(z - 1)];
			y[z] = y[(z - 1)];
		}

		if (left) {
			x[0] -= DOT_SIZE;
		}

		if (right) {
			x[0] += DOT_SIZE;
		}

		if (up) {
			y[0] -= DOT_SIZE;
		}

		if (down) {
			y[0] += DOT_SIZE;
		}
	}

	public void checkCollision() {

		for (int z = dots; z > 0; z--) {

			if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
				inGame = false;
			}
		}

		if (y[0] > HEIGHT - DOT_SIZE) {
			inGame = false;
		}

		if (y[0] < 0) {
			inGame = false;
		}

		if (x[0] > WIDTH - DOT_SIZE) {
			inGame = false;
		}

		if (x[0] < 0) {
			inGame = false;
		}
	}

	public void locateApple() {

		int r = (int) (Math.random() * RAND_POS);
		apple_x = ((r * DOT_SIZE));
		r = (int) (Math.random() * RAND_POS);
		apple_y = ((r * DOT_SIZE));
	}

	private void onKeyDown(Event e) {

		int key = e.keyCode;

		if ((key == SWT.ARROW_LEFT) && (!right)) {
			left = true;
			up = false;
			down = false;
		}

		if ((key == SWT.ARROW_RIGHT) && (!left)) {
			right = true;
			up = false;
			down = false;
		}

		if ((key == SWT.ARROW_UP) && (!down)) {
			up = true;
			right = false;
			left = false;
		}

		if ((key == SWT.ARROW_DOWN) && (!up)) {
			down = true;
			right = false;
			left = false;
		}
	}
}
