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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.PathElement;
import net.sf.latexdraw.models.interfaces.shape.IModifiablePointsShape;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import org.eclipse.jdt.annotation.NonNull;

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

		final ObservableList<PathElement> elts = border.getElements();
		moveTo = new MoveTo();
		moveTo.xProperty().bind(sh.getPtAt(0).xProperty());
		moveTo.yProperty().bind(sh.getPtAt(0).yProperty());
		elts.add(moveTo);

		lineTos = new ArrayList<>();

		IntStream.range(1, sh.getNbPoints()).forEach(i -> {
			LineTo lineto = new LineTo();
			lineto.xProperty().bind(sh.getPtAt(i).xProperty());
			lineto.yProperty().bind(sh.getPtAt(i).yProperty());
			lineTos.add(lineto);
			elts.add(lineto);
		});

		model.getPoints().addListener((ListChangeListener.Change<? extends IPoint> c) -> {
			while(c.next()) {
				if(c.wasAdded()) {
					c.getAddedSubList().forEach(pt -> {
						LineTo lineto = new LineTo();
						lineto.xProperty().bind(pt.xProperty());
						lineto.yProperty().bind(pt.yProperty());
						lineTos.add(lineto);
						elts.add(lineto);
						if(elts.get(elts.size()-2) instanceof ClosePath) {
							elts.remove(elts.size()-2);
							elts.add(new ClosePath());
						}
					});
				}
			}
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
		lineTos.clear();
		super.flush();
	}
}
