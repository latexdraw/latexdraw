package test.glib.models;


import net.sf.latexdraw.glib.models.impl.LShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.ICircle;
import net.sf.latexdraw.glib.models.interfaces.IEllipse;
import net.sf.latexdraw.glib.models.interfaces.IPositionShape;
import net.sf.latexdraw.glib.models.interfaces.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.IRectangularShape;
import net.sf.latexdraw.glib.models.interfaces.IShape;

import org.junit.Before;
import org.junit.Test;

import test.glib.models.interfaces.TestIEllipse;


public class TestLEllipse<T extends IEllipse> extends TestIEllipse<T> {
	@Override
	@Before
	public void setUp() {
		DrawingTK.setFactory(new LShapeFactory());
		shape  = (T) DrawingTK.getFactory().createEllipse(false);
		shape2 = (T) DrawingTK.getFactory().createEllipse(false);
	}


	@Override
	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(IRectangle.class));
		assertFalse(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(IPositionShape.class));
		assertTrue(shape.isTypeOf(IRectangularShape.class));
		assertTrue(shape.isTypeOf(IEllipse.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}


	@Test
	public void testConstructors2() {
		IEllipse ell = DrawingTK.getFactory().createEllipse(false);

		assertEquals(4, ell.getNbPoints());
	}

	@Test
	public void testConstructors3() {
		IEllipse ell;

		try {
			ell = DrawingTK.getFactory().createEllipse(null, DrawingTK.getFactory().createPoint(), true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			ell = DrawingTK.getFactory().createEllipse(DrawingTK.getFactory().createPoint(), null, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			ell = DrawingTK.getFactory().createEllipse(DrawingTK.getFactory().createPoint(), DrawingTK.getFactory().createPoint(), true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			ell = DrawingTK.getFactory().createEllipse(DrawingTK.getFactory().createPoint(1,0), DrawingTK.getFactory().createPoint(2,0), true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			ell = DrawingTK.getFactory().createEllipse(DrawingTK.getFactory().createPoint(1,Double.NaN), DrawingTK.getFactory().createPoint(2,0), true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			ell = DrawingTK.getFactory().createEllipse(DrawingTK.getFactory().createPoint(1,2), DrawingTK.getFactory().createPoint(2,Double.NaN), true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		ell = DrawingTK.getFactory().createEllipse(DrawingTK.getFactory().createPoint(20, 26), DrawingTK.getFactory().createPoint(30, 35), true);
		assertEquals(20., ell.getPosition().getX());
		assertEquals(35., ell.getPosition().getY());
		assertEquals(10., ell.getWidth());
		assertEquals(9., ell.getHeight());
	}
}
