package net.sf.latexdraw.model.api.property;

import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.data.DoubleData;
import net.sf.latexdraw.data.LineArcData;
import net.sf.latexdraw.model.api.shape.Shape;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class TestLineArcProp implements HelperTest {
	@Theory
	public void testCopy(@LineArcData final LineArcProp shape, @LineArcData final LineArcProp shape2) {
		shape2.setLineArc(0.55);
		((Shape) shape).copy((Shape) shape2);
		assertEqualsDouble(0.55, shape.getLineArc());
		assertTrue(shape.isRoundCorner());
	}

	@Theory
	public void testGetSetLineArcOK(@LineArcData final LineArcProp shape, @DoubleData(vals = {0d, 0.001, 0.5, 1d}) final double value) {
		shape.setLineArc(value);
		assertEqualsDouble(value, shape.getLineArc());
	}

	@Theory
	public void testGetSetLineArcKO(@LineArcData final LineArcProp shape, @DoubleData(vals = {1.0001d, -0.001}, bads = true) final double
		value) {
		final double val = shape.getLineArc();
		shape.setLineArc(value);
		assertEqualsDouble(val, shape.getLineArc());
	}

	@Theory
	public void testIsSetRoundOK(@LineArcData final LineArcProp shape, @DoubleData(vals = {0.001, 0.5, 1d}) final double value) {
		shape.setLineArc(value);
		assertTrue(shape.isRoundCorner());
	}

	@Theory
	public void testIsSetRoundKO(@LineArcData final LineArcProp shape) {
		shape.setLineArc(0d);
		assertFalse(shape.isRoundCorner());
	}
}
