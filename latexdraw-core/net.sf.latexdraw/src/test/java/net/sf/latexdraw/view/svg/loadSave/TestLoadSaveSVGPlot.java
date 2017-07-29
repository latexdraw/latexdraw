package net.sf.latexdraw.view.svg.loadSave;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.DotStyle;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.PlotStyle;
import net.sf.latexdraw.view.latex.DviPsColors;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestLoadSaveSVGPlot extends TestLoadSaveSVGPositionShape<IPlot> {
	@Before
	public void setUp() {
		shape = ShapeFactory.INST.createPlot(ShapeFactory.INST.createPoint(), -338.0, 124.0, "2 x mul", true);
		shape.setNbPlottedPoints(331);
	}

	@Test
	public void testPropertiesDot() {
		shape.setPlotStyle(PlotStyle.DOTS);
		for(DotStyle style : DotStyle.values()) {
			shape.setDotStyle(style);
			assertEquals(style.isFillable(), shape.isFillable());
			assertFalse(shape.isShadowable());
			assertFalse(shape.isBordersMovable());
			assertFalse(shape.isInteriorStylable());
			assertFalse(shape.isLineStylable());
			assertFalse(shape.isShowPtsable());
			assertFalse(shape.isThicknessable());
		}
	}

	@Test
	public void testPropertiesNotDot() {
		shape.setPlotStyle(PlotStyle.CURVE);
		assertTrue(shape.isFillable());
		assertTrue(shape.isShadowable());
		assertFalse(shape.isBordersMovable());
		assertTrue(shape.isInteriorStylable());
		assertTrue(shape.isLineStylable());
		assertFalse(shape.isShowPtsable());
		assertTrue(shape.isThicknessable());
	}

	@Test
	public void testPlotDotsFillable() {
		shape.setPlotStyle(PlotStyle.DOTS);
		shape.setDiametre(12.0);
		shape.setDotStyle(DotStyle.DIAMOND);
		shape.setFillingCol(DviPsColors.BLUE);
		compareShapes(generateShape());
	}

	@Test
	public void testPlotDotsNotFillable() {
		shape.setPlotStyle(PlotStyle.DOTS);
		shape.setDiametre(12.0);
		shape.setDotStyle(DotStyle.DOT);
		shape.setLineColour(DviPsColors.BLUE);
		compareShapes(generateShape());
	}

	@Test
	public void testPlotCurves() {
		shape.setPlotStyle(PlotStyle.CURVE);
		compareShapes(generateShape());
		shape.setPlotStyle(PlotStyle.CCURVE);
		compareShapes(generateShape());
		shape.setPlotStyle(PlotStyle.ECURVE);
		compareShapes(generateShape());
	}

	@Test
	public void testPlotLines() {
		shape.setPlotStyle(PlotStyle.LINE);
		compareShapes(generateShape());
		shape.setPlotStyle(PlotStyle.POLYGON);
		compareShapes(generateShape());
	}

	@Override
	protected void compareShapes(final IPlot sh2) {
		super.compareShapes(sh2);
		assertEquals(shape.getPosition().getX(), sh2.getPosition().getX(), 0.0001);
		assertEquals(shape.getPosition().getY(), sh2.getPosition().getY(), 0.0001);
		assertEquals(shape.getPlotMaxX(), sh2.getPlotMaxX(), 0.0001);
		assertEquals(shape.getPlotMinX(), sh2.getPlotMinX(), 0.0001);
		assertEquals(shape.getPlotEquation(), sh2.getPlotEquation());
		assertEquals(shape.getPlotStyle(), sh2.getPlotStyle());
		assertEquals(shape.getPlottingStep(), sh2.getPlottingStep(), 0.0001);
		assertEquals(shape.getNbPlottedPoints(), sh2.getNbPlottedPoints());
		assertEquals(shape.getDotStyle(), sh2.getDotStyle());
		assertEquals(shape.getDiametre(), sh2.getDiametre(), 0.0001);
		if(shape.isFillable())
			assertEquals(shape.getDotFillingCol(), sh2.getDotFillingCol());
	}
}
