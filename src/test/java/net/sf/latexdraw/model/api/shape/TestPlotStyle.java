package net.sf.latexdraw.model.api.shape;

import net.sf.latexdraw.LatexdrawExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(LatexdrawExtension.class)
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

	@ParameterizedTest
	@EnumSource
	public void testGetPlotStyleOK(final PlotStyle style) {
		assertEquals(style, PlotStyle.getPlotStyle(style.getPSTToken()));
	}
}
