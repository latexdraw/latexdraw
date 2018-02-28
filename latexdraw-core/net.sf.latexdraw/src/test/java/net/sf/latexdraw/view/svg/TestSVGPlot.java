package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.data.PlotData;
import net.sf.latexdraw.models.CompareShapeMatcher;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.PlotStyle;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class TestSVGPlot extends TestSVGBase<IPlot> {
	@Theory
	public void testPlotParams(@PlotData(withParamVariants = true) final IPlot sh) {
		final IPlot s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsPlot(sh, s2);
	}

	@Theory
	public void testPlotParamsWithAllStyle(@PlotData final IPlot sh, final PlotStyle style) {
		sh.setPlotStyle(style);
		final IPlot s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsPlot(sh, s2);
	}
}
