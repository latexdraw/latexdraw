package net.sf.latexdraw.view.jfx;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import javafx.application.Platform;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ArcStyle;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.ICircleArc;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

@RunWith(Theories.class)
public class TestViewCircleArc extends TestViewShape<ViewCircleArc, ICircleArc> {
	// Tricky way to factorise the code that concerns all the layers of the view.
	@DataPoints
	public static Collection<Function<ViewCircleArc, Arc>> getArcsToTest() {
		return Arrays.asList(v -> v.border, v -> v.shadow, v -> v.dblBorder);
	}

	@BeforeClass
	public static void beforeClass() {
		try {
			Platform.startup(() -> {});
		}catch(final IllegalStateException ex) {
			// Ok
		}
	}

	@Override
	protected ICircleArc createModel() {
		return ShapeFactory.INST.createCircleArc(ShapeFactory.INST.createPoint(), 10d);
	}

	private Arc cloneArc(final Arc arc) {
		final Arc clone = new Arc(arc.getCenterX(), arc.getCenterY(), arc.getRadiusX(), arc.getRadiusY(), arc.getStartAngle(), arc.getLength());
		clone.setType(arc.getType());
		return clone;
	}

	@Theory
	public void testSetAngleStartMainBorder(final Function<ViewCircleArc, Arc> fct) {
		final Arc arc = fct.apply(view);
		final double startAngle = arc.getStartAngle();
		final double angle = model.getAngleStart() + Math.PI / 4d;
		model.setAngleStart(angle);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(arc.getStartAngle(), startAngle);
		assertEquals(Math.toDegrees(angle), arc.getStartAngle(), 0.00001);
	}

	@Theory
	public void testSetAngleStartMainBorderLength(final Function<ViewCircleArc, Arc> fct) {
		final Arc arc = fct.apply(view);
		final double length = arc.getLength();
		final double angle = model.getAngleStart() + Math.PI / 4d;
		model.setAngleStart(angle);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(arc.getLength(), length);
		assertEquals(Math.toDegrees(model.getAngleEnd() - model.getAngleStart()), arc.getLength(), 0.0001);
	}

	@Theory
	public void testSetAngleEndMainBorder(final Function<ViewCircleArc, Arc> fct) {
		final Arc arc = fct.apply(view);
		final double length = arc.getLength();
		final double angle = model.getAngleEnd() + Math.PI / 4d;
		model.setAngleEnd(angle);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(arc.getLength(), length);
		assertEquals(Math.toDegrees(model.getAngleEnd() - model.getAngleStart()), arc.getLength(), 0.0001);
	}

	@Theory
	public void testTypeArc(final ArcStyle style, final Function<ViewCircleArc, Arc> fct) {
		model.setArcStyle(style);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(style.getJFXStyle(), fct.apply(view).getType());
	}

	@Theory
	public void testClipArrowExists(final ArcStyle style) {//, final Function<ViewCircleArc, Arc> fct) {
		assumeTrue(style.supportArrow());
		model.setArcStyle(style);
		model.setArrowStyle(ArrowStyle.RIGHT_ARROW, 0);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(view.border.getClip() instanceof Arc);
	}


	@Theory
	public void testClipArrowSameType(final ArcStyle style) {
		assumeTrue(style.supportArrow());
		model.setArcStyle(style);
		model.setArrowStyle(ArrowStyle.RIGHT_ARROW, 0);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(((Arc) view.border.getClip()).getType(), style.getJFXStyle());
	}

	@Theory
	public void testClipArrowSameStrokeWidth(final ArcStyle style) {
		assumeTrue(style.supportArrow());
		model.setArcStyle(style);
		model.setThickness(model.getThickness() * 2d);
		model.setArrowStyle(ArrowStyle.RIGHT_ARROW, 0);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(((Arc) view.border.getClip()).getStrokeWidth(), view.border.getStrokeWidth(), 0.0001);
	}

	@Theory
	public void testClipArrowSameDimensions(final ArcStyle style) {
		assumeTrue(style.supportArrow());
		model.setArcStyle(style);
		model.setArrowStyle(ArrowStyle.RIGHT_ARROW, 0);
		model.setArrowStyle(ArrowStyle.RIGHT_DBLE_ARROW, -1);
		WaitForAsyncUtils.waitForFxEvents();
		final Arc clip = (Arc) view.border.getClip();
		assertEquals(clip.getCenterX(), view.border.getCenterX(), 0.0001);
		assertEquals(clip.getCenterY(), view.border.getCenterY(), 0.0001);
		assertEquals(clip.getRadiusX(), view.border.getRadiusX(), 0.0001);
		assertEquals(clip.getRadiusY(), view.border.getRadiusY(), 0.0001);
	}

	@Theory
	public void testClipArrowSameStartEndNotEmptyClip(final ArcStyle style) {
		assumeTrue(style.supportArrow());
		model.setArcStyle(style);
		model.setAngleStart(0d);
		model.setAngleEnd(0d);
		model.setArrowLength(0d);
		model.setArrowStyle(ArrowStyle.RIGHT_ARROW, 0);
		model.setArrowStyle(ArrowStyle.RIGHT_DBLE_ARROW, -1);
		WaitForAsyncUtils.waitForFxEvents();
		final Arc clip = (Arc) view.border.getClip();
		assertNotEquals(clip.getLength(), 0d);
	}

	@Test
	public void testRadiusArcConsidersThickness() {
		model.setWidth(10d);
		model.setPosition(0d, 10d);
		model.setThickness(2d);
		final Line line = new Line(11d, 0d, 11d, 10d);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(view.border.intersects(line.getBoundsInLocal()));
	}

	@Test
	public void testThicknessChangeRadius() {
		final Arc arc = cloneArc(view.border);
		model.setThickness(model.getThickness() * 2d);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(arc.getRadiusX(), view.border.getRadiusX(), 0.00001);
		assertNotEquals(arc.getRadiusY(), view.border.getRadiusY(), 0.00001);
	}

	@Test
	public void testTicknessChangeHasDblBord() {
		final Arc arc = cloneArc(view.border);
		model.setHasDbleBord(true);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(arc.getRadiusX(), view.border.getRadiusX(), 0.00001);
		assertNotEquals(arc.getRadiusY(), view.border.getRadiusY(), 0.00001);
	}

	@Test
	public void testTicknessChangeDbleSep() {
		final Arc arc = cloneArc(view.border);
		model.setHasDbleBord(true);
		model.setDbleBordSep(model.getDbleBordSep() * 2d);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(arc.getRadiusX(), view.border.getRadiusX(), 0.00001);
		assertNotEquals(arc.getRadiusY(), view.border.getRadiusY(), 0.00001);
	}

	@Override
	public void testOnTranslateX() {
		// To be removed
	}

	@Override
	public void testOnTranslateY() {
		// To be removed
	}
}
