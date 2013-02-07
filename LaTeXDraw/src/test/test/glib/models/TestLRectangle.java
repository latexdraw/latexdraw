package test.glib.models;


import net.sf.latexdraw.glib.models.impl.LShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.ICircle;
import net.sf.latexdraw.glib.models.interfaces.IEllipse;
import net.sf.latexdraw.glib.models.interfaces.ILineArcShape;
import net.sf.latexdraw.glib.models.interfaces.IPositionShape;
import net.sf.latexdraw.glib.models.interfaces.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.IRectangularShape;
import net.sf.latexdraw.glib.models.interfaces.IShape;

import org.junit.Before;
import org.junit.Test;

import test.glib.models.interfaces.TestIRectangle;

public class TestLRectangle<T extends IRectangle> extends TestIRectangle<T> {
	@Override
	@Before
	public void setUp() {
		DrawingTK.setFactory(new LShapeFactory());
		shape = (T) DrawingTK.getFactory().createRectangle(false);
		shape2 = (T) DrawingTK.getFactory().createRectangle(false);
	}


	@Override
	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(IEllipse.class));
		assertFalse(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(ILineArcShape.class));
		assertTrue(shape.isTypeOf(IPositionShape.class));
		assertTrue(shape.isTypeOf(IRectangularShape.class));
		assertTrue(shape.isTypeOf(IRectangle.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}



	@Test
	public void testConstructors() {
		IRectangle rec = DrawingTK.getFactory().createRectangle(false);
		assertEquals(4, rec.getNbPoints());

		try {
			rec = DrawingTK.getFactory().createRectangle(null, DrawingTK.getFactory().createPoint(), true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
		try {
			rec = DrawingTK.getFactory().createRectangle(DrawingTK.getFactory().createPoint(), null, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
		rec = DrawingTK.getFactory().createRectangle(DrawingTK.getFactory().createPoint(20, 26), DrawingTK.getFactory().createPoint(30, 35), true);
		assertEquals(20., rec.getPosition().getX());
		assertEquals(35., rec.getPosition().getY());
		assertEquals(10., rec.getWidth());
		assertEquals(9., rec.getHeight());

		rec = DrawingTK.getFactory().createRectangle(DrawingTK.getFactory().createPoint(5, 6), 11, 12, true);
		assertEquals(5., rec.getPosition().getX());
		assertEquals(18., rec.getPosition().getY());
		assertEquals(11., rec.getWidth());
		assertEquals(12., rec.getHeight());

		try {
			rec = DrawingTK.getFactory().createRectangle(null, 10, 10, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
		try {
			rec = DrawingTK.getFactory().createRectangle(DrawingTK.getFactory().createPoint(Double.NaN, 0), 10, 10, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
		try {
			rec = DrawingTK.getFactory().createRectangle(DrawingTK.getFactory().createPoint(0, 0), -10, 10, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
		try {
			rec = DrawingTK.getFactory().createRectangle(DrawingTK.getFactory().createPoint(0, 0), Double.NaN, 10, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
		try {
			rec = DrawingTK.getFactory().createRectangle(DrawingTK.getFactory().createPoint(0, 0), Double.POSITIVE_INFINITY, 10, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
		try {
			rec = DrawingTK.getFactory().createRectangle(DrawingTK.getFactory().createPoint(0, 0), Double.NEGATIVE_INFINITY, 10, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
		try {
			rec = DrawingTK.getFactory().createRectangle(DrawingTK.getFactory().createPoint(0, 0), 0, 10, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
		try {
			rec = DrawingTK.getFactory().createRectangle(DrawingTK.getFactory().createPoint(0, 0), 10, 0, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
		try {
			rec = DrawingTK.getFactory().createRectangle(DrawingTK.getFactory().createPoint(0, 0), 10, -10, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
		try {
			rec = DrawingTK.getFactory().createRectangle(DrawingTK.getFactory().createPoint(0, 0), 10, Double.NaN, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
		try {
			rec = DrawingTK.getFactory().createRectangle(DrawingTK.getFactory().createPoint(0, 0), 10, Double.NEGATIVE_INFINITY, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
		try {
			rec = DrawingTK.getFactory().createRectangle(DrawingTK.getFactory().createPoint(0, 0), 10, Double.POSITIVE_INFINITY, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
	}
}
