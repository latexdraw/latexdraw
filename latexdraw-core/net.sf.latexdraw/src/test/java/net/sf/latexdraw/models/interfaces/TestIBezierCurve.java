package net.sf.latexdraw.models.interfaces;

import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IBezierCurve;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.IControlPointShape;
import net.sf.latexdraw.models.interfaces.shape.IModifiablePointsShape;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class TestIBezierCurve implements HelperTest {
	IBezierCurve shape;

	@Before
	public void setUp() throws Exception {
		shape = ShapeFactory.INST.createBezierCurve();
	}

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
		assertEquals(0, shape.getPoints().size());
		assertEquals(0, shape.getFirstCtrlPts().size());
		assertEquals(0, shape.getSecondCtrlPts().size());
		assertEquals(2, shape.getNbArrows());
	}

	@Test
	public void testConstructors2() {
		final IBezierCurve curve = ShapeFactory.INST.createBezierCurve(ShapeFactory.INST.createPoint(100, 200), ShapeFactory.INST.createPoint(300, 400));

		assertEquals(2, curve.getPoints().size());
		assertEqualsDouble(100., curve.getPoints().get(0).getX());
		assertEqualsDouble(200., curve.getPoints().get(0).getY());
		assertEqualsDouble(300., curve.getPoints().get(1).getX());
		assertEqualsDouble(400., curve.getPoints().get(1).getY());
		assertEquals(2, curve.getFirstCtrlPts().size());
		assertEquals(2, curve.getSecondCtrlPts().size());
		assertEquals(2, curve.getNbArrows());
	}

	@Theory
	public void testSetIsClosed(final boolean value) {
		shape.setIsClosed(value);
		assertEquals(value, shape.isClosed());
	}

	@Test
	public void testCopy() {
		final IBezierCurve sh2 = ShapeFactory.INST.createBezierCurve();
		shape.setIsClosed(sh2.isClosed());
		sh2.setIsClosed(!sh2.isClosed());
		shape.copy(sh2);

		assertEquals(shape.isClosed(), sh2.isClosed());
	}

	@Test
	public void testDuplicate() {
		shape.setIsClosed(!shape.isClosed());
		final IBezierCurve dup = shape.duplicate();
		assertEquals(shape.isClosed(), dup.isClosed());
	}

}
