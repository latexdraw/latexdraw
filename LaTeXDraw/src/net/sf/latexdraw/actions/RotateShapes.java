package net.sf.latexdraw.actions;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IShape;

import org.malai.undo.Undoable;

/**
 * This action increments to rotation angle of shapes.<br>
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
 * 01/03/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class RotateShapes extends ShapeAction<IShape> implements Undoable, Modifying {
	/** The rotation angle to apply. */
	protected double rotationAngle;

	/** The gravity centre used for the rotation. */
	protected IPoint gc;
	
	
	/** The last increment performed on shapes. Used to execute several times the action. */
	private double lastRotationAngle;
	

	/**
	 * Creates the action.
	 */
	public RotateShapes() {
		super();

		rotationAngle 		= Double.NaN;
		lastRotationAngle 	= 0.;
	}


	@Override
	public boolean canDo() {
		return super.canDo() && GLibUtilities.INSTANCE.isValidCoordinate(rotationAngle) && GLibUtilities.INSTANCE.isValidPoint(gc);
	}


	@Override
	public boolean isRegisterable() {
		return true;
	}


	@Override
	protected void doActionBody() {
		rotateShapes(rotationAngle-lastRotationAngle);
		lastRotationAngle = rotationAngle;
	}


	/**
	 * Rotates the shape.
	 * @param angleIncrement The increment to add to the rotation angle of the shape.
	 */
	private void rotateShapes(final double angleIncrement) {
		shape.addToRotationAngle(gc, angleIncrement);
		shape.setModified(true);
	}


	@Override
	public void undo() {
		rotateShapes(-rotationAngle);
	}


	@Override
	public void redo() {
		rotateShapes(rotationAngle);
	}


	@Override
	public String getUndoName() {
		return ShapeProperties.ROTATION_ANGLE.getMessage();
	}


	/**
	 * @param rotationAngle The rotation angle to apply.
	 * @since 3.0
	 */
	public void setRotationAngle(final double rotationAngle) {
		this.rotationAngle = rotationAngle;
	}


	/**
	 * @param gc The gravity centre used for the rotation.
	 * @since 3.0
	 */
	public final void setGc(final IPoint gc) {
		this.gc = gc;
	}
}
