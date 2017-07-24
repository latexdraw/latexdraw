package test.models.interfaces;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.FreeHandStyle;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.IFreehand;
import net.sf.latexdraw.models.interfaces.shape.IModifiablePointsShape;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;
import test.HelperTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class TestIFreehand implements HelperTest {
	IFreehand shape;
	IFreehand shape2;

	@Before
	public void setUp() {
		shape = ShapeFactory.INST.createFreeHand();
		shape2 = ShapeFactory.INST.createFreeHand();
	}

	@Theory
	public void testSetGetType(final FreeHandStyle style) {
		shape.setType(style);
		assertEquals(style, shape.getType());
	}

	@Test
	public void testSetGetTypeKO() {
		shape.setType(FreeHandStyle.CURVES);
		shape.setType(null);
		assertEquals(FreeHandStyle.CURVES, shape.getType());
	}

	@Theory
	public void testSetIsOpen(final boolean open) {
		shape.setOpen(open);
		assertEquals(open, shape.isOpen());
	}

	@Theory
	public void testGetSetInterval(@TestedOn(ints = {1, 10}) final int value) {
		shape.setInterval(value);
		assertEquals(value, shape.getInterval());
	}

	@Test
	public void testGetSetIntervalKO() {
		shape.setInterval(10);
		shape.setInterval(-1);
		assertEquals(10, shape.getInterval());
	}

	@Test
	public void testCopy() {
		shape2.setOpen(!shape2.isOpen());
		shape.setInterval(shape.getInterval() * 2);
		shape2.setType(FreeHandStyle.LINES);

		shape.copy(shape2);

		assertEquals(shape2.isOpen(), shape.isOpen());
		assertEquals(shape2.getInterval(), shape.getInterval());
		assertEquals(FreeHandStyle.LINES, shape.getType());
	}

	@Test
	public void testDuplicate() {
		shape.setOpen(!shape.isOpen());
		shape.setInterval(shape.getInterval() * 2);
		shape.setType(FreeHandStyle.LINES);

		final IFreehand dup = shape.duplicate();

		assertEquals(shape.isOpen(), dup.isOpen());
		assertEquals(shape.getInterval(), dup.getInterval());
		assertEquals(FreeHandStyle.LINES, dup.getType());
	}

	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(IRectangle.class));
		assertFalse(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(IModifiablePointsShape.class));
		assertTrue(shape.isTypeOf(IFreehand.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}
}
