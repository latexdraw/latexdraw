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
import javafx.scene.shape.Ellipse;
import net.sf.latexdraw.glib.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import org.eclipse.jdt.annotation.NonNull;

/**
 * The JFX shape view for ellipses.
 */
public class ViewEllipse extends ViewSingleShape<IEllipse, Ellipse> {
	/**
	 * Creates the ellipse view.
	 * @param sh The model.
	 */
	public ViewEllipse(final @NonNull IEllipse sh) {
		super(sh);

		final IPoint tlp = model.getPtAt(0);
		final IPoint trp = model.getPtAt(1);
		final IPoint blp = model.getPtAt(3);
		final IPoint center = model.getCenter();

		border.centerXProperty().bind(Bindings.createDoubleBinding(center::getX, tlp.xProperty(), trp.xProperty()));
		border.centerYProperty().bind(Bindings.createDoubleBinding(center::getY, tlp.xProperty(), trp.xProperty()));
		border.radiusXProperty().bind(Bindings.createDoubleBinding(model::getA, tlp.xProperty(), trp.xProperty()));
		border.radiusYProperty().bind(Bindings.createDoubleBinding(model::getB, tlp.yProperty(), blp.yProperty()));
	}

	@Override
	protected Ellipse createJFXShape() {
		return new Ellipse();
	}

	@Override
	public void flush() {
		border.centerXProperty().unbind();
		border.centerYProperty().unbind();
		border.radiusXProperty().unbind();
		border.radiusYProperty().unbind();
	}
}
