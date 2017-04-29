package test.models.interfaces;

import net.sf.latexdraw.models.interfaces.shape.ISquare;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class TestISquare<T extends ISquare> extends TestISquaredShape<T> {
	@Override
	@Test
	public void testHas4Points() {
		assertEquals(shape.getNbPoints(), 4);
	}

	@Override
	@Test
	public void testCopy() {
		super.testCopy();

		shape2.setLineArc(0.55);
		shape.copy(shape2);
		assertEqualsDouble(0.55, shape.getLineArc());
		assertTrue(shape.isRoundCorner());
	}
}
