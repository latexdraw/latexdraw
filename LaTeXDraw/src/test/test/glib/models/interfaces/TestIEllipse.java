package test.glib.models.interfaces;


import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IEllipse;
import net.sf.latexdraw.glib.models.interfaces.ILine;
import net.sf.latexdraw.glib.models.interfaces.IPoint;

import org.junit.Test;

public abstract class TestIEllipse<T extends IEllipse> extends TestIRectangularShape<T> {
	@Test
	public void testGetRx() {
		shape.setPosition(10, 5);
		shape.setWidth(20);
		shape.setHeight(15);

		assertEquals(10., shape.getRx());
		shape.setHeight(10);
		shape.setWidth(5);

		assertEquals(2.5, shape.getRx());
	}


	@Test
	public void testGetRy() {
		shape.setPosition(10, 5);
		shape.setWidth(20);
		shape.setHeight(15);

		assertEquals(7.5, shape.getRy());
		shape.setHeight(10);
		shape.setWidth(5);

		assertEquals(5., shape.getRy());
	}


	@Test
	public void testSetRx() {
		shape.setPosition(10, 5);
		shape.setRx(10.);
		assertEquals(10., shape.getRx());
		shape.setRx(20.);
		assertEquals(20., shape.getRx());
		shape.setRx(Double.NaN);
		assertEquals(20., shape.getRx());
		shape.setRx(Double.NEGATIVE_INFINITY);
		assertEquals(20., shape.getRx());
		shape.setRx(Double.POSITIVE_INFINITY);
		assertEquals(20., shape.getRx());
	}


	@Test
	public void testSetRy() {
		shape.setPosition(10, 5);
		shape.setRy(10.);
		assertEquals(10., shape.getRy());
		shape.setRy(20.);
		assertEquals(20., shape.getRy());
		shape.setRy(Double.NaN);
		assertEquals(20., shape.getRy());
		shape.setRy(Double.NEGATIVE_INFINITY);
		assertEquals(20., shape.getRy());
		shape.setRy(Double.POSITIVE_INFINITY);
		assertEquals(20., shape.getRy());
	}


	@Test
	public void testGetIntersectionHoriz1() {
		ILine line = DrawingTK.getFactory().createLine(DrawingTK.getFactory().createPoint(-10,0), DrawingTK.getFactory().createPoint(10,0));
		shape.setPosition(-1,1);
		shape.setWidth(2);
		shape.setHeight(2);
		IPoint[] pts = shape.getIntersection(line);

		assertEquals(2, pts.length);
		if(pts[0].getX()<pts[1].getX()) {
			assertEquals(-1., pts[0].getX());
			assertEquals(0., pts[0].getY());
			assertEquals(1., pts[1].getX());
			assertEquals(0., pts[1].getY());
		}else {
			assertEquals(1., pts[0].getX());
			assertEquals(0., pts[0].getY());
			assertEquals(-1., pts[1].getX());
			assertEquals(0., pts[1].getY());
		}
	}


	@Test
	public void testGetIntersectionHoriz2() {
		ILine line = DrawingTK.getFactory().createLine(DrawingTK.getFactory().createPoint(-10,1), DrawingTK.getFactory().createPoint(10,1));
		shape.setPosition(-1,1);
		shape.setWidth(2);
		shape.setHeight(2);
		IPoint[] pts = shape.getIntersection(line);

		assertNotNull(pts);
		assertEquals(1, pts.length);
		assertEquals(0., pts[0].getX());
		assertEquals(1., pts[0].getY());
	}


	@Test
	public void testGetIntersectionHoriz3() {
		ILine line = DrawingTK.getFactory().createLine(DrawingTK.getFactory().createPoint(-10,-1), DrawingTK.getFactory().createPoint(10,-1));
		shape.setPosition(-1,1);
		shape.setWidth(2);
		shape.setHeight(2);
		IPoint[] pts = shape.getIntersection(line);

		assertNotNull(pts);
		assertEquals(1, pts.length);
		assertEquals(-0., pts[0].getX());
		assertEquals(-1., pts[0].getY());
	}



	@Test
	public void testGetIntersectionVert1() {
		ILine line = DrawingTK.getFactory().createLine(DrawingTK.getFactory().createPoint(0,10), DrawingTK.getFactory().createPoint(0,-10));
		shape.setPosition(-1,1);
		shape.setWidth(2);
		shape.setHeight(2);
		IPoint[] pts = shape.getIntersection(line);

		assertNotNull(pts);
		assertEquals(2, pts.length);
		if(pts[0].getY()<pts[1].getY()) {
			assertEquals(0., pts[0].getX());
			assertEquals(-1., pts[0].getY());
			assertEquals(0., pts[1].getX());
			assertEquals(1., pts[1].getY());
		}else {
			assertEquals(0., pts[0].getX());
			assertEquals(1., pts[0].getY());
			assertEquals(0., pts[1].getX());
			assertEquals(-1., pts[1].getY());
		}
	}


	@Test
	public void testGetIntersectionVert2() {
		ILine line = DrawingTK.getFactory().createLine(DrawingTK.getFactory().createPoint(1,-10), DrawingTK.getFactory().createPoint(1,10));
		shape.setPosition(-1,1);
		shape.setWidth(2);
		shape.setHeight(2);
		IPoint[] pts = shape.getIntersection(line);

		assertNotNull(pts);
		assertEquals(1, pts.length);
		assertEquals(1., pts[0].getX());
		assertEquals(0., pts[0].getY());
	}


	@Test
	public void testGetIntersectionVert3() {
		ILine line = DrawingTK.getFactory().createLine(DrawingTK.getFactory().createPoint(-1,-10), DrawingTK.getFactory().createPoint(-1,10));
		shape.setPosition(-1,1);
		shape.setWidth(2);
		shape.setHeight(2);
		IPoint[] pts = shape.getIntersection(line);

		assertNotNull(pts);
		assertEquals(1, pts.length);
		assertEquals(-1., pts[0].getX());
		assertEquals(0., pts[0].getY());
	}


	@Test
	public void testGetA() {
		shape.setPosition(10, 5);
		shape.setWidth(20);
		shape.setHeight(15);

		assertEquals(10., shape.getA());

		shape.setWidth(10);
		shape.setHeight(15);

		assertEquals(7.5, shape.getA());
	}



	@Test
	public void testGetB() {
		shape.setPosition(10, 5);
		shape.setWidth(20);
		shape.setHeight(15);

		assertEquals(7.5, shape.getB());

		shape.setWidth(10);
		shape.setHeight(15);

		assertEquals(5., shape.getB());
	}
}
