package test.glib.models.interfaces;


import net.sf.latexdraw.glib.models.interfaces.IStandardGrid;

import org.junit.Test;

public abstract class TestIStandardGrid<T extends IStandardGrid> extends TestIPositionShape<T> {
	@Test
	public void testGetSetLabelsSize() {
		shape.setLabelsSize(5);
		assertEquals(5, shape.getLabelsSize());
		shape.setLabelsSize(30);
		assertEquals(30, shape.getLabelsSize());
		shape.setLabelsSize(0);
		assertEquals(30, shape.getLabelsSize());
		shape.setLabelsSize(-1);
		assertEquals(30, shape.getLabelsSize());
	}


	@Test
	public void testGetSetGridEndX() {
		shape.setGridStartX(-10);
		shape.setGridEndX(5);
		assertEquals(5., shape.getGridEndX());
		shape.setGridEndX(0);
		assertEquals(0., shape.getGridEndX());
		shape.setGridEndX(-5);
		assertEquals(-5., shape.getGridEndX());
		shape.setGridEndX(-15);
		assertEquals(-5., shape.getGridEndX());
		shape.setGridEndX(Double.NaN);
		assertEquals(-5., shape.getGridEndX());
		shape.setGridEndX(Double.NEGATIVE_INFINITY);
		assertEquals(-5., shape.getGridEndX());
		shape.setGridEndX(Double.POSITIVE_INFINITY);
		assertEquals(-5., shape.getGridEndX());
	}


	@Test
	public void testGetSetGridEndY() {
		shape.setGridStartY(-10);
		shape.setGridEndY(5);
		assertEquals(5., shape.getGridEndY());
		shape.setGridEndY(0);
		assertEquals(0., shape.getGridEndY());
		shape.setGridEndY(-5);
		assertEquals(-5., shape.getGridEndY());
		shape.setGridEndY(-15);
		assertEquals(-5., shape.getGridEndY());
		shape.setGridEndY(Double.NaN);
		assertEquals(-5., shape.getGridEndY());
		shape.setGridEndY(Double.NEGATIVE_INFINITY);
		assertEquals(-5., shape.getGridEndY());
		shape.setGridEndY(Double.POSITIVE_INFINITY);
		assertEquals(-5., shape.getGridEndY());
	}


	@Test
	public void testIsSetXLabelSouth() {
		shape.setXLabelSouth(true);
		assertTrue(shape.isXLabelSouth());
		shape.setXLabelSouth(false);
		assertFalse(shape.isXLabelSouth());
	}


	@Test
	public void testIsSetYLabelWest() {
		shape.setYLabelWest(true);
		assertTrue(shape.isYLabelWest());
		shape.setYLabelWest(false);
		assertFalse(shape.isYLabelWest());
	}


	@Test
	public void testSetGridStart() {
		shape.setGridEnd(5, 9);
		shape.setGridStart(-5, -4);
		assertEquals(-5., shape.getGridStartX());
		assertEquals(-4., shape.getGridStartY());
		shape.setGridStart(-6, 10);
		assertEquals(-6., shape.getGridStartX());
		assertEquals(-4., shape.getGridStartY());
		shape.setGridStart(6, 8);
		assertEquals(-6., shape.getGridStartX());
		assertEquals(8., shape.getGridStartY());
		shape.setGridStart(6, Double.NaN);
		assertEquals(-6., shape.getGridStartX());
		assertEquals(8., shape.getGridStartY());
		shape.setGridStart(Double.NEGATIVE_INFINITY, 8);
		assertEquals(-6., shape.getGridStartX());
		assertEquals(8., shape.getGridStartY());
	}


	@Test
	public void testSetGridEnd() {
		shape.setGridStart(-5, -4);
		shape.setGridEnd(5, 9);
		assertEquals(5., shape.getGridEndX());
		assertEquals(9., shape.getGridEndY());
		shape.setGridEnd(6, -5);
		assertEquals(6., shape.getGridEndX());
		assertEquals(9., shape.getGridEndY());
		shape.setGridEnd(-6, 10);
		assertEquals(6., shape.getGridEndX());
		assertEquals(10., shape.getGridEndY());
		shape.setGridEnd(-6, Double.POSITIVE_INFINITY);
		assertEquals(6., shape.getGridEndX());
		assertEquals(10., shape.getGridEndY());
		shape.setGridEnd(Double.NaN, 10);
		assertEquals(6., shape.getGridEndX());
		assertEquals(10., shape.getGridEndY());
	}


	@Test
	public void testGetSetOrigin() {
		shape.setOrigin(10, 6);
		assertEquals(10., shape.getOriginX());
		assertEquals(6., shape.getOriginY());
		shape.setOrigin(0, 0);
		assertEquals(0., shape.getOriginX());
		assertEquals(0., shape.getOriginY());
		shape.setOrigin(0, -5);
		assertEquals(0., shape.getOriginX());
		assertEquals(-5., shape.getOriginY());
		shape.setOrigin(-8, 0);
		assertEquals(-8., shape.getOriginX());
		assertEquals(0., shape.getOriginY());
		shape.setOrigin(-9, Double.NaN);
		assertEquals(-9., shape.getOriginX());
		assertEquals(0., shape.getOriginY());
		shape.setOrigin(Double.POSITIVE_INFINITY, 1);
		assertEquals(-9., shape.getOriginX());
		assertEquals(1., shape.getOriginY());
	}


	@Test
	public void testGetSetGridStartY() {
		shape.setGridEndY(10);
		shape.setGridStartY(5);
		assertEquals(5., shape.getGridStartY());
		shape.setGridStartY(0);
		assertEquals(0., shape.getGridStartY());
		shape.setGridStartY(-5);
		assertEquals(-5., shape.getGridStartY());
		shape.setGridStartY(15);
		assertEquals(-5., shape.getGridStartY());
		shape.setGridStartY(Double.NaN);
		assertEquals(-5., shape.getGridStartY());
		shape.setGridStartY(Double.POSITIVE_INFINITY);
		assertEquals(-5., shape.getGridStartY());
		shape.setGridStartY(Double.NEGATIVE_INFINITY);
		assertEquals(-5., shape.getGridStartY());
	}


	@Test
	public void testGetSetGridStartX() {
		shape.setGridEndX(10);
		shape.setGridStartX(5);
		assertEquals(5., shape.getGridStartX());
		shape.setGridStartX(0);
		assertEquals(0., shape.getGridStartX());
		shape.setGridStartX(-5);
		assertEquals(-5., shape.getGridStartX());
		shape.setGridStartX(15);
		assertEquals(-5., shape.getGridStartX());
		shape.setGridStartX(Double.NaN);
		assertEquals(-5., shape.getGridStartX());
		shape.setGridStartX(Double.POSITIVE_INFINITY);
		assertEquals(-5., shape.getGridStartX());
		shape.setGridStartX(Double.NEGATIVE_INFINITY);
		assertEquals(-5., shape.getGridStartX());
	}


	@Test
	public void testGetSetOriginX() {
		shape.setOriginX(100);
		assertEquals(100., shape.getOriginX());
		shape.setOriginX(-100);
		assertEquals(-100., shape.getOriginX());
		shape.setOriginX(0);
		assertEquals(0., shape.getOriginX());
		shape.setOriginX(Double.NaN);
		assertEquals(0., shape.getOriginX());
		shape.setOriginX(Double.POSITIVE_INFINITY);
		assertEquals(0., shape.getOriginX());
		shape.setOriginX(Double.NEGATIVE_INFINITY);
		assertEquals(0., shape.getOriginX());
	}


	@Test
	public void testGetSetOriginY() {
		shape.setOriginY(100);
		assertEquals(100., shape.getOriginY());
		shape.setOriginY(-100);
		assertEquals(-100., shape.getOriginY());
		shape.setOriginY(0);
		assertEquals(0., shape.getOriginY());
		shape.setOriginY(Double.NaN);
		assertEquals(0., shape.getOriginY());
		shape.setOriginY(Double.POSITIVE_INFINITY);
		assertEquals(0., shape.getOriginY());
		shape.setOriginY(Double.NEGATIVE_INFINITY);
		assertEquals(0., shape.getOriginY());
	}


	@Override
	@Test
	public void testDuplicate() {
		super.testDuplicate();

		shape.setOrigin(20, 30);
		shape.setGridStart(-100, -40);
		shape.setGridEnd(200, 300);
		shape.setXLabelSouth(false);
		shape.setYLabelWest(false);
//		shape.setLabelsSize(IText.TestSize.HUGE1.getSize());

		IStandardGrid g2 = (IStandardGrid) shape.duplicate();

		assertEquals(20., g2.getOriginX());
		assertEquals(30., g2.getOriginY());
		assertEquals(-100., g2.getGridStartX());
		assertEquals(-40., g2.getGridStartY());
		assertEquals(200., g2.getGridEndX());
		assertEquals(300., g2.getGridEndY());
		assertFalse(g2.isXLabelSouth());
		assertFalse(g2.isYLabelWest());
//		assertEquals(IText.TestSize.HUGE1.getSize(), shape2.getLabelsSize());//FIXME
	}


	@Override
	@Test
	public void testCopy() {
		super.testCopy();

		shape2.setOrigin(20, 30);
		shape2.setGridEnd(100, -40);
		shape2.setGridStart(200, 300);
		shape2.setXLabelSouth(false);
		shape2.setYLabelWest(false);
		shape2.setLabelsSize(20);

		shape.copy(shape2);

		assertEquals(shape2.getOriginX(), shape.getOriginX());
		assertEquals(shape2.getOriginY(), shape.getOriginY());
		assertEquals(shape2.getGridEndX(), shape.getGridEndX());
		assertEquals(shape2.getGridEndY(), shape.getGridEndY());
		assertEquals(shape2.getGridStartX(), shape.getGridStartX());
		assertEquals(shape2.getGridStartY(), shape.getGridStartY());
		assertFalse(shape2.isXLabelSouth());
		assertFalse(shape2.isYLabelWest());
		assertEquals(shape2.getLabelsSize(), shape.getLabelsSize());
	}


	@Override
	@Test
	public void testIsParametersEquals() {
		super.testIsParametersEquals();

		shape.setOrigin(20, 30);
		assertFalse(shape.isParametersEquals(shape2, true));
		shape2.setOrigin(20, 30);
		assertTrue(shape.isParametersEquals(shape2, true));
		shape.setGridEnd(100, -40);
		assertFalse(shape.isParametersEquals(shape2, true));
		shape2.setGridEnd(100, -40);
		assertTrue(shape.isParametersEquals(shape2, true));
		shape.setGridStart(-200, -300);
		assertFalse(shape.isParametersEquals(shape2, true));
		shape2.setGridStart(-200, -300);
		assertTrue(shape.isParametersEquals(shape2, true));
		shape.setXLabelSouth(false);
		assertFalse(shape.isParametersEquals(shape2, true));
		shape2.setXLabelSouth(false);
		assertTrue(shape.isParametersEquals(shape2, true));
		shape.setYLabelWest(false);
		assertFalse(shape.isParametersEquals(shape2, true));
		shape2.setYLabelWest(false);
		assertTrue(shape.isParametersEquals(shape2, true));
//		shape.setLabelsSize(IText.TestSize.HUGE1.getSize());//FIXME
//		assertFalse(shape.isParametersEquals(shape2, true));
//		shape2.setLabelsSize(IText.TestSize.HUGE1.getSize());
//		assertTrue(shape.isParametersEquals(shape2, true));

	}
}
