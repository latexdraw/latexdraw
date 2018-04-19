/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2018 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.models.impl;

import java.io.File;
import java.util.stream.Stream;
import javafx.scene.image.Image;
import net.sf.latexdraw.commands.ExportFormat;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IPicture;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.util.LFileUtils;
import net.sf.latexdraw.util.LSystem;

/**
 * A model of a picture.
 * @author Arnaud Blouin
 */
class LPicture extends LPositionShape implements IPicture {
	/** The buffered image. */
	private Image image;
	/** The path of the eps image. */
	private String pathTarget;
	/** The path of the source image. */
	private String pathSource;


	/**
	 * Creates a picture and the corresponding EPS picture.
	 * @param pt The position of the top-left point of the picture.
	 * @throws IllegalArgumentException If the given picture path is not valid.
	 */
	LPicture(final IPoint pt) {
		super(pt);
	}


	/**
	 * Loads the image using the source path and creates the eps picture.
	 * @since 3.0
	 */
	private void loadImage() {
		if(image != null) {
			new File(pathTarget).delete();
		}

		if(pathSource.endsWith(".eps") || pathSource.endsWith(".pdf")) { //NON-NLS
			pathTarget = pathSource;
			searchOrCreateImg();
		}

		image = new Image(new File(pathSource).toURI().toString());
		createEPSImage();
	}


	/**
	 * If the loaded file is a PDF or and EPS picture, a readable picture is first searched.
	 * If not found, a jpg picture is created (to be
	 */
	private void searchOrCreateImg() {
		final String path = LFileUtils.INSTANCE.getFileWithoutExtension(pathSource);
		System.out.println(path);
		pathSource = Stream.of(".jpg", ".png", ".gif", ".jpeg").map(ext -> new File(path + ext)). //NON-NLS
			filter(f -> f.exists()).map(f -> f.getPath()).findFirst().
			orElseGet(() -> {
				LSystem.INSTANCE.execute(new String[]{"convert", pathSource, path + ".jpg"}, null); //NON-NLS
				return path + ".jpg"; //NON-NLS
			});
	}


	@Override
	public void copy(final IShape sh) {
		super.copy(sh);

		if(sh instanceof IPicture) {
			setPathSource(((IPicture) sh).getPathSource());
		}
	}

	@Override
	public IPicture duplicate() {
		final IPicture pic = ShapeFactory.INST.createPicture(getPosition());
		pic.copy(this);
		return pic;
	}

	@Override
	public void mirrorVertical(final double y) {
		final IPoint gc = getGravityCentre();
		if(MathUtils.INST.isValidCoord(y) && !MathUtils.INST.equalsDouble(y, gc.getY())) {
			translate(0d, gc.verticalSymmetry(y).getY() - gc.getY());
		}
	}

	@Override
	public void mirrorHorizontal(final double x) {
		final IPoint gc = getGravityCentre();
		if(MathUtils.INST.isValidCoord(x) && !MathUtils.INST.equalsDouble(x, gc.getX())) {
			translate(gc.horizontalSymmetry(x).getX() - gc.getX(), 0d);
		}
	}


	/**
	 * Creates an EPS image from the source one.
	 * @since 2.0.0
	 */
	private void createEPSImage() {
		if(pathSource == null || image == null) {
			return;
		}

		pathTarget = LFileUtils.INSTANCE.getFileWithoutExtension(pathSource) + ExportFormat.EPS_LATEX.getFileExtension();

		if(!new File(pathTarget).exists()) {
			LSystem.INSTANCE.execute(new String[]{"convsert", pathSource, pathTarget}, null); //NON-NLS
		}
	}


	@Override
	public IPoint getPosition() {
		return getPtAt(0);
	}


	@Override
	public IPoint getTopRightPoint() {
		final IPoint pos = getPtAt(0);
		return ShapeFactory.INST.createPoint(pos.getX() + getWidth(), pos.getY());
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
		return ShapeFactory.INST.createPoint(pos.getX() + getWidth(), pos.getY() + getHeight());
	}


	@Override
	public IPoint getBottomLeftPoint() {
		final IPoint pos = getPtAt(0);
		return ShapeFactory.INST.createPoint(pos.getX(), pos.getY() + getHeight());
	}


	@Override
	public double getHeight() {
		return image == null ? 0.0 : image.getHeight();
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
	public void setPathSource(final String path) {
		pathSource = path;
		image = null;
		if(pathSource != null) {
			loadImage();
		}
	}

	@Override
	public String getPathTarget() {
		return pathTarget;
	}

	@Override
	public double getWidth() {
		return image == null ? 0d : image.getWidth();
	}

	@Override
	public boolean isColourable() {
		return false;
	}

	@Override
	public boolean isFillable() {
		return false;
	}

	@Override
	public boolean isInteriorStylable() {
		return false;
	}

	@Override
	public boolean isShadowable() {
		return false;
	}

	@Override
	public boolean isThicknessable() {
		return false;
	}
}
