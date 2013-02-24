package test.glib.models;


import net.sf.latexdraw.glib.models.impl.LShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IBezierCurve;
import net.sf.latexdraw.glib.models.interfaces.ICircle;
import net.sf.latexdraw.glib.models.interfaces.IControlPointShape;
import net.sf.latexdraw.glib.models.interfaces.IModifiablePointsShape;
import net.sf.latexdraw.glib.models.interfaces.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.IShape;

import org.junit.Before;
import org.junit.Test;

import test.glib.models.interfaces.TestIBezierCurve;

public class TestLBezierCurve<T extends IBezierCurve> extends TestIBezierCurve<T> {
	@Override
	@Before
	public void setUp() {
		DrawingTK.setFactory(new LShapeFactory());
		shape  = (T) DrawingTK.getFactory().createBezierCurve(false);
		shape2 = (T) DrawingTK.getFactory().createBezierCurve(false);
	}



	@Override
	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(IRectangle.class));
		assertFalse(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(IModifiablePointsShape.class));
		assertTrue(shape.isTypeOf(IControlPointShape.class));
		assertTrue(shape.isTypeOf(IBezierCurve.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}


	@Test
	public void testConstructors() {
		IBezierCurve curve = DrawingTK.getFactory().createBezierCurve(false);

		assertNotNull(curve);
		assertEquals(0, curve.getPoints().size());
		assertEquals(0, curve.getFirstCtrlPts().size());
		assertEquals(0, curve.getSecondCtrlPts().size());
		assertEquals(2, curve.getArrows().size());
	}

	@Test
	public void testConstructors2() {
		IBezierCurve curve = DrawingTK.getFactory().createBezierCurve(
				DrawingTK.getFactory().createPoint(100, 200),
				DrawingTK.getFactory().createPoint(300, 400), false);

		assertNotNull(curve);
		assertEquals(2, curve.getPoints().size());
		assertEquals(100., curve.getPoints().get(0).getX());
		assertEquals(200., curve.getPoints().get(0).getY());
		assertEquals(300., curve.getPoints().get(1).getX());
		assertEquals(400., curve.getPoints().get(1).getY());
		assertEquals(2, curve.getFirstCtrlPts().size());
		assertEquals(2, curve.getSecondCtrlPts().size());
		assertEquals(2, curve.getArrows().size());
	}
}
