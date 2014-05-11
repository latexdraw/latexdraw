package test.svg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import net.sf.latexdraw.parsers.svg.CSSColors;
import net.sf.latexdraw.parsers.svg.SVGAttr;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGNodeList;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.DOMException;

public abstract class AbstractTestSVGElement{
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
		node.setAttribute(SVGAttributes.SVG_STROKE, "testtest");
		assertEquals(node.getStroke(), null);
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
		assertEquals(node.getAttribute("testAttr"), "valAttr");
	}


	@Test
	public void testGetAttributeNode() {
		assertNull(node.getAttributeNode(null));
		assertNull(node.getAttributeNode(""));
		node.setAttribute("testAttr2", "valAttr2");
		assertEquals(node.getAttributeNode("testAttr2").getNodeValue(), "valAttr2");
	}


	@Test
	public void testGetTagName() {
		assertEquals(node.getNodeName(), node.getTagName());
	}



	@Test
	public void testAppendChild() {
		try {
			node.appendChild(null);
			fail();
		}
		catch(DOMException e) { /* ok */ }

		try {
			node.appendChild(new SVGAttr("", "", node));
			fail();
		}
		catch(DOMException e) { /* ok */ }


		SVGElement elt = (SVGElement)doc.createElement("eltAppendChild");
		assertEquals(node.appendChild(elt), elt);
		assertEquals(node.getChildren("eltAppendChild").getLength(), 1);
	}
}
