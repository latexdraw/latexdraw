package net.sf.latexdraw.models.impl;

import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.data.DoubleData;
import net.sf.latexdraw.data.EllData;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.models.interfaces.shape.IPositionShape;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IRectangularShape;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;

@RunWith(Theories.class)
public class TestIEllipse implements HelperTest {
	@Theory
	public void testGetAB(@EllData final IEllipse shape, @DoubleData final double w, @DoubleData final double h,
						@DoubleData final double x, @DoubleData final double y) {
		assumeThat(w, greaterThan(0d));
		assumeThat(h, greaterThan(0d));

		shape.setPosition(x, y);
		shape.setWidth(w);
		shape.setHeight(h);

		assertEqualsDouble(Math.max(w, h) / 2d, shape.getA());
		assertEqualsDouble(Math.min(w, h) / 2d, shape.getB());
	}

	@Test
	public void testConstructors2() {
		final IEllipse ell = ShapeFactory.INST.createEllipse();
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
		final IEllipse ell = ShapeFactory.INST.createEllipse(ShapeFactory.INST.createPoint(20, 26), ShapeFactory.INST.createPoint(30, 35));
		assertEqualsDouble(20., ell.getPosition().getX());
		assertEqualsDouble(35., ell.getPosition().getY());
		assertEqualsDouble(10., ell.getWidth());
		assertEqualsDouble(9., ell.getHeight());
	}

	@Test
	public void testIsTypeOf() {
		final IEllipse shape = ShapeFactory.INST.createEllipse();
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(IRectangle.class));
		assertFalse(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(IPositionShape.class));
		assertTrue(shape.isTypeOf(IRectangularShape.class));
		assertTrue(shape.isTypeOf(IEllipse.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}
}
