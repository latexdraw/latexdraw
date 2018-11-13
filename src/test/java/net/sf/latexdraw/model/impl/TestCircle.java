package net.sf.latexdraw.model.impl;

import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.data.CircleData;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Circle;
import net.sf.latexdraw.model.api.shape.PositionShape;
import net.sf.latexdraw.model.api.shape.Rectangle;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.model.api.shape.Square;
import net.sf.latexdraw.model.api.shape.SquaredShape;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class TestCircle implements HelperTest {
	@Theory
	public void testIsTypeOf(@CircleData final Circle shape) {
		assertFalse(shape.isTypeOf(Rectangle.class));
		assertFalse(shape.isTypeOf(Square.class));
		assertTrue(shape.isTypeOf(Shape.class));
		assertTrue(shape.isTypeOf(PositionShape.class));
		assertTrue(shape.isTypeOf(SquaredShape.class));
		assertTrue(shape.isTypeOf(Circle.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}

	@Test
	public void testConstructors() {
		final Circle circle = ShapeFactory.INST.createCircle();
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
		final Circle circle = ShapeFactory.INST.createCircle(ShapeFactory.INST.createPoint(1, 2), 10.);
		assertEqualsDouble(6., circle.getGravityCentre().getX());
		assertEqualsDouble(-3., circle.getGravityCentre().getY());
		assertEqualsDouble(10., circle.getWidth());
		assertEquals(4, circle.getNbPoints());
		assertEqualsDouble(circle.getWidth(), circle.getHeight());
	}
}
