/*
 * This file is part of LaTeXDraw<br>
 * Copyright (c) 2005-2015 Arnaud BLOUIN<br>
 * <br>
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.<br>
 * <br>
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.<br>
 */
package net.sf.latexdraw.view.jfx;

import org.eclipse.jdt.annotation.NonNull;

import javafx.scene.shape.Rectangle;
import net.sf.latexdraw.glib.models.interfaces.shape.IRectangle;

/**
 * The JFX shape view for rectangles.<br>
 * 2015-11-13<br>
 * @author Arnaud BLOUIN
 * @since 4.0
 */
public class ViewRectangle extends ViewShape<IRectangle, Rectangle> {
	/**
	 * Creates the rectangle view.
	 * @param sh The model.
	 */
	public ViewRectangle(final @NonNull IRectangle sh) {
		super(sh);
	}

	@Override
	protected @NonNull Rectangle createJFXShape() {
		return new Rectangle();
	}
}
