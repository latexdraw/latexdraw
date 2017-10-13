package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.parsers.svg.MalformedSVGDocument;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDefsElement;
import net.sf.latexdraw.parsers.svg.SVGElements;
import net.sf.latexdraw.parsers.svg.SVGMarkerElement;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class TestSVGDefsElement extends AbstractTestSVGElement {
	SVGDefsElement defs;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		defs = new SVGDefsElement(node, null);
	}

	@Test
	public void testEnableRendering() throws MalformedSVGDocument {
		assertFalse(defs.enableRendering());
	}

	@Test
	public void testGetDefNULL() throws MalformedSVGDocument {
		assertNull(defs.getDef(null));
	}

	@Test
	public void testGetDefEmpty() throws MalformedSVGDocument {
		assertNull(defs.getDef(""));
	}

	@Test
	public void testGetDefInvalid() throws MalformedSVGDocument {
		assertNull(defs.getDef("dsqd"));
	}

	@Test
	public void testGetDefOK() throws MalformedSVGDocument {
		final SVGMarkerElement mark = new SVGMarkerElement(node.getOwnerDocument());
		mark.setAttribute(SVGAttributes.SVG_ID, SVGAttributes.SVG_ID);
		defs.appendChild(mark);
		assertEquals(mark, defs.getDef("id"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testContructorKO() throws MalformedSVGDocument {
		new SVGDefsElement(null, null);
	}

	@Test
	public void testContructorOK() throws MalformedSVGDocument {
		new SVGDefsElement(node, null);
	}

	@Override
	public String getNameNode() {
		return SVGElements.SVG_DEFS;
	}
}
