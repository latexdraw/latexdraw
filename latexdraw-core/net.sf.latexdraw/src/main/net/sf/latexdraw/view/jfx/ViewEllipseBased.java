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
package net.sf.latexdraw.view.jfx;

import javafx.beans.binding.Bindings;
import javafx.scene.shape.Ellipse;
import net.sf.latexdraw.models.interfaces.shape.ISingleShape;
import org.eclipse.jdt.annotation.NonNull;

/**
 * A JFX abstract view to factorise code of views based on a JFX ellipse.
 * @author Arnaud Blouin
 */
public abstract class ViewEllipseBased<T extends ISingleShape> extends ViewSingleShape<T, Ellipse> {
	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewEllipseBased(final @NonNull T sh) {
		super(sh);

		if(dblBorder != null) {
			dblBorder.centerXProperty().bind(border.centerXProperty());
			dblBorder.centerYProperty().bind(border.centerYProperty());
			dblBorder.radiusXProperty().bind(Bindings.subtract(border.radiusXProperty(), getDbleBorderGap()));
			dblBorder.radiusYProperty().bind(Bindings.subtract(border.radiusYProperty(), getDbleBorderGap()));
		}

		if(shadow != null) {
			shadow.centerXProperty().bind(border.centerXProperty());
			shadow.centerYProperty().bind(border.centerYProperty());
			shadow.radiusXProperty().bind(border.radiusXProperty());
			shadow.radiusYProperty().bind(border.radiusYProperty());
		}
	}

	private static void unbindEll(Ellipse sh) {
		if(sh != null) {
			sh.centerXProperty().unbind();
			sh.centerYProperty().unbind();
			sh.radiusXProperty().unbind();
			sh.radiusYProperty().unbind();
		}
	}

	@Override
	protected Ellipse createJFXShape() {
		return new Ellipse();
	}

	@Override
	public void flush() {
		unbindEll(border);
		unbindEll(dblBorder);
		unbindEll(shadow);
	}
}
