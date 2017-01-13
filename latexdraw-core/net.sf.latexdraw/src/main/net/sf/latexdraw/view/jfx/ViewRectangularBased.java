/*
 * This file is part of LaTeXDraw
 * Copyright (c) 2005-2017 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 */
package net.sf.latexdraw.view.jfx;

import javafx.beans.binding.Bindings;
import javafx.scene.shape.Rectangle;
import net.sf.latexdraw.models.interfaces.shape.ISingleShape;
import org.eclipse.jdt.annotation.NonNull;

class ViewRectangularBased<T extends ISingleShape> extends ViewSingleShape<T, Rectangle> {
	/**
	 * Creates the rectangle view.
	 * @param sh The model.
	 */
	ViewRectangularBased(final @NonNull T sh) {
		super(sh);

		if(dblBorder != null) {
			dblBorder.xProperty().bind(Bindings.add(getDbleBorderGap(), border.xProperty()));
			dblBorder.yProperty().bind(Bindings.add(getDbleBorderGap(), border.yProperty()));
			dblBorder.heightProperty().bind(Bindings.subtract(border.heightProperty(), getDbleBorderGap() * 2d));
			dblBorder.widthProperty().bind(Bindings.subtract(border.widthProperty(), getDbleBorderGap() * 2d));
		}

		if(shadow != null) {
			shadow.xProperty().bind(border.xProperty());
			shadow.yProperty().bind(border.yProperty());
			shadow.widthProperty().bind(border.widthProperty());
			shadow.heightProperty().bind(border.heightProperty());
		}
	}


	private static void unbindRect(Rectangle rec) {
		if(rec != null) {
			rec.xProperty().unbind();
			rec.yProperty().unbind();
			rec.widthProperty().unbind();
			rec.heightProperty().unbind();
		}
	}

	@Override
	protected Rectangle createJFXShape() {
		return new Rectangle();
	}

	@Override
	public void flush() {
		unbindRect(border);
		unbindRect(dblBorder);
		unbindRect(shadow);
	}
}
