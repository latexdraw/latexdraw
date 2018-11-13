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
module latexdraw {
	requires javafx.graphics;
	requires javafx.base;
	requires javafx.fxml;
	requires javafx.controls;
	requires javafx.swing;
	requires java.logging;
	requires malai.core;
	requires malai.javafx;
	requires antlr4.runtime;
	requires annotations;

	exports net.sf.latexdraw to javafx.graphics;
	exports net.sf.latexdraw.ui to javafx.fxml;
	exports net.sf.latexdraw.util to javafx.fxml;
	exports net.sf.latexdraw.view to javafx.fxml;
	exports net.sf.latexdraw.view.jfx to javafx.fxml;

	opens net.sf.latexdraw.instrument to javafx.fxml;
	opens res;
	opens lang;
	opens fxml;
	opens css;
	opens res.hatch;
	opens res.align;
	opens res.arrowStyles;
	opens res.cursors;
	opens res.distrib;
	opens res.dotStyles;
	opens res.doubleBoundary;
	opens res.freehand;
	opens res.lineStyles;
}
