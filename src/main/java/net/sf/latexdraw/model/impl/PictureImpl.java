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
package net.sf.latexdraw.model.impl;

import java.io.File;
import java.util.stream.Stream;
import javafx.scene.image.Image;
import net.sf.latexdraw.command.ExportFormat;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Picture;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.util.SystemUtils;
import org.jetbrains.annotations.NotNull;

/**
 * A model of a picture.
 * @author Arnaud Blouin
 */
class PictureImpl extends PositionShapeBase implements Picture {
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
	PictureImpl(final Point pt) {
		super(pt);
	}


	/**
	 * Loads the image using the source path and creates the eps picture.
	 * @throws IllegalArgumentException if the source path is invalid or unsupported
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
		final String path = SystemUtils.getInstance().getFileWithoutExtension(pathSource);
		pathSource = Stream.of(".jpg", ".png", ".gif", ".jpeg").map(ext -> new File(path + ext)). //NON-NLS
			filter(f -> f.exists()).map(f -> f.getPath()).findFirst().
			orElseGet(() -> {
				SystemUtils.getInstance().execute(new String[] {"convert", pathSource, path + ".jpg"}, null); //NON-NLS
				return path + ".jpg"; //NON-NLS
			});
	}


	@Override
	public void copy(final Shape sh) {
		super.copy(sh);

		if(sh instanceof Picture) {
			setPathSource(((Picture) sh).getPathSource());
		}
	}

	@Override
	public @NotNull Picture duplicate() {
		final Picture pic = ShapeFactory.INST.createPicture(getPosition());
		pic.copy(this);
		return pic;
	}

	@Override
	public void mirrorVertical(final double y) {
		final Point gc = getGravityCentre();
		if(MathUtils.INST.isValidCoord(y) && !MathUtils.INST.equalsDouble(y, gc.getY())) {
			translate(0d, gc.verticalSymmetry(y).getY() - gc.getY());
		}
	}

	@Override
	public void mirrorHorizontal(final double x) {
		final Point gc = getGravityCentre();
		if(MathUtils.INST.isValidCoord(x) && !MathUtils.INST.equalsDouble(x, gc.getX())) {
			translate(gc.horizontalSymmetry(x).getX() - gc.getX(), 0d);
		}
	}


	/**
	 * Creates an EPS image from the source one.
	 */
	private void createEPSImage() {
		pathTarget = SystemUtils.getInstance().getFileWithoutExtension(pathSource) + ExportFormat.EPS_LATEX.getFileExtension();

		if(!new File(pathTarget).exists()) {
			SystemUtils.getInstance().execute(new String[] {"convert", pathSource, pathTarget}, null); //NON-NLS
		}
	}


	@Override
	public @NotNull Point getPosition() {
		return getPtAt(0);
	}


	@Override
	public @NotNull Point getTopRightPoint() {
		final Point pos = getPtAt(0);
		return ShapeFactory.INST.createPoint(pos.getX() + getWidth(), pos.getY());
	}


	@Override
	public @NotNull Point getFullBottomRightPoint() {
		return getBottomRightPoint();
	}


	@Override
	public @NotNull Point getFullTopLeftPoint() {
		return getTopLeftPoint();
	}


	@Override
	public @NotNull Point getBottomRightPoint() {
		final Point pos = getPtAt(0);
		return ShapeFactory.INST.createPoint(pos.getX() + getWidth(), pos.getY() + getHeight());
	}


	@Override
	public @NotNull Point getBottomLeftPoint() {
		final Point pos = getPtAt(0);
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
		if(pathSource != null) {
			loadImage();
		}else {
			image = null;
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
