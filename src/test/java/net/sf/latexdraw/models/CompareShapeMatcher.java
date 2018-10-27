package net.sf.latexdraw.models;

import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.models.interfaces.prop.ILineArcProp;
import net.sf.latexdraw.models.interfaces.shape.IArc;
import net.sf.latexdraw.models.interfaces.shape.IArrow;
import net.sf.latexdraw.models.interfaces.shape.IAxes;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.IStandardGrid;
import net.sf.latexdraw.models.interfaces.shape.IText;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public final class CompareShapeMatcher implements HelperTest {
	public static final CompareShapeMatcher INST = new CompareShapeMatcher();

	private CompareShapeMatcher() {
		super();
	}

	public void assertEqualShapeLineStyle(final IShape s1, final IShape s2) {
		if(s1.isThicknessable()) {
			assertEquals(s2.getThickness(), s1.getThickness(), 0.001);
		}
		if(s2.isLineStylable()) {
			assertEquals(s2.getLineStyle(), s1.getLineStyle());
			assertEquals(s2.getDashSepBlack(), s1.getDashSepBlack(), 0.001);
			assertEquals(s2.getDashSepWhite(), s1.getDashSepWhite(), 0.001);
			assertEquals(s2.getDotSep(), s1.getDotSep(), 0.001);
		}
		assertEquals(s2.getLineColour(), s1.getLineColour());
	}

	public void assertEqualShapeBorderMov(final IShape s1, final IShape s2) {
		assertEquals(s2.getBordersPosition(), s1.getBordersPosition());
	}

	public void assertEqualShapeDbleBorder(final IShape s1, final IShape s2) {
		if(s2.isDbleBorderable()) {
			assertEquals(s2.getDbleBordCol(), s1.getDbleBordCol());
			assertEquals(s2.getDbleBordSep(), s1.getDbleBordSep(), 0.001);
			assertEquals(s2.hasDbleBord(), s1.hasDbleBord());
		}
	}

	public void assertEqualShapeFill(final IShape s1, final IShape s2) {
		if(s1.isFillable()) {
			assertEquals(s2.isFilled(), s1.isFilled());
			assertEquals(s2.getFillingCol(), s1.getFillingCol());
		}
		if(s1.isInteriorStylable()) {
			assertEquals(s2.getFillingStyle(), s1.getFillingStyle());
		}
	}

	public void assertEqualShapeFillStyle(final IShape s1, final IShape s2) {
		if(s2.getFillingStyle().isGradient()) {
			assertEquals(s2.getGradAngle(), s1.getGradAngle(), 0.001);
			assertEquals(s2.getGradColEnd(), s1.getGradColEnd());
			assertEquals(s2.getGradColStart(), s1.getGradColStart());
			assertEquals(s2.getGradMidPt(), s1.getGradMidPt(), 0.001);
		}
		if(s2.getFillingStyle().isHatchings()) {
			assertEquals(s2.getHatchingsAngle(), s1.getHatchingsAngle(), 0.001);
			assertEquals(s2.getHatchingsCol(), s1.getHatchingsCol());
			assertEquals(s2.getHatchingsSep(), s1.getHatchingsSep(), 0.001);
			assertEquals(s2.getHatchingsWidth(), s1.getHatchingsWidth(), 0.001);
		}
	}

	public void assertEqualShapeShadow(final IShape s1, final IShape s2) {
		assertEquals(s2.hasShadow(), s1.hasShadow());
		if(s2.hasShadow()) {
			assertThat(s2.getShadowAngle(), anyOf(closeTo(s1.getShadowAngle(), 0.0001), closeTo(s1.getShadowAngle() + 2d * Math.PI, 0.001)));
			assertEquals(s2.getShadowSize(), s1.getShadowSize(), 0.001);
			assertEquals(s2.getShadowCol(), s1.getShadowCol());
		}
	}

	public void assertEqualShapeShowPts(final IShape s1, final IShape s2) {
		assertEquals(s2.isShowPts(), s1.isShowPts());
	}

	public void assertEqualShapeRotationAngle(final IShape s1, final IShape s2) {
		assertEquals(s2.getRotationAngle(), s1.getRotationAngle(), 0.001);
	}

	public void assertEqualArcParams(final IArc a1, final IArc a2) {
		assertEquals(a2.getAngleEnd(), a1.getAngleEnd(), 0.001);
		assertEquals(a2.getAngleStart(), a1.getAngleStart(), 0.001);
		assertEquals(a2.getArcStyle(), a1.getArcStyle());
	}

	public void assertEqualsLineArc(final ILineArcProp la1, final ILineArcProp la2) {
		assertEquals(la1.getLineArc(), la2.getLineArc(), 0.001);
	}

	public void assertEqualsText(final IText sh1, final IText sh2) {
		assertEquals(sh1.getText(), sh2.getText());
		assertEquals(sh1.getTextPosition(), sh2.getTextPosition());
	}

	public void assertEqualsArc(final IArc sh1, final IArc sh2) {
		assertEquals(sh1.getAngleStart(), sh2.getAngleStart(), 0.001);
		assertEquals(sh1.getAngleEnd(), sh2.getAngleEnd(), 0.001);
		assertEquals(sh1.getArcStyle(), sh2.getArcStyle());
	}

	public void assertEqualsDot(final IDot sh1, final IDot sh2) {
		assertEquals(sh1.getDotStyle(), sh2.getDotStyle());
		assertEquals(sh1.getDiametre(), sh2.getDiametre(), 0.001);
	}

	public void assertEqualsPlot(final IPlot sh1, final IPlot sh2) {
		assertEquals(sh1.getPlotStyle(), sh2.getPlotStyle());
		assertEquals(sh1.getPlotMinX(), sh2.getPlotMinX(), 0.001);
		assertEquals(sh1.getPlotMaxX(), sh2.getPlotMaxX(), 0.001);
		assertEquals(sh1.getNbPlottedPoints(), sh2.getNbPlottedPoints());
		assertEquals(sh1.getPlotEquation(), sh2.getPlotEquation());
	}

	public void assertEqualsGrid(final IGrid sh1, final IGrid sh2) {
		assertEquals(sh1.getGridLabelsColour(), sh2.getGridLabelsColour());
		assertEquals(sh1.getGridDots(), sh2.getGridDots());
		assertEquals(sh1.getSubGridColour(), sh2.getSubGridColour());
		assertEquals(sh1.getSubGridDots(), sh2.getSubGridDots());
		assertEquals(sh1.getSubGridDiv(), sh2.getSubGridDiv());
		assertEquals(sh1.isXLabelSouth(), sh2.isXLabelSouth());
		assertEquals(sh1.isYLabelWest(), sh2.isYLabelWest());
		assertEquals(sh1.getUnit(), sh2.getUnit(), 0.001);
		assertEquals(sh1.getSubGridWidth(), sh2.getSubGridWidth(), 0.001);
		assertEquals(sh1.getGridWidth(), sh2.getGridWidth(), 0.001);
	}

	public void assertEqualsAxes(final IAxes sh1, final IAxes sh2) {
		assertEquals(sh1.getLabelsDisplayed(), sh2.getLabelsDisplayed());
		assertEquals(sh1.getTicksDisplayed(), sh2.getTicksDisplayed());
		assertEquals(sh1.isShowOrigin(), sh2.isShowOrigin());
		assertEquals(sh1.getTicksStyle(), sh2.getTicksStyle());
		assertEquals(sh1.getAxesStyle(), sh2.getAxesStyle());
		assertEquals(sh1.getIncrementX(), sh2.getIncrementX(), 0.001);
		assertEquals(sh1.getIncrementY(), sh2.getIncrementY(), 0.001);
		assertEquals(sh1.getDistLabelsX(), sh2.getDistLabelsX(), 0.001);
		assertEquals(sh1.getDistLabelsY(), sh2.getDistLabelsY(), 0.001);
		assertEquals(sh1.getTicksSize(), sh2.getTicksSize(), 0.001);
	}

	public void assertEqualsStdGrid(final IStandardGrid sh1, final IStandardGrid sh2) {
		assertEquals(sh1.getGridEndX(), sh2.getGridEndX(), 0.001);
		assertEquals(sh1.getGridEndY(), sh2.getGridEndY(), 0.001);
		assertEquals(sh1.getGridStartX(), sh2.getGridStartX(), 0.001);
		assertEquals(sh1.getGridStartY(), sh2.getGridStartY(), 0.001);
		assertEquals(sh1.getOriginX(), sh2.getOriginX(), 0.001);
		assertEquals(sh1.getOriginY(), sh2.getOriginY(), 0.001);
		assertEquals(sh1.getLabelsSize(), sh2.getLabelsSize());
	}

	public void assertEqualsArrowStyle(final IArrow arr1, final IArrow arr2) {
		assertEquals(arr1.getArrowStyle(), arr2.getArrowStyle());
	}

	public void assertEqualsArrowArrow(final IArrow arr1, final IArrow arr2) {
		assertEquals(arr1.getArrowInset(), arr2.getArrowInset(), 0.001);
		assertEquals(arr1.getArrowLength(), arr2.getArrowLength(), 0.001);
		assertEquals(arr1.getArrowSizeDim(), arr2.getArrowSizeDim(), 0.001);
		assertEquals(arr1.getArrowSizeNum(), arr2.getArrowSizeNum(), 0.001);
	}

	public void assertEqualsArrowBar(final IArrow arr1, final IArrow arr2) {
		final String msg = arr1.getArrowStyle() + " " + arr2.getArrowStyle();
		assertEquals(msg, arr1.getTBarSizeNum(), arr2.getTBarSizeNum(), 0.001);
		assertEquals(msg, arr1.getTBarSizeDim(), arr2.getTBarSizeDim(), 0.001);
	}

	public void assertEqualsArrowBracket(final IArrow arr1, final IArrow arr2) {
		final String msg = arr1.getArrowStyle() + " " + arr2.getArrowStyle();
		assertEquals(msg, arr1.getBracketNum(), arr2.getBracketNum(), 0.001);
	}

	public void assertEqualsArrowRBracket(final IArrow arr1, final IArrow arr2) {
		final String msg = arr1.getArrowStyle() + " " + arr2.getArrowStyle();
		assertEquals(msg, arr1.getRBracketNum(), arr2.getRBracketNum(), 0.001);
	}

	public void assertEqualsArrowCircleDisk(final IArrow arr1, final IArrow arr2) {
		final String msg = arr1.getArrowStyle() + " " + arr2.getArrowStyle();
		assertEquals(msg, arr1.getDotSizeNum(), arr2.getDotSizeNum(), 0.001);
		assertEquals(msg, arr1.getDotSizeDim(), arr2.getDotSizeDim(), 0.001);
	}
}
