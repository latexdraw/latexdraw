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

import io.github.interacto.command.Command;
import java.util.ArrayList;
import java.util.List;
import net.sf.latexdraw.command.DrawingCmdImpl;
import net.sf.latexdraw.command.Modifying;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Shape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This command allows to (un-)select shapes.
 * @author Arnaud Blouin
 */
public class SelectShapes extends DrawingCmdImpl implements Modifying {
	/** The shapes to handle. */
	private final @NotNull List<Shape> shapes;

	public SelectShapes(final @NotNull Drawing theDrawing) {
		super(theDrawing);
		shapes = new ArrayList<>();
	}

	@Override
	public void doCmdBody() {
		drawing.setSelection(shapes);
	}

	@Override
	public @NotNull RegistrationPolicy getRegistrationPolicy() {
		return RegistrationPolicy.UNLIMITED;
	}

	@Override
	public boolean unregisteredBy(final Command cmd) {
		return cmd instanceof SelectShapes || cmd instanceof CutShapes || cmd instanceof DeleteShapes;
	}

	public @NotNull List<Shape> getShapes() {
		return shapes;
	}

	/**
	 * Sets the shape to handle.
	 * @param shape The shape to handle. Can be null.
	 */
	public void setShape(final @Nullable Shape shape) {
		getShapes().clear();

		if(shape != null) {
			getShapes().add(shape);
		}
	}

	/**
	 * Add a shape to the list of shapes to handle.
	 * @param shape The shape to handle.
	 */
	public void addShape(final @Nullable Shape shape) {
		if(shape != null) {
			getShapes().add(shape);
		}
	}
}
