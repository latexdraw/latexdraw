package test.glib.models.interfaces;


import net.sf.latexdraw.glib.models.interfaces.ICircle;

import org.junit.Test;

public abstract class TestICircle<T extends ICircle> extends TestIEllipse<T> {
	@Test
	@Override
	public void testGetRx() {
		shape.setPosition(10, 5);
		shape.setWidth(20);
		shape.setHeight(15);

		assertEquals(7.5, shape.getRx());
		shape.setHeight(10);
		shape.setWidth(5);

		assertEquals(2.5, shape.getRx());
	}


	@Test
	@Override
	public void testGetRy() {
		shape.setPosition(10, 5);
		shape.setWidth(20);
		shape.setHeight(15);

		assertEquals(7.5, shape.getRy());
		shape.setHeight(10);
		shape.setWidth(5);

		assertEquals(2.5, shape.getRy());
	}

	@Override
	@Test
	public void testSetRx() {
		shape.setPosition(0, 1);
		shape.setWidth(10);

		assertEquals(5., shape.getRx());
	}


	@Test
	@Override
	public void testSetRy() {
		shape.setPosition(0, 1);
		shape.setWidth(10);

		assertEquals(5., shape.getRy());
	}


	@Test
	public void testRxRyEqual() {
		shape.setPosition(0, 1);
		shape.setWidth(1);

		assertEquals(shape.getRy(), shape.getRx());
	}


	public void testSetRxImpliesRy() {
		shape.setPosition(0, 1);
		shape.setRx(23);

		assertEquals(23., shape.getRy());
		assertEquals(23., shape.getA());
		assertEquals(23., shape.getB());
		assertEquals(46., shape.getWidth());
		assertEquals(46., shape.getHeight());

		shape.setRy(25);

		assertEquals(25., shape.getRy());
		assertEquals(25., shape.getA());
		assertEquals(25., shape.getB());
		assertEquals(50., shape.getWidth());
		assertEquals(50., shape.getHeight());
	}


//
//	@Override
//	@Test
//	public void testScale() {
//		shape.setPosition(0, 2);
//		shape.setWidth(2);
//		IPoint tl1 = shape.getTopLeftPoint();
//		IPoint br1 = shape.getBottomRightPoint();
//
//		shape.scale(1.5, 1, Position.EAST);
//		IPoint tl2 = shape.getTopLeftPoint();
//		IPoint br2 = shape.getBottomRightPoint();
//
//		assertEquals((br1.getX()-tl1.getX())*1.5, br2.getX()-tl2.getX());
//		shape.scale(1, 1.5, Position.SOUTH);
//		tl2 = shape.getTopLeftPoint();
//		br2 = shape.getBottomRightPoint();
//		assertEquals((br1.getY()-tl1.getY())*1.5, br2.getY()-tl2.getY());
//
//		tl1 = shape.getTopLeftPoint();
//		br1 = shape.getBottomRightPoint();
//		shape.scale(1.5, 1, Position.WEST);
//		tl2 = shape.getTopLeftPoint();
//		br2 = shape.getBottomRightPoint();
//		assertEquals((br1.getX()-tl1.getX())*1.5, br2.getX()-tl2.getX());
//
//		tl1 = shape.getTopLeftPoint();
//		br1 = shape.getBottomRightPoint();
//		shape.scale(1, 1.5, Position.NORTH);
//		tl2 = shape.getTopLeftPoint();
//		br2 = shape.getBottomRightPoint();
//		assertEquals((br1.getY()-tl1.getY())*1.5, br2.getY()-tl2.getY());
//	}
//
//
//
//	@Override
//	@Test
//	public void testScaleNE() {
//		shape.setPosition(0, 2);
//		shape.setWidth(2);
//		IPoint tl1 = shape.getTopLeftPoint();
//		IPoint br1 = shape.getBottomRightPoint();
//
//		shape.scale(2, 1.5, Position.NE);
//		IPoint tl2 = shape.getTopLeftPoint();
//		IPoint br2 = shape.getBottomRightPoint();
//		assertEquals((br1.getY()-tl1.getY())*2., br2.getY()-tl2.getY());
//		assertEquals((br1.getX()-tl1.getX())*2., br2.getX()-tl2.getX());
//	}
//
//
//
//	@Override
//	@Test
//	public void testScaleSE() {
//		shape.setPosition(0, 2);
//		shape.setWidth(2);
//		IPoint tl1 = shape.getTopLeftPoint();
//		IPoint br1 = shape.getBottomRightPoint();
//
//		shape.scale(2, 1.5, Position.SE);
//		IPoint tl2 = shape.getTopLeftPoint();
//		IPoint br2 = shape.getBottomRightPoint();
//		assertEquals((br1.getY()-tl1.getY())*2., br2.getY()-tl2.getY());
//		assertEquals((br1.getX()-tl1.getX())*2., br2.getX()-tl2.getX());
//	}
//
//
//	@Override
//	@Test
//	public void testScaleSW() {
//		shape.setPosition(0, 2);
//		shape.setWidth(2);
//		IPoint tl1 = shape.getTopLeftPoint();
//		IPoint br1 = shape.getBottomRightPoint();
//
//		shape.scale(2, 1.5, Position.SW);
//		IPoint tl2 = shape.getTopLeftPoint();
//		IPoint br2 = shape.getBottomRightPoint();
//		assertEquals((br1.getY()-tl1.getY())*2., br2.getY()-tl2.getY());
//		assertEquals((br1.getX()-tl1.getX())*2., br2.getX()-tl2.getX());
//	}
//
//
//	@Override
//	@Test
//	public void testScaleNW() {
//		shape.setPosition(0, 2);
//		shape.setWidth(2);
//		IPoint tl1 = shape.getTopLeftPoint();
//		IPoint br1 = shape.getBottomRightPoint();
//
//		shape.scale(2, 1.5, Position.NW);
//		IPoint tl2 = shape.getTopLeftPoint();
//		IPoint br2 = shape.getBottomRightPoint();
//		assertEquals((br1.getY()-tl1.getY())*2., br2.getY()-tl2.getY());
//		assertEquals((br1.getX()-tl1.getX())*2., br2.getX()-tl2.getX());
//	}


	@Override
	@Test
	public void testGetA() {
		shape.setPosition(10, 5);
		shape.setWidth(20);

		assertEquals(10., shape.getA());

		shape.setHeight(15);

		assertEquals(7.5, shape.getA());
	}



	@Override
	@Test
	public void testGetB() {
		shape.setPosition(10, 5);
		shape.setHeight(15);

		assertEquals(7.5, shape.getB());

		shape.setWidth(10);

		assertEquals(5., shape.getB());
	}



	@Override
	@Test
	public void testGetBottomLeftPoint() {
		shape.setPosition(-5, 0);
		shape.setWidth(10);

		assertEquals(-5., shape.getBottomLeftPoint().getX());
		assertEquals(0., shape.getBottomLeftPoint().getY());
	}


	@Override
	@Test
	public void testGetBottomRightPoint() {
		shape.setPosition(-15, 100);
		shape.setWidth(10);

		assertEquals(-5., shape.getBottomRightPoint().getX());
		assertEquals(100., shape.getBottomRightPoint().getY());
	}


	@Override
	@Test
	public void testGetTopLeftPoint() {
		shape.setPosition(20, 10);
		shape.setHeight(20);

		assertEquals(20., shape.getTopLeftPoint().getX());
		assertEquals(-10., shape.getTopLeftPoint().getY());
	}


	@Override
	@Test
	public void testGetTopRightPoint() {
		shape.setPosition(20, 10);
		shape.setWidth(10);

		assertEquals(30., shape.getTopRightPoint().getX());
		assertEquals(0., shape.getTopRightPoint().getY());
	}


	@Test
	public void testSetWidthHeight() {
		shape.setPosition(-5, -5);
		shape.setHeight(2);
		assertEquals(2., shape.getWidth());
		assertEquals(2., shape.getHeight());
		shape.setWidth(3);
		assertEquals(3., shape.getWidth());
		assertEquals(3., shape.getHeight());
	}
}
