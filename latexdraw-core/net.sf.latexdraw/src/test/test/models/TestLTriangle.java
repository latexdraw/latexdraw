package test.models;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.IPositionShape;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IRectangularShape;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.ITriangle;
import org.junit.Test;
import test.HelperTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestLTriangle implements HelperTest {
	@Test
	public void testConstructor1() {
		final ITriangle shape = ShapeFactory.INST.createTriangle();
		assertEqualsDouble(1d, shape.getWidth());
		assertEqualsDouble(1d, shape.getHeight());
		assertEqualsDouble(0d, shape.getPosition().getX());
		assertEqualsDouble(1d, shape.getPosition().getY());
	}

	@Test
	public void testConstructor2() {
		final ITriangle rho = ShapeFactory.INST.createTriangle(ShapeFactory.INST.createPoint(5, 15), 20, 40);
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
		final ITriangle shape = ShapeFactory.INST.createTriangle();
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(IRectangle.class));
		assertFalse(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(IPositionShape.class));
		assertTrue(shape.isTypeOf(IRectangularShape.class));
		assertTrue(shape.isTypeOf(ITriangle.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}
}
