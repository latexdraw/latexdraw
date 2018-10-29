package net.sf.latexdraw.parsers.svg;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TestSVGAttr {
	SVGElement node;

	@Before
	public void setUp() {
		final SVGDocument doc = new SVGDocument();
		node = (SVGElement) doc.createElement("tag1");
	}

	@Test(expected = NullPointerException.class)
	public void testConstructorFail1() {
		new SVGAttr(null, null, null);
	}

	@Test(expected = NullPointerException.class)
	public void testConstructorFail2() {
		new SVGAttr("", "", null);
	}

	@Test(expected = NullPointerException.class)
	public void testConstructorFail3() {
		new SVGAttr(null, "", node);
	}

	@Test(expected = NullPointerException.class)
	public void testConstructorFail4() {
		new SVGAttr("", null, node);
	}

	@Test
	public void testConstructorOK() {
		new SVGAttr("", "", node);
	}

	@Test
	public void testGetName() {
		final SVGAttr attr = new SVGAttr("attrName", "", node);
		assertEquals("attrName", attr.getName());
	}

	@Test
	public void testGetElementOwner() {
		final SVGAttr attr = new SVGAttr("", "", node);
		assertEquals(node, attr.getOwnerElement());
	}

	@Test
	public void testGetValue() {
		final SVGAttr attr = new SVGAttr("", "attrValue", node);
		assertEquals("attrValue", attr.getValue());
	}

	@Test
	public void testIsIdOK() {
		final SVGAttr attr = new SVGAttr("id", "", node);
		assertTrue(attr.isId());
	}

	@Test
	public void testIsIdKO() {
		final SVGAttr attr = new SVGAttr("", "", node);
		assertFalse(attr.isId());
	}

	@Test(expected = DOMException.class)
	public void testSetValueFail() {
		final SVGAttr attr = new SVGAttr("", "", node);
		attr.setValue(null);
	}

	@Test
	public void testSetValue() {
		final SVGAttr attr = new SVGAttr("", "", node);
		attr.setValue("val");
		assertEquals("val", attr.getValue());
	}

	@Test
	public void testCloneNode() {
		final SVGAttr attr = new SVGAttr("n", "v", node);
		final SVGAttr attr2 = (SVGAttr) attr.cloneNode(false);

		assertNotNull(attr2);
		assertEquals(attr.getName(), attr2.getName());
		assertEquals(attr.getValue(), attr2.getValue());
		assertEquals(attr.getOwnerElement(), attr2.getOwnerElement());
	}

	@Test
	public void testGetNodeName() {
		final SVGAttr attr = new SVGAttr("attrNodeName", "", node);
		assertEquals("attrNodeName", attr.getNodeName());
	}

	@Test
	public void testGetNodeType() {
		final SVGAttr attr = new SVGAttr("", "", node);
		assertEquals(Node.ATTRIBUTE_NODE, attr.getNodeType());
	}

	@Test
	public void testGetNodeValue() {
		final SVGAttr attr = new SVGAttr("", "attrValue", node);
		assertEquals("attrValue", attr.getNodeValue());
	}

	@Test
	public void testGetParentNode() {
		final SVGAttr attr = new SVGAttr("", "", node);
		assertEquals(node, attr.getParentNode());
	}

	@Test
	public void testHasAttribute() {
		final SVGAttr attr = new SVGAttr("", "", node);
		assertFalse(attr.hasAttributes());
	}

	@Test
	public void testChildNodes() {
		final SVGAttr attr = new SVGAttr("", "", node);
		assertFalse(attr.hasChildNodes());
	}

	@Test
	public void testIsEqualNodeOK() {
		final SVGAttr attr = new SVGAttr("n", "v", node);
		final SVGAttr attr2 = (SVGAttr) attr.cloneNode(false);
		assertTrue(attr.isEqualNode(attr2));
	}

	@Test
	public void testIsEqualNodeKO1() {
		final SVGAttr attr = new SVGAttr("n", "v", node);
		final SVGAttr attr2 = new SVGAttr("n", "", node);
		assertFalse(attr.isEqualNode(attr2));
	}

	@Test
	public void testIsEqualNodeKO2() {
		final SVGAttr attr = new SVGAttr("n", "v", node);
		final SVGAttr attr2 = new SVGAttr("", "v", node);
		assertFalse(attr.isEqualNode(attr2));
	}

	@Test
	public void testIsEqualNodeKONULL() {
		final SVGAttr attr = new SVGAttr("n", "v", node);
		assertFalse(attr.isEqualNode(null));
	}

	@Test
	public void testIsSameNodeSelf() {
		final SVGAttr attr = new SVGAttr("n", "v", node);
		assertTrue(attr.isSameNode(attr));
	}

	@Test
	public void testIsSameNodeKONULL() {
		final SVGAttr attr = new SVGAttr("n", "v", node);
		assertFalse(attr.isSameNode(null));
	}

	@Test
	public void testIsSameNodeKOClone() {
		final SVGAttr attr = new SVGAttr("n", "v", node);
		final SVGAttr attr2 = (SVGAttr) attr.cloneNode(false);
		assertFalse(attr.isSameNode(attr2));
	}

	@Test(expected = DOMException.class)
	public void testSetNodeValueFail() {
		final SVGAttr attr = new SVGAttr("", "", node);
		attr.setNodeValue(null);
	}

	@Test
	public void testSetNodeValue() {
		final SVGAttr attr = new SVGAttr("", "", node);
		attr.setNodeValue("val");
		assertEquals("val", attr.getValue());
		assertEquals("val", attr.getNodeValue());
	}

	@Test
	public void testGetPrefixNULL() {
		final SVGAttr attr = new SVGAttr("", "", node);
		assertNull(attr.getPrefix());
	}

	@Test
	public void testGetPrefixOK() {
		final SVGAttr attr = new SVGAttr("pref:", "", node);
		assertEquals("pref", attr.getPrefix());
	}

	@Test
	public void testGetPrefixEmpty() {
		final SVGAttr attr = new SVGAttr(":", "", node);
		assertEquals("", attr.getPrefix());
	}

	@Test
	public void testGetNamespaceURINULL() {
		final SVGAttr attr = new SVGAttr("pref:n", "", node);
		assertNull(attr.getNamespaceURI());
	}

	@Test
	public void testGetNamespaceURIOK() {
		final SVGAttr attr = new SVGAttr("pref:n", "", node);
		final SVGElement elt = (SVGElement) node.getOwnerDocument().createElement("tag2");
		elt.setAttribute("xmlns:pref", "namespace");
		elt.appendChild(node);
		assertEquals("namespace", attr.getNamespaceURI());
	}

	@Test
	public void testLookupNamespaceURINULL() {
		final SVGAttr attr = new SVGAttr("pref:n", "", node);
		assertNull(attr.lookupNamespaceURI(null));
	}

	@Test
	public void testLookupNamespaceURINotFound() {
		final SVGAttr attr = new SVGAttr("pref:n", "", node);
		assertNull(attr.lookupNamespaceURI("a"));
	}

	@Test
	public void testLookupNamespaceURIOK() {
		final SVGAttr attr = new SVGAttr("pref:n", "", node);
		final SVGElement elt = (SVGElement) node.getOwnerDocument().createElement("tag2");
		elt.setAttribute("xmlns:pref", "namespace");
		elt.appendChild(node);
		assertEquals("namespace", attr.lookupNamespaceURI("pref"));
	}

	@Test
	public void testGetLocalName() {
		final SVGAttr attr = new SVGAttr("attrNodeName", "", node);
		assertEquals("attrNodeName", attr.getLocalName());
	}

	@Test
	public void testUselessMethods() {
		final SVGAttr attr = new SVGAttr("", "", node);
		assertNull(attr.removeChild(null));
		assertNull(attr.insertBefore(null, null));
		assertNull(attr.getAttributes());
		assertNull(attr.getFirstChild());
		assertNull(attr.getLastChild());
		assertNull(attr.appendChild(null));
	}
}
