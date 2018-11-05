package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.model.CompareShapeMatcher;
import net.sf.latexdraw.model.api.shape.Text;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestSVGText extends TestSVGBase<Text> {
	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.TextSupplier#createDiversifiedText")
	void testTextPosition(final Text sh) {
		final Text s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsText(sh, s2);
	}
}
