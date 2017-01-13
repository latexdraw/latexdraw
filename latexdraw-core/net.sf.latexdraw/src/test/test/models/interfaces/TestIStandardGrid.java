package test.models.interfaces;

import net.sf.latexdraw.models.interfaces.shape.IStandardGrid;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
		assertEqualsDouble(5., shape.getGridEndX());
		shape.setGridEndX(0);
		assertEqualsDouble(0., shape.getGridEndX());
		shape.setGridEndX(-5);
		assertEqualsDouble(-5., shape.getGridEndX());
		shape.setGridEndX(-15);
		assertEqualsDouble(-5., shape.getGridEndX());
		shape.setGridEndX(Double.NaN);
		assertEqualsDouble(-5., shape.getGridEndX());
		shape.setGridEndX(Double.NEGATIVE_INFINITY);
		assertEqualsDouble(-5., shape.getGridEndX());
		shape.setGridEndX(Double.POSITIVE_INFINITY);
		assertEqualsDouble(-5., shape.getGridEndX());
	}

	@Test
	public void testGetSetGridEndY() {
		shape.setGridStartY(-10);
		shape.setGridEndY(5);
		assertEqualsDouble(5., shape.getGridEndY());
		shape.setGridEndY(0);
		assertEqualsDouble(0., shape.getGridEndY());
		shape.setGridEndY(-5);
		assertEqualsDouble(-5., shape.getGridEndY());
		shape.setGridEndY(-15);
		assertEqualsDouble(-5., shape.getGridEndY());
		shape.setGridEndY(Double.NaN);
		assertEqualsDouble(-5., shape.getGridEndY());
		shape.setGridEndY(Double.NEGATIVE_INFINITY);
		assertEqualsDouble(-5., shape.getGridEndY());
		shape.setGridEndY(Double.POSITIVE_INFINITY);
		assertEqualsDouble(-5., shape.getGridEndY());
	}

	@Test
	public void testSetGridStart() {
		shape.setGridEnd(5, 9);
		shape.setGridStart(-5, -4);
		assertEqualsDouble(-5., shape.getGridStartX());
		assertEqualsDouble(-4., shape.getGridStartY());
		shape.setGridStart(-6, 10);
		assertEqualsDouble(-6., shape.getGridStartX());
		assertEqualsDouble(-4., shape.getGridStartY());
		shape.setGridStart(6, 8);
		assertEqualsDouble(-6., shape.getGridStartX());
		assertEqualsDouble(8., shape.getGridStartY());
		shape.setGridStart(6, Double.NaN);
		assertEqualsDouble(-6., shape.getGridStartX());
		assertEqualsDouble(8., shape.getGridStartY());
		shape.setGridStart(Double.NEGATIVE_INFINITY, 8);
		assertEqualsDouble(-6., shape.getGridStartX());
		assertEqualsDouble(8., shape.getGridStartY());
	}

	@Test
	public void testSetGridEnd() {
		shape.setGridStart(-5, -4);
		shape.setGridEnd(5, 9);
		assertEqualsDouble(5., shape.getGridEndX());
		assertEqualsDouble(9., shape.getGridEndY());
		shape.setGridEnd(6, -5);
		assertEqualsDouble(6., shape.getGridEndX());
		assertEqualsDouble(9., shape.getGridEndY());
		shape.setGridEnd(-6, 10);
		assertEqualsDouble(6., shape.getGridEndX());
		assertEqualsDouble(10., shape.getGridEndY());
		shape.setGridEnd(-6, Double.POSITIVE_INFINITY);
		assertEqualsDouble(6., shape.getGridEndX());
		assertEqualsDouble(10., shape.getGridEndY());
		shape.setGridEnd(Double.NaN, 10);
		assertEqualsDouble(6., shape.getGridEndX());
		assertEqualsDouble(10., shape.getGridEndY());
	}

	@Test
	public void testGetSetOrigin() {
		shape.setOrigin(10, 6);
		assertEqualsDouble(10., shape.getOriginX());
		assertEqualsDouble(6., shape.getOriginY());
		shape.setOrigin(0, 0);
		assertEqualsDouble(0., shape.getOriginX());
		assertEqualsDouble(0., shape.getOriginY());
		shape.setOrigin(0, -5);
		assertEqualsDouble(0., shape.getOriginX());
		assertEqualsDouble(-5., shape.getOriginY());
		shape.setOrigin(-8, 0);
		assertEqualsDouble(-8., shape.getOriginX());
		assertEqualsDouble(0., shape.getOriginY());
		shape.setOrigin(-9, Double.NaN);
		assertEqualsDouble(-9., shape.getOriginX());
		assertEqualsDouble(0., shape.getOriginY());
		shape.setOrigin(Double.POSITIVE_INFINITY, 1);
		assertEqualsDouble(-9., shape.getOriginX());
		assertEqualsDouble(1., shape.getOriginY());
	}

	@Test
	public void testGetSetGridStartY() {
		shape.setGridEndY(10);
		shape.setGridStartY(5);
		assertEqualsDouble(5., shape.getGridStartY());
		shape.setGridStartY(0);
		assertEqualsDouble(0., shape.getGridStartY());
		shape.setGridStartY(-5);
		assertEqualsDouble(-5., shape.getGridStartY());
		shape.setGridStartY(15);
		assertEqualsDouble(-5., shape.getGridStartY());
		shape.setGridStartY(Double.NaN);
		assertEqualsDouble(-5., shape.getGridStartY());
		shape.setGridStartY(Double.POSITIVE_INFINITY);
		assertEqualsDouble(-5., shape.getGridStartY());
		shape.setGridStartY(Double.NEGATIVE_INFINITY);
		assertEqualsDouble(-5., shape.getGridStartY());
	}

	@Test
	public void testGetSetGridStartX() {
		shape.setGridEndX(10);
		shape.setGridStartX(5);
		assertEqualsDouble(5., shape.getGridStartX());
		shape.setGridStartX(0);
		assertEqualsDouble(0., shape.getGridStartX());
		shape.setGridStartX(-5);
		assertEqualsDouble(-5., shape.getGridStartX());
		shape.setGridStartX(15);
		assertEqualsDouble(-5., shape.getGridStartX());
		shape.setGridStartX(Double.NaN);
		assertEqualsDouble(-5., shape.getGridStartX());
		shape.setGridStartX(Double.POSITIVE_INFINITY);
		assertEqualsDouble(-5., shape.getGridStartX());
		shape.setGridStartX(Double.NEGATIVE_INFINITY);
		assertEqualsDouble(-5., shape.getGridStartX());
	}

	@Test
	public void testGetSetOriginX() {
		shape.setOriginX(100);
		assertEqualsDouble(100., shape.getOriginX());
		shape.setOriginX(-100);
		assertEqualsDouble(-100., shape.getOriginX());
		shape.setOriginX(0);
		assertEqualsDouble(0., shape.getOriginX());
		shape.setOriginX(Double.NaN);
		assertEqualsDouble(0., shape.getOriginX());
		shape.setOriginX(Double.POSITIVE_INFINITY);
		assertEqualsDouble(0., shape.getOriginX());
		shape.setOriginX(Double.NEGATIVE_INFINITY);
		assertEqualsDouble(0., shape.getOriginX());
	}

	@Test
	public void testGetSetOriginY() {
		shape.setOriginY(100);
		assertEqualsDouble(100., shape.getOriginY());
		shape.setOriginY(-100);
		assertEqualsDouble(-100., shape.getOriginY());
		shape.setOriginY(0);
		assertEqualsDouble(0., shape.getOriginY());
		shape.setOriginY(Double.NaN);
		assertEqualsDouble(0., shape.getOriginY());
		shape.setOriginY(Double.POSITIVE_INFINITY);
		assertEqualsDouble(0., shape.getOriginY());
		shape.setOriginY(Double.NEGATIVE_INFINITY);
		assertEqualsDouble(0., shape.getOriginY());
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

		assertEqualsDouble(20., g2.getOriginX());
		assertEqualsDouble(30., g2.getOriginY());
		assertEqualsDouble(-100., g2.getGridStartX());
		assertEqualsDouble(-40., g2.getGridStartY());
		assertEqualsDouble(200., g2.getGridEndX());
		assertEqualsDouble(300., g2.getGridEndY());
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

		assertEqualsDouble(shape2.getOriginX(), shape.getOriginX());
		assertEqualsDouble(shape2.getOriginY(), shape.getOriginY());
		assertEqualsDouble(shape2.getGridEndX(), shape.getGridEndX());
		assertEqualsDouble(shape2.getGridEndY(), shape.getGridEndY());
		assertEqualsDouble(shape2.getGridStartX(), shape.getGridStartX());
		assertEqualsDouble(shape2.getGridStartY(), shape.getGridStartY());
		assertEquals(shape2.getLabelsSize(), shape.getLabelsSize());
	}
}
