package net.sf.latexdraw.parser.svg;

import java.awt.geom.Point2D;
import java.util.List;
import net.sf.latexdraw.NoBadaboomCheck;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSVGPolygonElement extends TestBaseSVGElement {
	SVGPolygonElement pl;

	@Test
	void testEnableRendering() {
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10 20,20");
		pl = new SVGPolygonElement(node, null);
		assertTrue(pl.enableRendering());
	}

	@Test
	void testSetPoints() {
		final String path = "10,10 20,20";
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10 20,20");
		pl = new SVGPolygonElement(node, null);
		assertEquals(pl.getPoints(), path);
	}

	@Test
	void testSetPoints2() {
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10 20,20");
		pl = new SVGPolygonElement(node, null);
		pl.setPoints("11,12 21,22");
		assertEquals(2, pl.getPoints2D().size());
		assertEquals(new Point2D.Double(11d, 12d), pl.getPoints2D().get(0));
		assertEquals(new Point2D.Double(21d, 22d), pl.getPoints2D().get(pl.getPoints2D().size() - 1));
	}

	@Test
	@NoBadaboomCheck
	void testSetPointsFail() {
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10 20,20");
		final SVGPolygonElement pl = new SVGPolygonElement(node, null);
		pl.setPoints("10,,20fdsf");
		assertEquals(2, pl.getPoints2D().size());
	}

	@Test
	void testGetPoints() {
		final String path = "10,10 20,20";
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10 20,20");
		final SVGPolygonElement pl = new SVGPolygonElement(node, null);
		assertEquals(pl.getPoints(), path);
	}

	@Test
	void testGetPoints2D() {
		final String path = "	10\t ,\n	10 	\t 	20 \t\n\t\r,	\n20 	\r30,30	\n";
		node.setAttribute(SVGAttributes.SVG_POINTS, path);
		final SVGPolygonElement pl = new SVGPolygonElement(node, null);
		final List<Point2D> pts = pl.getPoints2D();
		assertEquals(3, pts.size());
		assertEquals(new Point2D.Double(10, 10), pts.get(0));
		assertEquals(new Point2D.Double(20, 20), pts.get(1));
		assertEquals(new Point2D.Double(30, 30), pts.get(pts.size() - 1));
	}

	@Test
	void testContructorFail1() {
		assertThrows(IllegalArgumentException.class, () -> new SVGPolygonElement(null, null));
	}

	@Test
	@NoBadaboomCheck
	void testContructorFail2() {
		assertThrows(IllegalArgumentException.class, () -> new SVGPolygonElement(node, null));
	}

	@Test
	@NoBadaboomCheck
	void testContructorOK1() {
		node.setAttribute(SVGAttributes.SVG_POINTS, "");
		assertThrows(IllegalArgumentException.class, () -> new SVGPolygonElement(node, null));
	}

	@Test
	@NoBadaboomCheck
	void testContructorFail5() {
		node.setAttribute(SVGAttributes.SVG_POINTS, "dsqdgfd");
		assertThrows(IllegalArgumentException.class, () -> new SVGPolygonElement(node, null));
	}

	@Test
	void testContructorOK2() {
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10");
		new SVGPolygonElement(node, null);
	}

	@Test
	@NoBadaboomCheck
	void testContructorFail7() {
		node.setAttribute(SVGAttributes.SVG_POINTS, ",");
		assertThrows(IllegalArgumentException.class, () -> new SVGPolygonElement(node, null));
	}

	@Test
	void testContructorOK3() {
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10 20,20");
		new SVGPolygonElement(node, null);
	}

	@Override
	public String getNameNode() {
		return SVGElements.SVG_POLYGON;
	}
}
