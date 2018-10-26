package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.models.CompareShapeMatcher;
import net.sf.latexdraw.models.interfaces.shape.IStandardGrid;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestSVGStdGrid extends TestSVGBase<IStandardGrid> {
	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.StdGridSupplier#createDiversifiedStdGrids")
	void testStdGridParams(final IStandardGrid sh) {
		final IStandardGrid s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsStdGrid(sh, s2);
	}
}
