package test.glib.models;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.ICircle;
import net.sf.latexdraw.glib.models.interfaces.IGrid;
import net.sf.latexdraw.glib.models.interfaces.ILineArcShape;
import net.sf.latexdraw.glib.models.interfaces.IPositionShape;
import net.sf.latexdraw.glib.models.interfaces.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.IRectangularShape;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.ISquare;

import org.junit.Before;
import org.junit.Test;

import test.HelperTest;
import test.glib.models.interfaces.TestISquare;

public class TestLSquare<T extends ISquare> extends TestISquare<T> {
	@Before
	public void setUp() {
		shape = (T) ShapeFactory.factory().createSquare(false);
		shape2 = (T) ShapeFactory.factory().createSquare(false);
	}


	@Override
	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(IGrid.class));
		assertFalse(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(IPositionShape.class));
		assertTrue(shape.isTypeOf(IRectangularShape.class));
		assertTrue(shape.isTypeOf(ILineArcShape.class));
		assertTrue(shape.isTypeOf(IRectangle.class));
		assertTrue(shape.isTypeOf(ISquare.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}


	@Test
	public void testConstructors() {
		ISquare sq = ShapeFactory.factory().createSquare(false);
		assertEquals(4, sq.getNbPoints());

		try {
			sq = ShapeFactory.factory().createSquare(null, 10, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
		try {
			sq = ShapeFactory.factory().createSquare(ShapeFactory.factory().createPoint(Double.NaN, 0), 10, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
		try {
			sq = ShapeFactory.factory().createSquare(ShapeFactory.factory().createPoint(), 0, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
		try {
			sq = ShapeFactory.factory().createSquare(ShapeFactory.factory().createPoint(), -10, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
		try {
			sq = ShapeFactory.factory().createSquare(ShapeFactory.factory().createPoint(), Double.NaN, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
		try {
			sq = ShapeFactory.factory().createSquare(ShapeFactory.factory().createPoint(), Double.POSITIVE_INFINITY, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
		try {
			sq = ShapeFactory.factory().createSquare(ShapeFactory.factory().createPoint(), Double.NEGATIVE_INFINITY, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		sq = ShapeFactory.factory().createSquare(ShapeFactory.factory().createPoint(20, 26), 11, true);
		HelperTest.assertEqualsDouble(20., sq.getPosition().getX());
		HelperTest.assertEqualsDouble(37., sq.getPosition().getY());
		HelperTest.assertEqualsDouble(11., sq.getWidth());
		HelperTest.assertEqualsDouble(11., sq.getHeight());
	}
}
