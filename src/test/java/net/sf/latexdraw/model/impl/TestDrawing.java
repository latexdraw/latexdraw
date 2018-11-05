package net.sf.latexdraw.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.sf.latexdraw.model.ShapeFactory;
import net.sf.latexdraw.model.api.shape.Drawing;
import net.sf.latexdraw.model.api.shape.Group;
import net.sf.latexdraw.model.api.shape.Shape;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestDrawing {
	Drawing drawing;

	@Before
	public void setUp() {
		drawing = ShapeFactory.INST.createDrawing();
	}

	@Test
	public void testConstructor() {
		final Drawing d = ShapeFactory.INST.createDrawing();
		assertNotNull(d.getSelection());
		assertNotNull(d.getShapes());
	}

	@Test
	public void testGetSelection() {
		assertNotNull(drawing.getSelection());
	}

	@Test
	public void testSetSelectionList1() {
		final List<Shape> list = new ArrayList<>();
		final Group selection = drawing.getSelection();
		final Shape sh = ShapeFactory.INST.createRectangle();

		list.add(sh);
		drawing.setSelection(list);
		assertEquals(selection, drawing.getSelection());
		assertEquals(1, drawing.getSelection().size());
		assertEquals(sh, drawing.getSelection().getShapeAt(0));
	}

	@Test
	public void testSetSelectionList2() {
		final List<Shape> list = new ArrayList<>();
		final Group selection = drawing.getSelection();
		final Shape sh = ShapeFactory.INST.createRectangle();
		final Shape sh2 = ShapeFactory.INST.createRectangle();
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
		drawing.setSelection(Collections.emptyList());
		assertNotNull(drawing.getSelection());
		assertEquals(0, drawing.getSelection().size());
	}
}
