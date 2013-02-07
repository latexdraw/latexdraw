package test.glib.models ;

import junit.framework.TestCase;
import net.sf.latexdraw.glib.models.impl.LShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.GLibUtilities;

import org.junit.Before;
import org.junit.Test;


public class TestGLibUtilities extends TestCase {
	@Override
	@Before
	public void setUp() {
		DrawingTK.setFactory(new LShapeFactory());
	}

	@Test
	public void testIsValidPoint1() {
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(null));
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(DrawingTK.getFactory().createPoint(Double.NaN, 0)));
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(DrawingTK.getFactory().createPoint(Double.NaN, Double.NaN)));
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(DrawingTK.getFactory().createPoint(0, Double.NaN)));
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(DrawingTK.getFactory().createPoint(Double.POSITIVE_INFINITY, 0)));
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(DrawingTK.getFactory().createPoint(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)));
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(DrawingTK.getFactory().createPoint(0, Double.POSITIVE_INFINITY)));
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(DrawingTK.getFactory().createPoint(Double.NEGATIVE_INFINITY, 0)));
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(DrawingTK.getFactory().createPoint(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY)));
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(DrawingTK.getFactory().createPoint(0, Double.NEGATIVE_INFINITY)));
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(DrawingTK.getFactory().createPoint(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY)));
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(DrawingTK.getFactory().createPoint(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)));
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(DrawingTK.getFactory().createPoint(Double.NaN, Double.NEGATIVE_INFINITY)));
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(DrawingTK.getFactory().createPoint(Double.NEGATIVE_INFINITY, Double.NaN)));
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(DrawingTK.getFactory().createPoint(Double.NaN, Double.POSITIVE_INFINITY)));
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(DrawingTK.getFactory().createPoint(Double.POSITIVE_INFINITY, Double.NaN)));
		assertTrue(GLibUtilities.INSTANCE.isValidPoint(DrawingTK.getFactory().createPoint(0, 0)));
		assertTrue(GLibUtilities.INSTANCE.isValidPoint(DrawingTK.getFactory().createPoint(1000000, 1000000)));
		assertTrue(GLibUtilities.INSTANCE.isValidPoint(DrawingTK.getFactory().createPoint(-1000000, -1000000)));
	}



	@Test
	public void testIsValidPoint2() {
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(Double.NaN, 0));
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(Double.NaN, Double.NaN));
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(0, Double.NaN));
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(Double.POSITIVE_INFINITY, 0));
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(0, Double.POSITIVE_INFINITY));
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(Double.NEGATIVE_INFINITY, 0));
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY));
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(0, Double.NEGATIVE_INFINITY));
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY));
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(Double.NaN, Double.NEGATIVE_INFINITY));
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(Double.NEGATIVE_INFINITY, Double.NaN));
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(Double.NaN, Double.POSITIVE_INFINITY));
		assertFalse(GLibUtilities.INSTANCE.isValidPoint(Double.POSITIVE_INFINITY, Double.NaN));
		assertTrue(GLibUtilities.INSTANCE.isValidPoint(0, 0));
		assertTrue(GLibUtilities.INSTANCE.isValidPoint(1000000, 1000000));
		assertTrue(GLibUtilities.INSTANCE.isValidPoint(-1000000, -1000000));
	}



	@Test
	public void testIsValidCoordinate() {
		assertFalse(GLibUtilities.INSTANCE.isValidCoordinate(Double.NaN));
		assertFalse(GLibUtilities.INSTANCE.isValidCoordinate(Double.POSITIVE_INFINITY));
		assertFalse(GLibUtilities.INSTANCE.isValidCoordinate(Double.NEGATIVE_INFINITY));
		assertTrue(GLibUtilities.INSTANCE.isValidCoordinate(0));
		assertTrue(GLibUtilities.INSTANCE.isValidCoordinate(1000000));
		assertTrue(GLibUtilities.INSTANCE.isValidCoordinate(-1000000));
	}
}
