package test.models;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.IPositionShape;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.ISquare;
import net.sf.latexdraw.models.interfaces.shape.ISquaredShape;
import org.junit.Before;
import org.junit.Test;
import test.HelperTest;
import test.models.interfaces.TestICircle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestLCircle extends TestICircle<ICircle> implements HelperTest {
	@Before
	public void setUp() {
		shape = ShapeFactory.INST.createCircle();
		shape2 = ShapeFactory.INST.createCircle();
	}

	@Override
	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(IRectangle.class));
		assertFalse(shape.isTypeOf(ISquare.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(IPositionShape.class));
		assertTrue(shape.isTypeOf(ISquaredShape.class));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}

	@Test
	public void testConstructors() {
		ICircle circle = ShapeFactory.INST.createCircle();

		assertEquals(4, circle.getNbPoints());
		assertEqualsDouble(circle.getWidth(), circle.getHeight());
		assertTrue(circle.getHeight() > 0);
	}

	@Test
	public void testConstructors2() {
		ICircle circle = ShapeFactory.INST.createCircle();

		assertEqualsDouble(4, circle.getNbPoints());
		assertEqualsDouble(circle.getWidth(), circle.getHeight());
		assertTrue(circle.getHeight() > 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructors3NotOKNAN() {
		ShapeFactory.INST.createCircle(ShapeFactory.INST.createPoint(Double.NaN, 1), 10.);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructors3NotOKINF() {
		ShapeFactory.INST.createCircle(ShapeFactory.INST.createPoint(1, Double.NEGATIVE_INFINITY), 10.);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructors3NotOKNeg() {
		ShapeFactory.INST.createCircle(ShapeFactory.INST.createPoint(1, 1), -10.);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructors3NotOK0() {
		ShapeFactory.INST.createCircle(ShapeFactory.INST.createPoint(1, 1), 0.);
	}

	@Test
	public void testConstructors3OK() {
		ICircle circle = ShapeFactory.INST.createCircle(ShapeFactory.INST.createPoint(1, 2), 10.);

		assertEqualsDouble(6., circle.getGravityCentre().getX());
		assertEqualsDouble(-3., circle.getGravityCentre().getY());
		assertEqualsDouble(10., circle.getWidth());
		assertEquals(4, circle.getNbPoints());
		assertEqualsDouble(circle.getWidth(), circle.getHeight());
	}
}
