package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.parsers.svg.MalformedSVGDocument;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGElements;
import net.sf.latexdraw.parsers.svg.SVGImageElement;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestSVGImageElement extends AbstractTestSVGElement {
	@SuppressWarnings("unused")
	@Test
	public void testContructor() throws MalformedSVGDocument {
		try {
			new SVGImageElement(null, ""); //$NON-NLS-1$
			fail();
		}catch(Exception e) {
			/**/}

		try {
			new SVGImageElement(node, null);
			fail();
		}catch(MalformedSVGDocument e) {
			/**/}

		try {
			node.setAttribute(SVGAttributes.SVG_WIDTH, "dsd"); //$NON-NLS-1$
			node.setAttribute(SVGAttributes.SVG_HEIGHT, "dsd"); //$NON-NLS-1$
			new SVGImageElement(node, null);
			fail();
		}catch(MalformedSVGDocument e) {
			/**/}

		try {
			node.setAttribute(SVGAttributes.SVG_WIDTH, "1"); //$NON-NLS-1$
			new SVGImageElement(node, null);
			fail();
		}catch(MalformedSVGDocument e) {
			/**/}

		try {
			node.setAttribute(SVGAttributes.SVG_WIDTH, "-1"); //$NON-NLS-1$
			node.setAttribute(SVGAttributes.SVG_HEIGHT, "10"); //$NON-NLS-1$
			new SVGImageElement(node, null);
			fail();
		}catch(MalformedSVGDocument e) {
			/**/}

		try {
			node.setAttribute(SVGAttributes.SVG_WIDTH, "10"); //$NON-NLS-1$
			node.setAttribute(SVGAttributes.SVG_HEIGHT, "-1"); //$NON-NLS-1$
			new SVGImageElement(node, null);
			fail();
		}catch(MalformedSVGDocument e) {
			/**/}

		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20"); //$NON-NLS-1$
		new SVGImageElement(node, null);
	}

	@Test
	public void testGetHeight() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20"); //$NON-NLS-1$
		SVGImageElement e = new SVGImageElement(node, null);
		assertEquals(e.getHeight(), 20., 0.0001);
	}

	@Test
	public void testGetWidth() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20"); //$NON-NLS-1$
		SVGImageElement e = new SVGImageElement(node, null);
		assertEquals(e.getWidth(), 10., 0.0001);
	}

	@Test
	public void testGetURI() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20"); //$NON-NLS-1$
		SVGImageElement e = new SVGImageElement(node, null);
		assertNull(e.getURI());

		node.setAttribute("xlink:href", "/dir/file"); //$NON-NLS-1$ //$NON-NLS-2$
		e = new SVGImageElement(node, null);
		assertEquals(e.getURI(), "/dir/file"); //$NON-NLS-1$
	}

	@Test
	public void testEnableRendering() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20"); //$NON-NLS-1$
		SVGImageElement e = new SVGImageElement(node, null);
		assertFalse(e.enableRendering());

		node.setAttribute("xlink:href", "/rerzerojcsf/dsqdsdfgdre"); //$NON-NLS-1$ //$NON-NLS-2$
		e = new SVGImageElement(node, null);
		assertFalse(e.enableRendering());

		node.setAttribute("xlink:href", "build.xml"); //$NON-NLS-1$ //$NON-NLS-2$
		e = new SVGImageElement(node, null);
		assertFalse(e.enableRendering());

		node.setAttribute(SVGAttributes.SVG_WIDTH, "0"); //$NON-NLS-1$
		e = new SVGImageElement(node, null);
		assertFalse(e.enableRendering());

		node.setAttribute(SVGAttributes.SVG_HEIGHT, "0"); //$NON-NLS-1$
		e = new SVGImageElement(node, null);
		assertFalse(e.enableRendering());

		node.setAttribute(SVGAttributes.SVG_WIDTH, "20"); //$NON-NLS-1$
		e = new SVGImageElement(node, null);
		assertFalse(e.enableRendering());
	}

	@Test
	public void testGetY() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20"); //$NON-NLS-1$
		SVGImageElement e = new SVGImageElement(node, null);
		assertEquals(e.getY(), 0., 0.0001);

		node.setAttribute(SVGAttributes.SVG_Y, "1"); //$NON-NLS-1$
		e = new SVGImageElement(node, null);
		assertEquals(1., e.getY(), 0.0001);
	}

	@Test
	public void testGetX() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20"); //$NON-NLS-1$
		SVGImageElement e = new SVGImageElement(node, null);
		assertEquals(e.getX(), 0., 0.0001);

		node.setAttribute(SVGAttributes.SVG_X, "1"); //$NON-NLS-1$
		e = new SVGImageElement(node, null);
		assertEquals(1., e.getX(), 0.0001);
	}

	@Override
	public String getNameNode() {
		return SVGElements.SVG_IMAGE;
	}
}
