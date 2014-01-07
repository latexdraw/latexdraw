package test.glib.models;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.ICircle;
import net.sf.latexdraw.glib.models.interfaces.shape.IDot;
import net.sf.latexdraw.glib.models.interfaces.shape.IPositionShape;
import net.sf.latexdraw.glib.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;

import org.junit.Before;
import org.junit.Test;

import test.HelperTest;
import test.glib.models.interfaces.TestIDot;

public class TestLDot<T extends IDot> extends TestIDot<T> {
	@Before
	public void setUp() {
		shape  = (T) ShapeFactory.createDot(ShapeFactory.createPoint(), false);
		shape2 = (T) ShapeFactory.createDot(ShapeFactory.createPoint(), false);
	}


	@Override
	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(IRectangle.class));
		assertFalse(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(IDot.class));
		assertTrue(shape.isTypeOf(IPositionShape.class));
		assertTrue(shape.isTypeOf(IDot.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}


	@Test
	public void testConstructor1() {
		IDot dot1 = ShapeFactory.createDot(ShapeFactory.createPoint(), false);

		assertTrue(dot1.getRadius()>0);
		assertNotNull(dot1.getDotStyle());
		assertNotNull(dot1.getPosition());
		HelperTest.assertEqualsDouble(0., dot1.getPosition().getX());
		HelperTest.assertEqualsDouble(0., dot1.getPosition().getY());
	}


	@Test
	public void testConstructor3() {
		IDot dot1 = ShapeFactory.createDot(null, false);

		assertTrue(dot1.getRadius()>0);
		assertNotNull(dot1.getDotStyle());
		assertNotNull(dot1.getPosition());
		HelperTest.assertEqualsDouble(0., dot1.getPosition().getX());
		HelperTest.assertEqualsDouble(0., dot1.getPosition().getY());

		dot1 = ShapeFactory.createDot(ShapeFactory.createPoint(-1, 2), true);
		HelperTest.assertEqualsDouble(-1., dot1.getPosition().getX());
		HelperTest.assertEqualsDouble(2., dot1.getPosition().getY());
	}
}
