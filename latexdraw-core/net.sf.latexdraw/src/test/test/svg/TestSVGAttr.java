package test.svg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.latexdraw.parsers.svg.SVGAttr;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

public class TestSVGAttr{
	protected SVGElement node;


	@Before
	public void setUp() {
		SVGDocument doc = new SVGDocument();
        node = (SVGElement)doc.createElement("tag1");
	}



	@SuppressWarnings("unused")
	@Test
	public void testConstructor() {
		try {
			new SVGAttr(null, null, null);
			fail();
		}
		catch(Exception e) { /* ok */ }

		try {
			new SVGAttr("", "", null);
			fail();
		}
		catch(Exception e) { /* ok */ }

		try {
			new SVGAttr(null, "", node);
			fail();
		}
		catch(Exception e) { /* ok */ }

		try {
			new SVGAttr("", null, node);
			fail();
		}
		catch(Exception e) { /* ok */ }

		new SVGAttr("", "", node);
	}


	@Test
	public void testGetName() {
		SVGAttr attr = new SVGAttr("attrName", "", node);
		assertEquals("attrName", attr.getName());
	}


	@Test
	public void testGetElementOwner() {
		SVGAttr attr = new SVGAttr("", "", node);
		assertEquals(node, attr.getOwnerElement());
	}



	@Test
	public void testGetValue() {
		SVGAttr attr = new SVGAttr("", "attrValue", node);
		assertEquals("attrValue", attr.getValue());
	}


	@Test
	public void testIsId() {
		SVGAttr attr = new SVGAttr("id", "", node);

		assertTrue(attr.isId());
		attr = new SVGAttr("", "", node);
		assertFalse(attr.isId());
	}


	@Test
	public void testSetValue() {
		SVGAttr attr = new SVGAttr("", "", node);

		try {
			attr.setValue(null);
			fail();
		}
		catch(DOMException e) { /* Ok */ }

		attr.setValue("val");
		assertEquals("val", attr.getValue());
	}


	@Test
	public void testCloneNode() {
		SVGAttr attr = new SVGAttr("n", "v", node);
		SVGAttr attr2 = (SVGAttr)attr.cloneNode(false);

		assertNotNull(attr2);
		assertEquals(attr.getName(), attr2.getName());
		assertEquals(attr.getValue(), attr2.getValue());
		assertEquals(attr.getOwnerElement(), attr2.getOwnerElement());
	}


	@Test
	public void testGetNodeName() {
		SVGAttr attr = new SVGAttr("attrNodeName", "", node);
		assertEquals("attrNodeName", attr.getNodeName());
	}


	@Test
	public void testGetNodeType() {
		SVGAttr attr = new SVGAttr("", "", node);
		assertEquals(Node.ATTRIBUTE_NODE, attr.getNodeType());
	}


	@Test
	public void testGetNodeValue() {
		SVGAttr attr = new SVGAttr("", "attrValue", node);
		assertEquals("attrValue", attr.getNodeValue());
	}


	@Test
	public void testGetParentNode() {
		SVGAttr attr = new SVGAttr("", "", node);
		assertEquals(node, attr.getParentNode());
	}


	@Test
	public void testHasAttribute() {
		SVGAttr attr = new SVGAttr("", "", node);
		assertFalse(attr.hasAttributes());
	}


	@Test
	public void testChildNodes() {
		SVGAttr attr = new SVGAttr("", "", node);
		assertFalse(attr.hasChildNodes());
	}


	@Test
	public void testIsEqualNode() {
		SVGAttr attr = new SVGAttr("n", "v", node);
		SVGAttr attr2 = (SVGAttr)attr.cloneNode(false);

		assertTrue(attr.isEqualNode(attr2));
		attr2 = new SVGAttr("n", "", node);
		assertFalse(attr.isEqualNode(attr2));
		attr2 = new SVGAttr("", "v", node);
		assertFalse(attr.isEqualNode(attr2));
		assertFalse(attr.isEqualNode(null));
	}


	@Test
	public void testIsSameNode() {
		SVGAttr attr = new SVGAttr("n", "v", node);
		SVGAttr attr2 = (SVGAttr)attr.cloneNode(false);

		assertTrue(attr.isSameNode(attr));
		assertFalse(attr.isSameNode(null));
		assertFalse(attr.isSameNode(attr2));
	}


	@Test
	public void testSetNodeValue() {
		SVGAttr attr = new SVGAttr("", "", node);

		try {
			attr.setNodeValue(null);
			fail();
		}
		catch(DOMException e) { /* Ok */ }

		attr.setNodeValue("val");
		assertEquals("val", attr.getValue());
		assertEquals("val", attr.getNodeValue());
	}


	@Test
	public void testGetPrefix() {
		SVGAttr attr = new SVGAttr("", "", node);

		assertNull(attr.getPrefix());
		attr = new SVGAttr("pref:", "", node);
		assertEquals("pref", attr.getPrefix());
		attr = new SVGAttr(":", "", node);
		assertEquals("", attr.getPrefix());
	}


	@Test
	public void testGetNamespaceURI() {
		SVGAttr attr   = new SVGAttr("pref:n", "", node);
		SVGElement elt = (SVGElement)node.getOwnerDocument().createElement("tag2");

		assertNull(attr.getNamespaceURI());
		elt.setAttribute("xmlns:pref", "namespace");
		elt.appendChild(node);
		assertEquals(attr.getNamespaceURI(), "namespace");
	}


	@Test
	public void testLookupNamespaceURI() {
		SVGAttr attr   = new SVGAttr("pref:n", "", node);
		SVGElement elt = (SVGElement)node.getOwnerDocument().createElement("tag2");

		assertNull(attr.getNamespaceURI());
		elt.setAttribute("xmlns:pref", "namespace");
		elt.appendChild(node);
		assertEquals(attr.getNamespaceURI(), "namespace");
	}


	@Test
	public void testGetLocalName() {
		SVGAttr attr = new SVGAttr("attrNodeName", "", node);
		assertEquals("attrNodeName", attr.getLocalName());
	}


	@Test
	public void testUselessMethods() {
		SVGAttr attr = new SVGAttr("", "", node);
		assertNull(attr.removeChild(null));
		assertNull(attr.insertBefore(null, null));
		assertNull(attr.getAttributes());
		assertNull(attr.getFirstChild());
		assertNull(attr.getLastChild());
		assertNull(attr.appendChild(null));
	}
}
