package test.util;

import net.sf.latexdraw.models.MathUtils;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

@RunWith(Theories.class)
public class TestMathUtils {
	@DataPoints public static double[] dbleValues = ValuesGenerator.posNegZeroDoubleValues;
	@DataPoints public static float[] floatValues = ValuesGenerator.posNegZeroFloatValues;

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
}
