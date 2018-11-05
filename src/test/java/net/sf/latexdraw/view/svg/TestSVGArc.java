package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.model.CompareShapeMatcher;
import net.sf.latexdraw.model.api.shape.Arc;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestSVGArc extends TestSVGBase<Arc> {
	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ArcSupplier#createDiversifiedArc")
	void testStartAngle(final Arc sh) {
		final Arc s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArc(sh, s2);
	}
}
