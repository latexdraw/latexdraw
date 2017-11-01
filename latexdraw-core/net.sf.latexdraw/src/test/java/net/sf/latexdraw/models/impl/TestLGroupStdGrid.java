package net.sf.latexdraw.models.impl;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IAxes;
import net.sf.latexdraw.models.interfaces.shape.IGrid;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestLGroupStdGrid {
	private IGroup group;
	private IGrid grid1;
	private IAxes grid2;

	@Before
	public void setUp() {
		group = ShapeFactory.INST.createGroup();
		grid1 = ShapeFactory.INST.createGrid(ShapeFactory.INST.createPoint());
		grid2 = ShapeFactory.INST.createAxes(ShapeFactory.INST.createPoint());

		group.addShape(ShapeFactory.INST.createRectangle());
		group.addShape(grid1);
		group.addShape(ShapeFactory.INST.createCircle());
		group.addShape(grid2);

		grid1.setLabelsSize(23);
		grid1.setGridStart(-1d, -2d);
		grid1.setGridEnd(3d, 5d);
		grid1.setOrigin(2d, 3d);

		grid2.setLabelsSize(11);
		grid2.setGridStart(-2d, -3d);
		grid2.setGridEnd(4d, 6d);
		grid2.setOrigin(1d, -1d);
	}

	@Test
	public void testGetLabelsSize() {
		assertEquals(23, group.getLabelsSize());
	}

	@Test
	public void testGetGridStart() {
		assertEquals(grid1.getGridStart(), group.getGridStart());
	}

	@Test
	public void testGetGridStartX() {
		assertEquals(grid1.getGridStartX(), group.getGridStartX(), 0.00001);
	}

	@Test
	public void testGetGridStartY() {
		assertEquals(grid1.getGridStartY(), group.getGridStartY(), 0.00001);
	}

	@Test
	public void testGetGridEnd() {
		assertEquals(grid1.getGridEnd(), group.getGridEnd());
	}

	@Test
	public void testGetGridEndX() {
		assertEquals(grid1.getGridEndX(), group.getGridEndX(), 0.00001);
	}

	@Test
	public void testGetGridEndY() {
		assertEquals(grid1.getGridEndY(), group.getGridEndY(), 0.00001);
	}

	@Test
	public void testGetOriginX() {
		assertEquals(grid1.getOriginX(), group.getOriginX(), 0.0001);
	}

	@Test
	public void testGetOriginY() {
		assertEquals(grid1.getOriginY(), group.getOriginY(), 0.0001);
	}

	@Test
	public void testGetGridMinX() {
		assertEquals(grid1.getGridMinX(), group.getGridMinX(), 0.0001);
	}

	@Test
	public void testGetGridMinY() {
		assertEquals(grid1.getGridMinY(), group.getGridMinY(), 0.0001);
	}

	@Test
	public void testGetGridMaxX() {
		assertEquals(grid1.getGridMaxX(), group.getGridMaxX(), 0.0001);
	}

	@Test
	public void testGetGridMaxY() {
		assertEquals(grid1.getGridMaxY(), group.getGridMaxY(), 0.0001);
	}

	@Test
	public void testGetStep() {
		assertEquals(grid1.getStep(), group.getStep(), 0.0001);
	}

	@Test
	public void testSetLabelsSize() {
		group.setLabelsSize(24);
		assertEquals(24, grid1.getLabelsSize());
		assertEquals(24, grid2.getLabelsSize());
	}

	@Test
	public void testSetGridEndX() {
		group.setGridEndX(24d);
		assertEquals(24d, grid1.getGridEndX(), 0.0001);
		assertEquals(24d, grid2.getGridEndX(), 0.0001);
	}

	@Test
	public void testSetGridEndY() {
		group.setGridEndY(23d);
		assertEquals(23d, grid1.getGridEndY(), 0.0001);
		assertEquals(23d, grid2.getGridEndY(), 0.0001);
	}

	@Test
	public void testSetGridEnd() {
		group.setGridEnd(11d, 13d);
		assertEquals(ShapeFactory.INST.createPoint(11d, 13d), grid1.getGridEnd());
		assertEquals(ShapeFactory.INST.createPoint(11d, 13d), grid2.getGridEnd());
	}

	@Test
	public void testSetGridStart() {
		group.setGridStart(-2d, -5d);
		assertEquals(ShapeFactory.INST.createPoint(-2d, -5d), grid1.getGridStart());
		assertEquals(ShapeFactory.INST.createPoint(-2d, -5d), grid2.getGridStart());
	}

	@Test
	public void testSetOrigin() {
		group.setOrigin(-2d, -5d);
		assertEquals(-2d, grid1.getOriginX(), 0.0001);
		assertEquals(-5d, grid1.getOriginY(), 0.0001);
		assertEquals(-2d, grid2.getOriginX(), 0.0001);
		assertEquals(-5d, grid2.getOriginY(), 0.0001);
	}

	@Test
	public void testSetGridStartX() {
		group.setGridStartX(-2d);
		assertEquals(-2d, grid1.getGridStartX(), 0.0001);
		assertEquals(-2d, grid2.getGridStartX(), 0.0001);
	}

	@Test
	public void testSetGridStartY() {
		group.setGridStartY(2d);
		assertEquals(2d, grid1.getGridStartY(), 0.0001);
		assertEquals(2d, grid2.getGridStartY(), 0.0001);
	}

	@Test
	public void testSetOriginX() {
		group.setOriginX(11d);
		assertEquals(11d, grid1.getOriginX(), 0.0001);
		assertEquals(11d, grid2.getOriginX(), 0.0001);
	}

	@Test
	public void testSetOriginY() {
		group.setOriginY(12d);
		assertEquals(12d, grid1.getOriginY(), 0.0001);
		assertEquals(12d, grid2.getOriginY(), 0.0001);
	}
}
