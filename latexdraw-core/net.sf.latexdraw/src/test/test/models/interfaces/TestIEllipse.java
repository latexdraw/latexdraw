package test.models.interfaces;

import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import test.HelperTest;
import test.data.DoubleData;
import test.data.EllData;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assume.assumeThat;

@RunWith(Theories.class)
public class TestIEllipse implements HelperTest {
	@Theory
	public void testGetAB(@EllData final IEllipse shape, @DoubleData final double w, @DoubleData final double h,
						 @DoubleData final double x, @DoubleData final double y) {
		assumeThat(w, greaterThan(0d));
		assumeThat(h, greaterThan(0d));

		shape.setPosition(x, y);
		shape.setWidth(w);
		shape.setHeight(h);

		assertEqualsDouble(Math.max(w, h) / 2d, shape.getA());
		assertEqualsDouble(Math.min(w, h) / 2d, shape.getB());
	}
}
