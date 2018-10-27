package net.sf.latexdraw.parsers.svg;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestSVGPathElement extends TestBaseSVGElement {
	@Test(expected = IllegalArgumentException.class)
	public void testContructorFail1() throws MalformedSVGDocument {
		new SVGPathElement(null, null);
	}

	@Test(expected = MalformedSVGDocument.class)
	public void testContructorFail2() throws MalformedSVGDocument {
		new SVGPathElement(node, null);
	}

	@Test
	public void testContructorOK() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_D, "test");
		new SVGPathElement(node, null);
	}

	@Test
	public void testGetPathData() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_D, "M 0 0 L 10 10");
		SVGPathElement e = new SVGPathElement(node, null);
		assertEquals("M 0 0 L 10 10", e.getPathData());
	}

	@Override
	public String getNameNode() {
		return SVGElements.SVG_PATH;
	}
}
