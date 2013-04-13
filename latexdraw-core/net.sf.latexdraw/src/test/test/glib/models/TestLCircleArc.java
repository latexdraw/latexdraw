package test.glib.models;


import static org.junit.Assert.*;
import net.sf.latexdraw.glib.models.impl.LShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IArc;
import net.sf.latexdraw.glib.models.interfaces.ICircle;
import net.sf.latexdraw.glib.models.interfaces.ICircleArc;
import net.sf.latexdraw.glib.models.interfaces.IEllipse;
import net.sf.latexdraw.glib.models.interfaces.IPositionShape;
import net.sf.latexdraw.glib.models.interfaces.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.IRectangularShape;
import net.sf.latexdraw.glib.models.interfaces.IShape;

import org.junit.Before;
import org.junit.Test;

import test.glib.models.interfaces.TestICircleArc;

public class TestLCircleArc<T extends ICircleArc> extends TestICircleArc<T> {
	@Before
	public void setUp() {
		DrawingTK.setFactory(new LShapeFactory());
		shape  = (T) DrawingTK.getFactory().createCircleArc(false);
		shape2 = (T) DrawingTK.getFactory().createCircleArc(false);
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
		assertTrue(shape.isTypeOf(IArc.class));
		assertTrue(shape.isTypeOf(ICircleArc.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}


	@Test
	public void testConstructors() {
		//TODO
	}
}
