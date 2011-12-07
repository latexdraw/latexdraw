package net.sf.latexdraw.actions;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IShape.Position;

import org.malai.undo.Undoable;

/**
 * This action scales the selected shapes of a drawing.<br>
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
 * 29/11/2011<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class ScaleShapes extends DrawingSelectionAction implements Undoable, Modifying {
	/** The direction of the scaling. */
	protected Position position;

	/** The X-scale ratio. */
	protected double sx;

	/** The Y-scale ratio. */
	protected double sy;

	/** The X-scale that has been already performed (if the action is executed several times before being ended). */
	private double sxDone;

	/** The Y-scale that has been already performed (if the action is executed several times before being ended). */
	private double syDone;


	/**
	 * Creates the action.
	 */
	public ScaleShapes() {
		super();
		position 	= null;
		sx	 		= Double.NaN;
		sy	 		= Double.NaN;
		sxDone 		= 1.;
		syDone 		= 1.;
	}


	@Override
	public boolean isRegisterable() {
		return hadEffect();
	}


	@Override
	public boolean canDo() {
		return super.canDo() && position!=null && isValidScales();
	}


	private boolean isValidScales() {
		switch(position) {
			case EAST: case WEST:
				return isValidScale(sx, sxDone);
			case NORTH: case SOUTH:
				return isValidScale(sy, syDone);
			default: return isValidScale(sx, sxDone) && isValidScale(sy, syDone);
		}
	}


	private boolean isValidScale(final double scale, final double doneScale) {
		return GLibUtilities.INSTANCE.isValidCoordinate(scale) && GLibUtilities.INSTANCE.isValidCoordinate(scale/doneScale) && (scale/doneScale)>0.;
	}


	@Override
	protected void doActionBody() {
		scale(sx/sxDone, sy/syDone);
		sxDone = sx;
		syDone = sy;
	}


	/**
	 * Proceed to the scaling.
	 * @param sx2 The X-scale ratio to consider.
	 * @param sy2 The Y-scale ratio to consider.
	 */
	private void scale(final double sx2, final double sy2) {
		selection.scale(sx2, sy2, position);
		drawing.setModified(true);
	}


	@Override
	public void undo() {
		scale(1/sx, 1/sy);
	}


	@Override
	public void redo() {
		scale(sx, sy);
	}


	@Override
	public String getUndoName() {
		return "Scaling";
	}


	/**
	 * Sets the reference position of the scale.
	 * @param position The new position.
	 */
	public void setPosition(final Position position) {
		this.position = position;
	}


	/**
	 * Sets the new X-scale.
	 * @param sx The X-scale.
	 */
	public void setSx(final double sx) {
		this.sx = sx;
	}


	/**
	 * Sets the new Y-scale.
	 * @param sy The Y-scale.
	 */
	public void setSy(final double sy) {
		this.sy = sy;
	}
}
