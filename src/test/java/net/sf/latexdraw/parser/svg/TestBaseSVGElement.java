package net.sf.latexdraw.parser.svg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.DOMException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class TestBaseSVGElement {
	protected SVGElement node;
	protected SVGDocument doc = new SVGDocument();

	public abstract String getNameNode();

	@BeforeEach
	void setUp() {
		doc = new SVGDocument();
		node = (SVGElement) doc.createElement(getNameNode());
	}

	@Test
	void testGetStroke() {
		node.removeAttribute(SVGAttributes.SVG_STROKE);
		assertTrue(node.getStroke().isEmpty());
	}

	@Test
	void testGetNodeName() {
		assertEquals(getNameNode(), node.getNodeName());
	}

	@Test
	void testSetNodeName() {
		node.setNodeName("test");
		assertEquals("test", node.getNodeName());
		node.setNodeName(getNameNode());
		assertEquals(getNameNode(), node.getNodeName());
	}

	@Test
	void testSetParent() {
		final SVGElement elt = (SVGElement) doc.createElement("elt");

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
	void testGetAttribute() {
		assertEquals("", node.getAttribute(null));
		assertEquals("", node.getAttribute(""));
		node.setAttribute("testAttr", "valAttr");
		assertEquals("valAttr", node.getAttribute("testAttr"));
	}

	@Test
	void testGetAttributeNode() {
		assertNull(node.getAttributeNode(null));
		assertNull(node.getAttributeNode(""));
		node.setAttribute("testAttr2", "valAttr2");
		assertEquals("valAttr2", node.getAttributeNode("testAttr2").getNodeValue());
	}

	@Test
	void testGetTagName() {
		assertEquals(node.getNodeName(), node.getTagName());
	}

	@Test
	void testAppendChildNull() {
		assertThrows(DOMException.class, () -> node.appendChild(null));
	}

	@Test
	void testAppendChildEmpty() {
		assertThrows(DOMException.class, () -> node.appendChild(new SVGAttr("", "", node)));
	}

	@Test
	void testAppendChild() {
		final SVGElement elt = (SVGElement) doc.createElement("eltAppendChild");
		assertEquals(elt, node.appendChild(elt));
		assertEquals(1, node.getChildren("eltAppendChild").getLength());
	}
}
