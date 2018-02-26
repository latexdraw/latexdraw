package net.sf.latexdraw.view.svg;

import java.util.concurrent.TimeoutException;
import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.data.ParameteriseShapeData;
import net.sf.latexdraw.data.ShapeData;
import net.sf.latexdraw.models.CompareShapeMatcher;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDefsElement;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGSVGElement;
import net.sf.latexdraw.util.LNamespace;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.testfx.api.FxToolkit;

import static org.junit.Assume.assumeTrue;

@RunWith(Theories.class)
public class TestSVGShape implements HelperTest {
	SVGDocument doc;

	@BeforeClass
	public static void beforeClass() throws TimeoutException {
		FxToolkit.registerPrimaryStage();
	}

	@AfterClass
	public static void tearDownAll() {
		ParameteriseShapeData.INST.clearTempFolders();
	}

	@Before
	public void setUp() {
		doc = new SVGDocument();
		final SVGSVGElement root = doc.getFirstChild();
		root.setAttribute("xmlns:" + LNamespace.LATEXDRAW_NAMESPACE, LNamespace.LATEXDRAW_NAMESPACE_URI);
		root.appendChild(new SVGDefsElement(doc));
		root.setAttribute(SVGAttributes.SVG_VERSION, "1.1");
		root.setAttribute(SVGAttributes.SVG_BASE_PROFILE, "full");
	}

	protected IShape produceOutputShapeFrom(final IShape sh) {
		final SVGElement elt = SVGShapesFactory.INSTANCE.createSVGElement(sh, doc);
		doc.getFirstChild().appendChild(elt);
		return SVGShapesFactory.INSTANCE.createShape(elt);
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
}
