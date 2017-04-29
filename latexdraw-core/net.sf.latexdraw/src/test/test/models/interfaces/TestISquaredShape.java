package test.models.interfaces;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IPoint;
import net.sf.latexdraw.models.interfaces.shape.ISquaredShape;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public abstract class TestISquaredShape<T extends ISquaredShape> extends TestIPositionShape<T> {
	@Override
	@Test
	public void testCopy() {
		super.testCopy();

		shape2.setPosition(-120., 200.);
		shape2.setWidth(385.);
		shape.copy(shape2);

		for(int i = 0; i < shape.getPoints().size(); i++)
			assertEquals(shape.getPtAt(i), shape2.getPtAt(i));
	}

	@Test
	public void testHas4Points() {
		assertEquals(shape.getNbPoints(), 4);
	}

	@Test
	public void testGetSetWidth() {
		shape.setWidth(20);
		assertEqualsDouble(20., shape.getWidth());
		assertEqualsDouble(20., shape.getHeight());
		shape.setWidth(50);
		assertEqualsDouble(50., shape.getWidth());
		assertEqualsDouble(50., shape.getHeight());
		shape.setWidth(-10);
		assertEqualsDouble(50., shape.getWidth());
		assertEqualsDouble(50., shape.getHeight());
		shape.setWidth(0);
		assertEqualsDouble(50., shape.getWidth());
		assertEqualsDouble(50., shape.getHeight());
		shape.setWidth(Double.NaN);
		assertEqualsDouble(50., shape.getWidth());
		assertEqualsDouble(50., shape.getHeight());
		shape.setWidth(Double.NEGATIVE_INFINITY);
		assertEqualsDouble(50., shape.getWidth());
		assertEqualsDouble(50., shape.getHeight());
		shape.setWidth(Double.POSITIVE_INFINITY);
		assertEqualsDouble(50., shape.getWidth());
		assertEqualsDouble(50., shape.getHeight());
	}

	@Override
	@Test
	public void testGetNbPoints() {
		assertEquals(4, shape.getNbPoints());
	}

	@Override
	@Test
	public void testGetPtAt() {
		assertNotNull(shape.getPtAt(0));
		assertNotNull(shape.getPtAt(1));
		assertNotNull(shape.getPtAt(2));
		assertNotNull(shape.getPtAt(3));
		assertNotNull(shape.getPtAt(-1));
		assertNull(shape.getPtAt(4));
		assertNull(shape.getPtAt(-2));
	}

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
		shape.setWidth(10);

		assertEqualsDouble(20., shape.getTopLeftPoint().getX());
		assertEqualsDouble(0., shape.getTopLeftPoint().getY());
	}

	@Override
	@Test
	public void testGetTopRightPoint() {
		shape.setPosition(20, 10);
		shape.setWidth(10);

		assertEqualsDouble(30., shape.getTopRightPoint().getX());
		assertEqualsDouble(0., shape.getTopRightPoint().getY());
	}

	@Override
	@Test
	public void testMirrorHorizontal() {
		IPoint pt2 = ShapeFactory.INST.createPoint(3, 1);
		IPoint pt4 = ShapeFactory.INST.createPoint(1, 3);

		shape.setPosition(pt4);
		shape.setWidth(pt2.getX() - pt4.getX());

		shape.mirrorHorizontal(shape.getGravityCentre());
		assertEqualsDouble(3., shape.getPtAt(0).getX());
		assertEqualsDouble(1., shape.getPtAt(1).getX());
		assertEqualsDouble(1., shape.getPtAt(2).getX());
		assertEqualsDouble(3., shape.getPtAt(-1).getX());
		assertEqualsDouble(1., shape.getPtAt(0).getY());
		assertEqualsDouble(1., shape.getPtAt(1).getY());
		assertEqualsDouble(3., shape.getPtAt(2).getY());
		assertEqualsDouble(3., shape.getPtAt(-1).getY());
	}

	@Override
	@Test
	public void testMirrorVertical() {
		IPoint pt2 = ShapeFactory.INST.createPoint(3, 1);
		IPoint pt4 = ShapeFactory.INST.createPoint(1, 3);

		shape.setPosition(pt4);
		shape.setWidth(pt2.getX() - pt4.getX());

		shape.mirrorVertical(shape.getGravityCentre());
		assertEqualsDouble(1., shape.getPtAt(0).getX());
		assertEqualsDouble(3., shape.getPtAt(1).getX());
		assertEqualsDouble(3., shape.getPtAt(2).getX());
		assertEqualsDouble(1., shape.getPtAt(-1).getX());
		assertEqualsDouble(3., shape.getPtAt(0).getY());
		assertEqualsDouble(3., shape.getPtAt(1).getY());
		assertEqualsDouble(1., shape.getPtAt(2).getY());
		assertEqualsDouble(1., shape.getPtAt(-1).getY());
	}

	@Override
	@Test
	public void testTranslate() {
		IPoint pt2 = ShapeFactory.INST.createPoint(3, 1);
		IPoint pt4 = ShapeFactory.INST.createPoint(1, 3);

		shape.setPosition(pt4);
		shape.setWidth(pt2.getX() - pt4.getX());

		shape.translate(10, 0);
		assertEqualsDouble(11., shape.getPtAt(0).getX());
		assertEqualsDouble(13., shape.getPtAt(1).getX());
		assertEqualsDouble(13., shape.getPtAt(2).getX());
		assertEqualsDouble(11., shape.getPtAt(-1).getX());
		assertEqualsDouble(1., shape.getPtAt(0).getY());
		assertEqualsDouble(1., shape.getPtAt(1).getY());
		assertEqualsDouble(3., shape.getPtAt(2).getY());
		assertEqualsDouble(3., shape.getPtAt(-1).getY());

		shape.translate(5, 5);
		assertEqualsDouble(16., shape.getPtAt(0).getX());
		assertEqualsDouble(18., shape.getPtAt(1).getX());
		assertEqualsDouble(18., shape.getPtAt(2).getX());
		assertEqualsDouble(16., shape.getPtAt(-1).getX());
		assertEqualsDouble(6., shape.getPtAt(0).getY());
		assertEqualsDouble(6., shape.getPtAt(1).getY());
		assertEqualsDouble(8., shape.getPtAt(2).getY());
		assertEqualsDouble(8., shape.getPtAt(-1).getY());

		shape.translate(-5, -5);
		assertEqualsDouble(11., shape.getPtAt(0).getX());
		assertEqualsDouble(13., shape.getPtAt(1).getX());
		assertEqualsDouble(13., shape.getPtAt(2).getX());
		assertEqualsDouble(11., shape.getPtAt(-1).getX());
		assertEqualsDouble(1., shape.getPtAt(0).getY());
		assertEqualsDouble(1., shape.getPtAt(1).getY());
		assertEqualsDouble(3., shape.getPtAt(2).getY());
		assertEqualsDouble(3., shape.getPtAt(-1).getY());

		shape.translate(-10, 0);
		assertEqualsDouble(1., shape.getPtAt(0).getX());
		assertEqualsDouble(3., shape.getPtAt(1).getX());
		assertEqualsDouble(3., shape.getPtAt(2).getX());
		assertEqualsDouble(1., shape.getPtAt(-1).getX());
		assertEqualsDouble(1., shape.getPtAt(0).getY());
		assertEqualsDouble(1., shape.getPtAt(1).getY());
		assertEqualsDouble(3., shape.getPtAt(2).getY());
		assertEqualsDouble(3., shape.getPtAt(-1).getY());

		shape.translate(Double.NaN, -5);
		assertEqualsDouble(1., shape.getPtAt(0).getX());
		assertEqualsDouble(3., shape.getPtAt(1).getX());
		assertEqualsDouble(3., shape.getPtAt(2).getX());
		assertEqualsDouble(1., shape.getPtAt(-1).getX());
		assertEqualsDouble(1., shape.getPtAt(0).getY());
		assertEqualsDouble(1., shape.getPtAt(1).getY());
		assertEqualsDouble(3., shape.getPtAt(2).getY());
		assertEqualsDouble(3., shape.getPtAt(-1).getY());

		shape.translate(1, Double.NaN);
		assertEqualsDouble(1., shape.getPtAt(0).getX());
		assertEqualsDouble(3., shape.getPtAt(1).getX());
		assertEqualsDouble(3., shape.getPtAt(2).getX());
		assertEqualsDouble(1., shape.getPtAt(-1).getX());
		assertEqualsDouble(1., shape.getPtAt(0).getY());
		assertEqualsDouble(1., shape.getPtAt(1).getY());
		assertEqualsDouble(3., shape.getPtAt(2).getY());
		assertEqualsDouble(3., shape.getPtAt(-1).getY());

		shape.translate(Double.NEGATIVE_INFINITY, -5);
		assertEqualsDouble(1., shape.getPtAt(0).getX());
		assertEqualsDouble(3., shape.getPtAt(1).getX());
		assertEqualsDouble(3., shape.getPtAt(2).getX());
		assertEqualsDouble(1., shape.getPtAt(-1).getX());
		assertEqualsDouble(1., shape.getPtAt(0).getY());
		assertEqualsDouble(1., shape.getPtAt(1).getY());
		assertEqualsDouble(3., shape.getPtAt(2).getY());
		assertEqualsDouble(3., shape.getPtAt(-1).getY());

		shape.translate(1, Double.NEGATIVE_INFINITY);
		assertEqualsDouble(1., shape.getPtAt(0).getX());
		assertEqualsDouble(3., shape.getPtAt(1).getX());
		assertEqualsDouble(3., shape.getPtAt(2).getX());
		assertEqualsDouble(1., shape.getPtAt(-1).getX());
		assertEqualsDouble(1., shape.getPtAt(0).getY());
		assertEqualsDouble(1., shape.getPtAt(1).getY());
		assertEqualsDouble(3., shape.getPtAt(2).getY());
		assertEqualsDouble(3., shape.getPtAt(-1).getY());

		shape.translate(Double.POSITIVE_INFINITY, -5);
		assertEqualsDouble(1., shape.getPtAt(0).getX());
		assertEqualsDouble(3., shape.getPtAt(1).getX());
		assertEqualsDouble(3., shape.getPtAt(2).getX());
		assertEqualsDouble(1., shape.getPtAt(-1).getX());
		assertEqualsDouble(1., shape.getPtAt(0).getY());
		assertEqualsDouble(1., shape.getPtAt(1).getY());
		assertEqualsDouble(3., shape.getPtAt(2).getY());
		assertEqualsDouble(3., shape.getPtAt(-1).getY());

		shape.translate(1, Double.POSITIVE_INFINITY);
		assertEqualsDouble(1., shape.getPtAt(0).getX());
		assertEqualsDouble(3., shape.getPtAt(1).getX());
		assertEqualsDouble(3., shape.getPtAt(2).getX());
		assertEqualsDouble(1., shape.getPtAt(-1).getX());
		assertEqualsDouble(1., shape.getPtAt(0).getY());
		assertEqualsDouble(1., shape.getPtAt(1).getY());
		assertEqualsDouble(3., shape.getPtAt(2).getY());
		assertEqualsDouble(3., shape.getPtAt(-1).getY());

		shape.translate(0, 0);
		assertEqualsDouble(1., shape.getPtAt(0).getX());
		assertEqualsDouble(3., shape.getPtAt(1).getX());
		assertEqualsDouble(3., shape.getPtAt(2).getX());
		assertEqualsDouble(1., shape.getPtAt(-1).getX());
		assertEqualsDouble(1., shape.getPtAt(0).getY());
		assertEqualsDouble(1., shape.getPtAt(1).getY());
		assertEqualsDouble(3., shape.getPtAt(2).getY());
		assertEqualsDouble(3., shape.getPtAt(-1).getY());
	}
}
