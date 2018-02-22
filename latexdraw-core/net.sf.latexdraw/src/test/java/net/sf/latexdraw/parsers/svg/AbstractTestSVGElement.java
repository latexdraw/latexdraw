package net.sf.latexdraw.parsers.svg;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.DOMException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public abstract class AbstractTestSVGElement {
	protected SVGElement node;
	protected SVGDocument doc = new SVGDocument();

	public abstract String getNameNode();

	@Before
	public void setUp() throws Exception {
		doc = new SVGDocument();
		node = (SVGElement)doc.createElement(getNameNode());
	}

	@Test
	public void testGetStroke() {
		node.removeAttribute(SVGAttributes.SVG_STROKE);
		assertNull(node.getStroke());

		node.setAttribute(SVGAttributes.SVG_STROKE, CSSColors.CSS_ALICEBLUE_NAME);
		assertEquals(CSSColors.CSS_ALICEBLUE_RGB_VALUE, node.getStroke());
		node.setAttribute(SVGAttributes.SVG_STROKE, "testtest");
		assertNull(node.getStroke());
	}

	@Test
	public void testGetNodeName() {
		assertEquals(getNameNode(), node.getNodeName());
	}

	@Test
	public void testSetNodeName() {
		node.setNodeName("test");
		assertEquals("test", node.getNodeName());
		node.setNodeName(getNameNode());
		assertEquals(getNameNode(), node.getNodeName());
	}

	@Test
	public void testSetParent() {
		SVGElement elt = (SVGElement)doc.createElement("elt");

		node.setParent(null);
		assertNull(node.getParent());
		node.setParent(elt);
		assertEquals(elt, node.getParent());
		SVGNodeList list = elt.getChildren(getNameNode());
		assertEquals(1, list.getLength());
		node.setParent(null);
		list = elt.getChildren(getNameNode());
		assertEquals(0, list.getLength());
	}

	@Test
	public void testGetAttribute() {
		assertNull(node.getAttribute(null));
		assertNull(node.getAttribute(""));
		node.setAttribute("testAttr", "valAttr");
		assertEquals("valAttr", node.getAttribute("testAttr"));
	}

	@Test
	public void testGetAttributeNode() {
		assertNull(node.getAttributeNode(null));
		assertNull(node.getAttributeNode(""));
		node.setAttribute("testAttr2", "valAttr2");
		assertEquals("valAttr2", node.getAttributeNode("testAttr2").getNodeValue());
	}

	@Test
	public void testGetTagName() {
		assertEquals(node.getNodeName(), node.getTagName());
	}

	@Test(expected = DOMException.class)
	public void testAppendChildNull() {
		node.appendChild(null);
	}

	@Test(expected = DOMException.class)
	public void testAppendChildEmpty() {
		node.appendChild(new SVGAttr("", "", node));
	}

	@Test
	public void testAppendChild() {
		SVGElement elt = (SVGElement)doc.createElement("eltAppendChild");
		assertEquals(elt, node.appendChild(elt));
		assertEquals(1, node.getChildren("eltAppendChild").getLength());
	}
}
