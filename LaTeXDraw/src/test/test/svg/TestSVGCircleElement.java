package test.svg;

import net.sf.latexdraw.parsers.svg.*;
import net.sf.latexdraw.parsers.svg.parsers.SVGLength;
import net.sf.latexdraw.parsers.svg.parsers.UnitProcessor;

import org.junit.Before;
import org.junit.Test;

public class TestSVGCircleElement extends AbstractTestSVGElement {
	@Override
	@Before
	public void setUp() {
		doc = new SVGDocument();
        node = (SVGElement)doc.createElement(SVGElements.SVG_CIRCLE);
	}



	@SuppressWarnings("unused")
	@Test
	public void testContructor() throws MalformedSVGDocument {
		try {
			new SVGCircleElement(null, null);
			fail();
		}
		catch(Exception e){/**/}

		try {
			new SVGCircleElement(node, null);
			fail();
		}
		catch(MalformedSVGDocument e){/**/}

		try {
			node.setAttribute(SVGAttributes.SVG_R, "dsd"); //$NON-NLS-1$
			new SVGCircleElement(node, null);
			fail();
		}
		catch(MalformedSVGDocument e){/**/}

		try {
			node.setAttribute(SVGAttributes.SVG_R, "-1"); //$NON-NLS-1$
			new SVGCircleElement(node, null);
			fail();
		}
		catch(MalformedSVGDocument e){/**/}

		node.setAttribute(SVGAttributes.SVG_R, "10"); //$NON-NLS-1$
		new SVGCircleElement(node, null);
	}


	@Test
	public void testGetCy() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "10"); //$NON-NLS-1$
		SVGCircleElement e = new SVGCircleElement(node, null);
		assertEquals(e.getCy(), 0.);

		node.setAttribute(SVGAttributes.SVG_CY, "40"); //$NON-NLS-1$
		e = new SVGCircleElement(node, null);
		assertEquals(e.getCy(), 40.);

		node.setAttribute(SVGAttributes.SVG_CY, "40px "); //$NON-NLS-1$
		e = new SVGCircleElement(node, null);
		assertEquals(e.getCy(), 40.);

		node.setAttribute(SVGAttributes.SVG_CY, " 40 cm "); //$NON-NLS-1$
		e = new SVGCircleElement(node, null);
		assertEquals(e.getCy(), UnitProcessor.INSTANCE.toUserUnit(40, SVGLength.LengthType.CM));
	}


	@Test
	public void testGetCx() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "10"); //$NON-NLS-1$
		SVGCircleElement e = new SVGCircleElement(node, null);
		assertEquals(e.getCx(), 0.);

		node.setAttribute(SVGAttributes.SVG_CX, "76.987"); //$NON-NLS-1$
		e = new SVGCircleElement(node, null);
		assertEquals(e.getCx(), 76.987);

		node.setAttribute(SVGAttributes.SVG_CX, "40px "); //$NON-NLS-1$
		e = new SVGCircleElement(node, null);
		assertEquals(e.getCx(), 40.);

		node.setAttribute(SVGAttributes.SVG_CX, " 30.5 mm "); //$NON-NLS-1$
		e = new SVGCircleElement(node, null);
		assertEquals(e.getCx(), UnitProcessor.INSTANCE.toUserUnit(30.5, SVGLength.LengthType.MM));
	}



	@Test
	public void testEnableRendering() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "0"); //$NON-NLS-1$
		SVGCircleElement e = new SVGCircleElement(node, null);
		assertFalse(e.enableRendering());

		node.setAttribute(SVGAttributes.SVG_R, "10"); //$NON-NLS-1$
		e = new SVGCircleElement(node, null);
		assertTrue(e.enableRendering());
	}



	@Test
	public void testGetR() throws MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_R, "20"); //$NON-NLS-1$
		SVGCircleElement e = new SVGCircleElement(node, null);
		assertEquals(e.getR(), 20.);
		node.setAttribute(SVGAttributes.SVG_R, "20 pt"); //$NON-NLS-1$
		e = new SVGCircleElement(node, null);
		assertEquals(e.getR(), UnitProcessor.INSTANCE.toUserUnit(20, SVGLength.LengthType.PT));
	}


	@Override
	public String getNameNode() {
		return SVGElements.SVG_CIRCLE;
	}
}
