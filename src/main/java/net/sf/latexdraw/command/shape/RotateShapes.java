/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2019 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.command.shape;

import io.github.interacto.undo.Undoable;
import java.util.ResourceBundle;
import net.sf.latexdraw.command.Modifying;
import net.sf.latexdraw.command.ShapeCmdImpl;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Shape;
import org.jetbrains.annotations.NotNull;

/**
 * This command increments to rotation angle of shapes.
 * @author Arnaud Blouin
 */
public class RotateShapes extends ShapeCmdImpl<Shape> implements Undoable, Modifying {
	/** The rotation angle to apply. */
	private double rotationAngle;
	/** The gravity centre used for the rotation. */
	private final @NotNull Point gc;
	/** The last increment performed on shapes. Used to execute several times the command. */
	private double lastRotationAngle;


	public RotateShapes(final @NotNull Point gc, final @NotNull Shape sh, final double rotation) {
		super(sh);
		this.gc = gc;
		lastRotationAngle = 0d;
		rotationAngle = rotation;
	}

	@Override
	public boolean canDo() {
		return MathUtils.INST.isValidCoord(rotationAngle) && MathUtils.INST.isValidPt(gc);
	}

	@Override
	public void doCmdBody() {
		rotateShapes(rotationAngle - lastRotationAngle);
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
	public @NotNull String getUndoName(final @NotNull ResourceBundle bundle) {
		return bundle.getString("Actions.31");
	}

	public void setRotationAngle(final double angle) {
		rotationAngle = angle;
	}

	public double getRotationAngle() {
		return rotationAngle;
	}

	public @NotNull Point getGc() {
		return gc;
	}
}
