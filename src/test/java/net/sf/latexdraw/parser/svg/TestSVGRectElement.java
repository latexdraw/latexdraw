package net.sf.latexdraw.parser.svg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSVGRectElement extends TestBaseSVGElement {
	@Test
	public void testContructorFail1() {
		assertThrows(IllegalArgumentException.class, () -> new SVGRectElement(null, null));
	}

	@Test
	public void testContructorFail2() {
		assertThrows(IllegalArgumentException.class, () -> new SVGRectElement(node, null));
	}

	@Test
	public void testContructorFail3() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "dsd");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "dsd");
		assertThrows(IllegalArgumentException.class, () -> new SVGRectElement(node, null));
	}

	@Test
	public void testContructorFail4() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "1");
		assertThrows(IllegalArgumentException.class, () -> new SVGRectElement(node, null));
	}

	@Test
	public void testContructorFail5() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "-1");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "10");
		assertThrows(IllegalArgumentException.class, () -> new SVGRectElement(node, null));
	}

	@Test
	public void testContructorFail6() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "-1");
		assertThrows(IllegalArgumentException.class, () -> new SVGRectElement(node, null));
	}

	@Test
	public void testContructor() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		new SVGRectElement(node, null);
	}

	@Test
	public void testEnableRenderingKO00() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "0");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "0");
		final SVGRectElement e = new SVGRectElement(node, null);
		assertFalse(e.enableRendering());
	}

	@Test
	public void testEnableRenderingKO00100() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "0");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "0");
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "0");
		final SVGRectElement e = new SVGRectElement(node, null);
		assertFalse(e.enableRendering());
	}

	@Test
	public void testEnableRenderingKO00010() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "0");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "0");
		node.setAttribute(SVGAttributes.SVG_WIDTH, "0");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "10");
		final SVGRectElement e = new SVGRectElement(node, null);
		assertFalse(e.enableRendering());
	}

	@Test
	public void testEnableRenderingOK() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "0");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "0");
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "10");
		final SVGRectElement e = new SVGRectElement(node, null);
		assertTrue(e.enableRendering());
	}

	@Test
	public void testGetHeight() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		final SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(20d, r.getHeight(), 0.0001);
	}

	@Test
	public void testGetWidth() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		final SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(10d, r.getWidth(), 0.0001);
	}

	@Test
	public void testGetRy0() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		final SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(0d, r.getRy(), 0.0001);
	}

	@Test
	public void testGetRy() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		node.setAttribute(SVGAttributes.SVG_RY, "1");
		final SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(1d, r.getRy(), 0.0001);
	}

	@Test
	public void testGetRx0() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		final SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(0d, r.getRx(), 0.0001);
	}

	@Test
	public void testGetRx() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		node.setAttribute(SVGAttributes.SVG_RX, "1");
		final SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(1d, r.getRx(), 0.0001);
	}

	@Test
	public void testGetY0() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		final SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(0d, r.getY(), 0.0001);
	}

	@Test
	public void testGetY() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		node.setAttribute(SVGAttributes.SVG_Y, "1");
		final SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(1d, r.getY(), 0.0001);
	}

	@Test
	public void testGetX0() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		final SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(0d, r.getX(), 0.0001);
	}

	@Test
	public void testGetX() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		node.setAttribute(SVGAttributes.SVG_X, "1");
		final SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(1d, r.getX(), 0.0001);
	}

	@Override
	public String getNameNode() {
		return SVGElements.SVG_RECT;
	}
}
