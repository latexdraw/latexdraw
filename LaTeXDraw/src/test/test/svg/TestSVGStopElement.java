package test.svg;

import java.awt.Color;


import net.sf.latexdraw.parsers.svg.MalformedSVGDocument;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGElements;
import net.sf.latexdraw.parsers.svg.SVGStopElement;

import org.junit.Test;

public class TestSVGStopElement extends AbstractTestSVGElement {
	@SuppressWarnings("unused")
	@Test
	public void testContructor() throws MalformedSVGDocument {
		try {
			new SVGStopElement(null, null);
			fail();
		}
		catch(Exception e){/**/}

		try {
			new SVGStopElement(node, null);
			fail();
		}
		catch(MalformedSVGDocument e){/**/}

		try {
			node.setAttribute(SVGAttributes.SVG_OFFSET, "dsd");
			new SVGStopElement(node, null);
			fail();
		}
		catch(MalformedSVGDocument e){/**/}

		node.setAttribute(SVGAttributes.SVG_OFFSET, "0.5");
		new SVGStopElement(node, null);
	}


	@Test
	public void testGetStopColor() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_OFFSET, "0.5");
		SVGStopElement e = new SVGStopElement(node, null);
		assertEquals(e.getStopColor(), Color.BLACK);

		node.setAttribute(SVGAttributes.SVG_OFFSET, "0.5");
		node.setAttribute(SVGAttributes.SVG_STOP_COLOR, "blue");
		e = new SVGStopElement(node, null);
		assertEquals(e.getStopColor(), Color.BLUE);
	}



	@Test
	public void testGetOffset() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_OFFSET, "0.5");
		SVGStopElement e = new SVGStopElement(node, null);
		assertEquals(e.getOffset(), 0.5);
	}


	@Test
	public void testEnableRendering() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_OFFSET, "0.5");
		SVGStopElement s = new SVGStopElement(node, null);
		assertTrue(s.enableRendering());
	}


	@Override
	public String getNameNode() {
		return SVGElements.SVG_STOP;
	}
}
