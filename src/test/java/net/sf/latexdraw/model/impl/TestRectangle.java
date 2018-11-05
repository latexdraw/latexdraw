package net.sf.latexdraw.model.impl;

import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.property.LineArcProp;
import net.sf.latexdraw.model.api.shape.Circle;
import net.sf.latexdraw.model.api.shape.Ellipse;
import net.sf.latexdraw.model.api.shape.PositionShape;
import net.sf.latexdraw.model.api.shape.Rectangle;
import net.sf.latexdraw.model.api.shape.RectangularShape;
import net.sf.latexdraw.model.api.shape.Shape;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestRectangle implements HelperTest {
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNotValid1() {
		ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(Double.NaN, 0), 10, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNotValid2() {
		ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(0, 0), -10, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNotValid3() {
		ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(0, 0), Double.NaN, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNotValid4() {
		ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(0, 0), Double.POSITIVE_INFINITY, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNotValid5() {
		ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(0, 0), Double.NEGATIVE_INFINITY, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNotValid6() {
		ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(0, 0), 0, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNotValid7() {
		ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(0, 0), 10, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNotValid8() {
		ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(0, 0), 10, -10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNotValid9() {
		ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(0, 0), 10, Double.NaN);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNotValid10() {
		ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(0, 0), 10, Double.NEGATIVE_INFINITY);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNotValid11() {
		ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(0, 0), 10, Double.POSITIVE_INFINITY);
	}

	@Test
	public void testConstructorsOKNbPoints() {
		final Rectangle rec = ShapeFactory.INST.createRectangle();
		assertEquals(4, rec.getNbPoints());
	}

	@Test
	public void testConstructorsOK1() {
		final Rectangle rec = ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(20, 26), ShapeFactory.INST.createPoint(30, 35));
		assertEqualsDouble(20., rec.getPosition().getX());
		assertEqualsDouble(35., rec.getPosition().getY());
		assertEqualsDouble(10., rec.getWidth());
		assertEqualsDouble(9., rec.getHeight());
	}

	@Test
	public void testConstructorsOK2() {
		final Rectangle rec = ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(5, 6), 11, 12);
		assertEqualsDouble(5., rec.getPosition().getX());
		assertEqualsDouble(18., rec.getPosition().getY());
		assertEqualsDouble(11., rec.getWidth());
		assertEqualsDouble(12., rec.getHeight());
	}

	@Test
	public void testIsTypeOf() {
		final Rectangle shape = ShapeFactory.INST.createRectangle();
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(Ellipse.class));
		assertFalse(shape.isTypeOf(Circle.class));
		assertTrue(shape.isTypeOf(Shape.class));
		assertTrue(shape.isTypeOf(LineArcProp.class));
		assertTrue(shape.isTypeOf(PositionShape.class));
		assertTrue(shape.isTypeOf(RectangularShape.class));
		assertTrue(shape.isTypeOf(Rectangle.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}
}
