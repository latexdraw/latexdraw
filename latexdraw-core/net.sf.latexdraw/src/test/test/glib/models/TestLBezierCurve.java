package test.glib.models;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.glib.models.interfaces.shape.ICircle;
import net.sf.latexdraw.glib.models.interfaces.shape.IControlPointShape;
import net.sf.latexdraw.glib.models.interfaces.shape.IModifiablePointsShape;
import net.sf.latexdraw.glib.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;

import org.junit.Before;
import org.junit.Test;

import test.HelperTest;
import test.glib.models.interfaces.TestIBezierCurve;

public class TestLBezierCurve<T extends IBezierCurve> extends TestIBezierCurve<T> {
	@Before
	public void setUp() {
		shape  = (T) ShapeFactory.createBezierCurve(false);
		shape2 = (T) ShapeFactory.createBezierCurve(false);
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
		IBezierCurve curve = ShapeFactory.createBezierCurve(false);

		assertNotNull(curve);
		assertEquals(0, curve.getPoints().size());
		assertEquals(0, curve.getFirstCtrlPts().size());
		assertEquals(0, curve.getSecondCtrlPts().size());
		assertEquals(2, curve.getArrows().size());
	}

	@Test
	public void testConstructors2() {
		IBezierCurve curve = ShapeFactory.createBezierCurve(
				ShapeFactory.createPoint(100, 200),
				ShapeFactory.createPoint(300, 400), false);

		assertNotNull(curve);
		assertEquals(2, curve.getPoints().size());
		HelperTest.assertEqualsDouble(100., curve.getPoints().get(0).getX());
		HelperTest.assertEqualsDouble(200., curve.getPoints().get(0).getY());
		HelperTest.assertEqualsDouble(300., curve.getPoints().get(1).getX());
		HelperTest.assertEqualsDouble(400., curve.getPoints().get(1).getY());
		assertEquals(2, curve.getFirstCtrlPts().size());
		assertEquals(2, curve.getSecondCtrlPts().size());
		assertEquals(2, curve.getArrows().size());
	}
}
