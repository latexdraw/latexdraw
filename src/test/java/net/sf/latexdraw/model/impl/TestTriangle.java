package net.sf.latexdraw.model.impl;

import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Circle;
import net.sf.latexdraw.model.api.shape.PositionShape;
import net.sf.latexdraw.model.api.shape.Rectangle;
import net.sf.latexdraw.model.api.shape.RectangularShape;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.model.api.shape.Triangle;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestTriangle implements HelperTest {
	@Test
	public void testConstructor1() {
		final Triangle shape = ShapeFactory.INST.createTriangle();
		assertEqualsDouble(1d, shape.getWidth());
		assertEqualsDouble(1d, shape.getHeight());
		assertEqualsDouble(0d, shape.getPosition().getX());
		assertEqualsDouble(1d, shape.getPosition().getY());
	}

	@Test
	public void testConstructor2() {
		final Triangle rho = ShapeFactory.INST.createTriangle(ShapeFactory.INST.createPoint(5, 15), 20, 40);
		assertEqualsDouble(4d, rho.getNbPoints());
		assertEqualsDouble(20d, rho.getWidth());
		assertEqualsDouble(40d, rho.getHeight());
		assertEqualsDouble(5d, rho.getPosition().getX());
		assertEqualsDouble(55d, rho.getPosition().getY());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInvalidPos() {
		ShapeFactory.INST.createTriangle(ShapeFactory.INST.createPoint(Double.NaN, 0), 10, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInvalidWidth() {
		ShapeFactory.INST.createTriangle(ShapeFactory.INST.createPoint(1, 1), -10, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInvalidHeight() {
		ShapeFactory.INST.createTriangle(ShapeFactory.INST.createPoint(1, 1), 10, -10);
	}

	@Test
	public void testIsTypeOf() {
		final Triangle shape = ShapeFactory.INST.createTriangle();
		assertFalse(shape.isTypeOf(Rectangle.class));
		assertFalse(shape.isTypeOf(Circle.class));
		assertTrue(shape.isTypeOf(Shape.class));
		assertTrue(shape.isTypeOf(PositionShape.class));
		assertTrue(shape.isTypeOf(RectangularShape.class));
		assertTrue(shape.isTypeOf(Triangle.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}
}
