package net.sf.latexdraw.glib.models.impl;

import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.filters.EPSFilter;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.IPicture;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.glib.views.pst.PSTricksConstants;
import net.sf.latexdraw.util.LPath;

import org.sourceforge.jlibeps.epsgraphics.EpsGraphics2D;

/**
 * Defines a model of a picture.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
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
	/** The buffered image. */
	protected Image image;

	/** The path of the eps image. */
	protected String pathTarget;

	/** The path of the source image. */
	protected String pathSource;


	/**
	 * Creates a picture and the corresponding EPS picture.
	 * @param pt The position of the top-left point of the picture.
	 * @throws IllegalArgumentException If the given picture path is not valid.
	 */
	protected LPicture(final IPoint pt) {
		super(pt);
	}


	/**
	 * Loads the image using the source path and creates the eps picture.
	 * @throws IOException If the picture cannot be loaded.
	 * @since 3.0
	 */
	protected void loadImage() throws IOException {
		if(image!=null) {
			image.flush();
			new File(pathTarget).delete();
		}

		image = ImageIO.read(new File(pathSource));

		if(image==null)
			throw new IOException("Picture " + pathSource + " is not a valid picture."); //$NON-NLS-1$ //$NON-NLS-2$

		createEPSImage();
	}



	@Override
	public void copy(final IShape sh) {
		super.copy(sh);

		if(sh instanceof IPicture)
			try{ setPathSource(((IPicture)sh).getPathSource()); }
			catch(final IOException ex) { BadaboomCollector.INSTANCE.add(ex); }
	}


	/**
	 * Creates an EPS image from the source one.
	 * @throws IOException If a problem while reading/writing files occurs.
	 * @since 2.0.0
	 */
	protected void createEPSImage() throws IOException {
		if(pathSource==null || image==null)
			return ;

		final int indexName 	= pathSource.lastIndexOf(File.separator)+1;
		final String name 		= pathSource.substring(indexName, pathSource.lastIndexOf('.'))+EPSFilter.EPS_EXTENSION;
		final String dirPath 	= pathSource.substring(0, indexName);
		pathTarget 				= dirPath+name;
		File file 				= new File(pathTarget);
		boolean created;

		try {// We create the output file that will contains the eps picture.
			created = file.createNewFile();
		}catch(final IOException ex) { created = false; }

		// If created is false, it may mean that the file already exist.
		if(!created && !file.canWrite()) {
			pathTarget = LPath.PATH_CACHE_DIR+File.separator+name;
			file = new File(pathTarget);
		}

		// Within jlibeps, graphics are defined using 72 DPI (72/2.54=28,3465 PPC), but latexdraw uses 50 PPC.
		// That's why, we need the scale the graphics to have a 50 PPC eps picture.
		final double scale = 72. / PSTricksConstants.INCH_VAL_CM / IShape.PPC;// 72 DPI / 2.54 / 50 PPC
		try(FileOutputStream finalImage = new FileOutputStream(file)){
			final EpsGraphics2D g = new EpsGraphics2D("LaTeXDrawPicture", finalImage, 0, 0, (int)(getWidth()*scale), (int)(getHeight()*scale));//$NON-NLS-1$
			g.scale(scale, scale);
			g.drawImage(image, 0, 0, null);
			g.flush();
			g.close();
		}
	}


	@Override
	public IPoint getPosition() {
		return getTopLeftPoint();
	}


	@Override
	public IPoint getTopLeftPoint() {
		return getPtAt(0);
	}


	@Override
	public IPoint getTopRightPoint() {
		final IPoint pos = getPtAt(0);
		return ShapeFactory.createPoint(pos.getX()+getWidth(), pos.getY());
	}


	@Override
	public IPoint getFullBottomRightPoint() {
		return getBottomRightPoint();
	}


	@Override
	public IPoint getFullTopLeftPoint() {
		return getTopLeftPoint();
	}


	@Override
	public IPoint getBottomRightPoint() {
		final IPoint pos = getPtAt(0);
		return ShapeFactory.createPoint(pos.getX()+getWidth(), pos.getY()+getHeight());
	}


	@Override
	public IPoint getBottomLeftPoint() {
		final IPoint pos = getPtAt(0);
		return ShapeFactory.createPoint(pos.getX(), pos.getY()+getHeight());
	}


	@Override
	public double getHeight() {
		return image==null ? 0 : image.getHeight(null);
	}


	@Override
	public Image getImage() {
		return image;
	}


	@Override
	public String getPathSource() {
		return pathSource;
	}


	@Override
	public String getPathTarget() {
		return pathTarget;
	}


	@Override
	public double getWidth() {
		return image==null ? 0 : image.getWidth(null);
	}


	@Override
	public void setPathSource(final String pathSource) throws IOException {
		this.pathSource = pathSource;
		loadImage();
	}

	@Override
	public boolean isColourable() {
		return false;
	}
}
