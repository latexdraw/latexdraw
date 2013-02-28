package net.sf.latexdraw.util

import java.awt.Image
import java.awt.image.BufferedImage

/**
 * This singleton removes white margins of an image.<br>
 *<br>
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2013 Arnaud BLOUIN<br>
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
 * 2012-04-18<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 */
object ImageCropper {
	/**
	 * Removes the white margins of the given image.
	 * @param img The image to crop.
	 * @return The cropped image or null if the given image is null or fully white.
	 * @since 3.0
	 */
	def cropImage(img : BufferedImage) : Image = {
		if(img==null)
			return null

		val width = img.getWidth
		val height = img.getHeight
		val left = getLeft(img, width, height)

		if(left==width)
			return null

		val right = getRight(img, width, height)
		val top = getTop(img, width, height, left, right)
		val bottom = getBottom(img, width, height, left, right)
		val newWidth = img.getWidth(null)-left-right
		val newHeight = img.getHeight(null)-top-bottom
		val cropped = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB_PRE)
		val g = cropped.getGraphics

	    g.drawImage(img, 0, 0, newWidth, newHeight, left, top, newWidth+left, newHeight+top, null)
	    g.dispose

		return cropped
	}


	/**
	 * Gets the bottom position where a pixel is not white.
	 */
	private def getBottom(img : BufferedImage, width : Int, height : Int, left : Int, right : Int) : Int = {
		var bottom = 0
		var y = height-1

		while(y>=0 && !hasColouredPixelRow(img, y, left, width-right)) {
			bottom+=1
			y-=1
		}
		return bottom
	}


	/**
	 * Gets the top position where a pixel is not white.
	 */
	private def getTop(img : BufferedImage, width : Int, height : Int, left : Int, right : Int) : Int = {
		var top = 0

		while(top<height && !hasColouredPixelRow(img, top, left, width-right))
			top+=1

		return top
	}


	/**
	 * Gets the right position where a pixel is not white.
	 */
	private def getRight(img : BufferedImage, width : Int, height : Int) : Int = {
		var right = 0
		var x = width-1

		while(x>=0 && !hasColouredPixelColumn(img, x, height)) {
			right+=1
			x-=1
		}
		return right
	}


	/**
	 * Gets the left position where a pixel is not white.
	 */
	private def getLeft(img : BufferedImage, width : Int, height : Int) : Int = {
		var left = 0

		while(left<width && !hasColouredPixelColumn(img, left, height))
			left+=1

		return left
	}



	private def hasColouredPixelColumn(img : BufferedImage, x : Int, imgHeight : Int) : Boolean = {
		for(y <- 0 until imgHeight)
			if(img.getRGB(x, y)!=0)
				return true
		return false
	}


	private def hasColouredPixelRow(img : BufferedImage, y : Int, min : Int, max : Int) : Boolean = {
		for(x <- min until max)
			if(img.getRGB(x, y)!=0)
				return true
		return false
	}
}
