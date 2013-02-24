package test.glib.models.interfaces;


import net.sf.latexdraw.glib.models.interfaces.IRectangle;

import org.junit.Test;


public abstract class TestIRectangle<T extends IRectangle> extends TestIRectangularShape<T> {
	@Override
	@Test
	public void testCopy() {
		super.testCopy();

		shape2.setLineArc(0.55);
		shape.copy(shape2);
		assertEquals(0.55, shape.getLineArc());
		assertTrue(shape.isRoundCorner());
	}



	@Test
	public void testGetSetLineArc() {
		shape.setLineArc(0.5);
		assertEquals(0.5, shape.getLineArc());
		shape.setLineArc(1);
		assertEquals(1., shape.getLineArc());
		shape.setLineArc(0);
		assertEquals(0., shape.getLineArc());
		shape.setLineArc(-0.0001);
		assertEquals(0., shape.getLineArc());
		shape.setLineArc(1.0001);
		assertEquals(0., shape.getLineArc());
		shape.setLineArc(Double.NaN);
		assertEquals(0., shape.getLineArc());
		shape.setLineArc(Double.NEGATIVE_INFINITY);
		assertEquals(0., shape.getLineArc());
		shape.setLineArc(Double.POSITIVE_INFINITY);
		assertEquals(0., shape.getLineArc());
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
