package test.glib.models.interfaces;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.sf.latexdraw.glib.models.interfaces.shape.PlotStyle;

public class TestPlotStyle {
	@Test
	public void testGetPSTToken() {
		assertEquals("curve", PlotStyle.CURVE.getPSTToken()); //$NON-NLS-1$
		assertEquals("ccurve", PlotStyle.CCURVE.getPSTToken()); //$NON-NLS-1$
		assertEquals("dots", PlotStyle.DOTS.getPSTToken()); //$NON-NLS-1$
		assertEquals("ecurve", PlotStyle.ECURVE.getPSTToken()); //$NON-NLS-1$
		assertEquals("line", PlotStyle.LINE.getPSTToken()); //$NON-NLS-1$
		assertEquals("polygon", PlotStyle.POLYGON.getPSTToken()); //$NON-NLS-1$
	}

	@Test
	public void testGetPlotStyleNULL() {
		assertEquals(PlotStyle.CURVE, PlotStyle.getPlotStyle(null));
	}

	@Test
	public void testGetPlotStyleEmpty() {
		assertEquals(PlotStyle.CURVE, PlotStyle.getPlotStyle("")); //$NON-NLS-1$
	}

	@Test
	public void testGetPlotStyleNotCorrect() {
		assertEquals(PlotStyle.CURVE, PlotStyle.getPlotStyle("dfs@fgd")); //$NON-NLS-1$
	}

	@Test
	public void testGetPlotStyleOK() {
		assertEquals(PlotStyle.CURVE, PlotStyle.getPlotStyle(PlotStyle.CURVE.getPSTToken()));
		assertEquals(PlotStyle.CCURVE, PlotStyle.getPlotStyle(PlotStyle.CCURVE.getPSTToken()));
		assertEquals(PlotStyle.DOTS, PlotStyle.getPlotStyle(PlotStyle.DOTS.getPSTToken()));
		assertEquals(PlotStyle.ECURVE, PlotStyle.getPlotStyle(PlotStyle.ECURVE.getPSTToken()));
		assertEquals(PlotStyle.LINE, PlotStyle.getPlotStyle(PlotStyle.LINE.getPSTToken()));
		assertEquals(PlotStyle.POLYGON, PlotStyle.getPlotStyle(PlotStyle.POLYGON.getPSTToken()));

	}
}
