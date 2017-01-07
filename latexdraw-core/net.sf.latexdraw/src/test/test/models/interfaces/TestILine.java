package test.models.interfaces;

import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ILine;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public abstract class TestILine {

	protected ILine line;

	@Test
	public void testLineAngle() {
		line.setLine(0, 100, 100, 100);
		line.updateAandB();

		assertEquals(0., line.getLineAngle(), 0.1);

		line.setLine(0, 100, -100, 100);
		line.updateAandB();

		assertEquals(0., line.getLineAngle(), 0.1);

		line.setLine(0, 100, 0, 200);
		line.updateAandB();

		assertEquals(Math.PI / 2., line.getLineAngle(), 0.1);

		line.setLine(0, 100, 0, -200);
		line.updateAandB();

		assertEquals(Math.PI / 2., line.getLineAngle(), 0.1);

		line.setLine(100, 100, 100, 100);
		line.updateAandB();

		assertEquals(0., line.getLineAngle(), 0.1);

		line.setLine(92.27, -12028.2, 98700, 982);
		line.updateAandB();

		assertEquals(Math.atan(line.getA()), line.getLineAngle(), 0.1);
	}

	@Test
	public void testGetSetX1() {
		line.setX1(0);
		assertEquals(0., line.getX1(), 0.1);
		line.setX1(10);
		assertEquals(10., line.getX1(), 0.1);
		line.setX1(-10);
		assertEquals(-10., line.getX1(), 0.1);
	}

	@Test
	public void testGetSetX2() {
		line.setX2(0);
		assertEquals(0., line.getX2(), 0.1);
		line.setX2(10);
		assertEquals(10., line.getX2(), 0.1);
		line.setX2(-10);
		assertEquals(-10., line.getX2(), 0.1);
	}

	@Test
	public void testGetSetY1() {
		line.setY1(0);
		assertEquals(0., line.getY1(), 0.1);
		line.setY1(10);
		assertEquals(10., line.getY1(), 0.1);
		line.setY1(-10);
		assertEquals(-10., line.getY1(), 0.1);
	}

	@Test
	public void testGetSetY2() {
		line.setY2(0);
		assertEquals(0., line.getY2(), 0.1);
		line.setY2(10);
		assertEquals(10., line.getY2(), 0.1);
		line.setY2(-10);
		assertEquals(-10., line.getY2(), 0.1);
	}

	@Test
	public void testGetPoint1() {
		assertNotNull(line.getPoint1());
		line.setP1(ShapeFactory.INST.createPoint(100, 200));
		assertEquals(100., line.getPoint1().getX(), 0.1);
		assertEquals(200., line.getPoint1().getY(), 0.1);
		line.setP1(ShapeFactory.INST.createPoint(-300, 400));
		assertEquals(-300., line.getPoint1().getX(), 0.1);
		assertEquals(400., line.getPoint1().getY(), 0.1);
	}

	@Test
	public void testGetPoint2() {
		assertNotNull(line.getPoint1());
		line.setP2(ShapeFactory.INST.createPoint(100, 200));
		assertEquals(100., line.getPoint2().getX(), 0.1);
		assertEquals(200., line.getPoint2().getY(), 0.1);
		line.setP2(ShapeFactory.INST.createPoint(-300, 400));
		assertEquals(-300., line.getPoint2().getX(), 0.1);
		assertEquals(400., line.getPoint2().getY(), 0.1);
	}

	@Test
	public void testSetLine() {
		line.setLine(10, 20, 30, 40);
		assertEquals(10., line.getX1(), 0.1);
		assertEquals(30., line.getX2(), 0.1);
		assertEquals(20., line.getY1(), 0.1);
		assertEquals(40., line.getY2(), 0.1);
		line.setLine(Double.NaN, 21, 31, 41);
		assertEquals(10., line.getX1(), 0.1);
		assertEquals(30., line.getX2(), 0.1);
		assertEquals(20., line.getY1(), 0.1);
		assertEquals(40., line.getY2(), 0.1);
		line.setLine(11, Double.POSITIVE_INFINITY, 31, 41);
		assertEquals(10., line.getX1(), 0.1);
		assertEquals(30., line.getX2(), 0.1);
		assertEquals(20., line.getY1(), 0.1);
		assertEquals(40., line.getY2(), 0.1);
		line.setLine(11, 21, Double.NEGATIVE_INFINITY, 41);
		assertEquals(10., line.getX1(), 0.1);
		assertEquals(30., line.getX2(), 0.1);
		assertEquals(20., line.getY1(), 0.1);
		assertEquals(40., line.getY2(), 0.1);
		line.setLine(11, 21, 31, Double.NaN);
		assertEquals(10., line.getX1(), 0.1);
		assertEquals(30., line.getX2(), 0.1);
		assertEquals(20., line.getY1(), 0.1);
		assertEquals(40., line.getY2(), 0.1);
		line.setLine(-11, -21, -31, -41);
		assertEquals(-11., line.getX1(), 0.1);
		assertEquals(-31., line.getX2(), 0.1);
		assertEquals(-21., line.getY1(), 0.1);
		assertEquals(-41., line.getY2(), 0.1);
		line.setLine(0, 0, 0, 0);
		assertEquals(0., line.getX1(), 0.1);
		assertEquals(0., line.getX2(), 0.1);
		assertEquals(0., line.getY1(), 0.1);
		assertEquals(0., line.getY2(), 0.1);
	}

	@Test
	public void testSetP1IPoint() {
		line.setP1(ShapeFactory.INST.createPoint(20, 30));
		assertEquals(20., line.getX1(), 0.1);
		assertEquals(30., line.getY1(), 0.1);
		line.setP1(null);
		assertNotNull(line.getPoint1());
		assertEquals(20., line.getX1(), 0.1);
		assertEquals(30., line.getY1(), 0.1);
		line.setP1(ShapeFactory.INST.createPoint(Double.NaN, -10));
		assertEquals(20., line.getX1(), 0.1);
		assertEquals(30., line.getY1(), 0.1);
		line.setP1(ShapeFactory.INST.createPoint(Double.POSITIVE_INFINITY, -10));
		assertEquals(20., line.getX1(), 0.1);
		assertEquals(30., line.getY1(), 0.1);
		line.setP1(ShapeFactory.INST.createPoint(Double.NEGATIVE_INFINITY, -10));
		assertEquals(20., line.getX1(), 0.1);
		assertEquals(30., line.getY1(), 0.1);
		line.setP1(ShapeFactory.INST.createPoint(-10, Double.NaN));
		assertEquals(20., line.getX1(), 0.1);
		assertEquals(30., line.getY1(), 0.1);
		line.setP1(ShapeFactory.INST.createPoint(-10, Double.POSITIVE_INFINITY));
		assertEquals(20., line.getX1(), 0.1);
		assertEquals(30., line.getY1(), 0.1);
		line.setP1(ShapeFactory.INST.createPoint(-10, Double.NEGATIVE_INFINITY));
		assertEquals(20., line.getX1(), 0.1);
		assertEquals(30., line.getY1(), 0.1);
	}

	@Test
	public void testSetP2IPoint() {
		line.setP2(ShapeFactory.INST.createPoint(20, 30));
		assertEquals(20., line.getX2(), 0.1);
		assertEquals(30., line.getY2(), 0.1);
		line.setP2(null);
		assertNotNull(line.getPoint2());
		assertEquals(20., line.getX2(), 0.1);
		assertEquals(30., line.getY2(), 0.1);
		line.setP2(ShapeFactory.INST.createPoint(Double.NaN, -20));
		assertEquals(20., line.getX2(), 0.1);
		assertEquals(30., line.getY2(), 0.1);
		line.setP2(ShapeFactory.INST.createPoint(Double.POSITIVE_INFINITY, -20));
		assertEquals(20., line.getX2(), 0.1);
		assertEquals(30., line.getY2(), 0.1);
		line.setP2(ShapeFactory.INST.createPoint(Double.NEGATIVE_INFINITY, -20));
		assertEquals(20., line.getX2(), 0.1);
		assertEquals(30., line.getY2(), 0.1);
		line.setP2(ShapeFactory.INST.createPoint(-20, Double.NaN));
		assertEquals(20., line.getX2(), 0.1);
		assertEquals(30., line.getY2(), 0.1);
		line.setP2(ShapeFactory.INST.createPoint(-20, Double.POSITIVE_INFINITY));
		assertEquals(20., line.getX2(), 0.1);
		assertEquals(30., line.getY2(), 0.1);
		line.setP2(ShapeFactory.INST.createPoint(-20, Double.NEGATIVE_INFINITY));
		assertEquals(20., line.getX2(), 0.1);
		assertEquals(30., line.getY2(), 0.1);
	}

	@Test
	public void testSetP1DoubleDouble() {
		line.setP1(ShapeFactory.INST.createPoint(20, 30));
		assertEquals(20., line.getX1(), 0.1);
		assertEquals(30., line.getY1(), 0.1);
		line.setP1(ShapeFactory.INST.createPoint(Double.NaN, -10));
		assertEquals(20., line.getX1(), 0.1);
		assertEquals(30., line.getY1(), 0.1);
		line.setP1(ShapeFactory.INST.createPoint(Double.POSITIVE_INFINITY, -10));
		assertEquals(20., line.getX1(), 0.1);
		assertEquals(30., line.getY1(), 0.1);
		line.setP1(ShapeFactory.INST.createPoint(Double.NEGATIVE_INFINITY, -10));
		assertEquals(20., line.getX1(), 0.1);
		assertEquals(30., line.getY1(), 0.1);
		line.setP1(ShapeFactory.INST.createPoint(-10, Double.NaN));
		assertEquals(20., line.getX1(), 0.1);
		assertEquals(30., line.getY1(), 0.1);
		line.setP1(ShapeFactory.INST.createPoint(-10, Double.POSITIVE_INFINITY));
		assertEquals(20., line.getX1(), 0.1);
		assertEquals(30., line.getY1(), 0.1);
		line.setP1(ShapeFactory.INST.createPoint(-10, Double.NEGATIVE_INFINITY));
		assertEquals(20., line.getX1(), 0.1);
		assertEquals(30., line.getY1(), 0.1);
	}

	@Test
	public void testSetP2DoubleDouble() {
		line.setP2(ShapeFactory.INST.createPoint(20, 30));
		assertEquals(20., line.getX2(), 0.1);
		assertEquals(30., line.getY2(), 0.1);
		line.setP2(null);
		assertNotNull(line.getPoint2());
		assertEquals(20., line.getX2(), 0.1);
		assertEquals(30., line.getY2(), 0.1);
		line.setP2(ShapeFactory.INST.createPoint(Double.NaN, -20));
		assertEquals(20., line.getX2(), 0.1);
		assertEquals(30., line.getY2(), 0.1);
		line.setP2(ShapeFactory.INST.createPoint(Double.POSITIVE_INFINITY, -20));
		assertEquals(20., line.getX2(), 0.1);
		assertEquals(30., line.getY2(), 0.1);
		line.setP2(ShapeFactory.INST.createPoint(Double.NEGATIVE_INFINITY, -20));
		assertEquals(20., line.getX2(), 0.1);
		assertEquals(30., line.getY2(), 0.1);
		line.setP2(ShapeFactory.INST.createPoint(-20, Double.NaN));
		assertEquals(20., line.getX2(), 0.1);
		assertEquals(30., line.getY2(), 0.1);
		line.setP2(ShapeFactory.INST.createPoint(-20, Double.POSITIVE_INFINITY));
		assertEquals(20., line.getX2(), 0.1);
		assertEquals(30., line.getY2(), 0.1);
		line.setP2(ShapeFactory.INST.createPoint(-20, Double.NEGATIVE_INFINITY));
		assertEquals(20., line.getX2(), 0.1);
		assertEquals(30., line.getY2(), 0.1);
	}

	@Test
	public void testGetA() {
		line.setLine(10, 5, 20, 5);
		line.updateAandB();
		assertEquals(-0., line.getA(), 0.1);
		line.setLine(0, 0, 1, 1);
		line.updateAandB();
		assertEquals(1., line.getA(), 0.1);
		line.setLine(0, 0, -1, 1);
		line.updateAandB();
		assertEquals(-1., line.getA(), 0.1);
		line.setLine(0, 0, 1, 2);
		line.updateAandB();
		assertEquals(2., line.getA(), 0.1);
		line.setLine(1, 0, 2, 1);
		line.updateAandB();
		assertEquals(1., line.getA(), 0.1);
		line.setLine(1, 0, 1, 1);
		line.updateAandB();
		assertTrue(Double.isNaN(line.getA()));
	}

	@Test
	public void testGetB() {
		line.setLine(10, 5, 20, -10);
		line.updateAandB();
		assertEquals(20., line.getB(), 0.1);
		line.setLine(1, 0, 1, 1);
		line.updateAandB();
		assertTrue(Double.isNaN(line.getB()));
	}

	@Test
	public void testGetTopLeftPoint() {
		line.setLine(10, 5, 20, -10);
		assertEquals(10., line.getTopLeftPoint().getX(), 0.1);
		assertEquals(-10., line.getTopLeftPoint().getY(), 0.1);
		line.setLine(0, 50, -20, 30);
		assertEquals(-20., line.getTopLeftPoint().getX(), 0.1);
		assertEquals(30., line.getTopLeftPoint().getY(), 0.1);
		line.setLine(0, 0, 0, 0);
		assertEquals(0., line.getTopLeftPoint().getX(), 0.1);
		assertEquals(0., line.getTopLeftPoint().getY(), 0.1);
	}

	@Test
	public void testGetBottomRightPoint() {
		line.setLine(10, 5, 20, -10);
		assertEquals(20., line.getBottomRightPoint().getX(), 0.1);
		assertEquals(5., line.getBottomRightPoint().getY(), 0.1);
		line.setLine(0, 50, -20, 30);
		assertEquals(0., line.getBottomRightPoint().getX(), 0.1);
		assertEquals(50., line.getBottomRightPoint().getY(), 0.1);
		line.setLine(0, 0, 0, 0);
		assertEquals(0., line.getBottomRightPoint().getX(), 0.1);
		assertEquals(0., line.getBottomRightPoint().getY(), 0.1);
	}

	@Test
	public void testGetPerpendicularLineHoriz() {
		line.setLine(-10, 0, 10, 0);
		line.updateAandB();
		ILine line2 = line.getPerpendicularLine(ShapeFactory.INST.createPoint());
		assertNotNull(line2);
		assertEquals(0., line2.getX1(), 0.1);
		assertEquals(0., line2.getX2(), 0.1);
		line.setLine(-10, 1, 10, 1);
		line.updateAandB();
		line2 = line.getPerpendicularLine(ShapeFactory.INST.createPoint(1, 1));
		assertNotNull(line2);
		assertEquals(1., line2.getX1(), 0.1);
		assertEquals(1., line2.getX2(), 0.1);
		line.setLine(-10, -1, 10, -1);
		line.updateAandB();
		line2 = line.getPerpendicularLine(ShapeFactory.INST.createPoint(-1, -1));
		assertNotNull(line2);
		assertEquals(-1., line2.getX1(), 0.1);
		assertEquals(-1., line2.getX2(), 0.1);
	}

	@Test
	public void testGetPerpendicularLineVert() {
		line.setLine(0, 10, 0, -10);
		line.updateAandB();
		ILine line2 = line.getPerpendicularLine(ShapeFactory.INST.createPoint());
		assertNotNull(line2);
		assertEquals(0., line2.getY1(), 0.1);
		assertEquals(0., line2.getY2(), 0.1);
		line.setLine(1, 10, 1, -10);
		line.updateAandB();
		line2 = line.getPerpendicularLine(ShapeFactory.INST.createPoint(1, 1));
		assertNotNull(line2);
		assertEquals(1., line2.getY1(), 0.1);
		assertEquals(1., line2.getY2(), 0.1);
		line.setLine(-1, 10, -1, -10);
		line.updateAandB();
		line2 = line.getPerpendicularLine(ShapeFactory.INST.createPoint(-1, -1));
		assertNotNull(line2);
		assertEquals(-1., line2.getY1(), 0.1);
		assertEquals(-1., line2.getY2(), 0.1);
	}

	@Test
	public void testGetPerpendicularLineDiag() {
		line.setLine(1, 1, 2, 2);
		line.updateAandB();
		ILine line2 = line.getPerpendicularLine(ShapeFactory.INST.createPoint());
		assertNotNull(line2);
		assertEquals(0., line2.getB(), 0.1);
		assertEquals(-1., line2.getA(), 0.1);
		line.setLine(-1, 1, 1, -1);
		line.updateAandB();
		line2 = line.getPerpendicularLine(ShapeFactory.INST.createPoint(0, 0));
		assertNotNull(line2);
		assertEquals(0., line2.getB(), 0.1);
		assertEquals(1., line2.getA(), 0.1);

		assertNull(line.getPerpendicularLine(null));
		assertNull(line.getPerpendicularLine(ShapeFactory.INST.createPoint(Double.NaN, 0)));
		assertNull(line.getPerpendicularLine(ShapeFactory.INST.createPoint(0, Double.NEGATIVE_INFINITY)));
	}

	@Test
	public void testGetIntersection() {
		ILine line2 = ShapeFactory.INST.createLine(72, -981, 0, 0);
		line.setLine(-237, 17, 13, -82);
		line.updateAandB();
		IPoint pt = line.getIntersection(line2);
		assertNotNull(pt);
		assertTrue(MathUtils.INST.equalsDouble(5.809358228, pt.getX(), 0.00000001));
		assertTrue(MathUtils.INST.equalsDouble(-79.152505858, pt.getY(), 0.00000001));

		assertNull(line.getIntersection(null));
		assertNull(line.getIntersection(line));
		assertNull(line.getIntersection(ShapeFactory.INST.createLine(1, 1, 1, 1)));
	}

	@Test
	public void testGetIntersectionVert() {
		ILine line2 = ShapeFactory.INST.createLine(0, 0, 1, 0);
		line.setLine(0, -10, 0, 10);
		line.updateAandB();
		IPoint pt = line.getIntersection(line2);
		assertNotNull(pt);
		assertEquals(0., pt.getX(), 0.1);
		assertEquals(0., pt.getY(), 0.1);

		line2 = ShapeFactory.INST.createLine(0, 0, 1, 0);
		line.setLine(1, -10, 1, 10);
		line.updateAandB();
		pt = line.getIntersection(line2);
		assertNotNull(pt);
		assertEquals(1., pt.getX(), 0.1);
		assertEquals(0., pt.getY(), 0.1);

		line2 = ShapeFactory.INST.createLine(0, 0, 1, 0);
		line.setLine(-1, -10, -1, 10);
		line.updateAandB();
		pt = line.getIntersection(line2);
		assertNotNull(pt);
		assertEquals(-1., pt.getX(), 0.1);
		assertEquals(0., pt.getY(), 0.1);

		line2 = ShapeFactory.INST.createLine(1, 2, -1, 0);
		line.setLine(0., -10, 0., 10);
		line.updateAandB();
		pt = line.getIntersection(line2);
		assertNotNull(pt);
		assertEquals(0., pt.getX(), 0.1);
		assertEquals(1., pt.getY(), 0.1);
	}

	@Test
	public void testGetIntersectionHoriz() {
		ILine line2 = ShapeFactory.INST.createLine(0, 0, 1, 0);
		line.setLine(0, -10, 0, 10);
		line.updateAandB();
		IPoint pt = line2.getIntersection(line);
		assertNotNull(pt);
		assertEquals(0., pt.getX(), 0.1);
		assertEquals(0., pt.getY(), 0.1);

		line2 = ShapeFactory.INST.createLine(0, 0, 1, 0);
		line.setLine(1, -10, 1, 10);
		line.updateAandB();
		pt = line2.getIntersection(line);
		assertNotNull(pt);
		assertEquals(1., pt.getX(), 0.1);
		assertEquals(0., pt.getY(), 0.1);

		line2 = ShapeFactory.INST.createLine(0, 0, 1, 0);
		line.setLine(-1, -10, -1, 10);
		line.updateAandB();
		pt = line2.getIntersection(line);
		assertNotNull(pt);
		assertEquals(-1., pt.getX(), 0.1);
		assertEquals(0., pt.getY(), 0.1);

		line2 = ShapeFactory.INST.createLine(1, 2, -1, 0);
		line.setLine(0., -10, 0., 10);
		line.updateAandB();
		pt = line2.getIntersection(line);
		assertNotNull(pt);
		assertEquals(0., pt.getX(), 0.1);
		assertEquals(1., pt.getY(), 0.1);
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
		ILine line2 = ShapeFactory.INST.createLine(0, 0, 1, 0);
		line.setLine(1, 1, 0, -1);
		line.updateAandB();
		IPoint pt = line.getIntersectionSegment(line2);
		assertNotNull(pt);
		assertEquals(0.5, pt.getX(), 0.1);
		assertEquals(0., pt.getY(), 0.1);

		assertNull(line.getIntersectionSegment(null));
		assertNull(line.getIntersectionSegment(line));
		assertNull(line.getIntersectionSegment(ShapeFactory.INST.createLine(1, 1, 1, 1)));
	}

	@Test
	public void testGetIntersectionSegmentVert() {
		ILine line2 = ShapeFactory.INST.createLine(0, 0, 1, 0);
		line.setLine(0, -10, 0, 10);
		line.updateAandB();
		IPoint pt = line.getIntersectionSegment(line2);
		assertNotNull(pt);
		assertEquals(0., pt.getX(), 0.1);
		assertEquals(0., pt.getY(), 0.1);

		line2 = ShapeFactory.INST.createLine(0, 0, 1, 0);
		line.setLine(1, -10, 1, 10);
		line.updateAandB();
		pt = line.getIntersectionSegment(line2);
		assertNotNull(pt);
		assertEquals(1., pt.getX(), 0.1);
		assertEquals(0., pt.getY(), 0.1);

		line2 = ShapeFactory.INST.createLine(0, 0, 1, 0);
		line.setLine(1.001, -10, 1.001, 10);
		line.updateAandB();
		pt = line.getIntersectionSegment(line2);
		assertNull(pt);

		line2 = ShapeFactory.INST.createLine(0, 0, 1, 0);
		line.setLine(-0.1, -10, -0.1, 10);
		line.updateAandB();
		pt = line.getIntersectionSegment(line2);
		assertNull(pt);

		line2 = ShapeFactory.INST.createLine(1, 2, -1, 0);
		line.setLine(0., -10, 0., 10);
		line.updateAandB();
		pt = line.getIntersectionSegment(line2);
		assertNotNull(pt);
		assertEquals(0., pt.getX(), 0.1);
		assertEquals(1., pt.getY(), 0.1);

		line2 = ShapeFactory.INST.createLine(1, 2, -1, 0);
		line.setLine(0., 10, 0., 1.01);
		line.updateAandB();
		pt = line.getIntersectionSegment(line2);
		assertNull(pt);
	}

	@Test
	public void testGetIntersectionSegmentHoriz() {
		ILine line2 = ShapeFactory.INST.createLine(0, 0, 1, 0);
		line.setLine(0, -10, 0, 10);
		line.updateAandB();
		IPoint pt = line2.getIntersectionSegment(line);
		assertNotNull(pt);
		assertEquals(0., pt.getX(), 0.1);
		assertEquals(0., pt.getY(), 0.1);

		line2 = ShapeFactory.INST.createLine(0.01, 0, 1, 0);
		line.setLine(0, -10, 0, 10);
		line.updateAandB();
		pt = line2.getIntersectionSegment(line);
		assertNull(pt);

		line2 = ShapeFactory.INST.createLine(-0.01, 0, -1, 0);
		line.setLine(0, -10, 0, 10);
		line.updateAandB();
		pt = line2.getIntersectionSegment(line);
		assertNull(pt);

		line2 = ShapeFactory.INST.createLine(0, 10.01, 1, 11);
		line.setLine(0, -10, 0, 10);
		line.updateAandB();
		pt = line2.getIntersectionSegment(line);
		assertNull(pt);

		line2 = ShapeFactory.INST.createLine(0, -10.01, 1, -11);
		line.setLine(0, -10, 0, 10);
		line.updateAandB();
		pt = line2.getIntersectionSegment(line);
		assertNull(pt);

		line2 = ShapeFactory.INST.createLine(0, 0, 1, 0);
		line.setLine(1, -10, 1, 10);
		line.updateAandB();
		pt = line2.getIntersectionSegment(line);
		assertNotNull(pt);
		assertEquals(1., pt.getX(), 0.1);
		assertEquals(0., pt.getY(), 0.1);

		line2 = ShapeFactory.INST.createLine(1, 2, -1, 0);
		line.setLine(0., -10, 0., 10);
		line.updateAandB();
		pt = line2.getIntersectionSegment(line);
		assertNotNull(pt);
		assertEquals(0., pt.getX(), 0.1);
		assertEquals(1., pt.getY(), 0.1);
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
		assertEquals(1., pts[0].getY(), 0.1);
		assertEquals(1., pts[1].getY(), 0.1);

		if(pts[0].getX() < 1.) {
			assertEquals(-1., pts[0].getX(), 0.1);
			assertEquals(3., pts[1].getX(), 0.1);
		}else {
			assertEquals(3., pts[0].getX(), 0.1);
			assertEquals(-1., pts[1].getX(), 0.1);
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
		assertNull(line.findPoints(ShapeFactory.INST.createPoint(Double.NaN, 10), 10));
		assertNull(line.findPoints(ShapeFactory.INST.createPoint(10, Double.POSITIVE_INFINITY), 10));
		assertNull(line.findPoints(ShapeFactory.INST.createPoint(10, 10), Double.NaN));
		assertNull(line.findPoints(ShapeFactory.INST.createPoint(10, 10), Double.NEGATIVE_INFINITY));

		IPoint[] pts = line.findPoints(ShapeFactory.INST.createPoint(1, 1), 2);
		assertNotNull(pts);
		assertEquals(2, pts.length);
		assertEquals(1., pts[0].getY(), 0.1);
		assertEquals(1., pts[1].getY(), 0.1);

		if(pts[0].getX() < 1.) {
			assertEquals(-1., pts[0].getX(), 0.1);
			assertEquals(3., pts[1].getX(), 0.1);
		}else {
			assertEquals(3., pts[0].getX(), 0.1);
			assertEquals(-1., pts[1].getX(), 0.1);
		}

		line.setLine(3, 3, 3, 3);
		line.updateAandB();
		pts = line.findPoints(ShapeFactory.INST.createPoint(3, 3), 1);
		assertNull(pts);

		line.setLine(0, 3, 3, 3);
		line.updateAandB();
		pts = line.findPoints(ShapeFactory.INST.createPoint(10, 10), 1);
		assertNull(pts);
	}

	@Test
	public void testUpdateAandB() {
		line.setLine(0, 0, 1, 1);
		line.updateAandB();
		assertEquals(0., line.getB(), 0.1);
		assertEquals(1., line.getA(), 0.1);

		line.setLine(0, 0, -1, 1);
		line.updateAandB();
		assertEquals(0., line.getB(), 0.1);
		assertEquals(-1., line.getA(), 0.1);

		line.setLine(0, 1, 1, 2);
		line.updateAandB();
		assertEquals(1., line.getB(), 0.1);
		assertEquals(1., line.getA(), 0.1);

		line.setLine(1, 1, 1, 1);
		line.updateAandB();
		assertEquals(Double.NaN, line.getB(), 0.1);
		assertEquals(Double.NaN, line.getA(), 0.1);

		line.setLine(1, 1, 1, 3);
		line.updateAandB();
		assertEquals(Double.NaN, line.getB(), 0.1);
		assertEquals(Double.NaN, line.getA(), 0.1);
	}
}
