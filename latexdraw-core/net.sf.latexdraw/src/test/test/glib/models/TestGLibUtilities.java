package test.glib.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.ShapeFactory;

import org.junit.Test;

public class TestGLibUtilities {
	@Test
	public void testIsValidPoint1() {
		assertFalse(MathUtils.INST.isValidPt(null));
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(Double.NaN, 0)));
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(Double.NaN, Double.NaN)));
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(0, Double.NaN)));
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(Double.POSITIVE_INFINITY, 0)));
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)));
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(0, Double.POSITIVE_INFINITY)));
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(Double.NEGATIVE_INFINITY, 0)));
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY)));
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(0, Double.NEGATIVE_INFINITY)));
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY)));
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)));
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(Double.NaN, Double.NEGATIVE_INFINITY)));
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(Double.NEGATIVE_INFINITY, Double.NaN)));
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(Double.NaN, Double.POSITIVE_INFINITY)));
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(Double.POSITIVE_INFINITY, Double.NaN)));
		assertTrue(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(0, 0)));
		assertTrue(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(1000000, 1000000)));
		assertTrue(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(-1000000, -1000000)));
	}

	@Test
	public void testIsValidPoint2() {
		assertFalse(MathUtils.INST.isValidPt(Double.NaN, 0));
		assertFalse(MathUtils.INST.isValidPt(Double.NaN, Double.NaN));
		assertFalse(MathUtils.INST.isValidPt(0, Double.NaN));
		assertFalse(MathUtils.INST.isValidPt(Double.POSITIVE_INFINITY, 0));
		assertFalse(MathUtils.INST.isValidPt(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
		assertFalse(MathUtils.INST.isValidPt(0, Double.POSITIVE_INFINITY));
		assertFalse(MathUtils.INST.isValidPt(Double.NEGATIVE_INFINITY, 0));
		assertFalse(MathUtils.INST.isValidPt(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY));
		assertFalse(MathUtils.INST.isValidPt(0, Double.NEGATIVE_INFINITY));
		assertFalse(MathUtils.INST.isValidPt(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
		assertFalse(MathUtils.INST.isValidPt(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY));
		assertFalse(MathUtils.INST.isValidPt(Double.NaN, Double.NEGATIVE_INFINITY));
		assertFalse(MathUtils.INST.isValidPt(Double.NEGATIVE_INFINITY, Double.NaN));
		assertFalse(MathUtils.INST.isValidPt(Double.NaN, Double.POSITIVE_INFINITY));
		assertFalse(MathUtils.INST.isValidPt(Double.POSITIVE_INFINITY, Double.NaN));
		assertTrue(MathUtils.INST.isValidPt(0, 0));
		assertTrue(MathUtils.INST.isValidPt(1000000, 1000000));
		assertTrue(MathUtils.INST.isValidPt(-1000000, -1000000));
	}

	@Test
	public void testIsValidCoordinate() {
		assertFalse(MathUtils.INST.isValidCoord(Double.NaN));
		assertFalse(MathUtils.INST.isValidCoord(Double.POSITIVE_INFINITY));
		assertFalse(MathUtils.INST.isValidCoord(Double.NEGATIVE_INFINITY));
		assertTrue(MathUtils.INST.isValidCoord(0));
		assertTrue(MathUtils.INST.isValidCoord(1000000));
		assertTrue(MathUtils.INST.isValidCoord(-1000000));
	}
}
