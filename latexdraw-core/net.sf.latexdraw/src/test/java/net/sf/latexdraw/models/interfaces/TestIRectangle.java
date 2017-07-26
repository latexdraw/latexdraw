package net.sf.latexdraw.models.interfaces;

import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.prop.ILineArcProp;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.models.interfaces.shape.IPositionShape;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IRectangularShape;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestIRectangle implements HelperTest {
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
		IRectangle rec = ShapeFactory.INST.createRectangle();
		assertEquals(4, rec.getNbPoints());
	}

	@Test
	public void testConstructorsOK1() {
		IRectangle rec = ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(20, 26), ShapeFactory.INST.createPoint(30, 35));
		assertEqualsDouble(20., rec.getPosition().getX());
		assertEqualsDouble(35., rec.getPosition().getY());
		assertEqualsDouble(10., rec.getWidth());
		assertEqualsDouble(9., rec.getHeight());
	}

	@Test
	public void testConstructorsOK2() {
		IRectangle rec = ShapeFactory.INST.createRectangle(ShapeFactory.INST.createPoint(5, 6), 11, 12);
		assertEqualsDouble(5., rec.getPosition().getX());
		assertEqualsDouble(18., rec.getPosition().getY());
		assertEqualsDouble(11., rec.getWidth());
		assertEqualsDouble(12., rec.getHeight());
	}

	@Test
	public void testIsTypeOf() {
		final IRectangle shape = ShapeFactory.INST.createRectangle();
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(IEllipse.class));
		assertFalse(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(ILineArcProp.class));
		assertTrue(shape.isTypeOf(IPositionShape.class));
		assertTrue(shape.isTypeOf(IRectangularShape.class));
		assertTrue(shape.isTypeOf(IRectangle.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}
}
