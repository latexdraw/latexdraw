package net.sf.latexdraw.view;

import net.sf.latexdraw.model.CompareShapeMatcher;
import net.sf.latexdraw.model.api.property.LineArcProp;
import net.sf.latexdraw.model.api.shape.Shape;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public interface PolymorphLineArcTest extends PolymorphicConversion<Shape> {
	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.LineArcSupplier#lineArcDiversified")
	default void testLoadSaveLineArcParams(final Shape sh) {
		final LineArcProp s2 = (LineArcProp) produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsLineArc((LineArcProp) sh, s2);
	}
}
