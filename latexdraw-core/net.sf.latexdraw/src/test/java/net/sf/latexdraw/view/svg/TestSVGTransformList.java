package net.sf.latexdraw.view.svg;

import java.awt.geom.Point2D;
import net.sf.latexdraw.data.StringData;
import net.sf.latexdraw.parsers.svg.SVGMatrix;
import net.sf.latexdraw.parsers.svg.SVGTransformList;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class TestSVGTransformList {
	SVGTransformList t;

	@Before
	public void setUp() throws Exception {
		t = new SVGTransformList();
	}

	@Test
	public void testContructorsEmpty() {
		assertTrue(t.isEmpty());
	}

	@Test
	public void testContructorsEmptynull() {
		t = new SVGTransformList(null);
		assertTrue(t.isEmpty());
	}

	@Test
	public void testContructorsEmptyEmpty() {
		t = new SVGTransformList("");
		assertTrue(t.isEmpty());
	}

	@Test
	public void testContructorsNotEmpty() {
		t = new SVGTransformList("translate(2,2)");
		assertFalse(t.isEmpty());
	}

	@Test
	public void testAddTransformationsNULL() {
		t.addTransformations(null);
		assertTrue(t.isEmpty());
	}

	@Test
	public void testAddTransformationsEmpty() {
		t.addTransformations("");
		assertTrue(t.isEmpty());
	}

	@Theory
	public void testAddTransformationsInvalid(@StringData(vals = {"dsqdsq", "translate", "matrix(1 2 3)"}) final String data) {
		t.addTransformations(data);
		assertTrue(t.isEmpty());
	}

	@Theory
	public void testAddTransformationsOK(@StringData(vals = {"rotate(1)", "rotate(1 , 2 4)", "skewY(	1)", "skewX(1)", "scale(1 1)",
		"translate(1 ,1)", "matrix(1 2 3 ,4 ,5 ,6)", "translate(1 1) ,"}) final String data) {
		t.addTransformations(data);
		assertEquals(1, t.size());
	}

	@Theory
	public void testAddTransformationsOK2(@StringData(vals = {"matrix(1 2 3 ,4 ,5 ,6) translate(2 2)", "rotate ( 2, 3 5), \n skewX( 2	\n)",
		"rotate ( 2, 3 5)scale( 2)"}) final String data) {
		t.addTransformations(data);
		assertEquals(2, t.size());
	}

	@Test
	public void testAddTransformationsOK4() {
		t.addTransformations("rotate ( 2, 3 5), \n skewX( 2	\n)");
		t.addTransformations("rotate ( 2, 3 5)scale( 2)");
		assertEquals(4, t.size());
	}

	@Test
	public void testTransformPoint() {
		Point2D pt1 = new Point2D.Double(1d, 1d);
		Point2D pt2 = new Point2D.Double(3d, 4d);
		t.addTransformations("translate( 2 3)");
		assertEquals(pt2, t.transformPoint(pt1));
	}

	@Test
	public void testTransformPoint2() {
		Point2D pt1 = new Point2D.Double(1d, 1d);
		Point2D pt2 = new Point2D.Double(3d, 4d);
		Point2D pt3;
		t.addTransformations("translate( 2 3)");
		t.addTransformations("translate( -2 -3)");
		t.addTransformations("rotate(90)");
		pt2.setLocation(0d, 1d);
		pt1.setLocation(1d, 0d);
		pt3 = t.transformPoint(pt1);
		assertEquals(Math.rint(pt3.getX()), pt2.getX(), 0.0001);
		assertEquals(Math.rint(pt3.getY()), pt2.getY(), 0.0001);
	}

	@Test
	public void testGetGlobalTransformationDefault() {
		SVGMatrix m = t.getGlobalTransformationMatrix();
		assertNull(m);
	}

	@Test
	public void testGetGlobalTransformation1() {
		t.addTransformations("scale( 2 3)");
		SVGMatrix m = t.getGlobalTransformationMatrix();
		assertEquals(2d, m.getA(), 0.0001);
		assertEquals(0d, m.getB(), 0.0001);
		assertEquals(0d, m.getC(), 0.0001);
		assertEquals(3d, m.getD(), 0.0001);
		assertEquals(0d, m.getE(), 0.0001);
		assertEquals(0d, m.getF(), 0.0001);
	}

	@Test
	public void testGetGlobalTransformation2() {
		t.addTransformations("scale( 2 3)");
		t.addTransformations("skewX( 0.5	\n)");
		SVGMatrix m = t.getGlobalTransformationMatrix();
		assertEquals(2d, m.getA(), 0.0001);
		assertEquals(0d, m.getB(), 0.0001);
		assertEquals(2d * Math.tan(Math.toRadians(0.5)), m.getC(), 0.0001);
		assertEquals(3d, m.getD(), 0.0001);
		assertEquals(0d, m.getE(), 0.0001);
		assertEquals(0d, m.getF(), 0.0001);
	}
}
