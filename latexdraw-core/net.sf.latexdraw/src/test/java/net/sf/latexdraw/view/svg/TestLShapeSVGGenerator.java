package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.data.ShapeData;
import net.sf.latexdraw.models.CompareShapeMatcher;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IPicture;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.parsers.svg.SVGAttributes;
import net.sf.latexdraw.parsers.svg.SVGDefsElement;
import net.sf.latexdraw.parsers.svg.SVGDocument;
import net.sf.latexdraw.parsers.svg.SVGElement;
import net.sf.latexdraw.parsers.svg.SVGGElement;
import net.sf.latexdraw.parsers.svg.SVGSVGElement;
import net.sf.latexdraw.util.LNamespace;
import net.sf.latexdraw.view.TestCompareShapeIO;
import org.junit.Before;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;

@RunWith(Theories.class)
public class TestLShapeSVGGenerator extends TestCompareShapeIO<IShape> {
	SVGDocument doc;
	SVGGElement g;

	@Before
	public void setUp() {
		doc = new SVGDocument();
		final SVGSVGElement root = doc.getFirstChild();
		root.setAttribute("xmlns:" + LNamespace.LATEXDRAW_NAMESPACE, LNamespace.LATEXDRAW_NAMESPACE_URI);
		root.appendChild(new SVGDefsElement(doc));
		root.setAttribute(SVGAttributes.SVG_VERSION, "1.1");
		root.setAttribute(SVGAttributes.SVG_BASE_PROFILE, "full");
	}

	@Override
	protected IShape produceOutputShapeFrom() {
		final SVGElement elt = SVGShapesFactory.INSTANCE.createSVGElement(srcShape, doc);
		doc.getFirstChild().appendChild(elt);
		return IShapeSVGFactory.INSTANCE.createShape(elt);
	}

	protected IShape produceOutputShapeFrom(final IShape sh) {
		final SVGElement elt = SVGShapesFactory.INSTANCE.createSVGElement(sh, doc);
		doc.getFirstChild().appendChild(elt);
		return IShapeSVGFactory.INSTANCE.createShape(elt);
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
		assumeThat(sh, not(instanceOf(IPicture.class)));
		assumeThat(sh, not(instanceOf(IDot.class)));
		assumeThat(sh, not(instanceOf(IPlot.class)));
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
