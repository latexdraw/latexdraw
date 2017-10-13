package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.parsers.svg.MalformedSVGDocument;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGElements;
import net.sf.latexdraw.parsers.svg.SVGStopElement;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestSVGStopElement extends AbstractTestSVGElement {
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		node.setAttribute(SVGAttributes.SVG_OFFSET, "0.5");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testContructorFail1() throws MalformedSVGDocument {
		new SVGStopElement(null, null);
	}

	@Test(expected = MalformedSVGDocument.class)
	public void testContructorFail3() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_OFFSET, "dsd");
		new SVGStopElement(node, null);
	}

	@Test
	public void testContructor() throws MalformedSVGDocument {
		new SVGStopElement(node, null);
	}

	@Test
	public void testGetStopColorDefault() throws MalformedSVGDocument {
		SVGStopElement e = new SVGStopElement(node, null);
		assertEquals(e.getStopColor(), DviPsColors.BLACK);
	}

	@Test
	public void testGetStopColor() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_OFFSET, "0.5");
		node.setAttribute(SVGAttributes.SVG_STOP_COLOR, "blue");
		SVGStopElement e = new SVGStopElement(node, null);
		assertEquals(DviPsColors.BLUE, e.getStopColor());
	}

	@Test
	public void testGetOffset() throws MalformedSVGDocument {
		SVGStopElement e = new SVGStopElement(node, null);
		assertEquals(0.5, e.getOffset(), 0.0001);
	}

	@Test
	public void testEnableRendering() throws MalformedSVGDocument {
		SVGStopElement s = new SVGStopElement(node, null);
		assertTrue(s.enableRendering());
	}

	@Override
	public String getNameNode() {
		return SVGElements.SVG_STOP;
	}
}
