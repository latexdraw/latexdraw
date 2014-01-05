package test.glib.models;

import junit.framework.TestCase;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.IPoint;

import org.junit.Test;

public class TestLPoint extends TestCase {
	@Test
	public void testLPoint() {
		IPoint pt = ShapeFactory.factory().createPoint(1.,-200.);

		assertEquals(ShapeFactory.factory().createPoint(), ShapeFactory.factory().createPoint(0.,0.));
		assertEquals(pt.getX(), 1.);
		assertEquals(pt.getY(), -200.);

		pt = ShapeFactory.factory().createPoint((IPoint)null);
		assertEquals(pt.getX(), 0.);
		assertEquals(pt.getY(), 0.);

		pt = ShapeFactory.factory().createPoint(ShapeFactory.factory().createPoint(-1.34, Double.POSITIVE_INFINITY));
		assertEquals(pt.getX(), -1.34);
		assertEquals(pt.getY(), Double.POSITIVE_INFINITY);
	}


	@Test
	public void testRotatePoint() {
		IPoint pt = ShapeFactory.factory().createPoint();

		assertNull(pt.rotatePoint( null, 0));
		assertNull(pt.rotatePoint(null, 0));
		assertNull(pt.rotatePoint(ShapeFactory.factory().createPoint(), Double.NaN));
		assertNull(pt.rotatePoint(ShapeFactory.factory().createPoint(), Double.NEGATIVE_INFINITY));
		assertNull(pt.rotatePoint(ShapeFactory.factory().createPoint(), Double.POSITIVE_INFINITY));

		assertEquals(ShapeFactory.factory().createPoint(0,1),  ShapeFactory.factory().createPoint(1,0).rotatePoint(ShapeFactory.factory().createPoint(), Math.PI/2.));
		assertEquals(ShapeFactory.factory().createPoint(-1,0), ShapeFactory.factory().createPoint(0,1).rotatePoint(ShapeFactory.factory().createPoint(), Math.PI/2.));
		assertEquals(ShapeFactory.factory().createPoint(0,-1), ShapeFactory.factory().createPoint(-1,0).rotatePoint(ShapeFactory.factory().createPoint(), Math.PI/2.));
		assertEquals(ShapeFactory.factory().createPoint(1,0),  ShapeFactory.factory().createPoint(0,-1).rotatePoint(ShapeFactory.factory().createPoint(), Math.PI/2.));

		assertEquals(ShapeFactory.factory().createPoint(0,1),  ShapeFactory.factory().createPoint(1,0).rotatePoint(pt, -3.*Math.PI/2.));
		assertEquals(ShapeFactory.factory().createPoint(-1,0), ShapeFactory.factory().createPoint(0,1).rotatePoint(pt, -3.*Math.PI/2.));
		assertEquals(ShapeFactory.factory().createPoint(0,-1), ShapeFactory.factory().createPoint(-1,0).rotatePoint(pt, -3.*Math.PI/2.));
		assertEquals(ShapeFactory.factory().createPoint(1,0),  ShapeFactory.factory().createPoint(0,-1).rotatePoint(pt, -3.*Math.PI/2.));

		assertEquals(ShapeFactory.factory().createPoint(-1,0), ShapeFactory.factory().createPoint(1,0).rotatePoint(pt, Math.PI));
		assertEquals(ShapeFactory.factory().createPoint(0,-1), ShapeFactory.factory().createPoint(0,1).rotatePoint(pt, Math.PI));
		assertEquals(ShapeFactory.factory().createPoint(1,0),  ShapeFactory.factory().createPoint(-1,0).rotatePoint(pt, Math.PI));
		assertEquals(ShapeFactory.factory().createPoint(0,1),  ShapeFactory.factory().createPoint(0,-1).rotatePoint(pt, Math.PI));

		assertEquals(ShapeFactory.factory().createPoint(-1,0), ShapeFactory.factory().createPoint(1,0).rotatePoint(pt, -Math.PI));
		assertEquals(ShapeFactory.factory().createPoint(0,-1), ShapeFactory.factory().createPoint(0,1).rotatePoint(pt, -Math.PI));
		assertEquals(ShapeFactory.factory().createPoint(1,0),  ShapeFactory.factory().createPoint(-1,0).rotatePoint(pt, -Math.PI));
		assertEquals(ShapeFactory.factory().createPoint(0,1),  ShapeFactory.factory().createPoint(0,-1).rotatePoint(pt, -Math.PI));

		assertEquals(ShapeFactory.factory().createPoint(0,-1), ShapeFactory.factory().createPoint(1,0).rotatePoint(pt, 3.*Math.PI/2.));
		assertEquals(ShapeFactory.factory().createPoint(1,0),  ShapeFactory.factory().createPoint(0,1).rotatePoint(pt, 3.*Math.PI/2.));
		assertEquals(ShapeFactory.factory().createPoint(0,1),  ShapeFactory.factory().createPoint(-1,0).rotatePoint(pt, 3.*Math.PI/2.));
		assertEquals(ShapeFactory.factory().createPoint(-1,0), ShapeFactory.factory().createPoint(0,-1).rotatePoint(pt, 3.*Math.PI/2.));

		assertEquals(ShapeFactory.factory().createPoint(0,-1), ShapeFactory.factory().createPoint(1,0).rotatePoint(pt, -Math.PI/2.));
		assertEquals(ShapeFactory.factory().createPoint(1,0),  ShapeFactory.factory().createPoint(0,1).rotatePoint(pt, -Math.PI/2.));
		assertEquals(ShapeFactory.factory().createPoint(0,1),  ShapeFactory.factory().createPoint(-1,0).rotatePoint(pt, -Math.PI/2.));
		assertEquals(ShapeFactory.factory().createPoint(-1,0), ShapeFactory.factory().createPoint(0,-1).rotatePoint(pt, -Math.PI/2.));

		assertEquals(ShapeFactory.factory().createPoint(1,0),  ShapeFactory.factory().createPoint(1,0).rotatePoint(pt, 2.*Math.PI));
		assertEquals(ShapeFactory.factory().createPoint(0,1),  ShapeFactory.factory().createPoint(0,1).rotatePoint(pt, 2.*Math.PI));
		assertEquals(ShapeFactory.factory().createPoint(-1,0), ShapeFactory.factory().createPoint(-1,0).rotatePoint(pt, 2.*Math.PI));
		assertEquals(ShapeFactory.factory().createPoint(0,-1), ShapeFactory.factory().createPoint(0,-1).rotatePoint(pt, 2.*Math.PI));

		assertEquals(ShapeFactory.factory().createPoint(1,0),  ShapeFactory.factory().createPoint(1,0).rotatePoint(pt, -2.*Math.PI));
		assertEquals(ShapeFactory.factory().createPoint(0,1),  ShapeFactory.factory().createPoint(0,1).rotatePoint(pt, -2.*Math.PI));
		assertEquals(ShapeFactory.factory().createPoint(-1,0), ShapeFactory.factory().createPoint(-1,0).rotatePoint(pt, -2.*Math.PI));
		assertEquals(ShapeFactory.factory().createPoint(0,-1), ShapeFactory.factory().createPoint(0,-1).rotatePoint(pt, -2.*Math.PI));

		assertEquals(ShapeFactory.factory().createPoint(1,0),  ShapeFactory.factory().createPoint(1,0).rotatePoint(pt, 0));
		assertEquals(ShapeFactory.factory().createPoint(0,1),  ShapeFactory.factory().createPoint(0,1).rotatePoint(pt, 0));
		assertEquals(ShapeFactory.factory().createPoint(-1,0), ShapeFactory.factory().createPoint(-1,0).rotatePoint(pt, 0));
		assertEquals(ShapeFactory.factory().createPoint(0,-1), ShapeFactory.factory().createPoint(0,-1).rotatePoint(pt, 0));
	}



	@Test
	public void testCentralSymmetry() {
		IPoint pt = ShapeFactory.factory().createPoint();
		assertNull(pt.centralSymmetry(null));
		assertEquals(ShapeFactory.factory().createPoint(-1,0),  ShapeFactory.factory().createPoint(1,0).centralSymmetry(pt));
		assertEquals(ShapeFactory.factory().createPoint(0,-1),  ShapeFactory.factory().createPoint(0,1).centralSymmetry(pt));
		assertEquals(ShapeFactory.factory().createPoint(1,0),  ShapeFactory.factory().createPoint(-1,0).centralSymmetry(pt));
		assertEquals(ShapeFactory.factory().createPoint(0,1),  ShapeFactory.factory().createPoint(0,-1).centralSymmetry(pt));
	}


	@Test
	public void testComputeAngle() {
		IPoint pt1 = ShapeFactory.factory().createPoint();

		assertEquals(Double.NaN, pt1.computeAngle(null));
		assertEquals(0., pt1.computeAngle(ShapeFactory.factory().createPoint(1,0)));
		assertEquals(Math.PI/2., pt1.computeAngle(ShapeFactory.factory().createPoint(0,1)));
		assertEquals(Math.PI, pt1.computeAngle(ShapeFactory.factory().createPoint(-1,0)));
		assertEquals(3.*Math.PI/2., pt1.computeAngle(ShapeFactory.factory().createPoint(0,-1)));

		pt1.setPoint(1, 1);
		assertEquals(0., pt1.computeAngle(ShapeFactory.factory().createPoint(2,1)));
		assertEquals(Math.PI/2., pt1.computeAngle(ShapeFactory.factory().createPoint(1,2)));
		assertEquals(Math.PI, pt1.computeAngle(ShapeFactory.factory().createPoint(0,1)));
		assertEquals(3.*Math.PI/2., pt1.computeAngle(ShapeFactory.factory().createPoint(1,0)));
	}


	@Test
	public void testComputeRotationAngle() {
		IPoint pt1 = ShapeFactory.factory().createPoint();

		assertEquals(Double.NaN, pt1.computeRotationAngle(null, null));
		assertEquals(Double.NaN, pt1.computeRotationAngle(ShapeFactory.factory().createPoint(), null));
		assertEquals(Double.NaN, pt1.computeRotationAngle(null, ShapeFactory.factory().createPoint()));

		assertEquals(0., pt1.computeRotationAngle(ShapeFactory.factory().createPoint(1,0), ShapeFactory.factory().createPoint(1,0)));
		assertEquals(0., pt1.computeRotationAngle(ShapeFactory.factory().createPoint(0,1), ShapeFactory.factory().createPoint(0,1)));
		assertEquals(0., pt1.computeRotationAngle(ShapeFactory.factory().createPoint(-1,0), ShapeFactory.factory().createPoint(-1,0)));
		assertEquals(0., pt1.computeRotationAngle(ShapeFactory.factory().createPoint(0,-1), ShapeFactory.factory().createPoint(0,-1)));

		assertEquals(Math.PI/2., pt1.computeRotationAngle(ShapeFactory.factory().createPoint(1,0), ShapeFactory.factory().createPoint(0,1)));
		assertEquals(Math.PI, Math.abs(pt1.computeRotationAngle(ShapeFactory.factory().createPoint(1,0), ShapeFactory.factory().createPoint(-1,0))));
		assertEquals(3.*Math.PI/2., pt1.computeRotationAngle(ShapeFactory.factory().createPoint(1,0), ShapeFactory.factory().createPoint(0,-1)));

		assertEquals(Math.PI/2., pt1.computeRotationAngle(ShapeFactory.factory().createPoint(0,1), ShapeFactory.factory().createPoint(-1,0)));
		assertEquals(Math.PI, Math.abs(pt1.computeRotationAngle(ShapeFactory.factory().createPoint(0,1), ShapeFactory.factory().createPoint(0,-1))));
		assertEquals(-Math.PI/2., pt1.computeRotationAngle(ShapeFactory.factory().createPoint(0,1), ShapeFactory.factory().createPoint(1,0)));

		assertEquals(Math.PI/2., pt1.computeRotationAngle(ShapeFactory.factory().createPoint(-1,0), ShapeFactory.factory().createPoint(0,-1)));
		assertEquals(Math.PI, Math.abs(pt1.computeRotationAngle(ShapeFactory.factory().createPoint(-1,0), ShapeFactory.factory().createPoint(1,0))));
		assertEquals(-Math.PI/2., pt1.computeRotationAngle(ShapeFactory.factory().createPoint(-1,0), ShapeFactory.factory().createPoint(0,1)));

		assertEquals(-3.*Math.PI/2., pt1.computeRotationAngle(ShapeFactory.factory().createPoint(0,-1), ShapeFactory.factory().createPoint(1,0)));
		assertEquals(Math.PI, Math.abs(pt1.computeRotationAngle(ShapeFactory.factory().createPoint(0,-1), ShapeFactory.factory().createPoint(0,1))));
		assertEquals(-Math.PI/2., pt1.computeRotationAngle(ShapeFactory.factory().createPoint(0,-1), ShapeFactory.factory().createPoint(-1,0)));
	}



	@Test
	public void testEquals() {
		assertFalse(ShapeFactory.factory().createPoint().equals(null, 0));
		assertFalse(ShapeFactory.factory().createPoint().equals(ShapeFactory.factory().createPoint(), Double.NaN));
		assertFalse(ShapeFactory.factory().createPoint().equals(ShapeFactory.factory().createPoint(), Double.POSITIVE_INFINITY));
		assertFalse(ShapeFactory.factory().createPoint().equals(ShapeFactory.factory().createPoint(), Double.NEGATIVE_INFINITY));
		assertFalse(ShapeFactory.factory().createPoint(0,0).equals(ShapeFactory.factory().createPoint(1,1), 0));
		assertFalse(ShapeFactory.factory().createPoint(0,0).equals(ShapeFactory.factory().createPoint(-1,-1), 0));
		assertFalse(ShapeFactory.factory().createPoint(0,0).equals(ShapeFactory.factory().createPoint(10,10), 9));
		assertFalse(ShapeFactory.factory().createPoint(0,0).equals(ShapeFactory.factory().createPoint(-10,-10), 9));
		assertFalse(ShapeFactory.factory().createPoint(0,0).equals(ShapeFactory.factory().createPoint(Double.NaN,0), 1));
		assertFalse(ShapeFactory.factory().createPoint(0,0).equals(ShapeFactory.factory().createPoint(0,Double.NaN), 1));
		assertFalse(ShapeFactory.factory().createPoint(0,0).equals(ShapeFactory.factory().createPoint(Double.POSITIVE_INFINITY,0), 1));
		assertFalse(ShapeFactory.factory().createPoint(0,0).equals(ShapeFactory.factory().createPoint(0,Double.POSITIVE_INFINITY), 1));
		assertFalse(ShapeFactory.factory().createPoint(0,0).equals(ShapeFactory.factory().createPoint(Double.NEGATIVE_INFINITY,0), 1));
		assertFalse(ShapeFactory.factory().createPoint(0,0).equals(ShapeFactory.factory().createPoint(0,Double.NEGATIVE_INFINITY), 1));
		assertFalse(ShapeFactory.factory().createPoint(Double.NaN,0).equals(ShapeFactory.factory().createPoint(0,0), 1));
		assertFalse(ShapeFactory.factory().createPoint(0,Double.NaN).equals(ShapeFactory.factory().createPoint(0,0), 1));
		assertFalse(ShapeFactory.factory().createPoint(Double.POSITIVE_INFINITY,0).equals(ShapeFactory.factory().createPoint(0,0), 1));
		assertFalse(ShapeFactory.factory().createPoint(0,Double.POSITIVE_INFINITY).equals(ShapeFactory.factory().createPoint(0,0), 1));
		assertFalse(ShapeFactory.factory().createPoint(Double.NEGATIVE_INFINITY,0).equals(ShapeFactory.factory().createPoint(0,0), 1));
		assertFalse(ShapeFactory.factory().createPoint(0,Double.NEGATIVE_INFINITY).equals(ShapeFactory.factory().createPoint(0,0), 1));

		assertTrue(ShapeFactory.factory().createPoint(0,0).equals(ShapeFactory.factory().createPoint(0,0), 0));
		assertTrue(ShapeFactory.factory().createPoint(-1,-1).equals(ShapeFactory.factory().createPoint(-1,-1), 0));
		assertTrue(ShapeFactory.factory().createPoint(1,1).equals(ShapeFactory.factory().createPoint(10,10), 9));
		assertTrue(ShapeFactory.factory().createPoint(-1,-1).equals(ShapeFactory.factory().createPoint(-10,-10), 9));
		assertTrue(ShapeFactory.factory().createPoint(0,0).equals(ShapeFactory.factory().createPoint(0.000001,0), 0.0001));
		assertTrue(ShapeFactory.factory().createPoint(0,0).equals(ShapeFactory.factory().createPoint(0, 0.000001), 0.0001));
	}


	@Test
	public void testGetMiddlePoint() {
		assertNull(ShapeFactory.factory().createPoint().getMiddlePoint(null));
		assertEquals(ShapeFactory.factory().createPoint(10,0).getMiddlePoint(ShapeFactory.factory().createPoint(-10,0)), ShapeFactory.factory().createPoint(0,0));
		assertEquals(ShapeFactory.factory().createPoint(0,10).getMiddlePoint(ShapeFactory.factory().createPoint(0,-10)), ShapeFactory.factory().createPoint(0,0));
		assertEquals(ShapeFactory.factory().createPoint(10,10).getMiddlePoint(ShapeFactory.factory().createPoint(-10,-10)), ShapeFactory.factory().createPoint(0,0));
		assertEquals(ShapeFactory.factory().createPoint(0,0).getMiddlePoint(ShapeFactory.factory().createPoint(5,6)), ShapeFactory.factory().createPoint(2.5,3));
		assertEquals(ShapeFactory.factory().createPoint(0,0).getMiddlePoint(ShapeFactory.factory().createPoint(-5,-6)), ShapeFactory.factory().createPoint(-2.5,-3));
		assertEquals(ShapeFactory.factory().createPoint(Double.POSITIVE_INFINITY,10).getMiddlePoint(ShapeFactory.factory().createPoint(-10,-10)), ShapeFactory.factory().createPoint(Double.POSITIVE_INFINITY,0));
		assertEquals(ShapeFactory.factory().createPoint(Double.NEGATIVE_INFINITY,10).getMiddlePoint(ShapeFactory.factory().createPoint(-10,-10)), ShapeFactory.factory().createPoint(Double.NEGATIVE_INFINITY,0));
		assertEquals(ShapeFactory.factory().createPoint(10, Double.POSITIVE_INFINITY).getMiddlePoint(ShapeFactory.factory().createPoint(-10,-10)), ShapeFactory.factory().createPoint(0, Double.POSITIVE_INFINITY));
		assertEquals(ShapeFactory.factory().createPoint(10, Double.NEGATIVE_INFINITY).getMiddlePoint(ShapeFactory.factory().createPoint(-10,-10)), ShapeFactory.factory().createPoint(0, Double.NEGATIVE_INFINITY));
	}


	@Test
	public void testTranslate() {
		IPoint pt1 = ShapeFactory.factory().createPoint(2., 1.);
		IPoint pt2 = ShapeFactory.factory().createPoint(2., 1.);

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
		assertEquals(pt1, ShapeFactory.factory().createPoint(2., 11.));
		pt1.translate(0., -12.);
		assertEquals(pt1, ShapeFactory.factory().createPoint(2., -1.));
		pt1.translate(5., 0.);
		assertEquals(pt1, ShapeFactory.factory().createPoint(7., -1.));
		pt1.translate(-4., 0.);
		assertEquals(pt1, ShapeFactory.factory().createPoint(3., -1.));
		pt1.translate(3.12, 7.98);
		assertEquals(pt1, ShapeFactory.factory().createPoint(6.12, 6.98));
	}


	@Test
	public void testHorizontalSymmetry() {
		IPoint pt = ShapeFactory.factory().createPoint(10., 10.);

		assertNull(pt.horizontalSymmetry(null));
		assertNull(pt.horizontalSymmetry(ShapeFactory.factory().createPoint(Double.NaN, 1.)));
		assertNull(pt.horizontalSymmetry(ShapeFactory.factory().createPoint(1., Double.NaN)));
		assertNull(pt.horizontalSymmetry(ShapeFactory.factory().createPoint(Double.POSITIVE_INFINITY, 1.)));
		assertNull(pt.horizontalSymmetry(ShapeFactory.factory().createPoint(1., Double.POSITIVE_INFINITY)));
		assertNull(pt.horizontalSymmetry(ShapeFactory.factory().createPoint(Double.NEGATIVE_INFINITY, 1.)));
		assertNull(pt.horizontalSymmetry(ShapeFactory.factory().createPoint(1., Double.NEGATIVE_INFINITY)));
		assertEquals(pt.horizontalSymmetry(ShapeFactory.factory().createPoint(0.,0.)), ShapeFactory.factory().createPoint(-10., 10.));
		assertEquals(pt.horizontalSymmetry(ShapeFactory.factory().createPoint(0.,18780.)), ShapeFactory.factory().createPoint(-10., 10.));
		assertEquals(ShapeFactory.factory().createPoint(-10., 10.).horizontalSymmetry(ShapeFactory.factory().createPoint(0.,18780.)), pt);
		assertEquals(ShapeFactory.factory().createPoint(0.,0.).horizontalSymmetry(ShapeFactory.factory().createPoint(0.,0.)), ShapeFactory.factory().createPoint(0., 0.));
	}


	@Test
	public void testVerticalSymmetry() {
		IPoint pt = ShapeFactory.factory().createPoint(10., 10.);

		assertNull(pt.verticalSymmetry(null));
		assertNull(pt.verticalSymmetry(ShapeFactory.factory().createPoint(Double.NaN, 1.)));
		assertNull(pt.verticalSymmetry(ShapeFactory.factory().createPoint(1., Double.NaN)));
		assertNull(pt.verticalSymmetry(ShapeFactory.factory().createPoint(Double.POSITIVE_INFINITY, 1.)));
		assertNull(pt.verticalSymmetry(ShapeFactory.factory().createPoint(1., Double.POSITIVE_INFINITY)));
		assertNull(pt.verticalSymmetry(ShapeFactory.factory().createPoint(Double.NEGATIVE_INFINITY, 1.)));
		assertNull(pt.verticalSymmetry(ShapeFactory.factory().createPoint(1., Double.NEGATIVE_INFINITY)));
		assertEquals(pt.verticalSymmetry(ShapeFactory.factory().createPoint(0.,0.)), ShapeFactory.factory().createPoint(10., -10.));
		assertEquals(pt.verticalSymmetry(ShapeFactory.factory().createPoint(18780., 0.)), ShapeFactory.factory().createPoint(10., -10.));
		assertEquals(ShapeFactory.factory().createPoint(10., -10.).verticalSymmetry(ShapeFactory.factory().createPoint(18780., 0.)), pt);
		assertEquals(ShapeFactory.factory().createPoint(0.,0.).verticalSymmetry(ShapeFactory.factory().createPoint(0.,0.)), ShapeFactory.factory().createPoint(0., 0.));
	}
}
