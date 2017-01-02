package test.glib.models.interfaces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IShape;

import org.junit.Test;

public abstract class TestIDrawing {
	public IDrawing drawing;

	@Test
	public void testAddShapeIShape() {
		IShape sh1 = ShapeFactory.INST.createRectangle();
		IShape sh2 = ShapeFactory.INST.createRectangle();

		drawing.addShape(sh1);
		drawing.addShape(sh2);
		assertEquals(2, drawing.getShapes().size());
		assertEquals(sh1, drawing.getShapes().get(0));
		assertEquals(sh2, drawing.getShapes().get(1));
		drawing.addShape((IShape)null);
		assertEquals(2, drawing.getShapes().size());
		assertEquals(sh1, drawing.getShapes().get(0));
		assertEquals(sh2, drawing.getShapes().get(1));
	}

	@Test
	public void testAddShapeIShapeInt() {
		IShape sh1 = ShapeFactory.INST.createRectangle();
		IShape sh2 = ShapeFactory.INST.createRectangle();
		IShape sh3 = ShapeFactory.INST.createRectangle();

		drawing.addShape(sh1, 1);
		assertEquals(0, drawing.getShapes().size());
		drawing.addShape(sh1, -2);
		assertEquals(0, drawing.getShapes().size());
		drawing.addShape(sh1, 0);
		assertEquals(1, drawing.getShapes().size());
		assertEquals(sh1, drawing.getShapes().get(0));
		drawing.addShape(null, 0);
		assertEquals(1, drawing.getShapes().size());
		assertEquals(sh1, drawing.getShapes().get(0));
		drawing.addShape(sh2, 2);
		assertEquals(1, drawing.getShapes().size());
		drawing.addShape(sh2, -2);
		assertEquals(1, drawing.getShapes().size());
		drawing.addShape(sh2, -1);
		assertEquals(2, drawing.getShapes().size());
		assertEquals(sh1, drawing.getShapes().get(0));
		assertEquals(sh2, drawing.getShapes().get(1));
		drawing.addShape(sh3, 1);
		assertEquals(3, drawing.getShapes().size());
		assertEquals(sh1, drawing.getShapes().get(0));
		assertEquals(sh2, drawing.getShapes().get(2));
		assertEquals(sh3, drawing.getShapes().get(1));
	}

	@Test
	public void testRemoveShapeIShape() {
		IShape sh1 = ShapeFactory.INST.createRectangle();
		IShape sh2 = ShapeFactory.INST.createRectangle();
		IShape sh3 = ShapeFactory.INST.createRectangle();

		drawing.getShapes().add(sh1);
		drawing.getShapes().add(sh2);
		drawing.getShapes().add(sh3);
		drawing.removeShape(sh2);
		assertEquals(2, drawing.getShapes().size());
		assertEquals(sh1, drawing.getShapes().get(0));
		assertEquals(sh3, drawing.getShapes().get(1));
		drawing.removeShape(null);
		assertEquals(2, drawing.getShapes().size());
		assertEquals(sh1, drawing.getShapes().get(0));
		assertEquals(sh3, drawing.getShapes().get(1));
		drawing.removeShape(ShapeFactory.INST.createRectangle());
		assertEquals(2, drawing.getShapes().size());
		assertEquals(sh1, drawing.getShapes().get(0));
		assertEquals(sh3, drawing.getShapes().get(1));
	}

	@Test
	public void testRemoveShapeInt() {
		IShape sh1 = ShapeFactory.INST.createRectangle();
		IShape sh2 = ShapeFactory.INST.createRectangle();
		IShape sh3 = ShapeFactory.INST.createRectangle();

		drawing.getShapes().add(sh1);
		drawing.getShapes().add(sh2);
		drawing.getShapes().add(sh3);

		assertNull(drawing.removeShape(4));
		assertNull(drawing.removeShape(-2));
		assertEquals(sh2, drawing.removeShape(1));
		assertEquals(sh1, drawing.removeShape(0));
		assertEquals(sh3, drawing.removeShape(-1));
	}

	@Test
	public void testGetShapeAt() {
		IShape sh1 = ShapeFactory.INST.createRectangle();
		IShape sh2 = ShapeFactory.INST.createRectangle();
		IShape sh3 = ShapeFactory.INST.createRectangle();

		drawing.getShapes().add(sh1);
		drawing.getShapes().add(sh2);
		drawing.getShapes().add(sh3);

		assertEquals(sh1, drawing.getShapeAt(0));
		assertEquals(sh2, drawing.getShapeAt(1));
		assertEquals(sh3, drawing.getShapeAt(2));
		assertEquals(sh3, drawing.getShapeAt(-1));
		assertNull(drawing.getShapeAt(-2));
		assertNull(drawing.getShapeAt(3));
	}

	@Test
	public void testSize() {
		IShape sh1 = ShapeFactory.INST.createRectangle();
		IShape sh2 = ShapeFactory.INST.createRectangle();
		IShape sh3 = ShapeFactory.INST.createRectangle();

		drawing.getShapes().add(sh1);
		assertEquals(1, drawing.size());
		drawing.getShapes().add(sh2);
		assertEquals(2, drawing.size());
		drawing.getShapes().add(sh3);
		assertEquals(3, drawing.size());
		drawing.getShapes().clear();
		assertEquals(0, drawing.size());
	}

	@Test
	public void testContains() {
		IShape sh1 = ShapeFactory.INST.createRectangle();
		IShape sh2 = ShapeFactory.INST.createRectangle();
		IShape sh3 = ShapeFactory.INST.createRectangle();
		IShape sh4 = ShapeFactory.INST.createRectangle();

		drawing.getShapes().add(sh1);
		drawing.getShapes().add(sh2);
		drawing.getShapes().add(sh3);

		assertFalse(drawing.contains(null));
		assertFalse(drawing.contains(sh4));
		assertTrue(drawing.contains(sh1));
		assertTrue(drawing.contains(sh2));
		assertTrue(drawing.contains(sh3));
	}

	@Test
	public void testIsEmpty() {
		IShape sh1 = ShapeFactory.INST.createRectangle();
		IShape sh2 = ShapeFactory.INST.createRectangle();
		IShape sh3 = ShapeFactory.INST.createRectangle();

		drawing.getShapes().clear();
		assertTrue(drawing.isEmpty());
		drawing.getShapes().add(sh1);
		assertFalse(drawing.isEmpty());
		drawing.getShapes().add(sh2);
		assertFalse(drawing.isEmpty());
		drawing.getShapes().add(sh3);
		assertFalse(drawing.isEmpty());
		drawing.getShapes().clear();
		assertTrue(drawing.isEmpty());
	}

	@Test
	public void testClear() {
		IShape sh1 = ShapeFactory.INST.createRectangle();
		IShape sh2 = ShapeFactory.INST.createRectangle();
		IShape sh3 = ShapeFactory.INST.createRectangle();

		drawing.getShapes().add(sh1);
		drawing.getShapes().add(sh2);
		drawing.getShapes().add(sh3);
		drawing.clear();
		assertEquals(0, drawing.getShapes().size());
	}

	@Test
	public void testGetShapes() {
		assertNotNull(drawing.getShapes());
	}

	@Test
	public void testGetSelection() {
		assertNotNull(drawing.getSelection());
	}

	@Test
	public void testSetSelectionList() {
		List<IShape> list = new ArrayList<>();
		IGroup selection = drawing.getSelection();
		IShape sh = ShapeFactory.INST.createRectangle();
		IShape sh2;

		list.add(sh);
		drawing.setSelection(list);
		assertEquals(selection, drawing.getSelection());
		assertEquals(1, drawing.getSelection().size());
		assertEquals(sh, drawing.getSelection().getShapeAt(0));

		list = new ArrayList<>();
		sh = ShapeFactory.INST.createRectangle();
		sh2 = ShapeFactory.INST.createRectangle();
		list.add(sh);
		list.add(sh2);
		drawing.setSelection(list);
		assertEquals(selection, drawing.getSelection());
		assertEquals(2, drawing.getSelection().size());
		assertEquals(sh, drawing.getSelection().getShapeAt(0));
		assertEquals(sh2, drawing.getSelection().getShapeAt(1));
		drawing.setSelection(Collections.<IShape> emptyList());
		assertNotNull(drawing.getSelection());
		assertEquals(0, drawing.getSelection().size());
	}
}
