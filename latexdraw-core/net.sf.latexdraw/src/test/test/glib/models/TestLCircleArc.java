package test.glib.models;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.shape.IArc;
import net.sf.latexdraw.glib.models.interfaces.shape.ICircle;
import net.sf.latexdraw.glib.models.interfaces.shape.ICircleArc;
import net.sf.latexdraw.glib.models.interfaces.shape.IPositionShape;
import net.sf.latexdraw.glib.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.glib.models.interfaces.shape.IShape;

import org.junit.Before;
import org.junit.Test;

import test.HelperTest;
import test.glib.models.interfaces.TestICircleArc;

public class TestLCircleArc<T extends ICircleArc> extends TestICircleArc<T> {
	@Before
	public void setUp() {
		shape  = (T) ShapeFactory.createCircleArc(false);
		shape2 = (T) ShapeFactory.createCircleArc(false);
	}

	@Override
	@Test
	public void testGetStartPoint(){
		shape.setWidth(2.0);
		shape.setPosition(-1, -1);

		shape.setAngleStart(0);
		HelperTest.assertEqualsDouble(1, shape.getStartPoint().getX());
		HelperTest.assertEqualsDouble(0, shape.getStartPoint().getY());

		shape.setAngleStart(Math.PI/2.);
		HelperTest.assertEqualsDouble(0, shape.getStartPoint().getX());
		HelperTest.assertEqualsDouble(1, shape.getStartPoint().getY());

		shape.setAngleStart(Math.PI);
		HelperTest.assertEqualsDouble(-1, shape.getStartPoint().getX());
		HelperTest.assertEqualsDouble(0, shape.getStartPoint().getY());

		shape.setAngleStart(1.5*Math.PI);
		HelperTest.assertEqualsDouble(0, shape.getStartPoint().getX());
		HelperTest.assertEqualsDouble(-1, shape.getStartPoint().getY());

		shape.setAngleStart(2.*Math.PI);
		HelperTest.assertEqualsDouble(1, shape.getStartPoint().getX());
		HelperTest.assertEqualsDouble(0, shape.getStartPoint().getY());

		shape.setAngleStart(-2.*Math.PI);
		HelperTest.assertEqualsDouble(1, shape.getStartPoint().getX());
		HelperTest.assertEqualsDouble(0, shape.getStartPoint().getY());

		shape.setAngleStart(-Math.PI/2.);
		HelperTest.assertEqualsDouble(0, shape.getStartPoint().getX());
		HelperTest.assertEqualsDouble(-1, shape.getStartPoint().getY());

		shape.setAngleStart(-Math.PI);
		HelperTest.assertEqualsDouble(-1, shape.getStartPoint().getX());
		HelperTest.assertEqualsDouble(0, shape.getStartPoint().getY());
	}


	@Override
	@Test
	public void testGetEndPoint() {
		//TODO
	}


	@Override
	@Test
	public void testIsTypeOf() {
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(IRectangle.class));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(IPositionShape.class));
		assertTrue(shape.isTypeOf(IArc.class));
		assertTrue(shape.isTypeOf(ICircleArc.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}


	@Test
	public void testConstructors() {
		//TODO
	}


	@Override
	public void testGetTopLeftPoint() {
		// TODO Auto-generated method stub

	}


	@Override
	public void testGetTopRightPoint() {
		// TODO Auto-generated method stub

	}


	@Override
	public void testGetBottomRightPoint() {
		// TODO Auto-generated method stub

	}


	@Override
	public void testGetBottomLeftPoint() {
		// TODO Auto-generated method stub

	}


	@Override
	public void testMirrorHorizontal() {
		// TODO Auto-generated method stub

	}


	@Override
	public void testMirrorVertical() {
		// TODO Auto-generated method stub

	}
}
