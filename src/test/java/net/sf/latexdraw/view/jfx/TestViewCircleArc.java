package net.sf.latexdraw.view.jfx;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.ArcStyle;
import net.sf.latexdraw.model.api.shape.ArrowStyle;
import net.sf.latexdraw.model.api.shape.CircleArc;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestViewCircleArc extends TestViewShape<ViewCircleArc, CircleArc> {
	// Tricky way to factorise the code that concerns all the layers of the view.
	static Stream<Function<ViewCircleArc, Arc>> getArcsToTest() {
		return Stream.of(v -> v.border, v -> v.shadow, v -> v.dblBorder);
	}
	static Stream<Arguments> styleFunction() {
		return Arrays.stream(ArcStyle.values()).map(s -> getArcsToTest().map(i1 -> arguments(s, i1))).flatMap(s -> s);
	}

	@Override
	protected CircleArc createModel() {
		return ShapeFactory.INST.createCircleArc(ShapeFactory.INST.createPoint(), 10d);
	}

	private Arc cloneArc(final Arc arc) {
		final Arc clone = new Arc(arc.getCenterX(), arc.getCenterY(), arc.getRadiusX(), arc.getRadiusY(), arc.getStartAngle(), arc.getLength());
		clone.setType(arc.getType());
		return clone;
	}

	@ParameterizedTest
	@MethodSource("getArcsToTest")
	void testSetAngleStartMainBorder(final Function<ViewCircleArc, Arc> fct) {
		final Arc arc = fct.apply(view);
		final double startAngle = arc.getStartAngle();
		final double angle = model.getAngleStart() + Math.PI / 4d;
		model.setAngleStart(angle);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(arc.getStartAngle(), startAngle);
		assertEquals(Math.toDegrees(angle), arc.getStartAngle(), 0.00001);
	}

	@ParameterizedTest
	@MethodSource("getArcsToTest")
	void testSetAngleStartMainBorderLength(final Function<ViewCircleArc, Arc> fct) {
		final Arc arc = fct.apply(view);
		final double length = arc.getLength();
		final double angle = model.getAngleStart() + Math.PI / 4d;
		model.setAngleStart(angle);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(arc.getLength(), length);
		assertEquals(Math.toDegrees(model.getAngleEnd() - model.getAngleStart()), arc.getLength(), 0.0001);
	}

	@ParameterizedTest
	@MethodSource("getArcsToTest")
	void testSetAngleEndMainBorder(final Function<ViewCircleArc, Arc> fct) {
		final Arc arc = fct.apply(view);
		final double length = arc.getLength();
		final double angle = model.getAngleEnd() + Math.PI / 4d;
		model.setAngleEnd(angle);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(arc.getLength(), length);
		assertEquals(Math.toDegrees(model.getAngleEnd() - model.getAngleStart()), arc.getLength(), 0.0001);
	}

	@ParameterizedTest
	@MethodSource("styleFunction")
	void testTypeArc(final ArcStyle style, final Function<ViewCircleArc, Arc> fct) {
		model.setArcStyle(style);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(style.getJFXStyle(), fct.apply(view).getType());
	}

	@ParameterizedTest
	@EnumSource(ArcStyle.class)
	void testClipArrowExists(final ArcStyle style) {
		assumeTrue(style.supportArrow());
		model.setArcStyle(style);
		model.setArrowStyle(ArrowStyle.RIGHT_ARROW, 0);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(view.border.getClip() instanceof Arc);
	}


	@ParameterizedTest
	@EnumSource(ArcStyle.class)
	void testClipArrowSameType(final ArcStyle style) {
		assumeTrue(style.supportArrow());
		model.setArcStyle(style);
		model.setArrowStyle(ArrowStyle.RIGHT_ARROW, 0);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(((Arc) view.border.getClip()).getType(), style.getJFXStyle());
	}

	@ParameterizedTest
	@EnumSource(ArcStyle.class)
	void testClipArrowSameStrokeWidth(final ArcStyle style) {
		assumeTrue(style.supportArrow());
		model.setArcStyle(style);
		model.setThickness(model.getThickness() * 2d);
		model.setArrowStyle(ArrowStyle.RIGHT_ARROW, 0);
		WaitForAsyncUtils.waitForFxEvents();
		assertEquals(((Arc) view.border.getClip()).getStrokeWidth(), view.border.getStrokeWidth(), 0.0001);
	}

	@ParameterizedTest
	@EnumSource(ArcStyle.class)
	void testClipArrowSameDimensions(final ArcStyle style) {
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

	@ParameterizedTest
	@EnumSource(ArcStyle.class)
	void testClipArrowSameStartEndNotEmptyClip(final ArcStyle style) {
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
	void testRadiusArcConsidersThickness() {
		model.setWidth(10d);
		model.setPosition(0d, 10d);
		model.setThickness(2d);
		final Line line = new Line(11d, 0d, 11d, 10d);
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(view.border.intersects(line.getBoundsInLocal()));
	}

	@Test
	void testThicknessChangeRadius() {
		final Arc arc = cloneArc(view.border);
		model.setThickness(model.getThickness() * 2d);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(arc.getRadiusX(), view.border.getRadiusX());
		assertNotEquals(arc.getRadiusY(), view.border.getRadiusY());
	}

	@Test
	void testTicknessChangeHasDblBord() {
		final Arc arc = cloneArc(view.border);
		model.setHasDbleBord(true);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(arc.getRadiusX(), view.border.getRadiusX());
		assertNotEquals(arc.getRadiusY(), view.border.getRadiusY());
	}

	@Test
	void testTicknessChangeDbleSep() {
		final Arc arc = cloneArc(view.border);
		model.setHasDbleBord(true);
		model.setDbleBordSep(model.getDbleBordSep() * 2d);
		WaitForAsyncUtils.waitForFxEvents();
		assertNotEquals(arc.getRadiusX(), view.border.getRadiusX());
		assertNotEquals(arc.getRadiusY(), view.border.getRadiusY());
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
