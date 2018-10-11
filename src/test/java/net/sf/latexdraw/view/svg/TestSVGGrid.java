package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.data.GridData;
import net.sf.latexdraw.models.CompareShapeMatcher;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class TestSVGGrid extends TestSVGBase<IGrid> {
	@Theory
	public void testGridParams(@GridData(withParamVariants = true) final IGrid sh) {
		final IGrid s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsGrid(sh, s2);
	}
}
