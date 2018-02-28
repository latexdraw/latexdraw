package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.data.LineArcData;
import net.sf.latexdraw.models.CompareShapeMatcher;
import net.sf.latexdraw.models.interfaces.prop.ILineArcProp;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class TestSVGLineArc extends TestSVGBase<IShape> implements HelperTest {
	@Theory
	public void testLoadSaveLineArcParams(@LineArcData(withParamVariants = true) final IShape sh) {
		final ILineArcProp s2 = (ILineArcProp) produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsLineArc((ILineArcProp) sh, s2);
	}
}
