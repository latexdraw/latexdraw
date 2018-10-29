package net.sf.latexdraw.parsers.svg;

import net.sf.latexdraw.data.StringData;
import net.sf.latexdraw.parsers.svg.parsers.SVGLength;
import net.sf.latexdraw.parsers.svg.parsers.UnitProcessor;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

@RunWith(Theories.class)
public class TestSVGEllipseElement extends TestBaseSVGElement {
	@Test(expected = IllegalArgumentException.class)
	public void testContructorNULLNULL() throws MalformedSVGDocument {
		new SVGEllipseElement(null, null);
	}

	@Test(expected = MalformedSVGDocument.class)
	public void testContructorNodeNULL() throws MalformedSVGDocument {
		new SVGEllipseElement(node, null);
	}

	@Test(expected = MalformedSVGDocument.class)
	public void testContructorNodeInvInvNULL() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_RX, "dsd");
		node.setAttribute(SVGAttributes.SVG_RY, "dsd");
		new SVGEllipseElement(node, null);
	}

	@Test(expected = MalformedSVGDocument.class)
	public void testContructorNodeInvNULL() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_RX, "1");
		new SVGEllipseElement(node, null);
	}

	@Test(expected = MalformedSVGDocument.class)
	public void testContructorNode2NULL() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_RX, "-1");
		node.setAttribute(SVGAttributes.SVG_RY, "10");
		new SVGEllipseElement(node, null);
	}

	@Test(expected = MalformedSVGDocument.class)
	public void testContructorNode3NULL() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_RX, "10");
		node.setAttribute(SVGAttributes.SVG_RY, "-1");
		new SVGEllipseElement(node, null);
	}

	@Test
	public void testContructorNodeOK() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_RX, "10");
		node.setAttribute(SVGAttributes.SVG_RY, "20");
		new SVGEllipseElement(node, null);
	}

	@Test
	public void testGetCyDefault() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_RX, "10");
		node.setAttribute(SVGAttributes.SVG_RY, "20");
		final SVGEllipseElement ell = new SVGEllipseElement(node, null);
		assertEquals(0d, ell.getCy(), 0.0001);
	}

	@Test
	public void testGetCyVal() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_RX, "10");
		node.setAttribute(SVGAttributes.SVG_RY, "20");
		node.setAttribute(SVGAttributes.SVG_CY, "40");
		final SVGEllipseElement ell = new SVGEllipseElement(node, null);
		assertEquals(40d, ell.getCy(), 0.0001);
	}

	@Test
	public void testGetCyValPX() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_RX, "10");
		node.setAttribute(SVGAttributes.SVG_RY, "20");
		node.setAttribute(SVGAttributes.SVG_CY, "40px");
		final SVGEllipseElement ell = new SVGEllipseElement(node, null);
		assertEquals(40d, ell.getCy(), 0.0001);
	}

	@Test
	public void testGetCyValCM() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_RX, "10");
		node.setAttribute(SVGAttributes.SVG_RY, "20");
		node.setAttribute(SVGAttributes.SVG_CY, "40 cm");
		final SVGEllipseElement ell = new SVGEllipseElement(node, null);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(40, SVGLength.LengthType.CM), ell.getCy(), 0.0001);
	}

	@Test
	public void testGetCxDefault() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_RX, "10");
		node.setAttribute(SVGAttributes.SVG_RY, "20");
		final SVGEllipseElement ell = new SVGEllipseElement(node, null);
		assertEquals(0d, ell.getCx(), 0.0001);
	}

	@Test
	public void testGetCxVal() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_RX, "10");
		node.setAttribute(SVGAttributes.SVG_RY, "20");
		node.setAttribute(SVGAttributes.SVG_CX, "30");
		final SVGEllipseElement ell = new SVGEllipseElement(node, null);
		assertEquals(30d, ell.getCx(), 0.0001);
	}

	@Test
	public void testGetCxValPX() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_RX, "10");
		node.setAttribute(SVGAttributes.SVG_RY, "20");
		node.setAttribute(SVGAttributes.SVG_CX, "40px");
		final SVGEllipseElement ell = new SVGEllipseElement(node, null);
		assertEquals(40d, ell.getCx(), 0.0001);
	}

	@Test
	public void testGetCxValCM() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_RX, "10");
		node.setAttribute(SVGAttributes.SVG_RY, "20");
		node.setAttribute(SVGAttributes.SVG_CX, "40 cm");
		final SVGEllipseElement ell = new SVGEllipseElement(node, null);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(40, SVGLength.LengthType.CM), ell.getCx(), 0.0001);
	}

	@Theory
	public void testEnableRenderingKO(@StringData(vals = {"0", "10"}) final String v1, @StringData(vals = {"0", "10"}) final String v2) throws MalformedSVGDocument {
		assumeTrue(!("10".equals(v1) && "10".equals(v2)));
		node.setAttribute(SVGAttributes.SVG_RX, v1);
		node.setAttribute(SVGAttributes.SVG_RY, v2);
		final SVGEllipseElement ell = new SVGEllipseElement(node, null);
		assertFalse(ell.enableRendering());
	}

	@Theory
	public void testEnableRendering() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_RX, "10");
		node.setAttribute(SVGAttributes.SVG_RY, "10");
		final SVGEllipseElement ell = new SVGEllipseElement(node, null);
		assertTrue(ell.enableRendering());
	}

	@Test
	public void testGetRy() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_RX, "10");
		node.setAttribute(SVGAttributes.SVG_RY, "20");
		final SVGEllipseElement ell = new SVGEllipseElement(node, null);
		assertEquals(20d, ell.getRy(), 0.0001);
	}

	@Test
	public void testGetRyValDim() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_RX, "10");
		node.setAttribute(SVGAttributes.SVG_RY, "20");
		node.setAttribute(SVGAttributes.SVG_RY, "20 pt");
		final SVGEllipseElement ell = new SVGEllipseElement(node, null);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(20, SVGLength.LengthType.PT), ell.getRy(), 0.0001);
	}

	@Test
	public void testGetRx() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_RX, "10");
		node.setAttribute(SVGAttributes.SVG_RY, "20");
		final SVGEllipseElement ell = new SVGEllipseElement(node, null);
		assertEquals(10d, ell.getRx(), 0.0001);
	}

	@Test
	public void testGetRxValDim() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_RX, "10");
		node.setAttribute(SVGAttributes.SVG_RY, "20");
		node.setAttribute(SVGAttributes.SVG_RX, "10mm");
		final SVGEllipseElement ell = new SVGEllipseElement(node, null);
		assertEquals(UnitProcessor.INSTANCE.toUserUnit(10, SVGLength.LengthType.MM), ell.getRx(), 0.0001);
	}

	@Override
	public String getNameNode() {
		return SVGElements.SVG_ELLIPSE;
	}
}
