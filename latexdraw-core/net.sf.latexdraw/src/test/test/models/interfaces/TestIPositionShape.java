package test.models.interfaces;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.IPositionShape;

import org.junit.Test;

import test.HelperTest;

public abstract class TestIPositionShape<T extends IPositionShape> extends TestIShape<T> {
	@Override
	@Test
	public void testTranslate() {
		shape.setPosition(0, 0);
		shape.translate(100, 50);
		HelperTest.assertEqualsDouble(100., shape.getPosition().getX());
		HelperTest.assertEqualsDouble(50., shape.getPosition().getY());
	}

	@Test
	public void testGetSetX() {
		shape.setX(10.);
		HelperTest.assertEqualsDouble(10., shape.getX());
		shape.setX(-20.);
		HelperTest.assertEqualsDouble(-20., shape.getX());
		shape.setX(Double.NaN);
		HelperTest.assertEqualsDouble(-20., shape.getX());
		shape.setX(Double.POSITIVE_INFINITY);
		HelperTest.assertEqualsDouble(-20., shape.getX());
		shape.setX(Double.NEGATIVE_INFINITY);
		HelperTest.assertEqualsDouble(-20., shape.getX());
	}

	@Test
	public void testGetSetY() {
		shape.setY(10.);
		HelperTest.assertEqualsDouble(10., shape.getY());
		shape.setY(-20.);
		HelperTest.assertEqualsDouble(-20., shape.getY());
		shape.setY(Double.NaN);
		HelperTest.assertEqualsDouble(-20., shape.getY());
		shape.setY(Double.POSITIVE_INFINITY);
		HelperTest.assertEqualsDouble(-20., shape.getY());
		shape.setY(Double.NEGATIVE_INFINITY);
		HelperTest.assertEqualsDouble(-20., shape.getY());
	}

	@Test
	public void testSetXThenSetY() {
		shape.setX(40.);
		HelperTest.assertEqualsDouble(40., shape.getX());
		shape.setX(-30.);
		HelperTest.assertEqualsDouble(-30., shape.getX());

		shape.setY(10.);
		HelperTest.assertEqualsDouble(10., shape.getY());
		HelperTest.assertEqualsDouble(-30., shape.getX());
		shape.setY(-20.);
		HelperTest.assertEqualsDouble(-20., shape.getY());
		HelperTest.assertEqualsDouble(-30., shape.getX());
	}

	@Test
	public void testSetYThenSetX() {
		shape.setY(10.);
		HelperTest.assertEqualsDouble(10., shape.getY());
		shape.setY(-20.);
		HelperTest.assertEqualsDouble(-20., shape.getY());

		shape.setX(40.);
		HelperTest.assertEqualsDouble(40., shape.getX());
		HelperTest.assertEqualsDouble(-20., shape.getY());
		shape.setX(-30.);
		HelperTest.assertEqualsDouble(-30., shape.getX());
		HelperTest.assertEqualsDouble(-20., shape.getY());
	}

	@Test
	public void testGetSetPosition() {
		IPoint pt = ShapeFactory.INST.createPoint(15, 25);

		shape.setPosition(pt);
		HelperTest.assertEqualsDouble(pt.getX(), shape.getPosition().getX());
		HelperTest.assertEqualsDouble(pt.getY(), shape.getPosition().getY());
		HelperTest.assertEqualsDouble(15., shape.getX());
		HelperTest.assertEqualsDouble(25., shape.getY());
		shape.setPosition(null);
		HelperTest.assertEqualsDouble(pt.getX(), shape.getPosition().getX());
		HelperTest.assertEqualsDouble(pt.getY(), shape.getPosition().getY());
		shape.setPosition(ShapeFactory.INST.createPoint(Double.NaN, 0));
		HelperTest.assertEqualsDouble(pt.getX(), shape.getPosition().getX());
		HelperTest.assertEqualsDouble(pt.getY(), shape.getPosition().getY());
		shape.setPosition(ShapeFactory.INST.createPoint(Double.NEGATIVE_INFINITY, 0));
		HelperTest.assertEqualsDouble(pt.getX(), shape.getPosition().getX());
		HelperTest.assertEqualsDouble(pt.getY(), shape.getPosition().getY());
		shape.setPosition(ShapeFactory.INST.createPoint(Double.POSITIVE_INFINITY, 0));
		HelperTest.assertEqualsDouble(pt.getX(), shape.getPosition().getX());
		HelperTest.assertEqualsDouble(pt.getY(), shape.getPosition().getY());
		shape.setPosition(ShapeFactory.INST.createPoint(0, Double.NaN));
		HelperTest.assertEqualsDouble(pt.getX(), shape.getPosition().getX());
		HelperTest.assertEqualsDouble(pt.getY(), shape.getPosition().getY());
		shape.setPosition(ShapeFactory.INST.createPoint(0, Double.NEGATIVE_INFINITY));
		HelperTest.assertEqualsDouble(pt.getX(), shape.getPosition().getX());
		HelperTest.assertEqualsDouble(pt.getY(), shape.getPosition().getY());
		shape.setPosition(ShapeFactory.INST.createPoint(0, Double.POSITIVE_INFINITY));
		HelperTest.assertEqualsDouble(pt.getX(), shape.getPosition().getX());
		HelperTest.assertEqualsDouble(pt.getY(), shape.getPosition().getY());

		shape.setPosition(15, 25);
		HelperTest.assertEqualsDouble(pt.getX(), shape.getPosition().getX());
		HelperTest.assertEqualsDouble(pt.getY(), shape.getPosition().getY());
		HelperTest.assertEqualsDouble(15., shape.getX());
		HelperTest.assertEqualsDouble(25., shape.getY());
		shape.setPosition(Double.NaN, 0);
		HelperTest.assertEqualsDouble(pt.getX(), shape.getPosition().getX());
		HelperTest.assertEqualsDouble(pt.getY(), shape.getPosition().getY());
		shape.setPosition(Double.NEGATIVE_INFINITY, 0);
		HelperTest.assertEqualsDouble(pt.getX(), shape.getPosition().getX());
		HelperTest.assertEqualsDouble(pt.getY(), shape.getPosition().getY());
		shape.setPosition(Double.POSITIVE_INFINITY, 0);
		HelperTest.assertEqualsDouble(pt.getX(), shape.getPosition().getX());
		HelperTest.assertEqualsDouble(pt.getY(), shape.getPosition().getY());
		shape.setPosition(0, Double.NaN);
		HelperTest.assertEqualsDouble(pt.getX(), shape.getPosition().getX());
		HelperTest.assertEqualsDouble(pt.getY(), shape.getPosition().getY());
		shape.setPosition(0, Double.NEGATIVE_INFINITY);
		HelperTest.assertEqualsDouble(pt.getX(), shape.getPosition().getX());
		HelperTest.assertEqualsDouble(pt.getY(), shape.getPosition().getY());
		shape.setPosition(0, Double.POSITIVE_INFINITY);
		HelperTest.assertEqualsDouble(pt.getX(), shape.getPosition().getX());
		HelperTest.assertEqualsDouble(pt.getY(), shape.getPosition().getY());
	}
}
