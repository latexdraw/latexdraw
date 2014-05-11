package test.svg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.awt.geom.Point2D;

import net.sf.latexdraw.parsers.svg.SVGMatrix;
import net.sf.latexdraw.parsers.svg.SVGTransformList;

import org.junit.Test;

public class TestSVGTransformList{
	@Test
	public void testContructors() {
		SVGTransformList t = new SVGTransformList();
		assertTrue(t.isEmpty());

		t = new SVGTransformList(null);
		assertTrue(t.isEmpty());

		t = new SVGTransformList("");
		assertTrue(t.isEmpty());

		t = new SVGTransformList("translate(2,2)");
		assertFalse(t.isEmpty());
	}


	@Test
	public void testAddTransformations() {
		SVGTransformList t = new SVGTransformList();

		t.addTransformations(null);
		assertTrue(t.isEmpty());

		t.addTransformations("");
		assertTrue(t.isEmpty());

		t.addTransformations("dsqdsq");
		assertTrue(t.isEmpty());

		t.addTransformations("translate");
		assertTrue(t.isEmpty());

		t.addTransformations("translate(,)");
		assertTrue(t.isEmpty());

		t.addTransformations("translate( )");
		assertTrue(t.isEmpty());

		t.addTransformations("scale( 3");
		assertTrue(t.isEmpty());

		t.addTransformations("rotate(a)");
		assertTrue(t.isEmpty());

		t.addTransformations("rotate(1)");
		assertFalse(t.isEmpty());
		t.clear();

		t.addTransformations("rotate(1 , 2 4)");
		assertFalse(t.isEmpty());
		t.clear();

		t.addTransformations("skewY(	1)");
		assertFalse(t.isEmpty());
		t.clear();

		t.addTransformations("skewX(1)");
		assertFalse(t.isEmpty());
		t.clear();

		t.addTransformations("scale(1 1)");
		assertFalse(t.isEmpty());
		t.clear();

		t.addTransformations("translate(1 ,1)");
		assertFalse(t.isEmpty());
		t.clear();

		t.addTransformations("translate(1 1) ,");
		assertFalse(t.isEmpty());
		t.clear();

		t.addTransformations("matrix(1 2 3)");
		assertTrue(t.isEmpty());

		t.addTransformations("matrix(1 2 3 ,4 ,5 ,6)");
		assertFalse(t.isEmpty());
		t.clear();

		t.addTransformations("matrix(1 2 3 ,4 ,5 ,6) translate(2 2)");
		assertEquals(2, t.size());
		t.clear();

		t.addTransformations("rotate ( 2, 3 5), \n skewX( 2	\n)");
		assertEquals(2, t.size());
		t.clear();

		t.addTransformations("rotate ( 2, 3 5)scale( 2)");
		assertEquals(2, t.size());
		t.clear();

		t.addTransformations("rotate ( 2, 3 5), \n skewX( 2	\n)");
		t.addTransformations("rotate ( 2, 3 5)scale( 2)");
		assertEquals(4, t.size());
	}


	@Test
	public void testTransformPoint() {
		SVGTransformList t = new SVGTransformList();
		Point2D pt1 = new Point2D.Double(1,1);
		Point2D pt2 = new Point2D.Double(3,4);
		Point2D pt3;

		t.addTransformations("translate( 2 3)");
		assertEquals(t.transformPoint(pt1), pt2);

		t.addTransformations("translate( -2 -3)");
		t.addTransformations("rotate(90)");
		pt2.setLocation(0, 1);
		pt1.setLocation(1, 0);

		pt3 = t.transformPoint(pt1);
		assertEquals(Math.rint(pt3.getX()), pt2.getX(), 0.0001);
		assertEquals(Math.rint(pt3.getY()), pt2.getY(), 0.0001);
	}



	@Test
	public void testGetGlobalTransformation() {
		SVGTransformList t = new SVGTransformList();
		SVGMatrix m;

		m = t.getGlobalTransformationMatrix();
		assertNull(m);

		t.addTransformations("scale( 2 3)");
		m = t.getGlobalTransformationMatrix();

		assertEquals(m.getA(), 2., 0.0001);
		assertEquals(m.getB(), 0., 0.0001);
		assertEquals(m.getC(), 0., 0.0001);
		assertEquals(m.getD(), 3., 0.0001);
		assertEquals(m.getE(), 0., 0.0001);
		assertEquals(m.getF(), 0., 0.0001);

		t.addTransformations("skewX( 0.5	\n)");
		m = t.getGlobalTransformationMatrix();

		assertEquals(m.getA(), 2., 0.0001);
		assertEquals(m.getB(), 0., 0.0001);
		assertEquals(m.getC(), 2.*Math.tan(Math.toRadians(0.5)), 0.0001);
		assertEquals(m.getD(), 3., 0.0001);
		assertEquals(m.getE(), 0., 0.0001);
		assertEquals(m.getF(), 0., 0.0001);
	}
}
