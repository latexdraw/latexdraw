package test.svg;

import net.sf.latexdraw.parsers.svg.MalformedSVGDocument;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGElements;
import net.sf.latexdraw.parsers.svg.SVGRectElement;

import org.junit.Test;

public class TestSVGRectElement extends AbstractTestSVGElement {
	@SuppressWarnings("unused")
	@Test
	public void testContructor() throws MalformedSVGDocument {
		try {
			new SVGRectElement(null, null);
			fail();
		}
		catch(Exception e){/**/}

		try {
			new SVGRectElement(node, null);
			fail();
		}
		catch(MalformedSVGDocument e){/**/}

		try {
			node.setAttribute(SVGAttributes.SVG_WIDTH, "dsd");
			node.setAttribute(SVGAttributes.SVG_HEIGHT, "dsd");
			new SVGRectElement(node, null);
			fail();
		}
		catch(MalformedSVGDocument e){/**/}

		try {
			node.setAttribute(SVGAttributes.SVG_WIDTH, "1");
			new SVGRectElement(node, null);
			fail();
		}
		catch(MalformedSVGDocument e){/**/}

		try {
			node.setAttribute(SVGAttributes.SVG_WIDTH, "-1");
			node.setAttribute(SVGAttributes.SVG_HEIGHT, "10");
			new SVGRectElement(node, null);
			fail();
		}
		catch(MalformedSVGDocument e){/**/}

		try {
			node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
			node.setAttribute(SVGAttributes.SVG_HEIGHT, "-1");
			new SVGRectElement(node, null);
			fail();
		}
		catch(MalformedSVGDocument e){/**/}

		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		new SVGRectElement(node, null);
	}



	@Test
	public void testEnableRendering() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "0");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "0");
		SVGRectElement e = new SVGRectElement(node, null);
		assertFalse(e.enableRendering());

		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "0");
		e = new SVGRectElement(node, null);
		assertFalse(e.enableRendering());

		node.setAttribute(SVGAttributes.SVG_WIDTH, "0");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "10");
		e = new SVGRectElement(node, null);
		assertFalse(e.enableRendering());

		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "10");
		e = new SVGRectElement(node, null);
		assertTrue(e.enableRendering());
	}



	@Test
	public void testGetHeight() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(r.getHeight(), 20.);
	}


	@Test
	public void testGetWidth() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(r.getWidth(), 10.);
	}


	@Test
	public void testGetRy() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(r.getRy(), 0.);

		node.setAttribute(SVGAttributes.SVG_RY, "1");
		r = new SVGRectElement(node, null);
		assertEquals(1., r.getRy());
	}



	@Test
	public void testGetRx() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(r.getRx(), 0.);

		node.setAttribute(SVGAttributes.SVG_RX, "1");
		r = new SVGRectElement(node, null);
		assertEquals(1., r.getRx());
	}



	@Test
	public void testGetY() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(r.getY(), 0.);

		node.setAttribute(SVGAttributes.SVG_Y, "1");
		r = new SVGRectElement(node, null);
		assertEquals(1., r.getY());
	}


	@Test
	public void testGetX() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(r.getX(), 0.);

		node.setAttribute(SVGAttributes.SVG_X, "1");
		r = new SVGRectElement(node, null);
		assertEquals(1., r.getX());
	}


	@Override
	public String getNameNode() {
		return SVGElements.SVG_RECT;
	}
}
