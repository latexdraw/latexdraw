package net.sf.latexdraw.parsers.svg;

import net.sf.latexdraw.parsers.svg.parsers.SVGLength;
import net.sf.latexdraw.parsers.svg.parsers.UnitProcessor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestSVGCircleElement extends TestBaseSVGElement {
	@Override
	@Before
	public void setUp() {
		doc = new SVGDocument();
		node = (SVGElement) doc.createElement(SVGElements.SVG_CIRCLE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testContructorFail1() throws MalformedSVGDocument {
		new SVGCircleElement(null, null);
	}

	@Test(expected = MalformedSVGDocument.class)
	public void testContructorFail2() throws MalformedSVGDocument {
		new SVGCircleElement(node, null);
	}

	@Test(expected = MalformedSVGDocument.class)
	public void testContructorFail3() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "dsd");
		new SVGCircleElement(node, null);
	}

	@Test(expected = MalformedSVGDocument.class)
	public void testContructorFail4() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "-1");
		new SVGCircleElement(node, null);
	}

	@Test
	public void testContructorOK() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "10");
		new SVGCircleElement(node, null);
	}

	@Test
	public void testGetCy0() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "10");
		SVGCircleElement e = new SVGCircleElement(node, null);
		assertEquals(0d, e.getCy(), 0.0001);
	}

	@Test
	public void testGetCyOkRCY() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "10");
		node.setAttribute(SVGAttributes.SVG_CY, "40");
		SVGCircleElement e = new SVGCircleElement(node, null);
		assertEquals(40d, e.getCy(), 0.0001);
	}

	@Test
	public void testGetCyPix() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "10");
		node.setAttribute(SVGAttributes.SVG_CY, "40px ");
		SVGCircleElement e = new SVGCircleElement(node, null);
		assertEquals(40d, e.getCy(), 0.0001);
	}

	@Test
	public void testGetCycm() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "10");
		node.setAttribute(SVGAttributes.SVG_CY, " 40 cm ");
		SVGCircleElement e = new SVGCircleElement(node, null);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(40, SVGLength.LengthType.CM), e.getCy(), 0.0001);
	}

	@Test
	public void testGetCx0() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "10");
		SVGCircleElement e = new SVGCircleElement(node, null);
		assertEquals(0d, e.getCx(), 0.0001);
	}

	@Test
	public void testGetCxVal() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "10");
		node.setAttribute(SVGAttributes.SVG_CX, "76.987");
		SVGCircleElement e = new SVGCircleElement(node, null);
		assertEquals(76.987, e.getCx(), 0.0001);
	}

	@Test
	public void testGetCxPix() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "10");
		node.setAttribute(SVGAttributes.SVG_CX, "40px ");
		SVGCircleElement e = new SVGCircleElement(node, null);
		assertEquals(40d, e.getCx(), 0.0001);
	}

	@Test
	public void testGetCxmm() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "10");
		node.setAttribute(SVGAttributes.SVG_CX, " 30.5 mm ");
		SVGCircleElement e = new SVGCircleElement(node, null);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(30.5, SVGLength.LengthType.MM), e.getCx(), 0.0001);
	}

	@Test
	public void testEnableRenderingKO() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "0");
		SVGCircleElement e = new SVGCircleElement(node, null);
		assertFalse(e.enableRendering());
	}

	@Test
	public void testEnableRenderingOK() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "10");
		SVGCircleElement e = new SVGCircleElement(node, null);
		assertTrue(e.enableRendering());
	}

	@Test
	public void testGetR() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "20");
		SVGCircleElement e = new SVGCircleElement(node, null);
		assertEquals(20d, e.getR(), 0.0001);
	}

	@Test
	public void testGetRpt() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "20 pt");
		SVGCircleElement e = new SVGCircleElement(node, null);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(20, SVGLength.LengthType.PT), e.getR(), 0.0001);
	}

	@Override
	public String getNameNode() {
		return SVGElements.SVG_CIRCLE;
	}
}
