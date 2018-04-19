package net.sf.latexdraw.models;

import net.sf.latexdraw.data.DoubleData;
import net.sf.latexdraw.data.ValuesGenerator;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;

@RunWith(Theories.class)
public class TestMathUtils {
	@DataPoints
	public static double[] dbleValues = ValuesGenerator.doubleValues;
	@DataPoints
	public static float[] floatValues = ValuesGenerator.floatValues;

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

	@Theory
	public void testEquals(final double value) {
		assertThat(MathUtils.INST.equalsDouble(value, 0d, Math.abs(value)), is(true));
		assertThat(MathUtils.INST.equalsDouble(value, value, 0.000000001), is(true));
		assertThat(MathUtils.INST.equalsDouble(value, value + 0.000001, 0.0000001), is(false));
		assertThat(MathUtils.INST.equalsDouble(value, value + 0.000001, 0.00001), is(true));
	}

	@Theory
	public void testGetCutNumberNotCut(final double value, final double threshold) {
		assumeThat(Math.abs(threshold), greaterThan(Math.abs(value)));
		assertThat(MathUtils.INST.getCutNumber(value, threshold), closeTo(0d, 0.00001));
	}

	@Theory
	public void testGetCutNumberCut(final double value, final double threshold) {
		assumeThat(Math.abs(value), greaterThan(Math.abs(threshold)));
		assertThat(MathUtils.INST.getCutNumber(value, threshold), closeTo(value, 0.00001));
	}

	@Theory
	public void testGetCutNumberNotCutFloat(final float value, final double threshold) {
		assumeThat(Math.abs(threshold), greaterThan(Math.abs((double) value)));
		assertThat(MathUtils.INST.getCutNumber(value, threshold), is(0f));
	}

	@Theory
	public void testGetCutNumberCutFloat(final float value, final double threshold) {
		assumeThat(Math.abs((double) value), greaterThan(Math.abs(threshold)));
		assertThat(MathUtils.INST.getCutNumber(value, threshold), is(value));
	}

	@Theory
	public void testMod2pi(@DoubleData(vals = {0d, 1.1, 2.3, 3.1}) final double value,
						@DoubleData(vals = {Math.PI * 2d, Math.PI * 4d}) final double piVals) {
		assertEquals(value, MathUtils.INST.mod2pi(value + piVals), 0.0001);
	}

	@Theory
	public void testMod2piNegVal(@DoubleData(vals = {-0d, -1.1, -2.3, -3.1}) final double value,
						@DoubleData(vals = {Math.PI * -2d, Math.PI * -4d}) final double piVals) {
		assertEquals(value, MathUtils.INST.mod2pi(value + piVals), 0.0001);
	}
}
