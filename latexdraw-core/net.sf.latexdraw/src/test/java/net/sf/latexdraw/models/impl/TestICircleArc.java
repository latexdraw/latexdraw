package net.sf.latexdraw.models.impl;

import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.data.ArcData;
import net.sf.latexdraw.data.DoubleData;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.IArc;
import net.sf.latexdraw.models.interfaces.shape.ICircle;
import net.sf.latexdraw.models.interfaces.shape.ICircleArc;
import net.sf.latexdraw.models.interfaces.shape.ILine;
import net.sf.latexdraw.models.interfaces.shape.IPositionShape;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(Theories.class)
public class TestICircleArc implements HelperTest {
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

	@Theory
	public void testGetArrowLineBadIndex(@ArcData final ICircleArc shape) {
		assertNull(shape.getArrowLine(10));
	}

	@Theory
	public void testGetArrowLineArrow0StartLesserEnd(@ArcData final ICircleArc shape) {
		shape.setAngleStart(Math.PI * 1.3);
		shape.setAngleEnd(Math.PI * 1.5);
		shape.setArrowStyle(ArrowStyle.RIGHT_ARROW, 0);
		final ILine line = shape.getArrowLine(0);
		assertThat(line.getX1(), lessThan(line.getX2()));
		assertTrue(line.getY1() > line.getY2());
	}

	@Theory
	public void testGetArrowLineArrow0StartGreaterEnd(@ArcData final ICircleArc shape) {
		shape.setAngleStart(Math.PI * 1.22);
		shape.setAngleEnd(Math.PI * 0.98);
		shape.setArrowStyle(ArrowStyle.RIGHT_ARROW, 0);
		final ILine line = shape.getArrowLine(0);
		assertTrue(line.getX1() < line.getX2());
		assertTrue(line.getY1() > line.getY2());
	}

	@Theory
	public void testGetArrowLineArrow1StartGreaterEnd(@ArcData final ICircleArc shape) {
		shape.setAngleStart(Math.PI * 1.22);
		shape.setAngleEnd(Math.PI * 0.98);
		shape.setArrowStyle(ArrowStyle.RIGHT_ARROW, 1);
		final ILine line = shape.getArrowLine(1);
		assertTrue(line.getX1() < line.getX2());
		assertTrue(line.getY1() < line.getY2());
	}

	@Theory
	public void testGetArrowLineArrow1StartLesserEnd(@ArcData final ICircleArc shape) {
		shape.setAngleStart(Math.PI * 1.22);
		shape.setAngleEnd(Math.PI * 1.56);
		shape.setArrowStyle(ArrowStyle.RIGHT_ARROW, 1);
		final ILine line = shape.getArrowLine(1);
		assertTrue(line.getX1() > line.getX2());
		assertTrue(line.getY1() > line.getY2());
	}

	@Theory
	public void testGetCenter(@ArcData final ICircleArc shape) {
		assertEquals(shape.getGravityCentre(), shape.getCenter());
	}

	@Theory
	public void testRadius(@ArcData final ICircleArc shape) {
		assertEquals(shape.getWidth() / 2d, shape.getRadius(), 0.0001);
	}
}
