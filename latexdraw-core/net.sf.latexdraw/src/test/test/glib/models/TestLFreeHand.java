package test.glib.models;


import static org.junit.Assert.*;
import net.sf.latexdraw.glib.models.impl.LShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.ICircle;
import net.sf.latexdraw.glib.models.interfaces.IFreehand;
import net.sf.latexdraw.glib.models.interfaces.IModifiablePointsShape;
import net.sf.latexdraw.glib.models.interfaces.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.IShape;

import org.junit.Before;
import org.junit.Test;

import test.glib.models.interfaces.TestIFreehand;

public class TestLFreeHand<T extends IFreehand> extends TestIFreehand<T> {
	@Before
	public void setUp() {
		DrawingTK.setFactory(new LShapeFactory());
		shape  = (T) DrawingTK.getFactory().createFreeHand(DrawingTK.getFactory().createPoint(), false);
		shape2 = (T) DrawingTK.getFactory().createFreeHand(DrawingTK.getFactory().createPoint(), false);
	}


	@Override
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


	@Test
	public void testConstructorsWithValidPoint() {
		final IFreehand fh = DrawingTK.getFactory().createFreeHand(DrawingTK.getFactory().createPoint(), false);
		assertNotNull(fh);
	}

	@Test
	public void testConstructorsWithNotValidPoint() {
		try {
			DrawingTK.getFactory().createFreeHand(null, false);
			fail();
		}catch(IllegalArgumentException e) { /* ok */ }
	}
}
