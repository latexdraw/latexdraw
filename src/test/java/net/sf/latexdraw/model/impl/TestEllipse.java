package net.sf.latexdraw.model.impl;

import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.data.DoubleData;
import net.sf.latexdraw.data.EllData;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Circle;
import net.sf.latexdraw.model.api.shape.Ellipse;
import net.sf.latexdraw.model.api.shape.PositionShape;
import net.sf.latexdraw.model.api.shape.Rectangle;
import net.sf.latexdraw.model.api.shape.RectangularShape;
import net.sf.latexdraw.model.api.shape.Shape;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

@RunWith(Theories.class)
public class TestEllipse implements HelperTest {
	@Theory
	public void testGetAB(@EllData final Ellipse shape, @DoubleData final double w, @DoubleData final double h,
						@DoubleData final double x, @DoubleData final double y) {
		assumeTrue(w > 0d);
		assumeTrue(h > 0d);

		shape.setPosition(x, y);
		shape.setWidth(w);
		shape.setHeight(h);

		assertEqualsDouble(Math.max(w, h) / 2d, shape.getA());
		assertEqualsDouble(Math.min(w, h) / 2d, shape.getB());
	}

	@Test
	public void testConstructors2() {
		final Ellipse ell = ShapeFactory.INST.createEllipse();
		assertEquals(4, ell.getNbPoints());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructors3NotOKSamePoints() {
		ShapeFactory.INST.createEllipse(ShapeFactory.INST.createPoint(), ShapeFactory.INST.createPoint());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructors3NotOKIncorrectPoints() {
		ShapeFactory.INST.createEllipse(ShapeFactory.INST.createPoint(1, 0), ShapeFactory.INST.createPoint(2, 0));
	}


	@Test(expected = IllegalArgumentException.class)
	public void testConstructors3NotOKNAN() {
		ShapeFactory.INST.createEllipse(ShapeFactory.INST.createPoint(1, Double.NaN), ShapeFactory.INST.createPoint(2, 0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructors3() {
		ShapeFactory.INST.createEllipse(ShapeFactory.INST.createPoint(1, 2), ShapeFactory.INST.createPoint(2, Double.NaN));
	}

	@Test
	public void testConstructors3OK() {
		final Ellipse ell = ShapeFactory.INST.createEllipse(ShapeFactory.INST.createPoint(20, 26), ShapeFactory.INST.createPoint(30, 35));
		assertEqualsDouble(20., ell.getPosition().getX());
		assertEqualsDouble(35., ell.getPosition().getY());
		assertEqualsDouble(10., ell.getWidth());
		assertEqualsDouble(9., ell.getHeight());
	}

	@Test
	public void testIsTypeOf() {
		final Ellipse shape = ShapeFactory.INST.createEllipse();
		assertFalse(shape.isTypeOf(Rectangle.class));
		assertFalse(shape.isTypeOf(Circle.class));
		assertTrue(shape.isTypeOf(Shape.class));
		assertTrue(shape.isTypeOf(PositionShape.class));
		assertTrue(shape.isTypeOf(RectangularShape.class));
		assertTrue(shape.isTypeOf(Ellipse.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}
}
