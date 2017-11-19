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
package net.sf.latexdraw;

import javafx.fxml.JavaFXBuilderFactory;
import javafx.util.Builder;
import javafx.util.BuilderFactory;
import net.sf.latexdraw.util.Injector;
import net.sf.latexdraw.view.jfx.Canvas;

/**
 * @author Arnaud Blouin
 */
class LatexdrawBuilderFactory implements BuilderFactory {
	private final Injector injector;
	private final BuilderFactory defaultFactory;

	LatexdrawBuilderFactory(final Injector inj) {
		super();
		injector = inj;
		defaultFactory = new JavaFXBuilderFactory();
	}

	@Override
	public Builder<?> getBuilder(final Class<?> type) {
		if(type == Canvas.class) return () -> injector.getInstance(type);
		return defaultFactory.getBuilder(type);
	}
}
