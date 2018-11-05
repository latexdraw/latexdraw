package net.sf.latexdraw.model.impl;

import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.data.DoubleData;
import net.sf.latexdraw.data.PlotSupplier;
import net.sf.latexdraw.data.StringData;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.property.PlotProp;
import net.sf.latexdraw.model.api.shape.DotStyle;
import net.sf.latexdraw.model.api.shape.Circle;
import net.sf.latexdraw.model.api.shape.Dot;
import net.sf.latexdraw.model.api.shape.Plot;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.PositionShape;
import net.sf.latexdraw.model.api.shape.Rectangle;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.model.api.shape.PlotStyle;
import net.sf.latexdraw.parser.ps.InvalidFormatPSFunctionException;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;

@RunWith(Theories.class)
public class TestPlot implements HelperTest {
	Plot shape;

	@Before
	public void setUp() {
		shape = PlotSupplier.createPlot();
	}

	@Test
	public void testDuplicate() {
		shape.setPlotEquation("2 x mul");
		shape.setPolar(true);
		shape.setNbPlottedPoints(73);
		shape.setPlotStyle(PlotStyle.ECURVE);
		shape.setPlotMinX(-234.0);
		shape.setPlotMaxX(123.0);
		shape.setDotStyle(DotStyle.BAR);
		shape.setDiametre(23.0);
		shape.setDotFillingCol(DviPsColors.YELLOW);
		shape.setPlotEquation("x");

		final Plot plot = shape.duplicate();

		assertEquals("x", plot.getPlotEquation());
		assertTrue(plot.isPolar());
		assertEquals(DviPsColors.YELLOW, plot.getDotFillingCol());
		assertEquals(23.0, plot.getDiametre(), 0.0001);
		assertEquals(DotStyle.BAR, plot.getDotStyle());
		assertEquals(123.0, plot.getPlotMaxX(), 0.0001);
		assertEquals(-234.0, plot.getPlotMinX(), 0.0001);
		assertEquals(PlotStyle.ECURVE, plot.getPlotStyle());
		assertEquals(73, plot.getNbPlottedPoints());
	}

	@Theory
	public void testNotValidEquation(@StringData(vals = {""}, withNull = true) final String value) {
		final String eq = shape.getPlotEquation();
		shape.setPlotEquation(value);
		assertEquals(eq, shape.getPlotEquation());
	}

	@Test(expected = InvalidFormatPSFunctionException.class)
	public void testNotValidPSEquation1() {
		shape.setPlotEquation("y");
	}

	@Theory
	public void testValidEquation(@StringData(vals = {"x 2 sub", "2 x mul sin"}) final String value) {
		shape.setPlotEquation(value);
		assertEquals(value, shape.getPlotEquation());
	}

	@Theory
	public void testPolar(final boolean value) {
		shape.setPolar(value);
		assertEquals(value, shape.isPolar());
	}

	@Theory
	public void testValidPlotXMax(@DoubleData final double value) {
		assumeThat(value, greaterThan(shape.getPlotMinX()));
		shape.setPlotMaxX(value);
		assertEqualsDouble(value, shape.getPlotMaxX());
	}

	@Theory
	public void testValidPlotXMaxKO(@DoubleData(bads = true, vals = {0d, -1d}) final double value) {
		shape.setPlotMaxX(value);
		assertEqualsDouble(10d, shape.getPlotMaxX());
	}

	@Theory
	public void testValidPlotXMin(@DoubleData final double value) {
		assumeThat(value, lessThan(10d));

		shape.setPlotMinX(value);
		assertEqualsDouble(value, shape.getPlotMinX());
	}

	@Theory
	public void testValidPlotXMinKO(@DoubleData(bads = true, vals = {10d, 11d}) final double value) {
		final double oldval = shape.getPlotMinX();
		shape.setPlotMinX(value);
		assertEqualsDouble(oldval, shape.getPlotMinX());
	}

	@Theory
	public void testValidGetSetNbPlottedPoints(@TestedOn(ints = {2, 10, 200}) final int value) {
		shape.setNbPlottedPoints(value);
		assertEquals(value, shape.getNbPlottedPoints());
	}

	@Theory
	public void testValidGetSetNbPlottedPointsKO(@TestedOn(ints = {1, 0, -1}) final int value) {
		shape.setNbPlottedPoints(10);
		shape.setNbPlottedPoints(value);
		assertEquals(10, shape.getNbPlottedPoints());
	}

	@Test
	public void testPlottingSet() {
		shape.setPlotMaxX(10.0);
		shape.setPlotMinX(1.0);
		shape.setNbPlottedPoints(10);
		assertEquals(1.0, shape.getPlottingStep(), 0.00001);
	}

	@Theory
	public void testValidPlotStyle(final PlotStyle style) {
		shape.setPlotStyle(style);
		assertEquals(style, shape.getPlotStyle());
	}

	@Test
	public void testValidPlotStyleKO() {
		shape.setPlotStyle(PlotStyle.ECURVE);
		shape.setPlotStyle(null);
		assertEquals(PlotStyle.ECURVE, shape.getPlotStyle());
	}

	@Theory
	public void testValidDotStyle(final DotStyle style) {
		shape.setPlotStyle(PlotStyle.DOTS);
		shape.setDotStyle(style);
		assertEquals(style, shape.getDotStyle());
	}

	@Test
	public void testValidDotStyleKO() {
		shape.setDotStyle(DotStyle.BAR);
		shape.setDotStyle(null);
		assertEquals(DotStyle.BAR, shape.getDotStyle());
	}

	@Theory
	public void testValidDotSize(@DoubleData(vals = {1d, 23d}) final double value) {
		shape.setPlotStyle(PlotStyle.DOTS);
		shape.setDiametre(value);
		assertEquals(value, shape.getDiametre(), 0.00001);
	}

	@Theory
	public void testValidDotSizeKO(@DoubleData(vals = {0d, -1d}) final double value) {
		shape.setPlotStyle(PlotStyle.DOTS);
		shape.setDiametre(23.0);
		shape.setDiametre(value);
		assertEquals(23.0, shape.getDiametre(), 0.00001);
	}

	@Test
	public void testValidDotFillingCol() {
		shape.setDotFillingCol(DviPsColors.RED);
		assertEquals(DviPsColors.RED, shape.getDotFillingCol());
		assertEquals(DviPsColors.RED, shape.getFillingCol());
	}

	@Test
	public void testValidDotFillingColKO() {
		shape.setDotFillingCol(DviPsColors.RED);
		shape.setDotFillingCol(null);
		assertEquals(DviPsColors.RED, shape.getDotFillingCol());
		assertEquals(DviPsColors.RED, shape.getFillingCol());
	}

	@Test
	public void testGetBottomLeftPoint() {
		shape.setPlotEquation("x");
		shape.setPlotMaxX(20.0);
		shape.setPlotMinX(10.0);
		shape.setXScale(1.0);
		shape.setYScale(1.0);
		shape.setNbPlottedPoints(30);
		shape.setPosition(10.0, 20.0);

		final Point pt = shape.getBottomLeftPoint();
		assertTrue(MathUtils.INST.isValidPt(pt));
		assertEquals(10.0 + 10.0 * Shape.PPC, pt.getX(), 0.0001);
		assertEquals(20.0 - 10.0 * Shape.PPC, pt.getY(), 0.0001);
	}

	@Test
	public void testGetBottomRightPoint() {
		shape.setPlotEquation("x");
		shape.setPlotMaxX(20.0);
		shape.setPlotMinX(10.0);
		shape.setXScale(1.0);
		shape.setYScale(1.0);
		shape.setNbPlottedPoints(30);
		shape.setPosition(10.0, 20.0);

		final Point pt = shape.getBottomRightPoint();
		assertTrue(MathUtils.INST.isValidPt(pt));
		assertEquals(10.0 + 20.0 * Shape.PPC, pt.getX(), 0.0001);
		assertEquals(20.0 - 10.0 * Shape.PPC, pt.getY(), 0.0001);
	}

	@Test
	public void testGetTopLeftPoint() {
		shape.setPlotEquation("x");
		shape.setPlotMaxX(20.0);
		shape.setPlotMinX(10.0);
		shape.setXScale(1.0);
		shape.setYScale(1.0);
		shape.setNbPlottedPoints(30);
		shape.setPosition(10.0, 20.0);

		final Point pt = shape.getTopLeftPoint();
		assertTrue(MathUtils.INST.isValidPt(pt));
		assertEquals(10.0 + 10.0 * Shape.PPC, pt.getX(), 0.0001);
		assertEquals(20.0 - 20.0 * Shape.PPC, pt.getY(), 0.0001);
	}

	@Test
	public void testGetTopRightPoint() {
		shape.setPlotEquation("x");
		shape.setPlotMaxX(20.0);
		shape.setPlotMinX(10.0);
		shape.setXScale(1.0);
		shape.setYScale(1.0);
		shape.setNbPlottedPoints(30);
		shape.setPosition(10.0, 20.0);

		final Point pt = shape.getTopRightPoint();
		assertTrue(MathUtils.INST.isValidPt(pt));
		assertEquals(10.0 + 20.0 * Shape.PPC, pt.getX(), 0.0001);
		assertEquals(20.0 - 20.0 * Shape.PPC, pt.getY(), 0.0001);
	}

	@Theory
	public void testInvalidMirrorHorizontal(@DoubleData(bads = true, vals = {}) final double value) {
		shape.setPosition(100, 200);
		shape.mirrorHorizontal(value);
		assertEquals(100.0, shape.getX(), 0.00001);
		assertEquals(200.0, shape.getY(), 0.00001);
	}

	@Theory
	public void testInvalidMirrorVertical(@DoubleData(bads = true, vals = {}) final double value) {
		shape.setPosition(100, 200);
		shape.mirrorVertical(value);
		assertEquals(100.0, shape.getX(), 0.00001);
		assertEquals(200.0, shape.getY(), 0.00001);
	}

	@Test
	public void testMirrorHorizontal() {
		shape.setPosition(100, 200);
		shape.mirrorHorizontal(shape.getGravityCentre().getX());
		assertEquals(100.0, shape.getX(), 0.00001);
		assertEquals(200.0, shape.getY(), 0.00001);
	}

	@Test
	public void testMirrorVertical() {
		shape.setPosition(100, 200);
		shape.mirrorVertical(shape.getGravityCentre().getY());
		assertEquals(100.0, shape.getX(), 0.00001);
		assertEquals(200.0, shape.getY(), 0.00001);
	}

	@Test
	public void testCopyFromOther() {
		final Rectangle rec = ShapeFactory.INST.createRectangle();
		rec.setFillingCol(DviPsColors.BLUE);

		shape.copy(rec);

		assertEquals(DviPsColors.BLUE, shape.getFillingCol());
	}

	@Test
	public void testCopyFromDot() {
		final Dot dot = ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint());
		dot.setDotStyle(DotStyle.DIAMOND);
		dot.setDotFillingCol(DviPsColors.BLUE);
		dot.setDiametre(3.0);

		shape.copy(dot);

		assertEquals(DviPsColors.BLUE, shape.getDotFillingCol());
		assertEquals(3.0, shape.getDiametre(), 0.0001);
		assertEquals(DotStyle.DIAMOND, shape.getDotStyle());
	}

	@Test
	public void testCopy() {
		final Plot shape2 = ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(), 1d, 11d, "x 2 mul", false);

		shape2.setPlotEquation("2 x mul");
		shape2.setPolar(true);
		shape2.setNbPlottedPoints(73);
		shape2.setPlotStyle(PlotStyle.ECURVE);
		shape2.setPlotMinX(-234.0);
		shape2.setPlotMaxX(123.0);
		shape2.setDotStyle(DotStyle.BAR);
		shape2.setDiametre(23.0);
		shape2.setDotFillingCol(DviPsColors.YELLOW);

		shape.setPlotEquation("x");
		shape.copy(shape2);

		assertEquals("2 x mul", shape.getPlotEquation());
		assertTrue(shape.isPolar());
		assertEquals(DviPsColors.YELLOW, shape.getDotFillingCol());
		assertEquals(23.0, shape.getDiametre(), 0.0001);
		assertEquals(DotStyle.BAR, shape.getDotStyle());
		assertEquals(123.0, shape.getPlotMaxX(), 0.0001);
		assertEquals(-234.0, shape.getPlotMinX(), 0.0001);
		assertEquals(PlotStyle.ECURVE, shape.getPlotStyle());
		assertEquals(73, shape.getNbPlottedPoints());
	}

	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(Rectangle.class));
		assertFalse(shape.isTypeOf(Circle.class));
		assertTrue(shape.isTypeOf(Shape.class));
		assertTrue(shape.isTypeOf(PositionShape.class));
		assertTrue(shape.isTypeOf(Plot.class));
		assertTrue(shape.isTypeOf(PlotProp.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}

	@Test
	public void testPolarPropertyNotNull() {
		assertNotNull(shape.polarProperty());
	}

	@Test
	public void testPlotEquationPropertyNotNull() {
		assertNotNull(shape.plotEquationProperty());
	}

	@Test
	public void testPlotMinXPropertyNotNull() {
		assertNotNull(shape.plotMinXProperty());
	}

	@Test
	public void testPlotMaxXPropertyNotNull() {
		assertNotNull(shape.plotMaxXProperty());
	}

	@Test
	public void testNbPlottedPointsPropertyNotNull() {
		assertNotNull(shape.nbPlottedPointsProperty());
	}

	@Test
	public void testPlotStylePropertyNotNull() {
		assertNotNull(shape.plotStyleProperty());
	}

	@Test
	public void testDotStylePropertyNotNull() {
		assertNotNull(shape.dotStyleProperty());
	}

	@Test
	public void testDotDiametrePropertyNotNull() {
		assertNotNull(shape.dotDiametreProperty());
	}

	@Test
	public void testXScalePropertyNotNull() {
		assertNotNull(shape.xScaleProperty());
	}

	@Test
	public void testYScalePropertyNotNull() {
		assertNotNull(shape.yScaleProperty());
	}
}
