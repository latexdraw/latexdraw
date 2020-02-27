/*
 * This file is part of LaTeXDraw.
 * Copyright (c) 2005-2020 Arnaud BLOUIN
 * LaTeXDraw is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * LaTeXDraw is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 */
package net.sf.latexdraw.instrument;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.ArcStyle;
import net.sf.latexdraw.model.api.shape.BezierCurve;
import net.sf.latexdraw.model.api.shape.CircleArc;
import net.sf.latexdraw.model.api.shape.Shape;
import org.jetbrains.annotations.NotNull;

/**
 * Defines the editing choices tht can be done on the canvas.
 * @author Arnaud BLOUIN
 */
public enum EditionChoice {
	HAND {
		@Override
		public @NotNull Optional<Shape> createShapeInstance() {
			return Optional.empty();
		}
	},
	RECT {
		@Override
		public @NotNull Optional<Shape> createShapeInstance() {
			return Optional.of(ShapeFactory.INST.createRectangle());
		}
	}, DOT {
		@Override
		public @NotNull Optional<Shape> createShapeInstance() {
			return Optional.of(ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint()));
		}
	}, SQUARE {
		@Override
		public @NotNull Optional<Shape> createShapeInstance() {
			return Optional.of(ShapeFactory.INST.createSquare());
		}
	}, RHOMBUS {
		@Override
		public @NotNull Optional<Shape> createShapeInstance() {
			return Optional.of(ShapeFactory.INST.createRhombus());
		}
	}, FREE_HAND {
		@Override
		public @NotNull Optional<Shape> createShapeInstance() {
			return Optional.of(ShapeFactory.INST.createFreeHand(Collections.singletonList(ShapeFactory.INST.createPoint())));
		}
	}, TRIANGLE {
		@Override
		public @NotNull Optional<Shape> createShapeInstance() {
			return Optional.of(ShapeFactory.INST.createTriangle());
		}
	}, LINES {
		@Override
		public @NotNull Optional<Shape> createShapeInstance() {
			return Optional.of(ShapeFactory.INST.createPolyline(Arrays.asList(ShapeFactory.INST.createPoint(), ShapeFactory.INST.createPoint())));
		}
	}, CIRCLE {
		@Override
		public @NotNull Optional<Shape> createShapeInstance() {
			return Optional.of(ShapeFactory.INST.createCircle());
		}
	}, GRID {
		@Override
		public @NotNull Optional<Shape> createShapeInstance() {
			return Optional.of(ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint()));
		}
	}, AXES {
		@Override
		public @NotNull Optional<Shape> createShapeInstance() {
			return Optional.of(ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint()));
		}
	}, ELLIPSE {
		@Override
		public @NotNull Optional<Shape> createShapeInstance() {
			return Optional.of(ShapeFactory.INST.createEllipse());
		}
	}, POLYGON {
		@Override
		public @NotNull Optional<Shape> createShapeInstance() {
			return Optional.of(ShapeFactory.INST.createPolygon(Arrays.asList(ShapeFactory.INST.createPoint(), ShapeFactory.INST.createPoint())));
		}
	}, CIRCLE_ARC {
		@Override
		public @NotNull Optional<Shape> createShapeInstance() {
			final CircleArc shape = ShapeFactory.INST.createCircleArc();
			shape.setArcStyle(ArcStyle.ARC);
			return Optional.of(shape);
		}
	}, BEZIER_CURVE {
		@Override
		public @NotNull Optional<Shape> createShapeInstance() {
			final BezierCurve shape = ShapeFactory.INST.createBezierCurve(Arrays.asList(ShapeFactory.INST.createPoint(), ShapeFactory.INST.createPoint()));
			shape.setOpened(true);
			return Optional.of(shape);
		}
	}, TEXT {
		@Override
		public @NotNull Optional<Shape> createShapeInstance() {
			return Optional.of(ShapeFactory.INST.createText());
		}
	}, PICTURE {
		@Override
		public @NotNull Optional<Shape> createShapeInstance() {
			return Optional.of(ShapeFactory.INST.createPicture(ShapeFactory.INST.createPoint()));
		}
	}, PLOT {
		@Override
		public @NotNull Optional<Shape> createShapeInstance() {
			return Optional.of(ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(), 1, 5, "x", false)); //NON-NLS
		}
	};

	/**
	 * @return A new shape instance corresponding to the editing choice.
	 */
	public abstract @NotNull Optional<Shape> createShapeInstance();
}
