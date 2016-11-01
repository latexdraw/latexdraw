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

import javafx.scene.image.ImageView;
import net.sf.latexdraw.models.interfaces.shape.IPicture;

/**
 * The JFX view of a picture.
 */
public class ViewPicture extends ViewShape<IPicture, ImageView> {
	ImageView view;

	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewPicture(final IPicture sh) {
		super(sh);
		view = new ImageView(model.getImage());
		view.xProperty().bind(model.getPosition().xProperty());
		view.yProperty().bind(model.getPosition().yProperty());
		getChildren().add(view);
	}

	@Override
	public void flush() {
		super.flush();
		view.xProperty().unbind();
		view.yProperty().unbind();
	}
}
