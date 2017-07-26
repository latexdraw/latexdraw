package net.sf.latexdraw.models.interfaces;

import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.data.DoubleData;
import net.sf.latexdraw.data.LineArcData;
import net.sf.latexdraw.models.interfaces.prop.ILineArcProp;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class TestILineArcProp implements HelperTest {
	@Theory
	public void testCopy(@LineArcData final ILineArcProp shape, @LineArcData final ILineArcProp shape2) {
		shape2.setLineArc(0.55);
		((IShape) shape).copy((IShape) shape2);
		assertEqualsDouble(0.55, shape.getLineArc());
		assertTrue(shape.isRoundCorner());
	}

	@Theory
	public void testGetSetLineArcOK(@LineArcData final ILineArcProp shape, @DoubleData(vals = {0d, 0.001, 0.5, 1d}) final double value) {
		shape.setLineArc(value);
		assertEqualsDouble(value, shape.getLineArc());
	}

	@Theory
	public void testGetSetLineArcKO(@LineArcData final ILineArcProp shape, @DoubleData(vals = {1.0001d, -0.001}, bads = true) final double
		value) {
		final double val = shape.getLineArc();
		shape.setLineArc(value);
		assertEqualsDouble(val, shape.getLineArc());
	}

	@Theory
	public void testIsSetRoundOK(@LineArcData final ILineArcProp shape, @DoubleData(vals = {0.001, 0.5, 1d}) final double value) {
		shape.setLineArc(value);
		assertTrue(shape.isRoundCorner());
	}

	@Theory
	public void testIsSetRoundKO(@LineArcData final ILineArcProp shape, @DoubleData(vals = {0d}) final double value) {
		shape.setLineArc(value);
		assertFalse(shape.isRoundCorner());
	}
}
