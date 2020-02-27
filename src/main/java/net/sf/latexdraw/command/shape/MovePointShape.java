/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
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
import net.sf.latexdraw.model.api.shape.ModifiablePointsShape;
import net.sf.latexdraw.model.api.shape.Point;
import org.jetbrains.annotations.NotNull;

/**
 * This command moves points of IModifiablePointsShape.
 * @author Arnaud Blouin
 */
public class MovePointShape extends MovePoint implements Undoable {
	/** The shape to modify. */
	private final @NotNull ModifiablePointsShape shape;
	private boolean mementoModified;

	public MovePointShape(final @NotNull ModifiablePointsShape shape, final @NotNull Point coord) {
		super(coord);
		this.shape = shape;
	}

	@Override
	protected void createMemento() {
		mementoModified = shape.isModified();
	}

	@Override
	protected void doCmdBody() {
		shape.setPoint(newCoord.getX(), newCoord.getY(), shape.getPoints().indexOf(point));
		shape.setModified(true);
	}

	@Override
	public boolean canDo() {
		return shape.getPoints().indexOf(point) != -1 && super.canDo();
	}

	@Override
	public void undo() {
		// Must use setPoint since other attributes of the shape may depend on the point (e.g. control points).
		shape.setPoint(mementoPoint.getX(), mementoPoint.getY(), shape.getPoints().indexOf(point));
		shape.setModified(mementoModified);
	}

	@Override
	public void redo() {
		doCmdBody();
	}

	@Override
	public @NotNull String getUndoName(final @NotNull ResourceBundle bundle) {
		return bundle.getString("Actions.10"); //NON-NLS
	}

	public @NotNull ModifiablePointsShape getShape() {
		return shape;
	}
}
