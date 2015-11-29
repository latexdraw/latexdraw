package test.glib.models.interfaces;

import net.sf.latexdraw.glib.models.interfaces.shape.ICircle;

import org.junit.Test;

import test.HelperTest;

public abstract class TestICircle<T extends ICircle> extends TestISquaredShape<T> {
	//
	// @Override
	// @Test
	// public void testScale() {
	// shape.setPosition(0, 2);
	// shape.setWidth(2);
	// IPoint tl1 = shape.getTopLeftPoint();
	// IPoint br1 = shape.getBottomRightPoint();
	//
	// shape.scale(1.5, 1, Position.EAST);
	// IPoint tl2 = shape.getTopLeftPoint();
	// IPoint br2 = shape.getBottomRightPoint();
	//
	// assertEquals((br1.getX()-tl1.getX())*1.5, br2.getX()-tl2.getX());
	// shape.scale(1, 1.5, Position.SOUTH);
	// tl2 = shape.getTopLeftPoint();
	// br2 = shape.getBottomRightPoint();
	// assertEquals((br1.getY()-tl1.getY())*1.5, br2.getY()-tl2.getY());
	//
	// tl1 = shape.getTopLeftPoint();
	// br1 = shape.getBottomRightPoint();
	// shape.scale(1.5, 1, Position.WEST);
	// tl2 = shape.getTopLeftPoint();
	// br2 = shape.getBottomRightPoint();
	// assertEquals((br1.getX()-tl1.getX())*1.5, br2.getX()-tl2.getX());
	//
	// tl1 = shape.getTopLeftPoint();
	// br1 = shape.getBottomRightPoint();
	// shape.scale(1, 1.5, Position.NORTH);
	// tl2 = shape.getTopLeftPoint();
	// br2 = shape.getBottomRightPoint();
	// assertEquals((br1.getY()-tl1.getY())*1.5, br2.getY()-tl2.getY());
	// }
	//
	//
	//
	// @Override
	// @Test
	// public void testScaleNE() {
	// shape.setPosition(0, 2);
	// shape.setWidth(2);
	// IPoint tl1 = shape.getTopLeftPoint();
	// IPoint br1 = shape.getBottomRightPoint();
	//
	// shape.scale(2, 1.5, Position.NE);
	// IPoint tl2 = shape.getTopLeftPoint();
	// IPoint br2 = shape.getBottomRightPoint();
	// assertEquals((br1.getY()-tl1.getY())*2., br2.getY()-tl2.getY());
	// assertEquals((br1.getX()-tl1.getX())*2., br2.getX()-tl2.getX());
	// }
	//
	//
	//
	// @Override
	// @Test
	// public void testScaleSE() {
	// shape.setPosition(0, 2);
	// shape.setWidth(2);
	// IPoint tl1 = shape.getTopLeftPoint();
	// IPoint br1 = shape.getBottomRightPoint();
	//
	// shape.scale(2, 1.5, Position.SE);
	// IPoint tl2 = shape.getTopLeftPoint();
	// IPoint br2 = shape.getBottomRightPoint();
	// assertEquals((br1.getY()-tl1.getY())*2., br2.getY()-tl2.getY());
	// assertEquals((br1.getX()-tl1.getX())*2., br2.getX()-tl2.getX());
	// }
	//
	//
	// @Override
	// @Test
	// public void testScaleSW() {
	// shape.setPosition(0, 2);
	// shape.setWidth(2);
	// IPoint tl1 = shape.getTopLeftPoint();
	// IPoint br1 = shape.getBottomRightPoint();
	//
	// shape.scale(2, 1.5, Position.SW);
	// IPoint tl2 = shape.getTopLeftPoint();
	// IPoint br2 = shape.getBottomRightPoint();
	// assertEquals((br1.getY()-tl1.getY())*2., br2.getY()-tl2.getY());
	// assertEquals((br1.getX()-tl1.getX())*2., br2.getX()-tl2.getX());
	// }
	//
	//
	// @Override
	// @Test
	// public void testScaleNW() {
	// shape.setPosition(0, 2);
	// shape.setWidth(2);
	// IPoint tl1 = shape.getTopLeftPoint();
	// IPoint br1 = shape.getBottomRightPoint();
	//
	// shape.scale(2, 1.5, Position.NW);
	// IPoint tl2 = shape.getTopLeftPoint();
	// IPoint br2 = shape.getBottomRightPoint();
	// assertEquals((br1.getY()-tl1.getY())*2., br2.getY()-tl2.getY());
	// assertEquals((br1.getX()-tl1.getX())*2., br2.getX()-tl2.getX());
	// }

	@Override
	@Test
	public void testGetBottomLeftPoint() {
		shape.setPosition(-5, 0);
		shape.setWidth(10);

		HelperTest.assertEqualsDouble(-5., shape.getBottomLeftPoint().getX());
		HelperTest.assertEqualsDouble(0., shape.getBottomLeftPoint().getY());
	}

	@Override
	@Test
	public void testGetBottomRightPoint() {
		shape.setPosition(-15, 100);
		shape.setWidth(10);

		HelperTest.assertEqualsDouble(-5., shape.getBottomRightPoint().getX());
		HelperTest.assertEqualsDouble(100., shape.getBottomRightPoint().getY());
	}

	@Override
	@Test
	public void testGetTopLeftPoint() {
		shape.setPosition(20, 10);
		shape.setWidth(20);

		HelperTest.assertEqualsDouble(20., shape.getTopLeftPoint().getX());
		HelperTest.assertEqualsDouble(-10., shape.getTopLeftPoint().getY());
	}

	@Override
	@Test
	public void testGetTopRightPoint() {
		shape.setPosition(20, 10);
		shape.setWidth(10);

		HelperTest.assertEqualsDouble(30., shape.getTopRightPoint().getX());
		HelperTest.assertEqualsDouble(0., shape.getTopRightPoint().getY());
	}

	@Test
	public void testSetWidth() {
		shape.setPosition(-5, -5);
		shape.setWidth(3);
		HelperTest.assertEqualsDouble(3., shape.getWidth());
		HelperTest.assertEqualsDouble(3., shape.getHeight());
	}
}
