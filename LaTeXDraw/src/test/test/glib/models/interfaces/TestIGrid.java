package test.glib.models.interfaces;

import java.awt.Color;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IGrid;
import net.sf.latexdraw.glib.models.interfaces.IShape;

import org.junit.Test;


public abstract class TestIGrid<T extends IGrid> extends TestIStandardGrid<T> {
	@Test
	public void testGetStep() {
		shape.setUnit(1);
		assertEquals((double)IShape.PPC, shape.getStep());
		shape.setUnit(2);
		assertEquals(2.*IShape.PPC, shape.getStep());
	}

	@Test
	public void testGetSetGridDots() {
		shape.setGridDots(20);
		assertEquals(20, shape.getGridDots());
		shape.setGridDots(10);
		assertEquals(10, shape.getGridDots());
		shape.setGridDots(0);
		assertEquals(0, shape.getGridDots());
		shape.setGridDots(-30);
		assertEquals(0, shape.getGridDots());
	}

	@Test
	public void testGetSetGridLabelsColor() {
		shape.setGridLabelsColour(Color.BLUE);
		assertEquals(Color.BLUE, shape.getGridLabelsColour());
		shape.setGridLabelsColour(Color.RED);
		assertEquals(Color.RED, shape.getGridLabelsColour());
		shape.setGridLabelsColour(null);
		assertEquals(Color.RED, shape.getGridLabelsColour());
	}

	@Test
	public void testGetSetGridWidth() {
		shape.setGridWidth(30);
		assertEquals(30., shape.getGridWidth());
		shape.setGridWidth(50);
		assertEquals(50., shape.getGridWidth());
		shape.setGridWidth(0);
		assertEquals(50., shape.getGridWidth());
		shape.setGridWidth(-1);
		assertEquals(50., shape.getGridWidth());
		shape.setGridWidth(Double.NaN);
		assertEquals(50., shape.getGridWidth());
		shape.setGridWidth(Double.NEGATIVE_INFINITY);
		assertEquals(50., shape.getGridWidth());
		shape.setGridWidth(Double.POSITIVE_INFINITY);
		assertEquals(50., shape.getGridWidth());
	}

	@Test
	public void testGetSetSubGridColor() {
		shape.setSubGridColour(Color.BLUE);
		assertEquals(Color.BLUE, shape.getSubGridColour());
		shape.setSubGridColour(Color.RED);
		assertEquals(Color.RED, shape.getSubGridColour());
		shape.setSubGridColour(null);
		assertEquals(Color.RED, shape.getSubGridColour());
	}

	@Test
	public void testGetSetSubGridDiv() {
		shape.setSubGridDiv(20);
		assertEquals(20, shape.getSubGridDiv());
		shape.setSubGridDiv(10);
		assertEquals(10, shape.getSubGridDiv());
		shape.setSubGridDiv(0);
		assertEquals(0, shape.getSubGridDiv());
		shape.setSubGridDiv(-30);
		assertEquals(0, shape.getSubGridDiv());
	}

	@Test
	public void testGetSetSubGridDots() {
		shape.setSubGridDots(20);
		assertEquals(20, shape.getSubGridDots());
		shape.setSubGridDots(10);
		assertEquals(10, shape.getSubGridDots());
		shape.setSubGridDots(0);
		assertEquals(0, shape.getSubGridDots());
		shape.setSubGridDots(-30);
		assertEquals(0, shape.getSubGridDots());
	}

	@Test
	public void testGetSetSubGridWidth() {
		shape.setSubGridWidth(30);
		assertEquals(30., shape.getSubGridWidth());
		shape.setSubGridWidth(50);
		assertEquals(50., shape.getSubGridWidth());
		shape.setSubGridWidth(0);
		assertEquals(50., shape.getSubGridWidth());
		shape.setSubGridWidth(-1);
		assertEquals(50., shape.getSubGridWidth());
		shape.setSubGridWidth(Double.NaN);
		assertEquals(50., shape.getSubGridWidth());
		shape.setSubGridWidth(Double.NEGATIVE_INFINITY);
		assertEquals(50., shape.getSubGridWidth());
		shape.setSubGridWidth(Double.POSITIVE_INFINITY);
		assertEquals(50., shape.getSubGridWidth());
	}

	@Test
	public void testGetSetUnit() {
		shape.setUnit(30);
		assertEquals(30., shape.getUnit());
		shape.setUnit(50);
		assertEquals(50., shape.getUnit());
		shape.setUnit(0);
		assertEquals(50., shape.getUnit());
		shape.setUnit(-1);
		assertEquals(50., shape.getUnit());
		shape.setUnit(Double.NaN);
		assertEquals(50., shape.getUnit());
		shape.setUnit(Double.NEGATIVE_INFINITY);
		assertEquals(50., shape.getUnit());
		shape.setUnit(Double.POSITIVE_INFINITY);
		assertEquals(50., shape.getUnit());
	}


	@Override@Test
	public void testDuplicate() {
		super.testDuplicate();

		shape.setGridDots(45);
		shape.setSubGridDots(55);
		shape.setGridLabelsColour(Color.CYAN);
		shape.setSubGridColour(Color.GREEN);
		shape.setUnit(0.6);
		shape.setGridWidth(12);
		shape.setSubGridWidth(24);
		shape.setSubGridDiv(32);

		IGrid g2 = (IGrid)shape.duplicate();

		assertEquals(g2.getGridDots(), shape.getGridDots());
		assertEquals(g2.getSubGridDiv(), shape2.getSubGridDiv());
		assertEquals(g2.getGridLabelsColour(), shape.getGridLabelsColour());
		assertEquals(g2.getSubGridColour(), shape.getSubGridColour());
		assertEquals(g2.getUnit(), shape.getUnit());
		assertEquals(g2.getGridWidth(), shape.getGridWidth());
		assertEquals(g2.getSubGridWidth(), shape.getSubGridWidth());
		assertEquals(g2.getSubGridDiv(), shape.getSubGridDiv());
	}


	@Override@Test
	public void testGetBottomLeftPoint() {
		shape.setPosition(10, 20);
		assertEquals(10., shape.getBottomLeftPoint().getX());
		assertEquals(20., shape.getBottomLeftPoint().getY());
		shape.setPosition(-10, -20);
		assertEquals(-10., shape.getBottomLeftPoint().getX());
		assertEquals(-20., shape.getBottomLeftPoint().getY());
	}


	@Override@Test
	public void testGetBottomRightPoint() {
		shape.setPosition(0, 0);
		shape.setGridStart(-200, -100);
		shape.setGridEnd(50, 75);
		shape.setUnit(2);

		assertEquals(2.*IShape.PPC*250., shape.getBottomRightPoint().getX());
		assertEquals(0., shape.getBottomRightPoint().getY());
	}


	@Override@Test
	public void testGetTopLeftPoint() {
		shape.setPosition(0, 0);
		shape.setGridStart(-200, -100);
		shape.setGridEnd(50, 75);
		shape.setUnit(2);

		assertEquals(0., shape.getTopLeftPoint().getX());
		assertEquals(-2.*IShape.PPC*175., shape.getTopLeftPoint().getY());
	}


	@Override@Test
	public void testGetTopRightPoint() {
		shape.setPosition(0, 0);
		shape.setGridStart(-200, -100);
		shape.setGridEnd(50, 75);
		shape.setUnit(2);

		assertEquals(2.*IShape.PPC*250., shape.getTopRightPoint().getX());
		assertEquals(-2.*IShape.PPC*175., shape.getTopRightPoint().getY());
	}


	@Override@Test
	public void testMirrorHorizontal() {
		shape.setPosition(0, 0);
		shape.setGridStart(0, 0);
		shape.setGridEnd(10, 10);
		shape.setUnit(1);

		shape.mirrorHorizontal(DrawingTK.getFactory().createPoint(IShape.PPC*10., 0.));
		assertEquals(IShape.PPC*10., shape.getPosition().getX());
		assertEquals(0., shape.getPosition().getY());
	}


	@Override@Test
	public void testMirrorVertical() {
		shape.setPosition(0, 0);
		shape.setGridStart(0, 0);
		shape.setGridEnd(10, 10);
		shape.setUnit(1);

		shape.mirrorVertical(DrawingTK.getFactory().createPoint(0., -IShape.PPC*10.));
		assertEquals(0., shape.getPosition().getX());
		assertEquals(-IShape.PPC*10., shape.getPosition().getY());
	}


//	@Override@Test
//	public void testScale() {
//		shape.setPosition(0, 0);
//		shape.setGridStart(0, 0);
//		shape.setGridEnd(10, 10);
//		shape.setUnit(1);
//
//		IPoint pos = shape.getPosition();
//
//		shape.scale(3., 1., Position.EAST);
//		assertEquals(pos.getX(), shape.getPosition().getX());
//		assertEquals(pos.getY(), shape.getPosition().getY());
//		assertEquals(3., shape.getUnit());
//	}
//
//	@Test
//	public void testScaleNE() {
//		shape.setPosition(0, 0);
//		shape.setGridStart(0, 0);
//		shape.setGridEnd(10, 10);
//		shape.setUnit(1);
//
//		IPoint pos = shape.getPosition();
//
//		shape.scale(3., 1., Position.NE);
//		assertEquals(pos.getX(), shape.getPosition().getX());
//		assertEquals(pos.getY(), shape.getPosition().getY());
//		assertEquals(3., shape.getUnit());
//	}
//
//	@Test
//	public void testScaleSE() {
//		shape.setPosition(0, 0);
//		shape.setGridStart(0, 0);
//		shape.setGridEnd(10, 10);
//		shape.setUnit(1);
//
//		IPoint pos = shape.getPosition();
//
//		shape.scale(2., 1., Position.SE);
//		assertEquals(pos.getX(), shape.getPosition().getX());
//		assertEquals(pos.getY()+IShape.PPC*10., shape.getPosition().getY());
//		assertEquals(2., shape.getUnit());
//	}
//
//	@Test
//	public void testScaleNorth() {
//		shape.setPosition(0, 0);
//		shape.setGridStart(0, 0);
//		shape.setGridEnd(10, 10);
//		shape.setUnit(1);
//
//		IPoint pos = shape.getPosition();
//
//		shape.scale(3., 2., Position.NORTH);
//		assertEquals(pos.getX(), shape.getPosition().getX());
//		assertEquals(pos.getY(), shape.getPosition().getY());
//		assertEquals(2., shape.getUnit());
//	}
//
//
//	@Test
//	public void testScaleSouth() {
//		shape.setPosition(0, 0);
//		shape.setGridStart(0, 0);
//		shape.setGridEnd(10, 10);
//		shape.setUnit(1);
//
//		IPoint pos = shape.getPosition();
//
//		shape.scale(3., 2., Position.SOUTH);
//		assertEquals(pos.getX(), shape.getPosition().getX());
//		assertEquals(pos.getY()+IShape.PPC*10., shape.getPosition().getY());
//		assertEquals(2., shape.getUnit());
//	}
//
//
//	@Test
//	public void testScaleSW() {
//		shape.setPosition(0, 0);
//		shape.setGridStart(0, 0);
//		shape.setGridEnd(10, 10);
//		shape.setUnit(1);
//
//		IPoint pos = shape.getPosition();
//
//		shape.scale(2., 3., Position.SW);
//		assertEquals(pos.getX()-IShape.PPC*10., shape.getPosition().getX());
//		assertEquals(pos.getY()+IShape.PPC*10., shape.getPosition().getY());
//		assertEquals(2., shape.getUnit());
//	}
//
//
//	@Test
//	public void testScaleWest() {
//		shape.setPosition(0, 0);
//		shape.setGridStart(0, 0);
//		shape.setGridEnd(10, 10);
//		shape.setUnit(1);
//
//		IPoint pos = shape.getPosition();
//
//		shape.scale(2., 3., Position.WEST);
//		assertEquals(pos.getX()-IShape.PPC*10., shape.getPosition().getX());
//		assertEquals(pos.getY(), shape.getPosition().getY());
//		assertEquals(2., shape.getUnit());
//	}
//
//
//	@Test
//	public void testScaleNW() {
//		shape.setPosition(0, 0);
//		shape.setGridStart(0, 0);
//		shape.setGridEnd(10, 10);
//		shape.setUnit(1);
//
//		IPoint pos = shape.getPosition();
//
//		shape.scale(2., 3., Position.NW);
//		assertEquals(pos.getX()-IShape.PPC*10., shape.getPosition().getX());
//		assertEquals(pos.getY(), shape.getPosition().getY());
//		assertEquals(2., shape.getUnit());
//	}


	@Override
	@Test
	public void testCopy() {
		super.testCopy();

		shape2.setGridDots(45);
		shape2.setSubGridDots(55);
		shape2.setGridLabelsColour(Color.CYAN);
		shape2.setSubGridColour(Color.GREEN);
		shape2.setUnit(0.6);
		shape2.setGridWidth(12);
		shape2.setSubGridWidth(24);
		shape2.setSubGridDiv(32);

		shape.copy(shape2);

		assertEquals(shape2.getGridDots(), shape.getGridDots());
		assertEquals(shape2.getSubGridDiv(), shape2.getSubGridDiv());
		assertEquals(shape2.getGridLabelsColour(), shape.getGridLabelsColour());
		assertEquals(shape2.getSubGridColour(), shape.getSubGridColour());
		assertEquals(shape2.getUnit(), shape.getUnit());
		assertEquals(shape2.getGridWidth(), shape.getGridWidth());
		assertEquals(shape2.getSubGridWidth(), shape.getSubGridWidth());
		assertEquals(shape2.getSubGridDiv(), shape.getSubGridDiv());
	}


	@Override
	@Test
	public void testIsParametersEquals() {
		super.testIsParametersEquals();

		shape.setGridDots(45);
		assertFalse(shape.isParametersEquals(shape2, true));
		shape2.setGridDots(45);
		assertTrue(shape.isParametersEquals(shape2, true));
		shape.setSubGridDots(55);
		assertFalse(shape.isParametersEquals(shape2, true));
		shape2.setSubGridDots(55);
		assertTrue(shape.isParametersEquals(shape2, true));
		shape.setGridLabelsColour(Color.CYAN);
		assertFalse(shape.isParametersEquals(shape2, true));
		shape2.setGridLabelsColour(Color.CYAN);
		assertTrue(shape.isParametersEquals(shape2, true));
		shape.setSubGridColour(Color.GREEN);
		assertFalse(shape.isParametersEquals(shape2, true));
		shape2.setSubGridColour(Color.GREEN);
		assertTrue(shape.isParametersEquals(shape2, true));
		shape.setUnit(0.6);
		assertFalse(shape.isParametersEquals(shape2, true));
		shape2.setUnit(0.6);
		assertTrue(shape.isParametersEquals(shape2, true));
		shape.setGridWidth(12);
		assertFalse(shape.isParametersEquals(shape2, true));
		shape2.setGridWidth(12);
		assertTrue(shape.isParametersEquals(shape2, true));
		shape.setSubGridWidth(24);
		assertFalse(shape.isParametersEquals(shape2, true));
		shape2.setSubGridWidth(24);
		assertTrue(shape.isParametersEquals(shape2, true));
		shape.setSubGridDiv(32);
		assertFalse(shape.isParametersEquals(shape2, true));
		shape2.setSubGridDiv(32);
		assertTrue(shape.isParametersEquals(shape2, true));
	}
}
