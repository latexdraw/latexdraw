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

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import net.sf.latexdraw.models.interfaces.shape.IModifiablePointsShape;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import org.eclipse.jdt.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * The JFX shape view for multipoints shapes.
 */
abstract class ViewPolyPoint<T extends IModifiablePointsShape> extends ViewPathShape<T> {
	final MoveTo moveTo;
	final List<LineTo> lineTos;

	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewPolyPoint(final @NonNull T sh) {
		super(sh);

		moveTo = new MoveTo();
		IPoint pt = sh.getPtAt(0);
		moveTo.xProperty().bind(pt.xProperty());
		moveTo.yProperty().bind(pt.yProperty());
		border.getElements().add(moveTo);

		lineTos = new ArrayList<>();

		IntStream.range(1, sh.getNbPoints()).forEach(i -> {
			LineTo lineto = new LineTo();
			IPoint p = sh.getPtAt(i);
			lineto.xProperty().bind(p.xProperty());
			lineto.yProperty().bind(p.yProperty());
			lineTos.add(lineto);
			border.getElements().add(lineto);
		});
	}

	@Override
	public void flush() {
		moveTo.xProperty().unbind();
		moveTo.yProperty().unbind();

		lineTos.forEach(lineTo -> {
			lineTo.xProperty().unbind();
			lineTo.yProperty().unbind();
		});
		super.flush();
	}
}
