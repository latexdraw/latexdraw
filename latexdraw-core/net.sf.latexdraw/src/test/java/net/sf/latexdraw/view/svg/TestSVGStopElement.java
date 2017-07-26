package net.sf.latexdraw.view.svg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.view.latex.DviPsColors;
import net.sf.latexdraw.parsers.svg.MalformedSVGDocument;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGElements;
import net.sf.latexdraw.parsers.svg.SVGStopElement;

import org.junit.Before;
import org.junit.Test;

public class TestSVGStopElement extends AbstractTestSVGElement {
	@Override
	@Before
	public void setUp() {
		super.setUp();
		node.setAttribute(SVGAttributes.SVG_OFFSET, "0.5"); //$NON-NLS-1$
	}

	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void testContructorFail1() throws MalformedSVGDocument {
		new SVGStopElement(null, null);
	}

	@SuppressWarnings("unused")
	@Test(expected = MalformedSVGDocument.class)
	public void testContructorFail3() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_OFFSET, "dsd"); //$NON-NLS-1$
		new SVGStopElement(node, null);
	}

	@SuppressWarnings("unused")
	@Test
	public void testContructor() throws MalformedSVGDocument {
		new SVGStopElement(node, null);
	}

	@Test
	public void testGetStopColor1() throws MalformedSVGDocument {
		SVGStopElement e = new SVGStopElement(node, null);
		assertEquals(e.getStopColor(), DviPsColors.BLACK);
		node.setAttribute(SVGAttributes.SVG_OFFSET, "0.5"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_STOP_COLOR, "blue"); //$NON-NLS-1$
		e = new SVGStopElement(node, null);
		assertEquals(e.getStopColor(), DviPsColors.BLUE);
	}

	@Test
	public void testGetOffset() throws MalformedSVGDocument {
		SVGStopElement e = new SVGStopElement(node, null);
		assertEquals(e.getOffset(), 0.5, 0.0001);
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
