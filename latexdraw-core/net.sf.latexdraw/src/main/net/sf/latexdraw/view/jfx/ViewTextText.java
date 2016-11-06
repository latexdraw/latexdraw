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

import javafx.scene.text.Text;
import net.sf.latexdraw.models.interfaces.shape.IText;

public class ViewTextText extends ViewSingleShape<IText, Text> {
	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewTextText(final IText sh) {
		super(sh);
		border.textProperty().bind(sh.textProperty());
		border.layoutXProperty().bind(sh.getPosition().xProperty());
		border.layoutYProperty().bind(sh.getPosition().yProperty());
	}

	@Override
	protected Text createJFXShape() {
		return new Text();
	}

	@Override
	public void flush() {
		border.textProperty().unbind();
		border.layoutXProperty().unbind();
		border.layoutYProperty().unbind();
		super.flush();
	}
}
