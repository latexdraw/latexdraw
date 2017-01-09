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
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import org.eclipse.jdt.annotation.NonNull;

/**
 * The JFX shape view for rectangles.
 */
public class ViewRectangle extends ViewSingleShape<IRectangle, Rectangle> {
	/**
	 * Creates the rectangle view.
	 * @param sh The model.
	 */
	public ViewRectangle(final @NonNull IRectangle sh) {
		super(sh);

		border.xProperty().bind(model.getPtAt(0).xProperty());
		border.yProperty().bind(model.getPtAt(0).yProperty());
		border.widthProperty().bind(Bindings.createDoubleBinding(model::getWidth, model.getPtAt(0).xProperty(), model.getPtAt(1).xProperty()));
		border.heightProperty().bind(Bindings.createDoubleBinding(model::getHeight, model.getPtAt(0).yProperty(), model.getPtAt(3).yProperty()));

		if(model.isDbleBorderable()) {
			dblBorder.xProperty().bind(Bindings.add(getDbleBorderGap(), border.xProperty()));
			dblBorder.yProperty().bind(Bindings.add(getDbleBorderGap(), border.yProperty()));
			dblBorder.heightProperty().bind(Bindings.subtract(border.heightProperty(), getDbleBorderGap()*2d));
			dblBorder.widthProperty().bind(Bindings.subtract(border.widthProperty(), getDbleBorderGap()*2d));
		}
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

		if(model.isDbleBorderable()) {
			dblBorder.widthProperty().unbind();
			dblBorder.heightProperty().unbind();
		}
	}
}
