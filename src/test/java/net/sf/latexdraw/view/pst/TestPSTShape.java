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
package net.sf.latexdraw.view.pst;

import java.util.Arrays;
import net.sf.latexdraw.model.CompareShapeMatcher;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.BezierCurve;
import net.sf.latexdraw.model.api.shape.FreeHandStyle;
import net.sf.latexdraw.model.api.shape.Freehand;
import net.sf.latexdraw.model.api.shape.Polygon;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.view.PolymorphShapeTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

public class TestPSTShape extends TestPSTBase<Shape> implements PolymorphShapeTest {
	@Override
	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#getDiversifiedShapes")
	public void testLoadRotationAngleParams(final Shape sh) {
		assumeFalse(sh instanceof Polygon || sh instanceof BezierCurve);
		final Shape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualShapeRotationAngle(sh, s2);
	}

	@Override
	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#getDiversifiedShapes")
	public void testPointsEquals(final Shape sh) {
		assumeFalse(sh instanceof Freehand);
		assertThat(sh.getPoints()).isEqualTo(produceOutputShapeFrom(sh).getPoints());
	}

	@Test
	public void testFreeHandPointsLines() {
		final Freehand fh = ShapeFactory.INST.createFreeHand(Arrays.asList(ShapeFactory.INST.createPoint(50d, 70d),
			ShapeFactory.INST.createPoint(100d, 90d),
			ShapeFactory.INST.createPoint(132d, 112d),
			ShapeFactory.INST.createPoint(150d, 180d)));
		fh.setInterval(1);
		fh.setType(FreeHandStyle.LINES);

		final Shape s2 = produceOutputShapeFrom(fh);
		assertThat(s2.getPoints()).isEqualTo(produceOutputShapeFrom(s2).getPoints());
	}

	@Test
	public void testFreeHandPointsCurves() {
		final Freehand fh = ShapeFactory.INST.createFreeHand(Arrays.asList(
			ShapeFactory.INST.createPoint(50d, 70d),
			ShapeFactory.INST.createPoint(100d, 90d),
			ShapeFactory.INST.createPoint(110d, 100d),
			ShapeFactory.INST.createPoint(150d, 180d)));
		fh.setInterval(1);
		fh.setType(FreeHandStyle.CURVES);
		final Shape s2 = produceOutputShapeFrom(fh);
		assertThat(s2.getPoints()).isEqualTo(Arrays.asList(
			ShapeFactory.INST.createPoint(50d, 70d),
			ShapeFactory.INST.createPoint(75d, 80d),
			ShapeFactory.INST.createPoint(105d, 95d),
			ShapeFactory.INST.createPoint(130d, 140d)));
	}
}
