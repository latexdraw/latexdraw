package test.models.interfaces;

import net.sf.latexdraw.models.interfaces.shape.ITriangle;

import org.junit.Test;

import test.HelperTest;

public abstract class TestITriangle<T extends ITriangle> extends TestIPositionShape<T> {

	@Override
	@Test
	public void testGetBottomLeftPoint() {
		shape.setPosition(10, 20);
		shape.setWidth(10);
		shape.setHeight(10);

		HelperTest.assertEqualsDouble(10., shape.getBottomLeftPoint().getX());
		HelperTest.assertEqualsDouble(20., shape.getBottomLeftPoint().getY());
	}

	@Override
	@Test
	public void testGetBottomRightPoint() {
		shape.setPosition(10, 20);
		shape.setWidth(10);
		shape.setHeight(10);

		HelperTest.assertEqualsDouble(20., shape.getBottomRightPoint().getX());
		HelperTest.assertEqualsDouble(20., shape.getBottomRightPoint().getY());
	}

	@Override
	@Test
	public void testGetTopLeftPoint() {
		shape.setPosition(10, 20);
		shape.setWidth(10);
		shape.setHeight(10);

		HelperTest.assertEqualsDouble(10., shape.getTopLeftPoint().getX());
		HelperTest.assertEqualsDouble(10., shape.getTopLeftPoint().getY());
	}

	@Override
	@Test
	public void testGetTopRightPoint() {
		shape.setPosition(10, 20);
		shape.setWidth(10);
		shape.setHeight(10);

		HelperTest.assertEqualsDouble(20., shape.getTopRightPoint().getX());
		HelperTest.assertEqualsDouble(10., shape.getTopRightPoint().getY());
	}

	@Override
	@Test
	public void testMirrorHorizontal() {
		shape.setPosition(10, 20);
		shape.setWidth(30);
		shape.setHeight(40);
		shape.mirrorHorizontal(shape.getGravityCentre());

		HelperTest.assertEqualsDouble(10., shape.getPosition().getX());
		HelperTest.assertEqualsDouble(20., shape.getPosition().getY());
		HelperTest.assertEqualsDouble(30., shape.getWidth());
		HelperTest.assertEqualsDouble(40., shape.getHeight());
	}

	@Override
	@Test
	public void testMirrorVertical() {
		shape.setPosition(10, 20);
		shape.setWidth(30);
		shape.setHeight(40);
		shape.mirrorVertical(shape.getGravityCentre());

		HelperTest.assertEqualsDouble(10., shape.getPosition().getX());
		HelperTest.assertEqualsDouble(20., shape.getPosition().getY());
		HelperTest.assertEqualsDouble(30., shape.getWidth());
		HelperTest.assertEqualsDouble(40., shape.getHeight());
	}

	//
	//
	// @Override
	// @Test
	// public void testScale() {
	// shape.setPosition(0, 2);
	// shape.setRight(2);
	// shape.setTop(0);
	//
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

	@Override
	@Test
	public void testTranslate() {
		shape.setPosition(0, 2);
		shape.setWidth(3);
		shape.setHeight(2);

		shape.translate(10, 5);
		HelperTest.assertEqualsDouble(10.0, shape.getPosition().getX());
		HelperTest.assertEqualsDouble(7.0, shape.getPosition().getY());
		HelperTest.assertEqualsDouble(3.0, shape.getWidth());
		HelperTest.assertEqualsDouble(2.0, shape.getHeight());
	}
}
