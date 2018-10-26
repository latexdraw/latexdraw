package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.models.CompareShapeMatcher;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestSVGDot extends TestSVGBase<IDot> {
	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.DotSupplier#createDiversifiedDot")
	void testDotParams(final IDot sh) {
		final IDot s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsDot(sh, s2);
	}
}
