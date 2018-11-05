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
package net.sf.latexdraw.command.shape;

import java.awt.geom.Rectangle2D;
import java.util.Optional;
import java.util.ResourceBundle;
import net.sf.latexdraw.command.Modifying;
import net.sf.latexdraw.command.ShapeCmdImpl;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Position;
import org.malai.undo.Undoable;

/**
 * This command scales a shape.
 * @author Arnaud Blouin
 */
public class ScaleShapes extends ShapeCmdImpl<Group> implements Undoable, Modifying {
	/** The direction of the scaling. */
	private final Position refPosition;

	/** The new X position used to compute the scale factor. */
	private double newX;

	/** The new Y position used to compute the scale factor. */
	private double newY;

	/** The bound of the selected shapes used to perform the scaling. */
	private final Rectangle2D bound;

	/** The old width of the selection. */
	private double oldWidth;

	/** The old height of the selection. */
	private double oldHeight;

	/** The drawing that will be handled by the command. */
	private final Drawing drawing;


	public ScaleShapes(final Group gp, final Drawing drawing, final Position refPosition) {
		super(gp);
		this.drawing = drawing;
		this.refPosition = refPosition;
		bound = new Rectangle2D.Double();
		setShape(gp);
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
		return drawing != null && refPosition != null && isValidScales() && super.canDo();
	}

	private boolean isValidScales() {
		if(refPosition == null) {
			return false;
		}

		switch(refPosition) {
			case EAST:
				return MathUtils.INST.isValidCoord(newX) && scaledWidth(newX) > 1d;
			case WEST:
				return MathUtils.INST.isValidCoord(newX) && scaledWidth(newX) > 1d;
			case NORTH:
				return MathUtils.INST.isValidCoord(newY) && scaledHeight(newY) > 1d;
			case SOUTH:
				return MathUtils.INST.isValidCoord(newY) && scaledHeight(newY) > 1d;
			default:
				return MathUtils.INST.isValidCoord(newX) && MathUtils.INST.isValidCoord(newY) && scaledHeight(newY) > 1d && scaledWidth(newX) > 1d;
		}
	}

	@Override
	protected void doCmdBody() {
		if(Double.isNaN(oldWidth)) {
			shape.ifPresent(sh -> {
				final Point br = sh.getBottomRightPoint();
				final Point tl = sh.getTopLeftPoint();
				oldWidth = br.getX() - tl.getX();
				oldHeight = br.getY() - tl.getY();
				updateBound(tl, br);
			});
		}
		redo();
	}

	private void updateBound(final Point tl, final Point br) {
		bound.setFrameFromDiagonal(tl.getX(), tl.getY(), br.getX(), br.getY());
	}

	@Override
	public void undo() {
		shape.ifPresent(sh -> {
			sh.scale(oldWidth, oldHeight, refPosition, bound);
			sh.setModified(true);
			drawing.setModified(true);
			updateBound(sh.getTopLeftPoint(), sh.getBottomRightPoint());
		});
	}

	@Override
	public void setShape(final Group sh) {
		super.setShape(sh);

		if(sh != null) {
			updateBound(sh.getTopLeftPoint(), sh.getBottomRightPoint());
		}
	}

	private double scaledHeight(final double y) {
		if(refPosition == null) {
			return 0d;
		}
		if(refPosition.isSouth()) {
			return bound.getHeight() + bound.getY() - y;
		}
		if(refPosition.isNorth()) {
			return bound.getHeight() + y - bound.getMaxY();
		}
		return 0d;
	}

	private double scaledWidth(final double x) {
		if(refPosition == null) {
			return 0d;
		}
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
		shape.ifPresent(sh -> {
			sh.scale(scaledWidth(newX), scaledHeight(newY), refPosition, bound);
			sh.setModified(true);
			drawing.setModified(true);
			updateBound(sh.getTopLeftPoint(), sh.getBottomRightPoint());
		});
	}

	@Override
	public String getUndoName(final ResourceBundle bundle) {
		return bundle.getString("Actions.11");
	}

	public Optional<Position> getRefPosition() {
		return Optional.ofNullable(refPosition);
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
