package net.sf.latexdraw.model.impl;

import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.property.LineArcProp;
import net.sf.latexdraw.model.api.shape.Circle;
import net.sf.latexdraw.model.api.shape.Grid;
import net.sf.latexdraw.model.api.shape.PositionShape;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.model.api.shape.Square;
import net.sf.latexdraw.model.api.shape.SquaredShape;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestSquare implements HelperTest {
	@Test
	public void testConstructorsOKPoints() {
		final Square sq = ShapeFactory.INST.createSquare();
		assertEquals(4, sq.getNbPoints());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorsNotNaN() {
		ShapeFactory.INST.createSquare(ShapeFactory.INST.createPoint(Double.NaN, 0), 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorsNotOK0() {
		ShapeFactory.INST.createSquare(ShapeFactory.INST.createPoint(), 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorsNotOKNeg() {
		ShapeFactory.INST.createSquare(ShapeFactory.INST.createPoint(), -10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorsNotOKNaNSize() {
		ShapeFactory.INST.createSquare(ShapeFactory.INST.createPoint(), Double.NaN);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorsNotOKInfSize() {
		ShapeFactory.INST.createSquare(ShapeFactory.INST.createPoint(), Double.POSITIVE_INFINITY);
	}

	@Test
	public void testConstructorsOK() {
		final Square sq = ShapeFactory.INST.createSquare(ShapeFactory.INST.createPoint(20, 26), 11);
		assertEqualsDouble(20., sq.getPosition().getX());
		assertEqualsDouble(26., sq.getPosition().getY());
		assertEqualsDouble(11., sq.getWidth());
		assertEqualsDouble(11., sq.getHeight());
	}

	@Test
	public void testIsTypeOf() {
		final Square shape = ShapeFactory.INST.createSquare();
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(Grid.class));
		assertFalse(shape.isTypeOf(Circle.class));
		assertTrue(shape.isTypeOf(Shape.class));
		assertTrue(shape.isTypeOf(PositionShape.class));
		assertTrue(shape.isTypeOf(SquaredShape.class));
		assertTrue(shape.isTypeOf(LineArcProp.class));
		assertTrue(shape.isTypeOf(Square.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}
}
