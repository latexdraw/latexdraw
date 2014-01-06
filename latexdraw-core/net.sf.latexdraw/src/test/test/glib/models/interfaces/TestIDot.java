package test.glib.models.interfaces;


import static org.junit.Assert.assertEquals;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.IDot;
import net.sf.latexdraw.glib.models.interfaces.prop.IDotProp.DotStyle;

import org.junit.Test;

import test.HelperTest;

public abstract class TestIDot<T extends IDot> extends TestIPositionShape<T> {
	@Test
	public void testGetSetDotStyle() {
		shape.setDotStyle(DotStyle.ASTERISK);
		assertEquals(DotStyle.ASTERISK, shape.getDotStyle());
		shape.setDotStyle(DotStyle.BAR);
		assertEquals(DotStyle.BAR, shape.getDotStyle());
		shape.setDotStyle(DotStyle.DIAMOND);
		assertEquals(DotStyle.DIAMOND, shape.getDotStyle());
		shape.setDotStyle(DotStyle.DOT);
		assertEquals(DotStyle.DOT, shape.getDotStyle());
		shape.setDotStyle(DotStyle.FDIAMOND);
		assertEquals(DotStyle.FDIAMOND, shape.getDotStyle());
		shape.setDotStyle(DotStyle.FPENTAGON);
		assertEquals(DotStyle.FPENTAGON, shape.getDotStyle());
		shape.setDotStyle(DotStyle.FSQUARE);
		assertEquals(DotStyle.FSQUARE, shape.getDotStyle());
		shape.setDotStyle(DotStyle.FTRIANGLE);
		assertEquals(DotStyle.FTRIANGLE, shape.getDotStyle());
		shape.setDotStyle(DotStyle.O);
		assertEquals(DotStyle.O, shape.getDotStyle());
		shape.setDotStyle(DotStyle.OPLUS);
		assertEquals(DotStyle.OPLUS, shape.getDotStyle());
		shape.setDotStyle(DotStyle.OTIMES);
		assertEquals(DotStyle.OTIMES, shape.getDotStyle());
		shape.setDotStyle(DotStyle.PENTAGON);
		assertEquals(DotStyle.PENTAGON, shape.getDotStyle());
		shape.setDotStyle(DotStyle.PLUS);
		assertEquals(DotStyle.PLUS, shape.getDotStyle());
		shape.setDotStyle(DotStyle.SQUARE);
		assertEquals(DotStyle.SQUARE, shape.getDotStyle());
		shape.setDotStyle(DotStyle.TRIANGLE);
		assertEquals(DotStyle.TRIANGLE, shape.getDotStyle());
		shape.setDotStyle(DotStyle.X);
		assertEquals(DotStyle.X, shape.getDotStyle());
		shape.setDotStyle(null);
		assertEquals(DotStyle.X, shape.getDotStyle());
	}


	@Test
	public void testGetSetRadius() {
		shape.setRadius(22);
		HelperTest.assertEqualsDouble(22., shape.getRadius());
		shape.setRadius(1);
		HelperTest.assertEqualsDouble(1., shape.getRadius());
		shape.setRadius(0.001);
		HelperTest.assertEqualsDouble(0.001, shape.getRadius());
		shape.setRadius(0);
		HelperTest.assertEqualsDouble(0.001, shape.getRadius());
		shape.setRadius(-0.001);
		HelperTest.assertEqualsDouble(0.001, shape.getRadius());
		shape.setRadius(-1);
		HelperTest.assertEqualsDouble(0.001, shape.getRadius());
		shape.setRadius(Double.NaN);
		HelperTest.assertEqualsDouble(0.001, shape.getRadius());
		shape.setRadius(Double.POSITIVE_INFINITY);
		HelperTest.assertEqualsDouble(0.001, shape.getRadius());
		shape.setRadius(Double.NEGATIVE_INFINITY);
		HelperTest.assertEqualsDouble(0.001, shape.getRadius());
	}



	@Override
	@Test
	public void testCopy() {
		super.testCopy();

		shape2.setDotStyle(DotStyle.DIAMOND);
		shape2.setRadius(31);
		shape.copy(shape2);
		assertEquals(shape2.getDotStyle(), shape.getDotStyle());
		HelperTest.assertEqualsDouble(shape2.getRadius(), shape.getRadius());
	}


	@Override
	public void testGetBottomLeftPoint() {
		//TODO
	}


	@Override
	public void testGetBottomRightPoint() {
		//TODO
	}


	@Override
	public void testGetTopLeftPoint() {
		//TODO
	}


	@Override
	public void testGetTopRightPoint() {
		//TODO
	}


	@Override
	public void testMirrorHorizontal() {
		shape.setPosition(-10, -20);
		shape.mirrorHorizontal(ShapeFactory.factory().createPoint(100, 0));
		HelperTest.assertEqualsDouble(-10., shape.getPosition().getX());
		HelperTest.assertEqualsDouble(-20., shape.getPosition().getY());
		shape.mirrorHorizontal(null);
		HelperTest.assertEqualsDouble(-10., shape.getPosition().getX());
		HelperTest.assertEqualsDouble(-20., shape.getPosition().getY());
		shape.mirrorHorizontal(ShapeFactory.factory().createPoint(Double.NaN, Double.POSITIVE_INFINITY));
		HelperTest.assertEqualsDouble(-10., shape.getPosition().getX());
		HelperTest.assertEqualsDouble(-20., shape.getPosition().getY());
	}


	@Override
	public void testMirrorVertical() {
		shape.setPosition(-10, -20);
		shape.mirrorVertical(ShapeFactory.factory().createPoint(100, 0));
		HelperTest.assertEqualsDouble(-10., shape.getPosition().getX());
		HelperTest.assertEqualsDouble(-20., shape.getPosition().getY());
		shape.mirrorVertical(null);
		HelperTest.assertEqualsDouble(-10., shape.getPosition().getX());
		HelperTest.assertEqualsDouble(-20., shape.getPosition().getY());
		shape.mirrorVertical(ShapeFactory.factory().createPoint(Double.NaN, Double.POSITIVE_INFINITY));
		HelperTest.assertEqualsDouble(-10., shape.getPosition().getX());
		HelperTest.assertEqualsDouble(-20., shape.getPosition().getY());
	}


//	@Override
//	public void testScale() {
//		shape.setPosition(50, 100);
//		shape.setRadius(10);
//		shape.scale(0, 10, Position.EAST);
//		assertEquals(10., shape.getRadius());
//		assertEquals(50., shape.getPosition().getX());
//		assertEquals(100., shape.getPosition().getY());
//		shape.scale(-10, 10, Position.EAST);
//		assertEquals(10., shape.getRadius());
//		assertEquals(50., shape.getPosition().getX());
//		assertEquals(100., shape.getPosition().getY());
//		shape.scale(10, 10, null);
//		assertEquals(10., shape.getRadius());
//		assertEquals(50., shape.getPosition().getX());
//		assertEquals(100., shape.getPosition().getY());
//		shape.scale(10, 0, Position.WEST);
//		assertEquals(10., shape.getRadius());
//		assertEquals(50., shape.getPosition().getX());
//		assertEquals(100., shape.getPosition().getY());
//		shape.scale(10, -10, Position.WEST);
//		assertEquals(10., shape.getRadius());
//		assertEquals(50., shape.getPosition().getX());
//		assertEquals(100., shape.getPosition().getY());
//		shape.scale(10, 0, null);
//		assertEquals(10., shape.getRadius());
//		assertEquals(50., shape.getPosition().getX());
//		assertEquals(100., shape.getPosition().getY());
//	}
//
//
//	public void testScaleEast() {
//		shape.setPosition(50, 100);
//		shape.setRadius(10);
//		shape.scale(2, 3, Position.EAST);
//		assertEquals(20., shape.getRadius());
//		assertEquals(50., shape.getPosition().getX());
//		assertEquals(100., shape.getPosition().getY());
//	}
//
//
//	public void testScaleWest() {
//		shape.setPosition(50, 100);
//		shape.setRadius(10);
//		shape.scale(2, 3, Position.WEST);
//		assertEquals(20., shape.getRadius());
//		assertEquals(50., shape.getPosition().getX());
//		assertEquals(100., shape.getPosition().getY());
//	}
//
//
//	public void testScaleNW() {
//		shape.setPosition(50, 100);
//		shape.setRadius(10);
//		shape.scale(2, 3, Position.NW);
//		assertEquals(20., shape.getRadius());
//		assertEquals(50., shape.getPosition().getX());
//		assertEquals(100., shape.getPosition().getY());
//	}
//
//
//	public void testScaleSW() {
//		shape.setPosition(50, 100);
//		shape.setRadius(10);
//		shape.scale(2, 3, Position.SW);
//		assertEquals(20., shape.getRadius());
//		assertEquals(50., shape.getPosition().getX());
//		assertEquals(100., shape.getPosition().getY());
//	}
//
//
//
//	public void testScaleNE() {
//		shape.setPosition(50, 100);
//		shape.setRadius(10);
//		shape.scale(2, 3, Position.NE);
//		assertEquals(20., shape.getRadius());
//		assertEquals(50., shape.getPosition().getX());
//		assertEquals(100., shape.getPosition().getY());
//	}
//
//
//	public void testScaleSE() {
//		shape.setPosition(50, 100);
//		shape.setRadius(10);
//		shape.scale(2, 3, Position.SE);
//		assertEquals(20., shape.getRadius());
//		assertEquals(50., shape.getPosition().getX());
//		assertEquals(100., shape.getPosition().getY());
//	}
//
//
//
//	public void testScaleNorth() {
//		shape.setPosition(50, 100);
//		shape.setRadius(10);
//		shape.scale(2, 3, Position.NORTH);
//		assertEquals(30., shape.getRadius());
//		assertEquals(50., shape.getPosition().getX());
//		assertEquals(100., shape.getPosition().getY());
//	}
//
//
//
//	public void testScaleSouth() {
//		shape.setPosition(50, 100);
//		shape.setRadius(10);
//		shape.scale(2, 3, Position.SOUTH);
//		assertEquals(30., shape.getRadius());
//		assertEquals(50., shape.getPosition().getX());
//		assertEquals(100., shape.getPosition().getY());
//	}
}
