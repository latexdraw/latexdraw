package net.sf.latexdraw.models.impl;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.AxesStyle;
import net.sf.latexdraw.models.interfaces.shape.IAxes;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.PlottingStyle;
import net.sf.latexdraw.models.interfaces.shape.TicksStyle;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestLGroupAxes {
	IGroup group;
	IAxes axes1;
	IAxes axes2;

	@Before
	public void setUp() {
		group = ShapeFactory.INST.createGroup();
		axes1 = ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint());
		axes2 = ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint());
		group.addShape(ShapeFactory.INST.createRectangle());
		group.addShape(axes1);
		group.addShape(ShapeFactory.INST.createCircle());
		group.addShape(axes2);
	}

	@Test
	public void testGetIncrementXOK() {
		axes1.setIncrementX(11d);
		assertEquals(11d, group.getIncrementX(), 0.00001);
	}

	@Test
	public void testGetIncrementXKO() {
		group.clear();
		assertTrue(Double.isNaN(group.getIncrementX()));
	}

	@Test
	public void testGetIncrementyOK() {
		axes1.setIncrementY(11d);
		assertEquals(11d, group.getIncrementY(), 0.00001);
	}

	@Test
	public void testGetIncrementyKO() {
		group.clear();
		assertTrue(Double.isNaN(group.getIncrementY()));
	}

	@Test
	public void testGetDistLabelsXOK() {
		axes1.setDistLabelsX(11d);
		assertEquals(11d, group.getDistLabelsX(), 0.00001);
	}

	@Test
	public void testGetDistLabelsXKO() {
		group.clear();
		assertTrue(Double.isNaN(group.getDistLabelsX()));
	}

	@Test
	public void testGetDistLabelsYOK() {
		axes1.setDistLabelsY(11d);
		assertEquals(11d, group.getDistLabelsY(), 0.00001);
	}

	@Test
	public void testGetDistLabelsYKO() {
		group.clear();
		assertTrue(Double.isNaN(group.getDistLabelsY()));
	}

	@Test
	public void testGetTicksSizeOK() {
		axes1.setTicksSize(11d);
		assertEquals(11d, group.getTicksSize(), 0.00001);
	}

	@Test
	public void testGetTicksSizeKO() {
		group.clear();
		assertTrue(Double.isNaN(group.getTicksSize()));
	}

	@Test
	public void testGetLabelsDisplayedOK() {
		axes1.setLabelsDisplayed(PlottingStyle.Y);
		assertEquals(PlottingStyle.Y, group.getLabelsDisplayed());
	}

	@Test
	public void testIsShowOriginOK() {
		axes1.setShowOrigin(false);
		assertFalse(group.isShowOrigin());
	}

	@Test
	public void testGetTicksDisplayedOK() {
		axes1.setTicksDisplayed(PlottingStyle.Y);
		assertEquals(PlottingStyle.Y, group.getTicksDisplayed());
	}

	@Test
	public void testGetTicksStyleOK() {
		axes1.setTicksStyle(TicksStyle.BOTTOM);
		assertEquals(TicksStyle.BOTTOM, group.getTicksStyle());
	}

	@Test
	public void testGetAxesStyleOK() {
		axes1.setAxesStyle(AxesStyle.FRAME);
		assertEquals(AxesStyle.FRAME, group.getAxesStyle());
	}

	@Test
	public void testGetIncrementOK() {
		axes1.setIncrement(ShapeFactory.INST.createPoint(1d, 2d));
		assertEquals(ShapeFactory.INST.createPoint(1d, 2d), group.getIncrement());
	}

	@Test
	public void testGetDistLabelsOK() {
		axes1.setDistLabels(ShapeFactory.INST.createPoint(1d, 2d));
		assertEquals(ShapeFactory.INST.createPoint(1d, 2d), group.getDistLabels());
	}

	@Test
	public void testSetIncrementX() {
		group.setIncrementX(0.5);
		assertEquals(0.5, axes1.getIncrementX(), 0.0001);
		assertEquals(0.5, axes2.getIncrementX(), 0.0001);
	}

	@Test
	public void testSetIncrementY() {
		group.setIncrementY(0.5);
		assertEquals(0.5, axes1.getIncrementY(), 0.0001);
		assertEquals(0.5, axes2.getIncrementY(), 0.0001);
	}

	@Test
	public void testSetDistLabelsX() {
		group.setDistLabelsX(0.6);
		assertEquals(0.6, axes1.getDistLabelsX(), 0.0001);
		assertEquals(0.6, axes2.getDistLabelsX(), 0.0001);
	}

	@Test
	public void testSetDistLabelsY() {
		group.setDistLabelsY(0.6);
		assertEquals(0.6, axes1.getDistLabelsY(), 0.0001);
		assertEquals(0.6, axes2.getDistLabelsY(), 0.0001);
	}

	@Test
	public void testSetLabelsDisplayed() {
		group.setLabelsDisplayed(PlottingStyle.NONE);
		assertEquals(PlottingStyle.NONE, axes1.getLabelsDisplayed());
		assertEquals(PlottingStyle.NONE, axes2.getLabelsDisplayed());
	}

	@Test
	public void testSetTicksDisplayed() {
		group.setTicksDisplayed(PlottingStyle.NONE);
		assertEquals(PlottingStyle.NONE, axes1.getTicksDisplayed());
		assertEquals(PlottingStyle.NONE, axes2.getTicksDisplayed());
	}

	@Test
	public void testSetTicksStyle() {
		group.setTicksStyle(TicksStyle.BOTTOM);
		assertEquals(TicksStyle.BOTTOM, axes1.getTicksStyle());
		assertEquals(TicksStyle.BOTTOM, axes2.getTicksStyle());
	}

	@Test
	public void testSetShowOrigin() {
		group.setShowOrigin(false);
		assertFalse(axes1.isShowOrigin());
		assertFalse(axes2.isShowOrigin());
	}

	@Test
	public void testSetTicksSize() {
		group.setTicksSize(3.3);
		assertEquals(3.3, axes1.getTicksSize(), 0.0001);
		assertEquals(3.3, axes2.getTicksSize(), 0.0001);
	}

	@Test
	public void testSetAxesStyle() {
		group.setAxesStyle(AxesStyle.FRAME);
		assertEquals(AxesStyle.FRAME, axes1.getAxesStyle());
		assertEquals(AxesStyle.FRAME, axes2.getAxesStyle());
	}

	@Test
	public void testSetIncrement() {
		group.setIncrement(ShapeFactory.INST.createPoint(11d, 22d));
		assertEquals(ShapeFactory.INST.createPoint(11d, 22d), axes1.getIncrement());
		assertEquals(ShapeFactory.INST.createPoint(11d, 22d), axes2.getIncrement());
	}

	@Test
	public void testSetDistLabels() {
		group.setDistLabels(ShapeFactory.INST.createPoint(11d, 22d));
		assertEquals(ShapeFactory.INST.createPoint(11d, 22d), axes1.getDistLabels());
		assertEquals(ShapeFactory.INST.createPoint(11d, 22d), axes2.getDistLabels());
	}
}
