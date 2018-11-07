package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.data.ShapeSupplier;
import net.sf.latexdraw.model.CompareShapeMatcher;
import net.sf.latexdraw.model.api.shape.Axes;
import net.sf.latexdraw.model.api.shape.AxesStyle;
import net.sf.latexdraw.model.api.shape.PlottingStyle;
import net.sf.latexdraw.model.api.shape.TicksStyle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

public class TestSVGAxes extends TestSVGBase<Axes> {
	Axes sh;

	@BeforeEach
	void setUp() {
		sh = ShapeSupplier.createAxes();
	}

	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.AxesSupplier#createDiversifiedAxes")
	void testAxesParams(final Axes sh) {
		final Axes s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsAxes(sh, s2);
	}

	@ParameterizedTest
	@EnumSource(AxesStyle.class)
	void testAxesStyle(final AxesStyle style) {
		sh.setAxesStyle(style);
		final Axes s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsAxes(sh, s2);
	}

	@ParameterizedTest
	@EnumSource(TicksStyle.class)
	void testTicksStyle(final TicksStyle style) {
		sh.setTicksStyle(style);
		final Axes s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsAxes(sh, s2);
	}

	@ParameterizedTest
	@EnumSource(PlottingStyle.class)
	void testLabelsDisplayed(final PlottingStyle style) {
		sh.setLabelsDisplayed(style);
		final Axes s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsAxes(sh, s2);
	}

	@ParameterizedTest
	@EnumSource(PlottingStyle.class)
	void testTicksDisplayed(final PlottingStyle style) {
		sh.setTicksDisplayed(style);
		final Axes s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsAxes(sh, s2);
	}
}
