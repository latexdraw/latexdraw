package test.glib.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.models.GLibUtilities;
import net.sf.latexdraw.models.ShapeFactory;

import org.junit.Test;

public class TestGLibUtilities {
	@Test
	public void testIsValidPoint1() {
		assertFalse(GLibUtilities.isValidPoint(null));
		assertFalse(GLibUtilities.isValidPoint(ShapeFactory.createPoint(Double.NaN, 0)));
		assertFalse(GLibUtilities.isValidPoint(ShapeFactory.createPoint(Double.NaN, Double.NaN)));
		assertFalse(GLibUtilities.isValidPoint(ShapeFactory.createPoint(0, Double.NaN)));
		assertFalse(GLibUtilities.isValidPoint(ShapeFactory.createPoint(Double.POSITIVE_INFINITY, 0)));
		assertFalse(GLibUtilities.isValidPoint(ShapeFactory.createPoint(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)));
		assertFalse(GLibUtilities.isValidPoint(ShapeFactory.createPoint(0, Double.POSITIVE_INFINITY)));
		assertFalse(GLibUtilities.isValidPoint(ShapeFactory.createPoint(Double.NEGATIVE_INFINITY, 0)));
		assertFalse(GLibUtilities.isValidPoint(ShapeFactory.createPoint(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY)));
		assertFalse(GLibUtilities.isValidPoint(ShapeFactory.createPoint(0, Double.NEGATIVE_INFINITY)));
		assertFalse(GLibUtilities.isValidPoint(ShapeFactory.createPoint(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY)));
		assertFalse(GLibUtilities.isValidPoint(ShapeFactory.createPoint(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)));
		assertFalse(GLibUtilities.isValidPoint(ShapeFactory.createPoint(Double.NaN, Double.NEGATIVE_INFINITY)));
		assertFalse(GLibUtilities.isValidPoint(ShapeFactory.createPoint(Double.NEGATIVE_INFINITY, Double.NaN)));
		assertFalse(GLibUtilities.isValidPoint(ShapeFactory.createPoint(Double.NaN, Double.POSITIVE_INFINITY)));
		assertFalse(GLibUtilities.isValidPoint(ShapeFactory.createPoint(Double.POSITIVE_INFINITY, Double.NaN)));
		assertTrue(GLibUtilities.isValidPoint(ShapeFactory.createPoint(0, 0)));
		assertTrue(GLibUtilities.isValidPoint(ShapeFactory.createPoint(1000000, 1000000)));
		assertTrue(GLibUtilities.isValidPoint(ShapeFactory.createPoint(-1000000, -1000000)));
	}

	@Test
	public void testIsValidPoint2() {
		assertFalse(GLibUtilities.isValidPoint(Double.NaN, 0));
		assertFalse(GLibUtilities.isValidPoint(Double.NaN, Double.NaN));
		assertFalse(GLibUtilities.isValidPoint(0, Double.NaN));
		assertFalse(GLibUtilities.isValidPoint(Double.POSITIVE_INFINITY, 0));
		assertFalse(GLibUtilities.isValidPoint(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
		assertFalse(GLibUtilities.isValidPoint(0, Double.POSITIVE_INFINITY));
		assertFalse(GLibUtilities.isValidPoint(Double.NEGATIVE_INFINITY, 0));
		assertFalse(GLibUtilities.isValidPoint(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY));
		assertFalse(GLibUtilities.isValidPoint(0, Double.NEGATIVE_INFINITY));
		assertFalse(GLibUtilities.isValidPoint(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
		assertFalse(GLibUtilities.isValidPoint(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY));
		assertFalse(GLibUtilities.isValidPoint(Double.NaN, Double.NEGATIVE_INFINITY));
		assertFalse(GLibUtilities.isValidPoint(Double.NEGATIVE_INFINITY, Double.NaN));
		assertFalse(GLibUtilities.isValidPoint(Double.NaN, Double.POSITIVE_INFINITY));
		assertFalse(GLibUtilities.isValidPoint(Double.POSITIVE_INFINITY, Double.NaN));
		assertTrue(GLibUtilities.isValidPoint(0, 0));
		assertTrue(GLibUtilities.isValidPoint(1000000, 1000000));
		assertTrue(GLibUtilities.isValidPoint(-1000000, -1000000));
	}

	@Test
	public void testIsValidCoordinate() {
		assertFalse(GLibUtilities.isValidCoordinate(Double.NaN));
		assertFalse(GLibUtilities.isValidCoordinate(Double.POSITIVE_INFINITY));
		assertFalse(GLibUtilities.isValidCoordinate(Double.NEGATIVE_INFINITY));
		assertTrue(GLibUtilities.isValidCoordinate(0));
		assertTrue(GLibUtilities.isValidCoordinate(1000000));
		assertTrue(GLibUtilities.isValidCoordinate(-1000000));
	}
}
