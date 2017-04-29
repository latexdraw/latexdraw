package test.models.interfaces;

import net.sf.latexdraw.models.interfaces.shape.ITriangle;
import org.junit.Test;

public abstract class TestITriangle<T extends ITriangle> extends TestIPositionShape<T> {

	@Override
	@Test
	public void testGetBottomLeftPoint() {
		shape.setPosition(10, 20);
		shape.setWidth(10);
		shape.setHeight(10);

		assertEqualsDouble(10., shape.getBottomLeftPoint().getX());
		assertEqualsDouble(20., shape.getBottomLeftPoint().getY());
	}

	@Override
	@Test
	public void testGetBottomRightPoint() {
		shape.setPosition(10, 20);
		shape.setWidth(10);
		shape.setHeight(10);

		assertEqualsDouble(20., shape.getBottomRightPoint().getX());
		assertEqualsDouble(20., shape.getBottomRightPoint().getY());
	}

	@Override
	@Test
	public void testGetTopLeftPoint() {
		shape.setPosition(10, 20);
		shape.setWidth(10);
		shape.setHeight(10);

		assertEqualsDouble(10., shape.getTopLeftPoint().getX());
		assertEqualsDouble(10., shape.getTopLeftPoint().getY());
	}

	@Override
	@Test
	public void testGetTopRightPoint() {
		shape.setPosition(10, 20);
		shape.setWidth(10);
		shape.setHeight(10);

		assertEqualsDouble(20., shape.getTopRightPoint().getX());
		assertEqualsDouble(10., shape.getTopRightPoint().getY());
	}

	@Override
	@Test
	public void testMirrorHorizontal() {
		shape.setPosition(10, 20);
		shape.setWidth(30);
		shape.setHeight(40);
		shape.mirrorHorizontal(shape.getGravityCentre());

		assertEqualsDouble(10., shape.getPosition().getX());
		assertEqualsDouble(20., shape.getPosition().getY());
		assertEqualsDouble(30., shape.getWidth());
		assertEqualsDouble(40., shape.getHeight());
	}

	@Override
	@Test
	public void testMirrorVertical() {
		shape.setPosition(10, 20);
		shape.setWidth(30);
		shape.setHeight(40);
		shape.mirrorVertical(shape.getGravityCentre());

		assertEqualsDouble(10., shape.getPosition().getX());
		assertEqualsDouble(20., shape.getPosition().getY());
		assertEqualsDouble(30., shape.getWidth());
		assertEqualsDouble(40., shape.getHeight());
	}

	@Override
	@Test
	public void testTranslate() {
		shape.setPosition(0, 2);
		shape.setWidth(3);
		shape.setHeight(2);

		shape.translate(10, 5);
		assertEqualsDouble(10.0, shape.getPosition().getX());
		assertEqualsDouble(7.0, shape.getPosition().getY());
		assertEqualsDouble(3.0, shape.getWidth());
		assertEqualsDouble(2.0, shape.getHeight());
	}
}
