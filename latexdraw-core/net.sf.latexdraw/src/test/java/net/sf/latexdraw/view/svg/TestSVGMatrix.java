package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.parsers.svg.SVGMatrix;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestSVGMatrix {
	SVGMatrix m;

	@Before
	public void setUp() throws Exception {
		m = new SVGMatrix();
	}

	@Test
	public void testConstructor() {
		assertEquals(1d, m.getA(), 0.0001);
		assertEquals(1d, m.getD(), 0.0001);
		assertEquals(0d, m.getE(), 0.0001);
		assertEquals(0d, m.getB(), 0.0001);
		assertEquals(0d, m.getC(), 0.0001);
		assertEquals(0d, m.getF(), 0.0001);
	}

	@Test
	public void testInitMatrix() {
		m.initMatrix();
		assertEquals(1d, m.getA(), 0.0001);
		assertEquals(1d, m.getD(), 0.0001);
		assertEquals(0d, m.getE(), 0.0001);
		assertEquals(0d, m.getB(), 0.0001);
		assertEquals(0d, m.getC(), 0.0001);
		assertEquals(0d, m.getF(), 0.0001);
	}

	@Test
	public void testRotate() {
		final double angle = 0.14;
		m.rotate(angle);
		assertEquals(Math.cos(angle), m.getA(), 0.0001);
		assertEquals(Math.sin(angle), m.getB(), 0.0001);
		assertEquals(-Math.sin(angle), m.getC(), 0.0001);
		assertEquals(Math.cos(angle), m.getD(), 0.0001);
		assertEquals(0d, m.getE(), 0.0001);
		assertEquals(0d, m.getF(), 0.0001);
	}

	@Test
	public void testTranslate() {
		final double tx = 1.4;
		final double ty = -0.4;
		m.translate(tx, ty);
		assertEquals(1d, m.getA(), 0.0001);
		assertEquals(0d, m.getB(), 0.0001);
		assertEquals(0d, m.getC(), 0.0001);
		assertEquals(1d, m.getD(), 0.0001);
		assertEquals(tx, m.getE(), 0.0001);
		assertEquals(ty, m.getF(), 0.0001);
	}

	@Test
	public void testScaleNonUniform() {
		final double sx = 5.877423;
		final double sy = -0.04;
		m.scaleNonUniform(sx, sy);
		assertEquals(sx, m.getA(), 0.0001);
		assertEquals(0d, m.getB(), 0.0001);
		assertEquals(0d, m.getC(), 0.0001);
		assertEquals(sy, m.getD(), 0.0001);
		assertEquals(0d, m.getE(), 0.0001);
		assertEquals(0d, m.getF(), 0.0001);
	}

	@Test
	public void testScale() {
		final double s = 5.877423;
		m.scale(s);
		assertEquals(s, m.getA(), 0.0001);
		assertEquals(0d, m.getB(), 0.0001);
		assertEquals(0d, m.getC(), 0.0001);
		assertEquals(s, m.getD(), 0.0001);
		assertEquals(0d, m.getE(), 0.0001);
		assertEquals(0d, m.getF(), 0.0001);
	}

	@Test
	public void testSkewX() {
		final double angle = 1.92837;
		m.skewX(angle);
		assertEquals(1d, m.getA(), 0.0001);
		assertEquals(0d, m.getB(), 0.0001);
		assertEquals(Math.tan(angle), m.getC(), 0.0001);
		assertEquals(1d, m.getD(), 0.0001);
		assertEquals(0d, m.getE(), 0.0001);
		assertEquals(0d, m.getF(), 0.0001);
	}

	@Test
	public void testSkewY() {
		final double angle = -7.7283;
		m.skewY(angle);
		assertEquals(1d, m.getA(), 0.0001);
		assertEquals(Math.tan(angle), m.getB(), 0.0001);
		assertEquals(0d, m.getC(), 0.0001);
		assertEquals(1d, m.getD(), 0.0001);
		assertEquals(0d, m.getE(), 0.0001);
		assertEquals(0d, m.getF(), 0.0001);
	}

	@Test
	public void testSetMatrix() {
		m.setMatrix(2, 3, 4, 5, 6, 7);
		assertEquals(2d, m.getA(), 0.0001);
		assertEquals(3d, m.getB(), 0.0001);
		assertEquals(4d, m.getC(), 0.0001);
		assertEquals(5d, m.getD(), 0.0001);
		assertEquals(6d, m.getE(), 0.0001);
		assertEquals(7d, m.getF(), 0.0001);
	}

	@Test
	public void testMultiply() {
		SVGMatrix m2 = new SVGMatrix();
		SVGMatrix m3;
		m.setMatrix(1, 2, 3, 4, 5, 6);
		m2.setMatrix(7, 8, 9, 10, 11, 12);
		m3 = m.multiply(m2);
		assertEquals(31d, m3.getA(), 0.0001);
		assertEquals(46d, m3.getB(), 0.0001);
		assertEquals(39d, m3.getC(), 0.0001);
		assertEquals(58d, m3.getD(), 0.0001);
		assertEquals(52d, m3.getE(), 0.0001);
		assertEquals(76d, m3.getF(), 0.0001);
	}
}
