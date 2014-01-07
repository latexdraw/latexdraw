package test.glib.models.interfaces;

import net.sf.latexdraw.glib.models.interfaces.shape.ICircleArc;

import org.junit.Test;

import test.HelperTest;


public abstract class TestICircleArc<T extends ICircleArc> extends TestIArc<T> {
	@Test
	public void testGetRadius() {
		//TODO
	}

	@Override
	@Test
	public void testGetA() {
		shape.setPosition(10, 5);
		shape.setWidth(20);
		shape.setHeight(15);

		HelperTest.assertEqualsDouble(7.5, shape.getA());

		shape.setHeight(15);
		shape.setWidth(10);

		HelperTest.assertEqualsDouble(5., shape.getA());
	}



	@Override
	@Test
	public void testGetB() {
		shape.setPosition(10, 5);
		shape.setWidth(20);
		shape.setHeight(15);

		HelperTest.assertEqualsDouble(7.5, shape.getB());

		shape.setHeight(15);
		shape.setWidth(10);

		HelperTest.assertEqualsDouble(5., shape.getB());
	}
}
