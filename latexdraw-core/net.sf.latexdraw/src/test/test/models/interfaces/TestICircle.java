package test.models.interfaces;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.IPositionShape;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.ISquare;
import net.sf.latexdraw.models.interfaces.shape.ISquaredShape;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import test.HelperTest;
import test.data.CircleData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class TestICircle implements HelperTest {
	@Theory
	public void testIsTypeOf(@CircleData final ICircle shape) {
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
		final ICircle circle = ShapeFactory.INST.createCircle();
		assertEquals(4, circle.getNbPoints());
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
		final ICircle circle = ShapeFactory.INST.createCircle(ShapeFactory.INST.createPoint(1, 2), 10.);
		assertEqualsDouble(6., circle.getGravityCentre().getX());
		assertEqualsDouble(-3., circle.getGravityCentre().getY());
		assertEqualsDouble(10., circle.getWidth());
		assertEquals(4, circle.getNbPoints());
		assertEqualsDouble(circle.getWidth(), circle.getHeight());
	}
}
