package net.sf.latexdraw.parsers.svg;

import java.awt.geom.Point2D;
import java.text.ParseException;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestSVGPolygonElement extends AbstractTestSVGElement {
	SVGPolygonElement pl;

	@Test
	public void testEnableRendering() throws MalformedSVGDocument, ParseException {
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10 20,20");
		pl = new SVGPolygonElement(node, null);
		assertTrue(pl.enableRendering());
	}

	@Test
	public void testSetPoints() throws ParseException, MalformedSVGDocument {
		String path = "10,10 20,20";
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10 20,20");
		pl = new SVGPolygonElement(node, null);
		assertEquals(pl.getPoints(), path);
	}

	@Test
	public void testSetPoints2() throws ParseException, MalformedSVGDocument {
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10 20,20");
		pl = new SVGPolygonElement(node, null);
		pl.setPoints("11,12 21,22");
		assertEquals(2, pl.getPoints2D().size());
		assertEquals(new Point2D.Double(11d, 12d), pl.getPoints2D().get(0));
		assertEquals(new Point2D.Double(21d, 22d), pl.getPoints2D().get(pl.getPoints2D().size() - 1));
	}

	@Test(expected = MalformedSVGDocument.class)
	public void testSetPointsFail() throws ParseException, MalformedSVGDocument {
		SVGPolygonElement pl = new SVGPolygonElement(node, null);
		pl.setPoints("10,,20fdsf");
	}

	@Test
	public void testGetPoints() throws MalformedSVGDocument, ParseException {
		String path = "10,10 20,20";
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10 20,20");
		SVGPolygonElement pl = new SVGPolygonElement(node, null);
		assertEquals(pl.getPoints(), path);
	}

	@Test
	public void testGetPoints2D() throws MalformedSVGDocument, ParseException {
		String path = "	10\t ,\n	10 	\t 	20 \t\n\t\r,	\n20 	\r30,30	\n";
		node.setAttribute(SVGAttributes.SVG_POINTS, path);
		SVGPolygonElement pl = new SVGPolygonElement(node, null);
		List<Point2D> pts;
		pts = pl.getPoints2D();
		assertEquals(3, pts.size());
		assertEquals(new Point2D.Double(10, 10), pts.get(0));
		assertEquals(new Point2D.Double(20, 20), pts.get(1));
		assertEquals(new Point2D.Double(30, 30), pts.get(pts.size() - 1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testContructorFail1() throws MalformedSVGDocument, ParseException {
		new SVGPolygonElement(null, null);
	}

	@Test(expected = MalformedSVGDocument.class)
	public void testContructorFail2() throws MalformedSVGDocument, ParseException {
		new SVGPolygonElement(node, null);
	}

	@Test
	public void testContructorOK1() throws MalformedSVGDocument, ParseException {
		node.setAttribute(SVGAttributes.SVG_POINTS, "");
		new SVGPolygonElement(node, null);
	}

	@Test(expected = ParseException.class)
	public void testContructorFail5() throws MalformedSVGDocument, ParseException {
		node.setAttribute(SVGAttributes.SVG_POINTS, "dsqdgfd");
		new SVGPolygonElement(node, null);
	}

	@Test
	public void testContructorOK2() throws MalformedSVGDocument, ParseException {
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10");
		new SVGPolygonElement(node, null);
	}

	@Test(expected = ParseException.class)
	public void testContructorFail7() throws MalformedSVGDocument, ParseException {
		node.setAttribute(SVGAttributes.SVG_POINTS, ",");
		new SVGPolygonElement(node, null);
	}

	@Test
	public void testContructorOK3() throws MalformedSVGDocument, ParseException {
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10 20,20");
		new SVGPolygonElement(node, null);
	}

	@Override
	public String getNameNode() {
		return SVGElements.SVG_POLYGON;
	}
}
