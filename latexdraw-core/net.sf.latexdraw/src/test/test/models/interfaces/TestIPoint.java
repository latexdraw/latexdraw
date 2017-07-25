package test.models.interfaces;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import test.HelperTest;
import test.data.DoubleData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class TestIPoint implements HelperTest {
	IPoint pt;

	@Before
	public void setUp() throws Exception {
		pt = ShapeFactory.INST.createPoint();
	}

	@Theory
	public void testLPointOK(@DoubleData final double x, @DoubleData final double y) {
		final IPoint point = ShapeFactory.INST.createPoint(x, y);
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
		assertEquals(ShapeFactory.INST.createPoint(-1, 0), ShapeFactory.INST.createPoint(0, 1).rotatePoint(ShapeFactory.INST.createPoint()
			, Math.PI / 2.));
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
		assertEquals(Double.NaN, pt.computeAngle(null), 0.1);
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
		assertEquals(Double.NaN, pt.computeRotationAngle(null, null), 0.1);
		assertEquals(Double.NaN, pt.computeRotationAngle(ShapeFactory.INST.createPoint(), null), 0.1);
		assertEquals(Double.NaN, pt.computeRotationAngle(null, ShapeFactory.INST.createPoint()), 0.1);
	}

	@Test
	public void testComputeRotationAngle() {
		assertEquals(0., pt.computeRotationAngle(ShapeFactory.INST.createPoint(1, 0), ShapeFactory.INST.createPoint(1, 0)), 0.1);
		assertEquals(Math.PI / 2., pt.computeRotationAngle(ShapeFactory.INST.createPoint(1, 0), ShapeFactory.INST.createPoint(0, 1)), 0.1);
		assertEquals(Math.PI, Math.abs(pt.computeRotationAngle(ShapeFactory.INST.createPoint(1, 0), ShapeFactory.INST.createPoint(-1, 0)))
			, 0.1);
		assertEquals(-Math.PI / 2., pt.computeRotationAngle(ShapeFactory.INST.createPoint(0, 1), ShapeFactory.INST.createPoint(1, 0)),
			0.1);
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

	@Theory
	public void testGetMiddlePoint(@DoubleData final double x1, @DoubleData final double x2, @DoubleData final double y1, @DoubleData
	final double y2) {
		assertEquals(ShapeFactory.INST.createPoint((x1 + x2) / 2d, (y1 + y2) / 2d), ShapeFactory.INST.createPoint(x1, y1).getMiddlePoint
			(ShapeFactory.INST.createPoint(x2, y2)));

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
	public void testTranslateKO(@DoubleData(bads = true, vals = {0d}) final double tx, @DoubleData(bads = true, vals = {0d}) final double
		ty) {
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
		final IPoint sym = pt.horizontalSymmetry(10d);
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
		final IPoint sym = pt.verticalSymmetry(20d);
		assertEqualsDouble(pt.getX(), sym.getX());
		assertEqualsDouble(20d, sym.getY());
	}

	@Theory
	public void testVerticalSymmetry(@DoubleData final double y) {
		pt.setPoint(10d, 20d);
		assertEquals(ShapeFactory.INST.createPoint(10d, 2d * y - pt.getY()), pt.verticalSymmetry(y));
	}
}
