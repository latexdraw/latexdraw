package net.sf.latexdraw.models;

import net.sf.latexdraw.models.interfaces.shape.PlotStyle;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(Theories.class)
public class TestPlotStyle {
	@Test
	public void testGetPSTToken() {
		assertEquals("curve", PlotStyle.CURVE.getPSTToken());
		assertEquals("ccurve", PlotStyle.CCURVE.getPSTToken());
		assertEquals("dots", PlotStyle.DOTS.getPSTToken());
		assertEquals("ecurve", PlotStyle.ECURVE.getPSTToken());
		assertEquals("line", PlotStyle.LINE.getPSTToken());
		assertEquals("polygon", PlotStyle.POLYGON.getPSTToken());
	}

	@Test
	public void testGetPlotStyleNULL() {
		assertEquals(PlotStyle.CURVE, PlotStyle.getPlotStyle(null));
	}

	@Test
	public void testGetPlotStyleEmpty() {
		assertEquals(PlotStyle.CURVE, PlotStyle.getPlotStyle(""));
	}

	@Test
	public void testGetPlotStyleNotCorrect() {
		assertEquals(PlotStyle.CURVE, PlotStyle.getPlotStyle("dfs@fgd"));
	}

	@Theory
	public void testGetPlotStyleOK(final PlotStyle style) {
		assertEquals(style, PlotStyle.getPlotStyle(style.getPSTToken()));
	}
}
