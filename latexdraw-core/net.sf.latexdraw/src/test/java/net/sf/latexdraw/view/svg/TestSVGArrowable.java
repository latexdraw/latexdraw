package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.data.ArrowableData;
import net.sf.latexdraw.models.CompareShapeMatcher;
import net.sf.latexdraw.models.interfaces.shape.ArrowStyle;
import net.sf.latexdraw.models.interfaces.shape.IArrowableSingleShape;
import net.sf.latexdraw.models.interfaces.shape.IAxes;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;

@RunWith(Theories.class)
public class TestSVGArrowable extends TestSVGBase<IArrowableSingleShape> {
	@Theory
	public void testArrowStyle(@ArrowableData final IArrowableSingleShape sh, final ArrowStyle arr1, final ArrowStyle arr2) {
		sh.setArrowStyle(arr1, 0);
		sh.setArrowStyle(arr2, -1);
		final IArrowableSingleShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArrowStyle(sh.getArrowAt(0), s2.getArrowAt(0));
		CompareShapeMatcher.INST.assertEqualsArrowStyle(sh.getArrowAt(-1), s2.getArrowAt(-1));
	}

	@Theory
	public void testArrowArrowParamsArr1(@ArrowableData(withParamVariants = true) final IArrowableSingleShape sh, final ArrowStyle arr1, final ArrowStyle arr2) {
		assumeTrue(arr1.isArrow());
		assumeFalse(sh instanceof IAxes);
		sh.setArrowStyle(arr1, 0);
		sh.setArrowStyle(arr2, -1);
		final IArrowableSingleShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArrowArrow(sh.getArrowAt(0), s2.getArrowAt(0));
	}

	@Theory
	public void testArrowArrowParamsArr2(@ArrowableData(withParamVariants = true) final IArrowableSingleShape sh, final ArrowStyle arr1, final ArrowStyle arr2) {
		assumeTrue(arr2.isArrow());
		assumeFalse(sh instanceof IAxes);
		sh.setArrowStyle(arr1, 0);
		sh.setArrowStyle(arr2, -1);
		final IArrowableSingleShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArrowArrow(sh.getArrowAt(-1), s2.getArrowAt(-1));
	}

	@Theory
	public void testArrowBarParamsArr1(@ArrowableData(withParamVariants = true) final IArrowableSingleShape sh, final ArrowStyle arr1, final ArrowStyle arr2) {
		assumeTrue(arr1.isBar());
		assumeFalse(sh instanceof IAxes);
		sh.setArrowStyle(arr1, 0);
		sh.setArrowStyle(arr2, -1);
		final IArrowableSingleShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArrowBar(sh.getArrowAt(0), s2.getArrowAt(0));
	}

	@Theory
	public void testArrowBarParamsArr2(@ArrowableData(withParamVariants = true) final IArrowableSingleShape sh, final ArrowStyle arr1, final ArrowStyle arr2) {
		assumeTrue(arr2.isBar());
		assumeFalse(sh instanceof IAxes);
		sh.setArrowStyle(arr1, 0);
		sh.setArrowStyle(arr2, -1);
		final IArrowableSingleShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArrowBar(sh.getArrowAt(-1), s2.getArrowAt(-1));
	}

	@Theory
	public void testArrowBracketParamsArr1(@ArrowableData(withParamVariants = true) final IArrowableSingleShape sh, final ArrowStyle arr1, final ArrowStyle arr2) {
		assumeTrue(arr1.isSquareBracket());
		assumeFalse(sh instanceof IAxes);
		sh.setArrowStyle(arr1, 0);
		sh.setArrowStyle(arr2, -1);
		final IArrowableSingleShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArrowBracket(sh.getArrowAt(0), s2.getArrowAt(0));
	}

	@Theory
	public void testArrowBracketParamsArr2(@ArrowableData(withParamVariants = true) final IArrowableSingleShape sh, final ArrowStyle arr1, final ArrowStyle arr2) {
		assumeTrue(arr2.isSquareBracket());
		assumeFalse(sh instanceof IAxes);
		sh.setArrowStyle(arr1, 0);
		sh.setArrowStyle(arr2, -1);
		final IArrowableSingleShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArrowBracket(sh.getArrowAt(-1), s2.getArrowAt(-1));
	}

	@Theory
	public void testArrowRBracketParamsArr1(@ArrowableData(withParamVariants = true) final IArrowableSingleShape sh, final ArrowStyle arr1, final ArrowStyle arr2) {
		assumeTrue(arr1.isRoundBracket());
		assumeFalse(sh instanceof IAxes);
		sh.setArrowStyle(arr1, 0);
		sh.setArrowStyle(arr2, -1);
		final IArrowableSingleShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArrowRBracket(sh.getArrowAt(0), s2.getArrowAt(0));
	}

	@Theory
	public void testArrowRBracketParamsArr2(@ArrowableData(withParamVariants = true) final IArrowableSingleShape sh, final ArrowStyle arr1, final ArrowStyle arr2) {
		assumeTrue(arr1.isRoundBracket());
		assumeFalse(sh instanceof IAxes);
		sh.setArrowStyle(arr1, 0);
		sh.setArrowStyle(arr2, -1);
		final IArrowableSingleShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArrowRBracket(sh.getArrowAt(0), s2.getArrowAt(-1));
	}

	@Theory
	public void testArrowCircleDiskParamsArr1(@ArrowableData(withParamVariants = true) final IArrowableSingleShape sh, final ArrowStyle arr1, final ArrowStyle arr2) {
		assumeTrue(arr1.isCircleDisk());
		assumeFalse(sh instanceof IAxes);
		sh.setArrowStyle(arr1, 0);
		sh.setArrowStyle(arr2, -1);
		final IArrowableSingleShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArrowCircleDisk(sh.getArrowAt(0), s2.getArrowAt(0));
	}

	@Theory
	public void testArrowCircleDiskParamsArr2(@ArrowableData(withParamVariants = true) final IArrowableSingleShape sh, final ArrowStyle arr1, final ArrowStyle arr2) {
		assumeTrue(arr1.isCircleDisk());
		assumeFalse(sh instanceof IAxes);
		sh.setArrowStyle(arr1, 0);
		sh.setArrowStyle(arr2, -1);
		final IArrowableSingleShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArrowCircleDisk(sh.getArrowAt(-1), s2.getArrowAt(-1));
	}
}
