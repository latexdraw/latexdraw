package test.util;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import net.sf.latexdraw.models.MathUtils;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;

@RunWith(JUnitQuickcheck.class)
public class TestMathUtils {
	@Property
	public void testEquals(final @InRange(minDouble = -2d, maxDouble = -2d) double value) {
		assertTrue(MathUtils.INST.equalsDouble(value, 0d, Math.abs(value)));
		assertTrue(MathUtils.INST.equalsDouble(value, value, 0.000000001));
		assertFalse(MathUtils.INST.equalsDouble(value, value+0.000001, 0.0000001));
		assertTrue(MathUtils.INST.equalsDouble(value, value+0.000001, 0.00001));
	}

	@Property
	public void testGetCutNumberNotCut(final @InRange(minDouble = -2d, maxDouble = 2d) double value, final @InRange(minDouble = -2d, maxDouble = 2d) double threshold) {
		assumeThat(Math.abs(threshold), greaterThan(Math.abs(value)));
		assertEquals(0d, MathUtils.INST.getCutNumber(value, threshold), 0.00001);
	}

	@Property
	public void testGetCutNumberCut(final @InRange(minDouble = -2d, maxDouble = 2d) double value, final @InRange(minDouble = -2d, maxDouble = 2d) double threshold) {
		assumeThat(Math.abs(value), greaterThan(Math.abs(threshold)));
		assertEquals(value, MathUtils.INST.getCutNumber(value, threshold), 0.00001);
	}

	@Property
	public void testGetCutNumberNotCutFloat(final @InRange(minFloat = -2f, maxFloat = 2f) float value, final @InRange(minDouble = -2d, maxDouble = 2d) double threshold) {
		assumeThat(Math.abs(threshold), greaterThan(Math.abs((double)value)));
		assertEquals(0d, MathUtils.INST.getCutNumber(value, threshold), 0.00001);
	}

	@Property
	public void testGetCutNumberCutFloat(final @InRange(minFloat = -2f, maxFloat = 2f) float value, final @InRange(minDouble = -2d, maxDouble = 2d) double threshold) {
		assumeThat(Math.abs((double)value), greaterThan(Math.abs(threshold)));
		assertEquals(value, MathUtils.INST.getCutNumber(value, threshold), 0.00001);
	}
}
