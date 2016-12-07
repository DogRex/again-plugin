package com.again.gc.link;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class Images {
	private static final Map<Integer, Image> IMAGES = new HashMap<Integer, Image>();

	static {
		Display device = Display.getDefault();
		URL imageFolderUrl = Images.class.getResource("image");
		if (imageFolderUrl == null) {
			throw new IllegalStateException("Failed to find the image folder.");
		}
		File imageFolder = new File(imageFolderUrl.getPath());
		for (File imageFile : imageFolder.listFiles()) {
			Image image = new Image(device, imageFile.getAbsolutePath());
			IMAGES.put(image.hashCode(), image);
		}
	}

	public static Map<Integer, Image> getImages() {
		return Collections.unmodifiableMap(IMAGES);
	}

	public static Image getImage(int hashCode) {
		return IMAGES.get(hashCode);
	}

	public static void dispose() {
		for (Image image : IMAGES.values()) {
			image.dispose();
		}
		IMAGES.clear();
	}
}
