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

import java.util.stream.IntStream;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import net.sf.latexdraw.models.interfaces.shape.IModifiablePointsShape;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import org.eclipse.jdt.annotation.NonNull;

/**
 * The JFX shape view for multipoints shapes.
 * @author Arnaud Blouin
 */
public abstract class ViewPolyPoint<T extends IModifiablePointsShape> extends ViewPathShape<T> {
	/**
	 * Creates the view.
	 * @param sh The model.
	 */
	ViewPolyPoint(final  T sh) {
		super(sh);
		initPath(border);
		initPath(shadow);
		initPath(dblBorder);
	}

	private void initPath(final Path path) {
		final ObservableList<PathElement> elts = path.getElements();
		final MoveTo moveTo = ViewFactory.INSTANCE.createMoveTo(0d, 0d);
		moveTo.xProperty().bind(model.getPtAt(0).xProperty());
		moveTo.yProperty().bind(model.getPtAt(0).yProperty());
		elts.add(moveTo);

		IntStream.range(1, model.getNbPoints()).forEach(i -> {
			LineTo lineto = ViewFactory.INSTANCE.createLineTo(0d, 0d);
			lineto.xProperty().bind(model.getPtAt(i).xProperty());
			lineto.yProperty().bind(model.getPtAt(i).yProperty());
			elts.add(lineto);
		});

		model.getPoints().addListener((ListChangeListener.Change<? extends IPoint> c) -> {
			while(c.next()) {
				if(c.wasAdded()) {
					c.getAddedSubList().forEach(pt -> {
						LineTo lineto = ViewFactory.INSTANCE.createLineTo(0d, 0d);
						lineto.xProperty().bind(pt.xProperty());
						lineto.yProperty().bind(pt.yProperty());
						elts.add(lineto);
						if(elts.get(elts.size() - 2) instanceof ClosePath) {
							elts.remove(elts.size() - 2);
							elts.add(ViewFactory.INSTANCE.createClosePath());
						}
					});
				}
			}
		});
	}

	@Override
	public void flush() {
		flushPath(border);
		flushPath(shadow);
		flushPath(dblBorder);
		super.flush();
	}

	private static void flushPath(final Path path) {
		path.getElements().forEach(elt -> {
			if(elt instanceof LineTo) {
				LineTo lineTo = (LineTo) elt;
				lineTo.xProperty().unbind();
				lineTo.yProperty().unbind();
			}else if(elt instanceof MoveTo) {
				MoveTo moveTo = (MoveTo) elt;
				moveTo.xProperty().unbind();
				moveTo.yProperty().unbind();
			}
		});
	}
}
