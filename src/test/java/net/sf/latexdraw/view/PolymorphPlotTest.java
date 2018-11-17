package net.sf.latexdraw.view;

import net.sf.latexdraw.data.ShapeSupplier;
import net.sf.latexdraw.model.CompareShapeMatcher;
import net.sf.latexdraw.model.api.shape.Plot;
import net.sf.latexdraw.model.api.shape.PlotStyle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

public interface PolymorphPlotTest extends PolymorphicConversion<Plot> {
	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#createDiversifiedPlot")
	default void testPlotParams(final Plot sh) {
		final Plot s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsPlot(sh, s2);
	}

	@ParameterizedTest
	@EnumSource(PlotStyle.class)
	default void testPlotParamsWithAllStyle(final PlotStyle style) {
		final Plot sh = ShapeSupplier.createPlot();
		sh.setPlotStyle(style);
		final Plot s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsPlot(sh, s2);
	}
}
