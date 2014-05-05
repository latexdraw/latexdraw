package test.glib.models;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.prop.ILineArcProp;
import net.sf.latexdraw.glib.models.interfaces.shape.ICircle;
import net.sf.latexdraw.glib.models.interfaces.shape.IGrid;
import net.sf.latexdraw.glib.models.interfaces.shape.IPositionShape;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;
import net.sf.latexdraw.glib.models.interfaces.shape.ISquare;
import net.sf.latexdraw.glib.models.interfaces.shape.ISquaredShape;

import org.junit.Before;
import org.junit.Test;

import test.HelperTest;
import test.glib.models.interfaces.TestISquare;

public class TestLSquare<T extends ISquare> extends TestISquare<T> {
	@Before
	public void setUp() {
		shape = (T) ShapeFactory.createSquare(false);
		shape2 = (T) ShapeFactory.createSquare(false);
	}


	@Override
	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(IGrid.class));
		assertFalse(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(IPositionShape.class));
		assertTrue(shape.isTypeOf(ISquaredShape.class));
		assertTrue(shape.isTypeOf(ILineArcProp.class));
		assertTrue(shape.isTypeOf(ISquare.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}


	@Test
	public void testConstructors() {
		ISquare sq = ShapeFactory.createSquare(false);
		assertEquals(4, sq.getNbPoints());

		try {
			sq = ShapeFactory.createSquare(null, 10, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
		try {
			sq = ShapeFactory.createSquare(ShapeFactory.createPoint(Double.NaN, 0), 10, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
		try {
			sq = ShapeFactory.createSquare(ShapeFactory.createPoint(), 0, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
		try {
			sq = ShapeFactory.createSquare(ShapeFactory.createPoint(), -10, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
		try {
			sq = ShapeFactory.createSquare(ShapeFactory.createPoint(), Double.NaN, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
		try {
			sq = ShapeFactory.createSquare(ShapeFactory.createPoint(), Double.POSITIVE_INFINITY, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
		try {
			sq = ShapeFactory.createSquare(ShapeFactory.createPoint(), Double.NEGATIVE_INFINITY, true);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		sq = ShapeFactory.createSquare(ShapeFactory.createPoint(20, 26), 11, true);
		HelperTest.assertEqualsDouble(20., sq.getPosition().getX());
		HelperTest.assertEqualsDouble(37., sq.getPosition().getY());
		HelperTest.assertEqualsDouble(11., sq.getWidth());
		HelperTest.assertEqualsDouble(11., sq.getHeight());
	}
}
