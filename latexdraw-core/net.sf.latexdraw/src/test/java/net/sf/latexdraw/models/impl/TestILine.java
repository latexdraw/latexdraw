package net.sf.latexdraw.models.impl;

import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.data.DoubleData;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ILine;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;

@RunWith(Theories.class)
public class TestILine implements HelperTest {
	ILine line;

	@Rule public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() {
		line = ShapeFactory.INST.createLine(1d, 2d, 3d, 4d);
	}

	@Test
	public void testConstructors2OK() {
		line = ShapeFactory.INST.createLine(10, ShapeFactory.INST.createPoint(1, 1));

		assertNotNull(line.getPoint1());
		assertNotNull(line.getPoint2());
		assertEqualsDouble(10d, line.getB());
		assertEqualsDouble(-9d, line.getA());
		assertEqualsDouble(1d, line.getX1());
		assertEqualsDouble(1d, line.getY1());
	}

	@Theory
	public void testConstructors2KO1(@DoubleData(bads = true, vals = {}) final double value) {
		thrown.expect(IllegalArgumentException.class);
		ShapeFactory.INST.createLine(value, ShapeFactory.INST.createPoint(1, 2));
	}

	@Theory
	public void testConstructors2KO2(@DoubleData(bads = true, vals = {}) final double x, @DoubleData(bads = true, vals = {}) final double
		y) {
		thrown.expect(IllegalArgumentException.class);
		ShapeFactory.INST.createLine(10d, ShapeFactory.INST.createPoint(x, y));
	}

	@Test
	public void testConstructors3OK() {
		line = ShapeFactory.INST.createLine(ShapeFactory.INST.createPoint(1, 1), ShapeFactory.INST.createPoint(2, 2));

		assertNotNull(line.getPoint1());
		assertNotNull(line.getPoint2());
		assertEqualsDouble(0d, line.getB());
		assertEqualsDouble(1d, line.getA());
		assertEqualsDouble(1d, line.getX1());
		assertEqualsDouble(1d, line.getY1());
		assertEqualsDouble(2d, line.getX2());
		assertEqualsDouble(2d, line.getY2());
	}

	@Theory
	public void testConstructors3KO1(@DoubleData(bads = true, vals = {}) final double x, @DoubleData(bads = true, vals = {}) final double
		y) {
		thrown.expect(IllegalArgumentException.class);
		ShapeFactory.INST.createLine(ShapeFactory.INST.createPoint(x, 2d), ShapeFactory.INST.createPoint(1d, y));
	}

	@Theory
	public void testConstructors3KO2(@DoubleData(bads = true, vals = {}) final double x, @DoubleData(bads = true, vals = {}) final double
		y) {
		thrown.expect(IllegalArgumentException.class);
		ShapeFactory.INST.createLine(ShapeFactory.INST.createPoint(1d, y), ShapeFactory.INST.createPoint(x, -10d));
	}

	@Theory
	public void testLineAngleHoriz1(@DoubleData final double x1, @DoubleData final double x2) {
		assumeThat(x1, greaterThan(x2));
		line.setLine(x1, 100d, x2, 100d);
		line.updateAandB();
		assertEqualsDouble(0d, line.getLineAngle());
	}

	@Theory
	public void testLineAngleHoriz2(@DoubleData final double x1, @DoubleData final double x2) {
		assumeThat(x1, lessThan(x2));
		line.setLine(x1, 100d, x2, 100d);
		line.updateAandB();
		assertEqualsDouble(Math.PI, line.getLineAngle());
	}

	@Theory
	public void testLineAngleVert(@DoubleData final double y1, @DoubleData final double y2) {
		assumeThat(y1, not(closeTo(y2, 0.0001)));
		line.setLine(-100d, y1, -100d, y2);
		line.updateAandB();
		assertEqualsDouble(Math.PI / 2d, line.getLineAngle() % Math.PI);
	}

	@Theory
	public void testGetSetX1(@DoubleData final double value) {
		line.setX1(value);
		assertEqualsDouble(value, line.getX1());
	}

	@Theory
	public void testGetSetX2(@DoubleData final double value) {
		line.setX2(value);
		assertEqualsDouble(value, line.getX2());
	}

	@Theory
	public void testGetSetY1(@DoubleData final double value) {
		line.setY1(value);
		assertEqualsDouble(value, line.getY1());
	}

	@Theory
	public void testGetSetY2(@DoubleData final double value) {
		line.setY2(value);
		assertEqualsDouble(value, line.getY2());
	}

	@Test
	public void testGetPoint1() {
		assertNotNull(line.getPoint1());
		assertEqualsDouble(1d, line.getPoint1().getX());
		assertEqualsDouble(2d, line.getPoint1().getY());
	}

	@Test
	public void testGetPoint2() {
		assertNotNull(line.getPoint2());
		assertEqualsDouble(3d, line.getPoint2().getX());
		assertEqualsDouble(4d, line.getPoint2().getY());
	}

	@Theory
	public void testSetLine(@DoubleData final double x1, @DoubleData final double x2, @DoubleData final double y1, @DoubleData final
	double y2) {
		line.setLine(x1, y1, x2, y2);
		assertEqualsDouble(x1, line.getX1());
		assertEqualsDouble(x2, line.getX2());
		assertEqualsDouble(y1, line.getY1());
		assertEqualsDouble(y2, line.getY2());
	}

	@Theory
	public void testSetLineKO1(@DoubleData(bads = true, vals = {}) final double value) {
		line.setLine(value, -1d, -2d, -3d);
		assertEqualsDouble(1d, line.getX1());
		assertEqualsDouble(2d, line.getY1());
		assertEqualsDouble(3d, line.getX2());
		assertEqualsDouble(4d, line.getY2());
	}

	@Theory
	public void testSetLineKO2(@DoubleData(bads = true, vals = {}) final double value) {
		line.setLine(-1d, value, -2d, -3d);
		assertEqualsDouble(1d, line.getX1());
		assertEqualsDouble(2d, line.getY1());
		assertEqualsDouble(3d, line.getX2());
		assertEqualsDouble(4d, line.getY2());
	}

	@Theory
	public void testSetLineKO3(@DoubleData(bads = true, vals = {}) final double value) {
		line.setLine(-1d, -2d, value, -3d);
		assertEqualsDouble(1d, line.getX1());
		assertEqualsDouble(2d, line.getY1());
		assertEqualsDouble(3d, line.getX2());
		assertEqualsDouble(4d, line.getY2());
	}

	@Theory
	public void testSetLineKO4(@DoubleData(bads = true, vals = {}) final double value) {
		line.setLine(-1d, -2d, -3d, value);
		assertEqualsDouble(1d, line.getX1());
		assertEqualsDouble(2d, line.getY1());
		assertEqualsDouble(3d, line.getX2());
		assertEqualsDouble(4d, line.getY2());
	}

	@Test
	public void testSetP1IPoint() {
		line.setP1(ShapeFactory.INST.createPoint(20, 30));
		assertEqualsDouble(20d, line.getX1());
		assertEqualsDouble(30d, line.getY1());
	}

	@Test
	public void testSetP1IPointNULL() {
		line.setP1(ShapeFactory.INST.createPoint(20, 30));
		line.setP1(null);
		assertEqualsDouble(20d, line.getX1());
		assertEqualsDouble(30d, line.getY1());
	}

	@Theory
	public void testSetP1IPointKO(@DoubleData(bads = true, vals = {}) final double x, @DoubleData(bads = true, vals = {}) final double y) {
		line.setP1(ShapeFactory.INST.createPoint(20, 30));
		line.setP1(ShapeFactory.INST.createPoint(x, y));
		assertEqualsDouble(20d, line.getX1());
		assertEqualsDouble(30d, line.getY1());
	}

	@Test
	public void testSetP2IPoint() {
		line.setP2(ShapeFactory.INST.createPoint(20, 30));
		assertEqualsDouble(20d, line.getX2());
		assertEqualsDouble(30d, line.getY2());
	}

	@Test
	public void testSetP2IPointNULL() {
		line.setP2(ShapeFactory.INST.createPoint(20, 30));
		line.setP2(null);
		assertEqualsDouble(20d, line.getX2());
		assertEqualsDouble(30d, line.getY2());
	}

	@Theory
	public void testSetP2IPointKO(@DoubleData(bads = true, vals = {}) final double x, @DoubleData(bads = true, vals = {}) final double y) {
		line.setP2(ShapeFactory.INST.createPoint(20, 30));
		line.setP2(ShapeFactory.INST.createPoint(x, y));
		assertEqualsDouble(20d, line.getX2());
		assertEqualsDouble(30d, line.getY2());
	}

	@Test
	public void testGetA() {
		line.setLine(0, 0, 1, 1);
		line.updateAandB();
		assertEqualsDouble(1d, line.getA());
	}

	@Test
	public void testGetANaN() {
		line.setLine(1, 0, 1, 1);
		line.updateAandB();
		assertTrue(Double.isNaN(line.getA()));
	}

	@Test
	public void testGetB() {
		line.setLine(10, 5, 20, -10);
		line.updateAandB();
		assertEqualsDouble(20d, line.getB());
	}

	@Test
	public void testGetBNaN() {
		line.setLine(1, 0, 1, 1);
		line.updateAandB();
		assertTrue(Double.isNaN(line.getB()));
	}

	@Test
	public void testGetTopLeftPoint() {
		line.setLine(10, 5, 20, -10);
		assertEqualsDouble(10d, line.getTopLeftPoint().getX());
		assertEqualsDouble(-10d, line.getTopLeftPoint().getY());
	}

	@Test
	public void testGetBottomRightPoint() {
		line.setLine(10, 5, 20, -10);
		assertEqualsDouble(20d, line.getBottomRightPoint().getX());
		assertEqualsDouble(5d, line.getBottomRightPoint().getY());
	}

	@Test
	public void testGetPerpendicularLineHoriz() {
		line.setLine(-10, 0, 10, 0);
		line.updateAandB();
		ILine line2 = line.getPerpendicularLine(ShapeFactory.INST.createPoint());
		assertNotNull(line2);
		assertEqualsDouble(0d, line2.getX1());
		assertEqualsDouble(0d, line2.getX2());
	}

	@Test
	public void testGetPerpendicularLineVert() {
		line.setLine(0, 10, 0, -10);
		line.updateAandB();
		ILine line2 = line.getPerpendicularLine(ShapeFactory.INST.createPoint());
		assertNotNull(line2);
		assertEqualsDouble(0d, line2.getY1());
		assertEqualsDouble(0d, line2.getY2());
	}

	@Test
	public void testGetPerpendicularLineDiag() {
		line.setLine(1, 1, 2, 2);
		line.updateAandB();
		ILine line2 = line.getPerpendicularLine(ShapeFactory.INST.createPoint());
		assertNotNull(line2);
		assertEqualsDouble(0d, line2.getB());
		assertEqualsDouble(-1d, line2.getA());
	}

	@Test
	public void testGetPerpendicularLineDiagNULL() {
		assertNull(line.getPerpendicularLine(null));
	}

	@Theory
	public void testGetPerpendicularLineDiagKO(@DoubleData(bads = true, vals = {}) final double x, @DoubleData(bads = true, vals = {})
	final double y) {
		assertNull(line.getPerpendicularLine(ShapeFactory.INST.createPoint(x, y)));
	}

	@Test
	public void testGetIntersection() {
		ILine line2 = ShapeFactory.INST.createLine(72, -981, 0, 0);
		line.setLine(-237, 17, 13, -82);
		line.updateAandB();
		IPoint pt = line.getIntersection(line2);
		assertNotNull(pt);
		assertEqualsDouble(5.809358228, pt.getX());
		assertEqualsDouble(-79.152505858, pt.getY());
	}

	@Test
	public void testGetIntersectionNULL() {
		assertNull(line.getIntersection(null));
	}

	@Test
	public void testGetIntersectionKO() {
		assertNull(line.getIntersection(line));
		assertNull(line.getIntersection(ShapeFactory.INST.createLine(line.getX1() + 1, line.getY1() + 1, line.getX2() + 1, line.getY2() +
			1)));
	}

	@Test
	public void testGetIntersectionVert() {
		ILine line2 = ShapeFactory.INST.createLine(0, 0, 1, 0);
		line.setLine(0, -10, 0, 10);
		line.updateAandB();
		IPoint pt = line.getIntersection(line2);
		assertNotNull(pt);
		assertEqualsDouble(0d, pt.getX());
		assertEqualsDouble(0d, pt.getY());
	}

	@Test
	public void testGetIntersectionHoriz() {
		ILine line2 = ShapeFactory.INST.createLine(0, 0, 1, 0);
		line.setLine(0, -10, 0, 10);
		line.updateAandB();
		IPoint pt = line2.getIntersection(line);
		assertNotNull(pt);
		assertEqualsDouble(0d, pt.getX());
		assertEqualsDouble(0d, pt.getY());
	}

	@Test
	public void testIsVerticalLineOK() {
		line.setLine(1, 1, 1, 1);
		line.updateAandB();
		assertTrue(line.isVerticalLine());
		line.setLine(-1000, -139, -1000, 2938);
		line.updateAandB();
		assertTrue(line.isVerticalLine());
	}

	@Test
	public void testIsVerticalLineKO() {
		line.setLine(187.3503, 938, 187.37035, 938);
		line.updateAandB();
		assertFalse(line.isVerticalLine());
	}

	@Test
	public void testIsHorizontalLineOK() {
		line.setLine(1, 1, 1, 1);
		line.updateAandB();
		assertTrue(line.isHorizontalLine());
		line.setLine(0, 1, 1, 1);
		line.updateAandB();
		assertTrue(line.isHorizontalLine());
	}

	@Test
	public void testIsHorizontalLineKO() {
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
		assertEqualsDouble(0.5, pt.getX());
		assertEqualsDouble(0d, pt.getY());
	}

	@Test
	public void testGetIntersectionSegmentNULL() {
		assertNull(line.getIntersectionSegment(null));
	}

	@Test
	public void testGetIntersectionSegmentKO() {
		assertNull(line.getIntersectionSegment(line));
		assertNull(line.getIntersectionSegment(ShapeFactory.INST.createLine(1, 1, 1, 1)));
	}

	@Test
	public void testGetIntersectionSegmentVert() {
		line.setLine(0, -10, 0, 10);
		line.updateAandB();
		IPoint pt = line.getIntersectionSegment(ShapeFactory.INST.createLine(0, 0, 1, 0));
		assertNotNull(pt);
		assertEqualsDouble(0d, pt.getX());
		assertEqualsDouble(0d, pt.getY());
	}

	@Test
	public void testGetIntersectionSegmentVertKO() {
		line.setLine(1.001, -10, 1.001, 10);
		line.updateAandB();
		assertNull(line.getIntersectionSegment(ShapeFactory.INST.createLine(0, 0, 1, 0)));
	}

	@Test
	public void testGetIntersectionSegmentHoriz() {
		line.setLine(0, -10, 0, 10);
		line.updateAandB();
		IPoint pt = ShapeFactory.INST.createLine(0, 0, 1, 0).getIntersectionSegment(line);
		assertNotNull(pt);
		assertEqualsDouble(0d, pt.getX());
		assertEqualsDouble(0d, pt.getY());
	}

	@Test
	public void testGetIntersectionSegmentHorizKO() {
		line.setLine(0, -10, 0, 10);
		line.updateAandB();
		assertNull(ShapeFactory.INST.createLine(0.01, 0, 1, 0).getIntersectionSegment(line));
	}

	@Theory
	public void testFindPointsKO1(@DoubleData(bads = true, vals = {}) final double value) {
		line.setLine(1, 1, 3, 1);
		line.updateAandB();
		assertEquals(0, line.findPoints(value, 0, 10).length);
	}

	@Theory
	public void testFindPointsKO2(@DoubleData(bads = true, vals = {}) final double value) {
		line.setLine(1, 1, 3, 1);
		line.updateAandB();
		assertEquals(0, line.findPoints(0, value, 10).length);
	}

	@Theory
	public void testFindPointsKO3(@DoubleData(bads = true, vals = {}) final double value) {
		line.setLine(1, 1, 3, 1);
		line.updateAandB();
		assertEquals(0, line.findPoints(0, 0, value).length);
	}

	@Test
	public void testFindPointsNULL() {
		assertEquals(0, line.findPoints(null, 10d).length);
	}

	@Theory
	public void testFindPointsKO4(@DoubleData(bads = true, vals = {}) final double value) {
		assertEquals(0, line.findPoints(ShapeFactory.INST.createPoint(value, value), 10).length);
	}

	@Test
	public void testFindPoints() {
		line.setLine(1, 1, 3, 1);
		IPoint[] pts = line.findPoints(1, 1, 2);
		assertEqualsDouble(2, pts.length);
		assertEqualsDouble(1d, pts[0].getY());
		assertEqualsDouble(1d, pts[1].getY());

		if(pts[0].getX() < 1d) {
			assertEqualsDouble(-1d, pts[0].getX());
			assertEqualsDouble(3d, pts[1].getX());
		}else {
			assertEqualsDouble(3d, pts[0].getX());
			assertEqualsDouble(-1d, pts[1].getX());
		}
	}

	@Test
	public void testUpdateAandB() {
		line.setLine(0, 0, 1, 1);
		line.updateAandB();
		assertEqualsDouble(0d, line.getB());
		assertEqualsDouble(1d, line.getA());
	}


	@Test
	public void testUpdateAandBKO() {
		line.setLine(1, 1, 1, 1);
		line.updateAandB();
		assertTrue(Double.isNaN(line.getB()));
		assertTrue(Double.isNaN(line.getA()));
	}
}
