package test.models.interfaces;

import net.sf.latexdraw.models.interfaces.shape.IRhombus;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public abstract class TestIRhombus<T extends IRhombus> extends TestIPositionShape<T> {
	@Override
	@Test
	public void testGetBottomLeftPoint() {
		shape.setPosition(0, 5);
		shape.setWidth(10);
		shape.setHeight(10);

		assertNotNull(shape.getBottomLeftPoint());
		assertEqualsDouble(0., shape.getBottomLeftPoint().getX());
		assertEqualsDouble(5., shape.getBottomLeftPoint().getY());
	}

	@Override
	@Test
	public void testGetBottomRightPoint() {
		shape.setPosition(0, 5);
		shape.setWidth(10);
		shape.setHeight(10);

		assertNotNull(shape.getBottomRightPoint());
		assertEqualsDouble(10., shape.getBottomRightPoint().getX());
		assertEqualsDouble(5., shape.getBottomRightPoint().getY());
	}

	@Override
	@Test
	public void testGetTopLeftPoint() {
		shape.setPosition(0, 5);
		shape.setWidth(10);
		shape.setHeight(10);

		assertNotNull(shape.getTopLeftPoint());
		assertEqualsDouble(0., shape.getTopLeftPoint().getX());
		assertEqualsDouble(-5., shape.getTopLeftPoint().getY());
	}

	@Override
	@Test
	public void testGetTopRightPoint() {
		shape.setPosition(0, 5);
		shape.setWidth(10);
		shape.setHeight(10);

		assertNotNull(shape.getTopRightPoint());
		assertEqualsDouble(10., shape.getTopRightPoint().getX());
		assertEqualsDouble(-5., shape.getTopRightPoint().getY());
	}

	@Override
	@Test
	public void testMirrorHorizontal() {
		shape.setPosition(15, 5);
		shape.setWidth(10);
		shape.setHeight(20);
		shape.mirrorHorizontal(shape.getGravityCentre());

		assertEquals(15., shape.getPosition().getX(), 0.001);
		assertEquals(5., shape.getPosition().getY(), 0.001);
		assertEquals(10., shape.getWidth(), 0.001);
		assertEquals(20., shape.getHeight(), 0.001);
		assertEquals(0., shape.getRotationAngle(), 0.001);
	}

	@Override
	@Test
	public void testMirrorVertical() {
		shape.setPosition(15, 5);
		shape.setWidth(10);
		shape.setHeight(20);
		shape.mirrorVertical(shape.getGravityCentre());

		assertEquals(15., shape.getPosition().getX(), 0.001);
		assertEquals(5., shape.getPosition().getY(), 0.001);
		assertEquals(10., shape.getWidth(), 0.001);
		assertEquals(20., shape.getHeight(), 0.001);
		assertEquals(0., shape.getRotationAngle(), 0.001);
	}

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
