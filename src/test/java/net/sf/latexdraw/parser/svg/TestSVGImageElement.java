package net.sf.latexdraw.parser.svg;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSVGImageElement extends TestBaseSVGElement {
	@Test
	void testContructorNULLEmpty() {
		assertThrows(NullPointerException.class, () -> new SVGImageElement(null, ""));
	}

	@Test
	void testContructorNodeNULL() {
		assertThrows(IllegalArgumentException.class, () -> new SVGImageElement(node, null));
	}

	@Test
	void testContructorNodeInvalidNULL() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "dsd");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "dsd");
		assertThrows(IllegalArgumentException.class, () -> new SVGImageElement(node, null));
	}

	@Test
	void testContructorNodeInvalid2NULL() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "1");
		assertThrows(IllegalArgumentException.class, () -> new SVGImageElement(node, null));
	}

	@Test
	void testContructorNodeInvalid3NULL() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "-1");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "10");
		assertThrows(IllegalArgumentException.class, () -> new SVGImageElement(node, null));
	}

	@Test
	void testContructorNodeInvalid4NULL() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "-1");
		assertThrows(IllegalArgumentException.class, () -> new SVGImageElement(node, null));
	}

	@Test
	void testContructorOK() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		new SVGImageElement(node, null);
	}

	@Test
	void testGetHeight() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		final SVGImageElement e = new SVGImageElement(node, null);
		assertEquals(20d, e.getHeight(), 0.0001);
	}

	@Test
	void testGetWidth() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		final SVGImageElement e = new SVGImageElement(node, null);
		assertEquals(10d, e.getWidth(), 0.0001);
	}

	@Test
	void testGetURINULL() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		final SVGImageElement e = new SVGImageElement(node, null);
		assertTrue(e.getURI().isEmpty());
	}

	@Test
	void testGetURIxlink() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		node.setAttribute("xlink:href", "/dir/file");
		final SVGImageElement e = new SVGImageElement(node, null);
		assertEquals("/dir/file", e.getURI());
	}

	@Test
	void testEnableRenderingNULL() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		final SVGImageElement e = new SVGImageElement(node, null);
		assertFalse(e.enableRendering());
	}

	@Test
	void testEnableRenderingBadxlink() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		node.setAttribute("xlink:href", "/rerzerojcsf/dsqdsdfgdre");
		final SVGImageElement e = new SVGImageElement(node, null);
		assertFalse(e.enableRendering());
	}

	@Test
	void testEnableRenderingBadSVGWIDTH() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "0");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		final SVGImageElement e = new SVGImageElement(node, null);
		assertFalse(e.enableRendering());
	}

	@Test
	void testEnableRenderingBadSVGHEIGHT() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "0");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "0");
		final SVGImageElement e = new SVGImageElement(node, null);
		assertFalse(e.enableRendering());
	}

	@Test
	void testEnableRenderingBadSVGHEIGHTOKSVGWIDTH() {
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "0");
		node.setAttribute(SVGAttributes.SVG_WIDTH, "20");
		final SVGImageElement e = new SVGImageElement(node, null);
		assertFalse(e.enableRendering());
	}

	@Test
	void testGetY0() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		final SVGImageElement e = new SVGImageElement(node, null);
		assertEquals(0d, e.getY(), 0.0001);
	}

	@Test
	void testGetY1() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		node.setAttribute(SVGAttributes.SVG_Y, "1");
		final SVGImageElement e = new SVGImageElement(node, null);
		assertEquals(1d, e.getY(), 0.0001);
	}

	@Test
	void testGetX0() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		final SVGImageElement e = new SVGImageElement(node, null);
		assertEquals(0.0001, e.getX(), 0.0001);
	}

	@Test
	void testGetX1() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		node.setAttribute(SVGAttributes.SVG_X, "1");
		final SVGImageElement e = new SVGImageElement(node, null);
		assertEquals(1d, e.getX(), 0.0001);
	}

	@Override
	public String getNameNode() {
		return SVGElements.SVG_IMAGE;
	}
}
