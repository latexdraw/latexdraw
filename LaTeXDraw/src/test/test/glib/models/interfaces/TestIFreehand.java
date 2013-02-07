package test.glib.models.interfaces;

import java.awt.Color;

import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IFreehand;
import net.sf.latexdraw.glib.models.interfaces.IFreehand.FreeHandType;
import net.sf.latexdraw.glib.models.interfaces.IShape;

import org.junit.Test;

public abstract class TestIFreehand<T extends IFreehand> extends TestIModifiablePointsShape<T> {
	@Test
	public void testGetType() {
		assertEquals(FreeHandType.CURVES, shape.getType());
	}


	@Test
	public void testSetType() {
		shape.setType(FreeHandType.LINES);
		assertEquals(FreeHandType.LINES, shape.getType());
		shape.setType(FreeHandType.CURVES);
		assertEquals(FreeHandType.CURVES, shape.getType());
		shape.setType(null);
		assertEquals(FreeHandType.CURVES, shape.getType());
	}


	@Test
	public void testIsOpen() {
		assertTrue(shape.isOpen());
	}


	@Test
	public void testSetOpen() {
		shape.setOpen(false);
		assertFalse(shape.isOpen());
		shape.setOpen(true);
		assertTrue(shape.isOpen());
	}


	@Test
	public void testGetInterval() {
		shape.setInterval(22);
		assertEquals(22, shape.getInterval());
	}


	@Test
	public void testSetInterval() {
		shape.setInterval(1);
		assertEquals(1, shape.getInterval());
		shape.setInterval(0);
		assertEquals(1, shape.getInterval());
		shape.setInterval(-1);
		assertEquals(1, shape.getInterval());
		shape.setInterval(50);
		assertEquals(50, shape.getInterval());
		shape.setInterval(Integer.MAX_VALUE);
		assertEquals(Integer.MAX_VALUE, shape.getInterval());
		shape.setInterval(Integer.MIN_VALUE);
		assertEquals(Integer.MAX_VALUE, shape.getInterval());
	}


	@Override
	@Test
	public void testCopy() {
		shape2.setOpen(false);
		shape2.setInterval(10);
		shape2.setType(FreeHandType.LINES);
		shape.copy(shape2);
		assertFalse(shape.isOpen());
		assertEquals(10, shape.getInterval());
		assertEquals(FreeHandType.LINES, shape.getType());
	}

	@Test
	public void testCopyWhenNotFreeHand() {
		IShape sh = DrawingTK.getFactory().createArc(false);
		shape.copy(sh);
		assertTrue(shape.isParametersEquals(sh, false));
		assertTrue(shape.isParametersEquals(sh, true));
	}


	@Override
	@Test
	public void testIsParametersEquals() {
		assertTrue(shape.isParametersEquals(shape2, false));
		assertTrue(shape.isParametersEquals(shape2, true));
		shape.setOpen(false);
		assertFalse(shape.isParametersEquals(shape2, true));
		assertFalse(shape.isParametersEquals(shape2, false));
		shape.setOpen(true);
		shape.setInterval(shape2.getInterval()+1);
		assertFalse(shape.isParametersEquals(shape2, true));
		assertFalse(shape.isParametersEquals(shape2, false));
		shape.setInterval(shape2.getInterval());
		shape.setType(FreeHandType.LINES);
		assertFalse(shape.isParametersEquals(shape2, true));
		assertFalse(shape.isParametersEquals(shape2, false));
		shape.setInterval(shape2.getInterval()+1);
		assertFalse(shape.isParametersEquals(shape2, true));
		assertFalse(shape.isParametersEquals(shape2, false));
		shape.setOpen(false);
		assertFalse(shape.isParametersEquals(shape2, true));
		assertFalse(shape.isParametersEquals(shape2, false));
		shape.setInterval(shape2.getInterval());
		assertFalse(shape.isParametersEquals(shape2, true));
		assertFalse(shape.isParametersEquals(shape2, false));
		shape.setInterval(shape2.getInterval()+1);
		shape.setType(FreeHandType.CURVES);
		assertFalse(shape.isParametersEquals(shape2, true));
		assertFalse(shape.isParametersEquals(shape2, false));
		shape.setLineColour(Color.RED);
		assertFalse(shape.isParametersEquals(shape2, true));
		assertFalse(shape.isParametersEquals(shape2, false));
	}


	@Override
	@Test
	public void testDuplicate() {
		shape.setOpen(false);
		shape.setInterval(10);
		shape.setType(FreeHandType.LINES);
		final IFreehand dup = (IFreehand)shape.duplicate();
		assertTrue(shape.isParametersEquals(dup, false));
		assertTrue(shape.isParametersEquals(dup, true));
	}
}
