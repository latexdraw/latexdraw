package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.data.AxesSupplier;
import net.sf.latexdraw.models.CompareShapeMatcher;
import net.sf.latexdraw.models.interfaces.shape.AxesStyle;
import net.sf.latexdraw.models.interfaces.shape.IAxes;
import net.sf.latexdraw.models.interfaces.shape.PlottingStyle;
import net.sf.latexdraw.models.interfaces.shape.TicksStyle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

public class TestSVGAxes extends TestSVGBase<IAxes> {
	IAxes sh;

	@BeforeEach
	void setUp() {
		sh = AxesSupplier.createAxes();
	}

	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.AxesSupplier#createDiversifiedAxes")
	void testAxesParams(final IAxes sh) {
		final IAxes s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsAxes(sh, s2);
	}

	@ParameterizedTest
	@EnumSource(AxesStyle.class)
	void testAxesStyle(final AxesStyle style) {
		sh.setAxesStyle(style);
		final IAxes s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsAxes(sh, s2);
	}

	@ParameterizedTest
	@EnumSource(TicksStyle.class)
	void testTicksStyle(final TicksStyle style) {
		sh.setTicksStyle(style);
		final IAxes s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsAxes(sh, s2);
	}

	@ParameterizedTest
	@EnumSource(PlottingStyle.class)
	void testLabelsDisplayed(final PlottingStyle style) {
		sh.setLabelsDisplayed(style);
		final IAxes s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsAxes(sh, s2);
	}

	@ParameterizedTest
	@EnumSource(PlottingStyle.class)
	void testTicksDisplayed(final PlottingStyle style) {
		sh.setTicksDisplayed(style);
		final IAxes s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsAxes(sh, s2);
	}
}
