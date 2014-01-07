package test.glib.models.interfaces;

import junit.framework.TestCase;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.ILine;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.util.LNumber;

import org.junit.Test;

public abstract class TestILine extends TestCase {

	protected ILine line;

	@Test
	public void testLineAngle() {
		line.setLine(0, 100, 100, 100);
		line.updateAandB();

		assertEquals(0., line.getLineAngle());

		line.setLine(0, 100, -100, 100);
		line.updateAandB();

		assertEquals(0., line.getLineAngle());

		line.setLine(0, 100, 0, 200);
		line.updateAandB();

		assertEquals(Math.PI/2., line.getLineAngle());

		line.setLine(0, 100, 0, -200);
		line.updateAandB();

		assertEquals(Math.PI/2., line.getLineAngle());

		line.setLine(100, 100, 100, 100);
		line.updateAandB();

		assertEquals(0., line.getLineAngle());

		line.setLine(92.27, -12028.2, 98700, 982);
		line.updateAandB();

		assertEquals(Math.atan(line.getA()), line.getLineAngle());
	}


	@Test
	public void testGetSetX1() {
		line.setX1(0);
		assertEquals(0., line.getX1());
		line.setX1(10);
		assertEquals(10., line.getX1());
		line.setX1(-10);
		assertEquals(-10., line.getX1());
	}


	@Test
	public void testGetSetX2() {
		line.setX2(0);
		assertEquals(0., line.getX2());
		line.setX2(10);
		assertEquals(10., line.getX2());
		line.setX2(-10);
		assertEquals(-10., line.getX2());
	}


	@Test
	public void testGetSetY1() {
		line.setY1(0);
		assertEquals(0., line.getY1());
		line.setY1(10);
		assertEquals(10., line.getY1());
		line.setY1(-10);
		assertEquals(-10., line.getY1());
	}


	@Test
	public void testGetSetY2() {
		line.setY2(0);
		assertEquals(0., line.getY2());
		line.setY2(10);
		assertEquals(10., line.getY2());
		line.setY2(-10);
		assertEquals(-10., line.getY2());
	}


	@Test
	public void testGetPoint1() {
		assertNotNull(line.getPoint1());
		line.setP1(ShapeFactory.createPoint(100, 200));
		assertEquals(100., line.getPoint1().getX());
		assertEquals(200., line.getPoint1().getY());
		line.setP1(ShapeFactory.createPoint(-300, 400));
		assertEquals(-300., line.getPoint1().getX());
		assertEquals(400., line.getPoint1().getY());
	}


	@Test
	public void testGetPoint2() {
		assertNotNull(line.getPoint1());
		line.setP2(ShapeFactory.createPoint(100, 200));
		assertEquals(100., line.getPoint2().getX());
		assertEquals(200., line.getPoint2().getY());
		line.setP2(ShapeFactory.createPoint(-300, 400));
		assertEquals(-300., line.getPoint2().getX());
		assertEquals(400., line.getPoint2().getY());
	}


	@Test
	public void testSetLine() {
		line.setLine(10, 20, 30, 40);
		assertEquals(10., line.getX1());
		assertEquals(30., line.getX2());
		assertEquals(20., line.getY1());
		assertEquals(40., line.getY2());
		line.setLine(Double.NaN, 21, 31, 41);
		assertEquals(10., line.getX1());
		assertEquals(30., line.getX2());
		assertEquals(20., line.getY1());
		assertEquals(40., line.getY2());
		line.setLine(11, Double.POSITIVE_INFINITY, 31, 41);
		assertEquals(10., line.getX1());
		assertEquals(30., line.getX2());
		assertEquals(20., line.getY1());
		assertEquals(40., line.getY2());
		line.setLine(11, 21, Double.NEGATIVE_INFINITY, 41);
		assertEquals(10., line.getX1());
		assertEquals(30., line.getX2());
		assertEquals(20., line.getY1());
		assertEquals(40., line.getY2());
		line.setLine(11, 21, 31, Double.NaN);
		assertEquals(10., line.getX1());
		assertEquals(30., line.getX2());
		assertEquals(20., line.getY1());
		assertEquals(40., line.getY2());
		line.setLine(-11, -21, -31, -41);
		assertEquals(-11., line.getX1());
		assertEquals(-31., line.getX2());
		assertEquals(-21., line.getY1());
		assertEquals(-41., line.getY2());
		line.setLine(0, 0, 0, 0);
		assertEquals(0., line.getX1());
		assertEquals(0., line.getX2());
		assertEquals(0., line.getY1());
		assertEquals(0., line.getY2());
	}


	@Test
	public void testSetP1IPoint() {
		line.setP1(ShapeFactory.createPoint(20, 30));
		assertEquals(20., line.getX1());
		assertEquals(30., line.getY1());
		line.setP1(null);
		assertNotNull(line.getPoint1());
		assertEquals(20., line.getX1());
		assertEquals(30., line.getY1());
		line.setP1(ShapeFactory.createPoint(Double.NaN, -10));
		assertEquals(20., line.getX1());
		assertEquals(30., line.getY1());
		line.setP1(ShapeFactory.createPoint(Double.POSITIVE_INFINITY, -10));
		assertEquals(20., line.getX1());
		assertEquals(30., line.getY1());
		line.setP1(ShapeFactory.createPoint(Double.NEGATIVE_INFINITY, -10));
		assertEquals(20., line.getX1());
		assertEquals(30., line.getY1());
		line.setP1(ShapeFactory.createPoint(-10, Double.NaN));
		assertEquals(20., line.getX1());
		assertEquals(30., line.getY1());
		line.setP1(ShapeFactory.createPoint(-10, Double.POSITIVE_INFINITY));
		assertEquals(20., line.getX1());
		assertEquals(30., line.getY1());
		line.setP1(ShapeFactory.createPoint(-10, Double.NEGATIVE_INFINITY));
		assertEquals(20., line.getX1());
		assertEquals(30., line.getY1());
	}


	@Test
	public void testSetP2IPoint() {
		line.setP2(ShapeFactory.createPoint(20, 30));
		assertEquals(20., line.getX2());
		assertEquals(30., line.getY2());
		line.setP2(null);
		assertNotNull(line.getPoint2());
		assertEquals(20., line.getX2());
		assertEquals(30., line.getY2());
		line.setP2(ShapeFactory.createPoint(Double.NaN, -20));
		assertEquals(20., line.getX2());
		assertEquals(30., line.getY2());
		line.setP2(ShapeFactory.createPoint(Double.POSITIVE_INFINITY, -20));
		assertEquals(20., line.getX2());
		assertEquals(30., line.getY2());
		line.setP2(ShapeFactory.createPoint(Double.NEGATIVE_INFINITY, -20));
		assertEquals(20., line.getX2());
		assertEquals(30., line.getY2());
		line.setP2(ShapeFactory.createPoint(-20, Double.NaN));
		assertEquals(20., line.getX2());
		assertEquals(30., line.getY2());
		line.setP2(ShapeFactory.createPoint(-20, Double.POSITIVE_INFINITY));
		assertEquals(20., line.getX2());
		assertEquals(30., line.getY2());
		line.setP2(ShapeFactory.createPoint(-20, Double.NEGATIVE_INFINITY));
		assertEquals(20., line.getX2());
		assertEquals(30., line.getY2());
	}


	@Test
	public void testSetP1DoubleDouble() {
		line.setP1(ShapeFactory.createPoint(20, 30));
		assertEquals(20., line.getX1());
		assertEquals(30., line.getY1());
		line.setP1(ShapeFactory.createPoint(Double.NaN, -10));
		assertEquals(20., line.getX1());
		assertEquals(30., line.getY1());
		line.setP1(ShapeFactory.createPoint(Double.POSITIVE_INFINITY, -10));
		assertEquals(20., line.getX1());
		assertEquals(30., line.getY1());
		line.setP1(ShapeFactory.createPoint(Double.NEGATIVE_INFINITY, -10));
		assertEquals(20., line.getX1());
		assertEquals(30., line.getY1());
		line.setP1(ShapeFactory.createPoint(-10, Double.NaN));
		assertEquals(20., line.getX1());
		assertEquals(30., line.getY1());
		line.setP1(ShapeFactory.createPoint(-10, Double.POSITIVE_INFINITY));
		assertEquals(20., line.getX1());
		assertEquals(30., line.getY1());
		line.setP1(ShapeFactory.createPoint(-10, Double.NEGATIVE_INFINITY));
		assertEquals(20., line.getX1());
		assertEquals(30., line.getY1());
	}


	@Test
	public void testSetP2DoubleDouble() {
		line.setP2(ShapeFactory.createPoint(20, 30));
		assertEquals(20., line.getX2());
		assertEquals(30., line.getY2());
		line.setP2(null);
		assertNotNull(line.getPoint2());
		assertEquals(20., line.getX2());
		assertEquals(30., line.getY2());
		line.setP2(ShapeFactory.createPoint(Double.NaN, -20));
		assertEquals(20., line.getX2());
		assertEquals(30., line.getY2());
		line.setP2(ShapeFactory.createPoint(Double.POSITIVE_INFINITY, -20));
		assertEquals(20., line.getX2());
		assertEquals(30., line.getY2());
		line.setP2(ShapeFactory.createPoint(Double.NEGATIVE_INFINITY, -20));
		assertEquals(20., line.getX2());
		assertEquals(30., line.getY2());
		line.setP2(ShapeFactory.createPoint(-20, Double.NaN));
		assertEquals(20., line.getX2());
		assertEquals(30., line.getY2());
		line.setP2(ShapeFactory.createPoint(-20, Double.POSITIVE_INFINITY));
		assertEquals(20., line.getX2());
		assertEquals(30., line.getY2());
		line.setP2(ShapeFactory.createPoint(-20, Double.NEGATIVE_INFINITY));
		assertEquals(20., line.getX2());
		assertEquals(30., line.getY2());
	}


	@Test
	public void testGetA() {
		line.setLine(10, 5, 20, 5);
		line.updateAandB();
		assertEquals(-0., line.getA());
		line.setLine(0, 0, 1, 1);
		line.updateAandB();
		assertEquals(1., line.getA());
		line.setLine(0, 0, -1, 1);
		line.updateAandB();
		assertEquals(-1., line.getA());
		line.setLine(0, 0, 1, 2);
		line.updateAandB();
		assertEquals(2., line.getA());
		line.setLine(1, 0, 2, 1);
		line.updateAandB();
		assertEquals(1., line.getA());
		line.setLine(1, 0, 1, 1);
		line.updateAandB();
		assertTrue(Double.isNaN(line.getA()));
	}


	@Test
	public void testGetB() {
		line.setLine(10, 5, 20, -10);
		line.updateAandB();
		assertEquals(20., line.getB());
		line.setLine(1, 0, 1, 1);
		line.updateAandB();
		assertTrue(Double.isNaN(line.getB()));
	}


	@Test
	public void testGetTopLeftPoint() {
		line.setLine(10, 5, 20, -10);
		assertEquals(10., line.getTopLeftPoint().getX());
		assertEquals(-10., line.getTopLeftPoint().getY());
		line.setLine(0, 50, -20, 30);
		assertEquals(-20., line.getTopLeftPoint().getX());
		assertEquals(30., line.getTopLeftPoint().getY());
		line.setLine(0, 0, 0, 0);
		assertEquals(0., line.getTopLeftPoint().getX());
		assertEquals(0., line.getTopLeftPoint().getY());
	}


	@Test
	public void testGetBottomRightPoint() {
		line.setLine(10, 5, 20, -10);
		assertEquals(20., line.getBottomRightPoint().getX());
		assertEquals(5., line.getBottomRightPoint().getY());
		line.setLine(0, 50, -20, 30);
		assertEquals(0., line.getBottomRightPoint().getX());
		assertEquals(50., line.getBottomRightPoint().getY());
		line.setLine(0, 0, 0, 0);
		assertEquals(0., line.getBottomRightPoint().getX());
		assertEquals(0., line.getBottomRightPoint().getY());
	}


	@Test
	public void testGetPerpendicularLineHoriz() {
		line.setLine(-10, 0, 10, 0);
		line.updateAandB();
		ILine line2 = line.getPerpendicularLine(ShapeFactory.createPoint());
		assertNotNull(line2);
		assertEquals(0., line2.getX1());
		assertEquals(0., line2.getX2());
		line.setLine(-10, 1, 10, 1);
		line.updateAandB();
		line2 = line.getPerpendicularLine(ShapeFactory.createPoint(1,1));
		assertNotNull(line2);
		assertEquals(1., line2.getX1());
		assertEquals(1., line2.getX2());
		line.setLine(-10, -1, 10, -1);
		line.updateAandB();
		line2 = line.getPerpendicularLine(ShapeFactory.createPoint(-1,-1));
		assertNotNull(line2);
		assertEquals(-1., line2.getX1());
		assertEquals(-1., line2.getX2());
	}


	@Test
	public void testGetPerpendicularLineVert() {
		line.setLine(0, 10, 0, -10);
		line.updateAandB();
		ILine line2 = line.getPerpendicularLine(ShapeFactory.createPoint());
		assertNotNull(line2);
		assertEquals(0., line2.getY1());
		assertEquals(0., line2.getY2());
		line.setLine(1, 10, 1, -10);
		line.updateAandB();
		line2 = line.getPerpendicularLine(ShapeFactory.createPoint(1,1));
		assertNotNull(line2);
		assertEquals(1., line2.getY1());
		assertEquals(1., line2.getY2());
		line.setLine(-1, 10, -1, -10);
		line.updateAandB();
		line2 = line.getPerpendicularLine(ShapeFactory.createPoint(-1,-1));
		assertNotNull(line2);
		assertEquals(-1., line2.getY1());
		assertEquals(-1., line2.getY2());
	}


	@Test
	public void testGetPerpendicularLineDiag() {
		line.setLine(1, 1, 2, 2);
		line.updateAandB();
		ILine line2 = line.getPerpendicularLine(ShapeFactory.createPoint());
		assertNotNull(line2);
		assertEquals(0., line2.getB());
		assertEquals(-1., line2.getA());
		line.setLine(-1, 1, 1, -1);
		line.updateAandB();
		line2 = line.getPerpendicularLine(ShapeFactory.createPoint(0,0));
		assertNotNull(line2);
		assertEquals(0., line2.getB());
		assertEquals(1., line2.getA());

		assertNull(line.getPerpendicularLine(null));
		assertNull(line.getPerpendicularLine(ShapeFactory.createPoint(Double.NaN, 0)));
		assertNull(line.getPerpendicularLine(ShapeFactory.createPoint(0, Double.NEGATIVE_INFINITY)));
	}


	@Test
	public void testGetIntersection() {
		ILine line2 = ShapeFactory.createLine(72, -981, 0, 0);
		line.setLine(-237, 17, 13, -82);
		line.updateAandB();
		IPoint pt = line.getIntersection(line2);
		assertNotNull(pt);
		assertTrue(LNumber.equalsDouble(5.809358228, pt.getX(), 0.00000001));
		assertTrue(LNumber.equalsDouble(-79.152505858, pt.getY(), 0.00000001));

		assertNull(line.getIntersection(null));
		assertNull(line.getIntersection(line));
		assertNull(line.getIntersection(ShapeFactory.createLine(1, 1, 1, 1)));
	}


	@Test
	public void testGetIntersectionVert() {
		ILine line2 = ShapeFactory.createLine(0, 0, 1, 0);
		line.setLine(0, -10, 0, 10);
		line.updateAandB();
		IPoint pt = line.getIntersection(line2);
		assertNotNull(pt);
		assertEquals(0., pt.getX());
		assertEquals(0., pt.getY());

		line2 = ShapeFactory.createLine(0, 0, 1, 0);
		line.setLine(1, -10, 1, 10);
		line.updateAandB();
		pt = line.getIntersection(line2);
		assertNotNull(pt);
		assertEquals(1., pt.getX());
		assertEquals(0., pt.getY());

		line2 = ShapeFactory.createLine(0, 0, 1, 0);
		line.setLine(-1, -10, -1, 10);
		line.updateAandB();
		pt = line.getIntersection(line2);
		assertNotNull(pt);
		assertEquals(-1., pt.getX());
		assertEquals(0., pt.getY());

		line2 = ShapeFactory.createLine(1, 2, -1, 0);
		line.setLine(0., -10, 0., 10);
		line.updateAandB();
		pt = line.getIntersection(line2);
		assertNotNull(pt);
		assertEquals(0., pt.getX());
		assertEquals(1., pt.getY());
	}


	@Test
	public void testGetIntersectionHoriz() {
		ILine line2 = ShapeFactory.createLine(0, 0, 1, 0);
		line.setLine(0, -10, 0, 10);
		line.updateAandB();
		IPoint pt = line2.getIntersection(line);
		assertNotNull(pt);
		assertEquals(0., pt.getX());
		assertEquals(0., pt.getY());

		line2 = ShapeFactory.createLine(0, 0, 1, 0);
		line.setLine(1, -10, 1, 10);
		line.updateAandB();
		pt = line2.getIntersection(line);
		assertNotNull(pt);
		assertEquals(1., pt.getX());
		assertEquals(0., pt.getY());

		line2 = ShapeFactory.createLine(0, 0, 1, 0);
		line.setLine(-1, -10, -1, 10);
		line.updateAandB();
		pt = line2.getIntersection(line);
		assertNotNull(pt);
		assertEquals(-1., pt.getX());
		assertEquals(0., pt.getY());

		line2 = ShapeFactory.createLine(1, 2, -1, 0);
		line.setLine(0., -10, 0., 10);
		line.updateAandB();
		pt = line2.getIntersection(line);
		assertNotNull(pt);
		assertEquals(0., pt.getX());
		assertEquals(1., pt.getY());
	}


	@Test
	public void testIsVerticalLine() {
		line.setLine(1, 1, 1, 1);
		line.updateAandB();
		assertTrue(line.isVerticalLine());
		line.setLine(1, 0, 1, 1);
		line.updateAandB();
		assertTrue(line.isVerticalLine());
		line.setLine(-1000, -139, -1000, 2938);
		line.updateAandB();
		assertTrue(line.isVerticalLine());
		line.setLine(187.3703, 192.456, 187.3703, 938);
		line.updateAandB();
		assertTrue(line.isVerticalLine());
		line.setLine(187.3703, 192.456, 187.36, 938);
		line.updateAandB();
		assertFalse(line.isVerticalLine());
		line.setLine(187.3503, 938, 187.37035, 938);
		line.updateAandB();
		assertFalse(line.isVerticalLine());
	}


	@Test
	public void testIsHorizontalLine() {
		line.setLine(1, 1, 1, 1);
		line.updateAandB();
		assertTrue(line.isHorizontalLine());
		line.setLine(0, 1, 1, 1);
		line.updateAandB();
		assertTrue(line.isHorizontalLine());
		line.setLine(-1000, -139, -1010, -139);
		line.updateAandB();
		assertTrue(line.isHorizontalLine());
		line.setLine(187.3703, 192.456, 185, 192.456);
		line.updateAandB();
		assertTrue(line.isHorizontalLine());
		line.setLine(192.456, 187.37035, 938, 187.36);
		line.updateAandB();
		assertFalse(line.isHorizontalLine());
		line.setLine(187.3503, 938, 938, 187.37035);
		line.updateAandB();
		assertFalse(line.isHorizontalLine());
	}


	@Test
	public void testGetIntersectionSegment() {
		ILine line2 = ShapeFactory.createLine(0, 0, 1, 0);
		line.setLine(1, 1, 0, -1);
		line.updateAandB();
		IPoint pt = line.getIntersectionSegment(line2);
		assertNotNull(pt);
		assertEquals(0.5, pt.getX());
		assertEquals(0., pt.getY());

		assertNull(line.getIntersectionSegment(null));
		assertNull(line.getIntersectionSegment(line));
		assertNull(line.getIntersectionSegment(ShapeFactory.createLine(1, 1, 1, 1)));
	}


	@Test
	public void testGetIntersectionSegmentVert() {
		ILine line2 = ShapeFactory.createLine(0, 0, 1, 0);
		line.setLine(0, -10, 0, 10);
		line.updateAandB();
		IPoint pt = line.getIntersectionSegment(line2);
		assertNotNull(pt);
		assertEquals(0., pt.getX());
		assertEquals(0., pt.getY());

		line2 = ShapeFactory.createLine(0, 0, 1, 0);
		line.setLine(1, -10, 1, 10);
		line.updateAandB();
		pt = line.getIntersectionSegment(line2);
		assertNotNull(pt);
		assertEquals(1., pt.getX());
		assertEquals(0., pt.getY());

		line2 = ShapeFactory.createLine(0, 0, 1, 0);
		line.setLine(1.001, -10, 1.001, 10);
		line.updateAandB();
		pt = line.getIntersectionSegment(line2);
		assertNull(pt);

		line2 = ShapeFactory.createLine(0, 0, 1, 0);
		line.setLine(-0.1, -10, -0.1, 10);
		line.updateAandB();
		pt = line.getIntersectionSegment(line2);
		assertNull(pt);

		line2 = ShapeFactory.createLine(1, 2, -1, 0);
		line.setLine(0., -10, 0., 10);
		line.updateAandB();
		pt = line.getIntersectionSegment(line2);
		assertNotNull(pt);
		assertEquals(0., pt.getX());
		assertEquals(1., pt.getY());

		line2 = ShapeFactory.createLine(1, 2, -1, 0);
		line.setLine(0., 10, 0., 1.01);
		line.updateAandB();
		pt = line.getIntersectionSegment(line2);
		assertNull(pt);
	}



	@Test
	public void testGetIntersectionSegmentHoriz() {
		ILine line2 = ShapeFactory.createLine(0, 0, 1, 0);
		line.setLine(0, -10, 0, 10);
		line.updateAandB();
		IPoint pt = line2.getIntersectionSegment(line);
		assertNotNull(pt);
		assertEquals(0., pt.getX());
		assertEquals(0., pt.getY());

		line2 = ShapeFactory.createLine(0.01, 0, 1, 0);
		line.setLine(0, -10, 0, 10);
		line.updateAandB();
		pt = line2.getIntersectionSegment(line);
		assertNull(pt);

		line2 = ShapeFactory.createLine(-0.01, 0, -1, 0);
		line.setLine(0, -10, 0, 10);
		line.updateAandB();
		pt = line2.getIntersectionSegment(line);
		assertNull(pt);

		line2 = ShapeFactory.createLine(0, 10.01, 1, 11);
		line.setLine(0, -10, 0, 10);
		line.updateAandB();
		pt = line2.getIntersectionSegment(line);
		assertNull(pt);

		line2 = ShapeFactory.createLine(0, -10.01, 1, -11);
		line.setLine(0, -10, 0, 10);
		line.updateAandB();
		pt = line2.getIntersectionSegment(line);
		assertNull(pt);

		line2 = ShapeFactory.createLine(0, 0, 1, 0);
		line.setLine(1, -10, 1, 10);
		line.updateAandB();
		pt = line2.getIntersectionSegment(line);
		assertNotNull(pt);
		assertEquals(1., pt.getX());
		assertEquals(0., pt.getY());

		line2 = ShapeFactory.createLine(1, 2, -1, 0);
		line.setLine(0., -10, 0., 10);
		line.updateAandB();
		pt = line2.getIntersectionSegment(line);
		assertNotNull(pt);
		assertEquals(0., pt.getX());
		assertEquals(1., pt.getY());
	}



	@Test
	public void testFindPointsDoubleDoubleDouble() {
		line.setLine(1, 1, 3, 1);
		line.updateAandB();
		assertNull(line.findPoints(Double.NaN, 0, 10));
		assertNull(line.findPoints(0, Double.NaN, 10));
		assertNull(line.findPoints(0, 0, Double.NaN));
		assertNull(line.findPoints(Double.NEGATIVE_INFINITY, 0, 10));
		assertNull(line.findPoints(0, Double.NEGATIVE_INFINITY, 10));
		assertNull(line.findPoints(0, 0, Double.NEGATIVE_INFINITY));
		assertNull(line.findPoints(Double.POSITIVE_INFINITY, 0, 10));
		assertNull(line.findPoints(0, Double.POSITIVE_INFINITY, 10));
		assertNull(line.findPoints(0, 0, Double.POSITIVE_INFINITY));

		IPoint[] pts = line.findPoints(1, 1, 2);
		assertNotNull(pts);
		assertEquals(2, pts.length);
		assertEquals(1., pts[0].getY());
		assertEquals(1., pts[1].getY());

		if(pts[0].getX()<1.) {
			assertEquals(-1., pts[0].getX());
			assertEquals(3., pts[1].getX());
		}
		else {
			assertEquals(3., pts[0].getX());
			assertEquals(-1., pts[1].getX());
		}

		line.setLine(3, 3, 3, 3);
		line.updateAandB();
		pts = line.findPoints(3, 3, 1);
		assertNull(pts);

		line.setLine(0, 3, 3, 3);
		line.updateAandB();
		pts = line.findPoints(10, 10, 1);
		assertNull(pts);
	}


	@Test
	public void testFindPointsIPointDouble() {
		line.setLine(1, 1, 3, 1);
		line.updateAandB();
		assertNull(line.findPoints((IPoint)null, 10));
		assertNull(line.findPoints(ShapeFactory.createPoint(Double.NaN,10), 10));
		assertNull(line.findPoints(ShapeFactory.createPoint(10, Double.POSITIVE_INFINITY), 10));
		assertNull(line.findPoints(ShapeFactory.createPoint(10, 10), Double.NaN));
		assertNull(line.findPoints(ShapeFactory.createPoint(10, 10), Double.NEGATIVE_INFINITY));

		IPoint[] pts = line.findPoints(ShapeFactory.createPoint(1, 1), 2);
		assertNotNull(pts);
		assertEquals(2, pts.length);
		assertEquals(1., pts[0].getY());
		assertEquals(1., pts[1].getY());

		if(pts[0].getX()<1.) {
			assertEquals(-1., pts[0].getX());
			assertEquals(3., pts[1].getX());
		}
		else {
			assertEquals(3., pts[0].getX());
			assertEquals(-1., pts[1].getX());
		}

		line.setLine(3, 3, 3, 3);
		line.updateAandB();
		pts = line.findPoints(ShapeFactory.createPoint(3, 3), 1);
		assertNull(pts);

		line.setLine(0, 3, 3, 3);
		line.updateAandB();
		pts = line.findPoints(ShapeFactory.createPoint(10, 10), 1);
		assertNull(pts);
	}




	@Test
	public void testUpdateAandB() {
		line.setLine(0, 0, 1, 1);
		line.updateAandB();
		assertEquals(0., line.getB());
		assertEquals(1., line.getA());

		line.setLine(0, 0, -1, 1);
		line.updateAandB();
		assertEquals(0., line.getB());
		assertEquals(-1., line.getA());

		line.setLine(0, 1, 1, 2);
		line.updateAandB();
		assertEquals(1., line.getB());
		assertEquals(1., line.getA());

		line.setLine(1, 1, 1, 1);
		line.updateAandB();
		assertEquals(Double.NaN, line.getB());
		assertEquals(Double.NaN, line.getA());

		line.setLine(1, 1, 1, 3);
		line.updateAandB();
		assertEquals(Double.NaN, line.getB());
		assertEquals(Double.NaN, line.getA());
	}
}
