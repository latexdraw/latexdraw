package net.sf.latexdraw.view;

import net.sf.latexdraw.model.CompareShapeMatcher;
import net.sf.latexdraw.model.api.shape.StandardGrid;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public interface PolymorphStdGridTest extends PolymorphicConversion<StandardGrid> {
	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.StdGridSupplier#createDiversifiedStdGrids")
	default void testStdGridParams(final StandardGrid sh) {
		final StandardGrid s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsStdGrid(sh, s2);
	}
}
