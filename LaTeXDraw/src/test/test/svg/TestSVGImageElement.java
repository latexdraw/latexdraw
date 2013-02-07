package test.svg;

import net.sf.latexdraw.parsers.svg.MalformedSVGDocument;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGElements;
import net.sf.latexdraw.parsers.svg.SVGImageElement;

import org.junit.Test;

public class TestSVGImageElement extends AbstractTestSVGElement {
	@SuppressWarnings("unused")
	@Test
	public void testContructor() throws MalformedSVGDocument {
		try {
			new SVGImageElement(null, "");
			fail();
		}
		catch(Exception e){/**/}

		try {
			new SVGImageElement(node, null);
			fail();
		}
		catch(MalformedSVGDocument e){/**/}

		try {
			node.setAttribute(SVGAttributes.SVG_WIDTH, "dsd");
			node.setAttribute(SVGAttributes.SVG_HEIGHT, "dsd");
			new SVGImageElement(node, null);
			fail();
		}
		catch(MalformedSVGDocument e){/**/}

		try {
			node.setAttribute(SVGAttributes.SVG_WIDTH, "1");
			new SVGImageElement(node, null);
			fail();
		}
		catch(MalformedSVGDocument e){/**/}

		try {
			node.setAttribute(SVGAttributes.SVG_WIDTH, "-1");
			node.setAttribute(SVGAttributes.SVG_HEIGHT, "10");
			new SVGImageElement(node, null);
			fail();
		}
		catch(MalformedSVGDocument e){/**/}

		try {
			node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
			node.setAttribute(SVGAttributes.SVG_HEIGHT, "-1");
			new SVGImageElement(node, null);
			fail();
		}
		catch(MalformedSVGDocument e){/**/}

		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		new SVGImageElement(node, null);
	}




	@Test
	public void testGetHeight() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		SVGImageElement e = new SVGImageElement(node, null);
		assertEquals(e.getHeight(), 20.);
	}


	@Test
	public void testGetWidth() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		SVGImageElement e = new SVGImageElement(node, null);
		assertEquals(e.getWidth(), 10.);
	}



	@Test
	public void testGetURI() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
		SVGImageElement e = new SVGImageElement(node, null);
		assertNull(e.getURI());

		node.setAttribute("xlink:href", "/dir/file");
		e = new SVGImageElement(node, null);
		assertEquals(e.getURI(), "/dir/file");
	}



	@Test
	public void testEnableRendering() {
		try {
			node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
			node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
			SVGImageElement e = new SVGImageElement(node, null);
			assertFalse(e.enableRendering());

			node.setAttribute("xlink:href", "/rerzerojcsf/dsqdsdfgdre");
			e = new SVGImageElement(node, null);
			assertFalse(e.enableRendering());

			node.setAttribute("xlink:href", "build.xml");
			e = new SVGImageElement(node, null);
			assertFalse(e.enableRendering());

			node.setAttribute(SVGAttributes.SVG_WIDTH, "0");
			e = new SVGImageElement(node, null);
			assertFalse(e.enableRendering());

			node.setAttribute(SVGAttributes.SVG_HEIGHT, "0");
			e = new SVGImageElement(node, null);
			assertFalse(e.enableRendering());

			node.setAttribute(SVGAttributes.SVG_WIDTH, "20");
			e = new SVGImageElement(node, null);
			assertFalse(e.enableRendering());
		}
		catch(MalformedSVGDocument e) { fail(); }
	}



	@Test
	public void testGetY() {
		try {
			node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
			node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
			SVGImageElement e = new SVGImageElement(node, null);
			assertEquals(e.getY(), 0.);

			node.setAttribute(SVGAttributes.SVG_Y, "1");
			e = new SVGImageElement(node, null);
			assertEquals(1., e.getY());
		}
		catch(MalformedSVGDocument e) { fail(); }
	}


	@Test
	public void testGetX() {
		try {
			node.setAttribute(SVGAttributes.SVG_WIDTH, "10");
			node.setAttribute(SVGAttributes.SVG_HEIGHT, "20");
			SVGImageElement e = new SVGImageElement(node, null);
			assertEquals(e.getX(), 0.);

			node.setAttribute(SVGAttributes.SVG_X, "1");
			e = new SVGImageElement(node, null);
			assertEquals(1., e.getX());
		}
		catch(MalformedSVGDocument e) { fail(); }
	}


	@Override
	public String getNameNode() {
		return SVGElements.SVG_IMAGE;
	}
}
