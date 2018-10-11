package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.data.TextData;
import net.sf.latexdraw.models.CompareShapeMatcher;
import net.sf.latexdraw.models.interfaces.shape.IText;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class TestSVGText extends TestSVGBase<IText> {
	@Theory
	public void testTextPosition(@TextData(withParamVariants = true) final IText sh) {
		final IText s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsText(sh, s2);
	}
}
