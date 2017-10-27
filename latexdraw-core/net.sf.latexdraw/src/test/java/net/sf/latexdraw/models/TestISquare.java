package net.sf.latexdraw.models;

import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.prop.ILineArcProp;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import net.sf.latexdraw.models.interfaces.shape.IPositionShape;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.ISquare;
import net.sf.latexdraw.models.interfaces.shape.ISquaredShape;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestISquare implements HelperTest {
	@Test
	public void testConstructorsOKPoints() {
		ISquare sq = ShapeFactory.INST.createSquare();
		assertEquals(4, sq.getNbPoints());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorsNotNaN() {
		ShapeFactory.INST.createSquare(ShapeFactory.INST.createPoint(Double.NaN, 0), 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorsNotOK0() {
		ShapeFactory.INST.createSquare(ShapeFactory.INST.createPoint(), 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorsNotOKNeg() {
		ShapeFactory.INST.createSquare(ShapeFactory.INST.createPoint(), -10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorsNotOKNaNSize() {
		ShapeFactory.INST.createSquare(ShapeFactory.INST.createPoint(), Double.NaN);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorsNotOKInfSize() {
		ShapeFactory.INST.createSquare(ShapeFactory.INST.createPoint(), Double.POSITIVE_INFINITY);
	}

	@Test
	public void testConstructorsOK() {
		final ISquare sq = ShapeFactory.INST.createSquare(ShapeFactory.INST.createPoint(20, 26), 11);
		assertEqualsDouble(20., sq.getPosition().getX());
		assertEqualsDouble(26., sq.getPosition().getY());
		assertEqualsDouble(11., sq.getWidth());
		assertEqualsDouble(11., sq.getHeight());
	}

	@Test
	public void testIsTypeOf() {
		final ISquare shape = ShapeFactory.INST.createSquare();
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
}
