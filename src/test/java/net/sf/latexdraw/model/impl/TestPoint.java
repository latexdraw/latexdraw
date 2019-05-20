package net.sf.latexdraw.model.impl;

import java.awt.geom.Point2D;
import javafx.geometry.Point3D;
import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.data.DoubleData;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Point;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class TestPoint implements HelperTest {
	Point pt;

	@Before
	public void setUp() {
		pt = ShapeFactory.INST.createPoint();
	}

	@Theory
	public void testLPointOK(@DoubleData final double x, @DoubleData final double y) {
		final Point point = ShapeFactory.INST.createPoint(x, y);
		assertEqualsDouble(x, point.getX());
		assertEqualsDouble(y, point.getY());
	}

	@Test
	public void testRotatePointKO() {
		assertNull(pt.rotatePoint(null, 0));
	}

	@Theory
	public void testRotatePointKO2(@DoubleData(bads = true, vals = {}) final double value) {
		assertNull(pt.rotatePoint(ShapeFactory.INST.createPoint(), value));
	}

	@Test
	public void testRotatePoint() {
		assertEquals(ShapeFactory.INST.createPoint(-1, 0), ShapeFactory.INST.createPoint(0, 1).rotatePoint(ShapeFactory.INST.createPoint(), Math.PI / 2.));
		assertEquals(ShapeFactory.INST.createPoint(-1, 0), ShapeFactory.INST.createPoint(1, 0).rotatePoint(pt, Math.PI));
		assertEquals(ShapeFactory.INST.createPoint(-1, 0), ShapeFactory.INST.createPoint(1, 0).rotatePoint(pt, -Math.PI));
		assertEquals(ShapeFactory.INST.createPoint(1, 0), ShapeFactory.INST.createPoint(1, 0).rotatePoint(pt, 2. * Math.PI));
		assertEquals(ShapeFactory.INST.createPoint(1, 0), ShapeFactory.INST.createPoint(1, 0).rotatePoint(pt, -2. * Math.PI));
		assertEquals(ShapeFactory.INST.createPoint(0, -1), ShapeFactory.INST.createPoint(0, -1).rotatePoint(pt, 0));
	}

	@Test
	public void testCentralSymmetry() {
		assertEquals(ShapeFactory.INST.createPoint(-1, 0), ShapeFactory.INST.createPoint(1, 0).centralSymmetry(pt));
		assertEquals(ShapeFactory.INST.createPoint(0, -1), ShapeFactory.INST.createPoint(0, 1).centralSymmetry(pt));
		assertEquals(ShapeFactory.INST.createPoint(1, 0), ShapeFactory.INST.createPoint(-1, 0).centralSymmetry(pt));
		assertEquals(ShapeFactory.INST.createPoint(0, 1), ShapeFactory.INST.createPoint(0, -1).centralSymmetry(pt));
	}

	@Test
	public void testCentralSymmetryKO() {
		assertNull(pt.centralSymmetry(null));
	}

	@Test
	public void testComputeAngleKO() {
		assertTrue(Double.isNaN(pt.computeAngle(null)));
	}

	@Test
	public void testComputeAngle00() {
		assertEquals(0., pt.computeAngle(ShapeFactory.INST.createPoint(1, 0)), 0.1);
		assertEquals(Math.PI / 2., pt.computeAngle(ShapeFactory.INST.createPoint(0, 1)), 0.1);
		assertEquals(Math.PI, pt.computeAngle(ShapeFactory.INST.createPoint(-1, 0)), 0.1);
		assertEquals(3. * Math.PI / 2., pt.computeAngle(ShapeFactory.INST.createPoint(0, -1)), 0.1);
	}

	@Test
	public void testComputeAngle11() {
		pt.setPoint(1, 1);
		assertEquals(0., pt.computeAngle(ShapeFactory.INST.createPoint(2, 1)), 0.1);
		assertEquals(Math.PI / 2., pt.computeAngle(ShapeFactory.INST.createPoint(1, 2)), 0.1);
		assertEquals(Math.PI, pt.computeAngle(ShapeFactory.INST.createPoint(0, 1)), 0.1);
		assertEquals(3. * Math.PI / 2., pt.computeAngle(ShapeFactory.INST.createPoint(1, 0)), 0.1);
	}

	@Test
	public void testComputeRotationAngleKO() {
		assertTrue(Double.isNaN(pt.computeRotationAngle(null, null)));
		assertTrue(Double.isNaN(pt.computeRotationAngle(ShapeFactory.INST.createPoint(), null)));
		assertTrue(Double.isNaN(pt.computeRotationAngle(null, ShapeFactory.INST.createPoint())));
	}

	@Test
	public void testComputeRotationAngle() {
		assertEquals(0., pt.computeRotationAngle(ShapeFactory.INST.createPoint(1, 0), ShapeFactory.INST.createPoint(1, 0)), 0.1);
		assertEquals(Math.PI / 2., pt.computeRotationAngle(ShapeFactory.INST.createPoint(1, 0), ShapeFactory.INST.createPoint(0, 1)), 0.1);
		assertEquals(Math.PI, Math.abs(pt.computeRotationAngle(ShapeFactory.INST.createPoint(1, 0), ShapeFactory.INST.createPoint(-1, 0))), 0.1);
		assertEquals(-Math.PI / 2., pt.computeRotationAngle(ShapeFactory.INST.createPoint(0, 1), ShapeFactory.INST.createPoint(1, 0)), 0.1);
	}

	@Test
	public void testEqualsKONULL() {
		assertFalse(ShapeFactory.INST.createPoint().equals(null, 0));
	}

	@Theory
	public void testEqualsKODELTA(@DoubleData(bads = true, vals = {}) final double value) {
		assertFalse(ShapeFactory.INST.createPoint().equals(ShapeFactory.INST.createPoint(), value));
	}

	@Theory
	public void testEqualsKO2(@DoubleData(bads = true, vals = {}) final double x, @DoubleData(bads = true, vals = {}) final double y) {
		assertFalse(ShapeFactory.INST.createPoint(0d, 0d).equals(ShapeFactory.INST.createPoint(x, y), 1));
	}

	@Theory
	public void testEqualsKO1(@DoubleData(bads = true, vals = {}) final double x, @DoubleData(bads = true, vals = {}) final double y) {
		assertFalse(ShapeFactory.INST.createPoint(x, y).equals(ShapeFactory.INST.createPoint(0d, 0d), 1));
	}

	@Test
	public void testEquals() {
		assertFalse(ShapeFactory.INST.createPoint(0, 0).equals(ShapeFactory.INST.createPoint(1, 1), 0));
		assertTrue(ShapeFactory.INST.createPoint(0, 0).equals(ShapeFactory.INST.createPoint(0, 0), 0));
		assertTrue(ShapeFactory.INST.createPoint(-1, -1).equals(ShapeFactory.INST.createPoint(-10, -10), 9));
		assertTrue(ShapeFactory.INST.createPoint(0, 0).equals(ShapeFactory.INST.createPoint(0.000001, 0), 0.0001));
		assertTrue(ShapeFactory.INST.createPoint(0, 0).equals(ShapeFactory.INST.createPoint(0, 0.000001), 0.0001));
	}

	@Test
	public void testEqualsNotPoint() {
		assertThat(pt.equals(new Object())).isFalse();
	}

	@Test
	public void testHashCodeSym() {
		pt.setPoint(22.1, -154.36);
		final Point p = ShapeFactory.INST.createPoint(22.1, -154.36);
		assertThat(pt.hashCode()).isEqualTo(p.hashCode());
	}

	@Test
	public void testHashCodeDiff() {
		pt.setPoint(22.1, -154.36);
		final Point p = ShapeFactory.INST.createPoint(22.1, 154.36);
		assertThat(pt.hashCode()).isNotEqualTo(p.hashCode());
	}

	@Theory
	public void testGetMiddlePoint(@DoubleData final double x1, @DoubleData final double x2, @DoubleData final double y1, @DoubleData final double y2) {
		assertEquals(ShapeFactory.INST.createPoint((x1 + x2) / 2d, (y1 + y2) / 2d), ShapeFactory.INST.createPoint(x1, y1).
			getMiddlePoint(ShapeFactory.INST.createPoint(x2, y2)));

	}

	@Test
	public void testGetMiddlePointKONULL() {
		assertNull(ShapeFactory.INST.createPoint().getMiddlePoint(null));
	}

	@Test
	public void testTranslate() {
		pt.setPoint(10d, -11d);
		pt.translate(14.5, -0.5);

		assertEqualsDouble(24.5, pt.getX());
		assertEqualsDouble(-11.5, pt.getY());
	}

	@Theory
	public void testTranslateKO(@DoubleData(bads = true, vals = {0d}) final double tx, @DoubleData(bads = true, vals = {0d}) final double ty) {
		pt.translate(10d, -11d);
		pt.translate(tx, -ty);

		assertEqualsDouble(10d, pt.getX());
		assertEqualsDouble(-11d, pt.getY());
	}

	@Theory
	public void testHorizontalSymmetryKO(@DoubleData(bads = true, vals = {}) final double x) {
		pt.setPoint(10d, 20d);
		assertNull(pt.horizontalSymmetry(x));
	}

	@Test
	public void testHorizontalSymmetrySame() {
		pt.setPoint(10d, 20d);
		final Point sym = pt.horizontalSymmetry(10d);
		assertEqualsDouble(pt.getY(), sym.getY());
		assertEqualsDouble(10d, sym.getX());
	}

	@Theory
	public void testHorizontalSymmetry(@DoubleData final double x) {
		pt.setPoint(10d, 20d);
		assertEquals(ShapeFactory.INST.createPoint(2d * x - pt.getX(), 20d), pt.horizontalSymmetry(x));
	}

	@Theory
	public void testVerticalSymmetryKO(@DoubleData(bads = true, vals = {}) final double y) {
		pt.setPoint(10d, 20d);
		assertNull(pt.verticalSymmetry(y));
	}

	@Test
	public void testVerticalSymmetrySame() {
		pt.setPoint(10d, 20d);
		final Point sym = pt.verticalSymmetry(20d);
		assertEqualsDouble(pt.getX(), sym.getX());
		assertEqualsDouble(20d, sym.getY());
	}

	@Theory
	public void testVerticalSymmetry(@DoubleData final double y) {
		pt.setPoint(10d, 20d);
		assertEquals(ShapeFactory.INST.createPoint(10d, 2d * y - pt.getY()), pt.verticalSymmetry(y));
	}

	@Test
	public void testZoom() {
		pt.setPoint(10d, 20d);
		final Point zoomed = this.pt.zoom(1.5);
		assertThat(zoomed.getX()).isEqualTo(15d, within(0.00001));
		assertThat(zoomed.getY()).isEqualTo(30d, within(0.00001));
	}

	@Test
	public void testSetPointPointOK() {
		pt.setPoint(10d, 11d);
		pt.setPoint(ShapeFactory.INST.createPoint(5.2, -3.3));
		assertThat(pt.getX()).isEqualTo(5.2, within(0.00001d));
		assertThat(pt.getY()).isEqualTo(-3.3, within(0.00001d));
	}

	@Test
	public void testSetPointPointKO() {
		pt.setPoint(10d, 11d);
		pt.setPoint(null);
		assertThat(pt.getX()).isEqualTo(10d, within(0.00001d));
		assertThat(pt.getY()).isEqualTo(11d, within(0.00001d));
	}

	@Test
	public void testDistanceKO() {
		assertThat(pt.distance(null)).isNaN();
	}

	@Test
	public void testDistanceOKY() {
		pt.setPoint(20d, 10d);
		assertThat(pt.distance(ShapeFactory.INST.createPoint(20d, 4d))).isEqualTo(6d, within(0.00001d));
	}

	@Test
	public void testDistanceOKX() {
		pt.setPoint(40d, 10d);
		assertThat(pt.distance(ShapeFactory.INST.createPoint(-20d, 10d))).isEqualTo(60d, within(0.00001d));
	}

	@Test
	public void testToPoint2D() {
		pt.setPoint(10d, 11d);
		final Point2D.Double p = pt.toPoint2D();
		assertThat(p.getX()).isEqualTo(10d, within(0.00001d));
		assertThat(p.getY()).isEqualTo(11d, within(0.00001d));
	}

	@Test
	public void testToPoint3D() {
		pt.setPoint(10d, 11d);
		final Point3D p = pt.toPoint3D();
		assertThat(p.getX()).isEqualTo(10d, within(0.00001d));
		assertThat(p.getY()).isEqualTo(11d, within(0.00001d));
		assertThat(p.getZ()).isZero();
	}

	@Test
	public void testSetPoint2DKO() {
		pt.setPoint(10d, 11d);
		pt.setPoint2D(null);
		assertThat(pt.getX()).isEqualTo(10d, within(0.00001d));
		assertThat(pt.getY()).isEqualTo(11d, within(0.00001d));
	}

	@Test
	public void testSetPoint2DOK() {
		pt.setPoint(10d, 11d);
		pt.setPoint2D(new Point2D.Double(-2d, 4d));
		assertThat(pt.getX()).isEqualTo(-2d, within(0.00001d));
		assertThat(pt.getY()).isEqualTo(4d, within(0.00001d));
	}

	@Test
	public void testSubstractKO() {
		pt.setPoint(10d, 11d);
		final Point p = pt.substract(null);
		assertThat(p.getX()).isEqualTo(10d, within(0.00001d));
		assertThat(p.getY()).isEqualTo(11d, within(0.00001d));
	}

	@Test
	public void testSubstractOK() {
		pt.setPoint(10d, 11d);
		final Point p = pt.substract(ShapeFactory.INST.createPoint(4d, -5d));
		assertThat(p.getX()).isEqualTo(6d, within(0.00001d));
		assertThat(p.getY()).isEqualTo(16d, within(0.00001d));
	}

	@Test
	public void testNormalise() {
		pt.setPoint(10d, -11d);
		final double magnitude = pt.magnitude();
		final Point p = pt.normalise();
		assertThat(p.getX()).isEqualTo(10d / magnitude, within(0.00001d));
		assertThat(p.getY()).isEqualTo(-11d / magnitude, within(0.00001d));
	}

	@Test
	public void testMagnitude() {
		pt.setPoint(10d, -11d);
		assertThat(pt.magnitude()).isEqualTo(Math.hypot(pt.getX(), pt.getY()), within(0.00001d));
	}

	@Test
	public void testAddKO() {
		pt.setPoint(10d, 11d);
		final Point p = pt.add(null);
		assertThat(p.getX()).isEqualTo(10d, within(0.00001d));
		assertThat(p.getY()).isEqualTo(11d, within(0.00001d));
	}

	@Test
	public void testAddOK() {
		pt.setPoint(10d, 11d);
		final Point p = pt.add(ShapeFactory.INST.createPoint(4d, -5d));
		assertThat(p.getX()).isEqualTo(14d, within(0.00001d));
		assertThat(p.getY()).isEqualTo(6d, within(0.00001d));
	}

	@Test
	public void testSetGetX() {
		pt.setX(2.3);
		assertThat(pt.getX()).isEqualTo(2.3, within(0.00001d));
	}

	@Test
	public void testSetGetY() {
		pt.setY(-2.1);
		assertThat(pt.getY()).isEqualTo(-2.1, within(0.00001d));
	}

	@Test
	public void testSetXKO() {
		pt.setX(2.3);
		pt.setX(Double.NaN);
		assertThat(pt.getX()).isEqualTo(2.3, within(0.00001d));
	}

	@Test
	public void testSetYKO() {
		pt.setY(22.3);
		pt.setY(Double.NaN);
		assertThat(pt.getY()).isEqualTo(22.3, within(0.00001d));
	}

	@Test
	public void testSetPointDoubles() {
		pt.setPoint(-12.1, 3.45);
		assertThat(pt.getX()).isEqualTo(-12.1, within(0.00001d));
		assertThat(pt.getY()).isEqualTo(3.45, within(0.00001d));
	}
}
