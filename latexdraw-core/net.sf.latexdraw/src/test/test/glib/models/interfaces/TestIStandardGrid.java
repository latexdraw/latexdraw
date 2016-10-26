package test.glib.models.interfaces;

import static org.junit.Assert.assertEquals;
import net.sf.latexdraw.models.interfaces.shape.IStandardGrid;

import org.junit.Test;

import test.HelperTest;

public abstract class TestIStandardGrid<T extends IStandardGrid> extends TestIPositionShape<T> {
	@Test
	public void testGetSetLabelsSize() {
		shape.setLabelsSize(5);
		assertEquals(5, shape.getLabelsSize());
		shape.setLabelsSize(30);
		assertEquals(30, shape.getLabelsSize());
		shape.setLabelsSize(0);
		assertEquals(0, shape.getLabelsSize());
		shape.setLabelsSize(-1);
		assertEquals(0, shape.getLabelsSize());
	}

	@Test
	public void testGetSetGridEndX() {
		shape.setGridStartX(-10);
		shape.setGridEndX(5);
		HelperTest.assertEqualsDouble(5., shape.getGridEndX());
		shape.setGridEndX(0);
		HelperTest.assertEqualsDouble(0., shape.getGridEndX());
		shape.setGridEndX(-5);
		HelperTest.assertEqualsDouble(-5., shape.getGridEndX());
		shape.setGridEndX(-15);
		HelperTest.assertEqualsDouble(-5., shape.getGridEndX());
		shape.setGridEndX(Double.NaN);
		HelperTest.assertEqualsDouble(-5., shape.getGridEndX());
		shape.setGridEndX(Double.NEGATIVE_INFINITY);
		HelperTest.assertEqualsDouble(-5., shape.getGridEndX());
		shape.setGridEndX(Double.POSITIVE_INFINITY);
		HelperTest.assertEqualsDouble(-5., shape.getGridEndX());
	}

	@Test
	public void testGetSetGridEndY() {
		shape.setGridStartY(-10);
		shape.setGridEndY(5);
		HelperTest.assertEqualsDouble(5., shape.getGridEndY());
		shape.setGridEndY(0);
		HelperTest.assertEqualsDouble(0., shape.getGridEndY());
		shape.setGridEndY(-5);
		HelperTest.assertEqualsDouble(-5., shape.getGridEndY());
		shape.setGridEndY(-15);
		HelperTest.assertEqualsDouble(-5., shape.getGridEndY());
		shape.setGridEndY(Double.NaN);
		HelperTest.assertEqualsDouble(-5., shape.getGridEndY());
		shape.setGridEndY(Double.NEGATIVE_INFINITY);
		HelperTest.assertEqualsDouble(-5., shape.getGridEndY());
		shape.setGridEndY(Double.POSITIVE_INFINITY);
		HelperTest.assertEqualsDouble(-5., shape.getGridEndY());
	}

	@Test
	public void testSetGridStart() {
		shape.setGridEnd(5, 9);
		shape.setGridStart(-5, -4);
		HelperTest.assertEqualsDouble(-5., shape.getGridStartX());
		HelperTest.assertEqualsDouble(-4., shape.getGridStartY());
		shape.setGridStart(-6, 10);
		HelperTest.assertEqualsDouble(-6., shape.getGridStartX());
		HelperTest.assertEqualsDouble(-4., shape.getGridStartY());
		shape.setGridStart(6, 8);
		HelperTest.assertEqualsDouble(-6., shape.getGridStartX());
		HelperTest.assertEqualsDouble(8., shape.getGridStartY());
		shape.setGridStart(6, Double.NaN);
		HelperTest.assertEqualsDouble(-6., shape.getGridStartX());
		HelperTest.assertEqualsDouble(8., shape.getGridStartY());
		shape.setGridStart(Double.NEGATIVE_INFINITY, 8);
		HelperTest.assertEqualsDouble(-6., shape.getGridStartX());
		HelperTest.assertEqualsDouble(8., shape.getGridStartY());
	}

	@Test
	public void testSetGridEnd() {
		shape.setGridStart(-5, -4);
		shape.setGridEnd(5, 9);
		HelperTest.assertEqualsDouble(5., shape.getGridEndX());
		HelperTest.assertEqualsDouble(9., shape.getGridEndY());
		shape.setGridEnd(6, -5);
		HelperTest.assertEqualsDouble(6., shape.getGridEndX());
		HelperTest.assertEqualsDouble(9., shape.getGridEndY());
		shape.setGridEnd(-6, 10);
		HelperTest.assertEqualsDouble(6., shape.getGridEndX());
		HelperTest.assertEqualsDouble(10., shape.getGridEndY());
		shape.setGridEnd(-6, Double.POSITIVE_INFINITY);
		HelperTest.assertEqualsDouble(6., shape.getGridEndX());
		HelperTest.assertEqualsDouble(10., shape.getGridEndY());
		shape.setGridEnd(Double.NaN, 10);
		HelperTest.assertEqualsDouble(6., shape.getGridEndX());
		HelperTest.assertEqualsDouble(10., shape.getGridEndY());
	}

	@Test
	public void testGetSetOrigin() {
		shape.setOrigin(10, 6);
		HelperTest.assertEqualsDouble(10., shape.getOriginX());
		HelperTest.assertEqualsDouble(6., shape.getOriginY());
		shape.setOrigin(0, 0);
		HelperTest.assertEqualsDouble(0., shape.getOriginX());
		HelperTest.assertEqualsDouble(0., shape.getOriginY());
		shape.setOrigin(0, -5);
		HelperTest.assertEqualsDouble(0., shape.getOriginX());
		HelperTest.assertEqualsDouble(-5., shape.getOriginY());
		shape.setOrigin(-8, 0);
		HelperTest.assertEqualsDouble(-8., shape.getOriginX());
		HelperTest.assertEqualsDouble(0., shape.getOriginY());
		shape.setOrigin(-9, Double.NaN);
		HelperTest.assertEqualsDouble(-9., shape.getOriginX());
		HelperTest.assertEqualsDouble(0., shape.getOriginY());
		shape.setOrigin(Double.POSITIVE_INFINITY, 1);
		HelperTest.assertEqualsDouble(-9., shape.getOriginX());
		HelperTest.assertEqualsDouble(1., shape.getOriginY());
	}

	@Test
	public void testGetSetGridStartY() {
		shape.setGridEndY(10);
		shape.setGridStartY(5);
		HelperTest.assertEqualsDouble(5., shape.getGridStartY());
		shape.setGridStartY(0);
		HelperTest.assertEqualsDouble(0., shape.getGridStartY());
		shape.setGridStartY(-5);
		HelperTest.assertEqualsDouble(-5., shape.getGridStartY());
		shape.setGridStartY(15);
		HelperTest.assertEqualsDouble(-5., shape.getGridStartY());
		shape.setGridStartY(Double.NaN);
		HelperTest.assertEqualsDouble(-5., shape.getGridStartY());
		shape.setGridStartY(Double.POSITIVE_INFINITY);
		HelperTest.assertEqualsDouble(-5., shape.getGridStartY());
		shape.setGridStartY(Double.NEGATIVE_INFINITY);
		HelperTest.assertEqualsDouble(-5., shape.getGridStartY());
	}

	@Test
	public void testGetSetGridStartX() {
		shape.setGridEndX(10);
		shape.setGridStartX(5);
		HelperTest.assertEqualsDouble(5., shape.getGridStartX());
		shape.setGridStartX(0);
		HelperTest.assertEqualsDouble(0., shape.getGridStartX());
		shape.setGridStartX(-5);
		HelperTest.assertEqualsDouble(-5., shape.getGridStartX());
		shape.setGridStartX(15);
		HelperTest.assertEqualsDouble(-5., shape.getGridStartX());
		shape.setGridStartX(Double.NaN);
		HelperTest.assertEqualsDouble(-5., shape.getGridStartX());
		shape.setGridStartX(Double.POSITIVE_INFINITY);
		HelperTest.assertEqualsDouble(-5., shape.getGridStartX());
		shape.setGridStartX(Double.NEGATIVE_INFINITY);
		HelperTest.assertEqualsDouble(-5., shape.getGridStartX());
	}

	@Test
	public void testGetSetOriginX() {
		shape.setOriginX(100);
		HelperTest.assertEqualsDouble(100., shape.getOriginX());
		shape.setOriginX(-100);
		HelperTest.assertEqualsDouble(-100., shape.getOriginX());
		shape.setOriginX(0);
		HelperTest.assertEqualsDouble(0., shape.getOriginX());
		shape.setOriginX(Double.NaN);
		HelperTest.assertEqualsDouble(0., shape.getOriginX());
		shape.setOriginX(Double.POSITIVE_INFINITY);
		HelperTest.assertEqualsDouble(0., shape.getOriginX());
		shape.setOriginX(Double.NEGATIVE_INFINITY);
		HelperTest.assertEqualsDouble(0., shape.getOriginX());
	}

	@Test
	public void testGetSetOriginY() {
		shape.setOriginY(100);
		HelperTest.assertEqualsDouble(100., shape.getOriginY());
		shape.setOriginY(-100);
		HelperTest.assertEqualsDouble(-100., shape.getOriginY());
		shape.setOriginY(0);
		HelperTest.assertEqualsDouble(0., shape.getOriginY());
		shape.setOriginY(Double.NaN);
		HelperTest.assertEqualsDouble(0., shape.getOriginY());
		shape.setOriginY(Double.POSITIVE_INFINITY);
		HelperTest.assertEqualsDouble(0., shape.getOriginY());
		shape.setOriginY(Double.NEGATIVE_INFINITY);
		HelperTest.assertEqualsDouble(0., shape.getOriginY());
	}

	@Override
	@Test
	public void testDuplicate() {
		super.testDuplicate();

		shape.setOrigin(20, 30);
		shape.setGridStart(-100, -40);
		shape.setGridEnd(200, 300);
		// shape.setLabelsSize(IText.TestSize.HUGE1.getSize());

		IStandardGrid g2 = (IStandardGrid)shape.duplicate();

		HelperTest.assertEqualsDouble(20., g2.getOriginX());
		HelperTest.assertEqualsDouble(30., g2.getOriginY());
		HelperTest.assertEqualsDouble(-100., g2.getGridStartX());
		HelperTest.assertEqualsDouble(-40., g2.getGridStartY());
		HelperTest.assertEqualsDouble(200., g2.getGridEndX());
		HelperTest.assertEqualsDouble(300., g2.getGridEndY());
		// assertEquals(IText.TestSize.HUGE1.getSize(), shape2.getLabelsSize());//FIXME
	}

	@Override
	@Test
	public void testCopy() {
		super.testCopy();

		shape2.setOrigin(20, 30);
		shape2.setGridEnd(100, -40);
		shape2.setGridStart(200, 300);
		shape2.setLabelsSize(20);

		shape.copy(shape2);

		HelperTest.assertEqualsDouble(shape2.getOriginX(), shape.getOriginX());
		HelperTest.assertEqualsDouble(shape2.getOriginY(), shape.getOriginY());
		HelperTest.assertEqualsDouble(shape2.getGridEndX(), shape.getGridEndX());
		HelperTest.assertEqualsDouble(shape2.getGridEndY(), shape.getGridEndY());
		HelperTest.assertEqualsDouble(shape2.getGridStartX(), shape.getGridStartX());
		HelperTest.assertEqualsDouble(shape2.getGridStartY(), shape.getGridStartY());
		assertEquals(shape2.getLabelsSize(), shape.getLabelsSize());
	}
}
