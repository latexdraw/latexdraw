package test.glib.models;


import net.sf.latexdraw.glib.models.ShapeFactory;

import org.junit.Before;
import org.junit.Test;

import test.glib.models.interfaces.TestILine;

public class TestLLine extends TestILine{

	@Override
	@Before
	public void setUp() {
		line = ShapeFactory.factory().createLine(ShapeFactory.factory().createPoint(0,0), ShapeFactory.factory().createPoint(1,1));
	}


	@Test
	public void testConstructors2() {
		line = ShapeFactory.factory().createLine(10, ShapeFactory.factory().createPoint(1,1));

		assertNotNull(line.getPoint1());
		assertNotNull(line.getPoint2());
		assertEquals(10., line.getB());
		assertEquals(-9., line.getA());
		assertEquals(1., line.getX1());
		assertEquals(1., line.getY1());

		line = ShapeFactory.factory().createLine(0, ShapeFactory.factory().createPoint(1,1));

		assertNotNull(line.getPoint1());
		assertNotNull(line.getPoint2());
		assertEquals(0., line.getB());
		assertEquals(1., line.getA());
		assertEquals(1., line.getX1());
		assertEquals(1., line.getY1());

		line = ShapeFactory.factory().createLine(-10, ShapeFactory.factory().createPoint(1,1));

		assertNotNull(line.getPoint1());
		assertNotNull(line.getPoint2());
		assertEquals(-10., line.getB());
		assertEquals(11., line.getA());
		assertEquals(1., line.getX1());
		assertEquals(1., line.getY1());

		try {
			line = ShapeFactory.factory().createLine(Double.NaN, ShapeFactory.factory().createPoint(1,2));
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			line = ShapeFactory.factory().createLine(Double.NEGATIVE_INFINITY, ShapeFactory.factory().createPoint(1,2));
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			line = ShapeFactory.factory().createLine(Double.POSITIVE_INFINITY, ShapeFactory.factory().createPoint(1,2));
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			line = ShapeFactory.factory().createLine(10, ShapeFactory.factory().createPoint(1,Double.NaN));
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			line = ShapeFactory.factory().createLine(10, ShapeFactory.factory().createPoint(Double.NEGATIVE_INFINITY,2));
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			line = ShapeFactory.factory().createLine(10, null);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
	}


	@Test
	public void testConstructors3() {
		line = ShapeFactory.factory().createLine(ShapeFactory.factory().createPoint(1,1), ShapeFactory.factory().createPoint(2,2));

		assertNotNull(line.getPoint1());
		assertNotNull(line.getPoint2());
		assertEquals(0., line.getB());
		assertEquals(1., line.getA());
		assertEquals(1., line.getX1());
		assertEquals(1., line.getY1());
		assertEquals(2., line.getX2());
		assertEquals(2., line.getY2());

		try {
			line = ShapeFactory.factory().createLine(null, ShapeFactory.factory().createPoint(1,2));
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			line = ShapeFactory.factory().createLine(ShapeFactory.factory().createPoint(Double.NaN,2), ShapeFactory.factory().createPoint(1,2));
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			line = ShapeFactory.factory().createLine(ShapeFactory.factory().createPoint(2, Double.POSITIVE_INFINITY), ShapeFactory.factory().createPoint(1,2));
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			line = ShapeFactory.factory().createLine(ShapeFactory.factory().createPoint(1,2), null);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			line = ShapeFactory.factory().createLine(ShapeFactory.factory().createPoint(1,2), ShapeFactory.factory().createPoint(Double.NaN, 1));
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			line = ShapeFactory.factory().createLine(ShapeFactory.factory().createPoint(1,2), ShapeFactory.factory().createPoint(1, Double.NEGATIVE_INFINITY));
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
	}
}
