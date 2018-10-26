package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.models.CompareShapeMatcher;
import net.sf.latexdraw.models.interfaces.shape.IText;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestSVGText extends TestSVGBase<IText> {
	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.TextSupplier#createDiversifiedText")
	void testTextPosition(final IText sh) {
		final IText s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsText(sh, s2);
	}
}
