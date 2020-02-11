package net.sf.latexdraw.view;

import net.sf.latexdraw.model.CompareShapeMatcher;
import net.sf.latexdraw.model.api.shape.Dot;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public interface PolymorphDotTest extends PolymorphicConversion<Dot> {
	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#createDiversifiedDot")
	default void testDotParams(final Dot sh) {
		final Dot s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsDot(sh, s2);
	}
}
