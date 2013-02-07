package test.glib.models.interfaces;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import net.sf.latexdraw.glib.models.interfaces.DrawingTK;
import net.sf.latexdraw.glib.models.interfaces.IDrawing;
import net.sf.latexdraw.glib.models.interfaces.IGroup;
import net.sf.latexdraw.glib.models.interfaces.IShape;

import org.junit.Test;



public abstract class TestIDrawing extends TestCase {
	public IDrawing drawing;



	@Test
	public void testAddShapeIShape() {
		IShape sh1 = DrawingTK.getFactory().createRectangle(false);
		IShape sh2 = DrawingTK.getFactory().createRectangle(false);

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
		IShape sh1 = DrawingTK.getFactory().createRectangle(false);
		IShape sh2 = DrawingTK.getFactory().createRectangle(false);
		IShape sh3 = DrawingTK.getFactory().createRectangle(false);

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
		IShape sh1 = DrawingTK.getFactory().createRectangle(false);
		IShape sh2 = DrawingTK.getFactory().createRectangle(false);
		IShape sh3 = DrawingTK.getFactory().createRectangle(false);

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
		drawing.removeShape(DrawingTK.getFactory().createRectangle(false));
		assertEquals(2, drawing.getShapes().size());
		assertEquals(sh1, drawing.getShapes().get(0));
		assertEquals(sh3, drawing.getShapes().get(1));
	}


	@Test
	public void testRemoveShapeInt() {
		IShape sh1 = DrawingTK.getFactory().createRectangle(false);
		IShape sh2 = DrawingTK.getFactory().createRectangle(false);
		IShape sh3 = DrawingTK.getFactory().createRectangle(false);

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
		IShape sh1 = DrawingTK.getFactory().createRectangle(false);
		IShape sh2 = DrawingTK.getFactory().createRectangle(false);
		IShape sh3 = DrawingTK.getFactory().createRectangle(false);

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
		IShape sh1 = DrawingTK.getFactory().createRectangle(false);
		IShape sh2 = DrawingTK.getFactory().createRectangle(false);
		IShape sh3 = DrawingTK.getFactory().createRectangle(false);

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
		IShape sh1 = DrawingTK.getFactory().createRectangle(false);
		IShape sh2 = DrawingTK.getFactory().createRectangle(false);
		IShape sh3 = DrawingTK.getFactory().createRectangle(false);
		IShape sh4 = DrawingTK.getFactory().createRectangle(false);

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
		IShape sh1 = DrawingTK.getFactory().createRectangle(false);
		IShape sh2 = DrawingTK.getFactory().createRectangle(false);
		IShape sh3 = DrawingTK.getFactory().createRectangle(false);

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
		IShape sh1 = DrawingTK.getFactory().createRectangle(false);
		IShape sh2 = DrawingTK.getFactory().createRectangle(false);
		IShape sh3 = DrawingTK.getFactory().createRectangle(false);

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
	public void testRemoveSelection() {
		drawing.getSelection().addShape(DrawingTK.getFactory().createRectangle(false));
		drawing.removeSelection();
		assertEquals(0, drawing.getSelection().size());
		drawing.getSelection().addShape(DrawingTK.getFactory().createRectangle(false));
		drawing.getSelection().addShape(DrawingTK.getFactory().createRectangle(false));
		drawing.getSelection().addShape(DrawingTK.getFactory().createRectangle(false));
		drawing.getSelection().addShape(DrawingTK.getFactory().createRectangle(false));
		drawing.removeSelection();
		assertEquals(0, drawing.getSelection().size());
	}



	@Test
	public void testSetSelection() {
		IShape sh = DrawingTK.getFactory().createRectangle(false);

		drawing.setSelection(sh);
		assertEquals(1, drawing.getSelection().size());
		assertEquals(sh, drawing.getSelection().getShapeAt(0));

		IShape sh2 = DrawingTK.getFactory().createRectangle(false);
		sh = DrawingTK.getFactory().createRectangle(false);

		drawing.setSelection(sh);
		drawing.setSelection(sh2);
		assertEquals(1, drawing.getSelection().size());
		assertEquals(sh2, drawing.getSelection().getShapeAt(0));
		drawing.setSelection((IShape)null);
		assertNotNull(drawing.getSelection());
		assertEquals(0, drawing.getSelection().size());
	}



	@Test
	public void testSetSelectionList() {
		List<IShape> list = new ArrayList<IShape>();
		IGroup selection  = drawing.getSelection();
		IShape sh = DrawingTK.getFactory().createRectangle(false);
		IShape sh2;

		list.add(sh);
		drawing.setSelection(list);
		assertEquals(selection, drawing.getSelection());
		assertEquals(1, drawing.getSelection().size());
		assertEquals(sh, drawing.getSelection().getShapeAt(0));

		list = new ArrayList<IShape>();
		sh   = DrawingTK.getFactory().createRectangle(false);
		sh2  = DrawingTK.getFactory().createRectangle(false);
		list.add(sh);
		list.add(sh2);
		drawing.setSelection(list);
		assertEquals(selection, drawing.getSelection());
		assertEquals(2, drawing.getSelection().size());
		assertEquals(sh, drawing.getSelection().getShapeAt(0));
		assertEquals(sh2, drawing.getSelection().getShapeAt(1));
		drawing.setSelection((List<IShape>)null);
		assertNotNull(drawing.getSelection());
		assertEquals(0, drawing.getSelection().size());
	}



	@Test
	public void testRemoveFromSelection() {
		IShape sh1 = DrawingTK.getFactory().createRectangle(false);
		IShape sh2 = DrawingTK.getFactory().createRectangle(false);
		IShape sh3 = DrawingTK.getFactory().createRectangle(false);

		drawing.getSelection().addShape(sh1);
		drawing.getSelection().addShape(sh2);
		drawing.getSelection().addShape(sh3);
		drawing.removeFromSelection(sh2);
		assertEquals(2, drawing.getSelection().size());
		assertEquals(sh1, drawing.getSelection().getShapeAt(0));
		assertEquals(sh3, drawing.getSelection().getShapeAt(1));
		drawing.removeFromSelection(null);
		assertEquals(2, drawing.getSelection().size());
		assertEquals(sh1, drawing.getSelection().getShapeAt(0));
		assertEquals(sh3, drawing.getSelection().getShapeAt(1));
		drawing.removeFromSelection(DrawingTK.getFactory().createRectangle(false));
		assertEquals(2, drawing.getSelection().size());
		assertEquals(sh1, drawing.getSelection().getShapeAt(0));
		assertEquals(sh3, drawing.getSelection().getShapeAt(1));
	}



	@Test
	public void testAddToSelection() {
		IShape sh1 = DrawingTK.getFactory().createRectangle(false);
		IShape sh2 = DrawingTK.getFactory().createRectangle(false);

		drawing.addToSelection(sh1);
		drawing.addToSelection(sh2);
		assertEquals(2, drawing.getSelection().size());
		assertEquals(sh1, drawing.getSelection().getShapeAt(0));
		assertEquals(sh2, drawing.getSelection().getShapeAt(1));
		drawing.addToSelection((IShape)null);
		assertEquals(2, drawing.getSelection().size());
		assertEquals(sh1, drawing.getSelection().getShapeAt(0));
		assertEquals(sh2, drawing.getSelection().getShapeAt(1));
	}



	@Test
	public void testAddToSelectionList() {
		IShape sh1 = DrawingTK.getFactory().createRectangle(false);
		IShape sh2 = DrawingTK.getFactory().createRectangle(false);
		List<IShape> list = new ArrayList<IShape>();
		IGroup selection = drawing.getSelection();

		list.add(sh1);
		list.add(sh2);
		drawing.addToSelection(list);
		assertEquals(selection, drawing.getSelection());
		assertEquals(2, drawing.getSelection().size());
		assertEquals(sh1, drawing.getSelection().getShapeAt(0));
		assertEquals(sh2, drawing.getSelection().getShapeAt(1));
		drawing.addToSelection((List<IShape>)null);
		assertEquals(selection, drawing.getSelection());
		assertEquals(2, drawing.getSelection().size());
		assertEquals(sh1, drawing.getSelection().getShapeAt(0));
		assertEquals(sh2, drawing.getSelection().getShapeAt(1));
	}
}
