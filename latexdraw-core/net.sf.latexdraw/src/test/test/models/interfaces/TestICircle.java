package test.models.interfaces;

import net.sf.latexdraw.models.interfaces.shape.ICircle;
import org.junit.Test;

public abstract class TestICircle<T extends ICircle> extends TestISquaredShape<T> {
	@Override
	@Test
	public void testGetBottomLeftPoint() {
		shape.setPosition(-5, 0);
		shape.setWidth(10);

		assertEqualsDouble(-5., shape.getBottomLeftPoint().getX());
		assertEqualsDouble(0., shape.getBottomLeftPoint().getY());
	}

	@Override
	@Test
	public void testGetBottomRightPoint() {
		shape.setPosition(-15, 100);
		shape.setWidth(10);

		assertEqualsDouble(-5., shape.getBottomRightPoint().getX());
		assertEqualsDouble(100., shape.getBottomRightPoint().getY());
	}

	@Override
	@Test
	public void testGetTopLeftPoint() {
		shape.setPosition(20, 10);
		shape.setWidth(20);

		assertEqualsDouble(20., shape.getTopLeftPoint().getX());
		assertEqualsDouble(-10., shape.getTopLeftPoint().getY());
	}

	@Override
	@Test
	public void testGetTopRightPoint() {
		shape.setPosition(20, 10);
		shape.setWidth(10);

		assertEqualsDouble(30., shape.getTopRightPoint().getX());
		assertEqualsDouble(0., shape.getTopRightPoint().getY());
	}

	@Test
	public void testSetWidth() {
		shape.setPosition(-5, -5);
		shape.setWidth(3);
		assertEqualsDouble(3., shape.getWidth());
		assertEqualsDouble(3., shape.getHeight());
	}
}
