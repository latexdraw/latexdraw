package net.sf.latexdraw.parsers.svg;

import java.awt.geom.Point2D;
import java.text.ParseException;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestSVGPolylineElement extends AbstractTestSVGElement {
	SVGPolyLineElement pl;

	@Test
	public void testGetPoints() throws MalformedSVGDocument, ParseException {
		final String path = "10,10 20,20";
		node.setAttribute(SVGAttributes.SVG_POINTS, path);
		pl = new SVGPolyLineElement(node, null);
		assertEquals(pl.getPoints(), path);
	}

	@Test
	public void testEnableRendering() throws MalformedSVGDocument, ParseException {
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10 20,20");
		pl = new SVGPolyLineElement(node, null);
		assertTrue(pl.enableRendering());
	}

	@Test
	public void testSetPoints() throws MalformedSVGDocument, ParseException {
		final String path = "10,10 20,20";
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10 20,20");
		pl = new SVGPolyLineElement(node, null);
		assertEquals(pl.getPoints(), path);
	}

	@Test
	public void testSetPoints2() throws MalformedSVGDocument, ParseException {
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10 20,20");
		pl = new SVGPolyLineElement(node, null);
		pl.setPoints("11,12 22,23");
		assertEquals(2, pl.getPoints2D().size());
		assertEquals(new Point2D.Double(11, 12), pl.getPoints2D().get(0));
		assertEquals(new Point2D.Double(22, 23), pl.getPoints2D().get(pl.getPoints2D().size() - 1));
	}

	@Test(expected = MalformedSVGDocument.class)
	public void testSetPointsFail() throws MalformedSVGDocument, ParseException {
		pl = new SVGPolyLineElement(node, null);
		pl.setPoints("10,,20fdsf");
	}

	@Test
	public void testGetPoints2D() throws MalformedSVGDocument, ParseException {
		final String path = "	  10\t ,\n	10 	\t 	20 \t\n\t\r,	\n20 	\r30,30	\n";
		node.setAttribute(SVGAttributes.SVG_POINTS, path);
		final SVGPolyLineElement pl = new SVGPolyLineElement(node, null);
		final List<Point2D> pts;
		pts = pl.getPoints2D();
		assertEquals(3, pts.size());
		assertEquals(new Point2D.Double(10, 10), pts.get(0));
		assertEquals(new Point2D.Double(20, 20), pts.get(1));
		assertEquals(new Point2D.Double(30, 30), pts.get(pts.size() - 1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testContructorFail1() throws MalformedSVGDocument, ParseException {
		new SVGPolyLineElement(null, null);
	}

	@Test(expected = MalformedSVGDocument.class)
	public void testContructorFail2() throws MalformedSVGDocument, ParseException {
		new SVGPolyLineElement(node, null);
	}

	@Test(expected = MalformedSVGDocument.class)
	public void testContructorFail3() throws MalformedSVGDocument, ParseException {
		node.setAttribute(SVGAttributes.SVG_POINTS, "");
		new SVGPolyLineElement(node, null);
	}

	@Test(expected = ParseException.class)
	public void testContructorFail4() throws MalformedSVGDocument, ParseException {
		node.setAttribute(SVGAttributes.SVG_POINTS, "dsqdgfd");
		new SVGPolyLineElement(node, null);
	}

	@Test
	public void testContructorOK1() throws MalformedSVGDocument, ParseException {
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10");
		new SVGPolyLineElement(node, null);
	}

	@Test(expected = ParseException.class)
	public void testContructorOK2() throws MalformedSVGDocument, ParseException {
		node.setAttribute(SVGAttributes.SVG_POINTS, ",");
		new SVGPolyLineElement(node, null);
	}

	@Test
	public void testContructorOK3() throws MalformedSVGDocument, ParseException {
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10 20,20");
		new SVGPolyLineElement(node, null);
	}

	@Override
	public String getNameNode() {
		return SVGElements.SVG_POLY_LINE;
	}
}
