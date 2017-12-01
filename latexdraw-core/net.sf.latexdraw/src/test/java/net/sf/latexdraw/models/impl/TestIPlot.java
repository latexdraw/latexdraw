package net.sf.latexdraw.models.impl;

import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.data.DoubleData;
import net.sf.latexdraw.data.StringData;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.prop.IPlotProp;
import net.sf.latexdraw.models.interfaces.shape.DotStyle;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IPositionShape;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.PlotStyle;
import net.sf.latexdraw.parsers.ps.InvalidFormatPSFunctionException;
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
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;

@RunWith(Theories.class)
public class TestIPlot implements HelperTest {
	IPlot shape;

	@Before
	public void setUp() {
		shape = ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(), 0d, 10d, "x", false);
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

		final IPlot plot = shape.duplicate();

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
		shape.setPlotEquation(value);
		assertEquals("x", shape.getPlotEquation());
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
		assumeThat(value, greaterThan(0d));

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
		shape.setPlotMinX(value);
		assertEqualsDouble(0d, shape.getPlotMinX());
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

		IPoint pt = shape.getBottomLeftPoint();
		assertTrue(MathUtils.INST.isValidPt(pt));
		assertEquals(10.0 + 10.0 * IShape.PPC, pt.getX(), 0.0001);
		assertEquals(20.0 - 10.0 * IShape.PPC, pt.getY(), 0.0001);
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

		IPoint pt = shape.getBottomRightPoint();
		assertTrue(MathUtils.INST.isValidPt(pt));
		assertEquals(10.0 + 20.0 * IShape.PPC, pt.getX(), 0.0001);
		assertEquals(20.0 - 10.0 * IShape.PPC, pt.getY(), 0.0001);
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

		IPoint pt = shape.getTopLeftPoint();
		assertTrue(MathUtils.INST.isValidPt(pt));
		assertEquals(10.0 + 10.0 * IShape.PPC, pt.getX(), 0.0001);
		assertEquals(20.0 - 20.0 * IShape.PPC, pt.getY(), 0.0001);
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

		IPoint pt = shape.getTopRightPoint();
		assertTrue(MathUtils.INST.isValidPt(pt));
		assertEquals(10.0 + 20.0 * IShape.PPC, pt.getX(), 0.0001);
		assertEquals(20.0 - 20.0 * IShape.PPC, pt.getY(), 0.0001);
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
		IRectangle rec = ShapeFactory.INST.createRectangle();
		rec.setFillingCol(DviPsColors.BLUE);

		shape.copy(rec);

		assertEquals(DviPsColors.BLUE, shape.getFillingCol());
	}

	@Test
	public void testCopyFromDot() {
		IDot dot = ShapeFactory.INST.createDot(ShapeFactory.INST.createPoint());
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
		final IPlot shape2 = ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(), 1d, 11d, "x 2 mul", false);

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
		assertFalse(shape.isTypeOf(IRectangle.class));
		assertFalse(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(IPositionShape.class));
		assertTrue(shape.isTypeOf(IPlot.class));
		assertTrue(shape.isTypeOf(IPlotProp.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}
}
