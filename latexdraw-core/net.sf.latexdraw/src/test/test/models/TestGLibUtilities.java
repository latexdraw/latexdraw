package test.models;

import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.ShapeFactory;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestGLibUtilities {
	@Test
	public void testIsValidPointNull() {
		assertFalse(MathUtils.INST.isValidPt(null));
	}

	@Test
	public void testIsValidPointNaN0() {
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(Double.NaN, 0)));
	}

	@Test
	public void testIsValidPointNaNNaN() {
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(Double.NaN, Double.NaN)));
	}

	@Test
	public void testIsValidPoint0NaN() {
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(0, Double.NaN)));
	}

	@Test
	public void testIsValidPointPOSINF0() {
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(Double.POSITIVE_INFINITY, 0)));
	}

	@Test
	public void testIsValidPointPOSINFPOSINF() {
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)));
	}

	@Test
	public void testIsValidPoint0POSINF() {
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(0, Double.POSITIVE_INFINITY)));
	}

	@Test
	public void testIsValidPointNEGINF0() {
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(Double.NEGATIVE_INFINITY, 0)));
	}

	@Test
	public void testIsValidPointNEGINFNEGINF() {
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY)));
	}

	@Test
	public void testIsValidPoint0NEGINF() {
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(0, Double.NEGATIVE_INFINITY)));
	}

	@Test
	public void testIsValidPointNEGINFPOSINF() {
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY)));
	}

	@Test
	public void testIsValidPointPOSINFNEGINF() {
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY)));
	}

	@Test
	public void testIsValidPointNANNEGINF() {
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(Double.NaN, Double.NEGATIVE_INFINITY)));
	}

	@Test
	public void testIsValidPointNEGINFNAN() {
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(Double.NEGATIVE_INFINITY, Double.NaN)));
	}

	@Test
	public void testIsValidPointNANPOSINF() {
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(Double.NaN, Double.POSITIVE_INFINITY)));
	}

	@Test
	public void testIsValidPointPOSINFNAN() {
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(Double.POSITIVE_INFINITY, Double.NaN)));
	}

	@Test
	public void testIsValidPoint00() {
		assertTrue(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(0, 0)));
	}

	@Test
	public void testIsValidPointPOSPOS() {
		assertTrue(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(1000000, 1000000)));
	}

	@Test
	public void testIsValidPointNEGNEG() {
		assertTrue(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(-1000000, -1000000)));
	}

	@Test
	public void testIsValidPointCoordsNAN0() {
		assertFalse(MathUtils.INST.isValidPt(Double.NaN, 0));
	}

	@Test
	public void testIsValidPointCoordsNANNAN() {
		assertFalse(MathUtils.INST.isValidPt(Double.NaN, Double.NaN));
	}

	@Test
	public void testIsValidPointCoords0NAN() {
		assertFalse(MathUtils.INST.isValidPt(0, Double.NaN));
	}

	@Test
	public void testIsValidPointCoordsPOSINF0() {
		assertFalse(MathUtils.INST.isValidPt(Double.POSITIVE_INFINITY, 0));
	}

	@Test
	public void testIsValidPointCoordsPOSINFPOSINF() {
		assertFalse(MathUtils.INST.isValidPt(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
	}

	@Test
	public void testIsValidPointCoords0POSINF() {
		assertFalse(MathUtils.INST.isValidPt(0, Double.POSITIVE_INFINITY));
	}

	@Test
	public void testIsValidPointCoordsNEGINF0() {
		assertFalse(MathUtils.INST.isValidPt(Double.NEGATIVE_INFINITY, 0));
	}

	@Test
	public void testIsValidPointCoordsNEGINFNEGINF() {
		assertFalse(MathUtils.INST.isValidPt(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY));
	}

	@Test
	public void testIsValidPointCoords0NEGINF() {
		assertFalse(MathUtils.INST.isValidPt(0, Double.NEGATIVE_INFINITY));
	}

	@Test
	public void testIsValidPointCoordsNEGINFPOSINF() {
		assertFalse(MathUtils.INST.isValidPt(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
	}

	@Test
	public void testIsValidPointCoordsPOSINFNEGINF() {
		assertFalse(MathUtils.INST.isValidPt(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY));
	}

	@Test
	public void testIsValidPointCoordsNANNEGINF() {
		assertFalse(MathUtils.INST.isValidPt(Double.NaN, Double.NEGATIVE_INFINITY));
	}

	@Test
	public void testIsValidPointCoordsNEGINFNAN() {
		assertFalse(MathUtils.INST.isValidPt(Double.NEGATIVE_INFINITY, Double.NaN));
	}

	@Test
	public void testIsValidPointCoordsNANPOSINF() {
		assertFalse(MathUtils.INST.isValidPt(Double.NaN, Double.POSITIVE_INFINITY));
	}

	@Test
	public void testIsValidPointCoordsPOSINFNAN() {
		assertFalse(MathUtils.INST.isValidPt(Double.POSITIVE_INFINITY, Double.NaN));
	}

	@Test
	public void testIsValidPointCoords00() {
		assertTrue(MathUtils.INST.isValidPt(0, 0));
	}

	@Test
	public void testIsValidPointCoordsPOSPOS() {
		assertTrue(MathUtils.INST.isValidPt(1000000, 1000000));
	}

	@Test
	public void testIsValidPointCoordsNEGNEG() {
		assertTrue(MathUtils.INST.isValidPt(-1000000, -1000000));
	}

	@Test
	public void testIsValidCoordinateNAN() {
		assertFalse(MathUtils.INST.isValidCoord(Double.NaN));
	}

	@Test
	public void testIsValidCoordinatePOSINF() {
		assertFalse(MathUtils.INST.isValidCoord(Double.POSITIVE_INFINITY));
	}

	@Test
	public void testIsValidCoordinateNEGINF() {
		assertFalse(MathUtils.INST.isValidCoord(Double.NEGATIVE_INFINITY));
	}

	@Test
	public void testIsValidCoordinate0() {
		assertTrue(MathUtils.INST.isValidCoord(0));
	}

	@Test
	public void testIsValidCoordinatePOS() {
		assertTrue(MathUtils.INST.isValidCoord(1000000));
	}

	@Test
	public void testIsValidCoordinateNEG() {
		assertTrue(MathUtils.INST.isValidCoord(-1000000));
	}
}
