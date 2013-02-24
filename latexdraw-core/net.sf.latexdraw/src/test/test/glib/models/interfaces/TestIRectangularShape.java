package test.glib.models.interfaces;


import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IRectangularShape;

import org.junit.Test;

public abstract class TestIRectangularShape<T extends IRectangularShape> extends TestIPositionShape<T> {
	@Override
	@Test
	public void testCopy() {
		super.testCopy();

		shape2.setPosition(-120., 200.);
		shape2.setWidth(385.);
		shape2.setHeight(300.);
		shape.copy(shape2);

		for(int i=0; i<shape.getPoints().size(); i++)
			assertEquals(shape.getPtAt(i), shape2.getPtAt(i));
	}


	@Test
	public void testHas4Points() {
		assertEquals(shape.getNbPoints(), 4);
	}


	@Test
	public void testGetSetWidth() {
//		shape.setRight(30);
//		shape.setLeft(10);
		assertEquals(20., shape.getWidth());
//		shape.setRight(40);
		assertEquals(30., shape.getWidth());
		shape.setWidth(50);
		assertEquals(50., shape.getWidth());
		shape.setWidth(-10);
		assertEquals(50., shape.getWidth());
		shape.setWidth(0);
		assertEquals(50., shape.getWidth());
		shape.setWidth(Double.NaN);
		assertEquals(50., shape.getWidth());
		shape.setWidth(Double.NEGATIVE_INFINITY);
		assertEquals(50., shape.getWidth());
		shape.setWidth(Double.POSITIVE_INFINITY);
		assertEquals(50., shape.getWidth());
	}


	@Test
	public void testGetSetHeight() {
//		shape.setBottom(30);
//		shape.setTop(10);
		assertEquals(20., shape.getHeight());
//		shape.setBottom(40);
		assertEquals(30., shape.getHeight());
		shape.setHeight(50);
		assertEquals(50., shape.getHeight());
		shape.setHeight(0);
		assertEquals(50., shape.getHeight());
		shape.setHeight(-10);
		assertEquals(50., shape.getHeight());
		shape.setHeight(Double.NaN);
		assertEquals(50., shape.getHeight());
		shape.setHeight(Double.POSITIVE_INFINITY);
		assertEquals(50., shape.getHeight());
		shape.setHeight(Double.NEGATIVE_INFINITY);
		assertEquals(50., shape.getHeight());
	}



	@Override
	@Test
	public void testGetNbPoints() {
		assertEquals(4, shape.getNbPoints());
	}


	@Override
	@Test
	public void testGetPtAt() {
		assertNotNull(shape.getPtAt(0));
		assertNotNull(shape.getPtAt(1));
		assertNotNull(shape.getPtAt(2));
		assertNotNull(shape.getPtAt(3));
		assertNotNull(shape.getPtAt(-1));
		assertNull(shape.getPtAt(4));
		assertNull(shape.getPtAt(-2));
	}


//
//
//	@Override
//	@Test
//	public void testScale() {
//		shape.setPosition(0, 2);
//		shape.setWidth(2);
//		shape.setHeight(2);
//		IPoint tl1 = shape.getTopLeftPoint();
//		IPoint br1 = shape.getBottomRightPoint();
//
//		shape.scale(1.5, 1, Position.WEST);
//		IPoint tl2 = shape.getTopLeftPoint();
//		IPoint br2 = shape.getBottomRightPoint();
//
//		assertEquals((br1.getX()-tl1.getX())*1.5, br2.getX()-tl2.getX());
//		shape.scale(1, 1.5, Position.NORTH);
//		tl2 = shape.getTopLeftPoint();
//		br2 = shape.getBottomRightPoint();
//		assertEquals((br1.getY()-tl1.getY())*1.5, br2.getY()-tl2.getY());
//
//		tl1 = shape.getTopLeftPoint();
//		br1 = shape.getBottomRightPoint();
//		shape.scale(1.5, 1, Position.EAST);
//		tl2 = shape.getTopLeftPoint();
//		br2 = shape.getBottomRightPoint();
//		assertEquals((br1.getX()-tl1.getX())*1.5, br2.getX()-tl2.getX());
//
//		tl1 = shape.getTopLeftPoint();
//		br1 = shape.getBottomRightPoint();
//		shape.scale(1, 1.5, Position.SOUTH);
//		tl2 = shape.getTopLeftPoint();
//		br2 = shape.getBottomRightPoint();
//		assertEquals((br1.getY()-tl1.getY())*1.5, br2.getY()-tl2.getY());
//	}
//
//
//
//
//	@Test
//	public void testScaleNE() {
//		shape.setPosition(0, 2);
//		shape.setWidth(2);
//		shape.setHeight(4);
//		IPoint tl1 = shape.getTopLeftPoint();
//		IPoint br1 = shape.getBottomRightPoint();
//
//		shape.scale(2, 1.5, Position.NE);
//		IPoint tl2 = shape.getTopLeftPoint();
//		IPoint br2 = shape.getBottomRightPoint();
//		assertEquals((br1.getY()-tl1.getY())*1.5, br2.getY()-tl2.getY());
//		assertEquals((br1.getX()-tl1.getX())*2., br2.getX()-tl2.getX());
//	}
//
//
//
//	@Test
//	public void testScaleSE() {
//		shape.setPosition(0, 2);
//		shape.setWidth(2);
//		shape.setHeight(4);
//		IPoint tl1 = shape.getTopLeftPoint();
//		IPoint br1 = shape.getBottomRightPoint();
//
//		shape.scale(2, 1.5, Position.SE);
//		IPoint tl2 = shape.getTopLeftPoint();
//		IPoint br2 = shape.getBottomRightPoint();
//		assertEquals((br1.getY()-tl1.getY())*1.5, br2.getY()-tl2.getY());
//		assertEquals((br1.getX()-tl1.getX())*2., br2.getX()-tl2.getX());
//	}
//
//
//	@Test
//	public void testScaleSW() {
//		shape.setPosition(0, 2);
//		shape.setWidth(2);
//		shape.setWidth(4);
//		IPoint tl1 = shape.getTopLeftPoint();
//		IPoint br1 = shape.getBottomRightPoint();
//
//		shape.scale(2, 1.5, Position.SW);
//		IPoint tl2 = shape.getTopLeftPoint();
//		IPoint br2 = shape.getBottomRightPoint();
//		assertEquals((br1.getY()-tl1.getY())*1.5, br2.getY()-tl2.getY());
//		assertEquals((br1.getX()-tl1.getX())*2., br2.getX()-tl2.getX());
//	}
//
//
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
//		assertEquals((br1.getY()-tl1.getY())*1.5, br2.getY()-tl2.getY());
//		assertEquals((br1.getX()-tl1.getX())*2., br2.getX()-tl2.getX());
//	}


	@Override
	@Test
	public void testGetBottomLeftPoint() {
		shape.setPosition(-5, 0);
		shape.setWidth(10);
		shape.setHeight(20);

		assertEquals(-5., shape.getBottomLeftPoint().getX());
		assertEquals(0., shape.getBottomLeftPoint().getY());
	}


	@Override
	@Test
	public void testGetBottomRightPoint() {
		shape.setPosition(-15, 100);
		shape.setWidth(10);
		shape.setHeight(20);

		assertEquals(-5., shape.getBottomRightPoint().getX());
		assertEquals(100., shape.getBottomRightPoint().getY());
	}


	@Override
	@Test
	public void testGetTopLeftPoint() {
		shape.setPosition(20, 10);
		shape.setWidth(10);
		shape.setHeight(20);

		assertEquals(20., shape.getTopLeftPoint().getX());
		assertEquals(-10., shape.getTopLeftPoint().getY());
	}


	@Override
	@Test
	public void testGetTopRightPoint() {
		shape.setPosition(20, 10);
		shape.setWidth(10);
		shape.setHeight(20);

		assertEquals(30., shape.getTopRightPoint().getX());
		assertEquals(-10., shape.getTopRightPoint().getY());
	}


	@Override
	@Test
	public void testMirrorHorizontal() {
		IPoint pt2 = DrawingTK.getFactory().createPoint(3,1);
		IPoint pt4 = DrawingTK.getFactory().createPoint(1,3);

		shape.setPosition(pt4);
		shape.setWidth(pt2.getX()-pt4.getX());
		shape.setHeight(pt4.getY()-pt2.getY());

		shape.mirrorHorizontal(shape.getGravityCentre());
		assertEquals(3., shape.getPtAt(0).getX());
		assertEquals(1., shape.getPtAt(1).getX());
		assertEquals(1., shape.getPtAt(2).getX());
		assertEquals(3., shape.getPtAt(-1).getX());
		assertEquals(1., shape.getPtAt(0).getY());
		assertEquals(1., shape.getPtAt(1).getY());
		assertEquals(3., shape.getPtAt(2).getY());
		assertEquals(3., shape.getPtAt(-1).getY());
	}


	@Override
	@Test
	public void testMirrorVertical() {
		IPoint pt2 = DrawingTK.getFactory().createPoint(3,1);
		IPoint pt4 = DrawingTK.getFactory().createPoint(1,3);

		shape.setPosition(pt4);
		shape.setWidth(pt2.getX()-pt4.getX());
		shape.setHeight(pt4.getY()-pt2.getY());

		shape.mirrorVertical(shape.getGravityCentre());
		assertEquals(1., shape.getPtAt(0).getX());
		assertEquals(3., shape.getPtAt(1).getX());
		assertEquals(3., shape.getPtAt(2).getX());
		assertEquals(1., shape.getPtAt(-1).getX());
		assertEquals(3., shape.getPtAt(0).getY());
		assertEquals(3., shape.getPtAt(1).getY());
		assertEquals(1., shape.getPtAt(2).getY());
		assertEquals(1., shape.getPtAt(-1).getY());
	}



	@Override
	@Test
	public void testTranslate() {
		IPoint pt2 = DrawingTK.getFactory().createPoint(3,1);
		IPoint pt4 = DrawingTK.getFactory().createPoint(1,3);

		shape.setPosition(pt4);
		shape.setWidth(pt2.getX()-pt4.getX());
		shape.setHeight(pt4.getY()-pt2.getY());

		shape.translate(10, 0);
		assertEquals(11., shape.getPtAt(0).getX());
		assertEquals(13., shape.getPtAt(1).getX());
		assertEquals(13., shape.getPtAt(2).getX());
		assertEquals(11., shape.getPtAt(-1).getX());
		assertEquals(1., shape.getPtAt(0).getY());
		assertEquals(1., shape.getPtAt(1).getY());
		assertEquals(3., shape.getPtAt(2).getY());
		assertEquals(3., shape.getPtAt(-1).getY());

		shape.translate(5, 5);
		assertEquals(16., shape.getPtAt(0).getX());
		assertEquals(18., shape.getPtAt(1).getX());
		assertEquals(18., shape.getPtAt(2).getX());
		assertEquals(16., shape.getPtAt(-1).getX());
		assertEquals(6., shape.getPtAt(0).getY());
		assertEquals(6., shape.getPtAt(1).getY());
		assertEquals(8., shape.getPtAt(2).getY());
		assertEquals(8., shape.getPtAt(-1).getY());

		shape.translate(-5, -5);
		assertEquals(11., shape.getPtAt(0).getX());
		assertEquals(13., shape.getPtAt(1).getX());
		assertEquals(13., shape.getPtAt(2).getX());
		assertEquals(11., shape.getPtAt(-1).getX());
		assertEquals(1., shape.getPtAt(0).getY());
		assertEquals(1., shape.getPtAt(1).getY());
		assertEquals(3., shape.getPtAt(2).getY());
		assertEquals(3., shape.getPtAt(-1).getY());

		shape.translate(-10, 0);
		assertEquals(1., shape.getPtAt(0).getX());
		assertEquals(3., shape.getPtAt(1).getX());
		assertEquals(3., shape.getPtAt(2).getX());
		assertEquals(1., shape.getPtAt(-1).getX());
		assertEquals(1., shape.getPtAt(0).getY());
		assertEquals(1., shape.getPtAt(1).getY());
		assertEquals(3., shape.getPtAt(2).getY());
		assertEquals(3., shape.getPtAt(-1).getY());

		shape.translate(Double.NaN, -5);
		assertEquals(1., shape.getPtAt(0).getX());
		assertEquals(3., shape.getPtAt(1).getX());
		assertEquals(3., shape.getPtAt(2).getX());
		assertEquals(1., shape.getPtAt(-1).getX());
		assertEquals(1., shape.getPtAt(0).getY());
		assertEquals(1., shape.getPtAt(1).getY());
		assertEquals(3., shape.getPtAt(2).getY());
		assertEquals(3., shape.getPtAt(-1).getY());

		shape.translate(1, Double.NaN);
		assertEquals(1., shape.getPtAt(0).getX());
		assertEquals(3., shape.getPtAt(1).getX());
		assertEquals(3., shape.getPtAt(2).getX());
		assertEquals(1., shape.getPtAt(-1).getX());
		assertEquals(1., shape.getPtAt(0).getY());
		assertEquals(1., shape.getPtAt(1).getY());
		assertEquals(3., shape.getPtAt(2).getY());
		assertEquals(3., shape.getPtAt(-1).getY());

		shape.translate(Double.NEGATIVE_INFINITY, -5);
		assertEquals(1., shape.getPtAt(0).getX());
		assertEquals(3., shape.getPtAt(1).getX());
		assertEquals(3., shape.getPtAt(2).getX());
		assertEquals(1., shape.getPtAt(-1).getX());
		assertEquals(1., shape.getPtAt(0).getY());
		assertEquals(1., shape.getPtAt(1).getY());
		assertEquals(3., shape.getPtAt(2).getY());
		assertEquals(3., shape.getPtAt(-1).getY());

		shape.translate(1, Double.NEGATIVE_INFINITY);
		assertEquals(1., shape.getPtAt(0).getX());
		assertEquals(3., shape.getPtAt(1).getX());
		assertEquals(3., shape.getPtAt(2).getX());
		assertEquals(1., shape.getPtAt(-1).getX());
		assertEquals(1., shape.getPtAt(0).getY());
		assertEquals(1., shape.getPtAt(1).getY());
		assertEquals(3., shape.getPtAt(2).getY());
		assertEquals(3., shape.getPtAt(-1).getY());

		shape.translate(Double.POSITIVE_INFINITY, -5);
		assertEquals(1., shape.getPtAt(0).getX());
		assertEquals(3., shape.getPtAt(1).getX());
		assertEquals(3., shape.getPtAt(2).getX());
		assertEquals(1., shape.getPtAt(-1).getX());
		assertEquals(1., shape.getPtAt(0).getY());
		assertEquals(1., shape.getPtAt(1).getY());
		assertEquals(3., shape.getPtAt(2).getY());
		assertEquals(3., shape.getPtAt(-1).getY());

		shape.translate(1, Double.POSITIVE_INFINITY);
		assertEquals(1., shape.getPtAt(0).getX());
		assertEquals(3., shape.getPtAt(1).getX());
		assertEquals(3., shape.getPtAt(2).getX());
		assertEquals(1., shape.getPtAt(-1).getX());
		assertEquals(1., shape.getPtAt(0).getY());
		assertEquals(1., shape.getPtAt(1).getY());
		assertEquals(3., shape.getPtAt(2).getY());
		assertEquals(3., shape.getPtAt(-1).getY());

		shape.translate(0, 0);
		assertEquals(1., shape.getPtAt(0).getX());
		assertEquals(3., shape.getPtAt(1).getX());
		assertEquals(3., shape.getPtAt(2).getX());
		assertEquals(1., shape.getPtAt(-1).getX());
		assertEquals(1., shape.getPtAt(0).getY());
		assertEquals(1., shape.getPtAt(1).getY());
		assertEquals(3., shape.getPtAt(2).getY());
		assertEquals(3., shape.getPtAt(-1).getY());
	}
}
