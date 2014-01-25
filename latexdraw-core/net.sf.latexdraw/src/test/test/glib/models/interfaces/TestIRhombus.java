package test.glib.models.interfaces;


import static org.junit.Assert.*;
import net.sf.latexdraw.glib.models.interfaces.shape.IRhombus;

import org.junit.Test;

import test.HelperTest;

public abstract class TestIRhombus<T extends IRhombus> extends TestIPositionShape<T> {
	@Override
	@Test
	public void testGetBottomLeftPoint() {
		shape.setPosition(0, 5);
		shape.setWidth(10);
		shape.setHeight(10);

		assertNotNull(shape.getBottomLeftPoint());
		HelperTest.assertEqualsDouble(0., shape.getBottomLeftPoint().getX());
		HelperTest.assertEqualsDouble(5., shape.getBottomLeftPoint().getY());
	}


	@Override
	@Test
	public void testGetBottomRightPoint() {
		shape.setPosition(0, 5);
		shape.setWidth(10);
		shape.setHeight(10);

		assertNotNull(shape.getBottomRightPoint());
		HelperTest.assertEqualsDouble(10., shape.getBottomRightPoint().getX());
		HelperTest.assertEqualsDouble(5., shape.getBottomRightPoint().getY());
	}



	@Override
	@Test
	public void testGetTopLeftPoint() {
		shape.setPosition(0, 5);
		shape.setWidth(10);
		shape.setHeight(10);

		assertNotNull(shape.getTopLeftPoint());
		HelperTest.assertEqualsDouble(0., shape.getTopLeftPoint().getX());
		HelperTest.assertEqualsDouble(-5., shape.getTopLeftPoint().getY());
	}



	@Override
	@Test
	public void testGetTopRightPoint() {
		shape.setPosition(0, 5);
		shape.setWidth(10);
		shape.setHeight(10);

		assertNotNull(shape.getTopRightPoint());
		HelperTest.assertEqualsDouble(10., shape.getTopRightPoint().getX());
		HelperTest.assertEqualsDouble(-5., shape.getTopRightPoint().getY());
	}



	@Override
	@Test
	public void testMirrorHorizontal() {
		shape.setPosition(0, 5);
		shape.setWidth(10);
		shape.setHeight(10);
		shape.mirrorHorizontal(shape.getGravityCentre());

		HelperTest.assertEqualsDouble(10., shape.getPtAt(0).getX());
		HelperTest.assertEqualsDouble(0., shape.getPtAt(0).getY());
		HelperTest.assertEqualsDouble(5., shape.getPtAt(1).getX());
		HelperTest.assertEqualsDouble(-5., shape.getPtAt(1).getY());
		HelperTest.assertEqualsDouble(5., shape.getPtAt(3).getX());
		HelperTest.assertEqualsDouble(5., shape.getPtAt(3).getY());
		HelperTest.assertEqualsDouble(0., shape.getPtAt(2).getX());
		HelperTest.assertEqualsDouble(0., shape.getPtAt(2).getY());
	}



	@Override
	@Test
	public void testMirrorVertical() {
		shape.setPosition(0, 5);
		shape.setWidth(10);
		shape.setHeight(10);
		shape.mirrorVertical(shape.getGravityCentre());

		HelperTest.assertEqualsDouble(0., shape.getPtAt(0).getX());
		HelperTest.assertEqualsDouble(0., shape.getPtAt(0).getY());
		HelperTest.assertEqualsDouble(5., shape.getPtAt(1).getX());
		HelperTest.assertEqualsDouble(5., shape.getPtAt(1).getY());
		HelperTest.assertEqualsDouble(5., shape.getPtAt(3).getX());
		HelperTest.assertEqualsDouble(-5., shape.getPtAt(3).getY());
		HelperTest.assertEqualsDouble(10., shape.getPtAt(2).getX());
		HelperTest.assertEqualsDouble(0., shape.getPtAt(2).getY());
	}


//
//	@Override
//	@Test
//	public void testScale() {
//		shape.setPosition(0, 2);
//		shape.setRight(2);
//		shape.setTop(0);
//
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


	@Override
	@Test
	public void testTranslate() {
		shape.setPosition(5, 15);
		shape.setWidth(20);
		shape.setHeight(10);
		shape.translate(100, 50);

		assertEquals(105, shape.getPosition().getX(), 0.0);
		assertEquals(65, shape.getPosition().getY(), 0.0);
		assertEquals(20, shape.getWidth(), 0.0);
		assertEquals(10, shape.getHeight(), 0.0);
	}
}
