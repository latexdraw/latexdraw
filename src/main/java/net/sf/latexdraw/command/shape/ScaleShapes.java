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
import java.awt.geom.Rectangle2D;
import java.util.ResourceBundle;
import net.sf.latexdraw.command.Modifying;
import net.sf.latexdraw.command.ShapeCmdImpl;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Position;
import org.jetbrains.annotations.NotNull;

/**
 * This command scales a shape.
 * @author Arnaud Blouin
 */
public class ScaleShapes extends ShapeCmdImpl<Group> implements Undoable, Modifying {
	/** The direction of the scaling. */
	private final @NotNull Position refPosition;

	/** The new X position used to compute the scale factor. */
	private double newX;

	/** The new Y position used to compute the scale factor. */
	private double newY;

	/** The bound of the selected shapes used to perform the scaling. */
	private final @NotNull Rectangle2D bound;

	/** The old width of the selection. */
	private double oldWidth;

	/** The old height of the selection. */
	private double oldHeight;

	/** The drawing that will be handled by the command. */
	private final @NotNull Drawing drawing;
	private boolean mementoModified;


	public ScaleShapes(final @NotNull Group gp, final @NotNull Drawing drawing, final @NotNull Position refPosition) {
		super(gp);
		this.drawing = drawing;
		this.refPosition = refPosition;
		bound = new Rectangle2D.Double();
		newX = Double.NaN;
		newY = Double.NaN;
		setShape(gp);
	}

	@Override
	public boolean hadEffect() {
		return isDone() && (!MathUtils.INST.equalsDouble(oldWidth, bound.getWidth())
			|| !MathUtils.INST.equalsDouble(oldHeight, bound.getHeight()));
	}

	@Override
	public boolean canDo() {
		switch(refPosition) {
			case EAST:
				return MathUtils.INST.isValidCoord(newX);
			case WEST:
				return MathUtils.INST.isValidCoord(newX);
			case NORTH:
				return MathUtils.INST.isValidCoord(newY);
			case SOUTH:
				return MathUtils.INST.isValidCoord(newY);
			default:
				return MathUtils.INST.isValidCoord(newX) && MathUtils.INST.isValidCoord(newY);
		}
	}


	@Override
	protected void createMemento() {
		mementoModified = drawing.isModified();
		final Point br = shape.getBottomRightPoint();
		final Point tl = shape.getTopLeftPoint();
		oldWidth = br.getX() - tl.getX();
		oldHeight = br.getY() - tl.getY();
		updateBound(tl, br);
	}

	@Override
	protected void doCmdBody() {
		shape.scale(scaledWidth(newX), scaledHeight(newY), refPosition, bound);
		drawing.setModified(true);
		updateBound(shape.getTopLeftPoint(), shape.getBottomRightPoint());
	}

	private void updateBound(final @NotNull Point tl, final @NotNull Point br) {
		bound.setFrameFromDiagonal(tl.getX(), tl.getY(), br.getX(), br.getY());
	}

	@Override
	public void undo() {
		shape.scale(oldWidth, oldHeight, refPosition, bound);
		drawing.setModified(mementoModified);
		updateBound(shape.getTopLeftPoint(), shape.getBottomRightPoint());
	}

	@Override
	public void setShape(final @NotNull Group sh) {
		super.setShape(sh);
		updateBound(sh.getTopLeftPoint(), sh.getBottomRightPoint());
	}

	private double scaledHeight(final double y) {
		if(refPosition.isSouth()) {
			return bound.getHeight() + bound.getY() - y;
		}
		if(refPosition.isNorth()) {
			return bound.getHeight() + y - bound.getMaxY();
		}
		return 0d;
	}

	private double scaledWidth(final double x) {
		if(refPosition.isWest()) {
			return bound.getWidth() + x - bound.getMaxX();
		}
		if(refPosition.isEast()) {
			return bound.getWidth() + bound.getX() - x;
		}
		return 0d;
	}

	@Override
	public void redo() {
		doCmdBody();
	}

	@Override
	public @NotNull String getUndoName(final @NotNull ResourceBundle bundle) {
		return bundle.getString("Actions.11");
	}

	public @NotNull Position getRefPosition() {
		return refPosition;
	}

	public void setNewX(final double x) {
		if(scaledWidth(x) > 1d) {
			newX = x;
		}
	}

	public void setNewY(final double y) {
		if(scaledHeight(y) > 1d) {
			newY = y;
		}
	}
}
