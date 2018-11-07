package net.sf.latexdraw.parser.svg;

import net.sf.latexdraw.parser.svg.parsers.SVGLength;
import net.sf.latexdraw.parser.svg.parsers.UnitProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSVGCircleElement extends TestBaseSVGElement {
	@Override
	@BeforeEach
	void setUp() {
		doc = new SVGDocument();
		node = (SVGElement) doc.createElement(SVGElements.SVG_CIRCLE);
	}

	@Test
	void testContructorFail1() {
		assertThrows(IllegalArgumentException.class, () -> new SVGCircleElement(null, null));
	}

	@Test
	void testContructorFail2() {
		assertThrows(MalformedSVGDocument.class, () -> new SVGCircleElement(node, null));
	}

	@Test
	void testContructorFail3() {
		node.setAttribute(SVGAttributes.SVG_R, "dsd");
		assertThrows(MalformedSVGDocument.class, () -> new SVGCircleElement(node, null));
	}

	@Test
	void testContructorFail4() {
		node.setAttribute(SVGAttributes.SVG_R, "-1");
		assertThrows(MalformedSVGDocument.class, () -> new SVGCircleElement(node, null));
	}

	@Test
	void testContructorOK() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "10");
		new SVGCircleElement(node, null);
	}

	@Test
	void testGetCy0() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "10");
		final SVGCircleElement e = new SVGCircleElement(node, null);
		assertEquals(0d, e.getCy(), 0.0001);
	}

	@Test
	void testGetCyOkRCY() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "10");
		node.setAttribute(SVGAttributes.SVG_CY, "40");
		final SVGCircleElement e = new SVGCircleElement(node, null);
		assertEquals(40d, e.getCy(), 0.0001);
	}

	@Test
	void testGetCyPix() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "10");
		node.setAttribute(SVGAttributes.SVG_CY, "40px ");
		final SVGCircleElement e = new SVGCircleElement(node, null);
		assertEquals(40d, e.getCy(), 0.0001);
	}

	@Test
	void testGetCycm() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "10");
		node.setAttribute(SVGAttributes.SVG_CY, " 40 cm ");
		final SVGCircleElement e = new SVGCircleElement(node, null);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(40, SVGLength.LengthType.CM), e.getCy(), 0.0001);
	}

	@Test
	void testGetCx0() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "10");
		final SVGCircleElement e = new SVGCircleElement(node, null);
		assertEquals(0d, e.getCx(), 0.0001);
	}

	@Test
	void testGetCxVal() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "10");
		node.setAttribute(SVGAttributes.SVG_CX, "76.987");
		final SVGCircleElement e = new SVGCircleElement(node, null);
		assertEquals(76.987, e.getCx(), 0.0001);
	}

	@Test
	void testGetCxPix() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "10");
		node.setAttribute(SVGAttributes.SVG_CX, "40px ");
		final SVGCircleElement e = new SVGCircleElement(node, null);
		assertEquals(40d, e.getCx(), 0.0001);
	}

	@Test
	void testGetCxmm() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "10");
		node.setAttribute(SVGAttributes.SVG_CX, " 30.5 mm ");
		final SVGCircleElement e = new SVGCircleElement(node, null);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(30.5, SVGLength.LengthType.MM), e.getCx(), 0.0001);
	}

	@Test
	void testEnableRenderingKO() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "0");
		final SVGCircleElement e = new SVGCircleElement(node, null);
		assertFalse(e.enableRendering());
	}

	@Test
	void testEnableRenderingOK() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "10");
		final SVGCircleElement e = new SVGCircleElement(node, null);
		assertTrue(e.enableRendering());
	}

	@Test
	void testGetR() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "20");
		final SVGCircleElement e = new SVGCircleElement(node, null);
		assertEquals(20d, e.getR(), 0.0001);
	}

	@Test
	void testGetRpt() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "20 pt");
		final SVGCircleElement e = new SVGCircleElement(node, null);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(20, SVGLength.LengthType.PT), e.getR(), 0.0001);
	}

	@Override
	public String getNameNode() {
		return SVGElements.SVG_CIRCLE;
	}
}
