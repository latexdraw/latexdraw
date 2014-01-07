package test.glib.models;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.ICircle;
import net.sf.latexdraw.glib.models.interfaces.IEllipse;
import net.sf.latexdraw.glib.models.interfaces.IPositionShape;
import net.sf.latexdraw.glib.models.interfaces.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.IRectangularShape;
import net.sf.latexdraw.glib.models.interfaces.IShape;

import org.junit.Before;
import org.junit.Test;

import test.HelperTest;
import test.glib.models.interfaces.TestIEllipse;


public class TestLEllipse<T extends IEllipse> extends TestIEllipse<T> {
	@Before
	public void setUp() {
		shape  = (T) ShapeFactory.createEllipse(false);
		shape2 = (T) ShapeFactory.createEllipse(false);
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
		IEllipse ell = ShapeFactory.createEllipse(false);

		assertEquals(4, ell.getNbPoints());
	}

	@Test
	public void testConstructors3() {
		IEllipse ell;

		try {
			ell = ShapeFactory.createEllipse(null, ShapeFactory.createPoint(), true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			ell = ShapeFactory.createEllipse(ShapeFactory.createPoint(), null, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			ell = ShapeFactory.createEllipse(ShapeFactory.createPoint(), ShapeFactory.createPoint(), true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			ell = ShapeFactory.createEllipse(ShapeFactory.createPoint(1,0), ShapeFactory.createPoint(2,0), true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			ell = ShapeFactory.createEllipse(ShapeFactory.createPoint(1,Double.NaN), ShapeFactory.createPoint(2,0), true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			ell = ShapeFactory.createEllipse(ShapeFactory.createPoint(1,2), ShapeFactory.createPoint(2,Double.NaN), true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		ell = ShapeFactory.createEllipse(ShapeFactory.createPoint(20, 26), ShapeFactory.createPoint(30, 35), true);
		HelperTest.assertEqualsDouble(20., ell.getPosition().getX());
		HelperTest.assertEqualsDouble(35., ell.getPosition().getY());
		HelperTest.assertEqualsDouble(10., ell.getWidth());
		HelperTest.assertEqualsDouble(9., ell.getHeight());
	}
}
