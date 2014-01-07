package test.glib.models.interfaces;

import static org.junit.Assert.*;

import java.awt.Color;

import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.IGrid;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;

import org.junit.Test;

import test.HelperTest;


public abstract class TestIGrid<T extends IGrid> extends TestIStandardGrid<T> {
	@Test
	public void testGetStep() {
		shape.setUnit(1);
		HelperTest.assertEqualsDouble(IShape.PPC, shape.getStep());
		shape.setUnit(2);
		HelperTest.assertEqualsDouble(2.*IShape.PPC, shape.getStep());
	}


	@Test
	public void testIsSetXLabelSouth() {
		shape.setXLabelSouth(true);
		assertTrue(shape.isXLabelSouth());
		shape.setXLabelSouth(false);
		assertFalse(shape.isXLabelSouth());
	}


	@Test
	public void testIsSetYLabelWest() {
		shape.setYLabelWest(true);
		assertTrue(shape.isYLabelWest());
		shape.setYLabelWest(false);
		assertFalse(shape.isYLabelWest());
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
		HelperTest.assertEqualsDouble(30., shape.getGridWidth());
		shape.setGridWidth(50);
		HelperTest.assertEqualsDouble(50., shape.getGridWidth());
		shape.setGridWidth(0);
		HelperTest.assertEqualsDouble(50., shape.getGridWidth());
		shape.setGridWidth(-1);
		HelperTest.assertEqualsDouble(50., shape.getGridWidth());
		shape.setGridWidth(Double.NaN);
		HelperTest.assertEqualsDouble(50., shape.getGridWidth());
		shape.setGridWidth(Double.NEGATIVE_INFINITY);
		HelperTest.assertEqualsDouble(50., shape.getGridWidth());
		shape.setGridWidth(Double.POSITIVE_INFINITY);
		HelperTest.assertEqualsDouble(50., shape.getGridWidth());
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
		HelperTest.assertEqualsDouble(30., shape.getSubGridWidth());
		shape.setSubGridWidth(50);
		HelperTest.assertEqualsDouble(50., shape.getSubGridWidth());
		shape.setSubGridWidth(0);
		HelperTest.assertEqualsDouble(50., shape.getSubGridWidth());
		shape.setSubGridWidth(-1);
		HelperTest.assertEqualsDouble(50., shape.getSubGridWidth());
		shape.setSubGridWidth(Double.NaN);
		HelperTest.assertEqualsDouble(50., shape.getSubGridWidth());
		shape.setSubGridWidth(Double.NEGATIVE_INFINITY);
		HelperTest.assertEqualsDouble(50., shape.getSubGridWidth());
		shape.setSubGridWidth(Double.POSITIVE_INFINITY);
		HelperTest.assertEqualsDouble(50., shape.getSubGridWidth());
	}

	@Test
	public void testGetSetUnit() {
		shape.setUnit(30);
		HelperTest.assertEqualsDouble(30., shape.getUnit());
		shape.setUnit(50);
		HelperTest.assertEqualsDouble(50., shape.getUnit());
		shape.setUnit(0);
		HelperTest.assertEqualsDouble(50., shape.getUnit());
		shape.setUnit(-1);
		HelperTest.assertEqualsDouble(50., shape.getUnit());
		shape.setUnit(Double.NaN);
		HelperTest.assertEqualsDouble(50., shape.getUnit());
		shape.setUnit(Double.NEGATIVE_INFINITY);
		HelperTest.assertEqualsDouble(50., shape.getUnit());
		shape.setUnit(Double.POSITIVE_INFINITY);
		HelperTest.assertEqualsDouble(50., shape.getUnit());
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
		shape.setXLabelSouth(false);
		shape.setYLabelWest(false);

		IGrid g2 = (IGrid)shape.duplicate();

		assertEquals(g2.getGridDots(), shape.getGridDots());
		assertEquals(g2.getSubGridDiv(), shape2.getSubGridDiv());
		assertEquals(g2.getGridLabelsColour(), shape.getGridLabelsColour());
		assertEquals(g2.getSubGridColour(), shape.getSubGridColour());
		HelperTest.assertEqualsDouble(g2.getUnit(), shape.getUnit());
		HelperTest.assertEqualsDouble(g2.getGridWidth(), shape.getGridWidth());
		HelperTest.assertEqualsDouble(g2.getSubGridWidth(), shape.getSubGridWidth());
		assertEquals(g2.getSubGridDiv(), shape.getSubGridDiv());
		assertFalse(g2.isXLabelSouth());
		assertFalse(g2.isYLabelWest());
	}


	@Override@Test
	public void testGetBottomLeftPoint() {
		shape.setPosition(10, 20);
		HelperTest.assertEqualsDouble(10., shape.getBottomLeftPoint().getX());
		HelperTest.assertEqualsDouble(20., shape.getBottomLeftPoint().getY());
		shape.setPosition(-10, -20);
		HelperTest.assertEqualsDouble(-10., shape.getBottomLeftPoint().getX());
		HelperTest.assertEqualsDouble(-20., shape.getBottomLeftPoint().getY());
	}


	@Override@Test
	public void testGetBottomRightPoint() {
		shape.setPosition(0, 0);
		shape.setGridStart(-200, -100);
		shape.setGridEnd(50, 75);
		shape.setUnit(2);

		HelperTest.assertEqualsDouble(2.*IShape.PPC*250., shape.getBottomRightPoint().getX());
		HelperTest.assertEqualsDouble(0., shape.getBottomRightPoint().getY());
	}


	@Override@Test
	public void testGetTopLeftPoint() {
		shape.setPosition(0, 0);
		shape.setGridStart(-200, -100);
		shape.setGridEnd(50, 75);
		shape.setUnit(2);

		HelperTest.assertEqualsDouble(0., shape.getTopLeftPoint().getX());
		HelperTest.assertEqualsDouble(-2.*IShape.PPC*175., shape.getTopLeftPoint().getY());
	}


	@Override@Test
	public void testGetTopRightPoint() {
		shape.setPosition(0, 0);
		shape.setGridStart(-200, -100);
		shape.setGridEnd(50, 75);
		shape.setUnit(2);

		HelperTest.assertEqualsDouble(2.*IShape.PPC*250., shape.getTopRightPoint().getX());
		HelperTest.assertEqualsDouble(-2.*IShape.PPC*175., shape.getTopRightPoint().getY());
	}


	@Override@Test
	public void testMirrorHorizontal() {
		shape.setPosition(0, 0);
		shape.setGridStart(0, 0);
		shape.setGridEnd(10, 10);
		shape.setUnit(1);

		shape.mirrorHorizontal(ShapeFactory.createPoint(IShape.PPC*10., 0.));
		HelperTest.assertEqualsDouble(IShape.PPC*10., shape.getPosition().getX());
		HelperTest.assertEqualsDouble(0., shape.getPosition().getY());
	}


	@Override@Test
	public void testMirrorVertical() {
		shape.setPosition(0, 0);
		shape.setGridStart(0, 0);
		shape.setGridEnd(10, 10);
		shape.setUnit(1);

		shape.mirrorVertical(ShapeFactory.createPoint(0., -IShape.PPC*10.));
		HelperTest.assertEqualsDouble(0., shape.getPosition().getX());
		HelperTest.assertEqualsDouble(-IShape.PPC*10., shape.getPosition().getY());
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
		shape2.setXLabelSouth(false);
		shape2.setYLabelWest(false);

		shape.copy(shape2);

		assertEquals(shape2.getGridDots(), shape.getGridDots());
		assertEquals(shape2.getSubGridDiv(), shape2.getSubGridDiv());
		assertEquals(shape2.getGridLabelsColour(), shape.getGridLabelsColour());
		assertEquals(shape2.getSubGridColour(), shape.getSubGridColour());
		HelperTest.assertEqualsDouble(shape2.getUnit(), shape.getUnit());
		HelperTest.assertEqualsDouble(shape2.getGridWidth(), shape.getGridWidth());
		HelperTest.assertEqualsDouble(shape2.getSubGridWidth(), shape.getSubGridWidth());
		assertEquals(shape2.getSubGridDiv(), shape.getSubGridDiv());
		assertFalse(shape2.isXLabelSouth());
		assertFalse(shape2.isYLabelWest());
	}
}
