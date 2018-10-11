package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.data.AxesData;
import net.sf.latexdraw.models.CompareShapeMatcher;
import net.sf.latexdraw.models.interfaces.shape.AxesStyle;
import net.sf.latexdraw.models.interfaces.shape.IAxes;
import net.sf.latexdraw.models.interfaces.shape.PlottingStyle;
import net.sf.latexdraw.models.interfaces.shape.TicksStyle;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class TestSVGAxes extends TestSVGBase<IAxes> {
	@Theory
	public void testAxesParams(@AxesData(withParamVariants = true) final IAxes sh) {
		final IAxes s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsAxes(sh, s2);
	}

	@Theory
	public void testAxesStyle(@AxesData final IAxes sh, final AxesStyle style) {
		sh.setAxesStyle(style);
		final IAxes s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsAxes(sh, s2);
	}

	@Theory
	public void testTicksStyle(@AxesData final IAxes sh, final TicksStyle style) {
		sh.setTicksStyle(style);
		final IAxes s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsAxes(sh, s2);
	}

	@Theory
	public void testLabelsDisplayed(@AxesData final IAxes sh, final PlottingStyle style) {
		sh.setLabelsDisplayed(style);
		final IAxes s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsAxes(sh, s2);
	}

	@Theory
	public void testTicksDisplayed(@AxesData final IAxes sh, final PlottingStyle style) {
		sh.setTicksDisplayed(style);
		final IAxes s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsAxes(sh, s2);
	}
}
