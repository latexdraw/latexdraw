package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.model.CompareShapeMatcher;
import net.sf.latexdraw.model.api.property.LineArcProp;
import net.sf.latexdraw.model.api.shape.Shape;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestSVGLineArc extends TestSVGBase<Shape> implements HelperTest {
	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.LineArcSupplier#lineArcDiversified")
	void testLoadSaveLineArcParams(final Shape sh) {
		final LineArcProp s2 = (LineArcProp) produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsLineArc((LineArcProp) sh, s2);
	}
}
