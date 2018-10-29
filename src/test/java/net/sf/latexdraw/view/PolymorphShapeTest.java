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
package net.sf.latexdraw.view;

import javafx.application.Platform;
import net.sf.latexdraw.data.ParameteriseShapeData;
import net.sf.latexdraw.models.CompareShapeMatcher;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public interface PolymorphShapeTest extends PolymorphicConversion<IShape> {
	@BeforeAll
	static void beforeClass() {
		try {
			Platform.startup(() -> {
			});
		}catch(final IllegalStateException ex) {
			// Ok
		}
	}

	@AfterAll
	static void tearDownAll() {
		ParameteriseShapeData.INST.clearTempFolders();
	}

	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#getDiversifiedShapes")
	default void testLoadSaveShapeParams(final IShape sh) {
		assumeTrue(sh.isBordersMovable());
		final IShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualShapeBorderMov(sh, s2);
	}

	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#getDiversifiedShapes")
	default void testLoadShapeLineStyleParams(final IShape sh) {
		assumeTrue(sh.isLineStylable());
		final IShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualShapeLineStyle(sh, s2);
	}

	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#getDiversifiedShapes")
	default void testLoadShapeShowPtsParams(final IShape sh) {
		assumeTrue(sh.isShowPtsable());
		final IShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualShapeShowPts(sh, s2);
	}

	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#getDiversifiedShapes")
	default void testLoadRotationAngleParams(final IShape sh) {
		final IShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualShapeRotationAngle(sh, s2);
	}

	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#getDiversifiedShapes")
	default void testLoadDbleBorderableParams(final IShape sh) {
		assumeTrue(sh.isDbleBorderable());
		final IShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualShapeDbleBorder(sh, s2);
	}

	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#getDiversifiedShapes")
	default void testLoadFillParams(final IShape sh) {
		assumeTrue(sh.isFillable());
		final IShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualShapeFill(sh, s2);
	}

	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#getDiversifiedShapes")
	default void testLoadFillStyleParams(final IShape sh) {
		assumeTrue(sh.isInteriorStylable());
		final IShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualShapeFillStyle(sh, s2);
	}

	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#getDiversifiedShapes")
	default void testLoadShadowParams(final IShape sh) {
		assumeTrue(sh.isShadowable());
		final IShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualShapeShadow(sh, s2);
	}

	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#getDiversifiedShapes")
	default void testPointsEquals(final IShape sh) {
		final IShape s2 = produceOutputShapeFrom(sh);
		assertEquals(sh.getPoints(), s2.getPoints());
	}
}
