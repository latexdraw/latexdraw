package test.views.jfx;

import java.util.Arrays;
import java.util.concurrent.TimeoutException;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.LinearGradient;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import net.sf.latexdraw.badaboom.BadaboomCollector;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.BorderPos;
import net.sf.latexdraw.models.interfaces.shape.FillingStyle;
import net.sf.latexdraw.models.interfaces.shape.ISingleShape;
import net.sf.latexdraw.models.interfaces.shape.LineStyle;
import net.sf.latexdraw.view.jfx.ViewFactory;
import net.sf.latexdraw.view.jfx.ViewSingleShape;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.HelperTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

abstract class TestViewSingleShape<T extends ViewSingleShape<S, R>, S extends ISingleShape, R extends Shape> implements HelperTest, ITestViewShape<T, S, R> {
	protected T view;
	protected S model;
	protected R border;

	protected abstract S createModel();

	private T createView() {
		return (T) ViewFactory.INSTANCE.createView(createModel()).get();
	}

	@Before
	public void setUp() {
		BadaboomCollector.INSTANCE.clear();
		view = createView();
		model = view.getModel();
		border = view.getBorder();
	}

	@After
	public void tearDown() throws Exception {
		view.flush();
	}

	@Override
	public T getView() {
		return view;
	}

	@Override
	public S getModel() {
		return model;
	}

	@Override
	public R getBorder() {
		return border;
	}

	@Test
	public void testLineColor() {
		model.setLineColour(DviPsColors.BITTERSWEET);
		assertEquals(DviPsColors.BITTERSWEET, ShapeFactory.INST.createColorFX((javafx.scene.paint.Color) border.getStroke()));
	}

	@Test
	public void testLineThickness() {
		if(model.isThicknessable()) {
			model.setThickness(10d);
			assertEquals(10d, border.getStrokeWidth(), 0.001);
		}
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
		if(model.isDbleBorderable()) {
			model.setHasDbleBord(true);
			assertEquals(model.getFullThickness(), border.getStrokeWidth(), 0.001);
		}
	}

	@Test
	public void testDoubleLineSepThickness() {
		if(model.isDbleBorderable()) {
			model.setHasDbleBord(true);
			model.setDbleBordSep(30d);
			assertEquals(model.getFullThickness(), border.getStrokeWidth(), 0.001);
		}
	}

	@Test
	public void testDoubleLineCol() {
		if(model.isDbleBorderable()) {
			model.setHasDbleBord(true);
			model.setDbleBordCol(DviPsColors.APRICOT);
			assertEquals(DviPsColors.APRICOT, view.getDbleBorder().map(b -> ShapeFactory.INST.createColorFX((Color) b.getStroke())).orElse(null));
		}
	}

	@Test
	public void testDoubleBorderAdded() {
		if(model.isDbleBorderable()) {
			assertTrue(view.getChildren().stream().anyMatch(c -> c == view.getDbleBorder().get()));
		}
	}

	@Test
	public void testDoubleBorderDefaultDisable() {
		if(model.isDbleBorderable()) {
			assertTrue(view.getDbleBorder().get().isDisable());
		}
	}

	@Test
	public void testDoubleBorderEnable() {
		if(model.isDbleBorderable()) {
			model.setHasDbleBord(true);
			assertFalse(view.getDbleBorder().get().isDisable());
		}
	}

	@Test
	public void testDoubleBorderDisable() {
		if(model.isDbleBorderable()) {
			model.setHasDbleBord(true);
			model.setHasDbleBord(false);
			assertTrue(view.getDbleBorder().get().isDisable());
		}
	}

	@Test
	public void testDoubleBorderDefaultVisible() {
		if(model.isDbleBorderable()) {
			assertFalse(view.getDbleBorder().get().isVisible());
		}
	}

	@Test
	public void testDoubleBorderVisible() {
		if(model.isDbleBorderable()) {
			model.setHasDbleBord(true);
			assertTrue(view.getDbleBorder().get().isVisible());
		}
	}

	@Test
	public void testDoubleBorderNotVisible() {
		if(model.isDbleBorderable()) {
			model.setHasDbleBord(true);
			model.setHasDbleBord(false);
			assertFalse(view.getDbleBorder().get().isVisible());
		}
	}

	@Test
	public void testDoubleBorderStrokeWidth() {
		if(model.isDbleBorderable()) {
			model.setHasDbleBord(true);
			assertEquals(model.getDbleBordSep(), view.getDbleBorder().get().getStrokeWidth(), 0.001);
		}
	}

	@Test
	public void testDoubleBorderNoFill() {
		if(model.isDbleBorderable()) {
			model.setHasDbleBord(true);
			assertNull(view.getDbleBorder().get().getFill());
		}
	}

	@Test
	public void testLineStylePlainLineCap() {
		if(model.isLineStylable()) {
			model.setLineStyle(LineStyle.SOLID);
			assertEquals(StrokeLineCap.SQUARE, border.getStrokeLineCap());
		}
	}

	@Test
	public void testLineStyleDashedSep() {
		if(model.isLineStylable()) {
			model.setLineStyle(LineStyle.DASHED);
			assertEquals(Arrays.asList(model.getDashSepBlack(), model.getDashSepWhite()), border.getStrokeDashArray());
		}
	}

	@Test
	public void testLineStyleDashedLineCap() {
		if(model.isLineStylable()) {
			model.setLineStyle(LineStyle.DASHED);
			assertEquals(StrokeLineCap.BUTT, border.getStrokeLineCap());
		}
	}

	@Test
	public void testDashSepBlackBefore() {
		if(model.isLineStylable()) {
			model.setDashSepBlack(21d);
			model.setLineStyle(LineStyle.DASHED);
			assertEquals(Arrays.asList(21d, model.getDashSepWhite()), border.getStrokeDashArray());
		}
	}

	@Test
	public void testDashSepBlack() {
		if(model.isLineStylable()) {
			model.setLineStyle(LineStyle.DASHED);
			model.setDashSepBlack(21d);
			assertEquals(Arrays.asList(21d, model.getDashSepWhite()), border.getStrokeDashArray());
		}
	}

	@Test
	public void testDashSepWhite() {
		if(model.isLineStylable()) {
			model.setLineStyle(LineStyle.DASHED);
			model.setDashSepWhite(2.451);
			assertEquals(Arrays.asList(model.getDashSepBlack(), 2.451), border.getStrokeDashArray());
		}
	}

	@Test
	public void testDashSepWhiteBefore() {
		if(model.isLineStylable()) {
			model.setDashSepWhite(2.451);
			model.setLineStyle(LineStyle.DASHED);
			assertEquals(Arrays.asList(model.getDashSepBlack(), 2.451), border.getStrokeDashArray());
		}
	}

	@Test
	public void testLineStyleDottedLineCap() {
		if(model.isLineStylable()) {
			model.setLineStyle(LineStyle.DOTTED);
			assertEquals(StrokeLineCap.ROUND, border.getStrokeLineCap());
		}
	}

	@Test
	public void testLineStyleDottedSepSimpleLine() {
		if(model.isLineStylable()) {
			model.setLineStyle(LineStyle.DOTTED);
			assertEquals(Arrays.asList(0d, model.getDotSep() + model.getFullThickness()), border.getStrokeDashArray());
		}
	}

	@Test
	public void testLineStyleDottedSepDoubleLine() {
		if(model.isLineStylable()) {
			if(model.isDbleBorderable()) {
				model.setHasDbleBord(true);
			}
			model.setLineStyle(LineStyle.DOTTED);
			assertEquals(Arrays.asList(0d, model.getDotSep() + model.getFullThickness()), border.getStrokeDashArray());
		}
	}

	@Test
	public void testLineStyleDottedSepDoubleLineAfterLineStyle() {
		if(model.isLineStylable()) {
			model.setLineStyle(LineStyle.DOTTED);
			if(model.isDbleBorderable()) {
				model.setHasDbleBord(true);
			}
			assertEquals(Arrays.asList(0d, model.getDotSep() + model.getFullThickness()), border.getStrokeDashArray());
		}
	}

	@Test
	public void testLineStyleDottedSepDoubleLineDoubleSep() {
		if(model.isLineStylable()) {
			model.setLineStyle(LineStyle.DOTTED);
			if(model.isDbleBorderable()) {
				model.setHasDbleBord(true);
				model.setDbleBordSep(33d);
			}
			assertEquals(Arrays.asList(0d, model.getDotSep() + model.getFullThickness()), border.getStrokeDashArray());
		}
	}


	@Test
	public void testLineStyleDottedSepBefore() {
		if(model.isLineStylable()) {
			model.setDotSep(23.23);
			model.setLineStyle(LineStyle.DOTTED);
			assertEquals(Arrays.asList(0d, 23.23 + model.getFullThickness()), border.getStrokeDashArray());
		}
	}

	@Test
	public void testLineStyleDottedSepAfter() {
		if(model.isLineStylable()) {
			model.setLineStyle(LineStyle.DOTTED);
			model.setDotSep(23.23);
			assertEquals(Arrays.asList(0d, 23.23 + model.getFullThickness()), border.getStrokeDashArray());
		}
	}

	@Test
	public void testFillPlain() {
		if(model.isFillable()) {
			model.setFillingStyle(FillingStyle.PLAIN);
			assertEquals(Color.WHITE, border.getFill());
		}
	}

	@Test
	public void testFillPlainColor() {
		if(model.isFillable()) {
			model.setFillingStyle(FillingStyle.PLAIN);
			model.setFillingCol(DviPsColors.OLIVEGREEN);
			assertEquals(DviPsColors.OLIVEGREEN.toJFX(), border.getFill());
		}
	}

	@Test
	public void testFillNone() {
		if(model.isFillable()) {
			model.setFillingStyle(FillingStyle.PLAIN);
			model.setFillingStyle(FillingStyle.NONE);
			assertNull(border.getFill());
		}
	}

	@Test
	public void testFillGradient() {
		if(model.isFillable()) {
			model.setFillingStyle(FillingStyle.GRAD);
			assertTrue(border.getFill() instanceof LinearGradient);
		}
	}

	@Test
	public void testFillGradientColor1() {
		if(model.isFillable()) {
			model.setFillingStyle(FillingStyle.GRAD);
			model.setGradColStart(DviPsColors.DANDELION);
			LinearGradient grad = (LinearGradient) border.getFill();
			assertEquals(DviPsColors.DANDELION.toJFX(), grad.getStops().get(0).getColor());
		}
	}

	@Test
	public void testFillGradientColor2() {
		if(model.isFillable()) {
			model.setFillingStyle(FillingStyle.GRAD);
			model.setGradColEnd(DviPsColors.BRICKRED);
			LinearGradient grad = (LinearGradient) border.getFill();
			assertEquals(DviPsColors.BRICKRED.toJFX(), grad.getStops().get(1).getColor());
		}
	}

	@Test
	public void testFillGradientAngle() {
		if(model.isFillable()) {
			model.setFillingStyle(FillingStyle.GRAD);
			LinearGradient grad1 = (LinearGradient) border.getFill();
			model.setGradAngle(Math.PI / 1.23);
			assertNotEquals(grad1, border.getFill());
		}
	}

	@Test
	public void testFillGradientMidPt() {
		if(model.isFillable()) {
			model.setFillingStyle(FillingStyle.GRAD);
			LinearGradient grad1 = (LinearGradient) border.getFill();
			model.setGradMidPt(0.6352);
			assertNotEquals(grad1, border.getFill());
		}
	}

	@Test
	public void testFillHatchingsCLINES() {
		if(model.isFillable()) {
			model.setFillingStyle(FillingStyle.CLINES);
			assertTrue(border.getFill() instanceof ImagePattern);
		}
	}

	@Test
	public void testFillHatchingsVLINES() {
		if(model.isFillable()) {
			model.setFillingStyle(FillingStyle.VLINES);
			assertTrue(border.getFill() instanceof ImagePattern);
		}
	}

	@Test
	public void testFillHatchingsCLINESPLAIN() {
		if(model.isFillable()) {
			model.setFillingStyle(FillingStyle.CLINES_PLAIN);
			assertTrue(border.getFill() instanceof ImagePattern);
		}
	}

	@Test
	public void testFillHatchingsVLINESPLAIN() throws TimeoutException {
		if(model.isFillable()) {
			model.setFillingStyle(FillingStyle.VLINES_PLAIN);
			assertTrue(border.getFill() instanceof ImagePattern);
		}
	}

	@Test
	public void testFillHatchingsHLINES() throws TimeoutException {
		if(model.isFillable()) {
			model.setFillingStyle(FillingStyle.HLINES);
			assertTrue(border.getFill() instanceof ImagePattern);
		}
	}

	@Test
	public void testFillHatchingsHLINESPLAIN() {
		if(model.isFillable()) {
			model.setFillingStyle(FillingStyle.HLINES_PLAIN);
			assertTrue(border.getFill() instanceof ImagePattern);
		}
	}

	@Test
	public void testFillHatchingsWidth() {
		if(model.isFillable()) {
			model.setFillingStyle(FillingStyle.HLINES);
			assertNotEqualsSnapshot(view, () -> model.setHatchingsWidth(model.getHatchingsWidth() + 10d));
		}
	}

	@Test
	public void testFillHatchingsSep() {
		if(model.isFillable()) {
			model.setFillingStyle(FillingStyle.HLINES);
			assertNotEqualsSnapshot(view, () -> model.setHatchingsSep(model.getHatchingsSep() + 10d));
		}
	}

	@Test
	public void testFillHatchingsAngle() {
		if(model.isFillable()) {
			model.setFillingStyle(FillingStyle.HLINES);
			assertNotEqualsSnapshot(view, () -> model.setHatchingsAngle(model.getHatchingsAngle() + Math.PI / 3d));
		}
	}

	@Test
	public void testFillHatchingsCol() {
		if(model.isFillable()) {
			model.setFillingStyle(FillingStyle.HLINES);
			assertNotEqualsSnapshot(view, () -> model.setHatchingsCol(DviPsColors.FUSHIA));
		}
	}

	@Test
	public void testFillHatchingsFillingCol() {
		if(model.isFillable()) {
			model.setFillingStyle(FillingStyle.HLINES);
			assertNotEqualsSnapshot(view, () -> {
				model.setFillingStyle(FillingStyle.HLINES_PLAIN);
				model.setFillingCol(DviPsColors.FUSHIA);
			});
		}
	}

	@Test
	public void testShadowAdded() {
		if(model.isDbleBorderable()) {
			assertTrue(view.getChildren().stream().anyMatch(c -> c == view.getShadow().get()));
		}
	}

	@Test
	public void testShadowDefaultDisable() {
		if(model.isShadowable()) {
			assertTrue(view.getShadow().get().isDisable());
		}
	}

	@Test
	public void testShadowEnable() {
		if(model.isShadowable()) {
			model.setHasShadow(true);
			assertFalse(view.getShadow().get().isDisable());
		}
	}

	@Test
	public void testShadowDisable() {
		if(model.isShadowable()) {
			model.setHasShadow(true);
			model.setHasShadow(false);
			assertTrue(view.getShadow().get().isDisable());
		}
	}

	@Test
	public void testShadowColorStroke() {
		if(model.isShadowable()) {
			model.setHasShadow(true);
			model.setShadowCol(DviPsColors.NAVYBLUE);
			assertEquals(DviPsColors.NAVYBLUE.toJFX(), view.getShadow().get().getStroke());
		}
	}

	@Test
	public void testShadowColorFillWhenFilled() {
		if(model.isShadowable() && model.isFillable()) {
			model.setHasShadow(true);
			model.setFillingStyle(FillingStyle.PLAIN);
			model.setShadowCol(DviPsColors.APRICOT);
			assertEquals(DviPsColors.APRICOT.toJFX(), view.getShadow().get().getFill());
		}
	}

	@Test
	public void testShadowColorFillWhenNotFilled() {
		if(model.isShadowable() && model.isFillable() && !model.shadowFillsShape()) {
			model.setHasShadow(true);
			model.setFillingStyle(FillingStyle.NONE);
			assertNull(view.getShadow().get().getFill());
		}
	}

	@Test
	public void testShadowColorFillWhenNotFilledButShadowFills() {
		if(model.isShadowable() && model.isFillable() && model.shadowFillsShape()) {
			model.setHasShadow(true);
			model.setFillingStyle(FillingStyle.NONE);
			model.setShadowCol(DviPsColors.APRICOT);
			assertEquals(DviPsColors.APRICOT.toJFX(), view.getShadow().get().getFill());
		}
	}


	@Test
	public void testShadowBoundNotSamePositionThanBorder() {
		if(model.isShadowable()) {
			model.setHasShadow(true);
			assertNotEquals(border.getBoundsInParent().getMinX(), view.getShadow().get().getBoundsInParent().getMinX(), 0.001);
			assertNotEquals(border.getBoundsInParent().getMinY(), view.getShadow().get().getBoundsInParent().getMinY(), 0.001);
		}
	}

	@Test
	public void testLineSizeShadowSameThanBorder() {
		if(model.isThicknessable()) {
			model.setThickness(20d);
			assertEquals(20d, view.getShadow().get().getStrokeWidth(), 0.001);
		}
	}

	@Test
	public void testLinePositionShadowSameThanBorder() {
		if(model.isThicknessable()) {
			model.setBordersPosition(BorderPos.INTO);
			assertEquals(border.getStrokeType(), view.getShadow().get().getStrokeType());
		}
	}

	@Test
	public void testShadowBoundSizeSameThanBorder() {
		if(model.isShadowable()) {
			model.setHasShadow(true);
			assertEquals(border.getBoundsInLocal().getWidth(), view.getShadow().get().getBoundsInLocal().getWidth(), 0.001);
			assertEquals(border.getBoundsInLocal().getHeight(), view.getShadow().get().getBoundsInLocal().getHeight(), 0.001);
		}
	}


	@Test
	public void testShadowAngle0Translate() {
		if(model.isShadowable()) {
			model.setHasShadow(true);
			model.setShadowAngle(0d);
			assertEquals(model.getShadowSize(), view.getShadow().get().getTranslateX(), 0.01);
			assertEquals(0d, view.getShadow().get().getTranslateY(), 0.01);
		}
	}

	@Test
	public void testShadowSizeAngle0Translate() {
		if(model.isShadowable()) {
			model.setHasShadow(true);
			model.setShadowAngle(0d);
			model.setShadowSize(100.21d);
			assertEquals(100.21, view.getShadow().get().getTranslateX(), 0.01);
			assertEquals(0d, view.getShadow().get().getTranslateY(), 0.01);
		}
	}


	@Test
	public void testShadowAngle90Translate() {
		if(model.isShadowable()) {
			model.setHasShadow(true);
			model.setShadowAngle(Math.PI / 2d);
			assertEquals(0d, view.getShadow().get().getTranslateX(), 0.01);
			assertEquals(-model.getShadowSize(), view.getShadow().get().getTranslateY(), 0.01);
		}
	}

	@Test
	public abstract void testShadowPositionSameThanBorder();

	@Test
	public void testShadowBeforeBorder() {
		if(model.isShadowable()) {
			assertTrue(view.getChildren().indexOf(border) > view.getChildren().indexOf(view.getShadow().get()));
		}
	}

	@Test
	public void testDbleBorderAfterBorder() {
		if(model.isShadowable()) {
			assertTrue(view.getChildren().indexOf(border) < view.getChildren().indexOf(view.getDbleBorder().get()));
		}
	}
}
