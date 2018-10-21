package net.sf.latexdraw.parsers.svg;

import java.awt.geom.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

 class TestSVGTransformList {
	SVGTransformList t;

	@BeforeEach
	 void setUp() {
		t = new SVGTransformList();
	}

	@Test
	 void testContructorsEmpty() {
		assertTrue(t.isEmpty());
	}

	@Test
	 void testContructorsEmptynull() {
		t = new SVGTransformList(null);
		assertTrue(t.isEmpty());
	}

	@Test
	 void testContructorsEmptyEmpty() {
		t = new SVGTransformList("");
		assertTrue(t.isEmpty());
	}

	@Test
	 void testContructorsNotEmpty() {
		t = new SVGTransformList("translate(2,2)");
		assertFalse(t.isEmpty());
	}

	@Test
	 void testAddTransformationsNULL() {
		t.addTransformations(null);
		assertTrue(t.isEmpty());
	}

	@Test
	 void testAddTransformationsEmpty() {
		t.addTransformations("");
		assertTrue(t.isEmpty());
	}

	@ParameterizedTest
	@ValueSource(strings = {"dsqdsq", "translate", "matrix(1 2 3)"})
	 void testAddTransformationsInvalid(final String data) {
		t.addTransformations(data);
		assertTrue(t.isEmpty());
	}

	@ParameterizedTest
	@ValueSource(strings = {"rotate(1)", "rotate(1 , 2 4)", "skewY(	1)", "skewX(1)", "scale(1 1)", "translate(1 ,1)", "matrix(1 2 3 ,4 ,5 ,6)", "translate(1 1) ,"})
	 void testAddTransformationsOK(final String data) {
		t.addTransformations(data);
		assertEquals(1, t.size());
	}

	@ParameterizedTest
	@ValueSource(strings = {"matrix(1 2 3 ,4 ,5 ,6) translate(2 2)", "rotate ( 2, 3 5), skewX( 2)", "rotate ( 2, 3 5)scale( 2)"})
	 void testAddTransformationsOK2(final String data) {
		t.addTransformations(data);
		assertEquals(2, t.size());
	}

	@Test
	 void testAddTransformationsOK4() {
		t.addTransformations("rotate ( 2, 3 5), \n skewX( 2	\n)");
		t.addTransformations("rotate ( 2, 3 5)scale( 2)");
		assertEquals(4, t.size());
	}

	@Test
	 void testTransformPoint() {
		final Point2D pt1 = new Point2D.Double(1d, 1d);
		final Point2D pt2 = new Point2D.Double(3d, 4d);
		t.addTransformations("translate( 2 3)");
		assertEquals(pt2, t.transformPoint(pt1));
	}

	 @Test
	 void testRotatePoint() {
		 t.addTransformations("rotate(90)");
		 final Point2D p = t.transformPoint(new Point2D.Double(1d, 0d));
		 assertEquals(0d, p.getX(), 0.0001);
		 assertEquals(1d, p.getY(), 0.0001);
	 }

	 @Test
	 void testTransformPointIso() {
		 final Point2D pt1 = new Point2D.Double(1d, 1d);
		 t.addTransformations("translate( 2 3)");
		 t.addTransformations("translate( -2 -3)");
		 assertEquals(pt1, t.transformPoint(pt1));
	 }

	@Test
	 void testTransformPoint2() {
		t.addTransformations("translate( 2 3)");
		t.addTransformations("translate( -2 -3)");
		t.addTransformations("rotate(90)");
		final Point2D pt3 = t.transformPoint(new Point2D.Double(1d, 0d));
		assertEquals(0d, pt3.getX(), 0.0001);
		assertEquals(1d, pt3.getY(), 0.0001);
	}

	@Test
	 void testGetGlobalTransformationDefault() {
		final SVGMatrix m = t.getGlobalTransformationMatrix();
		assertNull(m);
	}

	@Test
	 void testGetGlobalTransformation1() {
		t.addTransformations("scale( 2 3)");
		final SVGMatrix m = t.getGlobalTransformationMatrix();
		assertEquals(2d, m.a, 0.0001);
		assertEquals(0d, m.b, 0.0001);
		assertEquals(0d, m.c, 0.0001);
		assertEquals(3d, m.d, 0.0001);
		assertEquals(0d, m.e, 0.0001);
		assertEquals(0d, m.f, 0.0001);
	}

	@Test
	 void testGetGlobalTransformation2() {
		t.addTransformations("scale( 2 3)");
		t.addTransformations("skewX( 0.5	\n)");
		final SVGMatrix m = t.getGlobalTransformationMatrix();
		assertEquals(2d, m.a, 0.0001);
		assertEquals(0d, m.b, 0.0001);
		assertEquals(2d * Math.tan(0.5), m.c, 0.0001);
		assertEquals(3d, m.d, 0.0001);
		assertEquals(0d, m.e, 0.0001);
		assertEquals(0d, m.f, 0.0001);
	}
}
