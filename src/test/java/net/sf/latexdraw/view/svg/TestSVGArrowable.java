package net.sf.latexdraw.view.svg;

import java.util.Arrays;
import java.util.stream.Stream;
import net.sf.latexdraw.data.ArrowableSupplier;
import net.sf.latexdraw.models.CompareShapeMatcher;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.IArrowableSingleShape;
import net.sf.latexdraw.models.interfaces.shape.IAxes;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestSVGArrowable extends TestSVGBase<IArrowableSingleShape> {
	static Stream<Arguments> arrowsParamsDiv() {
		return ArrowableSupplier.createArrowableShapes().map(s -> Arrays.stream(ArrowStyle.values()).map(i1 -> arguments(s, i1))).flatMap(s -> s);
	}

	@ParameterizedTest
	@MethodSource("arrowsParamsDiv")
	void testArrowArrow1(final IArrowableSingleShape sh, final ArrowStyle arr) {
		sh.setArrowStyle(arr, 0);
		sh.setArrowStyle(ArrowStyle.LEFT_ROUND_BRACKET, -1);
		final IArrowableSingleShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArrowStyle(sh.getArrowAt(0), s2.getArrowAt(0));
		CompareShapeMatcher.INST.assertEqualsArrowStyle(sh.getArrowAt(-1), s2.getArrowAt(-1));
		CompareShapeMatcher.INST.assertEqualsArrowArrow(sh.getArrowAt(0), s2.getArrowAt(0));
	}

	@ParameterizedTest
	@MethodSource("arrowsParamsDiv")
	void testArrowArrow2(final IArrowableSingleShape sh, final ArrowStyle arr) {
		sh.setArrowStyle(ArrowStyle.RIGHT_ARROW, 0);
		sh.setArrowStyle(arr, -1);
		final IArrowableSingleShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArrowStyle(sh.getArrowAt(0), s2.getArrowAt(0));
		CompareShapeMatcher.INST.assertEqualsArrowStyle(sh.getArrowAt(-1), s2.getArrowAt(-1));
		CompareShapeMatcher.INST.assertEqualsArrowArrow(sh.getArrowAt(-1), s2.getArrowAt(-1));
	}

	@ParameterizedTest
	@MethodSource("arrowsParamsDiv")
	void testArrowBarParamsArr2(final IArrowableSingleShape sh, final ArrowStyle arr) {
		assumeTrue(arr.isBar());
		assumeFalse(sh instanceof IAxes);
		sh.setArrowStyle(ArrowStyle.ROUND_END, 0);
		sh.setArrowStyle(arr, -1);
		final IArrowableSingleShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArrowBar(sh.getArrowAt(-1), s2.getArrowAt(-1));
	}

	@ParameterizedTest
	@MethodSource("arrowsParamsDiv")
	void testArrowBracketParamsArr1(final IArrowableSingleShape sh, final ArrowStyle arr) {
		assumeTrue(arr.isSquareBracket());
		assumeFalse(sh instanceof IAxes);
		sh.setArrowStyle(arr, 0);
		sh.setArrowStyle(ArrowStyle.LEFT_ARROW, -1);
		final IArrowableSingleShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArrowBracket(sh.getArrowAt(0), s2.getArrowAt(0));
	}

	@ParameterizedTest
	@MethodSource("arrowsParamsDiv")
	void testArrowBracketParamsArr2(final IArrowableSingleShape sh, final ArrowStyle arr) {
		assumeTrue(arr.isSquareBracket());
		assumeFalse(sh instanceof IAxes);
		sh.setArrowStyle(arr, -1);
		final IArrowableSingleShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArrowBracket(sh.getArrowAt(-1), s2.getArrowAt(-1));
	}

	@ParameterizedTest
	@MethodSource("arrowsParamsDiv")
	void testArrowRBracketParamsArr1(final IArrowableSingleShape sh, final ArrowStyle arr) {
		assumeTrue(arr.isRoundBracket());
		assumeFalse(sh instanceof IAxes);
		sh.setArrowStyle(arr, 0);
		final IArrowableSingleShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArrowRBracket(sh.getArrowAt(0), s2.getArrowAt(0));
	}

	@ParameterizedTest
	@MethodSource("arrowsParamsDiv")
	void testArrowRBracketParamsArr2(final IArrowableSingleShape sh, final ArrowStyle arr) {
		assumeTrue(arr.isRoundBracket());
		assumeFalse(sh instanceof IAxes);
		sh.setArrowStyle(arr, 0);
		final IArrowableSingleShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArrowRBracket(sh.getArrowAt(0), s2.getArrowAt(-1));
	}

	@ParameterizedTest
	@MethodSource("arrowsParamsDiv")
	void testArrowCircleDiskParamsArr1(final IArrowableSingleShape sh, final ArrowStyle arr) {
		assumeTrue(arr.isCircleDisk());
		assumeFalse(sh instanceof IAxes);
		sh.setArrowStyle(arr, 0);
		final IArrowableSingleShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArrowCircleDisk(sh.getArrowAt(0), s2.getArrowAt(0));
	}

	@ParameterizedTest
	@MethodSource("arrowsParamsDiv")
	void testArrowCircleDiskParamsArr2(final IArrowableSingleShape sh, final ArrowStyle arr) {
		assumeTrue(arr.isCircleDisk());
		assumeFalse(sh instanceof IAxes);
		sh.setArrowStyle(arr, 0);
		final IArrowableSingleShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArrowCircleDisk(sh.getArrowAt(-1), s2.getArrowAt(-1));
	}
}
