package com.again.gc.link;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;

public class Board extends Canvas {

	public static final int HEIGHT = 600;
	public static final int WIDTH = 800;
	public static final int IMAGE_SIZE = 50;

	// 非常不好意思，选用的图片有10个像素的位置偏差，在这里补上
	private static final int IMAGE_OFFSET = 10;

	private static final int IMAGE_COUNT_X = 12;
	private static final int IMAGE_COUNT_Y = 8;

	private final int[][] imageMatrix = new int[IMAGE_COUNT_X][IMAGE_COUNT_Y];

	public Board(Composite parent) {
		super(parent, SWT.NONE);
		initBorder();
	}

	private void initBorder() {
		initImageMatrix();

		addListener();
	}

	private void addListener() {
		addListener(SWT.Paint, event -> doPaint(event));
		addListener(SWT.Dispose, event -> doDispose());
		addListener(SWT.MouseDown, event -> onMouseDown(event));
	}

	private void doPaint(Event e) {
		Display diaplay = getParent().getDisplay();
		GC gc = e.gc;
		Transform transform = new Transform(diaplay);
		transform.translate(WIDTH / 2 - IMAGE_COUNT_X / 2 * IMAGE_SIZE, HEIGHT / 2 - IMAGE_COUNT_Y / 2 * IMAGE_SIZE);
		gc.setTransform(transform);
		for (int x = 0; x < imageMatrix.length; x++) {
			int[] imagesX = imageMatrix[x];
			for (int y = 0; y < imagesX.length; y++) {
				Image image = Images.getImage(imagesX[y]);
				gc.drawImage(image, x * IMAGE_SIZE - IMAGE_OFFSET, y * IMAGE_SIZE - IMAGE_OFFSET);
			}
		}
	}

	private void onMouseDown(Event event) {
		return;
	}

	private void doDispose() {
		Images.dispose();
	}

	private void initImageMatrix() {
		// prepare for image matrix
		int totalImages = IMAGE_COUNT_X * IMAGE_COUNT_Y;
		int[] imageArr = new int[totalImages];
		Set<Integer> imageCodeSet = Images.getImages().keySet();
		List<Integer> imageCodeList = new ArrayList<Integer>(imageCodeSet);
		for (int i = 0; i < totalImages / 2; i++) {
			imageArr[i * 2] = (imageArr[i * 2 + 1] = imageCodeList.get(i % imageCodeList.size()));
		}

		// random sort the imageArr
		Random random = new Random();
		for (int i = 0; i < totalImages - 1; i++) {
			int j = random.nextInt(totalImages - i - 1) + i + 1;
			int temp = imageArr[i];
			imageArr[i] = imageArr[j];
			imageArr[j] = temp;
		}

		// set image matrix
		int count = 0;
		for (int x = 0; x < imageMatrix.length; x++) {
			int[] imagesX = imageMatrix[x];
			for (int y = 0; y < imagesX.length; y++) {
				imagesX[y] = imageArr[count++];
			}
		}
	}

}
