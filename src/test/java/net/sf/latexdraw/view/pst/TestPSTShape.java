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

import java.util.Arrays;
import net.sf.latexdraw.models.CompareShapeMatcher;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.IFreehand;
import net.sf.latexdraw.models.interfaces.shape.IPolygon;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.view.PolymorphShapeTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assumptions.assumeFalse;

public class TestPSTShape extends TestPSTBase<IShape> implements PolymorphShapeTest {
	@Override
	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#getDiversifiedShapes")
	public void testLoadRotationAngleParams(final IShape sh) {
		assumeFalse(sh instanceof IPolygon || sh instanceof IBezierCurve);
		final IShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualShapeRotationAngle(sh, s2);
	}

	@Override
	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#getDiversifiedShapes")
	public void testPointsEquals(final IShape sh) {
		assumeFalse(sh instanceof IFreehand);
		final IShape s2 = produceOutputShapeFrom(sh);
		assertListEquals(sh.getPoints(), s2.getPoints(), (p1, p2) -> p1.equals(p2, 0.001));
	}

	@Test
	public void testFreeHandPoints() {
		final IFreehand fh = ShapeFactory.INST.createFreeHand(Arrays.asList(ShapeFactory.INST.createPoint(51d, 73d), ShapeFactory.INST.createPoint(151d, 173d)));
		fh.setInterval(1);
		final IShape s2 = produceOutputShapeFrom(fh);
		assertListEquals(fh.getPoints(), s2.getPoints(), (p1, p2) -> p1.equals(p2, 0.001));
	}
}
