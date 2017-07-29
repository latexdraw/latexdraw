package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.parsers.svg.SVGMatrix;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestSVGMatrix {
	@Test
	public void testConstructor() {
		SVGMatrix m = new SVGMatrix();

		assertEquals(m.getA(), 1., 0.0001);
		assertEquals(m.getD(), 1., 0.0001);
		assertEquals(m.getE(), 0., 0.0001);
		assertEquals(m.getB(), 0., 0.0001);
		assertEquals(m.getC(), 0., 0.0001);
		assertEquals(m.getF(), 0., 0.0001);
	}

	@Test
	public void testInitMatrix() {
		SVGMatrix m = new SVGMatrix();
		m.initMatrix();

		assertEquals(m.getA(), 1., 0.0001);
		assertEquals(m.getD(), 1., 0.0001);
		assertEquals(m.getE(), 0., 0.0001);
		assertEquals(m.getB(), 0., 0.0001);
		assertEquals(m.getC(), 0., 0.0001);
		assertEquals(m.getF(), 0., 0.0001);
	}

	@Test
	public void testRotate() {
		SVGMatrix m = new SVGMatrix();
		double angle = 0.14;

		m.rotate(angle);
		assertEquals(m.getA(), Math.cos(angle), 0.0001);
		assertEquals(m.getB(), Math.sin(angle), 0.0001);
		assertEquals(m.getC(), -Math.sin(angle), 0.0001);
		assertEquals(m.getD(), Math.cos(angle), 0.0001);
		assertEquals(m.getE(), 0., 0.0001);
		assertEquals(m.getF(), 0., 0.0001);
	}

	@Test
	public void testTranslate() {
		SVGMatrix m = new SVGMatrix();
		double tx = 1.4, ty = -0.4;

		m.translate(tx, ty);
		assertEquals(m.getA(), 1., 0.0001);
		assertEquals(m.getB(), 0., 0.0001);
		assertEquals(m.getC(), 0., 0.0001);
		assertEquals(m.getD(), 1., 0.0001);
		assertEquals(m.getE(), tx, 0.0001);
		assertEquals(m.getF(), ty, 0.0001);
	}

	@Test
	public void testScaleNonUniform() {
		SVGMatrix m = new SVGMatrix();
		double sx = 5.877423, sy = -0.04;

		m.scaleNonUniform(sx, sy);
		assertEquals(m.getA(), sx, 0.0001);
		assertEquals(m.getB(), 0., 0.0001);
		assertEquals(m.getC(), 0., 0.0001);
		assertEquals(m.getD(), sy, 0.0001);
		assertEquals(m.getE(), 0., 0.0001);
		assertEquals(m.getF(), 0., 0.0001);
	}

	@Test
	public void testScale() {
		SVGMatrix m = new SVGMatrix();
		double s = 5.877423;

		m.scale(s);
		assertEquals(m.getA(), s, 0.0001);
		assertEquals(m.getB(), 0., 0.0001);
		assertEquals(m.getC(), 0., 0.0001);
		assertEquals(m.getD(), s, 0.0001);
		assertEquals(m.getE(), 0., 0.0001);
		assertEquals(m.getF(), 0., 0.0001);
	}

	@Test
	public void testSkewX() {
		SVGMatrix m = new SVGMatrix();
		double angle = 1.92837;

		m.skewX(angle);
		assertEquals(m.getA(), 1., 0.0001);
		assertEquals(m.getB(), 0., 0.0001);
		assertEquals(m.getC(), Math.tan(angle), 0.0001);
		assertEquals(m.getD(), 1., 0.0001);
		assertEquals(m.getE(), 0., 0.0001);
		assertEquals(m.getF(), 0., 0.0001);
	}

	@Test
	public void testSkewY() {
		SVGMatrix m = new SVGMatrix();
		double angle = -7.7283;

		m.skewY(angle);
		assertEquals(m.getA(), 1., 0.0001);
		assertEquals(m.getB(), Math.tan(angle), 0.0001);
		assertEquals(m.getC(), 0., 0.0001);
		assertEquals(m.getD(), 1., 0.0001);
		assertEquals(m.getE(), 0., 0.0001);
		assertEquals(m.getF(), 0., 0.0001);
	}

	@Test
	public void testSetMatrix() {
		SVGMatrix m = new SVGMatrix();

		m.setMatrix(2, 3, 4, 5, 6, 7);
		assertEquals(m.getA(), 2., 0.0001);
		assertEquals(m.getB(), 3., 0.0001);
		assertEquals(m.getC(), 4., 0.0001);
		assertEquals(m.getD(), 5., 0.0001);
		assertEquals(m.getE(), 6., 0.0001);
		assertEquals(m.getF(), 7., 0.0001);
	}

	@Test
	public void testMultiply() {
		SVGMatrix m1 = new SVGMatrix();
		SVGMatrix m2 = new SVGMatrix();
		SVGMatrix m3;

		m1.setMatrix(1, 2, 3, 4, 5, 6);
		m2.setMatrix(7, 8, 9, 10, 11, 12);
		m3 = m1.multiply(m2);

		assertEquals(m3.getA(), 31., 0.0001);
		assertEquals(m3.getB(), 46., 0.0001);
		assertEquals(m3.getC(), 39., 0.0001);
		assertEquals(m3.getD(), 58., 0.0001);
		assertEquals(m3.getE(), 52., 0.0001);
		assertEquals(m3.getF(), 76., 0.0001);
	}
}
