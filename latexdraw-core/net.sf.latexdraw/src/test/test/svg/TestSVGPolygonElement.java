package test.svg;

import java.awt.geom.Point2D;
import java.text.ParseException;
import java.util.List;

import net.sf.latexdraw.parsers.svg.MalformedSVGDocument;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGElements;
import net.sf.latexdraw.parsers.svg.SVGPolygonElement;

import org.junit.Test;

public class TestSVGPolygonElement extends AbstractTestSVGElement {
	@Test
	public void testEnableRendering() throws MalformedSVGDocument, ParseException {
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10 20,20");
		SVGPolygonElement pl = new SVGPolygonElement(node, null);
		assertTrue(pl.enableRendering());
	}


	@Test
	public void testSetPoints() throws ParseException, MalformedSVGDocument {
		String path = "10,10 20,20";
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10 20,20");
		SVGPolygonElement pl = new SVGPolygonElement(node, null);

		assertEquals(pl.getPoints(), path);
		pl.setPoints("10,10 20,20");
		assertEquals(2, pl.getPoints2D().size());
		assertEquals(new Point2D.Double(10, 10), pl.getPoints2D().get(0));
		assertEquals(new Point2D.Double(20, 20), pl.getPoints2D().get(pl.getPoints2D().size()-1));

		try {
			pl.setPoints("10,,20fdsf");
			fail();
		}
		catch(ParseException e) { /* */ }
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
		String path = "	  10\t ,\n	10 	\t 	20 \t\n\t\r,	\n20 	\r30,30	\n";

		node.setAttribute(SVGAttributes.SVG_POINTS, path);
		SVGPolygonElement pl = new SVGPolygonElement(node, null);
		List<Point2D> pts;

		pts = pl.getPoints2D();
		assertNotNull(pts);
		assertEquals(3, pts.size());
		assertEquals(new Point2D.Double(10, 10), pts.get(0));
		assertEquals(new Point2D.Double(20, 20), pts.get(1));
		assertEquals(new Point2D.Double(30, 30), pts.get(pts.size()-1));
	}



	@SuppressWarnings("unused")
	@Test
	public void testContructor() throws MalformedSVGDocument, ParseException {
		try {
			new SVGPolygonElement(null, null);
			fail();
		}
		catch(Exception e){/**/}

		try {
			new SVGPolygonElement(node, null);
			fail();
		}
		catch(MalformedSVGDocument e){/**/}
		catch(ParseException e)      {/**/}

		node.setAttribute(SVGAttributes.SVG_POINTS, "");
		new SVGPolygonElement(node, null);

		try {
			node.setAttribute(SVGAttributes.SVG_POINTS, "dsqdgfd");
			new SVGPolygonElement(node, null);
			fail();
		}
		catch(MalformedSVGDocument e){/**/}
		catch(ParseException e)      {/**/}

		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10");
		new SVGPolygonElement(node, null);

		try {
			node.setAttribute(SVGAttributes.SVG_POINTS, ",");
			new SVGPolygonElement(node, null);
			fail();
		}
		catch(MalformedSVGDocument e){/**/}
		catch(ParseException e)      {/**/}

		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10 20,20");
		new SVGPolygonElement(node, null);
	}


	@Override
	public String getNameNode() {
		return SVGElements.SVG_POLYGON;
	}
}
