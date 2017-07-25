package test.models;

import net.sf.latexdraw.models.MathUtils;
import net.sf.latexdraw.models.ShapeFactory;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import test.data.DoubleData;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class TestGLibUtilities {
	@Test
	public void testIsValidPointNull() {
		assertFalse(MathUtils.INST.isValidPt(null));
	}

	@Theory
	public void testIsValidPointKOX(@DoubleData(vals = {}, bads = true) final double value) {
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(value, 0d)));
	}

	@Theory
	public void testIsValidPointKOY(@DoubleData(vals = {}, bads = true) final double value) {
		assertFalse(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(0d, value)));
	}

	@Theory
	public void testIsValidPoint(@DoubleData final double x, @DoubleData final double y) {
		assertTrue(MathUtils.INST.isValidPt(ShapeFactory.INST.createPoint(x, y)));
	}


	@Theory
	public void testIsValidCoordinateKO(@DoubleData(vals = {}, bads = true) final double value) {
		assertFalse(MathUtils.INST.isValidCoord(value));
	}

	@Theory
	public void testIsValidCoordinate(@DoubleData final double value) {
		assertTrue(MathUtils.INST.isValidCoord(value));
	}
}
