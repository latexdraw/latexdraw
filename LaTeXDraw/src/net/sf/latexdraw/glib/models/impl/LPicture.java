package net.sf.latexdraw.glib.models.impl;

import java.awt.Image;

import net.sf.latexdraw.glib.models.interfaces.IPicture;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;


/**
 * Defines a model of a picture.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2011 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 07/05/2009<br>
 * @author Arnaud BLOUIN
 * @version 3.0
 * @since 3.0
 */
class LPicture extends LPositionShape implements IPicture {
	/**
	 * Creates a picture and the corresponding EPS picture.
	 * @param isUniqueID True: the shape will have a unique ID.
	 * @param pt The position of the top-left point of the picture.
	 * @param pathSource The path of the image to load.
	 * @throws IllegalArgumentException If the given picture path is not valid.
	 */
	protected LPicture(final boolean isUniqueID, final IPoint pt, final String pathSource) {
		super(isUniqueID, pt);
	}



	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getPathSource() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getPathTarget() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public boolean setImage(final String path) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void setPathSource(final String pathSource) {
		// TODO Auto-generated method stub

	}


	@Override
	public IPicture duplicate() {
		final IShape sh = super.duplicate();
		return sh instanceof IPicture ? (IPicture)sh : null;
	}
}
