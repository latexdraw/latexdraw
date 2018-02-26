package net.sf.latexdraw.view.svg;

import net.sf.latexdraw.HelperTest;
import net.sf.latexdraw.data.RectData;
import net.sf.latexdraw.models.CompareShapeMatcher;
import net.sf.latexdraw.models.interfaces.shape.IRectangle;
import org.junit.Ignore;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assume.assumeTrue;

@RunWith(Theories.class)
public class testSVGRectangle extends TestSVGBase<IRectangle> implements HelperTest {
	@Ignore
	@Theory
	public void testLoadSaveShapeParams(@RectData(withParamVariants = true) final IRectangle sh) {
		assumeTrue(sh.isBordersMovable());
		final IRectangle s2 = produceOutputShapeFrom(sh);
		CompareShapeMatcher.INST.assertEqualsRectangleArc(sh, s2);
	}
}
