package test.glib.models.interfaces;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.glib.models.interfaces.shape.IRectangle;

import org.junit.Test;

import test.HelperTest;

public abstract class TestIRectangle<T extends IRectangle> extends TestIRectangularShape<T> {
	@Override
	@Test
	public void testCopy() {
		super.testCopy();

		shape2.setLineArc(0.55);
		shape.copy(shape2);
		HelperTest.assertEqualsDouble(0.55, shape.getLineArc());
		assertTrue(shape.isRoundCorner());
	}

	@Test
	public void testGetSetLineArc() {
		shape.setLineArc(0.5);
		HelperTest.assertEqualsDouble(0.5, shape.getLineArc());
		shape.setLineArc(1);
		HelperTest.assertEqualsDouble(1., shape.getLineArc());
		shape.setLineArc(0);
		HelperTest.assertEqualsDouble(0., shape.getLineArc());
		shape.setLineArc(-0.0001);
		HelperTest.assertEqualsDouble(0., shape.getLineArc());
		shape.setLineArc(1.0001);
		HelperTest.assertEqualsDouble(0., shape.getLineArc());
		shape.setLineArc(Double.NaN);
		HelperTest.assertEqualsDouble(0., shape.getLineArc());
		shape.setLineArc(Double.NEGATIVE_INFINITY);
		HelperTest.assertEqualsDouble(0., shape.getLineArc());
		shape.setLineArc(Double.POSITIVE_INFINITY);
		HelperTest.assertEqualsDouble(0., shape.getLineArc());
	}

	@Test
	public void testIsSetRound() {
		shape.setLineArc(0.1);
		assertTrue(shape.isRoundCorner());
		shape.setLineArc(0.);
		assertFalse(shape.isRoundCorner());
		shape.setLineArc(1.);
		assertTrue(shape.isRoundCorner());

	}
}
