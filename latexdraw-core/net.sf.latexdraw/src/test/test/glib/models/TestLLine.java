package test.glib.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import net.sf.latexdraw.glib.models.ShapeFactory;

import org.junit.Before;
import org.junit.Test;

import test.glib.models.interfaces.TestILine;

public class TestLLine extends TestILine {
	@Before
	public void setUp() {
		line = ShapeFactory.createLine(ShapeFactory.createPoint(0, 0), ShapeFactory.createPoint(1, 1));
	}

	@Test
	public void testConstructors2() {
		line = ShapeFactory.createLine(10, ShapeFactory.createPoint(1, 1));

		assertNotNull(line.getPoint1());
		assertNotNull(line.getPoint2());
		assertEquals(10., line.getB(), 0.1);
		assertEquals(-9., line.getA(), 0.1);
		assertEquals(1., line.getX1(), 0.1);
		assertEquals(1., line.getY1(), 0.1);

		line = ShapeFactory.createLine(0, ShapeFactory.createPoint(1, 1));

		assertNotNull(line.getPoint1());
		assertNotNull(line.getPoint2());
		assertEquals(0., line.getB(), 0.1);
		assertEquals(1., line.getA(), 0.1);
		assertEquals(1., line.getX1(), 0.1);
		assertEquals(1., line.getY1(), 0.1);

		line = ShapeFactory.createLine(-10, ShapeFactory.createPoint(1, 1));

		assertNotNull(line.getPoint1());
		assertNotNull(line.getPoint2());
		assertEquals(-10., line.getB(), 0.1);
		assertEquals(11., line.getA(), 0.1);
		assertEquals(1., line.getX1(), 0.1);
		assertEquals(1., line.getY1(), 0.1);

		try {
			line = ShapeFactory.createLine(Double.NaN, ShapeFactory.createPoint(1, 2));
			fail();
		}catch(IllegalArgumentException ex) {
			/* */ }

		try {
			line = ShapeFactory.createLine(Double.NEGATIVE_INFINITY, ShapeFactory.createPoint(1, 2));
			fail();
		}catch(IllegalArgumentException ex) {
			/* */ }

		try {
			line = ShapeFactory.createLine(Double.POSITIVE_INFINITY, ShapeFactory.createPoint(1, 2));
			fail();
		}catch(IllegalArgumentException ex) {
			/* */ }

		try {
			line = ShapeFactory.createLine(10, ShapeFactory.createPoint(1, Double.NaN));
			fail();
		}catch(IllegalArgumentException ex) {
			/* */ }

		try {
			line = ShapeFactory.createLine(10, ShapeFactory.createPoint(Double.NEGATIVE_INFINITY, 2));
			fail();
		}catch(IllegalArgumentException ex) {
			/* */ }

		try {
			line = ShapeFactory.createLine(10, null);
			fail();
		}catch(IllegalArgumentException ex) {
			/* */ }
	}

	@Test
	public void testConstructors3() {
		line = ShapeFactory.createLine(ShapeFactory.createPoint(1, 1), ShapeFactory.createPoint(2, 2));

		assertNotNull(line.getPoint1());
		assertNotNull(line.getPoint2());
		assertEquals(0., line.getB(), 0.1);
		assertEquals(1., line.getA(), 0.1);
		assertEquals(1., line.getX1(), 0.1);
		assertEquals(1., line.getY1(), 0.1);
		assertEquals(2., line.getX2(), 0.1);
		assertEquals(2., line.getY2(), 0.1);

		try {
			line = ShapeFactory.createLine(null, ShapeFactory.createPoint(1, 2));
			fail();
		}catch(IllegalArgumentException ex) {
			/* */ }

		try {
			line = ShapeFactory.createLine(ShapeFactory.createPoint(Double.NaN, 2), ShapeFactory.createPoint(1, 2));
			fail();
		}catch(IllegalArgumentException ex) {
			/* */ }

		try {
			line = ShapeFactory.createLine(ShapeFactory.createPoint(2, Double.POSITIVE_INFINITY), ShapeFactory.createPoint(1, 2));
			fail();
		}catch(IllegalArgumentException ex) {
			/* */ }

		try {
			line = ShapeFactory.createLine(ShapeFactory.createPoint(1, 2), null);
			fail();
		}catch(IllegalArgumentException ex) {
			/* */ }

		try {
			line = ShapeFactory.createLine(ShapeFactory.createPoint(1, 2), ShapeFactory.createPoint(Double.NaN, 1));
			fail();
		}catch(IllegalArgumentException ex) {
			/* */ }

		try {
			line = ShapeFactory.createLine(ShapeFactory.createPoint(1, 2), ShapeFactory.createPoint(1, Double.NEGATIVE_INFINITY));
			fail();
		}catch(IllegalArgumentException ex) {
			/* */ }
	}
}
