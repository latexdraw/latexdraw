package net.sf.latexdraw.view.jfx;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import javafx.scene.shape.Arc;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.ArcStyle;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.FillingStyle;
import net.sf.latexdraw.models.interfaces.shape.ICircleArc;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

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

	@Override
	protected ICircleArc createModel() {
		return ShapeFactory.INST.createCircleArc(ShapeFactory.INST.createPoint(), 10d);
	}

	@Theory
	public void testSetAngleStartMainBorder(final Function<ViewCircleArc, Arc> fct) {
		final Arc arc = fct.apply(view);
		final double startAngle = arc.getStartAngle();
		final double angle = model.getAngleStart() + Math.PI / 4d;
		model.setAngleStart(angle);
		assertNotEquals(arc.getStartAngle(), startAngle);
		assertEquals(Math.toDegrees(angle), arc.getStartAngle(), 0.00001);
	}

	@Theory
	public void testSetAngleStartMainBorderLength(final Function<ViewCircleArc, Arc> fct) {
		final Arc arc = fct.apply(view);
		final double length = arc.getLength();
		final double angle = model.getAngleStart() + Math.PI / 4d;
		model.setAngleStart(angle);
		assertNotEquals(arc.getLength(), length);
		assertEquals(Math.toDegrees(model.getAngleEnd() - model.getAngleStart()), arc.getLength(), 0.0001);
	}

	@Theory
	public void testSetAngleEndMainBorder(final Function<ViewCircleArc, Arc> fct) {
		final Arc arc = fct.apply(view);
		final double length = arc.getLength();
		final double angle = model.getAngleEnd() + Math.PI / 4d;
		model.setAngleEnd(angle);
		assertNotEquals(arc.getLength(), length);
		assertEquals(Math.toDegrees(model.getAngleEnd() - model.getAngleStart()), arc.getLength(), 0.0001);
	}

	@Theory
	public void testTypeArc(final ArcStyle style, final Function<ViewCircleArc, Arc> fct) {
		model.setArcStyle(style);
		assertEquals(style.getJFXStyle(), fct.apply(view).getType());
	}

	@Theory
	public void testClipArrowExists(final ArcStyle style) {//, final Function<ViewCircleArc, Arc> fct) {
		assumeTrue(style.supportArrow());
		model.setArcStyle(style);
		model.setArrowStyle(ArrowStyle.RIGHT_ARROW, 0);
//		assertNotNull(fct.apply(view).getClip());
		assertTrue(view.border.getClip() instanceof Arc);
	}


	@Theory
	public void testClipArrowSameType(final ArcStyle style) {
		assumeTrue(style.supportArrow());
		model.setArcStyle(style);
		model.setArrowStyle(ArrowStyle.RIGHT_ARROW, 0);
		assertEquals(((Arc) view.border.getClip()).getType(), style.getJFXStyle());
	}

	@Theory
	public void testClipArrowSameFill(final ArcStyle style) {
		assumeTrue(style.supportArrow());
		model.setArcStyle(style);
		model.setFillingStyle(FillingStyle.PLAIN);
		model.setArrowStyle(ArrowStyle.RIGHT_ARROW, 0);
		assertEquals(((Arc) view.border.getClip()).getFill(), view.border.getFill());
	}

	@Theory
	public void testClipArrowSameStrokeWidth(final ArcStyle style) {
		assumeTrue(style.supportArrow());
		model.setArcStyle(style);
		model.setThickness(model.getThickness() * 2d);
		model.setArrowStyle(ArrowStyle.RIGHT_ARROW, 0);
		assertEquals(((Arc) view.border.getClip()).getStrokeWidth(), view.border.getStrokeWidth(), 0.0001);
	}

	@Theory
	public void testClipArrowSameDimensions(final ArcStyle style) {
		assumeTrue(style.supportArrow());
		model.setArcStyle(style);
		model.setArrowStyle(ArrowStyle.RIGHT_ARROW, 0);
		model.setArrowStyle(ArrowStyle.RIGHT_DBLE_ARROW, -1);
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
		final Arc clip = (Arc) view.border.getClip();
		assertNotEquals(clip.getLength(), 0d);
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
