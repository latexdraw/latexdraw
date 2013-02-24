package test.glib.models.interfaces;

import net.sf.latexdraw.glib.models.interfaces.ICircleArc;

import org.junit.Test;


public abstract class TestICircleArc<T extends ICircleArc> extends TestIArc<T> {
	@Test
	public void testGetRadius() {
		//TODO
	}


	@Override
	@Test
	public void testGetRx() {
		shape.setPosition(10, 5);
		shape.setWidth(20);
		shape.setHeight(15);

		assertEquals(7.5, shape.getRx());
		shape.setHeight(10);
		shape.setWidth(5);

		assertEquals(2.5, shape.getRx());
	}


	@Override
	@Test
	public void testGetRy() {
		shape.setPosition(10, 5);
		shape.setWidth(20);
		shape.setHeight(15);

		assertEquals(7.5, shape.getRy());
		shape.setHeight(10);
		shape.setWidth(5);

		assertEquals(2.5, shape.getRy());
	}


	@Override
	@Test
	public void testGetA() {
		shape.setPosition(10, 5);
		shape.setWidth(20);
		shape.setHeight(15);

		assertEquals(7.5, shape.getA());

		shape.setHeight(15);
		shape.setWidth(10);

		assertEquals(5., shape.getA());
	}



	@Override
	@Test
	public void testGetB() {
		shape.setPosition(10, 5);
		shape.setWidth(20);
		shape.setHeight(15);

		assertEquals(7.5, shape.getB());

		shape.setHeight(15);
		shape.setWidth(10);

		assertEquals(5., shape.getB());
	}
}
