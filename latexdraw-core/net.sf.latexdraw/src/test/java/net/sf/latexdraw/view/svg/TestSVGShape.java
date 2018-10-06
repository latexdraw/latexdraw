package net.sf.latexdraw.view.svg;

import javafx.application.Platform;
import net.sf.latexdraw.CollectionMatcher;
import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.data.ParameteriseShapeData;
import net.sf.latexdraw.data.ShapeData;
import net.sf.latexdraw.models.CompareShapeMatcher;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

@RunWith(Theories.class)
public class TestSVGShape extends TestSVGBase<IShape> implements HelperTest, CollectionMatcher {
	@BeforeClass
	public static void beforeClass() {
		Platform.startup(() -> {});
	}

	@AfterClass
	public static void tearDownAll() {
		ParameteriseShapeData.INST.clearTempFolders();
	}

	@Theory
	public void testLoadSaveShapeParams(@ShapeData(withParamVariants = true) final IShape sh) {
		assumeTrue(sh.isBordersMovable());
		final IShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualShapeBorderMov(sh, s2);
	}

	@Theory
	public void testLoadShapeLineStyleParams(@ShapeData(withParamVariants = true) final IShape sh) {
		assumeTrue(sh.isLineStylable());
		final IShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualShapeLineStyle(sh, s2);
	}

	@Theory
	public void testLoadShapeShowPtsParams(@ShapeData(withParamVariants = true) final IShape sh) {
		assumeTrue(sh.isShowPtsable());
		final IShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualShapeShowPts(sh, s2);
	}

	@Theory
	public void testLoadRotationAngleParams(@ShapeData(withParamVariants = true) final IShape sh) {
		final IShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualShapeRotationAngle(sh, s2);
	}

	@Theory
	public void testLoadDbleBorderableParams(@ShapeData(withParamVariants = true) final IShape sh) {
		assumeTrue(sh.isDbleBorderable());
		final IShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualShapeDbleBorder(sh, s2);
	}

	@Theory
	public void testLoadFillParams(@ShapeData(withParamVariants = true) final IShape sh) {
		assumeTrue(sh.isFillable());
		final IShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualShapeFill(sh, s2);
	}

	@Theory
	public void testLoadFillStyleParams(@ShapeData(withParamVariants = true) final IShape sh) {
		assumeTrue(sh.isInteriorStylable());
		final IShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualShapeFillStyle(sh, s2);
	}

	@Theory
	public void testLoadShadowParams(@ShapeData(withParamVariants = true) final IShape sh) {
		assumeTrue(sh.isShadowable());
		final IShape s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualShapeShadow(sh, s2);
	}

	@Theory
	public void testPointsEquals(@ShapeData(withParamVariants = true) final IShape sh) {
		final IShape s2 = produceOutputShapeFrom(sh);
		assertEquals(sh.getPoints(), s2.getPoints());
	}
}
