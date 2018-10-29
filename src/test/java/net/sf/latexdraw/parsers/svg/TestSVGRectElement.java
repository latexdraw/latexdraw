package net.sf.latexdraw.parsers.svg;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestSVGRectElement extends TestBaseSVGElement {
	@Test(expected = IllegalArgumentException.class)
	public void testContructorFail1() throws MalformedSVGDocument {
		new SVGRectElement(null, null);
	}

	@Test(expected = MalformedSVGDocument.class)
	public void testContructorFail2() throws MalformedSVGDocument {
		new SVGRectElement(node, null);
	}

	@Test(expected = MalformedSVGDocument.class)
	public void testContructorFail3() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "dsd");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "dsd");
		new SVGRectElement(node, null);
	}

	@Test(expected = MalformedSVGDocument.class)
	public void testContructorFail4() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "1");
		new SVGRectElement(node, null);
	}

	@Test(expected = MalformedSVGDocument.class)
	public void testContructorFail5() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "-1");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "10");
		new SVGRectElement(node, null);
	}

	@Test(expected = MalformedSVGDocument.class)
	public void testContructorFail6() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "-1");
		new SVGRectElement(node, null);
	}

	@Test
	public void testContructor() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		new SVGRectElement(node, null);
	}

	@Test
	public void testEnableRenderingKO00() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "0");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "0");
		final SVGRectElement e = new SVGRectElement(node, null);
		assertFalse(e.enableRendering());
	}

	@Test
	public void testEnableRenderingKO00100() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "0");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "0");
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "0");
		final SVGRectElement e = new SVGRectElement(node, null);
		assertFalse(e.enableRendering());
	}

	@Test
	public void testEnableRenderingKO00010() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "0");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "0");
		node.setAttribute(SVGAttributes.SVG_WIDTH, "0");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "10");
		final SVGRectElement e = new SVGRectElement(node, null);
		assertFalse(e.enableRendering());
	}

	@Test
	public void testEnableRenderingOK() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "0");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "0");
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "10");
		final SVGRectElement e = new SVGRectElement(node, null);
		assertTrue(e.enableRendering());
	}

	@Test
	public void testGetHeight() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		final SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(20d, r.getHeight(), 0.0001);
	}

	@Test
	public void testGetWidth() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		final SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(10d, r.getWidth(), 0.0001);
	}

	@Test
	public void testGetRy0() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		final SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(0d, r.getRy(), 0.0001);
	}

	@Test
	public void testGetRy() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		node.setAttribute(SVGAttributes.SVG_RY, "1");
		final SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(1d, r.getRy(), 0.0001);
	}

	@Test
	public void testGetRx0() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		final SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(0d, r.getRx(), 0.0001);
	}

	@Test
	public void testGetRx() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		node.setAttribute(SVGAttributes.SVG_RX, "1");
		final SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(1d, r.getRx(), 0.0001);
	}

	@Test
	public void testGetY0() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		final SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(0d, r.getY(), 0.0001);
	}

	@Test
	public void testGetY() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		node.setAttribute(SVGAttributes.SVG_Y, "1");
		final SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(1d, r.getY(), 0.0001);
	}

	@Test
	public void testGetX0() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		final SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(0d, r.getX(), 0.0001);
	}

	@Test
	public void testGetX() throws MalformedSVGDocument {
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
