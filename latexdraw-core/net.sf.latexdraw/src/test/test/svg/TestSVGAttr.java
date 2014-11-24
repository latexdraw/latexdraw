package test.svg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
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
        node = (SVGElement)doc.createElement("tag1"); //$NON-NLS-1$
	}



	@SuppressWarnings("unused")
	@Test(expected=NullPointerException.class)
	public void testConstructorFail1() {
			new SVGAttr(null, null, null);
	}
	
	@SuppressWarnings("unused")
	@Test(expected=NullPointerException.class)
	public void testConstructorFail2() {
			new SVGAttr("", "", null); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	@SuppressWarnings("unused")
	@Test(expected=NullPointerException.class)
	public void testConstructorFail3() {
			new SVGAttr(null, "", node); //$NON-NLS-1$
	}
	
	@SuppressWarnings("unused")
	@Test(expected=NullPointerException.class)
	public void testConstructorFail4() {
			new SVGAttr("", null, node); //$NON-NLS-1$

	}
	
	@SuppressWarnings("unused")
	@Test
	public void testConstructorOK() {
		new SVGAttr("", "", node); //$NON-NLS-1$ //$NON-NLS-2$
	}


	@Test
	public void testGetName() {
		SVGAttr attr = new SVGAttr("attrName", "", node); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("attrName", attr.getName()); //$NON-NLS-1$
	}


	@Test
	public void testGetElementOwner() {
		SVGAttr attr = new SVGAttr("", "", node); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(node, attr.getOwnerElement());
	}



	@Test
	public void testGetValue() {
		SVGAttr attr = new SVGAttr("", "attrValue", node); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("attrValue", attr.getValue()); //$NON-NLS-1$
	}


	@Test
	public void testIsId() {
		SVGAttr attr = new SVGAttr("id", "", node); //$NON-NLS-1$ //$NON-NLS-2$

		assertTrue(attr.isId());
		attr = new SVGAttr("", "", node); //$NON-NLS-1$ //$NON-NLS-2$
		assertFalse(attr.isId());
	}

	@Test(expected=DOMException.class)
	public void testSetValueFail() {
		SVGAttr attr = new SVGAttr("", "", node); //$NON-NLS-1$ //$NON-NLS-2$
		attr.setValue(null);
	}

	@Test
	public void testSetValue() {
		SVGAttr attr = new SVGAttr("", "", node); //$NON-NLS-1$ //$NON-NLS-2$
		attr.setValue("val"); //$NON-NLS-1$
		assertEquals("val", attr.getValue()); //$NON-NLS-1$
	}


	@Test
	public void testCloneNode() {
		SVGAttr attr = new SVGAttr("n", "v", node); //$NON-NLS-1$ //$NON-NLS-2$
		SVGAttr attr2 = (SVGAttr)attr.cloneNode(false);

		assertNotNull(attr2);
		assertEquals(attr.getName(), attr2.getName());
		assertEquals(attr.getValue(), attr2.getValue());
		assertEquals(attr.getOwnerElement(), attr2.getOwnerElement());
	}


	@Test
	public void testGetNodeName() {
		SVGAttr attr = new SVGAttr("attrNodeName", "", node); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("attrNodeName", attr.getNodeName()); //$NON-NLS-1$
	}


	@Test
	public void testGetNodeType() {
		SVGAttr attr = new SVGAttr("", "", node); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(Node.ATTRIBUTE_NODE, attr.getNodeType());
	}


	@Test
	public void testGetNodeValue() {
		SVGAttr attr = new SVGAttr("", "attrValue", node); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("attrValue", attr.getNodeValue()); //$NON-NLS-1$
	}


	@Test
	public void testGetParentNode() {
		SVGAttr attr = new SVGAttr("", "", node); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(node, attr.getParentNode());
	}


	@Test
	public void testHasAttribute() {
		SVGAttr attr = new SVGAttr("", "", node); //$NON-NLS-1$ //$NON-NLS-2$
		assertFalse(attr.hasAttributes());
	}


	@Test
	public void testChildNodes() {
		SVGAttr attr = new SVGAttr("", "", node); //$NON-NLS-1$ //$NON-NLS-2$
		assertFalse(attr.hasChildNodes());
	}


	@Test
	public void testIsEqualNode() {
		SVGAttr attr = new SVGAttr("n", "v", node); //$NON-NLS-1$ //$NON-NLS-2$
		SVGAttr attr2 = (SVGAttr)attr.cloneNode(false);

		assertTrue(attr.isEqualNode(attr2));
		attr2 = new SVGAttr("n", "", node); //$NON-NLS-1$ //$NON-NLS-2$
		assertFalse(attr.isEqualNode(attr2));
		attr2 = new SVGAttr("", "v", node); //$NON-NLS-1$ //$NON-NLS-2$
		assertFalse(attr.isEqualNode(attr2));
		assertFalse(attr.isEqualNode(null));
	}


	@Test
	public void testIsSameNode() {
		SVGAttr attr = new SVGAttr("n", "v", node); //$NON-NLS-1$ //$NON-NLS-2$
		SVGAttr attr2 = (SVGAttr)attr.cloneNode(false);

		assertTrue(attr.isSameNode(attr));
		assertFalse(attr.isSameNode(null));
		assertFalse(attr.isSameNode(attr2));
	}

	@Test(expected=DOMException.class)
	public void testSetNodeValueFail() {
		SVGAttr attr = new SVGAttr("", "", node); //$NON-NLS-1$ //$NON-NLS-2$
		attr.setNodeValue(null);
	}

	@Test
	public void testSetNodeValue() {
		SVGAttr attr = new SVGAttr("", "", node); //$NON-NLS-1$ //$NON-NLS-2$

		attr.setNodeValue("val"); //$NON-NLS-1$
		assertEquals("val", attr.getValue()); //$NON-NLS-1$
		assertEquals("val", attr.getNodeValue()); //$NON-NLS-1$
	}


	@Test
	public void testGetPrefix() {
		SVGAttr attr = new SVGAttr("", "", node); //$NON-NLS-1$ //$NON-NLS-2$

		assertNull(attr.getPrefix());
		attr = new SVGAttr("pref:", "", node); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("pref", attr.getPrefix()); //$NON-NLS-1$
		attr = new SVGAttr(":", "", node); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("", attr.getPrefix()); //$NON-NLS-1$
	}


	@Test
	public void testGetNamespaceURI() {
		SVGAttr attr   = new SVGAttr("pref:n", "", node); //$NON-NLS-1$ //$NON-NLS-2$
		SVGElement elt = (SVGElement)node.getOwnerDocument().createElement("tag2"); //$NON-NLS-1$

		assertNull(attr.getNamespaceURI());
		elt.setAttribute("xmlns:pref", "namespace"); //$NON-NLS-1$ //$NON-NLS-2$
		elt.appendChild(node);
		assertEquals(attr.getNamespaceURI(), "namespace"); //$NON-NLS-1$
	}


	@Test
	public void testLookupNamespaceURI() {
		SVGAttr attr   = new SVGAttr("pref:n", "", node); //$NON-NLS-1$ //$NON-NLS-2$
		SVGElement elt = (SVGElement)node.getOwnerDocument().createElement("tag2"); //$NON-NLS-1$

		assertNull(attr.getNamespaceURI());
		elt.setAttribute("xmlns:pref", "namespace"); //$NON-NLS-1$ //$NON-NLS-2$
		elt.appendChild(node);
		assertEquals(attr.getNamespaceURI(), "namespace"); //$NON-NLS-1$
	}


	@Test
	public void testGetLocalName() {
		SVGAttr attr = new SVGAttr("attrNodeName", "", node); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals("attrNodeName", attr.getLocalName()); //$NON-NLS-1$
	}


	@Test
	public void testUselessMethods() {
		SVGAttr attr = new SVGAttr("", "", node); //$NON-NLS-1$ //$NON-NLS-2$
		assertNull(attr.removeChild(null));
		assertNull(attr.insertBefore(null, null));
		assertNull(attr.getAttributes());
		assertNull(attr.getFirstChild());
		assertNull(attr.getLastChild());
		assertNull(attr.appendChild(null));
	}
}
