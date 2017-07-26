package net.sf.latexdraw.view.svg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import net.sf.latexdraw.parsers.svg.CSSColors;
import net.sf.latexdraw.parsers.svg.SVGAttr;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGNodeList;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.DOMException;

public abstract class AbstractTestSVGElement {
	protected SVGElement node;
	protected SVGDocument doc = new SVGDocument();

	public abstract String getNameNode();

	@Before
	public void setUp() {
		doc = new SVGDocument();
		node = (SVGElement)doc.createElement(getNameNode());
	}

	@Test
	public void testGetStroke() {
		node.removeAttribute(SVGAttributes.SVG_STROKE);
		assertEquals(node.getStroke(), null);

		node.setAttribute(SVGAttributes.SVG_STROKE, CSSColors.CSS_ALICEBLUE_NAME);
		assertEquals(node.getStroke(), CSSColors.CSS_ALICEBLUE_RGB_VALUE);
		node.setAttribute(SVGAttributes.SVG_STROKE, "testtest"); //$NON-NLS-1$
		assertEquals(node.getStroke(), null);
	}

	@Test
	public void testGetNodeName() {
		assertEquals(getNameNode(), node.getNodeName());
	}

	@Test
	public void testSetNodeName() {
		node.setNodeName("test"); //$NON-NLS-1$
		assertEquals("test", node.getNodeName()); //$NON-NLS-1$
		node.setNodeName(getNameNode());
		assertEquals(getNameNode(), node.getNodeName());
	}

	@Test
	public void testSetParent() {
		SVGElement elt = (SVGElement)doc.createElement("elt"); //$NON-NLS-1$

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
		assertNull(node.getAttribute("")); //$NON-NLS-1$
		node.setAttribute("testAttr", "valAttr"); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(node.getAttribute("testAttr"), "valAttr"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	public void testGetAttributeNode() {
		assertNull(node.getAttributeNode(null));
		assertNull(node.getAttributeNode("")); //$NON-NLS-1$
		node.setAttribute("testAttr2", "valAttr2"); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(node.getAttributeNode("testAttr2").getNodeValue(), "valAttr2"); //$NON-NLS-1$ //$NON-NLS-2$
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
		node.appendChild(new SVGAttr("", "", node)); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	public void testAppendChild() {
		SVGElement elt = (SVGElement)doc.createElement("eltAppendChild"); //$NON-NLS-1$
		assertEquals(node.appendChild(elt), elt);
		assertEquals(node.getChildren("eltAppendChild").getLength(), 1); //$NON-NLS-1$
	}
}
