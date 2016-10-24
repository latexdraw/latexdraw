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

import javafx.beans.binding.Bindings;
import javafx.scene.shape.Rectangle;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.glib.models.interfaces.shape.IRectangle;
import org.eclipse.jdt.annotation.NonNull;

/**
 * The JFX shape view for rectangles.
 */
public class ViewRectangle2 extends ViewSingleShape<IRectangle, Rectangle> {
	/**
	 * Creates the rectangle view.
	 * @param sh The model.
	 */
	public ViewRectangle2(final @NonNull IRectangle sh) {
		super(sh);

		final IPoint tlp = model.getPtAt(0);
		final IPoint trp = model.getPtAt(1);
		final IPoint blp = model.getPtAt(3);

		border.xProperty().bind(tlp.xProperty());
		border.yProperty().bind(tlp.yProperty());
		border.widthProperty().bind(Bindings.createDoubleBinding(model::getWidth, tlp.xProperty(), trp.xProperty()));
		border.heightProperty().bind(Bindings.createDoubleBinding(model::getHeight, tlp.yProperty(), blp.yProperty()));
	}

	@Override
	protected Rectangle createJFXShape() {
		return new Rectangle();
	}

	@Override
	public void flush() {
		border.xProperty().unbind();
		border.yProperty().unbind();
		border.widthProperty().unbind();
		border.heightProperty().unbind();
	}
}
