package test.models.interfaces;

import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import test.HelperTest;
import test.data.DoubleData;
import test.data.RectData;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class TestIRectangle implements HelperTest {
	@Theory
	public void testCopy(@RectData final IRectangle shape, @RectData final IRectangle shape2) {
		shape2.setLineArc(0.55);
		shape.copy(shape2);
		assertEqualsDouble(0.55, shape.getLineArc());
		assertTrue(shape.isRoundCorner());
	}

	@Theory
	public void testGetSetLineArcOK(@RectData final IRectangle shape, @DoubleData(vals = {0d, 0.001, 0.5, 1d}) final double value) {
		shape.setLineArc(value);
		assertEqualsDouble(value, shape.getLineArc());
	}

	@Theory
	public void testGetSetLineArcKO(@RectData final IRectangle shape, @DoubleData(vals = {1.0001d, -0.001}, bads = true) final double value) {
		final double val = shape.getLineArc();
		shape.setLineArc(value);
		assertEqualsDouble(val, shape.getLineArc());
	}

	@Theory
	public void testIsSetRoundOK(@RectData final IRectangle shape, @DoubleData(vals = {0.001, 0.5, 1d}) final double value) {
		shape.setLineArc(value);
		assertTrue(shape.isRoundCorner());
	}

	@Theory
	public void testIsSetRoundKO(@RectData final IRectangle shape, @DoubleData(vals = {0d}) final double value) {
		shape.setLineArc(value);
		assertFalse(shape.isRoundCorner());
	}
}
