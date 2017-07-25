package test.models.interfaces;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IModifiablePointsShape;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public abstract class TestIModifiablePointsShape<T extends IModifiablePointsShape> extends TestIShape<T> {
	@Override
	@Test
	public void testMirrorHorizontal() {
		IPoint pt1 = ShapeFactory.INST.createPoint(1, 1);
		IPoint pt2 = ShapeFactory.INST.createPoint(3, 1);
		IPoint pt3 = ShapeFactory.INST.createPoint(3, 3);
		IPoint pt4 = ShapeFactory.INST.createPoint(1, 3);
		shape.addPoint(pt1);
		shape.addPoint(pt2);
		shape.addPoint(pt3);
		shape.addPoint(pt4);
		shape.mirrorHorizontal(shape.getGravityCentre().getX());
		assertEqualsDouble(3., pt1.getX());
		assertEqualsDouble(1., pt2.getX());
		assertEqualsDouble(1., pt3.getX());
		assertEqualsDouble(3., pt4.getX());
		assertEqualsDouble(1., pt1.getY());
		assertEqualsDouble(1., pt2.getY());
		assertEqualsDouble(3., pt3.getY());
		assertEqualsDouble(3., pt4.getY());
	}

	@Override
	@Test
	public void testMirrorVertical() {
		IPoint pt1 = ShapeFactory.INST.createPoint(1, 1);
		IPoint pt2 = ShapeFactory.INST.createPoint(3, 1);
		IPoint pt3 = ShapeFactory.INST.createPoint(3, 3);
		IPoint pt4 = ShapeFactory.INST.createPoint(1, 3);
		shape.addPoint(pt1);
		shape.addPoint(pt2);
		shape.addPoint(pt3);
		shape.addPoint(pt4);
		shape.mirrorVertical(shape.getGravityCentre().getY());
		assertEqualsDouble(1., pt1.getX());
		assertEqualsDouble(3., pt2.getX());
		assertEqualsDouble(3., pt3.getX());
		assertEqualsDouble(1., pt4.getX());
		assertEqualsDouble(3., pt1.getY());
		assertEqualsDouble(3., pt2.getY());
		assertEqualsDouble(1., pt3.getY());
		assertEqualsDouble(1., pt4.getY());
	}

	@Override
	@Test
	public void testTranslate() {
		IPoint pt1 = ShapeFactory.INST.createPoint(1, 1);
		IPoint pt2 = ShapeFactory.INST.createPoint(3, 1);
		IPoint pt3 = ShapeFactory.INST.createPoint(3, 3);
		IPoint pt4 = ShapeFactory.INST.createPoint(1, 3);
		shape.addPoint(pt1);
		shape.addPoint(pt2);
		shape.addPoint(pt3);
		shape.addPoint(pt4);
		shape.translate(10, 0);
		assertEqualsDouble(11., pt1.getX());
		assertEqualsDouble(13., pt2.getX());
		assertEqualsDouble(13., pt3.getX());
		assertEqualsDouble(11., pt4.getX());
		assertEqualsDouble(1., pt1.getY());
		assertEqualsDouble(1., pt2.getY());
		assertEqualsDouble(3., pt3.getY());
		assertEqualsDouble(3., pt4.getY());

		shape.translate(5, 5);
		assertEqualsDouble(16., pt1.getX());
		assertEqualsDouble(18., pt2.getX());
		assertEqualsDouble(18., pt3.getX());
		assertEqualsDouble(16., pt4.getX());
		assertEqualsDouble(6., pt1.getY());
		assertEqualsDouble(6., pt2.getY());
		assertEqualsDouble(8., pt3.getY());
		assertEqualsDouble(8., pt4.getY());

		shape.translate(-5, -5);
		assertEqualsDouble(11., pt1.getX());
		assertEqualsDouble(13., pt2.getX());
		assertEqualsDouble(13., pt3.getX());
		assertEqualsDouble(11., pt4.getX());
		assertEqualsDouble(1., pt1.getY());
		assertEqualsDouble(1., pt2.getY());
		assertEqualsDouble(3., pt3.getY());
		assertEqualsDouble(3., pt4.getY());

		shape.translate(-10, 0);
		assertEqualsDouble(1., pt1.getX());
		assertEqualsDouble(3., pt2.getX());
		assertEqualsDouble(3., pt3.getX());
		assertEqualsDouble(1., pt4.getX());
		assertEqualsDouble(1., pt1.getY());
		assertEqualsDouble(1., pt2.getY());
		assertEqualsDouble(3., pt3.getY());
		assertEqualsDouble(3., pt4.getY());

		shape.translate(Double.NaN, -5);
		assertEqualsDouble(1., pt1.getX());
		assertEqualsDouble(3., pt2.getX());
		assertEqualsDouble(3., pt3.getX());
		assertEqualsDouble(1., pt4.getX());
		assertEqualsDouble(1., pt1.getY());
		assertEqualsDouble(1., pt2.getY());
		assertEqualsDouble(3., pt3.getY());
		assertEqualsDouble(3., pt4.getY());

		shape.translate(1, Double.NaN);
		assertEqualsDouble(1., pt1.getX());
		assertEqualsDouble(3., pt2.getX());
		assertEqualsDouble(3., pt3.getX());
		assertEqualsDouble(1., pt4.getX());
		assertEqualsDouble(1., pt1.getY());
		assertEqualsDouble(1., pt2.getY());
		assertEqualsDouble(3., pt3.getY());
		assertEqualsDouble(3., pt4.getY());

		shape.translate(Double.NEGATIVE_INFINITY, -5);
		assertEqualsDouble(1., pt1.getX());
		assertEqualsDouble(3., pt2.getX());
		assertEqualsDouble(3., pt3.getX());
		assertEqualsDouble(1., pt4.getX());
		assertEqualsDouble(1., pt1.getY());
		assertEqualsDouble(1., pt2.getY());
		assertEqualsDouble(3., pt3.getY());
		assertEqualsDouble(3., pt4.getY());

		shape.translate(1, Double.NEGATIVE_INFINITY);
		assertEqualsDouble(1., pt1.getX());
		assertEqualsDouble(3., pt2.getX());
		assertEqualsDouble(3., pt3.getX());
		assertEqualsDouble(1., pt4.getX());
		assertEqualsDouble(1., pt1.getY());
		assertEqualsDouble(1., pt2.getY());
		assertEqualsDouble(3., pt3.getY());
		assertEqualsDouble(3., pt4.getY());

		shape.translate(Double.POSITIVE_INFINITY, -5);
		assertEqualsDouble(1., pt1.getX());
		assertEqualsDouble(3., pt2.getX());
		assertEqualsDouble(3., pt3.getX());
		assertEqualsDouble(1., pt4.getX());
		assertEqualsDouble(1., pt1.getY());
		assertEqualsDouble(1., pt2.getY());
		assertEqualsDouble(3., pt3.getY());
		assertEqualsDouble(3., pt4.getY());

		shape.translate(1, Double.POSITIVE_INFINITY);
		assertEqualsDouble(1., pt1.getX());
		assertEqualsDouble(3., pt2.getX());
		assertEqualsDouble(3., pt3.getX());
		assertEqualsDouble(1., pt4.getX());
		assertEqualsDouble(1., pt1.getY());
		assertEqualsDouble(1., pt2.getY());
		assertEqualsDouble(3., pt3.getY());
		assertEqualsDouble(3., pt4.getY());

		shape.translate(0, 0);
		assertEqualsDouble(1., pt1.getX());
		assertEqualsDouble(3., pt2.getX());
		assertEqualsDouble(3., pt3.getX());
		assertEqualsDouble(1., pt4.getX());
		assertEqualsDouble(1., pt1.getY());
		assertEqualsDouble(1., pt2.getY());
		assertEqualsDouble(3., pt3.getY());
		assertEqualsDouble(3., pt4.getY());
	}

	@Override
	@Test
	public void testGetGravityCentre() {
		super.testGetGravityCentre();

		IPoint pt1 = ShapeFactory.INST.createPoint(0, 0);
		IPoint pt2 = ShapeFactory.INST.createPoint(2, 0);
		IPoint pt3 = ShapeFactory.INST.createPoint(2, 2);
		IPoint pt4 = ShapeFactory.INST.createPoint(0, 2);

		assertNotNull(shape.getGravityCentre());

		shape.getPoints().clear();
		shape.addPoint(pt1);
		shape.addPoint(pt2);
		shape.addPoint(pt3);
		shape.addPoint(pt4);
		assertTrue(shape.getGravityCentre().equals(ShapeFactory.INST.createPoint(1, 1)));
	}

	@Test
	public void testAddPoint() {
		IPoint pt = ShapeFactory.INST.createPoint();
		int size = shape.getNbPoints();

		shape.addPoint(null);
		assertEquals(size, shape.getPoints().size());

		shape.addPoint(pt);
		assertEquals(pt, shape.getPoints().get(shape.getPoints().size() - 1));
		assertEquals(size + 1, shape.getPoints().size());

		pt = ShapeFactory.INST.createPoint(1, 1);
		shape.addPoint(pt);
		assertEquals(pt, shape.getPoints().get(shape.getPoints().size() - 1));
		assertEquals(size + 2, shape.getPoints().size());

		shape.getPoints().remove(shape.getPoints().size() - 1);
		shape.getPoints().remove(shape.getPoints().size() - 1);
	}

	@Test
	public void testAddPointAt() {
		IPoint pt = ShapeFactory.INST.createPoint();
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
		IPoint pt = ShapeFactory.INST.createPoint();

		shape.addPoint(pt);
		assertFalse(shape.removePoint(null));
		assertEquals(size + 1, shape.getPoints().size());
		assertTrue(shape.removePoint(pt));
		assertEquals(size, shape.getPoints().size());
	}

	@Test
	public void testRemovePoint2() {
		int size = shape.getPoints().size();
		IPoint pt = ShapeFactory.INST.createPoint();

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
		IPoint pt = ShapeFactory.INST.createPoint(1, 0);

		shape.getPoints().clear();
		shape.addPoint(pt);

		assertFalse(shape.setPoint(ShapeFactory.INST.createPoint(), -2));
		assertFalse(shape.setPoint(ShapeFactory.INST.createPoint(), Integer.MIN_VALUE));
		assertFalse(shape.setPoint(ShapeFactory.INST.createPoint(), Integer.MAX_VALUE));
		assertFalse(shape.setPoint(ShapeFactory.INST.createPoint(0, Double.NaN), 0));
		assertFalse(shape.setPoint(ShapeFactory.INST.createPoint(Double.NaN, 0), 0));
		assertFalse(shape.setPoint(ShapeFactory.INST.createPoint(Double.POSITIVE_INFINITY, 0), 0));
		assertFalse(shape.setPoint(ShapeFactory.INST.createPoint(0, Double.POSITIVE_INFINITY), 0));
		assertFalse(shape.setPoint(ShapeFactory.INST.createPoint(Double.POSITIVE_INFINITY, 0), 0));
		assertFalse(shape.setPoint(ShapeFactory.INST.createPoint(0, Double.POSITIVE_INFINITY), 0));
		assertTrue(shape.setPoint(ShapeFactory.INST.createPoint(10, 12), 0));
		assertEqualsDouble(10., pt.getX());
		assertEqualsDouble(12., pt.getY());
	}

	@Test
	public void testSetPoint2() {
		IPoint pt = ShapeFactory.INST.createPoint(1, 0);

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
		assertEqualsDouble(10., pt.getX());
		assertEqualsDouble(12., pt.getY());
	}

	@Test
	public void testReplacePoint() {
		IPoint pt1 = ShapeFactory.INST.createPoint(2, 0);
		IPoint pt2 = ShapeFactory.INST.createPoint(2, 1);
		IPoint pt3 = ShapeFactory.INST.createPoint(2, 2);
		IPoint pt4 = ShapeFactory.INST.createPoint(2, 3);

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
		IPoint pt1 = ShapeFactory.INST.createPoint(0, 0);
		IPoint pt2 = ShapeFactory.INST.createPoint(2, 0);
		IPoint pt3 = ShapeFactory.INST.createPoint(2, 2);
		IPoint pt4 = ShapeFactory.INST.createPoint(0, 2);

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
		IPoint pt1 = ShapeFactory.INST.createPoint(0, 0);
		IPoint pt2 = ShapeFactory.INST.createPoint(2, 0);
		IPoint pt3 = ShapeFactory.INST.createPoint(2, 2);
		IPoint pt4 = ShapeFactory.INST.createPoint(0, 2);

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
		IPoint pt1 = ShapeFactory.INST.createPoint(0, 0);
		IPoint pt2 = ShapeFactory.INST.createPoint(2, 0);
		IPoint pt3 = ShapeFactory.INST.createPoint(2, 2);
		IPoint pt4 = ShapeFactory.INST.createPoint(0, 2);

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
		IPoint pt1 = ShapeFactory.INST.createPoint(0, 0);
		IPoint pt2 = ShapeFactory.INST.createPoint(2, 0);
		IPoint pt3 = ShapeFactory.INST.createPoint(2, 2);
		IPoint pt4 = ShapeFactory.INST.createPoint(0, 2);

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
