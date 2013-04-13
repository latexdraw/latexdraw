package test.glib.models.interfaces;


import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import static junit.framework.Assert.*;
import net.sf.latexdraw.glib.models.interfaces.IPoint;
import net.sf.latexdraw.glib.models.interfaces.IPositionShape;

import org.junit.Test;

public abstract class TestIPositionShape<T extends IPositionShape> extends TestIShape<T> {
	@Override
	public void testTranslate() {
		shape.setPosition(0, 0);
		shape.translate(100, 50);
		assertEquals(100., shape.getPosition().getX());
		assertEquals(50., shape.getPosition().getY());
	}


	@Test
	public void testGetSetX() {
		shape.setX(10.);
		assertEquals(10., shape.getX());
		shape.setX(-20.);
		assertEquals(-20., shape.getX());
		shape.setX(Double.NaN);
		assertEquals(-20., shape.getX());
		shape.setX(Double.POSITIVE_INFINITY);
		assertEquals(-20., shape.getX());
		shape.setX(Double.NEGATIVE_INFINITY);
		assertEquals(-20., shape.getX());
	}


	@Test
	public void testGetSetY() {
		shape.setY(10.);
		assertEquals(10., shape.getY());
		shape.setY(-20.);
		assertEquals(-20., shape.getY());
		shape.setY(Double.NaN);
		assertEquals(-20., shape.getY());
		shape.setY(Double.POSITIVE_INFINITY);
		assertEquals(-20., shape.getY());
		shape.setY(Double.NEGATIVE_INFINITY);
		assertEquals(-20., shape.getY());
	}



	@Test
	public void testGetSetPosition() {
		IPoint pt = DrawingTK.getFactory().createPoint(15, 25);

		shape.setPosition(pt);
		assertEquals(pt.getX(), shape.getPosition().getX());
		assertEquals(pt.getY(), shape.getPosition().getY());
		assertEquals(15., shape.getX());
		assertEquals(25., shape.getY());
		shape.setPosition(null);
		assertEquals(pt.getX(), shape.getPosition().getX());
		assertEquals(pt.getY(), shape.getPosition().getY());
		shape.setPosition(DrawingTK.getFactory().createPoint(Double.NaN, 0));
		assertEquals(pt.getX(), shape.getPosition().getX());
		assertEquals(pt.getY(), shape.getPosition().getY());
		shape.setPosition(DrawingTK.getFactory().createPoint(Double.NEGATIVE_INFINITY, 0));
		assertEquals(pt.getX(), shape.getPosition().getX());
		assertEquals(pt.getY(), shape.getPosition().getY());
		shape.setPosition(DrawingTK.getFactory().createPoint(Double.POSITIVE_INFINITY, 0));
		assertEquals(pt.getX(), shape.getPosition().getX());
		assertEquals(pt.getY(), shape.getPosition().getY());
		shape.setPosition(DrawingTK.getFactory().createPoint(0, Double.NaN));
		assertEquals(pt.getX(), shape.getPosition().getX());
		assertEquals(pt.getY(), shape.getPosition().getY());
		shape.setPosition(DrawingTK.getFactory().createPoint(0, Double.NEGATIVE_INFINITY));
		assertEquals(pt.getX(), shape.getPosition().getX());
		assertEquals(pt.getY(), shape.getPosition().getY());
		shape.setPosition(DrawingTK.getFactory().createPoint(0, Double.POSITIVE_INFINITY));
		assertEquals(pt.getX(), shape.getPosition().getX());
		assertEquals(pt.getY(), shape.getPosition().getY());

		shape.setPosition(15, 25);
		assertEquals(pt.getX(), shape.getPosition().getX());
		assertEquals(pt.getY(), shape.getPosition().getY());
		assertEquals(15., shape.getX());
		assertEquals(25., shape.getY());
		shape.setPosition(Double.NaN, 0);
		assertEquals(pt.getX(), shape.getPosition().getX());
		assertEquals(pt.getY(), shape.getPosition().getY());
		shape.setPosition(Double.NEGATIVE_INFINITY, 0);
		assertEquals(pt.getX(), shape.getPosition().getX());
		assertEquals(pt.getY(), shape.getPosition().getY());
		shape.setPosition(Double.POSITIVE_INFINITY, 0);
		assertEquals(pt.getX(), shape.getPosition().getX());
		assertEquals(pt.getY(), shape.getPosition().getY());
		shape.setPosition(0, Double.NaN);
		assertEquals(pt.getX(), shape.getPosition().getX());
		assertEquals(pt.getY(), shape.getPosition().getY());
		shape.setPosition(0, Double.NEGATIVE_INFINITY);
		assertEquals(pt.getX(), shape.getPosition().getX());
		assertEquals(pt.getY(), shape.getPosition().getY());
		shape.setPosition(0, Double.POSITIVE_INFINITY);
		assertEquals(pt.getX(), shape.getPosition().getX());
		assertEquals(pt.getY(), shape.getPosition().getY());
	}
}
