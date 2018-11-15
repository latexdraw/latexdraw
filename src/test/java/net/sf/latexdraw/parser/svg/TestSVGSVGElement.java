package net.sf.latexdraw.parser.svg;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSVGSVGElement extends TestBaseSVGElement {
	SVGSVGElement e;

	@BeforeEach
	void setUpSVGElt() {
		e = new SVGSVGElement(node, null);
	}

	@Test
	void testContructorFail() {
		assertThrows(IllegalArgumentException.class, () -> new SVGSVGElement(null));
	}

	@Test
	void testContructorOK() {
		new SVGSVGElement(node, null);
	}

	@Test
	void testGetDefsNULL() {
		assertNull(e.getDefs());
	}

	@Test
	void testGetDefs() throws ParserConfigurationException {
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder builder = factory.newDocumentBuilder();
		final Document document = builder.newDocument();
		final Element n = document.createElement(SVGElements.SVG_SVG);
		n.setAttribute("xmlns", SVGDocument.SVG_NAMESPACE);
		n.appendChild(document.createElement(SVGElements.SVG_DEFS));
		e = new SVGSVGElement(n, null);
		assertNotNull(e.getDefs());
	}

	@Test
	void testGetMetaNULL() {
		assertNull(e.getMeta());
	}

	@Test
	void testGetMeta() throws ParserConfigurationException {
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder builder = factory.newDocumentBuilder();
		final Document document = builder.newDocument();
		final Element n = document.createElement(SVGElements.SVG_SVG);
		n.setAttribute("xmlns", SVGDocument.SVG_NAMESPACE);
		n.appendChild(document.createElement(SVGElements.SVG_METADATA));
		e = new SVGSVGElement(n, null);
		assertNotNull(e.getMeta());
	}

	@Test
	void testGetHeight() {
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "200");
		final SVGSVGElement e = new SVGSVGElement(node, null);
		assertEquals(200d, e.getHeight(), 0.0001);
	}

	@Test
	void testEnableRenderingOK() {
		assertTrue(e.enableRendering());
	}

	@Test
	void testEnableRenderingKO0() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "-1");
		e = new SVGSVGElement(node, null);
		assertFalse(e.enableRendering());
	}

	@Test
	void testEnableRenderingKO00() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "-1");
		e = new SVGSVGElement(node, null);
		assertFalse(e.enableRendering());
	}

	@Test
	void testEnableRenderingOK1010() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "10");
		e = new SVGSVGElement(node, null);
		assertTrue(e.enableRendering());
	}

	@Test
	void testGetY0() {
		assertEquals(0d, e.getY(), 0.0001);
	}

	@Test
	void testGetY() {
		node.setAttribute(SVGAttributes.SVG_Y, "-10");
		e = new SVGSVGElement(node, null);
		assertEquals(-10d, e.getY(), 0.0001);
	}

	@Test
	void testGetX0() {
		assertEquals(0d, e.getX(), 0.0001);
	}

	@Test
	void testGetX() {
		node.setAttribute(SVGAttributes.SVG_X, "10");
		e = new SVGSVGElement(node, null);
		assertEquals(10d, e.getX(), 0.0001);
	}

	@Test
	void testGetWidth() {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "100");
		final SVGSVGElement e = new SVGSVGElement(node, null);
		assertEquals(100d, e.getWidth(), 0.0001);
	}

	@Test
	void testVersion() {
		node.setAttribute(SVGAttributes.SVG_VERSION, "1.1");
		final SVGSVGElement e = new SVGSVGElement(node, null);
		assertEquals("1.1", e.getVersion());
	}

	@Override
	public String getNameNode() {
		return SVGElements.SVG_SVG;
	}
}
