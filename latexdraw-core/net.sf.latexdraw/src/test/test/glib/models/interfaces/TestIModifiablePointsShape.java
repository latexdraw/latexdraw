package test.glib.models.interfaces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IModifiablePointsShape;
import net.sf.latexdraw.models.interfaces.shape.IPoint;

import org.junit.Test;

import test.HelperTest;

public abstract class TestIModifiablePointsShape<T extends IModifiablePointsShape> extends TestIShape<T> {
	// @Override
	// @Test
	// public void testScale() {
	// IPoint pt1 = DrawingTK.getFactory().createPoint(0,0);
	// IPoint pt2 = DrawingTK.getFactory().createPoint(2,0);
	// IPoint pt3 = DrawingTK.getFactory().createPoint(2,2);
	// IPoint pt4 = DrawingTK.getFactory().createPoint(0,2);
	// shape.addPoint(pt1);
	// shape.addPoint(pt2);
	// shape.addPoint(pt3);
	// shape.addPoint(pt4);
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
	public void testMirrorHorizontal() {
		IPoint pt1 = ShapeFactory.createPoint(1, 1);
		IPoint pt2 = ShapeFactory.createPoint(3, 1);
		IPoint pt3 = ShapeFactory.createPoint(3, 3);
		IPoint pt4 = ShapeFactory.createPoint(1, 3);
		shape.addPoint(pt1);
		shape.addPoint(pt2);
		shape.addPoint(pt3);
		shape.addPoint(pt4);
		shape.mirrorHorizontal(shape.getGravityCentre());
		HelperTest.assertEqualsDouble(3., pt1.getX());
		HelperTest.assertEqualsDouble(1., pt2.getX());
		HelperTest.assertEqualsDouble(1., pt3.getX());
		HelperTest.assertEqualsDouble(3., pt4.getX());
		HelperTest.assertEqualsDouble(1., pt1.getY());
		HelperTest.assertEqualsDouble(1., pt2.getY());
		HelperTest.assertEqualsDouble(3., pt3.getY());
		HelperTest.assertEqualsDouble(3., pt4.getY());
	}

	@Override
	@Test
	public void testMirrorVertical() {
		IPoint pt1 = ShapeFactory.createPoint(1, 1);
		IPoint pt2 = ShapeFactory.createPoint(3, 1);
		IPoint pt3 = ShapeFactory.createPoint(3, 3);
		IPoint pt4 = ShapeFactory.createPoint(1, 3);
		shape.addPoint(pt1);
		shape.addPoint(pt2);
		shape.addPoint(pt3);
		shape.addPoint(pt4);
		shape.mirrorVertical(shape.getGravityCentre());
		HelperTest.assertEqualsDouble(1., pt1.getX());
		HelperTest.assertEqualsDouble(3., pt2.getX());
		HelperTest.assertEqualsDouble(3., pt3.getX());
		HelperTest.assertEqualsDouble(1., pt4.getX());
		HelperTest.assertEqualsDouble(3., pt1.getY());
		HelperTest.assertEqualsDouble(3., pt2.getY());
		HelperTest.assertEqualsDouble(1., pt3.getY());
		HelperTest.assertEqualsDouble(1., pt4.getY());
	}

	@Override
	@Test
	public void testTranslate() {
		IPoint pt1 = ShapeFactory.createPoint(1, 1);
		IPoint pt2 = ShapeFactory.createPoint(3, 1);
		IPoint pt3 = ShapeFactory.createPoint(3, 3);
		IPoint pt4 = ShapeFactory.createPoint(1, 3);
		shape.addPoint(pt1);
		shape.addPoint(pt2);
		shape.addPoint(pt3);
		shape.addPoint(pt4);
		shape.translate(10, 0);
		HelperTest.assertEqualsDouble(11., pt1.getX());
		HelperTest.assertEqualsDouble(13., pt2.getX());
		HelperTest.assertEqualsDouble(13., pt3.getX());
		HelperTest.assertEqualsDouble(11., pt4.getX());
		HelperTest.assertEqualsDouble(1., pt1.getY());
		HelperTest.assertEqualsDouble(1., pt2.getY());
		HelperTest.assertEqualsDouble(3., pt3.getY());
		HelperTest.assertEqualsDouble(3., pt4.getY());

		shape.translate(5, 5);
		HelperTest.assertEqualsDouble(16., pt1.getX());
		HelperTest.assertEqualsDouble(18., pt2.getX());
		HelperTest.assertEqualsDouble(18., pt3.getX());
		HelperTest.assertEqualsDouble(16., pt4.getX());
		HelperTest.assertEqualsDouble(6., pt1.getY());
		HelperTest.assertEqualsDouble(6., pt2.getY());
		HelperTest.assertEqualsDouble(8., pt3.getY());
		HelperTest.assertEqualsDouble(8., pt4.getY());

		shape.translate(-5, -5);
		HelperTest.assertEqualsDouble(11., pt1.getX());
		HelperTest.assertEqualsDouble(13., pt2.getX());
		HelperTest.assertEqualsDouble(13., pt3.getX());
		HelperTest.assertEqualsDouble(11., pt4.getX());
		HelperTest.assertEqualsDouble(1., pt1.getY());
		HelperTest.assertEqualsDouble(1., pt2.getY());
		HelperTest.assertEqualsDouble(3., pt3.getY());
		HelperTest.assertEqualsDouble(3., pt4.getY());

		shape.translate(-10, 0);
		HelperTest.assertEqualsDouble(1., pt1.getX());
		HelperTest.assertEqualsDouble(3., pt2.getX());
		HelperTest.assertEqualsDouble(3., pt3.getX());
		HelperTest.assertEqualsDouble(1., pt4.getX());
		HelperTest.assertEqualsDouble(1., pt1.getY());
		HelperTest.assertEqualsDouble(1., pt2.getY());
		HelperTest.assertEqualsDouble(3., pt3.getY());
		HelperTest.assertEqualsDouble(3., pt4.getY());

		shape.translate(Double.NaN, -5);
		HelperTest.assertEqualsDouble(1., pt1.getX());
		HelperTest.assertEqualsDouble(3., pt2.getX());
		HelperTest.assertEqualsDouble(3., pt3.getX());
		HelperTest.assertEqualsDouble(1., pt4.getX());
		HelperTest.assertEqualsDouble(1., pt1.getY());
		HelperTest.assertEqualsDouble(1., pt2.getY());
		HelperTest.assertEqualsDouble(3., pt3.getY());
		HelperTest.assertEqualsDouble(3., pt4.getY());

		shape.translate(1, Double.NaN);
		HelperTest.assertEqualsDouble(1., pt1.getX());
		HelperTest.assertEqualsDouble(3., pt2.getX());
		HelperTest.assertEqualsDouble(3., pt3.getX());
		HelperTest.assertEqualsDouble(1., pt4.getX());
		HelperTest.assertEqualsDouble(1., pt1.getY());
		HelperTest.assertEqualsDouble(1., pt2.getY());
		HelperTest.assertEqualsDouble(3., pt3.getY());
		HelperTest.assertEqualsDouble(3., pt4.getY());

		shape.translate(Double.NEGATIVE_INFINITY, -5);
		HelperTest.assertEqualsDouble(1., pt1.getX());
		HelperTest.assertEqualsDouble(3., pt2.getX());
		HelperTest.assertEqualsDouble(3., pt3.getX());
		HelperTest.assertEqualsDouble(1., pt4.getX());
		HelperTest.assertEqualsDouble(1., pt1.getY());
		HelperTest.assertEqualsDouble(1., pt2.getY());
		HelperTest.assertEqualsDouble(3., pt3.getY());
		HelperTest.assertEqualsDouble(3., pt4.getY());

		shape.translate(1, Double.NEGATIVE_INFINITY);
		HelperTest.assertEqualsDouble(1., pt1.getX());
		HelperTest.assertEqualsDouble(3., pt2.getX());
		HelperTest.assertEqualsDouble(3., pt3.getX());
		HelperTest.assertEqualsDouble(1., pt4.getX());
		HelperTest.assertEqualsDouble(1., pt1.getY());
		HelperTest.assertEqualsDouble(1., pt2.getY());
		HelperTest.assertEqualsDouble(3., pt3.getY());
		HelperTest.assertEqualsDouble(3., pt4.getY());

		shape.translate(Double.POSITIVE_INFINITY, -5);
		HelperTest.assertEqualsDouble(1., pt1.getX());
		HelperTest.assertEqualsDouble(3., pt2.getX());
		HelperTest.assertEqualsDouble(3., pt3.getX());
		HelperTest.assertEqualsDouble(1., pt4.getX());
		HelperTest.assertEqualsDouble(1., pt1.getY());
		HelperTest.assertEqualsDouble(1., pt2.getY());
		HelperTest.assertEqualsDouble(3., pt3.getY());
		HelperTest.assertEqualsDouble(3., pt4.getY());

		shape.translate(1, Double.POSITIVE_INFINITY);
		HelperTest.assertEqualsDouble(1., pt1.getX());
		HelperTest.assertEqualsDouble(3., pt2.getX());
		HelperTest.assertEqualsDouble(3., pt3.getX());
		HelperTest.assertEqualsDouble(1., pt4.getX());
		HelperTest.assertEqualsDouble(1., pt1.getY());
		HelperTest.assertEqualsDouble(1., pt2.getY());
		HelperTest.assertEqualsDouble(3., pt3.getY());
		HelperTest.assertEqualsDouble(3., pt4.getY());

		shape.translate(0, 0);
		HelperTest.assertEqualsDouble(1., pt1.getX());
		HelperTest.assertEqualsDouble(3., pt2.getX());
		HelperTest.assertEqualsDouble(3., pt3.getX());
		HelperTest.assertEqualsDouble(1., pt4.getX());
		HelperTest.assertEqualsDouble(1., pt1.getY());
		HelperTest.assertEqualsDouble(1., pt2.getY());
		HelperTest.assertEqualsDouble(3., pt3.getY());
		HelperTest.assertEqualsDouble(3., pt4.getY());
	}

	@Override
	@Test
	public void testGetGravityCentre() {
		super.testGetGravityCentre();

		IPoint pt1 = ShapeFactory.createPoint(0, 0);
		IPoint pt2 = ShapeFactory.createPoint(2, 0);
		IPoint pt3 = ShapeFactory.createPoint(2, 2);
		IPoint pt4 = ShapeFactory.createPoint(0, 2);

		assertNotNull(shape.getGravityCentre());

		shape.getPoints().clear();
		shape.addPoint(pt1);
		shape.addPoint(pt2);
		shape.addPoint(pt3);
		shape.addPoint(pt4);
		assertTrue(shape.getGravityCentre().equals(ShapeFactory.createPoint(1, 1)));
	}

	@Test
	public void testAddPoint() {
		IPoint pt = ShapeFactory.createPoint();
		int size = shape.getNbPoints();

		shape.addPoint(null);
		assertEquals(size, shape.getPoints().size());

		shape.addPoint(pt);
		assertEquals(pt, shape.getPoints().get(shape.getPoints().size() - 1));
		assertEquals(size + 1, shape.getPoints().size());

		pt = ShapeFactory.createPoint(1, 1);
		shape.addPoint(pt);
		assertEquals(pt, shape.getPoints().get(shape.getPoints().size() - 1));
		assertEquals(size + 2, shape.getPoints().size());

		shape.getPoints().remove(shape.getPoints().size() - 1);
		shape.getPoints().remove(shape.getPoints().size() - 1);
	}

	@Test
	public void testAddPointAt() {
		IPoint pt = ShapeFactory.createPoint();
		int size = shape.getNbPoints();

		shape.addPoint(null, 0);
		assertEquals(size, shape.getPoints().size());

		shape.addPoint(null, -1);
		assertEquals(size, shape.getPoints().size());

		shape.addPoint(null, 7863);
		assertEquals(size, shape.getPoints().size());

		shape.addPoint(pt, Integer.MAX_VALUE);
		assertEquals(size, shape.getPoints().size());

		shape.addPoint(pt, -2);
		assertEquals(size, shape.getPoints().size());

		shape.addPoint(pt, shape.getPoints().size() + 1);
		assertEquals(size, shape.getPoints().size());

		shape.addPoint(pt, 0);
		assertEquals(pt, shape.getPoints().get(0));
		assertEquals(size + 1, shape.getPoints().size());
		shape.removePoint(0);

		shape.addPoint(pt, -1);
		assertEquals(pt, shape.getPoints().get(shape.getPoints().size() - 1));
		assertEquals(size + 1, shape.getPoints().size());
		shape.removePoint(pt);

		shape.addPoint(pt, shape.getPoints().size());
		assertEquals(pt, shape.getPoints().get(shape.getPoints().size() - 1));
		assertEquals(size + 1, shape.getPoints().size());
		shape.removePoint(pt);
	}

	@Test
	public void testRemovePoint() {
		int size = shape.getPoints().size();
		IPoint pt = ShapeFactory.createPoint();

		shape.addPoint(pt);
		assertFalse(shape.removePoint(null));
		assertEquals(size + 1, shape.getPoints().size());
		assertTrue(shape.removePoint(pt));
		assertEquals(size, shape.getPoints().size());
	}

	@Test
	public void testRemovePoint2() {
		int size = shape.getPoints().size();
		IPoint pt = ShapeFactory.createPoint();

		shape.addPoint(pt);
		assertNull(shape.removePoint(Integer.MAX_VALUE));
		assertEquals(size + 1, shape.getPoints().size());
		assertNull(shape.removePoint(Integer.MIN_VALUE));
		assertEquals(size + 1, shape.getPoints().size());
		assertEquals(shape.removePoint(-1), pt);
		assertEquals(size, shape.getPoints().size());
		shape.addPoint(pt);
		assertEquals(shape.removePoint(shape.getPoints().size() - 1), pt);
		assertEquals(size, shape.getPoints().size());
		shape.addPoint(pt, 0);
		assertEquals(shape.removePoint(0), pt);
		assertEquals(size, shape.getPoints().size());
	}

	@Test
	public void testSetPoint() {
		IPoint pt = ShapeFactory.createPoint(1, 0);

		shape.getPoints().clear();
		shape.addPoint(pt);

		assertFalse(shape.setPoint(ShapeFactory.createPoint(), -2));
		assertFalse(shape.setPoint(ShapeFactory.createPoint(), Integer.MIN_VALUE));
		assertFalse(shape.setPoint(ShapeFactory.createPoint(), Integer.MAX_VALUE));
		assertFalse(shape.setPoint(ShapeFactory.createPoint(0, Double.NaN), 0));
		assertFalse(shape.setPoint(ShapeFactory.createPoint(Double.NaN, 0), 0));
		assertFalse(shape.setPoint(ShapeFactory.createPoint(Double.POSITIVE_INFINITY, 0), 0));
		assertFalse(shape.setPoint(ShapeFactory.createPoint(0, Double.POSITIVE_INFINITY), 0));
		assertFalse(shape.setPoint(ShapeFactory.createPoint(Double.POSITIVE_INFINITY, 0), 0));
		assertFalse(shape.setPoint(ShapeFactory.createPoint(0, Double.POSITIVE_INFINITY), 0));
		assertTrue(shape.setPoint(ShapeFactory.createPoint(10, 12), 0));
		HelperTest.assertEqualsDouble(10., pt.getX());
		HelperTest.assertEqualsDouble(12., pt.getY());
	}

	@Test
	public void testSetPoint2() {
		IPoint pt = ShapeFactory.createPoint(1, 0);

		shape.getPoints().clear();
		shape.addPoint(pt);

		assertFalse(shape.setPoint(0, 0, -2));
		assertFalse(shape.setPoint(0, 0, Integer.MIN_VALUE));
		assertFalse(shape.setPoint(0, 0, Integer.MAX_VALUE));
		assertFalse(shape.setPoint(0, Double.NaN, 0));
		assertFalse(shape.setPoint(Double.NaN, 0, 0));
		assertFalse(shape.setPoint(Double.NEGATIVE_INFINITY, 0, 0));
		assertFalse(shape.setPoint(0, Double.NEGATIVE_INFINITY, 0));
		assertFalse(shape.setPoint(Double.POSITIVE_INFINITY, 0, 0));
		assertFalse(shape.setPoint(0, Double.POSITIVE_INFINITY, 0));
		assertTrue(shape.setPoint(10, 12, 0));
		HelperTest.assertEqualsDouble(10., pt.getX());
		HelperTest.assertEqualsDouble(12., pt.getY());
	}

	@Test
	public void testReplacePoint() {
		IPoint pt1 = ShapeFactory.createPoint(2, 0);
		IPoint pt2 = ShapeFactory.createPoint(2, 1);
		IPoint pt3 = ShapeFactory.createPoint(2, 2);
		IPoint pt4 = ShapeFactory.createPoint(2, 3);

		shape.getPoints().clear();
		shape.addPoint(pt1);
		shape.addPoint(pt2);
		shape.addPoint(pt3);

		assertNull(shape.replacePoint(null, 0));
		assertNull(shape.replacePoint(pt2, 0));
		assertEquals(pt1, shape.replacePoint(pt4, 0));
		assertEquals(0, shape.getPoints().indexOf(pt4));
		assertEquals(pt3, shape.replacePoint(pt1, -1));
		assertEquals(shape.getPoints().size() - 1, shape.getPoints().indexOf(pt1));
		assertEquals(pt1, shape.replacePoint(pt3, shape.getPoints().size() - 1));
		assertEquals(shape.getPoints().size() - 1, shape.getPoints().indexOf(pt3));
	}

	@Override
	@Test
	public void testGetTopLeftPoint() {
		IPoint pt1 = ShapeFactory.createPoint(0, 0);
		IPoint pt2 = ShapeFactory.createPoint(2, 0);
		IPoint pt3 = ShapeFactory.createPoint(2, 2);
		IPoint pt4 = ShapeFactory.createPoint(0, 2);

		assertNotNull(shape.getTopLeftPoint());
		shape.getPoints().clear();
		assertNotNull(shape.getTopLeftPoint());
		shape.addPoint(pt1);
		shape.addPoint(pt2);
		shape.addPoint(pt3);
		shape.addPoint(pt4);
		assertTrue(shape.getTopLeftPoint().equals(pt1));
	}

	@Override
	@Test
	public void testGetTopRightPoint() {
		IPoint pt1 = ShapeFactory.createPoint(0, 0);
		IPoint pt2 = ShapeFactory.createPoint(2, 0);
		IPoint pt3 = ShapeFactory.createPoint(2, 2);
		IPoint pt4 = ShapeFactory.createPoint(0, 2);

		assertNotNull(shape.getTopRightPoint());
		shape.getPoints().clear();
		assertNotNull(shape.getTopRightPoint());
		shape.addPoint(pt1);
		shape.addPoint(pt2);
		shape.addPoint(pt3);
		shape.addPoint(pt4);
		assertTrue(shape.getTopRightPoint().equals(pt2));
	}

	@Override
	@Test
	public void testGetBottomRightPoint() {
		IPoint pt1 = ShapeFactory.createPoint(0, 0);
		IPoint pt2 = ShapeFactory.createPoint(2, 0);
		IPoint pt3 = ShapeFactory.createPoint(2, 2);
		IPoint pt4 = ShapeFactory.createPoint(0, 2);

		assertNotNull(shape.getBottomRightPoint());
		shape.getPoints().clear();
		assertNotNull(shape.getBottomRightPoint());
		shape.addPoint(pt1);
		shape.addPoint(pt2);
		shape.addPoint(pt3);
		shape.addPoint(pt4);
		assertTrue(shape.getBottomRightPoint().equals(pt3));
	}

	@Override
	@Test
	public void testGetBottomLeftPoint() {
		IPoint pt1 = ShapeFactory.createPoint(0, 0);
		IPoint pt2 = ShapeFactory.createPoint(2, 0);
		IPoint pt3 = ShapeFactory.createPoint(2, 2);
		IPoint pt4 = ShapeFactory.createPoint(0, 2);

		assertNotNull(shape.getBottomLeftPoint());
		shape.getPoints().clear();
		assertNotNull(shape.getBottomLeftPoint());
		shape.addPoint(pt1);
		shape.addPoint(pt2);
		shape.addPoint(pt3);
		shape.addPoint(pt4);
		assertTrue(shape.getBottomLeftPoint().equals(pt4));
	}
}
