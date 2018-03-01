package net.sf.latexdraw.models;

import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.models.interfaces.prop.ILineArcProp;
import net.sf.latexdraw.models.interfaces.shape.IArc;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import net.sf.latexdraw.models.interfaces.shape.IText;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public final class CompareShapeMatcher implements HelperTest {
	public static CompareShapeMatcher INST = new CompareShapeMatcher();

	private CompareShapeMatcher() {
		super();
	}

	public void assertEqualShapeLineStyle(final IShape s1, final IShape s2) {
		assertEqualsDouble(s2.getDashSepBlack(), s1.getDashSepBlack());
		assertEqualsDouble(s2.getDashSepWhite(), s1.getDashSepWhite());
		assertEqualsDouble(s2.getDotSep(), s1.getDotSep());
		assertEquals(s2.getLineStyle(), s1.getLineStyle());
		assertEquals(s2.getLineColour(), s1.getLineColour());
		assertEqualsDouble(s2.getThickness(), s1.getThickness());
	}

	public void assertEqualShapeBorderMov(final IShape s1, final IShape s2) {
		assertEquals(s2.getBordersPosition(), s1.getBordersPosition());
	}

	public void assertEqualShapeDbleBorder(final IShape s1, final IShape s2) {
		assertEquals(s2.getDbleBordCol(), s1.getDbleBordCol());
		assertEqualsDouble(s2.getDbleBordSep(), s1.getDbleBordSep());
		assertEquals(s2.hasDbleBord(), s1.hasDbleBord());
	}

	public void assertEqualShapeFill(final IShape s1, final IShape s2) {
		assertEquals(s2.isFilled(), s1.isFilled());
		assertEquals(s2.getFillingCol(), s1.getFillingCol());
		if(s1.isInteriorStylable()) {
			assertEquals(s2.getFillingStyle(), s1.getFillingStyle());
		}
	}

	public void assertEqualShapeFillStyle(final IShape s1, final IShape s2) {
		assertEqualsDouble(s2.getGradAngle(), s1.getGradAngle());
		assertEquals(s2.getGradColEnd(), s1.getGradColEnd());
		assertEquals(s2.getGradColStart(), s1.getGradColStart());
		assertEqualsDouble(s2.getGradMidPt(), s1.getGradMidPt());
		assertEqualsDouble(s2.getHatchingsAngle(), s1.getHatchingsAngle());
		assertEquals(s2.getHatchingsCol(), s1.getHatchingsCol());
		assertEqualsDouble(s2.getHatchingsSep(), s1.getHatchingsSep());
		assertEqualsDouble(s2.getHatchingsWidth(), s1.getHatchingsWidth());
	}

	public void assertEqualShapeShadow(final IShape s1, final IShape s2) {
		assertEquals(s2.hasShadow(), s1.hasShadow());
		assertThat(s2.getShadowAngle(),
			anyOf(closeTo(s1.getShadowAngle(), 0.0001), closeTo(s1.getShadowAngle() + 2d * Math.PI, 0.0001)));
		assertEqualsDouble(s2.getShadowSize(), s1.getShadowSize());
		assertEquals(s2.getShadowCol(), s1.getShadowCol());
	}

	public void assertEqualShapeShowPts(final IShape s1, final IShape s2) {
		assertEquals(s2.isShowPts(), s1.isShowPts());
	}

	public void assertEqualShapeRotationAngle(final IShape s1, final IShape s2) {
		assertEqualsDouble(s2.getRotationAngle(), s1.getRotationAngle());
	}

	public void assertEqualArcParams(final IArc a1, final IArc a2) {
		assertEqualsDouble(a2.getAngleEnd(), a1.getAngleEnd());
		assertEqualsDouble(a2.getAngleStart(), a1.getAngleStart());
		assertEquals(a2.getArcStyle(), a1.getArcStyle());
	}

	public void assertEqualsLineArc(final ILineArcProp la1, final ILineArcProp la2) {
		assertEqualsDouble(la1.getLineArc(), la2.getLineArc());
	}

	public void assertEqualsText(final IText sh1, final IText sh2) {
		assertEquals(sh1.getText(), sh2.getText());
		assertEquals(sh1.getTextPosition(), sh2.getTextPosition());
	}

	public void assertEqualsArc(final IArc sh1, final IArc sh2) {
		assertEqualsDouble(sh1.getAngleStart(), sh2.getAngleStart());
		assertEqualsDouble(sh1.getAngleEnd(), sh2.getAngleEnd());
		assertEquals(sh1.getArcStyle(), sh2.getArcStyle());
	}

	public void assertEqualsDot(final IDot sh1, final IDot sh2) {
		assertEquals(sh1.getDotStyle(), sh2.getDotStyle());
		assertEqualsDouble(sh1.getDiametre(), sh2.getDiametre());
	}

	public void assertEqualsPlot(final IPlot sh1, final IPlot sh2) {
		assertEquals(sh1.getPlotStyle(), sh2.getPlotStyle());
		assertEqualsDouble(sh1.getPlotMinX(), sh2.getPlotMinX());
		assertEqualsDouble(sh1.getPlotMaxX(), sh2.getPlotMaxX());
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
		assertEqualsDouble(sh1.getUnit(), sh2.getUnit());
		assertEqualsDouble(sh1.getSubGridWidth(), sh2.getSubGridWidth());
		assertEqualsDouble(sh1.getGridWidth(), sh2.getGridWidth());
	}
}
