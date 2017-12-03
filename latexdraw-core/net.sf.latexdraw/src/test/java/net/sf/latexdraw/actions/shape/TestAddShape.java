package net.sf.latexdraw.actions.shape;

import net.sf.latexdraw.actions.TestUndoableAction;
import net.sf.latexdraw.models.ShapeFactory;
import net.sf.latexdraw.models.interfaces.shape.IShape;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestAddShape extends TestUndoableAction<AddShape, IShape> {
	@Override
	protected AddShape createAction() {
		return new AddShape();
	}

	@Override
	protected void checkUndo() {
		assertFalse(drawing.contains(memento));
		assertTrue(drawing.isEmpty());
	}

	@Override
	protected void configCorrectAction() {
		memento = ShapeFactory.INST.createRectangle();
		action.setDrawing(drawing);
		action.setShape(memento);
	}

	@Test
	public void testGetDrawingOK() {
		configCorrectAction();
		assertTrue(action.getDrawing().isPresent());
		assertEquals(drawing, action.getDrawing().get());
	}

	@Test
	public void testGetDrawingKO() {
		assertFalse(action.getDrawing().isPresent());
	}

	@Override
	protected void checkDo() {
		assertTrue(drawing.contains(memento));
		assertEquals(1, drawing.size());
	}
}
