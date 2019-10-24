/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2019 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.view.jfx;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import net.sf.latexdraw.model.MathUtils;
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
import net.sf.latexdraw.service.LaTeXDataService;
import net.sf.latexdraw.util.Inject;
import net.sf.latexdraw.util.Tuple;
import org.jetbrains.annotations.NotNull;

/**
 * The factory that creates views from given models.
 * @author Arnaud Blouin
 */
public final class ViewFactory implements PathElementProducer, JfxViewProducer {
	private final @NotNull List<Tuple<Class<? extends Shape>, Function<Shape, ViewShape<?>>>> producers;

	@Inject
	public ViewFactory(final LaTeXDataService latexdata) {
		super();
		producers = new ArrayList<>();
		fillProducers(Objects.requireNonNull(latexdata));
	}

	private void fillProducers(final LaTeXDataService latexdata) {
		producers.add(new Tuple<>(Group.class, sh -> new ViewGroup((Group) sh, this)));
		producers.add(new Tuple<>(Plot.class, sh -> new ViewPlot((Plot) sh, this)));
		producers.add(new Tuple<>(Square.class, sh -> new ViewSquare((Square) sh)));
		producers.add(new Tuple<>(Rectangle.class, sh -> new ViewRectangle((Rectangle) sh)));
		producers.add(new Tuple<>(Text.class, sh -> new ViewText((Text) sh, latexdata)));
		producers.add(new Tuple<>(CircleArc.class, sh -> new ViewCircleArc((CircleArc) sh)));
		producers.add(new Tuple<>(Circle.class, sh -> new ViewCircle((Circle) sh)));
		producers.add(new Tuple<>(Ellipse.class, sh -> new ViewEllipse((Ellipse) sh)));
		producers.add(new Tuple<>(Triangle.class, sh -> new ViewTriangle((Triangle) sh, this)));
		producers.add(new Tuple<>(Rhombus.class, sh -> new ViewRhombus((Rhombus) sh, this)));
		producers.add(new Tuple<>(Polyline.class, sh -> new ViewPolyline((Polyline) sh, this)));
		producers.add(new Tuple<>(Polygon.class, sh -> new ViewPolygon((Polygon) sh, this)));
		producers.add(new Tuple<>(BezierCurve.class, sh -> new ViewBezierCurve((BezierCurve) sh, this)));
		producers.add(new Tuple<>(Axes.class, sh -> new ViewAxes((Axes) sh, this)));
		producers.add(new Tuple<>(Grid.class, sh -> new ViewGrid((Grid) sh, this)));
		producers.add(new Tuple<>(Dot.class, sh -> new ViewDot((Dot) sh, this)));
		producers.add(new Tuple<>(Picture.class, sh -> new ViewPicture((Picture) sh)));
		producers.add(new Tuple<>(Freehand.class, sh -> new ViewFreeHand((Freehand) sh, this)));
	}

	@Override
	public <T extends Shape, S extends ViewShape<T>> Optional<S> createView(final T shape) {
		// Makes use of a list of tuples to reduce the CC.
		// Looking for the tuple which type matches the type of the given shape.
		// Then calling the associated function that produces the PST view.
		return producers.stream()
			.filter(t -> t.a.isAssignableFrom(shape.getClass()))
			.findFirst()
			.map(t -> (S) t.b.apply(shape));
	}

	@Override
	public void flushPathElement(final PathElement elt) {
		if(elt instanceof LineTo) {
			final LineTo lineTo = (LineTo) elt;
			lineTo.xProperty().unbind();
			lineTo.yProperty().unbind();
		}else {
			if(elt instanceof MoveTo) {
				final MoveTo moveTo = (MoveTo) elt;
				moveTo.xProperty().unbind();
				moveTo.yProperty().unbind();
			}else {
				if(elt instanceof CubicCurveTo) {
					final CubicCurveTo cct = (CubicCurveTo) elt;
					cct.xProperty().unbind();
					cct.yProperty().unbind();
					cct.controlX1Property().unbind();
					cct.controlX2Property().unbind();
					cct.controlY1Property().unbind();
					cct.controlY2Property().unbind();
				}
			}
		}
	}

	@Override
	public Optional<PathElement> createPathElement(final PathElement elt) {
		if(elt instanceof LineTo) {
			return Optional.of(createLineTo(((LineTo) elt).getX(), ((LineTo) elt).getY()));
		}
		if(elt instanceof MoveTo) {
			return Optional.of(createMoveTo(((MoveTo) elt).getX(), ((MoveTo) elt).getY()));
		}
		if(elt instanceof ClosePath) {
			return Optional.of(createClosePath());
		}
		if(elt instanceof CubicCurveTo) {
			final CubicCurveTo curve = (CubicCurveTo) elt;
			return Optional.of(createCubicCurveTo(curve.getControlX1(), curve.getControlY1(), curve.getControlX2(), curve.getControlY2(), curve.getX(), curve.getY()));
		}
		return Optional.empty();
	}

	@Override
	public Path clonePath(final Path path) {
		return new Path(path.getElements().stream().map(elt -> createPathElement(elt).orElse(null)).
			filter(elt -> elt != null).collect(Collectors.toList()));
	}

	@Override
	public LineTo createLineTo(final double x, final double y) {
		return new EqLineTo(x, y);
	}

	@Override
	public MoveTo createMoveTo(final double x, final double y) {
		return new EqMoveTo(x, y);
	}

	@Override
	public ClosePath createClosePath() {
		return new EqClosePath();
	}

	@Override
	public CubicCurveTo createCubicCurveTo(final double controlX1, final double controlY1, final double controlX2, final double controlY2, final double x, final double y) {
		return new EqCubicCurveTo(controlX1, controlY1, controlX2, controlY2, x, y);
	}


	private static class EqCubicCurveTo extends CubicCurveTo {
		EqCubicCurveTo(final double controlX1, final double controlY1, final double controlX2, final double controlY2, final double x, final double y) {
			super(controlX1, controlY1, controlX2, controlY2, x, y);
		}

		@Override
		public boolean equals(final Object o) {
			if(this == o) {
				return true;
			}
			if(!(o instanceof CubicCurveTo)) {
				return false;
			}

			final CubicCurveTo that = (CubicCurveTo) o;

			if(Double.compare(that.getControlX1(), getControlX1()) != 0) {
				return false;
			}
			if(Double.compare(that.getControlY1(), getControlY1()) != 0) {
				return false;
			}
			if(Double.compare(that.getControlX2(), getControlX2()) != 0) {
				return false;
			}
			if(Double.compare(that.getControlY2(), getControlY2()) != 0) {
				return false;
			}
			if(Double.compare(that.getX(), getX()) != 0) {
				return false;
			}
			return Double.compare(that.getY(), getY()) == 0;
		}

		@Override
		public int hashCode() {
			int result;
			long temp;
			temp = Double.doubleToLongBits(getControlX1());
			result = (int) (temp ^ temp >>> 32);
			temp = Double.doubleToLongBits(getControlY1());
			result = 31 * result + (int) (temp ^ temp >>> 32);
			temp = Double.doubleToLongBits(getControlX2());
			result = 31 * result + (int) (temp ^ temp >>> 32);
			temp = Double.doubleToLongBits(getControlY2());
			result = 31 * result + (int) (temp ^ temp >>> 32);
			temp = Double.doubleToLongBits(getX());
			result = 31 * result + (int) (temp ^ temp >>> 32);
			temp = Double.doubleToLongBits(getY());
			result = 31 * result + (int) (temp ^ temp >>> 32);
			return result;
		}
	}


	private static class EqClosePath extends ClosePath {
		@Override
		public boolean equals(final Object obj) {
			return obj == this || obj instanceof ClosePath;
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}
	}


	private static class EqLineTo extends LineTo {
		EqLineTo(final double x, final double y) {
			super(x, y);
		}

		@Override
		public int hashCode() {
			int result;
			long temp = Double.doubleToLongBits(getX());
			result = (int) (temp ^ temp >>> 32);
			temp = Double.doubleToLongBits(getY());
			result = 31 * result + (int) (temp ^ temp >>> 32);
			result = 31 * result + (isAbsolute() ? 1 : 0);
			return result;
		}

		@Override
		public boolean equals(final Object obj) {
			if(obj == this) {
				return true;
			}
			if(!(obj instanceof LineTo)) {
				return false;
			}
			final LineTo lt = (LineTo) obj;
			return MathUtils.INST.equalsDouble(lt.getX(), getX()) && MathUtils.INST.equalsDouble(lt.getY(), getY()) && lt.isAbsolute() == isAbsolute();
		}
	}


	private static class EqMoveTo extends MoveTo {
		EqMoveTo(final double x, final double y) {
			super(x, y);
		}

		@Override
		public int hashCode() {
			int result;
			long temp = Double.doubleToLongBits(getX());
			result = (int) (temp ^ temp >>> 32);
			temp = Double.doubleToLongBits(getY());
			result = 31 * result + (int) (temp ^ temp >>> 32);
			result = 31 * result + (isAbsolute() ? 1 : 0);
			return result;
		}

		@Override
		public boolean equals(final Object obj) {
			if(obj == this) {
				return true;
			}
			if(!(obj instanceof MoveTo)) {
				return false;
			}
			final MoveTo mt = (MoveTo) obj;
			return MathUtils.INST.equalsDouble(mt.getX(), getX()) && MathUtils.INST.equalsDouble(mt.getY(), getY()) && mt.isAbsolute() == isAbsolute();
		}
	}
}
