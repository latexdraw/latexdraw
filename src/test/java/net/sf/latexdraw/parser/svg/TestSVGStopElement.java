package net.sf.latexdraw.parser.svg;

import net.sf.latexdraw.view.latex.DviPsColors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSVGStopElement extends TestBaseSVGElement {
	@BeforeEach
	void setUpStop() {
		node.setAttribute(SVGAttributes.SVG_OFFSET, "0.5");
	}

	@Test
	void testContructorFail1() {
		assertThrows(IllegalArgumentException.class, () -> new SVGStopElement(null, null));
	}

	@Test
	void testContructorFail3() {
		node.setAttribute(SVGAttributes.SVG_OFFSET, "dsd");
		assertThrows(MalformedSVGDocument.class, () -> new SVGStopElement(node, null));
	}

	@Test
	void testContructor() throws MalformedSVGDocument {
		new SVGStopElement(node, null);
	}

	@Test
	void testGetStopColorDefault() throws MalformedSVGDocument {
		final SVGStopElement e = new SVGStopElement(node, null);
		assertEquals(DviPsColors.BLACK, e.getStopColor());
	}

	@Test
	void testGetStopColor() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_OFFSET, "0.5");
		node.setAttribute(SVGAttributes.SVG_STOP_COLOR, "blue");
		final SVGStopElement e = new SVGStopElement(node, null);
		assertEquals(DviPsColors.BLUE, e.getStopColor());
	}

	@Test
	void testGetOffset() throws MalformedSVGDocument {
		final SVGStopElement e = new SVGStopElement(node, null);
		assertEquals(0.5, e.getOffset(), 0.0001);
	}

	@Test
	void testEnableRendering() throws MalformedSVGDocument {
		final SVGStopElement s = new SVGStopElement(node, null);
		assertTrue(s.enableRendering());
	}

	@Override
	public String getNameNode() {
		return SVGElements.SVG_STOP;
	}
}
