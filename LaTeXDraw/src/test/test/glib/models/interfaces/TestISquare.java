package test.glib.models.interfaces;


import net.sf.latexdraw.glib.models.interfaces.ISquare;

import org.junit.Test;

public abstract class TestISquare<T extends ISquare> extends TestIRectangle<T> {
	@Override
	@Test
	public void testGetBottomRightPoint() {
		shape.setPosition(-15, 100);
		shape.setWidth(10);

		assertEquals(-5., shape.getBottomRightPoint().getX());
		assertEquals(100., shape.getBottomRightPoint().getY());
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
