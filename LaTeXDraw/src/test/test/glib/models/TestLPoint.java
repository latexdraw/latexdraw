package test.glib.models;

import junit.framework.TestCase;
import net.sf.latexdraw.glib.models.impl.LShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IPoint;

import org.junit.Before;
import org.junit.Test;

public class TestLPoint extends TestCase {
	@Override
	@Before
	public void setUp() {
		DrawingTK.setFactory(new LShapeFactory());
	}


	@Test
	public void testLPoint() {
		IPoint pt = DrawingTK.getFactory().createPoint(1.,-200.);

		assertEquals(DrawingTK.getFactory().createPoint(), DrawingTK.getFactory().createPoint(0.,0.));
		assertEquals(pt.getX(), 1.);
		assertEquals(pt.getY(), -200.);

		pt = DrawingTK.getFactory().createPoint((IPoint)null);
		assertEquals(pt.getX(), 0.);
		assertEquals(pt.getY(), 0.);

		pt = DrawingTK.getFactory().createPoint(DrawingTK.getFactory().createPoint(-1.34, Double.POSITIVE_INFINITY));
		assertEquals(pt.getX(), -1.34);
		assertEquals(pt.getY(), Double.POSITIVE_INFINITY);
	}


	@Test
	public void testRotatePoint() {
		IPoint pt = DrawingTK.getFactory().createPoint();

		assertNull(pt.rotatePoint( null, 0));
		assertNull(pt.rotatePoint(null, 0));
		assertNull(pt.rotatePoint(DrawingTK.getFactory().createPoint(), Double.NaN));
		assertNull(pt.rotatePoint(DrawingTK.getFactory().createPoint(), Double.NEGATIVE_INFINITY));
		assertNull(pt.rotatePoint(DrawingTK.getFactory().createPoint(), Double.POSITIVE_INFINITY));

		assertEquals(DrawingTK.getFactory().createPoint(0,1),  DrawingTK.getFactory().createPoint(1,0).rotatePoint(DrawingTK.getFactory().createPoint(), Math.PI/2.));
		assertEquals(DrawingTK.getFactory().createPoint(-1,0), DrawingTK.getFactory().createPoint(0,1).rotatePoint(DrawingTK.getFactory().createPoint(), Math.PI/2.));
		assertEquals(DrawingTK.getFactory().createPoint(0,-1), DrawingTK.getFactory().createPoint(-1,0).rotatePoint(DrawingTK.getFactory().createPoint(), Math.PI/2.));
		assertEquals(DrawingTK.getFactory().createPoint(1,0),  DrawingTK.getFactory().createPoint(0,-1).rotatePoint(DrawingTK.getFactory().createPoint(), Math.PI/2.));

		assertEquals(DrawingTK.getFactory().createPoint(0,1),  DrawingTK.getFactory().createPoint(1,0).rotatePoint(pt, -3.*Math.PI/2.));
		assertEquals(DrawingTK.getFactory().createPoint(-1,0), DrawingTK.getFactory().createPoint(0,1).rotatePoint(pt, -3.*Math.PI/2.));
		assertEquals(DrawingTK.getFactory().createPoint(0,-1), DrawingTK.getFactory().createPoint(-1,0).rotatePoint(pt, -3.*Math.PI/2.));
		assertEquals(DrawingTK.getFactory().createPoint(1,0),  DrawingTK.getFactory().createPoint(0,-1).rotatePoint(pt, -3.*Math.PI/2.));

		assertEquals(DrawingTK.getFactory().createPoint(-1,0), DrawingTK.getFactory().createPoint(1,0).rotatePoint(pt, Math.PI));
		assertEquals(DrawingTK.getFactory().createPoint(0,-1), DrawingTK.getFactory().createPoint(0,1).rotatePoint(pt, Math.PI));
		assertEquals(DrawingTK.getFactory().createPoint(1,0),  DrawingTK.getFactory().createPoint(-1,0).rotatePoint(pt, Math.PI));
		assertEquals(DrawingTK.getFactory().createPoint(0,1),  DrawingTK.getFactory().createPoint(0,-1).rotatePoint(pt, Math.PI));

		assertEquals(DrawingTK.getFactory().createPoint(-1,0), DrawingTK.getFactory().createPoint(1,0).rotatePoint(pt, -Math.PI));
		assertEquals(DrawingTK.getFactory().createPoint(0,-1), DrawingTK.getFactory().createPoint(0,1).rotatePoint(pt, -Math.PI));
		assertEquals(DrawingTK.getFactory().createPoint(1,0),  DrawingTK.getFactory().createPoint(-1,0).rotatePoint(pt, -Math.PI));
		assertEquals(DrawingTK.getFactory().createPoint(0,1),  DrawingTK.getFactory().createPoint(0,-1).rotatePoint(pt, -Math.PI));

		assertEquals(DrawingTK.getFactory().createPoint(0,-1), DrawingTK.getFactory().createPoint(1,0).rotatePoint(pt, 3.*Math.PI/2.));
		assertEquals(DrawingTK.getFactory().createPoint(1,0),  DrawingTK.getFactory().createPoint(0,1).rotatePoint(pt, 3.*Math.PI/2.));
		assertEquals(DrawingTK.getFactory().createPoint(0,1),  DrawingTK.getFactory().createPoint(-1,0).rotatePoint(pt, 3.*Math.PI/2.));
		assertEquals(DrawingTK.getFactory().createPoint(-1,0), DrawingTK.getFactory().createPoint(0,-1).rotatePoint(pt, 3.*Math.PI/2.));

		assertEquals(DrawingTK.getFactory().createPoint(0,-1), DrawingTK.getFactory().createPoint(1,0).rotatePoint(pt, -Math.PI/2.));
		assertEquals(DrawingTK.getFactory().createPoint(1,0),  DrawingTK.getFactory().createPoint(0,1).rotatePoint(pt, -Math.PI/2.));
		assertEquals(DrawingTK.getFactory().createPoint(0,1),  DrawingTK.getFactory().createPoint(-1,0).rotatePoint(pt, -Math.PI/2.));
		assertEquals(DrawingTK.getFactory().createPoint(-1,0), DrawingTK.getFactory().createPoint(0,-1).rotatePoint(pt, -Math.PI/2.));

		assertEquals(DrawingTK.getFactory().createPoint(1,0),  DrawingTK.getFactory().createPoint(1,0).rotatePoint(pt, 2.*Math.PI));
		assertEquals(DrawingTK.getFactory().createPoint(0,1),  DrawingTK.getFactory().createPoint(0,1).rotatePoint(pt, 2.*Math.PI));
		assertEquals(DrawingTK.getFactory().createPoint(-1,0), DrawingTK.getFactory().createPoint(-1,0).rotatePoint(pt, 2.*Math.PI));
		assertEquals(DrawingTK.getFactory().createPoint(0,-1), DrawingTK.getFactory().createPoint(0,-1).rotatePoint(pt, 2.*Math.PI));

		assertEquals(DrawingTK.getFactory().createPoint(1,0),  DrawingTK.getFactory().createPoint(1,0).rotatePoint(pt, -2.*Math.PI));
		assertEquals(DrawingTK.getFactory().createPoint(0,1),  DrawingTK.getFactory().createPoint(0,1).rotatePoint(pt, -2.*Math.PI));
		assertEquals(DrawingTK.getFactory().createPoint(-1,0), DrawingTK.getFactory().createPoint(-1,0).rotatePoint(pt, -2.*Math.PI));
		assertEquals(DrawingTK.getFactory().createPoint(0,-1), DrawingTK.getFactory().createPoint(0,-1).rotatePoint(pt, -2.*Math.PI));

		assertEquals(DrawingTK.getFactory().createPoint(1,0),  DrawingTK.getFactory().createPoint(1,0).rotatePoint(pt, 0));
		assertEquals(DrawingTK.getFactory().createPoint(0,1),  DrawingTK.getFactory().createPoint(0,1).rotatePoint(pt, 0));
		assertEquals(DrawingTK.getFactory().createPoint(-1,0), DrawingTK.getFactory().createPoint(-1,0).rotatePoint(pt, 0));
		assertEquals(DrawingTK.getFactory().createPoint(0,-1), DrawingTK.getFactory().createPoint(0,-1).rotatePoint(pt, 0));
	}



	@Test
	public void testCentralSymmetry() {
		IPoint pt = DrawingTK.getFactory().createPoint();
		assertNull(pt.centralSymmetry(null));
		assertEquals(DrawingTK.getFactory().createPoint(-1,0),  DrawingTK.getFactory().createPoint(1,0).centralSymmetry(pt));
		assertEquals(DrawingTK.getFactory().createPoint(0,-1),  DrawingTK.getFactory().createPoint(0,1).centralSymmetry(pt));
		assertEquals(DrawingTK.getFactory().createPoint(1,0),  DrawingTK.getFactory().createPoint(-1,0).centralSymmetry(pt));
		assertEquals(DrawingTK.getFactory().createPoint(0,1),  DrawingTK.getFactory().createPoint(0,-1).centralSymmetry(pt));
	}


	@Test
	public void testComputeAngle() {
		IPoint pt1 = DrawingTK.getFactory().createPoint();

		assertEquals(Double.NaN, pt1.computeAngle(null));
		assertEquals(0., pt1.computeAngle(DrawingTK.getFactory().createPoint(1,0)));
		assertEquals(Math.PI/2., pt1.computeAngle(DrawingTK.getFactory().createPoint(0,1)));
		assertEquals(Math.PI, pt1.computeAngle(DrawingTK.getFactory().createPoint(-1,0)));
		assertEquals(3.*Math.PI/2., pt1.computeAngle(DrawingTK.getFactory().createPoint(0,-1)));

		pt1.setPoint(1, 1);
		assertEquals(0., pt1.computeAngle(DrawingTK.getFactory().createPoint(2,1)));
		assertEquals(Math.PI/2., pt1.computeAngle(DrawingTK.getFactory().createPoint(1,2)));
		assertEquals(Math.PI, pt1.computeAngle(DrawingTK.getFactory().createPoint(0,1)));
		assertEquals(3.*Math.PI/2., pt1.computeAngle(DrawingTK.getFactory().createPoint(1,0)));
	}


	@Test
	public void testComputeRotationAngle() {
		IPoint pt1 = DrawingTK.getFactory().createPoint();

		assertEquals(Double.NaN, pt1.computeRotationAngle(null, null));
		assertEquals(Double.NaN, pt1.computeRotationAngle(DrawingTK.getFactory().createPoint(), null));
		assertEquals(Double.NaN, pt1.computeRotationAngle(null, DrawingTK.getFactory().createPoint()));

		assertEquals(0., pt1.computeRotationAngle(DrawingTK.getFactory().createPoint(1,0), DrawingTK.getFactory().createPoint(1,0)));
		assertEquals(0., pt1.computeRotationAngle(DrawingTK.getFactory().createPoint(0,1), DrawingTK.getFactory().createPoint(0,1)));
		assertEquals(0., pt1.computeRotationAngle(DrawingTK.getFactory().createPoint(-1,0), DrawingTK.getFactory().createPoint(-1,0)));
		assertEquals(0., pt1.computeRotationAngle(DrawingTK.getFactory().createPoint(0,-1), DrawingTK.getFactory().createPoint(0,-1)));

		assertEquals(Math.PI/2., pt1.computeRotationAngle(DrawingTK.getFactory().createPoint(1,0), DrawingTK.getFactory().createPoint(0,1)));
		assertEquals(Math.PI, Math.abs(pt1.computeRotationAngle(DrawingTK.getFactory().createPoint(1,0), DrawingTK.getFactory().createPoint(-1,0))));
		assertEquals(3.*Math.PI/2., pt1.computeRotationAngle(DrawingTK.getFactory().createPoint(1,0), DrawingTK.getFactory().createPoint(0,-1)));

		assertEquals(Math.PI/2., pt1.computeRotationAngle(DrawingTK.getFactory().createPoint(0,1), DrawingTK.getFactory().createPoint(-1,0)));
		assertEquals(Math.PI, Math.abs(pt1.computeRotationAngle(DrawingTK.getFactory().createPoint(0,1), DrawingTK.getFactory().createPoint(0,-1))));
		assertEquals(-Math.PI/2., pt1.computeRotationAngle(DrawingTK.getFactory().createPoint(0,1), DrawingTK.getFactory().createPoint(1,0)));

		assertEquals(Math.PI/2., pt1.computeRotationAngle(DrawingTK.getFactory().createPoint(-1,0), DrawingTK.getFactory().createPoint(0,-1)));
		assertEquals(Math.PI, Math.abs(pt1.computeRotationAngle(DrawingTK.getFactory().createPoint(-1,0), DrawingTK.getFactory().createPoint(1,0))));
		assertEquals(-Math.PI/2., pt1.computeRotationAngle(DrawingTK.getFactory().createPoint(-1,0), DrawingTK.getFactory().createPoint(0,1)));

		assertEquals(-3.*Math.PI/2., pt1.computeRotationAngle(DrawingTK.getFactory().createPoint(0,-1), DrawingTK.getFactory().createPoint(1,0)));
		assertEquals(Math.PI, Math.abs(pt1.computeRotationAngle(DrawingTK.getFactory().createPoint(0,-1), DrawingTK.getFactory().createPoint(0,1))));
		assertEquals(-Math.PI/2., pt1.computeRotationAngle(DrawingTK.getFactory().createPoint(0,-1), DrawingTK.getFactory().createPoint(-1,0)));
	}



	@Test
	public void testEquals() {
		assertFalse(DrawingTK.getFactory().createPoint().equals(null, 0));
		assertFalse(DrawingTK.getFactory().createPoint().equals(DrawingTK.getFactory().createPoint(), Double.NaN));
		assertFalse(DrawingTK.getFactory().createPoint().equals(DrawingTK.getFactory().createPoint(), Double.POSITIVE_INFINITY));
		assertFalse(DrawingTK.getFactory().createPoint().equals(DrawingTK.getFactory().createPoint(), Double.NEGATIVE_INFINITY));
		assertFalse(DrawingTK.getFactory().createPoint(0,0).equals(DrawingTK.getFactory().createPoint(1,1), 0));
		assertFalse(DrawingTK.getFactory().createPoint(0,0).equals(DrawingTK.getFactory().createPoint(-1,-1), 0));
		assertFalse(DrawingTK.getFactory().createPoint(0,0).equals(DrawingTK.getFactory().createPoint(10,10), 9));
		assertFalse(DrawingTK.getFactory().createPoint(0,0).equals(DrawingTK.getFactory().createPoint(-10,-10), 9));
		assertFalse(DrawingTK.getFactory().createPoint(0,0).equals(DrawingTK.getFactory().createPoint(Double.NaN,0), 1));
		assertFalse(DrawingTK.getFactory().createPoint(0,0).equals(DrawingTK.getFactory().createPoint(0,Double.NaN), 1));
		assertFalse(DrawingTK.getFactory().createPoint(0,0).equals(DrawingTK.getFactory().createPoint(Double.POSITIVE_INFINITY,0), 1));
		assertFalse(DrawingTK.getFactory().createPoint(0,0).equals(DrawingTK.getFactory().createPoint(0,Double.POSITIVE_INFINITY), 1));
		assertFalse(DrawingTK.getFactory().createPoint(0,0).equals(DrawingTK.getFactory().createPoint(Double.NEGATIVE_INFINITY,0), 1));
		assertFalse(DrawingTK.getFactory().createPoint(0,0).equals(DrawingTK.getFactory().createPoint(0,Double.NEGATIVE_INFINITY), 1));
		assertFalse(DrawingTK.getFactory().createPoint(Double.NaN,0).equals(DrawingTK.getFactory().createPoint(0,0), 1));
		assertFalse(DrawingTK.getFactory().createPoint(0,Double.NaN).equals(DrawingTK.getFactory().createPoint(0,0), 1));
		assertFalse(DrawingTK.getFactory().createPoint(Double.POSITIVE_INFINITY,0).equals(DrawingTK.getFactory().createPoint(0,0), 1));
		assertFalse(DrawingTK.getFactory().createPoint(0,Double.POSITIVE_INFINITY).equals(DrawingTK.getFactory().createPoint(0,0), 1));
		assertFalse(DrawingTK.getFactory().createPoint(Double.NEGATIVE_INFINITY,0).equals(DrawingTK.getFactory().createPoint(0,0), 1));
		assertFalse(DrawingTK.getFactory().createPoint(0,Double.NEGATIVE_INFINITY).equals(DrawingTK.getFactory().createPoint(0,0), 1));

		assertTrue(DrawingTK.getFactory().createPoint(0,0).equals(DrawingTK.getFactory().createPoint(0,0), 0));
		assertTrue(DrawingTK.getFactory().createPoint(-1,-1).equals(DrawingTK.getFactory().createPoint(-1,-1), 0));
		assertTrue(DrawingTK.getFactory().createPoint(1,1).equals(DrawingTK.getFactory().createPoint(10,10), 9));
		assertTrue(DrawingTK.getFactory().createPoint(-1,-1).equals(DrawingTK.getFactory().createPoint(-10,-10), 9));
		assertTrue(DrawingTK.getFactory().createPoint(0,0).equals(DrawingTK.getFactory().createPoint(0.000001,0), 0.0001));
		assertTrue(DrawingTK.getFactory().createPoint(0,0).equals(DrawingTK.getFactory().createPoint(0, 0.000001), 0.0001));
	}


	@Test
	public void testGetMiddlePoint() {
		assertNull(DrawingTK.getFactory().createPoint().getMiddlePoint(null));
		assertEquals(DrawingTK.getFactory().createPoint(10,0).getMiddlePoint(DrawingTK.getFactory().createPoint(-10,0)), DrawingTK.getFactory().createPoint(0,0));
		assertEquals(DrawingTK.getFactory().createPoint(0,10).getMiddlePoint(DrawingTK.getFactory().createPoint(0,-10)), DrawingTK.getFactory().createPoint(0,0));
		assertEquals(DrawingTK.getFactory().createPoint(10,10).getMiddlePoint(DrawingTK.getFactory().createPoint(-10,-10)), DrawingTK.getFactory().createPoint(0,0));
		assertEquals(DrawingTK.getFactory().createPoint(0,0).getMiddlePoint(DrawingTK.getFactory().createPoint(5,6)), DrawingTK.getFactory().createPoint(2.5,3));
		assertEquals(DrawingTK.getFactory().createPoint(0,0).getMiddlePoint(DrawingTK.getFactory().createPoint(-5,-6)), DrawingTK.getFactory().createPoint(-2.5,-3));
		assertEquals(DrawingTK.getFactory().createPoint(Double.POSITIVE_INFINITY,10).getMiddlePoint(DrawingTK.getFactory().createPoint(-10,-10)), DrawingTK.getFactory().createPoint(Double.POSITIVE_INFINITY,0));
		assertEquals(DrawingTK.getFactory().createPoint(Double.NEGATIVE_INFINITY,10).getMiddlePoint(DrawingTK.getFactory().createPoint(-10,-10)), DrawingTK.getFactory().createPoint(Double.NEGATIVE_INFINITY,0));
		assertEquals(DrawingTK.getFactory().createPoint(10, Double.POSITIVE_INFINITY).getMiddlePoint(DrawingTK.getFactory().createPoint(-10,-10)), DrawingTK.getFactory().createPoint(0, Double.POSITIVE_INFINITY));
		assertEquals(DrawingTK.getFactory().createPoint(10, Double.NEGATIVE_INFINITY).getMiddlePoint(DrawingTK.getFactory().createPoint(-10,-10)), DrawingTK.getFactory().createPoint(0, Double.NEGATIVE_INFINITY));
	}


	@Test
	public void testTranslate() {
		IPoint pt1 = DrawingTK.getFactory().createPoint(2., 1.);
		IPoint pt2 = DrawingTK.getFactory().createPoint(2., 1.);

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
		assertEquals(pt1, DrawingTK.getFactory().createPoint(2., 11.));
		pt1.translate(0., -12.);
		assertEquals(pt1, DrawingTK.getFactory().createPoint(2., -1.));
		pt1.translate(5., 0.);
		assertEquals(pt1, DrawingTK.getFactory().createPoint(7., -1.));
		pt1.translate(-4., 0.);
		assertEquals(pt1, DrawingTK.getFactory().createPoint(3., -1.));
		pt1.translate(3.12, 7.98);
		assertEquals(pt1, DrawingTK.getFactory().createPoint(6.12, 6.98));
	}


	@Test
	public void testHorizontalSymmetry() {
		IPoint pt = DrawingTK.getFactory().createPoint(10., 10.);

		assertNull(pt.horizontalSymmetry(null));
		assertNull(pt.horizontalSymmetry(DrawingTK.getFactory().createPoint(Double.NaN, 1.)));
		assertNull(pt.horizontalSymmetry(DrawingTK.getFactory().createPoint(1., Double.NaN)));
		assertNull(pt.horizontalSymmetry(DrawingTK.getFactory().createPoint(Double.POSITIVE_INFINITY, 1.)));
		assertNull(pt.horizontalSymmetry(DrawingTK.getFactory().createPoint(1., Double.POSITIVE_INFINITY)));
		assertNull(pt.horizontalSymmetry(DrawingTK.getFactory().createPoint(Double.NEGATIVE_INFINITY, 1.)));
		assertNull(pt.horizontalSymmetry(DrawingTK.getFactory().createPoint(1., Double.NEGATIVE_INFINITY)));
		assertEquals(pt.horizontalSymmetry(DrawingTK.getFactory().createPoint(0.,0.)), DrawingTK.getFactory().createPoint(-10., 10.));
		assertEquals(pt.horizontalSymmetry(DrawingTK.getFactory().createPoint(0.,18780.)), DrawingTK.getFactory().createPoint(-10., 10.));
		assertEquals(DrawingTK.getFactory().createPoint(-10., 10.).horizontalSymmetry(DrawingTK.getFactory().createPoint(0.,18780.)), pt);
		assertEquals(DrawingTK.getFactory().createPoint(0.,0.).horizontalSymmetry(DrawingTK.getFactory().createPoint(0.,0.)), DrawingTK.getFactory().createPoint(0., 0.));
	}


	@Test
	public void testVerticalSymmetry() {
		IPoint pt = DrawingTK.getFactory().createPoint(10., 10.);

		assertNull(pt.verticalSymmetry(null));
		assertNull(pt.verticalSymmetry(DrawingTK.getFactory().createPoint(Double.NaN, 1.)));
		assertNull(pt.verticalSymmetry(DrawingTK.getFactory().createPoint(1., Double.NaN)));
		assertNull(pt.verticalSymmetry(DrawingTK.getFactory().createPoint(Double.POSITIVE_INFINITY, 1.)));
		assertNull(pt.verticalSymmetry(DrawingTK.getFactory().createPoint(1., Double.POSITIVE_INFINITY)));
		assertNull(pt.verticalSymmetry(DrawingTK.getFactory().createPoint(Double.NEGATIVE_INFINITY, 1.)));
		assertNull(pt.verticalSymmetry(DrawingTK.getFactory().createPoint(1., Double.NEGATIVE_INFINITY)));
		assertEquals(pt.verticalSymmetry(DrawingTK.getFactory().createPoint(0.,0.)), DrawingTK.getFactory().createPoint(10., -10.));
		assertEquals(pt.verticalSymmetry(DrawingTK.getFactory().createPoint(18780., 0.)), DrawingTK.getFactory().createPoint(10., -10.));
		assertEquals(DrawingTK.getFactory().createPoint(10., -10.).verticalSymmetry(DrawingTK.getFactory().createPoint(18780., 0.)), pt);
		assertEquals(DrawingTK.getFactory().createPoint(0.,0.).verticalSymmetry(DrawingTK.getFactory().createPoint(0.,0.)), DrawingTK.getFactory().createPoint(0., 0.));
	}
}
