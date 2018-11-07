package net.sf.latexdraw.parser.svg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSVGAttr {
	SVGElement node;

	@BeforeEach
	void setUp() {
		final SVGDocument doc = new SVGDocument();
		node = (SVGElement) doc.createElement("tag1");
	}

	@Test
	void testConstructorFail1() {
		assertThrows(NullPointerException.class, () -> new SVGAttr(null, null, null));
	}

	@Test
	void testConstructorFail2() {
		assertThrows(NullPointerException.class, () -> new SVGAttr("", "", null));
	}

	@Test
	void testConstructorFail3() {
		assertThrows(NullPointerException.class, () -> new SVGAttr(null, "", node));
	}

	@Test
	void testConstructorFail4() {
		assertThrows(NullPointerException.class, () -> new SVGAttr("", null, node));
	}

	@Test
	void testConstructorOK() {
		new SVGAttr("", "", node);
	}

	@Test
	void testGetName() {
		final SVGAttr attr = new SVGAttr("attrName", "", node);
		assertEquals("attrName", attr.getName());
	}

	@Test
	void testGetElementOwner() {
		final SVGAttr attr = new SVGAttr("", "", node);
		assertEquals(node, attr.getOwnerElement());
	}

	@Test
	void testGetValue() {
		final SVGAttr attr = new SVGAttr("", "attrValue", node);
		assertEquals("attrValue", attr.getValue());
	}

	@Test
	void testIsIdOK() {
		final SVGAttr attr = new SVGAttr("id", "", node);
		assertTrue(attr.isId());
	}

	@Test
	void testIsIdKO() {
		final SVGAttr attr = new SVGAttr("", "", node);
		assertFalse(attr.isId());
	}

	@Test
	void testSetValueFail() {
		final SVGAttr attr = new SVGAttr("", "", node);
		assertThrows(DOMException.class, () -> attr.setValue(null));
	}

	@Test
	void testSetValue() {
		final SVGAttr attr = new SVGAttr("", "", node);
		attr.setValue("val");
		assertEquals("val", attr.getValue());
	}

	@Test
	void testCloneNode() {
		final SVGAttr attr = new SVGAttr("n", "v", node);
		final SVGAttr attr2 = (SVGAttr) attr.cloneNode(false);

		assertNotNull(attr2);
		assertEquals(attr.getName(), attr2.getName());
		assertEquals(attr.getValue(), attr2.getValue());
		assertEquals(attr.getOwnerElement(), attr2.getOwnerElement());
	}

	@Test
	void testGetNodeName() {
		final SVGAttr attr = new SVGAttr("attrNodeName", "", node);
		assertEquals("attrNodeName", attr.getNodeName());
	}

	@Test
	void testGetNodeType() {
		final SVGAttr attr = new SVGAttr("", "", node);
		assertEquals(Node.ATTRIBUTE_NODE, attr.getNodeType());
	}

	@Test
	void testGetNodeValue() {
		final SVGAttr attr = new SVGAttr("", "attrValue", node);
		assertEquals("attrValue", attr.getNodeValue());
	}

	@Test
	void testGetParentNode() {
		final SVGAttr attr = new SVGAttr("", "", node);
		assertEquals(node, attr.getParentNode());
	}

	@Test
	void testHasAttribute() {
		final SVGAttr attr = new SVGAttr("", "", node);
		assertFalse(attr.hasAttributes());
	}

	@Test
	void testChildNodes() {
		final SVGAttr attr = new SVGAttr("", "", node);
		assertFalse(attr.hasChildNodes());
	}

	@Test
	void testIsEqualNodeOK() {
		final SVGAttr attr = new SVGAttr("n", "v", node);
		final SVGAttr attr2 = (SVGAttr) attr.cloneNode(false);
		assertTrue(attr.isEqualNode(attr2));
	}

	@Test
	void testIsEqualNodeKO1() {
		final SVGAttr attr = new SVGAttr("n", "v", node);
		final SVGAttr attr2 = new SVGAttr("n", "", node);
		assertFalse(attr.isEqualNode(attr2));
	}

	@Test
	void testIsEqualNodeKO2() {
		final SVGAttr attr = new SVGAttr("n", "v", node);
		final SVGAttr attr2 = new SVGAttr("", "v", node);
		assertFalse(attr.isEqualNode(attr2));
	}

	@Test
	void testIsEqualNodeKONULL() {
		final SVGAttr attr = new SVGAttr("n", "v", node);
		assertFalse(attr.isEqualNode(null));
	}

	@Test
	void testIsSameNodeSelf() {
		final SVGAttr attr = new SVGAttr("n", "v", node);
		assertTrue(attr.isSameNode(attr));
	}

	@Test
	void testIsSameNodeKONULL() {
		final SVGAttr attr = new SVGAttr("n", "v", node);
		assertFalse(attr.isSameNode(null));
	}

	@Test
	void testIsSameNodeKOClone() {
		final SVGAttr attr = new SVGAttr("n", "v", node);
		final SVGAttr attr2 = (SVGAttr) attr.cloneNode(false);
		assertFalse(attr.isSameNode(attr2));
	}

	@Test
	void testSetNodeValueFail() {
		final SVGAttr attr = new SVGAttr("", "", node);
		assertThrows(DOMException.class, () -> attr.setNodeValue(null));
	}

	@Test
	void testSetNodeValue() {
		final SVGAttr attr = new SVGAttr("", "", node);
		attr.setNodeValue("val");
		assertEquals("val", attr.getValue());
		assertEquals("val", attr.getNodeValue());
	}

	@Test
	void testGetPrefixNULL() {
		final SVGAttr attr = new SVGAttr("", "", node);
		assertNull(attr.getPrefix());
	}

	@Test
	void testGetPrefixOK() {
		final SVGAttr attr = new SVGAttr("pref:", "", node);
		assertEquals("pref", attr.getPrefix());
	}

	@Test
	void testGetPrefixEmpty() {
		final SVGAttr attr = new SVGAttr(":", "", node);
		assertEquals("", attr.getPrefix());
	}

	@Test
	void testGetNamespaceURINULL() {
		final SVGAttr attr = new SVGAttr("pref:n", "", node);
		assertNull(attr.getNamespaceURI());
	}

	@Test
	void testGetNamespaceURIOK() {
		final SVGAttr attr = new SVGAttr("pref:n", "", node);
		final SVGElement elt = (SVGElement) node.getOwnerDocument().createElement("tag2");
		elt.setAttribute("xmlns:pref", "namespace");
		elt.appendChild(node);
		assertEquals("namespace", attr.getNamespaceURI());
	}

	@Test
	void testLookupNamespaceURINULL() {
		final SVGAttr attr = new SVGAttr("pref:n", "", node);
		assertNull(attr.lookupNamespaceURI(null));
	}

	@Test
	void testLookupNamespaceURINotFound() {
		final SVGAttr attr = new SVGAttr("pref:n", "", node);
		assertNull(attr.lookupNamespaceURI("a"));
	}

	@Test
	void testLookupNamespaceURIOK() {
		final SVGAttr attr = new SVGAttr("pref:n", "", node);
		final SVGElement elt = (SVGElement) node.getOwnerDocument().createElement("tag2");
		elt.setAttribute("xmlns:pref", "namespace");
		elt.appendChild(node);
		assertEquals("namespace", attr.lookupNamespaceURI("pref"));
	}

	@Test
	void testGetLocalName() {
		final SVGAttr attr = new SVGAttr("attrNodeName", "", node);
		assertEquals("attrNodeName", attr.getLocalName());
	}

	@Test
	void testUselessMethods() {
		final SVGAttr attr = new SVGAttr("", "", node);
		assertNull(attr.removeChild(null));
		assertNull(attr.insertBefore(null, null));
		assertNull(attr.getAttributes());
		assertNull(attr.getFirstChild());
		assertNull(attr.getLastChild());
		assertNull(attr.appendChild(null));
	}
}
