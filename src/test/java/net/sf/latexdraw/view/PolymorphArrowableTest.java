package net.sf.latexdraw.view;

import java.util.Arrays;
import java.util.stream.Stream;
import net.sf.latexdraw.data.ArrowableSupplier;
import net.sf.latexdraw.model.CompareShapeMatcher;
import net.sf.latexdraw.model.api.shape.ArrowStyle;
import net.sf.latexdraw.model.api.shape.ArrowableSingleShape;
import net.sf.latexdraw.model.api.shape.Axes;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public interface PolymorphArrowableTest extends PolymorphicConversion<ArrowableSingleShape> {
	static Stream<Arguments> arrowsParamsDiv() {
		return ArrowableSupplier.createArrowableShapes().flatMap(s -> Arrays.stream(ArrowStyle.values()).map(i1 -> arguments(s, i1)));
	}

	@ParameterizedTest
	@MethodSource("arrowsParamsDiv")
	default void testArrowArrow1(final ArrowableSingleShape sh, final ArrowStyle arr) {
		assumeTrue(arr.isArrow());
		sh.setArrowStyle(arr, 0);
		sh.setArrowStyle(ArrowStyle.LEFT_ROUND_BRACKET, 1);
		final ArrowableSingleShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArrowStyle(sh.getArrowAt(0), s2.getArrowAt(0));
		CompareShapeMatcher.INST.assertEqualsArrowStyle(sh.getArrowAt(1), s2.getArrowAt(1));
		CompareShapeMatcher.INST.assertEqualsArrowArrow(sh.getArrowAt(0), s2.getArrowAt(0));
	}

	@ParameterizedTest
	@MethodSource("arrowsParamsDiv")
	default void testArrowArrow2(final ArrowableSingleShape sh, final ArrowStyle arr) {
		assumeTrue(arr.isArrow());
		sh.setArrowStyle(ArrowStyle.LEFT_SQUARE_BRACKET, 0);
		sh.setArrowStyle(arr, 1);
		final ArrowableSingleShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArrowStyle(sh.getArrowAt(0), s2.getArrowAt(0));
		CompareShapeMatcher.INST.assertEqualsArrowStyle(sh.getArrowAt(1), s2.getArrowAt(1));
		CompareShapeMatcher.INST.assertEqualsArrowArrow(sh.getArrowAt(1), s2.getArrowAt(1));
	}

	@ParameterizedTest
	@MethodSource("arrowsParamsDiv")
	default void testArrowBarParamsArr2(final ArrowableSingleShape sh, final ArrowStyle arr) {
		assumeTrue(arr.isBar());
		assumeFalse(sh instanceof Axes);
		sh.setArrowStyle(ArrowStyle.ROUND_END, 0);
		sh.setArrowStyle(arr, 1);
		final ArrowableSingleShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArrowBar(sh.getArrowAt(1), s2.getArrowAt(1));
	}

	@ParameterizedTest
	@MethodSource("arrowsParamsDiv")
	default void testArrowBracketParamsArr1(final ArrowableSingleShape sh, final ArrowStyle arr) {
		assumeTrue(arr.isSquareBracket());
		assumeFalse(sh instanceof Axes);
		sh.setArrowStyle(arr, 0);
		sh.setArrowStyle(ArrowStyle.LEFT_ARROW, 1);
		final ArrowableSingleShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArrowBracket(sh.getArrowAt(0), s2.getArrowAt(0));
	}

	@ParameterizedTest
	@MethodSource("arrowsParamsDiv")
	default void testArrowBracketParamsArr2(final ArrowableSingleShape sh, final ArrowStyle arr) {
		assumeTrue(arr.isSquareBracket());
		assumeFalse(sh instanceof Axes);
		sh.setArrowStyle(arr, 1);
		final ArrowableSingleShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArrowBracket(sh.getArrowAt(1), s2.getArrowAt(1));
	}

	@ParameterizedTest
	@MethodSource("arrowsParamsDiv")
	default void testArrowRBracketParamsArr1(final ArrowableSingleShape sh, final ArrowStyle arr) {
		assumeTrue(arr.isRoundBracket());
		assumeFalse(sh instanceof Axes);
		sh.setArrowStyle(arr, 0);
		final ArrowableSingleShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArrowRBracket(sh.getArrowAt(0), s2.getArrowAt(0));
	}

	@ParameterizedTest
	@MethodSource("arrowsParamsDiv")
	default void testArrowRBracketParamsArr2(final ArrowableSingleShape sh, final ArrowStyle arr) {
		assumeTrue(arr.isRoundBracket());
		assumeFalse(sh instanceof Axes);
		sh.setArrowStyle(arr, 0);
		final ArrowableSingleShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArrowRBracket(sh.getArrowAt(0), s2.getArrowAt(1));
	}

	@ParameterizedTest
	@MethodSource("arrowsParamsDiv")
	default void testArrowCircleDiskParamsArr1(final ArrowableSingleShape sh, final ArrowStyle arr) {
		assumeTrue(arr.isCircleDisk());
		assumeFalse(sh instanceof Axes);
		sh.setArrowStyle(arr, 0);
		final ArrowableSingleShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArrowCircleDisk(sh.getArrowAt(0), s2.getArrowAt(0));
	}

	@ParameterizedTest
	@MethodSource("arrowsParamsDiv")
	default void testArrowCircleDiskParamsArr2(final ArrowableSingleShape sh, final ArrowStyle arr) {
		assumeTrue(arr.isCircleDisk());
		assumeFalse(sh instanceof Axes);
		sh.setArrowStyle(arr, 0);
		final ArrowableSingleShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArrowCircleDisk(sh.getArrowAt(1), s2.getArrowAt(1));
	}
}
