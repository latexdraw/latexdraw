package test.glib.models.interfaces;

import static org.junit.Assert.assertEquals;
import net.sf.latexdraw.glib.models.interfaces.prop.IPlotProp.PlotStyle;

import org.junit.Test;

public class TestPlotStyle {
	@Test public void testGetPSTToken() {
		assertEquals("curve", PlotStyle.CURVE.getPSTToken());
		assertEquals("ccurve", PlotStyle.CCURVE.getPSTToken());
		assertEquals("dots", PlotStyle.DOTS.getPSTToken());
		assertEquals("ecurve", PlotStyle.ECURVE.getPSTToken());
		assertEquals("line", PlotStyle.LINE.getPSTToken());
		assertEquals("polygon", PlotStyle.POLYGON.getPSTToken());
	}

	@Test public void testGetPlotStyleNULL() {
		assertEquals(PlotStyle.CURVE, PlotStyle.getPlotStyle(null));
	}

	@Test public void testGetPlotStyleEmpty() {
		assertEquals(PlotStyle.CURVE, PlotStyle.getPlotStyle(""));
	}

	@Test public void testGetPlotStyleNotCorrect() {
		assertEquals(PlotStyle.CURVE, PlotStyle.getPlotStyle("dfs@fgd"));
	}

	@Test public void testGetPlotStyleOK() {
		assertEquals(PlotStyle.CURVE, PlotStyle.getPlotStyle(PlotStyle.CURVE.getPSTToken()));
		assertEquals(PlotStyle.CCURVE, PlotStyle.getPlotStyle(PlotStyle.CCURVE.getPSTToken()));
		assertEquals(PlotStyle.DOTS, PlotStyle.getPlotStyle(PlotStyle.DOTS.getPSTToken()));
		assertEquals(PlotStyle.ECURVE, PlotStyle.getPlotStyle(PlotStyle.ECURVE.getPSTToken()));
		assertEquals(PlotStyle.LINE, PlotStyle.getPlotStyle(PlotStyle.LINE.getPSTToken()));
		assertEquals(PlotStyle.POLYGON, PlotStyle.getPlotStyle(PlotStyle.POLYGON.getPSTToken()));

	}
}
