package test.glib.models;


import net.sf.latexdraw.glib.models.impl.LShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.ICircle;
import net.sf.latexdraw.glib.models.interfaces.IEllipse;
import net.sf.latexdraw.glib.models.interfaces.IPositionShape;
import net.sf.latexdraw.glib.models.interfaces.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.IRectangularShape;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.ISquare;

import org.junit.Before;
import org.junit.Test;

import test.glib.models.interfaces.TestICircle;

public class TestLCircle<T extends ICircle> extends TestICircle<T> {
	@Override
	@Before
	public void setUp() {
		DrawingTK.setFactory(new LShapeFactory());
		shape  = (T) DrawingTK.getFactory().createCircle(false);
		shape2 = (T) DrawingTK.getFactory().createCircle(false);
	}


	@Override
	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(IRectangle.class));
		assertFalse(shape.isTypeOf(ISquare.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(IPositionShape.class));
		assertTrue(shape.isTypeOf(IRectangularShape.class));
		assertTrue(shape.isTypeOf(IEllipse.class));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}


	@Test
	public void testConstructors() {
		ICircle circle = DrawingTK.getFactory().createCircle(false);

		assertEquals(4, circle.getNbPoints());
		assertEquals(circle.getRx(), circle.getRy());
		assertEquals(circle.getWidth(), circle.getHeight());
		assertTrue(circle.getHeight()>0);
	}


	@Test
	public void testConstructors2() {
		ICircle circle = DrawingTK.getFactory().createCircle(false);

		assertEquals(4, circle.getNbPoints());
		assertEquals(circle.getRx(), circle.getRy());
		assertEquals(circle.getWidth(), circle.getHeight());
		assertTrue(circle.getHeight()>0);
	}


	@Test
	public void testConstructors3() {
		ICircle circle;

		try {
			circle = DrawingTK.getFactory().createCircle(null, 10., true);
			fail();
		}catch(NullPointerException e) {/* */}

		try {
			circle = DrawingTK.getFactory().createCircle(DrawingTK.getFactory().createPoint(Double.NaN, 1), 10., true);
			fail();
		}catch(IllegalArgumentException e) {/* */}

		try {
			circle = DrawingTK.getFactory().createCircle(DrawingTK.getFactory().createPoint(1, Double.NEGATIVE_INFINITY), 10., true);
			fail();
		}catch(IllegalArgumentException e) {/* */}

		try {
			circle = DrawingTK.getFactory().createCircle(DrawingTK.getFactory().createPoint(1, 1), -10., true);
			fail();
		}catch(IllegalArgumentException e) {/* */}

		try {
			circle = DrawingTK.getFactory().createCircle(DrawingTK.getFactory().createPoint(1, 1), 0., true);
			fail();
		}catch(IllegalArgumentException e) {/* */}

		circle = DrawingTK.getFactory().createCircle(DrawingTK.getFactory().createPoint(1, 2), 10., true);

		assertEquals(1., circle.getGravityCentre().getX());
		assertEquals(2., circle.getGravityCentre().getY());
		assertEquals(20., circle.getWidth());
		assertEquals(4, circle.getNbPoints());
		assertEquals(circle.getRx(), circle.getRy());
		assertEquals(circle.getWidth(), circle.getHeight());
	}
}
