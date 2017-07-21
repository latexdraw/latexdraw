package test.models;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.models.interfaces.shape.IPositionShape;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IRectangularShape;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.junit.Test;
import test.HelperTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestLEllipse implements HelperTest {
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
