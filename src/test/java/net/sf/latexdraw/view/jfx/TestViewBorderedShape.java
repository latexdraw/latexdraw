package net.sf.latexdraw.view.jfx;

import java.util.Arrays;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.LinearGradient;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.BorderPos;
import net.sf.latexdraw.models.interfaces.shape.FillingStyle;
import net.sf.latexdraw.models.interfaces.shape.ISingleShape;
import net.sf.latexdraw.models.interfaces.shape.LineStyle;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;


abstract class TestViewBorderedShape<T extends ViewSingleShape<S, R>, S extends ISingleShape, R extends Shape> extends TestViewShape<T, S>
	implements ITestViewBorderedShape<T, S, R> {
	protected R border;

	@BeforeEach
	void setUp() {
		border = view.getBorder();
	}

	@Override
	public R getBorder() {
		return border;
	}

	@Test
	void testLineColor() {
		model.setLineColour(DviPsColors.BITTERSWEET);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(DviPsColors.BITTERSWEET, ShapeFactory.INST.createColorFX((javafx.scene.paint.Color) border.getStroke()));
	}

	@Test
	void testLineThickness() {
		assumeTrue(model.isThicknessable());
		model.setThickness(10d);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(10d, border.getStrokeWidth(), 0.001);
	}

	@Test
	void testBorderAdded() {
		assertTrue(view.getChildren().stream().anyMatch(c -> c == border));
	}

	@Test
	void testBorderEnable() {
		assertFalse(view.getBorder().isDisable());
	}

	@Test
	void testDoubleLineThickness() {
		assumeTrue(model.isDbleBorderable());
		model.setHasDbleBord(true);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(model.getFullThickness(), border.getStrokeWidth(), 0.001);
	}

	@Test
	void testDoubleLineSepThickness() {
		assumeTrue(model.isDbleBorderable());
		model.setHasDbleBord(true);
		model.setDbleBordSep(30d);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(model.getFullThickness(), border.getStrokeWidth(), 0.001);
	}

	@Test
	void testDoubleLineCol() {
		assumeTrue(model.isDbleBorderable());
		model.setHasDbleBord(true);
		model.setDbleBordCol(DviPsColors.APRICOT);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(DviPsColors.APRICOT, view.getDbleBorder().map(b -> ShapeFactory.INST.createColorFX((Color) b.getStroke())).orElse(null));
	}

	@Test
	void testDoubleBorderAdded() {
		assumeTrue(model.isDbleBorderable());
		assertTrue(view.getChildren().stream().anyMatch(c -> c == view.getDbleBorder().orElseThrow()));
	}

	@Test
	void testDoubleBorderDefaultDisable() {
		assumeTrue(model.isDbleBorderable());
		assertTrue(view.getDbleBorder().orElseThrow().isDisable());
	}

	@Test
	void testDoubleBorderEnable() {
		assumeTrue(model.isDbleBorderable());
		model.setHasDbleBord(true);
		WaitForAsyncUtils.waitForFxEvents();
		assertFalse(view.getDbleBorder().orElseThrow().isDisable());
	}

	@Test
	void testDoubleBorderDisable() {
		assumeTrue(model.isDbleBorderable());
		model.setHasDbleBord(true);
		model.setHasDbleBord(false);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(view.getDbleBorder().orElseThrow().isDisable());
	}

	@Test
	void testDoubleBorderDefaultVisible() {
		assumeTrue(model.isDbleBorderable());
		assertFalse(view.getDbleBorder().orElseThrow().isVisible());
	}

	@Test
	void testDoubleBorderVisible() {
		assumeTrue(model.isDbleBorderable());
		model.setHasDbleBord(true);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(view.getDbleBorder().orElseThrow().isVisible());
	}

	@Test
	void testDoubleBorderNotVisible() {
		assumeTrue(model.isDbleBorderable());
		model.setHasDbleBord(true);
		model.setHasDbleBord(false);
		WaitForAsyncUtils.waitForFxEvents();
		assertFalse(view.getDbleBorder().orElseThrow().isVisible());
	}

	@Test
	void testDoubleBorderStrokeWidth() {
		assumeTrue(model.isDbleBorderable());
		model.setHasDbleBord(true);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(model.getDbleBordSep(), view.getDbleBorder().orElseThrow().getStrokeWidth(), 0.001);
	}

	@Test
	void testDoubleBorderNoFill() {
		assumeTrue(model.isDbleBorderable());
		model.setHasDbleBord(true);
		WaitForAsyncUtils.waitForFxEvents();
		assertNull(view.getDbleBorder().orElseThrow().getFill());
	}

	@Test
	void testLineStylePlainLineCap() {
		assumeTrue(model.isLineStylable());
		model.setLineStyle(LineStyle.SOLID);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(StrokeLineCap.BUTT, border.getStrokeLineCap());
	}

	@Test
	void testLineStyleDashedSep() {
		assumeTrue(model.isLineStylable());
		model.setLineStyle(LineStyle.DASHED);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(Arrays.asList(model.getDashSepBlack(), model.getDashSepWhite()), border.getStrokeDashArray());
	}

	@Test
	void testLineStyleDashedLineCap() {
		assumeTrue(model.isLineStylable());
		model.setLineStyle(LineStyle.DASHED);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(StrokeLineCap.BUTT, border.getStrokeLineCap());
	}

	@Test
	void testDashSepBlackBefore() {
		assumeTrue(model.isLineStylable());
		model.setDashSepBlack(21d);
		model.setLineStyle(LineStyle.DASHED);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(Arrays.asList(21d, model.getDashSepWhite()), border.getStrokeDashArray());
	}

	@Test
	void testDashSepBlack() {
		assumeTrue(model.isLineStylable());
		model.setLineStyle(LineStyle.DASHED);
		model.setDashSepBlack(21d);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(Arrays.asList(21d, model.getDashSepWhite()), border.getStrokeDashArray());
	}

	@Test
	void testDashSepWhite() {
		assumeTrue(model.isLineStylable());
		model.setLineStyle(LineStyle.DASHED);
		model.setDashSepWhite(2.451);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(Arrays.asList(model.getDashSepBlack(), 2.451), border.getStrokeDashArray());
	}

	@Test
	void testDashSepWhiteBefore() {
		assumeTrue(model.isLineStylable());
		model.setDashSepWhite(2.451);
		model.setLineStyle(LineStyle.DASHED);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(Arrays.asList(model.getDashSepBlack(), 2.451), border.getStrokeDashArray());
	}

	@Test
	void testLineStyleDottedLineCap() {
		assumeTrue(model.isLineStylable());
		model.setLineStyle(LineStyle.DOTTED);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(StrokeLineCap.ROUND, border.getStrokeLineCap());
	}

	@Test
	void testLineStyleDottedSepSimpleLine() {
		assumeTrue(model.isLineStylable());
		model.setLineStyle(LineStyle.DOTTED);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(Arrays.asList(0d, model.getDotSep() + model.getFullThickness()), border.getStrokeDashArray());
	}

	@Test
	void testLineStyleDottedSepDoubleLine() {
		assumeTrue(model.isLineStylable());
		if(model.isDbleBorderable()) {
			model.setHasDbleBord(true);
		}
		model.setLineStyle(LineStyle.DOTTED);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(Arrays.asList(0d, model.getDotSep() + model.getFullThickness()), border.getStrokeDashArray());
	}

	@Test
	void testLineStyleDottedSepDoubleLineAfterLineStyle() {
		assumeTrue(model.isLineStylable());
		model.setLineStyle(LineStyle.DOTTED);
		if(model.isDbleBorderable()) {
			model.setHasDbleBord(true);
		}
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(Arrays.asList(0d, model.getDotSep() + model.getFullThickness()), border.getStrokeDashArray());
	}

	@Test
	void testLineStyleDottedSepDoubleLineDoubleSep() {
		assumeTrue(model.isLineStylable());
		model.setLineStyle(LineStyle.DOTTED);
		if(model.isDbleBorderable()) {
			model.setHasDbleBord(true);
			model.setDbleBordSep(33d);
		}
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(Arrays.asList(0d, model.getDotSep() + model.getFullThickness()), border.getStrokeDashArray());
	}


	@Test
	void testLineStyleDottedSepBefore() {
		assumeTrue(model.isLineStylable());
		model.setDotSep(23.23);
		model.setLineStyle(LineStyle.DOTTED);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(Arrays.asList(0d, 23.23 + model.getFullThickness()), border.getStrokeDashArray());
	}

	@Test
	void testLineStyleDottedSepAfter() {
		assumeTrue(model.isLineStylable());
		model.setLineStyle(LineStyle.DOTTED);
		model.setDotSep(23.23);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(Arrays.asList(0d, 23.23 + model.getFullThickness()), border.getStrokeDashArray());
	}

	@Test
	void testFillPlain() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.PLAIN);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(Color.WHITE, border.getFill());
	}

	@Test
	void testFillPlainColor() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.PLAIN);
		model.setFillingCol(DviPsColors.OLIVEGREEN);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(DviPsColors.OLIVEGREEN.toJFX(), border.getFill());
	}

	@Test
	void testFillNone() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.PLAIN);
		model.setFillingStyle(FillingStyle.NONE);
		WaitForAsyncUtils.waitForFxEvents();
		assertNull(border.getFill());
	}

	@Test
	void testFillGradient() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.GRAD);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(border.getFill() instanceof LinearGradient);
	}

	@Test
	void testFillGradientColor1() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.GRAD);
		model.setGradColStart(DviPsColors.DANDELION);
		WaitForAsyncUtils.waitForFxEvents();
		final LinearGradient grad = (LinearGradient) border.getFill();
		assertEquals(DviPsColors.DANDELION.toJFX(), grad.getStops().get(0).getColor());
	}

	@Test
	void testFillGradientColor2() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.GRAD);
		model.setGradColEnd(DviPsColors.BRICKRED);
		WaitForAsyncUtils.waitForFxEvents();
		final LinearGradient grad = (LinearGradient) border.getFill();
		assertEquals(DviPsColors.BRICKRED.toJFX(), grad.getStops().get(1).getColor());
	}

	@Test
	void testFillGradientAngle() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.GRAD);
		WaitForAsyncUtils.waitForFxEvents();
		final LinearGradient grad1 = (LinearGradient) border.getFill();
		model.setGradAngle(Math.PI / 1.23);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(grad1, border.getFill());
	}

	@Test
	void testFillGradientMidPt() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.GRAD);
		WaitForAsyncUtils.waitForFxEvents();
		final LinearGradient grad1 = (LinearGradient) border.getFill();
		model.setGradMidPt(0.6352);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(grad1, border.getFill());
	}

	@Test
	void testFillHatchingsCLINES() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.CLINES);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(border.getFill() instanceof ImagePattern);
	}

	@Test
	void testFillHatchingsVLINES() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.VLINES);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(border.getFill() instanceof ImagePattern);
	}

	@Test
	void testFillHatchingsCLINESPLAIN() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.CLINES_PLAIN);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(border.getFill() instanceof ImagePattern);
	}

	@Test
	void testFillHatchingsVLINESPLAIN() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.VLINES_PLAIN);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(border.getFill() instanceof ImagePattern);
	}

	@Test
	void testFillHatchingsHLINES() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.HLINES);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(border.getFill() instanceof ImagePattern);
	}

	@Test
	void testFillHatchingsHLINESPLAIN() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.HLINES_PLAIN);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(border.getFill() instanceof ImagePattern);
	}

	@Test
	void testFillHatchingsWidth() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.HLINES);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEqualsSnapshot(view, () -> model.setHatchingsWidth(model.getHatchingsWidth() + 10d));
	}

	@Test
	void testFillHatchingsSep() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.HLINES);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEqualsSnapshot(view, () -> model.setHatchingsSep(model.getHatchingsSep() + 10d));
	}

	@Test
	void testFillHatchingsAngle() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.HLINES);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEqualsSnapshot(view, () -> model.setHatchingsAngle(model.getHatchingsAngle() + Math.PI / 3d));
	}

	@Test
	void testFillHatchingsCol() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.HLINES);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEqualsSnapshot(view, () -> model.setHatchingsCol(DviPsColors.FUSHIA));
	}

	@Test
	void testFillHatchingsFillingCol() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.HLINES);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEqualsSnapshot(view, () -> {
			model.setFillingStyle(FillingStyle.HLINES_PLAIN);
			model.setFillingCol(DviPsColors.FUSHIA);
		});
	}

	@Test
	void testShadowAdded() {
		assumeTrue(model.isShadowable());
		assertTrue(view.getChildren().stream().anyMatch(c -> c == view.getShadow().orElseThrow()));
	}

	@Test
	void testShadowDefaultDisable() {
		assumeTrue(model.isShadowable());
		assertTrue(view.getShadow().orElseThrow().isDisable());
	}

	@Test
	void testShadowEnable() {
		assumeTrue(model.isShadowable());
		model.setHasShadow(true);
		WaitForAsyncUtils.waitForFxEvents();
		assertFalse(view.getShadow().orElseThrow().isDisable());
	}

	@Test
	void testShadowDisable() {
		assumeTrue(model.isShadowable());
		model.setHasShadow(true);
		model.setHasShadow(false);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(view.getShadow().orElseThrow().isDisable());
	}

	@Test
	void testShadowColorStroke() {
		assumeTrue(model.isShadowable());
		model.setHasShadow(true);
		model.setShadowCol(DviPsColors.NAVYBLUE);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(DviPsColors.NAVYBLUE.toJFX(), view.getShadow().orElseThrow().getStroke());
	}

	@Test
	void testShadowColorFillWhenFilled() {
		assumeTrue(model.isShadowable());
		assumeTrue(model.isFillable());
		model.setHasShadow(true);
		model.setFillingStyle(FillingStyle.PLAIN);
		model.setShadowCol(DviPsColors.APRICOT);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(DviPsColors.APRICOT.toJFX(), view.getShadow().orElseThrow().getFill());
	}

	@Test
	void testShadowColorFillWhenNotFilled() {
		assumeTrue(model.isShadowable());
		assumeTrue(model.isFillable());
		assumeFalse(model.shadowFillsShape());
		model.setHasShadow(true);
		model.setFillingStyle(FillingStyle.NONE);
		WaitForAsyncUtils.waitForFxEvents();
		assertNull(view.getShadow().orElseThrow().getFill());
	}

	@Test
	void testShadowColorFillWhenNotFilledButShadowFills() {
		assumeTrue(model.isShadowable());
		assumeTrue(model.isFillable());
		assumeTrue(model.shadowFillsShape());
		model.setHasShadow(true);
		model.setFillingStyle(FillingStyle.NONE);
		model.setShadowCol(DviPsColors.APRICOT);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(DviPsColors.APRICOT.toJFX(), view.getShadow().orElseThrow().getFill());
	}


	@Test
	void testShadowBoundNotSamePositionThanBorder() {
		assumeTrue(model.isShadowable());
		model.setHasShadow(true);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(border.getBoundsInParent().getMinX(), view.getShadow().orElseThrow().getBoundsInParent().getMinX());
		assertNotEquals(border.getBoundsInParent().getMinY(), view.getShadow().orElseThrow().getBoundsInParent().getMinY());
	}

	@Test
	void testLineSizeShadowSameThanBorder() {
		assumeTrue(model.isThicknessable());
		model.setThickness(20d);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(20d, view.getShadow().orElseThrow().getStrokeWidth(), 0.001);
	}

	@Test
	void testLinePositionShadowSameThanBorder() {
		assumeTrue(model.isThicknessable());
		model.setBordersPosition(BorderPos.INTO);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(border.getStrokeType(), view.getShadow().orElseThrow().getStrokeType());
	}

	@Test
	void testShadowBoundSizeSameThanBorder() {
		assumeTrue(model.isShadowable());
		model.setHasShadow(true);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(border.getBoundsInLocal().getWidth(), view.getShadow().orElseThrow().getBoundsInLocal().getWidth(), 0.001);
		assertEquals(border.getBoundsInLocal().getHeight(), view.getShadow().orElseThrow().getBoundsInLocal().getHeight(), 0.001);
	}


	@Test
	void testShadowAngle0Translate() {
		assumeTrue(model.isShadowable());
		model.setHasShadow(true);
		model.setShadowAngle(0d);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(model.getShadowSize(), view.getShadow().orElseThrow().getTranslateX(), 0.01);
		assertEquals(0d, view.getShadow().orElseThrow().getTranslateY(), 0.01);
	}

	@Test
	void testShadowSizeAngle0Translate() {
		assumeTrue(model.isShadowable());
		model.setHasShadow(true);
		model.setShadowAngle(0d);
		model.setShadowSize(100.21d);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(100.21, view.getShadow().orElseThrow().getTranslateX(), 0.01);
		assertEquals(0d, view.getShadow().orElseThrow().getTranslateY(), 0.01);
	}


	@Test
	void testShadowAngle90Translate() {
		assumeTrue(model.isShadowable());
		model.setHasShadow(true);
		model.setShadowAngle(Math.PI / 2d);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(0d, view.getShadow().orElseThrow().getTranslateX(), 0.01);
		assertEquals(-model.getShadowSize(), view.getShadow().orElseThrow().getTranslateY(), 0.01);
	}

	@Test
	abstract void testShadowPositionSameThanBorder();

	@Test
	void testShadowBeforeBorder() {
		assumeTrue(model.isShadowable());
		assertTrue(view.getChildren().indexOf(border) > view.getChildren().indexOf(view.getShadow().orElseThrow()));
	}

	@Test
	void testDbleBorderAfterBorder() {
		assumeTrue(model.isDbleBorderable());
		assertTrue(view.getChildren().indexOf(border) < view.getChildren().indexOf(view.getDbleBorder().orElseThrow()));
	}
}
