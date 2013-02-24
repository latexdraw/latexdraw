package test.glib.models;


import net.sf.latexdraw.glib.models.impl.LShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.ICircle;
import net.sf.latexdraw.glib.models.interfaces.IModifiablePointsShape;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IPolygon;
import net.sf.latexdraw.glib.models.interfaces.IPolyline;
import net.sf.latexdraw.glib.models.interfaces.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.IShape;

import org.junit.Before;
import org.junit.Test;

import test.glib.models.interfaces.TestIPolyline;

public class TestLPolyline<T extends IPolyline> extends TestIPolyline<T> {
	@Override
	@Before
	public void setUp() {
		DrawingTK.setFactory(new LShapeFactory());
		shape  = (T) DrawingTK.getFactory().createPolyline(false);
		shape2 = (T) DrawingTK.getFactory().createPolyline(false);
		shape.addPoint(DrawingTK.getFactory().createPoint(1, 1));
		shape.addPoint(DrawingTK.getFactory().createPoint(2, 2));
		shape.addPoint(DrawingTK.getFactory().createPoint(3, 3));
		shape2.addPoint(DrawingTK.getFactory().createPoint(1, 1));
		shape2.addPoint(DrawingTK.getFactory().createPoint(2, 2));
		shape2.addPoint(DrawingTK.getFactory().createPoint(3, 3));
	}


	@Override
	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(IRectangle.class));
		assertFalse(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(IModifiablePointsShape.class));
		assertTrue(shape.isTypeOf(IPolygon.class));
		assertTrue(shape.isTypeOf(IPolyline.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}


	@Test
	public void testConstructor() {
		IPoint pt1   = DrawingTK.getFactory().createPoint(1, 1);
		IPoint pt2   = DrawingTK.getFactory().createPoint(2, 2);
		IPolyline pol = DrawingTK.getFactory().createPolyline(pt1, pt2, true);

		pol = DrawingTK.getFactory().createPolyline(pt1, pt2, false);
		assertEquals(pt1, pol.getPtAt(0));
		assertEquals(pt2, pol.getPtAt(-1));

		pol = DrawingTK.getFactory().createPolyline(true);
		pol = DrawingTK.getFactory().createPolyline(false);

		try {
			pol = DrawingTK.getFactory().createPolyline(null, pt2, true);
			fail();
			pol = DrawingTK.getFactory().createPolyline(pt1, null, true);
			fail();
			pol = DrawingTK.getFactory().createPolyline(null, null, true);
			fail();
		}catch(IllegalArgumentException e){ /* */ }
	}
}
