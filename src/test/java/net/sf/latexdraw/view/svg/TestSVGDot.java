package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.model.CompareShapeMatcher;
import net.sf.latexdraw.model.api.shape.Dot;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestSVGDot extends TestSVGBase<Dot> {
	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.DotSupplier#createDiversifiedDot")
	void testDotParams(final Dot sh) {
		final Dot s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsDot(sh, s2);
	}
}
