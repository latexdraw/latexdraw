package net.sf.latexdraw.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * This singleton removes white margins of an image.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
 *<br>
 *  LaTeXDraw is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.<br>
 *<br>
 *  LaTeXDraw is distributed without any warranty; without even the
 *  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the GNU General Public License for more details.<br>
 * <br>
 * 23/01/12<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
public final class ImageCropper {
	/** The singleton. */
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
	public Image cropImage(final BufferedImage img) {
		if(img==null)
			return null;

		final int width = img.getWidth();
		final int height = img.getHeight();
		final int left = getLeft(img, width, height);

		if(left == width) // Fully white.
			return null;

		final int right = getRight(img, width, height);
		final int top = getTop(img, width, height, left, right);
		final int bottom = getBottom(img, width, height, left, right);
		final int newWidth = img.getWidth(null)-left-right;
		final int newHeight = img.getHeight(null)-top-bottom;
		System.out.println(left + " " + right + " " + top + " " + bottom);
		final Image cropped = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB_PRE);
		final Graphics g = cropped.getGraphics();

	    g.drawImage(img, 0, 0, newWidth, newHeight, left, top, newWidth+left, newHeight+top, null);
	    g.dispose();

		return cropped;
	}


	/**
	 * Gets the bottom position where a pixel is not white.
	 */
	private int getBottom(final BufferedImage img, final int width, final int height, final int left, final int right) {
		int bottom = 0;

		for(int y=height-1; y>=0 && !hasColouredPixelRow(img, y, left, width-right); y--)
			bottom++;

		return bottom;
	}


	/**
	 * Gets the top position where a pixel is not white.
	 */
	private int getTop(final BufferedImage img, final int width, final int height, final int left, final int right) {
		int top = 0;

		for(int y=0; y<height && !hasColouredPixelRow(img, y, left, width-right); y++)
			top++;

		return top;
	}


	/**
	 * Gets the right position where a pixel is not white.
	 */
	private int getRight(final BufferedImage img, final int width, final int height) {
		int right = 0;

		for(int x=width-1; x>=0 && !hasColouredPixelColumn(img, x, height); x--)
			right++;

		return right;
	}


	/**
	 * Gets the left position where a pixel is not white.
	 */
	private int getLeft(final BufferedImage img, final int width, final int height) {
		int left = 0;

		for(int x=0; x<width && !hasColouredPixelColumn(img, x, height); x++)
			left++;

		return left;
	}



	private boolean hasColouredPixelColumn(final BufferedImage image, final int x, final int imgHeight) {
		boolean onlyWhitePixels = true;

		for(int y = 0; y<imgHeight && onlyWhitePixels; y++)
			if(image.getRGB(x, y)!=0)
				onlyWhitePixels = false;

		return !onlyWhitePixels;
	}


	private boolean hasColouredPixelRow(final BufferedImage image, final int y, final int min, final int max) {
		boolean onlyWhitePixels = true;

		for(int x=min; x<max && onlyWhitePixels; x++)
			if(image.getRGB(x, y) != 0)
				onlyWhitePixels = false;

		return !onlyWhitePixels;
	}
}
