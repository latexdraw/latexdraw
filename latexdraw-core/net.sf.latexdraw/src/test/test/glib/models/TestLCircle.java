package test.glib.models;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.ICircle;
import net.sf.latexdraw.glib.models.interfaces.shape.IPositionShape;
import net.sf.latexdraw.glib.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.glib.models.interfaces.shape.ISquare;
import net.sf.latexdraw.glib.models.interfaces.shape.ISquaredShape;

import org.junit.Before;
import org.junit.Test;

import test.HelperTest;
import test.glib.models.interfaces.TestICircle;

public class TestLCircle extends TestICircle<ICircle> {
	@Before
	public void setUp() {
		shape  = ShapeFactory.createCircle(false);
		shape2 = ShapeFactory.createCircle(false);
	}


	@Override
	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(IRectangle.class));
		assertFalse(shape.isTypeOf(ISquare.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(IPositionShape.class));
		assertTrue(shape.isTypeOf(ISquaredShape.class));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}


	@Test
	public void testConstructors() {
		ICircle circle = ShapeFactory.createCircle(false);

		assertEquals(4, circle.getNbPoints());
		HelperTest.assertEqualsDouble(circle.getWidth(), circle.getHeight());
		assertTrue(circle.getHeight()>0);
	}


	@Test
	public void testConstructors2() {
		ICircle circle = ShapeFactory.createCircle(false);

		HelperTest.assertEqualsDouble(4, circle.getNbPoints());
		HelperTest.assertEqualsDouble(circle.getWidth(), circle.getHeight());
		assertTrue(circle.getHeight()>0);
	}


	@Test
	public void testConstructors3() {
		ICircle circle;

		try {
			circle = ShapeFactory.createCircle(null, 10., true);
			fail();
		}catch(IllegalArgumentException e) {/* */}

		try {
			circle = ShapeFactory.createCircle(ShapeFactory.createPoint(Double.NaN, 1), 10., true);
			fail();
		}catch(IllegalArgumentException e) {/* */}

		try {
			circle = ShapeFactory.createCircle(ShapeFactory.createPoint(1, Double.NEGATIVE_INFINITY), 10., true);
			fail();
		}catch(IllegalArgumentException e) {/* */}

		try {
			circle = ShapeFactory.createCircle(ShapeFactory.createPoint(1, 1), -10., true);
			fail();
		}catch(IllegalArgumentException e) {/* */}

		try {
			circle = ShapeFactory.createCircle(ShapeFactory.createPoint(1, 1), 0., true);
			fail();
		}catch(IllegalArgumentException e) {/* */}

		circle = ShapeFactory.createCircle(ShapeFactory.createPoint(1, 2), 10., true);

		HelperTest.assertEqualsDouble(6., circle.getGravityCentre().getX());
		HelperTest.assertEqualsDouble(-3., circle.getGravityCentre().getY());
		HelperTest.assertEqualsDouble(10., circle.getWidth());
		assertEquals(4, circle.getNbPoints());
		HelperTest.assertEqualsDouble(circle.getWidth(), circle.getHeight());
	}
}
