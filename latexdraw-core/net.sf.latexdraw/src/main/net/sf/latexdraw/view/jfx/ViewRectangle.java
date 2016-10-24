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

import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.glib.models.interfaces.shape.IRectangle;
import org.eclipse.jdt.annotation.NonNull;

/**
 * The JFX shape view for rectangles.
 */
public class ViewRectangle extends ViewPathShape<IRectangle> {
	/** The top-left path element. */
	final MoveTo moveTL;
	/** The top-right path element. */
	final LineTo lineTR;
	/** The bottom-right path element. */
	final LineTo lineBR;
	/** The bottom-left path element. */
	final LineTo lineBL;

	/**
	 * Creates the rectangle view.
	 * @param sh The model.
	 */
	public ViewRectangle(final @NonNull IRectangle sh) {
		super(sh);

		moveTL = new MoveTo();
		lineTR = new LineTo();
		lineBR = new LineTo();
		lineBL = new LineTo();

		border.getElements().add(moveTL);
		border.getElements().add(lineTR);
		border.getElements().add(lineBR);
		border.getElements().add(lineBL);
		border.getElements().add(new ClosePath());

		IPoint pt = sh.getPtAt(0);
		moveTL.xProperty().bind(pt.xProperty());
		moveTL.yProperty().bind(pt.yProperty());

		pt = sh.getPtAt(1);
		lineTR.xProperty().bind(pt.xProperty());
		lineTR.yProperty().bind(pt.yProperty());

		pt = sh.getPtAt(2);
		lineBR.xProperty().bind(pt.xProperty());
		lineBR.yProperty().bind(pt.yProperty());

		pt = sh.getPtAt(3);
		lineBL.xProperty().bind(pt.xProperty());
		lineBL.yProperty().bind(pt.yProperty());
	}

	@Override
	public void flush() {
		moveTL.xProperty().unbind();
		moveTL.yProperty().unbind();

		lineTR.xProperty().unbind();
		lineTR.yProperty().unbind();

		lineBR.xProperty().unbind();
		lineBR.yProperty().unbind();

		lineBL.xProperty().unbind();
		lineBL.yProperty().unbind();

		super.flush();
	}
}
