package test.models;

import net.sf.latexdraw.models.interfaces.shape.IArc;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.ICircleArc;
import net.sf.latexdraw.models.interfaces.shape.IPositionShape;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import test.HelperTest;
import test.data.ArcData;
import test.data.DoubleData;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class TestLCircleArc implements HelperTest {
	@Theory
	public void testGetStartPoint(@ArcData final ICircleArc sh, @DoubleData(angle = true) final double angle) {
		sh.setWidth(2d);
		sh.setPosition(-1d, -1d);

		sh.setAngleStart(angle);
		assertEqualsDouble(sh.getGravityCentre().getX() + sh.getRadius() * Math.cos(angle), sh.getStartPoint().getX());
		assertEqualsDouble(sh.getGravityCentre().getY() - sh.getRadius() * Math.sin(angle), sh.getStartPoint().getY());
	}

	@Theory
	public void testGetEndPoint(@ArcData final ICircleArc sh, @DoubleData(angle = true) final double angle) {
		sh.setWidth(2d);
		sh.setPosition(-1d, -1d);

		sh.setAngleEnd(angle);
		assertEqualsDouble(sh.getGravityCentre().getX() + sh.getRadius() * Math.cos(angle), sh.getEndPoint().getX());
		assertEqualsDouble(sh.getGravityCentre().getY() - sh.getRadius() * Math.sin(angle), sh.getEndPoint().getY());
	}

	@Theory
	public void testIsTypeOf(@ArcData final ICircleArc shape) {
		assertFalse(shape.isTypeOf(null));
		assertFalse(shape.isTypeOf(IRectangle.class));
		assertTrue(shape.isTypeOf(ICircle.class));
		assertTrue(shape.isTypeOf(IShape.class));
		assertTrue(shape.isTypeOf(IPositionShape.class));
		assertTrue(shape.isTypeOf(IArc.class));
		assertTrue(shape.isTypeOf(ICircleArc.class));
		assertTrue(shape.isTypeOf(shape.getClass()));
	}
}
