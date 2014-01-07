package test.glib.models.interfaces;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import net.sf.latexdraw.glib.models.ShapeFactory;
import net.sf.latexdraw.glib.models.interfaces.IAxes;
import net.sf.latexdraw.glib.models.interfaces.IShape;
import net.sf.latexdraw.glib.models.interfaces.prop.IAxesProp.AxesStyle;
import net.sf.latexdraw.glib.models.interfaces.prop.IAxesProp.PlottingStyle;
import net.sf.latexdraw.glib.models.interfaces.prop.IAxesProp.TicksStyle;

import org.junit.Test;

import test.HelperTest;

public abstract class TestIAxes<T extends IAxes> extends TestIStandardGrid<T> {

	@Override
	@Test
	public void testGetSetLabelsSize() {//FIXME
//		shape.setLabelsSize(TestSize.FOOTNOTE.getSize());
//		assertEquals(TestSize.FOOTNOTE.getSize(), shape.getLabelsSize());
//		shape.setLabelsSize(TestSize.HUGE1.getSize());
//		assertEquals(TestSize.HUGE1.getSize(), shape.getLabelsSize());
//		shape.setLabelsSize(TestSize.HUGE2.getSize());
//		assertEquals(TestSize.HUGE2.getSize(), shape.getLabelsSize());
//		shape.setLabelsSize(TestSize.LARGE1.getSize());
//		assertEquals(TestSize.LARGE1.getSize(), shape.getLabelsSize());
//		shape.setLabelsSize(TestSize.LARGE2.getSize());
//		assertEquals(TestSize.LARGE2.getSize(), shape.getLabelsSize());
//		shape.setLabelsSize(TestSize.LARGE3.getSize());
//		assertEquals(TestSize.LARGE3.getSize(), shape.getLabelsSize());
//		shape.setLabelsSize(TestSize.NORMAL.getSize());
//		assertEquals(TestSize.NORMAL.getSize(), shape.getLabelsSize());
//		shape.setLabelsSize(TestSize.SCRIPT.getSize());
//		assertEquals(TestSize.SCRIPT.getSize(), shape.getLabelsSize());
//		shape.setLabelsSize(TestSize.SMALL.getSize());
//		assertEquals(TestSize.SMALL.getSize(), shape.getLabelsSize());
//		shape.setLabelsSize(TestSize.TINY.getSize());
//		assertEquals(TestSize.TINY.getSize(), shape.getLabelsSize());
//
//		shape.setLabelsSize(-1);
//		assertEquals(TestSize.TINY.getSize(), shape.getLabelsSize());
//		shape.setLabelsSize(100);
//		assertEquals(TestSize.TINY.getSize(), shape.getLabelsSize());
//		shape.setLabelsSize(1);
//		assertEquals(TestSize.TINY.getSize(), shape.getLabelsSize());
//		shape.setLabelsSize(2);
//		assertEquals(TestSize.TINY.getSize(), shape.getLabelsSize());
//		shape.setLabelsSize(0);
//		assertEquals(TestSize.TINY.getSize(), shape.getLabelsSize());
//		shape.setLabelsSize(-100);
//		assertEquals(TestSize.TINY.getSize(), shape.getLabelsSize());
//		shape.setLabelsSize(3);
//		assertEquals(TestSize.TINY.getSize(), shape.getLabelsSize());
//		shape.setLabelsSize(4);
//		assertEquals(TestSize.TINY.getSize(), shape.getLabelsSize());
//		shape.setLabelsSize(5);
//		assertEquals(TestSize.TINY.getSize(), shape.getLabelsSize());
	}


	@Test
	public void testGetStep() {
		HelperTest.assertEqualsDouble(IShape.PPC, shape.getStep());
	}


	@Test
	public void testGetSetIncrementX() {
		shape.setIncrementX(10);
		HelperTest.assertEqualsDouble(10., shape.getIncrementX());
		shape.setIncrementX(0.1);
		HelperTest.assertEqualsDouble(0.1, shape.getIncrementX());
		shape.setIncrementX(100.5);
		HelperTest.assertEqualsDouble(100.5, shape.getIncrementX());
		shape.setIncrementX(0);
		HelperTest.assertEqualsDouble(100.5, shape.getIncrementX());
		shape.setIncrementX(-10.5);
		HelperTest.assertEqualsDouble(100.5, shape.getIncrementX());
		shape.setIncrementX(Double.NaN);
		HelperTest.assertEqualsDouble(100.5, shape.getIncrementX());
		shape.setIncrementX(Double.POSITIVE_INFINITY);
		HelperTest.assertEqualsDouble(100.5, shape.getIncrementX());
		shape.setIncrementX(Double.NEGATIVE_INFINITY);
		HelperTest.assertEqualsDouble(100.5, shape.getIncrementX());
	}


	@Test
	public void testGetSetIncrementY() {
		shape.setIncrementY(10);
		HelperTest.assertEqualsDouble(10., shape.getIncrementY());
		shape.setIncrementY(0.1);
		HelperTest.assertEqualsDouble(0.1, shape.getIncrementY());
		shape.setIncrementY(100.5);
		HelperTest.assertEqualsDouble(100.5, shape.getIncrementY());
		shape.setIncrementY(0);
		HelperTest.assertEqualsDouble(100.5, shape.getIncrementY());
		shape.setIncrementY(-10.5);
		HelperTest.assertEqualsDouble(100.5, shape.getIncrementY());
		shape.setIncrementY(Double.NaN);
		HelperTest.assertEqualsDouble(100.5, shape.getIncrementY());
		shape.setIncrementY(Double.POSITIVE_INFINITY);
		HelperTest.assertEqualsDouble(100.5, shape.getIncrementY());
		shape.setIncrementY(Double.NEGATIVE_INFINITY);
		HelperTest.assertEqualsDouble(100.5, shape.getIncrementY());
	}


	@Test
	public void testGetSetDistLabelsX() {
		shape.setDistLabelsX(10);
		HelperTest.assertEqualsDouble(10., shape.getDistLabelsX());
		shape.setDistLabelsX(0.1);
		HelperTest.assertEqualsDouble(0.1, shape.getDistLabelsX());
		shape.setDistLabelsX(100.5);
		HelperTest.assertEqualsDouble(100.5, shape.getDistLabelsX());
		shape.setDistLabelsX(0);
		HelperTest.assertEqualsDouble(100.5, shape.getDistLabelsX());
		shape.setDistLabelsX(-10.5);
		HelperTest.assertEqualsDouble(100.5, shape.getDistLabelsX());
		shape.setDistLabelsX(Double.NaN);
		HelperTest.assertEqualsDouble(100.5, shape.getDistLabelsX());
		shape.setDistLabelsX(Double.POSITIVE_INFINITY);
		HelperTest.assertEqualsDouble(100.5, shape.getDistLabelsX());
		shape.setDistLabelsX(Double.NEGATIVE_INFINITY);
		HelperTest.assertEqualsDouble(100.5, shape.getDistLabelsX());
	}


	@Test
	public void testGetSetDistLabelsY() {
		shape.setDistLabelsY(10);
		HelperTest.assertEqualsDouble(10., shape.getDistLabelsY());
		shape.setDistLabelsY(0.1);
		HelperTest.assertEqualsDouble(0.1, shape.getDistLabelsY());
		shape.setDistLabelsY(100.5);
		HelperTest.assertEqualsDouble(100.5, shape.getDistLabelsY());
		shape.setDistLabelsY(0);
		HelperTest.assertEqualsDouble(100.5, shape.getDistLabelsY());
		shape.setDistLabelsY(-10.5);
		HelperTest.assertEqualsDouble(100.5, shape.getDistLabelsY());
		shape.setDistLabelsY(Double.NaN);
		HelperTest.assertEqualsDouble(100.5, shape.getDistLabelsY());
		shape.setDistLabelsY(Double.POSITIVE_INFINITY);
		HelperTest.assertEqualsDouble(100.5, shape.getDistLabelsY());
		shape.setDistLabelsY(Double.NEGATIVE_INFINITY);
		HelperTest.assertEqualsDouble(100.5, shape.getDistLabelsY());
	}



	@Test
	public void testGetSetLabelsDisplayed() {
		shape.setLabelsDisplayed(PlottingStyle.ALL);
		assertEquals(PlottingStyle.ALL, shape.getLabelsDisplayed());
		shape.setLabelsDisplayed(PlottingStyle.NONE);
		assertEquals(PlottingStyle.NONE, shape.getLabelsDisplayed());
		shape.setLabelsDisplayed(PlottingStyle.X);
		assertEquals(PlottingStyle.X, shape.getLabelsDisplayed());
		shape.setLabelsDisplayed(PlottingStyle.Y);
		assertEquals(PlottingStyle.Y, shape.getLabelsDisplayed());
		shape.setLabelsDisplayed(null);
		assertEquals(PlottingStyle.Y, shape.getLabelsDisplayed());
		shape.setLabelsDisplayed(PlottingStyle.ALL);
		assertEquals(PlottingStyle.ALL, shape.getLabelsDisplayed());
	}



	@Test
	public void testIsSetShowOrigin() {
		shape.setShowOrigin(true);
		assertTrue(shape.isShowOrigin());
		shape.setShowOrigin(false);
		assertFalse(shape.isShowOrigin());
		shape.setShowOrigin(true);
		assertTrue(shape.isShowOrigin());
	}



	@Test
	public void testGetSetTicksDisplayed() {
		shape.setTicksDisplayed(PlottingStyle.ALL);
		assertEquals(PlottingStyle.ALL, shape.getTicksDisplayed());
		shape.setTicksDisplayed(PlottingStyle.NONE);
		assertEquals(PlottingStyle.NONE, shape.getTicksDisplayed());
		shape.setTicksDisplayed(PlottingStyle.X);
		assertEquals(PlottingStyle.X, shape.getTicksDisplayed());
		shape.setTicksDisplayed(PlottingStyle.Y);
		assertEquals(PlottingStyle.Y, shape.getTicksDisplayed());
		shape.setTicksDisplayed(null);
		assertEquals(PlottingStyle.Y, shape.getTicksDisplayed());
		shape.setTicksDisplayed(PlottingStyle.ALL);
		assertEquals(PlottingStyle.ALL, shape.getTicksDisplayed());
	}



	@Test
	public void testGetSetTicksStyle() {
		shape.setTicksStyle(TicksStyle.BOTTOM);
		assertEquals(TicksStyle.BOTTOM, shape.getTicksStyle());
		shape.setTicksStyle(TicksStyle.FULL);
		assertEquals(TicksStyle.FULL, shape.getTicksStyle());
		shape.setTicksStyle(TicksStyle.TOP);
		assertEquals(TicksStyle.TOP, shape.getTicksStyle());
		shape.setTicksStyle(null);
		assertEquals(TicksStyle.TOP, shape.getTicksStyle());
		shape.setTicksStyle(TicksStyle.BOTTOM);
		assertEquals(TicksStyle.BOTTOM, shape.getTicksStyle());
	}


	@Test
	public void testGetSetTicksSize() {
		shape.setTicksSize(10);
		HelperTest.assertEqualsDouble(10., shape.getTicksSize());
		shape.setTicksSize(0.1);
		HelperTest.assertEqualsDouble(0.1, shape.getTicksSize());
		shape.setTicksSize(100.5);
		HelperTest.assertEqualsDouble(100.5, shape.getTicksSize());
		shape.setTicksSize(0);
		HelperTest.assertEqualsDouble(100.5, shape.getTicksSize());
		shape.setTicksSize(-10.5);
		HelperTest.assertEqualsDouble(100.5, shape.getTicksSize());
		shape.setTicksSize(Double.NaN);
		HelperTest.assertEqualsDouble(100.5, shape.getTicksSize());
		shape.setTicksSize(Double.POSITIVE_INFINITY);
		HelperTest.assertEqualsDouble(100.5, shape.getTicksSize());
		shape.setTicksSize(Double.NEGATIVE_INFINITY);
		HelperTest.assertEqualsDouble(100.5, shape.getTicksSize());
	}



	@Test
	public void testGetSetAxesStyle() {
		shape.setAxesStyle(AxesStyle.AXES);
		assertEquals(AxesStyle.AXES, shape.getAxesStyle());
		shape.setAxesStyle(AxesStyle.FRAME);
		assertEquals(AxesStyle.FRAME, shape.getAxesStyle());
		shape.setAxesStyle(AxesStyle.NONE);
		assertEquals(AxesStyle.NONE, shape.getAxesStyle());
		shape.setAxesStyle(null);
		assertEquals(AxesStyle.NONE, shape.getAxesStyle());
		shape.setAxesStyle(AxesStyle.AXES);
		assertEquals(AxesStyle.AXES, shape.getAxesStyle());
	}



	@Override
	@Test
	public void testCopy() {
		super.testCopy();

		shape2.setIncrementX(24);
		shape2.setIncrementY(28);
		shape2.setAxesStyle(AxesStyle.FRAME);
		shape2.setTicksStyle(TicksStyle.BOTTOM);
		shape2.setDistLabelsX(12);
		shape2.setDistLabelsY(112);
		shape2.setShowOrigin(false);
		shape2.setTicksDisplayed(PlottingStyle.NONE);
		shape2.setTicksSize(34);

		shape.copy(shape2);

		HelperTest.assertEqualsDouble(shape2.getIncrementX(), shape.getIncrementX());
		HelperTest.assertEqualsDouble(shape2.getIncrementY(), shape.getIncrementY());
		assertEquals(shape2.getAxesStyle(), shape.getAxesStyle());
		HelperTest.assertEqualsDouble(shape2.getTicksSize(), shape.getTicksSize());
		assertEquals(shape2.getTicksDisplayed(), shape.getTicksDisplayed());
		assertEquals(shape2.getTicksStyle(), shape.getTicksStyle());
		HelperTest.assertEqualsDouble(shape2.getDistLabelsX(), shape.getDistLabelsX());
		HelperTest.assertEqualsDouble(shape2.getDistLabelsY(), shape.getDistLabelsY());
		assertEquals(shape2.isShowOrigin(), shape.isShowOrigin());
	}


	@Override
	@Test
	public void testDuplicate() {
		super.testDuplicate();

		shape.setIncrementX(24);
		shape.setIncrementY(28);
		shape.setAxesStyle(AxesStyle.FRAME);
		shape.setTicksStyle(TicksStyle.BOTTOM);
		shape.setDistLabelsX(12);
		shape.setDistLabelsY(112);
		shape.setShowOrigin(false);
		shape.setTicksDisplayed(PlottingStyle.NONE);
		shape.setTicksSize(34);

		IAxes s2 = (IAxes)shape.duplicate();

		assertNotNull(s2);
		HelperTest.assertEqualsDouble(s2.getIncrementX(), shape.getIncrementX());
		HelperTest.assertEqualsDouble(s2.getIncrementY(), shape.getIncrementY());
		assertEquals(s2.getAxesStyle(), shape.getAxesStyle());
		HelperTest.assertEqualsDouble(s2.getTicksSize(), shape.getTicksSize());
		assertEquals(s2.getTicksDisplayed(), shape.getTicksDisplayed());
		assertEquals(s2.getTicksStyle(), shape.getTicksStyle());
		HelperTest.assertEqualsDouble(s2.getDistLabelsX(), shape.getDistLabelsX());
		HelperTest.assertEqualsDouble(s2.getDistLabelsY(), shape.getDistLabelsY());
		assertEquals(s2.isShowOrigin(), shape.isShowOrigin());
	}



	@Override
	public void testGetBottomLeftPoint() {
		shape.setPosition(10, 20);
		HelperTest.assertEqualsDouble(10., shape.getBottomLeftPoint().getX());
		HelperTest.assertEqualsDouble(20., shape.getBottomLeftPoint().getY());
		shape.setPosition(-10, -20);
		HelperTest.assertEqualsDouble(-10., shape.getBottomLeftPoint().getX());
		HelperTest.assertEqualsDouble(-20., shape.getBottomLeftPoint().getY());
	}


	@Override
	public void testGetBottomRightPoint() {
		shape.setPosition(0, 0);
		shape.setGridStart(-200, -100);
		shape.setGridEnd(50, 75);

		HelperTest.assertEqualsDouble(IShape.PPC*250., shape.getBottomRightPoint().getX());
		HelperTest.assertEqualsDouble(0., shape.getBottomRightPoint().getY());
	}


	@Override@Test
	public void testGetTopLeftPoint() {
		shape.setPosition(0, 0);
		shape.setGridStart(-200, -100);
		shape.setGridEnd(50, 75);

		HelperTest.assertEqualsDouble(0., shape.getTopLeftPoint().getX());
		HelperTest.assertEqualsDouble(-IShape.PPC*175., shape.getTopLeftPoint().getY());
	}


	@Override@Test
	public void testGetTopRightPoint() {
		shape.setPosition(0, 0);
		shape.setGridStart(-200, -100);
		shape.setGridEnd(50, 75);

		HelperTest.assertEqualsDouble(IShape.PPC*250., shape.getTopRightPoint().getX());
		HelperTest.assertEqualsDouble(-IShape.PPC*175., shape.getTopRightPoint().getY());
	}


	@Override@Test
	public void testMirrorHorizontal() {
		shape.setPosition(0, 0);
		shape.setGridStart(0, 0);
		shape.setGridEnd(10, 10);

		shape.mirrorHorizontal(ShapeFactory.createPoint(IShape.PPC*10., 0.));
		HelperTest.assertEqualsDouble(IShape.PPC*10., shape.getPosition().getX());
		HelperTest.assertEqualsDouble(0., shape.getPosition().getY());
	}


	@Override@Test
	public void testMirrorVertical() {
		shape.setPosition(0, 0);
		shape.setGridStart(0, 0);
		shape.setGridEnd(10, 10);

		shape.mirrorVertical(ShapeFactory.createPoint(0., -IShape.PPC*10.));
		HelperTest.assertEqualsDouble(0., shape.getPosition().getX());
		HelperTest.assertEqualsDouble(-IShape.PPC*10., shape.getPosition().getY());
	}
}
