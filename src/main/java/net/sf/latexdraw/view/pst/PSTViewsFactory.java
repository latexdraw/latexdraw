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
package net.sf.latexdraw.view.pst;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Function;
import net.sf.latexdraw.model.api.shape.Axes;
import net.sf.latexdraw.model.api.shape.BezierCurve;
import net.sf.latexdraw.model.api.shape.Circle;
import net.sf.latexdraw.model.api.shape.CircleArc;
import net.sf.latexdraw.model.api.shape.Dot;
import net.sf.latexdraw.model.api.shape.Ellipse;
import net.sf.latexdraw.model.api.shape.Freehand;
import net.sf.latexdraw.model.api.shape.Grid;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Picture;
import net.sf.latexdraw.model.api.shape.Plot;
import net.sf.latexdraw.model.api.shape.Polygon;
import net.sf.latexdraw.model.api.shape.Polyline;
import net.sf.latexdraw.model.api.shape.Rectangle;
import net.sf.latexdraw.model.api.shape.Rhombus;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.model.api.shape.Square;
import net.sf.latexdraw.model.api.shape.Text;
import net.sf.latexdraw.model.api.shape.Triangle;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.Tuple;
import org.jetbrains.annotations.NotNull;

/**
 * A singleton factory that produces PSTricks views.
 * @author Arnaud Blouin
 */
public final class PSTViewsFactory implements PSTViewProducer {
	private final @NotNull List<Tuple<Class<? extends Shape>, Function<@NotNull Shape, @NotNull PSTShapeView<?>>>> producers;

	@Inject
	public PSTViewsFactory(final ResourceBundle lang) {
		super();
		producers = new ArrayList<>();
		fillProducers(Objects.requireNonNull(lang));
	}

	private void fillProducers(final @NotNull ResourceBundle lang) {
		producers.add(new Tuple<>(Group.class, sh -> new PSTGroupView((Group) sh, this)));
		producers.add(new Tuple<>(Plot.class, sh -> new PSTPlotView((Plot) sh)));
		producers.add(new Tuple<>(Square.class, sh -> new PSTSquareView((Square) sh)));
		producers.add(new Tuple<>(Rectangle.class, sh -> new PSTRectView((Rectangle) sh)));
		producers.add(new Tuple<>(Text.class, sh -> new PSTTextView((Text) sh)));
		producers.add(new Tuple<>(CircleArc.class, sh -> new PSTArcView((CircleArc) sh)));
		producers.add(new Tuple<>(Circle.class, sh -> new PSTCircleView((Circle) sh)));
		producers.add(new Tuple<>(Ellipse.class, sh -> new PSTEllipseView((Ellipse) sh)));
		producers.add(new Tuple<>(Triangle.class, sh -> new PSTTriangleView((Triangle) sh)));
		producers.add(new Tuple<>(Rhombus.class, sh -> new PSTRhombusView((Rhombus) sh)));
		producers.add(new Tuple<>(Polyline.class, sh -> new PSTLinesView((Polyline) sh)));
		producers.add(new Tuple<>(Polygon.class, sh -> new PSTPolygonView((Polygon) sh)));
		producers.add(new Tuple<>(BezierCurve.class, sh -> new PSTBezierCurveView((BezierCurve) sh)));
		producers.add(new Tuple<>(Axes.class, sh -> new PSTAxesView((Axes) sh)));
		producers.add(new Tuple<>(Grid.class, sh -> new PSTGridView((Grid) sh)));
		producers.add(new Tuple<>(Dot.class, sh -> new PSTDotView((Dot) sh)));
		producers.add(new Tuple<>(Picture.class, sh -> new PSTPictureView((Picture) sh, lang)));
		producers.add(new Tuple<>(Freehand.class, sh -> new PSTFreeHandView((Freehand) sh)));
	}

	@Override
	public <T extends Shape> Optional<PSTShapeView<T>> createView(final @NotNull T shape) {
		// Makes use of a list of tuples to reduce the CC.
		// Looking for the tuple which type matches the type of the given shape.
		// Then calling the associated function that produces the PST view.
		return producers.stream()
			.filter(t -> t.a.isAssignableFrom(shape.getClass()))
			.findFirst()
			.map(t -> (PSTShapeView<T>) t.b.apply(shape));
	}
}
