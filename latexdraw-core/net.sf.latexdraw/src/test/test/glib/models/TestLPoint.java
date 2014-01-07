package test.glib.models;

import junit.framework.TestCase;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.IPoint;

import org.junit.Test;

public class TestLPoint extends TestCase {
	@Test
	public void testLPoint() {
		IPoint pt = ShapeFactory.createPoint(1.,-200.);

		assertEquals(ShapeFactory.createPoint(), ShapeFactory.createPoint(0.,0.));
		assertEquals(pt.getX(), 1.);
		assertEquals(pt.getY(), -200.);

		pt = ShapeFactory.createPoint((IPoint)null);
		assertEquals(pt.getX(), 0.);
		assertEquals(pt.getY(), 0.);

		pt = ShapeFactory.createPoint(ShapeFactory.createPoint(-1.34, Double.POSITIVE_INFINITY));
		assertEquals(pt.getX(), -1.34);
		assertEquals(pt.getY(), Double.POSITIVE_INFINITY);
	}


	@Test
	public void testRotatePoint() {
		IPoint pt = ShapeFactory.createPoint();

		assertNull(pt.rotatePoint( null, 0));
		assertNull(pt.rotatePoint(null, 0));
		assertNull(pt.rotatePoint(ShapeFactory.createPoint(), Double.NaN));
		assertNull(pt.rotatePoint(ShapeFactory.createPoint(), Double.NEGATIVE_INFINITY));
		assertNull(pt.rotatePoint(ShapeFactory.createPoint(), Double.POSITIVE_INFINITY));

		assertEquals(ShapeFactory.createPoint(0,1),  ShapeFactory.createPoint(1,0).rotatePoint(ShapeFactory.createPoint(), Math.PI/2.));
		assertEquals(ShapeFactory.createPoint(-1,0), ShapeFactory.createPoint(0,1).rotatePoint(ShapeFactory.createPoint(), Math.PI/2.));
		assertEquals(ShapeFactory.createPoint(0,-1), ShapeFactory.createPoint(-1,0).rotatePoint(ShapeFactory.createPoint(), Math.PI/2.));
		assertEquals(ShapeFactory.createPoint(1,0),  ShapeFactory.createPoint(0,-1).rotatePoint(ShapeFactory.createPoint(), Math.PI/2.));

		assertEquals(ShapeFactory.createPoint(0,1),  ShapeFactory.createPoint(1,0).rotatePoint(pt, -3.*Math.PI/2.));
		assertEquals(ShapeFactory.createPoint(-1,0), ShapeFactory.createPoint(0,1).rotatePoint(pt, -3.*Math.PI/2.));
		assertEquals(ShapeFactory.createPoint(0,-1), ShapeFactory.createPoint(-1,0).rotatePoint(pt, -3.*Math.PI/2.));
		assertEquals(ShapeFactory.createPoint(1,0),  ShapeFactory.createPoint(0,-1).rotatePoint(pt, -3.*Math.PI/2.));

		assertEquals(ShapeFactory.createPoint(-1,0), ShapeFactory.createPoint(1,0).rotatePoint(pt, Math.PI));
		assertEquals(ShapeFactory.createPoint(0,-1), ShapeFactory.createPoint(0,1).rotatePoint(pt, Math.PI));
		assertEquals(ShapeFactory.createPoint(1,0),  ShapeFactory.createPoint(-1,0).rotatePoint(pt, Math.PI));
		assertEquals(ShapeFactory.createPoint(0,1),  ShapeFactory.createPoint(0,-1).rotatePoint(pt, Math.PI));

		assertEquals(ShapeFactory.createPoint(-1,0), ShapeFactory.createPoint(1,0).rotatePoint(pt, -Math.PI));
		assertEquals(ShapeFactory.createPoint(0,-1), ShapeFactory.createPoint(0,1).rotatePoint(pt, -Math.PI));
		assertEquals(ShapeFactory.createPoint(1,0),  ShapeFactory.createPoint(-1,0).rotatePoint(pt, -Math.PI));
		assertEquals(ShapeFactory.createPoint(0,1),  ShapeFactory.createPoint(0,-1).rotatePoint(pt, -Math.PI));

		assertEquals(ShapeFactory.createPoint(0,-1), ShapeFactory.createPoint(1,0).rotatePoint(pt, 3.*Math.PI/2.));
		assertEquals(ShapeFactory.createPoint(1,0),  ShapeFactory.createPoint(0,1).rotatePoint(pt, 3.*Math.PI/2.));
		assertEquals(ShapeFactory.createPoint(0,1),  ShapeFactory.createPoint(-1,0).rotatePoint(pt, 3.*Math.PI/2.));
		assertEquals(ShapeFactory.createPoint(-1,0), ShapeFactory.createPoint(0,-1).rotatePoint(pt, 3.*Math.PI/2.));

		assertEquals(ShapeFactory.createPoint(0,-1), ShapeFactory.createPoint(1,0).rotatePoint(pt, -Math.PI/2.));
		assertEquals(ShapeFactory.createPoint(1,0),  ShapeFactory.createPoint(0,1).rotatePoint(pt, -Math.PI/2.));
		assertEquals(ShapeFactory.createPoint(0,1),  ShapeFactory.createPoint(-1,0).rotatePoint(pt, -Math.PI/2.));
		assertEquals(ShapeFactory.createPoint(-1,0), ShapeFactory.createPoint(0,-1).rotatePoint(pt, -Math.PI/2.));

		assertEquals(ShapeFactory.createPoint(1,0),  ShapeFactory.createPoint(1,0).rotatePoint(pt, 2.*Math.PI));
		assertEquals(ShapeFactory.createPoint(0,1),  ShapeFactory.createPoint(0,1).rotatePoint(pt, 2.*Math.PI));
		assertEquals(ShapeFactory.createPoint(-1,0), ShapeFactory.createPoint(-1,0).rotatePoint(pt, 2.*Math.PI));
		assertEquals(ShapeFactory.createPoint(0,-1), ShapeFactory.createPoint(0,-1).rotatePoint(pt, 2.*Math.PI));

		assertEquals(ShapeFactory.createPoint(1,0),  ShapeFactory.createPoint(1,0).rotatePoint(pt, -2.*Math.PI));
		assertEquals(ShapeFactory.createPoint(0,1),  ShapeFactory.createPoint(0,1).rotatePoint(pt, -2.*Math.PI));
		assertEquals(ShapeFactory.createPoint(-1,0), ShapeFactory.createPoint(-1,0).rotatePoint(pt, -2.*Math.PI));
		assertEquals(ShapeFactory.createPoint(0,-1), ShapeFactory.createPoint(0,-1).rotatePoint(pt, -2.*Math.PI));

		assertEquals(ShapeFactory.createPoint(1,0),  ShapeFactory.createPoint(1,0).rotatePoint(pt, 0));
		assertEquals(ShapeFactory.createPoint(0,1),  ShapeFactory.createPoint(0,1).rotatePoint(pt, 0));
		assertEquals(ShapeFactory.createPoint(-1,0), ShapeFactory.createPoint(-1,0).rotatePoint(pt, 0));
		assertEquals(ShapeFactory.createPoint(0,-1), ShapeFactory.createPoint(0,-1).rotatePoint(pt, 0));
	}



	@Test
	public void testCentralSymmetry() {
		IPoint pt = ShapeFactory.createPoint();
		assertNull(pt.centralSymmetry(null));
		assertEquals(ShapeFactory.createPoint(-1,0),  ShapeFactory.createPoint(1,0).centralSymmetry(pt));
		assertEquals(ShapeFactory.createPoint(0,-1),  ShapeFactory.createPoint(0,1).centralSymmetry(pt));
		assertEquals(ShapeFactory.createPoint(1,0),  ShapeFactory.createPoint(-1,0).centralSymmetry(pt));
		assertEquals(ShapeFactory.createPoint(0,1),  ShapeFactory.createPoint(0,-1).centralSymmetry(pt));
	}


	@Test
	public void testComputeAngle() {
		IPoint pt1 = ShapeFactory.createPoint();

		assertEquals(Double.NaN, pt1.computeAngle(null));
		assertEquals(0., pt1.computeAngle(ShapeFactory.createPoint(1,0)));
		assertEquals(Math.PI/2., pt1.computeAngle(ShapeFactory.createPoint(0,1)));
		assertEquals(Math.PI, pt1.computeAngle(ShapeFactory.createPoint(-1,0)));
		assertEquals(3.*Math.PI/2., pt1.computeAngle(ShapeFactory.createPoint(0,-1)));

		pt1.setPoint(1, 1);
		assertEquals(0., pt1.computeAngle(ShapeFactory.createPoint(2,1)));
		assertEquals(Math.PI/2., pt1.computeAngle(ShapeFactory.createPoint(1,2)));
		assertEquals(Math.PI, pt1.computeAngle(ShapeFactory.createPoint(0,1)));
		assertEquals(3.*Math.PI/2., pt1.computeAngle(ShapeFactory.createPoint(1,0)));
	}


	@Test
	public void testComputeRotationAngle() {
		IPoint pt1 = ShapeFactory.createPoint();

		assertEquals(Double.NaN, pt1.computeRotationAngle(null, null));
		assertEquals(Double.NaN, pt1.computeRotationAngle(ShapeFactory.createPoint(), null));
		assertEquals(Double.NaN, pt1.computeRotationAngle(null, ShapeFactory.createPoint()));

		assertEquals(0., pt1.computeRotationAngle(ShapeFactory.createPoint(1,0), ShapeFactory.createPoint(1,0)));
		assertEquals(0., pt1.computeRotationAngle(ShapeFactory.createPoint(0,1), ShapeFactory.createPoint(0,1)));
		assertEquals(0., pt1.computeRotationAngle(ShapeFactory.createPoint(-1,0), ShapeFactory.createPoint(-1,0)));
		assertEquals(0., pt1.computeRotationAngle(ShapeFactory.createPoint(0,-1), ShapeFactory.createPoint(0,-1)));

		assertEquals(Math.PI/2., pt1.computeRotationAngle(ShapeFactory.createPoint(1,0), ShapeFactory.createPoint(0,1)));
		assertEquals(Math.PI, Math.abs(pt1.computeRotationAngle(ShapeFactory.createPoint(1,0), ShapeFactory.createPoint(-1,0))));
		assertEquals(3.*Math.PI/2., pt1.computeRotationAngle(ShapeFactory.createPoint(1,0), ShapeFactory.createPoint(0,-1)));

		assertEquals(Math.PI/2., pt1.computeRotationAngle(ShapeFactory.createPoint(0,1), ShapeFactory.createPoint(-1,0)));
		assertEquals(Math.PI, Math.abs(pt1.computeRotationAngle(ShapeFactory.createPoint(0,1), ShapeFactory.createPoint(0,-1))));
		assertEquals(-Math.PI/2., pt1.computeRotationAngle(ShapeFactory.createPoint(0,1), ShapeFactory.createPoint(1,0)));

		assertEquals(Math.PI/2., pt1.computeRotationAngle(ShapeFactory.createPoint(-1,0), ShapeFactory.createPoint(0,-1)));
		assertEquals(Math.PI, Math.abs(pt1.computeRotationAngle(ShapeFactory.createPoint(-1,0), ShapeFactory.createPoint(1,0))));
		assertEquals(-Math.PI/2., pt1.computeRotationAngle(ShapeFactory.createPoint(-1,0), ShapeFactory.createPoint(0,1)));

		assertEquals(-3.*Math.PI/2., pt1.computeRotationAngle(ShapeFactory.createPoint(0,-1), ShapeFactory.createPoint(1,0)));
		assertEquals(Math.PI, Math.abs(pt1.computeRotationAngle(ShapeFactory.createPoint(0,-1), ShapeFactory.createPoint(0,1))));
		assertEquals(-Math.PI/2., pt1.computeRotationAngle(ShapeFactory.createPoint(0,-1), ShapeFactory.createPoint(-1,0)));
	}



	@Test
	public void testEquals() {
		assertFalse(ShapeFactory.createPoint().equals(null, 0));
		assertFalse(ShapeFactory.createPoint().equals(ShapeFactory.createPoint(), Double.NaN));
		assertFalse(ShapeFactory.createPoint().equals(ShapeFactory.createPoint(), Double.POSITIVE_INFINITY));
		assertFalse(ShapeFactory.createPoint().equals(ShapeFactory.createPoint(), Double.NEGATIVE_INFINITY));
		assertFalse(ShapeFactory.createPoint(0,0).equals(ShapeFactory.createPoint(1,1), 0));
		assertFalse(ShapeFactory.createPoint(0,0).equals(ShapeFactory.createPoint(-1,-1), 0));
		assertFalse(ShapeFactory.createPoint(0,0).equals(ShapeFactory.createPoint(10,10), 9));
		assertFalse(ShapeFactory.createPoint(0,0).equals(ShapeFactory.createPoint(-10,-10), 9));
		assertFalse(ShapeFactory.createPoint(0,0).equals(ShapeFactory.createPoint(Double.NaN,0), 1));
		assertFalse(ShapeFactory.createPoint(0,0).equals(ShapeFactory.createPoint(0,Double.NaN), 1));
		assertFalse(ShapeFactory.createPoint(0,0).equals(ShapeFactory.createPoint(Double.POSITIVE_INFINITY,0), 1));
		assertFalse(ShapeFactory.createPoint(0,0).equals(ShapeFactory.createPoint(0,Double.POSITIVE_INFINITY), 1));
		assertFalse(ShapeFactory.createPoint(0,0).equals(ShapeFactory.createPoint(Double.NEGATIVE_INFINITY,0), 1));
		assertFalse(ShapeFactory.createPoint(0,0).equals(ShapeFactory.createPoint(0,Double.NEGATIVE_INFINITY), 1));
		assertFalse(ShapeFactory.createPoint(Double.NaN,0).equals(ShapeFactory.createPoint(0,0), 1));
		assertFalse(ShapeFactory.createPoint(0,Double.NaN).equals(ShapeFactory.createPoint(0,0), 1));
		assertFalse(ShapeFactory.createPoint(Double.POSITIVE_INFINITY,0).equals(ShapeFactory.createPoint(0,0), 1));
		assertFalse(ShapeFactory.createPoint(0,Double.POSITIVE_INFINITY).equals(ShapeFactory.createPoint(0,0), 1));
		assertFalse(ShapeFactory.createPoint(Double.NEGATIVE_INFINITY,0).equals(ShapeFactory.createPoint(0,0), 1));
		assertFalse(ShapeFactory.createPoint(0,Double.NEGATIVE_INFINITY).equals(ShapeFactory.createPoint(0,0), 1));

		assertTrue(ShapeFactory.createPoint(0,0).equals(ShapeFactory.createPoint(0,0), 0));
		assertTrue(ShapeFactory.createPoint(-1,-1).equals(ShapeFactory.createPoint(-1,-1), 0));
		assertTrue(ShapeFactory.createPoint(1,1).equals(ShapeFactory.createPoint(10,10), 9));
		assertTrue(ShapeFactory.createPoint(-1,-1).equals(ShapeFactory.createPoint(-10,-10), 9));
		assertTrue(ShapeFactory.createPoint(0,0).equals(ShapeFactory.createPoint(0.000001,0), 0.0001));
		assertTrue(ShapeFactory.createPoint(0,0).equals(ShapeFactory.createPoint(0, 0.000001), 0.0001));
	}


	@Test
	public void testGetMiddlePoint() {
		assertNull(ShapeFactory.createPoint().getMiddlePoint(null));
		assertEquals(ShapeFactory.createPoint(10,0).getMiddlePoint(ShapeFactory.createPoint(-10,0)), ShapeFactory.createPoint(0,0));
		assertEquals(ShapeFactory.createPoint(0,10).getMiddlePoint(ShapeFactory.createPoint(0,-10)), ShapeFactory.createPoint(0,0));
		assertEquals(ShapeFactory.createPoint(10,10).getMiddlePoint(ShapeFactory.createPoint(-10,-10)), ShapeFactory.createPoint(0,0));
		assertEquals(ShapeFactory.createPoint(0,0).getMiddlePoint(ShapeFactory.createPoint(5,6)), ShapeFactory.createPoint(2.5,3));
		assertEquals(ShapeFactory.createPoint(0,0).getMiddlePoint(ShapeFactory.createPoint(-5,-6)), ShapeFactory.createPoint(-2.5,-3));
		assertEquals(ShapeFactory.createPoint(Double.POSITIVE_INFINITY,10).getMiddlePoint(ShapeFactory.createPoint(-10,-10)), ShapeFactory.createPoint(Double.POSITIVE_INFINITY,0));
		assertEquals(ShapeFactory.createPoint(Double.NEGATIVE_INFINITY,10).getMiddlePoint(ShapeFactory.createPoint(-10,-10)), ShapeFactory.createPoint(Double.NEGATIVE_INFINITY,0));
		assertEquals(ShapeFactory.createPoint(10, Double.POSITIVE_INFINITY).getMiddlePoint(ShapeFactory.createPoint(-10,-10)), ShapeFactory.createPoint(0, Double.POSITIVE_INFINITY));
		assertEquals(ShapeFactory.createPoint(10, Double.NEGATIVE_INFINITY).getMiddlePoint(ShapeFactory.createPoint(-10,-10)), ShapeFactory.createPoint(0, Double.NEGATIVE_INFINITY));
	}


	@Test
	public void testTranslate() {
		IPoint pt1 = ShapeFactory.createPoint(2., 1.);
		IPoint pt2 = ShapeFactory.createPoint(2., 1.);

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
		assertEquals(pt1, ShapeFactory.createPoint(2., 11.));
		pt1.translate(0., -12.);
		assertEquals(pt1, ShapeFactory.createPoint(2., -1.));
		pt1.translate(5., 0.);
		assertEquals(pt1, ShapeFactory.createPoint(7., -1.));
		pt1.translate(-4., 0.);
		assertEquals(pt1, ShapeFactory.createPoint(3., -1.));
		pt1.translate(3.12, 7.98);
		assertEquals(pt1, ShapeFactory.createPoint(6.12, 6.98));
	}


	@Test
	public void testHorizontalSymmetry() {
		IPoint pt = ShapeFactory.createPoint(10., 10.);

		assertNull(pt.horizontalSymmetry(null));
		assertNull(pt.horizontalSymmetry(ShapeFactory.createPoint(Double.NaN, 1.)));
		assertNull(pt.horizontalSymmetry(ShapeFactory.createPoint(1., Double.NaN)));
		assertNull(pt.horizontalSymmetry(ShapeFactory.createPoint(Double.POSITIVE_INFINITY, 1.)));
		assertNull(pt.horizontalSymmetry(ShapeFactory.createPoint(1., Double.POSITIVE_INFINITY)));
		assertNull(pt.horizontalSymmetry(ShapeFactory.createPoint(Double.NEGATIVE_INFINITY, 1.)));
		assertNull(pt.horizontalSymmetry(ShapeFactory.createPoint(1., Double.NEGATIVE_INFINITY)));
		assertEquals(pt.horizontalSymmetry(ShapeFactory.createPoint(0.,0.)), ShapeFactory.createPoint(-10., 10.));
		assertEquals(pt.horizontalSymmetry(ShapeFactory.createPoint(0.,18780.)), ShapeFactory.createPoint(-10., 10.));
		assertEquals(ShapeFactory.createPoint(-10., 10.).horizontalSymmetry(ShapeFactory.createPoint(0.,18780.)), pt);
		assertEquals(ShapeFactory.createPoint(0.,0.).horizontalSymmetry(ShapeFactory.createPoint(0.,0.)), ShapeFactory.createPoint(0., 0.));
	}


	@Test
	public void testVerticalSymmetry() {
		IPoint pt = ShapeFactory.createPoint(10., 10.);

		assertNull(pt.verticalSymmetry(null));
		assertNull(pt.verticalSymmetry(ShapeFactory.createPoint(Double.NaN, 1.)));
		assertNull(pt.verticalSymmetry(ShapeFactory.createPoint(1., Double.NaN)));
		assertNull(pt.verticalSymmetry(ShapeFactory.createPoint(Double.POSITIVE_INFINITY, 1.)));
		assertNull(pt.verticalSymmetry(ShapeFactory.createPoint(1., Double.POSITIVE_INFINITY)));
		assertNull(pt.verticalSymmetry(ShapeFactory.createPoint(Double.NEGATIVE_INFINITY, 1.)));
		assertNull(pt.verticalSymmetry(ShapeFactory.createPoint(1., Double.NEGATIVE_INFINITY)));
		assertEquals(pt.verticalSymmetry(ShapeFactory.createPoint(0.,0.)), ShapeFactory.createPoint(10., -10.));
		assertEquals(pt.verticalSymmetry(ShapeFactory.createPoint(18780., 0.)), ShapeFactory.createPoint(10., -10.));
		assertEquals(ShapeFactory.createPoint(10., -10.).verticalSymmetry(ShapeFactory.createPoint(18780., 0.)), pt);
		assertEquals(ShapeFactory.createPoint(0.,0.).verticalSymmetry(ShapeFactory.createPoint(0.,0.)), ShapeFactory.createPoint(0., 0.));
	}
}
