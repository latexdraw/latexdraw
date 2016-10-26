package test.glib.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.IPositionShape;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IRectangularShape;
import net.sf.latexdraw.models.interfaces.shape.IRhombus;
import net.sf.latexdraw.models.interfaces.shape.IShape;

import org.junit.Before;
import org.junit.Test;

import test.HelperTest;
import test.glib.models.interfaces.TestIRhombus;

public class TestLRhombus extends TestIRhombus<IRhombus> {
	@Before
	public void setUp() {
		shape = ShapeFactory.createRhombus();
		shape2 = ShapeFactory.createRhombus();
	}

	@Override
	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(IRectangle.class));
		assertFalse(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(IPositionShape.class));
		assertTrue(shape.isTypeOf(IRectangularShape.class));
		assertTrue(shape.isTypeOf(IRhombus.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}

	@Test
	public void testConstructor1() {
		assertEquals(4, shape.getNbPoints());
		assertEquals(1.0, shape.getWidth(), 0.0);
		assertEquals(1.0, shape.getHeight(), 0.0);
		assertEquals(0, shape.getPosition().getX(), 0.0);
		assertEquals(0, shape.getPosition().getY(), 0.0);
	}

	@Test
	public void testConstructor2() {
		IRhombus rho = ShapeFactory.createRhombus(ShapeFactory.createPoint(5, 15), 20, 40);
		HelperTest.assertEqualsDouble(4, rho.getNbPoints());
		assertEquals(20.0, rho.getWidth(), 0.0);
		assertEquals(40.0, rho.getHeight(), 0.0);
		assertEquals(-5.0, rho.getPosition().getX(), 0.0);
		assertEquals(35.0, rho.getPosition().getY(), 0.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInvalidPos() {
		ShapeFactory.createRhombus(ShapeFactory.createPoint(Double.NaN, 0), 10, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInvalidWidth() {
		ShapeFactory.createRhombus(ShapeFactory.createPoint(1, 1), -10, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorInvalidHeight() {
		ShapeFactory.createRhombus(ShapeFactory.createPoint(1, 1), 10, -10);
	}
}
