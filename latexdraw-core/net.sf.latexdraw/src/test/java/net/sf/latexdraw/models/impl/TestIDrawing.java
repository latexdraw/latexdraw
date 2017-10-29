package net.sf.latexdraw.models.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IDrawing;
import net.sf.latexdraw.models.interfaces.shape.IGroup;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestIDrawing {
	IDrawing drawing;

	@Before
	public void setUp() {
		drawing = ShapeFactory.INST.createDrawing();
	}

	@Test
	public void testConstructor() {
		IDrawing d = ShapeFactory.INST.createDrawing();
		assertNotNull(d.getSelection());
		assertNotNull(d.getShapes());
	}

	@Test
	public void testGetSelection() {
		assertNotNull(drawing.getSelection());
	}

	@Test
	public void testSetSelectionList1() {
		List<IShape> list = new ArrayList<>();
		IGroup selection = drawing.getSelection();
		IShape sh = ShapeFactory.INST.createRectangle();
		IShape sh2;

		list.add(sh);
		drawing.setSelection(list);
		assertEquals(selection, drawing.getSelection());
		assertEquals(1, drawing.getSelection().size());
		assertEquals(sh, drawing.getSelection().getShapeAt(0));
	}

	@Test
	public void testSetSelectionList2() {
		List<IShape> list = new ArrayList<>();
		IGroup selection = drawing.getSelection();
		IShape sh = ShapeFactory.INST.createRectangle();
		IShape sh2 = ShapeFactory.INST.createRectangle();
		list.add(sh);
		list.add(sh2);
		drawing.setSelection(list);
		assertEquals(selection, drawing.getSelection());
		assertEquals(2, drawing.getSelection().size());
		assertEquals(sh, drawing.getSelection().getShapeAt(0));
		assertEquals(sh2, drawing.getSelection().getShapeAt(1));
	}

	@Test
	public void testSetSelectionListEmpty() {
		drawing.setSelection(Collections.<IShape> emptyList());
		assertNotNull(drawing.getSelection());
		assertEquals(0, drawing.getSelection().size());
	}
}
