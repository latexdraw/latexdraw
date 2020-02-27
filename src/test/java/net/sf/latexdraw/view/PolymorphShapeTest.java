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
package net.sf.latexdraw.view;

import java.util.concurrent.TimeoutException;
import javafx.stage.Stage;
import net.sf.latexdraw.data.ParameteriseShapeData;
import net.sf.latexdraw.model.CompareShapeMatcher;
import net.sf.latexdraw.model.api.shape.Shape;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationAdapter;
import org.testfx.framework.junit5.ApplicationFixture;
import org.testfx.util.WaitForAsyncUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public interface PolymorphShapeTest extends PolymorphicConversion<Shape> {
	@BeforeAll
	static void beforeAll() throws TimeoutException {
		FxToolkit.registerPrimaryStage();
		FxToolkit.setupApplication(() -> new ApplicationAdapter(new ApplicationFixture() {
			@Override
			public void init() {
			}
			@Override
			public void start(final Stage stage) {
			}
			@Override
			public void stop() {
			}
		}));
	}

	@AfterAll
	static void tearDownAll() {
		ParameteriseShapeData.INST.clearTempFolders();
	}

	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#getDiversifiedShapes")
	default void testLoadSaveShapeParams(final Shape sh) {
		assumeTrue(sh.isBordersMovable());
		final Shape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualShapeBorderMov(sh, s2);
	}

	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#getDiversifiedShapes")
	default void testLoadShapeLineStyleParams(final Shape sh) {
		assumeTrue(sh.isLineStylable());
		final Shape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualShapeLineStyle(sh, s2);
	}

	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#getDiversifiedShapes")
	default void testLoadShapeShowPtsParams(final Shape sh) {
		assumeTrue(sh.isShowPtsable());
		final Shape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualShapeShowPts(sh, s2);
	}

	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#getDiversifiedShapes")
	default void testLoadRotationAngleParams(final Shape sh) {
		final Shape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualShapeRotationAngle(sh, s2);
	}

	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#getDiversifiedShapes")
	default void testLoadDbleBorderableParams(final Shape sh) {
		assumeTrue(sh.isDbleBorderable());
		final Shape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualShapeDbleBorder(sh, s2);
	}

	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#getDiversifiedShapes")
	default void testLoadFillParams(final Shape sh) {
		assumeTrue(sh.isFillable());
		final Shape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualShapeFill(sh, s2);
	}

	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#getDiversifiedShapes")
	default void testLoadFillStyleParams(final Shape sh) {
		assumeTrue(sh.isInteriorStylable());
		final Shape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualShapeFillStyle(sh, s2);
	}

	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#getDiversifiedShapes")
	default void testLoadShadowParams(final Shape sh) {
		assumeTrue(sh.isShadowable());
		final Shape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualShapeShadow(sh, s2);
	}

	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#getDiversifiedShapes")
	default void testPointsEquals(final Shape sh) {
		WaitForAsyncUtils.waitForFxEvents();
		final Shape s2 = produceOutputShapeFrom(sh);
		assertThat(sh.getPoints()).isEqualTo(s2.getPoints());
	}
}
