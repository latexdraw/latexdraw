package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.parsers.svg.MalformedSVGDocument;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGElements;
import net.sf.latexdraw.parsers.svg.SVGImageElement;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class TestSVGImageElement extends AbstractTestSVGElement {
	@Test(expected = NullPointerException.class)
	public void testContructorNULLEmpty() {
		new SVGImageElement(null, "");
	}

	@Test(expected = MalformedSVGDocument.class)
	public void testContructorNodeNULL() throws MalformedSVGDocument {
		new SVGImageElement(node, null);
	}

	@Test(expected = MalformedSVGDocument.class)
	public void testContructorNodeInvalidNULL() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "dsd");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "dsd");
		new SVGImageElement(node, null);
	}

	@Test(expected = MalformedSVGDocument.class)
	public void testContructorNodeInvalid2NULL() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "1");
		new SVGImageElement(node, null);
	}

	@Test(expected = MalformedSVGDocument.class)
	public void testContructorNodeInvalid3NULL() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "-1");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "10");
		new SVGImageElement(node, null);
	}

	@Test(expected = MalformedSVGDocument.class)
	public void testContructorNodeInvalid4NULL() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "-1");
		new SVGImageElement(node, null);
	}

	@Test
	public void testContructorOK() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		new SVGImageElement(node, null);
	}

	@Test
	public void testGetHeight() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		SVGImageElement e = new SVGImageElement(node, null);
		assertEquals(20d, e.getHeight(), 0.0001);
	}

	@Test
	public void testGetWidth() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		SVGImageElement e = new SVGImageElement(node, null);
		assertEquals(10d, e.getWidth(), 0.0001);
	}

	@Test
	public void testGetURINULL() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		SVGImageElement e = new SVGImageElement(node, null);
		assertNull(e.getURI());
	}

	@Test
	public void testGetURIxlink() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		node.setAttribute("xlink:href", "/dir/file");
		SVGImageElement e = new SVGImageElement(node, null);
		assertEquals("/dir/file", e.getURI());
	}

	@Test
	public void testEnableRenderingNULL() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		SVGImageElement e = new SVGImageElement(node, null);
		assertFalse(e.enableRendering());
	}

	@Test
	public void testEnableRenderingBadxlink() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		node.setAttribute("xlink:href", "/rerzerojcsf/dsqdsdfgdre");
		SVGImageElement e = new SVGImageElement(node, null);
		assertFalse(e.enableRendering());
	}

	@Test
	public void testEnableRenderingBadSVGWIDTH() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "0");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		SVGImageElement e = new SVGImageElement(node, null);
		assertFalse(e.enableRendering());
	}

	@Test
	public void testEnableRenderingBadSVGHEIGHT() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "0");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "0");
		SVGImageElement e = new SVGImageElement(node, null);
		assertFalse(e.enableRendering());
	}

	@Test
	public void testEnableRenderingBadSVGHEIGHTOKSVGWIDTH() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "0");
		node.setAttribute(SVGAttributes.SVG_WIDTH, "20");
		SVGImageElement e = new SVGImageElement(node, null);
		assertFalse(e.enableRendering());
	}

	@Test
	public void testGetY0() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		SVGImageElement e = new SVGImageElement(node, null);
		assertEquals(0d, e.getY(), 0.0001);
	}

	@Test
	public void testGetY1() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		node.setAttribute(SVGAttributes.SVG_Y, "1");
		SVGImageElement e = new SVGImageElement(node, null);
		assertEquals(1d, e.getY(), 0.0001);
	}

	@Test
	public void testGetX0() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		SVGImageElement e = new SVGImageElement(node, null);
		assertEquals(0.0001, e.getX(), 0.0001);
	}

	@Test
	public void testGetX1() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		node.setAttribute(SVGAttributes.SVG_X, "1");
		SVGImageElement e = new SVGImageElement(node, null);
		assertEquals(1d, e.getX(), 0.0001);
	}

	@Override
	public String getNameNode() {
		return SVGElements.SVG_IMAGE;
	}
}
