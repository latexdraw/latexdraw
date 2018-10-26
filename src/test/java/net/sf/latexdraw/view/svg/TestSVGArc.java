package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.models.CompareShapeMatcher;
import net.sf.latexdraw.models.interfaces.shape.IArc;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestSVGArc extends TestSVGBase<IArc> {
	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ArcSupplier#createDiversifiedArc")
	void testStartAngle(final IArc sh) {
		final IArc s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArc(sh, s2);
	}
}
