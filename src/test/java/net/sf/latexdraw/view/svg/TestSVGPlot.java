package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.data.PlotSupplier;
import net.sf.latexdraw.models.CompareShapeMatcher;
import net.sf.latexdraw.models.interfaces.shape.IPlot;
import net.sf.latexdraw.models.interfaces.shape.PlotStyle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

public class TestSVGPlot extends TestSVGBase<IPlot> {
	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.PlotSupplier#createDiversifiedPlot")
	void testPlotParams(final IPlot sh) {
		final IPlot s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsPlot(sh, s2);
	}

	@ParameterizedTest
	@EnumSource(PlotStyle.class)
	void testPlotParamsWithAllStyle(final PlotStyle style) {
		final IPlot sh = PlotSupplier.createPlot();
		sh.setPlotStyle(style);
		final IPlot s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsPlot(sh, s2);
	}
}
