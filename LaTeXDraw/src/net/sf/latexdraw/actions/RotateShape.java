package net.sf.latexdraw.actions;

import org.malai.undo.Undoable;

import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;

/**
 * This action increments to rotation angle of shapes.<br>
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
 * 01/03/2010<br>
 * @author Arnaud BLOUIN
 * @since 3.0
 */
public class RotateShape extends ShapeAction implements Undoable, Modifying {
	/** The increment to add to the rotation angle of the shape. */
	protected double rotationAngleIncrement;

	/** The last increment performed on shapes. Used to execute several times the action. */
	private double lastIncrement;


	/**
	 * Creates the action.
	 */
	public RotateShape() {
		super();

		rotationAngleIncrement = Double.NaN;
		lastIncrement		   = 0.;
	}


	@Override
	public boolean canDo() {
		return super.canDo() && GLibUtilities.INSTANCE.isValidCoordinate(rotationAngleIncrement);
	}


	@Override
	public boolean isRegisterable() {
		return true;
	}


	@Override
	protected void doActionBody() {
		rotateShapes(rotationAngleIncrement-lastIncrement);
		lastIncrement = rotationAngleIncrement;
	}


	/**
	 * Rotates the shape.
	 * @param angleIncrement The increment to add to the rotation angle of the shape.
	 */
	private void rotateShapes(final double angleIncrement) {
		shape.addToRotationAngle(null, angleIncrement);
		shape.setModified(true);
	}


	@Override
	public void undo() {
		rotateShapes(-rotationAngleIncrement);
	}


	@Override
	public void redo() {
		rotateShapes(rotationAngleIncrement);
	}


	@Override
	public String getUndoName() {
		return ShapeProperties.ROTATION_ANGLE.getMessage();
	}


	/**
	 * @param rotationAngleIncrement The increment to add to the rotation angle of the shape.
	 * @since 3.0
	 */
	public void setRotationAngleIncrement(final double rotationAngleIncrement) {
		this.rotationAngleIncrement = rotationAngleIncrement;
	}
}
