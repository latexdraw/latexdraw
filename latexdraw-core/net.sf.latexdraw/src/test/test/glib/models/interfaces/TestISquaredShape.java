package test.glib.models.interfaces;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.IPoint;
import net.sf.latexdraw.glib.models.interfaces.shape.ISquaredShape;

import org.junit.Test;

import test.HelperTest;

public abstract class TestISquaredShape<T extends ISquaredShape> extends TestIPositionShape<T> {
	@Override
	@Test
	public void testCopy() {
		super.testCopy();

		shape2.setPosition(-120., 200.);
		shape2.setWidth(385.);
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
		HelperTest.assertEqualsDouble(20., shape.getWidth());
		HelperTest.assertEqualsDouble(20., shape.getHeight());
//		shape.setRight(40);
		HelperTest.assertEqualsDouble(30., shape.getWidth());
		HelperTest.assertEqualsDouble(30., shape.getHeight());
		shape.setWidth(50);
		HelperTest.assertEqualsDouble(50., shape.getWidth());
		HelperTest.assertEqualsDouble(50., shape.getHeight());
		shape.setWidth(-10);
		HelperTest.assertEqualsDouble(50., shape.getWidth());
		HelperTest.assertEqualsDouble(50., shape.getHeight());
		shape.setWidth(0);
		HelperTest.assertEqualsDouble(50., shape.getWidth());
		HelperTest.assertEqualsDouble(50., shape.getHeight());
		shape.setWidth(Double.NaN);
		HelperTest.assertEqualsDouble(50., shape.getWidth());
		HelperTest.assertEqualsDouble(50., shape.getHeight());
		shape.setWidth(Double.NEGATIVE_INFINITY);
		HelperTest.assertEqualsDouble(50., shape.getWidth());
		HelperTest.assertEqualsDouble(50., shape.getHeight());
		shape.setWidth(Double.POSITIVE_INFINITY);
		HelperTest.assertEqualsDouble(50., shape.getWidth());
		HelperTest.assertEqualsDouble(50., shape.getHeight());
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
		shape.setWidth(10);

		HelperTest.assertEqualsDouble(20., shape.getTopLeftPoint().getX());
		HelperTest.assertEqualsDouble(-10., shape.getTopLeftPoint().getY());
	}


	@Override
	@Test
	public void testGetTopRightPoint() {
		shape.setPosition(20, 10);
		shape.setWidth(10);

		HelperTest.assertEqualsDouble(30., shape.getTopRightPoint().getX());
		HelperTest.assertEqualsDouble(-10., shape.getTopRightPoint().getY());
	}


	@Override
	@Test
	public void testMirrorHorizontal() {
		IPoint pt2 = ShapeFactory.createPoint(3,1);
		IPoint pt4 = ShapeFactory.createPoint(1,3);

		shape.setPosition(pt4);
		shape.setWidth(pt2.getX()-pt4.getX());

		shape.mirrorHorizontal(shape.getGravityCentre());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(0).getX());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(1).getX());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(2).getX());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(-1).getX());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(0).getY());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(1).getY());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(2).getY());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(-1).getY());
	}


	@Override
	@Test
	public void testMirrorVertical() {
		IPoint pt2 = ShapeFactory.createPoint(3,1);
		IPoint pt4 = ShapeFactory.createPoint(1,3);

		shape.setPosition(pt4);
		shape.setWidth(pt2.getX()-pt4.getX());

		shape.mirrorVertical(shape.getGravityCentre());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(0).getX());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(1).getX());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(2).getX());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(-1).getX());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(0).getY());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(1).getY());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(2).getY());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(-1).getY());
	}



	@Override
	@Test
	public void testTranslate() {
		IPoint pt2 = ShapeFactory.createPoint(3,1);
		IPoint pt4 = ShapeFactory.createPoint(1,3);

		shape.setPosition(pt4);
		shape.setWidth(pt2.getX()-pt4.getX());

		shape.translate(10, 0);
		HelperTest.assertEqualsDouble(11., shape.getPtAt(0).getX());
		HelperTest.assertEqualsDouble(13., shape.getPtAt(1).getX());
		HelperTest.assertEqualsDouble(13., shape.getPtAt(2).getX());
		HelperTest.assertEqualsDouble(11., shape.getPtAt(-1).getX());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(0).getY());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(1).getY());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(2).getY());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(-1).getY());

		shape.translate(5, 5);
		HelperTest.assertEqualsDouble(16., shape.getPtAt(0).getX());
		HelperTest.assertEqualsDouble(18., shape.getPtAt(1).getX());
		HelperTest.assertEqualsDouble(18., shape.getPtAt(2).getX());
		HelperTest.assertEqualsDouble(16., shape.getPtAt(-1).getX());
		HelperTest.assertEqualsDouble(6., shape.getPtAt(0).getY());
		HelperTest.assertEqualsDouble(6., shape.getPtAt(1).getY());
		HelperTest.assertEqualsDouble(8., shape.getPtAt(2).getY());
		HelperTest.assertEqualsDouble(8., shape.getPtAt(-1).getY());

		shape.translate(-5, -5);
		HelperTest.assertEqualsDouble(11., shape.getPtAt(0).getX());
		HelperTest.assertEqualsDouble(13., shape.getPtAt(1).getX());
		HelperTest.assertEqualsDouble(13., shape.getPtAt(2).getX());
		HelperTest.assertEqualsDouble(11., shape.getPtAt(-1).getX());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(0).getY());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(1).getY());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(2).getY());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(-1).getY());

		shape.translate(-10, 0);
		HelperTest.assertEqualsDouble(1., shape.getPtAt(0).getX());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(1).getX());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(2).getX());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(-1).getX());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(0).getY());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(1).getY());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(2).getY());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(-1).getY());

		shape.translate(Double.NaN, -5);
		HelperTest.assertEqualsDouble(1., shape.getPtAt(0).getX());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(1).getX());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(2).getX());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(-1).getX());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(0).getY());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(1).getY());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(2).getY());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(-1).getY());

		shape.translate(1, Double.NaN);
		HelperTest.assertEqualsDouble(1., shape.getPtAt(0).getX());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(1).getX());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(2).getX());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(-1).getX());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(0).getY());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(1).getY());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(2).getY());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(-1).getY());

		shape.translate(Double.NEGATIVE_INFINITY, -5);
		HelperTest.assertEqualsDouble(1., shape.getPtAt(0).getX());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(1).getX());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(2).getX());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(-1).getX());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(0).getY());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(1).getY());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(2).getY());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(-1).getY());

		shape.translate(1, Double.NEGATIVE_INFINITY);
		HelperTest.assertEqualsDouble(1., shape.getPtAt(0).getX());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(1).getX());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(2).getX());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(-1).getX());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(0).getY());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(1).getY());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(2).getY());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(-1).getY());

		shape.translate(Double.POSITIVE_INFINITY, -5);
		HelperTest.assertEqualsDouble(1., shape.getPtAt(0).getX());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(1).getX());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(2).getX());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(-1).getX());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(0).getY());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(1).getY());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(2).getY());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(-1).getY());

		shape.translate(1, Double.POSITIVE_INFINITY);
		HelperTest.assertEqualsDouble(1., shape.getPtAt(0).getX());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(1).getX());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(2).getX());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(-1).getX());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(0).getY());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(1).getY());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(2).getY());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(-1).getY());

		shape.translate(0, 0);
		HelperTest.assertEqualsDouble(1., shape.getPtAt(0).getX());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(1).getX());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(2).getX());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(-1).getX());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(0).getY());
		HelperTest.assertEqualsDouble(1., shape.getPtAt(1).getY());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(2).getY());
		HelperTest.assertEqualsDouble(3., shape.getPtAt(-1).getY());
	}
}
