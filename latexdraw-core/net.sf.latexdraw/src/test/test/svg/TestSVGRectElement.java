package test.svg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.parsers.svg.MalformedSVGDocument;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGElements;
import net.sf.latexdraw.parsers.svg.SVGRectElement;

import org.junit.Test;

public class TestSVGRectElement extends AbstractTestSVGElement {
	@SuppressWarnings("unused")
	@Test(expected=IllegalArgumentException.class)
	public void testContructorFail1() throws MalformedSVGDocument {
		new SVGRectElement(null, null);
	}
	
	@SuppressWarnings("unused")
	@Test(expected=MalformedSVGDocument.class)
	public void testContructorFail2() throws MalformedSVGDocument {
		new SVGRectElement(node, null);
	}
	
	@SuppressWarnings("unused")
	@Test(expected=MalformedSVGDocument.class)
	public void testContructorFail3() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "dsd"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "dsd"); //$NON-NLS-1$
		new SVGRectElement(node, null);
	}
	
	@SuppressWarnings("unused")
	@Test(expected=MalformedSVGDocument.class)
	public void testContructorFail4() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "1"); //$NON-NLS-1$
		new SVGRectElement(node, null);
	}
	
	@SuppressWarnings("unused")
	@Test(expected=MalformedSVGDocument.class)
	public void testContructorFail5() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "-1"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "10"); //$NON-NLS-1$
		new SVGRectElement(node, null);
	}
	
	@SuppressWarnings("unused")
	@Test(expected=MalformedSVGDocument.class)
	public void testContructorFail6() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "-1"); //$NON-NLS-1$
		new SVGRectElement(node, null);
	}
	
	@SuppressWarnings("unused")
	@Test
	public void testContructor() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20"); //$NON-NLS-1$
		new SVGRectElement(node, null);
	}



	@Test
	public void testEnableRendering() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "0"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "0"); //$NON-NLS-1$
		SVGRectElement e = new SVGRectElement(node, null);
		assertFalse(e.enableRendering());

		node.setAttribute(SVGAttributes.SVG_WIDTH, "10"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "0"); //$NON-NLS-1$
		e = new SVGRectElement(node, null);
		assertFalse(e.enableRendering());

		node.setAttribute(SVGAttributes.SVG_WIDTH, "0"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "10"); //$NON-NLS-1$
		e = new SVGRectElement(node, null);
		assertFalse(e.enableRendering());

		node.setAttribute(SVGAttributes.SVG_WIDTH, "10"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "10"); //$NON-NLS-1$
		e = new SVGRectElement(node, null);
		assertTrue(e.enableRendering());
	}



	@Test
	public void testGetHeight() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20"); //$NON-NLS-1$
		SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(r.getHeight(), 20., 0.0001);
	}


	@Test
	public void testGetWidth() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20"); //$NON-NLS-1$
		SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(r.getWidth(), 10., 0.0001);
	}


	@Test
	public void testGetRy() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20"); //$NON-NLS-1$
		SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(r.getRy(), 0., 0.0001);

		node.setAttribute(SVGAttributes.SVG_RY, "1"); //$NON-NLS-1$
		r = new SVGRectElement(node, null);
		assertEquals(1., r.getRy(), 0.0001);
	}



	@Test
	public void testGetRx() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20"); //$NON-NLS-1$
		SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(r.getRx(), 0., 0.0001);

		node.setAttribute(SVGAttributes.SVG_RX, "1"); //$NON-NLS-1$
		r = new SVGRectElement(node, null);
		assertEquals(1., r.getRx(), 0.0001);
	}



	@Test
	public void testGetY() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20"); //$NON-NLS-1$
		SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(r.getY(), 0., 0.0001);

		node.setAttribute(SVGAttributes.SVG_Y, "1"); //$NON-NLS-1$
		r = new SVGRectElement(node, null);
		assertEquals(1., r.getY(), 0.0001);
	}


	@Test
	public void testGetX() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_WIDTH, "10"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_HEIGHT, "20"); //$NON-NLS-1$
		SVGRectElement r = new SVGRectElement(node, null);
		assertEquals(r.getX(), 0., 0.0001);

		node.setAttribute(SVGAttributes.SVG_X, "1"); //$NON-NLS-1$
		r = new SVGRectElement(node, null);
		assertEquals(1., r.getX(), 0.0001);
	}


	@Override
	public String getNameNode() {
		return SVGElements.SVG_RECT;
	}
}
