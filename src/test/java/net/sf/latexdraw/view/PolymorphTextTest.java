package net.sf.latexdraw.view;

import net.sf.latexdraw.model.CompareShapeMatcher;
import net.sf.latexdraw.model.api.shape.Text;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public interface PolymorphTextTest extends PolymorphicConversion<Text> {
	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.ShapeSupplier#createDiversifiedText")
	default void testTextPosition(final Text sh) {
		final Text s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsText(sh, s2);
	}
}
