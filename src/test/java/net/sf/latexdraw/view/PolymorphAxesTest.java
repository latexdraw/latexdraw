package net.sf.latexdraw.view;

import net.sf.latexdraw.data.ShapeSupplier;
import net.sf.latexdraw.model.CompareShapeMatcher;
import net.sf.latexdraw.model.api.shape.Axes;
import net.sf.latexdraw.model.api.shape.AxesStyle;
import net.sf.latexdraw.model.api.shape.PlottingStyle;
import net.sf.latexdraw.model.api.shape.TicksStyle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

public interface PolymorphAxesTest extends PolymorphicConversion<Axes> {
	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#createDiversifiedAxes")
	default void testAxesParams(final Axes sh) {
		final Axes s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsAxes(sh, s2);
	}

	@ParameterizedTest
	@EnumSource(AxesStyle.class)
	default void testAxesStyle(final AxesStyle style) {
		final Axes sh = ShapeSupplier.createAxes();
		sh.setAxesStyle(style);
		final Axes s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsAxes(sh, s2);
	}

	@ParameterizedTest
	@EnumSource(TicksStyle.class)
	default void testTicksStyle(final TicksStyle style) {
		final Axes sh = ShapeSupplier.createAxes();
		sh.setTicksStyle(style);
		final Axes s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsAxes(sh, s2);
	}

	@ParameterizedTest
	@EnumSource(PlottingStyle.class)
	default void testLabelsDisplayed(final PlottingStyle style) {
		final Axes sh = ShapeSupplier.createAxes();
		sh.setLabelsDisplayed(style);
		final Axes s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsAxes(sh, s2);
	}

	@ParameterizedTest
	@EnumSource(PlottingStyle.class)
	default void testTicksDisplayed(final PlottingStyle style) {
		final Axes sh = ShapeSupplier.createAxes();
		sh.setTicksDisplayed(style);
		final Axes s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsAxes(sh, s2);
	}
}
