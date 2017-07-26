package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.parsers.svg.MalformedSVGDocument;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGElements;
import net.sf.latexdraw.parsers.svg.SVGEllipseElement;
import net.sf.latexdraw.parsers.svg.parsers.SVGLength;
import net.sf.latexdraw.parsers.svg.parsers.UnitProcessor;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestSVGEllipseElement extends AbstractTestSVGElement {
	@SuppressWarnings("unused")
	@Test
	public void testContructor() {
		try {
			new SVGEllipseElement(null, null);
			fail();
		}catch(Exception e) {
			/**/}

		try {
			new SVGEllipseElement(node, null);
			fail();
		}catch(MalformedSVGDocument e) {
			/**/}

		try {
			node.setAttribute(SVGAttributes.SVG_RX, "dsd"); //$NON-NLS-1$
			node.setAttribute(SVGAttributes.SVG_RY, "dsd"); //$NON-NLS-1$
			new SVGEllipseElement(node, null);
			fail();
		}catch(MalformedSVGDocument e) {
			/**/}

		try {
			node.setAttribute(SVGAttributes.SVG_RX, "1"); //$NON-NLS-1$
			new SVGEllipseElement(node, null);
			fail();
		}catch(MalformedSVGDocument e) {
			/**/}

		try {
			node.setAttribute(SVGAttributes.SVG_RX, "-1"); //$NON-NLS-1$
			node.setAttribute(SVGAttributes.SVG_RY, "10"); //$NON-NLS-1$
			new SVGEllipseElement(node, null);
			fail();
		}catch(MalformedSVGDocument e) {
			/**/}

		try {
			node.setAttribute(SVGAttributes.SVG_RX, "10"); //$NON-NLS-1$
			node.setAttribute(SVGAttributes.SVG_RY, "-1"); //$NON-NLS-1$
			new SVGEllipseElement(node, null);
			fail();
		}catch(MalformedSVGDocument e) {
			/**/}

		try {
			node.setAttribute(SVGAttributes.SVG_RX, "10"); //$NON-NLS-1$
			node.setAttribute(SVGAttributes.SVG_RY, "20"); //$NON-NLS-1$
			new SVGEllipseElement(node, null);
		}catch(MalformedSVGDocument e) {
			fail();
		}
	}

	@Test
	public void testGetCy() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_RX, "10"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_RY, "20"); //$NON-NLS-1$
		SVGEllipseElement e = new SVGEllipseElement(node, null);
		assertEquals(e.getCy(), 0., 0.0001);

		node.setAttribute(SVGAttributes.SVG_CY, "40"); //$NON-NLS-1$
		e = new SVGEllipseElement(node, null);
		assertEquals(e.getCy(), 40., 0.0001);

		node.setAttribute(SVGAttributes.SVG_CY, "40px"); //$NON-NLS-1$
		e = new SVGEllipseElement(node, null);
		assertEquals(e.getCy(), 40., 0.0001);

		node.setAttribute(SVGAttributes.SVG_CY, "40 cm"); //$NON-NLS-1$
		e = new SVGEllipseElement(node, null);
		assertEquals(e.getCy(), UnitProcessor.INSTANCE.toUserUnit(40, SVGLength.LengthType.CM), 0.0001);
	}

	@Test
	public void testGetCx() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_RX, "10"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_RY, "20"); //$NON-NLS-1$
		SVGEllipseElement e = new SVGEllipseElement(node, null);
		assertEquals(e.getCx(), 0., 0.0001);

		node.setAttribute(SVGAttributes.SVG_CX, "30"); //$NON-NLS-1$
		e = new SVGEllipseElement(node, null);
		assertEquals(e.getCx(), 30., 0.0001);

		node.setAttribute(SVGAttributes.SVG_CX, "40px"); //$NON-NLS-1$
		e = new SVGEllipseElement(node, null);
		assertEquals(e.getCx(), 40., 0.0001);

		node.setAttribute(SVGAttributes.SVG_CX, "40 cm"); //$NON-NLS-1$
		e = new SVGEllipseElement(node, null);
		assertEquals(e.getCx(), UnitProcessor.INSTANCE.toUserUnit(40, SVGLength.LengthType.CM), 0.0001);
	}

	@Test
	public void testEnableRendering() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_RX, "0"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_RY, "0"); //$NON-NLS-1$
		SVGEllipseElement e = new SVGEllipseElement(node, null);
		assertFalse(e.enableRendering());

		node.setAttribute(SVGAttributes.SVG_RX, "10"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_RY, "0"); //$NON-NLS-1$
		e = new SVGEllipseElement(node, null);
		assertFalse(e.enableRendering());

		node.setAttribute(SVGAttributes.SVG_RX, "0"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_RY, "10"); //$NON-NLS-1$
		e = new SVGEllipseElement(node, null);
		assertFalse(e.enableRendering());

		node.setAttribute(SVGAttributes.SVG_RX, "10"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_RY, "10"); //$NON-NLS-1$
		e = new SVGEllipseElement(node, null);
		assertTrue(e.enableRendering());
	}

	@Test
	public void testGetRy() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_RX, "10"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_RY, "20"); //$NON-NLS-1$
		SVGEllipseElement e = new SVGEllipseElement(node, null);
		assertEquals(e.getRy(), 20., 0.0001);
		node.setAttribute(SVGAttributes.SVG_RY, "20 pt"); //$NON-NLS-1$
		e = new SVGEllipseElement(node, null);
		assertEquals(e.getRy(), UnitProcessor.INSTANCE.toUserUnit(20, SVGLength.LengthType.PT), 0.0001);
	}

	@Test
	public void testGetRx() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_RX, "10"); //$NON-NLS-1$
		node.setAttribute(SVGAttributes.SVG_RY, "20"); //$NON-NLS-1$
		SVGEllipseElement e = new SVGEllipseElement(node, null);
		assertEquals(e.getRx(), 10., 0.0001);
		node.setAttribute(SVGAttributes.SVG_RX, "10mm"); //$NON-NLS-1$
		e = new SVGEllipseElement(node, null);
		assertEquals(e.getRx(), UnitProcessor.INSTANCE.toUserUnit(10, SVGLength.LengthType.MM), 0.0001);
	}

	@Override
	public String getNameNode() {
		return SVGElements.SVG_ELLIPSE;
	}
}
