package test.svg;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import net.sf.latexdraw.parsers.svg.MalformedSVGDocument;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElements;
import net.sf.latexdraw.parsers.svg.SVGSVGElement;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TestSVGSVGElement extends AbstractTestSVGElement {
	@SuppressWarnings("unused")
	@Test
	public void testContructor() throws MalformedSVGDocument {
		try {
			new SVGSVGElement(null);
			fail();
		}
		catch(Exception e){/**/}

		new SVGSVGElement(node, null);
	}



	@Test
	public void testGetDefs() throws MalformedSVGDocument, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
		Element elt, n;
        SVGSVGElement e = new SVGSVGElement(node, null);

		assertNull(e.getMeta());

		n = document.createElement(SVGElements.SVG_SVG);
		n.setAttribute("xmlns", SVGDocument.SVG_NAMESPACE);
		elt = document.createElement(SVGElements.SVG_DEFS);
		n.appendChild(elt);
		e = new SVGSVGElement(n, null);
		assertNotNull(e.getDefs());
	}



	@Test
	public void testGetMeta() throws MalformedSVGDocument, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
		Element elt, n;
        SVGSVGElement e = new SVGSVGElement(node, null);

		assertNull(e.getMeta());

		n = document.createElement(SVGElements.SVG_SVG);
		n.setAttribute("xmlns", SVGDocument.SVG_NAMESPACE);
		elt = document.createElement(SVGElements.SVG_METADATA);
		n.appendChild(elt);
		e = new SVGSVGElement(n, null);
		assertNotNull(e.getMeta());
	}




	@Test
	public void testGetHeight() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "200");
		SVGSVGElement e = new SVGSVGElement(node, null);
		assertEquals(e.getHeight(), 200.);
	}



	@Test
	public void testEnableRendering() throws MalformedSVGDocument {
		SVGSVGElement e = new SVGSVGElement(node, null);
		assertTrue(e.enableRendering());

		node.setAttribute(SVGAttributes.SVG_WIDTH, "0");
		e = new SVGSVGElement(node, null);
		assertFalse(e.enableRendering());

		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "0");
		e = new SVGSVGElement(node, null);
		assertFalse(e.enableRendering());

		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "10");
		e = new SVGSVGElement(node, null);
		assertTrue(e.enableRendering());
	}


	@Test
	public void testGetY() throws MalformedSVGDocument {
		SVGSVGElement e = new SVGSVGElement(node, null);
		assertEquals(e.getY(), 0.);

		node.setAttribute(SVGAttributes.SVG_Y, "-10");
		e = new SVGSVGElement(node, null);
		assertEquals(e.getY(), -10.);
	}



	@Test
	public void testGetX() throws MalformedSVGDocument {
		SVGSVGElement e = new SVGSVGElement(node, null);
		assertEquals(e.getX(), 0.);

		node.setAttribute(SVGAttributes.SVG_X, "10");
		e = new SVGSVGElement(node, null);
		assertEquals(e.getX(), 10.);
	}


	@Test
	public void testGetWidth() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "100");
		SVGSVGElement e = new SVGSVGElement(node, null);
		assertEquals(e.getWidth(), 100.);
	}


	@Test
	public void testVersion() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_VERSION, "1.1");
		SVGSVGElement e = new SVGSVGElement(node, null);
		assertEquals(e.getVersion(), "1.1");
	}


	@Override
	public String getNameNode() {
		return SVGElements.SVG_SVG;
	}
}
