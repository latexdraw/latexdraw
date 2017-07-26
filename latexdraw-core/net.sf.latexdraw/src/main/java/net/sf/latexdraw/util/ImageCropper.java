/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * This singleton removes white margins of an image.
 * @author Arnaud BLOUIN
 */
public final class ImageCropper {
	public static final ImageCropper INSTANCE = new ImageCropper();

	private ImageCropper() {
		super();
	}

	/**
	 * Removes the white margins of the given image.
	 * @param img The image to crop.
	 * @return The cropped image or null if the given image is null or fully white.
	 * @since 3.0
	 */
	public BufferedImage cropImage(final BufferedImage img) {
		if(img == null) return null;

		final int width = img.getWidth();
		final int height = img.getHeight();

		if(width == 0 && height == 0) return null;

		final int left = getLeft(img, width, height);
		final int right = getRight(img, width, height);
		final int top = getTop(img, width, height, left, right);
		final int bottom = getBottom(img, width, height, left, right);
		final int newWidth = img.getWidth(null) - left - right;
		final int newHeight = img.getHeight(null) - top - bottom;
		final BufferedImage cropped = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics g = cropped.getGraphics();

		g.drawImage(img, 0, 0, newWidth, newHeight, left, top, newWidth + left, newHeight + top, null);
		g.dispose();

		return cropped;
	}


	/**
	 * Gets the bottom position where a pixel is not white.
	 */
	private int getBottom(final BufferedImage img, final int width, final int height, final int left, final int right) {
		int bottom = 0;
		int y = height - 1;

		while(y >= 0 && !hasColouredPixelRow(img, y, left, width - right)) {
			bottom++;
			y--;
		}
		return bottom;
	}


	/**
	 * Gets the top position where a pixel is not white.
	 */
	private int getTop(final BufferedImage img, final int width, final int height, final int left, final int right) {
		int top = 0;

		while(top < height && !hasColouredPixelRow(img, top, left, width - right)) {
			top++;
		}

		return top;
	}


	/**
	 * Gets the right position where a pixel is not white.
	 */
	private int getRight(final BufferedImage img, final int width, final int height) {
		int right = 0;
		int x = width - 1;

		while(x >= 0 && !hasColouredPixelColumn(img, x, height)) {
			right++;
			x--;
		}
		return right;
	}


	/**
	 * Gets the left position where a pixel is not white.
	 */
	private int getLeft(final BufferedImage img, final int width, final int height) {
		int left = 0;

		while(left < width && !hasColouredPixelColumn(img, left, height)) {
			left++;
		}

		return left;
	}


	private boolean hasColouredPixelColumn(final BufferedImage img, final int x, final int imgHeight) {
		for(int y = 0; y < imgHeight; y++) {
			if(img.getRGB(x, y) != 0) {
				return true;
			}
		}
		return false;
	}


	private boolean hasColouredPixelRow(final BufferedImage img, final int y, final int min, final int max) {
		for(int x = min; x < max; x++) {
			if(img.getRGB(x, y) != 0) {
				return true;
			}
		}
		return false;
	}
}
