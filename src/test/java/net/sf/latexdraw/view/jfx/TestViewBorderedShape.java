package net.sf.latexdraw.view.jfx;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
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
import org.junit.Before;
import org.junit.Test;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;

abstract class TestViewBorderedShape<T extends ViewSingleShape<S, R>, S extends ISingleShape, R extends Shape> extends TestViewShape<T, S> implements
	ITestViewBorderedShape<T, S, R> {
	protected R border;

	@Override
	@Before
	public void setUp() throws InterruptedException, ExecutionException, TimeoutException {
		super.setUp();
		border = view.getBorder();
	}

	@Override
	public R getBorder() {
		return border;
	}

	@Test
	public void testLineColor() {
		model.setLineColour(DviPsColors.BITTERSWEET);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(DviPsColors.BITTERSWEET, ShapeFactory.INST.createColorFX((javafx.scene.paint.Color) border.getStroke()));
	}

	@Test
	public void testLineThickness() {
		assumeTrue(model.isThicknessable());
		model.setThickness(10d);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(10d, border.getStrokeWidth(), 0.001);
	}

	@Test
	public void testBorderAdded() {
		assertTrue(view.getChildren().stream().anyMatch(c -> c == border));
	}

	@Test
	public void testBorderEnable() {
		assertFalse(view.getBorder().isDisable());
	}

	@Test
	public void testDoubleLineThickness() {
		assumeTrue(model.isDbleBorderable());
		model.setHasDbleBord(true);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(model.getFullThickness(), border.getStrokeWidth(), 0.001);
	}

	@Test
	public void testDoubleLineSepThickness() {
		assumeTrue(model.isDbleBorderable());
		model.setHasDbleBord(true);
		model.setDbleBordSep(30d);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(model.getFullThickness(), border.getStrokeWidth(), 0.001);
	}

	@Test
	public void testDoubleLineCol() {
		assumeTrue(model.isDbleBorderable());
		model.setHasDbleBord(true);
		model.setDbleBordCol(DviPsColors.APRICOT);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(DviPsColors.APRICOT, view.getDbleBorder().map(b -> ShapeFactory.INST.createColorFX((Color) b.getStroke())).orElse(null));
	}

	@Test
	public void testDoubleBorderAdded() {
		assumeTrue(model.isDbleBorderable());
		assertTrue(view.getChildren().stream().anyMatch(c -> c == view.getDbleBorder().get()));
	}

	@Test
	public void testDoubleBorderDefaultDisable() {
		assumeTrue(model.isDbleBorderable());
		assertTrue(view.getDbleBorder().get().isDisable());
	}

	@Test
	public void testDoubleBorderEnable() {
		assumeTrue(model.isDbleBorderable());
		model.setHasDbleBord(true);
		WaitForAsyncUtils.waitForFxEvents();
		assertFalse(view.getDbleBorder().get().isDisable());
	}

	@Test
	public void testDoubleBorderDisable() {
		assumeTrue(model.isDbleBorderable());
		model.setHasDbleBord(true);
		model.setHasDbleBord(false);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(view.getDbleBorder().get().isDisable());
	}

	@Test
	public void testDoubleBorderDefaultVisible() {
		assumeTrue(model.isDbleBorderable());
		assertFalse(view.getDbleBorder().get().isVisible());
	}

	@Test
	public void testDoubleBorderVisible() {
		assumeTrue(model.isDbleBorderable());
		model.setHasDbleBord(true);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(view.getDbleBorder().get().isVisible());
	}

	@Test
	public void testDoubleBorderNotVisible() {
		assumeTrue(model.isDbleBorderable());
		model.setHasDbleBord(true);
		model.setHasDbleBord(false);
		WaitForAsyncUtils.waitForFxEvents();
		assertFalse(view.getDbleBorder().get().isVisible());
	}

	@Test
	public void testDoubleBorderStrokeWidth() {
		assumeTrue(model.isDbleBorderable());
		model.setHasDbleBord(true);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(model.getDbleBordSep(), view.getDbleBorder().get().getStrokeWidth(), 0.001);
	}

	@Test
	public void testDoubleBorderNoFill() {
		assumeTrue(model.isDbleBorderable());
		model.setHasDbleBord(true);
		WaitForAsyncUtils.waitForFxEvents();
		assertNull(view.getDbleBorder().get().getFill());
	}

	@Test
	public void testLineStylePlainLineCap() {
		assumeTrue(model.isLineStylable());
		model.setLineStyle(LineStyle.SOLID);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(StrokeLineCap.BUTT, border.getStrokeLineCap());
	}

	@Test
	public void testLineStyleDashedSep() {
		assumeTrue(model.isLineStylable());
		model.setLineStyle(LineStyle.DASHED);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(Arrays.asList(model.getDashSepBlack(), model.getDashSepWhite()), border.getStrokeDashArray());
	}

	@Test
	public void testLineStyleDashedLineCap() {
		assumeTrue(model.isLineStylable());
		model.setLineStyle(LineStyle.DASHED);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(StrokeLineCap.BUTT, border.getStrokeLineCap());
	}

	@Test
	public void testDashSepBlackBefore() {
		assumeTrue(model.isLineStylable());
		model.setDashSepBlack(21d);
		model.setLineStyle(LineStyle.DASHED);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(Arrays.asList(21d, model.getDashSepWhite()), border.getStrokeDashArray());
	}

	@Test
	public void testDashSepBlack() {
		assumeTrue(model.isLineStylable());
		model.setLineStyle(LineStyle.DASHED);
		model.setDashSepBlack(21d);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(Arrays.asList(21d, model.getDashSepWhite()), border.getStrokeDashArray());
	}

	@Test
	public void testDashSepWhite() {
		assumeTrue(model.isLineStylable());
		model.setLineStyle(LineStyle.DASHED);
		model.setDashSepWhite(2.451);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(Arrays.asList(model.getDashSepBlack(), 2.451), border.getStrokeDashArray());
	}

	@Test
	public void testDashSepWhiteBefore() {
		assumeTrue(model.isLineStylable());
		model.setDashSepWhite(2.451);
		model.setLineStyle(LineStyle.DASHED);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(Arrays.asList(model.getDashSepBlack(), 2.451), border.getStrokeDashArray());
	}

	@Test
	public void testLineStyleDottedLineCap() {
		assumeTrue(model.isLineStylable());
		model.setLineStyle(LineStyle.DOTTED);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(StrokeLineCap.ROUND, border.getStrokeLineCap());
	}

	@Test
	public void testLineStyleDottedSepSimpleLine() {
		assumeTrue(model.isLineStylable());
		model.setLineStyle(LineStyle.DOTTED);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(Arrays.asList(0d, model.getDotSep() + model.getFullThickness()), border.getStrokeDashArray());
	}

	@Test
	public void testLineStyleDottedSepDoubleLine() {
		assumeTrue(model.isLineStylable());
		if(model.isDbleBorderable()) {
			model.setHasDbleBord(true);
		}
		model.setLineStyle(LineStyle.DOTTED);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(Arrays.asList(0d, model.getDotSep() + model.getFullThickness()), border.getStrokeDashArray());
	}

	@Test
	public void testLineStyleDottedSepDoubleLineAfterLineStyle() {
		assumeTrue(model.isLineStylable());
		model.setLineStyle(LineStyle.DOTTED);
		if(model.isDbleBorderable()) {
			model.setHasDbleBord(true);
		}
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(Arrays.asList(0d, model.getDotSep() + model.getFullThickness()), border.getStrokeDashArray());
	}

	@Test
	public void testLineStyleDottedSepDoubleLineDoubleSep() {
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
	public void testLineStyleDottedSepBefore() {
		assumeTrue(model.isLineStylable());
		model.setDotSep(23.23);
		model.setLineStyle(LineStyle.DOTTED);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(Arrays.asList(0d, 23.23 + model.getFullThickness()), border.getStrokeDashArray());
	}

	@Test
	public void testLineStyleDottedSepAfter() {
		assumeTrue(model.isLineStylable());
		model.setLineStyle(LineStyle.DOTTED);
		model.setDotSep(23.23);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(Arrays.asList(0d, 23.23 + model.getFullThickness()), border.getStrokeDashArray());
	}

	@Test
	public void testFillPlain() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.PLAIN);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(Color.WHITE, border.getFill());
	}

	@Test
	public void testFillPlainColor() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.PLAIN);
		model.setFillingCol(DviPsColors.OLIVEGREEN);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(DviPsColors.OLIVEGREEN.toJFX(), border.getFill());
	}

	@Test
	public void testFillNone() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.PLAIN);
		model.setFillingStyle(FillingStyle.NONE);
		WaitForAsyncUtils.waitForFxEvents();
		assertNull(border.getFill());
	}

	@Test
	public void testFillGradient() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.GRAD);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(border.getFill() instanceof LinearGradient);
	}

	@Test
	public void testFillGradientColor1() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.GRAD);
		model.setGradColStart(DviPsColors.DANDELION);
		WaitForAsyncUtils.waitForFxEvents();
		LinearGradient grad = (LinearGradient) border.getFill();
		assertEquals(DviPsColors.DANDELION.toJFX(), grad.getStops().get(0).getColor());
	}

	@Test
	public void testFillGradientColor2() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.GRAD);
		model.setGradColEnd(DviPsColors.BRICKRED);
		WaitForAsyncUtils.waitForFxEvents();
		LinearGradient grad = (LinearGradient) border.getFill();
		assertEquals(DviPsColors.BRICKRED.toJFX(), grad.getStops().get(1).getColor());
	}

	@Test
	public void testFillGradientAngle() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.GRAD);
		WaitForAsyncUtils.waitForFxEvents();
		LinearGradient grad1 = (LinearGradient) border.getFill();
		model.setGradAngle(Math.PI / 1.23);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(grad1, border.getFill());
	}

	@Test
	public void testFillGradientMidPt() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.GRAD);
		WaitForAsyncUtils.waitForFxEvents();
		LinearGradient grad1 = (LinearGradient) border.getFill();
		model.setGradMidPt(0.6352);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(grad1, border.getFill());
	}

	@Test
	public void testFillHatchingsCLINES() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.CLINES);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(border.getFill() instanceof ImagePattern);
	}

	@Test
	public void testFillHatchingsVLINES() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.VLINES);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(border.getFill() instanceof ImagePattern);
	}

	@Test
	public void testFillHatchingsCLINESPLAIN() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.CLINES_PLAIN);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(border.getFill() instanceof ImagePattern);
	}

	@Test
	public void testFillHatchingsVLINESPLAIN() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.VLINES_PLAIN);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(border.getFill() instanceof ImagePattern);
	}

	@Test
	public void testFillHatchingsHLINES() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.HLINES);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(border.getFill() instanceof ImagePattern);
	}

	@Test
	public void testFillHatchingsHLINESPLAIN() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.HLINES_PLAIN);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(border.getFill() instanceof ImagePattern);
	}

	@Test
	public void testFillHatchingsWidth() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.HLINES);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEqualsSnapshot(view, () -> model.setHatchingsWidth(model.getHatchingsWidth() + 10d));
	}

	@Test
	public void testFillHatchingsSep() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.HLINES);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEqualsSnapshot(view, () -> model.setHatchingsSep(model.getHatchingsSep() + 10d));
	}

	@Test
	public void testFillHatchingsAngle() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.HLINES);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEqualsSnapshot(view, () -> model.setHatchingsAngle(model.getHatchingsAngle() + Math.PI / 3d));
	}

	@Test
	public void testFillHatchingsCol() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.HLINES);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEqualsSnapshot(view, () -> model.setHatchingsCol(DviPsColors.FUSHIA));
	}

	@Test
	public void testFillHatchingsFillingCol() {
		assumeTrue(model.isFillable());
		model.setFillingStyle(FillingStyle.HLINES);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEqualsSnapshot(view, () -> {
			model.setFillingStyle(FillingStyle.HLINES_PLAIN);
			model.setFillingCol(DviPsColors.FUSHIA);
		});
	}

	@Test
	public void testShadowAdded() {
		assumeTrue(model.isShadowable());
		assertTrue(view.getChildren().stream().anyMatch(c -> c == view.getShadow().get()));
	}

	@Test
	public void testShadowDefaultDisable() {
		assumeTrue(model.isShadowable());
		assertTrue(view.getShadow().get().isDisable());
	}

	@Test
	public void testShadowEnable() {
		assumeTrue(model.isShadowable());
		model.setHasShadow(true);
		WaitForAsyncUtils.waitForFxEvents();
		assertFalse(view.getShadow().get().isDisable());
	}

	@Test
	public void testShadowDisable() {
		assumeTrue(model.isShadowable());
		model.setHasShadow(true);
		model.setHasShadow(false);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(view.getShadow().get().isDisable());
	}

	@Test
	public void testShadowColorStroke() {
		assumeTrue(model.isShadowable());
		model.setHasShadow(true);
		model.setShadowCol(DviPsColors.NAVYBLUE);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(DviPsColors.NAVYBLUE.toJFX(), view.getShadow().get().getStroke());
	}

	@Test
	public void testShadowColorFillWhenFilled() {
		assumeTrue(model.isShadowable());
		assumeTrue(model.isFillable());
		model.setHasShadow(true);
		model.setFillingStyle(FillingStyle.PLAIN);
		model.setShadowCol(DviPsColors.APRICOT);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(DviPsColors.APRICOT.toJFX(), view.getShadow().get().getFill());
	}

	@Test
	public void testShadowColorFillWhenNotFilled() {
		assumeTrue(model.isShadowable());
		assumeTrue(model.isFillable());
		assumeFalse(model.shadowFillsShape());
		model.setHasShadow(true);
		model.setFillingStyle(FillingStyle.NONE);
		WaitForAsyncUtils.waitForFxEvents();
		assertNull(view.getShadow().get().getFill());
	}

	@Test
	public void testShadowColorFillWhenNotFilledButShadowFills() {
		assumeTrue(model.isShadowable());
		assumeTrue(model.isFillable());
		assumeTrue(model.shadowFillsShape());
		model.setHasShadow(true);
		model.setFillingStyle(FillingStyle.NONE);
		model.setShadowCol(DviPsColors.APRICOT);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(DviPsColors.APRICOT.toJFX(), view.getShadow().get().getFill());
	}


	@Test
	public void testShadowBoundNotSamePositionThanBorder() {
		assumeTrue(model.isShadowable());
		model.setHasShadow(true);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(border.getBoundsInParent().getMinX(), view.getShadow().get().getBoundsInParent().getMinX(), 0.001);
		assertNotEquals(border.getBoundsInParent().getMinY(), view.getShadow().get().getBoundsInParent().getMinY(), 0.001);
	}

	@Test
	public void testLineSizeShadowSameThanBorder() {
		assumeTrue(model.isThicknessable());
		model.setThickness(20d);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(20d, view.getShadow().get().getStrokeWidth(), 0.001);
	}

	@Test
	public void testLinePositionShadowSameThanBorder() {
		assumeTrue(model.isThicknessable());
		model.setBordersPosition(BorderPos.INTO);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(border.getStrokeType(), view.getShadow().get().getStrokeType());
	}

	@Test
	public void testShadowBoundSizeSameThanBorder() {
		assumeTrue(model.isShadowable());
		model.setHasShadow(true);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(border.getBoundsInLocal().getWidth(), view.getShadow().get().getBoundsInLocal().getWidth(), 0.001);
		assertEquals(border.getBoundsInLocal().getHeight(), view.getShadow().get().getBoundsInLocal().getHeight(), 0.001);
	}


	@Test
	public void testShadowAngle0Translate() {
		assumeTrue(model.isShadowable());
		model.setHasShadow(true);
		model.setShadowAngle(0d);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(model.getShadowSize(), view.getShadow().get().getTranslateX(), 0.01);
		assertEquals(0d, view.getShadow().get().getTranslateY(), 0.01);
	}

	@Test
	public void testShadowSizeAngle0Translate() {
		assumeTrue(model.isShadowable());
		model.setHasShadow(true);
		model.setShadowAngle(0d);
		model.setShadowSize(100.21d);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(100.21, view.getShadow().get().getTranslateX(), 0.01);
		assertEquals(0d, view.getShadow().get().getTranslateY(), 0.01);
	}


	@Test
	public void testShadowAngle90Translate() {
		assumeTrue(model.isShadowable());
		model.setHasShadow(true);
		model.setShadowAngle(Math.PI / 2d);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(0d, view.getShadow().get().getTranslateX(), 0.01);
		assertEquals(-model.getShadowSize(), view.getShadow().get().getTranslateY(), 0.01);
	}

	@Test
	public abstract void testShadowPositionSameThanBorder();

	@Test
	public void testShadowBeforeBorder() {
		assumeTrue(model.isShadowable());
		assertTrue(view.getChildren().indexOf(border) > view.getChildren().indexOf(view.getShadow().get()));
	}

	@Test
	public void testDbleBorderAfterBorder() {
		assumeTrue(model.isDbleBorderable());
		assertTrue(view.getChildren().indexOf(border) < view.getChildren().indexOf(view.getDbleBorder().get()));
	}
}
