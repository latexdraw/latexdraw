package test.models.interfaces;

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

	@Theory
	public void testGetPlotStyleOK(final PlotStyle style) {
		assertEquals(style, PlotStyle.getPlotStyle(style.getPSTToken()));
	}
}
