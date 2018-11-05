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
package net.sf.latexdraw.command;

import java.util.Optional;
import net.sf.latexdraw.model.api.shape.Drawing;
import org.malai.command.CommandImpl;

/**
 * @author Arnaud Blouin
 */
public abstract class DrawingCmdImpl extends CommandImpl {
	/** The drawing that will be handled by the command. */
	protected final Drawing drawing;

	protected DrawingCmdImpl(final Drawing theDrawing) {
		super();
		drawing = theDrawing;
	}

	public Optional<Drawing> getDrawing() {
		return Optional.ofNullable(drawing);
	}

	@Override
	public boolean canDo() {
		return drawing != null;
	}
}
