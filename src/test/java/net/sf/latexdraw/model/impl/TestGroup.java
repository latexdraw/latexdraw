package net.sf.latexdraw.model.impl;

import java.util.Collections;
import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.data.ShapeData;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.ArrowStyle;
import net.sf.latexdraw.model.api.shape.AxesStyle;
import net.sf.latexdraw.model.api.shape.BorderPos;
import net.sf.latexdraw.model.api.shape.DotStyle;
import net.sf.latexdraw.model.api.shape.FillingStyle;
import net.sf.latexdraw.model.api.shape.Axes;
import net.sf.latexdraw.model.api.shape.Dot;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Rectangle;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.model.api.shape.LineStyle;
import net.sf.latexdraw.model.api.shape.PlottingStyle;
import net.sf.latexdraw.model.api.shape.TicksStyle;
import net.sf.latexdraw.view.latex.DviPsColors;
import net.sf.latexdraw.view.pst.PSTricksConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class TestGroup implements HelperTest {
	Group shape;
	Rectangle sh1;
	Shape sh2;
	Shape sh3;

	@Before
	public void setUp() {
		shape = ShapeFactory.INST.createGroup();
		sh1 = ShapeFactory.INST.createRectangle();
		sh2 = ShapeFactory.INST.createEllipse();
		sh3 = ShapeFactory.INST.createRhombus();
	}

	@Test
	public void testCannotAddShapeWhichIsEmptyGroup() {
		shape.addShape(ShapeFactory.INST.createGroup());
		assertThat(shape.getShapes(), empty());
	}

	@Test
	public void testIsTypeOfOK() {
		assertTrue(shape.isTypeOf(shape.getClass()));
		assertTrue(shape.isTypeOf(Shape.class));
	}

	@Theory
	public void testIsTypeOf(@ShapeData final Shape sh2) {
		assertFalse(shape.isTypeOf(sh2.getClass()));
		shape.addShape(sh2);
		assertTrue(shape.isTypeOf(sh2.getClass()));
	}

	@Theory
	public void testAddToRotationAngle(@ShapeData final Shape sh2, @ShapeData final Shape sh3) {
		shape.addShape(sh2);
		shape.addShape(sh3);
		shape.setRotationAngle(0d);
		shape.addToRotationAngle(ShapeFactory.INST.createPoint(), 10d);
		assertThat(sh2.getRotationAngle(), closeTo(10d, 0.0001));
		assertThat(sh3.getRotationAngle(), closeTo(10d, 0.0001));
		assertThat(shape.getRotationAngle(), closeTo(10d, 0.0001));
	}

	@Test
	public void testGetDotStyleOK() {
		final Dot d1 = ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint());
		final Dot d2 = ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint());
		d1.setDotStyle(DotStyle.BAR);
		shape.getShapes().add(sh1);
		shape.getShapes().add(d1);
		shape.getShapes().add(d2);
		assertEquals(DotStyle.BAR, shape.getDotStyle());
	}

	@Test
	public void testGetDotStyleNoDot() {
		shape.getShapes().add(sh1);
		assertEquals(DotStyle.DOT, shape.getDotStyle());
	}

	@Test
	public void testSetDotStyle() {
		final Dot d1 = ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint());
		final Dot d2 = ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint());
		shape.getShapes().add(sh1);
		shape.getShapes().add(d1);
		shape.getShapes().add(d2);
		shape.setDotStyle(DotStyle.DIAMOND);
		assertEquals(DotStyle.DIAMOND, d1.getDotStyle());
		assertEquals(DotStyle.DIAMOND, d2.getDotStyle());
	}

	@Test
	public void testDuplicate() {
		assertNotNull(shape.duplicate());
	}

	@Test
	public void testDuplicateArrow() {
		shape.addShape(ShapeFactory.INST.createPolyline(Collections.emptyList()));
		shape.setArrowStyle(ArrowStyle.BAR_END, 0);
		shape.setArrowStyle(ArrowStyle.CIRCLE_END, 1);
		final Group shape2 = shape.duplicate();
		assertEquals(ArrowStyle.BAR_END, shape2.getArrowStyle(0));
		assertEquals(ArrowStyle.CIRCLE_END, shape2.getArrowStyle(1));
	}

	@Test
	public void testGetFullBottomRightPoint() {
		sh1.setPosition(10d, 20d);
		sh1.setWidth(11d);
		shape.addShape(sh1);
		assertEquals(sh1.getFullBottomRightPoint(), shape.getFullBottomRightPoint());
	}

	@Test
	public void testGetFullBottomRightPointKO() {
		assertTrue(Double.isNaN(shape.getFullBottomRightPoint().getX()));
		assertTrue(Double.isNaN(shape.getFullBottomRightPoint().getY()));
	}

	@Test
	public void testGetFullTopLeftPoint() {
		sh1.setPosition(10d, 20d);
		sh1.setWidth(11d);
		shape.addShape(sh1);
		assertEquals(sh1.getFullTopLeftPoint(), shape.getFullTopLeftPoint());
	}

	@Test
	public void testGetFullTopLeftPointKO() {
		assertTrue(Double.isNaN(shape.getFullTopLeftPoint().getX()));
		assertTrue(Double.isNaN(shape.getFullTopLeftPoint().getY()));
	}

	@Test
	public void testGetBottomLeftPoint() {
		sh1.setPosition(10d, 20d);
		sh1.setWidth(11d);
		shape.addShape(sh1);
		assertEquals(sh1.getBottomLeftPoint(), shape.getBottomLeftPoint());
	}

	@Test
	public void testGetBottomLeftPointKO() {
		assertTrue(Double.isNaN(shape.getBottomLeftPoint().getX()));
		assertTrue(Double.isNaN(shape.getBottomLeftPoint().getY()));
	}

	@Test
	public void testGetBottomRightPoint() {
		sh1.setPosition(10d, 20d);
		sh1.setWidth(11d);
		shape.addShape(sh1);
		assertEquals(sh1.getBottomRightPoint(), shape.getBottomRightPoint());
	}

	@Test
	public void testGetBottomRightPointKO() {
		assertTrue(Double.isNaN(shape.getBottomRightPoint().getX()));
		assertTrue(Double.isNaN(shape.getBottomRightPoint().getY()));
	}

	@Test
	public void testGetTopLeftPoint() {
		sh1.setPosition(10d, 20d);
		sh1.setWidth(11d);
		shape.addShape(sh1);
		assertEquals(sh1.getTopLeftPoint(), shape.getTopLeftPoint());
	}

	@Test
	public void testGetTopLeftPointKO() {
		assertTrue(Double.isNaN(shape.getTopLeftPoint().getX()));
		assertTrue(Double.isNaN(shape.getTopLeftPoint().getY()));
	}

	@Test
	public void testGetTopRightPoint() {
		sh1.setPosition(10d, 20d);
		sh1.setWidth(11d);
		shape.addShape(sh1);
		assertEquals(sh1.getTopRightPoint(), shape.getTopRightPoint());
	}

	@Test
	public void testGetTopRightPointKO() {
		assertTrue(Double.isNaN(shape.getTopRightPoint().getX()));
		assertTrue(Double.isNaN(shape.getTopRightPoint().getY()));
	}

	@Test
	public void testGetGravityCentreKO() {
		assertEquals(ShapeFactory.INST.createPoint(0, 0), shape.getGravityCentre());
	}

	@Test
	public void testGetGravityCentre() {
		sh1.setPosition(33d, -13d);
		shape.addShape(sh1);
		shape.addShape(sh2);
		assertEquals(sh1.getGravityCentre().getMiddlePoint(sh2.getGravityCentre()), shape.getGravityCentre());
	}

	@Test
	public void testSetHasHatchingsKO() {
		assertFalse(shape.hasHatchings());
	}

	@Test
	public void testSetHasHatchings() {
		shape.getShapes().addAll(sh1, sh2);
		sh2.setFillingStyle(FillingStyle.HLINES);
		assertTrue(shape.hasHatchings());
	}

	@Test
	public void testHasHatchingsWithUnstylableShape() {
		shape.addShape(ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint()));
		assertFalse(shape.hasHatchings());
	}

	@Test
	public void testSetHasGradientKO() {
		shape.addShape(ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint()));
		assertFalse(shape.hasGradient());
	}

	@Test
	public void testSetHasGradient() {
		sh1.setFillingStyle(FillingStyle.GRAD);
		shape.addShape(sh1);
		assertTrue(shape.hasGradient());
	}

	@Test
	public void testGetSetGradColEndKO() {
		assertEquals(PSTricksConstants.DEFAULT_GRADIENT_END_COLOR, shape.getGradColEnd());
	}

	@Test
	public void testGetSetGradColEnd() {
		shape.getShapes().addAll(sh1, sh2);
		shape.setGradColEnd(DviPsColors.CYAN);
		assertEquals(DviPsColors.CYAN, sh1.getGradColEnd());
		assertEquals(DviPsColors.CYAN, sh2.getGradColEnd());
	}

	@Test
	public void testGetSetGradColStartKO() {
		assertEquals(PSTricksConstants.DEFAULT_GRADIENT_START_COLOR, shape.getGradColStart());
	}

	@Test
	public void testGetSetGradColStart() {
		shape.getShapes().addAll(sh1, sh2);
		shape.setGradColStart(DviPsColors.CYAN);
		assertEquals(DviPsColors.CYAN, sh1.getGradColStart());
		assertEquals(DviPsColors.CYAN, sh2.getGradColStart());
	}


	private Axes init4getAxes() {
		final Shape sh1 = ShapeFactory.INST.createRectangle();
		final Axes sh2 = ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint());
		final Axes sh3 = ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint());
		shape.getShapes().add(sh1);
		shape.getShapes().add(sh2);
		shape.getShapes().add(sh3);
		return sh2;
	}

	@Test
	public void testGetAxesIncrementXOk() {
		init4getAxes().setIncrementX(11.0);
		assertEquals(11.0, shape.getIncrementX(), 0.0);
	}

	@Test
	public void testGetAxesIncrementXNoAxes() {
		shape.getShapes().add(ShapeFactory.INST.createRectangle());
		assertTrue(Double.isNaN(shape.getIncrementX()));
	}

	@Test
	public void testGetAxesIncrementYOk() {
		init4getAxes().setIncrementY(11.0);
		assertEqualsDouble(11.0, shape.getIncrementY());
	}

	@Test
	public void testGetAxesDistLabelsYNoAxes() {
		shape.getShapes().add(ShapeFactory.INST.createRectangle());
		assertTrue(Double.isNaN(shape.getDistLabelsY()));
	}

	@Test
	public void testGetAxesDistLabelsYOk() {
		init4getAxes().setDistLabelsY(11.0);
		assertEqualsDouble(11.0, shape.getDistLabelsY());
	}

	@Test
	public void testGetAxesDistLabelsXNoAxes() {
		shape.getShapes().add(ShapeFactory.INST.createRectangle());
		assertTrue(Double.isNaN(shape.getDistLabelsX()));
	}

	@Test
	public void testGetAxesDistLabelsXOk() {
		init4getAxes().setDistLabelsX(11.0);
		assertEqualsDouble(11.0, shape.getDistLabelsX());
	}

	@Test
	public void testGetAxesIncrementYNoAxes() {
		shape.getShapes().add(ShapeFactory.INST.createRectangle());
		assertTrue(Double.isNaN(shape.getIncrementY()));
	}

	@Test
	public void testGetAxesTicksSizeOk() {
		init4getAxes().setTicksSize(11.0);
		assertEqualsDouble(11.0, shape.getTicksSize());
	}

	@Test
	public void testGetAxesTicksSizeNoAxes() {
		shape.getShapes().add(ShapeFactory.INST.createRectangle());
		assertTrue(Double.isNaN(shape.getTicksSize()));
	}

	@Test
	public void testGetAxesIncrementOk() {
		final Point pt = ShapeFactory.INST.createPoint(10d, 11d);
		init4getAxes().setIncrement(pt);
		assertEquals(pt, shape.getIncrement());
	}

	@Test
	public void testGetAxesDistLabelsOk() {
		final Point pt = ShapeFactory.INST.createPoint(10d, 11d);
		init4getAxes().setDistLabels(pt);
		assertEquals(pt, shape.getDistLabels());
	}

	@Test
	public void testGetAxesAxesStyleOk() {
		init4getAxes().setAxesStyle(AxesStyle.FRAME);
		assertEquals(AxesStyle.FRAME, shape.getAxesStyle());
	}

	@Test
	public void testGetAxesAxesStyleNoAxes() {
		shape.getShapes().add(ShapeFactory.INST.createRectangle());
		assertEquals(AxesStyle.AXES, shape.getAxesStyle());
	}

	@Test
	public void testGetAxesTicksStyleOk() {
		init4getAxes().setTicksStyle(TicksStyle.BOTTOM);
		assertEquals(TicksStyle.BOTTOM, shape.getTicksStyle());
	}

	@Test
	public void testGetAxesTicksStyleNoAxes() {
		shape.getShapes().add(ShapeFactory.INST.createRectangle());
		assertEquals(TicksStyle.FULL, shape.getTicksStyle());
	}

	@Test
	public void testGetAxesTicksDisplayOk() {
		init4getAxes().setTicksDisplayed(PlottingStyle.NONE);
		assertEquals(PlottingStyle.NONE, shape.getTicksDisplayed());
	}

	@Test
	public void testGetAxesTicksDisplayNoAxes() {
		shape.getShapes().add(ShapeFactory.INST.createRectangle());
		assertEquals(PlottingStyle.ALL, shape.getTicksDisplayed());
	}

	@Test
	public void testGetAxesLabelsDisplayOk() {
		init4getAxes().setLabelsDisplayed(PlottingStyle.NONE);
		assertEquals(PlottingStyle.NONE, shape.getLabelsDisplayed());
	}

	@Test
	public void testGetAxesLabelsDisplayNoAxes() {
		shape.getShapes().add(ShapeFactory.INST.createRectangle());
		assertEquals(PlottingStyle.ALL, shape.getLabelsDisplayed());
	}

	@Test
	public void testGetAxesShowOriginOk() {
		init4getAxes().setShowOrigin(false);
		assertFalse(shape.isShowOrigin());
	}

	@Test
	public void testGetAxesShowOriginNoAxes() {
		shape.getShapes().add(ShapeFactory.INST.createRectangle());
		assertFalse(shape.isShowOrigin());
	}

	private void init4setAxes() {
		final Shape sh1 = ShapeFactory.INST.createRectangle();
		final Axes sh2 = ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint());
		final Shape sh1b = ShapeFactory.INST.createRectangle();
		final Axes sh3 = ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint());
		shape.getShapes().add(sh1);
		shape.getShapes().add(sh2);
		shape.getShapes().add(sh1b);
		shape.getShapes().add(sh3);
	}

	@Test
	public void testSetAxesLabelsDisplay() {
		init4setAxes();
		shape.setLabelsDisplayed(PlottingStyle.NONE);
		shape.getShapes().stream().filter(sh -> sh instanceof Axes).map(sh -> (Axes) sh).
			forEach(sh -> assertEquals(PlottingStyle.NONE, sh.getLabelsDisplayed()));
	}

	@Test
	public void testSetAxesIncrementX() {
		init4setAxes();
		shape.setIncrementX(12d);
		shape.getShapes().stream().filter(sh -> sh instanceof Axes).map(sh -> (Axes) sh).
			forEach(sh -> assertEqualsDouble(12d, sh.getIncrementX()));
	}

	@Test
	public void testSetAxesIncrementY() {
		init4setAxes();
		shape.setIncrementY(12d);
		shape.getShapes().stream().filter(sh -> sh instanceof Axes).map(sh -> (Axes) sh).
			forEach(sh -> assertEqualsDouble(12d, sh.getIncrementY()));
	}

	@Test
	public void testSetAxesDistLabelsX() {
		init4setAxes();
		shape.setDistLabelsX(12d);
		shape.getShapes().stream().filter(sh -> sh instanceof Axes).map(sh -> (Axes) sh).forEach(sh -> assertEqualsDouble(12d, sh.getDistLabelsX()));
	}

	@Test
	public void testSetAxesDistLabelsY() {
		init4setAxes();
		shape.setDistLabelsY(12d);
		shape.getShapes().stream().filter(sh -> sh instanceof Axes).map(sh -> (Axes) sh).forEach(sh -> assertEqualsDouble(12d, sh.getDistLabelsY()));
	}

	@Test
	public void testSetAxesticksSize() {
		init4setAxes();
		shape.setTicksSize(12d);
		shape.getShapes().stream().filter(sh -> sh instanceof Axes).map(sh -> (Axes) sh).
			forEach(sh -> assertEqualsDouble(12d, sh.getTicksSize()));
	}

	@Test
	public void testSetShowOrigin() {
		init4setAxes();
		shape.setShowOrigin(false);
		shape.getShapes().stream().filter(sh -> sh instanceof Axes).map(sh -> (Axes) sh).forEach(sh -> assertFalse(sh.isShowOrigin()));
	}

	@Test
	public void testSetAxesticksDisplayed() {
		init4setAxes();
		shape.setTicksDisplayed(PlottingStyle.X);
		shape.getShapes().stream().filter(sh -> sh instanceof Axes).map(sh -> (Axes) sh).
			forEach(sh -> assertEquals(PlottingStyle.X, sh.getTicksDisplayed()));
	}

	@Test
	public void testSetAxesticksStyle() {
		init4setAxes();
		shape.setTicksStyle(TicksStyle.BOTTOM);
		shape.getShapes().stream().filter(sh -> sh instanceof Axes).map(sh -> (Axes) sh).
			forEach(sh -> assertEquals(TicksStyle.BOTTOM, sh.getTicksStyle()));
	}

	@Test
	public void testSetAxesStyle() {
		init4setAxes();
		shape.setAxesStyle(AxesStyle.FRAME);
		shape.getShapes().stream().filter(sh -> sh instanceof Axes).map(sh -> (Axes) sh).
			forEach(sh -> assertEquals(AxesStyle.FRAME, sh.getAxesStyle()));
	}

	@Test
	public void testSetAxesIncrement() {
		init4setAxes();
		final Point pt = ShapeFactory.INST.createPoint(13d, 14d);
		shape.setIncrement(pt);
		shape.getShapes().stream().filter(sh -> sh instanceof Axes).map(sh -> (Axes) sh).
			forEach(sh -> assertEquals(pt, sh.getIncrement()));
	}

	@Test
	public void testSetAxesdistLabels() {
		init4setAxes();
		final Point pt = ShapeFactory.INST.createPoint(13d, 14d);
		shape.setDistLabels(pt);
		shape.getShapes().stream().filter(sh -> sh instanceof Axes).map(sh -> (Axes) sh).
			forEach(sh -> assertEquals(pt, sh.getDistLabels()));
	}

	private void init4setFill() {
		final Axes sh2 = ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint());
		final Shape sh1 = ShapeFactory.INST.createRectangle();
		final Shape sh1b = ShapeFactory.INST.createRectangle();
		shape.getShapes().add(sh2);
		shape.getShapes().add(sh1);
		shape.getShapes().add(sh1b);
	}

	@Test
	public void testGetSetGradAngle() {
		init4setFill();
		shape.setFillingStyle(FillingStyle.GRAD);
		shape.setGradAngle(1d);
		assertEqualsDouble(1d, shape.getGradAngle());
		shape.getShapes().stream().filter(sh -> sh.isInteriorStylable()).forEach(sh -> assertEqualsDouble(1d, sh.getGradAngle()));
	}

	@Test
	public void testGetSetGradMidPt() {
		init4setFill();
		shape.setFillingStyle(FillingStyle.GRAD);
		shape.setGradMidPt(1d);
		assertEqualsDouble(1d, shape.getGradMidPt());
		shape.getShapes().stream().filter(sh -> sh.isInteriorStylable()).forEach(sh -> assertEqualsDouble(1d, sh.getGradMidPt()));
	}

	@Test
	public void testGetSetHatchingsSep() {
		init4setFill();
		shape.setFillingStyle(FillingStyle.CLINES_PLAIN);
		shape.setHatchingsSep(1d);
		assertEqualsDouble(1d, shape.getHatchingsSep());
		shape.getShapes().stream().filter(sh -> sh.isInteriorStylable()).forEach(sh -> assertEqualsDouble(1d, sh.getHatchingsSep()));
	}

	@Test
	public void testGetSetHatchingsCol() {
		init4setFill();
		shape.setFillingStyle(FillingStyle.HLINES);
		shape.setHatchingsCol(DviPsColors.RED);
		assertEquals(java.awt.Color.RED, shape.getHatchingsCol().toAWT());
		shape.getShapes().stream().filter(sh -> sh.isFillable()).forEach(sh -> assertEquals(java.awt.Color.RED, sh.getHatchingsCol().toAWT()));
	}

	@Test
	public void testGetSetHatchingsAngle() {
		init4setFill();
		shape.setHatchingsAngle(15d);
		assertEqualsDouble(15d, shape.getHatchingsAngle());
		shape.getShapes().stream().filter(sh -> sh.isInteriorStylable()).forEach(sh -> assertEqualsDouble(15d, sh.getHatchingsAngle()));
	}

	@Test
	public void testGetSetHatchingsWidth() {
		init4setFill();
		shape.setHatchingsWidth(15d);
		assertEqualsDouble(15d, shape.getHatchingsWidth());
		shape.getShapes().stream().filter(sh -> sh.isInteriorStylable()).forEach(sh -> assertEqualsDouble(15d, sh.getHatchingsWidth()));
	}

	@Test
	public void testGetSetRotationAngle() {
		init4setFill();
		shape.setRotationAngle(1);
		assertEqualsDouble(1d, shape.getRotationAngle());
		shape.getShapes().forEach(sh -> assertEqualsDouble(1d, sh.getRotationAngle()));
	}

	@Test
	public void testIsSetShowPts() {
		init4setFill();
		shape.addShape(ShapeFactory.INST.createBezierCurve(Collections.emptyList()));
		shape.setShowPts(true);
		assertTrue(shape.isShowPts());
		shape.getShapes().stream().filter(sh -> sh.isShowPtsable()).forEach(sh -> assertTrue(sh.isShowPts()));
	}

	@Test
	public void testHasSetDbleBord() {
		init4setFill();
		shape.setHasDbleBord(true);
		assertTrue(shape.hasDbleBord());
		shape.getShapes().stream().filter(sh -> sh.isDbleBorderable()).forEach(sh -> assertTrue(sh.hasDbleBord()));
	}

	@Test
	public void testGetSetDbleBordCol() {
		init4setFill();
		shape.setHasDbleBord(true);
		shape.setDbleBordCol(DviPsColors.RED);
		assertEquals(java.awt.Color.RED, shape.getDbleBordCol().toAWT());
		shape.getShapes().stream().filter(sh -> sh.isDbleBorderable()).
			forEach(sh -> assertEquals(java.awt.Color.RED, sh.getDbleBordCol().toAWT()));
	}

	@Test
	public void testGetPtAt() {
		assertNull(shape.getPtAt(0));
	}

	@Test
	public void testGetNbPoints() {
		assertEquals(0, shape.getNbPoints());
	}

	@Test
	public void testGetSetDbleBordSep() {
		init4setFill();
		shape.setHasDbleBord(true);
		shape.setDbleBordSep(15d);
		assertEqualsDouble(15d, shape.getDbleBordSep());
		shape.getShapes().stream().filter(sh -> sh.isDbleBorderable()).forEach(sh -> assertEqualsDouble(15d, sh.getDbleBordSep()));
	}

	@Test
	public void testHasSetShadow() {
		init4setFill();
		shape.setHasShadow(true);
		assertTrue(shape.hasShadow());
		shape.getShapes().stream().filter(sh -> sh.isShadowable()).forEach(sh -> assertTrue(sh.hasShadow()));
	}

	@Test
	public void testGetSetShadowCol() {
		init4setFill();
		shape.setHasShadow(true);
		shape.setShadowCol(DviPsColors.RED);
		assertEquals(java.awt.Color.RED, shape.getShadowCol().toAWT());
		shape.getShapes().stream().filter(sh -> sh.isDbleBorderable()).
			forEach(sh -> assertEquals(java.awt.Color.RED, sh.getShadowCol().toAWT()));
	}

	@Test
	public void testGetSetShadowAngle() {
		init4setFill();
		shape.setHasShadow(true);
		shape.setShadowAngle(1d);
		assertEqualsDouble(1d, shape.getShadowAngle());
		shape.getShapes().stream().filter(sh -> sh.isFillable()).forEach(sh -> assertEqualsDouble(1d, sh.getShadowAngle()));
	}

	@Test
	public void testIsSetFilled() {
		init4setFill();
		shape.setFilled(true);
		assertTrue(shape.isFilled());
		shape.getShapes().stream().filter(sh -> sh.isFillable()).forEach(sh -> assertTrue(shape.isFilled()));
	}

	@Test
	public void testGetSetShadowSize() {
		init4setFill();
		shape.setHasShadow(true);
		shape.setShadowSize(1d);
		assertEqualsDouble(1d, shape.getShadowSize());
		shape.getShapes().stream().filter(sh -> sh.isFillable()).forEach(sh -> assertEqualsDouble(1d, sh.getShadowSize()));
	}

	@Test
	public void testGetSetBorderPosition() {
		init4setFill();
		shape.setBordersPosition(BorderPos.MID);
		assertEquals(BorderPos.MID, shape.getBordersPosition());
		shape.getShapes().stream().filter(sh -> sh.isBordersMovable()).forEach(sh -> assertEquals(BorderPos.MID, sh.getBordersPosition()));
	}

	@Test
	public void testSetGetThickness() {
		init4setFill();
		shape.setThickness(10d);
		assertEqualsDouble(10d, shape.getThickness());
		shape.getShapes().stream().filter(sh -> sh.isThicknessable()).forEach(sh -> assertEqualsDouble(10d, sh.getThickness()));
	}

	@Test
	public void testSetGetLineColour() {
		init4setFill();
		shape.setLineColour(DviPsColors.RED);
		assertEquals(java.awt.Color.RED, shape.getLineColour().toAWT());
		shape.getShapes().forEach(sh -> assertEquals(java.awt.Color.RED, sh.getLineColour().toAWT()));
	}

	@Test
	public void testSetGetLineColourWithDot() {
		init4setFill();
		shape.addShape(ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint()));
		shape.setLineColour(DviPsColors.RED);
		assertEquals(java.awt.Color.RED, shape.getLineColour().toAWT());
		shape.getShapes().forEach(sh -> assertEquals(java.awt.Color.RED, sh.getLineColour().toAWT()));
	}

	@Test
	public void testSetGetLineStyle() {
		init4setFill();
		shape.setLineStyle(LineStyle.DASHED);
		assertEquals(LineStyle.DASHED, shape.getLineStyle());
		shape.getShapes().stream().filter(sh -> sh.isBordersMovable()).forEach(sh -> assertEquals(LineStyle.DASHED, sh.getLineStyle()));
	}

	@Test
	public void testSetGetDashSepWhite() {
		init4setFill();
		shape.setDashSepWhite(15d);
		assertEqualsDouble(15d, shape.getDashSepWhite());
		shape.getShapes().stream().filter(sh -> sh.isLineStylable()).forEach(sh -> assertEqualsDouble(15d, sh.getDashSepWhite()));
	}

	@Test
	public void testSetGetDashSepBlack() {
		init4setFill();
		shape.setDashSepBlack(15d);
		assertEqualsDouble(15d, shape.getDashSepBlack());
		shape.getShapes().stream().filter(sh -> sh.isLineStylable()).forEach(sh -> assertEqualsDouble(15d, sh.getDashSepBlack()));
	}

	@Test
	public void testSetGetDotSep() {
		init4setFill();
		shape.setDotSep(15d);
		assertEqualsDouble(15d, shape.getDotSep());
		shape.getShapes().stream().filter(sh -> sh.isLineStylable()).forEach(sh -> assertEqualsDouble(15d, sh.getDotSep()));
	}

	@Test
	public void testSetGetFillingCol() {
		init4setFill();
		shape.setFilled(true);
		shape.setFillingCol(DviPsColors.RED);
		assertEquals(java.awt.Color.RED, shape.getFillingCol().toAWT());
		shape.getShapes().stream().filter(sh -> sh.isFillable()).
			forEach(sh -> assertEquals(java.awt.Color.RED, sh.getFillingCol().toAWT()));
	}

	@Test
	public void testSetGetFillingStyle() {
		init4setFill();
		shape.setFillingStyle(FillingStyle.GRAD);
		assertEquals(FillingStyle.GRAD, shape.getFillingStyle());
		shape.getShapes().stream().filter(sh -> sh.isInteriorStylable()).
			forEach(sh -> assertEquals(FillingStyle.GRAD, sh.getFillingStyle()));
	}
}
