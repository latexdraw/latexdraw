package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.models.CompareShapeMatcher;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestSVGGrid extends TestSVGBase<IGrid> {
	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.GridSupplier#createDiversifiedGrid")
	void testGridParams(final IGrid sh) {
		final IGrid s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsGrid(sh, s2);
	}
}
