package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.data.PlotSupplier;
import net.sf.latexdraw.model.CompareShapeMatcher;
import net.sf.latexdraw.model.api.shape.Plot;
import net.sf.latexdraw.model.api.shape.PlotStyle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

public class TestSVGPlot extends TestSVGBase<Plot> {
	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.PlotSupplier#createDiversifiedPlot")
	void testPlotParams(final Plot sh) {
		final Plot s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsPlot(sh, s2);
	}

	@ParameterizedTest
	@EnumSource(PlotStyle.class)
	void testPlotParamsWithAllStyle(final PlotStyle style) {
		final Plot sh = PlotSupplier.createPlot();
		sh.setPlotStyle(style);
		final Plot s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsPlot(sh, s2);
	}
}
