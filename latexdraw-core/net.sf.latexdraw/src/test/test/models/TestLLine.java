package test.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import net.sf.latexdraw.models.ShapeFactory;
import test.models.interfaces.TestILine;

public class TestLLine extends TestILine {
	@Before
	public void setUp() {
		line = ShapeFactory.INST.createLine(ShapeFactory.INST.createPoint(0, 0), ShapeFactory.INST.createPoint(1, 1));
	}

	@Test
	public void testConstructors2OK() {
		line = ShapeFactory.INST.createLine(10, ShapeFactory.INST.createPoint(1, 1));

		assertNotNull(line.getPoint1());
		assertNotNull(line.getPoint2());
		assertEquals(10., line.getB(), 0.1);
		assertEquals(-9., line.getA(), 0.1);
		assertEquals(1., line.getX1(), 0.1);
		assertEquals(1., line.getY1(), 0.1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructors2NotOKNaN() {
		ShapeFactory.INST.createLine(Double.NaN, ShapeFactory.INST.createPoint(1, 2));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructors2NotOKINF() {
		ShapeFactory.INST.createLine(Double.NEGATIVE_INFINITY, ShapeFactory.INST.createPoint(1, 2));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructors2NotOKNaNPoint() {
		ShapeFactory.INST.createLine(10, ShapeFactory.INST.createPoint(1, Double.NaN));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructors2NotOKInfPoint() {
		ShapeFactory.INST.createLine(10, ShapeFactory.INST.createPoint(Double.NEGATIVE_INFINITY, 2));
	}

	@Test
	public void testConstructors3OK() {
		line = ShapeFactory.INST.createLine(ShapeFactory.INST.createPoint(1, 1), ShapeFactory.INST.createPoint(2, 2));

		assertNotNull(line.getPoint1());
		assertNotNull(line.getPoint2());
		assertEquals(0., line.getB(), 0.1);
		assertEquals(1., line.getA(), 0.1);
		assertEquals(1., line.getX1(), 0.1);
		assertEquals(1., line.getY1(), 0.1);
		assertEquals(2., line.getX2(), 0.1);
		assertEquals(2., line.getY2(), 0.1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructors3NotOKNaN() {
		ShapeFactory.INST.createLine(ShapeFactory.INST.createPoint(Double.NaN, 2), ShapeFactory.INST.createPoint(1, 2));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructors3NotOKINF() {
		ShapeFactory.INST.createLine(ShapeFactory.INST.createPoint(2, Double.POSITIVE_INFINITY), ShapeFactory.INST.createPoint(1, 2));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testConstructors3NotOKNaNPoint() {
		ShapeFactory.INST.createLine(ShapeFactory.INST.createPoint(1, 2), ShapeFactory.INST.createPoint(Double.NaN, 1));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructors3NotOKINFPoint() {
			line = ShapeFactory.INST.createLine(ShapeFactory.INST.createPoint(1, 2), ShapeFactory.INST.createPoint(1, Double.NEGATIVE_INFINITY));
	}
}
