/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.actions;

import java.util.Optional;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import org.malai.action.ActionImpl;

/**
 * @author Arnaud Blouin
 */
public abstract class DrawingActionImpl extends ActionImpl {
	/** The drawing that will be handled by the action. */
	protected IDrawing drawing;

	protected DrawingActionImpl(final IDrawing theDrawing) {
		super();
		drawing = theDrawing;
	}

	protected DrawingActionImpl() {
		this(null);
	}

	public void setDrawing(final IDrawing dr) {
		drawing = dr;
	}

	public Optional<IDrawing> getDrawing() {
		return Optional.ofNullable(drawing);
	}

	@Override
	public boolean canDo() {
		return drawing != null;
	}
}
