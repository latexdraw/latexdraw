package net.sf.latexdraw.actions;

import java.awt.geom.Rectangle2D;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape.Position;

import org.malai.undo.Undoable;

/**
 * This action scales the selected shapes of a drawing.<br>
 * <br>
 * This file is part of LaTeXDraw.<br>
 * Copyright (c) 2005-2012 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 29/11/2011<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ScaleShapes extends ShapeAction<IGroup> implements Undoable, Modifying {
	/** The direction of the scaling. */
	protected Position refPosition;

	/** The new X position used to compute the scale factor. */
	protected double newX;

	/** The new Y position used to compute the scale factor. */
	protected double newY;

	/** The bound of the selected shapes used to perform the scaling. */
	private Rectangle2D bound;

	/** The old width of the selection. */
	private double oldWidth;

	/** The old height of the selection. */
	private double oldHeight;


	/**
	 * Creates the action.
	 */
	public ScaleShapes() {
		super();
		oldWidth	= Double.NaN;
		oldHeight	= Double.NaN;
		newX		= Double.NaN;
		newY		= Double.NaN;
		bound 		= new Rectangle2D.Double();
	}


	@Override
	public boolean isRegisterable() {
		return hadEffect();
	}


	@Override
	public boolean canDo() {
		return super.canDo() && refPosition!=null && isValidScales();
	}


	private boolean isValidScales() {
		switch(refPosition) {
			case EAST: case WEST:
				return isValidScale(newX) && getScaledWidth()>1.;
			case NORTH: case SOUTH:
				return isValidScale(newY) && getScaledHeight()>1.;
			default:
				return isValidScale(newX) && isValidScale(newY) && getScaledHeight()>1. && getScaledWidth()>1.;
		}
	}


	private boolean isValidScale(final double scale) {
		return GLibUtilities.INSTANCE.isValidCoordinate(scale) && scale>0;
	}


	@Override
	protected void doActionBody() {
		if(Double.isNaN(oldWidth)) {
			final IPoint br = shape.getBottomRightPoint();
			final IPoint tl = shape.getTopLeftPoint();
			oldWidth  = br.getX() - tl.getX();
			oldHeight = br.getY() - tl.getY();
			updateBound(tl, br);
		}
		redo();
	}


	private void updateBound(final IPoint tl, final IPoint br) {
		bound.setFrameFromDiagonal(tl.getX(), tl.getY(), br.getX(), br.getY());
	}


	@Override
	public void undo() {
		shape.scale(oldWidth, oldHeight, refPosition, bound);
		drawing.setModified(true);
		updateBound(shape.getTopLeftPoint(), shape.getBottomRightPoint());
	}


	@Override
	public void setShape(final IGroup shape) {
		super.setShape(shape);

		if(shape!=null)
			updateBound(shape.getTopLeftPoint(), shape.getBottomRightPoint());
	}


	private double getScaledHeight() {
		if(refPosition.isSouth())
			return bound.getHeight() + bound.getY() - newY;
		else if(refPosition.isNorth())
			return bound.getHeight() + newY - bound.getMaxY();
		return 0.;
	}


	private double getScaledWidth() {
		if(refPosition.isWest())
			return bound.getWidth() + newX - bound.getMaxX();
		else if(refPosition.isEast())
			return bound.getWidth() + bound.getX() - newX;
		return 0.;
	}


	@Override
	public void redo() {
		shape.scale(getScaledWidth(), getScaledHeight(), refPosition, bound);
		drawing.setModified(true);
		updateBound(shape.getTopLeftPoint(), shape.getBottomRightPoint());
	}


	@Override
	public String getUndoName() {
		return "Resizing";
	}


	/**
	 * @return The reference position of the scaling.
	 * @since 3.0
	 */
	public Position getRefPosition() {
		return refPosition;
	}


	/**
	 * @param newX The new X position used to compute the scale factor.
	 * @since 3.0
	 */
	public final void setNewX(final double newX) {
		this.newX = newX;
	}


	/**
	 * @param newY The new Y position used to compute the scale factor.
	 * @since 3.0
	 */
	public final void setNewY(final double newY) {
		this.newY = newY;
	}


	/**
	 * @param refPosition The reference position of the scaling.
	 * @since 3.0
	 */
	public void setRefPosition(final Position refPosition) {
		this.refPosition = refPosition;
	}
}
