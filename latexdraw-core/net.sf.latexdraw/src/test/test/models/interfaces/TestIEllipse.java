package test.models.interfaces;

import net.sf.latexdraw.models.interfaces.shape.IEllipse;
import org.junit.Test;

public abstract class TestIEllipse<T extends IEllipse> extends TestIRectangularShape<T> {
	@Test
	public void testGetA() {
		shape.setPosition(10, 5);
		shape.setWidth(20);
		shape.setHeight(15);

		assertEqualsDouble(10., shape.getA());

		shape.setWidth(10);
		shape.setHeight(15);

		assertEqualsDouble(7.5, shape.getA());
	}

	@Test
	public void testGetB() {
		shape.setPosition(10, 5);
		shape.setWidth(20);
		shape.setHeight(15);

		assertEqualsDouble(7.5, shape.getB());

		shape.setWidth(10);
		shape.setHeight(15);

		assertEqualsDouble(5., shape.getB());
	}
}
