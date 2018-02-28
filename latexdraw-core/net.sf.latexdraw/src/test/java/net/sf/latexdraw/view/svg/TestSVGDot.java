package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.data.DotData;
import net.sf.latexdraw.models.CompareShapeMatcher;
import net.sf.latexdraw.models.interfaces.shape.IDot;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class TestSVGDot extends TestSVGBase<IDot> {
	@Theory
	public void testTextPosition(@DotData(withParamVariants = true) final IDot sh) {
		final IDot s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsDot(sh, s2);
	}
}
