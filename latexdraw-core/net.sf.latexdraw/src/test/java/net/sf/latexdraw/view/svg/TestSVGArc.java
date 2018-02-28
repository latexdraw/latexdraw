package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.data.ArcData;
import net.sf.latexdraw.models.CompareShapeMatcher;
import net.sf.latexdraw.models.interfaces.shape.IArc;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class TestSVGArc extends TestSVGBase<IArc> {
	@Theory
	public void testStartAngle(@ArcData(withParamVariants = true) final IArc sh) {
		final IArc s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsArc(sh, s2);
	}
}
