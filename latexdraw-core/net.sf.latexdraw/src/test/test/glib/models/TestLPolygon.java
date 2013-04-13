package test.glib.models;


import static org.junit.Assert.*;
import net.sf.latexdraw.glib.models.impl.LShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.ICircle;
import net.sf.latexdraw.glib.models.interfaces.IModifiablePointsShape;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IPolygon;
import net.sf.latexdraw.glib.models.interfaces.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.IShape;

import org.junit.Before;
import org.junit.Test;

import test.glib.models.interfaces.TestIPolygon;

public class TestLPolygon<T extends IPolygon> extends TestIPolygon<T> {
	@Before
	public void setUp() {
		DrawingTK.setFactory(new LShapeFactory());
		shape  = (T) DrawingTK.getFactory().createPolygon(false);
		shape2 = (T) DrawingTK.getFactory().createPolygon(false);
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
		assertTrue(shape.isTypeOf(shape.getClass()));
	}


//	@Test
//	public void testGetSetLineArc() {
//		shape.setLineArc(0);
//		assertEquals(0., shape.getLineArc());
//		shape.setLineArc(1);
//		assertEquals(1., shape.getLineArc());
//		shape.setLineArc(Double.NaN);
//		assertEquals(1., shape.getLineArc());
//		shape.setLineArc(1.001);
//		assertEquals(1., shape.getLineArc());
//		shape.setLineArc(Double.NEGATIVE_INFINITY);
//		assertEquals(1., shape.getLineArc());
//		shape.setLineArc(Double.POSITIVE_INFINITY);
//		assertEquals(1., shape.getLineArc());
//		shape.setLineArc(-0.0001);
//		assertEquals(1., shape.getLineArc());
//	}
//
//
//	@Test
//	public void testIsSetCornerRound() {
////		shape.setRoundCorner(true);
//		assertEquals(true, shape.isRoundCorner());
////		shape.setRoundCorner(false);
//		assertEquals(false, shape.isRoundCorner());
////		shape.setRoundCorner(true);
//		assertEquals(true, shape.isRoundCorner());
//	}


	@Test
	public void testConstructor() {
		IPoint pt1   = DrawingTK.getFactory().createPoint(1, 1);
		IPoint pt2   = DrawingTK.getFactory().createPoint(2, 2);
		IPolygon pol = DrawingTK.getFactory().createPolygon(pt1, pt2, true);

		pol = DrawingTK.getFactory().createPolygon(pt1, pt2, false);
		assertEquals(pt1, pol.getPtAt(0));
		assertEquals(pt2, pol.getPtAt(-1));

		pol = DrawingTK.getFactory().createPolygon(false);
		pol = DrawingTK.getFactory().createPolygon(true);
		pol = DrawingTK.getFactory().createPolygon(false);

		try {
			pol = DrawingTK.getFactory().createPolygon(null, pt2, true);
			fail();
			pol = DrawingTK.getFactory().createPolygon(pt1, null, true);
			fail();
			pol = DrawingTK.getFactory().createPolygon(null, null, true);
			fail();
		}catch(IllegalArgumentException e){ /* */ }
	}
}
