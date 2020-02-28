package net.sf.latexdraw.parser.svg;

import java.awt.geom.Point2D;
import java.util.List;
import net.sf.latexdraw.NoBadaboomCheck;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSVGPolylineElement extends TestBaseSVGElement {
	SVGPolyLineElement pl;

	@Test
	void testGetPoints() {
		final String path = "10,10 20,20";
		node.setAttribute(SVGAttributes.SVG_POINTS, path);
		pl = new SVGPolyLineElement(node, null);
		assertEquals(pl.getPoints(), path);
	}

	@Test
	void testEnableRendering() {
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10 20,20");
		pl = new SVGPolyLineElement(node, null);
		assertTrue(pl.enableRendering());
	}

	@Test
	void testSetPoints() {
		final String path = "10,10 20,20";
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10 20,20");
		pl = new SVGPolyLineElement(node, null);
		assertEquals(pl.getPoints(), path);
	}

	@Test
	void testSetPoints2() {
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10 20,20");
		pl = new SVGPolyLineElement(node, null);
		pl.setPoints("11,12 22,23");
		assertEquals(2, pl.getPoints2D().size());
		assertEquals(new Point2D.Double(11, 12), pl.getPoints2D().get(0));
		assertEquals(new Point2D.Double(22, 23), pl.getPoints2D().get(pl.getPoints2D().size() - 1));
	}

	@Test
	@NoBadaboomCheck
	void testSetPointsFail() {
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10 20,20");
		pl = new SVGPolyLineElement(node, null);
		pl.setPoints("10,,20fdsf");
		assertEquals(2, pl.getPoints2D().size());
	}

	@Test
	void testGetPoints2D() {
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

	@Test
	void testContructorFail1() {
		assertThrows(IllegalArgumentException.class, () -> new SVGPolyLineElement(null, null));
	}

	@Test
	@NoBadaboomCheck
	void testContructorFail2() {
		assertThrows(IllegalArgumentException.class, () -> new SVGPolyLineElement(node, null));
	}

	@Test
	@NoBadaboomCheck
	void testContructorFail3() {
		node.setAttribute(SVGAttributes.SVG_POINTS, "");
		assertThrows(IllegalArgumentException.class, () -> new SVGPolyLineElement(node, null));
	}

	@Test
	@NoBadaboomCheck
	void testContructorFail4() {
		node.setAttribute(SVGAttributes.SVG_POINTS, "dsqdgfd");
		assertThrows(IllegalArgumentException.class, () -> new SVGPolyLineElement(node, null));
	}

	@Test
	void testContructorOK1() {
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10");
		new SVGPolyLineElement(node, null);
	}

	@Test
	@NoBadaboomCheck
	void testContructorOK2() {
		node.setAttribute(SVGAttributes.SVG_POINTS, ",");
		assertThrows(IllegalArgumentException.class, () -> new SVGPolyLineElement(node, null));
	}

	@Test
	void testContructorOK3() {
		node.setAttribute(SVGAttributes.SVG_POINTS, "10,10 20,20");
		new SVGPolyLineElement(node, null);
	}

	@Override
	public String getNameNode() {
		return SVGElements.SVG_POLY_LINE;
	}
}
