package test.glib.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IPoint;

import org.junit.Test;

public class TestLPoint {
	@Test
	public void testLPointOK() {
		IPoint pt = ShapeFactory.INST.createPoint(1., -200.);

		assertEquals(ShapeFactory.INST.createPoint(), ShapeFactory.INST.createPoint(0., 0.));
		assertEquals(pt.getX(), 1., 0.1);
		assertEquals(pt.getY(), -200., 0.1);
	}

	@Test
	public void testRotatePoint() {
		IPoint pt = ShapeFactory.INST.createPoint();

		assertNull(pt.rotatePoint(null, 0));
		assertNull(pt.rotatePoint(null, 0));
		assertNull(pt.rotatePoint(ShapeFactory.INST.createPoint(), Double.NaN));
		assertNull(pt.rotatePoint(ShapeFactory.INST.createPoint(), Double.NEGATIVE_INFINITY));
		assertNull(pt.rotatePoint(ShapeFactory.INST.createPoint(), Double.POSITIVE_INFINITY));

		assertEquals(ShapeFactory.INST.createPoint(0, 1), ShapeFactory.INST.createPoint(1, 0).rotatePoint(ShapeFactory.INST.createPoint(), Math.PI / 2.));
		assertEquals(ShapeFactory.INST.createPoint(-1, 0), ShapeFactory.INST.createPoint(0, 1).rotatePoint(ShapeFactory.INST.createPoint(), Math.PI / 2.));
		assertEquals(ShapeFactory.INST.createPoint(0, -1), ShapeFactory.INST.createPoint(-1, 0).rotatePoint(ShapeFactory.INST.createPoint(), Math.PI / 2.));
		assertEquals(ShapeFactory.INST.createPoint(1, 0), ShapeFactory.INST.createPoint(0, -1).rotatePoint(ShapeFactory.INST.createPoint(), Math.PI / 2.));

		assertEquals(ShapeFactory.INST.createPoint(0, 1), ShapeFactory.INST.createPoint(1, 0).rotatePoint(pt, -3. * Math.PI / 2.));
		assertEquals(ShapeFactory.INST.createPoint(-1, 0), ShapeFactory.INST.createPoint(0, 1).rotatePoint(pt, -3. * Math.PI / 2.));
		assertEquals(ShapeFactory.INST.createPoint(0, -1), ShapeFactory.INST.createPoint(-1, 0).rotatePoint(pt, -3. * Math.PI / 2.));
		assertEquals(ShapeFactory.INST.createPoint(1, 0), ShapeFactory.INST.createPoint(0, -1).rotatePoint(pt, -3. * Math.PI / 2.));

		assertEquals(ShapeFactory.INST.createPoint(-1, 0), ShapeFactory.INST.createPoint(1, 0).rotatePoint(pt, Math.PI));
		assertEquals(ShapeFactory.INST.createPoint(0, -1), ShapeFactory.INST.createPoint(0, 1).rotatePoint(pt, Math.PI));
		assertEquals(ShapeFactory.INST.createPoint(1, 0), ShapeFactory.INST.createPoint(-1, 0).rotatePoint(pt, Math.PI));
		assertEquals(ShapeFactory.INST.createPoint(0, 1), ShapeFactory.INST.createPoint(0, -1).rotatePoint(pt, Math.PI));

		assertEquals(ShapeFactory.INST.createPoint(-1, 0), ShapeFactory.INST.createPoint(1, 0).rotatePoint(pt, -Math.PI));
		assertEquals(ShapeFactory.INST.createPoint(0, -1), ShapeFactory.INST.createPoint(0, 1).rotatePoint(pt, -Math.PI));
		assertEquals(ShapeFactory.INST.createPoint(1, 0), ShapeFactory.INST.createPoint(-1, 0).rotatePoint(pt, -Math.PI));
		assertEquals(ShapeFactory.INST.createPoint(0, 1), ShapeFactory.INST.createPoint(0, -1).rotatePoint(pt, -Math.PI));

		assertEquals(ShapeFactory.INST.createPoint(0, -1), ShapeFactory.INST.createPoint(1, 0).rotatePoint(pt, 3. * Math.PI / 2.));
		assertEquals(ShapeFactory.INST.createPoint(1, 0), ShapeFactory.INST.createPoint(0, 1).rotatePoint(pt, 3. * Math.PI / 2.));
		assertEquals(ShapeFactory.INST.createPoint(0, 1), ShapeFactory.INST.createPoint(-1, 0).rotatePoint(pt, 3. * Math.PI / 2.));
		assertEquals(ShapeFactory.INST.createPoint(-1, 0), ShapeFactory.INST.createPoint(0, -1).rotatePoint(pt, 3. * Math.PI / 2.));

		assertEquals(ShapeFactory.INST.createPoint(0, -1), ShapeFactory.INST.createPoint(1, 0).rotatePoint(pt, -Math.PI / 2.));
		assertEquals(ShapeFactory.INST.createPoint(1, 0), ShapeFactory.INST.createPoint(0, 1).rotatePoint(pt, -Math.PI / 2.));
		assertEquals(ShapeFactory.INST.createPoint(0, 1), ShapeFactory.INST.createPoint(-1, 0).rotatePoint(pt, -Math.PI / 2.));
		assertEquals(ShapeFactory.INST.createPoint(-1, 0), ShapeFactory.INST.createPoint(0, -1).rotatePoint(pt, -Math.PI / 2.));

		assertEquals(ShapeFactory.INST.createPoint(1, 0), ShapeFactory.INST.createPoint(1, 0).rotatePoint(pt, 2. * Math.PI));
		assertEquals(ShapeFactory.INST.createPoint(0, 1), ShapeFactory.INST.createPoint(0, 1).rotatePoint(pt, 2. * Math.PI));
		assertEquals(ShapeFactory.INST.createPoint(-1, 0), ShapeFactory.INST.createPoint(-1, 0).rotatePoint(pt, 2. * Math.PI));
		assertEquals(ShapeFactory.INST.createPoint(0, -1), ShapeFactory.INST.createPoint(0, -1).rotatePoint(pt, 2. * Math.PI));

		assertEquals(ShapeFactory.INST.createPoint(1, 0), ShapeFactory.INST.createPoint(1, 0).rotatePoint(pt, -2. * Math.PI));
		assertEquals(ShapeFactory.INST.createPoint(0, 1), ShapeFactory.INST.createPoint(0, 1).rotatePoint(pt, -2. * Math.PI));
		assertEquals(ShapeFactory.INST.createPoint(-1, 0), ShapeFactory.INST.createPoint(-1, 0).rotatePoint(pt, -2. * Math.PI));
		assertEquals(ShapeFactory.INST.createPoint(0, -1), ShapeFactory.INST.createPoint(0, -1).rotatePoint(pt, -2. * Math.PI));

		assertEquals(ShapeFactory.INST.createPoint(1, 0), ShapeFactory.INST.createPoint(1, 0).rotatePoint(pt, 0));
		assertEquals(ShapeFactory.INST.createPoint(0, 1), ShapeFactory.INST.createPoint(0, 1).rotatePoint(pt, 0));
		assertEquals(ShapeFactory.INST.createPoint(-1, 0), ShapeFactory.INST.createPoint(-1, 0).rotatePoint(pt, 0));
		assertEquals(ShapeFactory.INST.createPoint(0, -1), ShapeFactory.INST.createPoint(0, -1).rotatePoint(pt, 0));
	}

	@Test
	public void testCentralSymmetry() {
		IPoint pt = ShapeFactory.INST.createPoint();
		assertNull(pt.centralSymmetry(null));
		assertEquals(ShapeFactory.INST.createPoint(-1, 0), ShapeFactory.INST.createPoint(1, 0).centralSymmetry(pt));
		assertEquals(ShapeFactory.INST.createPoint(0, -1), ShapeFactory.INST.createPoint(0, 1).centralSymmetry(pt));
		assertEquals(ShapeFactory.INST.createPoint(1, 0), ShapeFactory.INST.createPoint(-1, 0).centralSymmetry(pt));
		assertEquals(ShapeFactory.INST.createPoint(0, 1), ShapeFactory.INST.createPoint(0, -1).centralSymmetry(pt));
	}

	@Test
	public void testComputeAngle() {
		IPoint pt1 = ShapeFactory.INST.createPoint();

		assertEquals(Double.NaN, pt1.computeAngle(null), 0.1);
		assertEquals(0., pt1.computeAngle(ShapeFactory.INST.createPoint(1, 0)), 0.1);
		assertEquals(Math.PI / 2., pt1.computeAngle(ShapeFactory.INST.createPoint(0, 1)), 0.1);
		assertEquals(Math.PI, pt1.computeAngle(ShapeFactory.INST.createPoint(-1, 0)), 0.1);
		assertEquals(3. * Math.PI / 2., pt1.computeAngle(ShapeFactory.INST.createPoint(0, -1)), 0.1);

		pt1.setPoint(1, 1);
		assertEquals(0., pt1.computeAngle(ShapeFactory.INST.createPoint(2, 1)), 0.1);
		assertEquals(Math.PI / 2., pt1.computeAngle(ShapeFactory.INST.createPoint(1, 2)), 0.1);
		assertEquals(Math.PI, pt1.computeAngle(ShapeFactory.INST.createPoint(0, 1)), 0.1);
		assertEquals(3. * Math.PI / 2., pt1.computeAngle(ShapeFactory.INST.createPoint(1, 0)), 0.1);
	}

	@Test
	public void testComputeRotationAngle() {
		IPoint pt1 = ShapeFactory.INST.createPoint();

		assertEquals(Double.NaN, pt1.computeRotationAngle(null, null), 0.1);
		assertEquals(Double.NaN, pt1.computeRotationAngle(ShapeFactory.INST.createPoint(), null), 0.1);
		assertEquals(Double.NaN, pt1.computeRotationAngle(null, ShapeFactory.INST.createPoint()), 0.1);

		assertEquals(0., pt1.computeRotationAngle(ShapeFactory.INST.createPoint(1, 0), ShapeFactory.INST.createPoint(1, 0)), 0.1);
		assertEquals(0., pt1.computeRotationAngle(ShapeFactory.INST.createPoint(0, 1), ShapeFactory.INST.createPoint(0, 1)), 0.1);
		assertEquals(0., pt1.computeRotationAngle(ShapeFactory.INST.createPoint(-1, 0), ShapeFactory.INST.createPoint(-1, 0)), 0.1);
		assertEquals(0., pt1.computeRotationAngle(ShapeFactory.INST.createPoint(0, -1), ShapeFactory.INST.createPoint(0, -1)), 0.1);

		assertEquals(Math.PI / 2., pt1.computeRotationAngle(ShapeFactory.INST.createPoint(1, 0), ShapeFactory.INST.createPoint(0, 1)), 0.1);
		assertEquals(Math.PI, Math.abs(pt1.computeRotationAngle(ShapeFactory.INST.createPoint(1, 0), ShapeFactory.INST.createPoint(-1, 0))), 0.1);
		assertEquals(3. * Math.PI / 2., pt1.computeRotationAngle(ShapeFactory.INST.createPoint(1, 0), ShapeFactory.INST.createPoint(0, -1)), 0.1);

		assertEquals(Math.PI / 2., pt1.computeRotationAngle(ShapeFactory.INST.createPoint(0, 1), ShapeFactory.INST.createPoint(-1, 0)), 0.1);
		assertEquals(Math.PI, Math.abs(pt1.computeRotationAngle(ShapeFactory.INST.createPoint(0, 1), ShapeFactory.INST.createPoint(0, -1))), 0.1);
		assertEquals(-Math.PI / 2., pt1.computeRotationAngle(ShapeFactory.INST.createPoint(0, 1), ShapeFactory.INST.createPoint(1, 0)), 0.1);

		assertEquals(Math.PI / 2., pt1.computeRotationAngle(ShapeFactory.INST.createPoint(-1, 0), ShapeFactory.INST.createPoint(0, -1)), 0.1);
		assertEquals(Math.PI, Math.abs(pt1.computeRotationAngle(ShapeFactory.INST.createPoint(-1, 0), ShapeFactory.INST.createPoint(1, 0))), 0.1);
		assertEquals(-Math.PI / 2., pt1.computeRotationAngle(ShapeFactory.INST.createPoint(-1, 0), ShapeFactory.INST.createPoint(0, 1)), 0.1);

		assertEquals(-3. * Math.PI / 2., pt1.computeRotationAngle(ShapeFactory.INST.createPoint(0, -1), ShapeFactory.INST.createPoint(1, 0)), 0.1);
		assertEquals(Math.PI, Math.abs(pt1.computeRotationAngle(ShapeFactory.INST.createPoint(0, -1), ShapeFactory.INST.createPoint(0, 1))), 0.1);
		assertEquals(-Math.PI / 2., pt1.computeRotationAngle(ShapeFactory.INST.createPoint(0, -1), ShapeFactory.INST.createPoint(-1, 0)), 0.1);
	}

	@Test
	public void testEquals() {
		assertFalse(ShapeFactory.INST.createPoint().equals(null, 0));
		assertFalse(ShapeFactory.INST.createPoint().equals(ShapeFactory.INST.createPoint(), Double.NaN));
		assertFalse(ShapeFactory.INST.createPoint().equals(ShapeFactory.INST.createPoint(), Double.POSITIVE_INFINITY));
		assertFalse(ShapeFactory.INST.createPoint().equals(ShapeFactory.INST.createPoint(), Double.NEGATIVE_INFINITY));
		assertFalse(ShapeFactory.INST.createPoint(0, 0).equals(ShapeFactory.INST.createPoint(1, 1), 0));
		assertFalse(ShapeFactory.INST.createPoint(0, 0).equals(ShapeFactory.INST.createPoint(-1, -1), 0));
		assertFalse(ShapeFactory.INST.createPoint(0, 0).equals(ShapeFactory.INST.createPoint(10, 10), 9));
		assertFalse(ShapeFactory.INST.createPoint(0, 0).equals(ShapeFactory.INST.createPoint(-10, -10), 9));
		assertFalse(ShapeFactory.INST.createPoint(0, 0).equals(ShapeFactory.INST.createPoint(Double.NaN, 0), 1));
		assertFalse(ShapeFactory.INST.createPoint(0, 0).equals(ShapeFactory.INST.createPoint(0, Double.NaN), 1));
		assertFalse(ShapeFactory.INST.createPoint(0, 0).equals(ShapeFactory.INST.createPoint(Double.POSITIVE_INFINITY, 0), 1));
		assertFalse(ShapeFactory.INST.createPoint(0, 0).equals(ShapeFactory.INST.createPoint(0, Double.POSITIVE_INFINITY), 1));
		assertFalse(ShapeFactory.INST.createPoint(0, 0).equals(ShapeFactory.INST.createPoint(Double.NEGATIVE_INFINITY, 0), 1));
		assertFalse(ShapeFactory.INST.createPoint(0, 0).equals(ShapeFactory.INST.createPoint(0, Double.NEGATIVE_INFINITY), 1));
		assertFalse(ShapeFactory.INST.createPoint(Double.NaN, 0).equals(ShapeFactory.INST.createPoint(0, 0), 1));
		assertFalse(ShapeFactory.INST.createPoint(0, Double.NaN).equals(ShapeFactory.INST.createPoint(0, 0), 1));
		assertFalse(ShapeFactory.INST.createPoint(Double.POSITIVE_INFINITY, 0).equals(ShapeFactory.INST.createPoint(0, 0), 1));
		assertFalse(ShapeFactory.INST.createPoint(0, Double.POSITIVE_INFINITY).equals(ShapeFactory.INST.createPoint(0, 0), 1));
		assertFalse(ShapeFactory.INST.createPoint(Double.NEGATIVE_INFINITY, 0).equals(ShapeFactory.INST.createPoint(0, 0), 1));
		assertFalse(ShapeFactory.INST.createPoint(0, Double.NEGATIVE_INFINITY).equals(ShapeFactory.INST.createPoint(0, 0), 1));

		assertTrue(ShapeFactory.INST.createPoint(0, 0).equals(ShapeFactory.INST.createPoint(0, 0), 0));
		assertTrue(ShapeFactory.INST.createPoint(-1, -1).equals(ShapeFactory.INST.createPoint(-1, -1), 0));
		assertTrue(ShapeFactory.INST.createPoint(1, 1).equals(ShapeFactory.INST.createPoint(10, 10), 9));
		assertTrue(ShapeFactory.INST.createPoint(-1, -1).equals(ShapeFactory.INST.createPoint(-10, -10), 9));
		assertTrue(ShapeFactory.INST.createPoint(0, 0).equals(ShapeFactory.INST.createPoint(0.000001, 0), 0.0001));
		assertTrue(ShapeFactory.INST.createPoint(0, 0).equals(ShapeFactory.INST.createPoint(0, 0.000001), 0.0001));
	}

	@Test
	public void testGetMiddlePoint() {
		assertNull(ShapeFactory.INST.createPoint().getMiddlePoint(null));
		assertEquals(ShapeFactory.INST.createPoint(0, 0), ShapeFactory.INST.createPoint(10, 0).getMiddlePoint(ShapeFactory.INST.createPoint(-10, 0)));
		assertEquals(ShapeFactory.INST.createPoint(0, 0), ShapeFactory.INST.createPoint(0, 10).getMiddlePoint(ShapeFactory.INST.createPoint(0, -10)));
		assertEquals(ShapeFactory.INST.createPoint(0, 0), ShapeFactory.INST.createPoint(10, 10).getMiddlePoint(ShapeFactory.INST.createPoint(-10, -10)));
		assertEquals(ShapeFactory.INST.createPoint(2.5, 3), ShapeFactory.INST.createPoint(0, 0).getMiddlePoint(ShapeFactory.INST.createPoint(5, 6)));
		assertEquals(ShapeFactory.INST.createPoint(-2.5, -3), ShapeFactory.INST.createPoint(0, 0).getMiddlePoint(ShapeFactory.INST.createPoint(-5, -6)));
		assertEquals(ShapeFactory.INST.createPoint(Double.POSITIVE_INFINITY, 0), ShapeFactory.INST.createPoint(Double.POSITIVE_INFINITY, 10).getMiddlePoint(ShapeFactory.INST.createPoint(-10, -10)));
		assertEquals(ShapeFactory.INST.createPoint(Double.NEGATIVE_INFINITY, 0), ShapeFactory.INST.createPoint(Double.NEGATIVE_INFINITY, 10).getMiddlePoint(ShapeFactory.INST.createPoint(-10, -10)));
		assertEquals(ShapeFactory.INST.createPoint(0, Double.POSITIVE_INFINITY), ShapeFactory.INST.createPoint(10, Double.POSITIVE_INFINITY).getMiddlePoint(ShapeFactory.INST.createPoint(-10, -10)));
		assertEquals(ShapeFactory.INST.createPoint(0, Double.NEGATIVE_INFINITY), ShapeFactory.INST.createPoint(10, Double.NEGATIVE_INFINITY).getMiddlePoint(ShapeFactory.INST.createPoint(-10, -10)));
	}

	@Test
	public void testTranslate() {
		IPoint pt1 = ShapeFactory.INST.createPoint(2., 1.);
		IPoint pt2 = ShapeFactory.INST.createPoint(2., 1.);

		pt1.translate(Double.NaN, Double.NaN);
		assertEquals(pt1, pt2);
		pt1.translate(Double.NaN, 2.);
		assertEquals(pt1, pt2);
		pt1.translate(2., Double.NaN);
		assertEquals(pt1, pt2);
		pt1.translate(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
		assertEquals(pt1, pt2);
		pt1.translate(Double.POSITIVE_INFINITY, 2.);
		assertEquals(pt1, pt2);
		pt1.translate(2., Double.POSITIVE_INFINITY);
		assertEquals(pt1, pt2);
		pt1.translate(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
		assertEquals(pt1, pt2);
		pt1.translate(Double.NEGATIVE_INFINITY, 2.);
		assertEquals(pt1, pt2);
		pt1.translate(2., Double.NEGATIVE_INFINITY);
		assertEquals(pt1, pt2);
		pt1.translate(0., 0.);
		assertEquals(pt1, pt2);
		pt1.translate(0., 10.);
		assertEquals(pt1, ShapeFactory.INST.createPoint(2., 11.));
		pt1.translate(0., -12.);
		assertEquals(pt1, ShapeFactory.INST.createPoint(2., -1.));
		pt1.translate(5., 0.);
		assertEquals(pt1, ShapeFactory.INST.createPoint(7., -1.));
		pt1.translate(-4., 0.);
		assertEquals(pt1, ShapeFactory.INST.createPoint(3., -1.));
		pt1.translate(3.12, 7.98);
		assertEquals(pt1, ShapeFactory.INST.createPoint(6.12, 6.98));
	}

	@Test
	public void testHorizontalSymmetry() {
		IPoint pt = ShapeFactory.INST.createPoint(10., 10.);

		assertNull(pt.horizontalSymmetry(null));
		assertNull(pt.horizontalSymmetry(ShapeFactory.INST.createPoint(Double.NaN, 1.)));
		assertNull(pt.horizontalSymmetry(ShapeFactory.INST.createPoint(1., Double.NaN)));
		assertNull(pt.horizontalSymmetry(ShapeFactory.INST.createPoint(Double.POSITIVE_INFINITY, 1.)));
		assertNull(pt.horizontalSymmetry(ShapeFactory.INST.createPoint(1., Double.POSITIVE_INFINITY)));
		assertNull(pt.horizontalSymmetry(ShapeFactory.INST.createPoint(Double.NEGATIVE_INFINITY, 1.)));
		assertNull(pt.horizontalSymmetry(ShapeFactory.INST.createPoint(1., Double.NEGATIVE_INFINITY)));
		assertEquals(pt.horizontalSymmetry(ShapeFactory.INST.createPoint(0., 0.)), ShapeFactory.INST.createPoint(-10., 10.));
		assertEquals(pt.horizontalSymmetry(ShapeFactory.INST.createPoint(0., 18780.)), ShapeFactory.INST.createPoint(-10., 10.));
		assertEquals(ShapeFactory.INST.createPoint(-10., 10.).horizontalSymmetry(ShapeFactory.INST.createPoint(0., 18780.)), pt);
		assertEquals(ShapeFactory.INST.createPoint(0., 0.).horizontalSymmetry(ShapeFactory.INST.createPoint(0., 0.)), ShapeFactory.INST.createPoint(0., 0.));
	}

	@Test
	public void testVerticalSymmetry() {
		IPoint pt = ShapeFactory.INST.createPoint(10., 10.);

		assertNull(pt.verticalSymmetry(null));
		assertNull(pt.verticalSymmetry(ShapeFactory.INST.createPoint(Double.NaN, 1.)));
		assertNull(pt.verticalSymmetry(ShapeFactory.INST.createPoint(1., Double.NaN)));
		assertNull(pt.verticalSymmetry(ShapeFactory.INST.createPoint(Double.POSITIVE_INFINITY, 1.)));
		assertNull(pt.verticalSymmetry(ShapeFactory.INST.createPoint(1., Double.POSITIVE_INFINITY)));
		assertNull(pt.verticalSymmetry(ShapeFactory.INST.createPoint(Double.NEGATIVE_INFINITY, 1.)));
		assertNull(pt.verticalSymmetry(ShapeFactory.INST.createPoint(1., Double.NEGATIVE_INFINITY)));
		assertEquals(pt.verticalSymmetry(ShapeFactory.INST.createPoint(0., 0.)), ShapeFactory.INST.createPoint(10., -10.));
		assertEquals(pt.verticalSymmetry(ShapeFactory.INST.createPoint(18780., 0.)), ShapeFactory.INST.createPoint(10., -10.));
		assertEquals(ShapeFactory.INST.createPoint(10., -10.).verticalSymmetry(ShapeFactory.INST.createPoint(18780., 0.)), pt);
		assertEquals(ShapeFactory.INST.createPoint(0., 0.).verticalSymmetry(ShapeFactory.INST.createPoint(0., 0.)), ShapeFactory.INST.createPoint(0., 0.));
	}
}
