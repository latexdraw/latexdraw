package test.models;

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
import test.models.interfaces.TestICircleArc;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestLCircleArc extends TestICircleArc<ICircleArc> implements HelperTest {
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
		assertEqualsDouble(1, shape.getStartPoint().getX());
		assertEqualsDouble(-2, shape.getStartPoint().getY());
	}

	@Test
	public void testGetStartPointPIDIV2() {
		shape.setWidth(2.0);
		shape.setPosition(-1, -1);
		shape.setAngleStart(Math.PI / 2.);
		assertEqualsDouble(0, shape.getStartPoint().getX());
		assertEqualsDouble(-3, shape.getStartPoint().getY());
	}

	@Test
	public void testGetStartPointPI() {
		shape.setWidth(2.0);
		shape.setPosition(-1, -1);
		shape.setAngleStart(Math.PI);
		assertEqualsDouble(-1, shape.getStartPoint().getX());
		assertEqualsDouble(-2, shape.getStartPoint().getY());
	}

	@Test
	public void testGetStartPoint15PI() {
		shape.setWidth(2.0);
		shape.setPosition(-1, -1);
		shape.setAngleStart(1.5 * Math.PI);
		assertEqualsDouble(0, shape.getStartPoint().getX());
		assertEqualsDouble(-1, shape.getStartPoint().getY());
	}

	@Test
	public void testGetStartPoint2PI() {
		shape.setWidth(2.0);
		shape.setPosition(-1, -1);
		shape.setAngleStart(2. * Math.PI);
		assertEqualsDouble(1, shape.getStartPoint().getX());
		assertEqualsDouble(-2, shape.getStartPoint().getY());
	}

	@Test
	public void testGetStartPointMin2PI() {
		shape.setWidth(2.0);
		shape.setPosition(-1, -1);
		shape.setAngleStart(-2. * Math.PI);
		assertEqualsDouble(1, shape.getStartPoint().getX());
		assertEqualsDouble(-2, shape.getStartPoint().getY());
	}

	@Test
	public void testGetStartPointMinPIdiv2() {
		shape.setWidth(2.0);
		shape.setPosition(-1, -1);
		shape.setAngleStart(-Math.PI / 2.);
		assertEqualsDouble(0, shape.getStartPoint().getX());
		assertEqualsDouble(-1, shape.getStartPoint().getY());
	}

	@Test
	public void testGetStartPointMinPI() {
		shape.setWidth(2.0);
		shape.setPosition(-1, -1);
		shape.setAngleStart(-Math.PI);
		assertEqualsDouble(-1, shape.getStartPoint().getX());
		assertEqualsDouble(-2, shape.getStartPoint().getY());
	}

	@Override
	@Test
	@Ignore
	public void testGetEndPoint() {
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
	}

	@Override
	@Ignore
	@Test
	public void testGetTopLeftPoint() {
	}

	@Override
	@Ignore
	@Test
	public void testGetTopRightPoint() {
	}

	@Override
	@Ignore
	@Test
	public void testGetBottomRightPoint() {
	}

	@Override
	@Ignore
	@Test
	public void testGetBottomLeftPoint() {
	}

	@Override
	@Ignore
	@Test
	public void testMirrorHorizontal() {
	}

	@Override
	@Ignore
	@Test
	public void testMirrorVertical() {
	}
}
