package test.glib.views.java2D;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape.BorderPos;
import net.sf.latexdraw.glib.models.interfaces.shape.ISquaredShape;

import org.junit.Test;

public abstract class TestLSquaredView extends TestLShapeView {
	private ISquaredShape getShape() {
		return (ISquaredShape) view.getShape();
	}


	@Override@Test
	public void testContains1() {
		ISquaredShape model = getShape();
		final double thickness = 3.;

		model.setPosition(10, -20);
		model.setWidth(100);
		model.setThickness(thickness);
		model.setBordersPosition(BorderPos.INTO);
		model.setHasDbleBord(false);
		view.update();

		assertFalse(view.contains(null));
		assertFalse(view.contains(ShapeFactory.createPoint(Double.NaN, 0)));
		assertFalse(view.contains(ShapeFactory.createPoint(0, Double.NaN)));
		assertFalse(view.contains(ShapeFactory.createPoint(Double.POSITIVE_INFINITY, 0)));
		assertFalse(view.contains(ShapeFactory.createPoint(0, Double.NEGATIVE_INFINITY)));
	}


	@Override@Test
	public void testContains2() {
		ISquaredShape model = getShape();
		final double thickness = 3.;

		model.setPosition(10, -20);
		model.setWidth(100);
		model.setThickness(thickness);
		model.setBordersPosition(BorderPos.INTO);
		model.setHasDbleBord(false);
		view.update();

		assertFalse(view.contains(Double.NaN, 0));
		assertFalse(view.contains(0, Double.NaN));
		assertFalse(view.contains(Double.POSITIVE_INFINITY, 0));
		assertFalse(view.contains(0, Double.NEGATIVE_INFINITY));
	}


	@Test@Override
	public void testIntersects() {
		ISquaredShape model = getShape();
		final double thickness = 3.;

		model.setPosition(10, -20);
		model.setWidth(100);
		model.setThickness(thickness);
		model.setBordersPosition(BorderPos.INTO);
		model.setHasDbleBord(false);
		view.update();

		assertFalse(view.intersects(null));
		assertFalse(view.intersects(new Rectangle2D.Double(Double.NaN, -25, 10, 10)));
		assertFalse(view.intersects(new Rectangle2D.Double(15, -10, Double.NaN, 10)));
		assertFalse(view.intersects(new Rectangle2D.Double(0, -20, 10, Double.NaN)));
		assertFalse(view.intersects(new Rectangle2D.Double(0, Double.NaN, 10, 10)));
	}



	@Test
	public void testIntersectsInto() {
		ISquaredShape model = getShape();
		final double thickness = 3.;

		model.setPosition(10, -20);
		model.setWidth(100);
		model.setThickness(thickness);
		model.setBordersPosition(BorderPos.INTO);
		model.setHasDbleBord(false);
		view.update();

		assertFalse(view.intersects(new Rectangle2D.Double(15, -20, 10, 10)));
		assertFalse(view.intersects(new Rectangle2D.Double(110, -25, 10, 10)));
		assertFalse(view.intersects(new Rectangle2D.Double(15, -10, 10, 10)));
		assertFalse(view.intersects(new Rectangle2D.Double(0, -20, 10, 10)));

		assertTrue(view.intersects(new Rectangle2D.Double(15, -21, 10, 10)));
		assertTrue(view.intersects(new Rectangle2D.Double(109, -25, 10, 10)));
		assertTrue(view.intersects(new Rectangle2D.Double(15, -29, 10, 10)));
		assertTrue(view.intersects(new Rectangle2D.Double(1, -25, 10, 10)));

		assertFalse(view.intersects(new Rectangle2D.Double(14, -26, 92, 2)));
		model.setFilled(true);
		assertTrue(view.intersects(new Rectangle2D.Double(14, -26, 92, 2)));
	}


	@Test
	public void testIntersectsOut() {
		ISquaredShape model = getShape();
		final double thickness = 3.;

		model.setPosition(10, -20);
		model.setWidth(100);
		model.setThickness(thickness);
		model.setBordersPosition(BorderPos.OUT);
		model.setHasDbleBord(false);
		view.update();

		assertFalse(view.intersects(new Rectangle2D.Double(15, -43, 10, 10)));
		assertFalse(view.intersects(new Rectangle2D.Double(113, -25, 10, 10)));
		assertFalse(view.intersects(new Rectangle2D.Double(15, -17, 10, 10)));
		assertFalse(view.intersects(new Rectangle2D.Double(-3, -25, 10, 10)));

		assertTrue(view.intersects(new Rectangle2D.Double(15, -42, 100, 100)));
		assertTrue(view.intersects(new Rectangle2D.Double(112, -25, 10, 10)));
		assertTrue(view.intersects(new Rectangle2D.Double(15, -18, 10, 10)));
		assertTrue(view.intersects(new Rectangle2D.Double(-2, -25, 10, 10)));

		assertFalse(view.intersects(new Rectangle2D.Double(10, -30, 100, 10)));
		model.setFilled(true);
		assertTrue(view.intersects(new Rectangle2D.Double(10, -30, 100, 10)));
	}



	@Test
	public void testIntersectsMiddle() {
		ISquaredShape model = getShape();
		final double thickness = 3.;

		model.setPosition(10, -20);
		model.setWidth(100);
		model.setThickness(thickness);
		model.setBordersPosition(BorderPos.MID);
		model.setHasDbleBord(false);
		view.update();

		assertFalse(view.intersects(new Rectangle2D.Double(15, -42, 10, 10)));
		assertFalse(view.intersects(new Rectangle2D.Double(112, -25, 10, 10)));
		assertFalse(view.intersects(new Rectangle2D.Double(15, -18, 10, 10)));
		assertFalse(view.intersects(new Rectangle2D.Double(-2, -25, 10, 10)));

		assertTrue(view.intersects(new Rectangle2D.Double(15, -41, 100, 100)));
		assertTrue(view.intersects(new Rectangle2D.Double(111, -25, 10, 10)));
		assertTrue(view.intersects(new Rectangle2D.Double(15, -19, 10, 10)));
		assertTrue(view.intersects(new Rectangle2D.Double(-1, -25, 10, 10)));

		assertFalse(view.intersects(new Rectangle2D.Double(12, -28, 96, 6)));
		model.setFilled(true);
		assertTrue(view.intersects(new Rectangle2D.Double(12, -28, 96, 6)));
	}



	@Test
	public void testIntersectsDbleMiddle() {
		//TODO
	}



	@Test
	public void testIntersectsDbleInner() {
		//TODO
	}



	@Test
	public void testIntersectsDbleOuter() {
		//TODO
	}



	@Override@Test
	public void testUpdateDblePathInside() {
		ISquaredShape model = getShape();
		Path2D path;
		PathIterator pi;
		double[] coords = new double[6];
		final double thickness = 3.;
		final double dble = 1.;
		List<Double> xs = new ArrayList<>();
		List<Double> ys = new ArrayList<>();
		double width = 100;

		model.setPosition(-10, 20);
		model.setWidth(width);
		model.setThickness(thickness);
		model.setDbleBordSep(dble);
		model.setBordersPosition(BorderPos.INTO);
		model.setHasDbleBord(true);
		view.update();
		path = view.getPath();

		pi = path.getPathIterator(null);
		assertEquals(PathIterator.SEG_MOVETO, pi.currentSegment(coords));
		xs.add(coords[0]);
		ys.add(coords[1]);
		pi.next();
		assertEquals(PathIterator.SEG_LINETO, pi.currentSegment(coords));
		xs.add(coords[0]);
		ys.add(coords[1]);
		pi.next();
		assertEquals(PathIterator.SEG_LINETO, pi.currentSegment(coords));
		xs.add(coords[0]);
		ys.add(coords[1]);
		pi.next();
		assertEquals(PathIterator.SEG_LINETO, pi.currentSegment(coords));
		xs.add(coords[0]);
		ys.add(coords[1]);
		pi.next();
		assertEquals(PathIterator.SEG_CLOSE, pi.currentSegment(coords));

		double xMin = Double.MAX_VALUE, yMin = Double.MAX_VALUE;
		double xMax = Double.MIN_VALUE, yMax = Double.MIN_VALUE;
		int i=0, size = xs.size();
		double val;

		while(i<size) {
			val = xs.get(i);
			if(val<xMin) xMin = val;
			if(val>xMax) xMax = val;

			val = ys.get(i);
			if(val<yMin) yMin = val;
			if(val>yMax) yMax = val;

			i++;
		}

		assertEquals(-10.+thickness+dble/2., xMin,0.0001);
		assertEquals(20.-width+thickness+dble/2., yMin,0.0001);
		assertEquals(90.-thickness-dble/2., xMax,0.0001);
		assertEquals(20.-thickness-dble/2., yMax,0.0001);
	}


	@Override@Test
	public void testUpdateDblePathMiddle() {
		ISquaredShape model = getShape();
		Path2D path;
		PathIterator pi;
		double[] coords = new double[6];
		final double thickness = 3.;
		final double dble = 1.;
		List<Double> xs = new ArrayList<>();
		List<Double> ys = new ArrayList<>();
		double width = 100;

		model.setPosition(-10, 20);
		model.setWidth(width);
		model.setThickness(thickness);
		model.setDbleBordSep(dble);
		model.setBordersPosition(BorderPos.MID);
		model.setHasDbleBord(true);
		view.update();
		path = view.getPath();

		pi = path.getPathIterator(null);
		assertEquals(PathIterator.SEG_MOVETO, pi.currentSegment(coords));
		xs.add(coords[0]);
		ys.add(coords[1]);
		pi.next();
		assertEquals(PathIterator.SEG_LINETO, pi.currentSegment(coords));
		xs.add(coords[0]);
		ys.add(coords[1]);
		pi.next();
		assertEquals(PathIterator.SEG_LINETO, pi.currentSegment(coords));
		xs.add(coords[0]);
		ys.add(coords[1]);
		pi.next();
		assertEquals(PathIterator.SEG_LINETO, pi.currentSegment(coords));
		xs.add(coords[0]);
		ys.add(coords[1]);
		pi.next();
		assertEquals(PathIterator.SEG_CLOSE, pi.currentSegment(coords));

		double xMin = Double.MAX_VALUE, yMin = Double.MAX_VALUE;
		double xMax = Double.MIN_VALUE, yMax = Double.MIN_VALUE;
		int i=0, size = xs.size();
		double val;

		while(i<size) {
			val = xs.get(i);
			if(val<xMin) xMin = val;
			if(val>xMax) xMax = val;

			val = ys.get(i);
			if(val<yMin) yMin = val;
			if(val>yMax) yMax = val;

			i++;
		}

		assertEquals(-10., xMin,0.0001);
		assertEquals(20.-width, yMin,0.0001);
		assertEquals(90., xMax,0.0001);
		assertEquals(20., yMax,0.0001);
	}


	@Override@Test
	public void testUpdateDblePathOutside() {
		ISquaredShape model = getShape();
		Path2D path;
		PathIterator pi;
		double[] coords = new double[6];
		final double thickness = 3.;
		final double dble = 1.;
		List<Double> xs = new ArrayList<>();
		List<Double> ys = new ArrayList<>();
		double width = 100;

		model.setPosition(-10, 20);
		model.setWidth(width);
		model.setThickness(thickness);
		model.setDbleBordSep(dble);
		model.setBordersPosition(BorderPos.OUT);
		model.setHasDbleBord(true);
		view.update();
		path = view.getPath();

		pi = path.getPathIterator(null);
		assertEquals(PathIterator.SEG_MOVETO, pi.currentSegment(coords));
		xs.add(coords[0]);
		ys.add(coords[1]);
		pi.next();
		assertEquals(PathIterator.SEG_LINETO, pi.currentSegment(coords));
		xs.add(coords[0]);
		ys.add(coords[1]);
		pi.next();
		assertEquals(PathIterator.SEG_LINETO, pi.currentSegment(coords));
		xs.add(coords[0]);
		ys.add(coords[1]);
		pi.next();
		assertEquals(PathIterator.SEG_LINETO, pi.currentSegment(coords));
		xs.add(coords[0]);
		ys.add(coords[1]);
		pi.next();
		assertEquals(PathIterator.SEG_CLOSE, pi.currentSegment(coords));

		double xMin = Double.MAX_VALUE, yMin = Double.MAX_VALUE;
		double xMax = Double.MIN_VALUE, yMax = Double.MIN_VALUE;
		int i=0, size = xs.size();
		double val;

		while(i<size) {
			val = xs.get(i);
			if(val<xMin) xMin = val;
			if(val>xMax) xMax = val;

			val = ys.get(i);
			if(val<yMin) yMin = val;
			if(val>yMax) yMax = val;

			i++;
		}

		assertEquals(-10.-thickness-dble/2., xMin,0.0001);
		assertEquals(20.-width-thickness-dble/2., yMin,0.0001);
		assertEquals(90.+thickness+dble/2., xMax,0.0001);
		assertEquals(20.+thickness+dble/2., yMax,0.0001);
	}


	@Override@Test
	public void testUpdateGeneralPathInside() {
		ISquaredShape model = getShape();
		Path2D path;
		PathIterator pi;
		double[] coords = new double[6];
		final double thickness = 3.;
		List<Double> xs = new ArrayList<>();
		List<Double> ys = new ArrayList<>();
		double width = 100;

		model.setPosition(-10, 20);
		model.setWidth(width);
		model.setThickness(thickness);
		model.setBordersPosition(BorderPos.INTO);
		view.update();
		path = view.getPath();

		pi = path.getPathIterator(null);
		assertEquals(PathIterator.SEG_MOVETO, pi.currentSegment(coords));
		xs.add(coords[0]);
		ys.add(coords[1]);
		pi.next();
		assertEquals(PathIterator.SEG_LINETO, pi.currentSegment(coords));
		xs.add(coords[0]);
		ys.add(coords[1]);
		pi.next();
		assertEquals(PathIterator.SEG_LINETO, pi.currentSegment(coords));
		xs.add(coords[0]);
		ys.add(coords[1]);
		pi.next();
		assertEquals(PathIterator.SEG_LINETO, pi.currentSegment(coords));
		xs.add(coords[0]);
		ys.add(coords[1]);
		pi.next();
		assertEquals(PathIterator.SEG_CLOSE, pi.currentSegment(coords));

		double xMin = Double.MAX_VALUE, yMin = Double.MAX_VALUE;
		double xMax = Double.MIN_VALUE, yMax = Double.MIN_VALUE;
		int i=0, size = xs.size();
		double val;

		while(i<size) {
			val = xs.get(i);
			if(val<xMin) xMin = val;
			if(val>xMax) xMax = val;

			val = ys.get(i);
			if(val<yMin) yMin = val;
			if(val>yMax) yMax = val;

			i++;
		}

		assertEquals(-10.+thickness/2., xMin,0.0001);
		assertEquals(20.-width+thickness/2., yMin,0.0001);
		assertEquals(90.-thickness/2., xMax,0.0001);
		assertEquals(20.-thickness/2., yMax,0.0001);
	}


	@Override@Test
	public void testUpdateGeneralPathMiddle() {
		ISquaredShape model = getShape();
		Path2D path;
		PathIterator pi;
		double[] coords = new double[6];
		final double thickness = 3.;
		List<Double> xs = new ArrayList<>();
		List<Double> ys = new ArrayList<>();
		double width = 100;

		model.setPosition(-10, 20);
		model.setWidth(width);
		model.setThickness(thickness);
		model.setBordersPosition(BorderPos.MID);
		view.update();
		path = view.getPath();

		pi = path.getPathIterator(null);
		assertEquals(PathIterator.SEG_MOVETO, pi.currentSegment(coords));
		xs.add(coords[0]);
		ys.add(coords[1]);
		pi.next();
		assertEquals(PathIterator.SEG_LINETO, pi.currentSegment(coords));
		xs.add(coords[0]);
		ys.add(coords[1]);
		pi.next();
		assertEquals(PathIterator.SEG_LINETO, pi.currentSegment(coords));
		xs.add(coords[0]);
		ys.add(coords[1]);
		pi.next();
		assertEquals(PathIterator.SEG_LINETO, pi.currentSegment(coords));
		xs.add(coords[0]);
		ys.add(coords[1]);
		pi.next();
		assertEquals(PathIterator.SEG_CLOSE, pi.currentSegment(coords));

		double xMin = Double.MAX_VALUE, yMin = Double.MAX_VALUE;
		double xMax = Double.MIN_VALUE, yMax = Double.MIN_VALUE;
		int i=0, size = xs.size();
		double val;

		while(i<size) {
			val = xs.get(i);
			if(val<xMin) xMin = val;
			if(val>xMax) xMax = val;

			val = ys.get(i);
			if(val<yMin) yMin = val;
			if(val>yMax) yMax = val;

			i++;
		}

		assertEquals(-10., xMin,0.0001);
		assertEquals(20.-width, yMin,0.0001);
		assertEquals(90., xMax,0.0001);
		assertEquals(20., yMax,0.0001);
	}


	@Override@Test
	public void testUpdateGeneralPathOutside() {
		ISquaredShape model = getShape();
		Path2D path;
		PathIterator pi;
		double[] coords = new double[6];
		final double thickness = 3.;
		List<Double> xs = new ArrayList<>();
		List<Double> ys = new ArrayList<>();
		double width = 100;

		model.setPosition(-10, 20);
		model.setWidth(width);
		model.setThickness(thickness);
		model.setBordersPosition(BorderPos.OUT);
		view.update();
		path = view.getPath();

		pi = path.getPathIterator(null);
		assertEquals(PathIterator.SEG_MOVETO, pi.currentSegment(coords));
		xs.add(coords[0]);
		ys.add(coords[1]);
		pi.next();
		assertEquals(PathIterator.SEG_LINETO, pi.currentSegment(coords));
		xs.add(coords[0]);
		ys.add(coords[1]);
		pi.next();
		assertEquals(PathIterator.SEG_LINETO, pi.currentSegment(coords));
		xs.add(coords[0]);
		ys.add(coords[1]);
		pi.next();
		assertEquals(PathIterator.SEG_LINETO, pi.currentSegment(coords));
		xs.add(coords[0]);
		ys.add(coords[1]);
		pi.next();
		assertEquals(PathIterator.SEG_CLOSE, pi.currentSegment(coords));

		double xMin = Double.MAX_VALUE, yMin = Double.MAX_VALUE;
		double xMax = Double.MIN_VALUE, yMax = Double.MIN_VALUE;
		int i=0, size = xs.size();
		double val;

		while(i<size) {
			val = xs.get(i);
			if(val<xMin) xMin = val;
			if(val>xMax) xMax = val;

			val = ys.get(i);
			if(val<yMin) yMin = val;
			if(val>yMax) yMax = val;

			i++;
		}

		assertEquals(-10.-thickness/2., xMin,0.0001);
		assertEquals(20.-width-thickness/2., yMin,0.0001);
		assertEquals(90.+thickness/2., xMax,0.0001);
		assertEquals(20.+thickness/2., yMax,0.0001);
	}
}
