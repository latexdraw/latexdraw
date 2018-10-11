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
package net.sf.latexdraw.commands.shape;

import java.awt.geom.Rectangle2D;
import java.util.Optional;
import net.sf.latexdraw.commands.DrawingCmd;
import net.sf.latexdraw.commands.Modifying;
import net.sf.latexdraw.commands.ShapeCmdImpl;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.Position;
import net.sf.latexdraw.util.LangTool;
import org.malai.undo.Undoable;

/**
 * This command scales a shape.
 * @author Arnaud Blouin
 */
public class ScaleShapes extends ShapeCmdImpl<IGroup> implements DrawingCmd, Undoable, Modifying {
	/** The direction of the scaling. */
	Optional<Position> refPosition;

	/** The new X position used to compute the scale factor. */
	double newX;

	/** The new Y position used to compute the scale factor. */
	double newY;

	/** The bound of the selected shapes used to perform the scaling. */
	final Rectangle2D bound;

	/** The old width of the selection. */
	double oldWidth;

	/** The old height of the selection. */
	double oldHeight;

	boolean doneOnce;

	/** The drawing that will be handled by the command. */
	protected Optional<IDrawing> drawing;


	public ScaleShapes() {
		super();
		drawing = Optional.empty();
		refPosition = Optional.empty();
		bound = new Rectangle2D.Double();
		doneOnce = false;
	}

	@Override
	public RegistrationPolicy getRegistrationPolicy() {
		return hadEffect() ? RegistrationPolicy.LIMITED : RegistrationPolicy.NONE;
	}

	@Override
	public boolean hadEffect() {
		return isDone() && (!MathUtils.INST.equalsDouble(oldWidth, bound.getWidth()) || !MathUtils.INST.equalsDouble(oldHeight, bound.getHeight()));
	}

	@Override
	public boolean canDo() {
		return super.canDo() && drawing.isPresent() && refPosition.isPresent() && isValidScales();
	}

	private boolean isValidScales() {
		if(!refPosition.isPresent()) {
			return false;
		}
		switch(refPosition.get()) {
			case EAST:
				return MathUtils.INST.isValidCoord(newX) && scaledWidth(newX) > 1.0;
			case WEST:
				return MathUtils.INST.isValidCoord(newX) && scaledWidth(newX) > 1.0;
			case NORTH:
				return MathUtils.INST.isValidCoord(newY) && scaledHeight(newY) > 1.0;
			case SOUTH:
				return MathUtils.INST.isValidCoord(newY) && scaledHeight(newY) > 1.0;
			default:
				return MathUtils.INST.isValidCoord(newX) && MathUtils.INST.isValidCoord(newY) && scaledHeight(newY) > 1.0 && scaledWidth(newX) > 1.0;
		}
	}

	@Override
	protected void doCmdBody() {
		if(Double.isNaN(oldWidth)) {
			shape.ifPresent(sh -> {
				final IPoint br = sh.getBottomRightPoint();
				final IPoint tl = sh.getTopLeftPoint();
				oldWidth = br.getX() - tl.getX();
				oldHeight = br.getY() - tl.getY();
				updateBound(tl, br);
			});
		}
		redo();
	}

	private void updateBound(final IPoint tl, final IPoint br) {
		bound.setFrameFromDiagonal(tl.getX(), tl.getY(), br.getX(), br.getY());
	}

	@Override
	public void undo() {
		shape.ifPresent(sh -> refPosition.ifPresent(pos -> drawing.ifPresent(dr -> {
			sh.scale(oldWidth, oldHeight, pos, bound);
			sh.setModified(true);
			dr.setModified(true);
			updateBound(sh.getTopLeftPoint(), sh.getBottomRightPoint());
		})));
	}

	@Override
	public void setShape(final IGroup sh) {
		super.setShape(sh);

		if(sh != null) {
			updateBound(sh.getTopLeftPoint(), sh.getBottomRightPoint());
		}
	}

	private double scaledHeight(final double y) {
		if(!refPosition.isPresent()) {
			return 0.0;
		}
		if(refPosition.get().isSouth()) {
			return bound.getHeight() + bound.getY() - y;
		}
		if(refPosition.get().isNorth()) {
			return bound.getHeight() + y - bound.getMaxY();
		}
		return 0.0;
	}

	private double scaledWidth(final double x) {
		if(!refPosition.isPresent()) {
			return 0.0;
		}
		if(refPosition.get().isWest()) {
			return bound.getWidth() + x - bound.getMaxX();
		}
		if(refPosition.get().isEast()) {
			return bound.getWidth() + bound.getX() - x;
		}
		return 0.0;
	}

	@Override
	public void redo() {
		shape.ifPresent(sh -> refPosition.ifPresent(pos -> drawing.ifPresent(dr -> {
			sh.scale(scaledWidth(newX), scaledHeight(newY), pos, bound);
			sh.setModified(true);
			dr.setModified(true);
			updateBound(sh.getTopLeftPoint(), sh.getBottomRightPoint());
		})));
	}

	@Override
	public String getUndoName() {
		return LangTool.INSTANCE.getBundle().getString("Actions.11");
	}

	public Optional<Position> getRefPosition() {
		return refPosition;
	}

	public void setRefPosition(final Position pos) {
		refPosition = Optional.ofNullable(pos);
	}

	public double getNewX() {
		return newX;
	}

	public void setNewX(final double x) {
		if(scaledWidth(x) > 1.0) {
			newX = x;
		}
	}

	public double getNewY() {
		return newY;
	}

	public void setNewY(final double y) {
		if(scaledHeight(y) > 1.0) {
			newY = y;
		}
	}

	@Override
	public void setDrawing(final IDrawing dr) {
		drawing = Optional.ofNullable(dr);
	}

	@Override
	public Optional<IDrawing> getDrawing() {
		return drawing;
	}
}
