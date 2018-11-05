package net.sf.latexdraw.parser.svg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestSVGMatrix {
	@Test
	void testCreateRotate() {
		final double angle = 0.14;
		final SVGMatrix m = SVGMatrix.createRotate(angle);
		assertEquals(Math.cos(angle), m.a, 0.0001);
		assertEquals(Math.sin(angle), m.b, 0.0001);
		assertEquals(-Math.sin(angle), m.c, 0.0001);
		assertEquals(Math.cos(angle), m.d, 0.0001);
		assertEquals(0d, m.e, 0.0001);
		assertEquals(0d, m.f, 0.0001);
	}

	@Test
	void testCreateRotate00() {
		final double angle = 0.14;
		final SVGMatrix m = SVGMatrix.createRotate(angle, 0d, 0d);
		assertEquals(Math.cos(angle), m.a, 0.0001);
		assertEquals(Math.sin(angle), m.b, 0.0001);
		assertEquals(-Math.sin(angle), m.c, 0.0001);
		assertEquals(Math.cos(angle), m.d, 0.0001);
		assertEquals(0d, m.e, 0.0001);
		assertEquals(0d, m.f, 0.0001);
	}

	@Test
	void testCreateTranslate() {
		final double tx = 1.4;
		final double ty = -0.4;
		final SVGMatrix m = SVGMatrix.createTranslate(tx, ty);
		assertEquals(1d, m.a, 0.0001);
		assertEquals(0d, m.b, 0.0001);
		assertEquals(0d, m.c, 0.0001);
		assertEquals(1d, m.d, 0.0001);
		assertEquals(tx, m.e, 0.0001);
		assertEquals(ty, m.f, 0.0001);
	}

	@Test
	void testCreateScaleXY() {
		final double sx = 5.877423;
		final double sy = -0.04;
		final SVGMatrix m = SVGMatrix.createScale(sx, sy);
		assertEquals(sx, m.a, 0.0001);
		assertEquals(0d, m.b, 0.0001);
		assertEquals(0d, m.c, 0.0001);
		assertEquals(sy, m.d, 0.0001);
		assertEquals(0d, m.e, 0.0001);
		assertEquals(0d, m.f, 0.0001);
	}

	@Test
	void testCreateScaleX() {
		final double s = 5.877423;
		final SVGMatrix m = SVGMatrix.createScale(s, s);
		assertEquals(s, m.a, 0.0001);
		assertEquals(0d, m.b, 0.0001);
		assertEquals(0d, m.c, 0.0001);
		assertEquals(s, m.d, 0.0001);
		assertEquals(0d, m.e, 0.0001);
		assertEquals(0d, m.f, 0.0001);
	}

	@Test
	void testCreateSkewX() {
		final double angle = 1.92837;
		final SVGMatrix m = SVGMatrix.createSkewX(angle);
		assertEquals(1d, m.a, 0.0001);
		assertEquals(0d, m.b, 0.0001);
		assertEquals(Math.tan(angle), m.c, 0.0001);
		assertEquals(1d, m.d, 0.0001);
		assertEquals(0d, m.e, 0.0001);
		assertEquals(0d, m.f, 0.0001);
	}

	@Test
	void testCreateSkewY() {
		final double angle = -7.7283;
		final SVGMatrix m = SVGMatrix.createSkewY(angle);
		assertEquals(1d, m.a, 0.0001);
		assertEquals(Math.tan(angle), m.b, 0.0001);
		assertEquals(0d, m.c, 0.0001);
		assertEquals(1d, m.d, 0.0001);
		assertEquals(0d, m.e, 0.0001);
		assertEquals(0d, m.f, 0.0001);
	}

	@Test
	void testCreateMatrix() {
		final SVGMatrix m = new SVGMatrix(2, 3, 4, 5, 6, 7);
		assertEquals(2d, m.a, 0.0001);
		assertEquals(3d, m.b, 0.0001);
		assertEquals(4d, m.c, 0.0001);
		assertEquals(5d, m.d, 0.0001);
		assertEquals(6d, m.e, 0.0001);
		assertEquals(7d, m.f, 0.0001);
	}

	@Test
	void testMultiply() {
		final SVGMatrix m = new SVGMatrix(1, 2, 3, 4, 5, 6);
		final SVGMatrix m2 = new SVGMatrix(7, 8, 9, 10, 11, 12);
		final SVGMatrix m3 = m.multiply(m2);
		assertEquals(31d, m3.a, 0.0001);
		assertEquals(46d, m3.b, 0.0001);
		assertEquals(39d, m3.c, 0.0001);
		assertEquals(58d, m3.d, 0.0001);
		assertEquals(52d, m3.e, 0.0001);
		assertEquals(76d, m3.f, 0.0001);
	}

	@Test
	void testTranslate() {
		final SVGMatrix m = new SVGMatrix(1, 2, 3, 4, 5, 6);
		final SVGMatrix m3 = m.translate(10d, 11d);
		assertEquals(1d, m3.a, 0.0001);
		assertEquals(2d, m3.b, 0.0001);
		assertEquals(3d, m3.c, 0.0001);
		assertEquals(4d, m3.d, 0.0001);
		assertEquals(10d, m3.e, 0.0001);
		assertEquals(11d, m3.f, 0.0001);
	}

	@Test
	void testToStringNotNull() {
		assertNotNull(new SVGMatrix(1, 2, 3, 4, 5, 6).toString());
	}
}
