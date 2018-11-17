package net.sf.latexdraw.view;

import net.sf.latexdraw.model.CompareShapeMatcher;
import net.sf.latexdraw.model.api.shape.Grid;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public interface PolymorphGridTest extends PolymorphicConversion<Grid> {
	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#createDiversifiedGrid")
	default void testGridParams(final Grid sh) {
		final Grid s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsGrid(sh, s2);
	}
}
