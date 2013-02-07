package test.glib.models;


import net.sf.latexdraw.glib.models.impl.LShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;

import org.junit.Before;
import org.junit.Test;

import test.glib.models.interfaces.TestILine;

public class TestLLine extends TestILine{

	@Override
	@Before
	public void setUp() {
		DrawingTK.setFactory(new LShapeFactory());
		line = DrawingTK.getFactory().createLine(DrawingTK.getFactory().createPoint(0,0), DrawingTK.getFactory().createPoint(1,1));
	}


	@Test
	public void testConstructors2() {
		line = DrawingTK.getFactory().createLine(10, DrawingTK.getFactory().createPoint(1,1));

		assertNotNull(line.getPoint1());
		assertNotNull(line.getPoint2());
		assertEquals(10., line.getB());
		assertEquals(-9., line.getA());
		assertEquals(1., line.getX1());
		assertEquals(1., line.getY1());

		line = DrawingTK.getFactory().createLine(0, DrawingTK.getFactory().createPoint(1,1));

		assertNotNull(line.getPoint1());
		assertNotNull(line.getPoint2());
		assertEquals(0., line.getB());
		assertEquals(1., line.getA());
		assertEquals(1., line.getX1());
		assertEquals(1., line.getY1());

		line = DrawingTK.getFactory().createLine(-10, DrawingTK.getFactory().createPoint(1,1));

		assertNotNull(line.getPoint1());
		assertNotNull(line.getPoint2());
		assertEquals(-10., line.getB());
		assertEquals(11., line.getA());
		assertEquals(1., line.getX1());
		assertEquals(1., line.getY1());

		try {
			line = DrawingTK.getFactory().createLine(Double.NaN, DrawingTK.getFactory().createPoint(1,2));
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			line = DrawingTK.getFactory().createLine(Double.NEGATIVE_INFINITY, DrawingTK.getFactory().createPoint(1,2));
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			line = DrawingTK.getFactory().createLine(Double.POSITIVE_INFINITY, DrawingTK.getFactory().createPoint(1,2));
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			line = DrawingTK.getFactory().createLine(10, DrawingTK.getFactory().createPoint(1,Double.NaN));
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			line = DrawingTK.getFactory().createLine(10, DrawingTK.getFactory().createPoint(Double.NEGATIVE_INFINITY,2));
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			line = DrawingTK.getFactory().createLine(10, null);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
	}


	@Test
	public void testConstructors3() {
		line = DrawingTK.getFactory().createLine(DrawingTK.getFactory().createPoint(1,1), DrawingTK.getFactory().createPoint(2,2));

		assertNotNull(line.getPoint1());
		assertNotNull(line.getPoint2());
		assertEquals(0., line.getB());
		assertEquals(1., line.getA());
		assertEquals(1., line.getX1());
		assertEquals(1., line.getY1());
		assertEquals(2., line.getX2());
		assertEquals(2., line.getY2());

		try {
			line = DrawingTK.getFactory().createLine(null, DrawingTK.getFactory().createPoint(1,2));
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			line = DrawingTK.getFactory().createLine(DrawingTK.getFactory().createPoint(Double.NaN,2), DrawingTK.getFactory().createPoint(1,2));
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			line = DrawingTK.getFactory().createLine(DrawingTK.getFactory().createPoint(2, Double.POSITIVE_INFINITY), DrawingTK.getFactory().createPoint(1,2));
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			line = DrawingTK.getFactory().createLine(DrawingTK.getFactory().createPoint(1,2), null);
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			line = DrawingTK.getFactory().createLine(DrawingTK.getFactory().createPoint(1,2), DrawingTK.getFactory().createPoint(Double.NaN, 1));
			fail();
		}catch(IllegalArgumentException ex) { /* */ }

		try {
			line = DrawingTK.getFactory().createLine(DrawingTK.getFactory().createPoint(1,2), DrawingTK.getFactory().createPoint(1, Double.NEGATIVE_INFINITY));
			fail();
		}catch(IllegalArgumentException ex) { /* */ }
	}
}
