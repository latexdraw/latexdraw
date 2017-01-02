package test.glib.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IArc;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.ICircleArc;
import net.sf.latexdraw.models.interfaces.shape.IPositionShape;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IShape;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import test.HelperTest;
import test.glib.models.interfaces.TestICircleArc;

public class TestLCircleArc extends TestICircleArc<ICircleArc> {
	@Before
	public void setUp() {
		shape = ShapeFactory.INST.createCircleArc();
		shape2 = ShapeFactory.INST.createCircleArc();
	}

	@Override
	@Test
	public void testGetStartPoint() {
		shape.setWidth(2.0);
		shape.setPosition(-1, -1);

		shape.setAngleStart(0);
		HelperTest.assertEqualsDouble(1, shape.getStartPoint().getX());
		HelperTest.assertEqualsDouble(-2, shape.getStartPoint().getY());

		shape.setAngleStart(Math.PI / 2.);
		HelperTest.assertEqualsDouble(0, shape.getStartPoint().getX());
		HelperTest.assertEqualsDouble(-3, shape.getStartPoint().getY());

		shape.setAngleStart(Math.PI);
		HelperTest.assertEqualsDouble(-1, shape.getStartPoint().getX());
		HelperTest.assertEqualsDouble(-2, shape.getStartPoint().getY());

		shape.setAngleStart(1.5 * Math.PI);
		HelperTest.assertEqualsDouble(0, shape.getStartPoint().getX());
		HelperTest.assertEqualsDouble(-1, shape.getStartPoint().getY());

		shape.setAngleStart(2. * Math.PI);
		HelperTest.assertEqualsDouble(1, shape.getStartPoint().getX());
		HelperTest.assertEqualsDouble(-2, shape.getStartPoint().getY());

		shape.setAngleStart(-2. * Math.PI);
		HelperTest.assertEqualsDouble(1, shape.getStartPoint().getX());
		HelperTest.assertEqualsDouble(-2, shape.getStartPoint().getY());

		shape.setAngleStart(-Math.PI / 2.);
		HelperTest.assertEqualsDouble(0, shape.getStartPoint().getX());
		HelperTest.assertEqualsDouble(-1, shape.getStartPoint().getY());

		shape.setAngleStart(-Math.PI);
		HelperTest.assertEqualsDouble(-1, shape.getStartPoint().getX());
		HelperTest.assertEqualsDouble(-2, shape.getStartPoint().getY());
	}

	@Override
	@Test
	@Ignore
	public void testGetEndPoint() {
		// TODO
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
	@Ignore
	public void testConstructors() {
		// TODO
	}

	@Override
	@Ignore
	@Test
	public void testGetTopLeftPoint() {
		// TODO Auto-generated method stub

	}

	@Override
	@Ignore
	@Test
	public void testGetTopRightPoint() {
		// TODO Auto-generated method stub

	}

	@Override
	@Ignore
	@Test
	public void testGetBottomRightPoint() {
		// TODO Auto-generated method stub

	}

	@Override
	@Ignore
	@Test
	public void testGetBottomLeftPoint() {
		// TODO Auto-generated method stub

	}

	@Override
	@Ignore
	@Test
	public void testMirrorHorizontal() {
		// TODO Auto-generated method stub

	}

	@Override
	@Ignore
	@Test
	public void testMirrorVertical() {
		// TODO Auto-generated method stub

	}
}
