package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.models.CompareShapeMatcher;
import net.sf.latexdraw.models.interfaces.prop.ILineArcProp;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestSVGLineArc extends TestSVGBase<IShape> implements HelperTest {
	@ParameterizedTest
	@MethodSource("net.sf.latexdraw.data.LineArcSupplier#lineArcDiversified")
	void testLoadSaveLineArcParams(final IShape sh) {
		final ILineArcProp s2 = (ILineArcProp) produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsLineArc((ILineArcProp) sh, s2);
	}
}
