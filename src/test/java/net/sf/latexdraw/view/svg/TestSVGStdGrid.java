package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.data.StdGridData;
import net.sf.latexdraw.models.CompareShapeMatcher;
import net.sf.latexdraw.models.interfaces.shape.IStandardGrid;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class TestSVGStdGrid extends TestSVGBase<IStandardGrid> {
	@Theory
	public void testStdGridParams(@StdGridData(withParamVariants = true) final IStandardGrid sh) {
		final IStandardGrid s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsStdGrid(sh, s2);
	}
}
