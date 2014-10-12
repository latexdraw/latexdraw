package test.glib.models;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.ICircle;
import net.sf.latexdraw.glib.models.interfaces.shape.IEllipse;
import net.sf.latexdraw.glib.models.interfaces.shape.IPositionShape;
import net.sf.latexdraw.glib.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.shape.IRectangularShape;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;

import org.junit.Before;
import org.junit.Test;

import test.HelperTest;
import test.glib.models.interfaces.TestIEllipse;


public class TestLEllipse extends TestIEllipse<IEllipse> {
	@Before
	public void setUp() {
		shape  = ShapeFactory.createEllipse();
		shape2 = ShapeFactory.createEllipse();
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
		assertTrue(shape.isTypeOf(IEllipse.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}


	@Test
	public void testConstructors2() {
		IEllipse ell = ShapeFactory.createEllipse();

		assertEquals(4, ell.getNbPoints());
	}

	@Test
	public void testConstructors3() {
		IEllipse ell;

		try {
			ell = ShapeFactory.createEllipse(null, ShapeFactory.createPoint());
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			ell = ShapeFactory.createEllipse(ShapeFactory.createPoint(), null);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			ell = ShapeFactory.createEllipse(ShapeFactory.createPoint(), ShapeFactory.createPoint());
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			ell = ShapeFactory.createEllipse(ShapeFactory.createPoint(1,0), ShapeFactory.createPoint(2,0));
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			ell = ShapeFactory.createEllipse(ShapeFactory.createPoint(1,Double.NaN), ShapeFactory.createPoint(2,0));
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			ell = ShapeFactory.createEllipse(ShapeFactory.createPoint(1,2), ShapeFactory.createPoint(2,Double.NaN));
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		ell = ShapeFactory.createEllipse(ShapeFactory.createPoint(20, 26), ShapeFactory.createPoint(30, 35));
		HelperTest.assertEqualsDouble(20., ell.getPosition().getX());
		HelperTest.assertEqualsDouble(35., ell.getPosition().getY());
		HelperTest.assertEqualsDouble(10., ell.getWidth());
		HelperTest.assertEqualsDouble(9., ell.getHeight());
	}
}
