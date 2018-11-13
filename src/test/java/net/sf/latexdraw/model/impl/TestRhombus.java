package net.sf.latexdraw.model.impl;

import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Circle;
import net.sf.latexdraw.model.api.shape.PositionShape;
import net.sf.latexdraw.model.api.shape.Rectangle;
import net.sf.latexdraw.model.api.shape.RectangularShape;
import net.sf.latexdraw.model.api.shape.Rhombus;
import net.sf.latexdraw.model.api.shape.Shape;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestRhombus implements HelperTest {
	@Test
	public void testConstructor1() {
		final Rhombus shape = ShapeFactory.INST.createRhombus();
		assertEqualsDouble(1d, shape.getWidth());
		assertEqualsDouble(1d, shape.getHeight());
		assertEqualsDouble(0d, shape.getPosition().getX());
		assertEqualsDouble(0d, shape.getPosition().getY());
	}

	@Test
	public void testConstructor2() {
		final Rhombus rho = ShapeFactory.INST.createRhombus(ShapeFactory.INST.createPoint(5, 15), 20, 40);
		assertEqualsDouble(4d, rho.getNbPoints());
		assertEqualsDouble(20d, rho.getWidth());
		assertEqualsDouble(40d, rho.getHeight());
		assertEqualsDouble(-5d, rho.getPosition().getX());
		assertEqualsDouble(35d, rho.getPosition().getY());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInvalidPos() {
		ShapeFactory.INST.createRhombus(ShapeFactory.INST.createPoint(Double.NaN, 0), 10, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInvalidWidth() {
		ShapeFactory.INST.createRhombus(ShapeFactory.INST.createPoint(1, 1), -10, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInvalidHeight() {
		ShapeFactory.INST.createRhombus(ShapeFactory.INST.createPoint(1, 1), 10, -10);
	}

	@Test
	public void testIsTypeOf() {
		final Rhombus shape = ShapeFactory.INST.createRhombus();
		assertFalse(shape.isTypeOf(Rectangle.class));
		assertFalse(shape.isTypeOf(Circle.class));
		assertTrue(shape.isTypeOf(Shape.class));
		assertTrue(shape.isTypeOf(PositionShape.class));
		assertTrue(shape.isTypeOf(RectangularShape.class));
		assertTrue(shape.isTypeOf(Rhombus.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}
}
