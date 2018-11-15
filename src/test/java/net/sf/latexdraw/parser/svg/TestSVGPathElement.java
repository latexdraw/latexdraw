package net.sf.latexdraw.parser.svg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestSVGPathElement extends TestBaseSVGElement {
	@Test
	public void testContructorFail1() {
		assertThrows(IllegalArgumentException.class, () -> new SVGPathElement(null, null));
	}

	@Test
	public void testContructorFail2() {
		assertThrows(IllegalArgumentException.class, () -> new SVGPathElement(node, null));
	}

	@Test
	public void testContructorOK() {
		node.setAttribute(SVGAttributes.SVG_D, "test");
		new SVGPathElement(node, null);
	}

	@Test
	public void testGetPathData() {
		node.setAttribute(SVGAttributes.SVG_D, "M 0 0 L 10 10");
		final SVGPathElement e = new SVGPathElement(node, null);
		assertEquals("M 0 0 L 10 10", e.getPathData());
	}

	@Override
	public String getNameNode() {
		return SVGElements.SVG_PATH;
	}
}
