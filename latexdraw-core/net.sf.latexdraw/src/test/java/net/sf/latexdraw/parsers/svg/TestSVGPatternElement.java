package net.sf.latexdraw.parsers.svg;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestSVGPatternElement extends AbstractTestSVGElement {
	@Test(expected = IllegalArgumentException.class)
	public void testContructorFail() throws MalformedSVGDocument {
		new SVGPatternElement(null, null);
	}

	@Test
	public void testContructorOK() throws MalformedSVGDocument {
		new SVGPatternElement(node, null);
	}

	@Test
	public void testGetHeight0() throws MalformedSVGDocument {
		SVGPatternElement e = new SVGPatternElement(node, null);
		assertEquals(0d, e.getHeight(), 0.0001);
	}

	@Test
	public void testGetHeight() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		SVGPatternElement e = new SVGPatternElement(node, null);
		assertEquals(20d, e.getHeight(), 0.0001);
	}

	@Test
	public void testGetWidth0() throws MalformedSVGDocument {
		SVGPatternElement e = new SVGPatternElement(node, null);
		assertEquals(0d, e.getWidth(), 0.0001);
	}

	@Test
	public void testGetWidth() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "30");
		SVGPatternElement e = new SVGPatternElement(node, null);
		assertEquals(30d, e.getWidth(), 0.0001);
	}

	@Test
	public void testGetPatternUnitsdefault() throws MalformedSVGDocument {
		SVGPatternElement e = new SVGPatternElement(node, null);
		assertEquals(SVGAttributes.SVG_UNITS_VALUE_OBJ, e.getPatternUnits());
	}

	@Test
	public void testGetPatternUnitsUSR() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_PATTERN_UNITS, SVGAttributes.SVG_UNITS_VALUE_USR);
		SVGPatternElement e = new SVGPatternElement(node, null);
		assertEquals(SVGAttributes.SVG_UNITS_VALUE_USR, e.getPatternUnits());
	}

	@Test
	public void testGetPatternUnitsOBJ() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_PATTERN_UNITS, SVGAttributes.SVG_UNITS_VALUE_OBJ);
		SVGPatternElement e = new SVGPatternElement(node, null);
		assertEquals(SVGAttributes.SVG_UNITS_VALUE_OBJ, e.getPatternUnits());
	}

	@Test
	public void testGetPatternContentUnitsdefault() throws MalformedSVGDocument {
		SVGPatternElement e = new SVGPatternElement(node, null);
		assertEquals(SVGAttributes.SVG_UNITS_VALUE_USR, e.getPatternContentUnits());
	}

	@Test
	public void testGetPatternContentUnitsUSR() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_PATTERN_CONTENTS_UNITS, SVGAttributes.SVG_UNITS_VALUE_USR);
		SVGPatternElement e = new SVGPatternElement(node, null);
		assertEquals(SVGAttributes.SVG_UNITS_VALUE_USR, e.getPatternContentUnits());
	}

	@Test
	public void testGetPatternContentUnitsOBJ() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_PATTERN_CONTENTS_UNITS, SVGAttributes.SVG_UNITS_VALUE_OBJ);
		SVGPatternElement e = new SVGPatternElement(node, null);
		assertEquals(SVGAttributes.SVG_UNITS_VALUE_OBJ, e.getPatternContentUnits());
	}

	@Test
	public void testEnableRenderingKO00() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "0");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "0");
		SVGPatternElement e = new SVGPatternElement(node, null);
		assertFalse(e.enableRendering());
	}

	@Test
	public void testEnableRenderingKO100() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "0");
		SVGPatternElement e = new SVGPatternElement(node, null);
		assertFalse(e.enableRendering());
	}

	@Test
	public void testEnableRenderingKO010() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "0");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "10");
		SVGPatternElement e = new SVGPatternElement(node, null);
		assertFalse(e.enableRendering());
	}

	@Test
	public void testEnableRenderingOK() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "10");
		SVGPatternElement e = new SVGPatternElement(node, null);
		assertTrue(e.enableRendering());
	}

	@Test
	public void testGetY0() throws MalformedSVGDocument {
		SVGPatternElement e = new SVGPatternElement(node, null);
		assertEquals(0d, e.getY(), 0.0001);
	}

	@Test
	public void testGetY() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_Y, "1");
		SVGPatternElement e = new SVGPatternElement(node, null);
		assertEquals(1d, e.getY(), 0.0001);
	}

	@Test
	public void testGetX0() throws MalformedSVGDocument {
		SVGPatternElement e = new SVGPatternElement(node, null);
		assertEquals(0d, e.getX(), 0.0001);
	}

	@Test
	public void testGetX() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_X, "2");
		SVGPatternElement e = new SVGPatternElement(node, null);
		assertEquals(2d, e.getX(), 0.0001);
	}

	@Override
	public String getNameNode() {
		return SVGElements.SVG_PATTERN;
	}
}
