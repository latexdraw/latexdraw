package test.glib.models.interfaces;

import net.sf.latexdraw.glib.models.GLibUtilities;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.*;
import net.sf.latexdraw.glib.views.latex.DviPsColors;
import net.sf.latexdraw.parsers.ps.InvalidFormatPSFunctionException;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public abstract class TestIPlot<T extends IPlot> extends TestIPositionShape<T> {
	@Override
	@Test
	public void testDuplicate() {
		super.testDuplicate();
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

		final IPlot plot = (IPlot)shape.duplicate();

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

	@Test
	public void testNotValidEquation() {
		shape.setPlotEquation("x");

		shape.setPlotEquation("");
		assertEquals("x", shape.getPlotEquation());

		shape.setPlotEquation(null);
		assertEquals("x", shape.getPlotEquation());
	}

	@Test(expected = InvalidFormatPSFunctionException.class)
	public void testNotValidPSEquation1() {
		shape.setPlotEquation("y");
	}

	@Test
	public void testValidEquation() {
		shape.setPlotEquation("x");
		assertEquals("x", shape.getPlotEquation());

		shape.setPlotEquation("x 2 sub");
		assertEquals("x 2 sub", shape.getPlotEquation());

		shape.setPlotEquation("2 x mul sin");
		assertEquals("2 x mul sin", shape.getPlotEquation());
	}

	@Test
	public void testPolar() {
		shape.setPolar(true);
		assertTrue(shape.isPolar());
		shape.setPolar(false);
		assertFalse(shape.isPolar());
		shape.setPolar(true);
		assertTrue(shape.isPolar());
	}

	@Test
	public void testValidPlotXMinMax() {
		shape.setPlotMaxX(200.0);
		assertEquals(200.0, shape.getPlotMaxX(), 0.000001);
		shape.setPlotMinX(100.0);
		assertEquals(100.0, shape.getPlotMinX(), 0.000001);

		shape.setPlotMaxX(300.0);
		assertEquals(300.0, shape.getPlotMaxX(), 0.000001);
		shape.setPlotMinX(200.0);
		assertEquals(200.0, shape.getPlotMinX(), 0.000001);

		shape.setPlotMinX(-300.0);
		assertEquals(-300.0, shape.getPlotMinX(), 0.000001);
		shape.setPlotMaxX(0.0);
		assertEquals(0.0, shape.getPlotMaxX(), 0.000001);
	}

	@Test
	public void testNotValidPlotXMinMax() {
		shape.setPlotMaxX(200.0);
		shape.setPlotMinX(100.0);

		shape.setPlotMaxX(0.0);
		assertEquals(200.0, shape.getPlotMaxX(), 0.000001);
		assertEquals(100.0, shape.getPlotMinX(), 0.000001);

		shape.setPlotMaxX(Double.NaN);
		assertEquals(200.0, shape.getPlotMaxX(), 0.000001);
		assertEquals(100.0, shape.getPlotMinX(), 0.000001);

		shape.setPlotMaxX(Double.NEGATIVE_INFINITY);
		assertEquals(200.0, shape.getPlotMaxX(), 0.000001);
		assertEquals(100.0, shape.getPlotMinX(), 0.000001);

		shape.setPlotMaxX(Double.POSITIVE_INFINITY);
		assertEquals(200.0, shape.getPlotMaxX(), 0.000001);
		assertEquals(100.0, shape.getPlotMinX(), 0.000001);

		shape.setPlotMinX(300.0);
		assertEquals(200.0, shape.getPlotMaxX(), 0.000001);
		assertEquals(100.0, shape.getPlotMinX(), 0.000001);

		shape.setPlotMinX(Double.NaN);
		assertEquals(200.0, shape.getPlotMaxX(), 0.000001);
		assertEquals(100.0, shape.getPlotMinX(), 0.000001);

		shape.setPlotMinX(Double.NEGATIVE_INFINITY);
		assertEquals(200.0, shape.getPlotMaxX(), 0.000001);
		assertEquals(100.0, shape.getPlotMinX(), 0.000001);

		shape.setPlotMinX(Double.POSITIVE_INFINITY);
		assertEquals(200.0, shape.getPlotMaxX(), 0.000001);
		assertEquals(100.0, shape.getPlotMinX(), 0.000001);
	}

	@Test
	public void testValidGetSetNbPlottedPoints() {
		shape.setNbPlottedPoints(200);
		assertEquals(200, shape.getNbPlottedPoints());
		shape.setNbPlottedPoints(2);
		assertEquals(2, shape.getNbPlottedPoints());
	}

	@Test
	public void testNotValidGetSetNbPlottedPoints() {
		shape.setNbPlottedPoints(200);
		shape.setNbPlottedPoints(1);
		assertEquals(200, shape.getNbPlottedPoints());
		shape.setNbPlottedPoints(0);
		assertEquals(200, shape.getNbPlottedPoints());
		shape.setNbPlottedPoints(-1);
		assertEquals(200, shape.getNbPlottedPoints());
		shape.setNbPlottedPoints(1);
		assertEquals(200, shape.getNbPlottedPoints());
	}

	@Test
	public void testPlottingSet() {
		shape.setPlotMaxX(10.0);
		shape.setPlotMinX(1.0);
		shape.setNbPlottedPoints(10);
		assertEquals(1.0, shape.getPlottingStep(), 0.00001);
	}

	@Test
	public void testValidPlotStyle() {
		for(PlotStyle style : Arrays.asList(PlotStyle.values())) {
			shape.setPlotStyle(style);
			assertEquals(style, shape.getPlotStyle());
		}
	}

	@Test
	public void testNotValidPlotStyle() {
		shape.setPlotStyle(PlotStyle.ECURVE);
		shape.setPlotStyle(null);
		assertEquals(PlotStyle.ECURVE, shape.getPlotStyle());
	}

	@Test
	public void testValidDotStyle() {
		shape.setPlotStyle(PlotStyle.DOTS);
		for(DotStyle style : Arrays.asList(DotStyle.values())) {
			shape.setDotStyle(style);
			assertEquals(style, shape.getDotStyle());
		}
	}

	@Test
	public void testNotValidDotStyle() {
		shape.setDotStyle(DotStyle.BAR);
		shape.setDotStyle(null);
		assertEquals(DotStyle.BAR, shape.getDotStyle());
	}

	@Test
	public void testValidDotSize() {
		shape.setPlotStyle(PlotStyle.DOTS);
		shape.setDiametre(23.0);
		assertEquals(23.0, shape.getDiametre(), 0.00001);
		shape.setDiametre(1.0);
		assertEquals(1.0, shape.getDiametre(), 0.00001);
	}

	@Test
	public void testNotValidDotSize() {
		shape.setPlotStyle(PlotStyle.DOTS);
		shape.setDiametre(23.0);
		shape.setDiametre(0.0);
		assertEquals(23.0, shape.getDiametre(), 0.00001);
		shape.setDiametre(-1.0);
		assertEquals(23.0, shape.getDiametre(), 0.00001);
		shape.setDiametre(Double.NaN);
		assertEquals(23.0, shape.getDiametre(), 0.00001);
		shape.setDiametre(Double.NEGATIVE_INFINITY);
		assertEquals(23.0, shape.getDiametre(), 0.00001);
		shape.setDiametre(Double.POSITIVE_INFINITY);
		assertEquals(23.0, shape.getDiametre(), 0.00001);
	}

	@Test
	public void testValidDotFillingCol() {
		shape.setDotFillingCol(DviPsColors.RED);
		assertEquals(DviPsColors.RED, shape.getDotFillingCol());
		assertEquals(DviPsColors.RED, shape.getFillingCol());

		shape.setDotFillingCol(DviPsColors.BLUE);
		assertEquals(DviPsColors.BLUE, shape.getDotFillingCol());
		assertEquals(DviPsColors.BLUE, shape.getFillingCol());
	}

	@Test
	public void testNotValidDotFillingCol() {
		shape.setDotFillingCol(DviPsColors.RED);
		shape.setDotFillingCol(null);
		assertEquals(DviPsColors.RED, shape.getDotFillingCol());
		assertEquals(DviPsColors.RED, shape.getFillingCol());
	}

	@Override
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
		assertTrue(GLibUtilities.isValidPoint(pt));
		assertEquals(10.0 + 10.0 * IShape.PPC, pt.getX(), 0.0001);
		assertEquals(20.0 - 10.0 * IShape.PPC, pt.getY(), 0.0001);
	}

	@Override
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
		assertTrue(GLibUtilities.isValidPoint(pt));
		assertEquals(10.0 + 20.0 * IShape.PPC, pt.getX(), 0.0001);
		assertEquals(20.0 - 10.0 * IShape.PPC, pt.getY(), 0.0001);
	}

	@Override
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
		assertTrue(GLibUtilities.isValidPoint(pt));
		assertEquals(10.0 + 10.0 * IShape.PPC, pt.getX(), 0.0001);
		assertEquals(20.0 - 20.0 * IShape.PPC, pt.getY(), 0.0001);
	}

	@Override
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
		assertTrue(GLibUtilities.isValidPoint(pt));
		assertEquals(10.0 + 20.0 * IShape.PPC, pt.getX(), 0.0001);
		assertEquals(20.0 - 20.0 * IShape.PPC, pt.getY(), 0.0001);
	}

	@Test
	public void testInvalidMirrorHorizontal() {
		shape.setPosition(100, 200);
		shape.mirrorHorizontal(null);
		assertEquals(100.0, shape.getX(), 0.00001);
		assertEquals(200.0, shape.getY(), 0.00001);
	}

	@Test
	public void testInvalidMirrorVertical() {
		shape.setPosition(100, 200);
		shape.mirrorVertical(null);
		assertEquals(100.0, shape.getX(), 0.00001);
		assertEquals(200.0, shape.getY(), 0.00001);
	}

	@Override
	@Test
	public void testMirrorHorizontal() {
		shape.setPosition(100, 200);
		shape.mirrorHorizontal(shape.getGravityCentre());
		assertEquals(100.0, shape.getX(), 0.00001);
		assertEquals(200.0, shape.getY(), 0.00001);
	}

	@Override
	@Test
	public void testMirrorVertical() {
		shape.setPosition(100, 200);
		shape.mirrorVertical(shape.getGravityCentre());
		assertEquals(100.0, shape.getX(), 0.00001);
		assertEquals(200.0, shape.getY(), 0.00001);
	}

	@Test
	public void test2ShapesMirrorHorizontal() {
		shape.setPosition(100, 200);
		shape2.setPosition(-100, -200);
		shape.mirrorHorizontal(shape.getGravityCentre().getMiddlePoint(shape2.getGravityCentre()));
		assertEquals(-100.0, shape.getX(), 0.00001);
		assertEquals(200.0, shape.getY(), 0.00001);
	}

	@Test
	public void test2ShapesMirrorVertical() {
		shape.setPosition(100, 200);
		shape2.setPosition(-100, -200);
		shape.mirrorVertical(shape.getGravityCentre().getMiddlePoint(shape2.getGravityCentre()));
		assertEquals(100.0, shape.getX(), 0.00001);
		assertEquals(-200.0, shape.getY(), 0.00001);
	}

	@Test
	public void testCopyFromOther() {
		IRectangle rec = ShapeFactory.createRectangle();
		rec.setFillingCol(DviPsColors.BLUE);

		shape.copy(rec);

		assertEquals(DviPsColors.BLUE, shape.getFillingCol());
	}

	@Test
	public void testCopyFromDot() {
		IDot dot = ShapeFactory.createDot(ShapeFactory.createPoint());
		dot.setDotStyle(DotStyle.DIAMOND);
		dot.setDotFillingCol(DviPsColors.BLUE);
		dot.setDiametre(3.0);

		shape.copy(dot);

		assertEquals(DviPsColors.BLUE, shape.getDotFillingCol());
		assertEquals(3.0, shape.getDiametre(), 0.0001);
		assertEquals(DotStyle.DIAMOND, shape.getDotStyle());
	}

	@Override
	@Test
	public void testCopy() {
		super.testCopy();
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
}
