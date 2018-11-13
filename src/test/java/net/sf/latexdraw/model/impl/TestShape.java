package net.sf.latexdraw.model.impl;

import java.util.List;
import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.data.DoubleData;
import net.sf.latexdraw.data.ShapeData;
import net.sf.latexdraw.model.CompareShapeMatcher;
import net.sf.latexdraw.model.MathUtils;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.BorderPos;
import net.sf.latexdraw.model.api.shape.FillingStyle;
import net.sf.latexdraw.model.api.shape.LineStyle;
import net.sf.latexdraw.model.api.shape.Point;
import net.sf.latexdraw.model.api.shape.Shape;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;

@RunWith(Theories.class)
public class TestShape implements HelperTest {
	@Theory
	public void testGetPoints(@ShapeData final Shape shape) {
		assertNotNull(shape.getPoints());
	}

	@Theory
	public void testGetNbPoints(@ShapeData final Shape shape) {
		assertEquals(shape.getPoints().size(), shape.getNbPoints());
	}

	@Theory
	public void testGetPtAt(@ShapeData final Shape shape) {
		for(int i = 0; i < shape.getNbPoints(); i++) {
			assertEquals(shape.getPoints().get(i), shape.getPtAt(i));
		}
	}

	@Theory
	public void testCopyBorderMov(@ShapeData final Shape shape, @ShapeData final Shape shape2) {
		assumeTrue(shape.isBordersMovable());
		assumeTrue(shape2.isBordersMovable());

		shape2.setBordersPosition(BorderPos.MID);
		shape.copy(shape2);
		CompareShapeMatcher.INST.assertEqualShapeBorderMov(shape, shape2);
	}

	@Theory
	public void testCopyLineStyle(@ShapeData final Shape shape, @ShapeData final Shape shape2) {
		assumeTrue(shape.isLineStylable());
		assumeTrue(shape2.isLineStylable());

		shape2.setDashSepBlack(12.);
		shape2.setDashSepWhite(14.);
		shape2.setDotSep(25.);
		shape2.setLineStyle(LineStyle.DOTTED);
		shape2.setLineColour(DviPsColors.GREEN);
		shape2.setThickness(13);
		shape.copy(shape2);
		CompareShapeMatcher.INST.assertEqualShapeLineStyle(shape, shape2);
	}

	@Theory
	public void testCopyDbleBorderable(@ShapeData final Shape shape, @ShapeData final Shape shape2) {
		assumeTrue(shape.isDbleBorderable());
		assumeTrue(shape2.isDbleBorderable());
		shape2.setDbleBordCol(DviPsColors.RED);
		shape2.setDbleBordSep(20.);
		shape2.setHasDbleBord(true);
		shape.copy(shape2);
		CompareShapeMatcher.INST.assertEqualShapeDbleBorder(shape, shape2);
	}

	@Theory
	public void testCopyFillable(@ShapeData final Shape shape, @ShapeData final Shape shape2) {
		assumeTrue(shape.isFillable());
		assumeTrue(shape2.isFillable());

		shape2.setFilled(true);
		shape2.setFillingCol(DviPsColors.BLUE);
		shape2.setFillingStyle(FillingStyle.GRAD);
		shape.copy(shape2);
		CompareShapeMatcher.INST.assertEqualShapeFill(shape, shape2);
	}

	@Theory
	public void testCopyInteriorStylable(@ShapeData final Shape shape, @ShapeData final Shape shape2) {
		assumeTrue(shape.isInteriorStylable());
		assumeTrue(shape2.isInteriorStylable());

		shape2.setGradAngle(90);
		shape2.setGradColEnd(DviPsColors.MAGENTA);
		shape2.setGradColStart(DviPsColors.DARKGRAY);
		shape2.setGradMidPt(0.9);
		shape2.setHatchingsAngle(25);
		shape2.setHatchingsCol(DviPsColors.GRAY);
		shape2.setHatchingsSep(30);
		shape2.setHatchingsWidth(100);
		shape.copy(shape2);
		CompareShapeMatcher.INST.assertEqualShapeFillStyle(shape, shape2);
	}

	@Theory
	public void testCopyShadowable(@ShapeData final Shape shape, @ShapeData final Shape shape2) {
		assumeTrue(shape.isShadowable());
		assumeTrue(shape2.isShadowable());

		shape2.setHasShadow(true);
		shape2.setShadowAngle(-40);
		shape2.setShadowCol(DviPsColors.ORANGE);
		shape2.setShadowSize(17);
		shape.copy(shape2);
		CompareShapeMatcher.INST.assertEqualShapeShadow(shape, shape2);
	}

	@Theory
	public void testCopyisShowPtsable(@ShapeData final Shape shape, @ShapeData final Shape shape2) {
		assumeTrue(shape.isShowPtsable());
		assumeTrue(shape2.isShowPtsable());
		shape2.setShowPts(true);
		shape.copy(shape2);
		CompareShapeMatcher.INST.assertEqualShapeShowPts(shape, shape2);
	}

	@Theory
	public void testCopy(@ShapeData final Shape shape, @ShapeData final Shape shape2) {
		shape2.setRotationAngle(-30);
		shape.copy(shape2);
		CompareShapeMatcher.INST.assertEqualShapeRotationAngle(shape, shape2);
	}

	@Theory
	public void testGetGravityCentre(@ShapeData final Shape shape) {
		final Point gc = shape.getGravityCentre();
		assertTrue(MathUtils.INST.isValidPt(gc));
		assertEquals((shape.getTopLeftPoint().getX() + shape.getTopRightPoint().getX()) / 2., gc.getX(), 0.0001);
		assertEquals((shape.getTopLeftPoint().getY() + shape.getBottomLeftPoint().getY()) / 2., gc.getY(), 0.0001);
	}

	@Theory
	public void testGetFullBottomRightPoint(@ShapeData final Shape shape) {
		shape.setThickness(10.);
		shape.setHasDbleBord(false);
		shape.setBordersPosition(BorderPos.INTO);
		final Point pt = shape.getBottomRightPoint();
		final double gap = shape.getBorderGap();
		pt.translate(gap, gap);
		assertEquals(pt, shape.getFullBottomRightPoint());
	}

	@Theory
	public void testGetFullTopLeftPoint(@ShapeData final Shape shape) {
		shape.setThickness(10.);
		shape.setHasDbleBord(false);
		shape.setBordersPosition(BorderPos.INTO);
		final Point pt = shape.getTopLeftPoint();
		final double gap = shape.getBorderGap();
		pt.translate(-gap, -gap);
		assertEquals(pt, shape.getFullTopLeftPoint());
	}

	@Theory
	public void testSetHasHatchings(@ShapeData final Shape shape) {
		assumeTrue(shape.isInteriorStylable());
		shape.setFillingStyle(FillingStyle.CLINES);
		assertTrue(shape.hasHatchings());
		shape.setFillingStyle(FillingStyle.CLINES_PLAIN);
		assertTrue(shape.hasHatchings());
		shape.setFillingStyle(FillingStyle.HLINES);
		assertTrue(shape.hasHatchings());
		shape.setFillingStyle(FillingStyle.HLINES_PLAIN);
		assertTrue(shape.hasHatchings());
		shape.setFillingStyle(FillingStyle.VLINES);
		assertTrue(shape.hasHatchings());
		shape.setFillingStyle(FillingStyle.VLINES_PLAIN);
		assertTrue(shape.hasHatchings());
		shape.setFillingStyle(FillingStyle.GRAD);
		assertFalse(shape.hasHatchings());
		shape.setFillingStyle(FillingStyle.NONE);
		assertFalse(shape.hasHatchings());
		shape.setFillingStyle(FillingStyle.PLAIN);
		assertFalse(shape.hasHatchings());
	}

	@Theory
	public void testSetHasGradient(@ShapeData final Shape shape) {
		assumeTrue(shape.isInteriorStylable());
		shape.setFillingStyle(FillingStyle.GRAD);
		assertTrue(shape.hasGradient());
		shape.setFillingStyle(FillingStyle.CLINES);
		assertFalse(shape.hasGradient());
		shape.setFillingStyle(FillingStyle.CLINES_PLAIN);
		assertFalse(shape.hasGradient());
		shape.setFillingStyle(FillingStyle.HLINES);
		assertFalse(shape.hasGradient());
		shape.setFillingStyle(FillingStyle.HLINES_PLAIN);
		assertFalse(shape.hasGradient());
		shape.setFillingStyle(FillingStyle.VLINES);
		assertFalse(shape.hasGradient());
		shape.setFillingStyle(FillingStyle.VLINES_PLAIN);
		assertFalse(shape.hasGradient());
		shape.setFillingStyle(FillingStyle.NONE);
		assertFalse(shape.hasGradient());
		shape.setFillingStyle(FillingStyle.PLAIN);
		assertFalse(shape.hasGradient());
	}

	@Theory
	public void testGetSetGradColEnd(@ShapeData final Shape shape) {
		assumeTrue(shape.isInteriorStylable());
		shape.setGradColEnd(DviPsColors.BLUE);
		assertEquals(DviPsColors.BLUE, shape.getGradColEnd());
	}

	@Theory
	public void testGetSetGradColStart(@ShapeData final Shape shape) {
		assumeTrue(shape.isInteriorStylable());
		shape.setGradColStart(DviPsColors.BLUE);
		assertEquals(DviPsColors.BLUE, shape.getGradColStart());
	}

	@Theory
	public void testGetSetGradAngle(@ShapeData final Shape shape, @DoubleData final double value) {
		assumeTrue(shape.isInteriorStylable());
		shape.setGradAngle(value);
		assertEqualsDouble(value, shape.getGradAngle());
	}

	@Theory
	public void testGetSetGradAngleKO(@ShapeData final Shape shape, @DoubleData(vals = {}, bads = true) final double value) {
		assumeTrue(shape.isInteriorStylable());
		shape.setGradAngle(10d);
		shape.setGradAngle(value);
		assertEqualsDouble(10d, shape.getGradAngle());
	}

	@Theory
	public void testGetSetGradMidPt(@ShapeData final Shape shape, @DoubleData(vals = {0d, 0.4, 1d}) final double value) {
		assumeTrue(shape.isInteriorStylable());
		shape.setGradMidPt(value);
		assertEqualsDouble(value, shape.getGradMidPt());
	}

	@Theory
	public void testGetSetGradMidPtKO(@ShapeData final Shape shape, @DoubleData(vals = {-0.1d, 1.1d}, bads = true) final double value) {
		assumeTrue(shape.isInteriorStylable());
		shape.setGradMidPt(0.4);
		shape.setGradMidPt(value);
		assertEqualsDouble(0.4, shape.getGradMidPt());
	}

	@Theory
	public void testGetSetHatchingsSep(@ShapeData final Shape shape, @DoubleData final double value) {
		assumeTrue(shape.isInteriorStylable());
		assumeThat(value, greaterThan(0d));
		shape.setHatchingsSep(value);
		assertEqualsDouble(value, shape.getHatchingsSep());
	}

	@Theory
	public void testGetSetHatchingsSepKO(@ShapeData final Shape shape, @DoubleData(vals = {0d, -1d}, bads = true) final double value) {
		assumeTrue(shape.isInteriorStylable());
		assumeThat(value, greaterThan(0d));
		shape.setHatchingsSep(20d);
		shape.setHatchingsSep(value);
		assertEqualsDouble(20d, shape.getHatchingsSep());
	}

	@Theory
	public void testGetSetHatchingsCol(@ShapeData final Shape shape) {
		assumeTrue(shape.isInteriorStylable());
		shape.setHatchingsCol(DviPsColors.CYAN);
		assertEquals(DviPsColors.CYAN, shape.getHatchingsCol());
	}

	@Theory
	public void testGetSetHatchingsAngle(@ShapeData final Shape shape, @DoubleData(angle = true) final double value) {
		assumeTrue(shape.isInteriorStylable());
		shape.setHatchingsAngle(value);
		assertEqualsDouble(value, shape.getHatchingsAngle());
	}

	@Theory
	public void testGetSetHatchingsAngleKO(@ShapeData final Shape shape, @DoubleData(bads = true, vals = {}) final double value) {
		assumeTrue(shape.isInteriorStylable());
		shape.setHatchingsAngle(3d);
		shape.setHatchingsAngle(value);
		assertEqualsDouble(3d, shape.getHatchingsAngle());
	}

	@Theory
	public void testGetSetHatchingsWidth(@ShapeData final Shape shape, @DoubleData final double value) {
		assumeTrue(shape.isInteriorStylable());
		assumeThat(value, greaterThan(0d));
		shape.setHatchingsWidth(value);
		assertEqualsDouble(value, shape.getHatchingsWidth());
	}

	@Theory
	public void testGetSetHatchingsWidthKO(@ShapeData final Shape shape, @DoubleData(vals = {-1d, 0d}, bads = true) final double value) {
		assumeTrue(shape.isInteriorStylable());
		shape.setHatchingsWidth(20d);
		shape.setHatchingsWidth(value);
		assertEqualsDouble(20d, shape.getHatchingsWidth());
	}

	@Theory
	public void testGetSetRotationAngle(@ShapeData final Shape shape, @DoubleData(angle = true) final double value) {
		shape.setRotationAngle(value);
		assertEqualsDouble(value, shape.getRotationAngle());
	}

	@Theory
	public void testGetSetRotationAngleKO(@ShapeData final Shape shape, @DoubleData(bads = true, vals = {}) final double value) {
		shape.setRotationAngle(3d);
		shape.setRotationAngle(value);
		assertEqualsDouble(3d, shape.getRotationAngle());
	}

	@Theory
	public void testIsSetShowPts(@ShapeData final Shape shape, final boolean value) {
		assumeTrue(shape.isShowPtsable());
		shape.setShowPts(value);
		assertEquals(value, shape.isShowPts());
	}

	@Theory
	public void testHasSetDbleBord(@ShapeData final Shape shape, final boolean value) {
		assumeTrue(shape.isDbleBorderable());
		shape.setHasDbleBord(value);
		assertEquals(value, shape.hasDbleBord());
	}

	@Theory
	public void testGetSetDbleBordCol(@ShapeData final Shape shape) {
		assumeTrue(shape.isDbleBorderable());
		shape.setDbleBordCol(DviPsColors.CYAN);
		assertEquals(DviPsColors.CYAN, shape.getDbleBordCol());
	}

	@Theory
	public void testGetSetDbleBordSep(@ShapeData final Shape shape, @DoubleData final double value) {
		assumeTrue(shape.isDbleBorderable());
		assumeThat(value, greaterThan(0d));
		shape.setDbleBordSep(value);
		assertEqualsDouble(value, shape.getDbleBordSep());
	}

	@Theory
	public void testGetSetDbleBordSepKO(@ShapeData final Shape shape, @DoubleData(bads = true, vals = {}) final double value) {
		assumeTrue(shape.isDbleBorderable());
		shape.setDbleBordSep(20d);
		shape.setDbleBordSep(value);
		assertEqualsDouble(20d, shape.getDbleBordSep());
	}

	@Theory
	public void testHasSetShadow(@ShapeData final Shape shape, final boolean value) {
		assumeTrue(shape.isShadowable());
		shape.setHasShadow(value);
		assertEquals(value, shape.hasShadow());
	}

	@Theory
	public void testGetSetShadowCol(@ShapeData final Shape shape) {
		assumeTrue(shape.isShadowable());
		shape.setShadowCol(DviPsColors.CYAN);
		assertEquals(DviPsColors.CYAN, shape.getShadowCol());
	}

	@Theory
	public void testGetSetShadowAngle(@ShapeData final Shape shape, @DoubleData(angle = true) final double value) {
		assumeTrue(shape.isShadowable());
		shape.setShadowAngle(value);
		assertEqualsDouble(value, shape.getShadowAngle());
	}

	@Theory
	public void testGetSetShadowAngleKO(@ShapeData final Shape shape, @DoubleData(bads = true, vals = {}) final double value) {
		assumeTrue(shape.isShadowable());
		shape.setShadowAngle(20d);
		shape.setShadowAngle(value);
		assertEqualsDouble(20d, shape.getShadowAngle());
	}

	@Theory
	public void testIsSetFilled(@ShapeData final Shape shape, final boolean value) {
		assumeTrue(shape.isFillable());
		shape.setFilled(value);
		assertEquals(value, shape.isFilled());
	}

	@Theory
	public void testAddToRotationAngle(@ShapeData final Shape shape, @DoubleData(angle = true) final double value) {
		shape.setRotationAngle(0d);
		shape.addToRotationAngle(null, value);
		assertEqualsDouble(value, shape.getRotationAngle());
	}

	@Theory
	public void testAddToRotationAngleKO(@ShapeData final Shape shape, @DoubleData(bads = true, vals = {}) final double value) {
		shape.setRotationAngle(10d);
		shape.addToRotationAngle(null, value);
		assertEqualsDouble(10d, shape.getRotationAngle());
	}

	@Theory
	public void testGetSetShadowSize(@ShapeData final Shape shape, @DoubleData final double value) {
		assumeTrue(shape.isShadowable());
		assumeThat(value, greaterThan(0d));
		shape.setShadowSize(value);
		assertEqualsDouble(value, shape.getShadowSize());
	}

	@Theory
	public void testGetSetShadowSizeKO(@ShapeData final Shape shape, @DoubleData(bads = true, vals = {}) final double value) {
		assumeTrue(shape.isShadowable());
		shape.setShadowSize(20d);
		shape.setShadowSize(value);
		assertEqualsDouble(20d, shape.getShadowSize());
	}

	@Theory
	public void testGetSetBorderPosition(@ShapeData final Shape shape, final BorderPos style) {
		assumeTrue(shape.isBordersMovable());
		shape.setBordersPosition(style);
		assertEquals(style, shape.getBordersPosition());
	}

	@Theory
	public void testGetBorderGapThickINTONoDble(@ShapeData final Shape shape) {
		assumeTrue(shape.isThicknessable());
		assumeTrue(shape.isBordersMovable());
		shape.setThickness(10d);
		shape.setBordersPosition(BorderPos.INTO);
		assertEqualsDouble(0d, shape.getBorderGap());
	}

	@Theory
	public void testGetBorderGapThickINTODble(@ShapeData final Shape shape) {
		assumeTrue(shape.isThicknessable());
		assumeTrue(shape.isBordersMovable());
		assumeTrue(shape.isDbleBorderable());
		shape.setThickness(10d);
		shape.setBordersPosition(BorderPos.INTO);
		shape.setHasDbleBord(true);
		shape.setDbleBordSep(5.);
		assertEqualsDouble(0d, shape.getBorderGap());
	}

	@Theory
	public void testGetBorderGapThickMIDNoDble(@ShapeData final Shape shape) {
		assumeTrue(shape.isThicknessable());
		assumeTrue(shape.isBordersMovable());
		shape.setThickness(10d);
		shape.setBordersPosition(BorderPos.MID);
		assertEqualsDouble(5d, shape.getBorderGap());
	}

	@Theory
	public void testGetBorderGapThickMIDDble(@ShapeData final Shape shape) {
		assumeTrue(shape.isThicknessable());
		assumeTrue(shape.isBordersMovable());
		assumeTrue(shape.isDbleBorderable());
		shape.setThickness(10d);
		shape.setBordersPosition(BorderPos.MID);
		shape.setHasDbleBord(true);
		shape.setDbleBordSep(20d);
		assertEqualsDouble(20d, shape.getBorderGap());
	}

	@Theory
	public void testGetBorderGapThickOUTNoDble(@ShapeData final Shape shape) {
		assumeTrue(shape.isThicknessable());
		assumeTrue(shape.isBordersMovable());
		shape.setThickness(50d);
		shape.setBordersPosition(BorderPos.OUT);
		assertEqualsDouble(50d, shape.getBorderGap());
	}

	@Theory
	public void testGetBorderGapThickOUTDble(@ShapeData final Shape shape) {
		assumeTrue(shape.isThicknessable());
		assumeTrue(shape.isBordersMovable());
		assumeTrue(shape.isDbleBorderable());
		shape.setThickness(100d);
		shape.setBordersPosition(BorderPos.OUT);
		shape.setHasDbleBord(true);
		shape.setDbleBordSep(30d);
		assertEqualsDouble(230d, shape.getBorderGap());
	}

	@Theory
	public void testDuplicate(@ShapeData final Shape shape) {
		final Shape sh = shape.duplicate();
		assertNotNull(sh);
		assertEquals(sh.getClass(), shape.getClass());
	}

	@Theory
	public void testDuplicateDoNotSharePoints(@ShapeData final Shape shape) {
		final List<Point> pts = cloneList(shape.getPoints(), pt -> ShapeFactory.INST.createPoint(pt));
		final Shape sh = shape.duplicate();
		sh.translate(11d, 12d);
		assertEquals(pts, shape.getPoints());
	}

	@Theory
	public void testSetGetThickness(@ShapeData final Shape shape, @DoubleData final double value) {
		assumeTrue(shape.isThicknessable());
		assumeThat(value, greaterThan(0d));
		shape.setThickness(value);
		assertEqualsDouble(value, shape.getThickness());
	}

	@Theory
	public void testSetGetThicknessKO(@ShapeData final Shape shape, @DoubleData(bads = true, vals = {0d, -1d}) final double value) {
		assumeTrue(shape.isThicknessable());
		shape.setThickness(11d);
		shape.setThickness(value);
		assertEqualsDouble(11d, shape.getThickness());
	}

	@Theory
	public void testSetGetLineColour(@ShapeData final Shape shape) {
		shape.setLineColour(DviPsColors.CYAN);
		assertEquals(DviPsColors.CYAN, shape.getLineColour());
	}

	@Theory
	public void testSetGetLineStyle(@ShapeData final Shape shape, final LineStyle style) {
		assumeTrue(shape.isLineStylable());
		shape.setLineStyle(style);
		assertEquals(style, shape.getLineStyle());
	}

	@Theory
	public void testSetGetDashSepWhite(@ShapeData final Shape shape, @DoubleData final double value) {
		assumeTrue(shape.isLineStylable());
		assumeThat(value, greaterThan(0d));
		shape.setDashSepWhite(value);
		assertEqualsDouble(value, shape.getDashSepWhite());
	}

	@Theory
	public void testSetGetDashSepWhiteKO(@ShapeData final Shape shape, @DoubleData(bads = true, vals = {0d, -1d}) final double value) {
		assumeTrue(shape.isLineStylable());
		shape.setDashSepWhite(5d);
		shape.setDashSepWhite(value);
		assertEqualsDouble(5d, shape.getDashSepWhite());
	}

	@Theory
	public void testSetGetDashSepBlack(@ShapeData final Shape shape, @DoubleData final double value) {
		assumeTrue(shape.isLineStylable());
		assumeThat(value, greaterThan(0d));
		shape.setDashSepBlack(value);
		assertEqualsDouble(value, shape.getDashSepBlack());
	}

	@Theory
	public void testSetGetDashSepBlackKO(@ShapeData final Shape shape, @DoubleData(bads = true, vals = {0d, -1d}) final double value) {
		assumeTrue(shape.isLineStylable());
		shape.setDashSepBlack(5d);
		shape.setDashSepBlack(value);
		assertEqualsDouble(5d, shape.getDashSepBlack());
	}

	@Theory
	public void testSetGetDotSep(@ShapeData final Shape shape, @DoubleData final double value) {
		assumeTrue(shape.isLineStylable());
		assumeThat(value, greaterThanOrEqualTo(0d));
		shape.setDotSep(value);
		assertEqualsDouble(value, shape.getDotSep());
	}

	@Theory
	public void testSetGetDotSepKO(@ShapeData final Shape shape, @DoubleData(bads = true, vals = {-1d, -0.01d}) final double value) {
		assumeTrue(shape.isLineStylable());
		shape.setDotSep(5d);
		shape.setDotSep(value);
		assertEqualsDouble(5d, shape.getDotSep());
	}

	@Theory
	public void testSetGetFillingCol(@ShapeData final Shape shape) {
		assumeTrue(shape.isInteriorStylable());
		shape.setFillingCol(DviPsColors.DARKGRAY);
		assertEquals(DviPsColors.DARKGRAY, shape.getFillingCol());
	}

	@Theory
	public void testSetGetFillingStyle(@ShapeData final Shape shape, final FillingStyle style) {
		assumeTrue(shape.isInteriorStylable());
		shape.setFillingStyle(style);
		assertEquals(style, shape.getFillingStyle());
	}
}
