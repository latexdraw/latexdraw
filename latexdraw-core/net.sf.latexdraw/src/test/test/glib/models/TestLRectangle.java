package test.glib.models;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.prop.ILineArcProp;
import net.sf.latexdraw.glib.models.interfaces.shape.ICircle;
import net.sf.latexdraw.glib.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.glib.models.interfaces.shape.IPositionShape;
import net.sf.latexdraw.glib.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.shape.IRectangularShape;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;

import org.junit.Before;
import org.junit.Test;

import test.HelperTest;
import test.glib.models.interfaces.TestIRectangle;

public class TestLRectangle extends TestIRectangle<IRectangle> {
	@Before
	public void setUp() {
		shape = ShapeFactory.createRectangle(false);
		shape2 = ShapeFactory.createRectangle(false);
	}


	@Override
	@Test
	public void testIsTypeOf() {
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


	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullPoint1() {
		ShapeFactory.createRectangle(ShapeFactory.createPoint(), null, true);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullPoint2() {
		ShapeFactory.createRectangle(null, ShapeFactory.createPoint(), true);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullPoint3() {
		ShapeFactory.createRectangle(null, null, true);
	}

	@Test(expected=NullPointerException.class)
	public void testConstructorNullPoint4() {
		ShapeFactory.createRectangle(null, 10, 10, true);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNotValid1() {
		ShapeFactory.createRectangle(ShapeFactory.createPoint(Double.NaN, 0), 10, 10, true);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNotValid2() {
		ShapeFactory.createRectangle(ShapeFactory.createPoint(0, 0), -10, 10, true);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNotValid3() {
		ShapeFactory.createRectangle(ShapeFactory.createPoint(0, 0), Double.NaN, 10, true);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNotValid4() {
		ShapeFactory.createRectangle(ShapeFactory.createPoint(0, 0), Double.POSITIVE_INFINITY, 10, true);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNotValid5() {
		ShapeFactory.createRectangle(ShapeFactory.createPoint(0, 0), Double.NEGATIVE_INFINITY, 10, true);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNotValid6() {
		ShapeFactory.createRectangle(ShapeFactory.createPoint(0, 0), 0, 10, true);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNotValid7() {
		ShapeFactory.createRectangle(ShapeFactory.createPoint(0, 0), 10, 0, true);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNotValid8() {
		ShapeFactory.createRectangle(ShapeFactory.createPoint(0, 0), 10, -10, true);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNotValid9() {
		ShapeFactory.createRectangle(ShapeFactory.createPoint(0, 0), 10, Double.NaN, true);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNotValid10() {
		ShapeFactory.createRectangle(ShapeFactory.createPoint(0, 0), 10, Double.NEGATIVE_INFINITY, true);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNotValid11() {
		ShapeFactory.createRectangle(ShapeFactory.createPoint(0, 0), 10, Double.POSITIVE_INFINITY, true);
	}

	@Test
	public void testConstructors() {
		IRectangle rec = ShapeFactory.createRectangle(false);
		assertEquals(4, rec.getNbPoints());

		rec = ShapeFactory.createRectangle(ShapeFactory.createPoint(20, 26), ShapeFactory.createPoint(30, 35), true);
		HelperTest.assertEqualsDouble(20., rec.getPosition().getX());
		HelperTest.assertEqualsDouble(35., rec.getPosition().getY());
		HelperTest.assertEqualsDouble(10., rec.getWidth());
		HelperTest.assertEqualsDouble(9., rec.getHeight());

		rec = ShapeFactory.createRectangle(ShapeFactory.createPoint(5, 6), 11, 12, true);
		HelperTest.assertEqualsDouble(5., rec.getPosition().getX());
		HelperTest.assertEqualsDouble(18., rec.getPosition().getY());
		HelperTest.assertEqualsDouble(11., rec.getWidth());
		HelperTest.assertEqualsDouble(12., rec.getHeight());
	}
}
